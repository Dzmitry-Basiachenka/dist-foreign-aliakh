databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-06-27-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testFindAll')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '46b1d7b9-3355-491d-b6b4-ca4a1bb723bf')
            column(name: 'name', value: 'ACL Usage Batch 2022')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
            column(name: 'updated_datetime', value: '2022-12-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'c3642a01-5129-4d09-9dd1-1c3c497fcc43')
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
