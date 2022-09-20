databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-07-21-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testWriteAclScenarioDetailsCsvReport, testWriteAclScenarioDetailsEmptyCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '14edbc9b-1473-4fc9-95f6-07b3ef45e851')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b2ea68f6-3c15-4ae3-a04a-acdd5a236f0c')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '90a3a304-fd74-4a79-b0f6-bf5bb75c68f1')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '04528553-4882-4216-a5ae-ed6ce7b2043d')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'cd3906ee-05d2-448a-a8d9-8d6cc6de1f04')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
            column(name: 'df_acl_fund_pool_uid', value: '90a3a304-fd74-4a79-b0f6-bf5bb75c68f1')
            column(name: 'df_acl_usage_batch_uid', value: '04528553-4882-4216-a5ae-ed6ce7b2043d')
            column(name: 'df_acl_grant_set_uid', value: 'cd3906ee-05d2-448a-a8d9-8d6cc6de1f04')
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
            column(name: 'df_acl_scenario_detail_uid', value: 'bba31295-7e0c-40ed-bdd8-a530893d4ced')
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '4ad1a9cd-265f-4aca-9bee-474ab1a847fc')
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'ac764b6c-28fb-4f4d-88c6-a58295d249f4')
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
            column(name: 'period_end_date', value: 202206)
            column(name: 'original_detail_id', value: 'OGN674GHHSB109')
            column(name: 'wr_wrk_inst', value: 254327612)
            column(name: 'system_title', value: 'Current topics in library and information practice')
            column(name: 'detail_licensee_class_id', value: 4)
            column(name: 'publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'price', value: 5.1234567890)
            column(name: 'content', value: 1.0123456789)
            column(name: 'content_unit_price', value: 6.0123456789)
            column(name: 'number_of_copies', value: 3.00000)
            column(name: 'usage_age_weight', value: 0.99000)
            column(name: 'weighted_copies', value: 5.9000000000)
            column(name: 'pub_type_weight', value: 1.80)
            column(name: 'survey_country', value: 'Portugal')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'eea32899-4c51-4b13-986f-7f418386c219')
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
            column(name: 'df_acl_scenario_detail_uid', value: 'bba31295-7e0c-40ed-bdd8-a530893d4ced')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.5341365462)
            column(name: 'value_share', value: 0.4117647059)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.1000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '8a548fae-2eb4-4e30-af98-a865532508ab')
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
            column(name: 'df_acl_scenario_detail_uid', value: 'bba31295-7e0c-40ed-bdd8-a530893d4ced')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.6666666667)
            column(name: 'value_share', value: 0.1666666667)
            column(name: 'detail_share', value: 0.4166666667)
            column(name: 'net_amount', value: 833.3333334000)
            column(name: 'gross_amount', value: 837.3333334000)
            column(name: 'service_fee_amount', value: 4.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '09380181-8058-4c78-b150-20b5d017fb09')
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
            column(name: 'df_acl_scenario_detail_uid', value: '4ad1a9cd-265f-4aca-9bee-474ab1a847fc')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.1176470588)
            column(name: 'value_share', value: 0.0803212851)
            column(name: 'detail_share', value: 0.0989841720)
            column(name: 'net_amount', value: 202.9175526000)
            column(name: 'gross_amount', value: 234.9175526000)
            column(name: 'service_fee_amount', value: 32.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '49af2fb6-7b9a-4f92-8fad-2082fe5ceb85')
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
            column(name: 'df_acl_scenario_detail_uid', value: 'ac764b6c-28fb-4f4d-88c6-a58295d249f4')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 12)
            column(name: 'volume_weight', value: 4.0000000000)
            column(name: 'value_weight', value: 5.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.4705882353)
            column(name: 'value_share', value: 0.3855421687)
            column(name: 'detail_share', value: 0.4280652020)
            column(name: 'net_amount', value: 100.0000000000)
            column(name: 'gross_amount', value: 110.0000000000)
            column(name: 'service_fee_amount', value: 10.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: 'd36a7182-9c5e-44cd-b32f-4894bdb02d64')
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '645ec8b1-4fcb-4e20-a185-e53bcc79e4ef')
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '4fc455c6-c313-489b-98f8-de4a94d4836e')
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
            column(name: 'df_publication_type_uid', value: "f1f523ca-1b46-4d3a-842d-99252785187c")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '2ca6a8d7-052b-4fb7-8347-38d59f82f3e2')
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'b64137ec-14df-4c03-9954-004294b46a44')
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'd7181fca-b3fb-47e1-8c68-d02807a32a4b')
            column(name: 'df_acl_scenario_uid', value: 'cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a')
            column(name: 'detail_licensee_class_id', value: 4)
            column(name: 'aggregate_licensee_class_id', value: 57)
        }

        rollback {
            dbRollback
        }
    }
}
