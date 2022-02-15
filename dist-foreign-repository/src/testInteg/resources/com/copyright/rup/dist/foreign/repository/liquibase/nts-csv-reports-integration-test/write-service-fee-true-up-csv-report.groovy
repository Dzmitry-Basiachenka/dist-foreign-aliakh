databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-07-30-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testWriteServiceFeeTrueUpCsvReportWithFundPool, testWriteServiceFeeTrueUpCsvReportWithoutFundPool, ' +
                'testWriteServiceFeeTrueUpEmptyCsvReport')

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

        rollback {
            dbRollback
        }
    }
}
