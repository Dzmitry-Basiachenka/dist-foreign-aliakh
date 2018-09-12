databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-09-12-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment("B-45044 FDA: Process Post-Distribution Details: add column lm_detail_id to the table df_usage_archive")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'lm_detail_id', type: 'VARCHAR(255)', remarks: 'The identifier of LM detail')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'lm_detail_id')
        }
    }
}
