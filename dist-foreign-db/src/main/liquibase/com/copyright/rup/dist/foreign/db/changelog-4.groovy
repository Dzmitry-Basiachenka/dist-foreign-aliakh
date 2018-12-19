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

    changeSet(id: '2018-12-19-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-47645 Tech Debt: FDA: Implement Liquibase script to add primary key constraint " +
                "to the column df_rightsholder_uid instead of the column rh_account_number")

        dropPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_rightsholder',
                constraintName: 'pk_rh_account_number')

        dropUniqueConstraint(schemaName: dbAppsSchema, tableName: 'df_rightsholder',
                columnNames: 'df_rightsholder_uid', constraintName: 'uk_df_rightsholder')

        addPrimaryKey(tablespace: dbIndexTablespace, schemaName: dbAppsSchema, tableName: 'df_rightsholder',
                columnNames: 'df_rightsholder_uid', constraintName: 'pk_df_rightsholder')

        rollback {
            dropPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_rightsholder',
                    constraintName: 'pk_df_rightsholder')

            addUniqueConstraint(schemaName: dbAppsSchema, tableName: 'df_rightsholder',
                    columnNames: 'df_rightsholder_uid', constraintName: 'uk_df_rightsholder')

            addPrimaryKey(tablespace: dbIndexTablespace, schemaName: dbAppsSchema, tableName: 'df_rightsholder',
                    columnNames: 'rh_account_number', constraintName: 'pk_rh_account_number')
        }
    }
}
