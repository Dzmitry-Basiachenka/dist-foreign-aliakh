databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-11-18-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserting test data for testFindByScenarioIdNullSearchValue, testFindCountByScenarioIdNullSearchValue')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '2a6d77ca-9ea7-4b89-bbb7-c9453cd5cfa6')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '0a96dace-40df-4acf-9af9-c3823dcff72c')
            column(name: 'rh_account_number', value: 7000873612)
            column(name: 'name', value: 'Brewin Books Ltd')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'eb1b07f3-5610-4719-b217-b5a727eb7484')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'd684ae60-ec84-422b-9116-ceec167ffd31')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '780eb711-0c92-4dfe-a949-34702e675780')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'd03cb6d8-d396-4cae-bb8d-12488408e4c7')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '70776cb3-fc9e-4fef-a9a3-15f2d33916cf')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '8e93510e-a90c-4808-8d7c-f0b151f2c4a1')
            column(name: 'df_acl_fund_pool_uid', value: '780eb711-0c92-4dfe-a949-34702e675780')
            column(name: 'df_acl_usage_batch_uid', value: 'd03cb6d8-d396-4cae-bb8d-12488408e4c7')
            column(name: 'df_acl_grant_set_uid', value: '70776cb3-fc9e-4fef-a9a3-15f2d33916cf')
            column(name: 'name', value: 'ACL Scenario 202212')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '0dbe2801-ebec-42cf-838a-71d6b95c4f36')
            column(name: 'df_acl_scenario_uid', value: '8e93510e-a90c-4808-8d7c-f0b151f2c4a1')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHSB107')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'price', value: 10.0000000000)
            column(name: 'content', value: 11.0000000000)
            column(name: 'content_unit_price', value: 2.0000000000)
            column(name: 'number_of_copies', value: 1.00000)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 1.0000000000)
            column(name: 'pub_type_weight', value: 1.00)
            column(name: 'survey_country', value: 'United States')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'type_of_use', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'cc7c7289-9d2c-470a-91cd-352adf5c80b6')
            column(name: 'df_acl_scenario_uid', value: '8e93510e-a90c-4808-8d7c-f0b151f2c4a1')
            column(name: 'df_acl_scenario_detail_uid', value: '0dbe2801-ebec-42cf-838a-71d6b95c4f36')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000005413)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.0000000001)
            column(name: 'value_share', value: 0.1000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.0000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '661e71f2-970e-4951-b91e-84daad897cc0')
            column(name: 'df_acl_scenario_uid', value: '8e93510e-a90c-4808-8d7c-f0b151f2c4a1')
            column(name: 'df_acl_scenario_detail_uid', value: '0dbe2801-ebec-42cf-838a-71d6b95c4f36')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'payee_account_number', value: 2000017010)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.0000000002)
            column(name: 'value_share', value: 0.2000000000)
            column(name: 'detail_share', value: 0.8000000000)
            column(name: 'net_amount', value: 6.0000000000)
            column(name: 'gross_amount', value: 10.0000000000)
            column(name: 'service_fee_amount', value: 4.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '49686798-5071-49a7-b8e0-210afe5aa1f6')
            column(name: 'df_acl_scenario_uid', value: '8e93510e-a90c-4808-8d7c-f0b151f2c4a1')
            column(name: 'period_end_date', value: 202206)
            column(name: 'original_detail_id', value: 'OGN674GHHSB108')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'price', value: 5.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 6.0000000000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 4.0000000000)
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'number_of_copies', value: 2.00000)
            column(name: 'usage_age_weight', value: 0.99000)
            column(name: 'weighted_copies', value: 0.9000000000)
            column(name: 'pub_type_weight', value: 2.00)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'type_of_use', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '194cac9b-fe8d-425a-bc3b-e1f403380ddd')
            column(name: 'df_acl_scenario_uid', value: '8e93510e-a90c-4808-8d7c-f0b151f2c4a1')
            column(name: 'df_acl_scenario_detail_uid', value: '49686798-5071-49a7-b8e0-210afe5aa1f6')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'rh_account_number', value: 7000873612)
            column(name: 'payee_account_number', value: 7000873612)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.0000000002)
            column(name: 'value_share', value: 0.2000000000)
            column(name: 'detail_share', value: 0.7000000000)
            column(name: 'net_amount', value: 100.0000000000)
            column(name: 'gross_amount', value: 130.0000000000)
            column(name: 'service_fee_amount', value: 30.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '26e50617-bec0-4ed9-968e-2cbca2abe506')
            column(name: 'df_acl_scenario_uid', value: '8e93510e-a90c-4808-8d7c-f0b151f2c4a1')
            column(name: 'df_acl_scenario_detail_uid', value: '49686798-5071-49a7-b8e0-210afe5aa1f6')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'rh_account_number', value: 7000873612)
            column(name: 'payee_account_number', value: 7000873612)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.0000000003)
            column(name: 'value_share', value: 0.3000000000)
            column(name: 'detail_share', value: 0.9000000000)
            column(name: 'net_amount', value: 110.0000000000)
            column(name: 'gross_amount', value: 140.0000000000)
            column(name: 'service_fee_amount', value: 30.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '0c4e6ca9-1bfc-47fd-9ed2-2485e426dd10')
            column(name: 'df_acl_scenario_uid', value: '8e93510e-a90c-4808-8d7c-f0b151f2c4a1')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '85290785-1029-4efa-9113-1daef1420740')
            column(name: 'df_acl_scenario_uid', value: '8e93510e-a90c-4808-8d7c-f0b151f2c4a1')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '33371776-4499-4392-ab66-d3a44cbc5af9')
            column(name: 'df_acl_scenario_uid', value: '8e93510e-a90c-4808-8d7c-f0b151f2c4a1')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '3afbf740-7492-4d41-8d6c-7aa6fe7e3f58')
            column(name: 'df_acl_scenario_uid', value: '8e93510e-a90c-4808-8d7c-f0b151f2c4a1')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        rollback {
            dbRollback
        }
    }
}
