databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-12-22-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testUpdatePayeeAccountNumber')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'ac16e4fd-ab85-4f6e-9ffe-f87c3b7f0467')
            column(name: 'name', value: 'ACL Grant Set 2022')
            column(name: 'grant_period', value: 202206)
            column(name: 'periods', value: '[202206]')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '018c6593-7eb4-4dc7-bc5e-f9a4b5fd7df8')
            column(name: 'df_acl_grant_set_uid', value: 'ac16e4fd-ab85-4f6e-9ffe-f87c3b7f0467')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: false)
        }

        rollback {
            dbRollback
        }
    }
}
