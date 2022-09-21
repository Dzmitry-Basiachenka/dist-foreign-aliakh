databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-06-12-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testFindByScenarioId')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5bcf2c37-2f32-48e9-90fe-c9d75298eeed')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8a0dbf78-d9c9-49d9-a895-05f55cfc8329')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'd7e9bae8-6b10-4675-9668-8e3605a47dad')
            column(name: 'name', value: 'Test NTS scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":300.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '2167779f-cbae-4b4b-adc6-fb95aeb3c58d')
            column(name: 'name', value: 'NTS Batch')
            column(name: 'rro_account_number', value: 7000800832)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8a80a2e7-4758-4e43-ae42-e8b29802a210')
            column(name: 'df_usage_batch_uid', value: '2167779f-cbae-4b4b-adc6-fb95aeb3c58d')
            column(name: 'df_scenario_uid', value: 'd7e9bae8-6b10-4675-9668-8e3605a47dad')
            column(name: 'wr_wrk_inst', value: 122267672)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '0804709114')
            column(name: 'gross_amount', value: 256.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8a80a2e7-4758-4e43-ae42-e8b29802a210')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 296.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '085268cd-7a0c-414e-8b28-2acb299d9698')
            column(name: 'df_usage_batch_uid', value: '2167779f-cbae-4b4b-adc6-fb95aeb3c58d')
            column(name: 'df_scenario_uid', value: 'd7e9bae8-6b10-4675-9668-8e3605a47dad')
            column(name: 'wr_wrk_inst', value: 159526527)
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 1452.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '085268cd-7a0c-414e-8b28-2acb299d9698')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 162.41)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bfc9e375-c489-4600-9308-daa101eed97c')
            column(name: 'df_usage_batch_uid', value: '2167779f-cbae-4b4b-adc6-fb95aeb3c58d')
            column(name: 'df_scenario_uid', value: 'd7e9bae8-6b10-4675-9668-8e3605a47dad')
            column(name: 'wr_wrk_inst', value: 159526527)
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 145.20)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bfc9e375-c489-4600-9308-daa101eed97c')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 16.24)
        }

        rollback {
            dbRollback
        }
    }
}
