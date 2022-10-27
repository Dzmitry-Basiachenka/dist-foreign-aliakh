databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-10-26-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testSubmittedScenarioExistWithLicenseTypeAndPeriod')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'af280809-e951-49b4-8498-a3f41e550aaa')
            column(name: 'name', value: 'ACL Fund Pool 202012')
            column(name: 'period', value: 202012)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '800aeae2-79c6-4b26-bc86-2fe3a48cbc94')
            column(name: 'name', value: 'ACL Usage Batch 202012')
            column(name: 'distribution_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'b288b871-561b-4c89-985b-84347656df20')
            column(name: 'name', value: 'ACL Grant Set 202012')
            column(name: 'grant_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '64bf7021-70f9-4309-b7a6-3cb32d8d2778')
            column(name: 'df_acl_fund_pool_uid', value: 'af280809-e951-49b4-8498-a3f41e550aaa')
            column(name: 'df_acl_usage_batch_uid', value: '800aeae2-79c6-4b26-bc86-2fe3a48cbc94')
            column(name: 'df_acl_grant_set_uid', value: 'b288b871-561b-4c89-985b-84347656df20')
            column(name: 'name', value: 'ACL Scenario 202012')
            column(name: 'description', value: 'some description')
            column(name: 'period_end_date', value: 202012)
            column(name: 'status_ind', value: 'SUBMITTED')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'copied_from', value: "Another Scenario")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '23bf2f50-c97a-414d-a3b3-deb0b7e391ba')
            column(name: 'name', value: 'ACL Fund Pool 202206')
            column(name: 'period', value: 202206)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '23bd1b17-4fd2-4a99-86f3-62bfa00e2e22')
            column(name: 'name', value: 'ACL Usage Batch 202206')
            column(name: 'distribution_period', value: 202206)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'e9e71053-7d4f-4c3c-bbfb-176597081228')
            column(name: 'name', value: 'ACL Grant Set 202206')
            column(name: 'grant_period', value: 202206)
            column(name: 'periods', value: '[202006, 201912]')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '8632405e-74a3-4553-8666-dc886344dede')
            column(name: 'df_acl_fund_pool_uid', value: '23bf2f50-c97a-414d-a3b3-deb0b7e391ba')
            column(name: 'df_acl_usage_batch_uid', value: '23bd1b17-4fd2-4a99-86f3-62bfa00e2e22')
            column(name: 'df_acl_grant_set_uid', value: 'e9e71053-7d4f-4c3c-bbfb-176597081228')
            column(name: 'name', value: 'ACL Scenario 202206')
            column(name: 'description', value: 'some description')
            column(name: 'period_end_date', value: 202206)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
            column(name: 'copied_from', value: "Another Scenario")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'de96f11f-a2e7-4144-999c-6c6646c9cb218')
            column(name: 'df_acl_fund_pool_uid', value: 'af280809-e951-49b4-8498-a3f41e550aaa')
            column(name: 'df_acl_usage_batch_uid', value: '800aeae2-79c6-4b26-bc86-2fe3a48cbc94')
            column(name: 'df_acl_grant_set_uid', value: 'b288b871-561b-4c89-985b-84347656df20')
            column(name: 'name', value: 'ACL Scenario 202012')
            column(name: 'description', value: 'some description')
            column(name: 'period_end_date', value: 202012)
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'copied_from', value: "Another Scenario")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'edc599a5-1d90-4bdf-8b4b-9a0a6c200319')
            column(name: 'df_acl_fund_pool_uid', value: 'af280809-e951-49b4-8498-a3f41e550aaa')
            column(name: 'df_acl_usage_batch_uid', value: '800aeae2-79c6-4b26-bc86-2fe3a48cbc94')
            column(name: 'df_acl_grant_set_uid', value: 'b288b871-561b-4c89-985b-84347656df20')
            column(name: 'name', value: 'ACL Scenario 202012')
            column(name: 'description', value: 'some description')
            column(name: 'period_end_date', value: 202012)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'copied_from', value: "Another Scenario")
        }

        rollback {
            dbRollback
        }
    }
}
