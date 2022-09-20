databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-12-11-01', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testWriteUsagesCsvReport, testWriteUsagesEmptyCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '99991909-744c-4766-ad67-fdc9e2c043eb')
            column(name: 'rh_account_number', value: 1000002901)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1da0461a-92f9-40cc-a3c1-9b972505b9c9')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'name', value: 'CFC/ Center Fran dexploitation du droit de Copie')
        }

        // NTS batch with ELIGIBLE usages
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f20ac1a3-eee4-4027-b5fb-def9adf0f871')
            column(name: 'name', value: 'NTS batch')
            column(name: 'rro_account_number', value: 2000017001)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2019-01-01')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Bus,Univ,Doc Del"], "stm_amount": 10, "non_stm_amount": 20, "stm_minimum_amount": 30, "non_stm_minimum_amount": 40, "fund_pool_period_to": 2017, "fund_pool_period_from": 2017}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9705a377-d59a-4c84-bd26-7c754aab92e2')
            column(name: 'df_usage_batch_uid', value: 'f20ac1a3-eee4-4027-b5fb-def9adf0f871')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9705a377-d59a-4c84-bd26-7c754aab92e2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '581b71b0-07b1-4db1-9a3b-351c5c5a8cf0')
            column(name: 'df_usage_batch_uid', value: 'f20ac1a3-eee4-4027-b5fb-def9adf0f871')
            column(name: 'wr_wrk_inst', value: 743904744)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: 1000002901)
            column(name: 'payee_account_number', value: 1000002901)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '581b71b0-07b1-4db1-9a3b-351c5c5a8cf0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        // NTS batch with LOCKED usage
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c0c07d51-2216-43c3-b61b-b904d86ec36a')
            column(name: 'name', value: 'Test Batch 2')
            column(name: 'rro_account_number', value: 2000017001)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 8972.00)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"markets": ["Bus,Univ,Doc Del"], "stm_amount": 10, "non_stm_amount": 20, "stm_minimum_amount": 30, "non_stm_minimum_amount": 40, "fund_pool_period_to": 2017, "fund_pool_period_from": 2017}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'c90921d6-3315-4673-8825-2e0c6f7229ee')
            column(name: 'name', value: 'NTS Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'b5844849-8399-4ee1-a15b-0908a88f6570')
            column(name: 'df_scenario_uid', value: 'c90921d6-3315-4673-8825-2e0c6f7229ee')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bfc2ffbf-1b70-447e-a604-a6dee260d648')
            column(name: 'df_usage_batch_uid', value: 'c0c07d51-2216-43c3-b61b-b904d86ec36a')
            column(name: 'df_scenario_uid', value: 'c90921d6-3315-4673-8825-2e0c6f7229ee')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'work_title', value: 'CHICKEN BREAST ON GRILL WITH FLAMES')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'payee_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'net_amount', value: 6100.9872)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 2871.0528)
            column(name: 'gross_amount', value: 8972.04)
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bfc2ffbf-1b70-447e-a604-a6dee260d648')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 9900.00)
            column(name: 'is_rh_participating_flag', value: false)
            column(name: 'is_payee_participating_flag', value: false)
        }

        rollback {
            dbRollback
        }
    }
}
