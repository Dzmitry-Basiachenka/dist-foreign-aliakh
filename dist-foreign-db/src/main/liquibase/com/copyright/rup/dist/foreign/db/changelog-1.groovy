databaseChangeLog {

    property(file: 'database.properties')

    changeSet(id: '2017-02-01-00', author: 'Mikita_Hladkikh mhladkikh@copyright.com') {
        comment('B-28915 FDA DB prototyping: Implement liquibase script for usage table')

        createTable(tableName: 'df_usage', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing usages') {

            column(name: 'df_usage_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage') {
                constraints(nullable: false)
            }
            column(name: 'df_usage_batch_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage batch') {
                constraints(nullable: false)
            }
            column(name: 'detail_id', type: 'NUMERIC(15,0)', remarks: 'The usage identifier in TF') {
                constraints(nullable: false)
            }
            column(name: 'wr_wrk_inst', type: 'NUMERIC(15,0)', remarks: 'The wr wrk inst')
            column(name: 'work_title', type: 'VARCHAR(2000)', remarks: 'The work title')
            column(name: 'rh_account_number', type: 'NUMERIC(22,0)', remarks: 'The rightsholder account number')
            column(name: 'status_ind', type: 'VARCHAR(16)', remarks: 'The status of usage') {
                constraints(nullable: false)
            }
            column(name: 'article', type: 'VARCHAR(1000)', remarks: 'The article')
            column(name: 'standard_number', type: 'VARCHAR(1000)', remarks: 'The main piece of data used to identify the work')
            column(name: 'publisher', type: 'VARCHAR(1000)', remarks: 'The publisher')
            column(name: 'publication_date', type: 'DATE', remarks: 'The publication date')
            column(name: 'market', type: 'VARCHAR(200)', remarks: 'The market') {
                constraints(nullable: false)
            }
            column(name: 'market_period_from', type: 'NUMERIC(4,0)', remarks: 'The beginning period of when the usage occured') {
                constraints(nullable: false)
            }
            column(name: 'market_period_to', type: 'NUMERIC(4,0)', remarks: 'The ending period of when the usage occured') {
                constraints(nullable: false)
            }
            column(name: 'author', type: 'VARCHAR(1000)', remarks: 'The author')
            column(name: 'number_of_copies', type: 'INTEGER', remarks: 'The number of copies')
            column(name: 'original_amount', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The amount in original currency') {
                constraints(nullable: false)
            }
            column(name: 'net_amount', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The net amount') {
                constraints(nullable: false)
            }
            column(name: 'service_fee', type: 'DECIMAL(6,5)', defaultValue: 0.00000, remarks: 'The service fee') {
                constraints(nullable: false)
            }
            column(name: 'service_fee_amount', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The service fee amount') {
                constraints(nullable: false)
            }
            column(name: 'gross_amount', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The gross amount') {
                constraints(nullable: false)
            }
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

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_usage', tablespace: dbIndexTablespace,
                columnNames: 'df_usage_uid', constraintName: 'df_usage_pk')

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2017-02-01-01', author: 'Mikita_Hladkikh mhladkikh@copyright.com') {
        comment('B-28915 FDA DB prototyping: Implement liquibase script for usage archive table')

        createTable(tableName: 'df_usage_archive', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing archived usages') {

            column(name: 'df_usage_archive_uid', type: 'VARCHAR(255)', remarks: 'The identifier of archived usage') {
                constraints(nullable: false)
            }
            column(name: 'df_usage_batch_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage batch') {
                constraints(nullable: false)
            }
            column(name: 'detail_id', type: 'INTEGER', remarks: 'The usage identifier in TF') {
                constraints(nullable: false)
            }
            column(name: 'wr_wrk_inst', type: 'NUMERIC(15,0)', remarks: 'The wr wrk inst')
            column(name: 'work_title', type: 'VARCHAR(2000)', remarks: 'The work title')
            column(name: 'rh_account_number', type: 'NUMERIC(22,0)', remarks: 'The rightsholder account number')
            column(name: 'status_ind', type: 'VARCHAR(16)', remarks: 'The status of usage') {
                constraints(nullable: false)
            }
            column(name: 'article', type: 'VARCHAR(1000)', remarks: 'The article')
            column(name: 'standard_number', type: 'VARCHAR(1000)', remarks: 'The main piece of data used to identify the work')
            column(name: 'publisher', type: 'VARCHAR(1000)', remarks: 'The publisher')
            column(name: 'publication_date', type: 'DATE', remarks: 'The publication date')
            column(name: 'market', type: 'VARCHAR(200)', remarks: 'The market') {
                constraints(nullable: false)
            }
            column(name: 'market_period_from', type: 'NUMERIC(4,0)', remarks: 'The beginning period of when the usage occured') {
                constraints(nullable: false)
            }
            column(name: 'market_period_to', type: 'NUMERIC(4,0)', remarks: 'The ending period of when the usage occured') {
                constraints(nullable: false)
            }
            column(name: 'author', type: 'VARCHAR(1000)', remarks: 'The author')
            column(name: 'number_of_copies', type: 'INTEGER', remarks: 'The number of copies')
            column(name: 'original_amount', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The amount in original currency') {
                constraints(nullable: false)
            }
            column(name: 'net_amount', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The net amount') {
                constraints(nullable: false)
            }
            column(name: 'service_fee', type: 'DECIMAL(6,5)', defaultValue: 0.00000, remarks: 'The service fee') {
                constraints(nullable: false)
            }
            column(name: 'service_fee_amount', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The service fee amount') {
                constraints(nullable: false)
            }
            column(name: 'gross_amount', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The gross amount') {
                constraints(nullable: false)
            }
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

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_usage_archive', tablespace: dbIndexTablespace,
                columnNames: 'df_usage_archive_uid', constraintName: 'df_usage_archive_pk')

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2017-02-02-00', author: 'Mikalai_Bezmen mbezmen@copyright.com') {
        comment('B-28915 FDA DB prototyping: Implement liquibase script for usage batch table')

        createTable(tableName: 'df_usage_batch', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing usage batches') {

            column(name: 'df_usage_batch_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage batch') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(255)', remarks: 'The name of usage batch') {
                constraints(nullable: false)
            }
            column(name: 'rro_account_number', type: 'NUMERIC(22,0)', remarks: 'The RRO account number') {
                constraints(nullable: false)
            }
            column(name: 'payment_date', type: 'DATE', remarks: 'The payment date') {
                constraints(nullable: false)
            }
            column(name: 'fiscal_year', type: 'NUMERIC(4,0)', remarks: 'The fiscal year') {
                constraints(nullable: false)
            }
            column(name: 'gross_amount', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The gross amount') {
                constraints(nullable: false)
            }
            column(name: 'currency_ind', type: 'VARCHAR(3)', remarks: 'The currency index')
            column(name: 'conversion_rate', type: 'NUMERIC(20,10)', remarks: 'The conversion rate')
            column(name: 'applied_conversion_rate', type: 'NUMERIC(20,10)', remarks: 'The applied conversion rate')
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

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_usage_batch', tablespace: dbIndexTablespace,
                columnNames: 'df_usage_batch_uid', constraintName: 'df_usage_batch_pk')

        addForeignKeyConstraint(constraintName: 'fk_df_usage_2_df_usage_batch',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_usage',
                baseColumnNames: 'df_usage_batch_uid',
                referencedTableName: 'df_usage_batch',
                referencedColumnNames: 'df_usage_batch_uid')

        rollback {
            // automatic rollback
        }
    }
}
