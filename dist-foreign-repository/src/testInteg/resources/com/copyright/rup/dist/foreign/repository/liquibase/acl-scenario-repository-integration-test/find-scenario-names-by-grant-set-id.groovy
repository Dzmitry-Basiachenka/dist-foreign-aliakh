databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-07-21-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Insert test data for testFindScenarioNamesByGrantSetId')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '31f51778-9aec-4e7a-84d2-8dbad8594567')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'bdbd76f9-bdd6-4558-8f96-b25e429b4abe')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '60e31e39-bac9-4e51-8d5d-009f1ec334fa')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '25fb7299-35ae-4620-98bc-7670f1b7edb7')
            column(name: 'df_acl_fund_pool_uid', value: '31f51778-9aec-4e7a-84d2-8dbad8594567')
            column(name: 'df_acl_usage_batch_uid', value: 'bdbd76f9-bdd6-4558-8f96-b25e429b4abe')
            column(name: 'df_acl_grant_set_uid', value: '60e31e39-bac9-4e51-8d5d-009f1ec334fa')
            column(name: 'name', value: 'ACL Scenario 202212 find by Grant Set id')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
            column(name: 'created_by_user', value: 'auser@copyright.com')
            column(name: 'updated_by_user', value: 'auser@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '0f4b270b-0d00-4a7b-8f94-9e6617db51a6')
            column(name: 'df_acl_fund_pool_uid', value: '31f51778-9aec-4e7a-84d2-8dbad8594567')
            column(name: 'df_acl_usage_batch_uid', value: 'bdbd76f9-bdd6-4558-8f96-b25e429b4abe')
            column(name: 'df_acl_grant_set_uid', value: '60e31e39-bac9-4e51-8d5d-009f1ec334fa')
            column(name: 'name', value: 'ACL Scenario 202212 find by Grant Set id 2')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
            column(name: 'created_by_user', value: 'buser@copyright.com')
            column(name: 'updated_by_user', value: 'buser@copyright.com')
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'f71caf7b-410d-4a8e-b933-e93922067269')
            column(name: 'name', value: 'ACL Grant Set 202212 2')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        rollback {
            dbRollback
        }
    }
}
