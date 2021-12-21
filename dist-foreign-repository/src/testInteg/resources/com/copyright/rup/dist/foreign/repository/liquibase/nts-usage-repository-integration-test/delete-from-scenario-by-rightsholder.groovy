databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-07-29-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testDeleteFromScenarioByRightsholder')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '907b191b-34a6-4b34-b515-80c4a659b268')
            column(name: 'name', value: 'NTS Batch')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 3000.00)
            column(name: 'initial_usages_count', value: 4)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '03b9357e-0823-4abf-8e31-c615d735bf3b')
            column(name: 'name', value: 'NTS Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'NTS Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'fd0aa145-ec9b-46ad-b3b9-e30ab98b1de6')
            column(name: 'df_scenario_uid', value: '03b9357e-0823-4abf-8e31-c615d735bf3b')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'fd0aa145-ec9b-46ad-b3b9-e30ab98b1de6')
            column(name: 'df_usage_batch_uid', value: '907b191b-34a6-4b34-b515-80c4a659b268')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '36ed0b9b-94f7-4c22-af2f-5a4246014ff7')
            column(name: 'df_usage_batch_uid', value: '907b191b-34a6-4b34-b515-80c4a659b268')
            column(name: 'df_scenario_uid', value: '03b9357e-0823-4abf-8e31-c615d735bf3b')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 33)
            column(name: 'service_fee', value: 0.16)
            column(name: 'service_fee_amount', value: 5.28)
            column(name: 'net_amount', value: 27.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '36ed0b9b-94f7-4c22-af2f-5a4246014ff7')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e767a2f5-c7a7-4720-b74d-4c1d5139e12a')
            column(name: 'df_usage_batch_uid', value: '907b191b-34a6-4b34-b515-80c4a659b268')
            column(name: 'df_scenario_uid', value: '03b9357e-0823-4abf-8e31-c615d735bf3b')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 66)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 21.12)
            column(name: 'net_amount', value: 44.88)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e767a2f5-c7a7-4720-b74d-4c1d5139e12a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 66)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2970a59f-f13b-4914-97eb-a51edffcf404')
            column(name: 'df_usage_batch_uid', value: '907b191b-34a6-4b34-b515-80c4a659b268')
            column(name: 'df_scenario_uid', value: '03b9357e-0823-4abf-8e31-c615d735bf3b')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 33)
            column(name: 'service_fee', value: 0.16)
            column(name: 'service_fee_amount', value: 5.28)
            column(name: 'net_amount', value: 27.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2970a59f-f13b-4914-97eb-a51edffcf404')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 33)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2040f5ce-fecc-4601-9eab-6d2f3e3854fd')
            column(name: 'df_usage_batch_uid', value: '907b191b-34a6-4b34-b515-80c4a659b268')
            column(name: 'df_scenario_uid', value: '03b9357e-0823-4abf-8e31-c615d735bf3b')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'wr_wrk_inst', value: 471137967)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 66)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 21.12)
            column(name: 'net_amount', value: 44.88)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2040f5ce-fecc-4601-9eab-6d2f3e3854fd')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 66)
            column(name: 'is_rh_participating_flag', value: true)
            column(name: 'is_payee_participating_flag', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
