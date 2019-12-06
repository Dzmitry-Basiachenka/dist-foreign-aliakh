databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2017-11-30-00', author: 'Aliaksandra_Bayanouskaya <abayanouskaya@copyright.com>') {
        comment('Inserting test data for UsageRepositoryIntegrationTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '9905f006-a3e1-4061-b3d4-e7ece191103f')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5bcf2c37-2f32-48e9-90fe-c9d75298eeed')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8a0dbf78-d9c9-49d9-a895-05f55cfc8329')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'e9c9f51b-6048-4474-848a-2db1c410e463')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'name', value: 'British Film Institute (BFI)')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '46754660-b627-46b9-a782-3f703b6853c7')
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'name', value: 'Access Copyright, The Canadian Copyright Agency')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05c4714b-291d-4e38-ba4a-35307434acfb')
            column(name: 'rh_account_number', value: '7000813806')
            column(name: 'name', value: 'CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: '2000017010')
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a92a9eb8-ab95-420d-87a3-0ba663d5344e')
            column(name: 'rh_account_number', value: '7000896777')
            column(name: 'name', value: 'CeMPro, Centro Mexicano de Proteccion y Fomento de los Derechos de Autor')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '63319ddb-4a9d-4e86-aa88-1f046cd80ddf')
            column(name: 'rh_account_number', value: '2000017000')
            column(name: 'name', value: 'CLA, The Copyright Licensing Agency Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '35000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-94d3458a666a')
            column(name: 'name', value: 'AccessCopyright_11Dec16')
            column(name: 'rro_account_number', value: '2000017004')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-08-16')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'gross_amount', value: '35000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'name', value: 'JAACC_11Dec16')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: '2016')
            column(name: 'gross_amount', value: '35000')
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
            column(name: 'df_usage_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d11')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '9900') // B-55836 remove
            column(name: 'gross_amount', value: '16437.40')
            column(name: 'net_amount', value: '11177.40')
            column(name: 'service_fee_amount', value: '5260.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d11')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '9900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cf38d390-11bb-4af7-9685-e034c9c32fb6')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '9900') // B-55836 remove
            column(name: 'gross_amount', value: '16437.40')
            column(name: 'net_amount', value: '11177.40')
            column(name: 'service_fee_amount', value: '5260.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'cf38d390-11bb-4af7-9685-e034c9c32fb6')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '9900')
        }

        // testFindByStatuses
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '8b56c3d6-52c0-4f25-8d78-923afcfd31e6')
            column(name: 'name', value: 'JAACC_12Dec20')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2020-12-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '35000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '62e0ddd7-a37f-4810-8ada-abab805cb48d')
            column(name: 'df_usage_batch_uid', value: '8b56c3d6-52c0-4f25-8d78-923afcfd31e6')
            column(name: 'wr_wrk_inst', value: '922859149')
            column(name: 'work_title', value: 'Psychiatric services')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112316635XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '9900') // B-55836 remove
            column(name: 'gross_amount', value: '16437.40')
            column(name: 'net_amount', value: '11177.40')
            column(name: 'service_fee_amount', value: '5260.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'updated_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '62e0ddd7-a37f-4810-8ada-abab805cb48d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '9900')
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
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '3000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'a71a0544-128e-41c0-b6b0-cfbbea6d2182')
            column(name: 'df_usage_batch_uid', value: 'e0af666b-cbb7-4054-9906-12daa1fbd76e')
            column(name: 'df_scenario_uid', value: '979c981c-6a3a-46f3-bbd7-83d322ce9136')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002475')
            column(name: 'payee_account_number', value: '1000002475')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00') // B-55836 remove
            column(name: 'gross_amount', value: '1000.00')
            column(name: 'net_amount', value: '840.00')
            column(name: 'service_fee_amount', value: '160.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'comment', value: 'usage from usages_10.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a71a0544-128e-41c0-b6b0-cfbbea6d2182')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd9ca07b5-8282-4a81-9b9d-e4480f529d34')
            column(name: 'df_usage_batch_uid', value: 'e0af666b-cbb7-4054-9906-12daa1fbd76e')
            column(name: 'wr_wrk_inst', value: '103658926')
            column(name: 'work_title', value: 'Nitrates')
            column(name: 'system_title', value: 'Hydronitrous')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '200.00') // B-55836 remove
            column(name: 'gross_amount', value: '2000.00')
            column(name: 'net_amount', value: '1360.00')
            column(name: 'service_fee_amount', value: '640.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'comment', value: 'usage from usages_20.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd9ca07b5-8282-4a81-9b9d-e4480f529d34')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '200.00')
        }

        //testFindProductFamilies
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'cb597f4e-f636-447f-8710-0436d8994d10')
            column(name: 'name', value: 'Batch with usages in WORK_NOT_FOUND status')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '10.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4dd8cdf8-ca10-422e-bdd5-3220105e6379')
            column(name: 'df_usage_batch_uid', value: 'cb597f4e-f636-447f-8710-0436d8994d10')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'reported_value', value: '30.86') // B-55836 remove
            column(name: 'gross_amount', value: '16.40')
            column(name: 'updated_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4dd8cdf8-ca10-422e-bdd5-3220105e6379')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c10a11c6-dae3-43d7-a632-c682542b1209')
            column(name: 'name', value: 'Works without WrWrkInst test')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '2000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '593c49c3-eb5b-477b-8556-f7a4725df2b3')
            column(name: 'df_usage_batch_uid', value: 'c10a11c6-dae3-43d7-a632-c682542b1209')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '10000') // B-55836 remove
            column(name: 'gross_amount', value: '10000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '593c49c3-eb5b-477b-8556-f7a4725df2b3')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7db6455e-5249-44db-801a-307f1c239310')
            column(name: 'df_usage_batch_uid', value: 'c10a11c6-dae3-43d7-a632-c682542b1209')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2192-3566')
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '10000') // B-55836 remove
            column(name: 'gross_amount', value: '10000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7db6455e-5249-44db-801a-307f1c239310')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'name', value: 'Works without WrWrkInst test')
            column(name: 'rro_account_number', value: '2000017011')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '2000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '66a7c2c0-3b09-48ad-9aa5-a6d0822226c7')
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '10000') // B-55836 remove
            column(name: 'gross_amount', value: '10000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '66a7c2c0-3b09-48ad-9aa5-a6d0822226c7')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0c099fc0-e6f5-43c0-b2d5-ad971f974c10')
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2192-3567')
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '10000') // B-55836 remove
            column(name: 'gross_amount', value: '10000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '0c099fc0-e6f5-43c0-b2d5-ad971f974c10')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'dcc794ba-42aa-481d-937b-8f431929a611')
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '10000') // B-55836 remove
            column(name: 'gross_amount', value: '10000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'dcc794ba-42aa-481d-937b-8f431929a611')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '47d48889-76b5-4957-aca0-2a7850a09f92')
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '10000') // B-55836 remove
            column(name: 'gross_amount', value: '10000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '47d48889-76b5-4957-aca0-2a7850a09f92')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c5ea47b0-b269-4791-9aa7-76308fe835e6')
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '10000') // B-55836 remove
            column(name: 'gross_amount', value: '10000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c5ea47b0-b269-4791-9aa7-76308fe835e6')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b53bb4f3-9eee-4732-8e3d-0c88722081d8')
            column(name: 'df_usage_batch_uid', value: '6aa177f1-2d53-4c98-82bf-da7f78ae70fb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '10000') // B-55836 remove
            column(name: 'gross_amount', value: '10000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b53bb4f3-9eee-4732-8e3d-0c88722081d8')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        //testFindForAuditWithSort
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '091c08cf-8a93-4a64-87b5-4bdd44f97e79')
            column(name: 'name', value: 'Paid Scenario for Audit')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '48bfe456-fbc1-436e-8762-baca46a0e09c')
            column(name: 'name', value: 'Paid batch')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '0d85f51d-212b-4181-9972-3154cad74bd0')
            column(name: 'df_usage_batch_uid', value: '48bfe456-fbc1-436e-8762-baca46a0e09c')
            column(name: 'df_scenario_uid', value: '091c08cf-8a93-4a64-87b5-4bdd44f97e79')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000') // B-55836 remove
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '420.00')
            column(name: 'service_fee_amount', value: '80.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA_March_17')
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
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '3000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '1cb766c6-7c49-489a-bd8f-9b8b052f5785')
            column(name: 'df_usage_batch_uid', value: '48bfe456-fbc1-436e-8762-baca46a0e09c')
            column(name: 'df_scenario_uid', value: '091c08cf-8a93-4a64-87b5-4bdd44f97e79')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002797')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000') // B-55836 remove
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '420.00')
            column(name: 'service_fee_amount', value: '80.00')
            column(name: 'service_fee', value: '0.16000')
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
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '3000')
        }

        //testUpdateResearchedUsage
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'name', value: 'Works without WrWrkInst test')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '2000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '721ca627-09bc-4204-99f4-6acae415fa5d')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '10000') // B-55836 remove
            column(name: 'gross_amount', value: '10000.00')
            column(name: 'net_amount', value: '8400.00')
            column(name: 'service_fee_amount', value: '1600.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '721ca627-09bc-4204-99f4-6acae415fa5d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '10000') // B-55836 remove
            column(name: 'gross_amount', value: '10000.00')
            column(name: 'net_amount', value: '8400.00')
            column(name: 'service_fee_amount', value: '1600.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        //testFindByFilterSortingByBatchInfo
        //testFindByFilterSortingByUsageInfo
        //testFindByFilterSortingByWorkInfo
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '66e0deb8-9cf5-4495-994a-d3a5761572f3')
            column(name: 'name', value: 'Batch for sorting 1')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: '2022')
            column(name: 'gross_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9776da8d-098d-4f39-99fd-85405c339e9b')
            column(name: 'name', value: 'Batch for sorting 2')
            column(name: 'rro_account_number', value: '7000896777')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2023-02-12')
            column(name: 'fiscal_year', value: '2023')
            column(name: 'gross_amount', value: '2000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3b6892a9-49b2-41a2-aa3a-8705ea6640cc')
            column(name: 'df_usage_batch_uid', value: '66e0deb8-9cf5-4495-994a-d3a5761572f3')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002797')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '1000') // B-55836 remove
            column(name: 'gross_amount', value: '1000.00')
            column(name: 'net_amount', value: '8400.00')
            column(name: 'service_fee_amount', value: '1600.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'comment', value: 'usage from usages_15.csv')
            column(name: 'standard_number_type', value: 'VALISSN')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3b6892a9-49b2-41a2-aa3a-8705ea6640cc')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '1000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3c31db4f-4065-4fe1-84c2-b48a0f3bc079')
            column(name: 'df_usage_batch_uid', value: '9776da8d-098d-4f39-99fd-85405c339e9b')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'article', value: 'DIN EN 779:2014') // B-55836 remove
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2014-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2014') // B-55836 remove
            column(name: 'market_period_to', value: '2018') // B-55836 remove
            column(name: 'author', value: 'Aarseth, Espen J.') // B-55836 remove
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '200')
            column(name: 'reported_value', value: '2000') // B-55836 remove
            column(name: 'gross_amount', value: '2000.00')
            column(name: 'comment', value: 'usage from usages_25.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3c31db4f-4065-4fe1-84c2-b48a0f3bc079')
            column(name: 'article', value: 'DIN EN 779:2014')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2014-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2014')
            column(name: 'market_period_to', value: '2018')
            column(name: 'author', value: 'Aarseth, Espen J.')
            column(name: 'reported_value', value: '2000')
        }

        rollback ""
    }

    changeSet(id: '2018-09-14-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for UsageRepositoryIntegrationTest:' +
                'insert post-distribution usage for find for audit and find count for audit')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'cce295c6-23cf-47b4-b00c-2e0e50cce169')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2016-11-03') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000') // B-55836 remove
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA_March_17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: 'ced40f77-704a-4b77-ae46-2698ef408df4')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'cce295c6-23cf-47b4-b00c-2e0e50cce169')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '3000')
        }
    }

    changeSet(id: '2018-12-03-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testDeleteById()')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '8a901f85-0949-40b8-9176-378d1a61afb8')
            column(name: 'name', value: 'Usage Batch')
            column(name: 'rro_account_number', value: '2000017011')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3cf274c5-8eac-4d4a-96be-5921ae026840')
            column(name: 'df_usage_batch_uid', value: '8a901f85-0949-40b8-9176-378d1a61afb8')
            column(name: 'product_family', value: 'NTS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: '1000009523')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100') // B-55836 remove
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '420.00')
            column(name: 'service_fee_amount', value: '80.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3cf274c5-8eac-4d4a-96be-5921ae026840')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '100')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f5eb98ce-ab59-44c8-9a50-1afea2b5ae15')
            column(name: 'df_usage_batch_uid', value: '8a901f85-0949-40b8-9176-378d1a61afb8')
            column(name: 'product_family', value: 'NTS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: '1000009524')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100') // B-55836 remove
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '420.00')
            column(name: 'service_fee_amount', value: '80.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f5eb98ce-ab59-44c8-9a50-1afea2b5ae15')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '100')
        }
    }

    // testFindCountByFilterAndNotInStatus
    changeSet(id: '2018-12-10-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testFindCountByFilterAndNotInStatus')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'ee575916-f6d0-4c3c-b589-32663e0f4793')
            column(name: 'name', value: 'Usage Batch')
            column(name: 'rro_account_number', value: '2000017011')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3adb01b0-6dc0-4f3c-ba71-c47a1f8d69b8')
            column(name: 'df_usage_batch_uid', value: 'ee575916-f6d0-4c3c-b589-32663e0f4793')
            column(name: 'product_family', value: 'NTS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: '1000009523')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100') // B-55836 remove
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '420.00')
            column(name: 'service_fee_amount', value: '80.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3adb01b0-6dc0-4f3c-ba71-c47a1f8d69b8')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '100')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '775ceaf9-125f-4387-b076-459eb4673d92')
            column(name: 'df_usage_batch_uid', value: 'ee575916-f6d0-4c3c-b589-32663e0f4793')
            column(name: 'product_family', value: 'NTS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'rh_account_number', value: '1000009524')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100') // B-55836 remove
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '420.00')
            column(name: 'service_fee_amount', value: '80.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '775ceaf9-125f-4387-b076-459eb4673d92')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '100')
        }
    }

    // testFindByStatusAnsProductFamily
    changeSet(id: '2019-01-14-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testFindByStatusAnsProductFamily')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'bc4076cc-7554-4575-a03e-d4f4b3b76ca9')
            column(name: 'rh_account_number', value: '1000009523')
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1b2f23a7-921a-4116-9268-86627fd2fc0b')
            column(name: 'rh_account_number', value: '1000009524')
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd368a40b-d3ab-45b1-80a3-07be2cd5224c')
            column(name: 'name', value: 'Usage Batch')
            column(name: 'rro_account_number', value: '2000017011')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '463e2239-1a36-41cc-9a51-ee2a80eae0c7')
            column(name: 'df_usage_batch_uid', value: 'd368a40b-d3ab-45b1-80a3-07be2cd5224c')
            column(name: 'product_family', value: 'NTS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'US_TAX_COUNTRY')
            column(name: 'rh_account_number', value: '1000009523')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'standard_number_type', value: 'STDID')
            column(name: 'publisher', value: 'Network for Science') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2015') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '200') // B-55836 remove
            column(name: 'gross_amount', value: '1000.00')
            column(name: 'net_amount', value: '840.00')
            column(name: 'service_fee_amount', value: '160.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '463e2239-1a36-41cc-9a51-ee2a80eae0c7')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2015')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '200')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bd407b50-6101-4304-8316-6404fe32a800')
            column(name: 'df_usage_batch_uid', value: 'd368a40b-d3ab-45b1-80a3-07be2cd5224c')
            column(name: 'product_family', value: 'NTS')
            column(name: 'wr_wrk_inst', value: '823904752')
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'status_ind', value: 'US_TAX_COUNTRY')
            column(name: 'rh_account_number', value: '1000009524')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2192-2555')
            column(name: 'standard_number_type', value: 'STDID')
            column(name: 'publisher', value: 'Network for Medicine') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Arturo de Mézières') // B-55836 remove
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100') // B-55836 remove
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '420.00')
            column(name: 'service_fee_amount', value: '80.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bd407b50-6101-4304-8316-6404fe32a800')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Medicine')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Arturo de Mézières')
            column(name: 'reported_value', value: '100')
        }
    }

    changeSet(id: '2019-03-20-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testFindUsageIdsForClassificationUpdate and testGetCutoffAmountByBatchIdAndClassification')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '73027b25-f269-4bec-a8ea-b126431eedbe')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c6cb5b07-45c0-4188-9da3-920046eec4cf')
            column(name: 'df_usage_batch_uid', value: '73027b25-f269-4bec-a8ea-b126431eedbe')
            column(name: 'wr_wrk_inst', value: '987632764')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'UNCLASSIFIED')
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
            column(name: 'reported_value', value: '2500') // B-55836 remove
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c6cb5b07-45c0-4188-9da3-920046eec4cf')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '2500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6dc54058-5566-4aa2-8cd4-d1a09805ae20')
            column(name: 'df_usage_batch_uid', value: '73027b25-f269-4bec-a8ea-b126431eedbe')
            column(name: 'wr_wrk_inst', value: '632876487')
            column(name: 'work_title', value: 'The myth of the visual word form area')
            column(name: 'system_title', value: 'The myth of the visual word form area')
            column(name: 'rh_account_number', value: '1000015086')
            column(name: 'status_ind', value: 'ELIGIBLE')
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
            column(name: 'reported_value', value: '2500') // B-55836 remove
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6dc54058-5566-4aa2-8cd4-d1a09805ae20')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '2500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'c78924f5-baf1-4c70-aa80-3c49c193b9ff')
            column(name: 'wr_wrk_inst', value: '987632764')
            column(name: 'classification', value: 'NON-STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2255188f-d582-4516-8c08-835cfe1d68c2')
            column(name: 'df_usage_batch_uid', value: '73027b25-f269-4bec-a8ea-b126431eedbe')
            column(name: 'wr_wrk_inst', value: '12318778798')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'UNCLASSIFIED')
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
            column(name: 'reported_value', value: '2500') // B-55836 remove
            column(name: 'gross_amount', value: '35000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2255188f-d582-4516-8c08-835cfe1d68c2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '2500')
        }

        rollback ""
    }

    changeSet(id: '2019-03-07-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testInsertNtsUsages')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'name', value: 'Archived scenario')
            column(name: 'status_ind', value: 'ARCHIVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'name', value: 'Archived batch')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '15000.00')
        }

        // included
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'bc0fe9bc-9b24-4324-b624-eed0d9773e19')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2016-11-03') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000') // B-55836 remove
            column(name: 'gross_amount', value: '1176.9160')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '9873214')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '63b3da58-ac6b-4946-bd67-37a251769467')
            column(name: 'comment', value: 'for nts batch')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bc0fe9bc-9b24-4324-b624-eed0d9773e19')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '3000')
        }

        // included
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '5b8c2754-2f63-425a-a95f-dbd744e815fc')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2016-11-03') // B-55836 remove
            column(name: 'market', value: 'Bus') // B-55836 remove
            column(name: 'market_period_from', value: '2016') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000') // B-55836 remove
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '9873214')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '4c56f661-5bef-4c82-8526-fd987b5455f8')
            column(name: 'comment', value: 'for nts batch')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5b8c2754-2f63-425a-a95f-dbd744e815fc')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: '2016')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '3000')
        }

        // included unclassified
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'f06de87a-511e-46ae-88a8-fc9778efc194')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '105062654')
            column(name: 'work_title', value: 'Our fathers lies')
            column(name: 'system_title', value: 'Our fathers lies')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2016-11-03') // B-55836 remove
            column(name: 'market', value: 'Bus') // B-55836 remove
            column(name: 'market_period_from', value: '2014') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000') // B-55836 remove
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '9873214')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '4c56f661-5bef-4c82-8526-fd987b5455f8')
            column(name: 'comment', value: 'for nts batch')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f06de87a-511e-46ae-88a8-fc9778efc194')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: '2014')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '3000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '1d182000-4c91-11e9-b475-0800200c9a66')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'classification', value: 'NON-STM')
        }

        // excluded by market_period_to
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '33113b79-791a-4aa9-b192-12b292c32823')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2016-11-03') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2014') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000') // B-55836 remove
            column(name: 'gross_amount', value: '1200.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '9873214')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: 'bfa206bb-83fa-4c71-9a89-99f743018237')
            column(name: 'comment', value: 'for nts batch')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '33113b79-791a-4aa9-b192-12b292c32823')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2014')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '3000')
        }

        // excluded by market
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'a9fac1e1-5a34-416b-9ecb-f2615b24d1c1')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '632876487')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2016-11-03') // B-55836 remove
            column(name: 'market', value: 'Edu') // B-55836 remove
            column(name: 'market_period_from', value: '2016') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000') // B-55836 remove
            column(name: 'gross_amount', value: '1200.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
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
            column(name: 'market_period_from', value: '2016')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '3000')
        }

        // excluded as belletristic
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'b6fc6063-a0ea-4e4d-832d-b1cbc896963d')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '632876487')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2016-11-03') // B-55836 remove
            column(name: 'market', value: 'Bus') // B-55836 remove
            column(name: 'market_period_from', value: '2016') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000') // B-55836 remove
            column(name: 'gross_amount', value: '1200.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
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
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: '2016')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '3000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b9d0ea49-9e38-4bb0-a7e0-0ca299e3dcfa')
            column(name: 'name', value: 'NTS fund pool for testInsertNtsUsages')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2019-03-10')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fund_pool', value: '{"non_stm_minimum_amount":7,"stm_amount":700,"stm_minimum_amount":50,"non_stm_amount":5000,"fund_pool_period_from":2015,"markets":["Bus","Doc Del"],"fund_pool_period_to":2016}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '963b6497-1e95-4959-bf9a-416ad527d6a0')
            column(name: 'wr_wrk_inst', value: '632876487')
            column(name: 'classification', value: 'BELLETRISTIC')
        }

        rollback ""
    }

    changeSet(id: '2019-03-27-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testDeleteUnderMinimumCutoffAmountByBatchId')

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '5f0b074c-9de3-4972-bc87-44d95506fa6c')
            column(name: 'wr_wrk_inst', value: '122827635')
            column(name: 'classification', value: 'STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'e439f866-891e-4cf4-9237-8dfdfcf02104')
            column(name: 'wr_wrk_inst', value: '135632553')
            column(name: 'classification', value: 'NON-STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '74901845-ac06-44ab-9b79-9dc9622e74de')
            column(name: 'name', value: 'NTS Batch with Under Minimum usages')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0f86df24-39a0-4420-8e9b-327713ddd2b9')
            column(name: 'df_usage_batch_uid', value: '74901845-ac06-44ab-9b79-9dc9622e74de')
            column(name: 'wr_wrk_inst', value: '122827635')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
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
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '0f86df24-39a0-4420-8e9b-327713ddd2b9')
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
            column(name: 'df_usage_uid', value: 'a86308b1-7f89-474b-9390-fc926c5b218b')
            column(name: 'df_usage_batch_uid', value: '74901845-ac06-44ab-9b79-9dc9622e74de')
            column(name: 'wr_wrk_inst', value: '122827635')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
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
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a86308b1-7f89-474b-9390-fc926c5b218b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '1000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'af1f25e5-75ca-463f-8c9f-1f1e4b92f699')
            column(name: 'df_usage_batch_uid', value: '74901845-ac06-44ab-9b79-9dc9622e74de')
            column(name: 'wr_wrk_inst', value: '135632553')
            column(name: 'work_title', value: 'Business')
            column(name: 'system_title', value: 'Business')
            column(name: 'rh_account_number', value: '1000002859')
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
            column(name: 'reported_value', value: '3000') // B-55836 remove
            column(name: 'gross_amount', value: '30.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'af1f25e5-75ca-463f-8c9f-1f1e4b92f699')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '3000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f9ddb072-a411-443b-89ca-1bb5a63425a4')
            column(name: 'df_usage_batch_uid', value: '74901845-ac06-44ab-9b79-9dc9622e74de')
            column(name: 'wr_wrk_inst', value: '146547417')
            column(name: 'work_title', value: 'Test de aptitudes profesionales')
            column(name: 'system_title', value: 'Test de aptitudes profesionales')
            column(name: 'rh_account_number', value: '1000002859')
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
            column(name: 'reported_value', value: '2000') // B-55836 remove
            column(name: 'gross_amount', value: '2.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f9ddb072-a411-443b-89ca-1bb5a63425a4')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '2000')
        }

        rollback ""
    }

    changeSet(id: '2019-03-29-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testDeleteFromAdditionalFund')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0f00e96b-eed7-4f26-8004-3370ec30da45')
            column(name: 'name', value: 'FAS batch test delete additional fund')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: '2022')
            column(name: 'gross_amount', value: '10.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'name', value: 'Test fund')
            column(name: 'comment', value: 'test comment')
            column(name: 'withdrawn_amount', value: '10.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ba95f0b3-dc94-4925-96f2-93d05db9c469')
            column(name: 'df_usage_batch_uid', value: '0f00e96b-eed7-4f26-8004-3370ec30da45')
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '1')
            column(name: 'reported_value', value: '10') // B-55836 remove
            column(name: 'gross_amount', value: '10.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ba95f0b3-dc94-4925-96f2-93d05db9c469')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '10')
        }
    }

    changeSet(id: '2019-04-22-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testDeleteFromNtsScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '224180f9-0406-4181-9ad2-23e3804298aa')
            column(name: 'name', value: 'NTS Batch associated with Scenario')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
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
            column(name: 'wr_wrk_inst', value: '122267677')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '1')
            column(name: 'reported_value', value: '900') // B-55836 remove
            column(name: 'gross_amount', value: '900.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'service_fee_amount', value: '288.00')
            column(name: 'net_amount', value: '612.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c09aa888-85a5-4377-8c7a-85d84d255b5a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '45445974-5bee-477a-858b-e9e8c1a642b8')
            column(name: 'df_usage_batch_uid', value: '224180f9-0406-4181-9ad2-23e3804298aa')
            column(name: 'wr_wrk_inst', value: '642267671')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'NTS_EXCLUDED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '1')
            column(name: 'reported_value', value: '100') // B-55836 remove
            column(name: 'gross_amount', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '45445974-5bee-477a-858b-e9e8c1a642b8')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '30a9a53f-db64-4af3-9616-1e40edcef489')
            column(name: 'wr_wrk_inst', value: '642267671')
            column(name: 'classification', value: 'STM')
        }
    }

    changeSet(id: '2019-04-25-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for testUpdateUsagesStatusToUnclassified")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '83027b25-f269-4bec-a8ea-b126431eedbf')
            column(name: 'name', value: 'CADRA_10Dec16')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, "fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f6cb5b07-45c0-4188-9da3-920046eec4c0')
            column(name: 'df_usage_batch_uid', value: '83027b25-f269-4bec-a8ea-b126431eedbf')
            column(name: 'wr_wrk_inst', value: '122267671')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '0804709114')
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'reported_value', value: '296.72') // B-55836 remove
            column(name: 'gross_amount', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f6cb5b07-45c0-4188-9da3-920046eec4c0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '296.72')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f255188f-d582-4516-8c08-835cfe1d68c3')
            column(name: 'df_usage_batch_uid', value: '83027b25-f269-4bec-a8ea-b126431eedbf')
            column(name: 'wr_wrk_inst', value: '159526526')
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '10457143')
            column(name: 'publication_date', value: '2019-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'reported_value', value: '162.41') // B-55836 remove
            column(name: 'gross_amount', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f255188f-d582-4516-8c08-835cfe1d68c3')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '162.41')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'd78924f5-baf1-4c70-aa80-3c49c193b9f0')
            column(name: 'wr_wrk_inst', value: '122267671')
            column(name: 'classification', value: 'STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'a78924f5-4af1-7c70-1a80-5c49c193b9f1')
            column(name: 'wr_wrk_inst', value: '159526526')
            column(name: 'classification', value: 'NON-STM')
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
            column(name: 'name', value: 'NTS Batch')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bbbd64db-2668-499a-9d18-be8b3f87fbf5')
            column(name: 'df_usage_batch_uid', value: 'b614f8a0-9271-4cae-8a26-b39a83cb7c46')
            column(name: 'df_scenario_uid', value: 'dd4fca1d-eac8-4b76-85e4-121b7971d049')
            column(name: 'wr_wrk_inst', value: '122267672')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '0804709114')
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'reported_value', value: '296.72') // B-55836 remove
            column(name: 'gross_amount', value: '256.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bbbd64db-2668-499a-9d18-be8b3f87fbf5')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '296.72')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '83a26087-a3b3-43ca-8b34-c66134fb6edf')
            column(name: 'df_usage_batch_uid', value: 'b614f8a0-9271-4cae-8a26-b39a83cb7c46')
            column(name: 'df_scenario_uid', value: 'dd4fca1d-eac8-4b76-85e4-121b7971d049')
            column(name: 'wr_wrk_inst', value: '159526527')
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '10457143')
            column(name: 'publication_date', value: '2019-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2015') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'reported_value', value: '162.41') // B-55836 remove
            column(name: 'gross_amount', value: '1452.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '83a26087-a3b3-43ca-8b34-c66134fb6edf')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '162.41')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6cad4cf2-6a19-4e5b-b4e0-f2f7a62ff91c')
            column(name: 'df_usage_batch_uid', value: 'b614f8a0-9271-4cae-8a26-b39a83cb7c46')
            column(name: 'df_scenario_uid', value: 'dd4fca1d-eac8-4b76-85e4-121b7971d049')
            column(name: 'wr_wrk_inst', value: '569526592')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '10457143')
            column(name: 'publication_date', value: '2019-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2015') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'reported_value', value: '86.41') // B-55836 remove
            column(name: 'gross_amount', value: '359.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6cad4cf2-6a19-4e5b-b4e0-f2f7a62ff91c')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '86.41')
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
            column(name: 'wr_wrk_inst', value: '122267672')
            column(name: 'classification', value: 'BELLETRISTIC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '092e0fa4-d0ec-4d93-b700-7d5cb096a942')
            column(name: 'wr_wrk_inst', value: '159526527')
            column(name: 'classification', value: 'STM')
        }
    }

    changeSet(id: '2019-05-31-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testUpdateNtsWithdrawnUsagesAndGetIds')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '1d13b04b-4237-4ab7-ab17-4f23e7fb5e94')
            column(name: 'name', value: 'FAS batch test update NTS Withdrawn usages')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: '2022')
            column(name: 'gross_amount', value: '300.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2f2ca785-a7d3-4a7f-abd9-2bad80ac71dd')
            column(name: 'df_usage_batch_uid', value: '1d13b04b-4237-4ab7-ab17-4f23e7fb5e94')
            column(name: 'wr_wrk_inst', value: '122267672')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '0804709114')
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'reported_value', value: '25.00') // B-55836 remove
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2f2ca785-a7d3-4a7f-abd9-2bad80ac71dd')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cbd6768d-a424-476e-b502-a832d9dbe85e')
            column(name: 'df_usage_batch_uid', value: '1d13b04b-4237-4ab7-ab17-4f23e7fb5e94')
            column(name: 'wr_wrk_inst', value: '122267672')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '0804709114')
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'reported_value', value: '25.00') // B-55836 remove
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'cbd6768d-a424-476e-b502-a832d9dbe85e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e2834925-ede5-4796-a30b-05770a6f04be')
            column(name: 'df_usage_batch_uid', value: '1d13b04b-4237-4ab7-ab17-4f23e7fb5e94')
            column(name: 'wr_wrk_inst', value: '159526527')
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '10457143')
            column(name: 'publication_date', value: '2019-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2015') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'reported_value', value: '150.01') // B-55836 remove
            column(name: 'gross_amount', value: '150.01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e2834925-ede5-4796-a30b-05770a6f04be')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '150.01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd5e3c637-155a-4c05-999a-31a07e335491')
            column(name: 'df_usage_batch_uid', value: '1d13b04b-4237-4ab7-ab17-4f23e7fb5e94')
            column(name: 'wr_wrk_inst', value: '569526592')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '10457143')
            column(name: 'publication_date', value: '2019-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2015') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'reported_value', value: '99.99') // B-55836 remove
            column(name: 'gross_amount', value: '99.99')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd5e3c637-155a-4c05-999a-31a07e335491')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '99.99')
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
            column(name: 'name', value: 'NTS Batch')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8a80a2e7-4758-4e43-ae42-e8b29802a210')
            column(name: 'df_usage_batch_uid', value: '2167779f-cbae-4b4b-adc6-fb95aeb3c58d')
            column(name: 'df_scenario_uid', value: 'd7e9bae8-6b10-4675-9668-8e3605a47dad')
            column(name: 'wr_wrk_inst', value: '122267672')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '0804709114')
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'reported_value', value: '296.72') // B-55836 remove
            column(name: 'gross_amount', value: '256.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8a80a2e7-4758-4e43-ae42-e8b29802a210')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '296.72')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '085268cd-7a0c-414e-8b28-2acb299d9698')
            column(name: 'df_usage_batch_uid', value: '2167779f-cbae-4b4b-adc6-fb95aeb3c58d')
            column(name: 'df_scenario_uid', value: 'd7e9bae8-6b10-4675-9668-8e3605a47dad')
            column(name: 'wr_wrk_inst', value: '159526527')
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '10457143')
            column(name: 'publication_date', value: '2019-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2015') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'reported_value', value: '162.41') // B-55836 remove
            column(name: 'gross_amount', value: '1452.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '085268cd-7a0c-414e-8b28-2acb299d9698')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '162.41')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bfc9e375-c489-4600-9308-daa101eed97c')
            column(name: 'df_usage_batch_uid', value: '2167779f-cbae-4b4b-adc6-fb95aeb3c58d')
            column(name: 'df_scenario_uid', value: 'd7e9bae8-6b10-4675-9668-8e3605a47dad')
            column(name: 'wr_wrk_inst', value: '159526527')
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '10457143')
            column(name: 'publication_date', value: '2019-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2015') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'reported_value', value: '16.24') // B-55836 remove
            column(name: 'gross_amount', value: '145.20')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bfc9e375-c489-4600-9308-daa101eed97c')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '16.24')
        }
    }

    changeSet(id: '2019-06-13-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testApplyPostServiceFeeAmount')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'fd09c318-75e8-4f4d-b384-8c83e8033e25')
            column(name: 'name', value: 'NTS testApplyPostServiceFeeAmount')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 30, ' +
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
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'wr_wrk_inst', value: '471137967')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '1')
            column(name: 'reported_value', value: '33') // B-55836 remove
            column(name: 'gross_amount', value: '33')
            column(name: 'service_fee', value: '0.16')
            column(name: 'service_fee_amount', value: '5.28')
            column(name: 'net_amount', value: '27.72')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7778a37d-6184-42c1-8e23-5841837c5411')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '33')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '54247c55-bf6b-4ad6-9369-fb4baea6b19b')
            column(name: 'df_usage_batch_uid', value: 'fd09c318-75e8-4f4d-b384-8c83e8033e25')
            column(name: "df_scenario_uid", value: "c4bc09c1-eb9b-41f3-ac93-9cd088dff408")
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'wr_wrk_inst', value: '471137967')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '1')
            column(name: 'reported_value', value: '66') // B-55836 remove
            column(name: 'gross_amount', value: '66')
            column(name: 'service_fee', value: '0.32')
            column(name: 'service_fee_amount', value: '21.12')
            column(name: 'net_amount', value: '44.88')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '54247c55-bf6b-4ad6-9369-fb4baea6b19b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '66')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ade68eac-0d79-4d23-861b-499a0c6e91d3')
            column(name: 'df_usage_batch_uid', value: 'fd09c318-75e8-4f4d-b384-8c83e8033e25')
            column(name: 'rh_account_number', value: '7000896777')
            column(name: 'wr_wrk_inst', value: '471137967')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'NTS_EXCLUDED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Univ') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana') // B-55836 remove
            column(name: 'number_of_copies', value: '1')
            column(name: 'reported_value', value: '11') // B-55836 remove
            column(name: 'gross_amount', value: '11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ade68eac-0d79-4d23-861b-499a0c6e91d3')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'e920c634-f59d-4d9c-82bd-275af99132b6')
            column(name: 'wr_wrk_inst', value: '471137967')
            column(name: 'classification', value: 'STM')
        }
    }

    changeSet(id: '2019-08-05-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment("Insert archived NTS usage for testFindForAuditArchivedNts")

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'ddb556a0-991c-45a7-ade9-32959b676ae0')
            column(name: 'name', value: 'Test NTS Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Test NTS Scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'fb297d5d-4d46-4492-93b0-cd6e02b8ce8d')
            column(name: 'df_scenario_uid', value: 'ddb556a0-991c-45a7-ade9-32959b676ae0')
            column(name: 'wr_wrk_inst', value: '151811999')
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'NTS')
            column(name: 'reported_value', value: '0.00') // B-55836 remove
            column(name: 'gross_amount', value: '6000.00')
            column(name: 'net_amount', value: '4080.00')
            column(name: 'service_fee_amount', value: '1920.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'ccc_event_id', value: '53578')
            column(name: 'check_number', value: '578671')
            column(name: 'check_date', value: '2019-08-05 05:14:00-04:00')
            column(name: 'distribution_name', value: 'FDA April 19')
            column(name: 'distribution_date', value: '2019-08-05 05:14:00-04:00')
            column(name: 'period_end_date', value: '2019-08-05 05:14:00-04:00')
            column(name: 'lm_detail_id', value: '6e727232-15b6-4b98-8b97-a8536cd8b24c')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'fb297d5d-4d46-4492-93b0-cd6e02b8ce8d')
            column(name: 'reported_value', value: '0.00')
        }
    }

    changeSet(id: '2019-10-16-00', author: 'Uladzislau Shalamitski <ushalmitski@copyright.com>') {
        comment("Insert test data for testFindPayeeTotalHoldersByScenarioId")

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'FAS2 scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'name', value: 'FAS2 batch')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'fiscal_year', value: '2020')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '30787462-b66a-4679-a2bc-1b89abd6ea66')
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00') // B-55836 remove
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '30787462-b66a-4679-a2bc-1b89abd6ea66')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5e3b4c6c-fe20-4a16-bbb6-9dc61b6c1eed')
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'wr_wrk_inst', value: '471137967')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2008902112317645XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00') // B-55836 remove
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '84.00')
            column(name: 'service_fee_amount', value: '16.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'is_rh_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5e3b4c6c-fe20-4a16-bbb6-9dc61b6c1eed')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '77dce0ba-3a7c-4162-8264-69d1b973e7ac')
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'wr_wrk_inst', value: '12318778798')
            column(name: 'work_title', value: 'Future of medicine')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'payee_account_number', value: '7000813806')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '3008902112317645XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00') // B-55836 remove
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '77dce0ba-3a7c-4162-8264-69d1b973e7ac')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
        }
    }

    changeSet(id: '2019-10-17-00', author: 'Uladzislau Shalamitski <ushalmitski@copyright.com>') {
        comment("Insert test data for testDeleteFromScenarioByPayees")

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'edbcc8b3-8fa4-4c58-9244-a91627cac7a9')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'FAS2 scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '32daf15a-eb82-4b9e-862d-c647e72f76ca')
            column(name: 'name', value: 'FAS2 batch')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'fiscal_year', value: '2020')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7234feb4-a59e-483b-985a-e8de2e3eb190')
            column(name: 'df_usage_batch_uid', value: '32daf15a-eb82-4b9e-862d-c647e72f76ca')
            column(name: 'df_scenario_uid', value: 'edbcc8b3-8fa4-4c58-9244-a91627cac7a9')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00') // B-55836 remove
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7234feb4-a59e-483b-985a-e8de2e3eb190')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '582c86e2-213e-48ad-a885-f9ff49d48a69')
            column(name: 'df_usage_batch_uid', value: '32daf15a-eb82-4b9e-862d-c647e72f76ca')
            column(name: 'df_scenario_uid', value: 'edbcc8b3-8fa4-4c58-9244-a91627cac7a9')
            column(name: 'wr_wrk_inst', value: '773904752')
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'payee_account_number', value: '7000813806')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00') // B-55836 remove
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '84.00')
            column(name: 'service_fee_amount', value: '16.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'is_rh_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '582c86e2-213e-48ad-a885-f9ff49d48a69')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '730d7964-f399-4971-9403-dbedc9d7a180')
            column(name: 'df_usage_batch_uid', value: '32daf15a-eb82-4b9e-862d-c647e72f76ca')
            column(name: 'df_scenario_uid', value: 'edbcc8b3-8fa4-4c58-9244-a91627cac7a9')
            column(name: 'wr_wrk_inst', value: '12318778798')
            column(name: 'work_title', value: 'Future of medicine')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'payee_account_number', value: '7000813806')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '3008902112317645XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00') // B-55836 remove
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '730d7964-f399-4971-9403-dbedc9d7a180')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
        }
    }

    changeSet(id: '2019-10-17-01', author: 'Uladzislau Shalamitski <ushalmitski@copyright.com>') {
        comment("Insert test data for testRedisignateToNtsWithdrawnByPayees")

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '767a2647-7e6e-4479-b381-e642de480863')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'FAS2 scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '452baaea-5c52-417c-9ee6-70ab014624cf')
            column(name: 'name', value: 'FAS2 batch')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'fiscal_year', value: '2020')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '209a960f-5896-43da-b020-fc52981b9633')
            column(name: 'df_usage_batch_uid', value: '452baaea-5c52-417c-9ee6-70ab014624cf')
            column(name: 'df_scenario_uid', value: '767a2647-7e6e-4479-b381-e642de480863')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00') // B-55836 remove
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '209a960f-5896-43da-b020-fc52981b9633')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1ae671ca-ed5a-4d92-8ab6-a10a53d9884a')
            column(name: 'df_usage_batch_uid', value: '452baaea-5c52-417c-9ee6-70ab014624cf')
            column(name: 'df_scenario_uid', value: '767a2647-7e6e-4479-b381-e642de480863')
            column(name: 'wr_wrk_inst', value: '773904752')
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'payee_account_number', value: '7000813806')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00') // B-55836 remove
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '84.00')
            column(name: 'service_fee_amount', value: '16.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'is_rh_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '1ae671ca-ed5a-4d92-8ab6-a10a53d9884a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '72f6abdb-c82d-4cee-aadf-570942cf0093')
            column(name: 'df_usage_batch_uid', value: '452baaea-5c52-417c-9ee6-70ab014624cf')
            column(name: 'df_scenario_uid', value: '767a2647-7e6e-4479-b381-e642de480863')
            column(name: 'wr_wrk_inst', value: '12318778798')
            column(name: 'work_title', value: 'Future of medicine')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'payee_account_number', value: '7000813806')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '3008902112317645XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2019') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00') // B-55836 remove
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '72f6abdb-c82d-4cee-aadf-570942cf0093')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
        }
    }
}
