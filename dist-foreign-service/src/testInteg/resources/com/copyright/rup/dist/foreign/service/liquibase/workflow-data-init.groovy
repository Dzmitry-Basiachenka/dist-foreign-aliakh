databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-04-10-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting rightsholders for workflow test')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '77b111d3-9eea-49af-b815-100b9716c1b3')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'name', value: 'CLA, The Copyright Licensing Agency Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '60080587-a225-439c-81af-f016cb33aeac')
            column(name: 'rh_account_number', value: 2000133267)
            column(name: 'name', value: '101 Communications, Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b0e6b1f6-89e9-4767-b143-db0f49f32769')
            column(name: 'rh_account_number', value: 2000073957)
            column(name: 'name', value: '1st Contact Publishing')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'f366285a-ce46-48b0-96ee-cd35d62fb243')
            column(name: 'rh_account_number', value: 7001508482)
            column(name: 'name', value: '2000 BC Publishing Ltd')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '624dcf73-a30f-4381-b6aa-c86d17198bd5')
            column(name: 'rh_account_number', value: 1000024950)
            column(name: 'name', value: '2D Publications')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '37338ed1-7083-45e2-a96b-5872a7de3a98')
            column(name: 'rh_account_number', value: 2000139286)
            column(name: 'name', value: '2HC [T]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '03ba3d02-c86a-40f8-8b14-270ca46a1f3a')
            column(name: 'name', value: 'AT_service-fee-true-up-report-12_BATCH')
            column(name: 'rro_account_number', value: 5000581901)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-03')
            column(name: 'fiscal_year', value: 2013)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '99f7d565-2df3-45e9-93ff-5dce63c75263')
            column(name: 'name', value: 'AT_service-fee-true-up-report-7_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'bde1e665-b10f-4015-936f-71fb42410e3b')
            column(name: 'df_usage_batch_uid', value: '03ba3d02-c86a-40f8-8b14-270ca46a1f3a')
            column(name: 'df_scenario_uid', value: '99f7d565-2df3-45e9-93ff-5dce63c75263')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2011-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 11')
            column(name: 'distribution_date', value: '2011-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2011-03-15 11:41:52.735531+03')
            column(name: 'lm_detail_id', value: '803dea5c-03d9-4404-8b3a-7f6b5ba9acce')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bde1e665-b10f-4015-936f-71fb42410e3b')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        rollback ""
    }
}
