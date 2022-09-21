databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2017-11-30-00', author: 'Aliaksandra_Bayanouskaya <abayanouskaya@copyright.com>') {
        comment('Inserting test data for testFindReferencedUsagesCountByIds')

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
            column(name: 'df_usage_batch_uid', value: '8a901f85-0949-40b8-9176-378d1a61afb8')
            column(name: 'name', value: 'FAS batch 1')
            column(name: 'rro_account_number', value: 2000017011)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3cf274c5-8eac-4d4a-96be-5921ae026840')
            column(name: 'df_usage_batch_uid', value: '8a901f85-0949-40b8-9176-378d1a61afb8')
            column(name: 'product_family', value: 'NTS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: 1000009523)
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3cf274c5-8eac-4d4a-96be-5921ae026840')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f5eb98ce-ab59-44c8-9a50-1afea2b5ae15')
            column(name: 'df_usage_batch_uid', value: '8a901f85-0949-40b8-9176-378d1a61afb8')
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

        rollback {
            dbRollback
        }
    }
}
