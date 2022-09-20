databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-11-30-06', author: 'Uladzislau Shalamitski <ushalamitsski@copyright.com>') {
        comment('Inserting test data for testInsertOrUpdateWithExistingClassification')

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '8d3b077a-44be-48b4-ad0e-bf5de1cf97b0')
            column(name: 'wr_wrk_inst', value: 111111111)
            column(name: 'classification', value: 'NON-STM')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2019-02-01 00:00:00.000-0000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '013f6bb1-40b7-4d11-ba53-d54e9d67e61f')
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
