databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-10-15-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testWriteLiabilitiesSummaryByRhAndWorkCsvReport')

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

        rollback {
            dbRollback
        }
    }
}
