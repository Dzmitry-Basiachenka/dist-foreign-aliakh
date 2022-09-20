databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-10-15-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Insert test data for testCalculateAmounts')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '26d9bd2f-7024-474e-9dbf-c009158d81cd')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool to test calculation')
            column(name: 'total_amount', value: 2000.00)
            column(name: 'sal_fields', value: '{"date_received": "10/08/2020", "assessment_name": "FY2020 EOC", ' +
                    '"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", ' +
                    '"grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 5, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 1000.00, "item_bank_gross_amount": 200.01, ' +
                    '"grade_K_5_gross_amount": 533.33, "grade_6_8_gross_amount": 266.66, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a7b46cfc-95fa-4adb-8eae-e34438f17ece')
            column(name: 'name', value: 'SAL Usage Batch to test calculation')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 6)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '71d242e6-4009-4393-9962-45daf962706a')
            column(name: 'name', value: 'SAL Scenario to test calculation')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "26d9bd2f-7024-474e-9dbf-c009158d81cd"}')
        }

        // IB usage 1
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '015207e3-568d-4f3e-9845-ef1786fac399')
            column(name: 'df_usage_batch_uid', value: 'a7b46cfc-95fa-4adb-8eae-e34438f17ece')
            column(name: 'df_scenario_uid', value: '71d242e6-4009-4393-9962-45daf962706a')
            column(name: 'wr_wrk_inst', value: 243204754)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000011450)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '015207e3-568d-4f3e-9845-ef1786fac399')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
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

        // IB usage 2
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0fdb7912-2c56-49a9-b5bd-2364dd71c646')
            column(name: 'df_usage_batch_uid', value: 'a7b46cfc-95fa-4adb-8eae-e34438f17ece')
            column(name: 'df_scenario_uid', value: '71d242e6-4009-4393-9962-45daf962706a')
            column(name: 'wr_wrk_inst', value: 243204754)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000011450)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '0fdb7912-2c56-49a9-b5bd-2364dd71c646')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2262')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'IMAGE')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'coverage_year', value: '2014-2015')
        }

        // IB usage 3
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '382f727b-6791-4579-a5c3-3099ba856e70')
            column(name: 'df_usage_batch_uid', value: 'a7b46cfc-95fa-4adb-8eae-e34438f17ece')
            column(name: 'df_scenario_uid', value: '71d242e6-4009-4393-9962-45daf962706a')
            column(name: 'wr_wrk_inst', value: 243204754)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000011450)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '382f727b-6791-4579-a5c3-3099ba856e70')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '7')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2363')
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

        // UD usage 1
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '80a517a0-9a7a-4361-8840-e59d13d6e8db')
            column(name: 'df_usage_batch_uid', value: 'a7b46cfc-95fa-4adb-8eae-e34438f17ece')
            column(name: 'df_scenario_uid', value: '71d242e6-4009-4393-9962-45daf962706a')
            column(name: 'wr_wrk_inst', value: 243204754)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000011450)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '80a517a0-9a7a-4361-8840-e59d13d6e8db')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
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
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD;VT')
            column(name: 'number_of_views', value: 5)
        }

        // UD usage 2
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '98eedcf5-cd6d-46ee-9d70-912db0bf2997')
            column(name: 'df_usage_batch_uid', value: 'a7b46cfc-95fa-4adb-8eae-e34438f17ece')
            column(name: 'df_scenario_uid', value: '71d242e6-4009-4393-9962-45daf962706a')
            column(name: 'wr_wrk_inst', value: 243204754)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000011450)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '98eedcf5-cd6d-46ee-9d70-912db0bf2997')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
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
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD;VT')
            column(name: 'number_of_views', value: 4)
        }

        // UD usage 3
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cc2cf124-8c96-4662-8949-c56002247f39')
            column(name: 'df_usage_batch_uid', value: 'a7b46cfc-95fa-4adb-8eae-e34438f17ece')
            column(name: 'df_scenario_uid', value: '71d242e6-4009-4393-9962-45daf962706a')
            column(name: 'wr_wrk_inst', value: 243204754)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000011450)
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'cc2cf124-8c96-4662-8949-c56002247f39')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '6')
            column(name: 'grade_group', value: 'GRADE6_8')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2161')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'IMAGE')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD;VT')
            column(name: 'number_of_views', value: 7)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '6531a39b-eabe-4153-b695-1b3aced1af93')
            column(name: 'df_scenario_uid', value: '71d242e6-4009-4393-9962-45daf962706a')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '6531a39b-eabe-4153-b695-1b3aced1af93')
            column(name: 'df_usage_batch_uid', value: 'a7b46cfc-95fa-4adb-8eae-e34438f17ece')
        }

        rollback {
            dbRollback
        }
    }
}
