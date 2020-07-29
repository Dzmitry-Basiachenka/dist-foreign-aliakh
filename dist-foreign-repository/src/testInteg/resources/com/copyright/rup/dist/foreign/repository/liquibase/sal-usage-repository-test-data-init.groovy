databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-07-29-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for testFindCountByFilter, testFindDtosByFilter")

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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6aa46f9f-a0c2-4b61-97bc-aa35b7ce6e64')
            column(name: 'name', value: 'SAL usage batch 1')
            column(name: 'payment_date', value: '2015-06-03')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5ab5e80b-89c0-4d78-9675-54c7ab284450')
            column(name: 'df_usage_batch_uid', value: '6aa46f9f-a0c2-4b61-97bc-aa35b7ce6e64')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '5ab5e80b-89c0-4d78-9675-54c7ab284450')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'GRADE3_5')
            column(name: 'assessment_name', value: 'Spring2014 Eng Lang/Math')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1101001IB2361')
            column(name: 'reported_title', value: 'Science News for students')
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
            column(name: 'states', value: 'CA,WV')
            column(name: 'number_of_views', value: 1765)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7b5ac9fc-63e2-4162-8d63-953b7023293c')
            column(name: 'df_usage_batch_uid', value: '6aa46f9f-a0c2-4b61-97bc-aa35b7ce6e64')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'rh_account_number', value: '1000011450')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '7b5ac9fc-63e2-4162-8d63-953b7023293c')
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
}
