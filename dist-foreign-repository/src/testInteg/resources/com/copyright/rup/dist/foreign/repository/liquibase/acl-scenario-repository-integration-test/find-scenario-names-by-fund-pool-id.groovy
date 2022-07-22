databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-07-21-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Insert test data for testFindScenarioNamesByFundPoolId')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'f8e623b0-7e18-4a06-a754-0a81decff96f')
            column(name: 'name', value: 'ACL Fund Pool 202112')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '49647327-0a57-4ad2-acc6-afb29b96edb8')
            column(name: 'name', value: 'ACL Usage Batch 202112')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'b7a46f15-fc59-44e6-9995-d756e5a8876f')
            column(name: 'name', value: 'ACL Grant Set 202112')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '8a417330-9ec1-4267-9780-a527d53e15a4')
            column(name: 'df_acl_fund_pool_uid', value: 'f8e623b0-7e18-4a06-a754-0a81decff96f')
            column(name: 'df_acl_usage_batch_uid', value: '49647327-0a57-4ad2-acc6-afb29b96edb8')
            column(name: 'df_acl_grant_set_uid', value: 'b7a46f15-fc59-44e6-9995-d756e5a8876f')
            column(name: 'name', value: 'ACL Scenario 202112 find by fund pool id')
            column(name: 'period_end_date', value: 202112)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
            column(name: 'created_by_user', value: 'auser@copyright.com')
            column(name: 'updated_by_user', value: 'auser@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'fcdc647d-0712-4c38-aa49-382737418dd1')
            column(name: 'df_acl_fund_pool_uid', value: 'f8e623b0-7e18-4a06-a754-0a81decff96f')
            column(name: 'df_acl_usage_batch_uid', value: '49647327-0a57-4ad2-acc6-afb29b96edb8')
            column(name: 'df_acl_grant_set_uid', value: 'b7a46f15-fc59-44e6-9995-d756e5a8876f')
            column(name: 'name', value: 'ACL Scenario 202112 find by fund pool id 2')
            column(name: 'period_end_date', value: 202112)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
            column(name: 'created_by_user', value: 'buser@copyright.com')
            column(name: 'updated_by_user', value: 'buser@copyright.com')
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '7df8b1ec-c464-42a8-aa28-52bb5bc7cb7b')
            column(name: 'name', value: 'ACL Fund Pool 202112 2')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
