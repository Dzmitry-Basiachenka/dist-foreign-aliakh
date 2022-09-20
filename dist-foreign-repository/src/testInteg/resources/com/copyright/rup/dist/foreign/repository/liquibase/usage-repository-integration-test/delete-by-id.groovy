databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2018-12-03-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testDeleteById()')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '8a901f85-0949-40b8-9176-378d1a61afb8')
            column(name: 'name', value: 'FAS batch 1')
            column(name: 'rro_account_number', value: 2000017011)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3cf274c5-8eac-4d4a-96be-5921ae026840')
            column(name: 'df_usage_batch_uid', value: '8a901f85-0949-40b8-9176-378d1a61afb8')
            column(name: 'product_family', value: 'NTS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: 1000009523)
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3cf274c5-8eac-4d4a-96be-5921ae026840')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f5eb98ce-ab59-44c8-9a50-1afea2b5ae15')
            column(name: 'df_usage_batch_uid', value: '8a901f85-0949-40b8-9176-378d1a61afb8')
            column(name: 'product_family', value: 'NTS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: 1000009524)
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f5eb98ce-ab59-44c8-9a50-1afea2b5ae15')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 100)
        }

        rollback {
            dbRollback
        }
    }
}
