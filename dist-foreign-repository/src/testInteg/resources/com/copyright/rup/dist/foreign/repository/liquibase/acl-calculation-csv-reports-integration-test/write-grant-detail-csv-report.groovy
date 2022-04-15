databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-03-16-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testWriteGrantDetailCsvReport, testWriteGrantDetailEmptyCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '09559680-12cd-4f27-b1b5-9b13f2e3af0d')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'name', value: 'Greenleaf Publishing')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '30e6b7aa-91ab-4453-979f-c35ee1a30e56')
            column(name: 'rh_account_number', value: 2000024497)
            column(name: 'name', value: 'Alexander Stille')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '8d39a96c-ac47-457d-9634-a7236c971d08')
            column(name: 'name', value: 'ACL Grant Set 2022 1')
            column(name: 'grant_period', value: 202206)
            column(name: 'periods', value: '[202206]')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '5a7db6d3-d587-4c21-8bea-d9df8654b05f')
            column(name: 'df_acl_grant_set_uid', value: '8d39a96c-ac47-457d-9634-a7236c971d08')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Print&Digital')
            column(name: 'wr_wrk_inst', value: 122820628)
            column(name: 'system_title', value: 'Scientific American')
            column(name: 'rh_account_number', value: 2000024497)
            column(name: 'is_eligible', value: false)
            column(name: 'manual_upload_flag', value: true)
            column(name: 'created_datetime', value: '2022-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2022-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'a09fafdd-88a4-4066-91ed-15809ddd3ebb')
            column(name: 'df_acl_grant_set_uid', value: '8d39a96c-ac47-457d-9634-a7236c971d08')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print&Digital')
            column(name: 'wr_wrk_inst', value: 122820628)
            column(name: 'system_title', value: 'Scientific American')
            column(name: 'rh_account_number', value: 2000024497)
            column(name: 'is_eligible', value: true)
            column(name: 'manual_upload_flag', value: false)
            column(name: 'created_datetime', value: '2022-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2022-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '533a2e2f-6c6f-4a05-93c2-23e47015c3a2')
            column(name: 'df_acl_grant_set_uid', value: '8d39a96c-ac47-457d-9634-a7236c971d08')
            column(name: 'grant_status', value: 'DENY')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'manual_upload_flag', value: false)
            column(name: 'created_datetime', value: '2022-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2022-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '66248a4e-d424-44f4-b04f-6b5092ae59bf')
            column(name: 'df_acl_grant_set_uid', value: '8d39a96c-ac47-457d-9634-a7236c971d08')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'manual_upload_flag', value: true)
            column(name: 'created_datetime', value: '2022-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2022-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'a8bf4a60-ab02-42db-afea-5f668a546158')
            column(name: 'name', value: 'ACL Grant Set 2022 2')
            column(name: 'grant_period', value: 202206)
            column(name: 'periods', value: '[202206]')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'eb2545fa-0ca7-4488-a3a8-8987b86746d1')
            column(name: 'df_acl_grant_set_uid', value: 'a8bf4a60-ab02-42db-afea-5f668a546158')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 122820628)
            column(name: 'system_title', value: 'Scientific American')
            column(name: 'rh_account_number', value: 2000024497)
            column(name: 'is_eligible', value: false)
            column(name: 'manual_upload_flag', value: false)
            column(name: 'created_datetime', value: '2022-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2022-01-31T00:00:00-04:00')
        }

        rollback {
            dbRollback
        }
    }
}
