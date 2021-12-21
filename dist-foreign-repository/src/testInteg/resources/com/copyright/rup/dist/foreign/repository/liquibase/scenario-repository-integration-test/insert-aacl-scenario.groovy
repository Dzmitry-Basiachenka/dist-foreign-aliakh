databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-03-11-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testInsertAaclScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '95faddb9-27b6-422e-9de8-01f3aaa9c64d')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 1')
            column(name: 'total_amount', value: 10.95)
            column(name: 'created_datetime', value: '2020-01-03 11:00:00-04')
            column(name: 'created_by_user', value: 'coordinator@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '5ec2fbdf-29b2-4b42-b0eb-297e9dff53f3')
            column(name: 'df_fund_pool_uid', value: '95faddb9-27b6-422e-9de8-01f3aaa9c64d')
            column(name: 'df_aggregate_licensee_class_id', value: 108)
            column(name: 'gross_amount', value: 10.95)
        }

        rollback {
            dbRollback
        }
    }
}
