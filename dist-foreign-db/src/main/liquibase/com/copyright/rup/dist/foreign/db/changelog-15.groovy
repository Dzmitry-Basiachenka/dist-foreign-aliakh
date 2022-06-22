databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-01-26-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-71751 FDA: Create and View ACL Grant Set: create df_acl_grant_set, df_acl_grant_detail tables")

        createTable(tableName: 'df_acl_grant_set', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL grant sets') {

            column(name: 'df_acl_grant_set_uid', type: 'VARCHAR(255)', remarks: 'The identifier of grant set') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(255)', remarks: 'The name of grant set') {
                constraints(nullable: false)
            }
            column(name: 'grant_period', type: 'NUMERIC(6)', remarks: 'The grant period in YYYYMM format') {
                constraints(nullable: false)
            }
            column(name: 'periods', type: 'JSONB', remarks: 'The periods') {
                constraints(nullable: false)
            }
            column(name: 'license_type', type: 'VARCHAR(128)', remarks: 'The license type') {
                constraints(nullable: false)
            }
            column(name: 'is_editable', type: 'BOOLEAN', remarks: 'The editable flag') {
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
                tableName: 'df_acl_grant_set',
                columnNames: 'df_acl_grant_set_uid',
                constraintName: 'pk_df_acl_grant_set_uid')

        addUniqueConstraint(schemaName: dbAppsSchema,
                tableName: 'df_acl_grant_set',
                columnNames: 'name',
                constraintName: 'uk_df_acl_grant_set_name')

        createTable(tableName: 'df_acl_grant_detail', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL grant details') {

            column(name: 'df_acl_grant_detail_uid', type: 'VARCHAR(255)', remarks: 'The identifier of grant detail') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_grant_set_uid', type: 'VARCHAR(255)', remarks: 'The identifier of grant set') {
                constraints(nullable: false)
            }
            column(name: 'grant_status', type: 'VARCHAR(128)', remarks: 'The grant status') {
                constraints(nullable: false)
            }
            column(name: 'type_of_use', type: 'VARCHAR(128)', remarks: 'The type of use') {
                constraints(nullable: false)
            }
            column(name: 'type_of_use_status', type: 'VARCHAR(128)', remarks: 'The type of use status')
            column(name: 'wr_wrk_inst', type: 'NUMERIC(15)', remarks: 'The Wr Wrk Inst') {
                constraints(nullable: false)
            }
            column(name: 'system_title', type: 'VARCHAR(2000)', remarks: 'The system title')
            column(name: 'rh_account_number', type: 'NUMERIC(22)', remarks: 'The rightsholder account number') {
                constraints(nullable: false)
            }
            column(name: 'is_eligible', type: 'BOOLEAN', defaultValue: true, remarks: 'The eligible flag') {
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
                tableName: 'df_acl_grant_detail',
                columnNames: 'df_acl_grant_detail_uid',
                constraintName: 'pk_df_acl_grant_detail_uid')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_grant_detail',
                baseColumnNames: 'df_acl_grant_set_uid',
                referencedTableName: 'df_acl_grant_set',
                referencedColumnNames: 'df_acl_grant_set_uid',
                constraintName: 'fk_df_acl_grant_detail_2_df_acl_grant_set')

        rollback {
            dropTable(tableName: 'df_acl_grant_detail', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_acl_grant_set', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2022-02-02-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-70074 FDA: Tech Debt: add database indexes by Wr Wrk Inst and Period for UDM baseline value table")

        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_udm_value_baseline', indexName: 'ix_df_udm_value_baseline_period') {
            column(name: 'period')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_udm_value_baseline', indexName: 'ix_df_udm_value_baseline_wr_wrk_inst') {
            column(name: 'wr_wrk_inst')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_udm_value_baseline_period")
            sql("drop index ${dbAppsSchema}.ix_df_udm_value_baseline_wr_wrk_inst")
        }
    }

    changeSet(id: '2022-03-30-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-71795 FDA: Create ACL usage batch: create df_acl_usage_batch, df_acl_usage tables")

        createTable(tableName: 'df_acl_usage_batch', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL usage batches') {

            column(name: 'df_acl_usage_batch_uid', type: 'VARCHAR(255)', remarks: 'The identifier of ACL usage batch') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(255)', remarks: 'The name of ACL usage batch') {
                constraints(nullable: false)
            }
            column(name: 'distribution_period', type: 'NUMERIC(6)', remarks: 'The distribution period in YYYYMM format') {
                constraints(nullable: false)
            }
            column(name: 'periods', type: 'JSONB', remarks: 'The periods') {
                constraints(nullable: false)
            }
            column(name: 'is_editable', type: 'BOOLEAN', remarks: 'The editable flag') {
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
                tableName: 'df_acl_usage_batch',
                columnNames: 'df_acl_usage_batch_uid',
                constraintName: 'pk_df_acl_usage_batch_uid')

        addUniqueConstraint(schemaName: dbAppsSchema,
                tableName: 'df_acl_usage_batch',
                columnNames: 'name',
                constraintName: 'uk_df_acl_usage_batch_name')

        createTable(tableName: 'df_acl_usage', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL usages') {

            column(name: 'df_acl_usage_uid', type: 'VARCHAR(255)', remarks: 'The identifier of ACL usage') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_usage_batch_uid', type: 'VARCHAR(255)', remarks: 'The identifier of ACL usage batch') {
                constraints(nullable: false)
            }
            column(name: 'usage_origin', type: 'VARCHAR(20)', remarks: 'The usage origin') {
                constraints(nullable: false)
            }
            column(name: 'channel', type: 'VARCHAR(15)', remarks: 'The channel') {
                constraints(nullable: false)
            }
            column(name: 'period', type: 'NUMERIC(6)', remarks: 'The period in YYYYMM format') {
                constraints(nullable: false)
            }
            column(name: 'original_detail_id', type: 'VARCHAR(50)', remarks: 'The original detail id') {
                constraints(nullable: false)
            }
            column(name: 'wr_wrk_inst', type: 'NUMERIC(15)', remarks: 'The Wr Wrk Inst') {
                constraints(nullable: false)
            }
            column(name: 'system_title', type: 'VARCHAR(2000)', remarks: 'The system title')
            column(name: 'detail_licensee_class_id', type: 'INTEGER', remarks: 'The identifier of Detail Licensee Class')
            column(name: 'survey_country', type: 'VARCHAR(100)', remarks: 'The survey country') {
                constraints(nullable: false)
            }
            column(name: 'publication_type_uid', type: 'VARCHAR(255)', remarks: 'The identifier of Publication Type')
            column(name: 'content_unit_price', type: 'NUMERIC (38,10)', remarks: 'The content unit price')
            column(name: 'type_of_use', type: 'VARCHAR(128)', remarks: 'The type of use') {
                constraints(nullable: false)
            }
            column(name: 'annualized_copies', type: 'NUMERIC(38,5)', remarks: 'The number of annualized copies')
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
                tableName: 'df_acl_usage',
                columnNames: 'df_acl_usage_uid',
                constraintName: 'pk_df_acl_usage_uid')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_usage',
                baseColumnNames: 'df_acl_usage_batch_uid',
                referencedTableName: 'df_acl_usage_batch',
                referencedColumnNames: 'df_acl_usage_batch_uid',
                constraintName: 'fk_df_acl_usage_2_df_acl_usage_batch')

        addUniqueConstraint(schemaName: dbAppsSchema,
                tableName: 'df_acl_usage',
                columnNames: 'original_detail_id',
                constraintName: 'uk_df_acl_usage_original_detail_id')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_usage',
                baseColumnNames: 'detail_licensee_class_id',
                referencedTableName: 'df_detail_licensee_class',
                referencedColumnNames: 'detail_licensee_class_id',
                constraintName: 'fk_df_acl_usage_2_df_detail_licensee_class')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_usage',
                baseColumnNames: 'publication_type_uid',
                referencedTableName: 'df_publication_type',
                referencedColumnNames: 'df_publication_type_uid',
                constraintName: 'fk_df_acl_usage_2_df_publication_type')

        rollback {
            dropTable(tableName: 'df_acl_usage', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_acl_usage_batch', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2022-04-06-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-71795 FDA: Create ACL usage batch: drop unique constraint for column df_acl_usage.original_detail_id")

        dropUniqueConstraint(schemaName: dbAppsSchema,
                tableName: 'df_acl_usage',
                constraintName: 'uk_df_acl_usage_original_detail_id')
        rollback {
            addUniqueConstraint(schemaName: dbAppsSchema,
                    tableName: 'df_acl_usage',
                    columnNames: 'original_detail_id',
                    constraintName: 'uk_df_acl_usage_original_detail_id')
        }
    }

    changeSet(id: '2022-04-06-01', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-71754 FDA: Uploading works to a Grant set: add manual_upload_flag column to df_acl_grant_detail tables")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'manual_upload_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Manual Upload Flag') {
                constraints(nullable: false)
            }
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail', columnName: 'manual_upload_flag')
        }
    }

    changeSet(id: '2022-04-10-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-71795 FDA: Create ACL usage batch: add not null constrains to " +
                "columns publication_type_uid, detail_licensee_class_id in table df_acl_usage")

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                columnName: 'publication_type_uid', columnDataType: 'VARCHAR(255)')
        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                columnName: 'detail_licensee_class_id', columnDataType: 'INTEGER')

        rollback {
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                    columnName: 'publication_type_uid', columnDataType: 'VARCHAR(255)')
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                    columnName: 'detail_licensee_class_id', columnDataType: 'INTEGER')
        }
    }

    changeSet(id: '2022-04-21-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-71795 FDA: Create ACL usage batch: add not null constrains to " +
                "columns system_title, content_unit_price, annualized_copies in table df_acl_usage")

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                columnName: 'system_title', columnDataType: 'VARCHAR(2000)')
        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                columnName: 'content_unit_price', columnDataType: 'NUMERIC (38,10)')
        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                columnName: 'annualized_copies', columnDataType: 'NUMERIC(38,5)')

        rollback {
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                    columnName: 'system_title', columnDataType: 'VARCHAR(2000)')
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                    columnName: 'content_unit_price', columnDataType: 'NUMERIC (38,10)')
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                    columnName: 'annualized_copies', columnDataType: 'NUMERIC(38,5)')
        }
    }

    changeSet(id: '2022-04-26-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-64276 FDA: Fund Pool creation: create df_acl_fund_pool, df_acl_fund_pool_detail tables")

        createTable(tableName: 'df_acl_fund_pool', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL fund pools') {

            column(name: 'df_acl_fund_pool_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(255)', remarks: 'The name of fund pool') {
                constraints(nullable: false)
            }
            column(name: 'period', type: 'NUMERIC(6)', remarks: 'The fund pool period in YYYYMM format') {
                constraints(nullable: false)
            }
            column(name: 'license_type', type: 'VARCHAR(10)', remarks: 'The license type') {
                constraints(nullable: false)
            }
            column(name: 'is_manual', type: 'BOOLEAN', remarks: 'The manual upload flag') {
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
                tableName: 'df_acl_fund_pool',
                columnNames: 'df_acl_fund_pool_uid',
                constraintName: 'pk_df_acl_fund_pool_uid')

        addUniqueConstraint(schemaName: dbAppsSchema,
                tableName: 'df_acl_fund_pool',
                columnNames: 'name',
                constraintName: 'uk_df_acl_fund_pool_name')

        createTable(tableName: 'df_acl_fund_pool_detail', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL fund pool details') {

            column(name: 'df_acl_fund_pool_detail_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool detail') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_fund_pool_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool')
            column(name: 'detail_licensee_class_id', type: 'INTEGER', remarks: 'The identifier of Detail Licensee Class') {
                constraints(nullable: false)
            }
            column(name: 'license_type', type: 'VARCHAR(10)', remarks: 'The license type') {
                constraints(nullable: false)
            }
            column(name: 'type_of_use', type: 'VARCHAR(10)', remarks: 'The type of use') {
                constraints(nullable: false)
            }
            column(name: 'net_amount', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The net amount') {
                constraints(nullable: false)
            }
            column(name: 'gross_amount', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The gross amount') {
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
                tableName: 'df_acl_fund_pool_detail',
                columnNames: 'df_acl_fund_pool_detail_uid',
                constraintName: 'pk_df_acl_fund_pool_detail_uid')

        rollback {
            dropTable(tableName: 'df_acl_fund_pool_detail', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_acl_fund_pool', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2022-05-11-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle: add is_ldmt column to df_acl_fund_pool_detail tables")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'is_ldmt', type: 'BOOLEAN', defaultValue: false, remarks: 'The LDMT flag') {
                constraints(nullable: false)
            }
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail', columnName: 'is_ldmt')
        }
    }

    changeSet(id: '2022-05-20-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-69208 FDA: UAT Feedback Adjust Usage Batch Creation: drop not null constrains from " +
                "content_unit_price, publication_type_uid columns in table df_acl_usage")

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                columnName: 'content_unit_price', columnDataType: 'NUMERIC (38,10)')
        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                columnName: 'publication_type_uid', columnDataType: 'VARCHAR(255)')

        rollback {
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                    columnName: 'content_unit_price', columnDataType: 'NUMERIC (38,10)')
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_acl_usage',
                    columnName: 'publication_type_uid', columnDataType: 'VARCHAR(255)')
        }
    }

    changeSet(id: '2022-06-13-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-72776 FDA: Tech Debt: add database index by Period for AACL usage table")

        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_usage_aacl', indexName: 'ix_df_usage_aacl_period') {
            column(name: 'usage_period')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_usage_aacl_period")
        }
    }

    changeSet(id: '2022-06-22-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-57783 FDA: Create an ACL Scenario: add quantity column to df_acl_usage table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'quantity', type: 'NUMERIC(38)', remarks: 'The quantity', defaultValue: '1') {
                constraints(nullable: false)
            }
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage', columnName: 'quantity')
        }
    }
}
