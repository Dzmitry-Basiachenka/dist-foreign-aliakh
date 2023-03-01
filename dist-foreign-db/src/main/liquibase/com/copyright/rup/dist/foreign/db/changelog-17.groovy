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

    changeSet(id: '2023-03-01-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment("B-76925 FDA: Deleted works column for editing wrWrkInsts: add work_deleted_flag column to df_acl_usage table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'work_deleted_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Work was soft delete from MDWMS') {
                constraints(nullable: false)
            }
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage', columnName: 'work_deleted_flag')
        }
    }
}
