databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-05-17-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testIsLdmtDetailExist')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '6340cdfc-8fdc-4c5b-b33d-50243819fd5b')
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
            column(name: 'license_type', value: 'MACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'net_amount', value: 561345.24)
            column(name: 'gross_amount', value: 734220.41)
            column(name: 'is_ldmt', value: false)
        }

        rollback {
            dbRollback
        }
    }
}
