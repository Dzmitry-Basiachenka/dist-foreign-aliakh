databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2017-11-30-00', author: 'Aliaksandra_Bayanouskaya <abayanouskaya@copyright.com>') {
        comment('Inserting test data for testGetTotalAmountByTitleAndBatchId')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'cb597f4e-f636-447f-8710-0436d8994d10')
            column(name: 'name', value: 'Batch with usages in WORK_NOT_FOUND status')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 10.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4dd8cdf8-ca10-422e-bdd5-3220105e6379')
            column(name: 'df_usage_batch_uid', value: 'cb597f4e-f636-447f-8710-0436d8994d10')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'gross_amount', value: 16.40)
            column(name: 'updated_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4dd8cdf8-ca10-422e-bdd5-3220105e6379')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c10a11c6-dae3-43d7-a632-c682542b1209')
            column(name: 'name', value: 'Works without WrWrkInst test 1')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '593c49c3-eb5b-477b-8556-f7a4725df2b3')
            column(name: 'df_usage_batch_uid', value: 'c10a11c6-dae3-43d7-a632-c682542b1209')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 10000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '593c49c3-eb5b-477b-8556-f7a4725df2b3')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7db6455e-5249-44db-801a-307f1c239310')
            column(name: 'df_usage_batch_uid', value: 'c10a11c6-dae3-43d7-a632-c682542b1209')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'standard_number', value: '2192-3566')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 10000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7db6455e-5249-44db-801a-307f1c239310')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 10000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '66e0deb8-9cf5-4495-994a-d3a5761572f3')
            column(name: 'name', value: 'Batch for sorting 1')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9776da8d-098d-4f39-99fd-85405c339e9b')
            column(name: 'name', value: 'Batch for sorting 2')
            column(name: 'rro_account_number', value: 7000896777)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2023-02-12')
            column(name: 'fiscal_year', value: 2023)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3c31db4f-4065-4fe1-84c2-b48a0f3bc079')
            column(name: 'df_usage_batch_uid', value: '9776da8d-098d-4f39-99fd-85405c339e9b')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 200)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'comment', value: 'usage from usages_25.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3c31db4f-4065-4fe1-84c2-b48a0f3bc079')
            column(name: 'article', value: 'DIN EN 779:2014')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2014-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2014)
            column(name: 'market_period_to', value: 2018)
            column(name: 'author', value: 'Aarseth, Espen J.')
            column(name: 'reported_value', value: 2000)
        }

        rollback {
            dbRollback
        }
    }
}
