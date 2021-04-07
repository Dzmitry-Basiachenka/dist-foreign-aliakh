databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-06-14-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment("B-52145 FDA: Send NTS details to LM: drop not-null constraint from market, market_period_from, " +
                "market_period_to and system_title columns")

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market')
        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market_period_from')
        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market_period_to')
        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'system_title')

        rollback {
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market',
                    columnDataType: 'VARCHAR(200)')
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market_period_from',
                    columnDataType: 'NUMERIC(4,0)')
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'market_period_to',
                    columnDataType: 'NUMERIC(4,0)')
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'system_title',
                    columnDataType: 'VARCHAR(2000)')
        }
    }

    changeSet(id: '2019-07-30-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-51660 Tech Debt: drop not-null constraint and default value from batch gross_amount column")

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch', columnName: 'gross_amount')

        dropDefaultValue(schemaName: dbAppsSchema, tableName: 'df_usage_batch', columnName: 'gross_amount')

        update(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'gross_amount', value: 'null')
            where "product_family = 'NTS'"
        }

        rollback {

            update(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
                column(name: 'gross_amount', value: '0.00')
                where "product_family = 'NTS'"
            }

            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch', columnName: 'gross_amount',
                    columnDataType: 'DECIMAL(38,2)')

            addDefaultValue(schemaName: dbAppsSchema, tableName: 'df_usage_batch', columnName: 'gross_amount', defaultValue: '0.00')
        }
    }
}
