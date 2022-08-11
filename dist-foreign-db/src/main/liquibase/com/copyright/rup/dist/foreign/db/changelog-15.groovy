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

    changeSet(id: '2022-06-22-01', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-57783 FDA: Create an ACL Scenario: create df_acl_scenario table")

        createTable(tableName: 'df_acl_scenario', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL scanarios') {

            column(name: 'df_acl_scenario_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_fund_pool_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_usage_batch_uid', type: 'VARCHAR(255)', remarks: 'The identifier of ACL usage batch') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_grant_set_uid', type: 'VARCHAR(255)', remarks: 'The identifier of grant set') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(255)', remarks: 'The name of scenario') {
                constraints(nullable: false)
            }
            column(name: 'period_end_date', type: 'NUMERIC(6)', remarks: 'The period in YYYYMM format') {
                constraints(nullable: false)
            }
            column(name: 'status_ind', type: 'VARCHAR(16)', remarks: 'The status index') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(2000)', remarks: 'The description of scenario')
            column(name: 'license_type', type: 'VARCHAR(10)', remarks: 'The license type') {
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

        addPrimaryKey(schemaName: dbAppsSchema,
                tablespace: dbIndexTablespace,
                tableName: 'df_acl_scenario',
                columnNames: 'df_acl_scenario_uid',
                constraintName: 'pk_df_acl_scenario_uid')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_scenario',
                baseColumnNames: 'df_acl_fund_pool_uid',
                referencedTableName: 'df_acl_fund_pool',
                referencedColumnNames: 'df_acl_fund_pool_uid',
                constraintName: 'fk_df_acl_scenario_2_df_acl_fund_pool')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_scenario',
                baseColumnNames: 'df_acl_grant_set_uid',
                referencedTableName: 'df_acl_grant_set',
                referencedColumnNames: 'df_acl_grant_set_uid',
                constraintName: 'fk_df_acl_scenario_2_df_acl_grant_set')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_scenario',
                baseColumnNames: 'df_acl_usage_batch_uid',
                referencedTableName: 'df_acl_usage_batch',
                referencedColumnNames: 'df_acl_usage_batch_uid',
                constraintName: 'fk_df_df_acl_scenario_2_df_acl_usage_batch')

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2022-06-22-02', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-57783 FDA: Create an ACL Scenario: create df_acl_scenario_detail table")

        createTable(tableName: 'df_acl_scenario_detail', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL scanario details') {

            column(name: 'df_acl_scenario_detail_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario deetail') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_scenario_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario') {
                constraints(nullable: false)
            }
            column(name: 'period_end_date', type: 'NUMERIC(6)', remarks: 'The period in YYYYMM format') {
                constraints(nullable: false)
            }
            column(name: 'original_detail_id', type: 'VARCHAR(50)', remarks: 'The original detail id') {
                constraints(nullable: false)
            }
            column(name: 'wr_wrk_inst', type: 'NUMERIC(15)', remarks: 'The Wr Wrk Inst') {
                constraints(nullable: false)
            }
            column(name: 'system_title', type: 'VARCHAR(2000)', remarks: 'The system title') {
                constraints(nullable: false)
            }
            column(name: 'detail_licensee_class_id', type: 'INTEGER', remarks: 'The identifier of Detail Licensee Class') {
                constraints(nullable: false)
            }
            column(name: 'publication_type_uid', type: 'VARCHAR(255)', remarks: 'The identifier of Publication Type') {
                constraints(nullable: false)
            }
            column(name: 'content_unit_price', type: 'NUMERIC (38,10)', remarks: 'The content unit price') {
                constraints(nullable: false)
            }
            column(name: 'usage_quantity', type: 'NUMERIC(38)', remarks: 'The usage quantity') {
                constraints(nullable: false)
            }
            column(name: 'usage_age_weight', type: 'numeric(10,5)', remarks: 'The usage age weight') {
                constraints(nullable: false)
            }
            column(name: 'weighted_copies', type: 'NUMERIC (38,10)', remarks: 'The weighted copies') {
                constraints(nullable: false)
            }
            column(name: 'survey_country', type: 'VARCHAR(100)', remarks: 'The survey country') {
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
                tableName: 'df_acl_scenario_detail',
                columnNames: 'df_acl_scenario_detail_uid',
                constraintName: 'pk_df_acl_scenario_detail_uid')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_scenario_detail',
                baseColumnNames: 'df_acl_scenario_uid',
                referencedTableName: 'df_acl_scenario',
                referencedColumnNames: 'df_acl_scenario_uid',
                constraintName: 'fk_df_acl_scenario_detail_2_df_acl_scenario')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_scenario_detail',
                baseColumnNames: 'detail_licensee_class_id',
                referencedTableName: 'df_detail_licensee_class',
                referencedColumnNames: 'detail_licensee_class_id',
                constraintName: 'fk_df_acl_scenario_detail_2_df_detail_licensee_class')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_scenario_detail',
                baseColumnNames: 'publication_type_uid',
                referencedTableName: 'df_publication_type',
                referencedColumnNames: 'df_publication_type_uid',
                constraintName: 'fk_df_acl_scenario_detail_2_df_publication_type')

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2022-06-22-03', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-57783 FDA: Create an ACL Scenario: create df_acl_share_detail table")

        createTable(tableName: 'df_acl_share_detail', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL scanario share details') {

            column(name: 'df_acl_share_detail_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario deetail') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_scenario_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_scenario_detail_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario deetail') {
                constraints(nullable: false)
            }
            column(name: 'type_of_use', type: 'VARCHAR(128)', remarks: 'The type of use') {
                constraints(nullable: false)
            }
            column(name: 'rh_account_number', type: 'NUMERIC(22)', remarks: 'The rightsholder account number') {
                constraints(nullable: false)
            }
            column(name: 'aggregate_licensee_class_id', type: 'INTEGER', remarks: 'The identifier of Aggregate Licensee Class') {
                constraints(nullable: false)
            }
            column(name: 'volume_weight', type: 'NUMERIC (38,10)', remarks: 'The volume weight')
            column(name: 'value_weight', type: 'NUMERIC (38,10)', remarks: 'The value weight')
            column(name: 'value_weight_denominator', type: 'NUMERIC (38,10)', remarks: 'The value weight denominator')
            column(name: 'volume_weight_denominator', type: 'NUMERIC (38,10)', remarks: 'The volume weight denominator')
            column(name: 'value_share', type: 'NUMERIC (38,10)', remarks: 'The value share')
            column(name: 'volume_share', type: 'NUMERIC (38,10)', remarks: 'The volume share')
            column(name: 'net_amount', type: 'NUMERIC (38,10)', remarks: 'The net amount')
            column(name: 'gross_amount', type: 'NUMERIC (38,10)', remarks: 'TThe gross amount')
            column(name: 'service_fee_amount', type: 'NUMERIC (38,10)', remarks: 'The service fee amount')
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
                tableName: 'df_acl_share_detail',
                columnNames: 'df_acl_share_detail_uid',
                constraintName: 'pk_acl_share_detail_uid')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_share_detail',
                baseColumnNames: 'df_acl_scenario_uid',
                referencedTableName: 'df_acl_scenario',
                referencedColumnNames: 'df_acl_scenario_uid',
                constraintName: 'fk_df_acl_share_detail_2_df_acl_scenario')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_share_detail',
                baseColumnNames: 'aggregate_licensee_class_id',
                referencedTableName: 'df_aggregate_licensee_class',
                referencedColumnNames: 'aggregate_licensee_class_id',
                constraintName: 'fk_df_acl_share_detail_2_df_aggregate_licensee_class')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_share_detail',
                baseColumnNames: 'df_acl_scenario_detail_uid',
                referencedTableName: 'df_acl_scenario_detail',
                referencedColumnNames: 'df_acl_scenario_detail_uid',
                constraintName: 'fk_df_acl_share_detail_2_df_acl_scenario_detail')

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2022-06-22-04', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('B-57800 FDA: Scenario Tab modifications for ACL Metadata Panel: create df_acl_scenario_audit table')

        createTable(tableName: 'df_acl_scenario_audit', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL scenario audit actions') {
            column(name: 'df_acl_scenario_audit_uid', type: 'VARCHAR(255)', remarks: 'The identifier of ACL scenario audit action') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_scenario_uid', type: 'VARCHAR(255)', remarks: 'The identifier of ACL scenario') {
                constraints(nullable: false)
            }
            column(name: 'action_type_ind', type: 'VARCHAR(32)', remarks: 'Scenario action type index') {
                constraints(nullable: false)
            }
            column(name: 'action_reason', type: 'VARCHAR(1024)', remarks: 'Scenario action reason')
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
                tableName: 'df_acl_scenario_audit',
                columnNames: 'df_acl_scenario_audit_uid',
                constraintName: 'pk_df_acl_scenario_audit')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_scenario_audit',
                baseColumnNames: 'df_acl_scenario_uid',
                referencedTableName: 'df_acl_scenario',
                referencedColumnNames: 'df_acl_scenario_uid',
                constraintName: 'fk_df_acl_scenario_2_df_acl_scenario_audit')

        createIndex(schemaName: dbAppsSchema,
                tablespace: dbIndexTablespace,
                tableName: 'df_acl_scenario_audit',
                indexName: 'ix_df_acl_scenario_audit_df_acl_scenario_uid') {
            column(name: 'df_acl_scenario_uid')
        }

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2022-06-29-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-57783 FDA: Create an ACL Scenario: create df_acl_scenario_usage_age_weight table")

        createTable(tableName: 'df_acl_scenario_usage_age_weight', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL scenario usage age weights') {

            column(name: 'df_acl_scenario_usage_age_weight_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario usage age weight') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_scenario_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario') {
                constraints(nullable: false)
            }
            column(name: 'period_prior', type: 'INTEGER', remarks: 'Prior period') {
                constraints(nullable: false)
            }
            column(name: 'weight', type: 'NUMERIC (10, 5)', remarks: 'Value of age weight') {
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
                tableName: 'df_acl_scenario_usage_age_weight',
                columnNames: 'df_acl_scenario_usage_age_weight_uid',
                constraintName: 'pk_df_acl_scenario_usage_age_weight_uid')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_scenario_usage_age_weight',
                baseColumnNames: 'df_acl_scenario_uid',
                referencedTableName: 'df_acl_scenario',
                referencedColumnNames: 'df_acl_scenario_uid',
                constraintName: 'fk_df_acl_scenario_usage_age_weights_2_df_acl_scenario')

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2022-06-29-01', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-57783 FDA: Create an ACL Scenario: create df_acl_scenario_licensee_class table")

        createTable(tableName: 'df_acl_scenario_licensee_class', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL scenario licensee classes') {

            column(name: 'df_acl_scenario_licensee_class_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario licensee classes') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_scenario_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario') {
                constraints(nullable: false)
            }
            column(name: 'detail_licensee_class_id', type: 'INTEGER', remarks: 'The identifier of Detail Licensee Class') {
                constraints(nullable: false)
            }
            column(name: 'aggregate_licensee_class_id', type: 'INTEGER', remarks: 'Aggregate Licensee Class Id') {
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
                tableName: 'df_acl_scenario_licensee_class',
                columnNames: 'df_acl_scenario_licensee_class_uid',
                constraintName: 'pk_df_acl_scenario_licensee_class_uid')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_scenario_licensee_class',
                baseColumnNames: 'df_acl_scenario_uid',
                referencedTableName: 'df_acl_scenario',
                referencedColumnNames: 'df_acl_scenario_uid',
                constraintName: 'fk_df_acl_scenario_licensee_classes_2_df_acl_scenario')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_scenario_licensee_class',
                baseColumnNames: 'detail_licensee_class_id',
                referencedTableName: 'df_detail_licensee_class',
                referencedColumnNames: 'detail_licensee_class_id',
                constraintName: 'fk_df_acl_scenario_licensee_class_2_df_detail_licensee_class_id')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_scenario_licensee_class',
                baseColumnNames: 'aggregate_licensee_class_id',
                referencedTableName: 'df_aggregate_licensee_class',
                referencedColumnNames: 'aggregate_licensee_class_id',
                constraintName: 'fk_df_acl_scenario_licensee_class_2_df_aggregate_licensee_class')

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2022-06-29-02', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-57783 FDA: Create an ACL Scenario: create df_acl_scenario_pub_type_weight table")

        createTable(tableName: 'df_acl_scenario_pub_type_weight', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL scenario publication type weight') {

            column(name: 'df_acl_scenario_pub_type_weight_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario publication type') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_scenario_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario') {
                constraints(nullable: false)
            }
            column(name: 'df_publication_type_uid', type: 'VARCHAR(255)', remarks: 'Publication Type Id') {
                constraints(nullable: false)
            }
            column(name: 'weight', type: 'NUMERIC(10,2)', remarks: 'Publication Type Weight') {
                constraints(nullable: false)
            }
            column(name: 'period', type: 'NUMERIC(6)', remarks: 'The period in YYYYMM format')
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
                tableName: 'df_acl_scenario_pub_type_weight',
                columnNames: 'df_acl_scenario_pub_type_weight_uid',
                constraintName: 'pk_df_acl_scenario_pub_type_weight')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_scenario_pub_type_weight',
                baseColumnNames: 'df_acl_scenario_uid',
                referencedTableName: 'df_acl_scenario',
                referencedColumnNames: 'df_acl_scenario_uid',
                constraintName: 'fk_df_acl_scenario_pub_type_weights_2_df_acl_scenario')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_scenario_pub_type_weight',
                baseColumnNames: 'df_publication_type_uid',
                referencedTableName: 'df_publication_type',
                referencedColumnNames: 'df_publication_type_uid',
                constraintName: 'fk_df_acl_scenario_pub_type_weights_2_df_publication_type')

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2022-07-12-02', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-74353 FDA: Saving Pub Type Weights: create df_acl_pub_type_weight_history table")

        createTable(tableName: 'df_acl_pub_type_weight_history', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL history publication type weights') {

            column(name: 'df_acl_pub_type_weight_history_uid', type: 'VARCHAR(255)', remarks: 'The identifier of history publication type') {
                constraints(nullable: false)
            }
            column(name: 'df_publication_type_uid', type: 'VARCHAR(255)', remarks: 'Publication Type Id') {
                constraints(nullable: false)
            }
            column(name: 'weight', type: 'NUMERIC(10,2)', remarks: 'Publication Type Weight') {
                constraints(nullable: false)
            }
            column(name: 'period', type: 'NUMERIC(6)', remarks: 'The period in YYYYMM format'){
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
                tableName: 'df_acl_pub_type_weight_history',
                columnNames: 'df_acl_pub_type_weight_history_uid',
                constraintName: 'pk_df_acl_pub_type_weight_history')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_pub_type_weight_history',
                baseColumnNames: 'df_publication_type_uid',
                referencedTableName: 'df_publication_type',
                referencedColumnNames: 'df_publication_type_uid',
                constraintName: 'fk_df_acl_pub_type_weight_historys_2_df_publication_type')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_pub_type_weight_history') {
            column(name: 'df_acl_pub_type_weight_history_uid', value: 'a104b6d3-8431-4173-a50c-96d46f19a27b')
            column(name: 'df_publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_pub_type_weight_history') {
            column(name: 'df_acl_pub_type_weight_history_uid', value: 'f499b5d8-09ad-4d92-8283-8b35fb5a98a1')
            column(name: 'df_publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_pub_type_weight_history') {
            column(name: 'df_acl_pub_type_weight_history_uid', value: '29a12014-14ea-4f91-994e-c427da1a4adf')
            column(name: 'df_publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: '1.9')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_pub_type_weight_history') {
            column(name: 'df_acl_pub_type_weight_history_uid', value: '4dbd8c92-a74c-404b-9129-5cc1809615fb')
            column(name: 'df_publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: '3.5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_pub_type_weight_history') {
            column(name: 'df_acl_pub_type_weight_history_uid', value: 'c17a6e4e-6d21-45a1-9a18-c8e541247890')
            column(name: 'df_publication_type_uid', value: 'ad8df236-5200-4acf-be55-cf82cd342f14')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_pub_type_weight_history') {
            column(name: 'df_acl_pub_type_weight_history_uid', value: '97a5281d-9a04-49d7-b66e-d9034a10901c')
            column(name: 'df_publication_type_uid', value: '34574f62-7922-48b9-b798-73bf5c3163da')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: '1.3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_pub_type_weight_history') {
            column(name: 'df_acl_pub_type_weight_history_uid', value: 'c47318d0-a8a6-40ce-98e2-e96fc2afb093')
            column(name: 'df_publication_type_uid', value: '9c5c6797-a861-44ae-ada9-438acb20334d')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_pub_type_weight_history') {
            column(name: 'df_acl_pub_type_weight_history_uid', value: '8a7311ee-2050-4f95-b1cb-09842100e81a')
            column(name: 'df_publication_type_uid', value: 'c0db0a37-9854-495f-99b7-1e3486c232cb')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: '1.9')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_pub_type_weight_history') {
            column(name: 'df_acl_pub_type_weight_history_uid', value: '07b9b38b-e2ca-4d84-b5a7-72bf0e054858')
            column(name: 'df_publication_type_uid', value: '0a4bcf78-95cb-445e-928b-e48ad12acfd2')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: '1.9')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_pub_type_weight_history') {
            column(name: 'df_acl_pub_type_weight_history_uid', value: '3a9ef03c-6010-4d32-b5b0-bab4f4d2a4ed')
            column(name: 'df_publication_type_uid', value: '56e31ea2-2f32-43a5-a0a7-9b1ecb1e73fe')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: '2.7')
        }

        rollback {
            dropTable(tableName: 'df_acl_pub_type_weight_history', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2022-07-13-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("B-57798 FDA: View scenario specific usage details by RH for ACL: add reported_type_of_use, " +
                "content_unit_price_flag, price, price_flag, content, content_flag columns to df_acl_scenario_detail " +
                "and df_acl_usage tables")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'reported_type_of_use', type: 'VARCHAR(128)', remarks: 'The reported type of use')
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'content_unit_price_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Content Unit Price Flag') {
                constraints(nullable: false)
            }
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'price', type: 'NUMERIC(38,10)', remarks: 'Price')
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'price_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Price Flag') {
                constraints(nullable: false)
            }
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'content', type: 'NUMERIC (38, 10)', remarks: 'Content')
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'content_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Content Flag') {
                constraints(nullable: false)
            }
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'reported_type_of_use', type: 'VARCHAR(128)', remarks: 'The reported type of use')
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'content_unit_price_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Content Unit Price Flag') {
                constraints(nullable: false)
            }
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'price', type: 'NUMERIC(38,10)', remarks: 'Price')
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'price_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Price Flag') {
                constraints(nullable: false)
            }
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'content', type: 'NUMERIC (38, 10)', remarks: 'Content')
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'content_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Content Flag') {
                constraints(nullable: false)
            }
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail', columnName: 'reported_type_of_use')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail', columnName: 'content_unit_price_flag')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail', columnName: 'price')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail', columnName: 'price_flag')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail', columnName: 'content')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail', columnName: 'content_flag')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage', columnName: 'reported_type_of_use')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage', columnName: 'content_unit_price_flag')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage', columnName: 'price')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage', columnName: 'price_flag')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage', columnName: 'content')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage', columnName: 'content_flag')
        }
    }

    changeSet(id: '2022-07-13-01', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-57795 FDA: Calculate ACL Scenario: add pub_type_weight column to df_acl_scenario_detail table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'pub_type_weight', type: 'NUMERIC(10,2)', remarks: 'Publication Type Weight')
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'detail_share', type: 'NUMERIC (38,10)', remarks: 'Detail share')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail', columnName: 'pub_type_weight')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail', columnName: 'detail_share')
        }
    }

    changeSet(id: '2022-07-26-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-57795 FDA: Calculate ACL Scenario: drop foreign key from df_acl_share_detail table to df_acl_scenario_detail")

        dropForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
            baseTableName: 'df_acl_share_detail',
            constraintName: 'fk_df_acl_share_detail_2_df_acl_scenario_detail')

        rollback {
            addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_acl_share_detail',
                baseColumnNames: 'df_acl_scenario_detail_uid',
                referencedTableName: 'df_acl_scenario_detail',
                referencedColumnNames: 'df_acl_scenario_detail_uid',
                constraintName: 'fk_df_acl_share_detail_2_df_acl_scenario_detail')
        }
    }

    changeSet(id: '2022-08-05-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-74687 FDA: Fine tune performance for ACL components: add index by df_acl_scenario_uid to scenario " +
                "related tables")

        createIndex(indexName: 'ix_df_acl_scenario_detail_df_acl_scenario_uid', schemaName: dbAppsSchema,
                tableName: 'df_acl_scenario_detail', tablespace: dbIndexTablespace) {
            column(name: 'df_acl_scenario_uid')
        }
        createIndex(indexName: 'ix_df_acl_share_detail_df_acl_scenario_uid', schemaName: dbAppsSchema,
                tableName: 'df_acl_share_detail', tablespace: dbIndexTablespace) {
            column(name: 'df_acl_scenario_uid')
        }
        createIndex(indexName: 'ix_df_acl_scenario_licensee_class_df_acl_scenario_uid', schemaName: dbAppsSchema,
                tableName: 'df_acl_scenario_licensee_class', tablespace: dbIndexTablespace) {
            column(name: 'df_acl_scenario_uid')
        }
        createIndex(indexName: 'ix_df_acl_scenario_pub_type_weight_df_acl_scenario_uid', schemaName: dbAppsSchema,
                tableName: 'df_acl_scenario_pub_type_weight', tablespace: dbIndexTablespace) {
            column(name: 'df_acl_scenario_uid')
        }
        createIndex(indexName: 'ix_df_acl_scenario_usage_age_weight_df_acl_scenario_uid', schemaName: dbAppsSchema,
                tableName: 'df_acl_scenario_usage_age_weight', tablespace: dbIndexTablespace) {
            column(name: 'df_acl_scenario_uid')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_acl_scenario_detail_df_acl_scenario_uid")
            sql("drop index ${dbAppsSchema}.ix_df_acl_share_detail_df_acl_scenario_uid")
            sql("drop index ${dbAppsSchema}.ix_df_acl_scenario_licensee_class_df_acl_scenario_uid")
            sql("drop index ${dbAppsSchema}.ix_df_acl_scenario_pub_type_weight_df_acl_scenario_uid")
            sql("drop index ${dbAppsSchema}.ix_df_acl_scenario_usage_age_weight_df_acl_scenario_uid")
        }
    }

    changeSet(id: '2022-08-05-01', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-72122 FDA: Copy ACL Scenario: add copied_from column to df_acl_scenario table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'copied_from', type: 'VARCHAR(255)', remarks: 'Copied From')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario', columnName: 'copied_from')
        }
    }

    changeSet(id: '2022-08-10-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-57780 FDA: Migrate historical ACL baseline Usage & Value Data from Sharecalc: " +
                "drop not null constraint from rh_account_number column in df_udm_value table")

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_udm_value',
                columnName: 'rh_account_number', columnDataType: 'NUMERIC(22)')

        rollback ""
    }

    changeSet(id: '2022-08-11-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("DIST-305 FDA UAT: Change the calculation from Number of reported copies to Annualized Copies: " +
                "add type_of_use column to df_acl_scenario_detail")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'type_of_use', type: 'VARCHAR(128)', remarks: 'The type of use')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail', columnName: 'type_of_use')
        }
    }

    changeSet(id: '2022-08-11-01', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("DIST-305 FDA UAT: Change the calculation from Number of reported copies to Annualized Copies: " +
                "rename usage_quantity column to number_of_copies in df_acl_scenario_detail table")

        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail', columnName: 'usage_quantity', newDataType: 'numeric(38,5)')

        renameColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail', oldColumnName: 'usage_quantity',
            newColumnName: 'number_of_copies', columnDataType: 'NUMERIC(38,5)')

        rollback {

            renameColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail', oldColumnName: 'number_of_copies',
                newColumnName: 'usage_quantity', columnDataType: 'NUMERIC(38, 5)')

            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail', columnName: 'usage_quantity', newDataType: 'numeric(38)')
        }
    }
}
