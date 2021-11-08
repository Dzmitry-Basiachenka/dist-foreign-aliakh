databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-04-02-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for testFindCountByScenarioIdAndRhAccountNumber, testFindByScenarioIdAndRhAccountNumber, ' +
                'testSortingFindByScenarioIdAndRhAccountNumber')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '39548ee4-7929-477e-b9d2-bcb1e76f8037')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 1')
            column(name: 'total_amount', value: 10.95)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '7dc93e89-d1f4-4721-81be-fd32606e4a66')
            column(name: 'df_fund_pool_uid', value: '39548ee4-7929-477e-b9d2-bcb1e76f8037')
            column(name: 'df_aggregate_licensee_class_id', value: 108)
            column(name: 'gross_amount', value: 10.95)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '91f3c9ba-1b61-4dcc-b087-f88f89d22c35')
            column(name: 'name', value: 'AACL Usage Batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '64f3c9ba-1b61-4dcc-b087-f88f89d22c58')
            column(name: 'name', value: 'AACL Usage Batch 2')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '20bed3d9-8da3-470f-95d7-d839a41488d4')
            column(name: 'name', value: 'AACL Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2f3988e1-7cca-42b2-bdf8-a8850dbf315b')
            column(name: 'df_usage_batch_uid', value: '64f3c9ba-1b61-4dcc-b087-f88f89d22c58')
            column(name: 'df_scenario_uid', value: '20bed3d9-8da3-470f-95d7-d839a41488d4')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000011450)
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
            column(name: 'df_usage_aacl_uid', value: '2f3988e1-7cca-42b2-bdf8-a8850dbf315b')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 115)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
            column(name: 'original_publication_type', value: 'NATL-INTL Newspaper')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '10bd15c1-b907-457e-94c0-9d6bb66e706f')
            column(name: 'df_usage_batch_uid', value: '91f3c9ba-1b61-4dcc-b087-f88f89d22c35')
            column(name: 'df_scenario_uid', value: '20bed3d9-8da3-470f-95d7-d839a41488d4')
            column(name: 'wr_wrk_inst', value: 109040891)
            column(name: 'work_title', value: 'Biological Journal')
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 1000011450)
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
            column(name: 'df_usage_aacl_uid', value: '10bd15c1-b907-457e-94c0-9d6bb66e706f')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 200)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'publication_type_weight', value: 1.50)
            column(name: 'original_publication_type', value: 'Consumer Magazine')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'ceef1548-700e-48a2-a732-f9d183b3d2e3')
            column(name: 'df_scenario_uid', value: '20bed3d9-8da3-470f-95d7-d839a41488d4')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'ceef1548-700e-48a2-a732-f9d183b3d2e3')
            column(name: 'df_usage_batch_uid', value: '91f3c9ba-1b61-4dcc-b087-f88f89d22c35')
        }

        rollback {
            dbRollback
        }
    }
}
