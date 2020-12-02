databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-05-21-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserts data for csv reports integration tests')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: '2000017010')
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e855bf85-236c-42e7-9b12-8d68dd747bbe')
            column(name: 'name', value: 'Batch with NTS')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e2b3c369-3084-41ad-92b5-62197660d645')
            column(name: 'df_usage_batch_uid', value: 'e855bf85-236c-42e7-9b12-8d68dd747bbe')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '420.00')
            column(name: 'service_fee_amount', value: '80.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'comment', value: 'DIN EN 779:2012')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e2b3c369-3084-41ad-92b5-62197660d645')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '500.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '034873b3-97fa-475a-9a2a-191e8ec988b3')
            column(name: 'name', value: 'Test Batch 1')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '40300.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'name', value: 'Test Batch 3')
            column(name: 'rro_account_number', value: '2000017001')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '10250.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'name', value: 'Test Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2017-02-14 11:45:52.735531+03')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2017-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'b1b7c100-42f8-49f7-ab9f-a89e92a011c1')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2017-02-14 11:45:52.735531+03')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2017-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9f96760c-0de9-4cee-abf2-65521277281b')
            column(name: 'df_usage_batch_uid', value: '034873b3-97fa-475a-9a2a-191e8ec988b3')
            column(name: 'wr_wrk_inst', value: '122235134')
            column(name: 'work_title', value: 'CHICKEN BREAST ON GRILL WITH FLAMES')
            column(name: 'system_title', value: 'Chicken Breast On Grill With Flames')
            column(name: 'rh_account_number', value: '1000000001')
            column(name: 'payee_account_number', value: '1000000001')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'gross_amount', value: '26776.51')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'service_fee', value: '0.0')
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
            column(name: 'reported_value', value: '9900.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e4a81fad-7b0e-4c67-8df2-112c8913e45e')
            column(name: 'df_usage_batch_uid', value: '034873b3-97fa-475a-9a2a-191e8ec988b3')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000000002')
            column(name: 'payee_account_number', value: '1000000002')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '0003324112314587XX')
            column(name: 'number_of_copies', value: '25')
            column(name: 'gross_amount', value: '13523.49')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'service_fee', value: '0.0')
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
            column(name: 'reported_value', value: '5000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2641e7fe-2a5a-4cdf-8879-48816d705169')
            column(name: 'df_usage_batch_uid', value: '02a09322-5f0f-4cae-888c-73127050dc98')
            column(name: 'df_scenario_uid', value: '12ec845f-0e76-4d1c-85cd-bb3fb7ca260e')
            column(name: 'wr_wrk_inst', value: '471137967')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: '1000000003')
            column(name: 'payee_account_number', value: '1000000003')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '6509.31')
            column(name: 'service_fee_amount', value: '2082.98')
            column(name: 'net_amount', value: '4426.33')
            column(name: 'service_fee', value: '0.32')
            column(name: 'comment', value: '471137967')
            column(name: 'standard_number_type', value: 'VALISSN')
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
            column(name: 'system_title', value: 'BOWL OF BERRIES WITH SUGAR COOKIES')
            column(name: 'rh_account_number', value: '1000000004')
            column(name: 'payee_account_number', value: '1000000004')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '1301.86')
            column(name: 'service_fee_amount', value: '416.60')
            column(name: 'net_amount', value: '885.26')
            column(name: 'service_fee', value: '0.32')
            column(name: 'comment', value: '122235139')
            column(name: 'standard_number_type', value: 'VALISBN10')
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
            column(name: 'system_title', value: 'Solar Cells')
            column(name: 'rh_account_number', value: '1000000006')
            column(name: 'payee_account_number', value: '1000000006')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '052365874521235XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '2438.82')
            column(name: 'service_fee_amount', value: '780.42')
            column(name: 'net_amount', value: '1658.40')
            column(name: 'service_fee', value: '0.32')
            column(name: 'comment', value: '471137469')
            column(name: 'standard_number_type', value: 'VALISBN13')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'c44d7e52-8bf8-4e8e-85b4-542b6d6e1b86')
            column(name: 'df_usage_uid', value: '9f96760c-0de9-4cee-abf2-65521277281b')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test Batch 1\' Batch')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2017-02-14 11:25:52.735531+03')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2017-02-14 11:25:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '8a5afe55-5a5f-4893-aee1-96ac1361c033')
            column(name: 'df_usage_uid', value: 'e4a81fad-7b0e-4c67-8df2-112c8913e45e')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test Batch 1\' Batch')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2017-02-14 11:25:52.735531+03')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2017-02-14 11:25:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'cc7ba64a-b947-49ee-9e11-e6ba94ff7071')
            column(name: 'df_usage_uid', value: '2641e7fe-2a5a-4cdf-8879-48816d705169')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test Batch 2\' Batch')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2017-02-14 11:35:52.735531+03')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2017-02-14 11:35:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'd34b1830-8aa3-4e48-aeea-bca4682e090f')
            column(name: 'df_usage_uid', value: '405491b1-49a9-4b70-9cdb-d082be6a802d')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test Batch 2\' Batch')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2017-02-14 11:35:52.735531+03')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2017-02-14 11:35:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'd8134fef-9d0e-4126-9d7b-9340917bacc5')
            column(name: 'df_usage_uid', value: '4ddfcb74-cb72-48f6-9ee4-8b4e05afce75')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test Batch 2\' Batch')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2017-02-14 11:35:52.735531+03')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'updated_datetime', value: '2017-02-14 11:35:52.735531+03')
        }

        rollback ""
    }

    changeSet(id: '2019-03-27-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testWriteUndistributedLiabilitiesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '53089c29-7df1-4d41-93d3-4ad222408818')
            column(name: 'rh_account_number', value: '7000581909')
            column(name: 'name', value: 'CLASS, The Copyright Licensing and Administration Society of Singapore Ltd')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '066d2add-cff0-4007-83df-4b77c3e4b00e')
            column(name: 'rh_account_number', value: '7001226021')
            column(name: 'name', value: 'Zynga')
        }

        // Record for this batch should't be included into report.
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a1cdd210-b256-44f6-bb18-96fbe6bf40c9')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 10')
            column(name: 'rro_account_number', value: '7001226021')
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2010-01-01')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '500.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9f15966c-b368-4449-bcb1-ff4945e2e033')
            column(name: 'df_usage_batch_uid', value: 'a1cdd210-b256-44f6-bb18-96fbe6bf40c9')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '7001226021')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '249.9999999988')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9f15966c-b368-4449-bcb1-ff4945e2e033')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        // Record in Undistributed Liabilities Reconciliation Report that includes amounts for batch with usages that were sent to lm
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b654')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 1')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-01')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f5e')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report scenario for batch 1')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82824546-c34d-4102-9939-62c50f356f5e')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b654')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f5e')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.0000000010')
            column(name: 'net_amount', value: '34.0000000007')
            column(name: 'service_fee_amount', value: '16.0000000003')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'standard_number_type', value: 'VALISSN')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82824546-c34d-4102-9939-62c50f356f5e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '035c57a2-375f-421e-b92f-0b62a3d7fdda')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b654')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f5e')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.0000000010')
            column(name: 'net_amount', value: '34.0000000007')
            column(name: 'service_fee_amount', value: '16.0000000003')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'standard_number_type', value: 'VALISSN')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '035c57a2-375f-421e-b92f-0b62a3d7fdda')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        /* Record in report that includes amounts for following batch:
               batch with fas usages that were sent to lm
               batch with fas2 usages that were sent to lm
               batch with fas2 usages that were sent to lm and return to cla
               two batches with fas2 eligible usages */
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'acae006c-a4fe-45f0-a0cc-098e12db00c5')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 2')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2010-01-01')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '500.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '9fb85b60-8881-468b-8b53-a78bb7ff81d8')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report scenario for batch 2')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'fcecfba8-ca12-4543-83df-efdca1d0d504')
            column(name: 'df_usage_batch_uid', value: 'acae006c-a4fe-45f0-a0cc-098e12db00c5')
            column(name: 'df_scenario_uid', value: '9fb85b60-8881-468b-8b53-a78bb7ff81d8')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '249.9999999988')
            column(name: 'net_amount', value: '169.9999999992')
            column(name: 'service_fee_amount', value: '79.9999999996')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'standard_number_type', value: 'VALISSN')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'fcecfba8-ca12-4543-83df-efdca1d0d504')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '580487a7-aa96-47c8-a78e-1ac077fd61ae')
            column(name: 'df_usage_batch_uid', value: 'acae006c-a4fe-45f0-a0cc-098e12db00c5')
            column(name: 'df_scenario_uid', value: '9fb85b60-8881-468b-8b53-a78bb7ff81d8')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '249.9999999988')
            column(name: 'net_amount', value: '169.9999999992')
            column(name: 'service_fee_amount', value: '79.9999999996')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'standard_number_type', value: 'VALISSN')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '580487a7-aa96-47c8-a78e-1ac077fd61ae')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '8869cca8-b5a6-4122-ba56-fbc7d0585f65')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 3')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2010-01-01')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '380.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '7887b131-e38d-452d-806b-f9615a2401ce')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report scenario for batch 3')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'd04b4d45-b143-404c-bf45-0984542cc25a')
            column(name: 'df_usage_batch_uid', value: '8869cca8-b5a6-4122-ba56-fbc7d0585f65')
            column(name: 'df_scenario_uid', value: '7887b131-e38d-452d-806b-f9615a2401ce')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '2000017000')
            column(name: 'payee_account_number', value: '2000017000')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '190.0000000007')
            column(name: 'net_amount', value: '171.0000000006')
            column(name: 'service_fee_amount', value: '19.0000000001')
            column(name: 'service_fee', value: '0.10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd04b4d45-b143-404c-bf45-0984542cc25a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'af8e8b6c-29e2-4b7a-aaa1-746c48ef2253')
            column(name: 'df_usage_batch_uid', value: '8869cca8-b5a6-4122-ba56-fbc7d0585f65')
            column(name: 'df_scenario_uid', value: '7887b131-e38d-452d-806b-f9615a2401ce')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '2000017000')
            column(name: 'payee_account_number', value: '2000017000')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '190.0000000007')
            column(name: 'net_amount', value: '171.0000000006')
            column(name: 'service_fee_amount', value: '19.0000000001')
            column(name: 'service_fee', value: '0.10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'af8e8b6c-29e2-4b7a-aaa1-746c48ef2253')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '11d8d04f-4c20-4bd1-8b81-f80c2742e89e')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 4')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2010-01-01')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea82')
            column(name: 'df_usage_batch_uid', value: '11d8d04f-4c20-4bd1-8b81-f80c2742e89e')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '249.9999999988')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea82')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a63d87498-3c12-429a-87ef-241951638b8e')
            column(name: 'df_usage_batch_uid', value: '11d8d04f-4c20-4bd1-8b81-f80c2742e89e')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '249.9999999988')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a63d87498-3c12-429a-87ef-241951638b8e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e116e5e0-9080-4abf-9e67-86959f2cae52')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 5')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2010-01-01')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '1000.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd376bdd6-d87f-4d39-bf8f-05c41404d19b')
            column(name: 'df_usage_batch_uid', value: 'e116e5e0-9080-4abf-9e67-86959f2cae52')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '500.0000000007')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd376bdd6-d87f-4d39-bf8f-05c41404d19b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '038c46ae-7931-48c6-8cb9-ff4fab9faa95')
            column(name: 'df_usage_batch_uid', value: 'e116e5e0-9080-4abf-9e67-86959f2cae52')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '500.0000000007')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '038c46ae-7931-48c6-8cb9-ff4fab9faa95')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3b7aed62-eb9f-4244-9248-2f9ba329e3be')
            column(name: 'df_usage_batch_uid', value: 'e116e5e0-9080-4abf-9e67-86959f2cae52')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622Xxxx')
            column(name: 'gross_amount', value: '155.0000000007')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3b7aed62-eb9f-4244-9248-2f9ba329e3be')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        // Undistributed Liabilities reconciliation report TO_BE_DISTRIBUTED status
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '49060c9b-9cc2-4b93-b701-fffc82eb28b0')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: '155.00')
            column(name: 'updated_datetime', value: '2019-03-26 16:35:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b92f4f46-38c1-4f6c-9f69-2c1dcd73d579')
            column(name: 'df_usage_batch_uid', value: 'e116e5e0-9080-4abf-9e67-86959f2cae52')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112317622Xxxx')
            column(name: 'gross_amount', value: '155.0000000007')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b92f4f46-38c1-4f6c-9f69-2c1dcd73d579')
            column(name: 'df_fund_pool_uid', value: '49060c9b-9cc2-4b93-b701-fffc82eb28b0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        // Record in report that includes amounts for batch with fas and nts usages in eligible status
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '502cf0b4-3e28-4712-837a-3728ef57b100')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report Batch 6')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-02')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '60.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b05046a6-9baf-4be3-baa6-5c08c8527826')
            column(name: 'df_usage_batch_uid', value: '502cf0b4-3e28-4712-837a-3728ef57b100')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '20')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b05046a6-9baf-4be3-baa6-5c08c8527826')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5329943d-1214-405d-8e29-59a9281d3275')
            column(name: 'df_usage_batch_uid', value: '502cf0b4-3e28-4712-837a-3728ef57b100')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '20')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5329943d-1214-405d-8e29-59a9281d3275')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4dd8cdf8-ca10-422e-bdd5-3220105e6379')
            column(name: 'df_usage_batch_uid', value: '502cf0b4-3e28-4712-837a-3728ef57b100')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'gross_amount', value: '20')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4dd8cdf8-ca10-422e-bdd5-3220105e6379')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '30.86')
        }

        // Record in report that includes amounts for batch with usages in different statuses
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'name', value: 'Batch with usages in different statuses')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-03')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '800.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9e8de601-d771-42eb-992f-75ecd083b8d0')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '200')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9e8de601-d771-42eb-992f-75ecd083b8d0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'standard_number', value: '2192-3566')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'gross_amount', value: '200')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '721ca627-09bc-4204-99f4-6acae415fa5d')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '200')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '721ca627-09bc-4204-99f4-6acae415fa5d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd9ca07b5-8282-4a81-9b9d-e4480f529d34')
            column(name: 'df_usage_batch_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'wr_wrk_inst', value: '103658926')
            column(name: 'work_title', value: 'Nitrates')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'gross_amount', value: '200')

        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd9ca07b5-8282-4a81-9b9d-e4480f529d34')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '30.86')
        }

        // Record in report that includes amounts for batch with usages that were sent to lm and batch with paid usages
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '8bf56f3a-b868-4795-8691-d7f56f3f8540')
            column(name: 'name', value: 'Batch with sent to lm usage')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-04')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '145ea869-16aa-4953-a780-1d58abda692f')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report scenario for batch 1')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '14ed2c94-9316-49d9-ad74-02e5627eba8d')
            column(name: 'df_usage_batch_uid', value: '8bf56f3a-b868-4795-8691-d7f56f3f8540')
            column(name: 'df_scenario_uid', value: '145ea869-16aa-4953-a780-1d58abda692f')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '34.00')
            column(name: 'service_fee_amount', value: '16.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '14ed2c94-9316-49d9-ad74-02e5627eba8d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e7cfcf79-3d8c-478d-a7a4-2242867661f2')
            column(name: 'name', value: 'Batch with paid usage')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-04')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '74be71d1-b1dd-45ac-95b6-78677b37beec')
            column(name: 'name', value: 'Undistributed Liabilities Reconciliation Report scenario for batch 1')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '74be71d1-b1dd-45ac-95b6-78677b37beec')
            column(name: 'df_usage_batch_uid', value: 'e7cfcf79-3d8c-478d-a7a4-2242867661f2')
            column(name: 'df_scenario_uid', value: '74be71d1-b1dd-45ac-95b6-78677b37beec')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '34.00')
            column(name: 'service_fee_amount', value: '16.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2017-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '74be71d1-b1dd-45ac-95b6-78677b37beec')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        // Record in reports that includes amounts for batch with two sent to lm usages and two not included into scenario usages
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c38b1946-356b-4934-a765-eb8e824d1f01')
            column(name: 'name', value: 'Batch with two sent to lm usages and two not included into scenario usages')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-04-21')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '400.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '454b5ed8-bff1-4410-b455-235ec5271e6c')
            column(name: 'df_usage_batch_uid', value: 'c38b1946-356b-4934-a765-eb8e824d1f01')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '454b5ed8-bff1-4410-b455-235ec5271e6c')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '910dae16-f495-48aa-932d-0909d8103b00')
            column(name: 'df_usage_batch_uid', value: 'c38b1946-356b-4934-a765-eb8e824d1f01')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '910dae16-f495-48aa-932d-0909d8103b00')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '66460664-ccaf-4a11-935e-0d7b9da4e41b')
            column(name: 'name', value: 'Scenario with two sent to lm usages')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '0ac629ea-9e8b-45bd-bbd8-857bd7d34424')
            column(name: 'df_usage_batch_uid', value: 'c38b1946-356b-4934-a765-eb8e824d1f01')
            column(name: 'df_scenario_uid', value: '66460664-ccaf-4a11-935e-0d7b9da4e41b')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '0ac629ea-9e8b-45bd-bbd8-857bd7d34424')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '146ce0f2-f71f-4a53-b0c4-9925ec08da5f')
            column(name: 'df_usage_batch_uid', value: 'c38b1946-356b-4934-a765-eb8e824d1f01')
            column(name: 'df_scenario_uid', value: '66460664-ccaf-4a11-935e-0d7b9da4e41b')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '146ce0f2-f71f-4a53-b0c4-9925ec08da5f')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        // Record in report that includes amounts for batch with rro which is not included into table with estimated service fee
        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'adf97d33-4b76-4c2c-ad6e-91c4715d392f')
            column(name: 'rh_account_number', value: '5000581900')
            column(name: 'name', value: 'The Copyright Company')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f6a46748-5c19-4b10-953b-ae61d8e06822')
            column(name: 'name', value: 'Batch with rro which is not included into table with estimated service fee')
            column(name: 'rro_account_number', value: '5000581900')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-03')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5f95fedc-1c63-4cda-b874-2a39e0ac39fe')
            column(name: 'df_usage_batch_uid', value: 'f6a46748-5c19-4b10-953b-ae61d8e06822')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5f95fedc-1c63-4cda-b874-2a39e0ac39fe')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '6c170115-cd1f-4a73-9a56-6e9316579bc8')
            column(name: 'name', value: 'In progress scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'd1f1def6-4062-47f6-8675-59328fafb157')
            column(name: 'df_usage_batch_uid', value: 'f6a46748-5c19-4b10-953b-ae61d8e06822')
            column(name: 'df_scenario_uid', value: '6c170115-cd1f-4a73-9a56-6e9316579bc8')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100')
            column(name: 'net_amount', value: '64.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd1f1def6-4062-47f6-8675-59328fafb157')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        // Record for this batch should't be included into report. Batch contains paid and archived usages
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '24600e42-3c2c-4cf6-b376-d2d477128e04')
            column(name: 'name', value: 'Batch with paid and archived usages')
            column(name: 'rro_account_number', value: '5000581900')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2010-01-03')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'd3436a38-98ff-4061-b245-68ffeb1f216c')
            column(name: 'name', value: 'Scenario with paid and archived usages')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'e8790b32-9d4e-42e6-93f6-eeafc46131fc')
            column(name: 'df_usage_batch_uid', value: '24600e42-3c2c-4cf6-b376-d2d477128e04')
            column(name: 'df_scenario_uid', value: 'd3436a38-98ff-4061-b245-68ffeb1f216c')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2017-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e8790b32-9d4e-42e6-93f6-eeafc46131fc')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '9fe2be4a-d4fc-4d73-aa55-56da80feeff2')
            column(name: 'df_usage_batch_uid', value: '24600e42-3c2c-4cf6-b376-d2d477128e04')
            column(name: 'df_scenario_uid', value: 'd3436a38-98ff-4061-b245-68ffeb1f216c')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2017-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9fe2be4a-d4fc-4d73-aa55-56da80feeff2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        rollback ""
    }

    changeSet(id: '2019-03-27-01', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testWriteFasBatchSummaryCsvReport')

        // Batch summary report
        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '4502b24f-3146-4682-a654-7f1f6647cb7f')
            column(name: 'rh_account_number', value: '2000017011')
            column(name: 'name', value: 'DALRO, Dramatic, Artistic & Literary Rights Organisation')
        }

        // Batch with two details associated with in progress scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b651')
            column(name: 'name', value: 'AT_batch-summary-report-1_BATCH')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two nts and two eligible details
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
            column(name: 'name', value: 'AT_batch-summary-report-2_BATCH')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '250.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible and two details associated with in progress scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'name', value: 'AT_batch-summary-report-3_BATCH')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '550.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible, two nts, two details that were sent to lm and details in paid and archived status
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'name', value: 'AT_batch-summary-report-4_BATCH')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '450.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible, two nts and two details associated with scenario in status submit
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'name', value: 'AT_batch-summary-report-5_BATCH')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '450.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible, two nts and two details associated with scenario in status approved
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'name', value: 'AT_batch-summary-report-6_BATCH')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '450.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }
        // Batch with two non eligible, two eligible, two nts, two details associated with scenario and two return to cla details that were sent to lm
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'name', value: 'AT_batch-summary-report-7_BATCH')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '650.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible, two nts, two details associated with scenario and two details that were sent to lm
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'name', value: 'AT_batch-summary-report-8_BATCH')
            column(name: 'rro_account_number', value: '2000017011')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '550.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible, two nts, two details associated with scenario and two details that were sent to lm
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'name', value: 'AT_batch-summary-report-9_BATCH')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-05-08')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '550.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two details that were sent to lm. Shouldn't be included into report
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b610')
            column(name: 'name', value: 'AT_batch-summary-report-10_BATCH')
            column(name: 'rro_account_number', value: '2000017011')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-05-08')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '100.00')
        }

        // Batch with nts details. Shouldn't be included into report
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b611')
            column(name: 'name', value: 'AT_batch-summary-report-11_BATCH')
            column(name: 'rro_account_number', value: '2000017011')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-05-08')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with two non eligible, two eligible, two nts, four details associated with in progress scenario where two of them return to cla details
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'name', value: 'AT_batch-summary-report-12_BATCH')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2018-05-08')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '600.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        // Batch with NTS product family to be excluded from report
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e016d9c2-6460-51bf-937c-9598cf00b652')
            column(name: 'name', value: 'NTS batch to be excluded from FAS/FAS2 Batch Summary Report')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2018-01-04')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'nts_fields', value: '{"markets": ["Bus,Univ,Doc Del"], "stm_amount": 10, "non_stm_amount": 20, "stm_minimum_amount": 30, "non_stm_minimum_amount": 40, "fund_pool_period_to": 2018, "fund_pool_period_from": 2018}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe1')
            column(name: 'name', value: 'AT_batch-summary-report-1_SCENARIO')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe2')
            column(name: 'name', value: 'AT_batch-summary-report-2_SCENARIO')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe3')
            column(name: 'name', value: 'AT_batch-summary-report-3_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe4')
            column(name: 'name', value: 'AT_batch-summary-report-4_SCENARIO')
            column(name: 'status_ind', value: 'SUBMITTED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe5')
            column(name: 'name', value: 'AT_batch-summary-report-5_SCENARIO')
            column(name: 'status_ind', value: 'APPROVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe6')
            column(name: 'name', value: 'AT_batch-summary-report-6_SCENARIO')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe7')
            column(name: 'name', value: 'AT_batch-summary-report-7_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe8')
            column(name: 'name', value: 'AT_batch-summary-report-8_SCENARIO')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe9')
            column(name: 'name', value: 'AT_batch-summary-report-9_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f10')
            column(name: 'name', value: 'AT_batch-summary-report-10_SCENARIO')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f11')
            column(name: 'name', value: 'AT_batch-summary-report-11_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f12')
            column(name: 'name', value: 'AT_batch-summary-report-12_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f13')
            column(name: 'name', value: 'AT_batch-summary-report-13_SCENARIO')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '92824546-d34d-7102-9939-92c50f356fe2')
            column(name: 'name', value: 'AT_batch-summary-report-14_SCENARIO')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea01')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b651')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe1')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea01')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea02')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b651')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe1')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea02')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea03')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea03')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea04')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea04')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ,Bus,Doc,S')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea05')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea05')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ,Bus,Doc,S')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea06')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea06')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ,Bus,Doc,S')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea07')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea07')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Gov')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea08')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b652')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea08')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Gov')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea09')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea09')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea10')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea10')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea11')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea11')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea12')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea12')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea13')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe2')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '150.00')
            column(name: 'net_amount', value: '102.00')
            column(name: 'service_fee_amount', value: '48.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea13')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea14')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b653')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe2')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '150.00')
            column(name: 'net_amount', value: '102.00')
            column(name: 'service_fee_amount', value: '48.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea14')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea15')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea15')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea16')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea16')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea17')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea17')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea18')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea18')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea19')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea19')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea20')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea20')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea23')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe3')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '45.00')
            column(name: 'service_fee_amount', value: '5.00')
            column(name: 'service_fee', value: '0.10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea23')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea24')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe3')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '45.00')
            column(name: 'service_fee_amount', value: '5.00')
            column(name: 'service_fee', value: '0.10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea24')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea25')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe3')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2017-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea25')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea26')
            column(name: 'df_usage_batch_uid', value: 'f1a40b56-54f1-4a46-90fa-77946c2f7805')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe3')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2017-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2017-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea26')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea27')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea27')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea28')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea28')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea29')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea29')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea30')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea30')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea31')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea31')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea32')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea32')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea33')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe4')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea33')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea34')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b655')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe4')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea34')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea35')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea35')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea36')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea36')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea37')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea37')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea38')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea38')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea39')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea39')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea40')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea40')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea41')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe5')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea41')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea42')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b656')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe5')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea42')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea43')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea43')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea44')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea44')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea45')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea45')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea46')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea46')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea47')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea47')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea50')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea50')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea51')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe6')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '150.00')
            column(name: 'net_amount', value: '102.00')
            column(name: 'service_fee_amount', value: '48.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea51')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea52')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe6')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '150.00')
            column(name: 'net_amount', value: '102.00')
            column(name: 'service_fee_amount', value: '48.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea52')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea53')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe7')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '2000017000')
            column(name: 'payee_account_number', value: '2000017000')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea53')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea54')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b657')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe7')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '2000017000')
            column(name: 'payee_account_number', value: '2000017000')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea54')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea55')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea55')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea56')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea56')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea57')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea57')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea58')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea58')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea59')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea59')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea60')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea60')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea61')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe8')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea61')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea62')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe8')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea62')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea63')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe9')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea63')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea64')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b658')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356fe9')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea64')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea65')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea65')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea66')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea66')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea67')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea67')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea68')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea68')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea69')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea69')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea70')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea70')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea71')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f10')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea71')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea72')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f10')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea72')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea73')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f11')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea73')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea74')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b659')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f11')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea74')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea75')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b610')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f12')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea75')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea76')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b610')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f12')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea76')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea77')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b611')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea77')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea78')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b611')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea78')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea79')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea79')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea80')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea80')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea90')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea90')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea91')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '8902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea91')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea92')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea92')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea93')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea93')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea94')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f13')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea94')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea95')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f13')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '42.00')
            column(name: 'service_fee_amount', value: '8.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea95')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea96')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f13')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '2000017000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '45.00')
            column(name: 'service_fee_amount', value: '5.00')
            column(name: 'service_fee', value: '0.10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea96')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea97')
            column(name: 'df_usage_batch_uid', value: 'd016d9c2-5460-41bf-837c-8598cf00b612')
            column(name: 'df_scenario_uid', value: '82824546-c34d-4102-9939-62c50f356f13')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '2000017000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '45.00')
            column(name: 'service_fee_amount', value: '5.00')
            column(name: 'service_fee', value: '0.10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '82db1184-640c-4745-8e4e-2e96e9d0ea97')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '92db1184-740c-5745-9e4e-3e96e9d0ea02')
            column(name: 'df_usage_batch_uid', value: 'e016d9c2-6460-51bf-937c-9598cf00b652')
            column(name: 'df_scenario_uid', value: '92824546-d34d-7102-9939-92c50f356fe2')
            column(name: 'wr_wrk_inst', value: '123321123')
            column(name: 'work_title', value: 'The rites of passage')
            column(name: 'system_title', value: 'The rites of passage')
            column(name: 'rh_account_number', value: '7000581909')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '12345XX-177361')
            column(name: 'gross_amount', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '92db1184-740c-5745-9e4e-3e96e9d0ea02')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2018')
            column(name: 'market_period_to', value: '2018')
            column(name: 'reported_value', value: '51.50')
        }

        rollback ""
    }

    changeSet(id: '2019-03-27-02', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testFasWriteServiceFeeTrueUpCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '4cc22676-bb0b-4f06-be23-e245d474b01d')
            column(name: 'rh_account_number', value: '5000581901')
            column(name: 'name', value: 'Unknown RRO')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '40a02427-1204-47ae-849c-1299a337cc47')
            column(name: 'name', value: 'AT_service-fee-true-up-report-1_BATCH')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-01')
            column(name: 'fiscal_year', value: '2013')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'name', value: 'AT_service-fee-true-up-report-1_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'df_usage_batch_uid', value: '40a02427-1204-47ae-849c-1299a337cc47')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.0000000010')
            column(name: 'net_amount', value: '34.0000000007')
            column(name: 'service_fee_amount', value: '16.0000000003')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '7f1cb24d-cf00-4354-9ef9-0457d36a556e')
            column(name: 'df_usage_batch_uid', value: '40a02427-1204-47ae-849c-1299a337cc47')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.0000000010')
            column(name: 'net_amount', value: '34.0000000007')
            column(name: 'service_fee_amount', value: '16.0000000003')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7f1cb24d-cf00-4354-9ef9-0457d36a556e')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a2fd8112-4623-4abf-aa6b-3fe820c49eb2')
            column(name: 'name', value: 'AT_service-fee-true-up-report-2_BATCH')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2013-01-01')
            column(name: 'fiscal_year', value: '2013')
            column(name: 'gross_amount', value: '500.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'name', value: 'AT_service-fee-true-up-report-2_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '27845f34-ab87-4147-aea0-a191ded7412a')
            column(name: 'df_usage_batch_uid', value: 'a2fd8112-4623-4abf-aa6b-3fe820c49eb2')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '249.9999999988')
            column(name: 'net_amount', value: '169.9999999992')
            column(name: 'service_fee_amount', value: '79.9999999996')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '27845f34-ab87-4147-aea0-a191ded7412a')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '653c2173-5385-4a24-b42c-3428b256d74a')
            column(name: 'df_usage_batch_uid', value: 'a2fd8112-4623-4abf-aa6b-3fe820c49eb2')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '249.9999999988')
            column(name: 'net_amount', value: '169.9999999992')
            column(name: 'service_fee_amount', value: '79.9999999996')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '653c2173-5385-4a24-b42c-3428b256d74a')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '7675a48a-cba6-4a2e-a4a4-fee2237a0128')
            column(name: 'name', value: 'AT_service-fee-true-up-report-3_BATCH')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2013-01-01')
            column(name: 'fiscal_year', value: '2013')
            column(name: 'gross_amount', value: '380.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
            column(name: 'name', value: 'AT_service-fee-true-up-report-3_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '280b9e39-f934-4474-9d11-847e91e36609')
            column(name: 'df_usage_batch_uid', value: '7675a48a-cba6-4a2e-a4a4-fee2237a0128')
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '2000017000')
            column(name: 'payee_account_number', value: '2000017000')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '190.0000000007')
            column(name: 'net_amount', value: '171.0000000006')
            column(name: 'service_fee_amount', value: '19.0000000001')
            column(name: 'service_fee', value: '0.10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '280b9e39-f934-4474-9d11-847e91e36609')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '4179d85a-a09d-4521-8b5c-dc9026bd8249')
            column(name: 'df_usage_batch_uid', value: '7675a48a-cba6-4a2e-a4a4-fee2237a0128')
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '2000017000')
            column(name: 'payee_account_number', value: '2000017000')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '190.0000000007')
            column(name: 'net_amount', value: '171.0000000006')
            column(name: 'service_fee_amount', value: '19.0000000001')
            column(name: 'service_fee', value: '0.10000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4179d85a-a09d-4521-8b5c-dc9026bd8249')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '76cf371b-142d-4380-9e62-c09888d7a034')
            column(name: 'name', value: 'AT_service-fee-true-up-report-4_BATCH')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2013-01-01')
            column(name: 'fiscal_year', value: '2013')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b58e8f58-07fc-4597-9c96-551383cfa1b1')
            column(name: 'df_usage_batch_uid', value: '76cf371b-142d-4380-9e62-c09888d7a034')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '249.9999999988')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b58e8f58-07fc-4597-9c96-551383cfa1b1')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ddbed016-f04d-4556-ab9f-b8080fb90089')
            column(name: 'df_usage_batch_uid', value: '76cf371b-142d-4380-9e62-c09888d7a034')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '249.9999999988')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ddbed016-f04d-4556-ab9f-b8080fb90089')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '2b1b5fe1-fb0a-4243-8326-2cc9dcd4a73a')
            column(name: 'name', value: 'AT_service-fee-true-up-report-5_BATCH')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'payment_date', value: '2013-01-01')
            column(name: 'fiscal_year', value: '2013')
            column(name: 'gross_amount', value: '1000.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c38f4992-9e58-4a97-8a63-ce15c9ad4cd1')
            column(name: 'df_usage_batch_uid', value: '2b1b5fe1-fb0a-4243-8326-2cc9dcd4a73a')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '500.0000000007')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c38f4992-9e58-4a97-8a63-ce15c9ad4cd1')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '30121fa1-4550-4fe8-9776-2d17f16a54a1')
            column(name: 'df_usage_batch_uid', value: '2b1b5fe1-fb0a-4243-8326-2cc9dcd4a73a')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '500.0000000007')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '30121fa1-4550-4fe8-9776-2d17f16a54a1')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6b75221e-432e-4349-ba05-796d1fd5900e')
            column(name: 'name', value: 'AT_service-fee-true-up-report-6_BATCH')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-02')
            column(name: 'fiscal_year', value: '2013')
            column(name: 'gross_amount', value: '60.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c66b15fa-ce23-4d15-89d0-fbae38e360b6')
            column(name: 'df_usage_batch_uid', value: '6b75221e-432e-4349-ba05-796d1fd5900e')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '20')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c66b15fa-ce23-4d15-89d0-fbae38e360b6')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3def87c0-6759-4a16-bcd9-945b14c00219c')
            column(name: 'df_usage_batch_uid', value: '6b75221e-432e-4349-ba05-796d1fd5900e')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '20')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3def87c0-6759-4a16-bcd9-945b14c00219c')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '022184ed-2adb-4237-8a5f-34eac350bcbc')
            column(name: 'df_usage_batch_uid', value: '6b75221e-432e-4349-ba05-796d1fd5900e')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'gross_amount', value: '20')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '022184ed-2adb-4237-8a5f-34eac350bcbc')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '260d71f8-ccfa-46fa-9319-55a00726a266')
            column(name: 'name', value: 'AT_service-fee-true-up-report-7_BATCH')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-03')
            column(name: 'fiscal_year', value: '2013')
            column(name: 'gross_amount', value: '800.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f6849fd0-c094-4a72-979e-00cf462fb3eb')
            column(name: 'df_usage_batch_uid', value: '260d71f8-ccfa-46fa-9319-55a00726a266')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '200')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f6849fd0-c094-4a72-979e-00cf462fb3eb')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'eed8d6f8-7ac6-4ae7-9bdc-ba33a58a5bad')
            column(name: 'df_usage_batch_uid', value: '260d71f8-ccfa-46fa-9319-55a00726a266')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'standard_number', value: '2192-3566')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'gross_amount', value: '200')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'eed8d6f8-7ac6-4ae7-9bdc-ba33a58a5bad')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '15b144ba-58d6-4adb-9dc1-602eb09052ce')
            column(name: 'df_usage_batch_uid', value: '260d71f8-ccfa-46fa-9319-55a00726a266')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '200')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '15b144ba-58d6-4adb-9dc1-602eb09052ce')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '10000')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5420dea5-4acc-4d87-b264-a67ab17a93ae')
            column(name: 'df_usage_batch_uid', value: '260d71f8-ccfa-46fa-9319-55a00726a266')
            column(name: 'wr_wrk_inst', value: '103658926')
            column(name: 'work_title', value: 'Nitrates')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '5475802112214578XX')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'gross_amount', value: '200')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5420dea5-4acc-4d87-b264-a67ab17a93ae')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'bb6966f5-06e6-4224-8403-422e008fba3e')
            column(name: 'name', value: 'AT_service-fee-true-up-report-8_BATCH')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-04')
            column(name: 'fiscal_year', value: '2013')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
            column(name: 'name', value: 'AT_service-fee-true-up-report-4_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'a24c3287-7f34-4be0-b9b8-fb363979feba')
            column(name: 'df_usage_batch_uid', value: 'bb6966f5-06e6-4224-8403-422e008fba3e')
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '34.00')
            column(name: 'service_fee_amount', value: '16.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a24c3287-7f34-4be0-b9b8-fb363979feba')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a3546208-42a2-4a80-b58a-055ea2aadbdf')
            column(name: 'name', value: 'AT_service-fee-true-up-report-9_BATCH')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-04')
            column(name: 'fiscal_year', value: '2013')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'name', value: 'AT_service-fee-true-up-report-5_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'df_usage_batch_uid', value: 'a3546208-42a2-4a80-b58a-055ea2aadbdf')
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '50.00')
            column(name: 'net_amount', value: '34.00')
            column(name: 'service_fee_amount', value: '16.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2011-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 11')
            column(name: 'distribution_date', value: '2011-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2011-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '1a4a4c13-bfa8-4220-92b1-6ea5936ae28b')
            column(name: 'name', value: 'AT_service-fee-true-up-report-10_BATCH')
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-04-21')
            column(name: 'fiscal_year', value: '2013')
            column(name: 'gross_amount', value: '400.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6505f666-2534-4bb2-a72c-ae2e2dfa07e8')
            column(name: 'df_usage_batch_uid', value: '1a4a4c13-bfa8-4220-92b1-6ea5936ae28b')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6505f666-2534-4bb2-a72c-ae2e2dfa07e8')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd55a6040-ffba-4bcd-8569-cb09729f66fd')
            column(name: 'df_usage_batch_uid', value: '1a4a4c13-bfa8-4220-92b1-6ea5936ae28b')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd55a6040-ffba-4bcd-8569-cb09729f66fd')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'name', value: 'AT_service-fee-true-up-report-6_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '337c71b0-c665-46c7-945a-cf69e270dadf')
            column(name: 'df_usage_batch_uid', value: '1a4a4c13-bfa8-4220-92b1-6ea5936ae28b')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '337c71b0-c665-46c7-945a-cf69e270dadf')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '247030fa-86f0-4add-ad84-b0b6c8307f14d')
            column(name: 'df_usage_batch_uid', value: '1a4a4c13-bfa8-4220-92b1-6ea5936ae28b')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '247030fa-86f0-4add-ad84-b0b6c8307f14d')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3ecd88a9-2d45-4901-b341-c09cced9c6ce')
            column(name: 'name', value: 'AT_service-fee-true-up-report-11_BATCH')
            column(name: 'rro_account_number', value: '5000581901')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-03')
            column(name: 'fiscal_year', value: '2013')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bd04b243-ce8e-42c8-8cd5-7a1ba871d13a')
            column(name: 'df_usage_batch_uid', value: '3ecd88a9-2d45-4901-b341-c09cced9c6ce')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '200')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bd04b243-ce8e-42c8-8cd5-7a1ba871d13a')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '29e140ab-2a71-40a3-a55a-a68dcdb95a9b')
            column(name: 'name', value: 'AT_service-fee-true-up-report-12_BATCH')
            column(name: 'rro_account_number', value: '5000581901')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2013-01-03')
            column(name: 'fiscal_year', value: '2013')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'name', value: 'AT_service-fee-true-up-report-7_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '5ad8fe7f-73ea-4d7a-b27e-5b3bc73cf1dd')
            column(name: 'df_usage_batch_uid', value: '29e140ab-2a71-40a3-a55a-a68dcdb95a9b')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5ad8fe7f-73ea-4d7a-b27e-5b3bc73cf1dd')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'bde1e665-b10f-4015-936f-71fb42410e3b')
            column(name: 'df_usage_batch_uid', value: '29e140ab-2a71-40a3-a55a-a68dcdb95a9b')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2011-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 11')
            column(name: 'distribution_date', value: '2011-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2011-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bde1e665-b10f-4015-936f-71fb42410e3b')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1076962-f32e-4632-8d02-46c19ef0ad4a')
            column(name: 'df_usage_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-1_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '10474372-f32e-4632-8d02-46c19ef0ad4a')
            column(name: 'df_usage_uid', value: '7f1cb24d-cf00-4354-9ef9-0457d36a556e')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-1_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '8797bdae-8447-42b3-b7f9-b93c150e37ed')
            column(name: 'df_usage_uid', value: '27845f34-ab87-4147-aea0-a191ded7412a')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-2_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '2b157815-2114-467c-bce3-13ab2f70a1b1')
            column(name: 'df_usage_uid', value: '653c2173-5385-4a24-b42c-3428b256d74a')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-2_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'e09961a1-c364-42da-9ade-fdd7327df086')
            column(name: 'df_usage_uid', value: '4179d85a-a09d-4521-8b5c-dc9026bd8249')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-3_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '822091ac-730d-4a34-bf4d-1e0b7ffb558e')
            column(name: 'df_usage_uid', value: '280b9e39-f934-4474-9d11-847e91e36609')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-3_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '618ae1e7-110c-4d53-8212-5819daef6157')
            column(name: 'df_usage_uid', value: 'ddbed016-f04d-4556-ab9f-b8080fb90089')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-4_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'cac21d63-d425-4205-8b47-bf35bf9c071a')
            column(name: 'df_usage_uid', value: 'b58e8f58-07fc-4597-9c96-551383cfa1b1')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-4_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '74ed69e3-a7c7-9999-9a7b-48dbe1ac6f25')
            column(name: 'df_usage_uid', value: 'c38f4992-9e58-4a97-8a63-ce15c9ad4cd1')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-5_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '74ed69e3-a7c7-4859-9a44-48dbe1ac6f25')
            column(name: 'df_usage_uid', value: '30121fa1-4550-4fe8-9776-2d17f16a54a1')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-5_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '744443-a7c7-4859-9a7b-48dbe1ac6f25')
            column(name: 'df_usage_uid', value: 'c66b15fa-ce23-4d15-89d0-fbae38e360b6')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-6_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '744469e3-a7c7-4859-9a7b-48dbe1ac6f25')
            column(name: 'df_usage_uid', value: '022184ed-2adb-4237-8a5f-34eac350bcbc')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-6_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'a09600f6-61c9-4755-ae83-947d7c476f20')
            column(name: 'df_usage_uid', value: '022184ed-2adb-4237-8a5f-34eac350bcbc')
            column(name: 'action_type_ind', value: 'ELIGIBLE_FOR_NTS')
            column(name: 'action_reason', value: 'Detail was made eligible for NTS because sum of gross amounts, grouped by standard number, is less than \$100')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '74ed6111-a7c7-4859-9a7b-48dbe1ac6f25')
            column(name: 'df_usage_uid', value: '3def87c0-6759-4a16-bcd9-945b14c00219c')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-6_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '97759cc1-5ec1-4caa-9e99-ead8e58416c7')
            column(name: 'df_usage_uid', value: '5420dea5-4acc-4d87-b264-a67ab17a93ae')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-7_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'c6b58abe-8370-4b59-b57d-4d0486a60a04')
            column(name: 'df_usage_uid', value: 'eed8d6f8-7ac6-4ae7-9bdc-ba33a58a5bad')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-7_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '777b456-6654-4067-aa3e-3af127820d41')
            column(name: 'df_usage_uid', value: 'eed8d6f8-7ac6-4ae7-9bdc-ba33a58a5bad')
            column(name: 'action_type_ind', value: 'RH_NOT_FOUND')
            column(name: 'action_reason', value: 'Rightsholder account was not found in RMS')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '05fb62e0-3519-4373-a92a-78f6850f75de')
            column(name: 'df_usage_uid', value: 'f6849fd0-c094-4a72-979e-00cf462fb3eb')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-7_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '9d02cef1-1e08-4d60-b630-6dce1a972d8c')
            column(name: 'df_usage_uid', value: 'f6849fd0-c094-4a72-979e-00cf462fb3eb')
            column(name: 'action_type_ind', value: 'WORK_NOT_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst was not found by Standard Number \'2192-3558\'')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '9b562de6-deb3-4659-9967-39452a7bbac2')
            column(name: 'df_usage_uid', value: 'f6849fd0-c094-4a72-979e-00cf462fb3eb')
            column(name: 'action_type_ind', value: 'WORK_RESEARCH')
            column(name: 'action_reason', value: 'Usage detail was sent for Research')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '777b9823-6654-4067-aa3e-3af127820d41')
            column(name: 'df_usage_uid', value: '15b144ba-58d6-4adb-9dc1-602eb09052ce')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-7_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '349421a0-9ef5-4bf7-853d-ffcd8bb2af05')
            column(name: 'df_usage_uid', value: '15b144ba-58d6-4adb-9dc1-602eb09052ce')
            column(name: 'action_type_ind', value: 'RH_NOT_FOUND')
            column(name: 'action_reason', value: 'Rightsholder account was not found in RMS')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '3cfd44b2-077a-40da-88d8-7d5eb2d1181e')
            column(name: 'df_usage_uid', value: '15b144ba-58d6-4adb-9dc1-602eb09052ce')
            column(name: 'action_type_ind', value: 'SENT_FOR_RA')
            column(name: 'action_reason', value: 'Sent for RA: job id ‘3e66a95c-e13d-4267-8asgrgfdee75dc77’')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1ccde68e-896f-4035-b57e-1111126ffba2')
            column(name: 'df_usage_uid', value: 'a24c3287-7f34-4be0-b9b8-fb363979feba')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-8_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '4b763489-3e87-4073-b435-d6154cc4e761')
            column(name: 'df_usage_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-9_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '3efce0ea-5539-4510-b6d6-953763332bab')
            column(name: 'df_usage_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'action_type_ind', value: 'PAID')
            column(name: 'action_reason', value: 'Usage has been paid according to information from the LM')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '2caff610-009d-4365-889d-0123432491')
            column(name: 'df_usage_uid', value: '6505f666-2534-4bb2-a72c-ae2e2dfa07e8')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-10_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1ccde68e-896f-4035-b57e-012332143124ba2')
            column(name: 'df_usage_uid', value: '6505f666-2534-4bb2-a72c-ae2e2dfa07e8')
            column(name: 'action_type_ind', value: 'WORK_NOT_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst was not found by Standard Number \'2192-3558\'')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1ccde68e-896f-4035-b57e-2352532352fba3')
            column(name: 'df_usage_uid', value: '6505f666-2534-4bb2-a72c-ae2e2dfa07e8')
            column(name: 'action_type_ind', value: 'WORK_RESEARCH')
            column(name: 'action_reason', value: 'Usage detail was sent for Research')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '2216214610-009d-4365-889d-01ef07e28191')
            column(name: 'df_usage_uid', value: '247030fa-86f0-4add-ad84-b0b6c8307f14d')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-10_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '32463643160-009d-4365-889d-01ef07e28191')
            column(name: 'df_usage_uid', value: 'd55a6040-ffba-4bcd-8569-cb09729f66fd')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-10_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1ccde68e-896f-4035-b57e-0a1476598fba2')
            column(name: 'df_usage_uid', value: 'd55a6040-ffba-4bcd-8569-cb09729f66fd')
            column(name: 'action_type_ind', value: 'WORK_NOT_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst was not found by Standard Number \'2192-3558\'')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1ccde68e-896f-4035-b57e-0a4579d6ffba3')
            column(name: 'df_usage_uid', value: 'd55a6040-ffba-4bcd-8569-cb09729f66fd')
            column(name: 'action_type_ind', value: 'WORK_RESEARCH')
            column(name: 'action_reason', value: 'Usage detail was sent for Research')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '3d8a4857-f15f-4f9a-b104-ce3764d1531e')
            column(name: 'df_usage_uid', value: '337c71b0-c665-46c7-945a-cf69e270dadf')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-10_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1ccde68e-896f-4035-457e-0a1123ffba1')
            column(name: 'df_usage_uid', value: 'bd04b243-ce8e-42c8-8cd5-7a1ba871d13a')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-11_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1ccde68e-896f-4035-b57e-0a1476213fba2')
            column(name: 'df_usage_uid', value: 'bd04b243-ce8e-42c8-8cd5-7a1ba871d13a')
            column(name: 'action_type_ind', value: 'WORK_NOT_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst was not found by Standard Number \'2192-3558\'')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1ccde68e-896f-411115-b57e-0a4579d6ffba3')
            column(name: 'df_usage_uid', value: 'bd04b243-ce8e-42c8-8cd5-7a1ba871d13a')
            column(name: 'action_type_ind', value: 'WORK_RESEARCH')
            column(name: 'action_reason', value: 'Usage detail was sent for Research')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '23532532e68e-896f-4035-b57e-0a1976fba1')
            column(name: 'df_usage_uid', value: '5ad8fe7f-73ea-4d7a-b27e-5b3bc73cf1dd')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-12_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '52e5a7e4-6996-48e5-8606-84754ae0a714')
            column(name: 'df_usage_uid', value: 'bde1e665-b10f-4015-936f-71fb42410e3b')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_Undistributed Liabilities Reconciliation Report-12_BATCH\'')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '27344572-74be-41cd-b06a-74f5e557a2e6')
            column(name: 'df_usage_uid', value: 'bde1e665-b10f-4015-936f-71fb42410e3b')
            column(name: 'action_type_ind', value: 'PAID')
            column(name: 'action_reason', value: 'Usage has been paid according to information from the LM')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7f0f053b-e06b-44c6-886c-ca45c38f6af9')
            column(name: 'df_usage_uid', value: 'bde1e665-b10f-4015-936f-71fb42410e3b')
            column(name: 'action_type_ind', value: 'ARCHIVED')
            column(name: 'action_reason', value: 'Usage was sent to CRM')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'a87797e1-613a-4a52-8367-f1a33283f074')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-01-01 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '3aa29712-cd46-4a37-80ae-912c3e9df1c8')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '915fac96-4979-43f4-91dd-3231ceb713fd')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '79f43b59-388f-4d04-8e6a-e78d5edb758f')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a76')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a77')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a78')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a79')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'e7fb6d7d-a943-49eb-a08b-8ca498ec4128')
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '5dd612c2-bb61-463d-b997-a0bd65b352aa')
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '73ae3f48-a254-4360-a717-26fd3c4a6d78')
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '62d28288-0e58-4847-ba2d-695c763e814b')
            column(name: 'df_scenario_uid', value: 'e64ff3bd-921c-462a-8131-1f35b0852f8b')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '9d365e9b-8083-4fcb-b939-a60c9c4c4fc6')
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '26da4b77-1c4f-4600-b87b-aba2e01ee495')
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '6b1d2ccc-9949-4adb-9bbf-3d6aa144c837')
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '07bd1576-cf64-433b-a07f-7dd897de67b1')
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a06')
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '6a7e9a50-d57a-4eaa-b6f5-2aa0275adfd8')
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'afbaebbf-900a-4fe0-ba2b-823faf238b6b')
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '8900c492-19c7-44ac-bde1-23df42960926')
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'd17cc542-426b-4c06-b9b2-3438860f12d2')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '623b891c-e368-4244-997e-d744d087c633')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '481952de-609c-4c44-a139-1a8e3f58c0f4')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'd945dfb2-0938-4b36-9671-d289b3e36820')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'b8676230-f71f-4056-858b-5fea956c98b3')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a27')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a28')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'created_datetime', value: '2012-03-15 11:43:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:43:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'fec52b70-5768-11e8-b566-0800200c9a29')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-03-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-03-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'f3cbcf41-b1f9-4857-8f9d-17a1de9f5811')
            column(name: 'df_scenario_uid', value: '79841191-4101-4bee-beca-01cab4f62e23')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'f3cbcf41-b1f9-4857-8f9d-17a1de9f5811')
            column(name: 'df_usage_batch_uid', value: '40a02427-1204-47ae-849c-1299a337cc47')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '02d0823d-fc8f-4939-9339-3aaa31dc4883')
            column(name: 'df_scenario_uid', value: '1a286785-56e0-4d7d-af82-ec24892190e0')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '02d0823d-fc8f-4939-9339-3aaa31dc4883')
            column(name: 'df_usage_batch_uid', value: 'a2fd8112-4623-4abf-aa6b-3fe820c49eb2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '5d685f54-67dc-4fc9-a9b6-adcd5c2f2a49')
            column(name: 'df_scenario_uid', value: '7887b131-e38d-452d-806b-f9615a2401ce')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '5d685f54-67dc-4fc9-a9b6-adcd5c2f2a49')
            column(name: 'df_usage_batch_uid', value: '7675a48a-cba6-4a2e-a4a4-fee2237a0128')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '04d95dd5-8018-4eea-b297-1d334a694e29')
            column(name: 'df_scenario_uid', value: '0be76458-41f6-4abe-99ea-e1bf67964d41')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '04d95dd5-8018-4eea-b297-1d334a694e29')
            column(name: 'df_usage_batch_uid', value: 'bb6966f5-06e6-4224-8403-422e008fba3e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '4797d20e-65e0-4dd0-a2c0-3d9e8bf3b0b2')
            column(name: 'df_scenario_uid', value: 'ae7fdeeb-0407-4ca8-b03a-28c96a8b7fbf')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '4797d20e-65e0-4dd0-a2c0-3d9e8bf3b0b2')
            column(name: 'df_usage_batch_uid', value: 'a3546208-42a2-4a80-b58a-055ea2aadbdf')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '1cf842fa-2454-4256-aeed-da46075784d5')
            column(name: 'df_scenario_uid', value: '6c633083-c071-4735-af9d-e8e49b773ab0')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '1cf842fa-2454-4256-aeed-da46075784d5')
            column(name: 'df_usage_batch_uid', value: '1a4a4c13-bfa8-4220-92b1-6ea5936ae28b')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'de9c64ef-1fe3-4331-810d-bb535d9076a7')
            column(name: 'df_scenario_uid', value: 'e38b94d8-35e1-499a-8dfd-b31970b35cc9')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'de9c64ef-1fe3-4331-810d-bb535d9076a7')
            column(name: 'df_usage_batch_uid', value: '29e140ab-2a71-40a3-a55a-a68dcdb95a9b')
        }

        // Shouldn't be included into report as it is AACL data

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '6288ed1e-43d1-4a43-a19c-60f54c6cb524')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AT_service-fee-true-up-report_AACL_FUND_POOL')
            column(name: 'total_amount', value: '200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '820c819b-02fc-42d0-a61a-3e42d98b0601')
            column(name: 'df_fund_pool_uid', value: '6288ed1e-43d1-4a43-a19c-60f54c6cb524')
            column(name: 'df_aggregate_licensee_class_id', value: '141')
            column(name: 'gross_amount', value: '200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a0a99625-c358-448d-b00a-65fdf0529c73')
            column(name: 'name', value: 'AT_service-fee-true-up-report_AACL_BATCH')
            column(name: 'payment_date', value: '2012-06-30')
            column(name: 'product_family', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'fe5a3cef-6bbb-459f-a525-33ff253ab6b3')
            column(name: 'name', value: 'AT_service-fee-true-up-report_AACL_SCENARIO')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}], "fund_pool_uid": "6288ed1e-43d1-4a43-a19c-60f54c6cb524", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'b6b96bbb-e28b-4fc0-8db2-967ddcc36532')
            column(name: 'df_scenario_uid', value: 'fe5a3cef-6bbb-459f-a525-33ff253ab6b3')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'created_datetime', value: '2012-02-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-02-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'a1ddcc82-e954-4294-8a61-70e3395055f8')
            column(name: 'df_scenario_uid', value: 'fe5a3cef-6bbb-459f-a525-33ff253ab6b3')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'action_reason', value: 'Scenario submitted for approval')
            column(name: 'created_datetime', value: '2012-02-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-02-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '608b62ff-c55f-4cfa-af03-a8c8567ac99b')
            column(name: 'df_scenario_uid', value: 'fe5a3cef-6bbb-459f-a525-33ff253ab6b3')
            column(name: 'action_type_ind', value: 'APPROVED')
            column(name: 'action_reason', value: 'Scenario approved by manager')
            column(name: 'created_datetime', value: '2012-02-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-02-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '4ad5cfad-984d-43a6-a23b-308cba20d47a')
            column(name: 'df_scenario_uid', value: 'fe5a3cef-6bbb-459f-a525-33ff253ab6b3')
            column(name: 'action_type_ind', value: 'SENT_TO_LM')
            column(name: 'created_datetime', value: '2012-02-15 11:44:52.735531+03')
            column(name: 'updated_datetime', value: '2012-02-15 11:44:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '152e607e-7c9d-4f08-9bda-ee1315764417')
            column(name: 'df_usage_batch_uid', value: 'a0a99625-c358-448d-b00a-65fdf0529c73')
            column(name: 'df_scenario_uid', value: 'fe5a3cef-6bbb-459f-a525-33ff253ab6b3')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'AACL')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'number_of_copies', value: '155')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '152e607e-7c9d-4f08-9bda-ee1315764417')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '100')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '171')
            column(name: 'value_weight', value: '5.0000000')
            column(name: 'volume_weight', value: '54.0000000')
            column(name: 'volume_share', value: '1.0000000')
            column(name: 'value_share', value: '1.0000000')
            column(name: 'total_share', value: '1.0000000')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'ee2f5cb7-ed8c-46ea-93c8-432b9e8ee094')
            column(name: 'df_usage_uid', value: '152e607e-7c9d-4f08-9bda-ee1315764417')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AT_service-fee-true-up-report_AACL_BATCH\' Batch')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7c1a3dd2-ceb9-4974-b230-1edd11def45d')
            column(name: 'df_usage_uid', value: '152e607e-7c9d-4f08-9bda-ee1315764417')
            column(name: 'action_type_ind', value: 'WORK_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst 243904752 was found in PI')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '89a74732-8f94-45a3-adf9-121ad3200d1c')
            column(name: 'df_usage_uid', value: '152e607e-7c9d-4f08-9bda-ee1315764417')
            column(name: 'action_type_ind', value: 'RH_FOUND')
            column(name: 'action_reason', value: 'Rightsholder account 1000009522 was found in RMS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'ca1c3413-646f-4c49-987b-5b10f62eaa4e')
            column(name: 'df_usage_uid', value: '152e607e-7c9d-4f08-9bda-ee1315764417')
            column(name: 'action_type_ind', value: 'WORK_RESEARCH')
            column(name: 'action_reason', value: 'Usage detail was sent for classification')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '9e5813b3-c384-4c0f-ac2c-33174eda6380')
            column(name: 'df_usage_uid', value: '152e607e-7c9d-4f08-9bda-ee1315764417')
            column(name: 'action_type_ind', value: 'ELIGIBLE')
            column(name: 'action_reason', value: 'Usages has become eligible after classification')
        }

        rollback ""
    }

    changeSet(id: '2019-05-22-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testWriteOwnershipAdjustmentCsvReport, testWriteOwnershipAdjustmentCsvEmptyReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '60241909-744c-4766-ad67-fdc9e2c043eb')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282cac-2468-48d4-b346-93d3458a656a')
            column(name: 'name', value: 'Ownership Adjustment Report Batch')
            column(name: 'rro_account_number', value: '7000800832')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '30000')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'name', value: 'Ownership Adjustment Report Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'df_usage_batch_uid', value: '56282cac-2468-48d4-b346-93d3458a656a')
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'wr_wrk_inst', value: '122843178')
            column(name: 'work_title', value: 'Accounting education news')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'gross_amount', value: '16437.40')
            column(name: 'net_amount', value: '11177.40')
            column(name: 'service_fee_amount', value: '5260.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Bus, Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '9900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder_discrepancy') {
            column(name: 'df_rightsholder_discrepancy_uid', value: '5745caa4-4301-4943-a992-4561c22d3ef0')
            column(name: 'df_scenario_uid', value: '3210b236-1239-4a60-9fab-888b84199321')
            column(name: 'wr_wrk_inst', value: '122843178')
            column(name: 'old_rh_account_number', value: '1000002859')
            column(name: 'new_rh_account_number', value: '1000005413')
            column(name: 'work_title', value: 'Accounting education news')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'APPROVED')
        }

        rollback ""
    }

    changeSet(id: '2019-07-02-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testWriteWorkClassificationCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'name', value: 'NTS fund pool 1')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: '2016')
            column(name: 'product_family', value: 'NTS')
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":7,"stm_amount":700,"stm_minimum_amount":50,"non_stm_amount":5000,"fund_pool_period_from":2010,"markets":["Bus","Doc Del"],"fund_pool_period_to":2012}')
        }

        // has classification
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '85093193-00d9-436b-8fbc-078511b1d335')
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'wr_wrk_inst', value: '987654321')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'rh_account_number', value: '2000017001')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '85093193-00d9-436b-8fbc-078511b1d335')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2012')
            column(name: 'market_period_to', value: '2014')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '0b3f6bb1-40b7-4d11-ba53-d54e9d67e61f')
            column(name: 'wr_wrk_inst', value: '987654321')
            column(name: 'classification', value: 'NON-STM')
            column(name: 'updated_datetime', value: '2019-10-16')
            column(name: 'updated_by_user', value: 'user@copyright.com')
        }

        // archived
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'e7e02a20-9e54-11e9-b475-0800200c9a66')
            column(name: 'wr_wrk_inst', value: '987654321')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'rh_account_number', value: '2000017001')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: '1')
            column(name: 'gross_amount', value: '500.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e7e02a20-9e54-11e9-b475-0800200c9a66')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2012')
            column(name: 'market_period_to', value: '2014')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '500')
        }

        // work w/o classification
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5f956b4b-3b09-457f-a306-f36fc55710af')
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'wr_wrk_inst', value: '9876543212')
            column(name: 'work_title', value: 'future of children')
            column(name: 'system_title', value: 'future of children')
            column(name: 'rh_account_number', value: '2000017001')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5f956b4b-3b09-457f-a306-f36fc55710af')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: '2010')
            column(name: 'market_period_to', value: '2011')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '500')
        }

        // work not suits the search criteria
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b51340ad-cf32-4c38-8445-4455e4ae81eb')
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'wr_wrk_inst', value: '918765432')
            column(name: 'work_title', value: 'Corporate identity manuals')
            column(name: 'system_title', value: 'Corporate identity manuals')
            column(name: 'rh_account_number', value: '2000017001')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b51340ad-cf32-4c38-8445-4455e4ae81eb')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: '2010')
            column(name: 'market_period_to', value: '2011')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '84ba864e-716a-4103-bcd7-180563695f50')
            column(name: 'wr_wrk_inst', value: '98765432')
            column(name: 'classification', value: 'STM')
        }

        rollback ""
    }

    changeSet(id: '2019-07-30-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testExportScenarioUsagesCsvReportWithNts')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '85ba864e-1939-4a60-9fab-888b84199321')
            column(name: 'name', value: 'Ownership Adjustment Report Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter") {
            column(name: "df_scenario_usage_filter_uid", value: "85ba864e-b1f9-4857-8f9d-17a1de9f5811")
            column(name: "df_scenario_uid", value: "85ba864e-1939-4a60-9fab-888b84199321")
            column(name: "product_family", value: "NTS")
            column(name: "status_ind", value: "ELIGIBLE")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'name', value: 'NTS fund pool 2')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: '2016')
            column(name: 'product_family', value: 'NTS')
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":7,"stm_amount":700,"stm_minimum_amount":50,"non_stm_amount":5000,"fund_pool_period_from":2010,"markets":["Bus","Doc Del"],"fund_pool_period_to":2012}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '89ba847e-3b09-457f-a306-f36fc55710af')
            column(name: 'df_usage_batch_uid', value: 'f17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'df_scenario_uid', value: '85ba864e-1939-4a60-9fab-888b84199321')
            column(name: 'wr_wrk_inst', value: '9876543212')
            column(name: 'work_title', value: 'future of children')
            column(name: 'system_title', value: 'future of children')
            column(name: 'rh_account_number', value: '2000017001')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '1')
            column(name: 'service_fee', value: '0.0')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '89ba847e-3b09-457f-a306-f36fc55710af')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: '2010')
            column(name: 'market_period_to', value: '2011')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '500')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter_to_usage_batches_ids_map") {
            column(name: "df_scenario_usage_filter_uid", value: "85ba864e-b1f9-4857-8f9d-17a1de9f5811")
            column(name: "df_usage_batch_uid", value: "f17ebc80-e74e-436d-ba6e-acf3d355b7ff")
        }

        rollback ""
    }

    changeSet(id: '2019-10-28-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteNtsWithdrawnBatchSummaryCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund1')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: '10.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '4fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund2')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: '10.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '5fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund3')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: '10.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a6669f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: '1000002900')
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '99991909-744c-4766-ad67-fdc9e2c043eb')
            column(name: 'rh_account_number', value: '1000002901')
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '99966cac-2468-48d4-b346-93d3458a656a')
            column(name: 'name', value: 'NTS Withdrawn Report Batch')
            column(name: 'rro_account_number', value: '1000002901')
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '30001')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '88866cac-2468-48d4-b346-93d3458a656a')
            column(name: 'name', value: 'NTS Withdrawn Report Batch Included To Be Distributed')
            column(name: 'rro_account_number', value: '1000002900')
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '30002')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "66649fd0-c094-4a72-979e-00cf462fb3eb")
            column(name: "df_usage_batch_uid", value: "99966cac-2468-48d4-b346-93d3458a656a")
            column(name: "product_family", value: "FAS")
            column(name: "work_title", value: "Wissenschaft & Forschung Japan")
            column(name: "status_ind", value: "NTS_WITHDRAWN")
            column(name: "standard_number", value: "2192-3558")
            column(name: "number_of_copies", value: "100")
            column(name: "gross_amount", value: "200")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "66649fd0-c094-4a72-979e-00cf462fb3eb")
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "Network for Science")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "30.86")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "6666d6f8-7ac6-4ae7-9bdc-ba33a58a5bad")
            column(name: "df_usage_batch_uid", value: "99966cac-2468-48d4-b346-93d3458a656a")
            column(name: "product_family", value: "FAS")
            column(name: "wr_wrk_inst", value: "180382915")
            column(name: "work_title", value: "2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA")
            column(name: "status_ind", value: "NTS_WITHDRAWN")
            column(name: "standard_number", value: "2192-3566")
            column(name: "number_of_copies", value: "250232")
            column(name: "gross_amount", value: "200")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "6666d6f8-7ac6-4ae7-9bdc-ba33a58a5bad")
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "Network for Science")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "10000")
            column(name: "reported_value", value: "30.86")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "666644ba-58d6-4adb-9dc1-602eb09052ce")
            column(name: "df_usage_batch_uid", value: "99966cac-2468-48d4-b346-93d3458a656a")
            column(name: "product_family", value: "FAS")
            column(name: "work_title", value: "Wissenschaft & Forschung Japan")
            column(name: "status_ind", value: "NTS_WITHDRAWN")
            column(name: "standard_number", value: "2192-3558")
            column(name: "number_of_copies", value: "100")
            column(name: "gross_amount", value: "200")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "666644ba-58d6-4adb-9dc1-602eb09052ce")
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "Network for Science")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "10000")
            column(name: "reported_value", value: "30.86")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "6666dea5-4acc-4d87-b264-a67ab17a93ae")
            column(name: "df_usage_batch_uid", value: "99966cac-2468-48d4-b346-93d3458a656a")
            column(name: "wr_wrk_inst", value: "103658926")
            column(name: "work_title", value: "Nitrates")
            column(name: "status_ind", value: "ELIGIBLE")
            column(name: "product_family", value: "FAS")
            column(name: "standard_number", value: "5475802112214578XX")
            column(name: "number_of_copies", value: "250232")
            column(name: "gross_amount", value: "200")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "6666dea5-4acc-4d87-b264-a67ab17a93ae")
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "IEEE")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "30.86")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "11149fd0-c094-4a72-979e-00cf462fb3eb")
            column(name: "df_usage_batch_uid", value: "88866cac-2468-48d4-b346-93d3458a656a")
            column(name: "product_family", value: "NTS")
            column(name: "work_title", value: "Wissenschaft & Forschung Japan")
            column(name: "status_ind", value: "TO_BE_DISTRIBUTED")
            column(name: "standard_number", value: "2192-3558")
            column(name: "number_of_copies", value: "100")
            column(name: "gross_amount", value: "202")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "11149fd0-c094-4a72-979e-00cf462fb3eb")
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "Network for Science")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "30.86")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "2226d6f8-7ac6-4ae7-9bdc-ba33a58a5bad")
            column(name: "df_usage_batch_uid", value: "88866cac-2468-48d4-b346-93d3458a656a")
            column(name: "product_family", value: "NTS")
            column(name: "wr_wrk_inst", value: "180382915")
            column(name: "work_title", value: "2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA")
            column(name: "status_ind", value: "TO_BE_DISTRIBUTED")
            column(name: "standard_number", value: "2192-3566")
            column(name: "number_of_copies", value: "250232")
            column(name: "gross_amount", value: "202")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "2226d6f8-7ac6-4ae7-9bdc-ba33a58a5bad")
            column(name: "df_fund_pool_uid", value: "4fef25b0-c0d1-4819-887f-4c6acc01390e")
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "Network for Science")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "10000")
            column(name: "reported_value", value: "30.86")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "3336d6f8-7ac6-4ae7-9bdc-ba33a58a5bad")
            column(name: "df_usage_batch_uid", value: "88866cac-2468-48d4-b346-93d3458a656a")
            column(name: "product_family", value: "NTS")
            column(name: "wr_wrk_inst", value: "180382915")
            column(name: "work_title", value: "2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA")
            column(name: "status_ind", value: "TO_BE_DISTRIBUTED")
            column(name: "standard_number", value: "2192-3566")
            column(name: "number_of_copies", value: "250232")
            column(name: "gross_amount", value: "202")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_fas") {
            column(name: "df_usage_fas_uid", value: "3336d6f8-7ac6-4ae7-9bdc-ba33a58a5bad")
            column(name: "df_fund_pool_uid", value: "5fef25b0-c0d1-4819-887f-4c6acc01390e")
            column(name: "article", value: "DIN EN 779:2013")
            column(name: "publisher", value: "Network for Science")
            column(name: "publication_date", value: "2013-09-10")
            column(name: "market", value: "Doc Del")
            column(name: "market_period_from", value: "2013")
            column(name: "market_period_to", value: "2017")
            column(name: "author", value: "Philippe de Mézières")
            column(name: "reported_value", value: "10000")
            column(name: "reported_value", value: "30.86")
        }

        rollback ""
    }

    changeSet(id: '2019-12-11-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testWriteFasUsagesCsvReport')

        // batch with ELIGIBLE usages
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '4c9cc089-b812-42cf-a5d2-1f5eda51fa76')
            column(name: 'name', value: 'FAS batch 1')
            column(name: 'rro_account_number', value: '2000017001')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2019-01-01')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '25a96343-310f-4c9d-8923-63a58a3f57c6')
            column(name: 'df_usage_batch_uid', value: '4c9cc089-b812-42cf-a5d2-1f5eda51fa76')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '250.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '25a96343-310f-4c9d-8923-63a58a3f57c6')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2cd2b2be-605d-4fd5-9855-ca1064b00366')
            column(name: 'df_usage_batch_uid', value: '4c9cc089-b812-42cf-a5d2-1f5eda51fa76')
            column(name: 'wr_wrk_inst', value: '743904744')
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: '1000002901')
            column(name: 'payee_account_number', value: '1000002901')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '250.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2cd2b2be-605d-4fd5-9855-ca1064b00366')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        // batch with LOCKED usage
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '127da857-5f78-4d2d-a8c3-55a08d5bd52b')
            column(name: 'name', value: 'FAS Batch 2')
            column(name: 'rro_account_number', value: '2000017001')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '8972.00')
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
            column(name: 'wr_wrk_inst', value: '122235134')
            column(name: 'work_title', value: 'CHICKEN BREAST ON GRILL WITH FLAMES')
            column(name: 'rh_account_number', value: '7000429266')
            column(name: 'payee_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'net_amount', value: '6100.9872')
            column(name: 'service_fee', value: '0.32')
            column(name: 'service_fee_amount', value: '2871.0528')
            column(name: 'gross_amount', value: '8972.04')
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9d94a627-761a-4676-bd10-cad43f10da39')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: '9900.00')
            column(name: 'is_rh_participating_flag', value: false)
            column(name: 'is_payee_participating_flag', value: false)
        }
    }

    changeSet(id: '2019-12-11-01', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testWriteNtsUsagesCsvReport')

        // NTS batch with ELIGIBLE usages
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f20ac1a3-eee4-4027-b5fb-def9adf0f871')
            column(name: 'name', value: 'NTS batch')
            column(name: 'rro_account_number', value: '2000017001')
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2019-01-01')
            column(name: 'fiscal_year', value: '2010')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"markets": ["Bus,Univ,Doc Del"], "stm_amount": 10, "non_stm_amount": 20, "stm_minimum_amount": 30, "non_stm_minimum_amount": 40, "fund_pool_period_to": 2017, "fund_pool_period_from": 2017}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9705a377-d59a-4c84-bd26-7c754aab92e2')
            column(name: 'df_usage_batch_uid', value: 'f20ac1a3-eee4-4027-b5fb-def9adf0f871')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'payee_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9705a377-d59a-4c84-bd26-7c754aab92e2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '581b71b0-07b1-4db1-9a3b-351c5c5a8cf0')
            column(name: 'df_usage_batch_uid', value: 'f20ac1a3-eee4-4027-b5fb-def9adf0f871')
            column(name: 'wr_wrk_inst', value: '743904744')
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: '1000002901')
            column(name: 'payee_account_number', value: '1000002901')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '581b71b0-07b1-4db1-9a3b-351c5c5a8cf0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '30.86')
        }

        // NTS batch with LOCKED usage
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c0c07d51-2216-43c3-b61b-b904d86ec36a')
            column(name: 'name', value: 'Test Batch 2')
            column(name: 'rro_account_number', value: '2000017001')
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '8972.00')
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
            column(name: 'wr_wrk_inst', value: '122235134')
            column(name: 'work_title', value: 'CHICKEN BREAST ON GRILL WITH FLAMES')
            column(name: 'rh_account_number', value: '7000429266')
            column(name: 'payee_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'net_amount', value: '6100.9872')
            column(name: 'service_fee', value: '0.32')
            column(name: 'service_fee_amount', value: '2871.0528')
            column(name: 'gross_amount', value: '8972.04')
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bfc2ffbf-1b70-447e-a604-a6dee260d648')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: '9900.00')
            column(name: 'is_rh_participating_flag', value: false)
            column(name: 'is_payee_participating_flag', value: false)
        }
    }

    changeSet(id: '2019-12-18-01', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteAuditNtsCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0c0a379a-461c-4e84-8062-326ece3c1f65')
            column(name: 'name', value: 'Test Batch 4')
            column(name: 'rro_account_number', value: '2000017001')
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '8972.00')
            column(name: 'created_datetime', value: '2010-01-01 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"markets": ["Bus,Univ,Doc Del"], "stm_amount": 10, "non_stm_amount": 20, "stm_minimum_amount": 30, "non_stm_minimum_amount": 40, "fund_pool_period_to": 2017, "fund_pool_period_from": 2017}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b9a487f4-77b6-4264-8a53-ed1eab585a4a')
            column(name: 'df_usage_batch_uid', value: '0c0a379a-461c-4e84-8062-326ece3c1f65')
            column(name: 'df_scenario_uid', value: 'c90921d6-3315-4673-8825-2e0c6f7229ee')
            column(name: 'wr_wrk_inst', value: '122235134')
            column(name: 'work_title', value: 'CHICKEN BREAST ON GRILL WITH FLAMES')
            column(name: 'rh_account_number', value: '7000429266')
            column(name: 'payee_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'net_amount', value: '6100.9872')
            column(name: 'service_fee', value: '0.32')
            column(name: 'service_fee_amount', value: '2871.0528')
            column(name: 'gross_amount', value: '8972.04')
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b9a487f4-77b6-4264-8a53-ed1eab585a4a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: '9900.00')
            column(name: 'is_rh_participating_flag', value: false)
            column(name: 'is_payee_participating_flag', value: false)
        }
    }

    changeSet(id: '2020-01-03-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testWriteArchivedNtsScenarioUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '63f3ea68-b06a-4e59-8e16-48dc35f5091e')
            column(name: 'name', value: 'JAACC_11Dec16')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: '2016')
            column(name: 'gross_amount', value: '70000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'ff47dee9-327a-4ff6-b170-d89f5190ccd8')
            column(name: 'name', value: 'FAS scenario with archived usages')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '8dad0c7c-17d7-45c8-9e4e-329561be078a')
            column(name: 'df_scenario_uid', value: 'ff47dee9-327a-4ff6-b170-d89f5190ccd8')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '57860796-b72e-4e68-9968-309cbd208d5f')
            column(name: 'df_usage_batch_uid', value: '63f3ea68-b06a-4e59-8e16-48dc35f5091e')
            column(name: 'df_scenario_uid', value: 'ff47dee9-327a-4ff6-b170-d89f5190ccd8')
            column(name: 'wr_wrk_inst', value: '345870577')
            column(name: 'work_title', value: '10 Years Plant Molecular Biology')
            column(name: 'system_title', value: '10 Years Plant Molecular Biology')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'payee_account_number', value: '1000005413')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'number_of_copies', value: '2630')
            column(name: 'gross_amount', value: '2125.24')
            column(name: 'net_amount', value: '1445.1632')
            column(name: 'service_fee_amount', value: '680.0768')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'standard_number_type', value: 'VALISBN10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '57860796-b72e-4e68-9968-309cbd208d5f')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'McGraw-Hill')
            column(name: 'publication_date', value: '2009-12-31')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: 'Mirjam H. Hüberli')
            column(name: 'reported_value', value: '1280.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ff996279-39f2-46e5-8e29-3ecc3406117a')
            column(name: 'df_usage_batch_uid', value: '63f3ea68-b06a-4e59-8e16-48dc35f5091e')
            column(name: 'df_scenario_uid', value: 'ff47dee9-327a-4ff6-b170-d89f5190ccd8')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'gross_amount', value: '67874.80')
            column(name: 'net_amount', value: '46154.80')
            column(name: 'service_fee_amount', value: '21720.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'standard_number_type', value: 'VALISBN10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ff996279-39f2-46e5-8e29-3ecc3406117a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '9900')
        }
    }

    changeSet(id: '2020-01-03-01', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testWriteArchivedNtsScenarioUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1ead8a3e-1231-43a5-a3c5-ed766abe5a2f')
            column(name: 'name', value: 'NTS Scenario with archived usages')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario description')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":300.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '4a5bf68a-67fb-4dd5-ac26-94ef19dc25de')
            column(name: 'df_scenario_uid', value: '1ead8a3e-1231-43a5-a3c5-ed766abe5a2f')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'e256decd-46ac-4625-aa80-cafb26f93c2a')
            column(name: 'df_scenario_uid', value: '1ead8a3e-1231-43a5-a3c5-ed766abe5a2f')
            column(name: 'wr_wrk_inst', value: '151811999')
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: '6000.00')
            column(name: 'net_amount', value: '4080.00')
            column(name: 'service_fee_amount', value: '1920.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e256decd-46ac-4625-aa80-cafb26f93c2a')
            column(name: 'reported_value', value: '0.00')
            column(name: 'is_rh_participating_flag', value: 'false')
        }
    }

    changeSet(id: '2020-01-20-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testWriteUsagesForClassificationAndFindIds')

        insert(schemaName: dbAppsSchema, tableName: "df_usage_batch") {
            column(name: "df_usage_batch_uid", value: "aed882d5-7625-4039-8781-a6676e11c579")
            column(name: "name", value: "AACL batch")
            column(name: "rro_account_number", value: "2000017000")
            column(name: "payment_date", value: "2019-06-30")
            column(name: "product_family", value: "AACL")
            column(name: "fiscal_year", value: "2019")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "1208f434-3d98-49d5-bdc6-baa611d2d006")
            column(name: "df_usage_batch_uid", value: "aed882d5-7625-4039-8781-a6676e11c579")
            column(name: "wr_wrk_inst", value: "122825976")
            column(name: "system_title", value: "Ecotoxicology")
            column(name: "standard_number", value: "09639292")
            column(name: "standard_number_type", value: "VALISSN")
            column(name: "rh_account_number", value: "1000003578")
            column(name: "status_ind", value: "RH_FOUND")
            column(name: "product_family", value: "AACL")
            column(name: "number_of_copies", value: "10")
            column(name: "comment", value: "AACL comment")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_aacl") {
            column(name: "df_usage_aacl_uid", value: "1208f434-3d98-49d5-bdc6-baa611d2d006")
            column(name: "institution", value: "CORNELL UNIVERSITY")
            column(name: "usage_period", value: "2019")
            column(name: "usage_source", value: "Feb 2019 TUR")
            column(name: "number_of_pages", value: "12")
            column(name: "right_limitation", value: "ALL")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "0ac10a6f-1cf3-45b5-8d3b-0b4b0777a8e0")
            column(name: "df_usage_batch_uid", value: "aed882d5-7625-4039-8781-a6676e11c579")
            column(name: "wr_wrk_inst", value: "122820420")
            column(name: "system_title", value: "Castanea")
            column(name: "standard_number", value: "00087475")
            column(name: "standard_number_type", value: "VALISSN")
            column(name: "rh_account_number", value: "7001413934")
            column(name: "status_ind", value: "RH_FOUND")
            column(name: "product_family", value: "AACL")
            column(name: "number_of_copies", value: "20")
            column(name: "comment", value: "AACL comment")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_aacl") {
            column(name: "df_usage_aacl_uid", value: "0ac10a6f-1cf3-45b5-8d3b-0b4b0777a8e0")
            column(name: "institution", value: "BIOLA UNIVERSITY")
            column(name: "usage_period", value: "2019")
            column(name: "usage_source", value: "Aug 2018 TUR")
            column(name: "number_of_pages", value: "6")
            column(name: "right_limitation", value: "ALL")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "67d36799-5523-474d-91f6-2e12756a4918")
            column(name: "df_usage_batch_uid", value: "aed882d5-7625-4039-8781-a6676e11c579")
            column(name: "wr_wrk_inst", value: "109713043")
            column(name: "status_ind", value: "NEW")
            column(name: "product_family", value: "AACL")
            column(name: "number_of_copies", value: "20")
            column(name: "comment", value: "AACL comment")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_aacl") {
            column(name: "df_usage_aacl_uid", value: "67d36799-5523-474d-91f6-2e12756a4918")
            column(name: "institution", value: "BIOLA UNIVERSITY")
            column(name: "usage_period", value: "2019")
            column(name: "usage_source", value: "Aug 2018 TUR")
            column(name: "number_of_pages", value: "6")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "945553a2-0200-4e5d-9c5a-a44b57839c42")
            column(name: "df_usage_uid", value: "1208f434-3d98-49d5-bdc6-baa611d2d006")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'AACL batch'")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "25f4fa01-02aa-414a-9052-667360be8535")
            column(name: "df_usage_uid", value: "1208f434-3d98-49d5-bdc6-baa611d2d006")
            column(name: "action_type_ind", value: "WORK_FOUND")
            column(name: "action_reason", value: "Wr Wrk Inst 122825976 was found in PI")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "3ef7b6e0-8248-4ccf-b8db-ff1e0e3b5837")
            column(name: "df_usage_uid", value: "1208f434-3d98-49d5-bdc6-baa611d2d006")
            column(name: "action_type_ind", value: "RH_FOUND")
            column(name: "action_reason", value: "Rightsholder account 1000003578 was found in RMS")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "0dd59537-2266-411b-8171-092544911540")
            column(name: "df_usage_uid", value: "0ac10a6f-1cf3-45b5-8d3b-0b4b0777a8e0")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'AACL batch'")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "532a2ec3-75de-457b-b8d3-736e3c8e5e5a")
            column(name: "df_usage_uid", value: "0ac10a6f-1cf3-45b5-8d3b-0b4b0777a8e0")
            column(name: "action_type_ind", value: "WORK_FOUND")
            column(name: "action_reason", value: "Wr Wrk Inst 122820420 was found in PI")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "b1c672ce-30a1-4703-a143-dca797c2b0ea")
            column(name: "df_usage_uid", value: "0ac10a6f-1cf3-45b5-8d3b-0b4b0777a8e0")
            column(name: "action_type_ind", value: "RH_FOUND")
            column(name: "action_reason", value: "Rightsholder account 7001413934 was found in RMS")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "1fab6d9a-aa40-4ca9-b43e-cc657387c67d")
            column(name: "df_usage_uid", value: "67d36799-5523-474d-91f6-2e12756a4918")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'AACL batch'")
        }
    }

    changeSet(id: '2020-01-28-00', author: 'Ihar Suvorau<isuvorau@copyright.com>') {
        comment('Inserting AACL test data for testWriteResearchStatusCsvReport which should not be displayed in reports')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd08c250e-a8d8-491b-84d6-3945fd07be78')
            column(name: 'name', value: 'AACL Usage Batch 2015')
            column(name: 'product_family', value: 'AACL')
            column(name: 'payment_date', value: '2015-06-30')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '94ef6c15-bcd1-41ab-a65e-6dccec4a3213')
            column(name: 'df_usage_batch_uid', value: 'd08c250e-a8d8-491b-84d6-3945fd07be78')
            column(name: 'wr_wrk_inst', value: '122803735')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '94ef6c15-bcd1-41ab-a65e-6dccec4a3213')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2015')
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: '12')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '731ce234-0c44-41f3-971c-e5de3be7ab91')
            column(name: 'df_usage_batch_uid', value: 'd08c250e-a8d8-491b-84d6-3945fd07be78')
            column(name: 'wr_wrk_inst', value: '130297955')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '1')
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '731ce234-0c44-41f3-971c-e5de3be7ab91')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2015')
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: '199')
        }
    }

    changeSet(id: '2020-02-05-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("Insert test data for testWriteAaclUsagesCsvReport")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '600ad926-e7dd-4086-b283-87e6579395ce')
            column(name: 'name', value: 'AACL batch 2020')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8315e53b-0a7e-452a-a62c-17fe959f3f84')
            column(name: 'df_usage_batch_uid', value: '600ad926-e7dd-4086-b283-87e6579395ce')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: "system_title", value: 'Castanea')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'rh_account_number', value: '1000011451')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '8315e53b-0a7e-452a-a62c-17fe959f3f84')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: '6')
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '64194cf9-177f-4220-9eb5-01040324b8b2')
            column(name: 'df_usage_batch_uid', value: '600ad926-e7dd-4086-b283-87e6579395ce')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: "system_title", value: 'Castanea')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'rh_account_number', value: '1000011451')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'AACL classified usage 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '64194cf9-177f-4220-9eb5-01040324b8b2')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'detail_licensee_class_id', value: '120')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: '6')
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "abced58b-fff2-470b-99fc-11f2a3ae098a")
            column(name: "df_usage_batch_uid", value: "600ad926-e7dd-4086-b283-87e6579395ce")
            column(name: "wr_wrk_inst", value: "122820420")
            column(name: "system_title", value: "Castanea")
            column(name: "standard_number", value: "00087475")
            column(name: "standard_number_type", value: "VALISSN")
            column(name: "rh_account_number", value: "7001413934")
            column(name: "status_ind", value: "RH_FOUND")
            column(name: "product_family", value: "AACL")
            column(name: "number_of_copies", value: "20")
            column(name: "comment", value: "AACL comment")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_aacl") {
            column(name: "df_usage_aacl_uid", value: "abced58b-fff2-470b-99fc-11f2a3ae098a")
            column(name: "institution", value: "BIOLA UNIVERSITY")
            column(name: "usage_period", value: "2019")
            column(name: "usage_source", value: "Aug 2018 TUR")
            column(name: "number_of_pages", value: "6")
            column(name: "right_limitation", value: "ALL")
        }
    }

    changeSet(id: '2020-03-11-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("Insert test data for testWriteAuditAaclUsagesCsvReport")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '2eb52c26-b555-45ae-b8c5-21289dfeeac4')
            column(name: 'wr_wrk_inst', value: '123986581')
            column(name: 'usage_period', value: '2016')
            column(name: 'usage_source', value: 'Aug 2016 FR')
            column(name: 'number_of_copies', value: '30')
            column(name: 'number_of_pages', value: '6')
            column(name: 'detail_licensee_class_id', value: '143')
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'publication_type_weight', value: '2')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage')
            column(name: 'updated_datetime', value: '2020-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '29689635-c6ff-483c-972d-09eb2febb9e0')
            column(name: 'name', value: 'AACL batch 2020 for audit')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c794662f-a5d6-4b86-8955-582723631656')
            column(name: 'df_usage_batch_uid', value: '29689635-c6ff-483c-972d-09eb2febb9e0')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: "system_title", value: 'Castanea')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'rh_account_number', value: '1000011451')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '420.00')
            column(name: 'service_fee_amount', value: '80.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'AACL  usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'c794662f-a5d6-4b86-8955-582723631656')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: '6')
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3c96f468-abaa-4db7-9004-4012d8ba8e0d')
            column(name: 'df_usage_batch_uid', value: '29689635-c6ff-483c-972d-09eb2febb9e0')
            column(name: 'wr_wrk_inst', value: '123986581')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: "system_title", value: 'Castanea')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'rh_account_number', value: '1000011451')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '420.00')
            column(name: 'service_fee_amount', value: '80.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'comment', value: 'AACL audit usage 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '3c96f468-abaa-4db7-9004-4012d8ba8e0d')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'detail_licensee_class_id', value: '143')
            column(name: 'usage_period', value: '2016')
            column(name: 'usage_source', value: 'Aug 201 FR')
            column(name: 'number_of_pages', value: '6')
            column(name: 'right_limitation', value: 'ALL')
            column(name: 'baseline_uid', value: '2eb52c26-b555-45ae-b8c5-21289dfeeac4')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage") {
            column(name: "df_usage_uid", value: "027aa879-d963-41f1-bacf-db22ebe3584a")
            column(name: "df_usage_batch_uid", value: "29689635-c6ff-483c-972d-09eb2febb9e0")
            column(name: "wr_wrk_inst", value: "122820420")
            column(name: "system_title", value: "Castanea")
            column(name: "standard_number", value: "00087475")
            column(name: "standard_number_type", value: "VALISSN")
            column(name: "rh_account_number", value: "7001413934")
            column(name: "status_ind", value: "RH_FOUND")
            column(name: "product_family", value: "AACL")
            column(name: "number_of_copies", value: "20")
            column(name: "comment", value: "AACL comment")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_aacl") {
            column(name: "df_usage_aacl_uid", value: "027aa879-d963-41f1-bacf-db22ebe3584a")
            column(name: "institution", value: "BIOLA UNIVERSITY")
            column(name: "usage_period", value: "2019")
            column(name: "usage_source", value: "Aug 2018 TUR")
            column(name: "number_of_pages", value: "6")
            column(name: "right_limitation", value: "ALL")
        }
    }

    changeSet(id: '2020-04-22-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("Insert test data for testWriteWorkSharesByAggLcClassSummaryCsvReport")

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8fb69838-9f62-456f-ad52-58b55d71c305')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8aafed93-6964-41f6-be6e-f5e628c03ece')
            column(name: 'rh_account_number', value: '1000011881')
            column(name: 'name', value: 'William B. Eerdmans Publishing Company')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3a8eed5d-a2f2-47d2-9cba-b047d9947706')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool Test')
            column(name: 'total_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '65e72126-2d74-4018-a06a-7fa4c81f0b33')
            column(name: 'df_fund_pool_uid', value: '3a8eed5d-a2f2-47d2-9cba-b047d9947706')
            column(name: 'df_aggregate_licensee_class_id', value: '171')
            column(name: 'gross_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'name', value: 'AACL Usage Batch Test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: '2019')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
            column(name: 'name', value: 'AACL Scenario Test')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "fund_pool_uid": "3a8eed5d-a2f2-47d2-9cba-b047d9947706", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8eb9dbbc-3535-42cc-8094-2d90849952e2')
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'payee_account_number', value: '2580011451')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '155')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '8eb9dbbc-3535-42cc-8094-2d90849952e2')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '100')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '110')
            column(name: 'value_weight', value: '680.0000000000')
            column(name: 'volume_weight', value: '10.0000000000')
            column(name: 'volume_share', value: '0.4098360656')
            column(name: 'value_share', value: '0.3970571062')
            column(name: 'total_share', value: '0.4095917984')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e951defc-79c7-48d0-b7c7-958df4bdf2cb')
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
            column(name: 'wr_wrk_inst', value: '124181386')
            column(name: 'work_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'system_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '1000011881')
            column(name: 'payee_account_number', value: '1000011881')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'e951defc-79c7-48d0-b7c7-958df4bdf2cb')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '180')
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: '110')
            column(name: 'value_weight', value: '791.8000000000')
            column(name: 'volume_weight', value: '3.7000000000')
            column(name: 'volume_share', value: '0.1516393443')
            column(name: 'value_share', value: '0.4623379657')
            column(name: 'total_share', value: '0.3093360901')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f6411672-02d2-4e2e-8682-69f5ad7db8c4')
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'payee_account_number', value: '2580011451')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'f6411672-02d2-4e2e-8682-69f5ad7db8c4')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '180')
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: '110')
            column(name: 'value_weight', value: '240.8000000000')
            column(name: 'volume_weight', value: '10.7000000000')
            column(name: 'volume_share', value: '0.4385245902')
            column(name: 'value_share', value: '0.1406049282')
            column(name: 'total_share', value: '0.2810721113')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5408ee8b-30e0-416f-ada8-cbf08d62b26e')
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'payee_account_number', value: '2580011451')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '5408ee8b-30e0-416f-ada8-cbf08d62b26e')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '180')
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: '113')
            column(name: 'value_weight', value: '240.8000000000')
            column(name: 'volume_weight', value: '10.7000000000')
            column(name: 'volume_share', value: '1.0000000000')
            column(name: 'value_share', value: '1.0000000000')
            column(name: 'total_share', value: '1.0000000000')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '149ab28f-c795-4e29-9418-815c87dec127')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool Test 2')
            column(name: 'total_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'f88db923-ed05-4980-bb1d-8ae327824346')
            column(name: 'df_fund_pool_uid', value: '3a8eed5d-a2f2-47d2-9cba-b047d9947706')
            column(name: 'df_aggregate_licensee_class_id', value: '171')
            column(name: 'gross_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'name', value: 'AACL Usage Batch Test 2')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: '2019')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
            column(name: 'name', value: 'AACL Scenario Test 2')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "fund_pool_uid": "149ab28f-c795-4e29-9418-815c87dec127", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '3ae57b51-691d-4a97-95dd-434304325654')
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'payee_account_number', value: '2580011451')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '155')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '3ae57b51-691d-4a97-95dd-434304325654')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '100')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '110')
            column(name: 'value_weight', value: '680.0000000000')
            column(name: 'volume_weight', value: '10.0000000000')
            column(name: 'volume_share', value: '0.4098360656')
            column(name: 'value_share', value: '0.3970571062')
            column(name: 'total_share', value: '0.4095917984')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '4444ed28-cba5-4bea-9923-64a6264bca9d')
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
            column(name: 'wr_wrk_inst', value: '124181386')
            column(name: 'work_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'system_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '1000011881')
            column(name: 'payee_account_number', value: '1000011881')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '4444ed28-cba5-4bea-9923-64a6264bca9d')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '180')
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: '110')
            column(name: 'value_weight', value: '791.8000000000')
            column(name: 'volume_weight', value: '3.7000000000')
            column(name: 'volume_share', value: '0.1516393443')
            column(name: 'value_share', value: '0.4623379657')
            column(name: 'total_share', value: '0.3093360901')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'af7a5a9c-363c-4360-8fc0-7a318528f431')
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'payee_account_number', value: '2580011451')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'af7a5a9c-363c-4360-8fc0-7a318528f431')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '180')
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: '110')
            column(name: 'value_weight', value: '240.8000000000')
            column(name: 'volume_weight', value: '10.7000000000')
            column(name: 'volume_share', value: '0.4385245902')
            column(name: 'value_share', value: '0.1406049282')
            column(name: 'total_share', value: '0.2810721113')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '190a8e32-37ed-4fe2-987a-b481849c7939')
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'payee_account_number', value: '2580011451')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '190a8e32-37ed-4fe2-987a-b481849c7939')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '180')
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: '113')
            column(name: 'value_weight', value: '240.8000000000')
            column(name: 'volume_weight', value: '10.7000000000')
            column(name: 'volume_share', value: '1.0000000000')
            column(name: 'value_share', value: '1.0000000000')
            column(name: 'total_share', value: '1.0000000000')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }
    }

    changeSet(id: '2020-04-23-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("Insert test data for testWriteAaclScenarioUsagesCsvReport")

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'ffc1587c-9b05-4681-a3cc-dc02cec7fadc')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool For Export Detail Scenario Test')
            column(name: 'total_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '879229c9-82c5-4e82-8516-8366dd0e18ee')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'name', value: 'British Film Institute (BFI)')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '8474cceb-2367-4d03-b240-5ea6d2819a53')
            column(name: 'df_fund_pool_uid', value: 'ffc1587c-9b05-4681-a3cc-dc02cec7fadc')
            column(name: 'df_aggregate_licensee_class_id', value: '171')
            column(name: 'gross_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '354f8342-0bf0-4a89-aa9a-6f4428a29e9d')
            column(name: 'name', value: 'AACL Usage Batch For Export Details Scenario Test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '153b80ba-85e6-48ee-b5c3-c81664827e8a')
            column(name: 'name', value: 'AACL Scenario For Delete Scenario Test')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '13212981-431f-4311-97d5-1a39bc252afc')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'usage_period', value: '2015')
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_copies', value: '10')
            column(name: 'number_of_pages', value: '12')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: '1.71')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage')
            column(name: 'updated_datetime', value: '2020-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '53079eb1-ddfb-4d9b-a914-4402cb4b0f49')
            column(name: 'df_usage_batch_uid', value: '354f8342-0bf0-4a89-aa9a-6f4428a29e9d')
            column(name: 'df_scenario_uid', value: '153b80ba-85e6-48ee-b5c3-c81664827e8a')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'payee_account_number', value: "1000002797")
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '155')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '53079eb1-ddfb-4d9b-a914-4402cb4b0f49')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '100')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '171')
            column(name: 'value_weight', value: '0.1000000')
            column(name: 'volume_weight', value: '0.2000000')
            column(name: 'volume_share', value: '0.3000000')
            column(name: 'value_share', value: '0.4000000')
            column(name: 'total_share', value: '0.5000000')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7ec3e2df-5274-4eba-a493-b9502db11f4c')
            column(name: 'df_usage_batch_uid', value: '354f8342-0bf0-4a89-aa9a-6f4428a29e9d')
            column(name: 'df_scenario_uid', value: '153b80ba-85e6-48ee-b5c3-c81664827e8a')
            column(name: 'wr_wrk_inst', value: '109040891')
            column(name: 'work_title', value: 'Biological Journal')
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'payee_account_number', value: "1000002797")
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '75.00')
            column(name: 'service_fee_amount', value: '25.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '300')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '7ec3e2df-5274-4eba-a493-b9502db11f4c')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '200')
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: '113')
            column(name: 'value_weight', value: '0.6000000')
            column(name: 'volume_weight', value: '0.7000000')
            column(name: 'volume_share', value: '0.8000000')
            column(name: 'value_share', value: '0.9000000')
            column(name: 'total_share', value: '0.56000000')
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'baseline_uid', value: '13212981-431f-4311-97d5-1a39bc252afc')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '04c60c30-c11b-4cd4-9525-7a42793b00a6')
            column(name: 'df_scenario_uid', value: '153b80ba-85e6-48ee-b5c3-c81664827e8a')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '04c60c30-c11b-4cd4-9525-7a42793b00a6')
            column(name: 'df_usage_batch_uid', value: '354f8342-0bf0-4a89-aa9a-6f4428a29e9d')
        }

        //archived scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '0b28a1ff-ee07-4087-8980-ad7e7ea493f8')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool For Export Detail Archived Scenario Test')
            column(name: 'total_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '21c1240c-61f0-4aa7-a7d8-e4965415f80b')
            column(name: 'rh_account_number', value: '258001168')
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '1ec28821-2402-4063-bf2e-3bd0a20a8ed1')
            column(name: 'df_fund_pool_uid', value: '0b28a1ff-ee07-4087-8980-ad7e7ea493f8')
            column(name: 'df_aggregate_licensee_class_id', value: '171')
            column(name: 'gross_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '1e56b3b1-40a9-4f4f-8e2f-868caaba8693')
            column(name: 'name', value: 'AACL Usage Batch For Export Details Archived Scenario Test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5429c31b-ffd1-4a7f-9b24-8c7809417fce')
            column(name: 'name', value: 'AACL Scenario For Delete Scenario Test')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '44f90e4f-3038-4738-a41a-4e989d80b0f2')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'usage_period', value: '2015')
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_copies', value: '10')
            column(name: 'number_of_pages', value: '12')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: '1.71')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage')
            column(name: 'updated_datetime', value: '2020-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '04a64d17-eab8-4191-b162-f9c12e540795')
            column(name: 'df_usage_batch_uid', value: '1e56b3b1-40a9-4f4f-8e2f-868caaba8693')
            column(name: 'df_scenario_uid', value: '5429c31b-ffd1-4a7f-9b24-8c7809417fce')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'payee_account_number', value: '2580011451')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '04a64d17-eab8-4191-b162-f9c12e540795')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '200')
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'value_weight', value: '0.7900000')
            column(name: 'volume_weight', value: '0.5900000')
            column(name: 'volume_share', value: '0.4500000')
            column(name: 'value_share', value: '0.0780000')
            column(name: 'total_share', value: '0.9500000')
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'baseline_uid', value: '44f90e4f-3038-4738-a41a-4e989d80b0f2')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '89e290d3-c656-4b23-9e20-1daa975eeb19')
            column(name: 'df_scenario_uid', value: '5429c31b-ffd1-4a7f-9b24-8c7809417fce')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ARCHIVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '89e290d3-c656-4b23-9e20-1daa975eeb19')
            column(name: 'df_usage_batch_uid', value: '1e56b3b1-40a9-4f4f-8e2f-868caaba8693')
        }
    }

    changeSet(id: '2020-06-16-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for testWriteExcludeDetailsByPayeeCsvReport, testWriteExcludeDetailsByPayeeCsvEmptyReport")

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05c4714b-291d-4e38-ba4a-35307434acfb')
            column(name: 'rh_account_number', value: '7000813806')
            column(name: 'name', value: 'CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'name', value: 'FAS2 scenario for Exclude Details by Payee report')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'name', value: 'FAS2 batch for Exclude Details by Payee report')
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'payment_date', value: '2019-02-13')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '1000')
            column(name: 'created_datetime', value: '2019-01-01 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '30787462-b66a-4679-a2bc-1b89abd6ea66')
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '30787462-b66a-4679-a2bc-1b89abd6ea66')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5e3b4c6c-fe20-4a16-bbb6-9dc61b6c1eed')
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'wr_wrk_inst', value: '471137967')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '2008902112317645XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '84.00')
            column(name: 'service_fee_amount', value: '16.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5e3b4c6c-fe20-4a16-bbb6-9dc61b6c1eed')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
            column(name: 'is_rh_participating_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '77dce0ba-3a7c-4162-8264-69d1b973e7ac')
            column(name: 'df_usage_batch_uid', value: '26a66a43-2a5a-427a-b3af-0806ebfd7262')
            column(name: 'df_scenario_uid', value: 'e13ecc44-6795-4b75-90f0-4a3fc191f1b9')
            column(name: 'wr_wrk_inst', value: '12318778798')
            column(name: 'work_title', value: 'Future of medicine')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'payee_account_number', value: '7000813806')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '3008902112317645XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '77dce0ba-3a7c-4162-8264-69d1b973e7ac')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2019')
            column(name: 'reported_value', value: '100.00')
            column(name: 'is_payee_participating_flag', value: true)
        }
    }

    changeSet(id: '2020-06-17-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("Insert test data for testAaclUndistributedLiabilitiesCsvReport")

        //Should be included into report as it isn't associated with any scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '2a3aac29-6694-48fe-8c5d-c6709614ae73')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 1 for testAaclUndistributedLiabilitiesCsvReport')
            column(name: 'total_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'a463d517-0887-4d68-9422-a99e8997ddd5')
            column(name: 'df_fund_pool_uid', value: '2a3aac29-6694-48fe-8c5d-c6709614ae73')
            column(name: 'df_aggregate_licensee_class_id', value: '171')
            column(name: 'gross_amount', value: '100.00')
        }

        //Should be included into report as it is associated with SUBMITTED scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '6b2ba3de-f2a7-4d9b-8da1-d84118ddba30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 2 for testAaclUndistributedLiabilitiesCsvReport')
            column(name: 'total_amount', value: '200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '044f4190-5c0e-41e5-994b-983b7810ea74')
            column(name: 'df_fund_pool_uid', value: '6b2ba3de-f2a7-4d9b-8da1-d84118ddba30')
            column(name: 'df_aggregate_licensee_class_id', value: '141')
            column(name: 'gross_amount', value: '200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '4d6adcc5-9852-4322-b946-88e0ba977620')
            column(name: 'name', value: 'AACL Usage Batch 1 for testAaclUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '43242b46-1a85-4938-9865-b9b354b6ae44')
            column(name: 'name', value: 'AACL Scenario For Delete Scenario Test')
            column(name: 'status_ind', value: 'SUBMITTED')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}], "fund_pool_uid": "6b2ba3de-f2a7-4d9b-8da1-d84118ddba30", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3a70aab1-221c-46cd-89e2-417d0765fba2')
            column(name: 'df_usage_batch_uid', value: '4d6adcc5-9852-4322-b946-88e0ba977620')
            column(name: 'df_scenario_uid', value: '43242b46-1a85-4938-9865-b9b354b6ae44')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'payee_account_number', value: "1000002797")
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '155')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '3a70aab1-221c-46cd-89e2-417d0765fba2')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '100')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '171')
            column(name: 'value_weight', value: '5.0000000')
            column(name: 'volume_weight', value: '54.0000000')
            column(name: 'volume_share', value: '1.0000000')
            column(name: 'value_share', value: '1.0000000')
            column(name: 'total_share', value: '1.0000000')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        //Should be included into report as it is associated with APPROVED scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '404ba914-3c57-4551-867b-8bd4a1fdd8f2')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 6 for testAaclUndistributedLiabilitiesCsvReport')
            column(name: 'total_amount', value: '200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '24ddaead-40b3-47d9-af51-826878a6443e')
            column(name: 'df_fund_pool_uid', value: '404ba914-3c57-4551-867b-8bd4a1fdd8f2')
            column(name: 'df_aggregate_licensee_class_id', value: '141')
            column(name: 'gross_amount', value: '200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '1225cfc8-6b5e-48f2-b1a3-e4a887446532')
            column(name: 'name', value: 'AACL Usage Batch 5 for testAaclUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '50fcaabc-b12f-421d-aaf1-0b4e147d7540')
            column(name: 'name', value: 'AACL Scenario For Delete Scenario Test')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}], "fund_pool_uid": "404ba914-3c57-4551-867b-8bd4a1fdd8f2", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f5bc77ec-1be3-4867-a5ef-98b0a49b0d4e')
            column(name: 'df_usage_batch_uid', value: '1225cfc8-6b5e-48f2-b1a3-e4a887446532')
            column(name: 'df_scenario_uid', value: '50fcaabc-b12f-421d-aaf1-0b4e147d7540')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'payee_account_number', value: "1000002797")
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '155')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'f5bc77ec-1be3-4867-a5ef-98b0a49b0d4e')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '100')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '171')
            column(name: 'value_weight', value: '5.0000000')
            column(name: 'volume_weight', value: '54.0000000')
            column(name: 'volume_share', value: '1.0000000')
            column(name: 'value_share', value: '1.0000000')
            column(name: 'total_share', value: '1.0000000')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        //Shouldn't be included into report as it is associated with SENT_TO_LM scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '66bbea66-84e7-41cd-a5aa-9fd43f03dd5a')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 3 for testAaclUndistributedLiabilitiesCsvReport')
            column(name: 'total_amount', value: '200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '9124723e-bbd0-4750-bfad-764e7b5601e5')
            column(name: 'df_fund_pool_uid', value: '66bbea66-84e7-41cd-a5aa-9fd43f03dd5a')
            column(name: 'df_aggregate_licensee_class_id', value: '141')
            column(name: 'gross_amount', value: '200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6fcdd8f0-bb76-4dfa-9afe-00352cdef0d3')
            column(name: 'name', value: 'AACL Usage Batch 2 for testAaclUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'c5524e13-49a7-4057-8842-e8c3a8ad78cf')
            column(name: 'name', value: 'AACL Scenario For Delete Scenario Test')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}], "fund_pool_uid": "66bbea66-84e7-41cd-a5aa-9fd43f03dd5a", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '2c540f3a-0d43-4ca9-b1d2-73bb85774e43')
            column(name: 'df_usage_batch_uid', value: '6fcdd8f0-bb76-4dfa-9afe-00352cdef0d3')
            column(name: 'df_scenario_uid', value: 'c5524e13-49a7-4057-8842-e8c3a8ad78cf')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'payee_account_number', value: "1000002797")
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'number_of_copies', value: '155')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '2c540f3a-0d43-4ca9-b1d2-73bb85774e43')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '100')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '171')
            column(name: 'value_weight', value: '5.0000000')
            column(name: 'volume_weight', value: '54.0000000')
            column(name: 'volume_share', value: '1.0000000')
            column(name: 'value_share', value: '1.0000000')
            column(name: 'total_share', value: '1.0000000')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        //Shouldn't be included into report as it is associated with ARCHIVED scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3c79d8ee-42ef-4973-bdad-0a27d75504c9')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 5 for testAaclUndistributedLiabilitiesCsvReport')
            column(name: 'total_amount', value: '200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'ead693bc-e108-4d95-b660-17492b178823')
            column(name: 'df_fund_pool_uid', value: '3c79d8ee-42ef-4973-bdad-0a27d75504c9')
            column(name: 'df_aggregate_licensee_class_id', value: '141')
            column(name: 'gross_amount', value: '200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3bf8b930-a2cb-4af0-99e2-e69edd176450')
            column(name: 'name', value: 'AACL Usage Batch 4 for testAaclUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '75fe53a0-ccf4-404c-a4b5-143c88599fa0')
            column(name: 'name', value: 'AACL Scenario For Delete Scenario Test')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}], "fund_pool_uid": "3c79d8ee-42ef-4973-bdad-0a27d75504c9", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '381fcb77-8e28-4ab5-8f2d-ee295c2cf9e7')
            column(name: 'df_usage_batch_uid', value: '3bf8b930-a2cb-4af0-99e2-e69edd176450')
            column(name: 'df_scenario_uid', value: '75fe53a0-ccf4-404c-a4b5-143c88599fa0')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'payee_account_number', value: "1000002797")
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'number_of_copies', value: '155')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '381fcb77-8e28-4ab5-8f2d-ee295c2cf9e7')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '100')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '171')
            column(name: 'value_weight', value: '5.0000000')
            column(name: 'volume_weight', value: '54.0000000')
            column(name: 'volume_share', value: '1.0000000')
            column(name: 'value_share', value: '1.0000000')
            column(name: 'total_share', value: '1.0000000')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }
    }

    changeSet(id: '2020-07-01-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("Insert test data for testNtsUndistributedLiabilitiesCsvReport")

        //should be include in the report
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e558e176-901e-4101-9418-493bbcbd9679')
            column(name: 'name', value: 'NTS Batch for Nts undistributed liabilities report 1')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        //should be include in the report
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '7083ae7d-0079-41fb-aab1-8ae2bd5db8fe')
            column(name: 'name', value: 'NTS Batch for Nts undistributed liabilities report 2')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'ca163655-8978-4a45-8fe3-c3b5572c6879')
            column(name: 'name', value: 'Test NTS scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":300.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter") {
            column(name: "df_scenario_usage_filter_uid", value: "5dc4d7f0-2f77-4b3a-9b11-0033c300fdc6")
            column(name: "df_scenario_uid", value: "ca163655-8978-4a45-8fe3-c3b5572c6879")
            column(name: "product_family", value: "NTS")
            column(name: "status_ind", value: "ELIGIBLE")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter_to_usage_batches_ids_map") {
            column(name: "df_scenario_usage_filter_uid", value: "5dc4d7f0-2f77-4b3a-9b11-0033c300fdc6")
            column(name: "df_usage_batch_uid", value: "e558e176-901e-4101-9418-493bbcbd9679")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter_to_usage_batches_ids_map") {
            column(name: "df_scenario_usage_filter_uid", value: "5dc4d7f0-2f77-4b3a-9b11-0033c300fdc6")
            column(name: "df_usage_batch_uid", value: "7083ae7d-0079-41fb-aab1-8ae2bd5db8fe")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '429b9acd-f137-40c3-806d-dabae2315027')
            column(name: 'df_usage_batch_uid', value: 'e558e176-901e-4101-9418-493bbcbd9679')
            column(name: "df_scenario_uid", value: "ca163655-8978-4a45-8fe3-c3b5572c6879")
            column(name: 'wr_wrk_inst', value: '122267677')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '1')
            column(name: 'gross_amount', value: '900.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'service_fee_amount', value: '288.00')
            column(name: 'net_amount', value: '612.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '429b9acd-f137-40c3-806d-dabae2315027')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4e059d20-3224-44bb-976a-c8e3b64e4406')
            column(name: 'df_usage_batch_uid', value: 'e558e176-901e-4101-9418-493bbcbd9679')
            column(name: "df_scenario_uid", value: "ca163655-8978-4a45-8fe3-c3b5572c6879")
            column(name: 'wr_wrk_inst', value: '122267677')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '1')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'service_fee', value: '0.18500')
            column(name: 'service_fee_amount', value: '37.00')
            column(name: 'net_amount', value: '163.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4e059d20-3224-44bb-976a-c8e3b64e4406')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '200')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'bb76749b-19cb-44b2-9644-666bad872caf')
            column(name: 'df_usage_batch_uid', value: 'e558e176-901e-4101-9418-493bbcbd9679')
            column(name: 'wr_wrk_inst', value: '642267671')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '1')
            column(name: 'gross_amount', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bb76749b-19cb-44b2-9644-666bad872caf')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c8de604d-eb49-4d8f-9a42-2362fbca3277')
            column(name: 'df_usage_batch_uid', value: '7083ae7d-0079-41fb-aab1-8ae2bd5db8fe')
            column(name: "df_scenario_uid", value: "ca163655-8978-4a45-8fe3-c3b5572c6879")
            column(name: 'wr_wrk_inst', value: '122267677')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '1')
            column(name: 'gross_amount', value: '900.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'service_fee_amount', value: '288.00')
            column(name: 'net_amount', value: '612.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c8de604d-eb49-4d8f-9a42-2362fbca3277')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ae41625f-be8a-43c1-8310-6c09612181a8')
            column(name: 'df_usage_batch_uid', value: '7083ae7d-0079-41fb-aab1-8ae2bd5db8fe')
            column(name: "df_scenario_uid", value: "ca163655-8978-4a45-8fe3-c3b5572c6879")
            column(name: 'wr_wrk_inst', value: '122267677')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '1')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'service_fee', value: '0.18500')
            column(name: 'service_fee_amount', value: '37.00')
            column(name: 'net_amount', value: '163.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ae41625f-be8a-43c1-8310-6c09612181a8')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '200')
        }

        //should be exclude from the report
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'edb66e6b-a061-4ef8-bc30-28f00a2edaeb')
            column(name: 'name', value: 'NTS Batch for Nts undistributed liabilities report 3')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e3a18e26-dc58-416d-a707-5a7bc843d6a4')
            column(name: 'name', value: 'Test NTS scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":200.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter") {
            column(name: "df_scenario_usage_filter_uid", value: "230c34cf-c109-4ac2-8c4c-153d9dbb591c")
            column(name: "df_scenario_uid", value: "e3a18e26-dc58-416d-a707-5a7bc843d6a4")
            column(name: "product_family", value: "NTS")
            column(name: "status_ind", value: "ELIGIBLE")
        }

        insert(schemaName: dbAppsSchema, tableName: "df_scenario_usage_filter_to_usage_batches_ids_map") {
            column(name: "df_scenario_usage_filter_uid", value: "230c34cf-c109-4ac2-8c4c-153d9dbb591c")
            column(name: "df_usage_batch_uid", value: "edb66e6b-a061-4ef8-bc30-28f00a2edaeb")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '43ba1d77-b00f-4ae6-845a-332530ddc2eb')
            column(name: 'df_usage_batch_uid', value: 'edb66e6b-a061-4ef8-bc30-28f00a2edaeb')
            column(name: "df_scenario_uid", value: "e3a18e26-dc58-416d-a707-5a7bc843d6a4")
            column(name: 'wr_wrk_inst', value: '122267677')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '1')
            column(name: 'gross_amount', value: '900.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'service_fee_amount', value: '288.00')
            column(name: 'net_amount', value: '612.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '43ba1d77-b00f-4ae6-845a-332530ddc2eb')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '30a9a53f-db64-4af3-9616-1e40edcef489')
            column(name: 'wr_wrk_inst', value: '642267671')
            column(name: 'classification', value: 'STM')
        }
    }

    changeSet(id: '2020-07-30-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testWriteNtsServiceFeeTrueUpCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '78fa8e33-6194-4cf6-85a6-ad72f811b8af')
            column(name: 'name', value: 'NTS batch 8')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 900, "non_stm_amount": 1100, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56444713-d0d2-4c7f-ad50-f4af99b114e5')
            column(name: 'name', value: 'NTS batch 9')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 500, "non_stm_amount": 700, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '8b62a77c-f55f-4e20-a8bc-0fa4ba611e74')
            column(name: 'name', value: 'NTS batch 10')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 300, "non_stm_amount": 500, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '66171a5c-7a8b-4eed-abc9-82756a32ba0e')
            column(name: 'name', value: 'NTS batch 11')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 100, "non_stm_amount": 0, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        // Service Fee True-up report for this scenario will contain one row; scenario with fund pool
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'a537da01-b211-4b81-b2b9-7dc0c791811a')
            column(name: 'name', value: 'Scenario for empty NTS Service Fee True-up report 1')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario for NTS Service Fee True-up report')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2018-05-15 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 100.00, "pre_service_fee_amount": 200.00,' +
                    '"post_service_fee_amount": 100.00, "pre_service_fee_fund_uid": "815d6736-a34e-4fc8-96c3-662a114fa7f2"}')
        }

        // Service Fee True-up report for this scenario will contain one row; scenario without fund pool
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'dc6df4bd-7059-4975-8898-78b4a50d30b0')
            column(name: 'name', value: 'Scenario for empty NTS Service Fee True-up report')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario for NTS Service Fee True-up report 2')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2018-05-15 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 100.00, "pre_service_fee_amount": 200.00,' +
                    '"post_service_fee_amount": 100.00, "pre_service_fee_fund_uid": null}')
        }

        // Service Fee True-up report for this scenario will be empty
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1871799a-157a-4fb2-82ab-9092bb3b6395')
            column(name: 'name', value: 'Scenario for empty NTS Service Fee True-up report')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'description', value: 'Scenario for empty NTS Service Fee True-up report')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2018-05-15 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 100.00, "pre_service_fee_amount": 0.00,' +
                    '"post_service_fee_amount": 0.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '815d6736-a34e-4fc8-96c3-662a114fa7f2')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'NTS Fund Pool 4')
            column(name: 'total_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'bc304214-12ca-478f-821a-b21e5acd8076')
            column(name: 'df_scenario_uid', value: 'a537da01-b211-4b81-b2b9-7dc0c791811a')
            column(name: 'wr_wrk_inst', value: '151811999')
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: '1080.00')
            column(name: 'net_amount', value: '744.00')
            column(name: 'service_fee_amount', value: '336.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'bc304214-12ca-478f-821a-b21e5acd8076')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '119c97c4-34bd-4c64-99a9-925211ab3b8b')
            column(name: 'df_scenario_uid', value: 'a537da01-b211-4b81-b2b9-7dc0c791811a')
            column(name: 'wr_wrk_inst', value: '151811999')
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: '1080.00')
            column(name: 'net_amount', value: '744.00')
            column(name: 'service_fee_amount', value: '336.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '119c97c4-34bd-4c64-99a9-925211ab3b8b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'df5df8fa-abb2-497f-9cdd-a55cb0772748')
            column(name: 'df_scenario_uid', value: 'a537da01-b211-4b81-b2b9-7dc0c791811a')
            column(name: 'wr_wrk_inst', value: '151811999')
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: '1000000001')
            column(name: 'payee_account_number', value: '1000000001')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: '1440.00')
            column(name: 'net_amount', value: '1216.00')
            column(name: 'service_fee_amount', value: '224.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'df5df8fa-abb2-497f-9cdd-a55cb0772748')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '782f7828-abd8-40c1-9783-b2e7f12b5880')
            column(name: 'df_scenario_uid', value: 'dc6df4bd-7059-4975-8898-78b4a50d30b0')
            column(name: 'wr_wrk_inst', value: '151811999')
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: '550.00')
            column(name: 'net_amount', value: '390.00')
            column(name: 'service_fee_amount', value: '160.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '782f7828-abd8-40c1-9783-b2e7f12b5880')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ec3d55f3-8654-4e72-9122-8994f581a344')
            column(name: 'df_scenario_uid', value: 'dc6df4bd-7059-4975-8898-78b4a50d30b0')
            column(name: 'wr_wrk_inst', value: '151811999')
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: '550.00')
            column(name: 'net_amount', value: '470.00')
            column(name: 'service_fee_amount', value: '80.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ec3d55f3-8654-4e72-9122-8994f581a344')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '21c06a83-9cea-43ff-8773-e6bd4090f40b')
            column(name: 'df_scenario_uid', value: '1871799a-157a-4fb2-82ab-9092bb3b6395')
            column(name: 'wr_wrk_inst', value: '151811999')
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '68.00')
            column(name: 'service_fee_amount', value: '32.00')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-11-03')
            column(name: 'ccc_event_id', value: '9875643')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2017-11-03')
            column(name: 'lm_detail_id', value: '36799c8c-fbfc-4aa8-a9ce-d49c5b054214')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '21c06a83-9cea-43ff-8773-e6bd4090f40b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'ce7026a1-5cc2-4c62-98e1-e8d0cbb4ea62')
            column(name: 'df_scenario_uid', value: 'a537da01-b211-4b81-b2b9-7dc0c791811a')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'ce7026a1-5cc2-4c62-98e1-e8d0cbb4ea62')
            column(name: 'df_usage_batch_uid', value: '78fa8e33-6194-4cf6-85a6-ad72f811b8af')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'ce7026a1-5cc2-4c62-98e1-e8d0cbb4ea62')
            column(name: 'df_usage_batch_uid', value: '56444713-d0d2-4c7f-ad50-f4af99b114e5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'a4fee0ac-effc-436f-a884-c7ca61badf50')
            column(name: 'df_scenario_uid', value: 'dc6df4bd-7059-4975-8898-78b4a50d30b0')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'a4fee0ac-effc-436f-a884-c7ca61badf50')
            column(name: 'df_usage_batch_uid', value: '8b62a77c-f55f-4e20-a8bc-0fa4ba611e74')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'c61c31b8-e216-4740-a77a-06bc880cbac7')
            column(name: 'df_scenario_uid', value: '1871799a-157a-4fb2-82ab-9092bb3b6395')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'c61c31b8-e216-4740-a77a-06bc880cbac7')
            column(name: 'df_usage_batch_uid', value: '66171a5c-7a8b-4eed-abc9-82756a32ba0e')
        }

        rollback ""
    }

    changeSet(id: '2020-09-23-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("Insert test data for testWriteSalUsagesCsvReport")

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '07811891-eb9d-405b-a7ff-2264b83a77eb')
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'name', value: 'Access Copyright, The Canadian Copyright Agency')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'da616e09-ca76-4815-b178-637abf32a76e')
            column(name: 'name', value: 'SAL usage batch for export')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2015')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '207d92c3-b773-4754-a1cc-9f729f8b87e3')
            column(name: 'df_usage_batch_uid', value: 'da616e09-ca76-4815-b178-637abf32a76e')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'comment', value: 'comment')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '207d92c3-b773-4754-a1cc-9f729f8b87e3')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1101001IB2361')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8241d1b7-799b-4130-923b-76376723668a')
            column(name: 'df_usage_batch_uid', value: 'da616e09-ca76-4815-b178-637abf32a76e')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '1000011450')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'comment', value: 'comment')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '8241d1b7-799b-4130-923b-76376723668a')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '11')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'assessment_name', value: 'FY16 Smarter Balanaced ELA')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1101024IB2192')
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 762)
        }
    }

    changeSet(id: '2020-10-14-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteSalScenarioUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3680dbbf-e360-4658-b262-88e25652fa4e')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 1')
            column(name: 'total_amount', value: '1000.00')
            column(name: 'sal_fields', value: '{"date_received": "12/24/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 5, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 20.01, "grade_K_5_gross_amount": 653.3, "grade_6_8_gross_amount": 326.66, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.02000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0dbfa399-5c7d-487e-8ea1-cb38cbd15cef')
            column(name: 'name', value: 'SAL Usage Batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'd79c1cef-b764-49ca-ab54-812d84cca548')
            column(name: 'name', value: 'SAL Scenario 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "3680dbbf-e360-4658-b262-88e25652fa4e"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd8daeed3-e4ee-4b09-b6ec-ef12a12bcd3d')
            column(name: 'df_usage_batch_uid', value: '0dbfa399-5c7d-487e-8ea1-cb38cbd15cef')
            column(name: 'df_scenario_uid', value: 'd79c1cef-b764-49ca-ab54-812d84cca548')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'payee_account_number', value: '2000017004')
            column(name: "system_title", value: 'Castanea')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '75.00')
            column(name: 'service_fee_amount', value: '25.00')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'd8daeed3-e4ee-4b09-b6ec-ef12a12bcd3d')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1101001IB2361')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '88a685d3-ee1b-4a22-aab9-1ae5df7c84cb')
            column(name: 'df_usage_batch_uid', value: '0dbfa399-5c7d-487e-8ea1-cb38cbd15cef')
            column(name: 'df_scenario_uid', value: 'd79c1cef-b764-49ca-ab54-812d84cca548')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'payee_account_number', value: '2000017004')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '75.00')
            column(name: 'service_fee_amount', value: '25.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'comment', value: 'comment')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '88a685d3-ee1b-4a22-aab9-1ae5df7c84cb')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '11')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'assessment_name', value: 'FY16 Smarter Balanaced ELA')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1101024IB2192')
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 762)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'b36b6c4e-f4e6-40a1-bee2-c572c5ad8ca4')
            column(name: 'df_scenario_uid', value: 'd79c1cef-b764-49ca-ab54-812d84cca548')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'b36b6c4e-f4e6-40a1-bee2-c572c5ad8ca4')
            column(name: 'df_usage_batch_uid', value: '0dbfa399-5c7d-487e-8ea1-cb38cbd15cef')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '768a18ff-73d3-4eb5-8c8c-1cf0139c7818')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 2')
            column(name: 'total_amount', value: '1000.00')
            column(name: 'sal_fields', value: '{"date_received": "12/24/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 5, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 20.01, "grade_K_5_gross_amount": 653.3, "grade_6_8_gross_amount": 326.66, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.02000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '043ae316-6d55-45c3-92af-d7069530ea9e')
            column(name: 'name', value: 'SAL Usage Batch 2')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5ba85c68-1f19-45c0-a6f3-a5c0e7737636')
            column(name: 'name', value: 'SAL Scenario 2')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "043ae316-6d55-45c3-92af-d7069530ea9e"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '7994d23e-4a74-4999-8f65-ee1d9f774ab2')
            column(name: 'df_usage_batch_uid', value: '043ae316-6d55-45c3-92af-d7069530ea9e')
            column(name: 'df_scenario_uid', value: '5ba85c68-1f19-45c0-a6f3-a5c0e7737636')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'payee_account_number', value: '2000017004')
            column(name: "system_title", value: 'Castanea')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '75.00')
            column(name: 'service_fee_amount', value: '25.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'check_number', value: '578000')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '3356214')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '5375bee0-24f0-4e6c-a808-c62814dd93ae')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '7994d23e-4a74-4999-8f65-ee1d9f774ab2')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1101001IB2361')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'b6b37527-b235-424c-bb21-e4affdb21284')
            column(name: 'df_usage_batch_uid', value: '043ae316-6d55-45c3-92af-d7069530ea9e')
            column(name: 'df_scenario_uid', value: '5ba85c68-1f19-45c0-a6f3-a5c0e7737636')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'payee_account_number', value: '2000017004')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '75.00')
            column(name: 'service_fee_amount', value: '25.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'check_number', value: '578000')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '9873214')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '33f96faf-167f-4d16-9a6e-fc5188730ca2')
            column(name: 'comment', value: 'comment')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'b6b37527-b235-424c-bb21-e4affdb21284')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '11')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'assessment_name', value: 'FY16 Smarter Balanaced ELA')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1101024IB2192')
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 762)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'a856dfc9-3e38-4941-86e3-b1183f6a27f8')
            column(name: 'df_scenario_uid', value: '5ba85c68-1f19-45c0-a6f3-a5c0e7737636')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'a856dfc9-3e38-4941-86e3-b1183f6a27f8')
            column(name: 'df_usage_batch_uid', value: '043ae316-6d55-45c3-92af-d7069530ea9e')
        }

        rollback ""
    }

    changeSet(id: '2020-10-16-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for testWriteLiabilitiesByRhCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'e6cb4b13-30cf-4629-991b-4095fcaaaae6')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL liabilities by Rightsholder report Fund Pool 1')
            column(name: 'total_amount', value: '2000.00')
            column(name: 'sal_fields', value: '{"date_received": "10/08/2020", "assessment_name": "FY2020 AIR", ' +
                    '"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", ' +
                    '"grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 1000.00, "item_bank_gross_amount": 100.00, ' +
                    '"grade_K_5_gross_amount": 900.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.10000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c3d509ad-4790-464c-881c-c16022f4da72')
            column(name: 'name', value: 'SAL Liabilities by Rightsholder report Usage Batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5af9a0e6-4156-416d-b95b-f1aeeefa9545')
            column(name: 'name', value: 'SAL Liabilities by Rightsholder report Scenario 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "e6cb4b13-30cf-4629-991b-4095fcaaaae6"}')
        }

        // Scenario 1, IB usage 1
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '015207e3-568d-4f3e-9845-ef1786fac398')
            column(name: 'df_usage_batch_uid', value: 'c3d509ad-4790-464c-881c-c16022f4da72')
            column(name: 'df_scenario_uid', value: '5af9a0e6-4156-416d-b95b-f1aeeefa9545')
            column(name: 'wr_wrk_inst', value: '243204754')
            column(name: 'rh_account_number', value: '2000017010')
            column(name: 'payee_account_number', value: '2000017010')
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'gross_amount', value: '50.0000000000')
            column(name: 'net_amount', value: '37.5000000000')
            column(name: 'service_fee_amount', value: '12.5000000000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '015207e3-568d-4f3e-9845-ef1786fac398')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: 'K')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY20 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2161')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2020-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2019-2020')
        }

        // Scenario 1, UD usage 1
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '80a517a0-9a7a-4361-8840-e59d13d6e8da')
            column(name: 'df_usage_batch_uid', value: 'c3d509ad-4790-464c-881c-c16022f4da72')
            column(name: 'df_scenario_uid', value: '5af9a0e6-4156-416d-b95b-f1aeeefa9545')
            column(name: 'wr_wrk_inst', value: '243204754')
            column(name: 'rh_account_number', value: '2000017010')
            column(name: 'payee_account_number', value: '2000017010')
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'gross_amount', value: '506.2500000000')
            column(name: 'net_amount', value: '379.6875000000')
            column(name: 'service_fee_amount', value: '126.5625000000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '80a517a0-9a7a-4361-8840-e59d13d6e8da')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: 'K')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY20 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2161')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2020-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'coverage_year', value: '2019-2020')
            column(name: 'scored_assessment_date', value: '2020-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 5)
        }

        // Scenario 1, IB usage 2
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'aadb7912-2c56-49a9-b5bd-2364dd71c646')
            column(name: 'df_usage_batch_uid', value: 'c3d509ad-4790-464c-881c-c16022f4da72')
            column(name: 'df_scenario_uid', value: '5af9a0e6-4156-416d-b95b-f1aeeefa9545')
            column(name: 'wr_wrk_inst', value: '373204754')
            column(name: 'rh_account_number', value: '2000017010')
            column(name: 'payee_account_number', value: '2000017010')
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: "standard_number", value: '8999639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'gross_amount', value: '50.0000000000')
            column(name: 'net_amount', value: '37.5000000000')
            column(name: 'service_fee_amount', value: '12.5000000000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'aadb7912-2c56-49a9-b5bd-2364dd71c646')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY20 AIR')
            column(name: 'reported_work_portion_id', value: '4701001IB2262')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2020-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'IMAGE')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'coverage_year', value: '2019-2020')
        }

        // Scenario 1, UD usage 2
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '98eedcf5-cd6d-46ee-9d70-912db0bf2997')
            column(name: 'df_usage_batch_uid', value: 'c3d509ad-4790-464c-881c-c16022f4da72')
            column(name: 'df_scenario_uid', value: '5af9a0e6-4156-416d-b95b-f1aeeefa9545')
            column(name: 'wr_wrk_inst', value: '373204754')
            column(name: 'rh_account_number', value: '2000017010')
            column(name: 'payee_account_number', value: '2000017010')
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: "standard_number", value: '8999639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'gross_amount', value: '393.7500000000')
            column(name: 'net_amount', value: '295.3125000000')
            column(name: 'service_fee_amount', value: '98.4375000000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '98eedcf5-cd6d-46ee-9d70-912db0bf2997')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY20 AIR')
            column(name: 'reported_work_portion_id', value: '4701001IB2262')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'coverage_year', value: '2018-2019')
            column(name: 'scored_assessment_date', value: '2019-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 4)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '96a424f2-302e-42e5-850e-2f573fb6519b')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Liabilities by Rightsholder report Fund Pool 2')
            column(name: 'total_amount', value: '2000.00')
            column(name: 'sal_fields', value: '{"date_received": "10/08/2020", "assessment_name": "FY2020 AIR", ' +
                    '"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", ' +
                    '"grade_K_5_number_of_students": 0, "grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 10, ' +
                    '"gross_amount": 1000.00, "item_bank_gross_amount": 100.00, ' +
                    '"grade_K_5_gross_amount": 0.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 900.00, ' +
                    '"item_bank_split_percent": 0.10000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'fe6ca6cd-ff27-4725-bcb7-5d38c69b389e')
            column(name: 'name', value: 'SAL Liabilities by Rightsholder report Usage Batch 2')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'ebe447c1-5314-4075-9781-efc0887b6ffc')
            column(name: 'name', value: 'SAL Liabilities by Rightsholder report Scenario 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "96a424f2-302e-42e5-850e-2f573fb6519b"}')
        }

        // Scenario 2, IB usage 1
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9682e0f2-d0ac-4c36-94be-736a44d3292c')
            column(name: 'df_usage_batch_uid', value: 'fe6ca6cd-ff27-4725-bcb7-5d38c69b389e')
            column(name: 'df_scenario_uid', value: 'ebe447c1-5314-4075-9781-efc0887b6ffc')
            column(name: 'wr_wrk_inst', value: '112904754')
            column(name: 'rh_account_number', value: '5000581900')
            column(name: 'payee_account_number', value: '5000581900')
            column(name: 'work_title', value: 'The University of State Michigan')
            column(name: 'system_title', value: 'The University of State Michigan')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'gross_amount', value: '50.0000000000')
            column(name: 'net_amount', value: '37.5000000000')
            column(name: 'service_fee_amount', value: '12.5000000000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '9682e0f2-d0ac-4c36-94be-736a44d3292c')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '9')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY20 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2161')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2020-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 19, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        // Scenario 2, UD usage 1
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4feed9c9-3051-4f72-bf7d-6819d1cca471')
            column(name: 'df_usage_batch_uid', value: 'fe6ca6cd-ff27-4725-bcb7-5d38c69b389e')
            column(name: 'df_scenario_uid', value: 'ebe447c1-5314-4075-9781-efc0887b6ffc')
            column(name: 'wr_wrk_inst', value: '112904754')
            column(name: 'rh_account_number', value: '5000581900')
            column(name: 'payee_account_number', value: '5000581900')
            column(name: 'work_title', value: 'The University of State Michigan')
            column(name: 'system_title', value: 'The University of State Michigan')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'gross_amount', value: '506.2500000000')
            column(name: 'net_amount', value: '379.6875000000')
            column(name: 'service_fee_amount', value: '126.5625000000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '4feed9c9-3051-4f72-bf7d-6819d1cca471')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '9')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2161')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2020-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 19, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'coverage_year', value: '2019-2020')
            column(name: 'scored_assessment_date', value: '2020-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 5)
        }

        // Scenario 2, IB usage 2
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2ee9901a-eb96-42e3-8408-714ae295b736')
            column(name: 'df_usage_batch_uid', value: 'fe6ca6cd-ff27-4725-bcb7-5d38c69b389e')
            column(name: 'df_scenario_uid', value: 'ebe447c1-5314-4075-9781-efc0887b6ffc')
            column(name: 'wr_wrk_inst', value: '983204714')
            column(name: 'rh_account_number', value: '1000000001')
            column(name: 'payee_account_number', value: '1000000001')
            column(name: 'work_title', value: 'Burn Your Stuff')
            column(name: 'system_title', value: 'Burn Your Stuff')
            column(name: "standard_number", value: '7779639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'gross_amount', value: '50.0000000000')
            column(name: 'net_amount', value: '37.5000000000')
            column(name: 'service_fee_amount', value: '12.5000000000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '2ee9901a-eb96-42e3-8408-714ae295b736')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '10')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY20 AIR')
            column(name: 'reported_work_portion_id', value: '4701001IB2262')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2020-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 20, Issue 2')
            column(name: 'reported_media_type', value: 'IMAGE')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'coverage_year', value: '2019-2020')
        }

        // Scenario 2, UD usage 2
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'aedb2ff8-6e96-46c7-89cd-5adb4cb85478')
            column(name: 'df_usage_batch_uid', value: 'fe6ca6cd-ff27-4725-bcb7-5d38c69b389e')
            column(name: 'df_scenario_uid', value: 'ebe447c1-5314-4075-9781-efc0887b6ffc')
            column(name: 'wr_wrk_inst', value: '983204714')
            column(name: 'rh_account_number', value: '1000000001')
            column(name: 'payee_account_number', value: '1000000001')
            column(name: 'work_title', value: 'Burn Your Stuff')
            column(name: 'system_title', value: 'Burn Your Stuff')
            column(name: "standard_number", value: '7779639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'gross_amount', value: '393.7500000000')
            column(name: 'net_amount', value: '295.3125000000')
            column(name: 'service_fee_amount', value: '98.4375000000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'aedb2ff8-6e96-46c7-89cd-5adb4cb85478')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '10')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'assessment_name', value: 'FY20 AIR')
            column(name: 'reported_work_portion_id', value: '4701001IB2262')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2020-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 20, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'coverage_year', value: '2019-2020')
            column(name: 'scored_assessment_date', value: '2019-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 4)
        }

        rollback ""
    }

    changeSet(id: '2020-10-15-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for testWriteSalLiabilitiesSummaryByRhAndWorkReportCsvReport")

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a408ef06-05ea-4477-a5a6-ad448fd49bc7')
            column(name: 'rh_account_number', value: '1000028511')
            column(name: 'name', value: 'Greenleaf Publishing')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'bc7107a8-a098-47f9-b28c-d7e8bb8704f2')
            column(name: 'rh_account_number', value: '2000024497')
            column(name: 'name', value: 'Alexander Stille')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '9c1e3854-9f02-42e8-a8f5-c2cbfd0050c0')
            column(name: 'rh_account_number', value: '7000452842')
            column(name: 'name', value: 'Libertas Academica')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '462111b6-5d30-4a43-a35b-14796d34d847')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Liabilities Summary by Rightsholder and Work report Fund Pool 1')
            column(name: 'total_amount', value: '1000.00')
            column(name: 'sal_fields', value: '{"date_received": "10/08/2020", "assessment_name": "FY2020 EOC", ' +
                    '"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", ' +
                    '"grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 1000.00, "item_bank_gross_amount": 200.00, ' +
                    '"grade_K_5_gross_amount": 800.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '85df79f3-7e3f-4d74-9931-9aa513195815')
            column(name: 'name', value: 'SAL Liabilities Summary by Rightsholder and Work report Usage Batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'c0b30809-4a38-46cc-a0dc-641924d1fc43')
            column(name: 'name', value: 'SAL Liabilities Summary by Rightsholder and Work report Scenario 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "462111b6-5d30-4a43-a35b-14796d34d847"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e8daeed3-e4ee-4b09-b6ec-ef12a12bcd3d')
            column(name: 'df_usage_batch_uid', value: '85df79f3-7e3f-4d74-9931-9aa513195815')
            column(name: 'df_scenario_uid', value: 'c0b30809-4a38-46cc-a0dc-641924d1fc43')
            column(name: 'wr_wrk_inst', value: '122973671')
            column(name: 'rh_account_number', value: '7000452842')
            column(name: 'payee_account_number', value: '2000024497')
            column(name: 'work_title', value: 'Statements')
            column(name: 'system_title', value: 'Statements')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'e8daeed3-e4ee-4b09-b6ec-ef12a12bcd3d')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Statements')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '71d1a5d2-ba9f-48b7-9b09-0516840a07ee')
            column(name: 'df_usage_batch_uid', value: '85df79f3-7e3f-4d74-9931-9aa513195815')
            column(name: 'df_scenario_uid', value: 'c0b30809-4a38-46cc-a0dc-641924d1fc43')
            column(name: 'wr_wrk_inst', value: '122973671')
            column(name: 'rh_account_number', value: '7000452842')
            column(name: 'payee_account_number', value: '1000017454')
            column(name: 'work_title', value: 'Statements')
            column(name: 'system_title', value: 'Statements')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '800.00')
            column(name: 'net_amount', value: '600.00')
            column(name: 'service_fee_amount', value: '200.00')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '71d1a5d2-ba9f-48b7-9b09-0516840a07ee')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Statements')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 42)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'cbe96e80-1aa8-41e3-b57b-b8ee67a0f673')
            column(name: 'df_scenario_uid', value: 'c0b30809-4a38-46cc-a0dc-641924d1fc43')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'cbe96e80-1aa8-41e3-b57b-b8ee67a0f673')
            column(name: 'df_usage_batch_uid', value: '85df79f3-7e3f-4d74-9931-9aa513195815')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '93ad09c8-1ffa-4609-9917-e12aeee885a3')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Liabilities Summary by Rightsholder and Work report Fund Pool 2')
            column(name: 'total_amount', value: '2000.00')
            column(name: 'sal_fields', value: '{"date_received": "10/08/2020", "assessment_name": "FY2020 EOC", ' +
                    '"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", ' +
                    '"grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 2000.00, "item_bank_gross_amount": 400.00, ' +
                    '"grade_K_5_gross_amount": 1600.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9bd219c1-caae-4542-84e3-f9f4dba0d034')
            column(name: 'name', value: 'SAL Liabilities Summary by Rightsholder and Work report Usage Batch 2')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '0a3b533d-d3ed-48dc-b256-f4f9f6527d91')
            column(name: 'name', value: 'SAL Liabilities Summary by Rightsholder and Work report Scenario 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "93ad09c8-1ffa-4609-9917-e12aeee885a3"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f61890bc-318c-4042-9bd4-c12c4e2a2b0f')
            column(name: 'df_usage_batch_uid', value: '9bd219c1-caae-4542-84e3-f9f4dba0d034')
            column(name: 'df_scenario_uid', value: '0a3b533d-d3ed-48dc-b256-f4f9f6527d91')
            column(name: 'wr_wrk_inst', value: '180047973')
            column(name: 'rh_account_number', value: '1000028511')
            column(name: 'payee_account_number', value: '2000024497')
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '400.00')
            column(name: 'net_amount', value: '300.00')
            column(name: 'service_fee_amount', value: '100.00')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'f61890bc-318c-4042-9bd4-c12c4e2a2b0f')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '81fbbcc4-9705-4d5e-9824-79aa20f22dec')
            column(name: 'df_usage_batch_uid', value: '9bd219c1-caae-4542-84e3-f9f4dba0d034')
            column(name: 'df_scenario_uid', value: '0a3b533d-d3ed-48dc-b256-f4f9f6527d91')
            column(name: 'wr_wrk_inst', value: '180047973')
            column(name: 'rh_account_number', value: '1000028511')
            column(name: 'payee_account_number', value: '2000024497')
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '1600.00')
            column(name: 'net_amount', value: '1200.00')
            column(name: 'service_fee_amount', value: '400.00')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '81fbbcc4-9705-4d5e-9824-79aa20f22dec')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 42)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '106bb37d-37dc-4b85-88a8-c7ffa506c011')
            column(name: 'df_scenario_uid', value: '0a3b533d-d3ed-48dc-b256-f4f9f6527d91')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '106bb37d-37dc-4b85-88a8-c7ffa506c011')
            column(name: 'df_usage_batch_uid', value: '9bd219c1-caae-4542-84e3-f9f4dba0d034')
        }

        rollback ""
    }

    changeSet(id: '2020-11-05-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("Insert test data for testSalUndistributedLiabilitiesCsvReport")

        //Should be included into report as it isn't associated with any scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '5f4df57b-b318-4a9d-b00a-82ab04ed9331')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool Name 1 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'total_amount', value: '100.00')
            column(name: 'sal_fields', value: '{"service_fee": 0.25000, "gross_amount": 100.00, ' +
                    '"date_received": "10/15/2015", "licensee_name": "VG Wort, Verwertungsgesellschaft WORT", ' +
                    '"assessment_name": "Assessment", "grade_6_8_gross_amount": 0.00, "grade_K_5_gross_amount": 90.00, ' +
                    '"item_bank_gross_amount": 10.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.10000, "licensee_account_number": 2000017002, ' +
                    '"grade_6_8_number_of_students": 0, "grade_K_5_number_of_students": 1, ' +
                    '"grade_9_12_number_of_students": 0}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '10992462-4b8d-4616-bae8-2815dac6e9cd')
            column(name: 'name', value: 'SAL Usage Batch 1 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: "apps", tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cef3a3f5-25ea-4338-9344-661ee09c42f3')
            column(name: 'df_usage_batch_uid', value: '10992462-4b8d-4616-bae8-2815dac6e9cd')
            column(name: 'wr_wrk_inst', value: '180047973')
            column(name: 'rh_account_number', value: '1000028511')
            column(name: 'payee_account_number', value: '2000024497')
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '0.00')
            column(name: 'net_amount', value: '0.00')
            column(name: 'service_fee_amount', value: '0.00')
        }

        insert(schemaName: "apps", tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'cef3a3f5-25ea-4338-9344-661ee09c42f3')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: 'K')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        //Should be included into report as it is associated with IN_PROGRESS scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '5652ec3a-1817-4598-bd6c-26506949e0d8')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool Name 2 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'total_amount', value: '100.00')
            column(name: 'sal_fields', value: '{"service_fee": 0.25000, "gross_amount": 100.00, ' +
                    '"date_received": "05/20/2016", "licensee_name": "VG Wort, Verwertungsgesellschaft WORT", ' +
                    '"assessment_name": "Assessment", "grade_6_8_gross_amount": 0.00, "grade_K_5_gross_amount": 90.00, ' +
                    '"item_bank_gross_amount": 10.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.10000, "licensee_account_number": 2000017002, ' +
                    '"grade_6_8_number_of_students": 0, "grade_K_5_number_of_students": 1, ' +
                    '"grade_9_12_number_of_students": 0}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b309b1fa-d1f5-4278-bd9b-76e09b83c2ba')
            column(name: 'name', value: 'SAL Usage Batch 2 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '0e23d736-f921-4757-859a-cd73092f0e68')
            column(name: 'name', value: 'SAL Scenario 2 For testSalUndistributedLiabilitiesCsvReport')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "5652ec3a-1817-4598-bd6c-26506949e0d8"}')
            column(name: 'description', value: 'SAL Scenario Description 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9ed1cddf-6b95-44ed-a7a2-fa815a3f3023')
            column(name: 'df_usage_batch_uid', value: 'b309b1fa-d1f5-4278-bd9b-76e09b83c2ba')
            column(name: 'df_scenario_uid', value: '0e23d736-f921-4757-859a-cd73092f0e68')
            column(name: 'wr_wrk_inst', value: '180047973')
            column(name: 'rh_account_number', value: '1000028511')
            column(name: 'payee_account_number', value: '2000024497')
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '10.00')
            column(name: 'net_amount', value: '7.50')
            column(name: 'service_fee_amount', value: '2.50')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '9ed1cddf-6b95-44ed-a7a2-fa815a3f3023')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: 'K')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f1ef8c3d-3e89-4a6b-b30c-ba894a89695e')
            column(name: 'df_usage_batch_uid', value: 'b309b1fa-d1f5-4278-bd9b-76e09b83c2ba')
            column(name: 'df_scenario_uid', value: '0e23d736-f921-4757-859a-cd73092f0e68')
            column(name: 'wr_wrk_inst', value: '180047973')
            column(name: 'rh_account_number', value: '1000028511')
            column(name: 'payee_account_number', value: '2000024497')
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '90.00')
            column(name: 'net_amount', value: '67.50')
            column(name: 'service_fee_amount', value: '22.50')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'f1ef8c3d-3e89-4a6b-b30c-ba894a89695e')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 42)
        }

        //Should be included into report as it is associated with SUBMITTED scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '1a2f421d-5ae7-43ab-af08-482de7324534')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool Name 3 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'total_amount', value: '100.00')
            column(name: 'sal_fields', value: '{"service_fee": 0.25000, "gross_amount": 100.00, ' +
                    '"date_received": "10/17/2017", "licensee_name": "VG Wort, Verwertungsgesellschaft WORT", ' +
                    '"assessment_name": "Assessment", "grade_6_8_gross_amount": 0.00, "grade_K_5_gross_amount": 90.00, ' +
                    '"item_bank_gross_amount": 10.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.10000, "licensee_account_number": 2000017002, ' +
                    '"grade_6_8_number_of_students": 0, "grade_K_5_number_of_students": 1, ' +
                    '"grade_9_12_number_of_students": 0}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '7d27be27-5bd8-480b-a30a-d3bedcc174f9')
            column(name: 'name', value: 'SAL Usage Batch 3 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e8cffe6c-0e10-43dc-8be4-8cb3852bc70d')
            column(name: 'name', value: 'SAL Scenario 3 For testSalUndistributedLiabilitiesCsvReport')
            column(name: 'status_ind', value: 'SUBMITTED')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "1a2f421d-5ae7-43ab-af08-482de7324534"}')
            column(name: 'description', value: 'SAL Scenario Description 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9b588758-991f-47d9-9f7b-4823cf082a3a')
            column(name: 'df_usage_batch_uid', value: '7d27be27-5bd8-480b-a30a-d3bedcc174f9')
            column(name: 'df_scenario_uid', value: 'e8cffe6c-0e10-43dc-8be4-8cb3852bc70d')
            column(name: 'wr_wrk_inst', value: '180047973')
            column(name: 'rh_account_number', value: '1000028511')
            column(name: 'payee_account_number', value: '2000024497')
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '10.00')
            column(name: 'net_amount', value: '7.50')
            column(name: 'service_fee_amount', value: '25.00')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '9b588758-991f-47d9-9f7b-4823cf082a3a')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: 'K')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd6427ab5-f9d2-4027-abac-963273db1379')
            column(name: 'df_usage_batch_uid', value: '7d27be27-5bd8-480b-a30a-d3bedcc174f9')
            column(name: 'df_scenario_uid', value: 'e8cffe6c-0e10-43dc-8be4-8cb3852bc70d')
            column(name: 'wr_wrk_inst', value: '180047973')
            column(name: 'rh_account_number', value: '1000028511')
            column(name: 'payee_account_number', value: '2000024497')
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '90.00')
            column(name: 'net_amount', value: '67.50')
            column(name: 'service_fee_amount', value: '2.50')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'd6427ab5-f9d2-4027-abac-963273db1379')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 42)
        }

        //Should be included into report as it is associated with APPROVED scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '44764d4d-04d6-47f0-8789-eec182fcf567')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool Name 4 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'total_amount', value: '100.00')
            column(name: 'sal_fields', value: '{"service_fee": 0.25000, "gross_amount": 100.00, ' +
                    '"date_received": "10/29/2018", "licensee_name": "VG Wort, Verwertungsgesellschaft WORT", ' +
                    '"assessment_name": "Assessment", "grade_6_8_gross_amount": 0.00, "grade_K_5_gross_amount": 90.00, ' +
                    '"item_bank_gross_amount": 10.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.10000, "licensee_account_number": 2000017002, ' +
                    '"grade_6_8_number_of_students": 0, "grade_K_5_number_of_students": 1, ' +
                    '"grade_9_12_number_of_students": 0}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '07bdd480-6e19-4a98-97e5-15f250704a84')
            column(name: 'name', value: 'SAL Usage Batch 4 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5e81da52-8834-4f51-bc63-ecc9327beaac')
            column(name: 'name', value: 'SAL Scenario 4 For testSalUndistributedLiabilitiesCsvReport')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "44764d4d-04d6-47f0-8789-eec182fcf567"}')
            column(name: 'description', value: 'SAL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8ac3ba17-c9dd-4ea4-bfe7-fe3d7287e2c0')
            column(name: 'df_usage_batch_uid', value: '07bdd480-6e19-4a98-97e5-15f250704a84')
            column(name: 'df_scenario_uid', value: '5e81da52-8834-4f51-bc63-ecc9327beaac')
            column(name: 'wr_wrk_inst', value: '180047973')
            column(name: 'rh_account_number', value: '1000028511')
            column(name: 'payee_account_number', value: '2000024497')
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '10.00')
            column(name: 'net_amount', value: '7.50')
            column(name: 'service_fee_amount', value: '2.50')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '8ac3ba17-c9dd-4ea4-bfe7-fe3d7287e2c0')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: 'K')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8fe00ba5-05e7-4b79-bee7-01974ef49bd9')
            column(name: 'df_usage_batch_uid', value: '07bdd480-6e19-4a98-97e5-15f250704a84')
            column(name: 'df_scenario_uid', value: '5e81da52-8834-4f51-bc63-ecc9327beaac')
            column(name: 'wr_wrk_inst', value: '180047973')
            column(name: 'rh_account_number', value: '1000028511')
            column(name: 'payee_account_number', value: '2000024497')
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '90.00')
            column(name: 'net_amount', value: '67.50')
            column(name: 'service_fee_amount', value: '22.50')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '8fe00ba5-05e7-4b79-bee7-01974ef49bd9')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 42)
        }

        //Shouldn't be included into report as it is associated with SENT_TO_LM scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'f732a0ce-b59e-4cfa-9a9e-9e341065e042')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool Name 5 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'total_amount', value: '200.00')
            column(name: 'sal_fields', value: '{"service_fee": 0.25000, "gross_amount": 200.00, ' +
                    '"date_received": "11/10/2019", "licensee_name": "VG Wort, Verwertungsgesellschaft WORT", ' +
                    '"assessment_name": "Assessment", "grade_6_8_gross_amount": 0.00, ' +
                    '"grade_K_5_gross_amount": 180.00, "item_bank_gross_amount": 20.00, ' +
                    '"grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.10000, ' +
                    '"licensee_account_number": 2000017002, "grade_6_8_number_of_students": 0, ' +
                    '"grade_K_5_number_of_students": 1, "grade_9_12_number_of_students": 0}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '44c8a8c1-48e6-4042-aec1-cf135f0039bf')
            column(name: 'name', value: 'SAL Usage Batch 5 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '68427933-e409-47fd-8b0f-80f1fd5ae17a')
            column(name: 'name', value: 'SAL Scenario 5 For testSalUndistributedLiabilitiesCsvReport')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "f732a0ce-b59e-4cfa-9a9e-9e341065e042"}')
            column(name: 'description', value: 'SAL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'dc396d4a-2c88-4067-acd0-6bcd693c198c')
            column(name: 'df_usage_batch_uid', value: '44c8a8c1-48e6-4042-aec1-cf135f0039bf')
            column(name: 'df_scenario_uid', value: '68427933-e409-47fd-8b0f-80f1fd5ae17a')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'payee_account_number', value: "1000002797")
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'SAL')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '20.00')
            column(name: 'net_amount', value: '15.00')
            column(name: 'service_fee_amount', value: '5.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'number_of_copies', value: '155')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'dc396d4a-2c88-4067-acd0-6bcd693c198c')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: 'K')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '5ba83250-c6fc-4673-a54f-262521dcc93c')
            column(name: 'df_usage_batch_uid', value: '44c8a8c1-48e6-4042-aec1-cf135f0039bf')
            column(name: 'df_scenario_uid', value: '68427933-e409-47fd-8b0f-80f1fd5ae17a')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'payee_account_number', value: "1000002797")
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'SAL')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '180.00')
            column(name: 'net_amount', value: '135.00')
            column(name: 'service_fee_amount', value: '45.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'number_of_copies', value: '155')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '5ba83250-c6fc-4673-a54f-262521dcc93c')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 42)
        }

        //Shouldn't be included into report as it is associated with ARCHIVED scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '0ac98ae1-13b6-427e-a2df-2d59a164d313')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool Name 6 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'total_amount', value: '200.00')
            column(name: 'sal_fields', value: '{"service_fee": 0.25000, "gross_amount": 200.00, ' +
                    '"date_received": "12/30/2020", "licensee_name": "VG Wort, Verwertungsgesellschaft WORT", ' +
                    '"assessment_name": "Assessment", "grade_6_8_gross_amount": 0.00, ' +
                    '"grade_K_5_gross_amount": 180.00, "item_bank_gross_amount": 20.00, ' +
                    '"grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.10000, ' +
                    '"licensee_account_number": 2000017002, "grade_6_8_number_of_students": 0, ' +
                    '"grade_K_5_number_of_students": 1, "grade_9_12_number_of_students": 0}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '5a21f256-9274-4395-ac95-094add1527ff')
            column(name: 'name', value: 'SAL Usage Batch 6 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '20245f10-a071-4ccd-8cfe-dd51e98079f8')
            column(name: 'name', value: 'SAL Scenario 6 For testSalUndistributedLiabilitiesCsvReport')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "0ac98ae1-13b6-427e-a2df-2d59a164d313"}')
            column(name: 'description', value: 'SAL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '1e83f493-e711-4ed6-8c1b-d5115edf8398')
            column(name: 'df_usage_batch_uid', value: '5a21f256-9274-4395-ac95-094add1527ff')
            column(name: 'df_scenario_uid', value: '20245f10-a071-4ccd-8cfe-dd51e98079f8')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'payee_account_number', value: "1000002797")
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '20.00')
            column(name: 'net_amount', value: '15.00')
            column(name: 'service_fee_amount', value: '5.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'number_of_copies', value: '155')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '1e83f493-e711-4ed6-8c1b-d5115edf8398')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: 'K')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '34184360-d47c-446f-9c2e-19d5b3401abe')
            column(name: 'df_usage_batch_uid', value: '5a21f256-9274-4395-ac95-094add1527ff')
            column(name: 'df_scenario_uid', value: '20245f10-a071-4ccd-8cfe-dd51e98079f8')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'payee_account_number', value: "1000002797")
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: '180.00')
            column(name: 'net_amount', value: '135.00')
            column(name: 'service_fee_amount', value: '45.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'number_of_copies', value: '155')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '34184360-d47c-446f-9c2e-19d5b3401abe')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 42)
        }

        rollback ""
    }

    changeSet(id: '2020-11-09-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteSalFundPoolsCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '1dbec643-2133-4839-9cf4-60dcfd04cc59')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pools for Report 1')
            column(name: 'total_amount', value: '2000.00')
            column(name: 'sal_fields', value: '{"date_received": "10/08/2020", "assessment_name": "FY2020 AIR", ' +
                    '"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", ' +
                    '"grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 1000.00, "item_bank_gross_amount": 100.00, ' +
                    '"grade_K_5_gross_amount": 900.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.10000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '8b805b1a-e855-492a-b3a8-f14ec6598fa1')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pools for Report 2')
            column(name: 'total_amount', value: '5000.00')
            column(name: 'sal_fields', value: '{"date_received": "12/31/2018", "assessment_name": "FY1990 COG", ' +
                    '"licensee_account_number": 7001293454, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, ' +
                    '"grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, "gross_amount": 5000.00, ' +
                    '"item_bank_gross_amount": 250.00, "grade_K_5_gross_amount": 4750.00, "grade_6_8_gross_amount": 0.00, ' +
                    '"grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.50000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '8deedf18-7dbc-4521-b276-817de65dc220')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pools for Report 3')
            column(name: 'total_amount', value: '1000.00')
            column(name: 'sal_fields', value: '{"date_received": "12/31/2018", "assessment_name": "FY1990 COG", ' +
                    '"licensee_account_number": 1000003007, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, ' +
                    '"grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, ' +
                    '"item_bank_gross_amount": 100.00, "grade_K_5_gross_amount": 900.0, "grade_6_8_gross_amount": 0.00, ' +
                    '"grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.10000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '03d82f8e-2ae2-412a-9ba2-d127043b88a4')
            column(name: 'name', value: 'SAL Usage Batch 6 for testWriteSalFundPoolsCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5a5d2e4d-bc8e-486d-b635-45801dbb8a47')
            column(name: 'name', value: 'SAL Scenario 6 For testWriteSalFundPoolsCsvReport')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "8deedf18-7dbc-4521-b276-817de65dc220"}')
            column(name: 'description', value: 'SAL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cd868c8b-851c-4e90-b539-ef73bdadf0be')
            column(name: 'df_usage_batch_uid', value: '03d82f8e-2ae2-412a-9ba2-d127043b88a4')
            column(name: 'df_scenario_uid', value: '5a5d2e4d-bc8e-486d-b635-45801dbb8a47')
            column(name: 'wr_wrk_inst', value: '180047973')
            column(name: 'rh_account_number', value: '1000028511')
            column(name: 'payee_account_number', value: '2000024497')
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '75.00')
            column(name: 'service_fee_amount', value: '25.00')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'cd868c8b-851c-4e90-b539-ef73bdadf0be')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1235481IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ca221557-896e-4fab-846d-ec140e67134d')
            column(name: 'df_usage_batch_uid', value: '03d82f8e-2ae2-412a-9ba2-d127043b88a4')
            column(name: 'df_scenario_uid', value: '5a5d2e4d-bc8e-486d-b635-45801dbb8a47')
            column(name: 'wr_wrk_inst', value: '180047973')
            column(name: 'rh_account_number', value: '1000028511')
            column(name: 'payee_account_number', value: '2000024497')
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '75.00')
            column(name: 'service_fee_amount', value: '25.00')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'ca221557-896e-4fab-846d-ec140e67134d')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1235481IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 42)
        }

        rollback ""
    }

    changeSet(id: '2020-11-26-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for testWriteSalHistoricalItemBankDetailsCsvReport")

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '7d81d977-a96b-49d2-b08b-fb8089aed030')
            column(name: 'rh_account_number', value: '2000017128')
            column(name: 'name', value: 'Academia Sinica')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '867d42c1-f55c-47e4-91e9-973aae806fac')
            column(name: 'rh_account_number', value: '1000017527')
            column(name: 'name', value: 'Sphere Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '4d8fe2f4-29d3-4f01-ac2b-ede81cd7ae5d')
            column(name: 'rh_account_number', value: '7000256354')
            column(name: 'name', value: 'TransUnion LLC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '55df79f3-7e3f-4d74-9931-9aa513195816')
            column(name: 'name', value: 'SAL Historical Item Bank Details report Usage Batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'sal_fields', value: '{"licensee_account_number": 2000017003, "licensee_name": "ProLitteris"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '38daeed3-e4ee-4b09-b6ec-ef12a12bcd34')
            column(name: 'df_usage_batch_uid', value: '55df79f3-7e3f-4d74-9931-9aa513195816')
            column(name: 'wr_wrk_inst', value: '122973671')
            column(name: 'rh_account_number', value: '7000256354')
            column(name: 'payee_account_number', value: '1000017527')
            column(name: 'work_title', value: 'Statements')
            column(name: 'system_title', value: 'Statements')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '38daeed3-e4ee-4b09-b6ec-ef12a12bcd34')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Statements')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'abd219c1-caae-4542-84e3-f9f4dba0d03b')
            column(name: 'name', value: 'SAL Historical Item Bank Details report Usage Batch 2')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'sal_fields', value: '{"licensee_account_number": 2000017003, "licensee_name": "ProLitteris"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '161890bc-318c-4042-9bd4-c12c4e2a2b02')
            column(name: 'df_usage_batch_uid', value: 'abd219c1-caae-4542-84e3-f9f4dba0d03b')
            column(name: 'wr_wrk_inst', value: '180047973')
            column(name: 'rh_account_number', value: '2000017128')
            column(name: 'payee_account_number', value: '1000017527')
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: '400.00')
            column(name: 'net_amount', value: '300.00')
            column(name: 'service_fee_amount', value: '100.00')
            column(name: 'service_fee', value: '0.25000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '161890bc-318c-4042-9bd4-c12c4e2a2b02')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        rollback ""
    }
}
