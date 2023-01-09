databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-12-28-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment("B-68577 FDA: Load ACLCI fund pool: add aclci_fields column to df_fund_pool")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'aclci_fields', type: 'JSONB', remarks: 'The fields of ACLCI fund pool')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_fund_pool', columnName: 'aclci_fields')
        }
    }
}
