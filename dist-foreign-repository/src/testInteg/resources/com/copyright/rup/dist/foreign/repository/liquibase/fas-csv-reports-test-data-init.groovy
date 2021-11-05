databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-06-16-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testWriteExcludeDetailsByPayeeCsvReport, testWriteExcludeDetailsByPayeeCsvEmptyReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05c4714b-291d-4e38-ba4a-35307434acfb')
            column(name: 'rh_account_number', value: 7000813806)
            column(name: 'name', value: 'CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'name', value: 'FAS2 scenario for Exclude Details by Payee report')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'name', value: 'FAS2 batch for Exclude Details by Payee report')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 1000)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'created_datetime', value: '2019-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '30787462-b66a-4679-a2bc-1b89abd6ea66')
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '30787462-b66a-4679-a2bc-1b89abd6ea66')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2019)
            column(name: 'reported_value', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5e3b4c6c-fe20-4a16-bbb6-9dc61b6c1eed')
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '2008902112317645XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 84.00)
            column(name: 'service_fee_amount', value: 16.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5e3b4c6c-fe20-4a16-bbb6-9dc61b6c1eed')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2019)
            column(name: 'reported_value', value: 100.00)
            column(name: 'is_rh_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '77dce0ba-3a7c-4162-8264-69d1b973e7ac')
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'wr_wrk_inst', value: 12318778798)
            column(name: 'work_title', value: 'Future of medicine')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'payee_account_number', value: 7000813806)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '3008902112317645XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '77dce0ba-3a7c-4162-8264-69d1b973e7ac')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2019)
            column(name: 'reported_value', value: 100.00)
            column(name: 'is_payee_participating_flag', value: true)
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2020-01-03-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testWriteArchivedFasScenarioUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '63f3ea68-b06a-4e59-8e16-48dc35f5091e')
            column(name: 'name', value: 'JAACC_11Dec16')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'gross_amount', value: 70000.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'ff47dee9-327a-4ff6-b170-d89f5190ccd8')
            column(name: 'name', value: 'FAS scenario with archived usages')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '8dad0c7c-17d7-45c8-9e4e-329561be078a')
            column(name: 'df_scenario_uid', value: 'ff47dee9-327a-4ff6-b170-d89f5190ccd8')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '57860796-b72e-4e68-9968-309cbd208d5f')
            column(name: 'df_usage_batch_uid', value: '63f3ea68-b06a-4e59-8e16-48dc35f5091e')
            column(name: 'df_scenario_uid', value: 'ff47dee9-327a-4ff6-b170-d89f5190ccd8')
            column(name: 'wr_wrk_inst', value: 345870577)
            column(name: 'work_title', value: '10 Years Plant Molecular Biology')
            column(name: 'system_title', value: '10 Years Plant Molecular Biology')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'payee_account_number', value: 1000005413)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'number_of_copies', value: 2630)
            column(name: 'gross_amount', value: 2125.24)
            column(name: 'net_amount', value: 1445.1632)
            column(name: 'service_fee_amount', value: 680.0768)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'standard_number_type', value: 'VALISBN10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '57860796-b72e-4e68-9968-309cbd208d5f')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'McGraw-Hill')
            column(name: 'publication_date', value: '2009-12-31')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2019)
            column(name: 'author', value: 'Mirjam H. Hüberli')
            column(name: 'reported_value', value: 1280.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ff996279-39f2-46e5-8e29-3ecc3406117a')
            column(name: 'df_usage_batch_uid', value: '63f3ea68-b06a-4e59-8e16-48dc35f5091e')
            column(name: 'df_scenario_uid', value: 'ff47dee9-327a-4ff6-b170-d89f5190ccd8')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 67874.80)
            column(name: 'net_amount', value: 46154.80)
            column(name: 'service_fee_amount', value: 21720.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'standard_number_type', value: 'VALISBN10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ff996279-39f2-46e5-8e29-3ecc3406117a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 9900)
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2019-05-22-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testWriteOwnershipAdjustmentCsvReport, testWriteOwnershipAdjustmentCsvEmptyReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '60241909-744c-4766-ad67-fdc9e2c043eb')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '99991909-744c-4766-ad67-fdc9e2c043eb')
            column(name: 'rh_account_number', value: 1000002901)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282cac-2468-48d4-b346-93d3458a656a')
            column(name: 'name', value: 'Ownership Adjustment Report Batch')
            column(name: 'rro_account_number', value: 7000800832)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 30000)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'name', value: 'Ownership Adjustment Report Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'df_usage_batch_uid', value: '56282cac-2468-48d4-b346-93d3458a656a')
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'wr_wrk_inst', value: 122843178)
            column(name: 'work_title', value: 'Accounting education news')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'gross_amount', value: 16437.40)
            column(name: 'net_amount', value: 11177.40)
            column(name: 'service_fee_amount', value: 5260.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Bus, Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 9900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder_discrepancy') {
            column(name: 'df_rightsholder_discrepancy_uid', value: '5745caa4-4301-4943-a992-4561c22d3ef0')
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'wr_wrk_inst', value: 122843178)
            column(name: 'old_rh_account_number', value: 1000002859)
            column(name: 'new_rh_account_number', value: 1000005413)
            column(name: 'work_title', value: 'Accounting education news')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'APPROVED')
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2019-12-11-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testWriteFasUsagesCsvReport')

        // batch with ELIGIBLE usages
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '4c9cc089-b812-42cf-a5d2-1f5eda51fa76')
            column(name: 'name', value: 'FAS batch 1')
            column(name: 'rro_account_number', value: 2000017001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2019-01-01')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '25a96343-310f-4c9d-8923-63a58a3f57c6')
            column(name: 'df_usage_batch_uid', value: '4c9cc089-b812-42cf-a5d2-1f5eda51fa76')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 250.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '25a96343-310f-4c9d-8923-63a58a3f57c6')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2cd2b2be-605d-4fd5-9855-ca1064b00366')
            column(name: 'df_usage_batch_uid', value: '4c9cc089-b812-42cf-a5d2-1f5eda51fa76')
            column(name: 'wr_wrk_inst', value: 743904744)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: 1000002901)
            column(name: 'payee_account_number', value: 1000002901)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 250.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2cd2b2be-605d-4fd5-9855-ca1064b00366')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        // batch with LOCKED usage
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '127da857-5f78-4d2d-a8c3-55a08d5bd52b')
            column(name: 'name', value: 'FAS Batch 2')
            column(name: 'rro_account_number', value: 2000017001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 8972.00)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'created_datetime', value: '2019-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '0d90572c-696a-4e72-b9d1-e56d5d15045e')
            column(name: 'name', value: 'Test Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '7d90572c-696a-4e72-b9d1-e56d5d150451')
            column(name: 'df_scenario_uid', value: '0d90572c-696a-4e72-b9d1-e56d5d15045e')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9d94a627-761a-4676-bd10-cad43f10da39')
            column(name: 'df_usage_batch_uid', value: '127da857-5f78-4d2d-a8c3-55a08d5bd52b')
            column(name: 'df_scenario_uid', value: '0d90572c-696a-4e72-b9d1-e56d5d15045e')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'work_title', value: 'CHICKEN BREAST ON GRILL WITH FLAMES')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'payee_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'net_amount', value: 6100.9872)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 2871.0528)
            column(name: 'gross_amount', value: 8972.04)
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9d94a627-761a-4676-bd10-cad43f10da39')
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

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2018-05-21-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserts data for csv reports integration tests')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e855bf85-236c-42e7-9b12-8d68dd747bbe')
            column(name: 'name', value: 'Batch with NTS')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e2b3c369-3084-41ad-92b5-62197660d645')
            column(name: 'df_usage_batch_uid', value: 'e855bf85-236c-42e7-9b12-8d68dd747bbe')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'comment', value: 'DIN EN 779:2012')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e2b3c369-3084-41ad-92b5-62197660d645')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 500.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '034873b3-97fa-475a-9a2a-191e8ec988b3')
            column(name: 'name', value: 'Test Batch 1')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 40300.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'name', value: 'Test Batch 3')
            column(name: 'rro_account_number', value: 2000017001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 10250.00)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'name', value: 'Test Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
            column(name: 'record_version', value: 1)
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2017-02-14 11:45:52.735531+03')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2017-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'b1b7c100-42f8-49f7-ab9f-a89e92a011c1')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'record_version', value: 1)
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2017-02-14 11:45:52.735531+03')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2017-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9f96760c-0de9-4cee-abf2-65521277281b')
            column(name: 'df_usage_batch_uid', value: '034873b3-97fa-475a-9a2a-191e8ec988b3')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'work_title', value: 'CHICKEN BREAST ON GRILL WITH FLAMES')
            column(name: 'system_title', value: 'Chicken Breast On Grill With Flames')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'payee_account_number', value: 1000000001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 26776.51)
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'service_fee', value: 0.0)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9f96760c-0de9-4cee-abf2-65521277281b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 9900.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e4a81fad-7b0e-4c67-8df2-112c8913e45e')
            column(name: 'df_usage_batch_uid', value: '034873b3-97fa-475a-9a2a-191e8ec988b3')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000000002)
            column(name: 'payee_account_number', value: 1000000002)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '0003324112314587XX')
            column(name: 'number_of_copies', value: 25)
            column(name: 'gross_amount', value: 13523.49)
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'service_fee', value: 0.0)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e4a81fad-7b0e-4c67-8df2-112c8913e45e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 5000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2641e7fe-2a5a-4cdf-8879-48816d705169')
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: 1000000003)
            column(name: 'payee_account_number', value: 1000000003)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 6509.31)
            column(name: 'service_fee_amount', value: 2082.98)
            column(name: 'net_amount', value: 4426.33)
            column(name: 'service_fee', value: 0.32)
            column(name: 'comment', value: '471137967')
            column(name: 'standard_number_type', value: 'VALISSN')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2641e7fe-2a5a-4cdf-8879-48816d705169')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: 15000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '405491b1-49a9-4b70-9cdb-d082be6a802d')
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'wr_wrk_inst', value: 122235139)
            column(name: 'work_title', value: 'BOWL OF BERRIES WITH SUGAR COOKIES')
            column(name: 'system_title', value: 'BOWL OF BERRIES WITH SUGAR COOKIES')
            column(name: 'rh_account_number', value: 1000000004)
            column(name: 'payee_account_number', value: 1000000004)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1301.86)
            column(name: 'service_fee_amount', value: 416.60)
            column(name: 'net_amount', value: 885.26)
            column(name: 'service_fee', value: 0.32)
            column(name: 'comment', value: '122235139')
            column(name: 'standard_number_type', value: 'VALISBN10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '405491b1-49a9-4b70-9cdb-d082be6a802d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: 3000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4ddfcb74-cb72-48f6-9ee4-8b4e05afce75')
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'wr_wrk_inst', value: 471137469)
            column(name: 'work_title', value: 'Solar Cells')
            column(name: 'system_title', value: 'Solar Cells')
            column(name: 'rh_account_number', value: 1000000006)
            column(name: 'payee_account_number', value: 1000000006)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '052365874521235XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 2438.82)
            column(name: 'service_fee_amount', value: 780.42)
            column(name: 'net_amount', value: 1658.40)
            column(name: 'service_fee', value: 0.32)
            column(name: 'comment', value: '471137469')
            column(name: 'standard_number_type', value: 'VALISBN13')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4ddfcb74-cb72-48f6-9ee4-8b4e05afce75')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: 5620.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '77b111d3-9eea-49af-b815-100b9716c1b3')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'name', value: 'CLA, The Copyright Licensing Agency Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b2ea68f6-3c15-4ae3-a04a-acdd5a236f0c')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '074c0f1f-2457-49a9-ad72-2a85d07ce90e')
            column(name: 'rh_account_number', value: 1000000002)
            column(name: 'name', value: 'Royal Society of Victoria')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'd27264c2-eae4-4929-978d-77ba19ea417b')
            column(name: 'rh_account_number', value: 1000000003)
            column(name: 'name', value: 'South African Institute of Mining and Metallurgy')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b1c51d03-0c6e-4329-ba81-1060234c6c5e')
            column(name: 'rh_account_number', value: 1000000004)
            column(name: 'name', value: 'Computers for Design and Construction')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'bb888db8-1cd6-4c7e-a2b8-5486986eee95')
            column(name: 'rh_account_number', value: 1000000006)
            column(name: 'name', value: 'Januz Marketing Communications')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1da0461a-92f9-40cc-a3c1-9b972505b9c9')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'name', value: 'CFC/ Center Fran dexploitation du droit de Copie')
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2019-03-27-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testWriteUndistributedLiabilitiesCsvReport')

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

    changeSet(id: '2019-03-27-01', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testWriteFasBatchSummaryCsvReport')

        // Batch summary report
        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '4502b24f-3146-4682-a654-7f1f6647cb7f')
            column(name: 'rh_account_number', value: 2000017011)
            column(name: 'name', value: 'DALRO, Dramatic, Artistic & Literary Rights Organisation')
        }

        // Batch with two details associated with in progress scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b651')
            column(name: 'name', value: 'AT_batch-summary-report-1_BATCH')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two nts and two eligible details
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
            column(name: 'name', value: 'AT_batch-summary-report-2_BATCH')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 250.00)
            column(name: 'initial_usages_count', value: 6)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible and two details associated with in progress scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'name', value: 'AT_batch-summary-report-3_BATCH')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 550.00)
            column(name: 'initial_usages_count', value: 6)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible, two nts, two details that were sent to lm and details in paid and archived status
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'name', value: 'AT_batch-summary-report-4_BATCH')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 450.00)
            column(name: 'initial_usages_count', value: 10)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible, two nts and two details associated with scenario in status submit
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'name', value: 'AT_batch-summary-report-5_BATCH')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 450.00)
            column(name: 'initial_usages_count', value: 8)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible, two nts and two details associated with scenario in status approved
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'name', value: 'AT_batch-summary-report-6_BATCH')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 450.00)
            column(name: 'initial_usages_count', value: 8)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }
        // Batch with two non eligible, two eligible, two nts, two details associated with scenario and two return to cla details that were sent to lm
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'name', value: 'AT_batch-summary-report-7_BATCH')
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
            column(name: 'name', value: 'AT_batch-summary-report-8_BATCH')
            column(name: 'rro_account_number', value: 2000017011)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 550.00)
            column(name: 'initial_usages_count', value: 10)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible, two nts, two details associated with scenario and two details that were sent to lm
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'name', value: 'AT_batch-summary-report-9_BATCH')
            column(name: 'rro_account_number', value: 7000581909)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-05-08')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 550.00)
            column(name: 'initial_usages_count', value: 10)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two details that were sent to lm. Shouldn't be included into report
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b610')
            column(name: 'name', value: 'AT_batch-summary-report-10_BATCH')
            column(name: 'rro_account_number', value: 2000017011)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-05-08')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 2)
        }

        // Batch with nts details. Shouldn't be included into report
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b611')
            column(name: 'name', value: 'AT_batch-summary-report-11_BATCH')
            column(name: 'rro_account_number', value: 2000017011)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-05-08')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible, two nts, four details associated with in progress scenario where two of them return to cla details
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'name', value: 'AT_batch-summary-report-12_BATCH')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2018-05-08')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 600.00)
            column(name: 'initial_usages_count', value: 10)
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
            column(name: 'name', value: 'AT_batch-summary-report-1_SCENARIO')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe2')
            column(name: 'name', value: 'AT_batch-summary-report-2_SCENARIO')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe3')
            column(name: 'name', value: 'AT_batch-summary-report-3_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe4')
            column(name: 'name', value: 'AT_batch-summary-report-4_SCENARIO')
            column(name: 'status_ind', value: 'SUBMITTED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe5')
            column(name: 'name', value: 'AT_batch-summary-report-5_SCENARIO')
            column(name: 'status_ind', value: 'APPROVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe6')
            column(name: 'name', value: 'AT_batch-summary-report-6_SCENARIO')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe7')
            column(name: 'name', value: 'AT_batch-summary-report-7_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe8')
            column(name: 'name', value: 'AT_batch-summary-report-8_SCENARIO')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe9')
            column(name: 'name', value: 'AT_batch-summary-report-9_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f10')
            column(name: 'name', value: 'AT_batch-summary-report-10_SCENARIO')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f11')
            column(name: 'name', value: 'AT_batch-summary-report-11_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f12')
            column(name: 'name', value: 'AT_batch-summary-report-12_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f13')
            column(name: 'name', value: 'AT_batch-summary-report-13_SCENARIO')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '92824546-d34d-7102-9939-92c50f356fe2')
            column(name: 'name', value: 'AT_batch-summary-report-14_SCENARIO')
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
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea03')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea03')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea04')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea04')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ,Bus,Doc,S')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea05')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea05')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ,Bus,Doc,S')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea06')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea06')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ,Bus,Doc,S')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea07')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea07')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Gov')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea08')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea08')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Gov')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea09')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea09')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea10')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea10')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea11')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea11')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea12')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea12')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea13')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe2')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 150.00)
            column(name: 'net_amount', value: 102.00)
            column(name: 'service_fee_amount', value: 48.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea13')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea14')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe2')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 150.00)
            column(name: 'net_amount', value: 102.00)
            column(name: 'service_fee_amount', value: 48.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea14')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea15')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea15')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea16')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea16')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea17')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea17')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea18')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea18')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea19')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea19')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea20')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea20')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea23')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe3')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 45.00)
            column(name: 'service_fee_amount', value: 5.00)
            column(name: 'service_fee', value: 0.10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea23')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea24')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe3')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 45.00)
            column(name: 'service_fee_amount', value: 5.00)
            column(name: 'service_fee', value: 0.10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea24')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea25')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe3')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 42.00)
            column(name: 'service_fee_amount', value: 8.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2017-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea25')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea26')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe3')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 42.00)
            column(name: 'service_fee_amount', value: 8.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2017-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea26')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea27')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea27')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea28')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea28')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea29')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea29')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea30')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea30')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea31')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea31')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea32')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea32')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea33')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe4')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea33')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea34')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe4')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea34')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea35')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea35')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea36')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea36')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea37')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea37')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea38')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea38')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea39')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea39')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea40')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea40')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea41')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe5')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea41')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea42')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe5')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea42')
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
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea65')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea65')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea66')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea66')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea67')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea67')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea68')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea68')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea69')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea69')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea70')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea70')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea71')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f10')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea71')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea72')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f10')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea72')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea73')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f11')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea73')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea74')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f11')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea74')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea75')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b610')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f12')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea75')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea76')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b610')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f12')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea76')
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
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea79')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea79')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea80')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea80')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea90')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea90')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea91')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea91')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea92')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea92')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea93')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
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
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea93')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea94')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f13')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 42.00)
            column(name: 'service_fee_amount', value: 8.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea94')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea95')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f13')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 42.00)
            column(name: 'service_fee_amount', value: 8.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea95')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea96')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f13')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 2000017000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 45.00)
            column(name: 'service_fee_amount', value: 5.00)
            column(name: 'service_fee', value: 0.10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea96')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea97')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f13')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 2000017000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 45.00)
            column(name: 'service_fee_amount', value: 5.00)
            column(name: 'service_fee', value: 0.10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea97')
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

    changeSet(id: '2019-03-27-02', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testFasWriteServiceFeeTrueUpCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '4cc22676-bb0b-4f06-be23-e245d474b01d')
            column(name: 'rh_account_number', value: 5000581901)
            column(name: 'name', value: 'Unknown RRO')
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
            column(name: 'df_scenario_usage_filter_uid', value: '5d685f54-67dc-4fc9-a9b6-adcd5c2f2a49')
            column(name: 'df_scenario_uid', value: '7887b131-e38d-452d-806b-f9615a2401ce')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '5d685f54-67dc-4fc9-a9b6-adcd5c2f2a49')
            column(name: 'df_usage_batch_uid', value: '7675a48a-cba6-4a2e-a4a4-fee2237a0128')
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
