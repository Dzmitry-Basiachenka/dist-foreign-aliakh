databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-05-13-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testAddLdmtDetailsToFundPool')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'f89c0586-b406-4ad8-b22c-6c8f972914cb')
            column(name: 'name', value: 'ACL Fund Pool 2020')
            column(name: 'period', value: 202006)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '26c3de1a-4560-4db0-8f67-11874a49fe74')
            column(name: 'df_acl_fund_pool_uid', value: 'f89c0586-b406-4ad8-b22c-6c8f972914cb')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'net_amount', value: 450799.88)
            column(name: 'gross_amount', value: 634420.48)
            column(name: 'is_ldmt', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '5127f6ea-a576-4187-b858-5fe51b7cfac3')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'net_amount', value: 561345.24)
            column(name: 'gross_amount', value: 734220.41)
            column(name: 'is_ldmt', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '3b853cd2-da83-4900-917f-7e2d31b42390')
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'license_type', value: 'VGW')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'net_amount', value: 255235.85)
            column(name: 'gross_amount', value: 425246.53)
            column(name: 'is_ldmt', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '39edbdfd-d732-4a78-9ecd-6a756c9f5b93')
            column(name: 'name', value: 'ACL Fund Pool 2021')
            column(name: 'period', value: 202106)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'b2f01b15-2193-4d91-ae5b-0834452e4788')
            column(name: 'detail_licensee_class_id', value: 4)
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'net_amount', value: 354615.25)
            column(name: 'gross_amount', value: 576576.24)
            column(name: 'is_ldmt', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
