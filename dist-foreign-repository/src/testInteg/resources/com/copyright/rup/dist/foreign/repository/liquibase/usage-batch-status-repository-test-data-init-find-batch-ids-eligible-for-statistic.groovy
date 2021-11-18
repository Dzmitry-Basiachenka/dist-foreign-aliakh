databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-02-15-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for findFasUsageBatchIdsEligibleForStatistic, findUsageBatchIdsEligibleForStatistic')

        // Associated with NTS pre-service fee fund
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'fc4dc9a9-c301-4b1a-9e39-169ae47556d8')
            column(name: 'name', value: 'FAS associated with pre-service fee fund batch')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3b7e7275-f9f6-48c3-b0e9-84e109a1f732')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'NTS pre service fee fund')
            column(name: 'comment', value: 'Test comment')
            column(name: 'total_amount', value: 150.01)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b6973c70-33bd-4cac-8b27-f746a791967c')
            column(name: 'df_usage_batch_uid', value: 'fc4dc9a9-c301-4b1a-9e39-169ae47556d8')
            column(name: 'work_title', value: 'Speculum')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 150.01)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b6973c70-33bd-4cac-8b27-f746a791967c')
            column(name: 'df_fund_pool_uid', value: '3b7e7275-f9f6-48c3-b0e9-84e109a1f732')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 150.01)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e810c237-84e9-48b4-ae61-1a2d6556502a')
            column(name: 'df_usage_batch_uid', value: 'fc4dc9a9-c301-4b1a-9e39-169ae47556d8')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e810c237-84e9-48b4-ae61-1a2d6556502a')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100.00)
        }

        // Associated with IN_PROGRESS scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '38b6ae7e-20e4-46cf-a684-8d3c7c67a940')
            column(name: 'name', value: 'FAS associated with IN_PROGRESS scenario batch')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e350b2c2-1102-435b-a8b8-e80516b7d792')
            column(name: 'name', value: 'FAS In progress scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c0ae8a59-17cb-4fea-821d-9429e06b751e')
            column(name: 'df_usage_batch_uid', value: '38b6ae7e-20e4-46cf-a684-8d3c7c67a940')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c0ae8a59-17cb-4fea-821d-9429e06b751e')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3c0c5ae2-0d6a-4f7d-b530-5f89f58ac796')
            column(name: 'df_usage_batch_uid', value: '38b6ae7e-20e4-46cf-a684-8d3c7c67a940')
            column(name: 'df_scenario_uid', value: 'e350b2c2-1102-435b-a8b8-e80516b7d792')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3c0c5ae2-0d6a-4f7d-b530-5f89f58ac796')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100.00)
        }

        // Associated with SENT_TO_LM scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0e5af78f-5b2e-469c-9bdc-2e5e396f1436')
            column(name: 'name', value: 'FAS associated with SENT_TO_LM scenario batch')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a90436a0-1998-4959-8de5-ebdf1ed1321c')
            column(name: 'df_usage_batch_uid', value: '0e5af78f-5b2e-469c-9bdc-2e5e396f1436')
            column(name: 'wr_wrk_inst', value: 251235125)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1228902112377655XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a90436a0-1998-4959-8de5-ebdf1ed1321c')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '032640d3-44f4-4dd0-8bcd-168f55c6a59b')
            column(name: 'name', value: 'FAS Sent to LM Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '4ebb6197-bb64-4405-bace-00116332d583')
            column(name: 'df_usage_batch_uid', value: '0e5af78f-5b2e-469c-9bdc-2e5e396f1436')
            column(name: 'df_scenario_uid', value: '032640d3-44f4-4dd0-8bcd-168f55c6a59b')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4ebb6197-bb64-4405-bace-00116332d583')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '21aa1fab-cef7-4d22-be72-1336be7ff9fc')
            column(name: 'name', value: 'NTS associated with scenario batch')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2021-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'a74f7b4a-cb79-4f73-8513-cc16c4887d0e')
            column(name: 'name', value: 'NTS scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":300.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '738e43f5-46b6-44e9-aee9-a2cde425f279')
            column(name: 'df_scenario_uid', value: 'a74f7b4a-cb79-4f73-8513-cc16c4887d0e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '738e43f5-46b6-44e9-aee9-a2cde425f279')
            column(name: 'df_usage_batch_uid', value: '21aa1fab-cef7-4d22-be72-1336be7ff9fc')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6090f212-2396-43d5-b975-2092f4b14aa4')
            column(name: 'df_usage_batch_uid', value: '21aa1fab-cef7-4d22-be72-1336be7ff9fc')
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6090f212-2396-43d5-b975-2092f4b14aa4')
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
            column(name: 'df_usage_uid', value: '76cdf597-e8fc-49e7-8067-11b029858114')
            column(name: 'df_usage_batch_uid', value: '21aa1fab-cef7-4d22-be72-1336be7ff9fc')
            column(name: 'df_scenario_uid', value: 'a74f7b4a-cb79-4f73-8513-cc16c4887d0e')
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009999)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '76cdf597-e8fc-49e7-8067-11b029858114')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '515a78e7-2a92-4b15-859a-fd9f70e80982')
            column(name: 'name', value: 'FAS completed batch')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'gross_amount', value: 700.00)
            column(name: 'initial_usages_count', value: 8)
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '12faaaa6-1e12-4306-8aab-1da694f97cf6')
            column(name: 'df_usage_batch_uid', value: '515a78e7-2a92-4b15-859a-fd9f70e80982')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '12faaaa6-1e12-4306-8aab-1da694f97cf6')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '359de82f-374b-4d53-88ab-0be3982b22aa')
            column(name: 'name', value: 'NTS completed batch')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2021-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'initial_usages_count', value: 5)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5f491fe4-2f1a-434f-b536-92350c623dab')
            column(name: 'df_usage_batch_uid', value: '359de82f-374b-4d53-88ab-0be3982b22aa')
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5f491fe4-2f1a-434f-b536-92350c623dab')
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
            column(name: 'df_usage_uid', value: '9522a3cc-5526-48e5-b4f0-182a6c8ccdb1')
            column(name: 'df_usage_batch_uid', value: '359de82f-374b-4d53-88ab-0be3982b22aa')
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9522a3cc-5526-48e5-b4f0-182a6c8ccdb1')
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
            column(name: 'df_usage_uid', value: 'aee126ca-f143-44c4-9e65-3ca80ba59875')
            column(name: 'df_usage_batch_uid', value: '359de82f-374b-4d53-88ab-0be3982b22aa')
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'UNCLASSIFIED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'aee126ca-f143-44c4-9e65-3ca80ba59875')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 900)
        }

        rollback {
            dbRollback
        }
    }
}
