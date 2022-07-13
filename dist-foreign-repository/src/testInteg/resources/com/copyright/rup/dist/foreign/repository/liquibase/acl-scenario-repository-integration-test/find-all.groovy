databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-06-24-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Insert test data for testFindAll')

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
            column(name: 'df_acl_scenario_uid', value: '1995d50d-41c6-4e81-8c82-51a983bbecf8')
            column(name: 'df_acl_fund_pool_uid', value: '2a173b41-75e3-4478-80ef-157527b18996')
            column(name: 'df_acl_usage_batch_uid', value: '65b930f1-777d-4a51-b878-bea3c68624d8')
            column(name: 'df_acl_grant_set_uid', value: '83e881cf-b258-42c1-849e-b2ec32b302b5')
            column(name: 'name', value: 'ACL Scenario 202112')
            column(name: 'period_end_date', value: 202112)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
            column(name: 'created_by_user', value: 'auser@copyright.com')
            column(name: 'updated_by_user', value: 'auser@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '274ad62f-365e-41a6-a169-0e85e04d52d4')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '2474c9ae-dfaf-404f-b4eb-17b7c88794d2')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '7e89e5c4-7db6-44b6-9a82-43166ec8da63')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'c65e9c0a-006f-4b79-b828-87d2106330b7')
            column(name: 'df_acl_fund_pool_uid', value: '274ad62f-365e-41a6-a169-0e85e04d52d4')
            column(name: 'df_acl_usage_batch_uid', value: '2474c9ae-dfaf-404f-b4eb-17b7c88794d2')
            column(name: 'df_acl_grant_set_uid', value: '7e89e5c4-7db6-44b6-9a82-43166ec8da63')
            column(name: 'name', value: 'ACL Scenario 202212')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: 'eb80bec3-6b46-414a-83a8-40a8b94642c0')
            column(name: 'df_acl_scenario_uid', value: '1995d50d-41c6-4e81-8c82-51a983bbecf8')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '86625299-47a7-4adc-bc65-43c527e3c7af')
            column(name: 'df_acl_scenario_uid', value: '1995d50d-41c6-4e81-8c82-51a983bbecf8')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'fffabfef-3411-4f26-a3de-2e203f38716d')
            column(name: 'df_acl_scenario_uid', value: '1995d50d-41c6-4e81-8c82-51a983bbecf8')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: 'e6eff13e-6566-4585-9859-306b54553fe5')
            column(name: 'df_acl_scenario_uid', value: 'c65e9c0a-006f-4b79-b828-87d2106330b7')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '8ad5583b-7999-4a7b-b7aa-526cf32572d3')
            column(name: 'df_acl_scenario_uid', value: 'c65e9c0a-006f-4b79-b828-87d2106330b7')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'ada3da9b-e49f-449b-8942-f9844f5b8aca')
            column(name: 'df_acl_scenario_uid', value: 'c65e9c0a-006f-4b79-b828-87d2106330b7')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        rollback {
            dbRollback
        }
    }
}
