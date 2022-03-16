databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-03-18-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testDeleteByGrantSetId')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '0d651beb-e536-4854-aed7-50f068c369ba')
            column(name: 'name', value: 'ACL Grant Set 2022')
            column(name: 'grant_period', value: 202206)
            column(name: 'periods', value: '[202206]')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'a532c96d-895a-40ba-b123-5d775ff67397')
            column(name: 'df_acl_grant_set_uid', value: '0d651beb-e536-4854-aed7-50f068c369ba')
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
