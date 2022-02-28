databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-10-16-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for testWriteLiabilitiesByRhCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b2ea68f6-3c15-4ae3-a04a-acdd5a236f0c')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'adf97d33-4b76-4c2c-ad6e-91c4715d392f')
            column(name: 'rh_account_number', value: 5000581900)
            column(name: 'name', value: 'The Copyright Company')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'e6cb4b13-30cf-4629-991b-4095fcaaaae6')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL liabilities by Rightsholder report Fund Pool 1')
            column(name: 'total_amount', value: 2000.00)
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
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 4)
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
            column(name: 'wr_wrk_inst', value: 243204754)
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'payee_account_number', value: 2000017010)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'net_amount', value: 37.5000000000)
            column(name: 'service_fee_amount', value: 12.5000000000)
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
            column(name: 'wr_wrk_inst', value: 243204754)
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'payee_account_number', value: 2000017010)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'gross_amount', value: 506.2500000000)
            column(name: 'net_amount', value: 379.6875000000)
            column(name: 'service_fee_amount', value: 126.5625000000)
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
            column(name: 'wr_wrk_inst', value: 373204754)
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'payee_account_number', value: 2000017010)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '8999639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'net_amount', value: 37.5000000000)
            column(name: 'service_fee_amount', value: 12.5000000000)
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
            column(name: 'wr_wrk_inst', value: 373204754)
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'payee_account_number', value: 2000017010)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '8999639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'gross_amount', value: 393.7500000000)
            column(name: 'net_amount', value: 295.3125000000)
            column(name: 'service_fee_amount', value: 98.4375000000)
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
            column(name: 'total_amount', value: 2000.00)
            column(name: 'sal_fields', value: '{"date_received": "10/10/2020", "assessment_name": "FY2020 AIR", ' +
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
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 4)
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
            column(name: 'wr_wrk_inst', value: 112904754)
            column(name: 'rh_account_number', value: 5000581900)
            column(name: 'payee_account_number', value: 5000581900)
            column(name: 'work_title', value: 'The University of State Michigan')
            column(name: 'system_title', value: 'The University of State Michigan')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'net_amount', value: 37.5000000000)
            column(name: 'service_fee_amount', value: 12.5000000000)
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
            column(name: 'wr_wrk_inst', value: 112904754)
            column(name: 'rh_account_number', value: 5000581900)
            column(name: 'payee_account_number', value: 5000581900)
            column(name: 'work_title', value: 'The University of State Michigan')
            column(name: 'system_title', value: 'The University of State Michigan')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'gross_amount', value: 506.2500000000)
            column(name: 'net_amount', value: 379.6875000000)
            column(name: 'service_fee_amount', value: 126.5625000000)
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
            column(name: 'wr_wrk_inst', value: 983204714)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'payee_account_number', value: 1000000001)
            column(name: 'work_title', value: 'Burn Your Stuff')
            column(name: 'system_title', value: 'Burn Your Stuff')
            column(name: 'standard_number', value: '7779639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'net_amount', value: 37.5000000000)
            column(name: 'service_fee_amount', value: 12.5000000000)
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
            column(name: 'wr_wrk_inst', value: 983204714)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'payee_account_number', value: 1000000001)
            column(name: 'work_title', value: 'Burn Your Stuff')
            column(name: 'system_title', value: 'Burn Your Stuff')
            column(name: 'standard_number', value: '7779639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'gross_amount', value: 393.7500000000)
            column(name: 'net_amount', value: 295.3125000000)
            column(name: 'service_fee_amount', value: 98.4375000000)
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

        rollback {
            dbRollback
        }
    }
}
