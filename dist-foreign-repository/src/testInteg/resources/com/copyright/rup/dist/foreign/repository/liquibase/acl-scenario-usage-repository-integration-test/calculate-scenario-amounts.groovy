databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-07-18-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Insert test data for testCalculateScenarioAmounts')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'f2b3fd26-9e1c-48d7-968e-016c55d4ff13')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '957aebee-c642-463f-9e7f-9de8afca6c07')
            column(name: 'df_acl_fund_pool_uid', value: 'f2b3fd26-9e1c-48d7-968e-016c55d4ff13')
            column(name: 'detail_licensee_class_id', value: '1')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 1200.26)
            column(name: 'gross_amount', value: 1550.51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'e7b2d365-b66e-4027-8d3e-314ad6114983')
            column(name: 'df_acl_fund_pool_uid', value: 'f2b3fd26-9e1c-48d7-968e-016c55d4ff13')
            column(name: 'detail_licensee_class_id', value: '2')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 20500.26)
            column(name: 'gross_amount', value: 27895.51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'b2537cec-0f78-44f5-bd94-626f7911f64d')
            column(name: 'df_acl_fund_pool_uid', value: 'f2b3fd26-9e1c-48d7-968e-016c55d4ff13')
            column(name: 'detail_licensee_class_id', value: '2')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 21500.00)
            column(name: 'gross_amount', value: 29895.91)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'd89f67d7-59bf-4651-b040-c3ba10d42fde')
            column(name: 'df_acl_fund_pool_uid', value: 'f2b3fd26-9e1c-48d7-968e-016c55d4ff13')
            column(name: 'detail_licensee_class_id', value: '12')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 120500.32)
            column(name: 'gross_amount', value: 152895.68)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'cf38ea7f-e3c5-4b57-b157-be13c113f023')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '50648820-a66c-4aea-8143-31fff69531ac4')
            column(name: 'df_acl_grant_set_uid', value: 'cf38ea7f-e3c5-4b57-b157-be13c113f023')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Different RH')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'adf4ce62-6031-49bf-aed7-a5f49e60f320')
            column(name: 'df_acl_grant_set_uid', value: 'cf38ea7f-e3c5-4b57-b157-be13c113f023')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Different RH')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '4a8bddff-ad60-407e-8235-76ab51c7e42b')
            column(name: 'df_acl_grant_set_uid', value: 'cf38ea7f-e3c5-4b57-b157-be13c113f023')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 122825555)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'b4420946-6bc1-40d5-9b44-152d01f99c67')
            column(name: 'df_acl_grant_set_uid', value: 'cf38ea7f-e3c5-4b57-b157-be13c113f023')
            column(name: 'grant_status', value: 'DENY')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 122825555)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'f5b2dd11-f85e-443b-97a1-3f8c6270ab97')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'df_acl_fund_pool_uid', value: 'f2b3fd26-9e1c-48d7-968e-016c55d4ff13')
            column(name: 'df_acl_usage_batch_uid', value: 'f5b2dd11-f85e-443b-97a1-3f8c6270ab97')
            column(name: 'df_acl_grant_set_uid', value: 'cf38ea7f-e3c5-4b57-b157-be13c113f023')
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
            column(name: 'df_acl_scenario_detail_uid', value: '9a30d051-24e8-4fb2-8e9a-31fce9653be7')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN974GHHSB100')
            column(name: 'wr_wrk_inst', value: 122825555)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'pub_type_weight', value: 2)
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'usage_quantity', value: 3)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 3.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '5403bc0d-cb82-4bd9-82bd-0e637272e6ce')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'df_acl_scenario_detail_uid', value: '9a30d051-24e8-4fb2-8e9a-31fce9653be7')
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
            column(name: 'df_acl_scenario_detail_uid', value: 'fdac5a59-0e8f-4416-bee6-f6883a80a917')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN974GHHSB101')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'pub_type_weight', value: 1.9)
            column(name: 'content_unit_price', value: 6.3000000000)
            column(name: 'usage_quantity', value: 5)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 5.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '1f9d85d6-3bb2-402f-8cb7-800c838e7dd7')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'df_acl_scenario_detail_uid', value: 'fdac5a59-0e8f-4416-bee6-f6883a80a917')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 5.0000000000)
            column(name: 'value_weight', value: 59.8500000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '9324d7f6-a589-44b6-bff0-83038bc8d379')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'df_acl_scenario_detail_uid', value: 'fdac5a59-0e8f-4416-bee6-f6883a80a917')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 5.0000000000)
            column(name: 'value_weight', value: 59.8500000000)
            column(name: 'volume_share', value: 0.5000000000)
            column(name: 'value_share', value: 0.2861035422)
            column(name: 'detail_share', value: 0.3930517711)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'f1a8dd33-2a1d-4a5a-8354-5b15eb39ec9a')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN974GHHSB102')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'pub_type_weight', value: 1.9)
            column(name: 'content_unit_price', value: 21.0000000000)
            column(name: 'usage_quantity', value: 3)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 3.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '42ccd623-5fda-4b7c-87d8-137a3468737d')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'df_acl_scenario_detail_uid', value: 'f1a8dd33-2a1d-4a5a-8354-5b15eb39ec9a')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 3.0000000000)
            column(name: 'value_weight', value: 119.7000000000)
            column(name: 'volume_share', value: 0.3000000000)
            column(name: 'value_share', value: 0.5722070845)
            column(name: 'detail_share', value: 0.4361035422)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'e8ebc3f7-9075-4bf4-bdb3-00e9d3f780f4')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN974GHHSB103')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 5)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'pub_type_weight', value: 2)
            column(name: 'content_unit_price', value: 7.4100000000)
            column(name: 'usage_quantity', value: 2)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 2.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'eb549882-838f-4c31-8618-471fb4b0f17f')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'df_acl_scenario_detail_uid', value: 'e8ebc3f7-9075-4bf4-bdb3-00e9d3f780f4')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 2.0000000000)
            column(name: 'value_weight', value: 29.6400000000)
            column(name: 'volume_share', value: 0.2000000000)
            column(name: 'value_share', value: 0.1416893733)
            column(name: 'detail_share', value: 0.1708446866)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '4ec69906-4f1b-4ea1-a49f-f49bbe81bd9a')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'period_prior', value: 1)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: 'ace798b3-315b-43a2-81bb-73d986e4d2c0')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '99fff74d-0347-460d-8b70-a92c501379ba')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'period_prior', value: 2)
            column(name: 'weight', value: 0.5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '5cac99db-64aa-4742-a9d0-d197fd702b87')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'e612ad44-8084-42d7-b2aa-c77ee8eda4ef')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'df_publication_type_uid', value: "aef4304b-6722-4047-86e0-8c84c72f096d")
            column(name: 'weight', value: 1.9)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'd8d95f6f-12e7-47e5-8b75-fc9d1a77597d')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '28a87f06-8715-4f27-9123-8db6846688d8')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'eeb70f77-78c9-4de6-bdf5-c38732e0a997')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'a070faed-ea7c-4695-ac36-394142aad6fa')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'detail_licensee_class_id', value: 5)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '03aff9d9-86af-4734-bcb7-de3133baec36')
            column(name: 'df_acl_scenario_uid', value: '742c3061-50b6-498a-a440-17c3ba5bf7eb')
            column(name: 'detail_licensee_class_id', value: 12)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        rollback {
            dbRollback
        }
    }
}
