databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-03-16-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testUpdateGrantsDetails, testfindPairForGrant')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ca1fb12b-6d53-40cd-9d1d-afa3341f5ffc')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'name', value: 'Greenleaf Publishing')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '065cbfec-645a-4b96-ad2f-591ba35ed9d4')
            column(name: 'rh_account_number', value: 1000019896)
            column(name: 'name', value: 'Karnac Books Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'a59bfc41-7aa9-4aba-8a49-63626bcb3294')
            column(name: 'name', value: 'ACL Grant Set 2022')
            column(name: 'grant_period', value: 202206)
            column(name: 'periods', value: '[202206]')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '883c61f6-3e78-418a-ba97-2890a2ffa46b')
            column(name: 'df_acl_grant_set_uid', value: 'a59bfc41-7aa9-4aba-8a49-63626bcb3294')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print&Digital')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: false)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '882f4daf-87ea-4ae7-b63e-740e760e5973')
            column(name: 'df_acl_grant_set_uid', value: 'a59bfc41-7aa9-4aba-8a49-63626bcb3294')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Print&Digital')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: false)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '1030722b-8810-4d49-be84-b167a2d60cfe')
            column(name: 'df_acl_grant_set_uid', value: 'a59bfc41-7aa9-4aba-8a49-63626bcb3294')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 123338293)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000019896)
            column(name: 'is_eligible', value: false)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '96fb365c-b93f-40f9-80bd-0c0cb195193c')
            column(name: 'df_acl_grant_set_uid', value: 'a59bfc41-7aa9-4aba-8a49-63626bcb3294')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 161342004)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: false)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '8e15dc7c-f3a6-439a-9030-8765dcce145a')
            column(name: 'name', value: 'ACL Grant Set 2021')
            column(name: 'grant_period', value: 202106)
            column(name: 'periods', value: '[202106]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '715b0c58-1f51-4488-be1b-28721265cb8d')
            column(name: 'df_acl_grant_set_uid', value: '8e15dc7c-f3a6-439a-9030-8765dcce145a')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 455369389)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000019896)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        rollback {
            dbRollback
        }
    }
}
