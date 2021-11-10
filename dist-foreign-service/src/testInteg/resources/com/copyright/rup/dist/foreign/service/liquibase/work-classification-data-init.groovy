databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-03-18-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting data for testing applying classification')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '77b111d3-9eea-49af-b815-100b9716c1b3')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'name', value: 'CLA, The Copyright Licensing Agency Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'db8aca31-d542-4efb-9ba8-8b489014af36')
            column(name: 'wr_wrk_inst', value: 658824345)
            column(name: 'classification', value: 'NON-STM')
        }

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
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'bc0fe9bc-9b24-4324-b624-eed0d9773e19')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 658824345)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
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
            column(name: 'lm_detail_id', value: '63b3da58-ac6b-4946-bd67-37a251769467')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bc0fe9bc-9b24-4324-b624-eed0d9773e19')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '33113b79-791a-4aa9-b192-12b292c32823')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 658824345)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: 'bfa206bb-83fa-4c71-9a89-99f743018237')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '33113b79-791a-4aa9-b192-12b292c32823')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2001)
            column(name: 'market_period_to', value: 2003)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '3e69b60d-a8a3-4812-b3b9-6a291ad23b1c')
            column(name: 'df_usage_batch_uid', value: '63e350c1-b211-485f-bc42-6ebee8acb22d')
            column(name: 'df_scenario_uid', value: '929b9f19-489a-47a2-a680-4d5ad6ba887a')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 836698198)
            column(name: 'work_title', value: 'Corporate identity manuals')
            column(name: 'system_title', value: 'Corporate identity manuals')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
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
            column(name: 'lm_detail_id', value: '168dc9ea-fc94-440e-a1c7-1020fa13744f')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3e69b60d-a8a3-4812-b3b9-6a291ad23b1c')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'name', value: 'NTS fund pool')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'product_family', value: 'NTS')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":7,"stm_amount":700,"stm_minimum_amount":50,"non_stm_amount":5000,"fund_pool_period_from":2010,"markets":["Bus","Doc Del"],"fund_pool_period_to":2012}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '85093193-00d9-436b-8fbc-078511b1d335')
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 658824345)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 500.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '85093193-00d9-436b-8fbc-078511b1d335')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2012)
            column(name: 'market_period_to', value: 2014)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b51340ad-cf32-4c38-8445-4455e4ae81eb')
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: 'Corporate identity manuals')
            column(name: 'system_title', value: 'Corporate identity manuals')
            column(name: 'rh_account_number', value: 836698198)
            column(name: 'status_ind', value: 'UNCLASSIFIED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 500.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b51340ad-cf32-4c38-8445-4455e4ae81eb')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2010)
            column(name: 'market_period_to', value: 2011)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 500)
        }

        rollback {
            dbRollback
        }
    }
}
