databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-09-28-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testFindAaclNotAttachedToScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '1ea65e2a-22c1-4a96-b55b-b6b4fd7d51ed')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 1')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "09/28/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, ' +
                    '"licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 5, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 1000.00, "item_bank_gross_amount": 200.01, "grade_K_5_gross_amount": 533.33, "grade_6_8_gross_amount": 266.66,' +
                    ' "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
            column(name: 'created_datetime', value: '2020-09-28 11:00:00-04')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '15c3023d-1e68-4b7d-bfe3-18e85806b167')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 2')
            column(name: 'total_amount', value: 100.00)
            column(name: 'sal_fields', value: '{"date_received": "09/29/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, ' +
                    '"licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 2, "grade_6_8_number_of_students": 1, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 100.00, "item_bank_gross_amount": 20.01, "grade_K_5_gross_amount": 53.33, "grade_6_8_gross_amount": 26.66, ' +
                    '"grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
            column(name: 'created_datetime', value: '2020-09-29 11:00:00-04')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '8bc02d1f-35f8-4626-b206-0e9ffc8d9f97')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 3')
            column(name: 'total_amount', value: 10.00)
            column(name: 'sal_fields', value: '{"date_received": "12/30/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, ' +
                    '"licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 2, "grade_6_8_number_of_students": 1, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 10.00, "item_bank_gross_amount": 2.01, "grade_K_5_gross_amount": 5.33, "grade_6_8_gross_amount": 2.66, ' +
                    '"grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
            column(name: 'created_datetime', value: '2020-09-30 11:00:00-04')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c8ff486f-8aec-40ac-a249-2c828df81d0c')
            column(name: 'name', value: 'SAL batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'sal_fields', value: '{"licensee_name": "Truman State University", "licensee_account_number": "4444"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '3c7b100c-0e30-4381-bd06-787e058af6f1')
            column(name: 'name', value: 'SAL Scenario 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "8bc02d1f-35f8-4626-b206-0e9ffc8d9f97"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a664e039-994d-43f4-959a-54ad84d88ecf')
            column(name: 'df_usage_batch_uid', value: 'c8ff486f-8aec-40ac-a249-2c828df81d0c')
            column(name: 'df_scenario_uid', value: '3c7b100c-0e30-4381-bd06-787e058af6f1')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'payee_account_number', value: 1000000001)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'comment', value: 'SAL IB usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'a664e039-994d-43f4-959a-54ad84d88ecf')
            column(name: 'assessment_name', value: 'Spring2014 Eng Lang/Mathy')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'grade', value: '11')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'reported_work_portion_id', value: '1101024IB2190')
            column(name: 'reported_standard_number', value: '978-0-87664-361-7')
            column(name: 'reported_media_type', value: 'Image')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'reported_author', value: 'Linda J. S. Allen')
            column(name: 'reported_publisher', value: 'Rosen')
            column(name: 'reported_publication_date', value: '2016-11-03')
            column(name: 'reported_page_range', value: '25-30')
            column(name: 'reported_vol_number_series', value: '55(2)')
            column(name: 'detail_type', value: 'IB')
        }

        rollback {
            dbRollback
        }
    }
}
