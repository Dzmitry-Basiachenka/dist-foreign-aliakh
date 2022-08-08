databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-06-30-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testInsertScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '881e39b4-b8dc-4df5-97c1-e6dad801a3b0')
            column(name: 'name', value: 'ACL Fund Pool 202112')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '3b960493-95f6-44b6-bc85-30021a18c65c')
            column(name: 'name', value: 'ACL Usage Batch 202112')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '92362a0f-75a0-4746-8ccf-082041023016')
            column(name: 'name', value: 'ACL Grant Set 202112')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'd18b4933-98c7-45b0-9775-1a86f3525636')
            column(name: 'df_acl_fund_pool_uid', value: '881e39b4-b8dc-4df5-97c1-e6dad801a3b0')
            column(name: 'df_acl_usage_batch_uid', value: '3b960493-95f6-44b6-bc85-30021a18c65c')
            column(name: 'df_acl_grant_set_uid', value: '92362a0f-75a0-4746-8ccf-082041023016')
            column(name: 'name', value: 'ACL Scenario 201512')
            column(name: 'description', value: 'Description')
            column(name: 'period_end_date', value: 202012)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'copied_from', value: "Copied Scenario Name")
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: 'fbcc3113-3f5a-41a0-993e-fcdec1032eaa')
            column(name: 'df_acl_scenario_uid', value: 'd18b4933-98c7-45b0-9775-1a86f3525636')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'fbcc3113-3f5a-41a0-993e-fcdec1032eaa')
            column(name: 'df_acl_scenario_uid', value: 'd18b4933-98c7-45b0-9775-1a86f3525636')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        rollback {
            dbRollback
        }
    }
}
