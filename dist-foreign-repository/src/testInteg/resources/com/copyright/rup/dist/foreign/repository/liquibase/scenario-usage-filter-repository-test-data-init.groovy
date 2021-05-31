databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-03-05-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for ScenarioUsageFilterRepositoryIntegrationTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e1c64cac-3f2b-4105-8056-6660e1ec461a')
            column(name: 'name', value: 'Usage batch name')
            column(name: 'rro_account_number', value: 7000813807)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-03-05')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '1000')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '40f97da2-79f6-4917-b683-1cfa0fccd669')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'name', value: 'Access Copyright, The Canadian Copyright Agency')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '2bb64bac-8526-438c-8676-974dd9305bac')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario')
        }

        rollback ""
    }
}
