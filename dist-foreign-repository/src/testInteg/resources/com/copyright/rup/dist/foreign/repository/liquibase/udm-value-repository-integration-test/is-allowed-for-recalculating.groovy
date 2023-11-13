databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-11-12-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserts test data for testIsAllowedForRecalculating')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '2a173b41-75e3-4478-80ef-157527b18996')
            column(name: 'name', value: 'ACL Fund Pool 202112')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '65b930f1-777d-4a51-b878-bea3c68624d8')
            column(name: 'name', value: 'ACL Usage Batch 202112')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '83e881cf-b258-42c1-849e-b2ec32b302b5')
            column(name: 'name', value: 'ACL Grant Set 202112')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'cf1b6b34-0a67-4177-a456-4429f20fe2c5')
            column(name: 'df_acl_fund_pool_uid', value: '2a173b41-75e3-4478-80ef-157527b18996')
            column(name: 'df_acl_usage_batch_uid', value: '65b930f1-777d-4a51-b878-bea3c68624d8')
            column(name: 'df_acl_grant_set_uid', value: '83e881cf-b258-42c1-849e-b2ec32b302b5')
            column(name: 'name', value: 'ACL Scenario 202512')
            column(name: 'description', value: 'some description')
            column(name: 'period_end_date', value: 202012)
            column(name: 'status_ind', value: 'ADDED_USAGES')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '1eafb7f4-1bb3-499c-b310-434e9e4d04df')
            column(name: 'df_acl_fund_pool_uid', value: '2a173b41-75e3-4478-80ef-157527b18996')
            column(name: 'df_acl_usage_batch_uid', value: '65b930f1-777d-4a51-b878-bea3c68624d8')
            column(name: 'df_acl_grant_set_uid', value: '83e881cf-b258-42c1-849e-b2ec32b302b5')
            column(name: 'name', value: 'ACL Scenario 202512')
            column(name: 'description', value: 'some description')
            column(name: 'period_end_date', value: 202512)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'ec5eaac8-8f97-43fa-bec4-1a6597c4db64')
            column(name: 'df_acl_fund_pool_uid', value: '2a173b41-75e3-4478-80ef-157527b18996')
            column(name: 'df_acl_usage_batch_uid', value: '65b930f1-777d-4a51-b878-bea3c68624d8')
            column(name: 'df_acl_grant_set_uid', value: '83e881cf-b258-42c1-849e-b2ec32b302b5')
            column(name: 'name', value: 'ACL Scenario 202612')
            column(name: 'description', value: 'some description')
            column(name: 'period_end_date', value: 202612)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '596eab5a-9221-4d12-9323-88f8e0a99d4b')
            column(name: 'df_acl_fund_pool_uid', value: '2a173b41-75e3-4478-80ef-157527b18996')
            column(name: 'df_acl_usage_batch_uid', value: '65b930f1-777d-4a51-b878-bea3c68624d8')
            column(name: 'df_acl_grant_set_uid', value: '83e881cf-b258-42c1-849e-b2ec32b302b5')
            column(name: 'name', value: 'ACL Scenario 202712')
            column(name: 'description', value: 'some description')
            column(name: 'period_end_date', value: 202712)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        rollback {
            dbRollback
        }
    }
}
