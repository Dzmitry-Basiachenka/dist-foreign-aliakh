databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-02-10-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testInsertDetail')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '6d38454b-ce71-4b0e-8ecf-436d23dc6c3e')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL fund pool to verify details inserting')
            column(name: 'created_datetime', value: '2020-01-03 11:00:00-04')
            column(name: 'created_by_user', value: 'coordinator@copyright.com')
        }

        rollback {
            dbRollback
        }
    }
}
