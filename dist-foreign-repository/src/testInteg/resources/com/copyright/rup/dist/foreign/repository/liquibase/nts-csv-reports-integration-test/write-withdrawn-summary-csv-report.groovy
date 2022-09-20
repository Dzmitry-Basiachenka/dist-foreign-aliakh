databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-10-28-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteWithdrawnBatchSummaryCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund1')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '4fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund2')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '5fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund3')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a6669f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: 1000002900)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '99991909-744c-4766-ad67-fdc9e2c043eb')
            column(name: 'rh_account_number', value: 1000002901)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '99966cac-2468-48d4-b346-93d3458a656a')
            column(name: 'name', value: 'NTS Withdrawn Report Batch')
            column(name: 'rro_account_number', value: 1000002901)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 30001)
            column(name: 'initial_usages_count', value: 4)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '88866cac-2468-48d4-b346-93d3458a656a')
            column(name: 'name', value: 'NTS Withdrawn Report Batch Included To Be Distributed')
            column(name: 'rro_account_number', value: 1000002900)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 30002)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '66649fd0-c094-4a72-979e-00cf462fb3eb')
            column(name: 'df_usage_batch_uid', value: '99966cac-2468-48d4-b346-93d3458a656a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '66649fd0-c094-4a72-979e-00cf462fb3eb')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6666d6f8-7ac6-4ae7-9bdc-ba33a58a5bad')
            column(name: 'df_usage_batch_uid', value: '99966cac-2468-48d4-b346-93d3458a656a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 180382915)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'standard_number', value: '2192-3566')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6666d6f8-7ac6-4ae7-9bdc-ba33a58a5bad')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '666644ba-58d6-4adb-9dc1-602eb09052ce')
            column(name: 'df_usage_batch_uid', value: '99966cac-2468-48d4-b346-93d3458a656a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '666644ba-58d6-4adb-9dc1-602eb09052ce')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6666dea5-4acc-4d87-b264-a67ab17a93ae')
            column(name: 'df_usage_batch_uid', value: '99966cac-2468-48d4-b346-93d3458a656a')
            column(name: 'wr_wrk_inst', value: 103658926)
            column(name: 'work_title', value: 'Nitrates')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6666dea5-4acc-4d87-b264-a67ab17a93ae')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '11149fd0-c094-4a72-979e-00cf462fb3eb')
            column(name: 'df_usage_batch_uid', value: '88866cac-2468-48d4-b346-93d3458a656a')
            column(name: 'product_family', value: 'NTS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 202)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '11149fd0-c094-4a72-979e-00cf462fb3eb')
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2226d6f8-7ac6-4ae7-9bdc-ba33a58a5bad')
            column(name: 'df_usage_batch_uid', value: '88866cac-2468-48d4-b346-93d3458a656a')
            column(name: 'product_family', value: 'NTS')
            column(name: 'wr_wrk_inst', value: 180382915)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'standard_number', value: '2192-3566')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 202)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2226d6f8-7ac6-4ae7-9bdc-ba33a58a5bad')
            column(name: 'df_fund_pool_uid', value: '4fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3336d6f8-7ac6-4ae7-9bdc-ba33a58a5bad')
            column(name: 'df_usage_batch_uid', value: '88866cac-2468-48d4-b346-93d3458a656a')
            column(name: 'product_family', value: 'NTS')
            column(name: 'wr_wrk_inst', value: 180382915)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'standard_number', value: '2192-3566')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 202)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3336d6f8-7ac6-4ae7-9bdc-ba33a58a5bad')
            column(name: 'df_fund_pool_uid', value: '5fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
            column(name: 'reported_value', value: 30.86)
        }

        rollback {
            dbRollback
        }
    }
}
