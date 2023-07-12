databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-07-10-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testUpdateRightsProductFamilyFasGrantProductFamilyFas')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '4f14b43b-da01-456c-9144-ebb3b77cb7f6')
            column(name: 'rh_account_number', value: 1000027688)
            column(name: 'name', value: 'World Almanac')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'bc9cd7db-66f1-469d-b72f-f6e69d3430c9')
            column(name: 'rh_account_number', value: 1000011806)
            column(name: 'name', value: '3Com Corporation')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '05b24659-6929-4f13-9fc8-0bfcf002dc35')
            column(name: 'name', value: 'Test Usage Batch')
            column(name: 'rro_account_number', value: 1000027688)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2023-01-31')
            column(name: 'fiscal_year', value: 2023)
            column(name: 'gross_amount', value: 300.00)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1ad07d10-c545-4921-9273-cdf74350683d')
            column(name: 'df_usage_batch_uid', value: '05b24659-6929-4f13-9fc8-0bfcf002dc35')
            column(name: 'wr_wrk_inst', value: 123440502)
            column(name: 'work_title', value: 'Cinema journal')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1527-2087')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 100.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '1ad07d10-c545-4921-9273-cdf74350683d')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'reported_value', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3319494e-b75c-41fb-aed0-f65c14356942')
            column(name: 'df_usage_batch_uid', value: '05b24659-6929-4f13-9fc8-0bfcf002dc35')
            column(name: 'wr_wrk_inst', value: 122853015)
            column(name: 'work_title', value: 'Business journal')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1048-8812')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 100.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3319494e-b75c-41fb-aed0-f65c14356942')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'reported_value', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5ecad71e-0e13-40a8-9304-b93f37aa8358')
            column(name: 'df_usage_batch_uid', value: '05b24659-6929-4f13-9fc8-0bfcf002dc35')
            column(name: 'wr_wrk_inst', value: 122799600)
            column(name: 'work_title', value: 'Art journal')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '0004-3249')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 100.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5ecad71e-0e13-40a8-9304-b93f37aa8358')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'reported_value', value: 100.00)
        }

        rollback {
            dbRollback
        }
    }
}
