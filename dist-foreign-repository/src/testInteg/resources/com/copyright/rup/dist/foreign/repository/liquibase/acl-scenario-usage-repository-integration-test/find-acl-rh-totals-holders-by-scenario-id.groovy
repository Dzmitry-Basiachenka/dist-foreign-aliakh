databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-07-01-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Insert test data for testFindAclRightsholderTotalsHoldersByScenarioIdEmptySearchValue, ' +
                'testFindAclRightsholderTotalsHoldersByScenarioIdNotEmptySearchValue, ' +
                'testFindAclRightsholderTotalsHolderCountByScenarioIdEmptySearchValue, ' +
                'testFindAclRightsholderTotalsHolderCountByScenarioIdNullSearchValue, ' +
                'testSortingFindAclRightsholderTotalsHoldersByScenarioId')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '52379b50-1abf-4b20-a1a6-e6fd4e3bc20a')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5021690b-a6e4-4b88-8c22-230b816d9e08')
            column(name: 'rh_account_number', value: 7000873612)
            column(name: 'name', value: 'Brewin Books Ltd')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '5fad2697-d683-4e4d-ab1d-7e983d04204d')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '4826650a-4eee-4fc4-9f07-fd660aa91fb6')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'e29cf889-2818-46d4-8e97-f5a25897e312')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '0d0041a3-833e-463e-8ad4-f28461dc961d')
            column(name: 'df_acl_fund_pool_uid', value: '5fad2697-d683-4e4d-ab1d-7e983d04204d')
            column(name: 'df_acl_usage_batch_uid', value: '4826650a-4eee-4fc4-9f07-fd660aa91fb6')
            column(name: 'df_acl_grant_set_uid', value: 'e29cf889-2818-46d4-8e97-f5a25897e312')
            column(name: 'name', value: 'ACL Scenario 202212')
            column(name: 'description', value: 'Description')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'SUBMITTED')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '79c6196c-6d52-4ed5-8251-d653afa89aee')
            column(name: 'df_acl_scenario_uid', value: '0d0041a3-833e-463e-8ad4-f28461dc961d')
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
            column(name: 'df_acl_share_detail_uid', value: 'a5a30e94-6e8b-42bb-bd8e-37adb19741d2')
            column(name: 'df_acl_scenario_uid', value: '0d0041a3-833e-463e-8ad4-f28461dc961d')
            column(name: 'df_acl_scenario_detail_uid', value: '79c6196c-6d52-4ed5-8251-d653afa89aee')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 918765432)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'net_amount', value: 10.00)
            column(name: 'gross_amount', value: 20.00)
            column(name: 'service_fee_amount', value: 3.00)
            column(name: 'payee_account_number', value: 7000873612)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '86314fa2-4dd7-44d7-b75b-76358a599085')
            column(name: 'df_acl_scenario_uid', value: '0d0041a3-833e-463e-8ad4-f28461dc961d')
            column(name: 'df_acl_scenario_detail_uid', value: '79c6196c-6d52-4ed5-8251-d653afa89aee')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 918765432)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 12)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'net_amount', value: 10.00)
            column(name: 'gross_amount', value: 20.00)
            column(name: 'service_fee_amount', value: 3.00)
            column(name: 'payee_account_number', value: 1000000001)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '66845e1f-5603-4101-811d-29e4187a5976')
            column(name: 'df_acl_scenario_uid', value: '0d0041a3-833e-463e-8ad4-f28461dc961d')
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
            column(name: 'df_acl_share_detail_uid', value: '566da0b0-b0d6-4232-b7c2-00fce2305898')
            column(name: 'df_acl_scenario_uid', value: '0d0041a3-833e-463e-8ad4-f28461dc961d')
            column(name: 'df_acl_scenario_detail_uid', value: '66845e1f-5603-4101-811d-29e4187a5976')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.00)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'service_fee_amount', value: 16.00)
            column(name: 'payee_account_number', value: 7000873612)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '5493dd97-9967-4647-8ca3-759e9a83bad6')
            column(name: 'df_acl_scenario_uid', value: '0d0041a3-833e-463e-8ad4-f28461dc961d')
            column(name: 'df_acl_scenario_detail_uid', value: '66845e1f-5603-4101-811d-29e4187a5976')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'net_amount', value: 168.00)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'payee_account_number', value: 1000000001)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'a1c9054b-4716-4fdb-a9c7-8abc79347f35')
            column(name: 'df_acl_scenario_uid', value: '0d0041a3-833e-463e-8ad4-f28461dc961d')
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
            column(name: 'df_acl_share_detail_uid', value: 'b2ee2225-3436-4098-9e29-9cd861fb44f4')
            column(name: 'df_acl_scenario_uid', value: '0d0041a3-833e-463e-8ad4-f28461dc961d')
            column(name: 'df_acl_scenario_detail_uid', value: 'a1c9054b-4716-4fdb-a9c7-8abc79347f35')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 100002555)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'net_amount', value: 42.00)
            column(name: 'gross_amount', value: 50.00)
            column(name: 'service_fee_amount', value: 8.00)
            column(name: 'payee_account_number', value: 7000873612)
        }
        rollback {
            dbRollback
        }
    }
}
