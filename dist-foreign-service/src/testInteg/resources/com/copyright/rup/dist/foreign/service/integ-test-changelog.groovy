databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2017-03-28-00', author: 'Aliaksei Pchelnikau <aliaksei_pchelnikau@epam.com>') {
        comment('Inserting Usage Batch for integration tests')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '35000')
            column(name: 'updated_datetime', value: '2017-02-14 11:41:52.735531+03')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
                where "df_usage_batch_uid in ('56282dbc-2468-48d4-b926-93d3458a656a')"
            }
        }
    }
}
