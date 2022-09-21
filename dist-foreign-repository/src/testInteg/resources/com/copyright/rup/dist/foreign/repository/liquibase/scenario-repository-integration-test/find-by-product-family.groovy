databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-11-17-07', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindByProductFamily')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e27551ed-3f69-4e08-9e4f-8ac03f67595f')
            column(name: 'name', value: 'Scenario name 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 2')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '27ec001a-363e-11e8-b467-0ed5f89f718b')
            column(name: 'df_scenario_uid', value: 'e27551ed-3f69-4e08-9e4f-8ac03f67595f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '095f3df4-c8a7-4dba-9a8f-7dce0b61c40a')
            column(name: 'name', value: 'Scenario with excluded usages')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 6')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '3956ce16-4649-46a9-95da-337953436479')
            column(name: 'df_scenario_uid', value: '095f3df4-c8a7-4dba-9a8f-7dce0b61c40a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '2369313c-dd17-45ed-a6e9-9461b9232ffd')
            column(name: 'name', value: 'Rejected NTS scenario with audit')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 7')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 300.00, "pre_service_fee_amount": 50.00,' +
                    '"post_service_fee_amount": 100.00, "pre_service_fee_fund_uid": "7141290b-7042-4cc6-975f-10546370adce"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'a5314598-010c-48eb-8476-fb02c2c9a8fa')
            column(name: 'df_scenario_uid', value: '2369313c-dd17-45ed-a6e9-9461b9232ffd')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '8cb9092d-a0f7-474e-a13b-af1a134e4c86')
            column(name: 'name', value: 'Sent to LM NTS scenario with audit')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'The description of scenario 8')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2017-02-15 12:00:00+00')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 300.00, "pre_service_fee_amount": 50.00,' +
                    '"post_service_fee_amount": 100.00, "pre_service_fee_fund_uid": "815d6736-a34e-4fc8-96c3-662a114fa7f2"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '663e808d-8894-4f0b-955a-74760650b9d6')
            column(name: 'df_scenario_uid', value: '8cb9092d-a0f7-474e-a13b-af1a134e4c86')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        rollback {
            dbRollback
        }
    }
}
