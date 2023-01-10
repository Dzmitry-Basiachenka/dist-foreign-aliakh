databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2018-09-13-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testReceivePostDistributionUsageFromLm')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '19de64a0-f4d6-4ddf-9d00-882325e39c58')
            column(name: 'name', value: 'Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '244ecf91-bc53-4cf3-a3c4-170019125b46')
            column(name: 'name', value: 'Post Distribution Paid batch')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ef058a3f-b60e-429b-b6e3-14d386eb86ba')
            column(name: 'df_usage_batch_uid', value: '244ecf91-bc53-4cf3-a3c4-170019125b46')
            column(name: 'df_scenario_uid', value: '19de64a0-f4d6-4ddf-9d00-882325e39c58')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'payee_account_number', value: 1000000001)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'net_amount', value: 1680.00)
            column(name: 'service_fee_amount', value: 320.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ef058a3f-b60e-429b-b6e3-14d386eb86ba')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 6000)
        }

        rollback {
            dbRollback
        }
    }
}
