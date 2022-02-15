databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-01-03-01', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testWriteArchivedScenarioUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1da0461a-92f9-40cc-a3c1-9b972505b9c9')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'name', value: 'CFC/ Center Fran dexploitation du droit de Copie')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1ead8a3e-1231-43a5-a3c5-ed766abe5a2f')
            column(name: 'name', value: 'NTS Scenario with archived usages')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario description')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":300.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '4a5bf68a-67fb-4dd5-ac26-94ef19dc25de')
            column(name: 'df_scenario_uid', value: '1ead8a3e-1231-43a5-a3c5-ed766abe5a2f')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'e256decd-46ac-4625-aa80-cafb26f93c2a')
            column(name: 'df_scenario_uid', value: '1ead8a3e-1231-43a5-a3c5-ed766abe5a2f')
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
            column(name: 'df_usage_fas_uid', value: 'e256decd-46ac-4625-aa80-cafb26f93c2a')
            column(name: 'reported_value', value: 0.00)
            column(name: 'is_rh_participating_flag', value: 'false')
        }

        rollback {
            dbRollback
        }
    }
}
