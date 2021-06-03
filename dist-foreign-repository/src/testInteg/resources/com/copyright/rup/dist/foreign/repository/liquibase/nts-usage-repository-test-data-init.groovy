databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-01-28-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for testInsertUsages, testInsertUsagesZeroFundPoolAmount")

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'name', value: 'Archived scenario')
            column(name: 'status_ind', value: 'ARCHIVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'name', value: 'Archived batch 1')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 15000.00)
            column(name: 'initial_usages_count', value: 6)
        }

        // included
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'bc0fe9bc-9b24-4324-b624-eed0d9773e19')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1176.9160)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '9873214')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '63b3da58-ac6b-4946-bd67-37a251769467')
            column(name: 'comment', value: 'test comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bc0fe9bc-9b24-4324-b624-eed0d9773e19')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Gov')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        // included
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '5b8c2754-2f63-425a-a95f-dbd744e815fc')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '9873214')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '4c56f661-5bef-4c82-8526-fd987b5455f8')
            column(name: 'comment', value: 'test comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5b8c2754-2f63-425a-a95f-dbd744e815fc')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: 2016)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        // included unclassified
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'f06de87a-511e-46ae-88a8-fc9778efc194')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 105062654)
            column(name: 'work_title', value: 'Our fathers lies')
            column(name: 'system_title', value: 'Our fathers lies')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '9873214')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '4c56f661-5bef-4c82-8526-fd987b5455f8')
            column(name: 'comment', value: 'test comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f06de87a-511e-46ae-88a8-fc9778efc194')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: 2014)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '1d182000-4c91-11e9-b475-0800200c9a66')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'classification', value: 'NON-STM')
        }

        // excluded by market_period_to
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '33113b79-791a-4aa9-b192-12b292c32823')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1200.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '9873214')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: 'bfa206bb-83fa-4c71-9a89-99f743018237')
            column(name: 'comment', value: 'test comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '33113b79-791a-4aa9-b192-12b292c32823')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Gov')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2014)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        // excluded by market
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'a9fac1e1-5a34-416b-9ecb-f2615b24d1c1')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 632876487)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1200.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '9873214')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: 'b8688ed5-3986-4f41-8a16-d33cd44e2ab5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a9fac1e1-5a34-416b-9ecb-f2615b24d1c1')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: 2016)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        // excluded as belletristic
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'b6fc6063-a0ea-4e4d-832d-b1cbc896963d')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 632876487)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1200.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '9873214')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '83bb6666-7484-49d6-8ed1-c653286590f3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b6fc6063-a0ea-4e4d-832d-b1cbc896963d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: 2016)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b9d0ea49-9e38-4bb0-a7e0-0ca299e3dcfa')
            column(name: 'name', value: 'NTS fund pool for testInsertNtsUsages')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-03-10')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'product_family', value: 'NTS')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":7,"stm_amount":700,"stm_minimum_amount":50,"non_stm_amount":5000,"fund_pool_period_from":2015,"markets":["Edu","Gov"],"fund_pool_period_to":2016}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '963b6497-1e95-4959-bf9a-416ad527d6a0')
            column(name: 'wr_wrk_inst', value: 632876487)
            column(name: 'classification', value: 'BELLETRISTIC')
        }
    }

    changeSet(id: '2019-03-29-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testDeleteFromNtsFundPool')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0f00e96b-eed7-4f26-8004-3370ec30da45')
            column(name: 'name', value: 'FAS batch test delete NTS fund pool')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'gross_amount', value: 10.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ba95f0b3-dc94-4925-96f2-93d05db9c469')
            column(name: 'df_usage_batch_uid', value: '0f00e96b-eed7-4f26-8004-3370ec30da45')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ba95f0b3-dc94-4925-96f2-93d05db9c469')
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 10)
        }
    }

    changeSet(id: '2019-04-30-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testDeleteBelletristicByScenarioId')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'dd4fca1d-eac8-4b76-85e4-121b7971d049')
            column(name: 'name', value: 'Test NTS scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":300.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b614f8a0-9271-4cae-8a26-b39a83cb7c46')
            column(name: 'name', value: 'NTS batch 1')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bbbd64db-2668-499a-9d18-be8b3f87fbf5')
            column(name: 'df_usage_batch_uid', value: 'b614f8a0-9271-4cae-8a26-b39a83cb7c46')
            column(name: 'df_scenario_uid', value: 'dd4fca1d-eac8-4b76-85e4-121b7971d049')
            column(name: 'wr_wrk_inst', value: 122267672)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '0804709114')
            column(name: 'gross_amount', value: 256.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bbbd64db-2668-499a-9d18-be8b3f87fbf5')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 296.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '83a26087-a3b3-43ca-8b34-c66134fb6edf')
            column(name: 'df_usage_batch_uid', value: 'b614f8a0-9271-4cae-8a26-b39a83cb7c46')
            column(name: 'df_scenario_uid', value: 'dd4fca1d-eac8-4b76-85e4-121b7971d049')
            column(name: 'wr_wrk_inst', value: 159526527)
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 1452.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '83a26087-a3b3-43ca-8b34-c66134fb6edf')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 162.41)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6cad4cf2-6a19-4e5b-b4e0-f2f7a62ff91c')
            column(name: 'df_usage_batch_uid', value: 'b614f8a0-9271-4cae-8a26-b39a83cb7c46')
            column(name: 'df_scenario_uid', value: 'dd4fca1d-eac8-4b76-85e4-121b7971d049')
            column(name: 'wr_wrk_inst', value: 569526592)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 359.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6cad4cf2-6a19-4e5b-b4e0-f2f7a62ff91c')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 86.41)
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter") {
            column(name: "df_scenario_usage_filter_uid", value: "391eb094-f4e6-4601-b463-ef4058d4901f")
            column(name: "df_scenario_uid", value: "dd4fca1d-eac8-4b76-85e4-121b7971d049")
            column(name: "product_family", value: "NTS")
            column(name: "status_ind", value: "ELIGIBLE")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter_to_usage_batches_ids_map") {
            column(name: "df_scenario_usage_filter_uid", value: "391eb094-f4e6-4601-b463-ef4058d4901f")
            column(name: "df_usage_batch_uid", value: "b614f8a0-9271-4cae-8a26-b39a83cb7c46")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '30a9a53f-db53-4af3-9616-1e40edcef489')
            column(name: 'wr_wrk_inst', value: 122267672)
            column(name: 'classification', value: 'BELLETRISTIC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '092e0fa4-d0ec-4d93-b700-7d5cb096a942')
            column(name: 'wr_wrk_inst', value: 159526527)
            column(name: 'classification', value: 'STM')
        }
    }

    changeSet(id: '2019-04-22-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testDeleteFromNtsScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '224180f9-0406-4181-9ad2-23e3804298aa')
            column(name: 'name', value: 'NTS Batch associated with Scenario')
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
            column(name: "df_usage_batch_uid", value: "224180f9-0406-4181-9ad2-23e3804298aa")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c09aa888-85a5-4377-8c7a-85d84d255b5a')
            column(name: 'df_usage_batch_uid', value: '224180f9-0406-4181-9ad2-23e3804298aa')
            column(name: "df_scenario_uid", value: "ca163655-8978-4a45-8fe3-c3b5572c6879")
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'payee_account_number', value: 1000009997)
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
            column(name: 'df_usage_fas_uid', value: 'c09aa888-85a5-4377-8c7a-85d84d255b5a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 900)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '45445974-5bee-477a-858b-e9e8c1a642b8')
            column(name: 'df_usage_batch_uid', value: '224180f9-0406-4181-9ad2-23e3804298aa')
            column(name: 'wr_wrk_inst', value: 642267671)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '45445974-5bee-477a-858b-e9e8c1a642b8')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '30a9a53f-db64-4af3-9616-1e40edcef489')
            column(name: 'wr_wrk_inst', value: 642267671)
            column(name: 'classification', value: 'STM')
        }
    }

    changeSet(id: '2019-03-07-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for findCountForBatch')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1add4836-332c-4dfb-8eb5-d71db01fd275')
            column(name: 'name', value: 'Archived scenario')
            column(name: 'status_ind', value: 'ARCHIVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd685ddcb-c323-41ce-9c44-1dfdeaedcab7')
            column(name: 'name', value: 'Archived batch 2')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 15000.00)
            column(name: 'initial_usages_count', value: 5)
        }

        // included
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '5e9df606-fbd4-4619-94b0-9e1770203101')
            column(name: 'df_usage_batch_uid', value: 'd685ddcb-c323-41ce-9c44-1dfdeaedcab7')
            column(name: 'df_scenario_uid', value: '1add4836-332c-4dfb-8eb5-d71db01fd275')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904753)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1176.9160)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '63b3da58-ac6b-4946-bd67-37a251769467')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5e9df606-fbd4-4619-94b0-9e1770203101')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        // excluded by market_period_to
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '79d240ee-7676-414a-895c-8796cc2d497a')
            column(name: 'df_usage_batch_uid', value: 'd685ddcb-c323-41ce-9c44-1dfdeaedcab7')
            column(name: 'df_scenario_uid', value: '1add4836-332c-4dfb-8eb5-d71db01fd275')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904753)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: 'bfa206bb-83fa-4c71-9a89-99f743018237')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '79d240ee-7676-414a-895c-8796cc2d497a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2014)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        // included
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '979b1729-3933-490b-af83-6db03599fe5e')
            column(name: 'df_usage_batch_uid', value: 'd685ddcb-c323-41ce-9c44-1dfdeaedcab7')
            column(name: 'df_scenario_uid', value: '1add4836-332c-4dfb-8eb5-d71db01fd275')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904753)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '4c56f661-5bef-4c82-8526-fd987b5455f8')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '979b1729-3933-490b-af83-6db03599fe5e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2016)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '4f50cfb6-680f-4bcd-bc67-4c93c7989e1f')
            column(name: 'wr_wrk_inst', value: 243904753)
            column(name: 'classification', value: 'NON-STM')
        }

        // excluded by market
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '29409f02-e84a-48e5-9b31-d05668c40050')
            column(name: 'df_usage_batch_uid', value: 'd685ddcb-c323-41ce-9c44-1dfdeaedcab7')
            column(name: 'df_scenario_uid', value: '1add4836-332c-4dfb-8eb5-d71db01fd275')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 632876488)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: 'b8688ed5-3986-4f41-8a16-d33cd44e2ab5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '29409f02-e84a-48e5-9b31-d05668c40050')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: 2016)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        // excluded as belletristic
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '9e52e1f1-85f4-4d92-a6b8-c9a22c4cdab0')
            column(name: 'df_usage_batch_uid', value: 'd685ddcb-c323-41ce-9c44-1dfdeaedcab7')
            column(name: 'df_scenario_uid', value: '1add4836-332c-4dfb-8eb5-d71db01fd275')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 632876488)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '83bb6666-7484-49d6-8ed1-c653286590f3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9e52e1f1-85f4-4d92-a6b8-c9a22c4cdab0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2016)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'b1f04ae8-fe13-42c2-a43e-4de85ade10c7')
            column(name: 'wr_wrk_inst', value: 632876488)
            column(name: 'classification', value: 'BELLETRISTIC')
        }
    }

    changeSet(id: '2019-06-12-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testCalculateAmountsAndUpdatePayeeByAccountNumber')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'd7e9bae8-6b10-4675-9668-8e3605a47dad')
            column(name: 'name', value: 'Test NTS scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":300.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '2167779f-cbae-4b4b-adc6-fb95aeb3c58d')
            column(name: 'name', value: 'NTS batch 2')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8a80a2e7-4758-4e43-ae42-e8b29802a210')
            column(name: 'df_usage_batch_uid', value: '2167779f-cbae-4b4b-adc6-fb95aeb3c58d')
            column(name: 'df_scenario_uid', value: 'd7e9bae8-6b10-4675-9668-8e3605a47dad')
            column(name: 'wr_wrk_inst', value: 122267672)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '0804709114')
            column(name: 'gross_amount', value: 256.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8a80a2e7-4758-4e43-ae42-e8b29802a210')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 296.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '085268cd-7a0c-414e-8b28-2acb299d9698')
            column(name: 'df_usage_batch_uid', value: '2167779f-cbae-4b4b-adc6-fb95aeb3c58d')
            column(name: 'df_scenario_uid', value: 'd7e9bae8-6b10-4675-9668-8e3605a47dad')
            column(name: 'wr_wrk_inst', value: 159526527)
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 1452.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '085268cd-7a0c-414e-8b28-2acb299d9698')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 162.41)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bfc9e375-c489-4600-9308-daa101eed97c')
            column(name: 'df_usage_batch_uid', value: '2167779f-cbae-4b4b-adc6-fb95aeb3c58d')
            column(name: 'df_scenario_uid', value: 'd7e9bae8-6b10-4675-9668-8e3605a47dad')
            column(name: 'wr_wrk_inst', value: 159526527)
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 145.20)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bfc9e375-c489-4600-9308-daa101eed97c')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 16.24)
        }
    }

    changeSet(id: '2019-06-13-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testApplyPostServiceFeeAmount')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'fd09c318-75e8-4f4d-b384-8c83e8033e25')
            column(name: 'name', value: 'NTS testApplyPostServiceFeeAmount')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 30, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'c4bc09c1-eb9b-41f3-ac93-9cd088dff408')
            column(name: 'name', value: 'NTS testApplyPostServiceFeeAmount')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 30.00, "post_service_fee_amount": 100}')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter") {
            column(name: "df_scenario_usage_filter_uid", value: "3c191f12-4314-470a-b11e-a9a65030dddd")
            column(name: "df_scenario_uid", value: "c4bc09c1-eb9b-41f3-ac93-9cd088dff408")
            column(name: "product_family", value: "NTS")
            column(name: "status_ind", value: "ELIGIBLE")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter_to_usage_batches_ids_map") {
            column(name: "df_scenario_usage_filter_uid", value: "3c191f12-4314-470a-b11e-a9a65030dddd")
            column(name: "df_usage_batch_uid", value: "fd09c318-75e8-4f4d-b384-8c83e8033e25")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7778a37d-6184-42c1-8e23-5841837c5411')
            column(name: 'df_usage_batch_uid', value: 'fd09c318-75e8-4f4d-b384-8c83e8033e25')
            column(name: "df_scenario_uid", value: "c4bc09c1-eb9b-41f3-ac93-9cd088dff408")
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 33)
            column(name: 'service_fee', value: 0.16)
            column(name: 'service_fee_amount', value: 5.28)
            column(name: 'net_amount', value: 27.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7778a37d-6184-42c1-8e23-5841837c5411')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '54247c55-bf6b-4ad6-9369-fb4baea6b19b')
            column(name: 'df_usage_batch_uid', value: 'fd09c318-75e8-4f4d-b384-8c83e8033e25')
            column(name: "df_scenario_uid", value: "c4bc09c1-eb9b-41f3-ac93-9cd088dff408")
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 66)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 21.12)
            column(name: 'net_amount', value: 44.88)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '54247c55-bf6b-4ad6-9369-fb4baea6b19b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 66)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ade68eac-0d79-4d23-861b-499a0c6e91d3')
            column(name: 'df_usage_batch_uid', value: 'fd09c318-75e8-4f4d-b384-8c83e8033e25')
            column(name: 'rh_account_number', value: 7000896777)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 11)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ade68eac-0d79-4d23-861b-499a0c6e91d3')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 11)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'e920c634-f59d-4d9c-82bd-275af99132b6')
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'classification', value: 'STM')
        }
    }

    changeSet(id: '2019-04-25-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for testUpdateUsagesStatusToUnclassified and testFindUsageIdsForClassificationUpdate")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '83027b25-f269-4bec-a8ea-b126431eedbf')
            column(name: 'name', value: 'CADRA_10Dec16')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, "fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '73027b25-f269-4bec-a8ea-b126431eedbe')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c6cb5b07-45c0-4188-9da3-920046eec4cf')
            column(name: 'df_usage_batch_uid', value: '73027b25-f269-4bec-a8ea-b126431eedbe')
            column(name: 'wr_wrk_inst', value: 987632764)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'UNCLASSIFIED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 2502232)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c6cb5b07-45c0-4188-9da3-920046eec4cf')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2255188f-d582-4516-8c08-835cfe1d68c2')
            column(name: 'df_usage_batch_uid', value: '73027b25-f269-4bec-a8ea-b126431eedbe')
            column(name: 'wr_wrk_inst', value: 12318778798)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'UNCLASSIFIED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 35000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2255188f-d582-4516-8c08-835cfe1d68c2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        //testFindProductFamilies
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'cb597f4e-f636-447f-8710-0436d8994d10')
            column(name: 'name', value: 'Batch with usages in WORK_NOT_FOUND status')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 10.00)
            column(name: 'initial_usages_count', value: 1)
        }


        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4dd8cdf8-ca10-422e-bdd5-3220105e6379')
            column(name: 'df_usage_batch_uid', value: 'cb597f4e-f636-447f-8710-0436d8994d10')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'gross_amount', value: 16.40)
            column(name: 'updated_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4dd8cdf8-ca10-422e-bdd5-3220105e6379')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f6cb5b07-45c0-4188-9da3-920046eec4c0')
            column(name: 'df_usage_batch_uid', value: '83027b25-f269-4bec-a8ea-b126431eedbf')
            column(name: 'wr_wrk_inst', value: 122267671)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '0804709114')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f6cb5b07-45c0-4188-9da3-920046eec4c0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 296.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f255188f-d582-4516-8c08-835cfe1d68c3')
            column(name: 'df_usage_batch_uid', value: '83027b25-f269-4bec-a8ea-b126431eedbf')
            column(name: 'wr_wrk_inst', value: 159526526)
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f255188f-d582-4516-8c08-835cfe1d68c3')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 162.41)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'd78924f5-baf1-4c70-aa80-3c49c193b9f0')
            column(name: 'wr_wrk_inst', value: 122267671)
            column(name: 'classification', value: 'STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'a78924f5-4af1-7c70-1a80-5c49c193b9f1')
            column(name: 'wr_wrk_inst', value: 159526526)
            column(name: 'classification', value: 'NON-STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'c78924f5-baf1-4c70-aa80-3c49c193b9ff')
            column(name: 'wr_wrk_inst', value: 987632764)
            column(name: 'classification', value: 'NON-STM')
        }
    }

    changeSet(id: '2020-07-29-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testDeleteFromScenarioByRightsholder')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '907b191b-34a6-4b34-b515-80c4a659b268')
            column(name: 'name', value: 'NTS Batch')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 3000.00)
            column(name: 'initial_usages_count', value: 4)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '03b9357e-0823-4abf-8e31-c615d735bf3b')
            column(name: 'name', value: 'NTS Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'NTS Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter") {
            column(name: "df_scenario_usage_filter_uid", value: "fd0aa145-ec9b-46ad-b3b9-e30ab98b1de6")
            column(name: "df_scenario_uid", value: "03b9357e-0823-4abf-8e31-c615d735bf3b")
            column(name: "product_family", value: "NTS")
            column(name: "status_ind", value: "ELIGIBLE")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter_to_usage_batches_ids_map") {
            column(name: "df_scenario_usage_filter_uid", value: "fd0aa145-ec9b-46ad-b3b9-e30ab98b1de6")
            column(name: "df_usage_batch_uid", value: "907b191b-34a6-4b34-b515-80c4a659b268")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '36ed0b9b-94f7-4c22-af2f-5a4246014ff7')
            column(name: 'df_usage_batch_uid', value: '907b191b-34a6-4b34-b515-80c4a659b268')
            column(name: "df_scenario_uid", value: "03b9357e-0823-4abf-8e31-c615d735bf3b")
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 33)
            column(name: 'service_fee', value: 0.16)
            column(name: 'service_fee_amount', value: 5.28)
            column(name: 'net_amount', value: 27.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '36ed0b9b-94f7-4c22-af2f-5a4246014ff7')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e767a2f5-c7a7-4720-b74d-4c1d5139e12a')
            column(name: 'df_usage_batch_uid', value: '907b191b-34a6-4b34-b515-80c4a659b268')
            column(name: "df_scenario_uid", value: "03b9357e-0823-4abf-8e31-c615d735bf3b")
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 66)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 21.12)
            column(name: 'net_amount', value: 44.88)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e767a2f5-c7a7-4720-b74d-4c1d5139e12a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 66)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2970a59f-f13b-4914-97eb-a51edffcf404')
            column(name: 'df_usage_batch_uid', value: '907b191b-34a6-4b34-b515-80c4a659b268')
            column(name: "df_scenario_uid", value: "03b9357e-0823-4abf-8e31-c615d735bf3b")
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 33)
            column(name: 'service_fee', value: 0.16)
            column(name: 'service_fee_amount', value: 5.28)
            column(name: 'net_amount', value: 27.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2970a59f-f13b-4914-97eb-a51edffcf404')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2040f5ce-fecc-4601-9eab-6d2f3e3854fd')
            column(name: 'df_usage_batch_uid', value: '907b191b-34a6-4b34-b515-80c4a659b268')
            column(name: "df_scenario_uid", value: "03b9357e-0823-4abf-8e31-c615d735bf3b")
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 66)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 21.12)
            column(name: 'net_amount', value: 44.88)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2040f5ce-fecc-4601-9eab-6d2f3e3854fd')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 66)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }
    }

    changeSet(id: '2020-07-30-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testRecalculateAmountsFromExcludedRightshoders')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '04e0d0f3-caf1-409e-8312-52f6fc53979e')
            column(name: 'name', value: 'NTS Batch 2')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '24d11b82-bc2a-429a-92c4-809849d36e75')
            column(name: 'name', value: 'NTS Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'NTS Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter") {
            column(name: "df_scenario_usage_filter_uid", value: "e23c7078-7be9-4c35-8069-4f84d8744b61")
            column(name: "df_scenario_uid", value: "24d11b82-bc2a-429a-92c4-809849d36e75")
            column(name: "product_family", value: "NTS")
            column(name: "status_ind", value: "ELIGIBLE")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter_to_usage_batches_ids_map") {
            column(name: "df_scenario_usage_filter_uid", value: "e23c7078-7be9-4c35-8069-4f84d8744b61")
            column(name: "df_usage_batch_uid", value: "04e0d0f3-caf1-409e-8312-52f6fc53979e")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4604c954-e43b-4606-809a-665c81514dbf')
            column(name: 'df_usage_batch_uid', value: '04e0d0f3-caf1-409e-8312-52f6fc53979e')
            column(name: "df_scenario_uid", value: "24d11b82-bc2a-429a-92c4-809849d36e75")
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 400)
            column(name: 'service_fee', value: 0.16)
            column(name: 'service_fee_amount', value: 64)
            column(name: 'net_amount', value: 336)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4604c954-e43b-4606-809a-665c81514dbf')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '56f91295-db33-4440-b550-9bb515239750')
            column(name: 'df_usage_batch_uid', value: '04e0d0f3-caf1-409e-8312-52f6fc53979e')
            column(name: "df_scenario_uid", value: "24d11b82-bc2a-429a-92c4-809849d36e75")
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 350)
            column(name: 'service_fee', value: 0.16)
            column(name: 'service_fee_amount', value: 56)
            column(name: 'net_amount', value: 294)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '56f91295-db33-4440-b550-9bb515239750')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5e95d5e2-1d32-4a95-815c-0caf11f3e382')
            column(name: 'df_usage_batch_uid', value: '04e0d0f3-caf1-409e-8312-52f6fc53979e')
            column(name: "df_scenario_uid", value: "24d11b82-bc2a-429a-92c4-809849d36e75")
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 250)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 80)
            column(name: 'net_amount', value: 170)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5e95d5e2-1d32-4a95-815c-0caf11f3e382')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }
    }
}
