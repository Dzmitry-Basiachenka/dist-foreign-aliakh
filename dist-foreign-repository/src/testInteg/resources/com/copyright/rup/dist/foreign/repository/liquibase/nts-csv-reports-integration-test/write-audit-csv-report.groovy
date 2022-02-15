databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-12-18-01', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteAuditCsvReport, testWriteAuditEmptyCsvReport, testWriteAuditCsvReportSearchBySqlLikePattern')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1da0461a-92f9-40cc-a3c1-9b972505b9c9')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'name', value: 'CFC/ Center Fran dexploitation du droit de Copie')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0c0a379a-461c-4e84-8062-326ece3c1f65')
            column(name: 'name', value: 'Test Batch 4')
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
            column(name: 'df_scenario_uid', value: 'c98ff8e0-81f6-49bb-a30b-a40f051ea824')
            column(name: 'name', value: 'NTS Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '328552ab-9951-4c41-b548-704060f456e6')
            column(name: 'df_scenario_uid', value: 'c98ff8e0-81f6-49bb-a30b-a40f051ea824')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b9a487f4-77b6-4264-8a53-ed1eab585a4a')
            column(name: 'df_usage_batch_uid', value: '0c0a379a-461c-4e84-8062-326ece3c1f65')
            column(name: 'df_scenario_uid', value: 'c98ff8e0-81f6-49bb-a30b-a40f051ea824')
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
            column(name: 'df_usage_fas_uid', value: 'b9a487f4-77b6-4264-8a53-ed1eab585a4a')
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
