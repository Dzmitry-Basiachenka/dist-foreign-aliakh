databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-04-30-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testDeleteBelletristicByScenarioId')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'dd4fca1d-eac8-4b76-85e4-121b7971d049')
            column(name: 'name', value: 'Test NTS scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":300.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b614f8a0-9271-4cae-8a26-b39a83cb7c46')
            column(name: 'name', value: 'NTS batch 1')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bbbd64db-2668-499a-9d18-be8b3f87fbf5')
            column(name: 'df_usage_batch_uid', value: 'b614f8a0-9271-4cae-8a26-b39a83cb7c46')
            column(name: 'df_scenario_uid', value: 'dd4fca1d-eac8-4b76-85e4-121b7971d049')
            column(name: 'wr_wrk_inst', value: 122267672)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '0804709114')
            column(name: 'gross_amount', value: 256.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bbbd64db-2668-499a-9d18-be8b3f87fbf5')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 296.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '83a26087-a3b3-43ca-8b34-c66134fb6edf')
            column(name: 'df_usage_batch_uid', value: 'b614f8a0-9271-4cae-8a26-b39a83cb7c46')
            column(name: 'df_scenario_uid', value: 'dd4fca1d-eac8-4b76-85e4-121b7971d049')
            column(name: 'wr_wrk_inst', value: 159526527)
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 1452.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '83a26087-a3b3-43ca-8b34-c66134fb6edf')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 162.41)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6cad4cf2-6a19-4e5b-b4e0-f2f7a62ff91c')
            column(name: 'df_usage_batch_uid', value: 'b614f8a0-9271-4cae-8a26-b39a83cb7c46')
            column(name: 'df_scenario_uid', value: 'dd4fca1d-eac8-4b76-85e4-121b7971d049')
            column(name: 'wr_wrk_inst', value: 569526592)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 359.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6cad4cf2-6a19-4e5b-b4e0-f2f7a62ff91c')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 86.41)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '391eb094-f4e6-4601-b463-ef4058d4901f')
            column(name: 'df_scenario_uid', value: 'dd4fca1d-eac8-4b76-85e4-121b7971d049')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '391eb094-f4e6-4601-b463-ef4058d4901f')
            column(name: 'df_usage_batch_uid', value: 'b614f8a0-9271-4cae-8a26-b39a83cb7c46')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '30a9a53f-db53-4af3-9616-1e40edcef489')
            column(name: 'wr_wrk_inst', value: 122267672)
            column(name: 'classification', value: 'BELLETRISTIC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '092e0fa4-d0ec-4d93-b700-7d5cb096a942')
            column(name: 'wr_wrk_inst', value: 159526527)
            column(name: 'classification', value: 'STM')
        }

        rollback {
            dbRollback
        }
    }
}
