databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-01-31-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testIsGrantSetExist')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '384a380e-c6ef-4af2-a282-96f4b1570fdd')
            column(name: 'name', value: 'ACL Grant Set 2021')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
