databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-08-05-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserting test data for copyGrantDetailsByGrantSetId')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'ba787989-32a5-425c-b0b8-03392554bf2d')
            column(name: 'name', value: 'ACL Grant Set 2022')
            column(name: 'grant_period', value: 202206)
            column(name: 'periods', value: '[202206]')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '82fd9e08-b7e2-416e-b4dc-e40e6c331e68')
            column(name: 'df_acl_grant_set_uid', value: 'ba787989-32a5-425c-b0b8-03392554bf2d')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'manual_upload_flag', value: false)
            column(name: 'payee_account_number', value: 2000123124)
            column(name: 'created_by_user', value: 'auser@copyright.com')
            column(name: 'updated_by_user', value: 'auser@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'ce1462d6-c573-4f95-94e5-c295fa6e1525')
            column(name: 'name', value: 'ACL Grant Set 2022 new')
            column(name: 'grant_period', value: 202206)
            column(name: 'periods', value: '[202206]')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
