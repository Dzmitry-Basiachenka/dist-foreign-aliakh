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

    changeSet(id: '2023-03-07-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("B-77080 FDA: Show Reported Standard Number and Reported Title on UI & Exports: add reported_standard_number column to df_usage_fas table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'reported_standard_number', type: 'VARCHAR(1000)', remarks: 'The reported standard number')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_fas', columnName: 'reported_standard_number')
        }
    }
}
