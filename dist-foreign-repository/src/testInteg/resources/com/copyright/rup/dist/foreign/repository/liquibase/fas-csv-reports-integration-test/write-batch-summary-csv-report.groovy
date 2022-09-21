databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-03-27-01', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testWriteBatchSummaryCsvReport, testWriteSummaryMarketCsvReport, ' +
                'testWriteWorkClassificationCsvReportWithEmptySearch')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '77b111d3-9eea-49af-b815-100b9716c1b3')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'name', value: 'CLA, The Copyright Licensing Agency Ltd.')
        }

        // Batch summary report
        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '4502b24f-3146-4682-a654-7f1f6647cb7f')
            column(name: 'rh_account_number', value: 2000017011)
            column(name: 'name', value: 'DALRO, Dramatic, Artistic & Literary Rights Organisation')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '53089c29-7df1-4d41-93d3-4ad222408818')
            column(name: 'rh_account_number', value: 7000581909)
            column(name: 'name', value: 'CLASS, The Copyright Licensing and Administration Society of Singapore Ltd')
        }

        // Batch with two details associated with in progress scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b651')
            column(name: 'name', value: 'FAS Batch Summary 1')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible, two nts, two details associated with scenario and two return to cla details that were sent to lm
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'name', value: 'FAS Batch Summary 2')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 650.00)
            column(name: 'initial_usages_count', value: 10)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible, two nts, two details associated with scenario and two details that were sent to lm
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'name', value: 'FAS Batch Summary 3')
            column(name: 'rro_account_number', value: 2000017011)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 550.00)
            column(name: 'initial_usages_count', value: 10)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with nts details. Shouldn't be included into report
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b611')
            column(name: 'name', value: 'FAS Batch Summary 4')
            column(name: 'rro_account_number', value: 2000017011)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-05-08')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with NTS product family to be excluded from report
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e016d9c2-6460-51bf-937c-9598cf00b652')
            column(name: 'name', value: 'NTS batch to be excluded from FAS/FAS2 Batch Summary Report')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"markets": ["Bus,Univ,Doc Del"], "stm_amount": 10, "non_stm_amount": 20, "stm_minimum_amount": 30, "non_stm_minimum_amount": 40, "fund_pool_period_to": 2018, "fund_pool_period_from": 2018}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe1')
            column(name: 'name', value: 'FAS Scenario Summary 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe6')
            column(name: 'name', value: 'FAS Scenario Summary 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe7')
            column(name: 'name', value: 'FAS Scenario Summary 3')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe8')
            column(name: 'name', value: 'FAS Scenario Summary 4')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe9')
            column(name: 'name', value: 'FAS Scenario Summary 5')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '92824546-d34d-7102-9939-92c50f356fe2')
            column(name: 'name', value: 'FAS Scenario Summary 6')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea01')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b651')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe1')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 42.00)
            column(name: 'service_fee_amount', value: 8.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea01')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea02')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b651')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe1')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 42.00)
            column(name: 'service_fee_amount', value: 8.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea02')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea43')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea43')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea44')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea44')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea45')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea45')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea46')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea46')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea47')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea47')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea50')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea50')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea51')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe6')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 150.00)
            column(name: 'net_amount', value: 102.00)
            column(name: 'service_fee_amount', value: 48.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea51')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea52')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe6')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 150.00)
            column(name: 'net_amount', value: 102.00)
            column(name: 'service_fee_amount', value: 48.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea52')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea53')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe7')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'payee_account_number', value: 2000017000)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 42.00)
            column(name: 'service_fee_amount', value: 8.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea53')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea54')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe7')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'payee_account_number', value: 2000017000)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 42.00)
            column(name: 'service_fee_amount', value: 8.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea54')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea55')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea55')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea56')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea56')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea57')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea57')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea58')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea58')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea59')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea59')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea60')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea60')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea61')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe8')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 42.00)
            column(name: 'service_fee_amount', value: 8.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea61')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea62')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe8')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 42.00)
            column(name: 'service_fee_amount', value: 8.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea62')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea63')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe9')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 42.00)
            column(name: 'service_fee_amount', value: 8.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea63')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea64')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe9')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 42.00)
            column(name: 'service_fee_amount', value: 8.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea64')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea77')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b611')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea77')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea78')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b611')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea78')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '92db1184-740c-5745-9e4e-3e96e9d0ea02')
            column(name: 'df_usage_batch_uid', value: 'e016d9c2-6460-51bf-937c-9598cf00b652')
            column(name: 'df_scenario_uid', value: '92824546-d34d-7102-9939-92c50f356fe2')
            column(name: 'wr_wrk_inst', value: 123321123)
            column(name: 'work_title', value: 'The rites of passage')
            column(name: 'system_title', value: 'The rites of passage')
            column(name: 'rh_account_number', value: 7000581909)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '12345XX-177361')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '92db1184-740c-5745-9e4e-3e96e9d0ea02')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2018)
            column(name: 'market_period_to', value: 2018)
            column(name: 'reported_value', value: 51.50)
        }

        rollback {
            dbRollback
        }
    }
}
