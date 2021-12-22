databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-12-12-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testFindWithAmountsAndLastActionForNtsScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '385bdea7-49f3-451a-9a40-5a966bc243ae')
            column(name: 'name', value: 'FAS Batch with NTS usage 1')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'gross_amount', value: 50)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '680b2dca-9efa-4b4d-97ba-2fb5d18fd25b')
            column(name: 'name', value: 'NTS batch 7')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 900, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '2369313c-dd17-45ed-a6e9-9461b9232ffd')
            column(name: 'name', value: 'Rejected NTS scenario with audit')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 7')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2018-05-14 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 300.00, "pre_service_fee_amount": 50.00,' +
                    '"post_service_fee_amount": 100.00, "pre_service_fee_fund_uid": "7141290b-7042-4cc6-975f-10546370adce"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '7141290b-7042-4cc6-975f-10546370adce')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'NTS Fund Pool 3')
            column(name: 'total_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e2ca43c0-a8bf-4f6c-ad65-2b2e14b1d1de')
            column(name: 'df_usage_batch_uid', value: '385bdea7-49f3-451a-9a40-5a966bc243ae')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e2ca43c0-a8bf-4f6c-ad65-2b2e14b1d1de')
            column(name: 'df_fund_pool_uid', value: '7141290b-7042-4cc6-975f-10546370adce')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bc9dc966-c01e-4b1f-b9fb-6c1f739f6b71')
            column(name: 'df_usage_batch_uid', value: '680b2dca-9efa-4b4d-97ba-2fb5d18fd25b')
            column(name: 'df_scenario_uid', value: '2369313c-dd17-45ed-a6e9-9461b9232ffd')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 1100.00)
            column(name: 'net_amount', value: 780.00)
            column(name: 'service_fee_amount', value: 320.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bc9dc966-c01e-4b1f-b9fb-6c1f739f6b71')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '5542fced-7071-4393-877d-c77aaed3642b')
            column(name: 'df_scenario_uid', value: '2369313c-dd17-45ed-a6e9-9461b9232ffd')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'action_reason', value: 'Usages were added to scenario')
            column(name: 'created_datetime', value: '2017-03-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '756f26ed-f019-4ca4-a4f4-93beaa6ab55b')
            column(name: 'df_scenario_uid', value: '2369313c-dd17-45ed-a6e9-9461b9232ffd')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'action_reason', value: 'Scenario submitted for approval')
            column(name: 'created_datetime', value: '2017-03-10 11:41:58.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'd0e42478-b41e-441e-8d7d-e3851f741a37')
            column(name: 'df_scenario_uid', value: '2369313c-dd17-45ed-a6e9-9461b9232ffd')
            column(name: 'action_type_ind', value: 'REJECTED')
            column(name: 'action_reason', value: 'Scenario rejected by manager')
            column(name: 'created_datetime', value: '2017-04-10 11:28:58.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'a5314598-010c-48eb-8476-fb02c2c9a8fa')
            column(name: 'df_scenario_uid', value: '2369313c-dd17-45ed-a6e9-9461b9232ffd')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'a5314598-010c-48eb-8476-fb02c2c9a8fa')
            column(name: 'df_usage_batch_uid', value: '680b2dca-9efa-4b4d-97ba-2fb5d18fd25b')
        }

        rollback {
            dbRollback
        }
    }
}
