databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-03-29-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testAddWithdrawnUsagesToNtsFundPool')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0f00e96b-eed7-4f26-8004-3370ec30da45')
            column(name: 'name', value: 'FAS batch test delete NTS fund pool')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'gross_amount', value: 10.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ba95f0b3-dc94-4925-96f2-93d05db9c469')
            column(name: 'df_usage_batch_uid', value: '0f00e96b-eed7-4f26-8004-3370ec30da45')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ba95f0b3-dc94-4925-96f2-93d05db9c469')
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '83027b25-f269-4bec-a8ea-b126431eedbf')
            column(name: 'name', value: 'CADRA_10Dec16')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, "fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '73027b25-f269-4bec-a8ea-b126431eedbe')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c6cb5b07-45c0-4188-9da3-920046eec4cf')
            column(name: 'df_usage_batch_uid', value: '73027b25-f269-4bec-a8ea-b126431eedbe')
            column(name: 'wr_wrk_inst', value: 987632764)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'UNCLASSIFIED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 2502232)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c6cb5b07-45c0-4188-9da3-920046eec4cf')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2255188f-d582-4516-8c08-835cfe1d68c2')
            column(name: 'df_usage_batch_uid', value: '73027b25-f269-4bec-a8ea-b126431eedbe')
            column(name: 'wr_wrk_inst', value: 12318778798)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'UNCLASSIFIED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 35000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2255188f-d582-4516-8c08-835cfe1d68c2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f6cb5b07-45c0-4188-9da3-920046eec4c0')
            column(name: 'df_usage_batch_uid', value: '83027b25-f269-4bec-a8ea-b126431eedbf')
            column(name: 'wr_wrk_inst', value: 122267671)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '0804709114')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f6cb5b07-45c0-4188-9da3-920046eec4c0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 296.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f255188f-d582-4516-8c08-835cfe1d68c3')
            column(name: 'df_usage_batch_uid', value: '83027b25-f269-4bec-a8ea-b126431eedbf')
            column(name: 'wr_wrk_inst', value: 159526526)
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f255188f-d582-4516-8c08-835cfe1d68c3')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 162.41)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'd78924f5-baf1-4c70-aa80-3c49c193b9f0')
            column(name: 'wr_wrk_inst', value: 122267671)
            column(name: 'classification', value: 'STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'a78924f5-4af1-7c70-1a80-5c49c193b9f1')
            column(name: 'wr_wrk_inst', value: 159526526)
            column(name: 'classification', value: 'NON-STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'c78924f5-baf1-4c70-aa80-3c49c193b9ff')
            column(name: 'wr_wrk_inst', value: 987632764)
            column(name: 'classification', value: 'NON-STM')
        }

        rollback {
            dbRollback
        }
    }
}
