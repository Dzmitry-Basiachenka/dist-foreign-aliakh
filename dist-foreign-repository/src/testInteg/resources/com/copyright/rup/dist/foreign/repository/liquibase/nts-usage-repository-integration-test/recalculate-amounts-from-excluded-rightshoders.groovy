databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-07-30-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testRecalculateAmountsFromExcludedRightshoders')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '04e0d0f3-caf1-409e-8312-52f6fc53979e')
            column(name: 'name', value: 'NTS Batch 2')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '24d11b82-bc2a-429a-92c4-809849d36e75')
            column(name: 'name', value: 'NTS Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'NTS Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'e23c7078-7be9-4c35-8069-4f84d8744b61')
            column(name: 'df_scenario_uid', value: '24d11b82-bc2a-429a-92c4-809849d36e75')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'e23c7078-7be9-4c35-8069-4f84d8744b61')
            column(name: 'df_usage_batch_uid', value: '04e0d0f3-caf1-409e-8312-52f6fc53979e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4604c954-e43b-4606-809a-665c81514dbf')
            column(name: 'df_usage_batch_uid', value: '04e0d0f3-caf1-409e-8312-52f6fc53979e')
            column(name: 'df_scenario_uid', value: '24d11b82-bc2a-429a-92c4-809849d36e75')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 400)
            column(name: 'service_fee', value: 0.16)
            column(name: 'service_fee_amount', value: 64)
            column(name: 'net_amount', value: 336)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4604c954-e43b-4606-809a-665c81514dbf')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '56f91295-db33-4440-b550-9bb515239750')
            column(name: 'df_usage_batch_uid', value: '04e0d0f3-caf1-409e-8312-52f6fc53979e')
            column(name: 'df_scenario_uid', value: '24d11b82-bc2a-429a-92c4-809849d36e75')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 350)
            column(name: 'service_fee', value: 0.16)
            column(name: 'service_fee_amount', value: 56)
            column(name: 'net_amount', value: 294)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '56f91295-db33-4440-b550-9bb515239750')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5e95d5e2-1d32-4a95-815c-0caf11f3e382')
            column(name: 'df_usage_batch_uid', value: '04e0d0f3-caf1-409e-8312-52f6fc53979e')
            column(name: 'df_scenario_uid', value: '24d11b82-bc2a-429a-92c4-809849d36e75')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 250)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 80)
            column(name: 'net_amount', value: 170)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5e95d5e2-1d32-4a95-815c-0caf11f3e382')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
