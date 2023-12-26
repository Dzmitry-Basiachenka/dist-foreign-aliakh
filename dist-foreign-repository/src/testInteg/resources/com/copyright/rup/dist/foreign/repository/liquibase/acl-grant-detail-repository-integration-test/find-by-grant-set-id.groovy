databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-12-26-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindByGrantSetId')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '80a935c4-27ce-440d-8c91-9b49d830b85b')
            column(name: 'name', value: 'ACL Grant Set 2022')
            column(name: 'grant_period', value: 202206)
            column(name: 'periods', value: '[202206]')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '4e15747c-e145-4e92-8348-cd15e9c2f39b')
            column(name: 'df_acl_grant_set_uid', value: '80a935c4-27ce-440d-8c91-9b49d830b85b')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: false)
            column(name: 'payee_account_number', value: 7001645719)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '501f66a1-2bca-4084-bb89-65a9fbe01f3f')
            column(name: 'df_acl_grant_set_uid', value: '80a935c4-27ce-440d-8c91-9b49d830b85b')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'is_eligible', value: false)
            column(name: 'payee_account_number', value: 2000155939)
        }

        rollback {
            dbRollback
        }
    }
}
