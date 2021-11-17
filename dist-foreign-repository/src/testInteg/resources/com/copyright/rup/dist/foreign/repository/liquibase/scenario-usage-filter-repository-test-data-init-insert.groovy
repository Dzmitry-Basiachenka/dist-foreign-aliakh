databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-03-05-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testInsert')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '2bb64bac-8526-438c-8676-974dd9305bac')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario')
        }

        rollback {
            dbRollback
        }
    }
}
