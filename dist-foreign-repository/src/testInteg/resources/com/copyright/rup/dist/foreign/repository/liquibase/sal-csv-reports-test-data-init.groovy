databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-09-23-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testWriteSalUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '07811891-eb9d-405b-a7ff-2264b83a77eb')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'name', value: 'Access Copyright, The Canadian Copyright Agency')
        }

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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'da616e09-ca76-4815-b178-637abf32a76e')
            column(name: 'name', value: 'SAL usage batch for export')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2015)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '207d92c3-b773-4754-a1cc-9f729f8b87e3')
            column(name: 'df_usage_batch_uid', value: 'da616e09-ca76-4815-b178-637abf32a76e')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'comment', value: 'comment')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '207d92c3-b773-4754-a1cc-9f729f8b87e3')
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
            column(name: 'df_usage_uid', value: '8241d1b7-799b-4130-923b-76376723668a')
            column(name: 'df_usage_batch_uid', value: 'da616e09-ca76-4815-b178-637abf32a76e')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'comment', value: 'comment')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '8241d1b7-799b-4130-923b-76376723668a')
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
    }

    changeSet(id: '2020-10-14-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteSalScenarioUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3680dbbf-e360-4658-b262-88e25652fa4e')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 1')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "12/24/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 5, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 20.01, "grade_K_5_gross_amount": 653.3, "grade_6_8_gross_amount": 326.66, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.02000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0dbfa399-5c7d-487e-8ea1-cb38cbd15cef')
            column(name: 'name', value: 'SAL Usage Batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'd79c1cef-b764-49ca-ab54-812d84cca548')
            column(name: 'name', value: 'SAL Scenario 1')
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
            column(name: 'name', value: 'SAL Fund Pool 2')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "12/25/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 5, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 20.01, "grade_K_5_gross_amount": 653.3, "grade_6_8_gross_amount": 326.66, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.02000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '043ae316-6d55-45c3-92af-d7069530ea9e')
            column(name: 'name', value: 'SAL Usage Batch 2')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5ba85c68-1f19-45c0-a6f3-a5c0e7737636')
            column(name: 'name', value: 'SAL Scenario 2')
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

        rollback ""
    }

    changeSet(id: '2020-10-16-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for testWriteLiabilitiesByRhCsvReport')

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

        rollback ""
    }

    changeSet(id: '2020-10-15-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testWriteSalLiabilitiesSummaryByRhAndWorkReportCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a408ef06-05ea-4477-a5a6-ad448fd49bc7')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'name', value: 'Greenleaf Publishing')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'bc7107a8-a098-47f9-b28c-d7e8bb8704f2')
            column(name: 'rh_account_number', value: 2000024497)
            column(name: 'name', value: 'Alexander Stille')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '9c1e3854-9f02-42e8-a8f5-c2cbfd0050c0')
            column(name: 'rh_account_number', value: 7000452842)
            column(name: 'name', value: 'Libertas Academica')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '462111b6-5d30-4a43-a35b-14796d34d847')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Liabilities Summary by Rightsholder and Work report Fund Pool 1')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "10/09/2020", "assessment_name": "FY2020 EOC", ' +
                    '"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", ' +
                    '"grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 1000.00, "item_bank_gross_amount": 200.00, ' +
                    '"grade_K_5_gross_amount": 800.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '85df79f3-7e3f-4d74-9931-9aa513195815')
            column(name: 'name', value: 'SAL Liabilities Summary by Rightsholder and Work report Usage Batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'c0b30809-4a38-46cc-a0dc-641924d1fc43')
            column(name: 'name', value: 'SAL Liabilities Summary by Rightsholder and Work report Scenario 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "462111b6-5d30-4a43-a35b-14796d34d847"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e8daeed3-e4ee-4b09-b6ec-ef12a12bcd3d')
            column(name: 'df_usage_batch_uid', value: '85df79f3-7e3f-4d74-9931-9aa513195815')
            column(name: 'df_scenario_uid', value: 'c0b30809-4a38-46cc-a0dc-641924d1fc43')
            column(name: 'wr_wrk_inst', value: 122973671)
            column(name: 'rh_account_number', value: 7000452842)
            column(name: 'payee_account_number', value: 2000024497)
            column(name: 'work_title', value: 'Statements')
            column(name: 'system_title', value: 'Statements')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'e8daeed3-e4ee-4b09-b6ec-ef12a12bcd3d')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Statements')
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
            column(name: 'df_usage_uid', value: '71d1a5d2-ba9f-48b7-9b09-0516840a07ee')
            column(name: 'df_usage_batch_uid', value: '85df79f3-7e3f-4d74-9931-9aa513195815')
            column(name: 'df_scenario_uid', value: 'c0b30809-4a38-46cc-a0dc-641924d1fc43')
            column(name: 'wr_wrk_inst', value: 122973671)
            column(name: 'rh_account_number', value: 7000452842)
            column(name: 'payee_account_number', value: 1000017454)
            column(name: 'work_title', value: 'Statements')
            column(name: 'system_title', value: 'Statements')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 800.00)
            column(name: 'net_amount', value: 600.00)
            column(name: 'service_fee_amount', value: 200.00)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '71d1a5d2-ba9f-48b7-9b09-0516840a07ee')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Statements')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'cbe96e80-1aa8-41e3-b57b-b8ee67a0f673')
            column(name: 'df_scenario_uid', value: 'c0b30809-4a38-46cc-a0dc-641924d1fc43')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'cbe96e80-1aa8-41e3-b57b-b8ee67a0f673')
            column(name: 'df_usage_batch_uid', value: '85df79f3-7e3f-4d74-9931-9aa513195815')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '93ad09c8-1ffa-4609-9917-e12aeee885a3')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Liabilities Summary by Rightsholder and Work report Fund Pool 2')
            column(name: 'total_amount', value: 2000.00)
            column(name: 'sal_fields', value: '{"date_received": "10/11/2020", "assessment_name": "FY2020 EOC", ' +
                    '"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", ' +
                    '"grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 2000.00, "item_bank_gross_amount": 400.00, ' +
                    '"grade_K_5_gross_amount": 1600.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9bd219c1-caae-4542-84e3-f9f4dba0d034')
            column(name: 'name', value: 'SAL Liabilities Summary by Rightsholder and Work report Usage Batch 2')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '0a3b533d-d3ed-48dc-b256-f4f9f6527d91')
            column(name: 'name', value: 'SAL Liabilities Summary by Rightsholder and Work report Scenario 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "93ad09c8-1ffa-4609-9917-e12aeee885a3"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f61890bc-318c-4042-9bd4-c12c4e2a2b0f')
            column(name: 'df_usage_batch_uid', value: '9bd219c1-caae-4542-84e3-f9f4dba0d034')
            column(name: 'df_scenario_uid', value: '0a3b533d-d3ed-48dc-b256-f4f9f6527d91')
            column(name: 'wr_wrk_inst', value: 180047973)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'payee_account_number', value: 2000024497)
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 400.00)
            column(name: 'net_amount', value: 300.00)
            column(name: 'service_fee_amount', value: 100.00)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'f61890bc-318c-4042-9bd4-c12c4e2a2b0f')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
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
            column(name: 'df_usage_uid', value: '81fbbcc4-9705-4d5e-9824-79aa20f22dec')
            column(name: 'df_usage_batch_uid', value: '9bd219c1-caae-4542-84e3-f9f4dba0d034')
            column(name: 'df_scenario_uid', value: '0a3b533d-d3ed-48dc-b256-f4f9f6527d91')
            column(name: 'wr_wrk_inst', value: 180047973)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'payee_account_number', value: 2000024497)
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 1600.00)
            column(name: 'net_amount', value: 1200.00)
            column(name: 'service_fee_amount', value: 400.00)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '81fbbcc4-9705-4d5e-9824-79aa20f22dec')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '106bb37d-37dc-4b85-88a8-c7ffa506c011')
            column(name: 'df_scenario_uid', value: '0a3b533d-d3ed-48dc-b256-f4f9f6527d91')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '106bb37d-37dc-4b85-88a8-c7ffa506c011')
            column(name: 'df_usage_batch_uid', value: '9bd219c1-caae-4542-84e3-f9f4dba0d034')
        }

        rollback ""
    }

    changeSet(id: '2020-11-05-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Insert test data for testSalUndistributedLiabilitiesCsvReport')

        //Should be included into report as it isn't associated with any scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '5f4df57b-b318-4a9d-b00a-82ab04ed9331')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool Name 1 for testSalUndistributedLiabilitiesCsvReport')
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
            column(name: 'name', value: 'SAL Usage Batch 1 for testSalUndistributedLiabilitiesCsvReport')
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
            column(name: 'name', value: 'SAL Fund Pool Name 2 for testSalUndistributedLiabilitiesCsvReport')
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
            column(name: 'name', value: 'SAL Usage Batch 2 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '0e23d736-f921-4757-859a-cd73092f0e68')
            column(name: 'name', value: 'SAL Scenario 2 For testSalUndistributedLiabilitiesCsvReport')
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
            column(name: 'name', value: 'SAL Fund Pool Name 3 for testSalUndistributedLiabilitiesCsvReport')
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
            column(name: 'name', value: 'SAL Usage Batch 3 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e8cffe6c-0e10-43dc-8be4-8cb3852bc70d')
            column(name: 'name', value: 'SAL Scenario 3 For testSalUndistributedLiabilitiesCsvReport')
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
            column(name: 'name', value: 'SAL Fund Pool Name 4 for testSalUndistributedLiabilitiesCsvReport')
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
            column(name: 'name', value: 'SAL Usage Batch 4 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5e81da52-8834-4f51-bc63-ecc9327beaac')
            column(name: 'name', value: 'SAL Scenario 4 For testSalUndistributedLiabilitiesCsvReport')
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
            column(name: 'name', value: 'SAL Fund Pool Name 5 for testSalUndistributedLiabilitiesCsvReport')
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
            column(name: 'name', value: 'SAL Usage Batch 5 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '68427933-e409-47fd-8b0f-80f1fd5ae17a')
            column(name: 'name', value: 'SAL Scenario 5 For testSalUndistributedLiabilitiesCsvReport')
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
            column(name: 'name', value: 'SAL Fund Pool Name 6 for testSalUndistributedLiabilitiesCsvReport')
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
            column(name: 'name', value: 'SAL Usage Batch 6 for testSalUndistributedLiabilitiesCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '20245f10-a071-4ccd-8cfe-dd51e98079f8')
            column(name: 'name', value: 'SAL Scenario 6 For testSalUndistributedLiabilitiesCsvReport')
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

        rollback ""
    }

    changeSet(id: '2020-11-09-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteSalFundPoolsCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '1dbec643-2133-4839-9cf4-60dcfd04cc59')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pools for Report 1')
            column(name: 'total_amount', value: 2000.00)
            column(name: 'sal_fields', value: '{"date_received": "10/12/2020", "assessment_name": "FY2020 AIR", ' +
                    '"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", ' +
                    '"grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 1000.00, "item_bank_gross_amount": 100.00, ' +
                    '"grade_K_5_gross_amount": 900.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.10000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '8b805b1a-e855-492a-b3a8-f14ec6598fa1')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pools for Report 2')
            column(name: 'total_amount', value: 5000.00)
            column(name: 'sal_fields', value: '{"date_received": "12/31/2018", "assessment_name": "FY1990 COG", ' +
                    '"licensee_account_number": 7001293454, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, ' +
                    '"grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, "gross_amount": 5000.00, ' +
                    '"item_bank_gross_amount": 250.00, "grade_K_5_gross_amount": 4750.00, "grade_6_8_gross_amount": 0.00, ' +
                    '"grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.50000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '8deedf18-7dbc-4521-b276-817de65dc220')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pools for Report 3')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "12/31/2018", "assessment_name": "FY1990 COG", ' +
                    '"licensee_account_number": 1000003007, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, ' +
                    '"grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, ' +
                    '"item_bank_gross_amount": 100.00, "grade_K_5_gross_amount": 900.0, "grade_6_8_gross_amount": 0.00, ' +
                    '"grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.10000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '03d82f8e-2ae2-412a-9ba2-d127043b88a4')
            column(name: 'name', value: 'SAL Usage Batch 6 for testWriteSalFundPoolsCsvReport')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5a5d2e4d-bc8e-486d-b635-45801dbb8a47')
            column(name: 'name', value: 'SAL Scenario 6 For testWriteSalFundPoolsCsvReport')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "8deedf18-7dbc-4521-b276-817de65dc220"}')
            column(name: 'description', value: 'SAL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cd868c8b-851c-4e90-b539-ef73bdadf0be')
            column(name: 'df_usage_batch_uid', value: '03d82f8e-2ae2-412a-9ba2-d127043b88a4')
            column(name: 'df_scenario_uid', value: '5a5d2e4d-bc8e-486d-b635-45801dbb8a47')
            column(name: 'wr_wrk_inst', value: 180047973)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'payee_account_number', value: 2000024497)
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 75.00)
            column(name: 'service_fee_amount', value: 25.00)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'cd868c8b-851c-4e90-b539-ef73bdadf0be')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1235481IB2362')
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
            column(name: 'df_usage_uid', value: 'ca221557-896e-4fab-846d-ec140e67134d')
            column(name: 'df_usage_batch_uid', value: '03d82f8e-2ae2-412a-9ba2-d127043b88a4')
            column(name: 'df_scenario_uid', value: '5a5d2e4d-bc8e-486d-b635-45801dbb8a47')
            column(name: 'wr_wrk_inst', value: 180047973)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'payee_account_number', value: 2000024497)
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 75.00)
            column(name: 'service_fee_amount', value: 25.00)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'ca221557-896e-4fab-846d-ec140e67134d')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1235481IB2362')
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

        rollback ""
    }

    changeSet(id: '2020-11-26-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testWriteSalHistoricalItemBankDetailsCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '7d81d977-a96b-49d2-b08b-fb8089aed030')
            column(name: 'rh_account_number', value: 2000017128)
            column(name: 'name', value: 'Academia Sinica')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '867d42c1-f55c-47e4-91e9-973aae806fac')
            column(name: 'rh_account_number', value: 1000017527)
            column(name: 'name', value: 'Sphere Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '4d8fe2f4-29d3-4f01-ac2b-ede81cd7ae5d')
            column(name: 'rh_account_number', value: 7000256354)
            column(name: 'name', value: 'TransUnion LLC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '55df79f3-7e3f-4d74-9931-9aa513195816')
            column(name: 'name', value: 'SAL Historical Item Bank Details report Usage Batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'sal_fields', value: '{"licensee_account_number": 2000017003, "licensee_name": "ProLitteris"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '38daeed3-e4ee-4b09-b6ec-ef12a12bcd34')
            column(name: 'df_usage_batch_uid', value: '55df79f3-7e3f-4d74-9931-9aa513195816')
            column(name: 'wr_wrk_inst', value: 122973671)
            column(name: 'rh_account_number', value: 7000256354)
            column(name: 'payee_account_number', value: 1000017527)
            column(name: 'work_title', value: 'Statements')
            column(name: 'system_title', value: 'Statements')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '38daeed3-e4ee-4b09-b6ec-ef12a12bcd34')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Statements')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'abd219c1-caae-4542-84e3-f9f4dba0d03b')
            column(name: 'name', value: 'SAL Historical Item Bank Details report Usage Batch 2')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'sal_fields', value: '{"licensee_account_number": 2000017003, "licensee_name": "ProLitteris"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '161890bc-318c-4042-9bd4-c12c4e2a2b02')
            column(name: 'df_usage_batch_uid', value: 'abd219c1-caae-4542-84e3-f9f4dba0d03b')
            column(name: 'wr_wrk_inst', value: 180047973)
            column(name: 'rh_account_number', value: 2000017128)
            column(name: 'payee_account_number', value: 1000017527)
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 400.00)
            column(name: 'net_amount', value: 300.00)
            column(name: 'service_fee_amount', value: 100.00)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '161890bc-318c-4042-9bd4-c12c4e2a2b02')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
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

        rollback ""
    }

    changeSet(id: '2020-12-28-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testWriteAuditSalUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'f78b7a00-856d-4df6-aee6-0fa07e5a9f17')
            column(name: 'rh_account_number', value: 2000173934)
            column(name: 'name', value: 'Amy Mandelker')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '79702b06-3848-419b-9eae-726ac1f11875')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool to test audit export')
            column(name: 'total_amount', value: 2000.00)
            column(name: 'sal_fields', value: '{"date_received": "12/28/2020", "assessment_name": "FY2020 EOC", ' +
                    '"licensee_account_number": 2000017003, "licensee_name": "ProLitteris", ' +
                    '"grade_K_5_number_of_students": 0, "grade_6_8_number_of_students": 100, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 2000.00, "item_bank_gross_amount": 400.00, ' +
                    '"grade_K_5_gross_amount": 0.00, "grade_6_8_gross_amount": 1600.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a375c049-1289-4c85-994b-b2bd8ac043cf')
            column(name: 'name', value: 'SAL Usage Batch to test audit export')
            column(name: 'payment_date', value: '2018-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 2000017003, "licensee_name": "ProLitteris"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'f8ea52bf-8822-43a4-80bc-e6dde96f857b')
            column(name: 'name', value: 'SAL Scenario to test audit export')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "79702b06-3848-419b-9eae-726ac1f11875"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '19d37cf4-2dac-4e8f-aaf8-81833a05b0da')
            column(name: 'df_scenario_uid', value: 'f8ea52bf-8822-43a4-80bc-e6dde96f857b')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '19d37cf4-2dac-4e8f-aaf8-81833a05b0da')
            column(name: 'df_usage_batch_uid', value: 'a375c049-1289-4c85-994b-b2bd8ac043cf')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '870ee1dc-8596-409f-8ffe-717d17a33c9e')
            column(name: 'df_usage_batch_uid', value: 'a375c049-1289-4c85-994b-b2bd8ac043cf')
            column(name: 'df_scenario_uid', value: 'f8ea52bf-8822-43a4-80bc-e6dde96f857b')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'period_end_date', value: '2018-06-30')
            column(name: 'wr_wrk_inst', value: 123013764)
            column(name: 'work_title', value: 'Telling stories')
            column(name: 'system_title', value: 'Telling stories')
            column(name: 'rh_account_number', value: 2000173934)
            column(name: 'payee_account_number', value: 2000173934)
            column(name: 'standard_number', value: '9780415013871')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'gross_amount', value: 400.0000)
            column(name: 'net_amount', value: 300.0000)
            column(name: 'service_fee', value: 0.2500)
            column(name: 'service_fee_amount', value: 100.0000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53258')
            column(name: 'distribution_name', value: 'SAL_March_2017')
            column(name: 'distribution_date', value: '2018-11-03')
            column(name: 'comment', value: 'SAL comment 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '870ee1dc-8596-409f-8ffe-717d17a33c9e')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '6')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1101001IB2362')
            column(name: 'reported_article', value: 'Telling stories')
            column(name: 'reported_standard_number', value: '9780415013871')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2018-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2017-2018')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'a85d26e1-deb6-4f3f-96fa-58b4175825f4')
            column(name: 'df_usage_batch_uid', value: 'a375c049-1289-4c85-994b-b2bd8ac043cf')
            column(name: 'df_scenario_uid', value: 'f8ea52bf-8822-43a4-80bc-e6dde96f857b')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'period_end_date', value: '2018-06-30')
            column(name: 'wr_wrk_inst', value: 123013764)
            column(name: 'work_title', value: 'Telling stories')
            column(name: 'system_title', value: 'Telling stories')
            column(name: 'rh_account_number', value: 2000173934)
            column(name: 'payee_account_number', value: 2000173934)
            column(name: 'standard_number', value: '9780415013871')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'gross_amount', value: 1600.0000)
            column(name: 'net_amount', value: 1200.0000)
            column(name: 'service_fee', value: 0.2500)
            column(name: 'service_fee_amount', value: 400.0000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2018-11-03')
            column(name: 'ccc_event_id', value: '53259')
            column(name: 'distribution_name', value: 'SAL_March_2018')
            column(name: 'distribution_date', value: '2018-11-03')
            column(name: 'comment', value: 'SAL comment 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'a85d26e1-deb6-4f3f-96fa-58b4175825f4')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '6')
            column(name: 'grade_group', value: 'GRADE6_8')
            column(name: 'assessment_name', value: 'FY18 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2161')
            column(name: 'reported_article', value: 'Rimas')
            column(name: 'reported_standard_number', value: '9788408047827')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2017-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'Image')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'coverage_year', value: '2017-2018')
            column(name: 'scored_assessment_date', value: '2018-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD;VT')
            column(name: 'number_of_views', value: 5)
        }

        rollback ""
    }
}