databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-02-09-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testDeleteUsageBatch')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '10f425fa-8c2b-4a2f-9527-61533b79de39')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'updated_datetime', value: '2017-02-14 11:41:52.735531+03')
        }

        rollback {
            dbRollback
        }
    }
}
