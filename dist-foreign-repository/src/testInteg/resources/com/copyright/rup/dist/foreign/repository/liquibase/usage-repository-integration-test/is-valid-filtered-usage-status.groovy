databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2018-12-10-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testIsValidFilteredUsageStatus')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'ee575916-f6d0-4c3c-b589-32663e0f4793')
            column(name: 'name', value: 'FAS batch 2')
            column(name: 'rro_account_number', value: 2000017011)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3adb01b0-6dc0-4f3c-ba71-c47a1f8d69b8')
            column(name: 'df_usage_batch_uid', value: 'ee575916-f6d0-4c3c-b589-32663e0f4793')
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
            column(name: 'df_usage_fas_uid', value: '3adb01b0-6dc0-4f3c-ba71-c47a1f8d69b8')
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
            column(name: 'df_usage_uid', value: '775ceaf9-125f-4387-b076-459eb4673d92')
            column(name: 'df_usage_batch_uid', value: 'ee575916-f6d0-4c3c-b589-32663e0f4793')
            column(name: 'product_family', value: 'NTS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
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
            column(name: 'df_usage_fas_uid', value: '775ceaf9-125f-4387-b076-459eb4673d92')
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
