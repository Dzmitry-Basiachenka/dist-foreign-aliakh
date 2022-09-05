databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-08-29-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Insert test data for testFindRightsholderAggLcClassResults')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'bf47c3f4-f7b9-4f63-aec0-96cca167a315')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '957aebee-c642-463f-9e7f-9de8afca6c07')
            column(name: 'df_acl_fund_pool_uid', value: 'bf47c3f4-f7b9-4f63-aec0-96cca167a315')
            column(name: 'detail_licensee_class_id', value: '1')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 1200.26)
            column(name: 'gross_amount', value: 1550.51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'cbfa9b4c-2c14-40d3-8252-9ed1f20a130d')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '50648820-a66c-4aea-8143-31fff69531ac4')
            column(name: 'df_acl_grant_set_uid', value: 'cbfa9b4c-2c14-40d3-8252-9ed1f20a130d')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'c920822a-6f3e-4aa6-af2c-2c4c116fc833')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '7ae1468e-ae4d-4846-b20d-46f28b75c82c')
            column(name: 'df_acl_fund_pool_uid', value: 'bf47c3f4-f7b9-4f63-aec0-96cca167a315')
            column(name: 'df_acl_usage_batch_uid', value: 'c920822a-6f3e-4aa6-af2c-2c4c116fc833')
            column(name: 'df_acl_grant_set_uid', value: 'cbfa9b4c-2c14-40d3-8252-9ed1f20a130d')
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
            column(name: 'df_acl_scenario_detail_uid', value: '08d1abc3-d036-4a3b-99fb-57d8b7caa6aa')
            column(name: 'df_acl_scenario_uid', value: '7ae1468e-ae4d-4846-b20d-46f28b75c82c')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN974GHHSB100')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'pub_type_weight', value: 2)
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'number_of_copies', value: 3)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 3.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '08d1abc3-d036-4a3b-99fb-57a741caa6aa')
            column(name: 'df_acl_scenario_uid', value: '7ae1468e-ae4d-4846-b20d-46f28b75c82c')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN974GHHSB100')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'pub_type_weight', value: 2)
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'number_of_copies', value: 3)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 3.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '7b116db8-0c3f-4bb0-9e19-14fa1500a55e')
            column(name: 'df_acl_scenario_uid', value: '7ae1468e-ae4d-4846-b20d-46f28b75c82c')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN974GHHSB100')
            column(name: 'wr_wrk_inst', value: 122825555)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'pub_type_weight', value: 2)
            column(name: 'content_unit_price', value: 1.0000000000)
            column(name: 'number_of_copies', value: 3)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 3.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '249f7418-1ea6-4615-a688-525eb3c1fef5')
            column(name: 'df_acl_scenario_uid', value: '7ae1468e-ae4d-4846-b20d-46f28b75c82c')
            column(name: 'df_acl_scenario_detail_uid', value: '08d1abc3-d036-4a3b-99fb-57d8b7caa6aa')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 3.0000000000)
            column(name: 'value_weight', value: 6.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 1.0500000000)
            column(name: 'gross_amount', value: 2.5326400000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'f8717239-a851-478a-85a9-0630f0f92160')
            column(name: 'df_acl_scenario_uid', value: '7ae1468e-ae4d-4846-b20d-46f28b75c82c')
            column(name: 'df_acl_scenario_detail_uid', value: '08d1abc3-d036-4a3b-99fb-57d8b7caa6aa')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 3.0000000000)
            column(name: 'value_weight', value: 6.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 3.5463200000)
            column(name: 'gross_amount', value: 4.3456300000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '7c0651ca-8352-45fe-ad70-2eb7d6f8997b')
            column(name: 'df_acl_scenario_uid', value: '7ae1468e-ae4d-4846-b20d-46f28b75c82c')
            column(name: 'df_acl_scenario_detail_uid', value: '08d1abc3-d036-4a3b-99fb-57a741caa6aa')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 12)
            column(name: 'volume_weight', value: 3.0000000000)
            column(name: 'value_weight', value: 6.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 12.5710000000)
            column(name: 'gross_amount', value: 15.5400000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'abd179df-650b-4e2d-9642-92f023c732c5')
            column(name: 'df_acl_scenario_uid', value: '7ae1468e-ae4d-4846-b20d-46f28b75c82c')
            column(name: 'df_acl_scenario_detail_uid', value: '7b116db8-0c3f-4bb0-9e19-14fa1500a55e')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 13)
            column(name: 'volume_weight', value: 3.0000000000)
            column(name: 'value_weight', value: 6.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 25.0673233000)
            column(name: 'gross_amount', value: 27.6573523650)
        }

        rollback {
            dbRollback
        }
    }
}
