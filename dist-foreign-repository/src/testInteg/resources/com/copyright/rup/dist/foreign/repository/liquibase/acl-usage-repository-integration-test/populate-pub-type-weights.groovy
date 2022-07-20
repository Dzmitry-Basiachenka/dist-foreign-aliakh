databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-07-14-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Insert test data for testPopulatePubTypeWeights')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'f971b9a8-ac49-4279-a73b-3a8946286236')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '98a94e39-07cf-4c50-a338-5b4ce8b7a20f')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'b5a1d6b9-bee3-47e5-9bbb-8294e003f3f2')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'df_acl_fund_pool_uid', value: 'f971b9a8-ac49-4279-a73b-3a8946286236')
            column(name: 'df_acl_usage_batch_uid', value: 'b5a1d6b9-bee3-47e5-9bbb-8294e003f3f2')
            column(name: 'df_acl_grant_set_uid', value: '98a94e39-07cf-4c50-a338-5b4ce8b7a20f')
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
            column(name: 'df_acl_scenario_detail_uid', value: 'eb75e21e-2322-43bd-b11e-d5f46337c4c5')
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'AGN674GHHSB100')
            column(name: 'wr_wrk_inst', value: 122825555)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'usage_quantity', value: 3)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 3.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '8c8837b7-99fe-4776-ba5a-1b01625ea233')
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'AGN674GHHSB101')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'usage_quantity', value: 5)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 5.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '2185715b-66fe-4c67-9ca1-7c3ddbb5e977')
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'period_end_date', value: 201906)
            column(name: 'original_detail_id', value: 'AGN674GHHSB102')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'usage_quantity', value: 5)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 5.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'd62a8e33-b145-49c6-9de8-936323875563')
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'period_end_date', value: 202012)
            column(name: 'original_detail_id', value: 'AGN674GHHSB103')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'usage_quantity', value: 5)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 5.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '90dcb990-5826-476c-a8b7-6b1504d525d2')
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'period_end_date', value: 201806)
            column(name: 'original_detail_id', value: 'AGN674GHHSB104')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'usage_quantity', value: 5)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 5.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: 'fc047698-5dd1-4925-ad5b-b9d2c7a7772c')
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: 'e1e0a928-32b7-4bf1-84cd-6bb0713f6197')
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'period_prior', value: 1)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '8a38e327-2dff-4cf4-95e5-86f626598d3c')
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'period_prior', value: 2)
            column(name: 'weight', value: 0.5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'fa6ddb3d-3e5f-4c11-9ee8-4a4ecc11ef62')
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'df_publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '5fa8326a-74c6-4969-8161-3f94bb4baad6')
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'df_publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: 1.9)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '88321d95-4997-4ed4-86b8-58bad51a5fe7')
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'df_publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'period', value: '201906')
            column(name: 'weight', value: 2.5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '99f524df-0b13-438f-ae93-18daf36c71ec')
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'df_publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'period', value: '202112')
            column(name: 'weight', value: 3.6)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'bf6d83e2-2d1e-4151-8c31-22b54ded68d7')
            column(name: 'df_acl_scenario_uid', value: '66facb16-29aa-46ab-b99a-cdf303d4bb7d')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        //Second scenario data to cover CDP-1083
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'f2a45cea-88a2-4e52-8632-61b5232dde3d')
            column(name: 'name', value: 'ACL Fund Pool 202206')
            column(name: 'period', value: 202206)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'd30662f8-56b3-4226-a078-11324780c68c')
            column(name: 'name', value: 'ACL Grant Set 202206')
            column(name: 'grant_period', value: 202206)
            column(name: 'periods', value: '[202106, 202206]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '4a17cf07-1eb6-472d-8ece-7f1fa18557ea')
            column(name: 'name', value: 'ACL Usage Batch 202206')
            column(name: 'distribution_period', value: 202206)
            column(name: 'periods', value: '[202106, 202206]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'b1fa0b8a-9ff0-4b1b-ae35-16f38eb84ebd')
            column(name: 'df_acl_fund_pool_uid', value: 'f2a45cea-88a2-4e52-8632-61b5232dde3d')
            column(name: 'df_acl_usage_batch_uid', value: '4a17cf07-1eb6-472d-8ece-7f1fa18557ea')
            column(name: 'df_acl_grant_set_uid', value: 'd30662f8-56b3-4226-a078-11324780c68c')
            column(name: 'name', value: 'ACL Scenario 202206')
            column(name: 'description', value: 'Description')
            column(name: 'period_end_date', value: 202206)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '9f3d05b2-392d-4964-bd67-1f4c68927552')
            column(name: 'df_acl_scenario_uid', value: 'b1fa0b8a-9ff0-4b1b-ae35-16f38eb84ebd')
            column(name: 'df_publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'period', value: '202012')
            column(name: 'weight', value: 1.80)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '690dec9e-8c7f-4650-8687-69e5c780fbea')
            column(name: 'df_acl_scenario_uid', value: 'b1fa0b8a-9ff0-4b1b-ae35-16f38eb84ebd')
            column(name: 'df_publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'period', value: '202112')
            column(name: 'weight', value: 2.6)
        }

        rollback {
            dbRollback
        }
    }
}
