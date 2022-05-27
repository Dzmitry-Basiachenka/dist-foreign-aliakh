databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-05-27-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testExecuteInternal test')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '37338ed1-7083-45e2-a96b-5872a7de3a98')
            column(name: 'rh_account_number', value: 2000139286)
            column(name: 'name', value: '2HC [T]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'bf6bca99-f26d-4612-9eb5-d9ba554eacba')
            column(name: 'name', value: 'Test_RMS_get_rights')
            column(name: 'rro_account_number', value: 1000023401)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-26')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '03f307ac-81d1-4ab5-b037-9bd2ca899aab')
            column(name: 'df_usage_batch_uid', value: 'bf6bca99-f26d-4612-9eb5-d9ba554eacba')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1906011')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 100.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '03f307ac-81d1-4ab5-b037-9bd2ca899aab')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'reported_value', value: 100.00)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        rollback {
            dbRollback
        }
    }
}
