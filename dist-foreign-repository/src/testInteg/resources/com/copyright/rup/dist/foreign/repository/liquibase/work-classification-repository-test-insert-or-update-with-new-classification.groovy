databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-11-30-07', author: 'Uladzislau Shalamitski <ushalamitsski@copyright.com>') {
        comment('Inserting test data for testInsertOrUpdateWithNewClassification')

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '013f6bb1-40b5-4d11-ba53-d54e9d67e61f')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'classification', value: 'NON-STM')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2019-02-01 00:00:00.000-0000')
        }

        rollback {
            dbRollback
        }
    }
}
