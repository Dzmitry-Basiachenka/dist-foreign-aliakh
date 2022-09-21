databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-04-01-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testFindAll')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'dd559563-379d-4632-abea-922d2821746d')
            column(name: 'name', value: 'ACL Usage Batch 2022')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
            column(name: 'updated_datetime', value: '2022-12-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '446fba70-c15b-45ae-b53d-ba0de3dad0b5')
            column(name: 'name', value: 'ACL Usage Batch 2021')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: false)
            column(name: 'updated_datetime', value: '2021-12-31T00:00:00-04:00')
        }

        rollback {
            dbRollback
        }
    }
}
