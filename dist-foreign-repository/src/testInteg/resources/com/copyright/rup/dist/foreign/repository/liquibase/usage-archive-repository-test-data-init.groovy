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
            column(name: 'df_rightsholder_uid', value: 'a0663c51-87c6-4d03-8683-7640f12ae8c1')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: '2000017010')
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
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
            column(name: 'df_usage_batch_uid', value: 'd3523023-0c2a-4486-aab9-e425fddb271e')
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
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'The description of scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e27551ed-3f69-4e08-9e4f-8ac03f67595f')
            column(name: 'name', value: 'Scenario name 2')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'The description of scenario 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '8a06905f-37ae-4e1f-8550-245277f8165c')
            column(name: 'df_usage_batch_uid', value: 'd3523023-0c2a-4486-aab9-e425fddb271e')
            column(name: 'df_scenario_uid', value: 'e27551ed-3f69-4e08-9e4f-8ac03f67595f')
            column(name: 'wr_wrk_inst', value: '244614835')
            column(name: 'work_title', value: '15th International Conference on Environmental Degradation of Materials in Nuclear Power Systems Water Reactors')
            column(name: 'system_title', value: '15th International Conference on Environmental Degradation of Materials in Nuclear Power Systems Water Reactors')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'First-Week Protein and Energy Intakes Are Associated With 18-Month Developmental Outcomes in Extremely Low Birth Weight Infants')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'publisher', value: 'John Wiley & Sons')
            column(name: 'publication_date', value: '2011-05-10')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'number_of_copies', value: '1600')
            column(name: 'reported_value', value: '1560')
            column(name: 'gross_amount', value: '35000.00')
            column(name: 'net_amount', value: '23800.00')
            column(name: 'service_fee_amount', value: '11200.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '3ab5e80b-89c0-4d78-9675-54c7ab284450')
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'payee_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '2500')
            column(name: 'gross_amount', value: '35000.00')
            column(name: 'net_amount', value: '23800.00')
            column(name: 'service_fee_amount', value: '11200.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'comment', value: '180382914')
            column(name: 'standard_number_type', value: 'VALISSN')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '5c5f8c1c-1418-4cfd-8685-9212f4c421d1')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'wr_wrk_inst', value: '345870577')
            column(name: 'work_title', value: '10 Years Plant Molecular Biology')
            column(name: 'system_title', value: '10 Years Plant Molecular Biology')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'payee_account_number', value: '1000005413')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'publisher', value: 'McGraw-Hill')
            column(name: 'publication_date', value: '2009-12-31')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: 'Mirjam H. Hüberli')
            column(name: 'number_of_copies', value: '2630')
            column(name: 'reported_value', value: '1280.00')
            column(name: 'gross_amount', value: '2125.24')
            column(name: 'net_amount', value: '1445.1632')
            column(name: 'service_fee_amount', value: '680.0768')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'standard_number_type', value: 'VALISBN10');
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '2f660585-35a1-48a5-a506-904c725cda11')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '9900')
            column(name: 'gross_amount', value: '67874.80')
            column(name: 'net_amount', value: '46154.80')
            column(name: 'service_fee_amount', value: '21720.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'standard_number_type', value: 'VALISBN10')
        }

        //testUpdatePaidInfo
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '98caae9b-2f20-4c6d-b2af-3190a1115c48')
            column(name: 'name', value: 'Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b58a3ec8-4294-47ae-a12a-83cfe748909b')
            column(name: 'name', value: 'Paid batch')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '185.60')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '7241b7e0-6ab8-4483-896d-fd485c574293')
            column(name: 'df_usage_batch_uid', value: 'b58a3ec8-4294-47ae-a12a-83cfe748909b')
            column(name: 'df_scenario_uid', value: '98caae9b-2f20-4c6d-b2af-3190a1115c48')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000')
            column(name: 'gross_amount', value: '92.80')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '12.80')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '32db57d1-9140-4912-af27-656c6956752d')
            column(name: 'df_usage_batch_uid', value: 'b58a3ec8-4294-47ae-a12a-83cfe748909b')
            column(name: 'df_scenario_uid', value: '98caae9b-2f20-4c6d-b2af-3190a1115c48')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '7000813806')
            column(name: 'payee_account_number', value: '7000813806')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000')
            column(name: 'gross_amount', value: '92.80')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '12.80')
            column(name: 'service_fee', value: '0.16000')
        }

        //testFindByIdsAndStatus
        //testFindPaidIds
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '40c4d9ad-2c2f-4041-8bbf-630dc69dfaac')
            column(name: 'name', value: 'Paid Scenario 1')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f132d857-d66a-4b9f-acdc-b1a64c8db718')
            column(name: 'name', value: 'Paid batch')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '3f8ce825-6514-4307-a118-3ec89187bef3')
            column(name: 'df_usage_batch_uid', value: 'f132d857-d66a-4b9f-acdc-b1a64c8db718')
            column(name: 'df_scenario_uid', value: '40c4d9ad-2c2f-4041-8bbf-630dc69dfaac')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '5963a9c2-b639-468c-a4c1-02101a4597c6')
        }

        //testDeleteByBatchId
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '812792e9-1e32-4bf4-8881-a0bd2d9d036d')
            column(name: 'name', value: 'Archived scenario')
            column(name: 'status_ind', value: 'ARCHIVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f043c988-3344-4c0f-bce9-120af0027d09')
            column(name: 'name', value: 'Archived batch')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'product_family', value: 'FAS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '15000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '5f90f7d7-566f-402a-975b-d54466862704')
            column(name: 'df_usage_batch_uid', value: 'f043c988-3344-4c0f-bce9-120af0027d09')
            column(name: 'df_scenario_uid', value: '812792e9-1e32-4bf4-8881-a0bd2d9d036d')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Not Suitable For fund pool')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '30855870-09df-4341-bd88-2a92c5470d60')
        }

        rollback ""
    }

    changeSet(id: '2019-03-07-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testFindForNtsBatchCount')

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
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000')
            column(name: 'gross_amount', value: '1176.9160')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '63b3da58-ac6b-4946-bd67-37a251769467')
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
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2014')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: 'bfa206bb-83fa-4c71-9a89-99f743018237')
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
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: '2016')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '4c56f661-5bef-4c82-8526-fd987b5455f8')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '1d182000-4c91-11e9-b475-0800200c9a66')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'classification', value: 'NON-STM')
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
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: '2016')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: 'b8688ed5-3986-4f41-8a16-d33cd44e2ab5')
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
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: '2016')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '83bb6666-7484-49d6-8ed1-c653286590f3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '963b6497-1e95-4959-bf9a-416ad527d6a0')
            column(name: 'wr_wrk_inst', value: '632876487')
            column(name: 'classification', value: 'BELLETRISTIC')
        }

        rollback ""
    }

    changeSet(id: '2019-06-18-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testMoveFundToArchive')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3d587554-7564-4db9-a67a-8f2b35fa673d')
            column(name: 'name', value: 'FAS batch')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2021')
            column(name: 'gross_amount', value: '1500.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9abfd0a0-2779-4321-af07-ebabe22627a0')
            column(name: 'name', value: 'NTS batch')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2021')
            column(name: 'gross_amount', value: '0.00')
            column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 0, "stm_minimum_amount": 0, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 0}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'f00b5ee9-c75e-499d-aa41-ae56b3d68211')
            column(name: 'name', value: 'Pre-Service fee fund')
            column(name: 'comment', value: 'test comment')
            column(name: 'withdrawn_amount', value: '199.98')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '79d47e6e-2e84-4e9a-b92c-ab8d745935ef')
            column(name: 'name', value: 'NTS distribution batch')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 300.00, "pre_service_fee_fund_uid": "f00b5ee9-c75e-499d-aa41-ae56b3d68211"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '677e1740-c791-4929-87f9-e7fc68dd4699')
            column(name: 'df_usage_batch_uid', value: '3d587554-7564-4db9-a67a-8f2b35fa673d')
            column(name: 'df_fund_pool_uid', value: 'f00b5ee9-c75e-499d-aa41-ae56b3d68211')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'number_of_copies', value: '1')
            column(name: 'reported_value', value: '99.99')
            column(name: 'gross_amount', value: '99.99')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2a868c86-a639-400f-b407-0602dd7ec8df')
            column(name: 'df_usage_batch_uid', value: '3d587554-7564-4db9-a67a-8f2b35fa673d')
            column(name: 'df_fund_pool_uid', value: 'f00b5ee9-c75e-499d-aa41-ae56b3d68211')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'number_of_copies', value: '1')
            column(name: 'reported_value', value: '99.99')
            column(name: 'gross_amount', value: '99.99')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9abfd0a0-2779-4321-af07-ebabe22627a0')
            column(name: 'df_usage_batch_uid', value: '3d587554-7564-4db9-a67a-8f2b35fa673d')
            column(name: 'df_scenario_uid', value: '79d47e6e-2e84-4e9a-b92c-ab8d745935ef')
            column(name: 'wr_wrk_inst', value: '632876487')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '2500')
            column(name: 'gross_amount', value: '2500.00')
        }
    }
}
