databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-05-19-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Insert test data for testFindAaclCountByScenarioIdAndRhAccountNumberWithEmptySearch, testFindAaclByScenarioIdAndRhAccountNumberWithEmptySearch, ' +
                'testFindAaclByScenarioIdAndRhAccountNumberSearchByDetailId, testFindAaclByScenarioIdAndRhAccountNumberSearchByWrWrkInst, ' +
                'testFindAaclByScenarioIdAndRhAccountNumberSearchByStandardNumber, testFindAaclByScenarioIdAndRhAccountNumberSearchBySqlLikePattern, ' +
                'testFindAaclDtosByScenarioId')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '9905f006-a3e1-4061-b3d4-e7ece191103f')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5bcf2c37-2f32-48e9-90fe-c9d75298eeed')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '0da3bfe2-8cfe-4120-b15b-ac48cab97da9')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 1')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'ce6e3be7-8582-4958-80e0-27a133e74afe')
            column(name: 'df_fund_pool_uid', value: '0da3bfe2-8cfe-4120-b15b-ac48cab97da9')
            column(name: 'df_aggregate_licensee_class_id', value: 141)
            column(name: 'gross_amount', value: 1500.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '20ceb26a-6c22-45b9-92e6-c7d98ab04faa')
            column(name: 'name', value: 'AACL Usage Batch 1')
            column(name: 'payment_date', value: '2021-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '4f1714a1-5e23-4e46-aeb1-b44fbeea17e6')
            column(name: 'name', value: 'AACL Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'aacl_fields', value: '{"fund_pool_uid": "0da3bfe2-8cfe-4120-b15b-ac48cab97da9", "usageAges": [{"period": 2021, "weight": 1.00}], "publicationTypes": [{"id": "2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "weight": 3.00}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'cfd3e488-d709-4390-884f-2b2a5b1c9e22')
            column(name: 'df_usage_batch_uid', value: '20ceb26a-6c22-45b9-92e6-c7d98ab04faa')
            column(name: 'df_scenario_uid', value: '4f1714a1-5e23-4e46-aeb1-b44fbeea17e6')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'payee_account_number', value: 1000009997)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 375.00)
            column(name: 'service_fee_amount', value: 125.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'Newly uploaded LOCKED usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'cfd3e488-d709-4390-884f-2b2a5b1c9e22')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'value_weight', value: 24.0000000)
            column(name: 'volume_weight', value: 5.0000000)
            column(name: 'volume_share', value: 50.0000000)
            column(name: 'value_share', value: 60.0000000)
            column(name: 'total_share', value: 2.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 3.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'cd1a9398-0b86-42f1-bdc9-ff1ac764b1c2')
            column(name: 'df_usage_batch_uid', value: '20ceb26a-6c22-45b9-92e6-c7d98ab04faa')
            column(name: 'df_scenario_uid', value: '4f1714a1-5e23-4e46-aeb1-b44fbeea17e6')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'payee_account_number', value: 1000009997)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 375.00)
            column(name: 'service_fee_amount', value: 125.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'Newly uploaded LOCKED usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'cd1a9398-0b86-42f1-bdc9-ff1ac764b1c2')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'value_weight', value: 24.0000000)
            column(name: 'volume_weight', value: 5.0000000)
            column(name: 'volume_share', value: 50.0000000)
            column(name: 'value_share', value: 60.0000000)
            column(name: 'total_share', value: 2.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 3.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '927ea8a1-08d1-4301-8c8b-b465c5c179ee')
            column(name: 'df_usage_batch_uid', value: '20ceb26a-6c22-45b9-92e6-c7d98ab04faa')
            column(name: 'df_scenario_uid', value: '4f1714a1-5e23-4e46-aeb1-b44fbeea17e6')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 375.00)
            column(name: 'service_fee_amount', value: 125.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'Newly uploaded LOCKED usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '927ea8a1-08d1-4301-8c8b-b465c5c179ee')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'value_weight', value: 24.0000000)
            column(name: 'volume_weight', value: 5.0000000)
            column(name: 'volume_share', value: 50.0000000)
            column(name: 'value_share', value: 60.0000000)
            column(name: 'total_share', value: 2.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 3.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '566d2fba-1342-413e-a96e-1c678e781c3e')
            column(name: 'df_scenario_uid', value: '4f1714a1-5e23-4e46-aeb1-b44fbeea17e6')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '566d2fba-1342-413e-a96e-1c678e781c3e')
            column(name: 'df_usage_batch_uid', value: '20ceb26a-6c22-45b9-92e6-c7d98ab04faa')
        }

        rollback {
            dbRollback
        }
    }
}
