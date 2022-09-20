databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2017-12-13-01', author: 'Uladzislau_Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for testFindByScenarioId, testDeleteByScenarioId')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'name', value: 'Scenario name 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '8a06905f-37ae-4e1f-8550-245277f8165c')
            column(name: 'name', value: 'Scenario name 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 2')
        }

        rollback {
            dbRollback
        }
    }
}
