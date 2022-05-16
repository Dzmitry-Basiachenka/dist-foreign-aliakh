databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-05-13-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for findByFilter tests')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'c2778b06-fc08-43c9-a265-0e4773b35b35')
            column(name: 'name', value: 'ACL Fund Pool 202112')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '24426cee-7f6f-4b5d-a403-e2edd85f4884')
            column(name: 'df_acl_fund_pool_uid', value: 'c2778b06-fc08-43c9-a265-0e4773b35b35')
            column(name: 'detail_licensee_class_id', value: '2')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 500.00)
            column(name: 'gross_amount', value: 590.12)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '37a34d11-4be8-47e5-9a8d-a3f52aa59c69')
            column(name: 'name', value: 'MACL LDMT Fund Pool 202106')
            column(name: 'period', value: 202106)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '2261f2f5-5687-4278-a3c2-bb5bba28ae00')
            column(name: 'df_acl_fund_pool_uid', value: '37a34d11-4be8-47e5-9a8d-a3f52aa59c69')
            column(name: 'detail_licensee_class_id', value: '1')
            column(name: 'license_type', value: 'MACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 1300.26)
            column(name: 'gross_amount', value: 1510.5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'f4294433-a001-466e-ab13-6a4c1dba8442')
            column(name: 'name', value: 'VGW LDMT Fund Pool 201906')
            column(name: 'period', value: 201906)
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_manual', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '5ac16130-4da2-4aa7-a946-8b1ea926d0e7')
            column(name: 'df_acl_fund_pool_uid', value: 'f4294433-a001-466e-ab13-6a4c1dba8442')
            column(name: 'detail_licensee_class_id', value: '1')
            column(name: 'license_type', value: 'VGW')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 5100.26)
            column(name: 'gross_amount', value: 5550.51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'c9ee4f80-b64a-4362-ab20-1b3a09a0d9e9')
            column(name: 'df_acl_fund_pool_uid', value: 'f4294433-a001-466e-ab13-6a4c1dba8442')
            column(name: 'detail_licensee_class_id', value: '3')
            column(name: 'license_type', value: 'VGW')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 100.26)
            column(name: 'gross_amount', value: 550.51)
        }

        rollback {
            dbRollback
        }
    }
}
