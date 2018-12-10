databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-12-04-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: " +
                "add fund_pool column into df_usage_batch table, " +
                "remove not-null constraint from fiscal_year column from df_usage_batch table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'fund_pool', type: 'JSONB', remarks: 'The fund pool')
        }

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch',
            columnName: 'fiscal_year', columnDataType: 'NUMERIC(4,0)')

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch', columnName: 'fund_pool')

            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch',
                columnName: 'fiscal_year', columnDataType: 'NUMERIC(4,0)')
        }
    }

    changeSet(id: '2018-12-10-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: " +
                "add not-null constraint to fiscal_year column in df_usage_batch table")

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch',
                columnName: 'fiscal_year', columnDataType: 'NUMERIC(4,0)')

        rollback {
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch',
                    columnName: 'fiscal_year', columnDataType: 'NUMERIC(4,0)')
        }
    }
}
