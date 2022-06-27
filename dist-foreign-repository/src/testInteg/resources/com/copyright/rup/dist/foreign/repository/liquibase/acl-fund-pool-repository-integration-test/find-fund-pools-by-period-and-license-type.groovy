databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-05-14-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserting test data for testFindAll')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '7c3b7ccf-593a-43a5-86d6-83471a073bdc')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'cf9319b3-cd2e-46df-94b6-75acc6ba918b')
            column(name: 'name', value: 'ACL Fund Pool 202206')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
