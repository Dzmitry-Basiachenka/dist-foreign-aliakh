databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-10-26-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Insert test data for testFindScenarioNamesByUsageBatchId')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'f1aace0a7-c2e9-4a26-8e24-645d8e41d7e4')
            column(name: 'name', value: 'ACL Fund Pool 202112')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'b52b5b2d-77b3-42f1-89a1-85a0c083fd0a')
            column(name: 'name', value: 'ACL Usage Batch 202112')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'c21b99e6-34f0-48c5-b328-d7d8604764c3')
            column(name: 'name', value: 'ACL Grant Set 202112')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '660accb4-a900-4ac0-8c99-52ee594fbe07')
            column(name: 'df_acl_fund_pool_uid', value: 'f1aace0a7-c2e9-4a26-8e24-645d8e41d7e4')
            column(name: 'df_acl_usage_batch_uid', value: 'b52b5b2d-77b3-42f1-89a1-85a0c083fd0a')
            column(name: 'df_acl_grant_set_uid', value: 'c21b99e6-34f0-48c5-b328-d7d8604764c3')
            column(name: 'name', value: 'ACL Scenario 202112 find by Usage Batch id 2')
            column(name: 'period_end_date', value: 202112)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
            column(name: 'copied_from', value: "Copied Scenario Name")
            column(name: 'created_by_user', value: 'auser@copyright.com')
            column(name: 'updated_by_user', value: 'auser@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '60c54d09-56f4-43b8-9b05-b999a616fca0')
            column(name: 'df_acl_fund_pool_uid', value: 'f1aace0a7-c2e9-4a26-8e24-645d8e41d7e4')
            column(name: 'df_acl_usage_batch_uid', value: 'b52b5b2d-77b3-42f1-89a1-85a0c083fd0a')
            column(name: 'df_acl_grant_set_uid', value: 'c21b99e6-34f0-48c5-b328-d7d8604764c3')
            column(name: 'name', value: 'ACL Scenario 202112 find by Usage Batch id 1')
            column(name: 'period_end_date', value: 202112)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
            column(name: 'copied_from', value: "Copied Scenario Name")
            column(name: 'created_by_user', value: 'buser@copyright.com')
            column(name: 'updated_by_user', value: 'buser@copyright.com')
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '818d564a-2db7-431f-8ea1-fe74dfdd0973')
            column(name: 'name', value: 'ACL Usage Batch 202112 2')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: false)
        }

        rollback {
            dbRollback
        }
    }
}
