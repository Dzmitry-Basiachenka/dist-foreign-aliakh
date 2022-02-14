databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-03-27-02', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testWriteServiceFeeTrueUpCsvReport, testWriteServiceFeeTrueUpCsvEmptyReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '4cc22676-bb0b-4f06-be23-e245d474b01d')
            column(name: 'rh_account_number', value: 5000581901)
            column(name: 'name', value: 'Unknown RRO')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '77b111d3-9eea-49af-b815-100b9716c1b3')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'name', value: 'CLA, The Copyright Licensing Agency Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '53089c29-7df1-4d41-93d3-4ad222408818')
            column(name: 'rh_account_number', value: 7000581909)
            column(name: 'name', value: 'CLASS, The Copyright Licensing and Administration Society of Singapore Ltd')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '40a02427-1204-47ae-849c-1299a337cc47')
            column(name: 'name', value: 'AT_service-fee-true-up-report-1_BATCH')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-01')
            column(name: 'fiscal_year', value: 2013)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'name', value: 'AT_service-fee-true-up-report-1_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'df_usage_batch_uid', value: '40a02427-1204-47ae-849c-1299a337cc47')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
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
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '7f1cb24d-cf00-4354-9ef9-0457d36a556e')
            column(name: 'df_usage_batch_uid', value: '40a02427-1204-47ae-849c-1299a337cc47')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
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
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7f1cb24d-cf00-4354-9ef9-0457d36a556e')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a2fd8112-4623-4abf-aa6b-3fe820c49eb2')
            column(name: 'name', value: 'AT_service-fee-true-up-report-2_BATCH')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2013-01-01')
            column(name: 'fiscal_year', value: 2013)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'name', value: 'AT_service-fee-true-up-report-2_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '27845f34-ab87-4147-aea0-a191ded7412a')
            column(name: 'df_usage_batch_uid', value: 'a2fd8112-4623-4abf-aa6b-3fe820c49eb2')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
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
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '27845f34-ab87-4147-aea0-a191ded7412a')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '653c2173-5385-4a24-b42c-3428b256d74a')
            column(name: 'df_usage_batch_uid', value: 'a2fd8112-4623-4abf-aa6b-3fe820c49eb2')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
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
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '653c2173-5385-4a24-b42c-3428b256d74a')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '7675a48a-cba6-4a2e-a4a4-fee2237a0128')
            column(name: 'name', value: 'AT_service-fee-true-up-report-3_BATCH')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2013-01-01')
            column(name: 'fiscal_year', value: 2013)
            column(name: 'gross_amount', value: 380.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
            column(name: 'name', value: 'AT_service-fee-true-up-report-3_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '280b9e39-f934-4474-9d11-847e91e36609')
            column(name: 'df_usage_batch_uid', value: '7675a48a-cba6-4a2e-a4a4-fee2237a0128')
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
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
            column(name: 'df_usage_fas_uid', value: '280b9e39-f934-4474-9d11-847e91e36609')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '4179d85a-a09d-4521-8b5c-dc9026bd8249')
            column(name: 'df_usage_batch_uid', value: '7675a48a-cba6-4a2e-a4a4-fee2237a0128')
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
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
            column(name: 'df_usage_fas_uid', value: '4179d85a-a09d-4521-8b5c-dc9026bd8249')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '76cf371b-142d-4380-9e62-c09888d7a034')
            column(name: 'name', value: 'AT_service-fee-true-up-report-4_BATCH')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2013-01-01')
            column(name: 'fiscal_year', value: 2013)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b58e8f58-07fc-4597-9c96-551383cfa1b1')
            column(name: 'df_usage_batch_uid', value: '76cf371b-142d-4380-9e62-c09888d7a034')
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
            column(name: 'df_usage_fas_uid', value: 'b58e8f58-07fc-4597-9c96-551383cfa1b1')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ddbed016-f04d-4556-ab9f-b8080fb90089')
            column(name: 'df_usage_batch_uid', value: '76cf371b-142d-4380-9e62-c09888d7a034')
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
            column(name: 'df_usage_fas_uid', value: 'ddbed016-f04d-4556-ab9f-b8080fb90089')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '2b1b5fe1-fb0a-4243-8326-2cc9dcd4a73a')
            column(name: 'name', value: 'AT_service-fee-true-up-report-5_BATCH')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2013-01-01')
            column(name: 'fiscal_year', value: 2013)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c38f4992-9e58-4a97-8a63-ce15c9ad4cd1')
            column(name: 'df_usage_batch_uid', value: '2b1b5fe1-fb0a-4243-8326-2cc9dcd4a73a')
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
            column(name: 'df_usage_fas_uid', value: 'c38f4992-9e58-4a97-8a63-ce15c9ad4cd1')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '30121fa1-4550-4fe8-9776-2d17f16a54a1')
            column(name: 'df_usage_batch_uid', value: '2b1b5fe1-fb0a-4243-8326-2cc9dcd4a73a')
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
            column(name: 'df_usage_fas_uid', value: '30121fa1-4550-4fe8-9776-2d17f16a54a1')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6b75221e-432e-4349-ba05-796d1fd5900e')
            column(name: 'name', value: 'AT_service-fee-true-up-report-6_BATCH')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-02')
            column(name: 'fiscal_year', value: 2013)
            column(name: 'gross_amount', value: 60.00)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c66b15fa-ce23-4d15-89d0-fbae38e360b6')
            column(name: 'df_usage_batch_uid', value: '6b75221e-432e-4349-ba05-796d1fd5900e')
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
            column(name: 'df_usage_fas_uid', value: 'c66b15fa-ce23-4d15-89d0-fbae38e360b6')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3def87c0-6759-4a16-bcd9-945b14c00219c')
            column(name: 'df_usage_batch_uid', value: '6b75221e-432e-4349-ba05-796d1fd5900e')
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
            column(name: 'df_usage_fas_uid', value: '3def87c0-6759-4a16-bcd9-945b14c00219c')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '022184ed-2adb-4237-8a5f-34eac350bcbc')
            column(name: 'df_usage_batch_uid', value: '6b75221e-432e-4349-ba05-796d1fd5900e')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'gross_amount', value: 20)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '022184ed-2adb-4237-8a5f-34eac350bcbc')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '260d71f8-ccfa-46fa-9319-55a00726a266')
            column(name: 'name', value: 'AT_service-fee-true-up-report-7_BATCH')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-03')
            column(name: 'fiscal_year', value: 2013)
            column(name: 'gross_amount', value: 800.00)
            column(name: 'initial_usages_count', value: 4)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f6849fd0-c094-4a72-979e-00cf462fb3eb')
            column(name: 'df_usage_batch_uid', value: '260d71f8-ccfa-46fa-9319-55a00726a266')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f6849fd0-c094-4a72-979e-00cf462fb3eb')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'eed8d6f8-7ac6-4ae7-9bdc-ba33a58a5bad')
            column(name: 'df_usage_batch_uid', value: '260d71f8-ccfa-46fa-9319-55a00726a266')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'standard_number', value: '2192-3566')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'eed8d6f8-7ac6-4ae7-9bdc-ba33a58a5bad')
            column(name: 'article', value: 'DIN EN 779:2013')
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
            column(name: 'df_usage_uid', value: '15b144ba-58d6-4adb-9dc1-602eb09052ce')
            column(name: 'df_usage_batch_uid', value: '260d71f8-ccfa-46fa-9319-55a00726a266')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '15b144ba-58d6-4adb-9dc1-602eb09052ce')
            column(name: 'article', value: 'DIN EN 779:2013')
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
            column(name: 'df_usage_uid', value: '5420dea5-4acc-4d87-b264-a67ab17a93ae')
            column(name: 'df_usage_batch_uid', value: '260d71f8-ccfa-46fa-9319-55a00726a266')
            column(name: 'wr_wrk_inst', value: 103658926)
            column(name: 'work_title', value: 'Nitrates')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5420dea5-4acc-4d87-b264-a67ab17a93ae')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'bb6966f5-06e6-4224-8403-422e008fba3e')
            column(name: 'name', value: 'AT_service-fee-true-up-report-8_BATCH')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-04')
            column(name: 'fiscal_year', value: 2013)
            column(name: 'gross_amount', value: 50.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
            column(name: 'name', value: 'AT_service-fee-true-up-report-4_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'a24c3287-7f34-4be0-b9b8-fb363979feba')
            column(name: 'df_usage_batch_uid', value: 'bb6966f5-06e6-4224-8403-422e008fba3e')
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
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
            column(name: 'df_usage_fas_uid', value: 'a24c3287-7f34-4be0-b9b8-fb363979feba')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a3546208-42a2-4a80-b58a-055ea2aadbdf')
            column(name: 'name', value: 'AT_service-fee-true-up-report-9_BATCH')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-04')
            column(name: 'fiscal_year', value: 2013)
            column(name: 'gross_amount', value: 50.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'name', value: 'AT_service-fee-true-up-report-5_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'df_usage_batch_uid', value: 'a3546208-42a2-4a80-b58a-055ea2aadbdf')
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
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
            column(name: 'check_date', value: '2011-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 11')
            column(name: 'distribution_date', value: '2011-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2011-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '1a4a4c13-bfa8-4220-92b1-6ea5936ae28b')
            column(name: 'name', value: 'AT_service-fee-true-up-report-10_BATCH')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-04-21')
            column(name: 'fiscal_year', value: 2013)
            column(name: 'gross_amount', value: 400.00)
            column(name: 'initial_usages_count', value: 4)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6505f666-2534-4bb2-a72c-ae2e2dfa07e8')
            column(name: 'df_usage_batch_uid', value: '1a4a4c13-bfa8-4220-92b1-6ea5936ae28b')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6505f666-2534-4bb2-a72c-ae2e2dfa07e8')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd55a6040-ffba-4bcd-8569-cb09729f66fd')
            column(name: 'df_usage_batch_uid', value: '1a4a4c13-bfa8-4220-92b1-6ea5936ae28b')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd55a6040-ffba-4bcd-8569-cb09729f66fd')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'name', value: 'AT_service-fee-true-up-report-6_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '337c71b0-c665-46c7-945a-cf69e270dadf')
            column(name: 'df_usage_batch_uid', value: '1a4a4c13-bfa8-4220-92b1-6ea5936ae28b')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
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
            column(name: 'df_usage_fas_uid', value: '337c71b0-c665-46c7-945a-cf69e270dadf')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '247030fa-86f0-4add-ad84-b0b6c8307f14d')
            column(name: 'df_usage_batch_uid', value: '1a4a4c13-bfa8-4220-92b1-6ea5936ae28b')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
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
            column(name: 'df_usage_fas_uid', value: '247030fa-86f0-4add-ad84-b0b6c8307f14d')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3ecd88a9-2d45-4901-b341-c09cced9c6ce')
            column(name: 'name', value: 'AT_service-fee-true-up-report-11_BATCH')
            column(name: 'rro_account_number', value: 5000581901)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-03')
            column(name: 'fiscal_year', value: 2013)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bd04b243-ce8e-42c8-8cd5-7a1ba871d13a')
            column(name: 'df_usage_batch_uid', value: '3ecd88a9-2d45-4901-b341-c09cced9c6ce')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bd04b243-ce8e-42c8-8cd5-7a1ba871d13a')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '29e140ab-2a71-40a3-a55a-a68dcdb95a9b')
            column(name: 'name', value: 'AT_service-fee-true-up-report-12_BATCH')
            column(name: 'rro_account_number', value: 5000581901)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-03')
            column(name: 'fiscal_year', value: 2013)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'name', value: 'AT_service-fee-true-up-report-7_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '5ad8fe7f-73ea-4d7a-b27e-5b3bc73cf1dd')
            column(name: 'df_usage_batch_uid', value: '29e140ab-2a71-40a3-a55a-a68dcdb95a9b')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
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
            column(name: 'df_usage_fas_uid', value: '5ad8fe7f-73ea-4d7a-b27e-5b3bc73cf1dd')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'bde1e665-b10f-4015-936f-71fb42410e3b')
            column(name: 'df_usage_batch_uid', value: '29e140ab-2a71-40a3-a55a-a68dcdb95a9b')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
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
            column(name: 'check_date', value: '2011-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 11')
            column(name: 'distribution_date', value: '2011-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2011-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bde1e665-b10f-4015-936f-71fb42410e3b')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'a87797e1-613a-4a52-8367-f1a33283f074')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-01-01 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '3aa29712-cd46-4a37-80ae-912c3e9df1c8')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '915fac96-4979-43f4-91dd-3231ceb713fd')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '79f43b59-388f-4d04-8e6a-e78d5edb758f')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a76')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a77')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a78')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a79')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'e7fb6d7d-a943-49eb-a08b-8ca498ec4128')
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '5dd612c2-bb61-463d-b997-a0bd65b352aa')
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '73ae3f48-a254-4360-a717-26fd3c4a6d78')
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '62d28288-0e58-4847-ba2d-695c763e814b')
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '9d365e9b-8083-4fcb-b939-a60c9c4c4fc6')
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '26da4b77-1c4f-4600-b87b-aba2e01ee495')
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '6b1d2ccc-9949-4adb-9bbf-3d6aa144c837')
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '07bd1576-cf64-433b-a07f-7dd897de67b1')
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a06')
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '6a7e9a50-d57a-4eaa-b6f5-2aa0275adfd8')
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'afbaebbf-900a-4fe0-ba2b-823faf238b6b')
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '8900c492-19c7-44ac-bde1-23df42960926')
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'd17cc542-426b-4c06-b9b2-3438860f12d2')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '623b891c-e368-4244-997e-d744d087c633')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '481952de-609c-4c44-a139-1a8e3f58c0f4')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'd945dfb2-0938-4b36-9671-d289b3e36820')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'b8676230-f71f-4056-858b-5fea956c98b3')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a27')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a28')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a29')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'f3cbcf41-b1f9-4857-8f9d-17a1de9f5811')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'f3cbcf41-b1f9-4857-8f9d-17a1de9f5811')
            column(name: 'df_usage_batch_uid', value: '40a02427-1204-47ae-849c-1299a337cc47')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '02d0823d-fc8f-4939-9339-3aaa31dc4883')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '02d0823d-fc8f-4939-9339-3aaa31dc4883')
            column(name: 'df_usage_batch_uid', value: 'a2fd8112-4623-4abf-aa6b-3fe820c49eb2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '04d95dd5-8018-4eea-b297-1d334a694e29')
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '04d95dd5-8018-4eea-b297-1d334a694e29')
            column(name: 'df_usage_batch_uid', value: 'bb6966f5-06e6-4224-8403-422e008fba3e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '4797d20e-65e0-4dd0-a2c0-3d9e8bf3b0b2')
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '4797d20e-65e0-4dd0-a2c0-3d9e8bf3b0b2')
            column(name: 'df_usage_batch_uid', value: 'a3546208-42a2-4a80-b58a-055ea2aadbdf')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '1cf842fa-2454-4256-aeed-da46075784d5')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '1cf842fa-2454-4256-aeed-da46075784d5')
            column(name: 'df_usage_batch_uid', value: '1a4a4c13-bfa8-4220-92b1-6ea5936ae28b')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'de9c64ef-1fe3-4331-810d-bb535d9076a7')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'de9c64ef-1fe3-4331-810d-bb535d9076a7')
            column(name: 'df_usage_batch_uid', value: '29e140ab-2a71-40a3-a55a-a68dcdb95a9b')
        }

        rollback {
            dbRollback
        }
    }
}
