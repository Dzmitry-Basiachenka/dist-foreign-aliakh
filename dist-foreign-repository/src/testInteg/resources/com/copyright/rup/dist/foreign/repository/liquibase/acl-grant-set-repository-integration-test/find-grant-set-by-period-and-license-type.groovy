databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-06-27-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testFindGrantPeriods')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '9950ea35-41a4-48f5-9d14-b2182f771f66')
            column(name: 'name', value: 'ACL Grant Set_1')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'updated_datetime', value: '2021-12-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '1e06dd5e-6ab1-442a-95fc-65dec5a61658')
            column(name: 'name', value: 'ACL Grant Set_2')
            column(name: 'grant_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
            column(name: 'updated_datetime', value: '2020-12-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'f7a5bc79-2be2-4384-a113-34155828a4aa')
            column(name: 'name', value: 'ACL Grant Set_3')
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
