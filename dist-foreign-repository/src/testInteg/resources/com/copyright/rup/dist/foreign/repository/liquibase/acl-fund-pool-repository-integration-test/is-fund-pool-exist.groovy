databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-04-21-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testIsFundPoolExist')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'ea727865-e98d-4357-93e9-f82482e11a40')
            column(name: 'name', value: 'ACL Fund Pool 2021')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
