databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-06-24-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testFindForSendToLmByIds')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5bcf2c37-2f32-48e9-90fe-c9d75298eeed')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '8adfeeb9-42fb-4214-9f65-ef2d37a5d581')
            column(name: 'name', value: 'NTS batch with regenerated usages sent to LM')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e19570d3-e9a0-4805-90ed-bd5dbcfcf803')
            column(name: 'name', value: 'NTS Scenario to send to LM')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Sent to LM NTS scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '37ea653f-c748-4cb9-b4a3-7b11d434244a')
            column(name: 'df_scenario_uid', value: 'e19570d3-e9a0-4805-90ed-bd5dbcfcf803')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 6000.00)
            column(name: 'net_amount', value: 4080.00)
            column(name: 'service_fee_amount', value: 1920.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '37ea653f-c748-4cb9-b4a3-7b11d434244a')
            column(name: 'reported_value', value: 0.00)
            column(name: 'is_rh_participating_flag', value: 'false')
        }

        rollback {
            dbRollback
        }
    }
}
