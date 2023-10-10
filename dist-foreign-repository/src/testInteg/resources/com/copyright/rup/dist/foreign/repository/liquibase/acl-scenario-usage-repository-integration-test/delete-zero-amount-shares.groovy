databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-07-25-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Insert test data for testDeleteZeroAmountShares')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'b347f000-c20b-48df-8ea2-26abb84ea680')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'e0dd48d8-d4de-40cc-9cd5-bdd3f26e8b9d')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'f5b2dd11-f85e-443b-97a1-3f8c6270ab97')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '3f55f25f-c2cb-4e7b-aaa9-3c25f7a879cf')
            column(name: 'df_acl_fund_pool_uid', value: 'b347f000-c20b-48df-8ea2-26abb84ea680')
            column(name: 'df_acl_usage_batch_uid', value: 'f5b2dd11-f85e-443b-97a1-3f8c6270ab97')
            column(name: 'df_acl_grant_set_uid', value: 'e0dd48d8-d4de-40cc-9cd5-bdd3f26e8b9d')
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
            column(name: 'df_acl_scenario_uid', value: '3f55f25f-c2cb-4e7b-aaa9-3c25f7a879cf')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'AWN974GHHSB100')
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
            column(name: 'df_acl_scenario_uid', value: '3f55f25f-c2cb-4e7b-aaa9-3c25f7a879cf')
            column(name: 'df_acl_scenario_detail_uid', value: 'b5018606-8943-43db-ae80-57ad5ba15cba')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122825555)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 3.0000000000)
            column(name: 'value_weight', value: 6.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '7105dd3b-0104-482b-b0cb-80164a06b5bf')
            column(name: 'df_acl_scenario_uid', value: '3f55f25f-c2cb-4e7b-aaa9-3c25f7a879cf')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'AWN974GHHSB101')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '1f9d85d6-3bb2-402f-8cb7-800c838e7dd7')
            column(name: 'df_acl_scenario_uid', value: '3f55f25f-c2cb-4e7b-aaa9-3c25f7a879cf')
            column(name: 'df_acl_scenario_detail_uid', value: '7105dd3b-0104-482b-b0cb-80164a06b5bf')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 5.0000000000)
            column(name: 'value_weight', value: 59.8500000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 5.0000000000)
            column(name: 'gross_amount', value: 7.0000000000)
            column(name: 'service_fee_amount', value: 2.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '9324d7f6-a589-44b6-bff0-83038bc8d379')
            column(name: 'df_acl_scenario_uid', value: '3f55f25f-c2cb-4e7b-aaa9-3c25f7a879cf')
            column(name: 'df_acl_scenario_detail_uid', value: '7105dd3b-0104-482b-b0cb-80164a06b5bf')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 5.0000000000)
            column(name: 'value_weight', value: 59.8500000000)
            column(name: 'volume_share', value: 0.5000000000)
            column(name: 'value_share', value: 0.2861035422)
            column(name: 'detail_share', value: 0.3930517711)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'a635f8fd-ac49-436c-8192-d89831c8d374')
            column(name: 'df_acl_scenario_uid', value: '3f55f25f-c2cb-4e7b-aaa9-3c25f7a879cf')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '32098751-ed69-472b-bb36-3ae776f27da7')
            column(name: 'df_acl_scenario_uid', value: '3f55f25f-c2cb-4e7b-aaa9-3c25f7a879cf')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        rollback {
            dbRollback
        }
    }
}
