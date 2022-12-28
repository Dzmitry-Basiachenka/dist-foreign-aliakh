databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-10-05-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("B-73116 FDA: CUP Flag: add content_unit_price_flag column to df_udm_value and df_udm_value_baseline tables")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'content_unit_price_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Content Unit Price Flag') {
                constraints(nullable: false)
            }
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_value_baseline') {
            column(name: 'content_unit_price_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Content Unit Price Flag') {
                constraints(nullable: false)
            }
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_udm_value', columnName: 'content_unit_price_flag')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_udm_value_baseline', columnName: 'content_unit_price_flag')
        }
    }

    changeSet(id: '2022-10-13-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("B-75009 FDA: Liabilities by RH Report: add payee_account_number column to df_acl_share_detail table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'payee_account_number', type: 'NUMERIC(22,0)', remarks: 'The payee account number')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail', columnName: 'payee_account_number')
        }
    }

    changeSet(id: '2022-11-11-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-74821 Tech Debt: FDA: add index by df_acl_grant_set_uid for df_acl_grant_detail table")

        createIndex(indexName: 'ix_df_acl_grant_detail_df_acl_grant_set_uid', schemaName: dbAppsSchema,
                tableName: 'df_acl_grant_detail', tablespace: dbIndexTablespace) {
            column(name: 'df_acl_grant_set_uid')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_acl_grant_detail_df_acl_grant_set_uid")
        }
    }

    changeSet(id: '2022-11-28-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-74822 Tech Debt: FDA: implement insertion order in table df_acl_scenario_audit")

        createSequence(schemaName: dbPublicSchema, sequenceName: 'df_acl_scenario_audit_seq')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_audit') {
            column(name: 'df_acl_scenario_audit_id', type: 'NUMERIC(38)', remarks: 'The sequential identifier of ACL scenario audit action')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_audit', columnName: 'df_acl_scenario_audit_id')
            dropSequence(schemaName: dbPublicSchema, sequenceName: 'df_acl_scenario_audit_seq')
        }
    }

    changeSet(id: '2022-11-29-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("B-74822 Tech Debt: FDA: add index by ccc_event_id for df_usage_archive table")

        createIndex(indexName: 'ix_df_usage_archive_ccc_event_id', schemaName: dbAppsSchema,
                tableName: 'df_usage_archive', tablespace: dbIndexTablespace) {
            column(name: 'ccc_event_id')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_usage_archive_ccc_event_id")
        }
    }

    changeSet(id: '2022-12-08-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-68555 FDA: Load ACLCI usage batch: create df_usage_aclci table")

        createTable(tableName: 'df_usage_aclci', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing specific fields of usages with ACLCI product family') {

            column(name: 'df_usage_aclci_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage')
            column(name: 'coverage_period', type: 'VARCHAR(9)', remarks: 'The coverage period') {
                constraints(nullable: false)
            }
            column(name: 'license_type', type: 'VARCHAR(32)', remarks: 'The license type') {
                constraints(nullable: false)
            }
            column(name: 'reported_media_type', type: 'VARCHAR(16)', remarks: 'The reported media type') {
                constraints(nullable: false)
            }
            column(name: 'media_type_weight', type: 'NUMERIC(38,1)', remarks: 'The media type weight') {
                constraints(nullable: false)
            }
            column(name: 'reported_article', type: 'VARCHAR(1000)', remarks: 'The reported article or chapter title')
            column(name: 'reported_standard_number', type: 'VARCHAR(1000)', remarks: 'The reported_standard_number')
            column(name: 'reported_author', type: 'VARCHAR(1000)', remarks: 'The reported author')
            column(name: 'reported_publisher', type: 'VARCHAR(1000)', remarks: 'The reported publisher')
            column(name: 'reported_publication_date', type: 'VARCHAR(100)', remarks: 'The reported publication date')
            column(name: 'reported_grade', type: 'VARCHAR(32)', remarks: 'The reported grade') {
                constraints(nullable: false)
            }
            column(name: 'grade_group', type: 'VARCHAR(32)', remarks: 'The grade group') {
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

        rollback {
            dropTable(tableName: 'df_usage_aclci', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2022-12-09-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-68555 FDA: Load ACLCI usage batch: add aclci_fields column to df_usage_batch table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'aclci_fields', type: 'JSONB', remarks: 'The fields of ACLCI usage batches')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch', columnName: 'aclci_fields')
        }
    }

    changeSet(id: '2022-12-20-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-68555 FDA: Load ACLCI usage batch: expand coverage_period column")

        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_usage_aclci', columnName: 'coverage_period', newDataType: 'VARCHAR(100)')

        rollback {
            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_usage_aclci', columnName: 'coverage_period', newDataType: 'VARCHAR(9)')
        }
    }

    changeSet(id: '2020-12-28-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment("B-68577 FDA: Load ACLCI fund pool: add aclci_fields column to df_fund_pool")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'aclci_fields', type: 'JSONB', remarks: 'The fields of ACLCI fund pool')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_fund_pool', columnName: 'aclci_fields')
        }
    }
}
