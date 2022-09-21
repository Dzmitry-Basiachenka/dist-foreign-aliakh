databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-07-22-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testFindDetailDtosByFundPoolId test')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '8c2e21f0-c1f8-4bd8-8676-968e98617480')
            column(name: 'name', value: 'ACL Fund Pool 202112')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'e029c493-6243-4e21-b8d3-b5240fb54c4f')
            column(name: 'df_acl_fund_pool_uid', value: '8c2e21f0-c1f8-4bd8-8676-968e98617480')
            column(name: 'detail_licensee_class_id', value: '2')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 500.00)
            column(name: 'gross_amount', value: 590.12)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'a2b2cac9-051b-493e-9582-a4972faf5511')
            column(name: 'df_acl_fund_pool_uid', value: '8c2e21f0-c1f8-4bd8-8676-968e98617480')
            column(name: 'detail_licensee_class_id', value: '2')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 55029.02)
            column(name: 'gross_amount', value: 66912.12)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '846db57a-362f-493d-930c-e97ec15866f1')
            column(name: 'df_acl_fund_pool_uid', value: '8c2e21f0-c1f8-4bd8-8676-968e98617480')
            column(name: 'detail_licensee_class_id', value: '12')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 9992.1)
            column(name: 'gross_amount', value: 12861.56)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '37f5b125-c682-4a4e-b162-238df195a524')
            column(name: 'name', value: 'MACL LDMT Fund Pool 202106')
            column(name: 'period', value: 202106)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '88dceb96-df77-4bbd-8a47-cf3d7569da85')
            column(name: 'df_acl_fund_pool_uid', value: '37f5b125-c682-4a4e-b162-238df195a524')
            column(name: 'detail_licensee_class_id', value: '1')
            column(name: 'license_type', value: 'MACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 1300.26)
            column(name: 'gross_amount', value: 1510.5)
        }

        rollback {
            dbRollback
        }
    }
}
