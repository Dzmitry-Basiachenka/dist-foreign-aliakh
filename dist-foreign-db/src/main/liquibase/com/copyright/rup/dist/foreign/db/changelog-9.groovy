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
                tableName: 'df_fund_pool')
        rollback {
            addUniqueConstraint(constraintName: 'df_fund_pool_name_key',
                    schemaName: dbAppsSchema,
                    tablespace: dbDataTablespace,
                    tableName: 'df_fund_pool',
                    columnNames: 'name')

            dropUniqueConstraint(constraintName: 'uk_df_fund_pool_name_product_family',
                    schemaName: dbAppsSchema,
                    tableName: 'df_fund_pool')
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
                    tableName: 'df_usage_batch')
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

    changeSet(id: '2020-03-11-02', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-56947 FDA: AACL view and edit scenario Pub Type Weights: add aacl_fields column to df_scenario table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'aacl_fields', type: 'JSONB', remarks: 'The fields of AACL scenarios')
        }

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2020-03-13-00', author: 'Ihar Suvorau<isuvorau@copyright.com>') {
        comment("B-57745 FDA: AACL Licensee Class Name changes: replace aggregate_licensee_class_name " +
                "column by enrollment_profile and discipline columns in df_aggregate_licensee_class table")

        dropColumn(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class', columnName: 'aggregate_licensee_class_name')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'enrollment_profile', type: 'VARCHAR(255)', remarks: 'Aggregate Licensee Class Enrollment Profile')
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'discipline', type: 'VARCHAR(255)', remarks: 'Aggregate Licensee Class Discipline')
        }

        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'enrollment_profile', value: 'EXGP')
            where "aggregate_licensee_class_id in ('108', '115', '136', '143', '164', '171', '192', '206', '227')"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'enrollment_profile', value: 'EXU4')
            where "aggregate_licensee_class_id in ('110', '117', '138', '145', '166', '173', '194', '208', '229')"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'enrollment_profile', value: 'HGP')
            where "aggregate_licensee_class_id in ('111', '118', '139', '146', '167', '174', '195', '209', '230')"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'enrollment_profile', value: 'MU')
            where "aggregate_licensee_class_id in ('113', '120', '141', '148', '169', '176', '197', '211', '232')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'discipline', value: 'Life Sciences')
            where "aggregate_licensee_class_id in ('108', '110', '111', '113')"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'discipline', value: 'Business Management')
            where "aggregate_licensee_class_id in ('115', '117', '118', '120')"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'discipline', value: 'Education')
            where "aggregate_licensee_class_id in ('136', '138', '139', '141')"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'discipline', value: 'Engineering')
            where "aggregate_licensee_class_id in ('143', '145', '146', '148')"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'discipline', value: 'Law & Legal Studies')
            where "aggregate_licensee_class_id in ('164', '166', '167', '169')"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'discipline', value: 'Arts & Humanities')
            where "aggregate_licensee_class_id in ('171', '173', '174', '176')"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'discipline', value: 'Medical & Health')
            where "aggregate_licensee_class_id in ('192', '194', '195', '197')"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'discipline', value: 'Physical Sciences & Mathematics')
            where "aggregate_licensee_class_id in ('206', '208', '209', '211')"
        }
        update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
            column(name: 'discipline', value: 'Social & Behavioral Sciences')
            where "aggregate_licensee_class_id in ('227', '229', '230', '232')"
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class', columnName: 'enrollment_profile')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class', columnName: 'discipline')

            addColumn(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', type: 'VARCHAR(255)', remarks: 'Aggregate Licensee Class Name')
            }

            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXGP - Life Sciences')
                where "aggregate_licensee_class_id = '108'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Life Sciences')
                where "aggregate_licensee_class_id = '110'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'HGP - Life Sciences')
                where "aggregate_licensee_class_id = '111'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'MU - Life Sciences')
                where "aggregate_licensee_class_id = '113'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXGP - Business Management')
                where "aggregate_licensee_class_id = '115'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Business Management')
                where "aggregate_licensee_class_id = '117'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'HGP - Business Management')
                where "aggregate_licensee_class_id = '118'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'MU - Business Management')
                where "aggregate_licensee_class_id = '120'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXGP - Education')
                where "aggregate_licensee_class_id = '136'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Education')
                where "aggregate_licensee_class_id = '138'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'HGP - Education')
                where "aggregate_licensee_class_id = '139'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'MU - Education')
                where "aggregate_licensee_class_id = '141'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXGP - Engineering')
                where "aggregate_licensee_class_id = '143'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Engineering')
                where "aggregate_licensee_class_id = '145'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'HGP - Engineering')
                where "aggregate_licensee_class_id = '146'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'MU - Engineering')
                where "aggregate_licensee_class_id = '148'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXGP - Law & Legal Studies')
                where "aggregate_licensee_class_id = '164'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Law & Legal Studies')
                where "aggregate_licensee_class_id = '166'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'HGP - Law & Legal Studies')
                where "aggregate_licensee_class_id = '167'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'MU - Law & Legal Studies')
                where "aggregate_licensee_class_id = '169'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXGP - Arts & Humanities')
                where "aggregate_licensee_class_id = '171'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Arts & Humanities')
                where "aggregate_licensee_class_id = '173'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'HGP - Arts & Humanities')
                where "aggregate_licensee_class_id = '174'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'MU - Arts & Humanities')
                where "aggregate_licensee_class_id = '176'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXGP - Medical & Health')
                where "aggregate_licensee_class_id = '192'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Medical & Health')
                where "aggregate_licensee_class_id = '194'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'HGP - Medical & Health')
                where "aggregate_licensee_class_id = '195'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'MU - Medical & Health')
                where "aggregate_licensee_class_id = '197'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXGP - Physical Sciences & Mathematics')
                where "aggregate_licensee_class_id = '206'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Physical Sciences & Mathematics')
                where "aggregate_licensee_class_id = '208'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'HGP - Physical Sciences & Mathematics')
                where "aggregate_licensee_class_id = '209'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'MU - Physical Sciences & Mathematics')
                where "aggregate_licensee_class_id = '211'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXGP - Social & Behavioral Sciences')
                where "aggregate_licensee_class_id = '227'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'EXU4 - Social & Behavioral Sciences')
                where "aggregate_licensee_class_id = '229'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'HGP - Social & Behavioral Sciences')
                where "aggregate_licensee_class_id = '230'"
            }
            update(schemaName: dbAppsSchema, tableName: 'df_aggregate_licensee_class') {
                column(name: 'aggregate_licensee_class_name', value: 'MU - Social & Behavioral Sciences')
                where "aggregate_licensee_class_id = '232'"
            }
        }
    }

    changeSet(id: '2020-03-18-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment("B-52335 FDA: Create AACL Scenario: Add usage_period column to df_scenario_usage_filter table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'usage_period', type: 'NUMERIC(4)', remarks: 'The usage period')
        }

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2020-03-19-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-56844 Tech Debt: FDA: update NTS_EXCLUDED status to SCENARIO_EXCLUDED in df_usage table")

        update(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            where "status_ind = 'NTS_EXCLUDED'"
        }

        rollback {
            update(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'status_ind', value: 'NTS_EXCLUDED')
                where "status_ind = 'SCENARIO_EXCLUDED'"
            }
        }
    }

    changeSet(id: '2020-03-20-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-56844 Tech Debt: FDA: rename publication_type_uid columns to df_publication_type_uid " +
                "in df_usage_aacl, df_usage_baseline_aacl tables")

        renameColumn(schemaName: dbAppsSchema,
                tableName: 'df_usage_aacl',
                oldColumnName: 'publication_type_uid',
                newColumnName: 'df_publication_type_uid',
                columnDataType: 'VARCHAR(255)')

        renameColumn(schemaName: dbAppsSchema,
                tableName: 'df_usage_baseline_aacl',
                oldColumnName: 'publication_type_uid',
                newColumnName: 'df_publication_type_uid',
                columnDataType: 'VARCHAR(255)')

        rollback {
            renameColumn(schemaName: dbAppsSchema,
                    tableName: 'df_usage_aacl',
                    oldColumnName: 'df_publication_type_uid',
                    newColumnName: 'publication_type_uid',
                    columnDataType: 'VARCHAR(255)')

            renameColumn(schemaName: dbAppsSchema,
                    tableName: 'df_usage_baseline_aacl',
                    oldColumnName: 'df_publication_type_uid',
                    newColumnName: 'publication_type_uid',
                    columnDataType: 'VARCHAR(255)')
        }
    }

    changeSet(id: '2020-04-03-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-55090 FDA: AACL Calculate Scenario: add volume_weight and value_weight columns to df_usage_aacl table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'volume_weight', type: 'NUMERIC(38,10)', remarks: 'The volume weight')
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'value_weight', type: 'NUMERIC(38,10)', remarks: 'The value weight')
        }

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2020-04-10-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-55090 FDA: AACL Calculate Scenario: add volume_share, value_share and total_share columns to df_usage_aacl table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'volume_share', type: 'NUMERIC(38,10)', remarks: 'The volume share')
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'value_share', type: 'NUMERIC(38,10)', remarks: 'The value share')
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'total_share', type: 'NUMERIC(38,10)', remarks: 'The total share')
        }

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2020-04-30-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-56848 Tech Debt: FDA: update updated_datetime for usages migrated from SC")

        update(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'updated_datetime', value: '2018-06-30')
            where "rh_account_number is null"
        }

        rollback ""
    }

    changeSet(id: '2020-05-05-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-56848 Tech Debt: FDA: classification updates: add index by rh_account_number in df_rightsholder table")

        createIndex(indexName: 'ix_df_rightsholder_rh_account_number', schemaName: dbAppsSchema, tableName: 'df_rightsholder',
                tablespace: dbIndexTablespace) {
            column(name: 'rh_account_number')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_rightsholder_rh_account_number")
        }
    }
}
