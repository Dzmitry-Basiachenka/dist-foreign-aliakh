databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-03-31-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testIsAclUsageBatchExist')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '6b81ec70-2979-44dc-8d2c-3ae5fb3113f2')
            column(name: 'name', value: 'ACL Usage Batch 2021')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
