databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-02-05-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testAaclFundPoolExists')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_aacl') {
            column(name: 'df_fund_pool_aacl_uid', value: '5376a3c0-23df-41f9-b2fb-21de6d2fb707')
            column(name: 'name', value: 'fund_pool_name 100%')
        }
    }
}
