databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-05-14-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserting test data for testFindPeriods')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'c0dea2ee-2215-4985-9483-cbd164bec054')
            column(name: 'name', value: 'ACL Fund Pool 202212 testFindPeriods')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '3690d238-321c-4312-8e2b-ff6c50ed5a9c')
            column(name: 'name', value: 'ACL Fund Pool 202206 test FindPeriods')
            column(name: 'period', value: 202206)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '0323f25f-fce5-4c7b-98b1-ab9a468b9014')
            column(name: 'name', value: 'The second ACL Fund Pool 202212 testFindPeriods')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
