databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-02-01-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testReconciliationWithoutDiscrepancies')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'be66bae9-fa89-499a-8485-fb9e445bedd9')
            column(name: 'name', value: 'Test Reconcile rightsholders 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'name', value: 'Test Batch 1')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'fcdaea01-2439-4c51-b3e2-23649cf710c7')
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'df_scenario_uid', value: 'be66bae9-fa89-499a-8485-fb9e445bedd9')
            column(name: 'wr_wrk_inst', value: 471137470)
            column(name: 'work_title', value: 'Sunbeams')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'payee_account_number', value: 1000003821)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'service_fee_amount', value: 320.00)
            column(name: 'net_amount', value: 680.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'fcdaea01-2439-4c51-b3e2-23649cf710c7')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'd50d8097-b2dd-441b-bf3e-84eac1b3902a')
            column(name: 'df_usage_uid', value: 'fcdaea01-2439-4c51-b3e2-23649cf710c7')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test Batch 1\'')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05dc9217-26d4-46ca-aa6e-18572591f3c8')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'name', value: 'Abbey Publications, Inc. [L]')
        }

        rollback {
            dbRollback
        }
    }
}
