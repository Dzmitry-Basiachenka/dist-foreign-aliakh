databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-09-07-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindRightsholderDetailsResults')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '14edbc9b-1473-4fc9-95f6-07b3ef45e851')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'b715fdaf-c3a4-4a2e-895c-ea14dbb34942')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'd38cda3e-8354-421b-b246-379a713a003a')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'e67cbe0c-ac26-4c1c-8a3c-87d237b3d5d0')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'ae790945-f541-4149-9ac2-9a5d29516c38')
            column(name: 'df_acl_fund_pool_uid', value: 'b715fdaf-c3a4-4a2e-895c-ea14dbb34942')
            column(name: 'df_acl_usage_batch_uid', value: 'd38cda3e-8354-421b-b246-379a713a003a')
            column(name: 'df_acl_grant_set_uid', value: 'e67cbe0c-ac26-4c1c-8a3c-87d237b3d5d0')
            column(name: 'name', value: 'ACL Scenario 202212')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '2c13581a-3aff-4c94-b918-eb13264c990c')
            column(name: 'df_acl_scenario_uid', value: 'ae790945-f541-4149-9ac2-9a5d29516c38')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHSB107')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'price', value: 10.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 5.0000000000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 2.0000000000)
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'number_of_copies', value: 1.00000)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 1.0000000000)
            column(name: 'pub_type_weight', value: 1.00)
            column(name: 'survey_country', value: 'United States')
            column(name: 'reported_type_of_use', value: 'COPY_FOR_MYSELF')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '3cb65d25-af9a-4803-9ad5-04aa39aed4f0')
            column(name: 'df_acl_scenario_uid', value: 'ae790945-f541-4149-9ac2-9a5d29516c38')
            column(name: 'period_end_date', value: 202206)
            column(name: 'original_detail_id', value: 'OGN674GHHSB108')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'price', value: 6.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 2.0000000000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 3.0000000000)
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'number_of_copies', value: 2.00000)
            column(name: 'usage_age_weight', value: 0.99000)
            column(name: 'weighted_copies', value: 0.9000000000)
            column(name: 'pub_type_weight', value: 2.00)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '4065deb9-a7ea-4b9e-a96c-f3f911dfad6b')
            column(name: 'df_acl_scenario_uid', value: 'ae790945-f541-4149-9ac2-9a5d29516c38')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHSB109')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'price', value: 12.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 3.0000000000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 4.0000000000)
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'number_of_copies', value: 3.00000)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 1.0000000000)
            column(name: 'pub_type_weight', value: 1.00)
            column(name: 'survey_country', value: 'France')
            column(name: 'reported_type_of_use', value: 'DIGITAL_SHARING_OTHER')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '391eae7e-60f6-4192-9a2b-592d54ba0b3d')
            column(name: 'df_acl_scenario_uid', value: 'ae790945-f541-4149-9ac2-9a5d29516c38')
            column(name: 'df_acl_scenario_detail_uid', value: '2c13581a-3aff-4c94-b918-eb13264c990c')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000002859)
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
            column(name: 'df_acl_share_detail_uid', value: '3a540b19-d229-40e7-9b52-6235b2357ed2')
            column(name: 'df_acl_scenario_uid', value: 'ae790945-f541-4149-9ac2-9a5d29516c38')
            column(name: 'df_acl_scenario_detail_uid', value: '2c13581a-3aff-4c94-b918-eb13264c990c')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000002859)
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'eae1c5d0-8d18-486f-88ad-c8867678cd5a')
            column(name: 'df_acl_scenario_uid', value: 'ae790945-f541-4149-9ac2-9a5d29516c38')
            column(name: 'df_acl_scenario_detail_uid', value: '3cb65d25-af9a-4803-9ad5-04aa39aed4f0')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.0000000001)
            column(name: 'value_share', value: 0.1000000000)
            column(name: 'detail_share', value: 0.9000000000)
            column(name: 'net_amount', value: 168.0000000000)
            column(name: 'gross_amount', value: 200.0000000000)
            column(name: 'service_fee_amount', value: 32.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '92ceee4d-4aa8-490b-94b1-03a40f9f62a0')
            column(name: 'df_acl_scenario_uid', value: 'ae790945-f541-4149-9ac2-9a5d29516c38')
            column(name: 'df_acl_scenario_detail_uid', value: '4065deb9-a7ea-4b9e-a96c-f3f911dfad6b')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.0000000003)
            column(name: 'value_share', value: 0.3000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 110.0000000000)
            column(name: 'gross_amount', value: 130.0000000000)
            column(name: 'service_fee_amount', value: 20.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: 'b0ccb36c-94d8-4247-81cf-8206a3ab1bb0')
            column(name: 'df_acl_scenario_uid', value: 'ae790945-f541-4149-9ac2-9a5d29516c38')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'dcf1cdea-86c1-4fcf-8a79-79c438e7fa18')
            column(name: 'df_acl_scenario_uid', value: 'ae790945-f541-4149-9ac2-9a5d29516c38')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '5a0fae17-ecb5-40cb-b6ac-fece10387887')
            column(name: 'df_acl_scenario_uid', value: 'ae790945-f541-4149-9ac2-9a5d29516c38')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 12)
        }

        rollback {
            dbRollback
        }
    }
}
