databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-11-30-04', author: 'Uladzislau Shalamitski <ushalamitsski@copyright.com>') {
        comment('Inserting test data for testFindClassificationByWrWrkInst')

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'ad3b077a-44be-48b4-ad0e-bf5de1cf97b0')
            column(name: 'wr_wrk_inst', value: 1000009522)
            column(name: 'classification', value: 'NON-STM')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2019-02-01 00:00:00.000-0000')
        }

        rollback {
            dbRollback
        }
    }
}
