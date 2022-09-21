databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-05-14-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserting test data for testFindAll')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '2d46b574-c1f3-4322-8b45-e322574bf057')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '5ca53d80-b355-4d22-b798-6acdbd43d3ac')
            column(name: 'df_acl_fund_pool_uid', value: '2d46b574-c1f3-4322-8b45-e322574bf057')
            column(name: 'detail_licensee_class_id', value: '1')
            column(name: 'license_type', value: 'MACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 1300.26)
            column(name: 'gross_amount', value: 1510.5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: 'd9d01766-3bab-45e4-9899-540e2fca07df')
            column(name: 'df_acl_fund_pool_uid', value: '2d46b574-c1f3-4322-8b45-e322574bf057')
            column(name: 'detail_licensee_class_id', value: '1')
            column(name: 'license_type', value: 'MACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 1100.26)
            column(name: 'gross_amount', value: 1200.5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '037a9464-fec3-43a2-a71e-711c620100d7')
            column(name: 'name', value: 'ACL Fund Pool 202206')
            column(name: 'period', value: 202206)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '9c235fce-b1d5-43b2-b5b9-a4ad509a1e1a')
            column(name: 'df_acl_fund_pool_uid', value: '037a9464-fec3-43a2-a71e-711c620100d7')
            column(name: 'detail_licensee_class_id', value: '2')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 500.00)
            column(name: 'gross_amount', value: 590.12)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '7ff52fb7-bf85-4498-a360-9ccf0c7300ad')
            column(name: 'df_acl_fund_pool_uid', value: '037a9464-fec3-43a2-a71e-711c620100d7')
            column(name: 'detail_licensee_class_id', value: '2')
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'is_ldmt', value: false)
            column(name: 'net_amount', value: 500.00)
            column(name: 'gross_amount', value: 590.12)
        }

        rollback {
            dbRollback
        }
    }
}
