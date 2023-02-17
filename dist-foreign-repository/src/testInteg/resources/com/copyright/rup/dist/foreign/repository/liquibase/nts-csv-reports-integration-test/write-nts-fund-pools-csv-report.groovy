databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-02-17-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserting test data for writeNtsFundPoolsCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8f9d3a0e-dc2e-412d-8d81-2b1a8e64faf4')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '6c8d5520-5584-4ca5-918e-3dc1ed0c90a5')
            column(name: 'rh_account_number', value: 1000000004)
            column(name: 'name', value: 'Computers for Design and Construction')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b5a9658e-9b97-430b-8fde-92d7dc54f704')
            column(name: 'name', value: 'NTS Distribution 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '85ba864e-b1f9-4857-8f9d-17a1de9f5811')
            column(name: 'df_scenario_uid', value: 'b5a9658e-9b97-430b-8fde-92d7dc54f704')
            column(name: 'product_family', value: 'NTS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '7188eb35-2bfa-4e5e-93f9-64a39f321e01')
            column(name: 'name', value: 'NTS fund pool 1')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2022-09-10')
            column(name: 'fiscal_year', value: 2023)
            column(name: 'product_family', value: 'NTS')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":7,"stm_amount":700,"stm_minimum_amount":50,"non_stm_amount":5000,"fund_pool_period_from":2023,"markets":["Bus","Doc Del"],"fund_pool_period_to":2023}')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '977c10bb-ca6d-4f11-a0fb-bd79ffcc5a0c')
            column(name: 'df_usage_batch_uid', value: '7188eb35-2bfa-4e5e-93f9-64a39f321e01')
            column(name: 'df_scenario_uid', value: 'b5a9658e-9b97-430b-8fde-92d7dc54f704')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '85ba864e-b1f9-4857-8f9d-17a1de9f5811')
            column(name: 'df_usage_batch_uid', value: '7188eb35-2bfa-4e5e-93f9-64a39f321e01')
        }

        rollback {
            dbRollback
        }
    }
}
