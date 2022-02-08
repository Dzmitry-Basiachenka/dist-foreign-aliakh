databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-02-15-01', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testFindUsageBatchStatusesNts')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a34417b5-12c1-48e2-9aed-d3861b49545b')
            column(name: 'name', value: 'NTS in progress batch')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2021-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'initial_usages_count', value: 9)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c09aa888-85a5-4377-8c7a-85d84d255b5a')
            column(name: 'df_usage_batch_uid', value: 'a34417b5-12c1-48e2-9aed-d3861b49545b')
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c09aa888-85a5-4377-8c7a-85d84d255b5a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a3b1f680-6f30-4e99-98fb-5547957da80f')
            column(name: 'df_usage_batch_uid', value: 'a34417b5-12c1-48e2-9aed-d3861b49545b')
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a3b1f680-6f30-4e99-98fb-5547957da80f')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4c7f2465-9f6a-4dc7-85aa-eada6392a7e2')
            column(name: 'df_usage_batch_uid', value: 'a34417b5-12c1-48e2-9aed-d3861b49545b')
            column(name: 'wr_wrk_inst', value: 642267671)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4c7f2465-9f6a-4dc7-85aa-eada6392a7e2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '92818564-32b1-418c-bec3-28ae03f80455')
            column(name: 'df_usage_batch_uid', value: 'a34417b5-12c1-48e2-9aed-d3861b49545b')
            column(name: 'wr_wrk_inst', value: 642267671)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '92818564-32b1-418c-bec3-28ae03f80455')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '09c0f81a-22e7-4b63-8bb3-f8feb01c86f0')
            column(name: 'df_usage_batch_uid', value: 'a34417b5-12c1-48e2-9aed-d3861b49545b')
            column(name: 'wr_wrk_inst', value: 642267671)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'NON_STM_RH')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '09c0f81a-22e7-4b63-8bb3-f8feb01c86f0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '97491bc3-815a-474c-a01d-9cc40b404bee')
            column(name: 'df_usage_batch_uid', value: 'a34417b5-12c1-48e2-9aed-d3861b49545b')
            column(name: 'wr_wrk_inst', value: 642267671)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'US_TAX_COUNTRY')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '97491bc3-815a-474c-a01d-9cc40b404bee')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '359de82f-374b-4d53-88ab-0be3982b22aa')
            column(name: 'name', value: 'NTS completed batch')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2021-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'initial_usages_count', value: 5)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5f491fe4-2f1a-434f-b536-92350c623dab')
            column(name: 'df_usage_batch_uid', value: '359de82f-374b-4d53-88ab-0be3982b22aa')
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5f491fe4-2f1a-434f-b536-92350c623dab')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9522a3cc-5526-48e5-b4f0-182a6c8ccdb1')
            column(name: 'df_usage_batch_uid', value: '359de82f-374b-4d53-88ab-0be3982b22aa')
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9522a3cc-5526-48e5-b4f0-182a6c8ccdb1')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'aee126ca-f143-44c4-9e65-3ca80ba59875')
            column(name: 'df_usage_batch_uid', value: '359de82f-374b-4d53-88ab-0be3982b22aa')
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'UNCLASSIFIED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'aee126ca-f143-44c4-9e65-3ca80ba59875')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 900)
        }

        rollback {
            dbRollback
        }
    }
}
