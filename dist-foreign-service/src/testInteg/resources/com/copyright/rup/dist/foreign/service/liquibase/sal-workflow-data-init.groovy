databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-08-21-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting data for testSalWorkflow')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '85f864f2-30a5-4215-ac4f-f1f541901218')
            column(name: 'rh_account_number', value: 1000000322)
            column(name: 'name', value: 'American College of Physicians - Journals')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: 1000024950)
            column(name: 'name', value: '101 Communications, Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '077383cf-8d9b-42ac-bdac-073cde78fa1e')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool with 100% IB split')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "09/28/2020", "assessment_name": "FY2020 COG", "licensee_account_number": "4444", ' +
                    '"licensee_name": "Truman State University", "grade_K_5_number_of_students": 0, "grade_6_8_number_of_students": 0, ' +
                    '"grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 750.00, ' +
                    '"grade_K_5_gross_amount": 0.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 1.00000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '1aa3a67a-b206-4953-96e7-a9c213db2902')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool with 20% IB split')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "09/28/2020", "assessment_name": "FY2020 COG", "licensee_account_number": "4444", ' +
                    '"licensee_name": "Truman State University", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, ' +
                    '"grade_9_12_number_of_students": 5, "gross_amount": 1000.00, "item_bank_gross_amount": 200.00, "grade_K_5_gross_amount": 600.00, ' +
                    '"grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 200.00, "item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2020-12-23-00', author: 'Anton Azarenka <azarenka@copyright.com>') {
        comment('Inserting data for testSalWorkflowWithoutUsageData')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'd90108a1-2225-4c81-9442-8522be284e66')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool for testFindSalUsagesByIds')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "12/24/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 200.00, "grade_K_5_gross_amount": 900.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.10000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd7a0806f-6088-407d-97eb-5e3661b167c5')
            column(name: 'name', value: 'SAL Usage Batch for testFindSalUsagesByIds')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'f75c3bc8-7dc8-437e-943f-df0868c00ad6')
            column(name: 'name', value: 'SAL Scenario for testFindSalUsagesByIds')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "d90108a1-2225-4c81-9442-8522be284e66"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '856452f1-a241-4a5a-8386-f6c8cbd0329e')
            column(name: 'df_usage_batch_uid', value: 'd7a0806f-6088-407d-97eb-5e3661b167c5')
            column(name: 'df_scenario_uid', value: 'f75c3bc8-7dc8-437e-943f-df0868c00ad6')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'payee_account_number', value: 2000017010)
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 75.00)
            column(name: 'service_fee_amount', value: 25.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'check_number', value: '578000')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '3356214')
            column(name: 'distribution_name', value: 'FDA March 20')
            column(name: 'distribution_date', value: '2020-04-03')
            column(name: 'lm_detail_id', value: '4375bee0-24f0-4e6c-a808-c62814dd93ae')
            column(name: 'created_datetime', value: '2020-09-03 08:35:38')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '856452f1-a241-4a5a-8386-f6c8cbd0329e')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '5d721861-f9e0-423f-8bd1-ec306512b642')
            column(name: 'df_scenario_uid', value: 'f75c3bc8-7dc8-437e-943f-df0868c00ad6')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '5d721861-f9e0-423f-8bd1-ec306512b642')
            column(name: 'df_usage_batch_uid', value: 'd7a0806f-6088-407d-97eb-5e3661b167c5')
        }

        rollback {
            dbRollback
        }
    }
}
