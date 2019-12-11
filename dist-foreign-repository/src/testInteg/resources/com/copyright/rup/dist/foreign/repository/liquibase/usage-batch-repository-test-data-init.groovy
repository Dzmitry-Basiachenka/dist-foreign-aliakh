databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2017-11-30-00', author: 'Aliaksandra_Bayanouskaya <abayanouskaya@copyright.com>') {
        comment('Inserting test data for UsageBatchRepositoryIntegrationTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '35000')
            column(name: 'updated_datetime', value: '2017-02-14 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-94d3458a666a')
            column(name: 'name', value: 'AccessCopyright_11Dec16')
            column(name: 'rro_account_number', value: '2000017004')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-08-16')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'gross_amount', value: '35000')
            column(name: 'updated_datetime', value: '2017-02-21 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'name', value: 'JAACC_11Dec16')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: '2016')
            column(name: 'gross_amount', value: '35000')
            column(name: 'updated_datetime', value: '2017-02-11 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3f46981e-e85a-4786-9b60-ab009c4358e7')
            column(name: 'name', value: 'NEW_26_OCT_2017')
            column(name: 'rro_account_number', value: '7000813800')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-10-30')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '35000')
            column(name: 'updated_datetime', value: '2017-10-26 14:49:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1230b236-1239-4a60-9fab-123b84199123')
            column(name: 'name', value: 'Scenario name 4')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 4')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '57ae4986-7e66-480d-8c65-5d25f085dc38')
            column(name: 'df_scenario_uid', value: '1230b236-1239-4a60-9fab-123b84199123')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '57ae4986-7e66-480d-8c65-5d25f085dc38')
            column(name: 'df_usage_batch_uid', value: '3f46981e-e85a-4786-9b60-ab009c4358e7')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3ab5e80b-89c0-4d78-9675-54c7ab284450')
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle') // B-55836 remove
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '2500') // B-55836 remove
            column(name: 'gross_amount', value: '35000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3ab5e80b-89c0-4d78-9675-54c7ab284450')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '2500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8a06905f-37ae-4e1f-8550-245277f8165c')
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-94d3458a666a')
            column(name: 'wr_wrk_inst', value: '244614835')
            column(name: 'work_title', value: '15th International Conference on Environmental Degradation of Materials in Nuclear Power Systems Water Reactors')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'First-Week Protein and Energy Intakes Are Associated With 18-Month Developmental Outcomes in Extremely Low Birth Weight Infants') // B-55836 remove
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'publisher', value: 'John Wiley & Sons') // B-55836 remove
            column(name: 'publication_date', value: '2011-05-10') // B-55836 remove
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2015') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'author', value: 'Nanette M. Schwann') // B-55836 remove
            column(name: 'number_of_copies', value: '1600')
            column(name: 'reported_value', value: '1560') // B-55836 remove
            column(name: 'gross_amount', value: '35000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8a06905f-37ae-4e1f-8550-245277f8165c')
            column(name: 'article', value: 'First-Week Protein and Energy Intakes Are Associated With 18-Month Developmental Outcomes in Extremely Low Birth Weight Infants')
            column(name: 'publisher', value: 'John Wiley & Sons')
            column(name: 'publication_date', value: '2011-05-10')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: '1560')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5c5f8c1c-1418-4cfd-8685-9212f4c421d1')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'wr_wrk_inst', value: '345870577')
            column(name: 'work_title', value: '10 Years Plant Molecular Biology')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'publisher', value: 'McGraw-Hill') // B-55836 remove
            column(name: 'publication_date', value: '2009-12-31') // B-55836 remove
            column(name: 'market', value: 'Edu') // B-55836 remove
            column(name: 'market_period_from', value: '2015') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'author', value: 'Mirjam H. Hüberli') // B-55836 remove
            column(name: 'number_of_copies', value: '2630')
            column(name: 'reported_value', value: '1280.00') // B-55836 remove
            column(name: 'gross_amount', value: '2125.24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5c5f8c1c-1418-4cfd-8685-9212f4c421d1')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'McGraw-Hill')
            column(name: 'publication_date', value: '2009-12-31')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: 'Mirjam H. Hüberli')
            column(name: 'reported_value', value: '1280.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '23876f21-8ab2-4fcb-adf3-777be88eddbb')
            column(name: 'df_usage_batch_uid', value: '3f46981e-e85a-4786-9b60-ab009c4358e7')
            column(name: 'df_scenario_uid', value: '1230b236-1239-4a60-9fab-123b84199123')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'payee_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle') // B-55836 remove
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2014-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '2500') // B-55836 remove
            column(name: 'gross_amount', value: '35000.00')
            column(name: 'net_amount', value: '23800.00')
            column(name: 'service_fee_amount', value: '11200.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '23876f21-8ab2-4fcb-adf3-777be88eddbb')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2014-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '2500')
        }

        //testFindUsageBatchesForPreServiceFeeFunds
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '66282dbc-2468-48d4-b926-93d3458a656b')
            column(name: 'name', value: 'CADRA_12Dec17')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '35000')
            column(name: 'updated_datetime', value: '2017-01-14 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4ab5e80b-89c0-4d78-9675-54c7ab284451')
            column(name: 'df_usage_batch_uid', value: '66282dbc-2468-48d4-b926-93d3458a656b')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle') // B-55836 remove
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '2500') // B-55836 remove
            column(name: 'gross_amount', value: '35000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4ab5e80b-89c0-4d78-9675-54c7ab284451')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '2500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6c5f8c1c-1418-4cfd-8685-9212f4c421d2')
            column(name: 'df_usage_batch_uid', value: '66282dbc-2468-48d4-b926-93d3458a656b')
            column(name: 'wr_wrk_inst', value: '345870577')
            column(name: 'work_title', value: '10 Years Plant Molecular Biology')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'publisher', value: 'McGraw-Hill') // B-55836 remove
            column(name: 'publication_date', value: '2009-12-31') // B-55836 remove
            column(name: 'market', value: 'Edu') // B-55836 remove
            column(name: 'market_period_from', value: '2015') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'author', value: 'Mirjam H. Hüberli') // B-55836 remove
            column(name: 'number_of_copies', value: '2630')
            column(name: 'reported_value', value: '1280.00') // B-55836 remove
            column(name: 'gross_amount', value: '2125.24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6c5f8c1c-1418-4cfd-8685-9212f4c421d2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'McGraw-Hill')
            column(name: 'publication_date', value: '2009-12-31')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: 'Mirjam H. Hüberli')
            column(name: 'reported_value', value: '1280.00')
        }

        rollback ""
    }

    changeSet(id: '2019-04-24-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testFindBatchNamesWithoutRhsForClassification')

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '2790b818-8a85-464d-8fec-26c8163a7173')
            column(name: 'wr_wrk_inst', value: '122266795')
            column(name: 'classification', value: 'NON-STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '7c028d85-58c3-45f8-be2d-33c16b0905b0')
            column(name: 'name', value: 'NTS Batch without STM usages')
            column(name: 'rro_account_number', value: '123456789')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 0, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7, "excluding_stm": true}')
            column(name: 'updated_datetime', value: '2019-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'fe112c31-e069-455e-a64e-e98a8115e4b4')
            column(name: 'df_usage_batch_uid', value: '7c028d85-58c3-45f8-be2d-33c16b0905b0')
            column(name: 'wr_wrk_inst', value: '122265847')
            column(name: 'work_title', value: 'Ulysses')
            column(name: 'system_title', value: 'Ulysses')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '2000') // B-55836 remove
            column(name: 'gross_amount', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'fe112c31-e069-455e-a64e-e98a8115e4b4')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '2000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c88ba5e1-e1bc-4804-bd9b-13553fee3790')
            column(name: 'df_usage_batch_uid', value: '7c028d85-58c3-45f8-be2d-33c16b0905b0')
            column(name: 'wr_wrk_inst', value: '122266795')
            column(name: 'work_title', value: 'Nine stories')
            column(name: 'system_title', value: 'Nine stories')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2019-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '1000') // B-55836 remove
            column(name: 'gross_amount', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c88ba5e1-e1bc-4804-bd9b-13553fee3790')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '1000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '334959d7-ad39-4624-a8fa-38c3e82be6eb')
            column(name: 'name', value: 'NTS Batch with unclassified usages')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 0, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            column(name: 'updated_datetime', value: '2019-01-02 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8cd599c9-13d1-44b0-a4fc-dac66bcfdc23')
            column(name: 'df_usage_batch_uid', value: '334959d7-ad39-4624-a8fa-38c3e82be6eb')
            column(name: 'wr_wrk_inst', value: '122266807')
            column(name: 'work_title', value: 'My servants')
            column(name: 'system_title', value: 'My servants')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '2000') // B-55836 remove
            column(name: 'gross_amount', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8cd599c9-13d1-44b0-a4fc-dac66bcfdc23')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '2000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '818313ee-b78b-4091-812c-232bc6af90f9')
            column(name: 'df_usage_batch_uid', value: '334959d7-ad39-4624-a8fa-38c3e82be6eb')
            column(name: 'wr_wrk_inst', value: '122266947')
            column(name: 'work_title', value: 'Lucky Jim')
            column(name: 'system_title', value: 'Lucky Jim')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2019-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '1000') // B-55836 remove
            column(name: 'gross_amount', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '818313ee-b78b-4091-812c-232bc6af90f9')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '1000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'a49fcbe9-81d3-4223-8ed1-9c4ee04efdf5')
            column(name: 'wr_wrk_inst', value: '122267149')
            column(name: 'classification', value: 'BELLETRISTIC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '722cdcc8-de08-4330-b2a5-675285f012e1')
            column(name: 'wr_wrk_inst', value: '122267353')
            column(name: 'classification', value: 'BELLETRISTIC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '00aed73a-4243-440b-aa8a-445185580cb9')
            column(name: 'name', value: 'NTS Batch with Belletristic usages')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            column(name: 'updated_datetime', value: '2019-01-03 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8e837b15-2c5a-4ebe-8420-699b93bc05dc')
            column(name: 'df_usage_batch_uid', value: '00aed73a-4243-440b-aa8a-445185580cb9')
            column(name: 'wr_wrk_inst', value: '122267149')
            column(name: 'work_title', value: 'Personal influence')
            column(name: 'system_title', value: 'Personal influence')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '2000') // B-55836 remove
            column(name: 'gross_amount', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8e837b15-2c5a-4ebe-8420-699b93bc05dc')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '2000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bf94c94d-4175-4976-878e-5adb02c5d937')
            column(name: 'df_usage_batch_uid', value: '00aed73a-4243-440b-aa8a-445185580cb9')
            column(name: 'wr_wrk_inst', value: '122267353')
            column(name: 'work_title', value: 'Deliver us from evil')
            column(name: 'system_title', value: 'Deliver us from evil')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2019-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '1000') // B-55836 remove
            column(name: 'gross_amount', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bf94c94d-4175-4976-878e-5adb02c5d937')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '1000')
        }

        changeSet(id: '2019-04-29-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
            comment("Insert test data for testFindProcessingBatchesNames")

            insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
                column(name: 'df_usage_batch_uid', value: '13027b25-2269-3bec-48ea-5126431eedb0')
                column(name: 'name', value: 'CADRA_10Dec16')
                column(name: 'rro_account_number', value: '1000000001')
                column(name: 'payment_date', value: '2019-01-11')
                column(name: 'product_family', value: 'NTS')
                column(name: 'fiscal_year', value: '2020')
                column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, "fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
                column(name: 'updated_datetime', value: '2019-01-04 12:45:51.335531+03')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'df_usage_uid', value: '0d200064-185a-4c48-bbc9-c67554e7db8e')
                column(name: 'df_usage_batch_uid', value: '13027b25-2269-3bec-48ea-5126431eedb0')
                column(name: 'wr_wrk_inst', value: '122267671')
                column(name: 'work_title', value: 'A theory of cognitive dissonance')
                column(name: 'system_title', value: 'A theory of cognitive dissonance')
                column(name: 'rh_account_number', value: '1000009997')
                column(name: 'status_ind', value: 'ELIGIBLE')
                column(name: 'product_family', value: 'NTS')
                column(name: 'standard_number', value: '0804709114')
                column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
                column(name: 'market', value: 'Univ') // B-55836 remove
                column(name: 'market_period_from', value: '2013') // B-55836 remove
                column(name: 'market_period_to', value: '2017') // B-55836 remove
                column(name: 'reported_value', value: '141.32') // B-55836 remove
                column(name: 'gross_amount', value: '0.00')
            }
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '0d200064-185a-4c48-bbc9-c67554e7db8e')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '141.32')
        }

        rollback ""
    }

    changeSet(id: '2019-05-22-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testFindBatchNameToWrWrkInstMapByUsageStatus')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '071ebf56-eb38-49fc-b26f-cc210a374d3a')
            column(name: 'name', value: 'FAS2 Batch With RH Not Found usages')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '35000')
            column(name: 'updated_datetime', value: '2017-01-13 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c0ac91f9-9815-4c7b-aac9-3c446b1b9b78')
            column(name: 'df_usage_batch_uid', value: '071ebf56-eb38-49fc-b26f-cc210a374d3a')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle') // B-55836 remove
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '2500') // B-55836 remove
            column(name: 'gross_amount', value: '35000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c0ac91f9-9815-4c7b-aac9-3c446b1b9b78')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '2500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c49e77fb-b3b9-4b8b-884c-0216d46ed2dd')
            column(name: 'df_usage_batch_uid', value: '071ebf56-eb38-49fc-b26f-cc210a374d3a')
            column(name: 'wr_wrk_inst', value: '345870577')
            column(name: 'work_title', value: '10 Years Plant Molecular Biology')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'publisher', value: 'McGraw-Hill') // B-55836 remove
            column(name: 'publication_date', value: '2009-12-31') // B-55836 remove
            column(name: 'market', value: 'Edu') // B-55836 remove
            column(name: 'market_period_from', value: '2015') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'author', value: 'Mirjam H. Hüberli') // B-55836 remove
            column(name: 'number_of_copies', value: '2630')
            column(name: 'reported_value', value: '1280.00') // B-55836 remove
            column(name: 'gross_amount', value: '2125.24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c49e77fb-b3b9-4b8b-884c-0216d46ed2dd')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'McGraw-Hill')
            column(name: 'publication_date', value: '2009-12-31')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: 'Mirjam H. Hüberli')
            column(name: 'reported_value', value: '1280.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '033cc3dd-b121-41d5-91e6-cf4ddf71c141')
            column(name: 'name', value: 'FAS2 Batch With Eligible and RH Not Found usages')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '35000')
            column(name: 'updated_datetime', value: '2017-01-12 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e6bf0df7-5c84-4a04-b726-80853a39d050')
            column(name: 'df_usage_batch_uid', value: '033cc3dd-b121-41d5-91e6-cf4ddf71c141')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle') // B-55836 remove
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '2500') // B-55836 remove
            column(name: 'gross_amount', value: '35000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e6bf0df7-5c84-4a04-b726-80853a39d050')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '2500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2b7b7d14-606c-473f-a78b-2bd985d55a6a')
            column(name: 'df_usage_batch_uid', value: '033cc3dd-b121-41d5-91e6-cf4ddf71c141')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle') // B-55836 remove
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2014') // B-55836 remove
            column(name: 'market_period_to', value: '2016') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '200')
            column(name: 'reported_value', value: '1000') // B-55836 remove
            column(name: 'gross_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2b7b7d14-606c-473f-a78b-2bd985d55a6a')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2014')
            column(name: 'market_period_to', value: '2016')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '1000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ea8f10f6-e1b0-4e22-8cc4-b8b8d5627def')
            column(name: 'df_usage_batch_uid', value: '033cc3dd-b121-41d5-91e6-cf4ddf71c141')
            column(name: 'work_title', value: '10 Years Plant Molecular Biology')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'publisher', value: 'McGraw-Hill') // B-55836 remove
            column(name: 'publication_date', value: '2009-12-31') // B-55836 remove
            column(name: 'market', value: 'Edu') // B-55836 remove
            column(name: 'market_period_from', value: '2015') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'author', value: 'Mirjam H. Hüberli') // B-55836 remove
            column(name: 'number_of_copies', value: '2630')
            column(name: 'reported_value', value: '1280.00') // B-55836 remove
            column(name: 'gross_amount', value: '2125.24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ea8f10f6-e1b0-4e22-8cc4-b8b8d5627def')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'McGraw-Hill')
            column(name: 'publication_date', value: '2009-12-31')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: 'Mirjam H. Hüberli')
            column(name: 'reported_value', value: '1280.00')
        }
    }
}
