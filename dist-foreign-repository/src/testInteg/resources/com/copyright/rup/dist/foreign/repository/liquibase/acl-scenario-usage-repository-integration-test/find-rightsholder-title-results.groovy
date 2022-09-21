databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-08-30-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Insert test data for testFindRightsholderTitleResults')

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
            column(name: 'df_acl_fund_pool_uid', value: '6e7698ca-9535-4b58-9fe2-c4b65b104217')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '89a1ebf1-ca6f-48f4-9fd9-f9668484c761')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '828fbbd5-9413-4886-beef-7439801b8ce8')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
            column(name: 'df_acl_fund_pool_uid', value: '6e7698ca-9535-4b58-9fe2-c4b65b104217')
            column(name: 'df_acl_usage_batch_uid', value: '89a1ebf1-ca6f-48f4-9fd9-f9668484c761')
            column(name: 'df_acl_grant_set_uid', value: '828fbbd5-9413-4886-beef-7439801b8ce8')
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
            column(name: 'df_acl_scenario_detail_uid', value: 'f9c37312-cbfd-4a1f-8f3d-49e0f4986916')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
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
            column(name: 'df_acl_scenario_detail_uid', value: '6a06f2e5-aad6-499f-b5d4-c285bfe9abbc')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHSB117')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content', value: 1.0001230000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 2.3000000000)
            column(name: 'number_of_copies', value: 3.00000)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 1.0000000000)
            column(name: 'pub_type_weight', value: 1.00)
            column(name: 'survey_country', value: 'Spain')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'fd1c24b8-b2b6-4771-b8c1-8ffc3e8871be')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
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
            column(name: 'df_acl_scenario_detail_uid', value: '610940db-2be2-4d21-bd18-bfbed22aca8f')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
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
            column(name: 'df_acl_share_detail_uid', value: '620b4ec2-6d99-4eef-911a-b642e90545ab')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
            column(name: 'df_acl_scenario_detail_uid', value: 'f9c37312-cbfd-4a1f-8f3d-49e0f4986916')
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
            column(name: 'df_acl_share_detail_uid', value: 'd65fa895-a13d-4796-b7fd-e35ff396c681')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
            column(name: 'df_acl_scenario_detail_uid', value: 'f9c37312-cbfd-4a1f-8f3d-49e0f4986916')
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
            column(name: 'df_acl_share_detail_uid', value: '731e031b-28fa-4aa4-bb4a-cddfb86e385f')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
            column(name: 'df_acl_scenario_detail_uid', value: '6a06f2e5-aad6-499f-b5d4-c285bfe9abbc')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.5341365462)
            column(name: 'value_share', value: 0.4117647059)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 110.0000000000)
            column(name: 'gross_amount', value: 130.0000000000)
            column(name: 'service_fee_amount', value: 20.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '224b7b4d-c3db-4e91-b69a-a06f85090bba')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
            column(name: 'df_acl_scenario_detail_uid', value: 'fd1c24b8-b2b6-4771-b8c1-8ffc3e8871be')
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
            column(name: 'df_acl_share_detail_uid', value: '3593be2b-6ca4-44e5-ab97-c85f9cc2e10b')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
            column(name: 'df_acl_scenario_detail_uid', value: '610940db-2be2-4d21-bd18-bfbed22aca8f')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000002859)
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
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '90a16155-8225-4735-842c-89d3bc9028c5')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '69d829f5-8ad6-446a-a48d-a16e85ef1e5e')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'de1bf9c3-38d2-479a-872b-dd52d3e64ca6')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
            column(name: 'df_publication_type_uid', value: "f1f523ca-1b46-4d3a-842d-99252785187c")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '50ead363-fbe3-451d-a44a-c703e6ea83c0')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '6b9a3f04-b7fb-4fee-aee8-5d6ee2c8c624')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '68c60f2d-07f2-4175-a6fe-356cbc456043')
            column(name: 'df_acl_scenario_uid', value: '2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da')
            column(name: 'detail_licensee_class_id', value: 4)
            column(name: 'aggregate_licensee_class_id', value: 57)
        }

        rollback {
            dbRollback
        }
    }
}
