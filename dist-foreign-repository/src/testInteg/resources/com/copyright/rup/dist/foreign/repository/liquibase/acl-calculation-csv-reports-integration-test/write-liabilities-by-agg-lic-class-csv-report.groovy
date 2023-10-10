databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-10-05-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testWriteAclLiabilitiesByAggLicClassReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '833739a1-7520-49ba-a877-c168c81da434')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a520ddc0-06a1-49e3-8f13-a946bf2eb00c')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '8f5db28a-1ea5-4770-bcd3-199bd8c4618f')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '2b3e85bd-b40f-4301-b473-b7b45883cc42')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '40559c02-9f46-401d-bfe3-0f7469e7e2fe')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '06fee547-bfc4-4f2a-9578-58c03821e217')
            column(name: 'df_acl_fund_pool_uid', value: '40559c02-9f46-401d-bfe3-0f7469e7e2fe')
            column(name: 'df_acl_usage_batch_uid', value: '8f5db28a-1ea5-4770-bcd3-199bd8c4618f')
            column(name: 'df_acl_grant_set_uid', value: '2b3e85bd-b40f-4301-b473-b7b45883cc42')
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
            column(name: 'df_acl_grant_set_uid', value: 'ea52f75f-6854-4087-93cd-7cfba6a464c8')
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
            column(name: 'df_acl_scenario_uid', value: '6dbd30f7-91f6-4949-a74c-cfbac5e466ac')
            column(name: 'df_acl_fund_pool_uid', value: 'dfaa0834-fe3a-42d7-9d6f-b3a1733927ed')
            column(name: 'df_acl_usage_batch_uid', value: '8f5db28a-1ea5-4770-bcd3-199bd8c4618f')
            column(name: 'df_acl_grant_set_uid', value: 'ea52f75f-6854-4087-93cd-7cfba6a464c8')
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
            column(name: 'df_acl_usage_batch_uid', value: '8f5db28a-1ea5-4770-bcd3-199bd8c4618f')
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
            column(name: 'df_acl_fund_pool_uid', value: '649f0d35-0468-45ac-b5aa-ef2ab2d18dc4')
            column(name: 'name', value: 'JACDCL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'JACDCL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'd86f2c59-a50c-4e54-826a-ee50aeb98904')
            column(name: 'df_acl_fund_pool_uid', value: '649f0d35-0468-45ac-b5aa-ef2ab2d18dc4')
            column(name: 'df_acl_usage_batch_uid', value: '8f5db28a-1ea5-4770-bcd3-199bd8c4618f')
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
            column(name: 'df_acl_scenario_detail_uid', value: '37d4db65-e467-45c3-a775-ff9b23b014de')
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
            column(name: 'df_acl_share_detail_uid', value: 'bee31a35-c90f-48e1-8342-9fb05c2283f9')
            column(name: 'df_acl_scenario_uid', value: '06fee547-bfc4-4f2a-9578-58c03821e217')
            column(name: 'df_acl_scenario_detail_uid', value: '37d4db65-e467-45c3-a775-ff9b23b014de')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
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
            column(name: 'df_acl_share_detail_uid', value: '1991ce71-8645-41de-84ea-c6fcd5bcf732')
            column(name: 'df_acl_scenario_uid', value: '06fee547-bfc4-4f2a-9578-58c03821e217')
            column(name: 'df_acl_scenario_detail_uid', value: '37d4db65-e467-45c3-a775-ff9b23b014de')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
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
            column(name: 'df_acl_scenario_detail_uid', value: 'cc2d4654-1ccd-4d37-90c5-d00530f34113')
            column(name: 'df_acl_scenario_uid', value: '6dbd30f7-91f6-4949-a74c-cfbac5e466ac')
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
            column(name: 'df_acl_scenario_uid', value: '6dbd30f7-91f6-4949-a74c-cfbac5e466ac')
            column(name: 'df_acl_scenario_detail_uid', value: 'cc2d4654-1ccd-4d37-90c5-d00530f34113')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122820638)
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
            column(name: 'df_acl_share_detail_uid', value: 'c24a9aee-3298-4a8a-9bca-a43d013197aa')
            column(name: 'df_acl_scenario_uid', value: '6dbd30f7-91f6-4949-a74c-cfbac5e466ac')
            column(name: 'df_acl_scenario_detail_uid', value: 'cc2d4654-1ccd-4d37-90c5-d00530f34113')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122820638)
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
            column(name: 'df_acl_scenario_detail_uid', value: 'aa8136bd-0d0a-4efe-9742-568f6f93c0d5')
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
            column(name: 'df_acl_share_detail_uid', value: '2dde529d-9975-4d79-8e3f-edb464c9e6f5')
            column(name: 'df_acl_scenario_uid', value: 'ca1c5532-eb1c-440a-8fbc-8e595dbea5cb')
            column(name: 'df_acl_scenario_detail_uid', value: 'aa8136bd-0d0a-4efe-9742-568f6f93c0d5')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122820638)
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
            column(name: 'df_acl_share_detail_uid', value: '40515a5d-b55e-444f-a25a-ca17dde677de')
            column(name: 'df_acl_scenario_uid', value: 'ca1c5532-eb1c-440a-8fbc-8e595dbea5cb')
            column(name: 'df_acl_scenario_detail_uid', value: 'aa8136bd-0d0a-4efe-9742-568f6f93c0d5')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122820638)
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
            column(name: 'df_acl_scenario_detail_uid', value: '040b35bf-99e9-41d5-bd5e-41795246b8d1')
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
            column(name: 'df_acl_share_detail_uid', value: '693ca357-c6ba-4a5c-badd-e417c6c37aa4')
            column(name: 'df_acl_scenario_uid', value: 'd86f2c59-a50c-4e54-826a-ee50aeb98904')
            column(name: 'df_acl_scenario_detail_uid', value: '040b35bf-99e9-41d5-bd5e-41795246b8d1')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122820638)
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'a05e5cac-8e95-4c3b-b834-40f83a8c5834')
            column(name: 'df_acl_scenario_uid', value: 'd86f2c59-a50c-4e54-826a-ee50aeb98904')
            column(name: 'df_acl_scenario_detail_uid', value: '040b35bf-99e9-41d5-bd5e-41795246b8d1')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122820638)
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

        rollback {
            dbRollback
        }
    }
}
