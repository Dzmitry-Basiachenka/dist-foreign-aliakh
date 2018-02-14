databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-02-01-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Insert predefined data for ReconcileRightsholderUiTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'name', value: 'Scenario for reconciliation 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
            column(name: 'created_datetime', value: '2017-03-17')
            column(name: 'updated_datetime', value: '2017-03-17')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'name', value: 'JAACC_11Dec16')
            column(name: 'rro_account_number', value: '7001440663')
            column(name: 'payment_date', value: '2018-08-16')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'gross_amount', value: '28143.27')
            column(name: 'updated_datetime', value: '2017-02-07 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '47943793-4f9a-47b1-b2a8-e95a87aa58g6')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'detail_id', value: '6907853886')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '1000008666')
            column(name: 'payee_account_number', value: '1000008666')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '3000.00')
            column(name: 'gross_amount', value: '7675.44')
            column(name: 'net_amount', value: '5219.30')
            column(name: 'service_fee_amount', value: '2456.14')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3846ead0-87ae-4d9b-8dfe-1b985d78c062')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'detail_id', value: '6607723886')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '1000008666')
            column(name: 'payee_account_number', value: '1000008666')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '5000.00')
            column(name: 'gross_amount', value: '12792.40')
            column(name: 'net_amount', value: '8698.83')
            column(name: 'service_fee_amount', value: '4093.57')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b7ee7e4c-ec30-400b-ba49-70aaf8a4941e')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'detail_id', value: '3907723836')
            column(name: 'wr_wrk_inst', value: '122235139')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'payee_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '2500.00')
            column(name: 'gross_amount', value: '6396.19')
            column(name: 'net_amount', value: '4349.41')
            column(name: 'service_fee_amount', value: '2046.78')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b7ee7e4c-ec30-400b-ba49-70aaf8a4942e')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'detail_id', value: '3907723856')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'payee_account_number', value: '1000002797')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '500.00')
            column(name: 'gross_amount', value: '1279.24')
            column(name: 'net_amount', value: '869.88')
            column(name: 'service_fee_amount', value: '409.36')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a895-15a4b870fa39')
            column(name: 'name', value: 'Scenario for reconciliation 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
            column(name: 'created_datetime', value: '2017-03-17')
            column(name: 'updated_datetime', value: '2017-03-17')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d5-b026-94d3458a666a')
            column(name: 'name', value: 'JAACC_11Dec17')
            column(name: 'rro_account_number', value: '7001440663')
            column(name: 'payment_date', value: '2018-08-16')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'gross_amount', value: '1000')
            column(name: 'updated_datetime', value: '2017-02-07 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '47943793-4f9a-47b1-b2b8-e95a87aa58g6')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d5-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a895-15a4b870fa39')
            column(name: 'detail_id', value: '6905853886')
            column(name: 'wr_wrk_inst', value: '122235147')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '1000008666')
            column(name: 'payee_account_number', value: '1000008666')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '1000.00')
            column(name: 'gross_amount', value: '1000.00')
            column(name: 'net_amount', value: '680.00')
            column(name: 'service_fee_amount', value: '320.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'e9c9f51b-6048-4474-848a-2db1c410e463')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'name', value: 'British Film Institute (BFI)')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '6eb8ddf3-f08e-4c17-a0c5-5173d43a1625')
            column(name: 'rh_account_number', value: '1000008666')
            column(name: 'name', value: 'CCH')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '9905f006-a3e1-4061-b3d4-e7ece191103f')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '6aea1b34-b409-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: '47943793-4f9a-47b1-b2a8-e95a87aa58g6')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'JAACC_11Dec16' Batch")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7aa31ab2-b409-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: '3846ead0-87ae-4d9b-8dfe-1b985d78c062')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'JAACC_11Dec16' Batch")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '8b490944-b409-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: 'b7ee7e4c-ec30-400b-ba49-70aaf8a4941e')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'JAACC_11Dec16' Batch")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '97afdf1e-b409-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: 'b7ee7e4c-ec30-400b-ba49-70aaf8a4942e')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'JAACC_11Dec16' Batch")
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {}
            delete(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {}
            delete(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {}
            delete(schemaName: dbAppsSchema, tableName: 'df_usage') {}
            delete(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {}
            delete(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {}
            delete(schemaName: dbAppsSchema, tableName: 'df_scenario') {}
        }
    }
}
