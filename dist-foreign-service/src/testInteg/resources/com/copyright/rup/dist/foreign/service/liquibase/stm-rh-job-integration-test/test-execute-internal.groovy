databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-05-30-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testExecuteInternal test')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '75e057ac-7c24-4ae7-a0f5-aa75ea0895e6')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'name', value: 'Zoological Society of Pakistan [T]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b2341ecb-70e2-472a-9d87-5a3d33c7f22d')
            column(name: 'name', value: 'Test Usage Batch to verify RhTaxJob')
            column(name: 'product_family', value: 'NTS')
            column(name: 'rro_account_number', value: 2000017011)
            column(name: 'payment_date', value: '2022-05-27')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 1, "non_stm_minimum_amount": 0,' +
                    '"fund_pool_period_to": 2020, "fund_pool_period_from": 2021}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1d798afe-dabb-4dca-9351-7a6ef64f3708')
            column(name: 'df_usage_batch_uid', value: 'b2341ecb-70e2-472a-9d87-5a3d33c7f22d')
            column(name: 'status_ind', value: 'US_TAX_COUNTRY')
            column(name: 'product_family', value: 'NTS')
            column(name: 'wr_wrk_inst', value: '448824345')
            column(name: 'work_title', value: 'Technical Journal')
            column(name: 'system_title', value: 'Technical Journal')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '1d798afe-dabb-4dca-9351-7a6ef64f3708')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2020)
            column(name: 'market_period_to', value: 2021)
            column(name: 'reported_value', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'fb0d5bfe-b0a9-4956-94a0-1be434feda9c')
            column(name: 'wr_wrk_inst', value: 448824345)
            column(name: 'classification', value: 'NON-STM')
        }

        rollback {
            dbRollback
        }
    }
}
