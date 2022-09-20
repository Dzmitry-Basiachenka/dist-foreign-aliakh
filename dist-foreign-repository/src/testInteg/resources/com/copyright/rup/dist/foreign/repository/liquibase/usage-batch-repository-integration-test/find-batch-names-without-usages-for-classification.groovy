databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-02-09-03', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindBatchNamesWithoutUsagesForClassificationStm, testFindBatchNamesWithoutUsagesForClassificationNonStm, testFindBatchNamesWithoutUsagesForClassificationUnclassified')

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'f76738d6-3962-4dc3-a485-0683dee70d40')
            column(name: 'wr_wrk_inst', value: 122266795)
            column(name: 'classification', value: 'NON-STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e92f9fcd-9eec-4170-960c-f9f8d6cabd2d')
            column(name: 'name', value: 'NTS Batch without STM usages')
            column(name: 'rro_account_number', value: 123456789)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 0, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7, "excluding_stm": true}')
            column(name: 'updated_datetime', value: '2019-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '80f12013-f898-46f0-889d-9e249398c7e0')
            column(name: 'df_usage_batch_uid', value: 'e92f9fcd-9eec-4170-960c-f9f8d6cabd2d')
            column(name: 'wr_wrk_inst', value: 122265847)
            column(name: 'work_title', value: 'Ulysses')
            column(name: 'system_title', value: 'Ulysses')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '80f12013-f898-46f0-889d-9e249398c7e0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '14bc203b-a1a1-412e-848f-fa0a5c45a28d')
            column(name: 'df_usage_batch_uid', value: 'e92f9fcd-9eec-4170-960c-f9f8d6cabd2d')
            column(name: 'wr_wrk_inst', value: 122266795)
            column(name: 'work_title', value: 'Nine stories')
            column(name: 'system_title', value: 'Nine stories')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '14bc203b-a1a1-412e-848f-fa0a5c45a28d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 1000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '99725b90-c4da-414d-9159-f1c8c83c5e19')
            column(name: 'name', value: 'NTS Batch with unclassified usages')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 0, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            column(name: 'updated_datetime', value: '2019-01-02 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cd31e600-9755-422a-99b7-d8d8f3adbacd')
            column(name: 'df_usage_batch_uid', value: '99725b90-c4da-414d-9159-f1c8c83c5e19')
            column(name: 'wr_wrk_inst', value: 122266807)
            column(name: 'work_title', value: 'My servants')
            column(name: 'system_title', value: 'My servants')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'cd31e600-9755-422a-99b7-d8d8f3adbacd')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9713604b-4eeb-4148-b112-5fef7a1c0924')
            column(name: 'df_usage_batch_uid', value: '99725b90-c4da-414d-9159-f1c8c83c5e19')
            column(name: 'wr_wrk_inst', value: 122266947)
            column(name: 'work_title', value: 'Lucky Jim')
            column(name: 'system_title', value: 'Lucky Jim')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9713604b-4eeb-4148-b112-5fef7a1c0924')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 1000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '6d302e23-ed25-45cd-b77e-94af38965bb5')
            column(name: 'wr_wrk_inst', value: 122267149)
            column(name: 'classification', value: 'BELLETRISTIC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '833c132e-fa3e-40c9-a5b3-37d78c723120')
            column(name: 'wr_wrk_inst', value: 122267353)
            column(name: 'classification', value: 'BELLETRISTIC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3da5944b-dab1-47a4-a29d-2dbecc6737e0')
            column(name: 'name', value: 'NTS Batch with Belletristic usages')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            column(name: 'updated_datetime', value: '2019-01-03 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '156d4aa1-b7dd-4a2e-bfa3-c90207ff98c4')
            column(name: 'df_usage_batch_uid', value: '3da5944b-dab1-47a4-a29d-2dbecc6737e0')
            column(name: 'wr_wrk_inst', value: 122267149)
            column(name: 'work_title', value: 'Personal influence')
            column(name: 'system_title', value: 'Personal influence')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '156d4aa1-b7dd-4a2e-bfa3-c90207ff98c4')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a773031f-0922-4381-8a3d-ade88df14580')
            column(name: 'df_usage_batch_uid', value: '3da5944b-dab1-47a4-a29d-2dbecc6737e0')
            column(name: 'wr_wrk_inst', value: 122267353)
            column(name: 'work_title', value: 'Deliver us from evil')
            column(name: 'system_title', value: 'Deliver us from evil')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a773031f-0922-4381-8a3d-ade88df14580')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 1000)
        }

        rollback {
            dbRollback
        }
    }
}
