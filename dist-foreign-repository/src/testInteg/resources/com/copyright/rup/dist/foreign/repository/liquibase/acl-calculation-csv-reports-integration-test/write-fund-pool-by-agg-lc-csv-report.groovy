databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-03-24-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testWriteAclFundPoolByAggLcCsvReport test')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '9be3e7f9-a48a-4d23-86bb-b1dc5054e4bd')
            column(name: 'name', value: 'ACL Fund Pool 202112')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '7fe444eb-82de-43b7-91ee-738fbc67a930')
            column(name: 'df_acl_fund_pool_uid', value: '9be3e7f9-a48a-4d23-86bb-b1dc5054e4bd')
            column(name: 'detail_licensee_class_id', value: '2')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 100.00)
            column(name: 'gross_amount', value: 120.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '418d8499-9d8f-49c3-b625-53e805d00f01')
            column(name: 'df_acl_fund_pool_uid', value: '9be3e7f9-a48a-4d23-86bb-b1dc5054e4bd')
            column(name: 'detail_licensee_class_id', value: '3')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 500.00)
            column(name: 'gross_amount', value: 590.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '24426cee-7f6f-4b5d-a403-e2edd85f4884')
            column(name: 'df_acl_fund_pool_uid', value: '9be3e7f9-a48a-4d23-86bb-b1dc5054e4bd')
            column(name: 'detail_licensee_class_id', value: '5')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 1500.00)
            column(name: 'gross_amount', value: 1880.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '1ab6de5a-f4cd-43b6-b2d3-f4b6b5a1bc7a')
            column(name: 'name', value: 'MACL Fund Pool 202106')
            column(name: 'period', value: 202106)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '7fabe420-b1cf-4c04-a312-41915ed23679')
            column(name: 'df_acl_fund_pool_uid', value: '1ab6de5a-f4cd-43b6-b2d3-f4b6b5a1bc7a')
            column(name: 'detail_licensee_class_id', value: '1')
            column(name: 'license_type', value: 'MACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 40.00)
            column(name: 'gross_amount', value: 48.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'fde6559d-55df-43cc-8250-21ae4a6ac249')
            column(name: 'df_acl_fund_pool_uid', value: '1ab6de5a-f4cd-43b6-b2d3-f4b6b5a1bc7a')
            column(name: 'detail_licensee_class_id', value: '8')
            column(name: 'license_type', value: 'MACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 13000.00)
            column(name: 'gross_amount', value: 15114.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'a0bc955a-0c27-4234-9bf0-10301ced7c9b')
            column(name: 'df_acl_fund_pool_uid', value: '1ab6de5a-f4cd-43b6-b2d3-f4b6b5a1bc7a')
            column(name: 'detail_licensee_class_id', value: '29')
            column(name: 'license_type', value: 'MACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 6500.00)
            column(name: 'gross_amount', value: 7210.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'aa4c03af-6af3-49b5-9e67-ea8ec7bc6745')
            column(name: 'name', value: 'VGW LDMT Fund Pool 201906')
            column(name: 'period', value: 201906)
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_manual', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '6b9c626f-0c1c-49b1-873c-3cd1b4294fee')
            column(name: 'df_acl_fund_pool_uid', value: 'aa4c03af-6af3-49b5-9e67-ea8ec7bc6745')
            column(name: 'detail_licensee_class_id', value: '1')
            column(name: 'license_type', value: 'VGW')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: true)
            column(name: 'net_amount', value: 5100.00)
            column(name: 'gross_amount', value: 5550.00)
        }

        rollback {
            dbRollback
        }
    }
}
