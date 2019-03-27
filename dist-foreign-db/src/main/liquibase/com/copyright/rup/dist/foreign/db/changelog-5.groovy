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

    changeSet(id: '2019-03-22-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-46040 FDA: Assign title classification to titles in NTS distribution: " +
                "add index by wr_wrk_inst in df_usage_archive table")

        createIndex(indexName: 'ix_df_usage_archive_wr_wrk_inst', schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                tablespace: dbIndexTablespace) {
            column(name: 'wr_wrk_inst')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_usage_archive_wr_wrk_inst")
        }
    }

    changeSet(id: '2019-03-27-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: " +
                "Implement Liqubase script to create table df_fund_pool")

        createTable(tableName: 'df_fund_pool', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'The table to store NTS withdrawn fund pool') {

            column(name: 'df_fund_pool_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(255)', remarks: 'The name of fund pool') {
                constraints(nullable: false)
                constraints(unique: true)
            }
            column(name: 'comment', type: 'VARCHAR(2000)', remarks: 'The comment of fund pool') {
                constraints(nullable: true)
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

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_fund_pool', tablespace: dbIndexTablespace,
                columnNames: 'df_fund_pool_uid', constraintName: 'df_fund_pool_pk')

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2019-03-27-01', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('B-50004 FDA: Add Standard Number Type to the usage for classification: ' +
                'Add standard_number_type column to df_usage and df_usage_archive tables')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'standard_number_type', type: 'VARCHAR(50)', remarks: 'Standard Number Type')
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'standard_number_type', type: 'VARCHAR(50)', remarks: 'Standard Number Type')
        }

        rollback {
            // automatic rollback
        }
    }
}
