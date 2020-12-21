databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-12-16-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for SendToCrmIntegrationTest')

        // AACL
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '351e585c-0b08-429d-9e31-bea283ba33de')
            column(name: 'name', value: 'Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '4109ca95-326d-428a-9d7e-1a90bc891dba')
            column(name: 'df_scenario_uid', value: '351e585c-0b08-429d-9e31-bea283ba33de')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a87218a0-1966-40aa-846a-434b3fd41282')
            column(name: 'name', value: 'Paid batch')
            column(name: 'payment_date', value: '2020-05-24')
            column(name: 'product_family', value: 'AACL')
            column(name: 'gross_amount', value: '500.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'a5e00786-c741-4460-b436-da832a285cf8')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'usage_period', value: '2016')
            column(name: 'usage_source', value: 'Aug 2016 FR')
            column(name: 'number_of_copies', value: '30')
            column(name: 'number_of_pages', value: '35')
            column(name: 'detail_licensee_class_id', value: '143')
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'publication_type_weight', value: '2')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage Comment 3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'e5ae9237-05a0-4c82-b607-0f91f19b2f24')
            column(name: 'df_usage_batch_uid', value: 'a87218a0-1966-40aa-846a-434b3fd41282')
            column(name: 'df_scenario_uid', value: '351e585c-0b08-429d-9e31-bea283ba33de')
            column(name: 'product_family', value: 'AACL')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '420.00')
            column(name: 'service_fee_amount', value: '80.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-03-11')
            column(name: 'period_end_date', value: '2016-03-11')
            column(name: 'created_datetime', value: '2016-03-11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'e5ae9237-05a0-4c82-b607-0f91f19b2f24')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2017')
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'number_of_pages', value: '1')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: '1.71')
            column(name: 'baseline_uid', value: 'a5e00786-c741-4460-b436-da832a285cf8')
        }

        // FAS
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '221c5a30-1937-4bf6-977f-93741f9b20f1')
            column(name: 'name', value: 'Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'd612ad12-1d53-4284-9bed-050638e0b05f')
            column(name: 'df_scenario_uid', value: '221c5a30-1937-4bf6-977f-93741f9b20f1')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '10defe40-ef47-47c9-96b4-be9226f3d591')
            column(name: 'name', value: 'Paid batch')
            column(name: 'rro_account_number', value: '1000005413')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '0d1829eb-de35-4f93-bb36-2a7435263051')
            column(name: 'df_usage_batch_uid', value: '10defe40-ef47-47c9-96b4-be9226f3d591')
            column(name: 'df_scenario_uid', value: '221c5a30-1937-4bf6-977f-93741f9b20f1')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '420.00')
            column(name: 'service_fee_amount', value: '80.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-03-11')
            column(name: 'period_end_date', value: '2016-03-11')
            column(name: 'created_datetime', value: '2016-03-11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '0d1829eb-de35-4f93-bb36-2a7435263051')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2016')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '3000')
        }

        // NTS
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'name', value: 'NTS Scenario with paid usages')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario description')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 30.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'ab040bf1-717d-419a-b75c-6004c0036798')
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'adcd15c4-eb44-4e67-847a-7f386082646a')
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'wr_wrk_inst', value: '151811999')
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: '1000003821')
            column(name: 'payee_account_number', value: '1000003821')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: '90.63')
            column(name: 'service_fee_amount', value: '29.00')
            column(name: 'net_amount', value: '61.63')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2017-12-12')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA October 17')
            column(name: 'distribution_date', value: '2017-11-11')
            column(name: 'lm_detail_id', value: 'c4232309-d890-489d-b99e-ca96c8e7e473')
            column(name: 'period_end_date', value: '2018-03-11')
            column(name: 'created_datetime', value: '2016-03-12')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'adcd15c4-eb44-4e67-847a-7f386082646a')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '0.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '6fa92092-5cd3-4a12-bbf4-762f7ff6f815')
            column(name: 'df_scenario_uid', value: '67027e15-17c6-4b9b-b7f0-12ec414ad344')
            column(name: 'wr_wrk_inst', value: '151811999')
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: '7000429266')
            column(name: 'payee_account_number', value: '7000429266')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: '13503.37')
            column(name: 'net_amount', value: '9182.28')
            column(name: 'service_fee_amount', value: '4321.07')
            column(name: 'service_fee', value: '0.32000')
            column(name: 'period_end_date', value: '2018-03-11')
            column(name: 'created_datetime', value: '2016-03-12')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6fa92092-5cd3-4a12-bbf4-762f7ff6f815')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '0.00')
        }

        // SAL
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '2b664666-dcde-4abe-8757-34627606ee68')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool for testFindSalUsagesByIds')
            column(name: 'total_amount', value: '1000.00')
            column(name: 'sal_fields', value: '{"date_received": "12/24/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 200.00, "grade_K_5_gross_amount": 900.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.10000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '220a4c96-2cf6-4377-85f1-2a7cd6648b77')
            column(name: 'name', value: 'SAL Usage Batch for testFindSalUsagesByIds')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '183c0b55-3665-4863-a28c-0370feccad24')
            column(name: 'name', value: 'SAL Scenario for testFindSalUsagesByIds')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "2b664666-dcde-4abe-8757-34627606ee68"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '14704648-838e-444f-8987-c4f1dc3aa38d')
            column(name: 'df_usage_batch_uid', value: '220a4c96-2cf6-4377-85f1-2a7cd6648b77')
            column(name: 'df_scenario_uid', value: '183c0b55-3665-4863-a28c-0370feccad24')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '2000017010')
            column(name: 'payee_account_number', value: '2000017010')
            column(name: "system_title", value: 'Castanea')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '75.00')
            column(name: 'service_fee_amount', value: '25.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'check_number', value: '578000')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '3356214')
            column(name: 'distribution_name', value: 'FDA March 20')
            column(name: 'distribution_date', value: '2020-04-03')
            column(name: 'lm_detail_id', value: '4375bee0-24f0-4e6c-a808-c62814dd93ae')
            column(name: 'created_datetime', value: '2020-09-03 08:35:38')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '14704648-838e-444f-8987-c4f1dc3aa38d')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY20 AIR')
            column(name: 'reported_work_portion_id', value: '1101001IB2368')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2019-2020')
            column(name: 'scored_assessment_date', value: '2019-07-01')
            column(name: 'question_identifier', value: 'SB9')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '2b2cf124-8c96-4662-8949-c56002247f39')
            column(name: 'df_usage_batch_uid', value: '220a4c96-2cf6-4377-85f1-2a7cd6648b77')
            column(name: 'df_scenario_uid', value: '183c0b55-3665-4863-a28c-0370feccad24')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '2000017010')
            column(name: 'payee_account_number', value: '2000017010')
            column(name: "system_title", value: 'Castanea')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'gross_amount', value: '900.00')
            column(name: 'net_amount', value: '675.00')
            column(name: 'service_fee_amount', value: '225.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'check_number', value: '578000')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '3356214')
            column(name: 'distribution_name', value: 'FDA March 20')
            column(name: 'distribution_date', value: '2020-04-03')
            column(name: 'lm_detail_id', value: '9375bee0-24f0-4e6c-a808-c62814dd93ae')
            column(name: 'created_datetime', value: '2020-09-03 08:35:38')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '2b2cf124-8c96-4662-8949-c56002247f39')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY20 AIR')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1101001IB2368')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'IMAGE')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'coverage_year', value: '2019-2020')
            column(name: 'states', value: 'CA;WV')
            column(name: 'number_of_views', value: 7)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '03aa3c29-7feb-48a5-bb86-2db6afd55cda')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 2 for testFindSalUsagesByIds')
            column(name: 'total_amount', value: '1000.00')
            column(name: 'sal_fields', value: '{"date_received": "12/24/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 200.00, "grade_K_5_gross_amount": 900.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.10000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0d9483bc-17fd-42ff-8305-7a796a0b1c6b')
            column(name: 'name', value: 'SAL Usage Batch 2 for testFindSalUsagesByIds')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'dbe98f9f-76f9-4250-9822-1b5b0b046348')
            column(name: 'name', value: 'SAL Scenario 2 for testFindSalUsagesByIds')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "03aa3c29-7feb-48a5-bb86-2db6afd55cda"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '52604648-838e-333f-8987-c4f1dc3aa38a')
            column(name: 'df_usage_batch_uid', value: '0d9483bc-17fd-42ff-8305-7a796a0b1c6b')
            column(name: 'df_scenario_uid', value: 'dbe98f9f-76f9-4250-9822-1b5b0b046348')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '2000017010')
            column(name: 'payee_account_number', value: '2000017010')
            column(name: "system_title", value: 'Castanea')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'net_amount', value: '75.00')
            column(name: 'service_fee_amount', value: '25.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'created_datetime', value: '2020-09-03 08:35:38')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '52604648-838e-333f-8987-c4f1dc3aa38a')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY20 AIR')
            column(name: 'reported_work_portion_id', value: '1101001IB2368')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2019-2020')
            column(name: 'scored_assessment_date', value: '2019-07-01')
            column(name: 'question_identifier', value: 'SB9')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '563cf124-8c96-4662-8529-c56002247f39')
            column(name: 'df_usage_batch_uid', value: '0d9483bc-17fd-42ff-8305-7a796a0b1c6b')
            column(name: 'df_scenario_uid', value: 'dbe98f9f-76f9-4250-9822-1b5b0b046348')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '2000017010')
            column(name: 'payee_account_number', value: '2000017010')
            column(name: "system_title", value: 'Castanea')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'gross_amount', value: '900.00')
            column(name: 'net_amount', value: '675.00')
            column(name: 'service_fee_amount', value: '225.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'created_datetime', value: '2020-09-03 08:35:38')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '563cf124-8c96-4662-8529-c56002247f39')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY20 AIR')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1101001IB2368')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'IMAGE')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'coverage_year', value: '2019-2020')
            column(name: 'states', value: 'CA;WV')
            column(name: 'number_of_views', value: 7)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '193a615e-0af3-4497-9846-1578049a7b4b')
            column(name: 'df_scenario_uid', value: '183c0b55-3665-4863-a28c-0370feccad24')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '193a615e-0af3-4497-9846-1578049a7b4b')
            column(name: 'df_usage_batch_uid', value: '220a4c96-2cf6-4377-85f1-2a7cd6648b77')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '173a615e-0af3-4497-9846-1578049a7b4b')
            column(name: 'df_scenario_uid', value: 'dbe98f9f-76f9-4250-9822-1b5b0b046348')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '173a615e-0af3-4497-9846-1578049a7b4b')
            column(name: 'df_usage_batch_uid', value: '220a4c96-2cf6-4377-85f1-2a7cd6648b77')
        }
    }
}
