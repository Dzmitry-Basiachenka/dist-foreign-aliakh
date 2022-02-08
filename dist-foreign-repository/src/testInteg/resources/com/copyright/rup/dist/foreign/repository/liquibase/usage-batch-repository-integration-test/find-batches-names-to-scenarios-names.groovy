databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-02-09-04', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindBatchesNamesToScenariosNames')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'd81f3f4c-56bc-4890-8a43-2e3fbbe4c6b2')
            column(name: 'name', value: 'Scenario name 4')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 4')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '7a107d1e-10c3-43ed-8e39-4119d7878c59')
            column(name: 'df_scenario_uid', value: 'd81f3f4c-56bc-4890-8a43-2e3fbbe4c6b2')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '7a107d1e-10c3-43ed-8e39-4119d7878c59')
            column(name: 'df_usage_batch_uid', value: '8d6ac3b1-8704-43a8-acec-d86ac263996f')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '8d6ac3b1-8704-43a8-acec-d86ac263996f')
            column(name: 'name', value: 'NEW_26_OCT_2017')
            column(name: 'rro_account_number', value: 7000813800)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-10-30')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'updated_datetime', value: '2017-10-26 14:49:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ed16252c-e9df-4c70-94e4-a5cb6ae078eb')
            column(name: 'df_usage_batch_uid', value: '8d6ac3b1-8704-43a8-acec-d86ac263996f')
            column(name: 'df_scenario_uid', value: 'd81f3f4c-56bc-4890-8a43-2e3fbbe4c6b2')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'payee_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 35000.00)
            column(name: 'net_amount', value: 23800.00)
            column(name: 'service_fee_amount', value: 11200.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ed16252c-e9df-4c70-94e4-a5cb6ae078eb')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2014-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e40065de-4854-457b-86a1-59b8b3d33b9a')
            column(name: 'name', value: 'NTS Batch without STM usages')
            column(name: 'rro_account_number', value: 123456789)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 0, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7, "excluding_stm": true}')
            column(name: 'updated_datetime', value: '2019-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '67524d2d-e1a6-4716-8e50-4ff2280a7f5e')
            column(name: 'df_usage_batch_uid', value: 'e40065de-4854-457b-86a1-59b8b3d33b9a')
            column(name: 'wr_wrk_inst', value: 122265847)
            column(name: 'work_title', value: 'Ulysses')
            column(name: 'system_title', value: 'Ulysses')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '67524d2d-e1a6-4716-8e50-4ff2280a7f5e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cc7ef592-c537-412f-b1eb-402709cb3f54')
            column(name: 'df_usage_batch_uid', value: 'e40065de-4854-457b-86a1-59b8b3d33b9a')
            column(name: 'wr_wrk_inst', value: 122266795)
            column(name: 'work_title', value: 'Nine stories')
            column(name: 'system_title', value: 'Nine stories')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'cc7ef592-c537-412f-b1eb-402709cb3f54')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 1000)
        }

        rollback {
            dbRollback
        }
    }
}
