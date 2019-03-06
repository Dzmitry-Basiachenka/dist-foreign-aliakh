databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-03-06-01', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testFindClassificationByWrWrkInst')

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '8d3b077a-44be-48b4-ad0e-bf5de1cf97b0')
            column(name: 'wr_wrk_inst', value: '11111111111')
            column(name: 'classification', value: 'NON-STM')
        }

        rollback ""
    }
}
