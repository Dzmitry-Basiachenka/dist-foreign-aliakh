databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-10-16-00', author: 'Uladzislau Shalamitski <ushalmitski@copyright.com>') {
        comment('Insert test data for testFindPayeeTotalHoldersByScenarioFilter, testFindPayeeTotalHoldersByScenarioFilterAndSearch,' +
                'testFindPayeeTotalHoldersByScenarioFilterAndThreshold and testFindPayeeTotalHoldersByScenarioFilterAndParticipationStatus')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5bcf2c37-2f32-48e9-90fe-c9d75298eeed')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8a0dbf78-d9c9-49d9-a895-05f55cfc8329')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '9905f006-a3e1-4061-b3d4-e7ece191103f')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05c4714b-291d-4e38-ba4a-35307434acfb')
            column(name: 'rh_account_number', value: 7000813806)
            column(name: 'name', value: 'CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'FAS2 scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'name', value: 'FAS2 batch 1')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '30787462-b66a-4679-a2bc-1b89abd6ea66')
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '30787462-b66a-4679-a2bc-1b89abd6ea66')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2019)
            column(name: 'reported_value', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5e3b4c6c-fe20-4a16-bbb6-9dc61b6c1eed')
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '2008902112317645XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 84.00)
            column(name: 'service_fee_amount', value: 16.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5e3b4c6c-fe20-4a16-bbb6-9dc61b6c1eed')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2019)
            column(name: 'reported_value', value: 100.00)
            column(name: 'is_rh_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '77dce0ba-3a7c-4162-8264-69d1b973e7ac')
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'wr_wrk_inst', value: 12318778798)
            column(name: 'work_title', value: 'Future of medicine')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'payee_account_number', value: 7000813806)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '3008902112317645XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '77dce0ba-3a7c-4162-8264-69d1b973e7ac')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2019)
            column(name: 'reported_value', value: 100.00)
            column(name: 'is_payee_participating_flag', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
