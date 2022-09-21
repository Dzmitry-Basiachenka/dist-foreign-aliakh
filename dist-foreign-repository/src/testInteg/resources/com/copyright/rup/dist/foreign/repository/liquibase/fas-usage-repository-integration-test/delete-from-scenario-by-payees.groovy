databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-10-17-00', author: 'Uladzislau Shalamitski <ushalmitski@copyright.com>') {
        comment('Insert test data for testDeleteFromScenarioByPayees')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '9905f006-a3e1-4061-b3d4-e7ece191103f')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'bc4076cc-7554-4575-a03e-d4f4b3b76ca9')
            column(name: 'rh_account_number', value: 1000009523)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'edbcc8b3-8fa4-4c58-9244-a91627cac7a9')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'FAS2 scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '32daf15a-eb82-4b9e-862d-c647e72f76ca')
            column(name: 'name', value: 'FAS2 batch 1')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1497a662-f9d5-4192-922a-4bcb466e46cf')
            column(name: 'rh_account_number', value: 7000813806)
            column(name: 'name', value: 'CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'f3bb9d31-e305-4979-8591-bfaa2f930c90')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b5def044-259d-43fe-90d1-69a67e3abbd5')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 35000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7234feb4-a59e-483b-985a-e8de2e3eb190')
            column(name: 'df_usage_batch_uid', value: '32daf15a-eb82-4b9e-862d-c647e72f76ca')
            column(name: 'df_scenario_uid', value: 'edbcc8b3-8fa4-4c58-9244-a91627cac7a9')
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
            column(name: 'df_usage_fas_uid', value: '7234feb4-a59e-483b-985a-e8de2e3eb190')
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
            column(name: 'df_usage_uid', value: '582c86e2-213e-48ad-a885-f9ff49d48a69')
            column(name: 'df_usage_batch_uid', value: '32daf15a-eb82-4b9e-862d-c647e72f76ca')
            column(name: 'df_scenario_uid', value: 'edbcc8b3-8fa4-4c58-9244-a91627cac7a9')
            column(name: 'wr_wrk_inst', value: 773904752)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'payee_account_number', value: 7000813806)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 84.00)
            column(name: 'service_fee_amount', value: 16.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '582c86e2-213e-48ad-a885-f9ff49d48a69')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2019)
            column(name: 'reported_value', value: 100.00)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '730d7964-f399-4971-9403-dbedc9d7a180')
            column(name: 'df_usage_batch_uid', value: '32daf15a-eb82-4b9e-862d-c647e72f76ca')
            column(name: 'df_scenario_uid', value: 'edbcc8b3-8fa4-4c58-9244-a91627cac7a9')
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
            column(name: 'df_usage_fas_uid', value: '730d7964-f399-4971-9403-dbedc9d7a180')
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
