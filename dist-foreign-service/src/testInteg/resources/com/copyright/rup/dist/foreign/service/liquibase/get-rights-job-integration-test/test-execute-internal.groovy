databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-05-27-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testExecuteInternal test')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '37338ed1-7083-45e2-a96b-5872a7de3a98')
            column(name: 'rh_account_number', value: 2000139286)
            column(name: 'name', value: '2HC [T]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e92a3622-0f16-49f8-bdc0-dacd9ade1245')
            column(name: 'name', value: 'Test Usage Batch to verify GetRightsJob')
            column(name: 'rro_account_number', value: 1000023401)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-26')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '29ab73e6-2256-429d-bf36-e52315303165')
            column(name: 'df_usage_batch_uid', value: 'e92a3622-0f16-49f8-bdc0-dacd9ade1245')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '876543210')
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'standard_number', value: '978-0-7695-2365-2')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '29ab73e6-2256-429d-bf36-e52315303165')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'reported_value', value: 100.00)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        rollback {
            dbRollback
        }
    }
}
