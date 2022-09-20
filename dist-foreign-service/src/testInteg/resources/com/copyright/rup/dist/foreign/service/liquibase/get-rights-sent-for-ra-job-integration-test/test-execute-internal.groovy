databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-06-15-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testExecuteInternal test')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'd311340c-60e8-4df1-bbe1-788ba2ed9a15')
            column(name: 'rh_account_number', value: 1000023401)
            column(name: 'name', value: 'American City Business Journals, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c17981e6-5dfd-4ccf-8d64-182745f991f5')
            column(name: 'name', value: 'Test Usage Batch to verify GetRightsJob')
            column(name: 'rro_account_number', value: 1000023401)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-06-15')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '14c3d141-5475-4789-8fe4-ecae63a2d262')
            column(name: 'df_usage_batch_uid', value: 'c17981e6-5dfd-4ccf-8d64-182745f991f5')
            column(name: 'wr_wrk_inst', value: 122824345)
            column(name: 'work_title', value: 'ACP journal club')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '10568751')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 77.7300000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '14c3d141-5475-4789-8fe4-ecae63a2d262')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'reported_value', value: 101.17)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e878dfa2-31d8-4506-a7fd-1f4778d406e6')
            column(name: 'df_usage_batch_uid', value: 'c17981e6-5dfd-4ccf-8d64-182745f991f5')
            column(name: 'wr_wrk_inst', value: 465159524)
            column(name: 'work_title', value: 'Digital Transformation Playbook')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780231175449')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 122.2700000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e878dfa2-31d8-4506-a7fd-1f4778d406e6')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'reported_value', value: 179.34)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        rollback {
            dbRollback
        }
    }
}
