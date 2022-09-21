databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-02-10-01', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Insert test data for testWriteBaselineUsagesCsvEmptyReport, testWriteBaselineUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8fb69838-9f62-456f-ad52-58b55d71c305')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '879229c9-82c5-4e82-8516-8366dd0e18ee')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'name', value: 'British Film Institute (BFI)')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'f04f975f-80eb-489e-a8fc-119f3506a076')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 1 for Write Aacl Baseline Usages Csv Report test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '689c4669-207c-4391-bc15-6a36b426f949')
            column(name: 'df_fund_pool_uid', value: 'f04f975f-80eb-489e-a8fc-119f3506a076')
            column(name: 'df_aggregate_licensee_class_id', value: 171)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '80c81b83-fc32-4a67-8c98-0d7bb1440a4d')
            column(name: 'name', value: 'AACL Usage Batch 1 for Write Aacl Baseline Usages Csv Report test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '3081019e-5310-40ac-a052-5d961c6ecc5a')
            column(name: 'name', value: 'AACL Scenario 1 for Write Aacl Baseline Usages Csv Report test')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '8aa7676c-a8a1-4243-ac8c-1e1ea3e8c9c3')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'usage_period', value: 2014)
            column(name: 'usage_source', value: 'Feb 2014 TUR')
            column(name: 'number_of_copies', value: 10)
            column(name: 'number_of_pages', value: 12)
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 1.71)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'updated_datetime', value: '2014-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '52e16c57-95c1-4adf-bb00-063b5a0dce02')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'usage_period', value: 2013)
            column(name: 'usage_source', value: 'Feb 2013 TUR')
            column(name: 'number_of_copies', value: 10)
            column(name: 'number_of_pages', value: 12)
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 1.71)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'updated_datetime', value: '2013-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '12d5b830-73be-4b44-ad2e-8b046d9384af')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'usage_period', value: 2016)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_copies', value: 10)
            column(name: 'number_of_pages', value: 12)
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 1.71)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage')
            column(name: 'updated_datetime', value: '2020-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '40a6b9a0-9e3e-422d-9816-09a38de65f1c')
            column(name: 'df_usage_batch_uid', value: '80c81b83-fc32-4a67-8c98-0d7bb1440a4d')
            column(name: 'df_scenario_uid', value: '3081019e-5310-40ac-a052-5d961c6ecc5a')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '40a6b9a0-9e3e-422d-9816-09a38de65f1c')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 171)
            column(name: 'value_weight', value: 0.1000000)
            column(name: 'volume_weight', value: 0.2000000)
            column(name: 'volume_share', value: 0.3000000)
            column(name: 'value_share', value: 0.4000000)
            column(name: 'total_share', value: 0.5000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '08db3077-e722-483a-925c-66f907a0991b')
            column(name: 'df_usage_batch_uid', value: '80c81b83-fc32-4a67-8c98-0d7bb1440a4d')
            column(name: 'df_scenario_uid', value: '3081019e-5310-40ac-a052-5d961c6ecc5a')
            column(name: 'wr_wrk_inst', value: 109040891)
            column(name: 'work_title', value: 'Biological Journal')
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 75.00)
            column(name: 'service_fee_amount', value: 25.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 300)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '08db3077-e722-483a-925c-66f907a0991b')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 200)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'value_weight', value: 0.6000000)
            column(name: 'volume_weight', value: 0.7000000)
            column(name: 'volume_share', value: 0.8000000)
            column(name: 'value_share', value: 0.9000000)
            column(name: 'total_share', value: 0.56000000)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'baseline_uid', value: '12d5b830-73be-4b44-ad2e-8b046d9384af')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '70c4475b-e80c-4b57-92ff-6a96d2999311')
            column(name: 'df_scenario_uid', value: '3081019e-5310-40ac-a052-5d961c6ecc5a')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '70c4475b-e80c-4b57-92ff-6a96d2999311')
            column(name: 'df_usage_batch_uid', value: '80c81b83-fc32-4a67-8c98-0d7bb1440a4d')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '65706dae-95f4-404a-bdf0-b9c1d141cfbc')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 2 for Write Aacl Baseline Usages Csv Report test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '506a3ddc-2b55-49cf-9d0d-405d23233b18')
            column(name: 'df_fund_pool_uid', value: '65706dae-95f4-404a-bdf0-b9c1d141cfbc')
            column(name: 'df_aggregate_licensee_class_id', value: 171)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9a021824-3160-40ef-b5fc-79ff188b416b')
            column(name: 'name', value: 'AACL Usage Batch 2 for Write Aacl Baseline Usages Csv Report test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '9bd0f8fe-52c6-4986-8377-90c85e493a8d')
            column(name: 'name', value: 'AACL Scenario 2 for Write Aacl Baseline Usages Csv Report test')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'dfeae753-45ab-412b-b7d8-79b72b4bfb66')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_copies', value: 10)
            column(name: 'number_of_pages', value: 12)
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 1.71)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage')
            column(name: 'updated_datetime', value: '2020-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ae8c0c31-0916-4066-8d34-8dc270bd9d3f')
            column(name: 'df_usage_batch_uid', value: '9a021824-3160-40ef-b5fc-79ff188b416b')
            column(name: 'df_scenario_uid', value: '9bd0f8fe-52c6-4986-8377-90c85e493a8d')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'ae8c0c31-0916-4066-8d34-8dc270bd9d3f')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 200)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'value_weight', value: 0.7900000)
            column(name: 'volume_weight', value: 0.5900000)
            column(name: 'volume_share', value: 0.4500000)
            column(name: 'value_share', value: 0.0780000)
            column(name: 'total_share', value: 0.9500000)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'baseline_uid', value: 'dfeae753-45ab-412b-b7d8-79b72b4bfb66')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '79a55b56-2d6e-45cf-97cf-90e77d7c7097')
            column(name: 'df_scenario_uid', value: '9bd0f8fe-52c6-4986-8377-90c85e493a8d')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ARCHIVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '79a55b56-2d6e-45cf-97cf-90e77d7c7097')
            column(name: 'df_usage_batch_uid', value: '9a021824-3160-40ef-b5fc-79ff188b416b')
        }

        rollback {
            dbRollback
        }
    }
}
