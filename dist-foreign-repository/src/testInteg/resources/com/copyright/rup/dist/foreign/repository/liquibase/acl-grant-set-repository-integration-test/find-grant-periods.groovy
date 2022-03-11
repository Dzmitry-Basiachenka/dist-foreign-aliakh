databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-03-10-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testFindGrantPeriods')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'a450d39a-55e9-44a3-ad46-582601fe0812')
            column(name: 'name', value: 'ACL Grant Set 2022')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'updated_datetime', value: '2021-12-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '3cefbf90-de21-4b8b-9606-77da50017d1c')
            column(name: 'name', value: 'ACL Grant Set 2020')
            column(name: 'grant_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
            column(name: 'updated_datetime', value: '2020-12-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'ddb17e36-6ae7-4bd6-9357-5227d9249d79')
            column(name: 'name', value: 'ACL Grant Set 2021')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: false)
            column(name: 'updated_datetime', value: '2020-12-31T00:00:00-04:00')
        }

        rollback {
            dbRollback
        }
    }
}
