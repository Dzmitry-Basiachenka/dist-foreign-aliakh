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

    changeSet(id: '2020-02-26-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-52332 FDA: Add baseline usage details to usage batch: add is_baseline_flag and " +
                "publication_type_weight columns into df_usage_aacl table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'is_baseline_flag', type: 'BOOLEAN', defaultValue: 'false', remarks: 'Baseline Flag')
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'publication_type_weight', type: 'NUMERIC(10,2)', remarks: 'Publication Type Weight')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl', columnName: 'is_baseline_flag')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl', columnName: 'publication_type_weight')
        }
    }

    changeSet(id: '2020-02-26-01', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment("B-52332 FDA: Add baseline usage details to usage batch: create df_usage_baseline_aacl table")

        createTable(tableName: 'df_usage_baseline_aacl', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing AACL baseline usages') {

            column(name: 'df_usage_baseline_aacl_uid', type: 'VARCHAR(255)', remarks: 'The identifier of baseline usage')
            column(name: 'wr_wrk_inst', type: 'NUMERIC(15,0)', remarks: 'Wr Wrk Inst') {
                constraints(nullable: false)
            }
            column(name: 'usage_period', type: 'NUMERIC(6,0)', remarks: 'Usage period') {
                constraints(nullable: false)
            }
            column(name: 'usage_source', type: 'VARCHAR(150)', remarks: 'Usage source') {
                constraints(nullable: false)
            }
            column(name: 'number_of_copies', type: 'INTEGER', remarks: 'Number of copies') {
                constraints(nullable: false)
            }
            column(name: 'number_of_pages', type: 'INTEGER', remarks: 'Number of pages') {
                constraints(nullable: false)
            }
            column(name: 'detail_licensee_class_id', type: 'INTEGER', remarks: 'The identifier of Detail Licensee Class') {
                constraints(nullable: false)
            }
            column(name: 'original_publication_type', type: 'VARCHAR(255)', remarks: 'Publication Type from ShareCalc')
            column(name: 'publication_type_uid', type: 'VARCHAR(255)', remarks: 'The identifier of Publication Type') {
                constraints(nullable: false)
            }
            column(name: 'publication_type_weight', type: 'NUMERIC(10,2)', remarks: 'Publication Type weight')
            column(name: 'institution', type: 'VARCHAR(255)', remarks: 'Institution')
            column(name: 'comment', type: 'VARCHAR(100)', remarks: 'Usage comment')
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

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl', tablespace: dbIndexTablespace,
                columnNames: 'df_usage_baseline_aacl_uid', constraintName: 'pk_df_usage_baseline_aacl')

        addForeignKeyConstraint(constraintName: 'fk_df_usage_baseline_aacl_2_df_publication_type',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_usage_baseline_aacl',
                baseColumnNames: 'publication_type_uid',
                referencedTableName: 'df_publication_type',
                referencedColumnNames: 'df_publication_type_uid')

        addForeignKeyConstraint(constraintName: 'fk_df_usage_baseline_aacl_2_df_detail_licensee_class',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_usage_baseline_aacl',
                baseColumnNames: 'detail_licensee_class_id',
                referencedTableName: 'df_detail_licensee_class',
                referencedColumnNames: 'detail_licensee_class_id')

        rollback {
            dropTable(tableName: 'df_usage_baseline_aacl', schemaName: dbAppsSchema)
        }
    }
    
    changeSet(id: '2020-02-26-02', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-52332 FDA: Add baseline usage details to usage batch: add baseline_years column into " +
                "df_usage_batch table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'baseline_years', type: 'INTEGER', remarks: 'Number of Baseline Years')
        }

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2020-02-27-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment("B-52332 FDA: Add baseline usage details to usage batch: add original_publication_type column " +
                "to df_usage_aacl table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'original_publication_type', type: 'VARCHAR(255)', remarks: 'Publication Type from ShareCalc')
        }

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2020-02-27-01', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-57437 FDA: Tech Debt: make Usage Batch name unique across product family")

        addUniqueConstraint(constraintName: 'uk_df_usage_batch_name_product_family',
                schemaName: dbAppsSchema,
                tablespace: dbDataTablespace,
                tableName: 'df_usage_batch',
                columnNames: 'name, product_family')
        rollback {
            dropUniqueConstraint(constraintName: 'uk_df_usage_batch_name_product_family',
                    schemaName: dbAppsSchema,
                    tablespace: dbDataTablespace,
                    tableName: 'df_usage_batch',
                    columnNames: 'name, product_family')
        }
    }

    changeSet(id: '2020-03-11-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-56947 FDA: AACL view and edit scenario Pub Type Weights: delete Pub Type 'Other' and add Pub Type Weights")

        delete(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            where "name = 'Other'"
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'weight', type: 'NUMERIC(10,2)', remarks: 'Publication Type Weight')
        }

        update(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'weight', value: '1.00')
            where "name = 'Book'"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'weight', value: '1.50')
            where "name = 'Business or Trade Journal'"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'weight', value: '1.00')
            where "name = 'Consumer Magazine'"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'weight', value: '4.00')
            where "name = 'News Source'"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'weight', value: '1.10')
            where "name = 'STMA Journal'"
        }

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_publication_type',
                columnName: 'weight', columnDataType: 'NUMERIC(10,2)')

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_publication_type', columnName: 'weight')

            insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
                column(name: 'df_publication_type_uid', value: '357ea293-8f98-4160-820e-8369f6180654')
                column(name: 'name', value: 'Other')
            }
        }
    }

    changeSet(id: '2020-03-11-01', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-57613 FDA: Audit Tab modifications for AACL: remove is_baseline_flag column and " +
                "add baseline_uid column")

        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl', columnName: 'is_baseline_flag')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'baseline_uid', type: 'VARCHAR(255)', remarks: 'The Identifier of Baseline Usage')
        }

        addForeignKeyConstraint(constraintName: 'fk_df_usage_aacl_2_df_usage_baseline_aacl',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_usage_aacl',
                baseColumnNames: 'baseline_uid',
                referencedTableName: 'df_usage_baseline_aacl',
                referencedColumnNames: 'df_usage_baseline_aacl_uid')

        rollback {
            dropForeignKeyConstraint(
                    baseTableSchemaName: dbAppsSchema,
                    baseTableName: 'df_usage_aacl',
                    constraintName: 'fk_df_usage_aacl_2_df_usage_baseline_aacl')

            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl', columnName: 'baseline_uid')

            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
                column(name: 'is_baseline_flag', type: 'BOOLEAN', remarks: 'The Identifier of Baseline Usage')
            }
        }
    }
}
