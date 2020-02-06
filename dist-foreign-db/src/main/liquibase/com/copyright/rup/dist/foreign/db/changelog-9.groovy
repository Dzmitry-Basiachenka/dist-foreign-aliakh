databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-02-05-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-52334 FDA: Load AACL Fund Pool: create df_fund_pool_aacl, df_fund_pool_detail_aacl tables")

        createTable(tableName: 'df_fund_pool_aacl', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing AACL fund pool') {

            column(name: 'df_fund_pool_aacl_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool')
            column(name: 'name', type: 'VARCHAR(255)', remarks: 'The name of fund pool')
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

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_fund_pool_aacl', tablespace: dbIndexTablespace,
                columnNames: 'df_fund_pool_aacl_uid', constraintName: 'df_fund_pool_aacl_pk')

        createTable(tableName: 'df_fund_pool_detail_aacl', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing AACL fund pool details') {

            column(name: 'df_fund_pool_detail_aacl_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool detail')
            column(name: 'df_fund_pool_aacl_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool')
            column(name: 'gross_amount', type: 'NUMERIC(38,10)', remarks: 'The gross amount')
            column(name: 'df_aggregate_licensee_class_id', type: 'NUMERIC(38)', remarks: 'Aggregate Licensee Class Id')
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

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail_aacl', tablespace: dbIndexTablespace,
                columnNames: 'df_fund_pool_detail_aacl_uid', constraintName: 'df_fund_pool_detail_aacl_pk')

        addForeignKeyConstraint(constraintName: 'fk_df_fund_pool_detail_aacl_2_df_fund_pool_aacl',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_fund_pool_detail_aacl',
                baseColumnNames: 'df_fund_pool_aacl_uid',
                referencedTableName: 'df_fund_pool_aacl',
                referencedColumnNames: 'df_fund_pool_aacl_uid')

        addForeignKeyConstraint(constraintName: 'fk_df_fund_pool_detail_aacl_2_df_aggregate_licensee_class',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_fund_pool_detail_aacl',
                baseColumnNames: 'df_aggregate_licensee_class_id',
                referencedTableName: 'df_aggregate_licensee_class',
                referencedColumnNames: 'aggregate_licensee_class_id')

        rollback {
            dropTable(tableName: 'df_fund_pool_detail_aacl', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_fund_pool_aacl', schemaName: dbAppsSchema)
        }
    }
}
