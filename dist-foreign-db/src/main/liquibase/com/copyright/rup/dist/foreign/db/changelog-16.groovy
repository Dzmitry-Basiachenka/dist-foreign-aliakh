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
}
