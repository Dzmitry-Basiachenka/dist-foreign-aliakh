databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-10-19-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testFindCountByScenarioIdAndRhAccountNumber, testFindByScenarioIdAndRhAccountNumber')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05844db0-e0e4-4423-8966-7f1c6160f000')
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'name', value: 'Georgia State University Business Press [C]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'd638222e-1f02-4a53-806a-66162e795927')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool For Drill Down Window')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "12/24/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 5, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 20.01, "grade_K_5_gross_amount": 653.3, "grade_6_8_gross_amount": 326.66, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.02000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '35a5be09-00e3-49aa-9bfe-cd7a6c1b354e')
            column(name: 'name', value: 'SAL Usage Batch For Drill Down Window')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'bafc8277-d9f2-44b6-a68c-9e46165175f8')
            column(name: 'name', value: 'SAL Scenario 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "d638222e-1f02-4a53-806a-66162e795927"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1d437cb1-c262-44bf-a12a-65c562cfe8a7')
            column(name: 'df_usage_batch_uid', value: '35a5be09-00e3-49aa-9bfe-cd7a6c1b354e')
            column(name: 'df_scenario_uid', value: 'bafc8277-d9f2-44b6-a68c-9e46165175f8')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000000026)
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 75.00)
            column(name: 'service_fee_amount', value: 25.00)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '1d437cb1-c262-44bf-a12a-65c562cfe8a7')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'assessment_type', value: 'AIR')
            column(name: 'reported_work_portion_id', value: '1101001IB2362')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'states', value: 'CA;WV')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'number_of_views', value: 1765)
            column(name: 'scored_assessment_date', value: '2015-07-01')
            column(name: 'question_identifier', value: 'SB9')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '2371254d-2520-445f-b135-5ee9bccbbe05')
            column(name: 'df_scenario_uid', value: 'bafc8277-d9f2-44b6-a68c-9e46165175f8')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '2371254d-2520-445f-b135-5ee9bccbbe05')
            column(name: 'df_usage_batch_uid', value: '35a5be09-00e3-49aa-9bfe-cd7a6c1b354e')
        }

        rollback {
            dbRollback
        }
    }
}
