databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-02-04-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment("B-52334 FDA: Load AACL Fund Pool: add product_family column into df_fund_pool table " +
                "and rename withdrawn_amount to total_amount")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'product_family', type: 'VARCHAR(128)', remarks: 'Product Family')
        }

        renameColumn(schemaName: dbAppsSchema,
                tableName: 'df_fund_pool',
                oldColumnName: 'withdrawn_amount',
                newColumnName: 'total_amount',
                columnDataType: 'DECIMAL(38,2)')

        update(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'product_family', value: 'NTS')
        }

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_fund_pool',
                columnName: 'product_family', columnDataType: 'VARCHAR(128)')

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_fund_pool', columnName: 'product_family')

            renameColumn(schemaName: dbAppsSchema,
                    tableName: 'df_fund_pool',
                    oldColumnName: 'total_amount',
                    newColumnName: 'withdrawn_amount',
                    columnDataType: 'DECIMAL(38,2)')
        }
    }

    changeSet(id: '2020-02-05-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-52334 FDA: Load AACL Fund Pool: create df_fund_pool_detail table")

        createTable(tableName: 'df_fund_pool_detail', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing fund pool details') {

            column(name: 'df_fund_pool_detail_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool detail')
            column(name: 'df_fund_pool_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool')
            column(name: 'gross_amount', type: 'NUMERIC(38,2)', remarks: 'The gross amount')
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

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail', tablespace: dbIndexTablespace,
                columnNames: 'df_fund_pool_detail_uid', constraintName: 'pk_df_fund_pool_detail')

        addForeignKeyConstraint(constraintName: 'fk_df_fund_pool_detail_2_df_fund_pool',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_fund_pool_detail',
                baseColumnNames: 'df_fund_pool_uid',
                referencedTableName: 'df_fund_pool',
                referencedColumnNames: 'df_fund_pool_uid')

        addForeignKeyConstraint(constraintName: 'fk_df_fund_pool_detail_2_df_aggregate_licensee_class',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_fund_pool_detail',
                baseColumnNames: 'df_aggregate_licensee_class_id',
                referencedTableName: 'df_aggregate_licensee_class',
                referencedColumnNames: 'aggregate_licensee_class_id')

        rollback {
            dropTable(tableName: 'df_fund_pool_detail', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2020-02-12-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-52334 FDA: Load AACL Fund Pool: make Fund Pool name unique across product family")

        addUniqueConstraint(constraintName: 'uk_df_fund_pool_name_product_family',
                schemaName: dbAppsSchema,
                tablespace: dbDataTablespace,
                tableName: 'df_fund_pool',
                columnNames: 'name, product_family')

        dropUniqueConstraint(constraintName: 'df_fund_pool_name_key',
                schemaName: dbAppsSchema,
                tablespace: dbDataTablespace,
                tableName: 'df_fund_pool',
                columnNames: 'name')
        rollback {
            addUniqueConstraint(constraintName: 'df_fund_pool_name_key',
                    schemaName: dbAppsSchema,
                    tablespace: dbDataTablespace,
                    tableName: 'df_fund_pool',
                    columnNames: 'name')

            dropUniqueConstraint(constraintName: 'uk_df_fund_pool_name_product_family',
                    schemaName: dbAppsSchema,
                    tablespace: dbDataTablespace,
                    tableName: 'df_fund_pool',
                    columnNames: 'name, product_family')
        }
    }

    changeSet(id: '2020-02-17-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-56093 Tech Debt: FDA: modify data types of detail_licensee_class_id and aggregate_licensee_class_id columns")

        dropForeignKeyConstraint(
                baseTableSchemaName: dbAppsSchema,
                baseTableName: 'df_detail_licensee_class',
                constraintName: 'fk_df_detail_licensee_class_2_df_aggregate_licensee_class')

        dropForeignKeyConstraint(
                baseTableSchemaName: dbAppsSchema,
                baseTableName: 'df_fund_pool_detail',
                constraintName: 'fk_df_fund_pool_detail_2_df_aggregate_licensee_class')

        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class', columnName: 'detail_licensee_class_id', newDataType: 'INTEGER')
        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class', columnName: 'aggregate_licensee_class_id', newDataType: 'INTEGER')
        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class', columnName: 'aggregate_licensee_class_id', newDataType: 'INTEGER')
        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail', columnName: 'df_aggregate_licensee_class_id', newDataType: 'INTEGER')

        addForeignKeyConstraint(constraintName: 'fk_df_detail_licensee_class_2_df_aggregate_licensee_class',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_detail_licensee_class',
                baseColumnNames: 'aggregate_licensee_class_id',
                referencedTableName: 'df_aggregate_licensee_class',
                referencedColumnNames: 'aggregate_licensee_class_id')

        addForeignKeyConstraint(constraintName: 'fk_df_fund_pool_detail_2_df_aggregate_licensee_class',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_fund_pool_detail',
                baseColumnNames: 'df_aggregate_licensee_class_id',
                referencedTableName: 'df_aggregate_licensee_class',
                referencedColumnNames: 'aggregate_licensee_class_id')

        rollback {
            dropForeignKeyConstraint(
                    baseTableSchemaName: dbAppsSchema,
                    baseTableName: 'df_detail_licensee_class',
                    constraintName: 'fk_df_detail_licensee_class_2_df_aggregate_licensee_class')

            dropForeignKeyConstraint(
                    baseTableSchemaName: dbAppsSchema,
                    baseTableName: 'df_fund_pool_detail',
                    constraintName: 'fk_df_fund_pool_detail_2_df_aggregate_licensee_class')

            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class', columnName: 'aggregate_licensee_class_id', newDataType: 'NUMERIC(38)')
            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class', columnName: 'detail_licensee_class_id', newDataType: 'NUMERIC(38)')
            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_detail_licensee_class', columnName: 'aggregate_licensee_class_id', newDataType: 'NUMERIC(38)')
            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail', columnName: 'df_aggregate_licensee_class_id', newDataType: 'NUMERIC(38)')

            addForeignKeyConstraint(constraintName: 'fk_df_detail_licensee_class_2_df_aggregate_licensee_class',
                    baseTableSchemaName: dbAppsSchema,
                    referencedTableSchemaName: dbAppsSchema,
                    baseTableName: 'df_detail_licensee_class',
                    baseColumnNames: 'aggregate_licensee_class_id',
                    referencedTableName: 'df_aggregate_licensee_class',
                    referencedColumnNames: 'aggregate_licensee_class_id')

            addForeignKeyConstraint(constraintName: 'fk_df_fund_pool_detail_2_df_aggregate_licensee_class',
                    baseTableSchemaName: dbAppsSchema,
                    referencedTableSchemaName: dbAppsSchema,
                    baseTableName: 'df_fund_pool_detail',
                    baseColumnNames: 'df_aggregate_licensee_class_id',
                    referencedTableName: 'df_aggregate_licensee_class',
                    referencedColumnNames: 'aggregate_licensee_class_id')
        }
    }

    changeSet(id: '2020-02-18-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-56093 Tech Debt: FDA: rename fund_pool column to nts_fields in df_usage_batch table")

        renameColumn(schemaName: dbAppsSchema,
                tableName: 'df_usage_batch',
                oldColumnName: 'fund_pool',
                newColumnName: 'nts_fields',
                columnDataType: 'JSONB')

        rollback {
            renameColumn(schemaName: dbAppsSchema,
                    tableName: 'df_usage_batch',
                    oldColumnName: 'nts_fields',
                    newColumnName: 'fund_pool',
                    columnDataType: 'JSONB')
        }
    }
}
