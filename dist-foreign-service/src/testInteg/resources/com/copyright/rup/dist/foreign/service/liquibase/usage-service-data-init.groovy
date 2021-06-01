databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2017-11-10-00', author: 'Aliaksand Radkevich <aradkevich@copyright.com>') {
        comment('Inserting data for integration test for excluding usages from a scenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '034873b3-97fa-475a-9a2a-191e8ec988b3')
            column(name: 'name', value: 'Test Batch 1')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 40300.00)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'updated_datetime', value: '2017-02-14 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'name', value: 'Test Batch 2')
            column(name: 'rro_account_number', value: 2000017001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 10250.00)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'updated_datetime', value: '2017-02-14 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'name', value: 'Test Scenario for exclude')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9f96760c-0de9-4cee-abf2-65521277281b')
            column(name: 'df_usage_batch_uid', value: '034873b3-97fa-475a-9a2a-191e8ec988b3')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'wr_wrk_inst', value: '122235134')
            column(name: 'work_title', value: 'CHICKEN BREAST ON GRILL WITH FLAMES')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'payee_account_number', value: 1000000001)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'gross_amount', value: 26776.51)
            column(name: 'service_fee_amount', value: '8568.48')
            column(name: 'net_amount', value: '18208.03')
            column(name: 'service_fee', value: '0.32')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9f96760c-0de9-4cee-abf2-65521277281b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '9900.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e4a81fad-7b0e-4c67-8df2-112c8913e45e')
            column(name: 'df_usage_batch_uid', value: '034873b3-97fa-475a-9a2a-191e8ec988b3')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000000002)
            column(name: 'payee_account_number', value: 1000000002)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '25')
            column(name: 'gross_amount', value: 13523.49)
            column(name: 'service_fee_amount', value: '4327.52')
            column(name: 'net_amount', value: '9195.97')
            column(name: 'service_fee', value: '0.32')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e4a81fad-7b0e-4c67-8df2-112c8913e45e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '5000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2641e7fe-2a5a-4cdf-8879-48816d705169')
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'wr_wrk_inst', value: '471137967')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: 1000000003)
            column(name: 'payee_account_number', value: 1000000003)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: 6509.31)
            column(name: 'service_fee_amount', value: '2082.98')
            column(name: 'net_amount', value: '4426.33')
            column(name: 'service_fee', value: '0.32')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2641e7fe-2a5a-4cdf-8879-48816d705169')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '15000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '405491b1-49a9-4b70-9cdb-d082be6a802d')
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'wr_wrk_inst', value: '122235139')
            column(name: 'work_title', value: 'BOWL OF BERRIES WITH SUGAR COOKIES')
            column(name: 'rh_account_number', value: 1000000004)
            column(name: 'payee_account_number', value: 1000000004)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: 1301.86)
            column(name: 'service_fee_amount', value: '416.60')
            column(name: 'net_amount', value: '885.26')
            column(name: 'service_fee', value: '0.32')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '405491b1-49a9-4b70-9cdb-d082be6a802d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '3000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4ddfcb74-cb72-48f6-9ee4-8b4e05afce75')
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'wr_wrk_inst', value: '471137469')
            column(name: 'work_title', value: 'Solar Cells')
            column(name: 'rh_account_number', value: 1000000006)
            column(name: 'payee_account_number', value: 1000000006)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: 2438.82)
            column(name: 'service_fee_amount', value: '780.42')
            column(name: 'net_amount', value: '1658.40')
            column(name: 'service_fee', value: '0.32')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4ddfcb74-cb72-48f6-9ee4-8b4e05afce75')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '5620.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '77b111d3-9eea-49af-b815-100b9716c1b3')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'name', value: 'CLA, The Copyright Licensing Agency Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b2ea68f6-3c15-4ae3-a04a-acdd5a236f0c')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '074c0f1f-2457-49a9-ad72-2a85d07ce90e')
            column(name: 'rh_account_number', value: 1000000002)
            column(name: 'name', value: 'Royal Society of Victoria')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'd27264c2-eae4-4929-978d-77ba19ea417b')
            column(name: 'rh_account_number', value: 1000000003)
            column(name: 'name', value: 'South African Institute of Mining and Metallurgy')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b1c51d03-0c6e-4329-ba81-1060234c6c5e')
            column(name: 'rh_account_number', value: 1000000004)
            column(name: 'name', value: 'Computers for Design and Construction')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'bb888db8-1cd6-4c7e-a2b8-5486986eee95')
            column(name: 'rh_account_number', value: 1000000006)
            column(name: 'name', value: 'Januz Marketing Communications')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1da0461a-92f9-40cc-a3c1-9b972505b9c9')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'name', value: 'CFC/ Center Fran dexploitation du droit de Copie')
        }

        rollback ""
    }

    changeSet(id: '2017-11-10-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for UsageServiceIntegrationTest#testDeleteArchivedByBatchId')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'ceb97ce8-0efe-492d-a6c8-abe9dcbf325c')
            column(name: 'name', value: 'Archived scenario')
            column(name: 'status_ind', value: 'ARCHIVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0dc1cff9-fc33-47ef-866c-c97de0203f9c')
            column(name: 'name', value: 'Archived batch')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 15000.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '8b09419a-89d1-47ca-8c5a-5ee206e0b0e0')
            column(name: 'df_usage_batch_uid', value: '0dc1cff9-fc33-47ef-866c-c97de0203f9c')
            column(name: 'df_scenario_uid', value: 'ceb97ce8-0efe-492d-a6c8-abe9dcbf325c')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '30855870-09df-4341-bd88-2a92c5470d60')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8b09419a-89d1-47ca-8c5a-5ee206e0b0e0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Not Suitable For fund pool')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '3000')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "3b663c5a-7a4f-48b5-9b2f-89ce49aa23a3")
            column(name: "df_usage_uid", value: "8b09419a-89d1-47ca-8c5a-5ee206e0b0e0")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'Archived batch'")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "74cdde4e-686b-47b7-b0ba-e1f6d9592755")
            column(name: "df_usage_uid", value: "8b09419a-89d1-47ca-8c5a-5ee206e0b0e0")
            column(name: "action_type_ind", value: "PAID")
            column(name: "action_reason", value: "Usage has been paid according to information from the LM")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "06de58a5-eb32-4228-afdc-3cfac70922c3")
            column(name: "df_usage_uid", value: "8b09419a-89d1-47ca-8c5a-5ee206e0b0e0")
            column(name: "action_type_ind", value: "ARCHIVED")
            column(name: "action_reason", value: "Usage was sent to CRM")
        }

        rollback ""
    }
}
