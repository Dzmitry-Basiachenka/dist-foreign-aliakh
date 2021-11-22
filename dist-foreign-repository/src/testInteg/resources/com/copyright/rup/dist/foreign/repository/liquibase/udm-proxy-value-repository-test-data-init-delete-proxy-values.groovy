databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-11-24-00', author: 'Alaiksandr Liakh <aliakh@copyright.com>') {
        comment('Inserts test data for testDeleteProxyValues')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_proxy_value') {
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'period', value: 211012)
            column(name: 'content_unit_price', value: 100)
            column(name: 'content_unit_price_count', value: 1)
        }

        rollback {
            dbRollback
        }
    }
}
