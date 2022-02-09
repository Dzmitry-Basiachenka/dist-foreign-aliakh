databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-06-18-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testMoveFundUsagesToArchive')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5bcf2c37-2f32-48e9-90fe-c9d75298eeed')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3d587554-7564-4db9-a67a-8f2b35fa673d')
            column(name: 'name', value: 'FAS batch')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'gross_amount', value: 1500.00)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9abfd0a0-2779-4321-af07-ebabe22627a0')
            column(name: 'name', value: 'NTS batch')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 0, "stm_minimum_amount": 0, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 0}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'f00b5ee9-c75e-499d-aa41-ae56b3d68211')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Pre-Service fee fund')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 199.98)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '79d47e6e-2e84-4e9a-b92c-ab8d745935ef')
            column(name: 'name', value: 'NTS scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 300.00, "pre_service_fee_fund_uid": "f00b5ee9-c75e-499d-aa41-ae56b3d68211"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '677e1740-c791-4929-87f9-e7fc68dd4699')
            column(name: 'df_usage_batch_uid', value: '3d587554-7564-4db9-a67a-8f2b35fa673d')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 99.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '677e1740-c791-4929-87f9-e7fc68dd4699')
            column(name: 'df_fund_pool_uid', value: 'f00b5ee9-c75e-499d-aa41-ae56b3d68211')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 99.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2a868c86-a639-400f-b407-0602dd7ec8df')
            column(name: 'df_usage_batch_uid', value: '3d587554-7564-4db9-a67a-8f2b35fa673d')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 99.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2a868c86-a639-400f-b407-0602dd7ec8df')
            column(name: 'df_fund_pool_uid', value: 'f00b5ee9-c75e-499d-aa41-ae56b3d68211')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 99.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9abfd0a0-2779-4321-af07-ebabe22627a0')
            column(name: 'df_usage_batch_uid', value: '3d587554-7564-4db9-a67a-8f2b35fa673d')
            column(name: 'df_scenario_uid', value: '79d47e6e-2e84-4e9a-b92c-ab8d745935ef')
            column(name: 'wr_wrk_inst', value: 632876487)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 2500.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9abfd0a0-2779-4321-af07-ebabe22627a0')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        rollback {
            dbRollback
        }
    }
}
