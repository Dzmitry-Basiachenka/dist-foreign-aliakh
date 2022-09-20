databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-05-13-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for findByFilter tests')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '506e8afa-36ce-4472-8132-9147676e2668')
            column(name: 'name', value: 'ACL Fund Pool 202112')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'b63d6874-38aa-4dbc-bbf9-ff0e860d50e5')
            column(name: 'df_acl_fund_pool_uid', value: '506e8afa-36ce-4472-8132-9147676e2668')
            column(name: 'detail_licensee_class_id', value: '2')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 500.00)
            column(name: 'gross_amount', value: 590.12)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '8d9cf490-c5b0-4804-85dd-94b2bc0a4341')
            column(name: 'name', value: 'MACL LDMT Fund Pool 202106')
            column(name: 'period', value: 202106)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '8fb161fb-3bbf-46e7-ad16-150c64598180')
            column(name: 'df_acl_fund_pool_uid', value: '8d9cf490-c5b0-4804-85dd-94b2bc0a4341')
            column(name: 'detail_licensee_class_id', value: '1')
            column(name: 'license_type', value: 'MACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 1300.26)
            column(name: 'gross_amount', value: 1510.5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '5f74d83a-35d3-4b7e-8169-15b1d22ccc60')
            column(name: 'name', value: 'VGW LDMT Fund Pool 201906')
            column(name: 'period', value: 201906)
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_manual', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'ee8bee86-127e-4f36-af4c-771d59bc4e01')
            column(name: 'df_acl_fund_pool_uid', value: '5f74d83a-35d3-4b7e-8169-15b1d22ccc60')
            column(name: 'detail_licensee_class_id', value: '1')
            column(name: 'license_type', value: 'VGW')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 5100.26)
            column(name: 'gross_amount', value: 5550.51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'dbcad8cb-a228-4f1c-aa26-af7bd830c3bf')
            column(name: 'df_acl_fund_pool_uid', value: '5f74d83a-35d3-4b7e-8169-15b1d22ccc60')
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
