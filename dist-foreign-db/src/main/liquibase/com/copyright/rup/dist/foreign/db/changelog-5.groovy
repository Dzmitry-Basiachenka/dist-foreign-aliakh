databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-03-06-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-46040 FDA: Assign title classification to titles in NTS distribution: create df_work_classification table")

        createTable(tableName: 'df_work_classification', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'The table to store works classifications') {

            column(name: 'df_work_classification_uid', type: 'VARCHAR(255)', remarks: 'The identifier of work classification') {
                constraints(nullable: false)
            }
            column(name: 'wr_wrk_inst', type: 'NUMERIC(15,0)', remarks: 'Wr Wrk Inst') {
                constraints(nullable: false)
                constraints(unique: true)
            }
            column(name: 'classification', type: 'VARCHAR(128)', remarks: 'Work classification') {
                constraints(nullable: false)
            }
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1', remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM', remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created') {
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

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2019-03-11-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('B-49017 FDA: Address the number of buttons on the Usage tab: ' +
                'add product_family column into df_usage_batch table and populate historical data')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'product_family', type: 'VARCHAR(128)', remarks: 'Product Family')
        }

        update(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'product_family', value: 'NTS')
            where "fund_pool is not null"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'product_family', value: 'FAS2')
            where "fund_pool is null and rro_account_number = 2000017000"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'product_family', value: 'FAS')
            where "fund_pool is null and rro_account_number != 2000017000 and product_family is null"
        }

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch',
                columnName: 'product_family', columnDataType: 'VARCHAR(128)')
    
        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch', columnName: 'product_family')
        }
    }
}
