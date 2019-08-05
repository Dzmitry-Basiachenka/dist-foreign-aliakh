databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2017-11-30-00', author: 'Aliaksandra_Bayanouskaya <abayanouskaya@copyright.com>') {
        comment('Inserting test data for ScenarioRepositoryIntegrationTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: '2000017010')
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282cac-2468-48d4-b346-93d3458a656a')
            column(name: 'name', value: 'WOW_22Nov87')
            column(name: 'rro_account_number', value: '7000800832')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '30000')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3f46981e-e85a-4786-9b60-ab009c4358e7')
            column(name: 'name', value: 'NEW_26_OCT_2017')
            column(name: 'rro_account_number', value: '7000813800')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-10-30')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '35000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd398d6d7-9cbd-4f83-8d5e-90a005bf26c2')
            column(name: 'name', value: 'NEW_01_JAN_2018')
            column(name: 'rro_account_number', value: '7000813800')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-01')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '1000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'name', value: 'Scenario name 3')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 3')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-02-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-02-14 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 300.00, "pre_service_fee_amount": 500.00,' +
                    '"post_service_fee_amount": 800.00, "pre_service_fee_fund_uid": "a7131cd2-c7cd-4d70-a78f-9554e9598693"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e27551ed-3f69-4e08-9e4f-8ac03f67595f')
            column(name: 'name', value: 'Scenario name 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 2')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-03-14 12:50:42.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1230b236-1239-4a60-9fab-123b84199123')
            column(name: 'name', value: 'Scenario name 4')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 4')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-02-10 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'name', value: 'Scenario name 5')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'The description of scenario 5')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-02-09 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 300.00, "pre_service_fee_amount": 500.00,' +
                    '"post_service_fee_amount": 800.00, "pre_service_fee_fund_uid": "5210b859-1239-4a60-9fab-999b84199321"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '5210b859-1239-4a60-9fab-999b84199321')
            column(name: 'name', value: 'Pre-Service Fee Additional Fund 1')
            column(name: 'withdrawn_amount', value: '500.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f676ba94-bd15-435e-a458-833f0eb57ea8')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'df_fund_pool_uid', value: '5210b859-1239-4a60-9fab-999b84199321')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '500.00')
            column(name: 'gross_amount', value: '500.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'a7131cd2-c7cd-4d70-a78f-9554e9598693')
            column(name: 'name', value: 'Pre-Service Fee Additional Fund 2')
            column(name: 'withdrawn_amount', value: '500.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b4a5db6a-3b8c-4103-802e-dd80a94f0c99')
            column(name: 'df_usage_batch_uid', value: '56282cac-2468-48d4-b346-93d3458a656a')
            column(name: 'df_fund_pool_uid', value: 'a7131cd2-c7cd-4d70-a78f-9554e9598693')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'NTS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '500.00')
            column(name: 'gross_amount', value: '500.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '03f77c25-0c4c-4266-8a48-40f327779f12')
            column(name: 'df_usage_batch_uid', value: '56282cac-2468-48d4-b346-93d3458a656a')
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'wr_wrk_inst', value: '180382915')
            column(name: 'work_title', value: 'High Performance Switching and Routing')
            column(name: 'system_title', value: 'High Performance Switching and Routing')
            column(name: 'rh_account_number', value: '1000159997')
            column(name: 'payee_account_number', value: '7001555529')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water')
            column(name: 'standard_number', value: '1008902992377654XX')
            column(name: 'publisher', value: 'IEEE 015')
            column(name: 'publication_date', value: '2014-09-11')
            column(name: 'market', value: 'Play Market')
            column(name: 'market_period_from', value: '2014')
            column(name: 'market_period_to', value: '2018')
            column(name: 'author', value: 'Mikalai Bezmen')
            column(name: 'number_of_copies', value: '190222')
            column(name: 'reported_value', value: '2300')
            column(name: 'gross_amount', value: '30000.00')
            column(name: 'service_fee_amount', value: '9600.00')
            column(name: 'net_amount', value: '20400.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
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
            column(name: 'gross_amount', value: '16437.40')
            column(name: 'net_amount', value: '11177.40')
            column(name: 'service_fee_amount', value: '5260.00')
            column(name: 'service_fee', value: '0.32000')
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
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '9900')
            column(name: 'gross_amount', value: '16437.40')
            column(name: 'net_amount', value: '11177.40')
            column(name: 'service_fee_amount', value: '5260.00')
            column(name: 'service_fee', value: '0.32000')
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
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2014-09-10')
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
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '94839e3f-c69a-45f3-b236-a363408ef937')
            column(name: 'df_usage_batch_uid', value: 'd398d6d7-9cbd-4f83-8d5e-90a005bf26c2')
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'wr_wrk_inst', value: '180382915')
            column(name: 'work_title', value: 'High Performance Switching and Routing')
            column(name: 'system_title', value: 'High Performance Switching and Routing')
            column(name: 'rh_account_number', value: '1000159997')
            column(name: 'payee_account_number', value: '7001555529')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water')
            column(name: 'standard_number', value: '1008902992377654XX')
            column(name: 'publisher', value: 'IEEE 015')
            column(name: 'publication_date', value: '2014-09-11')
            column(name: 'market', value: 'Play Market')
            column(name: 'market_period_from', value: '2014')
            column(name: 'market_period_to', value: '2018')
            column(name: 'author', value: 'Iñigo López de Mendoza, marqués de Santillana')
            column(name: 'number_of_copies', value: '190222')
            column(name: 'reported_value', value: '1000')
            column(name: 'gross_amount', value: '1000.00')
            column(name: 'service_fee_amount', value: '320.00')
            column(name: 'net_amount', value: '680.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '826c6125-96b4-49a7-8c62-24ca807d439d')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'action_reason', value: 'Usages were added to scenario')
            column(name: 'created_datetime', value: '2017-03-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'dd62d1ed-de98-4708-8869-1333baf1bed4')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'action_reason', value: 'Scenario submitted for approval')
            column(name: 'created_datetime', value: '2017-03-10 11:41:58.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'f6368ad8-4c70-4877-9a34-00a03fc7ec26')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'action_reason', value: 'Scenario approved by manager')
            column(name: 'created_datetime', value: '2017-04-10 11:28:58.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'bbfc7a16-2372-44e3-a9e5-f284873f2438')
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'action_reason', value: 'Usages were added to scenario')
            column(name: 'created_datetime', value: '2017-03-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'e09a69c3-d153-4e22-81b4-7ce13869cb57')
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'action_reason', value: 'Scenario submitted for approval')
            column(name: 'created_datetime', value: '2017-03-10 11:41:58.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '88a05a1a-c81a-4266-a194-a0cf47df36cc')
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'action_reason', value: 'Scenario approved by manager')
            column(name: 'created_datetime', value: '2017-04-10 11:28:58.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '8d36af79-8bf4-4edc-8954-eb99c770f9e6')
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2017-06-10 11:28:58.735531+03')
        }

        rollback ""
    }

    changeSet(id: '2018-04-02-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Adding scenario filters for ScenarioRepositoryIntegrationTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '095f3df4-c8a7-4dba-9a8f-7dce0b61c40a')
            column(name: 'name', value: 'Scenario with excluded usages')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 6')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2018-03-30 17:47:24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '7459ee74-363b-11e8-b467-0ed5f89f718b')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '8f83dfce-363c-11e8-b467-0ed5f89f718b')
            column(name: 'df_scenario_uid', value: '1230b236-1239-4a60-9fab-123b84199123')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '09a754c4-363e-11e8-b467-0ed5f89f718b')
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '27ec001a-363e-11e8-b467-0ed5f89f718b')
            column(name: 'df_scenario_uid', value: 'e27551ed-3f69-4e08-9e4f-8ac03f67595f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '2c0df248-363e-11e8-b467-0ed5f89f718b')
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '3956ce16-4649-46a9-95da-337953436479')
            column(name: 'df_scenario_uid', value: '095f3df4-c8a7-4dba-9a8f-7dce0b61c40a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '7459ee74-363b-11e8-b467-0ed5f89f718b')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '8f83dfce-363c-11e8-b467-0ed5f89f718b')
            column(name: 'df_usage_batch_uid', value: '3f46981e-e85a-4786-9b60-ab009c4358e7')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '3956ce16-4649-46a9-95da-337953436479')
            column(name: 'df_usage_batch_uid', value: '4eff2685-4895-45a1-a886-c41a0f98204b')
        }

        //testFindFullPaidIds
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '005a33fc-26c5-4e0d-afd3-1d581b62ec70')
            column(name: 'name', value: 'Partially Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Not all usages are paid')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-01-04 17:47:24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '54bab996-829f-445e-a853-cf30ea760b5b')
            column(name: 'df_scenario_uid', value: '005a33fc-26c5-4e0d-afd3-1d581b62ec70')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '05bfb2e0-27ce-4378-ab46-9954e7d987b6')
            column(name: 'name', value: 'Paid batch')
            column(name: 'rro_account_number', value: '1000005413')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '4a8cd820-605d-439e-8619-7f7dcaaf31e0')
            column(name: 'df_usage_batch_uid', value: '05bfb2e0-27ce-4378-ab46-9954e7d987b6')
            column(name: 'df_scenario_uid', value: '005a33fc-26c5-4e0d-afd3-1d581b62ec70')
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
            column(name: 'market_period_to', value: '2016')
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
            column(name: 'distribution_date', value: '2016-03-11')
            column(name: 'period_end_date', value: '2016-03-11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '60b99116-8765-4bf6-91d3-c938dca8321a')
            column(name: 'df_usage_batch_uid', value: '05bfb2e0-27ce-4378-ab46-9954e7d987b6')
            column(name: 'df_scenario_uid', value: '005a33fc-26c5-4e0d-afd3-1d581b62ec70')
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
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'c1fcc8f5-91ac-41e2-a909-92a166641815')
            column(name: 'df_usage_batch_uid', value: '05bfb2e0-27ce-4378-ab46-9954e7d987b6')
            column(name: 'df_scenario_uid', value: '005a33fc-26c5-4e0d-afd3-1d581b62ec70')
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
            column(name: 'market_period_to', value: '2016')
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
            column(name: 'distribution_date', value: '2016-03-11')
            column(name: 'period_end_date', value: '2016-03-11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'a9ee7491-d166-47cd-b36f-fe80ee7450f1')
            column(name: 'name', value: 'Fully Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'All usages are paid and reported to CRM')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-01-02 17:47:24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '78f092a3-af1a-44ff-bde5-34263d30d040')
            column(name: 'df_scenario_uid', value: 'a9ee7491-d166-47cd-b36f-fe80ee7450f1')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '2f235210-36af-4c7e-a5a3-25fc2e0668a2')
            column(name: 'df_usage_batch_uid', value: '05bfb2e0-27ce-4378-ab46-9954e7d987b6')
            column(name: 'df_scenario_uid', value: 'a9ee7491-d166-47cd-b36f-fe80ee7450f1')
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
            column(name: 'market_period_to', value: '2016')
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
            column(name: 'distribution_date', value: '2016-03-11')
            column(name: 'period_end_date', value: '2016-03-11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'a386bd74-c112-4b19-b9b7-c5e4f18c7fcd')
            column(name: 'name', value: 'Archived Scenario')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'description', value: 'Scenario already archived')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-01-01 17:47:24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '4499e89b-3d2e-4358-b133-668e92ea4f43')
            column(name: 'df_scenario_uid', value: 'a386bd74-c112-4b19-b9b7-c5e4f18c7fcd')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ed7d46a4-9f42-49cc-9bb2-a3969a225042')
            column(name: 'df_usage_batch_uid', value: '05bfb2e0-27ce-4378-ab46-9954e7d987b6')
            column(name: 'df_scenario_uid', value: 'a386bd74-c112-4b19-b9b7-c5e4f18c7fcd')
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
            column(name: 'market_period_to', value: '2016')
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
            column(name: 'distribution_date', value: '2016-03-11')
            column(name: 'period_end_date', value: '2016-03-11')
        }

        changeSet(id: '2019-04-16-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
            comment("Insert test data for testFindAll")

            insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
                column(name: 'df_scenario_uid', value: '1a5f3df4-c8a7-4dba-9a8f-7dce0b61c41b')
                column(name: 'name', value: 'Test NTS scenario')
                column(name: 'status_ind', value: 'IN_PROGRESS')
                column(name: 'description', value: 'Description for test NTS scenario')
                column(name: 'nts_fields', value: '{"rh_minimum_amount":300.00}')
                column(name: 'created_by_user', value: 'user@copyright.com')
                column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
                column(name: 'updated_datetime', value: '2011-01-01 17:47:24')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
                column(name: 'df_scenario_usage_filter_uid', value: '102f2e3b-ed14-48a1-b628-a528d07fc9c5')
                column(name: 'df_scenario_uid', value: '1a5f3df4-c8a7-4dba-9a8f-7dce0b61c41b')
                column(name: 'product_family', value: 'NTS')
                column(name: 'status_ind', value: 'ELIGIBLE')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
                column(name: 'df_usage_batch_uid', value: 'f27ebc80-e74e-436d-ba6e-acf3d355b700')
                column(name: 'name', value: 'Test NTS batch')
                column(name: 'rro_account_number', value: '2000017010')
                column(name: 'payment_date', value: '2010-09-10')
                column(name: 'product_family', value: 'NTS')
                column(name: 'fiscal_year', value: '2011')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'df_usage_uid', value: '95093193-00d9-436b-8fbc-078511b1d336')
                column(name: 'df_usage_batch_uid', value: 'f27ebc80-e74e-436d-ba6e-acf3d355b700')
                column(name: 'df_scenario_uid', value: '1a5f3df4-c8a7-4dba-9a8f-7dce0b61c41b')
                column(name: 'wr_wrk_inst', value: '180382914')
                column(name: 'work_title', value: 'A history of contemporary Italy : society and politics1088.89 1943-1988')
                column(name: 'system_title', value: 'A history of contemporary Italy : society and politics1088.89 1943-1988')
                column(name: 'rh_account_number', value: '1000002859')
                column(name: 'status_ind', value: 'LOCKED')
                column(name: 'product_family', value: 'NTS')
                column(name: 'article', value: 'The Era of Collective Action 1968-73')
                column(name: 'standard_number', value: '1008902112377654XX')
                column(name: 'publisher', value: 'IEEE')
                column(name: 'publication_date', value: '2013-09-10')
                column(name: 'market', value: 'Doc Del')
                column(name: 'market_period_from', value: '2012')
                column(name: 'market_period_to', value: '2014')
                column(name: 'author', value: 'Aarseth1088.89 Espen J.')
                column(name: 'number_of_copies', value: '1')
                column(name: 'reported_value', value: '500')
                column(name: 'gross_amount', value: '500.00')
            }
        }

        changeSet(id: '2019-04-18-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
            comment("Insert test data for insertNtsScenarioAndAddUsages")

            insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
                column(name: 'df_work_classification_uid', value: 'fc9ee4ed-519e-41c8-927b-92206b34c8cc')
                column(name: 'wr_wrk_inst', value: '135632563')
                column(name: 'classification', value: 'NON-STM')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
                column(name: 'df_work_classification_uid', value: '7c04aac5-ccc5-4abc-b84a-4077dd6ca9a8')
                column(name: 'wr_wrk_inst', value: '145632563')
                column(name: 'classification', value: 'STM')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
                column(name: 'df_usage_batch_uid', value: '7c8f48fe-3429-43fd-9389-d9b77fa9f3a0')
                column(name: 'name', value: 'NTS Batch')
                column(name: 'rro_account_number', value: '2000017001')
                column(name: 'payment_date', value: '2019-01-11')
                column(name: 'product_family', value: 'NTS')
                column(name: 'fiscal_year', value: '2020')
                column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 10000, "stm_minimum_amount": 50, ' +
                        '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'df_usage_uid', value: '87666035-2bdf-49ef-8c80-1d1b281fdc34')
                column(name: 'df_usage_batch_uid', value: '7c8f48fe-3429-43fd-9389-d9b77fa9f3a0')
                column(name: 'wr_wrk_inst', value: '135632563')
                column(name: 'work_title', value: 'Cell Biology')
                column(name: 'system_title', value: 'Cell Biology')
                column(name: 'rh_account_number', value: '2000017004')
                column(name: 'status_ind', value: 'ELIGIBLE')
                column(name: 'product_family', value: 'NTS')
                column(name: 'article', value: 'DIN EN 779:2012')
                column(name: 'standard_number', value: '1003324112314587XX')
                column(name: 'standard_number_type', value: 'VALISBN')
                column(name: 'publisher', value: 'IEEE')
                column(name: 'publication_date', value: '2013-09-10')
                column(name: 'market', value: 'Univ')
                column(name: 'market_period_from', value: '2013')
                column(name: 'market_period_to', value: '2014')
                column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
                column(name: 'number_of_copies', value: '2502232')
                column(name: 'reported_value', value: '2000.00')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'df_usage_uid', value: '3caf371f-f2e6-47cd-af6b-1e02b79f6195')
                column(name: 'df_usage_batch_uid', value: '7c8f48fe-3429-43fd-9389-d9b77fa9f3a0')
                column(name: 'wr_wrk_inst', value: '135632563')
                column(name: 'work_title', value: 'Cell Biology')
                column(name: 'system_title', value: 'Cell Biology')
                column(name: 'rh_account_number', value: '1000002797')
                column(name: 'status_ind', value: 'ELIGIBLE')
                column(name: 'product_family', value: 'NTS')
                column(name: 'article', value: 'DIN EN 779:2012')
                column(name: 'standard_number', value: '1003324112314587XX')
                column(name: 'standard_number_type', value: 'VALISBN')
                column(name: 'publisher', value: 'IEEE')
                column(name: 'publication_date', value: '2013-09-10')
                column(name: 'market', value: 'Univ')
                column(name: 'market_period_from', value: '2013')
                column(name: 'market_period_to', value: '2017')
                column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
                column(name: 'number_of_copies', value: '2502232')
                column(name: 'reported_value', value: '2000.00')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'df_usage_uid', value: '9bc172f4-edbb-4a62-9ffc-254336e7a56d')
                column(name: 'df_usage_batch_uid', value: '7c8f48fe-3429-43fd-9389-d9b77fa9f3a0')
                column(name: 'wr_wrk_inst', value: '145632563')
                column(name: 'work_title', value: 'Cell Biology')
                column(name: 'system_title', value: 'Cell Biology')
                column(name: 'rh_account_number', value: '1000002859')
                column(name: 'status_ind', value: 'ELIGIBLE')
                column(name: 'product_family', value: 'NTS')
                column(name: 'article', value: 'DIN EN 779:2012')
                column(name: 'standard_number', value: '1003324112314587XX')
                column(name: 'standard_number_type', value: 'VALISBN')
                column(name: 'publisher', value: 'IEEE')
                column(name: 'publication_date', value: '2013-09-10')
                column(name: 'market', value: 'Univ')
                column(name: 'market_period_from', value: '2013')
                column(name: 'market_period_to', value: '2017')
                column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
                column(name: 'number_of_copies', value: '2502232')
                column(name: 'reported_value', value: '10000.00')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'df_usage_uid', value: '244de0db-b50c-45e8-937c-72e033e2a3a9')
                column(name: 'df_usage_batch_uid', value: '7c8f48fe-3429-43fd-9389-d9b77fa9f3a0')
                column(name: 'wr_wrk_inst', value: '145632563')
                column(name: 'work_title', value: 'LIGHT METALS')
                column(name: 'system_title', value: 'LIGHT METALS')
                column(name: 'rh_account_number', value: '1000005413')
                column(name: 'status_ind', value: 'ELIGIBLE')
                column(name: 'product_family', value: 'NTS')
                column(name: 'article', value: 'DIN EN 779:2012')
                column(name: 'standard_number', value: '1003324112314587XX')
                column(name: 'standard_number_type', value: 'VALISBN')
                column(name: 'publisher', value: 'IEEE')
                column(name: 'publication_date', value: '2013-09-10')
                column(name: 'market', value: 'Univ')
                column(name: 'market_period_from', value: '2013')
                column(name: 'market_period_to', value: '2017')
                column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
                column(name: 'number_of_copies', value: '2502232')
                column(name: 'reported_value', value: '20000.00')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'df_usage_uid', value: '27baef39-8f7b-4182-a055-85ae9556a24f')
                column(name: 'df_usage_batch_uid', value: '7c8f48fe-3429-43fd-9389-d9b77fa9f3a0')
                column(name: 'wr_wrk_inst', value: '145632563')
                column(name: 'work_title', value: 'LIGHT METALS')
                column(name: 'system_title', value: 'LIGHT METALS')
                column(name: 'rh_account_number', value: '1000009997')
                column(name: 'status_ind', value: 'WORK_FOUND')
                column(name: 'product_family', value: 'NTS')
                column(name: 'article', value: 'DIN EN 779:2012')
                column(name: 'standard_number', value: '1003324112314587XX')
                column(name: 'standard_number_type', value: 'VALISBN')
                column(name: 'publisher', value: 'IEEE')
                column(name: 'publication_date', value: '2013-09-10')
                column(name: 'market', value: 'Univ')
                column(name: 'market_period_from', value: '2013')
                column(name: 'market_period_to', value: '2017')
                column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
                column(name: 'number_of_copies', value: '2502232')
                column(name: 'reported_value', value: '2000.00')
            }

            /* zero fund pool amounts */
            insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
                column(name: 'df_usage_batch_uid', value: 'ac9bae73-9bd7-477c-bc95-e73daee455ee')
                column(name: 'name', value: 'NTS Batch 3')
                column(name: 'rro_account_number', value: '2000017001')
                column(name: 'payment_date', value: '2019-01-11')
                column(name: 'product_family', value: 'NTS')
                column(name: 'fiscal_year', value: '2020')
                column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 0, "non_stm_amount": 0, "stm_minimum_amount": 50, ' +
                        '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'df_usage_uid', value: '0d200064-185a-4c48-bbc9-c67554e7db8e')
                column(name: 'df_usage_batch_uid', value: 'ac9bae73-9bd7-477c-bc95-e73daee455ee')
                column(name: 'wr_wrk_inst', value: '145632563')
                column(name: 'work_title', value: 'LIGHT METALS')
                column(name: 'system_title', value: 'LIGHT METALS')
                column(name: 'rh_account_number', value: '1000009997')
                column(name: 'status_ind', value: 'ELIGIBLE')
                column(name: 'product_family', value: 'NTS')
                column(name: 'article', value: 'DIN EN 779:2012')
                column(name: 'standard_number', value: '1003324112314587XX')
                column(name: 'standard_number_type', value: 'VALISBN')
                column(name: 'publisher', value: 'IEEE')
                column(name: 'publication_date', value: '2013-09-10')
                column(name: 'market', value: 'Univ')
                column(name: 'market_period_from', value: '2013')
                column(name: 'market_period_to', value: '2017')
                column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
                column(name: 'number_of_copies', value: '2502232')
                column(name: 'reported_value', value: '3000.00')
            }

            /* payment date doesn't meet filter criteria */
            insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
                column(name: 'df_usage_batch_uid', value: '5769e9fe-1b4b-4399-841c-73108893f7d2')
                column(name: 'name', value: 'NTS Batch 2')
                column(name: 'rro_account_number', value: '2000017001')
                column(name: 'payment_date', value: '2021-01-11')
                column(name: 'product_family', value: 'NTS')
                column(name: 'fiscal_year', value: '2020')
                column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 2000, "non_stm_amount": 20000, "stm_minimum_amount": 50, ' +
                        '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'df_usage_uid', value: '5018dc5e-9985-4f15-ab31-3cbdb4aee446')
                column(name: 'df_usage_batch_uid', value: '5769e9fe-1b4b-4399-841c-73108893f7d2')
                column(name: 'wr_wrk_inst', value: '145632563')
                column(name: 'work_title', value: 'LIGHT METALS')
                column(name: 'system_title', value: 'LIGHT METALS')
                column(name: 'rh_account_number', value: '1000009997')
                column(name: 'status_ind', value: 'ELIGIBLE')
                column(name: 'product_family', value: 'NTS')
                column(name: 'article', value: 'DIN EN 779:2012')
                column(name: 'standard_number', value: '1003324112314587XX')
                column(name: 'standard_number_type', value: 'VALISBN')
                column(name: 'publisher', value: 'IEEE')
                column(name: 'publication_date', value: '2013-09-10')
                column(name: 'market', value: 'Univ')
                column(name: 'market_period_from', value: '2013')
                column(name: 'market_period_to', value: '2017')
                column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
                column(name: 'number_of_copies', value: '2502232')
                column(name: 'reported_value', value: '2000.00')
            }

            /* fiscal year doesn't meet filter criteria */
            insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
                column(name: 'df_usage_batch_uid', value: 'd8baa8e6-10fd-4c3c-8851-b1e6883e8cde')
                column(name: 'name', value: 'NTS Batch 2')
                column(name: 'rro_account_number', value: '2000017001')
                column(name: 'payment_date', value: '2019-01-11')
                column(name: 'product_family', value: 'NTS')
                column(name: 'fiscal_year', value: '2021')
                column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 2000, "non_stm_amount": 20000, "stm_minimum_amount": 50, ' +
                        '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'df_usage_uid', value: 'efadcdfa-fafe-4b23-aef0-92d2e68d15b4')
                column(name: 'df_usage_batch_uid', value: 'd8baa8e6-10fd-4c3c-8851-b1e6883e8cde')
                column(name: 'wr_wrk_inst', value: '145632563')
                column(name: 'work_title', value: 'LIGHT METALS')
                column(name: 'system_title', value: 'LIGHT METALS')
                column(name: 'rh_account_number', value: '1000009997')
                column(name: 'status_ind', value: 'ELIGIBLE')
                column(name: 'product_family', value: 'NTS')
                column(name: 'article', value: 'DIN EN 779:2012')
                column(name: 'standard_number', value: '1003324112314587XX')
                column(name: 'standard_number_type', value: 'VALISBN')
                column(name: 'publisher', value: 'IEEE')
                column(name: 'publication_date', value: '2013-09-10')
                column(name: 'market', value: 'Univ')
                column(name: 'market_period_from', value: '2013')
                column(name: 'market_period_to', value: '2017')
                column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
                column(name: 'number_of_copies', value: '2502232')
                column(name: 'reported_value', value: '2000.00')
            }

            /* RRO doesn't meet filter criteria */
            insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
                column(name: 'df_usage_batch_uid', value: 'f8f23728-75ac-4114-b910-2d7abc061217')
                column(name: 'name', value: 'NTS Batch 2')
                column(name: 'rro_account_number', value: '2000017002')
                column(name: 'payment_date', value: '2019-01-11')
                column(name: 'product_family', value: 'NTS')
                column(name: 'fiscal_year', value: '2019')
                column(name: 'fund_pool', value: '{"markets": ["Univ"], "stm_amount": 2000, "non_stm_amount": 20000, "stm_minimum_amount": 50, ' +
                        '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'df_usage_uid', value: '4c04d896-a63d-43e8-9a18-1e8503c177d0')
                column(name: 'df_usage_batch_uid', value: 'f8f23728-75ac-4114-b910-2d7abc061217')
                column(name: 'wr_wrk_inst', value: '145632563')
                column(name: 'work_title', value: 'LIGHT METALS')
                column(name: 'system_title', value: 'LIGHT METALS')
                column(name: 'rh_account_number', value: '1000009997')
                column(name: 'status_ind', value: 'ELIGIBLE')
                column(name: 'product_family', value: 'NTS')
                column(name: 'article', value: 'DIN EN 779:2012')
                column(name: 'standard_number', value: '1003324112314587XX')
                column(name: 'standard_number_type', value: 'VALISBN')
                column(name: 'publisher', value: 'IEEE')
                column(name: 'publication_date', value: '2013-09-10')
                column(name: 'market', value: 'Univ')
                column(name: 'market_period_from', value: '2013')
                column(name: 'market_period_to', value: '2017')
                column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
                column(name: 'number_of_copies', value: '2502232')
                column(name: 'reported_value', value: '2000.00')
            }
        }

        rollback ""
    }
}
