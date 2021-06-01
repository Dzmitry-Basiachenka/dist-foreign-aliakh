databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-04-03-00', author: 'Alaiksandr Liakh <aliakh@copyright.com>') {
        comment('B-41583 FDA Reload researched details: implement business validation')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'name', value: 'Test Batch')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e1108526-5945-4a30-971e-91e584b4bc88')
            column(name: 'df_usage_batch_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'wr_wrk_inst', value: '987654321')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: 10000.00)
            column(name: 'net_amount', value: '8400.00')
            column(name: 'service_fee_amount', value: '1600.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e1108526-5945-4a30-971e-91e584b4bc88')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9f717d69-785a-4f25-ae60-8d90c1f334cc')
            column(name: 'df_usage_batch_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'wr_wrk_inst', value: '876543210')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3566')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'gross_amount', value: 10000.00)
            column(name: 'net_amount', value: '8400.00')
            column(name: 'service_fee_amount', value: '1600.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9f717d69-785a-4f25-ae60-8d90c1f334cc')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8fc36b5d-a349-485d-9c3d-40806d1b4bca')
            column(name: 'df_usage_batch_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'wr_wrk_inst', value: '876543210')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'standard_number', value: '2192-3566')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'gross_amount', value: 10000.00)
            column(name: 'net_amount', value: '8400.00')
            column(name: 'service_fee_amount', value: '1600.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8fc36b5d-a349-485d-9c3d-40806d1b4bca')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        rollback ""
    }
}
