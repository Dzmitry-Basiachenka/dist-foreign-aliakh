databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-07-01-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("Insert test data for testNtsUndistributedLiabilitiesCsvReport")

        //should be include in the report
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e558e176-901e-4101-9418-493bbcbd9679')
            column(name: 'name', value: 'NTS Batch for Nts undistributed liabilities report 1')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        //should be include in the report
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '7083ae7d-0079-41fb-aab1-8ae2bd5db8fe')
            column(name: 'name', value: 'NTS Batch for Nts undistributed liabilities report 2')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'ca163655-8978-4a45-8fe3-c3b5572c6879')
            column(name: 'name', value: 'Test NTS scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":300.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter") {
            column(name: "df_scenario_usage_filter_uid", value: "5dc4d7f0-2f77-4b3a-9b11-0033c300fdc6")
            column(name: "df_scenario_uid", value: "ca163655-8978-4a45-8fe3-c3b5572c6879")
            column(name: "product_family", value: "NTS")
            column(name: "status_ind", value: "ELIGIBLE")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter_to_usage_batches_ids_map") {
            column(name: "df_scenario_usage_filter_uid", value: "5dc4d7f0-2f77-4b3a-9b11-0033c300fdc6")
            column(name: "df_usage_batch_uid", value: "e558e176-901e-4101-9418-493bbcbd9679")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter_to_usage_batches_ids_map") {
            column(name: "df_scenario_usage_filter_uid", value: "5dc4d7f0-2f77-4b3a-9b11-0033c300fdc6")
            column(name: "df_usage_batch_uid", value: "7083ae7d-0079-41fb-aab1-8ae2bd5db8fe")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '429b9acd-f137-40c3-806d-dabae2315027')
            column(name: 'df_usage_batch_uid', value: 'e558e176-901e-4101-9418-493bbcbd9679')
            column(name: "df_scenario_uid", value: "ca163655-8978-4a45-8fe3-c3b5572c6879")
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 900.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'service_fee_amount', value: 288.00)
            column(name: 'net_amount', value: 612.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '429b9acd-f137-40c3-806d-dabae2315027')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4e059d20-3224-44bb-976a-c8e3b64e4406')
            column(name: 'df_usage_batch_uid', value: 'e558e176-901e-4101-9418-493bbcbd9679')
            column(name: "df_scenario_uid", value: "ca163655-8978-4a45-8fe3-c3b5572c6879")
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'service_fee', value: 0.18500)
            column(name: 'service_fee_amount', value: 37.00)
            column(name: 'net_amount', value: 163.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4e059d20-3224-44bb-976a-c8e3b64e4406')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bb76749b-19cb-44b2-9644-666bad872caf')
            column(name: 'df_usage_batch_uid', value: 'e558e176-901e-4101-9418-493bbcbd9679')
            column(name: 'wr_wrk_inst', value: 642267671)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bb76749b-19cb-44b2-9644-666bad872caf')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c8de604d-eb49-4d8f-9a42-2362fbca3277')
            column(name: 'df_usage_batch_uid', value: '7083ae7d-0079-41fb-aab1-8ae2bd5db8fe')
            column(name: "df_scenario_uid", value: "ca163655-8978-4a45-8fe3-c3b5572c6879")
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 900.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'service_fee_amount', value: 288.00)
            column(name: 'net_amount', value: 612.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c8de604d-eb49-4d8f-9a42-2362fbca3277')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ae41625f-be8a-43c1-8310-6c09612181a8')
            column(name: 'df_usage_batch_uid', value: '7083ae7d-0079-41fb-aab1-8ae2bd5db8fe')
            column(name: "df_scenario_uid", value: "ca163655-8978-4a45-8fe3-c3b5572c6879")
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'service_fee', value: 0.18500)
            column(name: 'service_fee_amount', value: 37.00)
            column(name: 'net_amount', value: 163.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ae41625f-be8a-43c1-8310-6c09612181a8')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 200)
        }

        //should be exclude from the report
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'edb66e6b-a061-4ef8-bc30-28f00a2edaeb')
            column(name: 'name', value: 'NTS Batch for Nts undistributed liabilities report 3')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e3a18e26-dc58-416d-a707-5a7bc843d6a4')
            column(name: 'name', value: 'Test NTS scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":200.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter") {
            column(name: "df_scenario_usage_filter_uid", value: "230c34cf-c109-4ac2-8c4c-153d9dbb591c")
            column(name: "df_scenario_uid", value: "e3a18e26-dc58-416d-a707-5a7bc843d6a4")
            column(name: "product_family", value: "NTS")
            column(name: "status_ind", value: "ELIGIBLE")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter_to_usage_batches_ids_map") {
            column(name: "df_scenario_usage_filter_uid", value: "230c34cf-c109-4ac2-8c4c-153d9dbb591c")
            column(name: "df_usage_batch_uid", value: "edb66e6b-a061-4ef8-bc30-28f00a2edaeb")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '43ba1d77-b00f-4ae6-845a-332530ddc2eb')
            column(name: 'df_usage_batch_uid', value: 'edb66e6b-a061-4ef8-bc30-28f00a2edaeb')
            column(name: "df_scenario_uid", value: "e3a18e26-dc58-416d-a707-5a7bc843d6a4")
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 900.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'service_fee_amount', value: 288.00)
            column(name: 'net_amount', value: 612.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '43ba1d77-b00f-4ae6-845a-332530ddc2eb')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '30a9a53f-db64-4af3-9616-1e40edcef489')
            column(name: 'wr_wrk_inst', value: 642267671)
            column(name: 'classification', value: 'STM')
        }
    }

    changeSet(id: '2020-07-30-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testWriteNtsServiceFeeTrueUpCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '78fa8e33-6194-4cf6-85a6-ad72f811b8af')
            column(name: 'name', value: 'NTS batch 8')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 900, "non_stm_amount": 1100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56444713-d0d2-4c7f-ad50-f4af99b114e5')
            column(name: 'name', value: 'NTS batch 9')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 500, "non_stm_amount": 700, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '8b62a77c-f55f-4e20-a8bc-0fa4ba611e74')
            column(name: 'name', value: 'NTS batch 10')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 300, "non_stm_amount": 500, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '66171a5c-7a8b-4eed-abc9-82756a32ba0e')
            column(name: 'name', value: 'NTS batch 11')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 0, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        // Service Fee True-up report for this scenario will contain one row; scenario with fund pool
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'a537da01-b211-4b81-b2b9-7dc0c791811a')
            column(name: 'name', value: 'Scenario for empty NTS Service Fee True-up report 1')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario for NTS Service Fee True-up report')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2018-05-15 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 100.00, "pre_service_fee_amount": 200.00,' +
                    '"post_service_fee_amount": 100.00, "pre_service_fee_fund_uid": "815d6736-a34e-4fc8-96c3-662a114fa7f2"}')
        }

        // Service Fee True-up report for this scenario will contain one row; scenario without fund pool
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'dc6df4bd-7059-4975-8898-78b4a50d30b0')
            column(name: 'name', value: 'Scenario for empty NTS Service Fee True-up report')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario for NTS Service Fee True-up report 2')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2018-05-15 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 100.00, "pre_service_fee_amount": 200.00,' +
                    '"post_service_fee_amount": 100.00, "pre_service_fee_fund_uid": null}')
        }

        // Service Fee True-up report for this scenario will be empty
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1871799a-157a-4fb2-82ab-9092bb3b6395')
            column(name: 'name', value: 'Scenario for empty NTS Service Fee True-up report')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'description', value: 'Scenario for empty NTS Service Fee True-up report')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2018-05-15 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 100.00, "pre_service_fee_amount": 0.00,' +
                    '"post_service_fee_amount": 0.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '815d6736-a34e-4fc8-96c3-662a114fa7f2')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'NTS Fund Pool 4')
            column(name: 'total_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'bc304214-12ca-478f-821a-b21e5acd8076')
            column(name: 'df_scenario_uid', value: 'a537da01-b211-4b81-b2b9-7dc0c791811a')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 1080.00)
            column(name: 'net_amount', value: 744.00)
            column(name: 'service_fee_amount', value: 336.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bc304214-12ca-478f-821a-b21e5acd8076')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '119c97c4-34bd-4c64-99a9-925211ab3b8b')
            column(name: 'df_scenario_uid', value: 'a537da01-b211-4b81-b2b9-7dc0c791811a')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 1080.00)
            column(name: 'net_amount', value: 744.00)
            column(name: 'service_fee_amount', value: 336.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '119c97c4-34bd-4c64-99a9-925211ab3b8b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'df5df8fa-abb2-497f-9cdd-a55cb0772748')
            column(name: 'df_scenario_uid', value: 'a537da01-b211-4b81-b2b9-7dc0c791811a')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'payee_account_number', value: 1000000001)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 1440.00)
            column(name: 'net_amount', value: 1216.00)
            column(name: 'service_fee_amount', value: 224.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'df5df8fa-abb2-497f-9cdd-a55cb0772748')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '782f7828-abd8-40c1-9783-b2e7f12b5880')
            column(name: 'df_scenario_uid', value: 'dc6df4bd-7059-4975-8898-78b4a50d30b0')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 550.00)
            column(name: 'net_amount', value: 390.00)
            column(name: 'service_fee_amount', value: 160.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '782f7828-abd8-40c1-9783-b2e7f12b5880')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ec3d55f3-8654-4e72-9122-8994f581a344')
            column(name: 'df_scenario_uid', value: 'dc6df4bd-7059-4975-8898-78b4a50d30b0')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 550.00)
            column(name: 'net_amount', value: 470.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ec3d55f3-8654-4e72-9122-8994f581a344')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '21c06a83-9cea-43ff-8773-e6bd4090f40b')
            column(name: 'df_scenario_uid', value: '1871799a-157a-4fb2-82ab-9092bb3b6395')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-11-03')
            column(name: 'ccc_event_id', value: '9875643')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2017-11-03')
            column(name: 'lm_detail_id', value: '36799c8c-fbfc-4aa8-a9ce-d49c5b054214')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '21c06a83-9cea-43ff-8773-e6bd4090f40b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'ce7026a1-5cc2-4c62-98e1-e8d0cbb4ea62')
            column(name: 'df_scenario_uid', value: 'a537da01-b211-4b81-b2b9-7dc0c791811a')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'ce7026a1-5cc2-4c62-98e1-e8d0cbb4ea62')
            column(name: 'df_usage_batch_uid', value: '78fa8e33-6194-4cf6-85a6-ad72f811b8af')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'ce7026a1-5cc2-4c62-98e1-e8d0cbb4ea62')
            column(name: 'df_usage_batch_uid', value: '56444713-d0d2-4c7f-ad50-f4af99b114e5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'a4fee0ac-effc-436f-a884-c7ca61badf50')
            column(name: 'df_scenario_uid', value: 'dc6df4bd-7059-4975-8898-78b4a50d30b0')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'a4fee0ac-effc-436f-a884-c7ca61badf50')
            column(name: 'df_usage_batch_uid', value: '8b62a77c-f55f-4e20-a8bc-0fa4ba611e74')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'c61c31b8-e216-4740-a77a-06bc880cbac7')
            column(name: 'df_scenario_uid', value: '1871799a-157a-4fb2-82ab-9092bb3b6395')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'c61c31b8-e216-4740-a77a-06bc880cbac7')
            column(name: 'df_usage_batch_uid', value: '66171a5c-7a8b-4eed-abc9-82756a32ba0e')
        }

        rollback ""
    }

    changeSet(id: '2019-07-02-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testWriteWorkClassificationCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'name', value: 'NTS fund pool 1')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'product_family', value: 'NTS')
            column(name: 'initial_usages_count', value: 3)
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":7,"stm_amount":700,"stm_minimum_amount":50,"non_stm_amount":5000,"fund_pool_period_from":2010,"markets":["Bus","Doc Del"],"fund_pool_period_to":2012}')
        }

        // has classification
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '85093193-00d9-436b-8fbc-078511b1d335')
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'wr_wrk_inst', value: 987654321)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '85093193-00d9-436b-8fbc-078511b1d335')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2012)
            column(name: 'market_period_to', value: 2014)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '0b3f6bb1-40b7-4d11-ba53-d54e9d67e61f')
            column(name: 'wr_wrk_inst', value: 987654321)
            column(name: 'classification', value: 'NON-STM')
            column(name: 'updated_datetime', value: '2019-10-16')
            column(name: 'updated_by_user', value: 'user@copyright.com')
        }

        // archived
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'e7e02a20-9e54-11e9-b475-0800200c9a66')
            column(name: 'wr_wrk_inst', value: 987654321)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 500.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e7e02a20-9e54-11e9-b475-0800200c9a66')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2012)
            column(name: 'market_period_to', value: 2014)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 500)
        }

        // work w/o classification
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5f956b4b-3b09-457f-a306-f36fc55710af')
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'wr_wrk_inst', value: 9876543212)
            column(name: 'work_title', value: 'future of children')
            column(name: 'system_title', value: 'future of children')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5f956b4b-3b09-457f-a306-f36fc55710af')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2010)
            column(name: 'market_period_to', value: 2011)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 500)
        }

        // work not suits the search criteria
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b51340ad-cf32-4c38-8445-4455e4ae81eb')
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'wr_wrk_inst', value: 918765432)
            column(name: 'work_title', value: 'Corporate identity manuals')
            column(name: 'system_title', value: 'Corporate identity manuals')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b51340ad-cf32-4c38-8445-4455e4ae81eb')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2010)
            column(name: 'market_period_to', value: 2011)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '84ba864e-716a-4103-bcd7-180563695f50')
            column(name: 'wr_wrk_inst', value: 98765432)
            column(name: 'classification', value: 'STM')
        }

        rollback ""
    }

    changeSet(id: '2019-07-30-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testExportScenarioUsagesCsvReportWithNts')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '85ba864e-1939-4a60-9fab-888b84199321')
            column(name: 'name', value: 'Ownership Adjustment Report Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter") {
            column(name: "df_scenario_usage_filter_uid", value: "85ba864e-b1f9-4857-8f9d-17a1de9f5811")
            column(name: "df_scenario_uid", value: "85ba864e-1939-4a60-9fab-888b84199321")
            column(name: "product_family", value: "NTS")
            column(name: "status_ind", value: "ELIGIBLE")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'name', value: 'NTS fund pool 2')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'product_family', value: 'NTS')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":7,"stm_amount":700,"stm_minimum_amount":50,"non_stm_amount":5000,"fund_pool_period_from":2010,"markets":["Bus","Doc Del"],"fund_pool_period_to":2012}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '89ba847e-3b09-457f-a306-f36fc55710af')
            column(name: 'df_usage_batch_uid', value: 'f17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'df_scenario_uid', value: '85ba864e-1939-4a60-9fab-888b84199321')
            column(name: 'wr_wrk_inst', value: 9876543212)
            column(name: 'work_title', value: 'future of children')
            column(name: 'system_title', value: 'future of children')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'service_fee', value: 0.0)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '89ba847e-3b09-457f-a306-f36fc55710af')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2010)
            column(name: 'market_period_to', value: 2011)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 500)
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter_to_usage_batches_ids_map") {
            column(name: "df_scenario_usage_filter_uid", value: "85ba864e-b1f9-4857-8f9d-17a1de9f5811")
            column(name: "df_usage_batch_uid", value: "f17ebc80-e74e-436d-ba6e-acf3d355b7ff")
        }

        rollback ""
    }

    changeSet(id: '2019-10-28-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteNtsWithdrawnBatchSummaryCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund1')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '4fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund2')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '5fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund3')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a6669f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: 1000002900)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '99991909-744c-4766-ad67-fdc9e2c043eb')
            column(name: 'rh_account_number', value: 1000002901)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '99966cac-2468-48d4-b346-93d3458a656a')
            column(name: 'name', value: 'NTS Withdrawn Report Batch')
            column(name: 'rro_account_number', value: 1000002901)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 30001)
            column(name: 'initial_usages_count', value: 4)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '88866cac-2468-48d4-b346-93d3458a656a')
            column(name: 'name', value: 'NTS Withdrawn Report Batch Included To Be Distributed')
            column(name: 'rro_account_number', value: 1000002900)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 30002)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "66649fd0-c094-4a72-979e-00cf462fb3eb")
            column(name: "df_usage_batch_uid", value: "99966cac-2468-48d4-b346-93d3458a656a")
            column(name: "product_family", value: "FAS")
            column(name: "work_title", value: "Wissenschaft & Forschung Japan")
            column(name: "status_ind", value: "NTS_WITHDRAWN")
            column(name: "standard_number", value: "2192-3558")
            column(name: "number_of_copies", value: "100")
            column(name: "gross_amount", value: "200")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "66649fd0-c094-4a72-979e-00cf462fb3eb")
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "Network for Science")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "30.86")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "6666d6f8-7ac6-4ae7-9bdc-ba33a58a5bad")
            column(name: "df_usage_batch_uid", value: "99966cac-2468-48d4-b346-93d3458a656a")
            column(name: "product_family", value: "FAS")
            column(name: "wr_wrk_inst", value: "180382915")
            column(name: "work_title", value: "2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA")
            column(name: "status_ind", value: "NTS_WITHDRAWN")
            column(name: "standard_number", value: "2192-3566")
            column(name: "number_of_copies", value: "250232")
            column(name: "gross_amount", value: "200")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "6666d6f8-7ac6-4ae7-9bdc-ba33a58a5bad")
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "Network for Science")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "10000")
            column(name: "reported_value", value: "30.86")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "666644ba-58d6-4adb-9dc1-602eb09052ce")
            column(name: "df_usage_batch_uid", value: "99966cac-2468-48d4-b346-93d3458a656a")
            column(name: "product_family", value: "FAS")
            column(name: "work_title", value: "Wissenschaft & Forschung Japan")
            column(name: "status_ind", value: "NTS_WITHDRAWN")
            column(name: "standard_number", value: "2192-3558")
            column(name: "number_of_copies", value: "100")
            column(name: "gross_amount", value: "200")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "666644ba-58d6-4adb-9dc1-602eb09052ce")
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "Network for Science")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "10000")
            column(name: "reported_value", value: "30.86")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "6666dea5-4acc-4d87-b264-a67ab17a93ae")
            column(name: "df_usage_batch_uid", value: "99966cac-2468-48d4-b346-93d3458a656a")
            column(name: "wr_wrk_inst", value: "103658926")
            column(name: "work_title", value: "Nitrates")
            column(name: "status_ind", value: "ELIGIBLE")
            column(name: "product_family", value: "FAS")
            column(name: "standard_number", value: "5475802112214578XX")
            column(name: "number_of_copies", value: "250232")
            column(name: "gross_amount", value: "200")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "6666dea5-4acc-4d87-b264-a67ab17a93ae")
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "IEEE")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "30.86")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "11149fd0-c094-4a72-979e-00cf462fb3eb")
            column(name: "df_usage_batch_uid", value: "88866cac-2468-48d4-b346-93d3458a656a")
            column(name: "product_family", value: "NTS")
            column(name: "work_title", value: "Wissenschaft & Forschung Japan")
            column(name: "status_ind", value: "TO_BE_DISTRIBUTED")
            column(name: "standard_number", value: "2192-3558")
            column(name: "number_of_copies", value: "100")
            column(name: "gross_amount", value: "202")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "11149fd0-c094-4a72-979e-00cf462fb3eb")
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "Network for Science")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "30.86")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "2226d6f8-7ac6-4ae7-9bdc-ba33a58a5bad")
            column(name: "df_usage_batch_uid", value: "88866cac-2468-48d4-b346-93d3458a656a")
            column(name: "product_family", value: "NTS")
            column(name: "wr_wrk_inst", value: "180382915")
            column(name: "work_title", value: "2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA")
            column(name: "status_ind", value: "TO_BE_DISTRIBUTED")
            column(name: "standard_number", value: "2192-3566")
            column(name: "number_of_copies", value: "250232")
            column(name: "gross_amount", value: "202")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "2226d6f8-7ac6-4ae7-9bdc-ba33a58a5bad")
            column(name: "df_fund_pool_uid", value: "4fef25b0-c0d1-4819-887f-4c6acc01390e")
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "Network for Science")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "10000")
            column(name: "reported_value", value: "30.86")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "3336d6f8-7ac6-4ae7-9bdc-ba33a58a5bad")
            column(name: "df_usage_batch_uid", value: "88866cac-2468-48d4-b346-93d3458a656a")
            column(name: "product_family", value: "NTS")
            column(name: "wr_wrk_inst", value: "180382915")
            column(name: "work_title", value: "2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA")
            column(name: "status_ind", value: "TO_BE_DISTRIBUTED")
            column(name: "standard_number", value: "2192-3566")
            column(name: "number_of_copies", value: "250232")
            column(name: "gross_amount", value: "202")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "3336d6f8-7ac6-4ae7-9bdc-ba33a58a5bad")
            column(name: "df_fund_pool_uid", value: "5fef25b0-c0d1-4819-887f-4c6acc01390e")
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "Network for Science")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "10000")
            column(name: "reported_value", value: "30.86")
        }

        rollback ""
    }

    changeSet(id: '2019-12-11-01', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testWriteNtsUsagesCsvReport')

        // NTS batch with ELIGIBLE usages
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f20ac1a3-eee4-4027-b5fb-def9adf0f871')
            column(name: 'name', value: 'NTS batch')
            column(name: 'rro_account_number', value: 2000017001)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2019-01-01')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Bus,Univ,Doc Del"], "stm_amount": 10, "non_stm_amount": 20, "stm_minimum_amount": 30, "non_stm_minimum_amount": 40, "fund_pool_period_to": 2017, "fund_pool_period_from": 2017}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9705a377-d59a-4c84-bd26-7c754aab92e2')
            column(name: 'df_usage_batch_uid', value: 'f20ac1a3-eee4-4027-b5fb-def9adf0f871')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9705a377-d59a-4c84-bd26-7c754aab92e2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '581b71b0-07b1-4db1-9a3b-351c5c5a8cf0')
            column(name: 'df_usage_batch_uid', value: 'f20ac1a3-eee4-4027-b5fb-def9adf0f871')
            column(name: 'wr_wrk_inst', value: 743904744)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: 1000002901)
            column(name: 'payee_account_number', value: 1000002901)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '581b71b0-07b1-4db1-9a3b-351c5c5a8cf0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        // NTS batch with LOCKED usage
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c0c07d51-2216-43c3-b61b-b904d86ec36a')
            column(name: 'name', value: 'Test Batch 2')
            column(name: 'rro_account_number', value: 2000017001)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 8972.00)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"markets": ["Bus,Univ,Doc Del"], "stm_amount": 10, "non_stm_amount": 20, "stm_minimum_amount": 30, "non_stm_minimum_amount": 40, "fund_pool_period_to": 2017, "fund_pool_period_from": 2017}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'c90921d6-3315-4673-8825-2e0c6f7229ee')
            column(name: 'name', value: 'NTS Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'b5844849-8399-4ee1-a15b-0908a88f6570')
            column(name: 'df_scenario_uid', value: 'c90921d6-3315-4673-8825-2e0c6f7229ee')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bfc2ffbf-1b70-447e-a604-a6dee260d648')
            column(name: 'df_usage_batch_uid', value: 'c0c07d51-2216-43c3-b61b-b904d86ec36a')
            column(name: 'df_scenario_uid', value: 'c90921d6-3315-4673-8825-2e0c6f7229ee')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'work_title', value: 'CHICKEN BREAST ON GRILL WITH FLAMES')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'payee_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'net_amount', value: 6100.9872)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 2871.0528)
            column(name: 'gross_amount', value: 8972.04)
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bfc2ffbf-1b70-447e-a604-a6dee260d648')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 9900.00)
            column(name: 'is_rh_participating_flag', value: false)
            column(name: 'is_payee_participating_flag', value: false)
        }
    }

    changeSet(id: '2019-12-18-01', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteAuditNtsCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0c0a379a-461c-4e84-8062-326ece3c1f65')
            column(name: 'name', value: 'Test Batch 4')
            column(name: 'rro_account_number', value: 2000017001)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 8972.00)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"markets": ["Bus,Univ,Doc Del"], "stm_amount": 10, "non_stm_amount": 20, "stm_minimum_amount": 30, "non_stm_minimum_amount": 40, "fund_pool_period_to": 2017, "fund_pool_period_from": 2017}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b9a487f4-77b6-4264-8a53-ed1eab585a4a')
            column(name: 'df_usage_batch_uid', value: '0c0a379a-461c-4e84-8062-326ece3c1f65')
            column(name: 'df_scenario_uid', value: 'c90921d6-3315-4673-8825-2e0c6f7229ee')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'work_title', value: 'CHICKEN BREAST ON GRILL WITH FLAMES')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'payee_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'net_amount', value: 6100.9872)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 2871.0528)
            column(name: 'gross_amount', value: 8972.04)
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b9a487f4-77b6-4264-8a53-ed1eab585a4a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 9900.00)
            column(name: 'is_rh_participating_flag', value: false)
            column(name: 'is_payee_participating_flag', value: false)
        }
    }

    changeSet(id: '2020-01-03-01', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testWriteArchivedNtsScenarioUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1da0461a-92f9-40cc-a3c1-9b972505b9c9')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'name', value: 'CFC/ Center Fran dexploitation du droit de Copie')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1ead8a3e-1231-43a5-a3c5-ed766abe5a2f')
            column(name: 'name', value: 'NTS Scenario with archived usages')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario description')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":300.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '4a5bf68a-67fb-4dd5-ac26-94ef19dc25de')
            column(name: 'df_scenario_uid', value: '1ead8a3e-1231-43a5-a3c5-ed766abe5a2f')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'e256decd-46ac-4625-aa80-cafb26f93c2a')
            column(name: 'df_scenario_uid', value: '1ead8a3e-1231-43a5-a3c5-ed766abe5a2f')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 6000.00)
            column(name: 'net_amount', value: 4080.00)
            column(name: 'service_fee_amount', value: 1920.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e256decd-46ac-4625-aa80-cafb26f93c2a')
            column(name: 'reported_value', value: 0.00)
            column(name: 'is_rh_participating_flag', value: 'false')
        }
    }
}