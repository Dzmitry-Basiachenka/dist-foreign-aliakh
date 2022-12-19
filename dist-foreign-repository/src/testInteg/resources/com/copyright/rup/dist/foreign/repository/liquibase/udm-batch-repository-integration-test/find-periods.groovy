databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-12-16-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testFindPeriods')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'bb8556c3-77b4-4777-a425-5bc04ddf7196')
            column(name: 'name', value: 'UDM Batch 2021 1')
            column(name: 'period', value: 202106)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'b3e3078d-790e-4951-8f7f-6b0c9769d4a9')
            column(name: 'name', value: 'UDM Batch 2021 2')
            column(name: 'period', value: 202112)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        rollback {
            dbRollback
        }
    }
}
