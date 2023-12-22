databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-12-22-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testFindByAclGrantSetId')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '7b18daf7-679d-4bcf-a0c3-3a3cf8f7ee8c')
            column(name: 'rh_account_number', value: 7001645719)
            column(name: 'name', value: 'Ann Bastian')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'f2b84227-e24d-4da0-81b8-a6d9635576a2')
            column(name: 'rh_account_number', value: 2000155939)
            column(name: 'name', value: 'Advaita Ashrama')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '6d560952-070c-44cb-ba96-710f17a36017')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'c46c039e-b8b1-40c2-8512-82390591622e')
            column(name: 'df_acl_grant_set_uid', value: '6d560952-070c-44cb-ba96-710f17a36017')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 2000155939)
            column(name: 'is_eligible', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'debb9f12-bd34-46fe-b355-e82862b958cd')
            column(name: 'df_acl_grant_set_uid', value: '6d560952-070c-44cb-ba96-710f17a36017')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 7001645719)
            column(name: 'is_eligible', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '29b9c8a7-20eb-49d3-9c6a-ead7aa71e2ba')
            column(name: 'df_acl_grant_set_uid', value: '6d560952-070c-44cb-ba96-710f17a36017')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 7001645719)
            column(name: 'is_eligible', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
