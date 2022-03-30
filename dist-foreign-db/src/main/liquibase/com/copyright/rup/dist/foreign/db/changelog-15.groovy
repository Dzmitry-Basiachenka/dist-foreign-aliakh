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
}
