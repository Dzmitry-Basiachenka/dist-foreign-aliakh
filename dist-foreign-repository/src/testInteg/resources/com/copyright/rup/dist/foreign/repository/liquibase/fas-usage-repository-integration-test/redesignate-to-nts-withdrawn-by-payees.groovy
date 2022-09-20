databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-10-17-01', author: 'Uladzislau Shalamitski <ushalmitski@copyright.com>') {
        comment('Insert test data for testRedisignateToNtsWithdrawnByPayees')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '767a2647-7e6e-4479-b381-e642de480863')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'FAS2 scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '452baaea-5c52-417c-9ee6-70ab014624cf')
            column(name: 'name', value: 'FAS2 batch 2')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '209a960f-5896-43da-b020-fc52981b9633')
            column(name: 'df_usage_batch_uid', value: '452baaea-5c52-417c-9ee6-70ab014624cf')
            column(name: 'df_scenario_uid', value: '767a2647-7e6e-4479-b381-e642de480863')
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
            column(name: 'df_usage_fas_uid', value: '209a960f-5896-43da-b020-fc52981b9633')
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
            column(name: 'df_usage_uid', value: '1ae671ca-ed5a-4d92-8ab6-a10a53d9884a')
            column(name: 'df_usage_batch_uid', value: '452baaea-5c52-417c-9ee6-70ab014624cf')
            column(name: 'df_scenario_uid', value: '767a2647-7e6e-4479-b381-e642de480863')
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
            column(name: 'df_usage_fas_uid', value: '1ae671ca-ed5a-4d92-8ab6-a10a53d9884a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2019)
            column(name: 'reported_value', value: 100.00)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '72f6abdb-c82d-4cee-aadf-570942cf0093')
            column(name: 'df_usage_batch_uid', value: '452baaea-5c52-417c-9ee6-70ab014624cf')
            column(name: 'df_scenario_uid', value: '767a2647-7e6e-4479-b381-e642de480863')
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
            column(name: 'df_usage_fas_uid', value: '72f6abdb-c82d-4cee-aadf-570942cf0093')
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

        rollback {
            dbRollback
        }
    }
}
