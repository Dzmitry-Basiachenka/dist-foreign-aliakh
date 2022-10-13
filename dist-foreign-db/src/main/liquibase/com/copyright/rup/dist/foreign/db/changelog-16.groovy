databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-10-05-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("B-73116 FDA: CUP Flag: add content_unit_price_flag column to df_udm_value and df_udm_value_baseline tables")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'content_unit_price_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Content Unit Price Flag') {
                constraints(nullable: false)
            }
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_value_baseline') {
            column(name: 'content_unit_price_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Content Unit Price Flag') {
                constraints(nullable: false)
            }
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_udm_value', columnName: 'content_unit_price_flag')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_udm_value_baseline', columnName: 'content_unit_price_flag')
        }
    }

    changeSet(id: '2022-10-13-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("B-75009 FDA: Liabilities by RH Report: add payee_account_number column to df_acl_share_detail table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'payee_account_number', type: 'NUMERIC(22,0)', remarks: 'The payee account number')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail', columnName: 'payee_account_number')
        }
    }
}
