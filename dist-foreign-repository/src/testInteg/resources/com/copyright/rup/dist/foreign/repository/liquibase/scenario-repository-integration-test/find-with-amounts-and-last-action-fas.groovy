databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-11-17-03', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindArchivedWithAmountsAndLastActionForFasScenario')

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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd398d6d7-9cbd-4f83-8d5e-90a005bf26c2')
            column(name: 'name', value: 'NEW_01_JAN_2018')
            column(name: 'rro_account_number', value: 7000813800)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-01')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 1000)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'name', value: 'Scenario name 9')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 9')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-02-14 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'name', value: 'Scenario name 5')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'The description of scenario 5')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-02-09 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
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
            column(name: 'df_usage_fas_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '94839e3f-c69a-45f3-b236-a363408ef937')
            column(name: 'df_usage_batch_uid', value: 'd398d6d7-9cbd-4f83-8d5e-90a005bf26c2')
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'wr_wrk_inst', value: 180382915)
            column(name: 'work_title', value: 'High Performance Switching and Routing')
            column(name: 'system_title', value: 'High Performance Switching and Routing')
            column(name: 'rh_account_number', value: 1000159997)
            column(name: 'payee_account_number', value: 7001555529)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902992377654XX')
            column(name: 'number_of_copies', value: 190222)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'service_fee_amount', value: 320.00)
            column(name: 'net_amount', value: 680.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '94839e3f-c69a-45f3-b236-a363408ef937')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water')
            column(name: 'publisher', value: 'IEEE 015')
            column(name: 'publication_date', value: '2014-09-11')
            column(name: 'market', value: 'Play Market')
            column(name: 'market_period_from', value: 2014)
            column(name: 'market_period_to', value: 2018)
            column(name: 'author', value: 'Iñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 1000)
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
            column(name: 'action_type_ind', value: 'REJECTED')
            column(name: 'action_reason', value: 'Scenario rejected by manager')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '7459ee74-363b-11e8-b467-0ed5f89f718b')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '2c0df248-363e-11e8-b467-0ed5f89f718b')
            column(name: 'df_scenario_uid', value: '8a6a6b15-6922-4fda-b40c-5097fcbd256e')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '7459ee74-363b-11e8-b467-0ed5f89f718b')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
        }

        rollback {
            dbRollback
        }
    }
}
