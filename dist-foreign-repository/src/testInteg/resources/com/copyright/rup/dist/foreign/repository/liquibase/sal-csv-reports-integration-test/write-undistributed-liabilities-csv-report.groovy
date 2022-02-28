databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-11-05-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testWriteUndistributedLiabilitiesCsvReport')

        //Should be included into report as it isn't associated with any scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '5f4df57b-b318-4a9d-b00a-82ab04ed9331')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool Name 1 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 100.00)
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
            column(name: 'name', value: 'SAL Usage Batch 1 for Scenario Usages Report test for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cef3a3f5-25ea-4338-9344-661ee09c42f3')
            column(name: 'df_usage_batch_uid', value: '10992462-4b8d-4616-bae8-2815dac6e9cd')
            column(name: 'wr_wrk_inst', value: 180047973)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'payee_account_number', value: 2000024497)
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 0.00)
            column(name: 'net_amount', value: 0.00)
            column(name: 'service_fee_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
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
            column(name: 'name', value: 'SAL Fund Pool Name 2 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 100.00)
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
            column(name: 'name', value: 'SAL Usage Batch 2 for Scenario Usages Report test for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '0e23d736-f921-4757-859a-cd73092f0e68')
            column(name: 'name', value: 'SAL Scenario 2 for Scenario Usages Report test for Undistributed Liabilities Report test')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "5652ec3a-1817-4598-bd6c-26506949e0d8"}')
            column(name: 'description', value: 'SAL Scenario Description 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9ed1cddf-6b95-44ed-a7a2-fa815a3f3023')
            column(name: 'df_usage_batch_uid', value: 'b309b1fa-d1f5-4278-bd9b-76e09b83c2ba')
            column(name: 'df_scenario_uid', value: '0e23d736-f921-4757-859a-cd73092f0e68')
            column(name: 'wr_wrk_inst', value: 180047973)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'payee_account_number', value: 2000024497)
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 10.00)
            column(name: 'net_amount', value: 7.50)
            column(name: 'service_fee_amount', value: 2.50)
            column(name: 'service_fee', value: 0.25000)
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
            column(name: 'wr_wrk_inst', value: 180047973)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'payee_account_number', value: 2000024497)
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 90.00)
            column(name: 'net_amount', value: 67.50)
            column(name: 'service_fee_amount', value: 22.50)
            column(name: 'service_fee', value: 0.25000)
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
            column(name: 'name', value: 'SAL Fund Pool Name 3 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 100.00)
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
            column(name: 'name', value: 'SAL Usage Batch 3 for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e8cffe6c-0e10-43dc-8be4-8cb3852bc70d')
            column(name: 'name', value: 'SAL Scenario 3 for Undistributed Liabilities Report test')
            column(name: 'status_ind', value: 'SUBMITTED')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "1a2f421d-5ae7-43ab-af08-482de7324534"}')
            column(name: 'description', value: 'SAL Scenario Description 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9b588758-991f-47d9-9f7b-4823cf082a3a')
            column(name: 'df_usage_batch_uid', value: '7d27be27-5bd8-480b-a30a-d3bedcc174f9')
            column(name: 'df_scenario_uid', value: 'e8cffe6c-0e10-43dc-8be4-8cb3852bc70d')
            column(name: 'wr_wrk_inst', value: 180047973)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'payee_account_number', value: 2000024497)
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 10.00)
            column(name: 'net_amount', value: 7.50)
            column(name: 'service_fee_amount', value: 25.00)
            column(name: 'service_fee', value: 0.25000)
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
            column(name: 'wr_wrk_inst', value: 180047973)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'payee_account_number', value: 2000024497)
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 90.00)
            column(name: 'net_amount', value: 67.50)
            column(name: 'service_fee_amount', value: 2.50)
            column(name: 'service_fee', value: 0.25000)
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
            column(name: 'name', value: 'SAL Fund Pool Name 4 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 100.00)
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
            column(name: 'name', value: 'SAL Usage Batch 4 for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5e81da52-8834-4f51-bc63-ecc9327beaac')
            column(name: 'name', value: 'SAL Scenario 4 for Undistributed Liabilities Report test')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "44764d4d-04d6-47f0-8789-eec182fcf567"}')
            column(name: 'description', value: 'SAL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8ac3ba17-c9dd-4ea4-bfe7-fe3d7287e2c0')
            column(name: 'df_usage_batch_uid', value: '07bdd480-6e19-4a98-97e5-15f250704a84')
            column(name: 'df_scenario_uid', value: '5e81da52-8834-4f51-bc63-ecc9327beaac')
            column(name: 'wr_wrk_inst', value: 180047973)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'payee_account_number', value: 2000024497)
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 10.00)
            column(name: 'net_amount', value: 7.50)
            column(name: 'service_fee_amount', value: 2.50)
            column(name: 'service_fee', value: 0.25000)
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
            column(name: 'wr_wrk_inst', value: 180047973)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'payee_account_number', value: 2000024497)
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 90.00)
            column(name: 'net_amount', value: 67.50)
            column(name: 'service_fee_amount', value: 22.50)
            column(name: 'service_fee', value: 0.25000)
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
            column(name: 'name', value: 'SAL Fund Pool Name 5 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 200.00)
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
            column(name: 'name', value: 'SAL Usage Batch 5 for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '68427933-e409-47fd-8b0f-80f1fd5ae17a')
            column(name: 'name', value: 'SAL Scenario 5 for Undistributed Liabilities Report test')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "f732a0ce-b59e-4cfa-9a9e-9e341065e042"}')
            column(name: 'description', value: 'SAL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'dc396d4a-2c88-4067-acd0-6bcd693c198c')
            column(name: 'df_usage_batch_uid', value: '44c8a8c1-48e6-4042-aec1-cf135f0039bf')
            column(name: 'df_scenario_uid', value: '68427933-e409-47fd-8b0f-80f1fd5ae17a')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'SAL')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 20.00)
            column(name: 'net_amount', value: 15.00)
            column(name: 'service_fee_amount', value: 5.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'number_of_copies', value: 155)
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
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'SAL')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 180.00)
            column(name: 'net_amount', value: 135.00)
            column(name: 'service_fee_amount', value: 45.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'number_of_copies', value: 155)
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
            column(name: 'name', value: 'SAL Fund Pool Name 6 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 200.00)
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
            column(name: 'name', value: 'SAL Usage Batch 6 for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '20245f10-a071-4ccd-8cfe-dd51e98079f8')
            column(name: 'name', value: 'SAL Scenario 6 for Undistributed Liabilities Report test')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "0ac98ae1-13b6-427e-a2df-2d59a164d313"}')
            column(name: 'description', value: 'SAL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '1e83f493-e711-4ed6-8c1b-d5115edf8398')
            column(name: 'df_usage_batch_uid', value: '5a21f256-9274-4395-ac95-094add1527ff')
            column(name: 'df_scenario_uid', value: '20245f10-a071-4ccd-8cfe-dd51e98079f8')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 20.00)
            column(name: 'net_amount', value: 15.00)
            column(name: 'service_fee_amount', value: 5.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'number_of_copies', value: 155)
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
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 180.00)
            column(name: 'net_amount', value: 135.00)
            column(name: 'service_fee_amount', value: 45.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'number_of_copies', value: 155)
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

        rollback {
            dbRollback
        }
    }
}
