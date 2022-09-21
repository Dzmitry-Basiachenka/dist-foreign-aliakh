databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-06-13-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testApplyPostServiceFeeAmount')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'fd09c318-75e8-4f4d-b384-8c83e8033e25')
            column(name: 'name', value: 'NTS testApplyPostServiceFeeAmount')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 30, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'c4bc09c1-eb9b-41f3-ac93-9cd088dff408')
            column(name: 'name', value: 'NTS testApplyPostServiceFeeAmount')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 30.00, "post_service_fee_amount": 100}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '3c191f12-4314-470a-b11e-a9a65030dddd')
            column(name: 'df_scenario_uid', value: 'c4bc09c1-eb9b-41f3-ac93-9cd088dff408')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '3c191f12-4314-470a-b11e-a9a65030dddd')
            column(name: 'df_usage_batch_uid', value: 'fd09c318-75e8-4f4d-b384-8c83e8033e25')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7778a37d-6184-42c1-8e23-5841837c5411')
            column(name: 'df_usage_batch_uid', value: 'fd09c318-75e8-4f4d-b384-8c83e8033e25')
            column(name: 'df_scenario_uid', value: 'c4bc09c1-eb9b-41f3-ac93-9cd088dff408')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 33)
            column(name: 'service_fee', value: 0.16)
            column(name: 'service_fee_amount', value: 5.28)
            column(name: 'net_amount', value: 27.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7778a37d-6184-42c1-8e23-5841837c5411')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '54247c55-bf6b-4ad6-9369-fb4baea6b19b')
            column(name: 'df_usage_batch_uid', value: 'fd09c318-75e8-4f4d-b384-8c83e8033e25')
            column(name: 'df_scenario_uid', value: 'c4bc09c1-eb9b-41f3-ac93-9cd088dff408')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 66)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 21.12)
            column(name: 'net_amount', value: 44.88)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '54247c55-bf6b-4ad6-9369-fb4baea6b19b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 66)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ade68eac-0d79-4d23-861b-499a0c6e91d3')
            column(name: 'df_usage_batch_uid', value: 'fd09c318-75e8-4f4d-b384-8c83e8033e25')
            column(name: 'rh_account_number', value: 7000896777)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 11)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ade68eac-0d79-4d23-861b-499a0c6e91d3')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 11)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'e920c634-f59d-4d9c-82bd-275af99132b6')
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'classification', value: 'STM')
        }

        rollback {
            dbRollback
        }
    }
}
