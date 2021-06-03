databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2017-12-01-00', author: 'Aliaksandra Bayanouskaya <abayanouskaya@copyright.com>') {
        comment('Inserting test data for testCreateFasScenario and testCreateFasScenarioNoRollupsNoPreferences')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '4c014547-06f3-4840-94ff-6249730d537d')
            column(name: 'name', value: 'Test Scenario for exclude')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '0003cc8d-2e67-49aa-b6f0-e40efdf2cc90')
            column(name: 'df_scenario_uid', value: '4c014547-06f3-4840-94ff-6249730d537d')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'name', value: 'CADRA_27Oct17')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 6)
        }

        // usage won't be added to new scenario during test run
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'fcdaea01-2439-4c51-b3e2-23649cf710c7')
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'df_scenario_uid', value: '4c014547-06f3-4840-94ff-6249730d537d')
            column(name: 'wr_wrk_inst', value: 471137470)
            column(name: 'work_title', value: 'Sunbeams')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'payee_account_number', value: 1000003821)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 90.63)
            column(name: 'service_fee_amount', value: 29.00)
            column(name: 'net_amount', value: 61.63)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'fcdaea01-2439-4c51-b3e2-23649cf710c7')
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
            column(name: 'df_usage_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'work_title', value: 'CHICKEN BREAST ON GRILL WITH FLAMES')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 8972.04)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
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
            column(name: 'df_usage_uid', value: 'cf38d390-11bb-4af7-9685-e034c9c32fb6')
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 25)
            column(name: 'gross_amount', value: 4531.33)
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
            column(name: 'reported_value', value: 5000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd0816728-4726-483d-91ff-8f24fa605e01')
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: 1000001820)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 13593.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd0816728-4726-483d-91ff-8f24fa605e01')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 15000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0e49fd89-f094-4023-b729-afe240272ebe')
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'wr_wrk_inst', value: 122235139)
            column(name: 'work_title', value: 'BOWL OF BERRIES WITH SUGAR COOKIES')
            column(name: 'rh_account_number', value: 1000024497)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 2718.80)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '0e49fd89-f094-4023-b729-afe240272ebe')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cbda7c0d-c455-4d9f-b097-89db8d933264')
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'wr_wrk_inst', value: 471137469)
            column(name: 'work_title', value: 'Solar Cells')
            column(name: 'rh_account_number', value: 1000002562)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 5093.22)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'cbda7c0d-c455-4d9f-b097-89db8d933264')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 5620.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '756299b5-02ce-4f76-b0bc-ee2571cf906e')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'name', value: 'INSTITUTE OF FILM & TELEVISION STUDIES')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '019acfde-91be-43aa-8871-6305642bcb2c')
            column(name: 'rh_account_number', value: 1000024497)
            column(name: 'name', value: 'White Horse Press')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '038bf4aa-b6cc-430a-9b32-655954d95278')
            column(name: 'rh_account_number', value: 1000002562)
            column(name: 'name', value: 'Pall Mall Press/Phaidon Press')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '00d4ae90-5fe7-47bf-ace1-781c8d76d4da')
            column(name: 'rh_account_number', value: 1000001820)
            column(name: 'name', value: 'Delhi Medical Assn')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05dc9217-26d4-46ca-aa6e-18572591f3c8')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'name', value: 'Abbey Publications, Inc. [L]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d14')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d15')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        rollback ""
    }

    changeSet(id: '2018-03-15-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment("Insert test data for testCreateClaScenario")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'ce0ca941-1e16-4a3b-a991-b596189b4f22')
            column(name: 'name', value: 'CLA_27Oct17')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8fc81e08-3611-4697-8059-6c970ee5d643')
            column(name: 'df_usage_batch_uid', value: 'ce0ca941-1e16-4a3b-a991-b596189b4f22')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'work_title', value: 'CHICKEN BREAST ON GRILL WITH FLAMES')
            column(name: 'rh_account_number', value: 2000133267)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 8972.04)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8fc81e08-3611-4697-8059-6c970ee5d643')
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
            column(name: 'df_usage_uid', value: '007aff49-831c-46ab-9528-2e043f7564e9')
            column(name: 'df_usage_batch_uid', value: 'ce0ca941-1e16-4a3b-a991-b596189b4f22')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 2000073957)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 25)
            column(name: 'gross_amount', value: 4531.33)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '007aff49-831c-46ab-9528-2e043f7564e9')
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
            column(name: 'df_usage_uid', value: '455681ae-a02d-4cb9-a881-fcdc46cc5585')
            column(name: 'df_usage_batch_uid', value: 'ce0ca941-1e16-4a3b-a991-b596189b4f22')
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: 7001508482)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 13593.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '455681ae-a02d-4cb9-a881-fcdc46cc5585')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 15000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ec5c39b5-4c16-40a7-b1c8-730320971f11')
            column(name: 'df_usage_batch_uid', value: 'ce0ca941-1e16-4a3b-a991-b596189b4f22')
            column(name: 'wr_wrk_inst', value: 122235139)
            column(name: 'work_title', value: 'BOWL OF BERRIES WITH SUGAR COOKIES')
            column(name: 'rh_account_number', value: 1000024950)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 2718.80)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ec5c39b5-4c16-40a7-b1c8-730320971f11')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3c3a3329-d64c-45a9-962c-f247e4bbf3b6')
            column(name: 'df_usage_batch_uid', value: 'ce0ca941-1e16-4a3b-a991-b596189b4f22')
            column(name: 'wr_wrk_inst', value: 471137469)
            column(name: 'work_title', value: 'Solar Cells')
            column(name: 'rh_account_number', value: 2000139286)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 5093.22)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3c3a3329-d64c-45a9-962c-f247e4bbf3b6')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 5620.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '60080587-a225-439c-81af-f016cb33aeac')
            column(name: 'rh_account_number', value: 2000133267)
            column(name: 'name', value: '101 Communications, Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b0e6b1f6-89e9-4767-b143-db0f49f32769')
            column(name: 'rh_account_number', value: 2000073957)
            column(name: 'name', value: '1st Contact Publishing')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'f366285a-ce46-48b0-96ee-cd35d62fb243')
            column(name: 'rh_account_number', value: 7001508482)
            column(name: 'name', value: '2000 BC Publishing Ltd')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '624dcf73-a30f-4381-b6aa-c86d17198bd5')
            column(name: 'rh_account_number', value: 1000024950)
            column(name: 'name', value: '2D Publications')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '37338ed1-7083-45e2-a96b-5872a7de3a98')
            column(name: 'rh_account_number', value: 2000139286)
            column(name: 'name', value: '2HC [T]')
        }

        rollback ""
    }

    changeSet(id: '2019-04-19-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for testCreateNtsScenario")

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'fc9ee4ed-519e-41c8-927b-92206b34c8cc')
            column(name: 'wr_wrk_inst', value: 135632563)
            column(name: 'classification', value: 'NON-STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '7c04aac5-ccc5-4abc-b84a-4077dd6ca9a8')
            column(name: 'wr_wrk_inst', value: 145632563)
            column(name: 'classification', value: 'STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '26282dbd-3463-58d7-c927-03d3458a656a')
            column(name: 'name', value: 'NTS usage batch')
            column(name: 'rro_account_number', value: 2000017004)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'initial_usages_count', value: 5)
            column(name: 'nts_fields', value: '{"markets": ["Bus,Univ,Doc Del"], "stm_amount": 10, "non_stm_amount": 20, "stm_minimum_amount": 30, "non_stm_minimum_amount": 40, "fund_pool_period_to": 2017, "fund_pool_period_from": 2017}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3d921c9c-8036-421a-ab05-39cc4d3c3b68')
            column(name: 'df_usage_batch_uid', value: '26282dbd-3463-58d7-c927-03d3458a656a')
            column(name: 'wr_wrk_inst', value: 135632563)
            column(name: 'work_title', value: 'Jazz chants')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10859241')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3d921c9c-8036-421a-ab05-39cc4d3c3b68')
            column(name: 'market', value: 'Bus,Univ,Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 59.30)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '669cf304-0921-41a2-85d5-c3905e77c696')
            column(name: 'df_usage_batch_uid', value: '26282dbd-3463-58d7-c927-03d3458a656a')
            column(name: 'wr_wrk_inst', value: 135632563)
            column(name: 'work_title', value: 'Jazz chants')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10859241')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '669cf304-0921-41a2-85d5-c3905e77c696')
            column(name: 'market', value: 'Bus,Univ,Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 19.20)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6402d5c8-ba80-4966-a7cc-34ba1fdc1d9c')
            column(name: 'df_usage_batch_uid', value: '26282dbd-3463-58d7-c927-03d3458a656a')
            column(name: 'wr_wrk_inst', value: 135632563)
            column(name: 'work_title', value: 'Jazz chants')
            column(name: 'rh_account_number', value: 1000001820)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10859241')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6402d5c8-ba80-4966-a7cc-34ba1fdc1d9c')
            column(name: 'market', value: 'Bus,Univ,Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 25.96)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '91813777-3dd4-4f5f-bb83-ca145866317d')
            column(name: 'df_usage_batch_uid', value: '26282dbd-3463-58d7-c927-03d3458a656a')
            column(name: 'wr_wrk_inst', value: 145632563)
            column(name: 'work_title', value: 'Wired')
            column(name: 'rh_account_number', value: 1000024497)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10859241')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '91813777-3dd4-4f5f-bb83-ca145866317d')
            column(name: 'market', value: 'Bus,Univ,Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 896.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e001c596-a66f-4fd3-b34c-5ef65a215d68')
            column(name: 'df_usage_batch_uid', value: '26282dbd-3463-58d7-c927-03d3458a656a')
            column(name: 'wr_wrk_inst', value: 145632563)
            column(name: 'work_title', value: 'Wired')
            column(name: 'rh_account_number', value: 1000002562)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10859241')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e001c596-a66f-4fd3-b34c-5ef65a215d68')
            column(name: 'market', value: 'Bus,Univ,Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 62.41)
        }

        rollback ""
    }

    changeSet(id: '2019-06-14-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("Insert Pre-Service fee fund for testCreateNtsScenario")

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'c7ca1ca1-7cd8-49cc-aaeb-ac53fe62d903')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'NTS fund pool')
            column(name: 'total_amount', value: '190.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c3b10f93-44f6-4569-9e3e-3423852ef47a')
            column(name: 'name', value: 'FAS_DISTRIBUTION_2019')
            column(name: 'rro_account_number', value: 2000017004)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 190.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd6fc0577-a6de-4425-a377-1e4f3315d49d')
            column(name: 'df_usage_batch_uid', value: 'c3b10f93-44f6-4569-9e3e-3423852ef47a')
            column(name: 'work_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 99.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd6fc0577-a6de-4425-a377-1e4f3315d49d')
            column(name: 'df_fund_pool_uid', value: 'c7ca1ca1-7cd8-49cc-aaeb-ac53fe62d903')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 99.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3d60f842-b0a0-4aee-8de5-da433d0da477')
            column(name: 'df_usage_batch_uid', value: 'c3b10f93-44f6-4569-9e3e-3423852ef47a')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 91.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3d60f842-b0a0-4aee-8de5-da433d0da477')
            column(name: 'df_fund_pool_uid', value: 'c7ca1ca1-7cd8-49cc-aaeb-ac53fe62d903')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 91.00)
        }
    }
}
