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
}
