databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-07-18-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Insert test data for testCalculateScenarioShares')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'f12b60ae-0bcf-40e4-9372-50337ed382b1')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'f9c1fbd7-84ea-4413-8520-a88032d00b13')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'd18a7fba-e41d-4325-ad19-cbfc088a4e94')
            column(name: 'df_acl_grant_set_uid', value: 'f9c1fbd7-84ea-4413-8520-a88032d00b13')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'e298f352-782d-411c-8977-29ac753e1ab1')
            column(name: 'df_acl_grant_set_uid', value: 'f9c1fbd7-84ea-4413-8520-a88032d00b13')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Different RH')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'f5675921-72b7-4506-9cd7-64d9a86bdefc')
            column(name: 'df_acl_grant_set_uid', value: 'f9c1fbd7-84ea-4413-8520-a88032d00b13')
            column(name: 'grant_status', value: 'GRANT')
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
            column(name: 'df_acl_grant_detail_uid', value: 'dbaf01be-12c0-456a-afac-330b8e75bd16')
            column(name: 'df_acl_grant_set_uid', value: 'f9c1fbd7-84ea-4413-8520-a88032d00b13')
            column(name: 'grant_status', value: 'DENY')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 122825555)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '2f6f0f73-b838-428d-8997-62e589ca642e')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202106, 202212]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'df_acl_fund_pool_uid', value: 'f12b60ae-0bcf-40e4-9372-50337ed382b1')
            column(name: 'df_acl_usage_batch_uid', value: '2f6f0f73-b838-428d-8997-62e589ca642e')
            column(name: 'df_acl_grant_set_uid', value: 'f9c1fbd7-84ea-4413-8520-a88032d00b13')
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
            column(name: 'df_acl_scenario_detail_uid', value: '7edfc465-4588-4c70-b62b-4c9f194e5d06')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN774GHHSB100')
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
            column(name: 'df_acl_share_detail_uid', value: 'c310657e-5881-43d1-9463-07f43df147a9')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'df_acl_scenario_detail_uid', value: '7edfc465-4588-4c70-b62b-4c9f194e5d06')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 3.0000000000)
            column(name: 'value_weight', value: 6.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'fe8ba41d-01af-42e4-b400-d88733b3271f')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN774GHHSB101')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'pub_type_weight', value: 1.9)
            column(name: 'content_unit_price', value: 6.3000000000)
            column(name: 'number_of_copies', value: 5)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 5.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '83aa9edc-c05d-404c-a835-fb9f72dee898')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'df_acl_scenario_detail_uid', value: 'fe8ba41d-01af-42e4-b400-d88733b3271f')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 5.0000000000)
            column(name: 'value_weight', value: 59.8500000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'e161ad39-0c79-44a0-ade7-f0c14fd3802f')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'df_acl_scenario_detail_uid', value: 'fe8ba41d-01af-42e4-b400-d88733b3271f')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 5.0000000000)
            column(name: 'value_weight', value: 59.8500000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '7cb1ebeb-ee71-4ec4-bd0c-611d078dbe4b')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN774GHHSB102')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'pub_type_weight', value: 1.9)
            column(name: 'content_unit_price', value: 21.0000000000)
            column(name: 'number_of_copies', value: 3)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 3.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '7809df99-ac7d-44b2-bb6e-78c806315d50')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'df_acl_scenario_detail_uid', value: '7cb1ebeb-ee71-4ec4-bd0c-611d078dbe4b')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 3.0000000000)
            column(name: 'value_weight', value: 119.7000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'd7e066bb-2df4-45fe-b767-c716954e5af5')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN774GHHSB103')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'pub_type_weight', value: 2)
            column(name: 'content_unit_price', value: 7.4100000000)
            column(name: 'number_of_copies', value: 2)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 2.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'f5c41c74-c968-41d5-a881-da8739d5ddd5')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'df_acl_scenario_detail_uid', value: 'd7e066bb-2df4-45fe-b767-c716954e5af5')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 2.0000000000)
            column(name: 'value_weight', value: 29.6400000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: 'b9485a37-a451-42a4-a3f9-76874ab6578a')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'period_prior', value: 1)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '033c5632-97ef-46f4-9ea4-cee0b155af70')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '365ffade-4db4-479a-889c-e267f4ff3792')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'period_prior', value: 2)
            column(name: 'weight', value: 0.5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'd28451e1-adca-485c-8971-06067cb7d427')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '6882dba6-f424-4728-bf0d-e452c7cfc020')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'df_publication_type_uid', value: "aef4304b-6722-4047-86e0-8c84c72f096d")
            column(name: 'weight', value: 1.9)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'a3ad19db-5843-4af2-81f9-d6635ddaefd1')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'cfd2919d-f176-4321-a892-ba042800a43a')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'e52ed1ab-0e78-4a67-9d81-cd163700013c')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'c3546540-18da-4cd4-8fa1-e0922fd2644c')
            column(name: 'df_acl_scenario_uid', value: '8c855d2e-5bb6-435d-9da2-7a937c74cb6d')
            column(name: 'detail_licensee_class_id', value: 5)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        rollback {
            dbRollback
        }
    }
}
