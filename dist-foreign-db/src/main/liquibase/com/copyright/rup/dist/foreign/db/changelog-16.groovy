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
}
