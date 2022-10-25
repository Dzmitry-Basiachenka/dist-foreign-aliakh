databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-10-26-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testFindAclScenariosByStatuses')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '1ba51f2d-50f4-44a3-91c2-3a3359f498f2')
            column(name: 'name', value: 'ACL Fund Pool 202112')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '4bfcf746-ea11-4d62-bbc5-bf39f81fb203')
            column(name: 'name', value: 'ACL Usage Batch 202112')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '1be8d8db-b77a-43cd-a3ce-0e94b1887de0')
            column(name: 'name', value: 'ACL Grant Set 202112')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'd4525438-49a1-486a-a651-40b9099ece3a')
            column(name: 'df_acl_fund_pool_uid', value: '1ba51f2d-50f4-44a3-91c2-3a3359f498f2')
            column(name: 'df_acl_usage_batch_uid', value: '4bfcf746-ea11-4d62-bbc5-bf39f81fb203')
            column(name: 'df_acl_grant_set_uid', value: '1be8d8db-b77a-43cd-a3ce-0e94b1887de0')
            column(name: 'name', value: 'ACL Scenario 202112')
            column(name: 'period_end_date', value: 202112)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'e6c1a4e4-1484-498b-a134-5268bfcceb11')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '6ff8c9b3-17b9-48bd-9793-4e32d920f4fd')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'fd7b863f-530e-4f26-a66e-594295a6af95')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '26aaeab0-dfb8-4983-bc0a-b024fa2a8951')
            column(name: 'df_acl_fund_pool_uid', value: 'e6c1a4e4-1484-498b-a134-5268bfcceb11')
            column(name: 'df_acl_usage_batch_uid', value: '6ff8c9b3-17b9-48bd-9793-4e32d920f4fd')
            column(name: 'df_acl_grant_set_uid', value: 'fd7b863f-530e-4f26-a66e-594295a6af95')
            column(name: 'name', value: 'ACL Scenario 202212')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'SUBMITTED')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
