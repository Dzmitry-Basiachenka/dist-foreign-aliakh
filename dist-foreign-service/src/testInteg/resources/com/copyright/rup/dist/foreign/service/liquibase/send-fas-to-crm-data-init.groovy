databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2018-04-04-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testSendToCrm')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5bcf2c37-2f32-48e9-90fe-c9d75298eeed')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a0663c51-87c6-4d03-8683-7640f12ae8c1')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '221c5a30-1937-4bf6-977f-93741f9b20f1')
            column(name: 'name', value: 'Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'd612ad12-1d53-4284-9bed-050638e0b05f')
            column(name: 'df_scenario_uid', value: '221c5a30-1937-4bf6-977f-93741f9b20f1')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '10defe40-ef47-47c9-96b4-be9226f3d591')
            column(name: 'name', value: 'Paid batch')
            column(name: 'rro_account_number', value: 1000005413)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'initial_usages_count', value: 6)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '0d1829eb-de35-4f93-bb36-2a7435263051')
            column(name: 'df_usage_batch_uid', value: '10defe40-ef47-47c9-96b4-be9226f3d591')
            column(name: 'df_scenario_uid', value: '221c5a30-1937-4bf6-977f-93741f9b20f1')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-03-11')
            column(name: 'period_end_date', value: '2016-03-11')
            column(name: 'created_datetime', value: '2016-03-11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '0d1829eb-de35-4f93-bb36-2a7435263051')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2016)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        // usage with invalid fields
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '9e356e22-57b3-49b3-af99-155093a9dc0a')
            column(name: 'df_usage_batch_uid', value: '10defe40-ef47-47c9-96b4-be9226f3d591')
            column(name: 'df_scenario_uid', value: '221c5a30-1937-4bf6-977f-93741f9b20f1')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-03-11')
            column(name: 'ccc_event_id', value: '152130')
            column(name: 'period_end_date', value: '2016-03-11')
            column(name: 'created_datetime', value: '2016-03-11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9e356e22-57b3-49b3-af99-155093a9dc0a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2016)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        // usage with invalid fields
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '53496a2f-fb52-4b5b-9f60-9034cceb69b9')
            column(name: 'df_usage_batch_uid', value: '10defe40-ef47-47c9-96b4-be9226f3d591')
            column(name: 'df_scenario_uid', value: '221c5a30-1937-4bf6-977f-93741f9b20f1')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-03-11')
            column(name: 'ccc_event_id', value: '141029')
            column(name: 'period_end_date', value: '2016-03-11')
            column(name: 'created_datetime', value: '2016-03-11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '53496a2f-fb52-4b5b-9f60-9034cceb69b9')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2016)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'feefdfd2-71fe-4c0a-a701-9dacffa9bccb')
            column(name: 'df_usage_batch_uid', value: '10defe40-ef47-47c9-96b4-be9226f3d591')
            column(name: 'df_scenario_uid', value: '221c5a30-1937-4bf6-977f-93741f9b20f1')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'feefdfd2-71fe-4c0a-a701-9dacffa9bccb')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'cb7e3237-50c3-46a5-938e-46afd8c1e0bf')
            column(name: 'name', value: 'Sent to CRM Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'All usages reported to CRM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'd2a33753-9e84-463e-a2fc-79de8b57744c')
            column(name: 'df_scenario_uid', value: 'cb7e3237-50c3-46a5-938e-46afd8c1e0bf')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '675fd69f-6c81-4469-83a6-38ba40a081b5')
            column(name: 'df_usage_batch_uid', value: '10defe40-ef47-47c9-96b4-be9226f3d591')
            column(name: 'df_scenario_uid', value: 'cb7e3237-50c3-46a5-938e-46afd8c1e0bf')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-03-11')
            column(name: 'ccc_event_id', value: '140918')
            column(name: 'period_end_date', value: '2016-03-11')
            column(name: 'created_datetime', value: '2016-03-11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '675fd69f-6c81-4469-83a6-38ba40a081b5')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2016)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2018-09-14-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testSendToCrm: insert post distribution usage')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '48189e92-b9d2-46be-94a4-c2adf83f21ce')
            column(name: 'df_usage_batch_uid', value: '10defe40-ef47-47c9-96b4-be9226f3d591')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '763497')
            column(name: 'check_date', value: '2018-11-03')
            column(name: 'distribution_name', value: 'FDA March 18')
            column(name: 'distribution_date', value: '2018-03-11')
            column(name: 'ccc_event_id', value: '156665')
            column(name: 'period_end_date', value: '2018-03-11')
            column(name: 'created_datetime', value: '2018-03-11')
            column(name: 'lm_detail_id', value: '48189e92-b9d2-46be-94a4-c2adf83f21ce')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '48189e92-b9d2-46be-94a4-c2adf83f21ce')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2016)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        rollback {
            dbRollback
        }
    }
}
