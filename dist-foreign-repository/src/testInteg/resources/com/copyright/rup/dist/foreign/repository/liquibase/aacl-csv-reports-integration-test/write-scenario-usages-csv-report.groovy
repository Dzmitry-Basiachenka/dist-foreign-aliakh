databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-04-23-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testWriteScenarioUsagesCsvReport, testWriteArchivedScenarioUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8fb69838-9f62-456f-ad52-58b55d71c305')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'ffc1587c-9b05-4681-a3cc-dc02cec7fadc')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool for Export Detail Scenario test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '879229c9-82c5-4e82-8516-8366dd0e18ee')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'name', value: 'British Film Institute (BFI)')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '8474cceb-2367-4d03-b240-5ea6d2819a53')
            column(name: 'df_fund_pool_uid', value: 'ffc1587c-9b05-4681-a3cc-dc02cec7fadc')
            column(name: 'df_aggregate_licensee_class_id', value: 171)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '354f8342-0bf0-4a89-aa9a-6f4428a29e9d')
            column(name: 'name', value: 'AACL Usage Batch for Export Details Scenario test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '153b80ba-85e6-48ee-b5c3-c81664827e8a')
            column(name: 'name', value: 'AACL Scenario for Export Details Scenario test')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '005cc2bd-fd05-4d91-aaa2-33631741927b')
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
            column(name: 'df_usage_baseline_aacl_uid', value: 'd788c7af-c528-40bf-a998-beae4e6da94d')
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
            column(name: 'df_usage_baseline_aacl_uid', value: '13212981-431f-4311-97d5-1a39bc252afc')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '53079eb1-ddfb-4d9b-a914-4402cb4b0f49')
            column(name: 'df_usage_batch_uid', value: '354f8342-0bf0-4a89-aa9a-6f4428a29e9d')
            column(name: 'df_scenario_uid', value: '153b80ba-85e6-48ee-b5c3-c81664827e8a')
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
            column(name: 'df_usage_aacl_uid', value: '53079eb1-ddfb-4d9b-a914-4402cb4b0f49')
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
            column(name: 'df_usage_uid', value: '7ec3e2df-5274-4eba-a493-b9502db11f4c')
            column(name: 'df_usage_batch_uid', value: '354f8342-0bf0-4a89-aa9a-6f4428a29e9d')
            column(name: 'df_scenario_uid', value: '153b80ba-85e6-48ee-b5c3-c81664827e8a')
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
            column(name: 'df_usage_aacl_uid', value: '7ec3e2df-5274-4eba-a493-b9502db11f4c')
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
            column(name: 'baseline_uid', value: '13212981-431f-4311-97d5-1a39bc252afc')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '04c60c30-c11b-4cd4-9525-7a42793b00a6')
            column(name: 'df_scenario_uid', value: '153b80ba-85e6-48ee-b5c3-c81664827e8a')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '04c60c30-c11b-4cd4-9525-7a42793b00a6')
            column(name: 'df_usage_batch_uid', value: '354f8342-0bf0-4a89-aa9a-6f4428a29e9d')
        }

        //archived scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '0b28a1ff-ee07-4087-8980-ad7e7ea493f8')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool for Export Detail Archived Scenario test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '1ec28821-2402-4063-bf2e-3bd0a20a8ed1')
            column(name: 'df_fund_pool_uid', value: '0b28a1ff-ee07-4087-8980-ad7e7ea493f8')
            column(name: 'df_aggregate_licensee_class_id', value: 171)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '1e56b3b1-40a9-4f4f-8e2f-868caaba8693')
            column(name: 'name', value: 'AACL Usage Batch For Export Details Archived Scenario Test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5429c31b-ffd1-4a7f-9b24-8c7809417fce')
            column(name: 'name', value: 'AACL Scenario For Export Details Archived Scenario Test')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '44f90e4f-3038-4738-a41a-4e989d80b0f2')
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
            column(name: 'df_usage_archive_uid', value: '04a64d17-eab8-4191-b162-f9c12e540795')
            column(name: 'df_usage_batch_uid', value: '1e56b3b1-40a9-4f4f-8e2f-868caaba8693')
            column(name: 'df_scenario_uid', value: '5429c31b-ffd1-4a7f-9b24-8c7809417fce')
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
            column(name: 'df_usage_aacl_uid', value: '04a64d17-eab8-4191-b162-f9c12e540795')
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
            column(name: 'baseline_uid', value: '44f90e4f-3038-4738-a41a-4e989d80b0f2')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '89e290d3-c656-4b23-9e20-1daa975eeb19')
            column(name: 'df_scenario_uid', value: '5429c31b-ffd1-4a7f-9b24-8c7809417fce')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ARCHIVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '89e290d3-c656-4b23-9e20-1daa975eeb19')
            column(name: 'df_usage_batch_uid', value: '1e56b3b1-40a9-4f4f-8e2f-868caaba8693')
        }

        rollback {
            dbRollback
        }
    }
}
