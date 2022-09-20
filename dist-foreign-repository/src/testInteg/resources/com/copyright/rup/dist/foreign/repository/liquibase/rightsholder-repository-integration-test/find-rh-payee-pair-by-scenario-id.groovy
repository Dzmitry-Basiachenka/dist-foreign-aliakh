databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-08-12-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testFindRhPayeePairByScenarioId')

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
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '435a1689-8dfd-4a94-8ef5-72e2c4e6f3fa')
            column(name: 'name', value: 'NTS Batch 2')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'f933e8cb-3795-44bb-bc6a-f59d5c37781d')
            column(name: 'name', value: 'NTS Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'NTS Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'e23c7078-7be9-4c35-8069-4f84d8744b61')
            column(name: 'df_scenario_uid', value: 'f933e8cb-3795-44bb-bc6a-f59d5c37781d')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'e23c7078-7be9-4c35-8069-4f84d8744b61')
            column(name: 'df_usage_batch_uid', value: '435a1689-8dfd-4a94-8ef5-72e2c4e6f3fa')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '88946f05-63d6-492d-8d98-a0d3d66d7a5c')
            column(name: 'df_usage_batch_uid', value: '435a1689-8dfd-4a94-8ef5-72e2c4e6f3fa')
            column(name: 'df_scenario_uid', value: 'f933e8cb-3795-44bb-bc6a-f59d5c37781d')
            column(name: 'rh_account_number', value: 7000813806)
            column(name: 'payee_account_number', value: 7000813806)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 400)
            column(name: 'service_fee', value: 0.16)
            column(name: 'service_fee_amount', value: 64)
            column(name: 'net_amount', value: 336)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '88946f05-63d6-492d-8d98-a0d3d66d7a5c')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '80898048-649a-47ae-9d16-eff7212a55e4')
            column(name: 'df_usage_batch_uid', value: '435a1689-8dfd-4a94-8ef5-72e2c4e6f3fa')
            column(name: 'df_scenario_uid', value: 'f933e8cb-3795-44bb-bc6a-f59d5c37781d')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'payee_account_number', value: 7000813806)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 350)
            column(name: 'service_fee', value: 0.16)
            column(name: 'service_fee_amount', value: 56)
            column(name: 'net_amount', value: 294)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '80898048-649a-47ae-9d16-eff7212a55e4')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c80f782c-8824-4101-93b7-9e786fbf8854')
            column(name: 'df_usage_batch_uid', value: '435a1689-8dfd-4a94-8ef5-72e2c4e6f3fa')
            column(name: 'df_scenario_uid', value: 'f933e8cb-3795-44bb-bc6a-f59d5c37781d')
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'payee_account_number', value: 7000813806)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 250)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 80)
            column(name: 'net_amount', value: 170)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c80f782c-8824-4101-93b7-9e786fbf8854')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
