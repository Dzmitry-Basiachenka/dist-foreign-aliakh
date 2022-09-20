databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-07-30-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteScenarioUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1da0461a-92f9-40cc-a3c1-9b972505b9c9')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'name', value: 'CFC/ Center Fran dexploitation du droit de Copie')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '85ba864e-1939-4a60-9fab-888b84199321')
            column(name: 'name', value: 'Ownership Adjustment Report Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '85ba864e-b1f9-4857-8f9d-17a1de9f5811')
            column(name: 'df_scenario_uid', value: '85ba864e-1939-4a60-9fab-888b84199321')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'name', value: 'NTS fund pool 2')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'product_family', value: 'NTS')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":7,"stm_amount":700,"stm_minimum_amount":50,"non_stm_amount":5000,"fund_pool_period_from":2010,"markets":["Bus","Doc Del"],"fund_pool_period_to":2012}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '89ba847e-3b09-457f-a306-f36fc55710af')
            column(name: 'df_usage_batch_uid', value: 'f17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'df_scenario_uid', value: '85ba864e-1939-4a60-9fab-888b84199321')
            column(name: 'wr_wrk_inst', value: 9876543212)
            column(name: 'work_title', value: 'future of children')
            column(name: 'system_title', value: 'future of children')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'service_fee', value: 0.0)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '89ba847e-3b09-457f-a306-f36fc55710af')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2010)
            column(name: 'market_period_to', value: 2011)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '85ba864e-b1f9-4857-8f9d-17a1de9f5811')
            column(name: 'df_usage_batch_uid', value: 'f17ebc80-e74e-436d-ba6e-acf3d355b7ff')
        }

        rollback {
            dbRollback
        }
    }
}
