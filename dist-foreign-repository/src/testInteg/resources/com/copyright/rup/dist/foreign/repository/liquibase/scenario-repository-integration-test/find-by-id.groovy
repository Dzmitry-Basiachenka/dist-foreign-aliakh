databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-06-16-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindById test')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'dc60e68e-8167-4cf5-90d7-2f7c5d9e0389 ')
            column(name: 'name', value: 'Test scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Test scenario description')
        }

        rollback {
            dbRollback
        }
    }
}
