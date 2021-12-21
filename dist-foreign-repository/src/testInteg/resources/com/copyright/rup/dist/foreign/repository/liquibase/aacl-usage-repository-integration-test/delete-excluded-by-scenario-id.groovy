databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-11-09-03', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testDeleteExcludedByScenarioId')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '7c770933-6d00-4228-964b-0062e5d8d7dd')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_copies', value: 155)
            column(name: 'number_of_pages', value: 100)
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.50)
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'comment', value: 'LOCKED usage from baseline')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'bf259213-a2b8-45cc-ab57-e4c7860bd6ab')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_copies', value: 155)
            column(name: 'number_of_pages', value: 100)
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'original_publication_type', value: 'Journal')
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'publication_type_weight', value: 2.50)
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'comment', value: 'SCENARIO_EXCLUDED usage from baseline')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '8ec0c5df-ae3c-45a0-89d3-404e9f85dbbb')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool For Add To Baseline Test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '95cd1db8-34f2-4537-96d7-5e7d14c2f440')
            column(name: 'df_fund_pool_uid', value: '8ec0c5df-ae3c-45a0-89d3-404e9f85dbbb')
            column(name: 'df_aggregate_licensee_class_id', value: 141)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0fc14882-971a-4ba3-ac49-483cd2890b62')
            column(name: 'name', value: 'AACL Usage Batch For Add To Baseline Test')
            column(name: 'payment_date', value: '2021-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'initial_usages_count', value: 4)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '45f17838-b5cb-47e2-a57a-8d128fa07edf')
            column(name: 'name', value: 'AACL Scenario For Add To Baseline Test')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'aacl_fields', value: '{"fund_pool_uid": "8ec0c5df-ae3c-45a0-89d3-404e9f85dbbb", "usageAges": [{"period": 2021, "weight": 1.00}], "publicationTypes": [{"id": "2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "weight": 3.00},{"id": "68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "weight": 2.00}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        // LOCKED usage from baseline
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f92c8af2-dea6-4243-ac58-01055932187e')
            column(name: 'df_usage_batch_uid', value: '0fc14882-971a-4ba3-ac49-483cd2890b62')
            column(name: 'df_scenario_uid', value: '45f17838-b5cb-47e2-a57a-8d128fa07edf')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 375.00)
            column(name: 'service_fee_amount', value: 125.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'LOCKED usage from baseline')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'f92c8af2-dea6-4243-ac58-01055932187e')
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
            column(name: 'publication_type_weight', value: 1.50)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'baseline_uid', value: '7c770933-6d00-4228-964b-0062e5d8d7dd')
        }

        // SCENARIO_EXCLUDED usage from baseline
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '45ad755f-5b8f-4f38-b362-d37de4b520eb')
            column(name: 'df_usage_batch_uid', value: '0fc14882-971a-4ba3-ac49-483cd2890b62')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'SCENARIO_EXCLUDED usage from baseline')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '45ad755f-5b8f-4f38-b362-d37de4b520eb')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'publication_type_weight', value: 2.50)
            column(name: 'original_publication_type', value: 'Journal')
            column(name: 'baseline_uid', value: 'bf259213-a2b8-45cc-ab57-e4c7860bd6ab')
        }

        // Newly uploaded LOCKED usage
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a46b6313-11de-4d6e-a51e-b50dd8239ec7')
            column(name: 'df_usage_batch_uid', value: '0fc14882-971a-4ba3-ac49-483cd2890b62')
            column(name: 'df_scenario_uid', value: '45f17838-b5cb-47e2-a57a-8d128fa07edf')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
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
            column(name: 'df_usage_aacl_uid', value: 'a46b6313-11de-4d6e-a51e-b50dd8239ec7')
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

        // Newly uploaded SCENARIO_EXCLUDED usage
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bb87e43f-b755-467f-abdd-30ac0500aeff')
            column(name: 'df_usage_batch_uid', value: '0fc14882-971a-4ba3-ac49-483cd2890b62')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'Newly uploaded SCENARIO_EXCLUDED usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'bb87e43f-b755-467f-abdd-30ac0500aeff')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'publication_type_weight', value: 2.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'aeccfc6d-c983-4fbb-96ee-d2fc4c4472b7')
            column(name: 'df_scenario_uid', value: '45f17838-b5cb-47e2-a57a-8d128fa07edf')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'aeccfc6d-c983-4fbb-96ee-d2fc4c4472b7')
            column(name: 'df_usage_batch_uid', value: '0fc14882-971a-4ba3-ac49-483cd2890b62')
        }

        rollback {
            dbRollback
        }
    }
}
