databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2017-11-10-00', author: 'Aliaksand Radkevich <aradkevich@copyright.com>') {
        comment('Inserting data for integration test for excluding usages from a scenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '034873b3-97fa-475a-9a2a-191e8ec988b3')
            column(name: 'name', value: 'Test Batch 1')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '40300.00')
            column(name: 'updated_datetime', value: '2017-02-14 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'name', value: 'Test Batch 2')
            column(name: 'rro_account_number', value: '2000017001')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '10250.00')
            column(name: 'updated_datetime', value: '2017-02-14 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'name', value: 'Test Scenario for exclude')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
            column(name: 'updated_datetime', value: '2017-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9f96760c-0de9-4cee-abf2-65521277281b')
            column(name: 'df_usage_batch_uid', value: '034873b3-97fa-475a-9a2a-191e8ec988b3')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'detail_id', value: '100000001')
            column(name: 'wr_wrk_inst', value: '122235134')
            column(name: 'work_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'rh_account_number', value: '1000000001')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '9900.00')
            column(name: 'gross_amount', value: '10000.00')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'service_fee', value: '0.32')
            column(name: 'service_fee_amount', value: '3200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e4a81fad-7b0e-4c67-8df2-112c8913e45e')
            column(name: 'df_usage_batch_uid', value: '034873b3-97fa-475a-9a2a-191e8ec988b3')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'detail_id', value: '100000002')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000000002')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '25')
            column(name: 'reported_value', value: '5000.00')
            column(name: 'gross_amount', value: '3100.00')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'service_fee', value: '0.32')
            column(name: 'service_fee_amount', value: '992.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2641e7fe-2a5a-4cdf-8879-48816d705169')
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'detail_id', value: '100000003')
            column(name: 'wr_wrk_inst', value: '471137967')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: '1000000003')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '15000.00')
            column(name: 'gross_amount', value: '10300.00')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'service_fee', value: '0.32')
            column(name: 'service_fee_amount', value: '3296.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '405491b1-49a9-4b70-9cdb-d082be6a802d')
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'detail_id', value: '100000004')
            column(name: 'wr_wrk_inst', value: '122235139')
            column(name: 'work_title', value: 'BOWL OF BERRIES WITH SUGAR COOKIES')
            column(name: 'rh_account_number', value: '1000000004')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '3000.00')
            column(name: 'gross_amount', value: '1000.00')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'service_fee', value: '0.32')
            column(name: 'service_fee_amount', value: '320.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4ddfcb74-cb72-48f6-9ee4-8b4e05afce75')
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'detail_id', value: '100000005')
            column(name: 'wr_wrk_inst', value: '471137469')
            column(name: 'work_title', value: 'Solar Cells')
            column(name: 'rh_account_number', value: '1000000006')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '5620.00')
            column(name: 'gross_amount', value: '2450.55')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'service_fee', value: '0.32')
            column(name: 'service_fee_amount', value: '784.18')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '77b111d3-9eea-49af-b815-100b9716c1b3')
            column(name: 'rh_account_number', value: '2000017000')
            column(name: 'name', value: 'CLA, The Copyright Licensing Agency Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b2ea68f6-3c15-4ae3-a04a-acdd5a236f0c')
            column(name: 'rh_account_number', value: '1000000001')
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '074c0f1f-2457-49a9-ad72-2a85d07ce90e')
            column(name: 'rh_account_number', value: '1000000002')
            column(name: 'name', value: 'Royal Society of Victoria')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'd27264c2-eae4-4929-978d-77ba19ea417b')
            column(name: 'rh_account_number', value: '1000000003')
            column(name: 'name', value: 'South African Institute of Mining and Metallurgy')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b1c51d03-0c6e-4329-ba81-1060234c6c5e')
            column(name: 'rh_account_number', value: '1000000004')
            column(name: 'name', value: 'Computers for Design and Construction')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'bb888db8-1cd6-4c7e-a2b8-5486986eee95')
            column(name: 'rh_account_number', value: '1000000006')
            column(name: 'name', value: 'Januz Marketing Communications')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1da0461a-92f9-40cc-a3c1-9b972505b9c9')
            column(name: 'rh_account_number', value: '2000017001')
            column(name: 'name', value: 'CFC/ Center Fran dexploitation du droit de Copie')
        }

        rollback ""
    }
}
