databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-12-05-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting data for integration tests for RhTaxService')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e26ba092-677f-438c-9ba5-3f1ffff3627f')
            column(name: 'name', value: 'Batch')
            column(name: 'rro_account_number', value: 2000017001)
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'eae81bc0-a756-43a2-b236-05a0184384f4')
            column(name: 'df_usage_batch_uid', value: 'e26ba092-677f-438c-9ba5-3f1ffff3627f')
            column(name: 'rh_account_number', value: 2000133267)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: 84.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'eae81bc0-a756-43a2-b236-05a0184384f4')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ,Bus,Doc,S')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '16.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4ae8c9cb-3cd0-4497-ac8b-f19f85b259cb')
            column(name: 'df_usage_batch_uid', value: 'e26ba092-677f-438c-9ba5-3f1ffff3627f')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 2000133261)
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: 84.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4ae8c9cb-3cd0-4497-ac8b-f19f85b259cb')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ,Bus,Doc,S')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '16.00')
        }

        rollback ""
    }
}
