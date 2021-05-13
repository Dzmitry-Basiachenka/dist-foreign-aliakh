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
}
