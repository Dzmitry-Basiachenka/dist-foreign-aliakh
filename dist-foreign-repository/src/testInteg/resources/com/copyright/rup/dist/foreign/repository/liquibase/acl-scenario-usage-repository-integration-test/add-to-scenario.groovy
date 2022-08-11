databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-06-30-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testInsertScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '11c6590a-cea4-4cb6-a3ce-0f23a6f2e81c')
            column(name: 'name', value: 'ACL Fund Pool 202112')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '17970e6b-c020-4c84-9282-045ca465a8af')
            column(name: 'name', value: 'ACL Grant Set 202112')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'd18a7fba-e41d-4325-ad19-cbfc088a4e94')
            column(name: 'df_acl_grant_set_uid', value: '17970e6b-c020-4c84-9282-045ca465a8af')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Print&Digital')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '044560ea-2af4-4f25-ae58-516eecc136a2')
            column(name: 'df_acl_grant_set_uid', value: '17970e6b-c020-4c84-9282-045ca465a8af')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print&Digital')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '96d266e4-9633-4a6e-8e26-748596bd71f3')
            column(name: 'df_acl_grant_set_uid', value: '17970e6b-c020-4c84-9282-045ca465a8af')
            column(name: 'grant_status', value: 'DENY')
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
            column(name: 'df_acl_grant_detail_uid', value: '6e6ccdad-62e0-4d55-8dc7-e1fd6a227140')
            column(name: 'df_acl_grant_set_uid', value: '17970e6b-c020-4c84-9282-045ca465a8af')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 300820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '7c14afc1-3082-46fc-8df3-a96a55fdfaf3')
            column(name: 'df_acl_grant_set_uid', value: '17970e6b-c020-4c84-9282-045ca465a8af')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 107039807)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '0f65b9b0-308f-4f73-b232-773a98baba2e')
            column(name: 'name', value: 'ACL Usage Batch 202112')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '1c222dda-9137-46a8-acdc-730785e4e782')
            column(name: 'df_acl_usage_batch_uid', value: '0f65b9b0-308f-4f73-b232-773a98baba2e')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202112)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0111')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 10)
            column(name: 'quantity', value: 2)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        //will be excluded by wrWrkInst
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '6497c851-7d5e-4832-9166-124982d08228')
            column(name: 'df_acl_usage_batch_uid', value: '0f65b9b0-308f-4f73-b232-773a98baba2e')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202112)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0112')
            column(name: 'wr_wrk_inst', value: 823333789)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 10)
            column(name: 'quantity', value: 2)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        //will be excluded from scenario by quantity
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '639a476b-4a5f-4712-a5c0-a92e75c60622')
            column(name: 'df_acl_usage_batch_uid', value: '0f65b9b0-308f-4f73-b232-773a98baba2e')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202112)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0113')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2000)
            column(name: 'quantity', value: 200)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        //will be excluded from scenario by usage age weight
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '49166318-3c28-4ec0-b368-9383e99edce1')
            column(name: 'df_acl_usage_batch_uid', value: '0f65b9b0-308f-4f73-b232-773a98baba2e')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 200806)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0114')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        //will be excluded from scenario by grant status
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: 'c386408a-3f71-4958-b82f-c137922a0c43')
            column(name: 'df_acl_usage_batch_uid', value: '0f65b9b0-308f-4f73-b232-773a98baba2e')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 201812)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0115')
            column(name: 'wr_wrk_inst', value: 122825555)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        //will be excluded from scenario by grant status and type of use
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: 'e4fd24d3-c52c-4d1a-a07c-120c03af4ba1')
            column(name: 'df_acl_usage_batch_uid', value: '0f65b9b0-308f-4f73-b232-773a98baba2e')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 201812)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0116')
            column(name: 'wr_wrk_inst', value: 107039807)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 20)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'dec62df4-6a8f-4c59-ad65-2a5e06b3924d')
            column(name: 'df_acl_fund_pool_uid', value: '11c6590a-cea4-4cb6-a3ce-0f23a6f2e81c')
            column(name: 'df_acl_usage_batch_uid', value: '0f65b9b0-308f-4f73-b232-773a98baba2e')
            column(name: 'df_acl_grant_set_uid', value: '17970e6b-c020-4c84-9282-045ca465a8af')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '83357502-0b68-4526-b36c-ccd8e2482644')
            column(name: 'df_acl_scenario_uid', value: 'dec62df4-6a8f-4c59-ad65-2a5e06b3924d')
            column(name: 'period_prior', value: 1)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '32a30480-6c8b-4880-8d8b-95e45195f09c')
            column(name: 'df_acl_scenario_uid', value: 'dec62df4-6a8f-4c59-ad65-2a5e06b3924d')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '87a03cc0-f901-4184-90db-07cf698761b0')
            column(name: 'df_acl_scenario_uid', value: 'dec62df4-6a8f-4c59-ad65-2a5e06b3924d')
            column(name: 'period_prior', value: 2)
            column(name: 'weight', value: 0.5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '51ca0b4c-ff25-4a9e-9fd7-33b7143f0cde')
            column(name: 'df_acl_scenario_uid', value: 'dec62df4-6a8f-4c59-ad65-2a5e06b3924d')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'aad78b5c-5c74-4da4-82e5-59a135bdc208')
            column(name: 'df_acl_scenario_uid', value: 'dec62df4-6a8f-4c59-ad65-2a5e06b3924d')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '109d35c4-9072-428e-8b8c-54b1defd58b1')
            column(name: 'name', value: 'ACL Fund Pool 202112-2')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '69f63054-42d4-4292-9a92-e055eefc00aa')
            column(name: 'name', value: 'ACL Grant Set 202112-2')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '1c300c88-8ad4-4391-9dbc-d0a683754e6c')
            column(name: 'df_acl_grant_set_uid', value: '69f63054-42d4-4292-9a92-e055eefc00aa')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Print&Digital')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'ea499399-ebbd-4dc5-ade3-0f0fd34aa71e')
            column(name: 'df_acl_grant_set_uid', value: '69f63054-42d4-4292-9a92-e055eefc00aa')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print&Digital')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '8fd1a35b-7288-4701-a4ce-6bf67b0507ea')
            column(name: 'df_acl_grant_set_uid', value: '69f63054-42d4-4292-9a92-e055eefc00aa')
            column(name: 'grant_status', value: 'DENY')
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
            column(name: 'df_acl_grant_detail_uid', value: 'a591a138-4ddc-4a1c-8328-58c5636116c2')
            column(name: 'df_acl_grant_set_uid', value: '69f63054-42d4-4292-9a92-e055eefc00aa')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 300820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '1326041c-258e-4c7d-907b-6340246420fc')
            column(name: 'name', value: 'ACL Usage Batch 202111')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '859aaf80-a84c-439d-98df-8ef63b0cb5d5')
            column(name: 'df_acl_fund_pool_uid', value: '109d35c4-9072-428e-8b8c-54b1defd58b1')
            column(name: 'df_acl_usage_batch_uid', value: '1326041c-258e-4c7d-907b-6340246420fc')
            column(name: 'df_acl_grant_set_uid', value: '69f63054-42d4-4292-9a92-e055eefc00aa')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '2bdcf31a-90cd-4186-8137-091a38f173db')
            column(name: 'df_acl_scenario_uid', value: '859aaf80-a84c-439d-98df-8ef63b0cb5d5')
            column(name: 'period_prior', value: 1)
            column(name: 'weight', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '7140cd37-e85f-4c2c-8b55-6e46c78540f2')
            column(name: 'df_acl_scenario_uid', value: '859aaf80-a84c-439d-98df-8ef63b0cb5d5')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '9408f5c8-46df-44fc-a2bc-beed282467d3')
            column(name: 'df_acl_scenario_uid', value: '859aaf80-a84c-439d-98df-8ef63b0cb5d5')
            column(name: 'period_prior', value: 2)
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '408c5f25-7511-40b9-ae8e-6d915dc345e8')
            column(name: 'df_acl_scenario_uid', value: '859aaf80-a84c-439d-98df-8ef63b0cb5d5')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '2c224a53-350b-49fb-b04a-ce5c90f99acf')
            column(name: 'df_acl_scenario_uid', value: '859aaf80-a84c-439d-98df-8ef63b0cb5d5')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        rollback {
            dbRollback
        }
    }
}
