databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-12-01-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserting data for testRemoveClassification')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '77b111d3-9eea-49af-b815-100b9716c1b3')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'name', value: 'CLA, The Copyright Licensing Agency Ltd.')
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
