databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-01-12-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testSendToLmFas')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '4c014547-06f3-4840-94ff-6249730d537d')
            column(name: 'name', value: 'FAS Scenario for sending to LM')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'name', value: 'FasOct17')
            column(name: 'rro_account_number', value: 1000002562)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 30000)
            column(name: 'initial_usages_count', value: 4)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'fcdaea01-2439-4c51-b3e2-23649cf710c7')
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'df_scenario_uid', value: '4c014547-06f3-4840-94ff-6249730d537d')
            column(name: 'wr_wrk_inst', value: 471137470)
            column(name: 'work_title', value: 'Sunbeams')
            column(name: 'system_title', value: 'Sunbeams')
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
            column(name: 'comment', value: 'usage from usages.csv')
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
            column(name: 'df_scenario_uid', value: '4c014547-06f3-4840-94ff-6249730d537d')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'work_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'system_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'payee_account_number', value: 7000429266)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 8972.04)
            column(name: 'net_amount', value: 2871.0528)
            column(name: 'service_fee_amount', value: 6100.9872)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'comment', value: 'usage from usages.csv')
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
            column(name: 'df_scenario_uid', value: '4c014547-06f3-4840-94ff-6249730d537d')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: 25)
            column(name: 'gross_amount', value: 4531.33)
            column(name: 'net_amount', value: 1450.0256)
            column(name: 'service_fee_amount', value: 3081.3044)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'comment', value: 'usage from usages.csv')
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
            column(name: 'df_scenario_uid', value: '4c014547-06f3-4840-94ff-6249730d537d')
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: 1000001820)
            column(name: 'payee_account_number', value: 1000001820)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 13593.99)
            column(name: 'net_amount', value: 4350.0768)
            column(name: 'service_fee_amount', value: 9243.9132)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'comment', value: 'usage from usages.csv')
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

        rollback ""
    }

    changeSet(id: '2019-06-17-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testSendToLmNts')

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
            column(name: 'rro_account_number', value: 7000800832)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 199.98)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'f2d19889-b6f6-4f8e-a89d-2ed757d6b883')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'NTS fund pool')
            column(name: 'total_amount', value: 199.98)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '91b5539e-68e2-440c-8993-538eadd1c847')
            column(name: 'df_usage_batch_uid', value: 'a3af8396-acf3-432b-9f23-7554e3d8f50d')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 99.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '91b5539e-68e2-440c-8993-538eadd1c847')
            column(name: 'df_fund_pool_uid', value: 'f2d19889-b6f6-4f8e-a89d-2ed757d6b883')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 99.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '27d7eb01-45e2-474e-b715-5d90597b7fb0')
            column(name: 'df_usage_batch_uid', value: 'a3af8396-acf3-432b-9f23-7554e3d8f50d')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112317623XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 99.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '27d7eb01-45e2-474e-b715-5d90597b7fb0')
            column(name: 'df_fund_pool_uid', value: 'f2d19889-b6f6-4f8e-a89d-2ed757d6b883')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 99.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'fd0554ff-f19c-48f4-82ce-b6e6539f6134')
            column(name: 'name', value: 'NtsOct17')
            column(name: 'rro_account_number', value: 1000002562)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '73c8a661-a729-4494-b287-bbacc7e6459a')
            column(name: 'df_usage_batch_uid', value: 'fd0554ff-f19c-48f4-82ce-b6e6539f6134')
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'wr_wrk_inst', value: 471137470)
            column(name: 'work_title', value: 'Sunbeams')
            column(name: 'system_title', value: 'Sunbeams')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'payee_account_number', value: 1000003821)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 90.63)
            column(name: 'service_fee_amount', value: 29.00)
            column(name: 'net_amount', value: 61.63)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '73c8a661-a729-4494-b287-bbacc7e6459a')
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
            column(name: 'df_usage_uid', value: '9ee73b05-7c75-4cca-ab2d-b5369fcbd183')
            column(name: 'df_usage_batch_uid', value: 'fd0554ff-f19c-48f4-82ce-b6e6539f6134')
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'work_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'system_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'payee_account_number', value: 7000429266)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 8972.04)
            column(name: 'net_amount', value: 2871.05)
            column(name: 'service_fee_amount', value: 6100.98)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9ee73b05-7c75-4cca-ab2d-b5369fcbd183')
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
            column(name: 'df_usage_uid', value: 'c6e658f1-556e-47d3-9071-2d5365c63ead')
            column(name: 'df_usage_batch_uid', value: 'fd0554ff-f19c-48f4-82ce-b6e6539f6134')
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'payee_account_number', value: 7000429266)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: 25)
            column(name: 'gross_amount', value: 4531.33)
            column(name: 'net_amount', value: 1450.02)
            column(name: 'service_fee_amount', value: 3081.30)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c6e658f1-556e-47d3-9071-2d5365c63ead')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 5000.00)
        }
    }

    changeSet(id: '2020-05-15-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testSendToLmAacl')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '98b4a093-b48c-4a41-828b-30cb54ebd387')
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_copies', value: 155)
            column(name: 'number_of_pages', value: 100)
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.50)
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'comment', value: 'LOCKED usage from baseline')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '75cfffc6-5aba-43a6-9ece-6c7da065cf20')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_copies', value: 155)
            column(name: 'number_of_pages', value: 100)
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'original_publication_type', value: 'Journal')
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'publication_type_weight', value: 2.50)
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'comment', value: 'SCENARIO_EXCLUDED usage from baseline')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '0ea96c66-72aa-4d59-8128-31c20e7eb9de')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool For Add To Baseline Test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'a00cafcc-4157-409a-b381-33d5fe08631c')
            column(name: 'df_fund_pool_uid', value: '0ea96c66-72aa-4d59-8128-31c20e7eb9de')
            column(name: 'df_aggregate_licensee_class_id', value: 141)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e5177da8-099f-41c4-b4b0-49a38da94805')
            column(name: 'name', value: 'AACL Usage Batch')
            column(name: 'payment_date', value: '2021-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 4)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'd92e3c8e-7ecc-4080-bf3f-b541f51c9a06')
            column(name: 'name', value: 'AACL Scenario')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'aacl_fields', value: '{"fund_pool_uid": "0ea96c66-72aa-4d59-8128-31c20e7eb9de", "usageAges": [{"period": 2021, "weight": 1.00}], "publicationTypes": [{"id": "2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "weight": 3.00},{"id": "68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "weight": 2.00}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        // LOCKED usage from baseline
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '297d5aea-24f8-4b26-be22-2441bda526dd')
            column(name: 'df_usage_batch_uid', value: 'e5177da8-099f-41c4-b4b0-49a38da94805')
            column(name: 'df_scenario_uid', value: 'd92e3c8e-7ecc-4080-bf3f-b541f51c9a06')
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 375.00)
            column(name: 'service_fee_amount', value: 125.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'LOCKED usage from baseline')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '297d5aea-24f8-4b26-be22-2441bda526dd')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'value_weight', value: 24.0000000)
            column(name: 'volume_weight', value: 5.0000000)
            column(name: 'volume_share', value: 50.0000000)
            column(name: 'value_share', value: 60.0000000)
            column(name: 'total_share', value: 2.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.50)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'baseline_uid', value: '98b4a093-b48c-4a41-828b-30cb54ebd387')
        }

        // SCENARIO_EXCLUDED usage from baseline
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd2704d44-9d3f-4f9c-bef8-38ac8116592c')
            column(name: 'df_usage_batch_uid', value: 'e5177da8-099f-41c4-b4b0-49a38da94805')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'system_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'SCENARIO_EXCLUDED usage from baseline')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'd2704d44-9d3f-4f9c-bef8-38ac8116592c')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'publication_type_weight', value: 2.50)
            column(name: 'original_publication_type', value: 'Journal')
            column(name: 'baseline_uid', value: '75cfffc6-5aba-43a6-9ece-6c7da065cf20')
        }

        // Newly uploaded LOCKED usage
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '190fee30-4de4-4cac-afce-432503e42485')
            column(name: 'df_usage_batch_uid', value: 'e5177da8-099f-41c4-b4b0-49a38da94805')
            column(name: 'df_scenario_uid', value: 'd92e3c8e-7ecc-4080-bf3f-b541f51c9a06')
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 375.00)
            column(name: 'service_fee_amount', value: 125.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'Newly uploaded LOCKED usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '190fee30-4de4-4cac-afce-432503e42485')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'value_weight', value: 24.0000000)
            column(name: 'volume_weight', value: 5.0000000)
            column(name: 'volume_share', value: 50.0000000)
            column(name: 'value_share', value: 60.0000000)
            column(name: 'total_share', value: 2.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 3.00)
        }

        // Newly uploaded SCENARIO_EXCLUDED usage
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f9c1549f-e170-44a9-a33d-60f858d868e0')
            column(name: 'df_usage_batch_uid', value: 'e5177da8-099f-41c4-b4b0-49a38da94805')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'system_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'Newly uploaded SCENARIO_EXCLUDED usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'f9c1549f-e170-44a9-a33d-60f858d868e0')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'publication_type_weight', value: 2.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '06620f6f-6ebd-4944-a9d5-eedb9fdd1648')
            column(name: 'df_scenario_uid', value: 'd92e3c8e-7ecc-4080-bf3f-b541f51c9a06')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '06620f6f-6ebd-4944-a9d5-eedb9fdd1648')
            column(name: 'df_usage_batch_uid', value: 'e5177da8-099f-41c4-b4b0-49a38da94805')
        }
    }

    changeSet(id: '2020-11-05-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testSendToLmSal')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '6cb5fe9f-d524-4dad-9d22-feb6a4476ba8')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 1 for testSendToLmSal')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "12/24/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 200.00, "grade_K_5_gross_amount": 800.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '7c7c9a7c-ac77-4477-badd-c769d1e5a8b5')
            column(name: 'name', value: 'SAL Usage Batch 1 for testSendToLmSal')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 4)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b32a1abe-0de7-4889-99aa-fd5491c85a94')
            column(name: 'name', value: 'SAL Scenario 1 for testSendToLmSal')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "6cb5fe9f-d524-4dad-9d22-feb6a4476ba8"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'q371254d-2520-445f-b135-5ee9bccbbe05')
            column(name: 'df_scenario_uid', value: 'b32a1abe-0de7-4889-99aa-fd5491c85a94')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'q371254d-2520-445f-b135-5ee9bccbbe05')
            column(name: 'df_usage_batch_uid', value: '7c7c9a7c-ac77-4477-badd-c769d1e5a8b5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b27b4760-8d15-483b-bf41-6670ce4c15e8')
            column(name: 'df_usage_batch_uid', value: '7c7c9a7c-ac77-4477-badd-c769d1e5a8b5')
            column(name: 'df_scenario_uid', value: 'b32a1abe-0de7-4889-99aa-fd5491c85a94')
            column(name: 'wr_wrk_inst', value: 243204754)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000000026)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 35.50)
            column(name: 'service_fee_amount', value: 12.50)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'b27b4760-8d15-483b-bf41-6670ce4c15e8')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '1')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1201001IB2161')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '11d1a5d2-ba9f-48b7-9b09-0516840a07ee')
            column(name: 'df_usage_batch_uid', value: '7c7c9a7c-ac77-4477-badd-c769d1e5a8b5')
            column(name: 'df_scenario_uid', value: 'b32a1abe-0de7-4889-99aa-fd5491c85a94')
            column(name: 'wr_wrk_inst', value: 243204754)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000000026)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'gross_amount', value: 506.25)
            column(name: 'net_amount', value: 379.6875)
            column(name: 'service_fee_amount', value: 126.5625)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '11d1a5d2-ba9f-48b7-9b09-0516840a07ee')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '1')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1201001IB2161')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 42)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '18b5445f-2146-4f3d-a34a-aadf1e81aed3')
            column(name: 'df_usage_batch_uid', value: '7c7c9a7c-ac77-4477-badd-c769d1e5a8b5')
            column(name: 'df_scenario_uid', value: 'b32a1abe-0de7-4889-99aa-fd5491c85a94')
            column(name: 'wr_wrk_inst', value: 100004110)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000000026)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'standard_number', value: '09639291')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 35.50)
            column(name: 'service_fee_amount', value: 12.50)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '18b5445f-2146-4f3d-a34a-aadf1e81aed3')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '1')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1201001IB2162')
            column(name: 'reported_article', value: 'Medical Journal')
            column(name: 'reported_standard_number', value: '09639291')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9469ab6e-61e4-4e38-8011-2133cc0546f9')
            column(name: 'df_usage_batch_uid', value: '7c7c9a7c-ac77-4477-badd-c769d1e5a8b5')
            column(name: 'df_scenario_uid', value: 'b32a1abe-0de7-4889-99aa-fd5491c85a94')
            column(name: 'wr_wrk_inst', value: 100004110)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000000026)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'standard_number', value: '09639291')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'gross_amount', value: 393.75)
            column(name: 'net_amount', value: 295.3125)
            column(name: 'service_fee_amount', value: 98.4375)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '9469ab6e-61e4-4e38-8011-2133cc0546f9')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '1')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1201001IB2162')
            column(name: 'reported_article', value: 'Medical Journal')
            column(name: 'reported_standard_number', value: '09639291')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2019-2020')
            column(name: 'scored_assessment_date', value: '2020-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 24)
        }
    }
}
