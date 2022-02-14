databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-12-11-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testWriteUsagesCsvReport, testWriteUsagesEmptyCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1da0461a-92f9-40cc-a3c1-9b972505b9c9')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'name', value: 'CFC/ Center Fran dexploitation du droit de Copie')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '99991909-744c-4766-ad67-fdc9e2c043eb')
            column(name: 'rh_account_number', value: 1000002901)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        // batch with ELIGIBLE usages
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '4c9cc089-b812-42cf-a5d2-1f5eda51fa76')
            column(name: 'name', value: 'FAS batch 1')
            column(name: 'rro_account_number', value: 2000017001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2019-01-01')
            column(name: 'fiscal_year', value: 2010)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '25a96343-310f-4c9d-8923-63a58a3f57c6')
            column(name: 'df_usage_batch_uid', value: '4c9cc089-b812-42cf-a5d2-1f5eda51fa76')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 250.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '25a96343-310f-4c9d-8923-63a58a3f57c6')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2cd2b2be-605d-4fd5-9855-ca1064b00366')
            column(name: 'df_usage_batch_uid', value: '4c9cc089-b812-42cf-a5d2-1f5eda51fa76')
            column(name: 'wr_wrk_inst', value: 743904744)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: 1000002901)
            column(name: 'payee_account_number', value: 1000002901)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 250.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2cd2b2be-605d-4fd5-9855-ca1064b00366')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        // batch with LOCKED usage
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '127da857-5f78-4d2d-a8c3-55a08d5bd52b')
            column(name: 'name', value: 'FAS Batch 2')
            column(name: 'rro_account_number', value: 2000017001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 8972.00)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'created_datetime', value: '2019-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '0d90572c-696a-4e72-b9d1-e56d5d15045e')
            column(name: 'name', value: 'Test Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '7d90572c-696a-4e72-b9d1-e56d5d150451')
            column(name: 'df_scenario_uid', value: '0d90572c-696a-4e72-b9d1-e56d5d15045e')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9d94a627-761a-4676-bd10-cad43f10da39')
            column(name: 'df_usage_batch_uid', value: '127da857-5f78-4d2d-a8c3-55a08d5bd52b')
            column(name: 'df_scenario_uid', value: '0d90572c-696a-4e72-b9d1-e56d5d15045e')
            column(name: 'wr_wrk_inst', value: 122235134)
            column(name: 'work_title', value: 'CHICKEN BREAST ON GRILL WITH FLAMES')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'payee_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'net_amount', value: 6100.9872)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 2871.0528)
            column(name: 'gross_amount', value: 8972.04)
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9d94a627-761a-4676-bd10-cad43f10da39')
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
