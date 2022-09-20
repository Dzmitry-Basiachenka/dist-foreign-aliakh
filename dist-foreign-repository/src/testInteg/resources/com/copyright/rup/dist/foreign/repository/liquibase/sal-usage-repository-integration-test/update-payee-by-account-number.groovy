databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-10-01-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Insert test data for testUpdatePayeeByAccountNumber')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '87a8b327-6fcc-417f-8fd6-bb6615103b53')
            column(name: 'name', value: 'SAL batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 4)
            column(name: 'sal_fields', value: '{"licensee_name": "Truman State University", "licensee_account_number": "4444"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '1b032d29-21e5-41ce-80d0-9739d10630ee')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 1')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "09/28/2020", "assessment_name": "FY2020 COG", "licensee_account_number": "4444", ' +
                    '"licensee_name": "Truman State University", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 5, ' +
                    '"grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 200.00, "grade_K_5_gross_amount": 600.00, ' +
                    '"grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '6252afe5-e756-42d4-b96a-708afeda9122')
            column(name: 'name', value: 'SAL Scenario 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "1b032d29-21e5-41ce-80d0-9739d10630ee"}')
            column(name: 'description', value: 'SAL Scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e823d079-3e82-4a5c-bdad-a8707b47b665')
            column(name: 'df_usage_batch_uid', value: '87a8b327-6fcc-417f-8fd6-bb6615103b53')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'comment', value: 'SAL IB usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'e823d079-3e82-4a5c-bdad-a8707b47b665')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9439530a-d7a9-40a9-a881-f892d13eaf9f')
            column(name: 'df_usage_batch_uid', value: '87a8b327-6fcc-417f-8fd6-bb6615103b53')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'comment', value: 'SAL UB usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '9439530a-d7a9-40a9-a881-f892d13eaf9f')
            column(name: 'detail_type', value: 'UD')
            column(name: 'assessment_name', value: 'Spring2014 Eng Lang/Mathy')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'grade', value: '11')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'reported_work_portion_id', value: '1101024IB2190')
            column(name: 'reported_standard_number', value: '978-0-87664-361-7')
            column(name: 'reported_media_type', value: 'Image')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'reported_author', value: 'Linda J. S. Allen')
            column(name: 'reported_publisher', value: 'Rosen')
            column(name: 'reported_publication_date', value: '2016-11-03')
            column(name: 'reported_page_range', value: '25-30')
            column(name: 'reported_vol_number_series', value: '55(2)')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD;VT')
            column(name: 'number_of_views', value: 27)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ad4caa00-c95a-453e-9253-f9810d84d269')
            column(name: 'df_usage_batch_uid', value: '87a8b327-6fcc-417f-8fd6-bb6615103b53')
            column(name: 'df_scenario_uid', value: '6252afe5-e756-42d4-b96a-708afeda9122')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'comment', value: 'SAL comment 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'ad4caa00-c95a-453e-9253-f9810d84d269')
            column(name: 'assessment_name', value: 'Spring2014 Eng Lang/Mathy')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'grade', value: '4')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'reported_work_portion_id', value: '1101024IB2191')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd7764071-935f-4281-a643-656354ccf690')
            column(name: 'df_usage_batch_uid', value: '87a8b327-6fcc-417f-8fd6-bb6615103b53')
            column(name: 'df_scenario_uid', value: '6252afe5-e756-42d4-b96a-708afeda9122')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'd7764071-935f-4281-a643-656354ccf690')
            column(name: 'detail_type', value: 'UD')
            column(name: 'assessment_name', value: 'Spring2014 Eng Lang/Mathy')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'grade', value: '4')
            column(name: 'grade_group', value: 'GRADEK_5')
            column(name: 'reported_work_portion_id', value: '1101024IB2191')
            column(name: 'reported_standard_number', value: '978-0-87664-361-7')
            column(name: 'reported_media_type', value: 'Image')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'reported_author', value: 'Linda J. S. Allen')
            column(name: 'reported_publisher', value: 'Rosen')
            column(name: 'reported_publication_date', value: '2016-11-03')
            column(name: 'reported_page_range', value: '25-30')
            column(name: 'reported_vol_number_series', value: '55(2)')
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD;VT')
            column(name: 'number_of_views', value: 42)
        }

        rollback {
            dbRollback
        }
    }
}
