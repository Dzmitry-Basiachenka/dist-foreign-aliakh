databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-07-06-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testExecuteInternal test')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '75e057ac-7c24-4ae7-a0f5-aa75ea0895e6')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'name', value: 'Zoological Society of Pakistan [T]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '1e4b06e6-7ade-4535-aa82-4f6c09a01b14')
            column(name: 'name', value: 'CADRA_06Jun22')
            column(name: 'rro_account_number', value: 1000000322)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-07-06')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'gross_amount', value: 100)
            column(name: 'initial_usages_count', value: 1)
        }

        rollback {
            dbRollback
        }
    }
}
