databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-03-23-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testFundPoolsByPeriods')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '99300101-68c1-434b-b80e-469aa82eab94')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
            column(name: 'updated_datetime', value: '2022-11-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '284feea2-9c71-4b1a-80db-796207787780')
            column(name: 'df_acl_fund_pool_uid', value: '99300101-68c1-434b-b80e-469aa82eab94')
            column(name: 'detail_licensee_class_id', value: '1')
            column(name: 'license_type', value: 'MACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 1300.26)
            column(name: 'gross_amount', value: 1510.5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '2d3e1bd5-8dee-4957-812c-20ab67abf230')
            column(name: 'df_acl_fund_pool_uid', value: '99300101-68c1-434b-b80e-469aa82eab94')
            column(name: 'detail_licensee_class_id', value: '1')
            column(name: 'license_type', value: 'MACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 1100.26)
            column(name: 'gross_amount', value: 1200.5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'b9311704-0c34-499c-b527-974ad0ecd40e')
            column(name: 'name', value: 'ACL Fund Pool 202206')
            column(name: 'period', value: 202206)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: true)
            column(name: 'updated_datetime', value: '2022-04-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'b4990f50-49d4-4b0e-93aa-3be79f11a6f2')
            column(name: 'df_acl_fund_pool_uid', value: 'b9311704-0c34-499c-b527-974ad0ecd40e')
            column(name: 'detail_licensee_class_id', value: '2')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 500.00)
            column(name: 'gross_amount', value: 590.12)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'bc1254c4-29f1-4af3-a7a5-a54765c3497b')
            column(name: 'df_acl_fund_pool_uid', value: 'b9311704-0c34-499c-b527-974ad0ecd40e')
            column(name: 'detail_licensee_class_id', value: '2')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 500.00)
            column(name: 'gross_amount', value: 590.12)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '7f3be0e9-d951-426f-a628-e16254bb3430')
            column(name: 'name', value: 'ACL Fund Pool 202006')
            column(name: 'period', value: 202006)
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_manual', value: true)
            column(name: 'updated_datetime', value: '2020-05-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'a0ea8c7b-9ed9-4c40-ab67-48e995240a1d')
            column(name: 'df_acl_fund_pool_uid', value: '7f3be0e9-d951-426f-a628-e16254bb3430')
            column(name: 'detail_licensee_class_id', value: '2')
            column(name: 'license_type', value: 'VGW')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 16.00)
            column(name: 'gross_amount', value: 20.00)
        }

        rollback {
            dbRollback
        }
    }
}
