databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-10-27-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testFindRightsholderPayeeProductFamilyHoldersByAclScenarioIds')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05844db0-e0e4-4423-8966-7f1c6160f000')
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'name', value: 'Georgia State University Business Press [C]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5021690b-a6e4-4b88-8c22-230b816d9e08')
            column(name: 'rh_account_number', value: 7000873612)
            column(name: 'name', value: 'Brewin Books Ltd')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'd2470f8e-7a4b-4b5c-8a6c-b034662a0f5b')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'fa55f125-16e6-415c-bda5-5f80c15749a9')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'a1795d9c-3cfa-4c30-a159-b95d6d7496f7')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'f146c64c-6cf7-4d8d-9011-132ee6bf2f3b')
            column(name: 'df_acl_fund_pool_uid', value: 'd2470f8e-7a4b-4b5c-8a6c-b034662a0f5b')
            column(name: 'df_acl_usage_batch_uid', value: 'fa55f125-16e6-415c-bda5-5f80c15749a9')
            column(name: 'df_acl_grant_set_uid', value: 'a1795d9c-3cfa-4c30-a159-b95d6d7496f7')
            column(name: 'name', value: 'ACL Scenario 202212')
            column(name: 'description', value: 'Description')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'SUBMITTED')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '9a5ce8bf-3ceb-4c50-b5a0-edf6b0632e7b')
            column(name: 'df_acl_scenario_uid', value: 'f146c64c-6cf7-4d8d-9011-132ee6bf2f3b')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHSB108')
            column(name: 'wr_wrk_inst', value: 918765432)
            column(name: 'system_title', value: 'Corporate identity manuals')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: 'ad8df236-5200-4acf-be55-cf82cd342f14')
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'number_of_copies', value: 1)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 1.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'a8b53cd0-b276-46d2-b0ad-500aebb86ea0')
            column(name: 'df_acl_scenario_uid', value: 'f146c64c-6cf7-4d8d-9011-132ee6bf2f3b')
            column(name: 'df_acl_scenario_detail_uid', value: '9a5ce8bf-3ceb-4c50-b5a0-edf6b0632e7b')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 918765432)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 7000873612)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'net_amount', value: 2.00)
            column(name: 'gross_amount', value: 3.00)
            column(name: 'service_fee_amount', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'f4e631f5-bcfa-4a4c-9e24-460e6bea25f6')
            column(name: 'df_acl_scenario_uid', value: 'f146c64c-6cf7-4d8d-9011-132ee6bf2f3b')
            column(name: 'df_acl_scenario_detail_uid', value: '9a5ce8bf-3ceb-4c50-b5a0-edf6b0632e7b')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 918765432)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 7000873612)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'net_amount', value: 2.00)
            column(name: 'gross_amount', value: 3.00)
            column(name: 'service_fee_amount', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '8c92674d-b4da-40f4-bca8-456b4345b0ae')
            column(name: 'df_acl_scenario_uid', value: 'f146c64c-6cf7-4d8d-9011-132ee6bf2f3b')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHSB107')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: 'ad8df236-5200-4acf-be55-cf82cd342f14')
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'number_of_copies', value: 1)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 1.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '34be27eb-6e6b-45e8-8971-55634b508074')
            column(name: 'df_acl_scenario_uid', value: 'f146c64c-6cf7-4d8d-9011-132ee6bf2f3b')
            column(name: 'df_acl_scenario_detail_uid', value: '8c92674d-b4da-40f4-bca8-456b4345b0ae')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 7000873612)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'net_amount', value: 2.00)
            column(name: 'gross_amount', value: 3.00)
            column(name: 'service_fee_amount', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'b0cefc39-abe2-4742-ae75-61aafe79134a')
            column(name: 'df_acl_scenario_uid', value: 'f146c64c-6cf7-4d8d-9011-132ee6bf2f3b')
            column(name: 'df_acl_scenario_detail_uid', value: '8c92674d-b4da-40f4-bca8-456b4345b0ae')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 7000873612)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'net_amount', value: 2.00)
            column(name: 'gross_amount', value: 3.00)
            column(name: 'service_fee_amount', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'cf576e06-adca-4cd9-b1ee-5fd7731be083')
            column(name: 'df_acl_scenario_uid', value: 'f146c64c-6cf7-4d8d-9011-132ee6bf2f3b')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHSB109')
            column(name: 'wr_wrk_inst', value: 100002555)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'publication_type_uid', value: 'ad8df236-5200-4acf-be55-cf82cd342f14')
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'number_of_copies', value: 1)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 1.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '02810710-9fec-40e6-b36a-ab4c9b712a7d')
            column(name: 'df_acl_scenario_uid', value: 'f146c64c-6cf7-4d8d-9011-132ee6bf2f3b')
            column(name: 'df_acl_scenario_detail_uid', value: 'cf576e06-adca-4cd9-b1ee-5fd7731be083')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 100002555)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 7000873612)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'net_amount', value: 2.00)
            column(name: 'gross_amount', value: 3.00)
            column(name: 'service_fee_amount', value: 1.00)
        }

        rollback {
            dbRollback
        }
    }
}
