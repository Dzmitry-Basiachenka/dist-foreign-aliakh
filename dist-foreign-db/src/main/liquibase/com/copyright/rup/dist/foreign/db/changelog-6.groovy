databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-06-14-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment("B-52145 FDA: Send NTS details to LM: drop not-null constraint from market, market_period_from and market_period_to columns")

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market')
        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market_period_from')
        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market_period_to')

        rollback {
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market',
                    columnDataType: 'VARCHAR(200)')
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market_period_from',
                    columnDataType: 'NUMERIC(4,0)')
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market_period_to',
                    columnDataType: 'NUMERIC(4,0)')
        }
    }
}
