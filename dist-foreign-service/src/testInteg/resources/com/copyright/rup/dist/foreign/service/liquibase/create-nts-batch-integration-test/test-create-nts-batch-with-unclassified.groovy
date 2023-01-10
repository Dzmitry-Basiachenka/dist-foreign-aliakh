databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-03-07-01', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting data for testCreateNtsBatchWithUnclassified')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'name', value: 'Archived scenario')
            column(name: 'status_ind', value: 'ARCHIVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'name', value: 'Archived batch')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 15000.00)
            column(name: 'initial_usages_count', value: 13)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '28749ea6-0c42-4e4d-9b7d-64cdb94817d0')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 576324545)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1176.916)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '20ee948c-3ed1-49f8-83fe-6bc106ef3c25')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '28749ea6-0c42-4e4d-9b7d-64cdb94817d0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Gov')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        rollback {
            dbRollback
        }
    }
}
