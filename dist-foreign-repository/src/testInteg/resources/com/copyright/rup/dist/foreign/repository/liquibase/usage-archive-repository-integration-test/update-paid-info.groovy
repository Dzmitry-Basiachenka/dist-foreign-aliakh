databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-02-20-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testUpdatePaidInfo')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5bcf2c37-2f32-48e9-90fe-c9d75298eeed')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05c4714b-291d-4e38-ba4a-35307434acfb')
            column(name: 'rh_account_number', value: 7000813806)
            column(name: 'name', value: 'CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '98caae9b-2f20-4c6d-b2af-3190a1115c48')
            column(name: 'name', value: 'Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b58a3ec8-4294-47ae-a12a-83cfe748909b')
            column(name: 'name', value: 'Paid batch 1')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 185.60)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '7241b7e0-6ab8-4483-896d-fd485c574293')
            column(name: 'df_usage_batch_uid', value: 'b58a3ec8-4294-47ae-a12a-83cfe748909b')
            column(name: 'df_scenario_uid', value: '98caae9b-2f20-4c6d-b2af-3190a1115c48')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 92.80)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 12.80)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7241b7e0-6ab8-4483-896d-fd485c574293')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '32db57d1-9140-4912-af27-656c6956752d')
            column(name: 'df_usage_batch_uid', value: 'b58a3ec8-4294-47ae-a12a-83cfe748909b')
            column(name: 'df_scenario_uid', value: '98caae9b-2f20-4c6d-b2af-3190a1115c48')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 7000813806)
            column(name: 'payee_account_number', value: 7000813806)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 92.80)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 12.80)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '32db57d1-9140-4912-af27-656c6956752d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        rollback {
            dbRollback
        }
    }
}
