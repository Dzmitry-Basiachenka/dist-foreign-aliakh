databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-10-17-02', author: 'Uladzislau Shalamitski <ushalmitski@copyright.com>') {
        comment('Insert test data for testUpdateResearchedUsages')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'name', value: 'Works without WrWrkInst test')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '721ca627-09bc-4204-99f4-6acae415fa5d')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 10000.00)
            column(name: 'net_amount', value: 8400.00)
            column(name: 'service_fee_amount', value: 1600.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '721ca627-09bc-4204-99f4-6acae415fa5d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 10000.00)
            column(name: 'net_amount', value: 8400.00)
            column(name: 'service_fee_amount', value: 1600.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
        }

        rollback {
            dbRollback
        }
    }
}
