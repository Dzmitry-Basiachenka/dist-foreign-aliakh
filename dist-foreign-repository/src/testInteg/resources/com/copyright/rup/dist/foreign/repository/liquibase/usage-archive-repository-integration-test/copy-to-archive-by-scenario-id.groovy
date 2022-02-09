databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-06-21-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testCopyFasToArchiveByScenarioId, testCopyNtsToArchiveByScenarioId')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5bcf2c37-2f32-48e9-90fe-c9d75298eeed')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '72ba99f3-f4ee-45ab-8708-81be9207841d')
            column(name: 'name', value: 'NTS batch for grouping usages to send to LM')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e65833c8-3a40-47ba-98fe-21aba07ef11e')
            column(name: 'name', value: 'NTS Scenario to send to LM')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'description', value: 'Approved NTS scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '56ec7a5b-bfc2-4e45-9747-5ab6916e84b7')
            column(name: 'df_usage_batch_uid', value: '72ba99f3-f4ee-45ab-8708-81be9207841d')
            column(name: 'df_scenario_uid', value: 'e65833c8-3a40-47ba-98fe-21aba07ef11e')
            column(name: 'wr_wrk_inst', value: 569526592)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '0804709114')
            column(name: 'gross_amount', value: 6509.31)
            column(name: 'service_fee_amount', value: 2082.98)
            column(name: 'net_amount', value: 4426.33)
            column(name: 'service_fee', value: 0.32)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '56ec7a5b-bfc2-4e45-9747-5ab6916e84b7')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 15000.00)
            column(name: 'is_rh_participating_flag', value: 'false')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1f598431-08bc-448c-baf1-4aeb35e37f33')
            column(name: 'df_usage_batch_uid', value: '72ba99f3-f4ee-45ab-8708-81be9207841d')
            column(name: 'df_scenario_uid', value: 'e65833c8-3a40-47ba-98fe-21aba07ef11e')
            column(name: 'wr_wrk_inst', value: 146547417)
            column(name: 'work_title', value: 'Test de aptitudes profesionales')
            column(name: 'system_title', value: 'Test de aptitudes profesionales')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '0804709114')
            column(name: 'gross_amount', value: 6000.00)
            column(name: 'net_amount', value: 4080.00)
            column(name: 'service_fee_amount', value: 1920.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '1f598431-08bc-448c-baf1-4aeb35e37f33')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 6000.00)
            column(name: 'is_rh_participating_flag', value: 'false')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '83eef503-0f35-44fc-8b0a-9b6bf6a7f41d')
            column(name: 'name', value: 'FAS batch to send to LM')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'gross_amount', value: 300.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5f7c87e7-34d9-4548-8b85-97e405235f4a')
            column(name: 'name', value: 'FAS Scenario to send to LM')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'description', value: 'Approved NTS scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82dff947-9fa8-4aae-9d42-1453c7d56fed')
            column(name: 'df_usage_batch_uid', value: '83eef503-0f35-44fc-8b0a-9b6bf6a7f41d')
            column(name: 'df_scenario_uid', value: '5f7c87e7-34d9-4548-8b85-97e405235f4a')
            column(name: 'wr_wrk_inst', value: 569526592)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '0804709114')
            column(name: 'gross_amount', value: 6509.31)
            column(name: 'service_fee_amount', value: 2082.98)
            column(name: 'net_amount', value: 4426.33)
            column(name: 'service_fee', value: 0.32)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82dff947-9fa8-4aae-9d42-1453c7d56fed')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 15000.00)
            column(name: 'is_rh_participating_flag', value: 'false')
        }

        rollback {
            dbRollback
        }
    }
}
