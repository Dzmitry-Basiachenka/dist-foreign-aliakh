databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-06-24-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testDeleteByScenarioId')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c905546a-6405-467d-a7ce-d4b19e5f7d5f')
            column(name: 'name', value: 'NTS Batch with SCENARIO_EXCLUDED usages')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'fccc8c31-5259-472a-a9ca-508a8ed1cbc0')
            column(name: 'name', value: 'Test NTS scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":300.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '7f6899c5-b68c-4afb-8012-6bd93f238ec0')
            column(name: 'df_scenario_uid', value: 'fccc8c31-5259-472a-a9ca-508a8ed1cbc0')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '7f6899c5-b68c-4afb-8012-6bd93f238ec0')
            column(name: 'df_usage_batch_uid', value: 'c905546a-6405-467d-a7ce-d4b19e5f7d5f')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ea85a226-8a4b-45e3-82f8-1233a9cd7ecb')
            column(name: 'df_usage_batch_uid', value: 'c905546a-6405-467d-a7ce-d4b19e5f7d5f')
            column(name: 'df_scenario_uid', value: 'fccc8c31-5259-472a-a9ca-508a8ed1cbc0')
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 900.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'service_fee_amount', value: 288.00)
            column(name: 'net_amount', value: 612.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ea85a226-8a4b-45e3-82f8-1233a9cd7ecb')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4b5751aa-6258-44c6-b839-a1ec0edfcf4d')
            column(name: 'df_usage_batch_uid', value: 'c905546a-6405-467d-a7ce-d4b19e5f7d5f')
            column(name: 'wr_wrk_inst', value: 642267671)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4b5751aa-6258-44c6-b839-a1ec0edfcf4d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '71f2422f-92c4-4ef5-bd91-d5192797f9ee')
            column(name: 'df_usage_uid', value: 'ea85a226-8a4b-45e3-82f8-1233a9cd7ecb')
            column(name: 'action_type_ind', value: 'ELIGIBLE')
            column(name: 'action_reason', value: 'Usage has become eligible')
            column(name: 'created_datetime', value: '2051-02-14 11:45:03.721492+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '9e18bdcd-a2b7-4eda-b287-dd0b1acd3854')
            column(name: 'df_usage_uid', value: '4b5751aa-6258-44c6-b839-a1ec0edfcf4d')
            column(name: 'action_type_ind', value: 'ELIGIBLE_FOR_NTS')
            column(name: 'action_reason', value: 'Detail was made eligible for NTS because sum of gross amounts, grouped by Wr Wrk Inst, is less than 100')
            column(name: 'created_datetime', value: '2051-02-14 11:46:01.52369+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'd77613dc-66ee-4bff-b47f-f5b960d98393')
            column(name: 'df_usage_uid', value: '4b5751aa-6258-44c6-b839-a1ec0edfcf4d')
            column(name: 'action_type_ind', value: 'EXCLUDED_FROM_SCENARIO')
            column(name: 'action_reason', value: 'Usage was excluded from scenario')
            column(name: 'created_datetime', value: '2051-02-14 11:49:02.645621+03')
        }

        rollback {
            dbRollback
        }
    }
}
