databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-03-27-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testWriteUndistributedLiabilitiesCsvReport, testWriteUndistributedLiabilitiesCsvEmptyReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '53089c29-7df1-4d41-93d3-4ad222408818')
            column(name: 'rh_account_number', value: 7000581909)
            column(name: 'name', value: 'CLASS, The Copyright Licensing and Administration Society of Singapore Ltd')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '066d2add-cff0-4007-83df-4b77c3e4b00e')
            column(name: 'rh_account_number', value: 7001226021)
            column(name: 'name', value: 'Zynga')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '77b111d3-9eea-49af-b815-100b9716c1b3')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'name', value: 'CLA, The Copyright Licensing Agency Ltd.')
        }

        // Record for this batch should't be included into report.
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a1cdd210-b256-44f6-bb18-96fbe6bf40c9')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 10')
            column(name: 'rro_account_number', value: 7001226021)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2010-01-01')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9f15966c-b368-4449-bcb1-ff4945e2e033')
            column(name: 'df_usage_batch_uid', value: 'a1cdd210-b256-44f6-bb18-96fbe6bf40c9')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 7001226021)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 249.9999999988)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9f15966c-b368-4449-bcb1-ff4945e2e033')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        // Record in Undistributed Liabilities Reconciliation Report that includes amounts for batch with usages that were sent to lm
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b654')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 1')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-01')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f5e')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report scenario for batch 1')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82824546-c34d-4102-9939-62c50f356f5e')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b654')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f5e')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.0000000010)
            column(name: 'net_amount', value: 34.0000000007)
            column(name: 'service_fee_amount', value: 16.0000000003)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'standard_number_type', value: 'VALISSN')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82824546-c34d-4102-9939-62c50f356f5e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '035c57a2-375f-421e-b92f-0b62a3d7fdda')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b654')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f5e')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.0000000010)
            column(name: 'net_amount', value: 34.0000000007)
            column(name: 'service_fee_amount', value: 16.0000000003)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'standard_number_type', value: 'VALISSN')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '035c57a2-375f-421e-b92f-0b62a3d7fdda')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        /* Record in report that includes amounts for following batch:
               batch with fas usages that were sent to lm
               batch with fas2 usages that were sent to lm
               batch with fas2 usages that were sent to lm and return to cla
               two batches with fas2 eligible usages */
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'acae006c-a4fe-45f0-a0cc-098e12db00c5')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 2')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2010-01-01')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '9fb85b60-8881-468b-8b53-a78bb7ff81d8')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report scenario for batch 2')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'fcecfba8-ca12-4543-83df-efdca1d0d504')
            column(name: 'df_usage_batch_uid', value: 'acae006c-a4fe-45f0-a0cc-098e12db00c5')
            column(name: 'df_scenario_uid', value: '9fb85b60-8881-468b-8b53-a78bb7ff81d8')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 249.9999999988)
            column(name: 'net_amount', value: 169.9999999992)
            column(name: 'service_fee_amount', value: 79.9999999996)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'standard_number_type', value: 'VALISSN')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'fcecfba8-ca12-4543-83df-efdca1d0d504')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '580487a7-aa96-47c8-a78e-1ac077fd61ae')
            column(name: 'df_usage_batch_uid', value: 'acae006c-a4fe-45f0-a0cc-098e12db00c5')
            column(name: 'df_scenario_uid', value: '9fb85b60-8881-468b-8b53-a78bb7ff81d8')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 249.9999999988)
            column(name: 'net_amount', value: 169.9999999992)
            column(name: 'service_fee_amount', value: 79.9999999996)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'standard_number_type', value: 'VALISSN')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '580487a7-aa96-47c8-a78e-1ac077fd61ae')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '8869cca8-b5a6-4122-ba56-fbc7d0585f65')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 3')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2010-01-01')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 380.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '7887b131-e38d-452d-806b-f9615a2401ce')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report scenario for batch 3')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'd04b4d45-b143-404c-bf45-0984542cc25a')
            column(name: 'df_usage_batch_uid', value: '8869cca8-b5a6-4122-ba56-fbc7d0585f65')
            column(name: 'df_scenario_uid', value: '7887b131-e38d-452d-806b-f9615a2401ce')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'payee_account_number', value: 2000017000)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 190.0000000007)
            column(name: 'net_amount', value: 171.0000000006)
            column(name: 'service_fee_amount', value: 19.0000000001)
            column(name: 'service_fee', value: 0.10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd04b4d45-b143-404c-bf45-0984542cc25a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'af8e8b6c-29e2-4b7a-aaa1-746c48ef2253')
            column(name: 'df_usage_batch_uid', value: '8869cca8-b5a6-4122-ba56-fbc7d0585f65')
            column(name: 'df_scenario_uid', value: '7887b131-e38d-452d-806b-f9615a2401ce')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'payee_account_number', value: 2000017000)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 190.0000000007)
            column(name: 'net_amount', value: 171.0000000006)
            column(name: 'service_fee_amount', value: 19.0000000001)
            column(name: 'service_fee', value: 0.10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'af8e8b6c-29e2-4b7a-aaa1-746c48ef2253')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '11d8d04f-4c20-4bd1-8b81-f80c2742e89e')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 4')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2010-01-01')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea82')
            column(name: 'df_usage_batch_uid', value: '11d8d04f-4c20-4bd1-8b81-f80c2742e89e')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 249.9999999988)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea82')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a63d87498-3c12-429a-87ef-241951638b8e')
            column(name: 'df_usage_batch_uid', value: '11d8d04f-4c20-4bd1-8b81-f80c2742e89e')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 249.9999999988)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a63d87498-3c12-429a-87ef-241951638b8e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e116e5e0-9080-4abf-9e67-86959f2cae52')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 5')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2010-01-01')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'initial_usages_count', value: 4)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd376bdd6-d87f-4d39-bf8f-05c41404d19b')
            column(name: 'df_usage_batch_uid', value: 'e116e5e0-9080-4abf-9e67-86959f2cae52')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 500.0000000007)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd376bdd6-d87f-4d39-bf8f-05c41404d19b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '038c46ae-7931-48c6-8cb9-ff4fab9faa95')
            column(name: 'df_usage_batch_uid', value: 'e116e5e0-9080-4abf-9e67-86959f2cae52')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 500.0000000007)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '038c46ae-7931-48c6-8cb9-ff4fab9faa95')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3b7aed62-eb9f-4244-9248-2f9ba329e3be')
            column(name: 'df_usage_batch_uid', value: 'e116e5e0-9080-4abf-9e67-86959f2cae52')
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622Xxxx')
            column(name: 'gross_amount', value: 155.0000000007)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3b7aed62-eb9f-4244-9248-2f9ba329e3be')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        // Undistributed Liabilities reconciliation report TO_BE_DISTRIBUTED status
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '49060c9b-9cc2-4b93-b701-fffc82eb28b0')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 155.00)
            column(name: 'updated_datetime', value: '2019-03-26 16:35:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b92f4f46-38c1-4f6c-9f69-2c1dcd73d579')
            column(name: 'df_usage_batch_uid', value: 'e116e5e0-9080-4abf-9e67-86959f2cae52')
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112317622Xxxx')
            column(name: 'gross_amount', value: 155.0000000007)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b92f4f46-38c1-4f6c-9f69-2c1dcd73d579')
            column(name: 'df_fund_pool_uid', value: '49060c9b-9cc2-4b93-b701-fffc82eb28b0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        // Record in report that includes amounts for batch with fas and nts usages in eligible status
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '502cf0b4-3e28-4712-837a-3728ef57b100')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 6')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-02')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 60.00)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b05046a6-9baf-4be3-baa6-5c08c8527826')
            column(name: 'df_usage_batch_uid', value: '502cf0b4-3e28-4712-837a-3728ef57b100')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 20)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b05046a6-9baf-4be3-baa6-5c08c8527826')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5329943d-1214-405d-8e29-59a9281d3275')
            column(name: 'df_usage_batch_uid', value: '502cf0b4-3e28-4712-837a-3728ef57b100')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 20)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5329943d-1214-405d-8e29-59a9281d3275')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4dd8cdf8-ca10-422e-bdd5-3220105e6379')
            column(name: 'df_usage_batch_uid', value: '502cf0b4-3e28-4712-837a-3728ef57b100')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'gross_amount', value: 20)
            column(name: 'created_by_user', value: 'user@copyright.com')
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

        // Record in report that includes amounts for batch with usages in different statuses
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'name', value: 'Batch with usages in different statuses')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-03')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 800.00)
            column(name: 'initial_usages_count', value: 4)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9e8de601-d771-42eb-992f-75ecd083b8d0')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9e8de601-d771-42eb-992f-75ecd083b8d0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'standard_number', value: '2192-3566')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '721ca627-09bc-4204-99f4-6acae415fa5d')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '721ca627-09bc-4204-99f4-6acae415fa5d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd9ca07b5-8282-4a81-9b9d-e4480f529d34')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'wr_wrk_inst', value: 103658926)
            column(name: 'work_title', value: 'Nitrates')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd9ca07b5-8282-4a81-9b9d-e4480f529d34')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        // Record in report that includes amounts for batch with usages that were sent to lm and batch with paid usages
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '8bf56f3a-b868-4795-8691-d7f56f3f8540')
            column(name: 'name', value: 'Batch with sent to lm usage')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-04')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 50.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '145ea869-16aa-4953-a780-1d58abda692f')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report scenario for batch 1')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '14ed2c94-9316-49d9-ad74-02e5627eba8d')
            column(name: 'df_usage_batch_uid', value: '8bf56f3a-b868-4795-8691-d7f56f3f8540')
            column(name: 'df_scenario_uid', value: '145ea869-16aa-4953-a780-1d58abda692f')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 34.00)
            column(name: 'service_fee_amount', value: 16.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '14ed2c94-9316-49d9-ad74-02e5627eba8d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e7cfcf79-3d8c-478d-a7a4-2242867661f2')
            column(name: 'name', value: 'Batch with paid usage')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-04')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 50.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '74be71d1-b1dd-45ac-95b6-78677b37beec')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report scenario for batch 1')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '74be71d1-b1dd-45ac-95b6-78677b37beec')
            column(name: 'df_usage_batch_uid', value: 'e7cfcf79-3d8c-478d-a7a4-2242867661f2')
            column(name: 'df_scenario_uid', value: '74be71d1-b1dd-45ac-95b6-78677b37beec')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 34.00)
            column(name: 'service_fee_amount', value: 16.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2017-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '74be71d1-b1dd-45ac-95b6-78677b37beec')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        // Record in reports that includes amounts for batch with two sent to lm usages and two not included into scenario usages
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c38b1946-356b-4934-a765-eb8e824d1f01')
            column(name: 'name', value: 'Batch with two sent to lm usages and two not included into scenario usages')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-04-21')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 400.00)
            column(name: 'initial_usages_count', value: 4)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '454b5ed8-bff1-4410-b455-235ec5271e6c')
            column(name: 'df_usage_batch_uid', value: 'c38b1946-356b-4934-a765-eb8e824d1f01')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '454b5ed8-bff1-4410-b455-235ec5271e6c')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '910dae16-f495-48aa-932d-0909d8103b00')
            column(name: 'df_usage_batch_uid', value: 'c38b1946-356b-4934-a765-eb8e824d1f01')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '910dae16-f495-48aa-932d-0909d8103b00')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '66460664-ccaf-4a11-935e-0d7b9da4e41b')
            column(name: 'name', value: 'Scenario with two sent to lm usages')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '0ac629ea-9e8b-45bd-bbd8-857bd7d34424')
            column(name: 'df_usage_batch_uid', value: 'c38b1946-356b-4934-a765-eb8e824d1f01')
            column(name: 'df_scenario_uid', value: '66460664-ccaf-4a11-935e-0d7b9da4e41b')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '0ac629ea-9e8b-45bd-bbd8-857bd7d34424')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '146ce0f2-f71f-4a53-b0c4-9925ec08da5f')
            column(name: 'df_usage_batch_uid', value: 'c38b1946-356b-4934-a765-eb8e824d1f01')
            column(name: 'df_scenario_uid', value: '66460664-ccaf-4a11-935e-0d7b9da4e41b')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '146ce0f2-f71f-4a53-b0c4-9925ec08da5f')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        // Record in report that includes amounts for batch with rro which is not included into table with estimated service fee
        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'adf97d33-4b76-4c2c-ad6e-91c4715d392f')
            column(name: 'rh_account_number', value: 5000581900)
            column(name: 'name', value: 'The Copyright Company')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f6a46748-5c19-4b10-953b-ae61d8e06822')
            column(name: 'name', value: 'Batch with rro which is not included into table with estimated service fee')
            column(name: 'rro_account_number', value: 5000581900)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-03')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5f95fedc-1c63-4cda-b874-2a39e0ac39fe')
            column(name: 'df_usage_batch_uid', value: 'f6a46748-5c19-4b10-953b-ae61d8e06822')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5f95fedc-1c63-4cda-b874-2a39e0ac39fe')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '6c170115-cd1f-4a73-9a56-6e9316579bc8')
            column(name: 'name', value: 'In progress scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'd1f1def6-4062-47f6-8675-59328fafb157')
            column(name: 'df_usage_batch_uid', value: 'f6a46748-5c19-4b10-953b-ae61d8e06822')
            column(name: 'df_scenario_uid', value: '6c170115-cd1f-4a73-9a56-6e9316579bc8')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100)
            column(name: 'net_amount', value: 64.00)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd1f1def6-4062-47f6-8675-59328fafb157')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        // Record for this batch should't be included into report. Batch contains paid and archived usages
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '24600e42-3c2c-4cf6-b376-d2d477128e04')
            column(name: 'name', value: 'Batch with paid and archived usages')
            column(name: 'rro_account_number', value: 5000581900)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-03')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'd3436a38-98ff-4061-b245-68ffeb1f216c')
            column(name: 'name', value: 'Scenario with paid and archived usages')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'e8790b32-9d4e-42e6-93f6-eeafc46131fc')
            column(name: 'df_usage_batch_uid', value: '24600e42-3c2c-4cf6-b376-d2d477128e04')
            column(name: 'df_scenario_uid', value: 'd3436a38-98ff-4061-b245-68ffeb1f216c')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2017-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e8790b32-9d4e-42e6-93f6-eeafc46131fc')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '9fe2be4a-d4fc-4d73-aa55-56da80feeff2')
            column(name: 'df_usage_batch_uid', value: '24600e42-3c2c-4cf6-b376-d2d477128e04')
            column(name: 'df_scenario_uid', value: 'd3436a38-98ff-4061-b245-68ffeb1f216c')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2017-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9fe2be4a-d4fc-4d73-aa55-56da80feeff2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        rollback {
            dbRollback
        }
    }
}
