databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-02-09-05', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindById')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6b27ad49-ec1c-4c63-9ff7-c79317087180')
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
            column(name: 'df_usage_uid', value: '24f464b2-ccea-46d0-a11d-7b0e3805abe4')
            column(name: 'df_usage_batch_uid', value: '6b27ad49-ec1c-4c63-9ff7-c79317087180')
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
            column(name: 'df_usage_fas_uid', value: '24f464b2-ccea-46d0-a11d-7b0e3805abe4')
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
            column(name: 'df_usage_uid', value: 'b9f79e84-448e-46f1-907a-69801b2582d2')
            column(name: 'df_usage_batch_uid', value: '6b27ad49-ec1c-4c63-9ff7-c79317087180')
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
            column(name: 'df_usage_fas_uid', value: 'b9f79e84-448e-46f1-907a-69801b2582d2')
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
