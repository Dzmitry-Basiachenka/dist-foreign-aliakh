databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-08-21-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting data for testSalWorkflow')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '85f864f2-30a5-4215-ac4f-f1f541901218')
            column(name: 'rh_account_number', value: '1000000322')
            column(name: 'name', value: 'American College of Physicians - Journals')
        }

        rollback ""
    }
}
