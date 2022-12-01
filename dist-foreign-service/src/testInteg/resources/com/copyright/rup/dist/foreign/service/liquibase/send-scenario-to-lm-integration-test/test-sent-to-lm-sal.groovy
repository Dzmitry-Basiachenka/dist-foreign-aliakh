databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-12-01-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testSendToLmSal')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '6cb5fe9f-d524-4dad-9d22-feb6a4476ba8')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 1 for testSendToLmSal')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "12/24/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 200.00, "grade_K_5_gross_amount": 800.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '7c7c9a7c-ac77-4477-badd-c769d1e5a8b5')
            column(name: 'name', value: 'SAL Usage Batch 1 for testSendToLmSal')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 4)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b32a1abe-0de7-4889-99aa-fd5491c85a94')
            column(name: 'name', value: 'SAL Scenario 1 for testSendToLmSal')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "6cb5fe9f-d524-4dad-9d22-feb6a4476ba8"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'q371254d-2520-445f-b135-5ee9bccbbe05')
            column(name: 'df_scenario_uid', value: 'b32a1abe-0de7-4889-99aa-fd5491c85a94')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'q371254d-2520-445f-b135-5ee9bccbbe05')
            column(name: 'df_usage_batch_uid', value: '7c7c9a7c-ac77-4477-badd-c769d1e5a8b5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b27b4760-8d15-483b-bf41-6670ce4c15e8')
            column(name: 'df_usage_batch_uid', value: '7c7c9a7c-ac77-4477-badd-c769d1e5a8b5')
            column(name: 'df_scenario_uid', value: 'b32a1abe-0de7-4889-99aa-fd5491c85a94')
            column(name: 'wr_wrk_inst', value: 243204754)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000000026)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 35.50)
            column(name: 'service_fee_amount', value: 12.50)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'b27b4760-8d15-483b-bf41-6670ce4c15e8')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '1')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1201001IB2161')
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
            column(name: 'df_usage_uid', value: '11d1a5d2-ba9f-48b7-9b09-0516840a07ee')
            column(name: 'df_usage_batch_uid', value: '7c7c9a7c-ac77-4477-badd-c769d1e5a8b5')
            column(name: 'df_scenario_uid', value: 'b32a1abe-0de7-4889-99aa-fd5491c85a94')
            column(name: 'wr_wrk_inst', value: 243204754)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000000026)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'gross_amount', value: 506.25)
            column(name: 'net_amount', value: 379.6875)
            column(name: 'service_fee_amount', value: 126.5625)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '11d1a5d2-ba9f-48b7-9b09-0516840a07ee')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '1')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1201001IB2161')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '18b5445f-2146-4f3d-a34a-aadf1e81aed3')
            column(name: 'df_usage_batch_uid', value: '7c7c9a7c-ac77-4477-badd-c769d1e5a8b5')
            column(name: 'df_scenario_uid', value: 'b32a1abe-0de7-4889-99aa-fd5491c85a94')
            column(name: 'wr_wrk_inst', value: 100004110)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000000026)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'standard_number', value: '09639291')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 35.50)
            column(name: 'service_fee_amount', value: 12.50)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '18b5445f-2146-4f3d-a34a-aadf1e81aed3')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '1')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1201001IB2162')
            column(name: 'reported_article', value: 'Medical Journal')
            column(name: 'reported_standard_number', value: '09639291')
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
            column(name: 'df_usage_uid', value: '9469ab6e-61e4-4e38-8011-2133cc0546f9')
            column(name: 'df_usage_batch_uid', value: '7c7c9a7c-ac77-4477-badd-c769d1e5a8b5')
            column(name: 'df_scenario_uid', value: 'b32a1abe-0de7-4889-99aa-fd5491c85a94')
            column(name: 'wr_wrk_inst', value: 100004110)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000000026)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'standard_number', value: '09639291')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'gross_amount', value: 393.75)
            column(name: 'net_amount', value: 295.3125)
            column(name: 'service_fee_amount', value: 98.4375)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '9469ab6e-61e4-4e38-8011-2133cc0546f9')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '1')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1201001IB2162')
            column(name: 'reported_article', value: 'Medical Journal')
            column(name: 'reported_standard_number', value: '09639291')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2019-2020')
            column(name: 'scored_assessment_date', value: '2020-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 24)
        }

        rollback {
            dbRollback
        }
    }
}
