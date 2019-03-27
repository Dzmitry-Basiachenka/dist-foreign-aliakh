databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-03-27-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: " +
                "Implement repository")

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'b5b64c3a-55d2-462e-b169-362dca6a4dd7')
            column(name: 'name', value: 'FAS Q1 2019')
            column(name: 'comment', value: 'some comment')
        }
    }
}
