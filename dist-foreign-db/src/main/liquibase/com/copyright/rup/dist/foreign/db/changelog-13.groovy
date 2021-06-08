databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-02-10-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-65718 FDA: Batch Processing View: add initial_usages_count column to df_usage_batch table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'initial_usages_count', type: 'INTEGER', remarks: 'The initial usages count for batch')
        }

        sql("""update ${dbAppsSchema}.df_usage_batch b
                set 
                    initial_usages_count = (select count(1) from apps.df_usage where df_usage_batch_uid = b.df_usage_batch_uid)
                                         + (select count(1) from apps.df_usage_archive where df_usage_batch_uid = b.df_usage_batch_uid)
                where product_family = 'FAS'"""
        )

        sql("""update ${dbAppsSchema}.df_usage_batch b
                set 
                    initial_usages_count = (select count(1) from apps.df_usage where df_usage_batch_uid = b.df_usage_batch_uid)
                                         + (select count(1) from apps.df_usage_archive where df_usage_batch_uid = b.df_usage_batch_uid)
                where product_family = 'FAS2'"""
        )

        sql("""update ${dbAppsSchema}.df_usage_batch b
                set 
                    initial_usages_count = (select count(1) from apps.df_usage where df_usage_batch_uid = b.df_usage_batch_uid)
                                         + (select count(1) from apps.df_usage_archive where df_usage_batch_uid = b.df_usage_batch_uid)
                where product_family = 'NTS'"""
        )

        sql("""update ${dbAppsSchema}.df_usage_batch b
                set 
                    initial_usages_count = (select count(1) from apps.df_usage where df_usage_batch_uid = b.df_usage_batch_uid)
                                         + (select count(1) from apps.df_usage_archive where df_usage_batch_uid = b.df_usage_batch_uid)
                where product_family = 'AACL'"""
        )

        sql("""update ${dbAppsSchema}.df_usage_batch b
                set 
                    initial_usages_count = (select count(1) from apps.df_usage where df_usage_batch_uid = b.df_usage_batch_uid)
                                         + (select count(1) from apps.df_usage_archive where df_usage_batch_uid = b.df_usage_batch_uid)
                where product_family = 'SAL'"""
        )

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch', columnName: 'initial_usages_count')
        }
    }

    changeSet(id: '2021-04-28-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-66740 FDA: DB changes for UDM: create df_udm_usage_batch, df_udm_usage tables")

        createTable(tableName: 'df_udm_usage_batch', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing UDM usage batches') {

            column(name: 'df_udm_usage_batch_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage batch') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(255)', remarks: 'The name of usage batch') {
                constraints(nullable: false)
            }
            column(name: 'period', type: 'NUMERIC(6)', remarks: 'The UDM period in YYYYMM format') {
                constraints(nullable: false)
            }
            column(name: 'usage_origin', type: 'VARCHAR(20)', remarks: 'The UDM usage origin') {
                constraints(nullable: false)
            }
            column(name: 'channel', type: 'VARCHAR(15)', remarks: 'The UDM channel') {
                constraints(nullable: false)
            }
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1',
                    remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM',
                    remarks: 'The user name who created this record') {
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

        addPrimaryKey(schemaName: dbAppsSchema, tablespace: dbIndexTablespace,
                tableName: 'df_udm_usage_batch',
                columnNames: 'df_udm_usage_batch_uid',
                constraintName: 'pk_df_udm_usage_batch_uid')

        addUniqueConstraint(schemaName: dbAppsSchema,
                tableName: 'df_udm_usage_batch',
                columnNames: 'name',
                constraintName: 'uk_df_udm_usage_batch_name')

        createTable(tableName: 'df_udm_usage', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing UDM usages') {

            column(name: 'df_udm_usage_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage') {
                constraints(nullable: false)
            }
            column(name: 'df_udm_usage_batch_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage batch') {
                constraints(nullable: false)
            }
            column(name: 'original_detail_id', type: 'VARCHAR(50)', remarks: 'The original detail id') {
                constraints(nullable: false)
            }
            column(name: 'period_end_date', type: 'TIMESTAMPTZ', remarks: 'The period end date') {
                constraints(nullable: false)
            }
            column(name: 'status_ind', type: 'VARCHAR(32)', remarks: 'The status of usage') {
                constraints(nullable: false)
            }
            column(name: 'wr_wrk_inst', type: 'NUMERIC(15)', remarks: 'The Wr Wrk Inst')
            column(name: 'rh_account_number', type: 'NUMERIC(22)', remarks: 'The rightsholder account number')
            column(name: 'system_title', type: 'VARCHAR(2000)', remarks: 'The system title')
            column(name: 'reported_title', type: 'VARCHAR(1000)', remarks: 'The reported title')
            column(name: 'standard_number', type: 'VARCHAR(1000)', remarks: 'The standard number')
            column(name: 'reported_standard_number', type: 'VARCHAR(100)', remarks: 'The reported standard number')
            column(name: 'reported_pub_type', type: 'VARCHAR(100)', remarks: 'The reported publication type')
            column(name: 'publication_format', type: 'VARCHAR(20)', remarks: 'The publication format')
            column(name: 'article', type: 'VARCHAR(500)', remarks: 'The article')
            column(name: 'language', type: 'VARCHAR(255)', remarks: 'The language')
            column(name: 'detail_licensee_class_id', type: 'INTEGER', remarks: 'The identifier of Detail Licensee Class')
            column(name: 'company_id', type: 'NUMERIC(10)', remarks: 'The company id') {
                constraints(nullable: false)
            }
            column(name: 'company_name', type: 'VARCHAR(200)', remarks: 'The company name')
            column(name: 'survey_respondent', type: 'VARCHAR(200)', remarks: 'The survey respondent')
            column(name: 'ip_address', type: 'VARCHAR(20)', remarks: 'The IP address')
            column(name: 'survey_country', type: 'VARCHAR(100)', remarks: 'The survey country') {
                constraints(nullable: false)
            }
            column(name: 'usage_date', type: 'TIMESTAMPTZ', remarks: 'The usage date') {
                constraints(nullable: false)
            }
            column(name: 'survey_start_date', type: 'TIMESTAMPTZ', remarks: 'The survey start date') {
                constraints(nullable: false)
            }
            column(name: 'survey_end_date', type: 'TIMESTAMPTZ', remarks: 'The survey end date') {
                constraints(nullable: false)
            }
            column(name: 'annual_multiplier', type: 'NUMERIC(3)', remarks: 'The annual multiplier')
            column(name: 'statistical_multiplier', type: 'NUMERIC(4,2)', remarks: 'The statistical multiplier used to weight library data with a value less than 1 to normalize it with survey data')
            column(name: 'type_of_use', type: 'VARCHAR(128)', remarks: 'The type of use')
            column(name: 'reported_type_of_use', type: 'VARCHAR(128)', remarks: 'The reported type of use')
            column(name: 'quantity', type: 'NUMERIC(38)', remarks: 'The quantity') {
                constraints(nullable: false)
            }
            column(name: 'annualized_copies', type: 'NUMERIC(38)', remarks: 'The number of annualized copies')
            column(name: 'ineligible_reason', type: 'VARCHAR(100)', remarks: 'The ineligible reason')
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1',
                    remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM',
                    remarks: 'The user name who created this record') {
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

        addPrimaryKey(schemaName: dbAppsSchema,
                tablespace: dbIndexTablespace,
                tableName: 'df_udm_usage',
                columnNames: 'df_udm_usage_uid',
                constraintName: 'pk_df_udm_usage_uid')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_udm_usage',
                baseColumnNames: 'df_udm_usage_batch_uid',
                referencedTableName: 'df_udm_usage_batch',
                referencedColumnNames: 'df_udm_usage_batch_uid',
                constraintName: 'fk_df_udm_usage_2_df_udm_usage_batch')

        addUniqueConstraint(schemaName: dbAppsSchema,
                tableName: 'df_udm_usage',
                columnNames: 'original_detail_id',
                constraintName: 'uk_df_udm_usage_original_detail_id')

        rollback {
            dropTable(tableName: 'df_udm_usage', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_udm_usage_batch', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2021-05-04-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-66740 FDA: DB changes for UDM: update statistical_multiplier, annualized_copies fields")

        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'statistical_multiplier', newDataType: 'DECIMAL(6,5)')
        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'annualized_copies', newDataType: 'NUMERIC(38,5)')

        rollback {
            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'statistical_multiplier', newDataType: 'NUMERIC(4,2)')
            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'annualized_copies', newDataType: 'NUMERIC(38)')
        }
    }

    changeSet(id: '2021-05-06-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-66740 FDA: DB changes for UDM: add assignee column to df_udm_usage table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'assignee', type: 'VARCHAR(320)', remarks: 'The assignee')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'assignee')
        }
    }

    changeSet(id: '2021-05-12-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("CDP-981 FDA: Upgrade to Gradle 4 / RUP Gradle Plugins 6: modify BLOB data type for quartz tables to BYTEA")

        modifyDataType(tableName: 'df_qrtz_job_details', schemaName: dbAppsSchema, columnName: 'job_data', newDataType: 'text')
        modifyDataType(tableName: 'df_qrtz_job_details', schemaName: dbAppsSchema, columnName: 'job_data', newDataType: 'bytea')
        modifyDataType(tableName: 'df_qrtz_triggers', schemaName: dbAppsSchema, columnName: 'job_data', newDataType: 'text')
        modifyDataType(tableName: 'df_qrtz_triggers', schemaName: dbAppsSchema, columnName: 'job_data', newDataType: 'bytea')
        modifyDataType(tableName: 'df_qrtz_blob_triggers', schemaName: dbAppsSchema, columnName: 'blob_data', newDataType: 'text')
        modifyDataType(tableName: 'df_qrtz_blob_triggers', schemaName: dbAppsSchema, columnName: 'blob_data', newDataType: 'bytea')
        modifyDataType(tableName: 'df_qrtz_calendars', schemaName: dbAppsSchema, columnName: 'calendar', newDataType: 'text')
        modifyDataType(tableName: 'df_qrtz_calendars', schemaName: dbAppsSchema, columnName: 'calendar', newDataType: 'bytea')

        rollback ""
    }

    changeSet(id: '2021-05-19-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment("B-67378 FDA & UDM: Detail/Aggregate licensee mapping: add description column into df_detail_licensee_class table, " +
                "add product_family and description columns into df_aggregate_licensee_class table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'description', type: 'VARCHAR(256)', remarks: 'The description')
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'description', type: 'VARCHAR(256)', remarks: 'The description')
            column(name: 'product_family', type: 'VARCHAR(128)', remarks: 'The product family')
        }

        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'product_family', value: 'AACL')
            where "product_family is null"
        }

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class', columnName: 'product_family',
                columnDataType: 'VARCHAR(128)')

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class', columnName: 'description')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class', columnName: 'description')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class', columnName: 'product_family')
        }
    }

    changeSet(id: '2021-05-19-01', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment("B-67378 FDA & UDM: Detail/Aggregate licensee mapping: add ACL aggregate licensee classes into df_aggregate_licensee_class table")

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'description', value: 'Food and Tobacco')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 12)
            column(name: 'description', value: 'Machinery')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 13)
            column(name: 'description', value: 'Computers')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 18)
            column(name: 'description', value: 'Aircraft and Aerospace')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 23)
            column(name: 'description', value: 'Electric and Gas Utilities')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 25)
            column(name: 'description', value: 'Business Services')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 26)
            column(name: 'description', value: 'Law Firms')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 28)
            column(name: 'description', value: 'Wholesale & Retail Trade')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 29)
            column(name: 'description', value: 'Consumer Services')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 39)
            column(name: 'description', value: 'Government')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'description', value: 'Materials')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 52)
            column(name: 'description', value: 'Medical')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 53)
            column(name: 'description', value: 'Metals')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 54)
            column(name: 'description', value: 'Electronics')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 55)
            column(name: 'description', value: 'Transportation')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 56)
            column(name: 'description', value: 'Financial')
            column(name: 'product_family', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'aggregate_licensee_class_id', value: 57)
            column(name: 'description', value: 'Communications')
            column(name: 'product_family', value: 'ACL')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                where "product_family = 'ACL'"
            }
        }
    }

    changeSet(id: '2021-05-19-02', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment("B-67378 FDA & UDM: Detail/Aggregate licensee mapping: add ACL detail licensee classes into df_detail_licensee_class table")

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'description', value: 'Food and Tobacco')
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'description', value: 'Textiles, Apparel, etc.')
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'description', value: 'Lumber, Paper, etc.')
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 4)
            column(name: 'description', value: 'Publishing')
            column(name: 'aggregate_licensee_class_id', value: 57)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 5)
            column(name: 'description', value: 'Chemicals')
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 6)
            column(name: 'description', value: 'Health Care & Pharm')
            column(name: 'aggregate_licensee_class_id', value: 52)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 7)
            column(name: 'description', value: 'Fuels')
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 8)
            column(name: 'description', value: 'Rubber Products')
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 9)
            column(name: 'description', value: 'Stone, Clay and Glass')
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 10)
            column(name: 'description', value: 'Primary Metals')
            column(name: 'aggregate_licensee_class_id', value: 53)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 11)
            column(name: 'description', value: 'Fabricated Metal Products')
            column(name: 'aggregate_licensee_class_id', value: 53)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 12)
            column(name: 'description', value: 'Machinery')
            column(name: 'aggregate_licensee_class_id', value: 12)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 13)
            column(name: 'description', value: 'Computers')
            column(name: 'aggregate_licensee_class_id', value: 13)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 14)
            column(name: 'description', value: 'Radio, TV, Commun Equip')
            column(name: 'aggregate_licensee_class_id', value: 54)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 15)
            column(name: 'description', value: 'Electronic Components')
            column(name: 'aggregate_licensee_class_id', value: 54)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 16)
            column(name: 'description', value: 'Electric/Electronic Equip')
            column(name: 'aggregate_licensee_class_id', value: 54)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 17)
            column(name: 'description', value: 'Transportation Equipment')
            column(name: 'aggregate_licensee_class_id', value: 55)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 18)
            column(name: 'description', value: 'Aircraft and Aerospace')
            column(name: 'aggregate_licensee_class_id', value: 18)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 19)
            column(name: 'description', value: 'Scientific Instruments')
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 20)
            column(name: 'description', value: 'Misc. Manufacturing NEC')
            column(name: 'aggregate_licensee_class_id', value: 54)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 21)
            column(name: 'description', value: 'Telecommunications Services')
            column(name: 'aggregate_licensee_class_id', value: 57)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'description', value: 'Banks/Ins/RE/Holding Cos')
            column(name: 'aggregate_licensee_class_id', value: 56)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 23)
            column(name: 'description', value: 'Electric and Gas Utilities')
            column(name: 'aggregate_licensee_class_id', value: 23)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 24)
            column(name: 'description', value: 'Doctors Offices/Genl Hosp')
            column(name: 'aggregate_licensee_class_id', value: 52)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 25)
            column(name: 'description', value: 'Business Services')
            column(name: 'aggregate_licensee_class_id', value: 25)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 26)
            column(name: 'description', value: 'Law Firms')
            column(name: 'aggregate_licensee_class_id', value: 26)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 27)
            column(name: 'description', value: 'Transportation Services')
            column(name: 'aggregate_licensee_class_id', value: 55)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 28)
            column(name: 'description', value: 'Wholesale & Retail Trade')
            column(name: 'aggregate_licensee_class_id', value: 28)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 29)
            column(name: 'description', value: 'Consumer Services')
            column(name: 'aggregate_licensee_class_id', value: 29)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 31)
            column(name: 'description', value: 'Fishing/Hunting/Trapping')
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 32)
            column(name: 'description', value: 'Agriculture/Forestry/Fishing')
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 34)
            column(name: 'description', value: 'Construction')
            column(name: 'aggregate_licensee_class_id', value: 56)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 35)
            column(name: 'description', value: 'Software & Systems Design')
            column(name: 'aggregate_licensee_class_id', value: 13)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 36)
            column(name: 'description', value: 'Consulting & Research')
            column(name: 'aggregate_licensee_class_id', value: 52)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 39)
            column(name: 'description', value: 'Government')
            column(name: 'aggregate_licensee_class_id', value: 39)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 41)
            column(name: 'description', value: 'Govt - Legal & Health')
            column(name: 'aggregate_licensee_class_id', value: 39)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 42)
            column(name: 'description', value: 'Govt-Admin,Legislative,Environ')
            column(name: 'aggregate_licensee_class_id', value: 39)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'description', value: 'Other - Govt')
            column(name: 'aggregate_licensee_class_id', value: 39)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 93)
            column(name: 'description', value: 'Membership Organizations')
            column(name: 'aggregate_licensee_class_id', value: 25)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 94)
            column(name: 'description', value: 'Lumber, Paper, Allied Products')
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 95)
            column(name: 'description', value: 'Electronic & Electric Equip')
            column(name: 'aggregate_licensee_class_id', value: 54)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 97)
            column(name: 'description', value: 'Security and Commodity Brokers')
            column(name: 'aggregate_licensee_class_id', value: 56)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 98)
            column(name: 'description', value: 'Other Health Services')
            column(name: 'aggregate_licensee_class_id', value: 52)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 99)
            column(name: 'description', value: 'Specialty Hospitals')
            column(name: 'aggregate_licensee_class_id', value: 52)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class') {
            column(name: 'detail_licensee_class_id', value: 100)
            column(name: 'description', value: 'Generic Pharmaceuticals')
            column(name: 'aggregate_licensee_class_id', value: 52)
        }

        rollback {
            sql("delete from ${dbAppsSchema}.df_detail_licensee_class where (detail_licensee_class_id >=1 and detail_licensee_class_id <=29) " +
                    "or detail_licensee_class_id in (31, 32, 34, 35, 36, 39, 41, 42, 43, 93, 94, 95, 97, 98, 99, 100)")
        }
    }

    changeSet(id: '2021-05-19-03', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("B-67105 FDA & UDM: UDM TOU Mapping: create df_udm_tou_mapping table")

        createTable(tableName: 'df_udm_tou_mapping', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing UDM type of uses for mapping') {

            column(name: 'df_udm_tou_mapping_uid', type: 'VARCHAR(255)', remarks: 'The UDM type of use identifier') {
                constraints(nullable: false)
            }
            column(name: 'type_of_use', type: 'VARCHAR(100)', remarks: 'Type of use') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(250)', remarks: 'The type of use description') {
                constraints(nullable: false)
            }
            column(name: 'rms_type_of_use', type: 'VARCHAR(128)', remarks: 'RMS type of use') {
                constraints(nullable: false)
            }
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1',
                    remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM',
                    remarks: 'The user name who created this record') {
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

        addPrimaryKey(schemaName: dbAppsSchema,
                tablespace: dbIndexTablespace,
                tableName: 'df_udm_tou_mapping',
                columnNames: 'df_udm_tou_mapping_uid',
                constraintName: 'pk_df_udm_tou_mapping_uid')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_tou_mapping') {
            column(name: 'df_udm_tou_mapping_uid', value: '659dd6d1-e8ee-40c8-bdc9-bce8f6b48a65')
            column(name: 'type_of_use', value: 'COPY_FOR_MYSELF')
            column(name: 'description', value: 'Keep copy for myself')
            column(name: 'rms_type_of_use', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_tou_mapping') {
            column(name: 'df_udm_tou_mapping_uid', value: '79157ac9-888a-4482-a1a4-fcce50bd29c2')
            column(name: 'type_of_use', value: 'EMAIL_COPY')
            column(name: 'description', value: 'Email a copy to my co-workers')
            column(name: 'rms_type_of_use', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_tou_mapping') {
            column(name: 'df_udm_tou_mapping_uid', value: '9f349b46-0c8d-4b67-aa10-9e08098444e4')
            column(name: 'type_of_use', value: 'PRINT_COPIES')
            column(name: 'description', value: 'Print copies and share with co-workers')
            column(name: 'rms_type_of_use', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_tou_mapping') {
            column(name: 'df_udm_tou_mapping_uid', value: '42d8991c-91ef-415b-ab40-ac1eda84ef71')
            column(name: 'type_of_use', value: 'DISPLAY_IN_POWERPOINT')
            column(name: 'description', value: 'Display in a PowerPoint presentation to co-workers')
            column(name: 'rms_type_of_use', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_tou_mapping') {
            column(name: 'df_udm_tou_mapping_uid', value: 'cfd6a47b-73ab-4b05-a96f-1bdc7ea209b3')
            column(name: 'type_of_use', value: 'DISTRIBUTE_IN_POWERPOINT')
            column(name: 'description', value: 'Distribute in a PowerPoint presentation to co-workers')
            column(name: 'rms_type_of_use', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_tou_mapping') {
            column(name: 'df_udm_tou_mapping_uid', value: '709bd060-e47f-4f01-b1ea-96ccff03ddb7')
            column(name: 'type_of_use', value: 'FAX_PHOTOCOPIES')
            column(name: 'description', value: 'FAX photocopies to co-workers')
            column(name: 'rms_type_of_use', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_tou_mapping') {
            column(name: 'df_udm_tou_mapping_uid', value: '5e777a3c-bba8-4465-b6e0-f5079eebc6fc')
            column(name: 'type_of_use', value: 'PHOTOCOPY_SHARING_OTHER')
            column(name: 'description', value: 'Other')
            column(name: 'rms_type_of_use', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_tou_mapping') {
            column(name: 'df_udm_tou_mapping_uid', value: 'af2fed07-75eb-4339-be0c-bea9549c7f1f')
            column(name: 'type_of_use', value: 'SHARE_PHOTOCOPIES')
            column(name: 'description', value: 'Share photocopies with co-workers')
            column(name: 'rms_type_of_use', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_tou_mapping') {
            column(name: 'df_udm_tou_mapping_uid', value: '8ca7ff4b-d5e5-4ac0-8150-f070493c6a30')
            column(name: 'type_of_use', value: 'SHARE_SINGLE_ELECTRONIC_COPY')
            column(name: 'description', value: 'Share single electronic copies with clients, prospects or customers of my organization, in response to a specific request, for information purposes')
            column(name: 'rms_type_of_use', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_tou_mapping') {
            column(name: 'df_udm_tou_mapping_uid', value: 'd81ad8d2-384a-4ad4-bac8-bce1f4949601')
            column(name: 'type_of_use', value: 'STORE_COPY')
            column(name: 'description', value: 'Store a copy on an internal shared network')
            column(name: 'rms_type_of_use', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_tou_mapping') {
            column(name: 'df_udm_tou_mapping_uid', value: '06a10ea4-5503-4f7a-b31e-5bc5839f8cc0')
            column(name: 'type_of_use', value: 'SUBMIT_ELECTRONIC_COPY')
            column(name: 'description', value: 'Submit an electronic copy to regulatory authorities')
            column(name: 'rms_type_of_use', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_tou_mapping') {
            column(name: 'df_udm_tou_mapping_uid', value: 'd0191236-75f1-46dc-adc1-4cd08395465b')
            column(name: 'type_of_use', value: 'SUBMIT_PHOTOCOPY')
            column(name: 'description', value: 'Submit a photocopy to regulatory authorities')
            column(name: 'rms_type_of_use', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_tou_mapping') {
            column(name: 'df_udm_tou_mapping_uid', value: 'bc4d8020-657e-4427-9cda-dbbbfeba0f9d')
            column(name: 'type_of_use', value: 'DIGITAL_SHARING_OTHER')
            column(name: 'description', value: 'Other')
            column(name: 'rms_type_of_use', value: 'DIGITAL')
        }

        rollback {
            dropTable(tableName: 'df_udm_tou_mapping', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2021-05-19-04', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment("B-67378 FDA & UDM: Detail/Aggregate licensee mapping: create df_company table")

        createTable(tableName: 'df_company', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing company information') {

            column(name: 'df_company_id', type: 'NUMERIC(10)', remarks: 'The company id') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(200)', remarks: 'The company name') {
                constraints(nullable: false)
            }
            column(name: 'detail_licensee_class_id', type: 'INTEGER', remarks: 'The identifier of Detail Licensee Class') {
                constraints(nullable: false)
            }
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1',
                    remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM',
                    remarks: 'The user name who created this record') {
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

        addPrimaryKey(schemaName: dbAppsSchema,
                tablespace: dbIndexTablespace,
                tableName: 'df_company',
                columnNames: 'df_company_id',
                constraintName: 'pk_df_company_id')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_company',
                baseColumnNames: 'detail_licensee_class_id',
                referencedTableName: 'df_detail_licensee_class',
                referencedColumnNames: 'detail_licensee_class_id',
                constraintName: 'fk_df_company_2_df_detail_licensee_class')

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2021-05-27-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment("B-66754 FDA & UDM: Pull Company Information from Telesales: drop df_company table")

        dropTable(tableName: 'df_company', schemaName: dbAppsSchema)

        rollback {
            createTable(tableName: 'df_company', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                    remarks: 'Table for storing company information') {

                column(name: 'df_company_id', type: 'NUMERIC(10)', remarks: 'The company id') {
                    constraints(nullable: false)
                }
                column(name: 'name', type: 'VARCHAR(200)', remarks: 'The company name') {
                    constraints(nullable: false)
                }
                column(name: 'detail_licensee_class_id', type: 'INTEGER', remarks: 'The identifier of Detail Licensee Class') {
                    constraints(nullable: false)
                }
                column(name: 'record_version', type: 'INTEGER', defaultValue: '1',
                        remarks: 'The latest version of this record, used for optimistic locking') {
                    constraints(nullable: false)
                }
                column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM',
                        remarks: 'The user name who created this record') {
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

            addPrimaryKey(schemaName: dbAppsSchema,
                    tablespace: dbIndexTablespace,
                    tableName: 'df_company',
                    columnNames: 'df_company_id',
                    constraintName: 'pk_df_company_id')

            addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                    referencedTableSchemaName: dbAppsSchema,
                    baseTableName: 'df_company',
                    baseColumnNames: 'detail_licensee_class_id',
                    referencedTableName: 'df_detail_licensee_class',
                    referencedColumnNames: 'detail_licensee_class_id',
                    constraintName: 'fk_df_company_2_df_detail_licensee_class')
        }
    }

    changeSet(id: '2021-06-09-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-65859 FDA: UDM in ACL Audit: create df_udm_audit table")

        createTable(tableName: 'df_udm_audit', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing UDM usage audit actions') {

            column(name: 'df_udm_audit_uid', type: 'VARCHAR(255)', remarks: 'The identifier of UDM usage audit action') {
                constraints(nullable: false)
            }
            column(name: 'df_udm_usage_uid', type: 'VARCHAR(255)', remarks: 'The identifier of UDM usage') {
                constraints(nullable: false)
            }
            column(name: 'action_type_ind', type: 'VARCHAR(32)', remarks: 'Usage action type index') {
                constraints(nullable: false)
            }
            column(name: 'action_reason', type: 'VARCHAR(1024)', remarks: 'Usage action reason') {
                constraints(nullable: false)
            }
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1',
                    remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM',
                    remarks: 'The user name who created this record') {
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

        addPrimaryKey(schemaName: dbAppsSchema,
                tablespace: dbIndexTablespace,
                tableName: 'df_udm_audit',
                columnNames: 'df_udm_audit_uid',
                constraintName: 'pk_df_udm_audit_uid')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_udm_audit',
                baseColumnNames: 'df_udm_usage_uid',
                referencedTableName: 'df_udm_usage',
                referencedColumnNames: 'df_udm_usage_uid',
                constraintName: 'fk_df_udm_usage_2_df_udm_audit')

        rollback {
            dropTable(tableName: 'df_udm_audit', schemaName: dbAppsSchema)
        }
    }
}
