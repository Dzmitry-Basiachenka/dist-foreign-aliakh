databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-10-05-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteAclLiabilityDetailsReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'd9626196-9019-428b-a587-cc1dda2d065b')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '9c30ceb6-8dfa-4f57-abd3-2875d355f092')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'c2e3d674-4457-4d58-b7d5-67ff14e4eb7e')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'f033bc34-a863-4d40-a93a-a16271c6e109')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'be7936f7-f4c5-4036-b1aa-de3f2ea4960c')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '06fee547-bfc4-4f2a-9578-58c03821e217')
            column(name: 'df_acl_fund_pool_uid', value: 'be7936f7-f4c5-4036-b1aa-de3f2ea4960c')
            column(name: 'df_acl_usage_batch_uid', value: 'c2e3d674-4457-4d58-b7d5-67ff14e4eb7e')
            column(name: 'df_acl_grant_set_uid', value: 'f033bc34-a863-4d40-a93a-a16271c6e109')
            column(name: 'name', value: 'ACL Scenario 10/05/202212')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'edb2b7de-436b-4563-aaac-c17cef323f82')
            column(name: 'name', value: 'MACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'dfaa0834-fe3a-42d7-9d6f-b3a1733927ed')
            column(name: 'name', value: 'MACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '0fb2691e-98bb-4ab7-be4e-12bf3984e804')
            column(name: 'df_acl_fund_pool_uid', value: 'dfaa0834-fe3a-42d7-9d6f-b3a1733927ed')
            column(name: 'df_acl_usage_batch_uid', value: 'c2e3d674-4457-4d58-b7d5-67ff14e4eb7e')
            column(name: 'df_acl_grant_set_uid', value: 'edb2b7de-436b-4563-aaac-c17cef323f82')
            column(name: 'name', value: 'MACL Scenario 10/05/202212')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'e59cae8e-cb2a-4a4f-8548-f23834b52bc0')
            column(name: 'name', value: 'VGW Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'a02d3408-4bb1-4bbd-b021-cc256fd0ae34')
            column(name: 'name', value: 'VGW Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'ca1c5532-eb1c-440a-8fbc-8e595dbea5cb')
            column(name: 'df_acl_fund_pool_uid', value: 'a02d3408-4bb1-4bbd-b021-cc256fd0ae34')
            column(name: 'df_acl_usage_batch_uid', value: 'c2e3d674-4457-4d58-b7d5-67ff14e4eb7e')
            column(name: 'df_acl_grant_set_uid', value: 'e59cae8e-cb2a-4a4f-8548-f23834b52bc0')
            column(name: 'name', value: 'VGW Scenario 10/05/202212')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '62f7c69f-86c9-4f57-9041-e57b991dffa5')
            column(name: 'name', value: 'JACDCL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'JACDCL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '9a6a808c-83d8-47d3-858a-c7eb462f742f')
            column(name: 'name', value: 'JACDCL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'JACDCL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'd86f2c59-a50c-4e54-826a-ee50aeb98904')
            column(name: 'df_acl_fund_pool_uid', value: '9a6a808c-83d8-47d3-858a-c7eb462f742f')
            column(name: 'df_acl_usage_batch_uid', value: 'c2e3d674-4457-4d58-b7d5-67ff14e4eb7e')
            column(name: 'df_acl_grant_set_uid', value: '62f7c69f-86c9-4f57-9041-e57b991dffa5')
            column(name: 'name', value: 'JACDCL Scenario 10/05/202212')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'JACDCL')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '6317d28b-4e05-4030-a249-4bf93dbd66d2')
            column(name: 'df_acl_scenario_uid', value: '06fee547-bfc4-4f2a-9578-58c03821e217')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHSB107')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content', value: 11.0001230000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 2.0000000000)
            column(name: 'number_of_copies', value: 1.00000)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 1.0000000000)
            column(name: 'pub_type_weight', value: 1.00)
            column(name: 'survey_country', value: 'United States')
            column(name: 'reported_type_of_use', value: 'COPY_FOR_MYSELF')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '1bc33a45-34fd-4fe7-ab0f-499792d98028')
            column(name: 'df_acl_scenario_uid', value: '06fee547-bfc4-4f2a-9578-58c03821e217')
            column(name: 'df_acl_scenario_detail_uid', value: '6317d28b-4e05-4030-a249-4bf93dbd66d2')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.1000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'f81462d0-f602-4f68-9300-57685f0e89c6')
            column(name: 'df_acl_scenario_uid', value: '06fee547-bfc4-4f2a-9578-58c03821e217')
            column(name: 'df_acl_scenario_detail_uid', value: '6317d28b-4e05-4030-a249-4bf93dbd66d2')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 42.1000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '46b35011-a7a8-4a99-8d7e-485b5b356146')
            column(name: 'df_acl_scenario_uid', value: '06fee547-bfc4-4f2a-9578-58c03821e217')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHSB107')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content', value: 11.0001230000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 2.0000000000)
            column(name: 'number_of_copies', value: 1.00000)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 1.0000000000)
            column(name: 'pub_type_weight', value: 1.00)
            column(name: 'survey_country', value: 'United States')
            column(name: 'reported_type_of_use', value: 'COPY_FOR_MYSELF')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '20a84f88-64cc-45b1-b165-4a8c8224421d')
            column(name: 'df_acl_scenario_uid', value: '06fee547-bfc4-4f2a-9578-58c03821e217')
            column(name: 'df_acl_scenario_detail_uid', value: '46b35011-a7a8-4a99-8d7e-485b5b356146')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 168.0000000000)
            column(name: 'gross_amount', value: 200.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '8b0e02a0-12d8-46b2-874c-344e1772005d')
            column(name: 'df_acl_scenario_uid', value: '06fee547-bfc4-4f2a-9578-58c03821e217')
            column(name: 'df_acl_scenario_detail_uid', value: '46b35011-a7a8-4a99-8d7e-485b5b356146')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 135.0000000000)
            column(name: 'gross_amount', value: 150.0000000000)
            column(name: 'service_fee_amount', value: 10.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '855e4081-fec6-4bfb-99ec-658e90d0e567')
            column(name: 'df_acl_scenario_uid', value: '0fb2691e-98bb-4ab7-be4e-12bf3984e804')
            column(name: 'period_end_date', value: 202206)
            column(name: 'original_detail_id', value: 'OGN674GHHSB108')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'price', value: 4.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 2.0000000000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 3.0001230000)
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'number_of_copies', value: 2.00000)
            column(name: 'usage_age_weight', value: 0.99000)
            column(name: 'weighted_copies', value: 0.9000000000)
            column(name: 'pub_type_weight', value: 2.00)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '71a78d4c-ef31-4a1b-ad1e-8fe6eeb6a544')
            column(name: 'df_acl_scenario_uid', value: '0fb2691e-98bb-4ab7-be4e-12bf3984e804')
            column(name: 'df_acl_scenario_detail_uid', value: '855e4081-fec6-4bfb-99ec-658e90d0e567')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.2000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 15.8000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '9735c9f1-d7c0-400e-bb1a-f3823e2515c6')
            column(name: 'df_acl_scenario_uid', value: '0fb2691e-98bb-4ab7-be4e-12bf3984e804')
            column(name: 'df_acl_scenario_detail_uid', value: '855e4081-fec6-4bfb-99ec-658e90d0e567')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 42.2000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.8000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '23b7a827-0afd-47aa-9059-7ca636611ec7')
            column(name: 'df_acl_scenario_uid', value: 'ca1c5532-eb1c-440a-8fbc-8e595dbea5cb')
            column(name: 'period_end_date', value: 202206)
            column(name: 'original_detail_id', value: 'OGN674GHHSB208')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'price', value: 4.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 2.0000000000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 3.0001230000)
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'number_of_copies', value: 2.00000)
            column(name: 'usage_age_weight', value: 0.99000)
            column(name: 'weighted_copies', value: 0.9000000000)
            column(name: 'pub_type_weight', value: 2.00)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '38cc20b4-21b3-4528-a88c-303f52f16f41')
            column(name: 'df_acl_scenario_uid', value: 'ca1c5532-eb1c-440a-8fbc-8e595dbea5cb')
            column(name: 'df_acl_scenario_detail_uid', value: '23b7a827-0afd-47aa-9059-7ca636611ec7')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.3000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 15.7000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'fd04b6cb-824c-47ea-ba51-2e41da07b09a')
            column(name: 'df_acl_scenario_uid', value: 'ca1c5532-eb1c-440a-8fbc-8e595dbea5cb')
            column(name: 'df_acl_scenario_detail_uid', value: '23b7a827-0afd-47aa-9059-7ca636611ec7')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 42.3000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.7000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'ead37bd8-a0c5-4bf0-ac8b-9e5737b77172')
            column(name: 'df_acl_scenario_uid', value: 'd86f2c59-a50c-4e54-826a-ee50aeb98904')
            column(name: 'period_end_date', value: 202206)
            column(name: 'original_detail_id', value: 'OGN674GHHSB308')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'price', value: 4.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 2.0000000000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 3.0001230000)
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'number_of_copies', value: 2.00000)
            column(name: 'usage_age_weight', value: 0.99000)
            column(name: 'weighted_copies', value: 0.9000000000)
            column(name: 'pub_type_weight', value: 2.00)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '34805b96-9707-40e4-8480-359577d2fc68')
            column(name: 'df_acl_scenario_uid', value: 'd86f2c59-a50c-4e54-826a-ee50aeb98904')
            column(name: 'df_acl_scenario_detail_uid', value: 'ead37bd8-a0c5-4bf0-ac8b-9e5737b77172')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.4000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 15.6000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'b4208d32-78d2-49cb-b3c6-de93d4ad0c93')
            column(name: 'df_acl_scenario_uid', value: 'd86f2c59-a50c-4e54-826a-ee50aeb98904')
            column(name: 'period_end_date', value: 202206)
            column(name: 'original_detail_id', value: 'OGN674GHHSB308')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'price', value: 4.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 2.0000000000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 3.0001230000)
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'number_of_copies', value: 2.00000)
            column(name: 'usage_age_weight', value: 0.99000)
            column(name: 'weighted_copies', value: 0.9000000000)
            column(name: 'pub_type_weight', value: 2.00)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '93a7bee8-e9f5-496e-a805-6fbe0e3b88c5')
            column(name: 'df_acl_scenario_uid', value: 'd86f2c59-a50c-4e54-826a-ee50aeb98904')
            column(name: 'df_acl_scenario_detail_uid', value: 'b4208d32-78d2-49cb-b3c6-de93d4ad0c93')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.4000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 15.6000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'f9afed9f-e76d-4a77-b6dc-c04cd54c6f6b')
            column(name: 'df_acl_scenario_uid', value: 'd86f2c59-a50c-4e54-826a-ee50aeb98904')
            column(name: 'df_acl_scenario_detail_uid', value: 'ead37bd8-a0c5-4bf0-ac8b-9e5737b77172')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 42.4000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.6000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'e5231d8a-6095-4dbf-bb0c-cb47f7aabe6f')
            column(name: 'df_acl_scenario_uid', value: 'd86f2c59-a50c-4e54-826a-ee50aeb98904')
            column(name: 'df_acl_scenario_detail_uid', value: 'ead37bd8-a0c5-4bf0-ac8b-9e5737b77172')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 42.4000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.6000000000)
        }

        rollback {
            dbRollback
        }
    }
}
