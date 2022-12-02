databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-12-01-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testSendToLmAacl')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '98b4a093-b48c-4a41-828b-30cb54ebd387')
            column(name: 'wr_wrk_inst', value: 471137967)
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
            column(name: 'df_usage_baseline_aacl_uid', value: '75cfffc6-5aba-43a6-9ece-6c7da065cf20')
            column(name: 'wr_wrk_inst', value: 122235134)
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
            column(name: 'df_fund_pool_uid', value: '0ea96c66-72aa-4d59-8128-31c20e7eb9de')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool For Add To Baseline Test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'a00cafcc-4157-409a-b381-33d5fe08631c')
            column(name: 'df_fund_pool_uid', value: '0ea96c66-72aa-4d59-8128-31c20e7eb9de')
            column(name: 'df_aggregate_licensee_class_id', value: 141)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e5177da8-099f-41c4-b4b0-49a38da94805')
            column(name: 'name', value: 'AACL Usage Batch')
            column(name: 'payment_date', value: '2021-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 4)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'd92e3c8e-7ecc-4080-bf3f-b541f51c9a06')
            column(name: 'name', value: 'AACL Scenario')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'aacl_fields', value: '{"fund_pool_uid": "0ea96c66-72aa-4d59-8128-31c20e7eb9de", "usageAges": [{"period": 2021, "weight": 1.00}], "publicationTypes": [{"id": "2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "weight": 3.00},{"id": "68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "weight": 2.00}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        // LOCKED usage from baseline
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '297d5aea-24f8-4b26-be22-2441bda526dd')
            column(name: 'df_usage_batch_uid', value: 'e5177da8-099f-41c4-b4b0-49a38da94805')
            column(name: 'df_scenario_uid', value: 'd92e3c8e-7ecc-4080-bf3f-b541f51c9a06')
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'system_title', value: 'Cell Biology')
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
            column(name: 'df_usage_aacl_uid', value: '297d5aea-24f8-4b26-be22-2441bda526dd')
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
            column(name: 'baseline_uid', value: '98b4a093-b48c-4a41-828b-30cb54ebd387')
        }

        // SCENARIO_EXCLUDED usage from baseline
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd2704d44-9d3f-4f9c-bef8-38ac8116592c')
            column(name: 'df_usage_batch_uid', value: 'e5177da8-099f-41c4-b4b0-49a38da94805')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'system_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'SCENARIO_EXCLUDED usage from baseline')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'd2704d44-9d3f-4f9c-bef8-38ac8116592c')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'publication_type_weight', value: 2.50)
            column(name: 'original_publication_type', value: 'Journal')
            column(name: 'baseline_uid', value: '75cfffc6-5aba-43a6-9ece-6c7da065cf20')
        }

        // Newly uploaded LOCKED usage
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '190fee30-4de4-4cac-afce-432503e42485')
            column(name: 'df_usage_batch_uid', value: 'e5177da8-099f-41c4-b4b0-49a38da94805')
            column(name: 'df_scenario_uid', value: 'd92e3c8e-7ecc-4080-bf3f-b541f51c9a06')
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'system_title', value: 'Cell Biology')
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
            column(name: 'df_usage_aacl_uid', value: '190fee30-4de4-4cac-afce-432503e42485')
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
            column(name: 'df_usage_uid', value: 'f9c1549f-e170-44a9-a33d-60f858d868e0')
            column(name: 'df_usage_batch_uid', value: 'e5177da8-099f-41c4-b4b0-49a38da94805')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'system_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'Newly uploaded SCENARIO_EXCLUDED usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'f9c1549f-e170-44a9-a33d-60f858d868e0')
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
            column(name: 'df_scenario_usage_filter_uid', value: '06620f6f-6ebd-4944-a9d5-eedb9fdd1648')
            column(name: 'df_scenario_uid', value: 'd92e3c8e-7ecc-4080-bf3f-b541f51c9a06')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '06620f6f-6ebd-4944-a9d5-eedb9fdd1648')
            column(name: 'df_usage_batch_uid', value: 'e5177da8-099f-41c4-b4b0-49a38da94805')
        }

        rollback {
            dbRollback
        }
    }
}
