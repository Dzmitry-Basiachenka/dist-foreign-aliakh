databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-01-28-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindCountByFilter, testFindDtosByFilter, testSortingFindDtosByFilter')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a408ef06-05ea-4477-a5a6-ad448fd49bc7')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'name', value: 'Greenleaf Publishing')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'bc7107a8-a098-47f9-b28c-d7e8bb8704f2')
            column(name: 'rh_account_number', value: 2000024497)
            column(name: 'name', value: 'Alexander Stille')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '5b9af191-40d4-4969-976b-176fc24289d7')
            column(name: 'name', value: 'ACL Grant Set 2021')
            column(name: 'grant_period', value: 202106)
            column(name: 'periods', value: '[202106]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'bc696696-e46f-4fbd-85c7-4c509b370deb')
            column(name: 'df_acl_grant_set_uid', value: '5b9af191-40d4-4969-976b-176fc24289d7')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: '122820638')
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'f4d4f8d1-a3e2-4463-8143-c1c0d07ba1b1')
            column(name: 'name', value: 'ACL Grant Set 2020')
            column(name: 'grant_period', value: 202012)
            column(name: 'periods', value: '[202012]')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'ce59e8f3-de93-4fef-9cfb-47eb8c0175cc')
            column(name: 'df_acl_grant_set_uid', value: 'f4d4f8d1-a3e2-4463-8143-c1c0d07ba1b1')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Print&Digital')
            column(name: 'wr_wrk_inst', value: '122820628')
            column(name: 'system_title', value: 'Scientific American')
            column(name: 'rh_account_number', value: 2000024497)
            column(name: 'is_eligible', value: false)
            column(name: 'created_datetime', value: '2020-12-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2020-12-31T00:00:00-04:00')
        }

        rollback {
            dbRollback
        }
    }
}
