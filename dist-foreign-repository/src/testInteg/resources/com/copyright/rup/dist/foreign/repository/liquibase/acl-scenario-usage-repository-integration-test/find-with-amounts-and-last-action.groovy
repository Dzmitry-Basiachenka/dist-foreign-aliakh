databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-06-27-01', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testFindWithAmountsAndLastAction')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '1b48301c-e953-4af1-8ccb-8b3f9ed31544')
            column(name: 'name', value: 'ACL Fund Pool 202012')
            column(name: 'period', value: 202012)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '30d8a41f-9b01-42cd-8041-ce840512a040')
            column(name: 'name', value: 'ACL Usage Batch 202012')
            column(name: 'distribution_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'b175a252-2fb9-47da-8d40-8ad82107f546')
            column(name: 'name', value: 'ACL Grant Set 202012')
            column(name: 'grant_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'd18d7cab-8a69-4b60-af5a-0a0c99b8a4d3')
            column(name: 'df_acl_fund_pool_uid', value: '1b48301c-e953-4af1-8ccb-8b3f9ed31544')
            column(name: 'df_acl_usage_batch_uid', value: '30d8a41f-9b01-42cd-8041-ce840512a040')
            column(name: 'df_acl_grant_set_uid', value: 'b175a252-2fb9-47da-8d40-8ad82107f546')
            column(name: 'name', value: 'ACL Scenario 202012')
            column(name: 'description', value: 'some description')
            column(name: 'period_end_date', value: 202012)
            column(name: 'status_ind', value: 'SUBMITTED')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'copied_from', value: "Another Scenario")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '3f5c6098-e026-46a5-a279-a72a3c958783')
            column(name: 'df_acl_scenario_uid', value: 'd18d7cab-8a69-4b60-af5a-0a0c99b8a4d3')
            column(name: 'period_end_date', value: 202012)
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '2305195c-83c4-4ff9-9e91-6c230dbf4230')
            column(name: 'df_acl_scenario_uid', value: 'd18d7cab-8a69-4b60-af5a-0a0c99b8a4d3')
            column(name: 'period_end_date', value: 202012)
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
            column(name: 'df_acl_share_detail_uid', value: '1bcdcc38-ee66-491b-9e1a-4545ce7be8b5')
            column(name: 'df_acl_scenario_uid', value: 'd18d7cab-8a69-4b60-af5a-0a0c99b8a4d3')
            column(name: 'df_acl_scenario_detail_uid', value: '3f5c6098-e026-46a5-a279-a72a3c958783')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.00)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'service_fee_amount', value: 16.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'ec0223ee-b125-457c-8474-b0acddbd8858')
            column(name: 'df_acl_scenario_uid', value: 'd18d7cab-8a69-4b60-af5a-0a0c99b8a4d3')
            column(name: 'df_acl_scenario_detail_uid', value: '2305195c-83c4-4ff9-9e91-6c230dbf4230')
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
            column(name: 'net_amount', value: 168.00)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'service_fee_amount', value: 32.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_audit') {
            column(name: 'df_acl_scenario_audit_uid', value: 'dc99bc62-96f5-42cc-b585-4013ba7519c8')
            column(name: 'df_acl_scenario_uid', value: 'd18d7cab-8a69-4b60-af5a-0a0c99b8a4d3')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'action_reason', value: 'Usages were added to scenario')
            column(name: 'created_datetime', value: '2022-06-17 01:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_audit') {
            column(name: 'df_acl_scenario_audit_uid', value: 'c902eb15-e778-4d80-a5dd-b918173d488f')
            column(name: 'df_acl_scenario_uid', value: 'd18d7cab-8a69-4b60-af5a-0a0c99b8a4d3')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'action_reason', value: 'Scenario submitted for approval')
            column(name: 'created_datetime', value: '2022-06-17 02:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'e8a591d8-2803-4f9e-8cf5-4cd6257917e8')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '794481d7-41e5-44b5-929b-87f379b28ffa')
            column(name: 'name', value: 'ACL Usage Batch 202112')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'fb637adf-04a6-4bee-b195-8cbde93bf672')
            column(name: 'name', value: 'ACL Grant Set 202112')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '53a1c4e8-f1fe-4b17-877e-2d721b2059b5')
            column(name: 'df_acl_fund_pool_uid', value: 'e8a591d8-2803-4f9e-8cf5-4cd6257917e8')
            column(name: 'df_acl_usage_batch_uid', value: '794481d7-41e5-44b5-929b-87f379b28ffa')
            column(name: 'df_acl_grant_set_uid', value: 'fb637adf-04a6-4bee-b195-8cbde93bf672')
            column(name: 'name', value: 'ACL Scenario 202112')
            column(name: 'description', value: 'another description')
            column(name: 'period_end_date', value: 202112)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
            column(name: 'copied_from', value: "Another Scenario")
        }

        rollback {
            dbRollback
        }
    }
}
