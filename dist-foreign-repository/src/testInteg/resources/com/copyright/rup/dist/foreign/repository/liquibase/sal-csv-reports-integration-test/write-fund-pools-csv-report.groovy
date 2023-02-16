databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-11-09-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testWriteFundPoolsCsvReport and testWriteFundPoolsListCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '1dbec643-2133-4839-9cf4-60dcfd04cc59')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 1 for Scenario Usages Report test for Fund Pools Report test')
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
            column(name: 'name', value: 'SAL Fund Pool 2 for Scenario Usages Report test for Fund Pools Report test')
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
            column(name: 'name', value: 'SAL Fund Pool 3 for Fund Pools Report test')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "12/31/2018", "assessment_name": "FY1990 COG", ' +
                    '"licensee_account_number": 1000003007, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, ' +
                    '"grade_6_8_number_of_students": 0, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, ' +
                    '"item_bank_gross_amount": 100.00, "grade_K_5_gross_amount": 900.0, "grade_6_8_gross_amount": 0.00, ' +
                    '"grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.10000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '03d82f8e-2ae2-412a-9ba2-d127043b88a4')
            column(name: 'name', value: 'SAL Usage Batch 3 for Fund Pools Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5a5d2e4d-bc8e-486d-b635-45801dbb8a47')
            column(name: 'name', value: 'SAL Scenario 3 for Fund Pools Report test')
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

        rollback {
            dbRollback
        }
    }
}
