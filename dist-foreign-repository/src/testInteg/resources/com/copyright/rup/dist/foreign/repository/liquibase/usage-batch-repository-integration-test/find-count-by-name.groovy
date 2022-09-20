databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-02-09-07', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindCountByName')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b47ebcdb-840b-4bf9-9197-21ea57b4c963')
            column(name: 'name', value: 'JAACC_11Dec16')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'updated_datetime', value: '2017-02-11 11:41:52.735531+03')
        }

        rollback {
            dbRollback
        }
    }
}
