databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2017-11-30-00', author: 'Aliaksandra_Bayanouskaya <abayanouskaya@copyright.com>') {
        comment('Inserting test data for UsageRepositoryIntegrationTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '9905f006-a3e1-4061-b3d4-e7ece191103f')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5bcf2c37-2f32-48e9-90fe-c9d75298eeed')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8a0dbf78-d9c9-49d9-a895-05f55cfc8329')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'e9c9f51b-6048-4474-848a-2db1c410e463')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'name', value: 'British Film Institute (BFI)')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '46754660-b627-46b9-a782-3f703b6853c7')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'name', value: 'Access Copyright, The Canadian Copyright Agency')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05c4714b-291d-4e38-ba4a-35307434acfb')
            column(name: 'rh_account_number', value: 7000813806)
            column(name: 'name', value: 'CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1caa7fac-7287-4075-8e8e-492bdcfcba5a')
            column(name: 'rh_account_number', value: 7000813807)
            column(name: 'name', value: 'CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a92a9eb8-ab95-420d-87a3-0ba663d5344e')
            column(name: 'rh_account_number', value: 7000896777)
            column(name: 'name', value: 'CeMPro, Centro Mexicano de Proteccion y Fomento de los Derechos de Autor')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '63319ddb-4a9d-4e86-aa88-1f046cd80ddf')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'name', value: 'CLA, The Copyright Licensing Agency Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-94d3458a666a')
            column(name: 'name', value: 'AccessCopyright_11Dec16')
            column(name: 'rro_account_number', value: 2000017004)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-08-16')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'name', value: 'JAACC_11Dec16')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e27551ed-3f69-4e08-9e4f-8ac03f67595f')
            column(name: 'name', value: 'Scenario name 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'abe31cdc-adfb-41c5-9a46-4ca4966a41be')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9a898ab5-30c9-4289-8e17-2c35dcb7f9e1')
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'df_scenario_uid', value: 'abe31cdc-adfb-41c5-9a46-4ca4966a41be')
            column(name: 'wr_wrk_inst', value: 123456783)
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'rh_account_number', value: 7000813807)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'standard_number_type', value: 'STDID')
            column(name: 'number_of_copies', value: 155)
            column(name: 'gross_amount', value: 54.4400000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9a898ab5-30c9-4289-8e17-2c35dcb7f9e1')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 11.25)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3ab5e80b-89c0-4d78-9675-54c7ab284450')
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 35000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3ab5e80b-89c0-4d78-9675-54c7ab284450')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8a06905f-37ae-4e1f-8550-245277f8165c')
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-94d3458a666a')
            column(name: 'wr_wrk_inst', value: 244614835)
            column(name: 'work_title', value: '15th International Conference on Environmental Degradation of Materials in Nuclear Power Systems Water Reactors')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'number_of_copies', value: 1600)
            column(name: 'gross_amount', value: 35000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8a06905f-37ae-4e1f-8550-245277f8165c')
            column(name: 'article', value: 'First-Week Protein and Energy Intakes Are Associated With 18-Month Developmental Outcomes in Extremely Low Birth Weight Infants')
            column(name: 'publisher', value: 'John Wiley & Sons')
            column(name: 'publication_date', value: '2011-05-10')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2019)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 1560)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5c5f8c1c-1418-4cfd-8685-9212f4c421d1')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'wr_wrk_inst', value: 345870577)
            column(name: 'work_title', value: '10 Years Plant Molecular Biology')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'number_of_copies', value: 2630)
            column(name: 'gross_amount', value: 2125.24)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5c5f8c1c-1418-4cfd-8685-9212f4c421d1')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'McGraw-Hill')
            column(name: 'publication_date', value: '2009-12-31')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2019)
            column(name: 'author', value: 'Mirjam H. Hüberli')
            column(name: 'reported_value', value: 1280.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d11')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 16437.40)
            column(name: 'net_amount', value: 11177.40)
            column(name: 'service_fee_amount', value: 5260.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d11')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 9900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cf38d390-11bb-4af7-9685-e034c9c32fb6')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 16437.40)
            column(name: 'net_amount', value: 11177.40)
            column(name: 'service_fee_amount', value: 5260.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'cf38d390-11bb-4af7-9685-e034c9c32fb6')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 9900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '8b56c3d6-52c0-4f25-8d78-923afcfd31e6')
            column(name: 'name', value: 'JAACC_12Dec20')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2020-12-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '62e0ddd7-a37f-4810-8ada-abab805cb48d')
            column(name: 'df_usage_batch_uid', value: '8b56c3d6-52c0-4f25-8d78-923afcfd31e6')
            column(name: 'wr_wrk_inst', value: 922859149)
            column(name: 'work_title', value: 'Psychiatric services')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112316635XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 16437.40)
            column(name: 'net_amount', value: 11177.40)
            column(name: 'service_fee_amount', value: 5260.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'updated_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '62e0ddd7-a37f-4810-8ada-abab805cb48d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 9900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '979c981c-6a3a-46f3-bbd7-83d322ce9136')
            column(name: 'name', value: 'Sent To LM Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e0af666b-cbb7-4054-9906-12daa1fbd76e')
            column(name: 'name', value: 'Audit Test batch')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 3000.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'a71a0544-128e-41c0-b6b0-cfbbea6d2182')
            column(name: 'df_usage_batch_uid', value: 'e0af666b-cbb7-4054-9906-12daa1fbd76e')
            column(name: 'df_scenario_uid', value: '979c981c-6a3a-46f3-bbd7-83d322ce9136')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002475)
            column(name: 'payee_account_number', value: 1000002475)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'net_amount', value: 840.00)
            column(name: 'service_fee_amount', value: 160.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'comment', value: 'usage from usages_10.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a71a0544-128e-41c0-b6b0-cfbbea6d2182')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd9ca07b5-8282-4a81-9b9d-e4480f529d34')
            column(name: 'df_usage_batch_uid', value: 'e0af666b-cbb7-4054-9906-12daa1fbd76e')
            column(name: 'wr_wrk_inst', value: 103658926)
            column(name: 'work_title', value: 'Nitrates')
            column(name: 'system_title', value: 'Hydronitrous')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'net_amount', value: 1360.00)
            column(name: 'service_fee_amount', value: 640.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'comment', value: 'usage from usages_20.csv')
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
            column(name: 'reported_value', value: 200.00)
        }

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
            column(name: 'df_usage_uid', value: '2255188f-d582-4516-8c08-835cfe1d68c2')
            column(name: 'df_usage_batch_uid', value: 'cb597f4e-f636-447f-8710-0436d8994d10')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f5eb98ce-ab59-44c8-9a50-1afea2b5ae15')
            column(name: 'df_usage_batch_uid', value: 'cb597f4e-f636-447f-8710-0436d8994d10')
            column(name: 'product_family', value: 'NTS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: 1000009524)
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f5eb98ce-ab59-44c8-9a50-1afea2b5ae15')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c10a11c6-dae3-43d7-a632-c682542b1209')
            column(name: 'name', value: 'Works without WrWrkInst test 1')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '593c49c3-eb5b-477b-8556-f7a4725df2b3')
            column(name: 'df_usage_batch_uid', value: 'c10a11c6-dae3-43d7-a632-c682542b1209')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 10000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '593c49c3-eb5b-477b-8556-f7a4725df2b3')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7db6455e-5249-44db-801a-307f1c239310')
            column(name: 'df_usage_batch_uid', value: 'c10a11c6-dae3-43d7-a632-c682542b1209')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'standard_number', value: '2192-3566')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 10000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7db6455e-5249-44db-801a-307f1c239310')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'name', value: 'Works without WrWrkInst test 2')
            column(name: 'rro_account_number', value: 2000017011)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'initial_usages_count', value: 6)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '66a7c2c0-3b09-48ad-9aa5-a6d0822226c7')
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 10000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '66a7c2c0-3b09-48ad-9aa5-a6d0822226c7')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0c099fc0-e6f5-43c0-b2d5-ad971f974c10')
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'standard_number', value: '2192-3567')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 10000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '0c099fc0-e6f5-43c0-b2d5-ad971f974c10')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'dcc794ba-42aa-481d-937b-8f431929a611')
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 10000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'dcc794ba-42aa-481d-937b-8f431929a611')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '47d48889-76b5-4957-aca0-2a7850a09f92')
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 10000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '47d48889-76b5-4957-aca0-2a7850a09f92')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c5ea47b0-b269-4791-9aa7-76308fe835e6')
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 10000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c5ea47b0-b269-4791-9aa7-76308fe835e6')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b53bb4f3-9eee-4732-8e3d-0c88722081d8')
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 10000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b53bb4f3-9eee-4732-8e3d-0c88722081d8')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '091c08cf-8a93-4a64-87b5-4bdd44f97e79')
            column(name: 'name', value: 'Paid Scenario for Audit')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '48bfe456-fbc1-436e-8762-baca46a0e09c')
            column(name: 'name', value: 'Paid batch')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '0d85f51d-212b-4181-9972-3154cad74bd0')
            column(name: 'df_usage_batch_uid', value: '48bfe456-fbc1-436e-8762-baca46a0e09c')
            column(name: 'df_scenario_uid', value: '091c08cf-8a93-4a64-87b5-4bdd44f97e79')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'comment', value: 'usage from usages_10.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '0d85f51d-212b-4181-9972-3154cad74bd0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '1cb766c6-7c49-489a-bd8f-9b8b052f5785')
            column(name: 'df_usage_batch_uid', value: '48bfe456-fbc1-436e-8762-baca46a0e09c')
            column(name: 'df_scenario_uid', value: '091c08cf-8a93-4a64-87b5-4bdd44f97e79')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'check_number', value: '578946')
            column(name: 'check_date', value: '2017-03-16 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA July 17')
            column(name: 'distribution_date', value: '2017-03-16 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2017-03-16 11:41:52.735531+03')
            column(name: 'comment', value: 'usage from usages_20.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '1cb766c6-7c49-489a-bd8f-9b8b052f5785')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '66e0deb8-9cf5-4495-994a-d3a5761572f3')
            column(name: 'name', value: 'Batch for sorting 1')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9776da8d-098d-4f39-99fd-85405c339e9b')
            column(name: 'name', value: 'Batch for sorting 2')
            column(name: 'rro_account_number', value: 7000896777)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2023-02-12')
            column(name: 'fiscal_year', value: 2023)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3b6892a9-49b2-41a2-aa3a-8705ea6640cc')
            column(name: 'df_usage_batch_uid', value: '66e0deb8-9cf5-4495-994a-d3a5761572f3')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'net_amount', value: 8400.00)
            column(name: 'service_fee_amount', value: 1600.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'comment', value: 'usage from usages_15.csv')
            column(name: 'standard_number_type', value: 'VALISSN')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3b6892a9-49b2-41a2-aa3a-8705ea6640cc')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 1000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3c31db4f-4065-4fe1-84c2-b48a0f3bc079')
            column(name: 'df_usage_batch_uid', value: '9776da8d-098d-4f39-99fd-85405c339e9b')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 200)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'comment', value: 'usage from usages_25.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3c31db4f-4065-4fe1-84c2-b48a0f3bc079')
            column(name: 'article', value: 'DIN EN 779:2014')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2014-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2014)
            column(name: 'market_period_to', value: 2018)
            column(name: 'author', value: 'Aarseth, Espen J.')
            column(name: 'reported_value', value: 2000)
        }

        rollback {
            dbRollback
        }
    }
}
