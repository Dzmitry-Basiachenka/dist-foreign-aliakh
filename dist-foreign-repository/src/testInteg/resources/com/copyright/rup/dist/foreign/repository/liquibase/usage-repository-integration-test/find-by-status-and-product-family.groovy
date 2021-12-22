databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-01-14-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testFindByStatusAnsProductFamily and testFindIdsByStatusAnsProductFamily')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'bc4076cc-7554-4575-a03e-d4f4b3b76ca9')
            column(name: 'rh_account_number', value: 1000009523)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1b2f23a7-921a-4116-9268-86627fd2fc0b')
            column(name: 'rh_account_number', value: 1000009524)
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd368a40b-d3ab-45b1-80a3-07be2cd5224c')
            column(name: 'name', value: 'FAS batch 3')
            column(name: 'rro_account_number', value: 2000017011)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '463e2239-1a36-41cc-9a51-ee2a80eae0c7')
            column(name: 'df_usage_batch_uid', value: 'd368a40b-d3ab-45b1-80a3-07be2cd5224c')
            column(name: 'product_family', value: 'NTS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'US_TAX_COUNTRY')
            column(name: 'rh_account_number', value: 1000009523)
            column(name: 'standard_number', value: '2192-3559')
            column(name: 'standard_number_type', value: 'STDID')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'net_amount', value: 840.00)
            column(name: 'service_fee_amount', value: 160.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '463e2239-1a36-41cc-9a51-ee2a80eae0c7')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2015)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 200)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bd407b50-6101-4304-8316-6404fe32a800')
            column(name: 'df_usage_batch_uid', value: 'd368a40b-d3ab-45b1-80a3-07be2cd5224c')
            column(name: 'product_family', value: 'NTS')
            column(name: 'wr_wrk_inst', value: 823904752)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'status_ind', value: 'US_TAX_COUNTRY')
            column(name: 'rh_account_number', value: 1000009524)
            column(name: 'standard_number', value: '2192-2555')
            column(name: 'standard_number_type', value: 'STDID')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bd407b50-6101-4304-8316-6404fe32a800')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Medicine')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Arturo de Mézières')
            column(name: 'reported_value', value: 100)
        }

        rollback {
            dbRollback
        }
    }
}
