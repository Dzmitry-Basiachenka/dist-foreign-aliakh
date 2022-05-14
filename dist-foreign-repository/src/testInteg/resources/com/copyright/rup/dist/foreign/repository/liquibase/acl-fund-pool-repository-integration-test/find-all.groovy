databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-05-14-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserting test data for testFindAll')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '2d46b574-c1f3-4322-8b45-e322574bf057')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '037a9464-fec3-43a2-a71e-711c620100d7')
            column(name: 'name', value: 'ACL Fund Pool 202206')
            column(name: 'period', value: 202206)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
