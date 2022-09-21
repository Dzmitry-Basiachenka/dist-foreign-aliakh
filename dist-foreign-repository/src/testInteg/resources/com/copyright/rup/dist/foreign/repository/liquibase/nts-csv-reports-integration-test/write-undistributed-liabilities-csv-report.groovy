databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-07-01-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testWriteUndistributedLiabilitiesCsvReport')

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

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '5dc4d7f0-2f77-4b3a-9b11-0033c300fdc6')
            column(name: 'df_scenario_uid', value: 'ca163655-8978-4a45-8fe3-c3b5572c6879')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '5dc4d7f0-2f77-4b3a-9b11-0033c300fdc6')
            column(name: 'df_usage_batch_uid', value: 'e558e176-901e-4101-9418-493bbcbd9679')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '5dc4d7f0-2f77-4b3a-9b11-0033c300fdc6')
            column(name: 'df_usage_batch_uid', value: '7083ae7d-0079-41fb-aab1-8ae2bd5db8fe')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '429b9acd-f137-40c3-806d-dabae2315027')
            column(name: 'df_usage_batch_uid', value: 'e558e176-901e-4101-9418-493bbcbd9679')
            column(name: 'df_scenario_uid', value: 'ca163655-8978-4a45-8fe3-c3b5572c6879')
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
            column(name: 'df_scenario_uid', value: 'ca163655-8978-4a45-8fe3-c3b5572c6879')
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
            column(name: 'df_scenario_uid', value: 'ca163655-8978-4a45-8fe3-c3b5572c6879')
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
            column(name: 'df_scenario_uid', value: 'ca163655-8978-4a45-8fe3-c3b5572c6879')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '230c34cf-c109-4ac2-8c4c-153d9dbb591c')
            column(name: 'df_scenario_uid', value: 'e3a18e26-dc58-416d-a707-5a7bc843d6a4')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '230c34cf-c109-4ac2-8c4c-153d9dbb591c')
            column(name: 'df_usage_batch_uid', value: 'edb66e6b-a061-4ef8-bc30-28f00a2edaeb')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '43ba1d77-b00f-4ae6-845a-332530ddc2eb')
            column(name: 'df_usage_batch_uid', value: 'edb66e6b-a061-4ef8-bc30-28f00a2edaeb')
            column(name: 'df_scenario_uid', value: 'e3a18e26-dc58-416d-a707-5a7bc843d6a4')
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

        rollback {
            dbRollback
        }
    }
}
