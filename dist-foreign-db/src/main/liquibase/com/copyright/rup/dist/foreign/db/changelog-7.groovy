databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-10-09-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-50006 FDA: Exclude details from FAS scenario at the Payee level: add is_payee_participating_flag column to df_usage table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'is_payee_participating_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Payee participating flag') {
                constraints(nullable: false)
            }
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'is_payee_participating_flag')
        }
    }
}
