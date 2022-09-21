databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-03-31-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testIsGrantDetailExist')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'e8d4ac06-e8e5-40b7-ade1-9a5dae1f1311')
            column(name: 'name', value: 'ACL Grant Set 2022')
            column(name: 'grant_period', value: 202206)
            column(name: 'periods', value: '[202206]')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '2f904dd9-f8d2-4512-8a41-bfaac1f93872')
            column(name: 'df_acl_grant_set_uid', value: 'e8d4ac06-e8e5-40b7-ade1-9a5dae1f1311')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'f3fdb968-c8be-4366-b476-4ae87da12998')
            column(name: 'df_acl_grant_set_uid', value: 'e8d4ac06-e8e5-40b7-ade1-9a5dae1f1311')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'is_eligible', value: false)
        }

        rollback {
            dbRollback
        }
    }
}
