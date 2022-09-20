databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-02-09-11', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindSalNotAttachedToScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '4f0a6d1a-4df5-4d29-a1b4-9fc32f031ba5')
            column(name: 'name', value: 'SAL usage batch attached to a scenario')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2015)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
            column(name: 'updated_datetime', value: '2017-01-09 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'c4b5dbec-bd87-40ef-9168-97aa35a0d3f2')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 3')
            column(name: 'total_amount', value: 10.00)
            column(name: 'sal_fields', value: '{"date_received": "12/30/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, ' +
                    '"licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 2, "grade_6_8_number_of_students": 1, ' +
                    '"grade_9_12_number_of_students": 0, "gross_amount": 10.00, "grade_K_5_gross_amount": 6.00, "grade_6_8_gross_amount": 0.00, ' +
                    '"grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
            column(name: 'created_datetime', value: '2020-09-30 11:00:00-04')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '71018679-9338-4078-81b8-d9ca73af0d9a')
            column(name: 'name', value: 'SAL Scenario 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "c4b5dbec-bd87-40ef-9168-97aa35a0d3f2"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd2288c2b-3fbb-46d6-80fc-d11b22b2c33f')
            column(name: 'df_usage_batch_uid', value: '4f0a6d1a-4df5-4d29-a1b4-9fc32f031ba5')
            column(name: 'df_scenario_uid', value: '71018679-9338-4078-81b8-d9ca73af0d9a')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'd2288c2b-3fbb-46d6-80fc-d11b22b2c33f')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1101024IB2192')
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
            column(name: 'df_usage_uid', value: '7f9b4a23-cac2-41a6-80b3-9da4ccf2f0f4')
            column(name: 'df_usage_batch_uid', value: '4f0a6d1a-4df5-4d29-a1b4-9fc32f031ba5')
            column(name: 'df_scenario_uid', value: '71018679-9338-4078-81b8-d9ca73af0d9a')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '7f9b4a23-cac2-41a6-80b3-9da4ccf2f0f4')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '11')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'assessment_name', value: 'FY16 Smarter Balanaced ELA')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1101024IB2192')
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD;VT')
            column(name: 'number_of_views', value: 762)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'b4d12062-3001-4e30-843d-b54d6b742a26')
            column(name: 'df_scenario_uid', value: '71018679-9338-4078-81b8-d9ca73af0d9a')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'b4d12062-3001-4e30-843d-b54d6b742a26')
            column(name: 'df_usage_batch_uid', value: '4f0a6d1a-4df5-4d29-a1b4-9fc32f031ba5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '930caf5f-839f-4926-bebe-bde38d90b0e1')
            column(name: 'name', value: 'SAL Batch With New and RH Not Found usages')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2015)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 5588, "licensee_name": "RGS Energy Group"}')
            column(name: 'updated_datetime', value: '2017-01-10 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '21b562a2-a417-4502-b295-22e0ae09fe9d')
            column(name: 'df_usage_batch_uid', value: '930caf5f-839f-4926-bebe-bde38d90b0e1')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '21b562a2-a417-4502-b295-22e0ae09fe9d')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'assessment_type', value: 'AIR')
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
            column(name: 'scored_assessment_date', value: '2015-07-01')
            column(name: 'question_identifier', value: 'SB9')
            column(name: 'states', value: 'CA;WV')
            column(name: 'number_of_views', value: 1765)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7e575665-f74c-4e2b-ba53-33e488a70f67')
            column(name: 'df_usage_batch_uid', value: '930caf5f-839f-4926-bebe-bde38d90b0e1')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '7e575665-f74c-4e2b-ba53-33e488a70f67')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '11')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'assessment_name', value: 'FY16 Smarter Balanaced ELA')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1101024IB2192')
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD;VT')
            column(name: 'number_of_views', value: 762)
        }

        rollback {
            dbRollback
        }
    }
}
