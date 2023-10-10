databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-12-12-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testWriteWorkSharesByAggLcCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '833739a1-7520-49ba-a877-c168c81da434')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1908ca9a-309d-4a3b-976f-3d20b747dd54')
            column(name: 'rh_account_number', value: 1000009482)
            column(name: 'name', value: 'Daedalus Enterprises Inc')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'bf3ff505-2b9f-46d1-ac30-f90b2a79ad99')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'a64ca439-546e-4015-9b7e-79ead5ec2a20')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'a3748007-4fee-4aac-ae8f-2afb30d5d16e')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'a0ffe3b2-2fe6-4eea-8ed9-9200b24973ea')
            column(name: 'df_acl_fund_pool_uid', value: 'bf3ff505-2b9f-46d1-ac30-f90b2a79ad99')
            column(name: 'df_acl_usage_batch_uid', value: 'a64ca439-546e-4015-9b7e-79ead5ec2a20')
            column(name: 'df_acl_grant_set_uid', value: 'a3748007-4fee-4aac-ae8f-2afb30d5d16e')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '433f6ed5-33d4-457a-a899-69a26adc10ca')
            column(name: 'df_acl_scenario_uid', value: 'a0ffe3b2-2fe6-4eea-8ed9-9200b24973ea')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'AGN674GHHSB100')
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
            column(name: 'df_acl_scenario_detail_uid', value: '0136cc1f-14d3-4bb2-bb0b-58eebeebe1c5')
            column(name: 'df_acl_scenario_uid', value: 'a0ffe3b2-2fe6-4eea-8ed9-9200b24973ea')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'AGN674GHHSB101')
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
            column(name: 'df_acl_scenario_detail_uid', value: 'c3d1e9c7-2550-4554-8d94-58ece123fb32')
            column(name: 'df_acl_scenario_uid', value: 'a0ffe3b2-2fe6-4eea-8ed9-9200b24973ea')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'AGN674GHHSB102')
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
            column(name: 'df_acl_scenario_detail_uid', value: 'c7067e93-7136-439d-b62a-4abd2ba3bfda')
            column(name: 'df_acl_scenario_uid', value: 'a0ffe3b2-2fe6-4eea-8ed9-9200b24973ea')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'AGN674GHHSB102')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'price', value: 14.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 22.0000000000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 33.0001230000)
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'number_of_copies', value: 6.00000)
            column(name: 'usage_age_weight', value: 0.99000)
            column(name: 'weighted_copies', value: 0.9000000000)
            column(name: 'pub_type_weight', value: 2.00)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '97948ce0-0bcd-46e9-b5bc-8c63e90e4827')
            column(name: 'df_acl_scenario_uid', value: 'a0ffe3b2-2fe6-4eea-8ed9-9200b24973ea')
            column(name: 'df_acl_scenario_detail_uid', value: '433f6ed5-33d4-457a-a899-69a26adc10ca')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 0.0067546423)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.5341365462)
            column(name: 'value_share', value: 0.4117647059)
            column(name: 'detail_share', value: 0.5833333345)
            column(name: 'net_amount', value: 84.1000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '5954b18f-e127-4d7d-a87d-1f18b0178c80')
            column(name: 'df_acl_scenario_uid', value: 'a0ffe3b2-2fe6-4eea-8ed9-9200b24973ea')
            column(name: 'df_acl_scenario_detail_uid', value: '433f6ed5-33d4-457a-a899-69a26adc10ca')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000009482)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 0.0067546423)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.5341365462)
            column(name: 'value_share', value: 0.4117647059)
            column(name: 'detail_share', value: 0.4166666667)
            column(name: 'net_amount', value: 84.1000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '8048da2c-927f-439a-9083-36cdcb13e87c')
            column(name: 'df_acl_scenario_uid', value: 'a0ffe3b2-2fe6-4eea-8ed9-9200b24973ea')
            column(name: 'df_acl_scenario_detail_uid', value: '0136cc1f-14d3-4bb2-bb0b-58eebeebe1c5')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 0.0067546423)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.5341365462)
            column(name: 'value_share', value: 0.4117647059)
            column(name: 'detail_share', value: 0.5833333345)
            column(name: 'net_amount', value: 84.1000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'a3a48df1-8cc0-465c-83ff-51fa00acbf44')
            column(name: 'df_acl_scenario_uid', value: 'a0ffe3b2-2fe6-4eea-8ed9-9200b24973ea')
            column(name: 'df_acl_scenario_detail_uid', value: '0136cc1f-14d3-4bb2-bb0b-58eebeebe1c5')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000009482)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.6666666667)
            column(name: 'value_share', value: 0.1666666667)
            column(name: 'detail_share', value: 0.6543876538)
            column(name: 'net_amount', value: 833.3333334000)
            column(name: 'gross_amount', value: 837.3333334000)
            column(name: 'service_fee_amount', value: 4.0000000000)
        }
 
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'fa425562-08ba-42f4-b575-ea0bfcad6073')
            column(name: 'df_acl_scenario_uid', value: 'a0ffe3b2-2fe6-4eea-8ed9-9200b24973ea')
            column(name: 'df_acl_scenario_detail_uid', value: 'c3d1e9c7-2550-4554-8d94-58ece123fb32')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'rh_account_number', value: 1000009482)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.1176470588)
            column(name: 'value_share', value: 0.0803212851)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 202.9175526000)
            column(name: 'gross_amount', value: 234.9175526000)
            column(name: 'service_fee_amount', value: 32.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '39384d2f-ab23-4cd9-8570-2baa0d7fe80b')
            column(name: 'df_acl_scenario_uid', value: 'a0ffe3b2-2fe6-4eea-8ed9-9200b24973ea')
            column(name: 'df_acl_scenario_detail_uid', value: 'c7067e93-7136-439d-b62a-4abd2ba3bfda')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.1176470588)
            column(name: 'value_share', value: 0.0803212851)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 202.9175526000)
            column(name: 'gross_amount', value: 234.9175526000)
            column(name: 'service_fee_amount', value: 32.0000000000)
        }

        rollback {
            dbRollback
        }
    }
}
