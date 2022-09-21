databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-01-31-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindAll')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '8e5930c0-318c-4cf3-bde7-452a2d572f03')
            column(name: 'name', value: 'ACL Grant Set 2021')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'updated_datetime', value: '2021-12-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'b4f502c7-583f-4bb8-aa37-7d60db3a9cb1')
            column(name: 'name', value: 'ACL Grant Set 2020')
            column(name: 'grant_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
            column(name: 'updated_datetime', value: '2020-12-31T00:00:00-04:00')
        }

        rollback {
            dbRollback
        }
    }
}
