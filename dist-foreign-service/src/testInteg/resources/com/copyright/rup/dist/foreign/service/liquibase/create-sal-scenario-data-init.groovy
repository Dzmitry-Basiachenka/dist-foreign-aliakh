databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-10-12-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for testCreateSalScenario")

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05844db0-e0e4-4423-8966-7f1c6160f000')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'name', value: 'Georgia State University Business Press [C]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'fd137df2-7308-49a0-b72e-0ea6924249a9')
            column(name: 'rh_account_number', value: '1000011450')
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '462111b6-5d30-4a43-a35b-14796d34d847')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool')
            column(name: 'total_amount', value: '2000.00')
            column(name: 'sal_fields', value: '{"date_received": "10/08/2020", "assessment_name": "FY2020 EOC", ' +
                    '"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers", ' +
                    '"grade_K_5_number_of_students": 0, "grade_6_8_number_of_students": 100, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 1000.00, "item_bank_gross_amount": 20.00, ' +
                    '"grade_K_5_gross_amount": 0.00, "grade_6_8_gross_amount": 980.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.02000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '85df79f3-7e3f-4d74-9931-9aa513195815')
            column(name: 'name', value: 'SAL Usage Batch')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd8daeed3-e4ee-4b09-b6ec-ef12a12bcd3d')
            column(name: 'df_usage_batch_uid', value: '85df79f3-7e3f-4d74-9931-9aa513195815')
            column(name: 'wr_wrk_inst', value: '243204754')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'payee_account_number', value: '1000011450')
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'SAL usage comment 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'd8daeed3-e4ee-4b09-b6ec-ef12a12bcd3d')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '6')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 7, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '71d1a5d2-ba9f-48b7-9b09-0516840a07ee')
            column(name: 'df_usage_batch_uid', value: '85df79f3-7e3f-4d74-9931-9aa513195815')
            column(name: 'wr_wrk_inst', value: '243204754')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'payee_account_number', value: '1000011450')
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'SAL usage comment 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '71d1a5d2-ba9f-48b7-9b09-0516840a07ee')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '6')
            column(name: 'grade_group', value: 'GRADE6_8')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-03-04')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 6, Issue 3')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD;VT')
            column(name: 'number_of_views', value: 42)
        }
    }
}
