databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-06-16-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testExecuteInternal test')

        // scenario for a positive case
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '27755cf6-66fc-4636-862e-3d9c9f4e7a94')
            column(name: 'name', value: 'Test scenario 1')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Test scenario description 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '4023bb7e-de22-4054-9c73-96ec7897cf98')
            column(name: 'df_scenario_uid', value: '27755cf6-66fc-4636-862e-3d9c9f4e7a94')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '5e2af966-d887-4cdc-bc99-8eb492b6506d')
            column(name: 'name', value: 'Test usage batch 1')
            column(name: 'rro_account_number', value: 1000005413)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-06-16')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '06eae1b7-6132-4e19-b8ee-864a5ad65924')
            column(name: 'df_usage_batch_uid', value: '5e2af966-d887-4cdc-bc99-8eb492b6506d')
            column(name: 'df_scenario_uid', value: '27755cf6-66fc-4636-862e-3d9c9f4e7a94')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 122465241)
            column(name: 'work_title', value: 'Water for energy')
            column(name: 'system_title', value: 'Water for energy')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 84.00)
            column(name: 'service_fee_amount', value: 16.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2022-06-16')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA June 2022')
            column(name: 'distribution_date', value: '2022-06-16')
            column(name: 'lm_detail_id', value: '9b6f7a2d-9065-4472-89ad-01fe12b23670')
            column(name: 'period_end_date', value: '2022-06-16')
            column(name: 'created_datetime', value: '2022-06-15')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '06eae1b7-6132-4e19-b8ee-864a5ad65924')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2021-12-31')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2021)
            column(name: 'market_period_to', value: 2022)
            column(name: 'reported_value', value: 100.00)
        }

        // scenario for a negative case
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '612b1a40-411b-4819-9b67-cb4f6abc18eb')
            column(name: 'name', value: 'Test scenario 2')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Test scenario description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'b5eb73f0-b8d0-4057-822e-8708d31b17d3')
            column(name: 'df_scenario_uid', value: '612b1a40-411b-4819-9b67-cb4f6abc18eb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'df1981d9-dc43-4f5c-b8ef-3126bae3eb28')
            column(name: 'name', value: 'Test usage batch 2')
            column(name: 'rro_account_number', value: 1000005413)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-06-16')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ac4ccfee-5fb6-4f9f-870d-35b2997f288f')
            column(name: 'df_usage_batch_uid', value: 'df1981d9-dc43-4f5c-b8ef-3126bae3eb28')
            column(name: 'df_scenario_uid', value: '612b1a40-411b-4819-9b67-cb4f6abc18eb')
            column(name: 'wr_wrk_inst', value: 102875272)
            column(name: 'work_title', value: 'Travels in West Africa')
            column(name: 'system_title', value: 'Travels in West Africa')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'payee_account_number', value: 1000003821)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'FAS')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 84.00)
            column(name: 'service_fee_amount', value: 16.00)
            column(name: 'service_fee', value: 0.16)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2022-06-16')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA June 2022')
            column(name: 'distribution_date', value: '2022-06-16')
            column(name: 'lm_detail_id', value: '434b0a86-a911-4ad7-abce-7eab696aec8e')
            column(name: 'period_end_date', value: '2022-06-16')
            column(name: 'created_datetime', value: '2022-06-15')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ac4ccfee-5fb6-4f9f-870d-35b2997f288f')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2021-12-31')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2021)
            column(name: 'market_period_to', value: 2022)
            column(name: 'reported_value', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '68ffad13-55c9-424c-88f7-a4289504d217')
            column(name: 'df_usage_batch_uid', value: 'df1981d9-dc43-4f5c-b8ef-3126bae3eb28')
            column(name: 'df_scenario_uid', value: '612b1a40-411b-4819-9b67-cb4f6abc18eb')
            column(name: 'wr_wrk_inst', value: 101272573)
            column(name: 'work_title', value: 'People of the land')
            column(name: 'system_title', value: 'People of the land')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'payee_account_number', value: 7000429266)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 84.00)
            column(name: 'service_fee_amount', value: 16.00)
            column(name: 'service_fee', value: 0.16)
            column(name: 'period_end_date', value: '2022-06-16')
            column(name: 'created_datetime', value: '2022-06-15')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '68ffad13-55c9-424c-88f7-a4289504d217')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2021-12-31')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2021)
            column(name: 'market_period_to', value: 2022)
            column(name: 'reported_value', value: 100.00)
        }

        rollback {
            dbRollback
        }
    }
}
