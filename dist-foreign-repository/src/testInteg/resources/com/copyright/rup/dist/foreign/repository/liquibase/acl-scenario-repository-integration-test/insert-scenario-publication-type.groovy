databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-06-30-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testInsertScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '3ef4e970-7a40-4909-b0a3-7a59c8a28e82')
            column(name: 'name', value: 'ACL Fund Pool 202112')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '4a716c5e-ef92-4507-9283-c2be0f622494')
            column(name: 'name', value: 'ACL Usage Batch 202112')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'd6209e69-527b-469e-8ba8-2f83ef4f99aa')
            column(name: 'name', value: 'ACL Grant Set 202112')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '798964c8-e267-46e2-8685-29e84a5ab8c0')
            column(name: 'df_acl_fund_pool_uid', value: '3ef4e970-7a40-4909-b0a3-7a59c8a28e82')
            column(name: 'df_acl_usage_batch_uid', value: '4a716c5e-ef92-4507-9283-c2be0f622494')
            column(name: 'df_acl_grant_set_uid', value: 'd6209e69-527b-469e-8ba8-2f83ef4f99aa')
            column(name: 'name', value: 'ACL Scenario 201712')
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
            column(name: 'df_acl_scenario_uid', value: '798964c8-e267-46e2-8685-29e84a5ab8c0')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '556537fb-0809-45ea-b653-9768d5823111')
            column(name: 'df_acl_scenario_uid', value: '798964c8-e267-46e2-8685-29e84a5ab8c0')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        rollback {
            dbRollback
        }
    }
}
