databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-07-19-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testFindByScenarioIdAndRhAccountNumberNullSearchValue, testFindCountByScenarioIdAndRhAccountNumberNullSearchValue, ' +
                'testFindByScenarioIdAndRhAccountNumberSearchByUsageDetailId, testFindCountByScenarioIdAndRhAccountNumberSearchByUsageDetailId, ' +
                'testFindByScenarioIdAndRhAccountNumberSearchByWrWrkInst, testFindCountByScenarioIdAndRhAccountNumberSearchByWrWrkInst, ' +
                'testFindByScenarioIdAndRhAccountNumberSearchSystemTitle, testFindCountByScenarioIdAndRhAccountNumberSearchSystemTitle, ' +
                'testFindByScenarioIdAndRhAccountNumberSearchRhAccountNumber, testFindCountByScenarioIdAndRhAccountNumberSearchRhAccountNumber, ' +
                'testFindByScenarioIdAndRhAccountNumberSearchRhName, testFindCountByScenarioIdAndRhAccountNumberSearchRhName, ' +
                'testSortingFindFindByScenarioIdAndRhAccountNumber')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '14edbc9b-1473-4fc9-95f6-07b3ef45e851')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '99991909-744c-4766-ad67-fdc9e2c043eb')
            column(name: 'rh_account_number', value: 1000002901)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '861374bd-c619-4e52-b518-d4bbc3c8a209')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '6af467b8-102e-41af-8bde-f4726d7d7b26')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '41046ff3-ce3c-41da-a8aa-56e93cbc4736')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'f473fa64-12ea-4db6-9d30-94087fe500fd')
            column(name: 'df_acl_fund_pool_uid', value: '861374bd-c619-4e52-b518-d4bbc3c8a209')
            column(name: 'df_acl_usage_batch_uid', value: '6af467b8-102e-41af-8bde-f4726d7d7b26')
            column(name: 'df_acl_grant_set_uid', value: '41046ff3-ce3c-41da-a8aa-56e93cbc4736')
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
            column(name: 'df_acl_scenario_detail_uid', value: 'd1e23c04-8f45-44c8-a036-d2744f191073')
            column(name: 'df_acl_scenario_uid', value: 'f473fa64-12ea-4db6-9d30-94087fe500fd')
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
            column(name: 'reported_type_of_use', value: 'COPY_FOR_MYSELF')
            column(name: 'type_of_use', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '3028b170-8b74-4d65-97ec-c98fcdc2ede5')
            column(name: 'df_acl_scenario_uid', value: 'f473fa64-12ea-4db6-9d30-94087fe500fd')
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
            column(name: 'content_unit_price', value: 3.0000000000)
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
            column(name: 'df_acl_share_detail_uid', value: 'ad201ccc-decd-432f-8727-42438fb082aa')
            column(name: 'df_acl_scenario_uid', value: 'f473fa64-12ea-4db6-9d30-94087fe500fd')
            column(name: 'df_acl_scenario_detail_uid', value: 'd1e23c04-8f45-44c8-a036-d2744f191073')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
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
            column(name: 'df_acl_share_detail_uid', value: 'd09b1b34-09ae-4293-a703-f47220d1c2e8')
            column(name: 'df_acl_scenario_uid', value: 'f473fa64-12ea-4db6-9d30-94087fe500fd')
            column(name: 'df_acl_scenario_detail_uid', value: 'd1e23c04-8f45-44c8-a036-d2744f191073')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
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
            column(name: 'df_acl_share_detail_uid', value: 'a8a910c4-f13c-45dc-925a-b0e8ed542be9')
            column(name: 'df_acl_scenario_uid', value: 'f473fa64-12ea-4db6-9d30-94087fe500fd')
            column(name: 'df_acl_scenario_detail_uid', value: '3028b170-8b74-4d65-97ec-c98fcdc2ede5')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122820638)
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
            column(name: 'df_acl_share_detail_uid', value: 'd8f1f6ba-e49e-40b3-ab16-3b15ef4e30e4')
            column(name: 'df_acl_scenario_uid', value: 'f473fa64-12ea-4db6-9d30-94087fe500fd')
            column(name: 'df_acl_scenario_detail_uid', value: '3028b170-8b74-4d65-97ec-c98fcdc2ede5')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'rh_account_number', value: 1000002901)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.0000000001)
            column(name: 'value_share', value: 0.1000000000)
            column(name: 'detail_share', value: 0.9000000000)
            column(name: 'net_amount', value: 16.0000000000)
            column(name: 'gross_amount', value: 20.0000000000)
            column(name: 'service_fee_amount', value: 4.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: 'a1478254-584f-4c25-aae9-408a0ecd4b12')
            column(name: 'df_acl_scenario_uid', value: 'f473fa64-12ea-4db6-9d30-94087fe500fd')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '8a873087-764c-4c02-9f96-7b1cc13a27fb')
            column(name: 'df_acl_scenario_uid', value: 'f473fa64-12ea-4db6-9d30-94087fe500fd')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '33371776-4499-4392-ab66-d3a44cbc5af9')
            column(name: 'df_acl_scenario_uid', value: 'f473fa64-12ea-4db6-9d30-94087fe500fd')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '3afbf740-7492-4d41-8d6c-7aa6fe7e3f58')
            column(name: 'df_acl_scenario_uid', value: 'f473fa64-12ea-4db6-9d30-94087fe500fd')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        rollback {
            dbRollback
        }
    }
}
