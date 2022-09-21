databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-10-14-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteScenarioUsagesCsvReport, testWriteArchivedScenarioUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '07811891-eb9d-405b-a7ff-2264b83a77eb')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'name', value: 'Access Copyright, The Canadian Copyright Agency')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3680dbbf-e360-4658-b262-88e25652fa4e')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 1 for Scenario Usages Report test')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "12/24/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 5, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 20.01, "grade_K_5_gross_amount": 653.3, "grade_6_8_gross_amount": 326.66, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.02000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0dbfa399-5c7d-487e-8ea1-cb38cbd15cef')
            column(name: 'name', value: 'SAL Usage Batch 1 for Scenario Usages Report test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'd79c1cef-b764-49ca-ab54-812d84cca548')
            column(name: 'name', value: 'SAL Scenario 1 for Scenario Usages Report test')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "3680dbbf-e360-4658-b262-88e25652fa4e"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd8daeed3-e4ee-4b09-b6ec-ef12a12bcd3d')
            column(name: 'df_usage_batch_uid', value: '0dbfa399-5c7d-487e-8ea1-cb38cbd15cef')
            column(name: 'df_scenario_uid', value: 'd79c1cef-b764-49ca-ab54-812d84cca548')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'payee_account_number', value: 2000017004)
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
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'payee_account_number', value: 2000017004)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 75.00)
            column(name: 'service_fee_amount', value: 25.00)
            column(name: 'service_fee', value: 0.25000)
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
            column(name: 'name', value: 'SAL Fund Pool 2 for Scenario Usages Report test')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "12/25/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 5, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 20.01, "grade_K_5_gross_amount": 653.3, "grade_6_8_gross_amount": 326.66, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.02000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '043ae316-6d55-45c3-92af-d7069530ea9e')
            column(name: 'name', value: 'SAL Usage Batch 2 for Scenario Usages Report test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5ba85c68-1f19-45c0-a6f3-a5c0e7737636')
            column(name: 'name', value: 'SAL Scenario 2 for Scenario Usages Report test')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "043ae316-6d55-45c3-92af-d7069530ea9e"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '7994d23e-4a74-4999-8f65-ee1d9f774ab2')
            column(name: 'df_usage_batch_uid', value: '043ae316-6d55-45c3-92af-d7069530ea9e')
            column(name: 'df_scenario_uid', value: '5ba85c68-1f19-45c0-a6f3-a5c0e7737636')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'payee_account_number', value: 2000017004)
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
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'payee_account_number', value: 2000017004)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 75.00)
            column(name: 'service_fee_amount', value: 25.00)
            column(name: 'service_fee', value: 0.25000)
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

        rollback {
            dbRollback
        }
    }
}
