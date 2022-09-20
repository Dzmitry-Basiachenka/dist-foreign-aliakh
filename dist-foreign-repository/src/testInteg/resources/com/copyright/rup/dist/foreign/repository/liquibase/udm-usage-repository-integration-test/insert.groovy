databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-04-29-01', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testInsert')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'aa5751aa-2858-38c6-b0d9-51ec0edfcf4f')
            column(name: 'name', value: 'UDM Batch 2021 1')
            column(name: 'period', value: 202106)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        rollback {
            dbRollback
        }
    }
}
