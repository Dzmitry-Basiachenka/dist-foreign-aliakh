databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-01-12-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for sending FAS Scenario to LM test')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '4c014547-06f3-4840-94ff-6249730d537d')
            column(name: 'name', value: 'FAS Scenario for sending to LM')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'name', value: 'FasOct17')
            column(name: 'rro_account_number', value: '1000002562')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '30000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'fcdaea01-2439-4c51-b3e2-23649cf710c7')
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'df_scenario_uid', value: '4c014547-06f3-4840-94ff-6249730d537d')
            column(name: 'wr_wrk_inst', value: '471137470')
            column(name: 'work_title', value: 'Sunbeams')
            column(name: 'system_title', value: 'Sunbeams')
            column(name: 'rh_account_number', value: '1000003821')
            column(name: 'payee_account_number', value: '1000003821')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00') // B-55836 remove
            column(name: 'gross_amount', value: '90.63')
            column(name: 'service_fee_amount', value: '29.00')
            column(name: 'net_amount', value: '61.63')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'fcdaea01-2439-4c51-b3e2-23649cf710c7')
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
            column(name: 'df_usage_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'df_scenario_uid', value: '4c014547-06f3-4840-94ff-6249730d537d')
            column(name: 'wr_wrk_inst', value: '122235134')
            column(name: 'work_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'system_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'rh_account_number', value: '7000429266')
            column(name: 'payee_account_number', value: '7000429266')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Nanette M. Schwann') // B-55836 remove
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '9900.00') // B-55836 remove
            column(name: 'gross_amount', value: '8972.04')
            column(name: 'net_amount', value: '2871.0528')
            column(name: 'service_fee_amount', value: '6100.9872')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: '9900.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cf38d390-11bb-4af7-9685-e034c9c32fb6')
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'df_scenario_uid', value: '4c014547-06f3-4840-94ff-6249730d537d')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '25')
            column(name: 'reported_value', value: '5000.00') // B-55836 remove
            column(name: 'gross_amount', value: '4531.33')
            column(name: 'net_amount', value: '1450.0256')
            column(name: 'service_fee_amount', value: '3081.3044')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'comment', value: 'usage from usages.csv')
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
            column(name: 'reported_value', value: '5000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd0816728-4726-483d-91ff-8f24fa605e01')
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'df_scenario_uid', value: '4c014547-06f3-4840-94ff-6249730d537d')
            column(name: 'wr_wrk_inst', value: '471137967')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: '1000001820')
            column(name: 'payee_account_number', value: '1000001820')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '15000.00') // B-55836 remove
            column(name: 'gross_amount', value: '13593.99')
            column(name: 'net_amount', value: '4350.0768')
            column(name: 'service_fee_amount', value: '9243.9132')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd0816728-4726-483d-91ff-8f24fa605e01')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '15000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '756299b5-02ce-4f76-b0bc-ee2571cf906e')
            column(name: 'rh_account_number', value: '7000429266')
            column(name: 'name', value: 'INSTITUTE OF FILM & TELEVISION STUDIES')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '038bf4aa-b6cc-430a-9b32-655954d95278')
            column(name: 'rh_account_number', value: '1000002562')
            column(name: 'name', value: 'Pall Mall Press/Phaidon Press')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '00d4ae90-5fe7-47bf-ace1-781c8d76d4da')
            column(name: 'rh_account_number', value: '1000001820')
            column(name: 'name', value: 'Delhi Medical Assn')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05dc9217-26d4-46ca-aa6e-18572591f3c8')
            column(name: 'rh_account_number', value: '1000003821')
            column(name: 'name', value: 'Abbey Publications, Inc. [L]')
        }

        rollback ""
    }

    changeSet(id: '2019-06-17-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for sending NTS Scenario to LM test')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'name', value: 'NTS Scenario for sending to LM')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'description', value: 'Scenario description')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 300.00, "pre_service_fee_fund_uid": "f2d19889-b6f6-4f8e-a89d-2ed757d6b883"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a3af8396-acf3-432b-9f23-7554e3d8f50d')
            column(name: 'name', value: 'FAS_22Nov87')
            column(name: 'rro_account_number', value: '7000800832')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '199.98')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'f2d19889-b6f6-4f8e-a89d-2ed757d6b883')
            column(name: 'name', value: 'Pre-Service Fee Additional Fund')
            column(name: 'withdrawn_amount', value: '199.98')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '91b5539e-68e2-440c-8993-538eadd1c847')
            column(name: 'df_usage_batch_uid', value: 'a3af8396-acf3-432b-9f23-7554e3d8f50d')
            column(name: 'df_fund_pool_uid', value: 'f2d19889-b6f6-4f8e-a89d-2ed757d6b883')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '99.99') // B-55836 remove
            column(name: 'gross_amount', value: '99.99')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '91b5539e-68e2-440c-8993-538eadd1c847')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '99.99')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '27d7eb01-45e2-474e-b715-5d90597b7fb0')
            column(name: 'df_usage_batch_uid', value: 'a3af8396-acf3-432b-9f23-7554e3d8f50d')
            column(name: 'df_fund_pool_uid', value: 'f2d19889-b6f6-4f8e-a89d-2ed757d6b883')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1008902112317623XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '99.99') // B-55836 remove
            column(name: 'gross_amount', value: '99.99')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '27d7eb01-45e2-474e-b715-5d90597b7fb0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '99.99')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'fd0554ff-f19c-48f4-82ce-b6e6539f6134')
            column(name: 'name', value: 'NtsOct17')
            column(name: 'rro_account_number', value: '1000002562')
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '73c8a661-a729-4494-b287-bbacc7e6459a')
            column(name: 'df_usage_batch_uid', value: 'fd0554ff-f19c-48f4-82ce-b6e6539f6134')
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'wr_wrk_inst', value: '471137470')
            column(name: 'work_title', value: 'Sunbeams')
            column(name: 'system_title', value: 'Sunbeams')
            column(name: 'rh_account_number', value: '1000003821')
            column(name: 'payee_account_number', value: '1000003821')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00') // B-55836 remove
            column(name: 'gross_amount', value: '90.63')
            column(name: 'service_fee_amount', value: '29.00')
            column(name: 'net_amount', value: '61.63')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '73c8a661-a729-4494-b287-bbacc7e6459a')
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
            column(name: 'df_usage_uid', value: '9ee73b05-7c75-4cca-ab2d-b5369fcbd183')
            column(name: 'df_usage_batch_uid', value: 'fd0554ff-f19c-48f4-82ce-b6e6539f6134')
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'wr_wrk_inst', value: '122235134')
            column(name: 'work_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'system_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'rh_account_number', value: '7000429266')
            column(name: 'payee_account_number', value: '7000429266')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Nanette M. Schwann') // B-55836 remove
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '9900.00') // B-55836 remove
            column(name: 'gross_amount', value: '8972.04')
            column(name: 'net_amount', value: '2871.05')
            column(name: 'service_fee_amount', value: '6100.98')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9ee73b05-7c75-4cca-ab2d-b5369fcbd183')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: '9900.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c6e658f1-556e-47d3-9071-2d5365c63ead')
            column(name: 'df_usage_batch_uid', value: 'fd0554ff-f19c-48f4-82ce-b6e6539f6134')
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '7000429266')
            column(name: 'payee_account_number', value: '7000429266')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012') // B-55836 remove
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'publisher', value: 'IEEE') // B-55836 remove
            column(name: 'publication_date', value: '2013-09-10') // B-55836 remove
            column(name: 'market', value: 'Doc Del') // B-55836 remove
            column(name: 'market_period_from', value: '2013') // B-55836 remove
            column(name: 'market_period_to', value: '2017') // B-55836 remove
            column(name: 'author', value: 'Philippe de Mézières') // B-55836 remove
            column(name: 'number_of_copies', value: '25')
            column(name: 'reported_value', value: '5000.00') // B-55836 remove
            column(name: 'gross_amount', value: '4531.33')
            column(name: 'net_amount', value: '1450.02')
            column(name: 'service_fee_amount', value: '3081.30')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c6e658f1-556e-47d3-9071-2d5365c63ead')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '5000.00')
        }
    }
}
