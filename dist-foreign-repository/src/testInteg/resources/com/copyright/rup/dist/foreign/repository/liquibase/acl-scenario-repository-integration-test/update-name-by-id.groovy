databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-12-21-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Insert test data for testUpdateNameById')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '1545a8c5-79f3-494d-9f3a-be74b086dc04')
            column(name: 'name', value: 'ACL Fund Pool 202312')
            column(name: 'period', value: 202312)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'f09466f2-fcb0-41b8-81cf-b6bce65b4ade')
            column(name: 'name', value: 'ACL Usage Batch 202312')
            column(name: 'distribution_period', value: 202312)
            column(name: 'periods', value: '[202306, 202312]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'b6b2418d-fc32-4a04-8e2f-fb50b321d74c')
            column(name: 'name', value: 'ACL Grant Set 202312')
            column(name: 'grant_period', value: 202312)
            column(name: 'periods', value: '[202306, 202312]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '70d880ed-da0b-47fa-b7cf-2282ed389deb')
            column(name: 'df_acl_fund_pool_uid', value: '1545a8c5-79f3-494d-9f3a-be74b086dc04')
            column(name: 'df_acl_usage_batch_uid', value: 'f09466f2-fcb0-41b8-81cf-b6bce65b4ade')
            column(name: 'df_acl_grant_set_uid', value: 'b6b2418d-fc32-4a04-8e2f-fb50b321d74c')
            column(name: 'name', value: 'ACL Scenario 202312')
            column(name: 'description', value: 'Description')
            column(name: 'period_end_date', value: 202312)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'copied_from', value: "Another Scenario")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: 'd7143044-1289-416d-bb04-ab079d419370')
            column(name: 'df_acl_scenario_uid', value: '70d880ed-da0b-47fa-b7cf-2282ed389deb')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '938b78ec-1281-469f-987e-dff5714de1be')
            column(name: 'df_acl_scenario_uid', value: '70d880ed-da0b-47fa-b7cf-2282ed389deb')
            column(name: 'period_prior', value: 1)
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '26f52fbe-52cf-4c03-af0a-6a207605fb92')
            column(name: 'df_acl_scenario_uid', value: '70d880ed-da0b-47fa-b7cf-2282ed389deb')
            column(name: 'df_publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '3a2153d8-9d68-4921-95e5-f9863694de17')
            column(name: 'df_acl_scenario_uid', value: '70d880ed-da0b-47fa-b7cf-2282ed389deb')
            column(name: 'df_publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'ed37f2d8-ecee-4254-b5f6-9f6f6e338c1a')
            column(name: 'df_acl_scenario_uid', value: '70d880ed-da0b-47fa-b7cf-2282ed389deb')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'aa080c9b-0e85-4f2b-a251-059ea7632814')
            column(name: 'df_acl_scenario_uid', value: '70d880ed-da0b-47fa-b7cf-2282ed389deb')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'aggregate_licensee_class_id', value: 52)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '8602439d-0d92-4418-99cd-7ec8ba9af9a1')
            column(name: 'df_acl_scenario_uid', value: '70d880ed-da0b-47fa-b7cf-2282ed389deb')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN974GHHSB101')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'pub_type_weight', value: 1.9)
            column(name: 'content_unit_price', value: 6.3000000000)
            column(name: 'number_of_copies', value: 5)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 5.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '23792dea-9e17-4564-86bb-8ea3f8e3e80c')
            column(name: 'df_acl_scenario_uid', value: '70d880ed-da0b-47fa-b7cf-2282ed389deb')
            column(name: 'df_acl_scenario_detail_uid', value: '8602439d-0d92-4418-99cd-7ec8ba9af9a1')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 5.0000000000)
            column(name: 'value_weight', value: 59.8500000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
        }

        rollback {
            dbRollback
        }
    }
}
