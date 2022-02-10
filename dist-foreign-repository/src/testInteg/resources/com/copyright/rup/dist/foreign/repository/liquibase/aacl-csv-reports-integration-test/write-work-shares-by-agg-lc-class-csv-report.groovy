databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-04-22-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for testWriteWorkSharesByAggLcClassSummaryCsvReport, testWriteArchivedWorkSharesByAggLcClassSummaryCsvReport, ' +
                'testWriteArchivedWorkSharesByAggLcClassSummaryCsvEmptyReport, testWriteWorkSharesByAggLcClassCsvReport, ' +
                'testWriteArchivedWorkSharesByAggLcClassCsvReport, testWriteArchivedWorkSharesByAggLcClassCsvEmptyReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8fb69838-9f62-456f-ad52-58b55d71c305')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8aafed93-6964-41f6-be6e-f5e628c03ece')
            column(name: 'rh_account_number', value: 1000011881)
            column(name: 'name', value: 'William B. Eerdmans Publishing Company')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3a8eed5d-a2f2-47d2-9cba-b047d9947706')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 1 for Work Shares by Aggregate Licensee Class Summary Report test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '65e72126-2d74-4018-a06a-7fa4c81f0b33')
            column(name: 'df_fund_pool_uid', value: '3a8eed5d-a2f2-47d2-9cba-b047d9947706')
            column(name: 'df_aggregate_licensee_class_id', value: 171)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'name', value: 'AACL Usage Batch 1 for Work Shares by Aggregate Licensee Class Summary Report test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 4)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
            column(name: 'name', value: 'AACL Scenario 1 for Work Shares by Aggregate Licensee Class Summary Report test')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "fund_pool_uid": "3a8eed5d-a2f2-47d2-9cba-b047d9947706", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8eb9dbbc-3535-42cc-8094-2d90849952e2')
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
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
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '8eb9dbbc-3535-42cc-8094-2d90849952e2')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 680.0000000000)
            column(name: 'volume_weight', value: 10.0000000000)
            column(name: 'volume_share', value: 0.4098360656)
            column(name: 'value_share', value: 0.3970571062)
            column(name: 'total_share', value: 0.4095917984)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e951defc-79c7-48d0-b7c7-958df4bdf2cb')
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
            column(name: 'wr_wrk_inst', value: 124181386)
            column(name: 'work_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'system_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000011881)
            column(name: 'payee_account_number', value: 1000011881)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'e951defc-79c7-48d0-b7c7-958df4bdf2cb')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 791.8000000000)
            column(name: 'volume_weight', value: 3.7000000000)
            column(name: 'volume_share', value: 0.1516393443)
            column(name: 'value_share', value: 0.4623379657)
            column(name: 'total_share', value: 0.3093360901)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f6411672-02d2-4e2e-8682-69f5ad7db8c4')
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
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
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'f6411672-02d2-4e2e-8682-69f5ad7db8c4')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 240.8000000000)
            column(name: 'volume_weight', value: 10.7000000000)
            column(name: 'volume_share', value: 0.4385245902)
            column(name: 'value_share', value: 0.1406049282)
            column(name: 'total_share', value: 0.2810721113)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5408ee8b-30e0-416f-ada8-cbf08d62b26e')
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
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
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '5408ee8b-30e0-416f-ada8-cbf08d62b26e')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'value_weight', value: 240.8000000000)
            column(name: 'volume_weight', value: 10.7000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'total_share', value: 1.0000000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '149ab28f-c795-4e29-9418-815c87dec127')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 2 for Work Shares by Aggregate Licensee Class Summary Report test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'f88db923-ed05-4980-bb1d-8ae327824346')
            column(name: 'df_fund_pool_uid', value: '3a8eed5d-a2f2-47d2-9cba-b047d9947706')
            column(name: 'df_aggregate_licensee_class_id', value: 171)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'name', value: 'AACL Usage Batch 2 for Work Shares by Aggregate Licensee Class Summary Report test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 4)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
            column(name: 'name', value: 'AACL Scenario 2 for Work Shares by Aggregate Licensee Class Summary Report test')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "fund_pool_uid": "149ab28f-c795-4e29-9418-815c87dec127", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '3ae57b51-691d-4a97-95dd-434304325654')
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
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
            column(name: 'number_of_copies', value: 155)
            column(name: 'check_number', value: 578945)
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '3ae57b51-691d-4a97-95dd-434304325654')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 680.0000000000)
            column(name: 'volume_weight', value: 10.0000000000)
            column(name: 'volume_share', value: 0.4098360656)
            column(name: 'value_share', value: 0.3970571062)
            column(name: 'total_share', value: 0.4095917984)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '4444ed28-cba5-4bea-9923-64a6264bca9d')
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
            column(name: 'wr_wrk_inst', value: 124181386)
            column(name: 'work_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'system_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000011881)
            column(name: 'payee_account_number', value: 1000011881)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'check_number', value: 578945)
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '4444ed28-cba5-4bea-9923-64a6264bca9d')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 791.8000000000)
            column(name: 'volume_weight', value: 3.7000000000)
            column(name: 'volume_share', value: 0.1516393443)
            column(name: 'value_share', value: 0.4623379657)
            column(name: 'total_share', value: 0.3093360901)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'af7a5a9c-363c-4360-8fc0-7a318528f431')
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
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
            column(name: 'check_number', value: 578945)
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'af7a5a9c-363c-4360-8fc0-7a318528f431')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 240.8000000000)
            column(name: 'volume_weight', value: 10.7000000000)
            column(name: 'volume_share', value: 0.4385245902)
            column(name: 'value_share', value: 0.1406049282)
            column(name: 'total_share', value: 0.2810721113)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '190a8e32-37ed-4fe2-987a-b481849c7939')
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
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
            column(name: 'check_number', value: 578945)
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '190a8e32-37ed-4fe2-987a-b481849c7939')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'value_weight', value: 240.8000000000)
            column(name: 'volume_weight', value: 10.7000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'total_share', value: 1.0000000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        rollback {
            dbRollback
        }
    }
}
