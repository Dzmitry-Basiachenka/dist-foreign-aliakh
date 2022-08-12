databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-07-25-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Insert test data for testDeleteZeroAmountUsages')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'b22c9c65-f592-4dad-8c22-c67eaad2ae30')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'b339c953-1be7-4932-8dad-2ec9c068c330')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'e5f06484-83d3-4c24-877d-d432254afafe')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '9fab9b5f-86cd-487a-a5e1-aab5ea8df175')
            column(name: 'df_acl_fund_pool_uid', value: 'b22c9c65-f592-4dad-8c22-c67eaad2ae30')
            column(name: 'df_acl_usage_batch_uid', value: 'e5f06484-83d3-4c24-877d-d432254afafe')
            column(name: 'df_acl_grant_set_uid', value: 'b339c953-1be7-4932-8dad-2ec9c068c330')
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
            column(name: 'df_acl_scenario_detail_uid', value: 'b5018606-8943-43db-ae80-57ad5ba15cba')
            column(name: 'df_acl_scenario_uid', value: '9fab9b5f-86cd-487a-a5e1-aab5ea8df175')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'ASN974GHHSB100')
            column(name: 'wr_wrk_inst', value: 122825555)
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '5403bc0d-cb82-4bd9-82bd-0e637272e6ce')
            column(name: 'df_acl_scenario_uid', value: '9fab9b5f-86cd-487a-a5e1-aab5ea8df175')
            column(name: 'df_acl_scenario_detail_uid', value: 'b5018606-8943-43db-ae80-57ad5ba15cba')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 3.0000000000)
            column(name: 'value_weight', value: 6.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '7105dd3b-0104-482b-b0cb-80164a06b5bf')
            column(name: 'df_acl_scenario_uid', value: '9fab9b5f-86cd-487a-a5e1-aab5ea8df175')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'ASN974GHHSB101')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'pub_type_weight', value: 1.9)
            column(name: 'content_unit_price', value: 6.3000000000)
            column(name: 'number_of_copies', value: 5)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 5.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'daab6051-288f-43b8-a980-b69a860e92fd')
            column(name: 'df_acl_scenario_uid', value: '9fab9b5f-86cd-487a-a5e1-aab5ea8df175')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'ASN974GHHSB102')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'pub_type_weight', value: 1.9)
            column(name: 'content_unit_price', value: 6.3000000000)
            column(name: 'number_of_copies', value: 5)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 5.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'ebb9e312-ec32-404c-a47c-f60e9a32e166')
            column(name: 'df_acl_scenario_uid', value: '9fab9b5f-86cd-487a-a5e1-aab5ea8df175')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '318eb3b8-290c-4f2e-9c30-e52c7a0ac7f2')
            column(name: 'df_acl_scenario_uid', value: '9fab9b5f-86cd-487a-a5e1-aab5ea8df175')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        rollback {
            dbRollback
        }
    }
}
