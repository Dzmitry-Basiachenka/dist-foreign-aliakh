databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-10-30-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-53752 FDA: Create NTS withdrawn batch summary report: create DB view for generating batch summary report")

        createView(viewName: 'v_nts_withdrawn_batch_summary_report', schemaName: dbAppsSchema) {
            """select
                rro.name rro_name,
                rro.rh_account_number rro_account_number,
                report.batch_name,
                report.payment_date,
                report.gross_amount,
                report.nts_details_count,
                report.nts_details_gross_amount,
                report.created_datetime
            from 
                (select
                    nested_report.rro_account_number,
                    nested_report.batch_name as batch_name,
                    nested_report.payment_date,
                    nested_report.gross_amount,
                    sum(nested_report.nts_details_count) nts_details_count,
                    sum(nested_report.nts_details_gross_amount) nts_details_gross_amount,
                    nested_report.created_datetime
                from
                    (select 
                        b.rro_account_number,
                        b.name as batch_name,
                        b.payment_date,
                        b.gross_amount,
                        count(1) as nts_details_count,
                        sum(u.gross_amount) as nts_details_gross_amount,
                        b.created_datetime
                    from ${dbAppsSchema}.df_usage u
                    join ${dbAppsSchema}.df_usage_batch b on b.df_usage_batch_uid = u.df_usage_batch_uid
                    where u.status_ind = 'NTS_WITHDRAWN'
                    group by b.rro_account_number, b.name, b.payment_date, b.gross_amount, u.product_family, b.created_datetime) as nested_report
                group by nested_report.rro_account_number, nested_report.batch_name, nested_report.payment_date, nested_report.gross_amount, nested_report.created_datetime) as report
            left join ${dbAppsSchema}.df_rightsholder rro on rro.rh_account_number = report.rro_account_number"""
        }

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2019-12-04-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-55836 FDA: Database changes to support AACL: create table for FAS specific fields")

        createTable(tableName: 'df_usage_fas', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing specific fields of usages with FAS/FAS2/NTS product family') {

            column(name: 'df_usage_fas_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage')
            column(name: 'article', type: 'VARCHAR(1000)', remarks: 'The article')
            column(name: 'author', type: 'VARCHAR(2000)', remarks: 'The author')
            column(name: 'publisher', type: 'VARCHAR(1000)', remarks: 'The publisher')
            column(name: 'publication_date', type: 'DATE', remarks: 'The publication date')
            column(name: 'market', type: 'VARCHAR(200)', remarks: 'The market')
            column(name: 'market_period_from', type: 'NUMERIC(4,0)', remarks: 'The beginning period of when the usage occured')
            column(name: 'market_period_to', type: 'NUMERIC(4,0)', remarks: 'The ending period of when the usage occured')
            column(name: 'df_fund_pool_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool')
            column(name: 'is_rh_participating_flag', type: 'BOOLEAN', defaultValueBoolean: false, remarks: 'RH participating flag')
            column(name: 'is_payee_participating_flag', type: 'BOOLEAN', defaultValueBoolean: false, remarks: 'Payee participating flag')
            column(name: 'reported_value', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The amount in original currency')
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1',
                    remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM', remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()',
                    remarks: 'The date and time this record was created') {
                constraints(nullable: false)
            }
            column(name: 'updated_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM',
                    remarks: 'The user name who updated this record; when a record is first created, this will be the same as the created_by_user') {
                constraints(nullable: false)
            }
            column(name: 'updated_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()',
                    remarks: 'The date and time this record was created; when a record is first created, this will be the same as the created_datetime') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_usage_fas', tablespace: dbIndexTablespace,
                columnNames: 'df_usage_fas_uid', constraintName: 'df_usage_fas_pk')

        sql("""insert into ${dbAppsSchema}.df_usage_fas
            select df_usage_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, is_payee_participating_flag, reported_value
            from ${dbAppsSchema}.df_usage where product_family='FAS'"""
        )

        sql("""insert into ${dbAppsSchema}.df_usage_fas
            select df_usage_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, is_payee_participating_flag, reported_value
            from ${dbAppsSchema}.df_usage where product_family='FAS2'"""
        )

        sql("""insert into ${dbAppsSchema}.df_usage_fas
            select df_usage_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, is_payee_participating_flag, reported_value
            from ${dbAppsSchema}.df_usage where product_family='NTS'"""
        )

        sql("""insert into ${dbAppsSchema}.df_usage_fas
            (df_usage_fas_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, reported_value)
            select df_usage_archive_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, reported_value
            from ${dbAppsSchema}.df_usage_archive where product_family='FAS'"""
        )

        sql("""insert into ${dbAppsSchema}.df_usage_fas
            (df_usage_fas_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, reported_value)
            select df_usage_archive_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, reported_value
            from ${dbAppsSchema}.df_usage_archive where product_family='FAS2'"""
        )

        sql("""insert into ${dbAppsSchema}.df_usage_fas
            (df_usage_fas_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, reported_value)
            select df_usage_archive_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, reported_value
            from ${dbAppsSchema}.df_usage_archive where product_family='NTS'"""
        )

        rollback {
            dropTable(tableName: 'df_usage_fas', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2019-12-11-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-55836 FDA: Database changes to support AACL: remove FAS specific fields from df_usage and df_usage_archive tables")

        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'article')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'author')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'publisher')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'publication_date')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'market')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'market_period_from')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'market_period_to')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'df_fund_pool_uid')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'is_rh_participating_flag')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'is_payee_participating_flag')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'reported_value')

        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'article')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'author')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'publisher')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'publication_date')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market_period_from')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market_period_to')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'df_fund_pool_uid')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'is_rh_participating_flag')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'reported_value')


        rollback {
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'article', type: 'VARCHAR(1000)', remarks: 'The article')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'author', type: 'VARCHAR(2000)', remarks: 'The author')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'publisher', type: 'VARCHAR(1000)', remarks: 'The publisher')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'publication_date', type: 'DATE', remarks: 'The publication date')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'market', type: 'VARCHAR(200)', remarks: 'The market')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'market_period_from', type: 'NUMERIC(4,0)', remarks: 'The beginning period of when the usage occured')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'market_period_to', type: 'NUMERIC(4,0)', remarks: 'The ending period of when the usage occured')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'df_fund_pool_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'is_rh_participating_flag', type: 'BOOLEAN', defaultValueBoolean: false, remarks: 'RH participating flag') {
                    constraints(nullable: false)
                }
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'is_payee_participating_flag', type: 'BOOLEAN', defaultValueBoolean: false, remarks: 'Payee participating flag') {
                    constraints(nullable: false)
                }
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'reported_value', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The amount in original currency') {
                    constraints(nullable: false)
                }
            }

            sql("""update ${dbAppsSchema}.df_usage u
                set 
                    article = ufas.article, 
                    author = ufas.author, 
                    publisher = ufas.publisher, 
                    publication_date = ufas.publication_date, 
                    market = ufas.market, 
                    market_period_from = ufas.market_period_from, 
                    market_period_to = ufas.market_period_to, 
                    df_fund_pool_uid = ufas.df_fund_pool_uid, 
                    is_rh_participating_flag = ufas.is_rh_participating_flag, 
                    is_payee_participating_flag = ufas.is_payee_participating_flag, 
                    reported_value = ufas.reported_value
                from ${dbAppsSchema}.df_usage_fas ufas
                where u.df_usage_uid = ufas.df_usage_fas_uid
                and product_family = 'FAS'"""
            )

            sql("""update ${dbAppsSchema}.df_usage u
                set 
                    article = ufas.article, 
                    author = ufas.author, 
                    publisher = ufas.publisher, 
                    publication_date = ufas.publication_date, 
                    market = ufas.market, 
                    market_period_from = ufas.market_period_from, 
                    market_period_to = ufas.market_period_to, 
                    df_fund_pool_uid = ufas.df_fund_pool_uid, 
                    is_rh_participating_flag = ufas.is_rh_participating_flag, 
                    is_payee_participating_flag = ufas.is_payee_participating_flag, 
                    reported_value = ufas.reported_value
                from ${dbAppsSchema}.df_usage_fas ufas
                where u.df_usage_uid = ufas.df_usage_fas_uid
                and product_family = 'FAS2'"""
            )

            sql("""update ${dbAppsSchema}.df_usage u
                set 
                    article = ufas.article, 
                    author = ufas.author, 
                    publisher = ufas.publisher, 
                    publication_date = ufas.publication_date, 
                    market = ufas.market, 
                    market_period_from = ufas.market_period_from, 
                    market_period_to = ufas.market_period_to, 
                    df_fund_pool_uid = ufas.df_fund_pool_uid, 
                    is_rh_participating_flag = ufas.is_rh_participating_flag, 
                    is_payee_participating_flag = ufas.is_payee_participating_flag, 
                    reported_value = ufas.reported_value
                from ${dbAppsSchema}.df_usage_fas ufas
                where u.df_usage_uid = ufas.df_usage_fas_uid
                and product_family = 'NTS'"""
            )

            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'market',
                    columnDataType: 'VARCHAR(200)')
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'market_period_from',
                    columnDataType: 'NUMERIC(4,0)')
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'market_period_to',
                    columnDataType: 'NUMERIC(4,0)')

            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'article', type: 'VARCHAR(1000)', remarks: 'The article')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'author', type: 'VARCHAR(2000)', remarks: 'The author')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'publisher', type: 'VARCHAR(1000)', remarks: 'The publisher')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'publication_date', type: 'DATE', remarks: 'The publication date')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'market', type: 'VARCHAR(200)', remarks: 'The market')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'market_period_from', type: 'NUMERIC(4,0)', remarks: 'The beginning period of when the usage occured')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'market_period_to', type: 'NUMERIC(4,0)', remarks: 'The ending period of when the usage occured')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'df_fund_pool_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool')
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'is_rh_participating_flag', type: 'BOOLEAN', defaultValueBoolean: false, remarks: 'RH participating flag') {
                    constraints(nullable: false)
                }
            }
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'reported_value', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The amount in original currency') {
                    constraints(nullable: false)
                }
            }

            sql("""update ${dbAppsSchema}.df_usage_archive u
                set 
                    article = ufas.article, 
                    author = ufas.author, 
                    publisher = ufas.publisher, 
                    publication_date = ufas.publication_date, 
                    market = ufas.market, 
                    market_period_from = ufas.market_period_from, 
                    market_period_to = ufas.market_period_to, 
                    df_fund_pool_uid = ufas.df_fund_pool_uid, 
                    is_rh_participating_flag = ufas.is_rh_participating_flag, 
                    reported_value = ufas.reported_value
                from ${dbAppsSchema}.df_usage_fas ufas
                where u.df_usage_archive_uid = ufas.df_usage_fas_uid
                and product_family = 'FAS'"""
            )

            sql("""update ${dbAppsSchema}.df_usage_archive u
                set 
                    article = ufas.article, 
                    author = ufas.author, 
                    publisher = ufas.publisher, 
                    publication_date = ufas.publication_date, 
                    market = ufas.market, 
                    market_period_from = ufas.market_period_from, 
                    market_period_to = ufas.market_period_to, 
                    df_fund_pool_uid = ufas.df_fund_pool_uid, 
                    is_rh_participating_flag = ufas.is_rh_participating_flag, 
                    reported_value = ufas.reported_value
                from ${dbAppsSchema}.df_usage_fas ufas
                where u.df_usage_archive_uid = ufas.df_usage_fas_uid
                and product_family = 'FAS2'"""
            )

            sql("""update ${dbAppsSchema}.df_usage_archive u
                set 
                    article = ufas.article, 
                    author = ufas.author, 
                    publisher = ufas.publisher, 
                    publication_date = ufas.publication_date, 
                    market = ufas.market, 
                    market_period_from = ufas.market_period_from, 
                    market_period_to = ufas.market_period_to, 
                    df_fund_pool_uid = ufas.df_fund_pool_uid, 
                    is_rh_participating_flag = ufas.is_rh_participating_flag, 
                    reported_value = ufas.reported_value
                from ${dbAppsSchema}.df_usage_fas ufas
                where u.df_usage_archive_uid = ufas.df_usage_fas_uid
                and product_family = 'NTS'"""
            )
        }
    }

    changeSet(id: '2019-12-26-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-55412 FDA: Load AACL Usage Data: create table for storing AACL specific usage data")

        createTable(tableName: 'df_usage_aacl', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing specific fields of usages with AACL product family') {

            column(name: 'df_usage_aacl_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage')
            column(name: 'institution', type: 'VARCHAR(255)', remarks: 'The institution of usage')
            column(name: 'usage_period', type: 'NUMERIC(6,0)', remarks: 'The usage period')
            column(name: 'usage_source', type: 'VARCHAR(150)', remarks: 'The usage source')
            column(name: 'number_of_pages', type: 'INTEGER', remarks: 'The number of pages')
            column(name: 'right_limitation', type: 'VARCHAR(20)', remarks: 'The rights limitation')
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1', remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM', remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created') {
                constraints(nullable: false)
            }
            column(name: 'updated_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM', remarks: 'The user name who updated this record; when a record is first created, this will be the same as the created_by_user') {
                constraints(nullable: false)
            }
            column(name: 'updated_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created; when a record is first created, this will be the same as the created_datetime') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_usage_aacl', tablespace: dbIndexTablespace,
                columnNames: 'df_usage_aacl_uid', constraintName: 'df_usage_aacl_pk')

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2019-12-27-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-55412 FDA: Load AACL Usage Data: drop not null constrains from rro_account_number and fiscal_year " +
                "columns in df_usage_batch table")

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch', columnName: 'fiscal_year')
        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch', columnName: 'rro_account_number')

        rollback ""
    }

    changeSet(id: '2020-01-09-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('B-55412 FDA: Load AACL Usage Data: rename primary keys based on database code standards')

        dropPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_usage_aacl', constraintName: 'df_usage_aacl_pk')
        dropPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_usage_fas', constraintName: 'df_usage_fas_pk')

        addPrimaryKey(tablespace: dbIndexTablespace, schemaName: dbAppsSchema, tableName: 'df_usage_aacl',
                columnNames: 'df_usage_aacl_uid', constraintName: 'pk_df_usage_aacl')
        addPrimaryKey(tablespace: dbIndexTablespace, schemaName: dbAppsSchema, tableName: 'df_usage_fas',
                columnNames: 'df_usage_fas_uid', constraintName: 'pk_df_usage_fas')

        rollback {
            dropPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_usage_aacl', constraintName: 'pk_df_usage_aacl')
            dropPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_usage_fas', constraintName: 'pk_df_usage_fas')

            addPrimaryKey(tablespace: dbIndexTablespace, schemaName: dbAppsSchema, tableName: 'df_usage_aacl',
                    columnNames: 'df_usage_aacl_uid', constraintName: 'df_usage_aacl_pk')
            addPrimaryKey(tablespace: dbIndexTablespace, schemaName: dbAppsSchema, tableName: 'df_usage_fas',
                    columnNames: 'df_usage_fas_uid', constraintName: 'df_usage_fas_pk')
        }
    }

    changeSet(id: '2020-01-15-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('B-55538 FDA: Create Licensee Class Mapping: create df_aggregate_licensee_class table')

        createTable(tableName: 'df_aggregate_licensee_class', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing aggregate licensee classes for AACL product family') {

            column(name: 'aggregate_licensee_class_id', type: 'NUMERIC(38)', remarks: 'Aggregate Licensee Class Id')
            column(name: 'aggregate_licensee_class_name', type: 'VARCHAR(255)', remarks: 'Aggregate Licensee Class Name')
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1', remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM', remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created') {
                constraints(nullable: false)
            }
            column(name: 'updated_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM', remarks: 'The user name who updated this record; when a record is first created, this will be the same as the created_by_user') {
                constraints(nullable: false)
            }
            column(name: 'updated_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created; when a record is first created, this will be the same as the created_datetime') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(tablespace: dbIndexTablespace, schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class',
                columnNames: 'aggregate_licensee_class_id', constraintName: 'pk_df_aggregate_licensee_class')

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 108)
            column(name: 'aggregate_licensee_class_name', value: 'EXGP - Life Sciences')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 110)
            column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Life Sciences')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 111)
            column(name: 'aggregate_licensee_class_name', value: 'HGP - Life Sciences')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 113)
            column(name: 'aggregate_licensee_class_name', value: 'MU - Life Sciences')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 115)
            column(name: 'aggregate_licensee_class_name', value: 'EXGP - Business Management')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 117)
            column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Business Management')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 118)
            column(name: 'aggregate_licensee_class_name', value: 'HGP - Business Management')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 120)
            column(name: 'aggregate_licensee_class_name', value: 'MU - Business Management')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 136)
            column(name: 'aggregate_licensee_class_name', value: 'EXGP - Education')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 138)
            column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Education')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 139)
            column(name: 'aggregate_licensee_class_name', value: 'HGP - Education')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 141)
            column(name: 'aggregate_licensee_class_name', value: 'MU - Education')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 143)
            column(name: 'aggregate_licensee_class_name', value: 'EXGP - Engineering')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 145)
            column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Engineering')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 146)
            column(name: 'aggregate_licensee_class_name', value: 'HGP - Engineering')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 148)
            column(name: 'aggregate_licensee_class_name', value: 'MU - Engineering')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 164)
            column(name: 'aggregate_licensee_class_name', value: 'EXGP - Law & Legal Studies')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 166)
            column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Law & Legal Studies')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 167)
            column(name: 'aggregate_licensee_class_name', value: 'HGP - Law & Legal Studies')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 169)
            column(name: 'aggregate_licensee_class_name', value: 'MU - Law & Legal Studies')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 171)
            column(name: 'aggregate_licensee_class_name', value: 'EXGP - Arts & Humanities')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 173)
            column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Arts & Humanities')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 174)
            column(name: 'aggregate_licensee_class_name', value: 'HGP - Arts & Humanities')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 176)
            column(name: 'aggregate_licensee_class_name', value: 'MU - Arts & Humanities')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 192)
            column(name: 'aggregate_licensee_class_name', value: 'EXGP - Medical & Health')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 194)
            column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Medical & Health')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 195)
            column(name: 'aggregate_licensee_class_name', value: 'HGP - Medical & Health')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 197)
            column(name: 'aggregate_licensee_class_name', value: 'MU - Medical & Health')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 206)
            column(name: 'aggregate_licensee_class_name', value: 'EXGP - Physical Sciences & Mathematics')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 208)
            column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Physical Sciences & Mathematics')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 209)
            column(name: 'aggregate_licensee_class_name', value: 'HGP - Physical Sciences & Mathematics')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 211)
            column(name: 'aggregate_licensee_class_name', value: 'MU - Physical Sciences & Mathematics')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 227)
            column(name: 'aggregate_licensee_class_name', value: 'EXGP - Social & Behavioral Sciences')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 229)
            column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Social & Behavioral Sciences')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 230)
            column(name: 'aggregate_licensee_class_name', value: 'HGP - Social & Behavioral Sciences')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 232)
            column(name: 'aggregate_licensee_class_name', value: 'MU - Social & Behavioral Sciences')
        }

        rollback {
            dropTable(tableName: 'df_aggregate_licensee_class', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2020-01-15-01', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('B-55538 FDA: Create Licensee Class Mapping: create df_detail_licensee_class table')

        createTable(tableName: 'df_detail_licensee_class', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing detail licensee classes for AACL product family') {

            column(name: 'detail_licensee_class_id', type: 'NUMERIC(38)', remarks: 'Detail Licensee Class Id')
            column(name: 'enrollment_profile', type: 'VARCHAR(20)', remarks: 'Enrollment Profile')
            column(name: 'discipline', type: 'VARCHAR(100)', remarks: 'Course discipline')
            column(name: 'aggregate_licensee_class_id', type: 'NUMERIC(38)', remarks: 'Aggregate Licensee Class Id')
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1', remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM', remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created') {
                constraints(nullable: false)
            }
            column(name: 'updated_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM', remarks: 'The user name who updated this record; when a record is first created, this will be the same as the created_by_user') {
                constraints(nullable: false)
            }
            column(name: 'updated_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created; when a record is first created, this will be the same as the created_datetime') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(tablespace: dbIndexTablespace, schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class',
                columnNames: 'detail_licensee_class_id', constraintName: 'pk_df_detail_licensee_class')

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'enrollment_profile', value: 'EXGP')
            column(name: 'discipline', value: 'Life Sciences')
            column(name: 'aggregate_licensee_class_id', value: 108)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'enrollment_profile', value: 'EXU4')
            column(name: 'discipline', value: 'Life Sciences')
            column(name: 'aggregate_licensee_class_id', value: 110)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 111)
            column(name: 'enrollment_profile', value: 'HGP')
            column(name: 'discipline', value: 'Life Sciences')
            column(name: 'aggregate_licensee_class_id', value: 111)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'enrollment_profile', value: 'MU')
            column(name: 'discipline', value: 'Life Sciences')
            column(name: 'aggregate_licensee_class_id', value: 113)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 115)
            column(name: 'enrollment_profile', value: 'EXGP')
            column(name: 'discipline', value: 'Business Management')
            column(name: 'aggregate_licensee_class_id', value: 115)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 117)
            column(name: 'enrollment_profile', value: 'EXU4')
            column(name: 'discipline', value: 'Business Management')
            column(name: 'aggregate_licensee_class_id', value: 117)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 118)
            column(name: 'enrollment_profile', value: 'HGP')
            column(name: 'discipline', value: 'Business Management')
            column(name: 'aggregate_licensee_class_id', value: 118)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 120)
            column(name: 'enrollment_profile', value: 'MU')
            column(name: 'discipline', value: 'Business Management')
            column(name: 'aggregate_licensee_class_id', value: 120)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 136)
            column(name: 'enrollment_profile', value: 'EXGP')
            column(name: 'discipline', value: 'Education')
            column(name: 'aggregate_licensee_class_id', value: 136)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 138)
            column(name: 'enrollment_profile', value: 'EXU4')
            column(name: 'discipline', value: 'Education')
            column(name: 'aggregate_licensee_class_id', value: 138)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 139)
            column(name: 'enrollment_profile', value: 'HGP')
            column(name: 'discipline', value: 'Education')
            column(name: 'aggregate_licensee_class_id', value: 139)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 141)
            column(name: 'enrollment_profile', value: 'MU')
            column(name: 'discipline', value: 'Education')
            column(name: 'aggregate_licensee_class_id', value: 141)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'enrollment_profile', value: 'EXGP')
            column(name: 'discipline', value: 'Engineering')
            column(name: 'aggregate_licensee_class_id', value: 143)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 145)
            column(name: 'enrollment_profile', value: 'EXU4')
            column(name: 'discipline', value: 'Engineering')
            column(name: 'aggregate_licensee_class_id', value: 145)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 146)
            column(name: 'enrollment_profile', value: 'HGP')
            column(name: 'discipline', value: 'Engineering')
            column(name: 'aggregate_licensee_class_id', value: 146)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 148)
            column(name: 'enrollment_profile', value: 'MU')
            column(name: 'discipline', value: 'Engineering')
            column(name: 'aggregate_licensee_class_id', value: 148)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 164)
            column(name: 'enrollment_profile', value: 'EXGP')
            column(name: 'discipline', value: 'Law & Legal Studies')
            column(name: 'aggregate_licensee_class_id', value: 164)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 166)
            column(name: 'enrollment_profile', value: 'EXU4')
            column(name: 'discipline', value: 'Law & Legal Studies')
            column(name: 'aggregate_licensee_class_id', value: 166)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 167)
            column(name: 'enrollment_profile', value: 'HGP')
            column(name: 'discipline', value: 'Law & Legal Studies')
            column(name: 'aggregate_licensee_class_id', value: 167)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 169)
            column(name: 'enrollment_profile', value: 'MU')
            column(name: 'discipline', value: 'Law & Legal Studies')
            column(name: 'aggregate_licensee_class_id', value: 169)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 171)
            column(name: 'enrollment_profile', value: 'EXGP')
            column(name: 'discipline', value: 'Arts & Humanities')
            column(name: 'aggregate_licensee_class_id', value: 171)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 173)
            column(name: 'enrollment_profile', value: 'EXU4')
            column(name: 'discipline', value: 'Arts & Humanities')
            column(name: 'aggregate_licensee_class_id', value: 173)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 174)
            column(name: 'enrollment_profile', value: 'HGP')
            column(name: 'discipline', value: 'Arts & Humanities')
            column(name: 'aggregate_licensee_class_id', value: 174)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 176)
            column(name: 'enrollment_profile', value: 'MU')
            column(name: 'discipline', value: 'Arts & Humanities')
            column(name: 'aggregate_licensee_class_id', value: 176)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 192)
            column(name: 'enrollment_profile', value: 'EXGP')
            column(name: 'discipline', value: 'Medical & Health')
            column(name: 'aggregate_licensee_class_id', value: 192)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 194)
            column(name: 'enrollment_profile', value: 'EXU4')
            column(name: 'discipline', value: 'Medical & Health')
            column(name: 'aggregate_licensee_class_id', value: 194)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 195)
            column(name: 'enrollment_profile', value: 'HGP')
            column(name: 'discipline', value: 'Medical & Health')
            column(name: 'aggregate_licensee_class_id', value: 195)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 197)
            column(name: 'enrollment_profile', value: 'MU')
            column(name: 'discipline', value: 'Medical & Health')
            column(name: 'aggregate_licensee_class_id', value: 197)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 206)
            column(name: 'enrollment_profile', value: 'EXGP')
            column(name: 'discipline', value: 'Physical Sciences & Mathematics')
            column(name: 'aggregate_licensee_class_id', value: 206)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 208)
            column(name: 'enrollment_profile', value: 'EXU4')
            column(name: 'discipline', value: 'Physical Sciences & Mathematics')
            column(name: 'aggregate_licensee_class_id', value: 208)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 209)
            column(name: 'enrollment_profile', value: 'HGP')
            column(name: 'discipline', value: 'Physical Sciences & Mathematics')
            column(name: 'aggregate_licensee_class_id', value: 209)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 211)
            column(name: 'enrollment_profile', value: 'MU')
            column(name: 'discipline', value: 'Physical Sciences & Mathematics')
            column(name: 'aggregate_licensee_class_id', value: 211)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 227)
            column(name: 'enrollment_profile', value: 'EXGP')
            column(name: 'discipline', value: 'Social & Behavioral Sciences')
            column(name: 'aggregate_licensee_class_id', value: 227)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 229)
            column(name: 'enrollment_profile', value: 'EXU4')
            column(name: 'discipline', value: 'Social & Behavioral Sciences')
            column(name: 'aggregate_licensee_class_id', value: 229)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 230)
            column(name: 'enrollment_profile', value: 'HGP')
            column(name: 'discipline', value: 'Social & Behavioral Sciences')
            column(name: 'aggregate_licensee_class_id', value: 230)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 232)
            column(name: 'enrollment_profile', value: 'MU')
            column(name: 'discipline', value: 'Social & Behavioral Sciences')
            column(name: 'aggregate_licensee_class_id', value: 232)
        }

        addForeignKeyConstraint(constraintName: 'fk_df_detail_licensee_class_2_df_aggregate_licensee_class',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_detail_licensee_class',
                baseColumnNames: 'aggregate_licensee_class_id',
                referencedTableName: 'df_aggregate_licensee_class',
                referencedColumnNames: 'aggregate_licensee_class_id')

        rollback {
            dropTable(tableName: 'df_detail_licensee_class', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2020-01-20-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('B-55751 FDA: Load Pub Types: create df_publication_type table')

        createTable(tableName: 'df_publication_type', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing publication types for AACL product family') {

            column(name: 'df_publication_type_uid', type: 'VARCHAR(255)', remarks: 'Publication Type Identifier')
            column(name: 'name', type: 'VARCHAR(255)', remarks: 'Publication Type Name')
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1', remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM', remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created') {
                constraints(nullable: false)
            }
            column(name: 'updated_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM', remarks: 'The user name who updated this record; when a record is first created, this will be the same as the created_by_user') {
                constraints(nullable: false)
            }
            column(name: 'updated_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created; when a record is first created, this will be the same as the created_datetime') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(tablespace: dbIndexTablespace, schemaName: dbAppsSchema, tableName: 'df_publication_type',
                columnNames: 'df_publication_type_uid', constraintName: 'pk_df_publication_type')

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'name', value: 'Book')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'name', value: 'Business or Trade Journal')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'name', value: 'Consumer Magazine')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: 'a3dff475-fc06-4d8c-b7cc-f093073ada6f')
            column(name: 'name', value: 'News Source')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: '357ea293-8f98-4160-820e-8369f6180654')
            column(name: 'name', value: 'Other')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'name', value: 'STMA Journal')
        }

        rollback {
            dropTable(tableName: 'df_publication_type', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2020-01-22-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('B-55751 FDA: Load Pub Types: create NOT NULL constraint for name column in df_publication_type table')

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_publication_type',
                columnName: 'name', columnDataType: 'VARCHAR(255)')

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2020-01-27-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('B-52329 FDA: Load classified AACL usages: add publication_type_uid and detail_licensee_class_id columns in df_usage_aacl table')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'publication_type_uid', type: 'VARCHAR(255)', remarks: 'Publication Type Identifier')
            column(name: 'detail_licensee_class_id', type: 'INTEGER', remarks: 'Detail Licensee Class Id')
        }

        addForeignKeyConstraint(constraintName: 'fk_df_usage_aacl_2_df_publication_type',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_usage_aacl',
                baseColumnNames: 'publication_type_uid',
                referencedTableName: 'df_publication_type',
                referencedColumnNames: 'df_publication_type_uid')

        addForeignKeyConstraint(constraintName: 'fk_df_usage_aacl_2_df_detail_licensee_class',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_usage_aacl',
                baseColumnNames: 'detail_licensee_class_id',
                referencedTableName: 'df_detail_licensee_class',
                referencedColumnNames: 'detail_licensee_class_id')

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2020-01-28-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("CDP-880 FDA: Research Status Report for FAS/FAS2: AACL batches are displayed in the report")

        createView(viewName: 'v_research_status_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
            """select
                    nestedReport.batch_name, 
                    nestedReport.rro_account_number,
                    rh.name rro_name,
                    nestedReport.payment_date, 
                    nestedReport.gross_amount,
                    sum(work_not_found_count) work_not_found_details_count,
                    sum(nestedReport.work_not_found) work_not_found_gross_amount,
                    sum(researched_count) work_research_details_count,
                    sum(nestedReport.researched) work_research_gross_amount,
                    sum(sent_for_ra_count) send_for_ra_details_count,
                    sum(nestedReport.sent_for_ra) send_for_ra_gross_amount,
                    sum(rh_not_found_count) rh_not_found_details_count,
                    sum(nestedReport.rh_not_found) rh_not_found_gross_amount
                from(
                    select
                        b.name batch_name, 
                        b.rro_account_number,
                        b.payment_date, 
                        b.gross_amount,     
                        case when (u.status_ind = 'WORK_NOT_FOUND') then count(1) else 0 end work_not_found_count,
                        case when (u.status_ind = 'WORK_NOT_FOUND') then sum(u.gross_amount) else 0 end work_not_found,
                        case when (u.status_ind = 'WORK_RESEARCH') then count(1) else 0 end researched_count,
                        case when (u.status_ind = 'WORK_RESEARCH') then sum(u.gross_amount) else 0 end researched,
                        case when (u.status_ind = 'SENT_FOR_RA') then count(1) else 0 end sent_for_ra_count,
                        case when (u.status_ind = 'SENT_FOR_RA') then sum(u.gross_amount) else 0 end sent_for_ra,
                        case when (u.status_ind = 'RH_NOT_FOUND') then count(1) else 0 end rh_not_found_count,
                        case when (u.status_ind = 'RH_NOT_FOUND') then sum(u.gross_amount) else 0 end rh_not_found     
                    from ${dbAppsSchema}.df_usage u
                    left join ${dbAppsSchema}.df_usage_batch b on u.df_usage_batch_uid = b.df_usage_batch_uid 
                    where status_ind in ('WORK_NOT_FOUND', 'WORK_RESEARCH', 'SENT_FOR_RA','RH_NOT_FOUND')
                        and u.product_family in ('FAS', 'FAS2')
                    group by b.name, rro_account_number, payment_date, b.gross_amount, u.status_ind) nestedReport
                left join ${dbAppsSchema}.df_rightsholder rh on nestedReport.rro_account_number = rh.rh_account_number  
                group by nestedReport.batch_name, nestedReport.rro_account_number, rh.name, nestedReport.payment_date, nestedReport.gross_amount
                order by nestedReport.payment_date, nestedReport.batch_name"""
        }

        rollback {
            createView(viewName:'v_research_status_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
                """select
                    nestedReport.batch_name, 
                    nestedReport.rro_account_number,
                    nestedReport.rro_name,
                    nestedReport.payment_date, 
                    nestedReport.gross_amount,
                    sum(work_not_found_count) work_not_found_details_count,
                    sum(nestedReport.work_not_found) work_not_found_gross_amount,
                    sum(researched_count) work_research_details_count,
                    sum(nestedReport.researched) work_research_gross_amount,
                    sum(sent_for_ra_count) send_for_ra_details_count,
                    sum(nestedReport.sent_for_ra) send_for_ra_gross_amount,
                    sum(rh_not_found_count) rh_not_found_details_count,
                    sum(nestedReport.rh_not_found) rh_not_found_gross_amount
                    from(
                        select
                            b.name batch_name, 
                            b.rro_account_number,
                            rh.name rro_name,
                            b.payment_date, 
                            b.gross_amount,     
                            case when (u.status_ind = 'WORK_NOT_FOUND') then count(1) else 0 end work_not_found_count,
                            case when (u.status_ind = 'WORK_NOT_FOUND') then sum(u.gross_amount) else 0 end work_not_found,
                            case when (u.status_ind = 'WORK_RESEARCH') then count(1) else 0 end researched_count,
                            case when (u.status_ind = 'WORK_RESEARCH') then sum(u.gross_amount) else 0 end researched,
                            case when (u.status_ind = 'SENT_FOR_RA') then count(1) else 0 end sent_for_ra_count,
                            case when (u.status_ind = 'SENT_FOR_RA') then sum(u.gross_amount) else 0 end sent_for_ra,
                            case when (u.status_ind = 'RH_NOT_FOUND') then count(1) else 0 end rh_not_found_count,
                            case when (u.status_ind = 'RH_NOT_FOUND') then sum(u.gross_amount) else 0 end rh_not_found     
                        from ${dbAppsSchema}.df_usage u
                        left join ${dbAppsSchema}.df_usage_batch b on u.df_usage_batch_uid = b.df_usage_batch_uid 
                        left join ${dbAppsSchema}.df_rightsholder rh on b.rro_account_number = rh.rh_account_number  
                        where status_ind in ('WORK_NOT_FOUND', 'WORK_RESEARCH', 'SENT_FOR_RA','RH_NOT_FOUND')
                        group by b.name, rro_account_number, rh.name, payment_date, b.gross_amount, u.status_ind) nestedReport
                group by nestedReport.batch_name, nestedReport.rro_account_number, nestedReport.rro_name, nestedReport.payment_date, nestedReport.gross_amount
                order by nestedReport.payment_date desc, nestedReport.batch_name"""
            }
        }
    }
}
