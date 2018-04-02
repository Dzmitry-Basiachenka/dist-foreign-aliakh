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
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '30000')
            column(name: 'updated_datetime', value: '2017-02-02 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'name', value: 'JAACC_11Dec16')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: '2016')
            column(name: 'gross_amount', value: '35000')
            column(name: 'updated_datetime', value: '2017-02-11 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3f46981e-e85a-4786-9b60-ab009c4358e7')
            column(name: 'name', value: 'NEW_26_OCT_2017')
            column(name: 'rro_account_number', value: '7000813800')
            column(name: 'payment_date', value: '2017-10-30')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '35000')
            column(name: 'updated_datetime', value: '2017-10-26 14:49:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd398d6d7-9cbd-4f83-8d5e-90a005bf26c2')
            column(name: 'name', value: 'NEW_01_JAN_2018')
            column(name: 'rro_account_number', value: '7000813800')
            column(name: 'payment_date', value: '2018-01-01')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '1000')
            column(name: 'updated_datetime', value: '2017-10-26 14:49:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'name', value: 'Scenario name 3')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 3')
            column(name: 'updated_datetime', value: '2017-02-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario')
            column(name: 'updated_datetime', value: '2017-02-14 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e27551ed-3f69-4e08-9e4f-8ac03f67595f')
            column(name: 'name', value: 'Scenario name 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 2')
            column(name: 'updated_datetime', value: '2017-03-14 12:50:42.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1230b236-1239-4a60-9fab-123b84199123')
            column(name: 'name', value: 'Scenario name 4')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 4')
            column(name: 'updated_datetime', value: '2017-02-10 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'name', value: 'Scenario name 5')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'The description of scenario 5')
            column(name: 'updated_datetime', value: '2017-02-10 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '03f77c25-0c4c-4266-8a48-40f327779f12')
            column(name: 'df_usage_batch_uid', value: '56282cac-2468-48d4-b346-93d3458a656a')
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'detail_id', value: '6997788338')
            column(name: 'wr_wrk_inst', value: '180382915')
            column(name: 'work_title', value: 'High Performance Switching and Routing')
            column(name: 'rh_account_number', value: '1000159997')
            column(name: 'payee_account_number', value: '7001555529')
            column(name: 'status_ind', value: 'LOCKED')
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
            column(name: 'detail_id', value: '6997788886')
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
            column(name: 'detail_id', value: '6213788886')
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
            column(name: 'detail_id', value: '1917718881')
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
            column(name: 'detail_id', value: '6997788358')
            column(name: 'wr_wrk_inst', value: '180382915')
            column(name: 'work_title', value: 'High Performance Switching and Routing')
            column(name: 'rh_account_number', value: '1000159997')
            column(name: 'payee_account_number', value: '7001555529')
            column(name: 'status_ind', value: 'LOCKED')
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
            column(name: 'updated_datetime', value: '2017-03-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'dd62d1ed-de98-4708-8869-1333baf1bed4')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'action_reason', value: 'Scenario submitted for approval')
            column(name: 'created_datetime', value: '2017-03-10 11:41:58.735531+03')
            column(name: 'updated_datetime', value: '2017-03-10 11:41:58.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'f6368ad8-4c70-4877-9a34-00a03fc7ec26')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'action_reason', value: 'Scenario approved by manager')
            column(name: 'created_datetime', value: '2017-04-10 11:28:58.735531+03')
            column(name: 'updated_datetime', value: '2017-04-10 11:28:58.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'bbfc7a16-2372-44e3-a9e5-f284873f2438')
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'action_reason', value: 'Usages were added to scenario')
            column(name: 'created_datetime', value: '2017-03-01 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2017-03-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'e09a69c3-d153-4e22-81b4-7ce13869cb57')
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'action_reason', value: 'Scenario submitted for approval')
            column(name: 'created_datetime', value: '2017-03-10 11:41:58.735531+03')
            column(name: 'updated_datetime', value: '2017-03-10 11:41:58.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '88a05a1a-c81a-4266-a194-a0cf47df36cc')
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'action_reason', value: 'Scenario approved by manager')
            column(name: 'created_datetime', value: '2017-04-10 11:28:58.735531+03')
            column(name: 'updated_datetime', value: '2017-04-10 11:28:58.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '8d36af79-8bf4-4edc-8954-eb99c770f9e6')
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2017-06-10 11:28:58.735531+03')
            column(name: 'updated_datetime', value: '2017-06-10 11:28:58.735531+03')
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
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2018-03-30 17:47:24')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2018-03-30 17:47:24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '7459ee74-363b-11e8-b467-0ed5f89f718b')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2018-03-30 17:47:24')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2018-03-30 17:47:24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '8f83dfce-363c-11e8-b467-0ed5f89f718b')
            column(name: 'df_scenario_uid', value: '1230b236-1239-4a60-9fab-123b84199123')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2018-03-30 17:47:24')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2018-03-30 17:47:24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '09a754c4-363e-11e8-b467-0ed5f89f718b')
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2018-03-30 17:47:24')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2018-03-30 17:47:24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '27ec001a-363e-11e8-b467-0ed5f89f718b')
            column(name: 'df_scenario_uid', value: 'e27551ed-3f69-4e08-9e4f-8ac03f67595f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2018-03-30 17:47:24')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2018-03-30 17:47:24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '2c0df248-363e-11e8-b467-0ed5f89f718b')
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2018-03-30 17:47:24')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2018-03-30 17:47:24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '3956ce16-4649-46a9-95da-337953436479')
            column(name: 'df_scenario_uid', value: '095f3df4-c8a7-4dba-9a8f-7dce0b61c40a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2018-03-30 17:47:24')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2018-03-30 17:47:24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '7459ee74-363b-11e8-b467-0ed5f89f718b')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2018-03-30 17:47:24')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2018-03-30 17:47:24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '8f83dfce-363c-11e8-b467-0ed5f89f718b')
            column(name: 'df_usage_batch_uid', value: '3f46981e-e85a-4786-9b60-ab009c4358e7')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2018-03-30 17:47:24')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2018-03-30 17:47:24')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '3956ce16-4649-46a9-95da-337953436479')
            column(name: 'df_usage_batch_uid', value: '4eff2685-4895-45a1-a886-c41a0f98204b')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2018-03-30 17:47:24')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2018-03-30 17:47:24')
        }

        rollback ""
    }
}
