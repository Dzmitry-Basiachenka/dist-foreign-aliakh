databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2018-02-22-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testReceivePaidSplitUsagesFromLm')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '4924da00-ee87-41b3-9aed-caa5c5ba94f1')
            column(name: 'name', value: 'Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c392b841-33a6-4268-861a-2c4334a7fabc')
            column(name: 'name', value: 'Paid batch')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'e783caf7-586d-434c-baaa-2ddf09afee77')
            column(name: 'df_usage_batch_uid', value: 'c392b841-33a6-4268-861a-2c4334a7fabc')
            column(name: 'df_scenario_uid', value: '4924da00-ee87-41b3-9aed-caa5c5ba94f1')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 7001832491)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'net_amount', value: 840.00)
            column(name: 'service_fee_amount', value: 160.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e783caf7-586d-434c-baaa-2ddf09afee77')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 6000)
        }

        rollback {
            dbRollback
        }
    }
}
