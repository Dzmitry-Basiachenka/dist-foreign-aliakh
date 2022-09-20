databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-07-04-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testSendToCrm: insert NTS usages')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'name', value: 'NTS Scenario with paid usages')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario description')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 30.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'ab040bf1-717d-419a-b75c-6004c0036798')
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'adcd15c4-eb44-4e67-847a-7f386082646a')
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'payee_account_number', value: 1000003821)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 90.63)
            column(name: 'service_fee_amount', value: 29.00)
            column(name: 'net_amount', value: 61.63)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-12-12')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA October 17')
            column(name: 'distribution_date', value: '2017-11-11')
            column(name: 'lm_detail_id', value: 'c4232309-d890-489d-b99e-ca96c8e7e473')
            column(name: 'period_end_date', value: '2018-03-11')
            column(name: 'created_datetime', value: '2016-03-12')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'adcd15c4-eb44-4e67-847a-7f386082646a')
            column(name: 'reported_value', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '6fa92092-5cd3-4a12-bbf4-762f7ff6f815')
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'payee_account_number', value: 7000429266)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 13503.37)
            column(name: 'net_amount', value: 9182.28)
            column(name: 'service_fee_amount', value: 4321.07)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-12-12')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA October 17')
            column(name: 'distribution_date', value: '2017-11-11')
            column(name: 'lm_detail_id', value: '13f6c68e-9000-465d-8db0-fe03deeebe01')
            column(name: 'period_end_date', value: '2018-03-11')
            column(name: 'created_datetime', value: '2016-03-12')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6fa92092-5cd3-4a12-bbf4-762f7ff6f815')
            column(name: 'reported_value', value: 0.00)
        }

        rollback {
            dbRollback
        }
    }
}
