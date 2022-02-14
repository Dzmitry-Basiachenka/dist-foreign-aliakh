databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-05-22-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testWriteOwnershipAdjustmentCsvReport, testWriteOwnershipAdjustmentCsvEmptyReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '60241909-744c-4766-ad67-fdc9e2c043eb')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '99991909-744c-4766-ad67-fdc9e2c043eb')
            column(name: 'rh_account_number', value: 1000002901)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282cac-2468-48d4-b346-93d3458a656a')
            column(name: 'name', value: 'Ownership Adjustment Report Batch')
            column(name: 'rro_account_number', value: 7000800832)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 30000)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'name', value: 'Ownership Adjustment Report Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'df_usage_batch_uid', value: '56282cac-2468-48d4-b346-93d3458a656a')
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'wr_wrk_inst', value: 122843178)
            column(name: 'work_title', value: 'Accounting education news')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'gross_amount', value: 16437.40)
            column(name: 'net_amount', value: 11177.40)
            column(name: 'service_fee_amount', value: 5260.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Bus, Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 9900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder_discrepancy') {
            column(name: 'df_rightsholder_discrepancy_uid', value: '5745caa4-4301-4943-a992-4561c22d3ef0')
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'wr_wrk_inst', value: 122843178)
            column(name: 'old_rh_account_number', value: 1000002859)
            column(name: 'new_rh_account_number', value: 1000005413)
            column(name: 'work_title', value: 'Accounting education news')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'APPROVED')
        }

        rollback {
            dbRollback
        }
    }
}
