databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-06-22-01', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testfindByScenarioId')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '6e574487-3d58-4e7a-b89d-df96204b43d8')
            column(name: 'name', value: 'ACL Fund Pool 202012')
            column(name: 'period', value: 202012)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '962a1e2a-9174-49cb-b69b-ba425ed0a827')
            column(name: 'name', value: 'ACL Usage Batch 202012')
            column(name: 'distribution_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '3eb5deb2-9ec8-41c8-94c0-525cf37b08a8')
            column(name: 'name', value: 'ACL Grant Set 202012')
            column(name: 'grant_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '70c70f5b-074f-4936-8347-a5c19306b5ac')
            column(name: 'df_acl_fund_pool_uid', value: '6e574487-3d58-4e7a-b89d-df96204b43d8')
            column(name: 'df_acl_usage_batch_uid', value: '962a1e2a-9174-49cb-b69b-ba425ed0a827')
            column(name: 'df_acl_grant_set_uid', value: '3eb5deb2-9ec8-41c8-94c0-525cf37b08a8')
            column(name: 'name', value: 'ACL Scenario 202012')
            column(name: 'period_end_date', value: 202012)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'f92a01f7-4b82-47b9-8d58-6835c0eb7ba8')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '325c5714-42ce-48db-9d15-ad3c6960dd73')
            column(name: 'name', value: 'ACL Usage Batch 202112')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '265ba482-0481-4115-8870-7199b0f768c4')
            column(name: 'name', value: 'ACL Grant Set 202112')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '8cd69469-cb63-46bf-9e33-2ba0e0904278')
            column(name: 'df_acl_fund_pool_uid', value: 'f92a01f7-4b82-47b9-8d58-6835c0eb7ba8')
            column(name: 'df_acl_usage_batch_uid', value: '325c5714-42ce-48db-9d15-ad3c6960dd73')
            column(name: 'df_acl_grant_set_uid', value: '265ba482-0481-4115-8870-7199b0f768c4')
            column(name: 'name', value: 'ACL Scenario 202112')
            column(name: 'period_end_date', value: 202112)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
