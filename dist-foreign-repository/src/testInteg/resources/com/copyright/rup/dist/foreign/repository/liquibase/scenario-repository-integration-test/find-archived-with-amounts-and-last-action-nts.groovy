databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-12-12-01', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testFindArchivedWithAmountsAndLastActionForNtsScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b2dec214-8ffb-4164-852c-3b1b8047f02c')
            column(name: 'name', value: 'FAS Batch with NTS usage 2')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'gross_amount', value: 50)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '668495a3-44fd-4863-8fc1-9f96229bfe9d')
            column(name: 'name', value: 'NTS batch 8')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 900, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '8cb9092d-a0f7-474e-a13b-af1a134e4c86')
            column(name: 'name', value: 'Sent to LM NTS scenario with audit')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'The description of scenario 8')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2018-05-15 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 300.00, "pre_service_fee_amount": 50.00,' +
                    '"post_service_fee_amount": 100.00, "pre_service_fee_fund_uid": "815d6736-a34e-4fc8-96c3-662a114fa7f2"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '815d6736-a34e-4fc8-96c3-662a114fa7f2')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'NTS Fund Pool 4')
            column(name: 'total_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '6ef849e4-68be-47c7-abc4-50440b60d789')
            column(name: 'df_usage_batch_uid', value: 'b2dec214-8ffb-4164-852c-3b1b8047f02c')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6ef849e4-68be-47c7-abc4-50440b60d789')
            column(name: 'df_fund_pool_uid', value: '815d6736-a34e-4fc8-96c3-662a114fa7f2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 50)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'bc9dc966-c01e-4b1f-b9fb-6c1f739f6b71')
            column(name: 'df_scenario_uid', value: '8cb9092d-a0f7-474e-a13b-af1a134e4c86')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 1100.00)
            column(name: 'net_amount', value: 780.00)
            column(name: 'service_fee_amount', value: 320.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '96414358-8114-47ad-b4fd-eba41cc7625d')
            column(name: 'df_scenario_uid', value: '8cb9092d-a0f7-474e-a13b-af1a134e4c86')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'action_reason', value: 'Usages were added to scenario')
            column(name: 'created_datetime', value: '2017-03-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '05419a66-55df-416c-a1de-96ccde50f1f9')
            column(name: 'df_scenario_uid', value: '8cb9092d-a0f7-474e-a13b-af1a134e4c86')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'action_reason', value: 'Scenario submitted for approval')
            column(name: 'created_datetime', value: '2017-03-10 11:41:58.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'b9866e64-7ad6-4df5-a965-e21a7b7623b4')
            column(name: 'df_scenario_uid', value: '8cb9092d-a0f7-474e-a13b-af1a134e4c86')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'action_reason', value: 'Scenario approved by manager')
            column(name: 'created_datetime', value: '2017-04-10 11:28:58.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'a394bfc3-5d5f-4732-9687-535f38ecb404')
            column(name: 'df_scenario_uid', value: '8cb9092d-a0f7-474e-a13b-af1a134e4c86')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2017-04-11 11:28:58.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '663e808d-8894-4f0b-955a-74760650b9d6')
            column(name: 'df_scenario_uid', value: '8cb9092d-a0f7-474e-a13b-af1a134e4c86')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '663e808d-8894-4f0b-955a-74760650b9d6')
            column(name: 'df_usage_batch_uid', value: '668495a3-44fd-4863-8fc1-9f96229bfe9d')
        }

        rollback {
            dbRollback
        }
    }
}
