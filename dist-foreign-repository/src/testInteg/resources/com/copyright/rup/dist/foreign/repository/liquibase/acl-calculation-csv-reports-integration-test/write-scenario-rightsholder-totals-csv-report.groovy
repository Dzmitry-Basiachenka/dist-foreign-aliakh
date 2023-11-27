databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-09-05-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testWriteAclScenarioRightsholderTotalsCsvReport, ' +
                'testWriteAclScenarioRightsholderTotalsEmptyCsvReport')

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

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1908ca9a-309d-4a3b-976f-3d20b747dd54')
            column(name: 'rh_account_number', value: 1000009482)
            column(name: 'name', value: 'Daedalus Enterprises Inc')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5252eaad-2e7e-45cc-9784-fb207ffb374f')
            column(name: 'rh_account_number', value: 2000149570)
            column(name: 'name', value: 'National Association for Management')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '9a3c2bec-cb00-4fc9-a538-ed71a58c37be')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '1db40e97-44ec-49d5-9180-f3dccfe2032f')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '85784ff2-b9a3-4b68-9543-a7e8a2aa7e7b')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
            column(name: 'df_acl_fund_pool_uid', value: '9a3c2bec-cb00-4fc9-a538-ed71a58c37be')
            column(name: 'df_acl_usage_batch_uid', value: '1db40e97-44ec-49d5-9180-f3dccfe2032f')
            column(name: 'df_acl_grant_set_uid', value: '85784ff2-b9a3-4b68-9543-a7e8a2aa7e7b')
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
            column(name: 'df_acl_scenario_detail_uid', value: '72d8f20c-5bf1-470d-b694-a4e957496ded')
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
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
            column(name: 'df_acl_scenario_detail_uid', value: '45c1987f-f781-497c-aacd-97d745da1149')
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
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
            column(name: 'df_acl_scenario_detail_uid', value: 'fc2fd7f7-cba5-421d-bdc9-de12aac939cf')
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
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
            column(name: 'df_acl_share_detail_uid', value: 'a2617b29-d7af-4a6c-a08f-bb259fea294b')
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
            column(name: 'df_acl_scenario_detail_uid', value: '72d8f20c-5bf1-470d-b694-a4e957496ded')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 0.5341365462)
            column(name: 'value_share', value: 0.4117647059)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.1000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
            column(name: 'payee_account_number', value: 1000009482)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '70d28d47-c1e5-4878-a586-cb6d849de6e8')
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
            column(name: 'df_acl_scenario_detail_uid', value: '72d8f20c-5bf1-470d-b694-a4e957496ded')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 0.6666666667)
            column(name: 'value_share', value: 0.1666666667)
            column(name: 'detail_share', value: 0.4166666667)
            column(name: 'net_amount', value: 833.3333334000)
            column(name: 'gross_amount', value: 837.3333334000)
            column(name: 'service_fee_amount', value: 4.0000000000)
            column(name: 'payee_account_number', value: 2000149570)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'ced22c5d-e2c6-4905-a581-38bf82217a5d')
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
            column(name: 'df_acl_scenario_detail_uid', value: '45c1987f-f781-497c-aacd-97d745da1149')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 0.1176470588)
            column(name: 'value_share', value: 0.0803212851)
            column(name: 'detail_share', value: 0.0989841720)
            column(name: 'net_amount', value: 202.9175526000)
            column(name: 'gross_amount', value: 234.9175526000)
            column(name: 'service_fee_amount', value: 32.0000000000)
            column(name: 'payee_account_number', value: 2000149570)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'f6b22a77-c1ae-46bc-b908-f12371e283c0')
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
            column(name: 'df_acl_scenario_detail_uid', value: 'fc2fd7f7-cba5-421d-bdc9-de12aac939cf')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 254327612)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 12)
            column(name: 'volume_weight', value: 4.0000000000)
            column(name: 'value_weight', value: 5.0000000000)
            column(name: 'volume_share', value: 0.4705882353)
            column(name: 'value_share', value: 0.3855421687)
            column(name: 'detail_share', value: 0.4280652020)
            column(name: 'net_amount', value: 100.0000000000)
            column(name: 'gross_amount', value: 110.0000000000)
            column(name: 'service_fee_amount', value: 10.0000000000)
            column(name: 'payee_account_number', value: 1000028511)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '6683602c-6168-4434-85f3-6adc04f02996')
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '88dd269b-33fd-47b4-8b5e-aa2136edbf1e')
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '975d5b25-eb0c-48b8-94fa-ec9d44f80c7b')
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
            column(name: 'df_publication_type_uid', value: "f1f523ca-1b46-4d3a-842d-99252785187c")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'a0a45ccc-0374-4edf-be9b-923afbd16b4d')
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '015da6d4-4065-4930-926c-996bbd30252d')
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '502aca76-a587-4f8d-ac4f-e1d0139b1c18')
            column(name: 'df_acl_scenario_uid', value: 'ac2b822b-c8d1-4599-acdc-e1d13713a6c9')
            column(name: 'detail_licensee_class_id', value: 4)
            column(name: 'aggregate_licensee_class_id', value: 57)
        }

        rollback {
            dbRollback
        }
    }
}
