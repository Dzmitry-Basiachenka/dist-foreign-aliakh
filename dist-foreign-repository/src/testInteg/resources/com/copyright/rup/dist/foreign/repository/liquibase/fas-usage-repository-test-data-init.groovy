databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-10-17-00', author: 'Uladzislau Shalamitski <ushalmitski@copyright.com>') {
        comment("Insert test data for testDeleteFromScenarioByPayees")

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'edbcc8b3-8fa4-4c58-9244-a91627cac7a9')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'FAS2 scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '32daf15a-eb82-4b9e-862d-c647e72f76ca')
            column(name: 'name', value: 'FAS2 batch')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'fiscal_year', value: '2020')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7234feb4-a59e-483b-985a-e8de2e3eb190')
            column(name: 'df_usage_batch_uid', value: '32daf15a-eb82-4b9e-862d-c647e72f76ca')
            column(name: 'df_scenario_uid', value: 'edbcc8b3-8fa4-4c58-9244-a91627cac7a9')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7234feb4-a59e-483b-985a-e8de2e3eb190')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '582c86e2-213e-48ad-a885-f9ff49d48a69')
            column(name: 'df_usage_batch_uid', value: '32daf15a-eb82-4b9e-862d-c647e72f76ca')
            column(name: 'df_scenario_uid', value: 'edbcc8b3-8fa4-4c58-9244-a91627cac7a9')
            column(name: 'wr_wrk_inst', value: '773904752')
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'payee_account_number', value: '7000813806')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '84.00')
            column(name: 'service_fee_amount', value: '16.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '582c86e2-213e-48ad-a885-f9ff49d48a69')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
            column(name: 'is_rh_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '730d7964-f399-4971-9403-dbedc9d7a180')
            column(name: 'df_usage_batch_uid', value: '32daf15a-eb82-4b9e-862d-c647e72f76ca')
            column(name: 'df_scenario_uid', value: 'edbcc8b3-8fa4-4c58-9244-a91627cac7a9')
            column(name: 'wr_wrk_inst', value: '12318778798')
            column(name: 'work_title', value: 'Future of medicine')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'payee_account_number', value: '7000813806')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '3008902112317645XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '730d7964-f399-4971-9403-dbedc9d7a180')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
            column(name: 'is_payee_participating_flag', value: true)
        }
    }

    changeSet(id: '2019-10-17-01', author: 'Uladzislau Shalamitski <ushalmitski@copyright.com>') {
        comment("Insert test data for testRedisignateToNtsWithdrawnByPayees")

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '767a2647-7e6e-4479-b381-e642de480863')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'FAS2 scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '452baaea-5c52-417c-9ee6-70ab014624cf')
            column(name: 'name', value: 'FAS2 batch')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'fiscal_year', value: '2020')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '209a960f-5896-43da-b020-fc52981b9633')
            column(name: 'df_usage_batch_uid', value: '452baaea-5c52-417c-9ee6-70ab014624cf')
            column(name: 'df_scenario_uid', value: '767a2647-7e6e-4479-b381-e642de480863')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '209a960f-5896-43da-b020-fc52981b9633')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1ae671ca-ed5a-4d92-8ab6-a10a53d9884a')
            column(name: 'df_usage_batch_uid', value: '452baaea-5c52-417c-9ee6-70ab014624cf')
            column(name: 'df_scenario_uid', value: '767a2647-7e6e-4479-b381-e642de480863')
            column(name: 'wr_wrk_inst', value: '773904752')
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'payee_account_number', value: '7000813806')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '84.00')
            column(name: 'service_fee_amount', value: '16.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '1ae671ca-ed5a-4d92-8ab6-a10a53d9884a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
            column(name: 'is_rh_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '72f6abdb-c82d-4cee-aadf-570942cf0093')
            column(name: 'df_usage_batch_uid', value: '452baaea-5c52-417c-9ee6-70ab014624cf')
            column(name: 'df_scenario_uid', value: '767a2647-7e6e-4479-b381-e642de480863')
            column(name: 'wr_wrk_inst', value: '12318778798')
            column(name: 'work_title', value: 'Future of medicine')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'payee_account_number', value: '7000813806')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '3008902112317645XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '72f6abdb-c82d-4cee-aadf-570942cf0093')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
            column(name: 'is_rh_participating_flag', value: true)
        }
    }

    changeSet(id: '2019-10-17-02', author: 'Uladzislau Shalamitski <ushalmitski@copyright.com>') {
        comment("Insert test data for testUpdateResearchedUsage")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'name', value: 'Works without WrWrkInst test')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '2000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '721ca627-09bc-4204-99f4-6acae415fa5d')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '10000.00')
            column(name: 'net_amount', value: '8400.00')
            column(name: 'service_fee_amount', value: '1600.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '721ca627-09bc-4204-99f4-6acae415fa5d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'gross_amount', value: '10000.00')
            column(name: 'net_amount', value: '8400.00')
            column(name: 'service_fee_amount', value: '1600.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
        }
    }
}
