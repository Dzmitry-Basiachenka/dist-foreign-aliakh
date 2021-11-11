databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-11-08-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testFindByProductFamily')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'b5b64c3a-55d2-462e-b169-362dca6a4dd7')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Q1 2019 100%')
            column(name: 'comment', value: 'some comment')
            column(name: 'total_amount', value: 50.00)
            column(name: 'updated_datetime', value: '2019-03-27 16:35:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '49060c9b-9cc2-4b93-b701-fffc82eb28b0')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 10.00)
            column(name: 'updated_datetime', value: '2019-03-26 16:35:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a163cca7-8eeb-449c-8a3c-29ff3ec82e58')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 10)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b2d4646f-9ecb-4b64-bb47-384de8d3f7f1')
            column(name: 'df_usage_batch_uid', value: 'a163cca7-8eeb-449c-8a3c-29ff3ec82e58')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 25)
            column(name: 'gross_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b2d4646f-9ecb-4b64-bb47-384de8d3f7f1')
            column(name: 'df_fund_pool_uid', value: '49060c9b-9cc2-4b93-b701-fffc82eb28b0')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '1a615c47-531a-4a27-a4f3-a5bd3d5a4b1c')
            column(name: 'name', value: 'CADRA_12Dec16')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 99.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'a40132c0-d724-4450-81d2-456e67ff6f64')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Archived Pre-Service fee fund')
            column(name: 'total_amount', value: 99.00)
            column(name: 'updated_datetime', value: '2019-03-26 16:35:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '22af0aa6-0ce9-4a51-9535-78dc811044b4')
            column(name: 'df_usage_batch_uid', value: '1a615c47-531a-4a27-a4f3-a5bd3d5a4b1c')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 25)
            column(name: 'gross_amount', value: 99.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '22af0aa6-0ce9-4a51-9535-78dc811044b4')
            column(name: 'df_fund_pool_uid', value: 'a40132c0-d724-4450-81d2-456e67ff6f64')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'd4834496-b680-4cc8-b4bc-295440b39c59')
            column(name: 'name', value: 'FAS Distribution 2019-2')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 300.00, "pre_service_fee_fund_uid": "a40132c0-d724-4450-81d2-456e67ff6f64"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '63b45167-a6ce-4cd5-84c6-5167916aee98')
            column(name: 'name', value: 'CADRA_13Dec16')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 150.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ea4be90e-a0a8-4e72-b1e3-28545c687ae8')
            column(name: 'df_usage_batch_uid', value: '63b45167-a6ce-4cd5-84c6-5167916aee98')
            column(name: 'df_scenario_uid', value: 'd4834496-b680-4cc8-b4bc-295440b39c59')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 150.00)
            column(name: 'net_amount', value: 126.00)
            column(name: 'service_fee_amount', value: 24.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-03-11')
            column(name: 'period_end_date', value: '2016-03-11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ea4be90e-a0a8-4e72-b1e3-28545c687ae8')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2016)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 150)
        }

        rollback {
            dbRollback
        }
    }
}
