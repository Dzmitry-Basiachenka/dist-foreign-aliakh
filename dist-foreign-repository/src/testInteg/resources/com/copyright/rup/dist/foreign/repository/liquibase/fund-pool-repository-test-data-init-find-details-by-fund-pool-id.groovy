databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-02-06-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for findDetailsByFundPoolId')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'ce9c1258-6d29-4224-a4e6-6f03b6aeef53')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 1')
            column(name: 'total_amount', value: 31.20)
            column(name: 'created_datetime', value: '2020-01-02 11:00:00-04')
            column(name: 'created_by_user', value: 'coordinator@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '100ce91c-49c1-4197-9f7a-23a8210d5706')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 2')
            column(name: 'total_amount', value: 71.10)
            column(name: 'created_datetime', value: '2020-01-03 11:00:00-04')
            column(name: 'created_by_user', value: 'coordinator@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '44709581-fb7e-4d72-9427-dd5681f24fc5')
            column(name: 'df_fund_pool_uid', value: 'ce9c1258-6d29-4224-a4e6-6f03b6aeef53')
            column(name: 'df_aggregate_licensee_class_id', value: 108)
            column(name: 'gross_amount', value: 10.95)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'a6a2c928-297d-4584-9fb6-abf41c84e5e4')
            column(name: 'df_fund_pool_uid', value: 'ce9c1258-6d29-4224-a4e6-6f03b6aeef53')
            column(name: 'df_aggregate_licensee_class_id', value: 110)
            column(name: 'gross_amount', value: 20.25)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '50216473-2713-4b52-b40f-dfc2d2aef755')
            column(name: 'df_fund_pool_uid', value: '100ce91c-49c1-4197-9f7a-23a8210d5706')
            column(name: 'df_aggregate_licensee_class_id', value: 111)
            column(name: 'gross_amount', value: 30.35)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '4ff44b56-6b2f-4b00-8989-9bbc23796254')
            column(name: 'df_fund_pool_uid', value: '100ce91c-49c1-4197-9f7a-23a8210d5706')
            column(name: 'df_aggregate_licensee_class_id', value: 113)
            column(name: 'gross_amount', value: 40.75)
        }

        rollback {
            dbRollback
        }
    }
}
