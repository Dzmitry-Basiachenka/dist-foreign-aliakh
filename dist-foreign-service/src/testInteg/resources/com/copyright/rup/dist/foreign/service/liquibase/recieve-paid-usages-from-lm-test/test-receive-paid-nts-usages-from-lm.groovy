databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-07-10-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testReceivePaidNtsUsagesFromLm')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05dc9217-26d4-46ca-aa6e-18572591f3c8')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'name', value: 'Abbey Publications, Inc. [L]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1f8074bd-9af7-42ec-95b1-853cddc32f40')
            column(name: 'name', value: 'NTS ReceivePaidUsageFromLM')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 30.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'cc391849-8da7-467f-bf43-90e836578283')
            column(name: 'df_scenario_uid', value: '1f8074bd-9af7-42ec-95b1-853cddc32f40')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '84cd1b64-d1eb-455f-a52c-f9dd0da2e356')
            column(name: 'df_scenario_uid', value: '1f8074bd-9af7-42ec-95b1-853cddc32f40')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'payee_account_number', value: 1000003821)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 90.63)
            column(name: 'service_fee_amount', value: 29.00)
            column(name: 'net_amount', value: 61.63)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'created_datetime', value: '2016-03-12')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '84cd1b64-d1eb-455f-a52c-f9dd0da2e356')
            column(name: 'reported_value', value: 0.00)
        }

        // will not be updated
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'c70b06a4-dfdf-4fe3-8ad9-f90d973f566f')
            column(name: 'df_scenario_uid', value: '1f8074bd-9af7-42ec-95b1-853cddc32f40')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'payee_account_number', value: 7000429266)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 13503.37)
            column(name: 'net_amount', value: 9182.28)
            column(name: 'service_fee_amount', value: 4321.07)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'created_datetime', value: '2016-03-12')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c70b06a4-dfdf-4fe3-8ad9-f90d973f566f')
            column(name: 'reported_value', value: 0.00)
        }

        rollback {
            dbRollback
        }
    }
}
