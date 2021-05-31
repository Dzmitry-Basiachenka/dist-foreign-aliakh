databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-12-16-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for SendSalToCrmIntegrationTest')

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
            column(name: 'initial_usages_count', value: 2)
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
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'payee_account_number', value: 2000017010)
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
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'payee_account_number', value: 2000017010)
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
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'payee_account_number', value: 2000017010)
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
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'payee_account_number', value: 2000017010)
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
