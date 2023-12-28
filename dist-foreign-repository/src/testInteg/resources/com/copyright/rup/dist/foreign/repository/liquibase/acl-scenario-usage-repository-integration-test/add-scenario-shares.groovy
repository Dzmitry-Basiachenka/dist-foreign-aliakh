databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-07-13-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Insert test data for testAddScenarioShares')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'f74d4355-2f86-4168-a85d-9233f98ce0eb')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '6b821963-c0d1-41f4-8e97-a63f737c34fb')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'd18a7fba-e41d-4325-ad19-cbfc088a4e94')
            column(name: 'df_acl_grant_set_uid', value: '6b821963-c0d1-41f4-8e97-a63f737c34fb')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Different RH')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'payee_account_number', value: 1000000206)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '044560ea-2af4-4f25-ae58-516eecc136a2')
            column(name: 'df_acl_grant_set_uid', value: '6b821963-c0d1-41f4-8e97-a63f737c34fb')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Different RH')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'is_eligible', value: true)
            column(name: 'payee_account_number', value: 1000000229)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '96d266e4-9633-4a6e-8e26-748596bd71f3')
            column(name: 'df_acl_grant_set_uid', value: '6b821963-c0d1-41f4-8e97-a63f737c34fb')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 122825555)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'payee_account_number', value: 1000000294)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '6e6ccdad-62e0-4d55-8dc7-e1fd6a227140')
            column(name: 'df_acl_grant_set_uid', value: '6b821963-c0d1-41f4-8e97-a63f737c34fb')
            column(name: 'grant_status', value: 'DENY')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 122825555)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'payee_account_number', value: 1000000322)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '2a8c042c-1d66-469f-b4df-0987de0e308c')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '17d43251-6637-41cb-8831-1bce47a7da85')
            column(name: 'df_acl_fund_pool_uid', value: 'f74d4355-2f86-4168-a85d-9233f98ce0eb')
            column(name: 'df_acl_usage_batch_uid', value: '2a8c042c-1d66-469f-b4df-0987de0e308c')
            column(name: 'df_acl_grant_set_uid', value: '6b821963-c0d1-41f4-8e97-a63f737c34fb')
            column(name: 'name', value: 'ACL Scenario 201512')
            column(name: 'description', value: 'Description')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '8827d6c6-16d8-4102-b257-ce861ce77491')
            column(name: 'df_acl_scenario_uid', value: '17d43251-6637-41cb-8831-1bce47a7da85')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN674GHHSB101')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'pub_type_weight', value: 1.9)
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'number_of_copies', value: 5)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 5.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'df038efe-72c1-4081-88e7-17fa4fa5ff6a')
            column(name: 'df_acl_scenario_uid', value: '17d43251-6637-41cb-8831-1bce47a7da85')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN674GHHSB100')
            column(name: 'wr_wrk_inst', value: 122825555)
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'pub_type_weight', value: 2)
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'number_of_copies', value: 3)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 3.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '419d57c4-7040-47b2-b3d5-a5412af9991d')
            column(name: 'df_acl_scenario_uid', value: '17d43251-6637-41cb-8831-1bce47a7da85')
            column(name: 'period_prior', value: 1)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '2199f1a9-31fe-4138-bb4a-280daec03219')
            column(name: 'df_acl_scenario_uid', value: '17d43251-6637-41cb-8831-1bce47a7da85')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '579d68f3-88eb-4d3a-9550-c18f1714a2e1')
            column(name: 'df_acl_scenario_uid', value: '17d43251-6637-41cb-8831-1bce47a7da85')
            column(name: 'period_prior', value: 2)
            column(name: 'weight', value: 0.5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '55c19f52-919b-4fab-87bc-9b96855f1de1')
            column(name: 'df_acl_scenario_uid', value: '17d43251-6637-41cb-8831-1bce47a7da85')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'f50d9b3d-f538-4e2a-ab31-13a2ac666377')
            column(name: 'df_acl_scenario_uid', value: '17d43251-6637-41cb-8831-1bce47a7da85')
            column(name: 'df_publication_type_uid', value: "aef4304b-6722-4047-86e0-8c84c72f096d")
            column(name: 'weight', value: 1.9)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '88c88fb1-c4d0-46d2-b563-daeb486470a9')
            column(name: 'df_acl_scenario_uid', value: '17d43251-6637-41cb-8831-1bce47a7da85')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        rollback {
            dbRollback
        }
    }
}
