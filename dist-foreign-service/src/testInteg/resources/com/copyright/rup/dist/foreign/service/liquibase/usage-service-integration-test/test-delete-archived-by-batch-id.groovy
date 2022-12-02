databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-12-01-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testDeleteArchivedByBatchId')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'ceb97ce8-0efe-492d-a6c8-abe9dcbf325c')
            column(name: 'name', value: 'Archived scenario')
            column(name: 'status_ind', value: 'ARCHIVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0dc1cff9-fc33-47ef-866c-c97de0203f9c')
            column(name: 'name', value: 'Archived batch')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 15000.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '8b09419a-89d1-47ca-8c5a-5ee206e0b0e0')
            column(name: 'df_usage_batch_uid', value: '0dc1cff9-fc33-47ef-866c-c97de0203f9c')
            column(name: 'df_scenario_uid', value: 'ceb97ce8-0efe-492d-a6c8-abe9dcbf325c')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '30855870-09df-4341-bd88-2a92c5470d60')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8b09419a-89d1-47ca-8c5a-5ee206e0b0e0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Not Suitable For fund pool')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '3b663c5a-7a4f-48b5-9b2f-89ce49aa23a3')
            column(name: 'df_usage_uid', value: '8b09419a-89d1-47ca-8c5a-5ee206e0b0e0')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Archived batch\'')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '74cdde4e-686b-47b7-b0ba-e1f6d9592755')
            column(name: 'df_usage_uid', value: '8b09419a-89d1-47ca-8c5a-5ee206e0b0e0')
            column(name: 'action_type_ind', value: 'PAID')
            column(name: 'action_reason', value: 'Usage has been paid according to information from the LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '06de58a5-eb32-4228-afdc-3cfac70922c3')
            column(name: 'df_usage_uid', value: '8b09419a-89d1-47ca-8c5a-5ee206e0b0e0')
            column(name: 'action_type_ind', value: 'ARCHIVED')
            column(name: 'action_reason', value: 'Usage was sent to CRM')
        }

        rollback {
            dbRollback
        }
    }
}
