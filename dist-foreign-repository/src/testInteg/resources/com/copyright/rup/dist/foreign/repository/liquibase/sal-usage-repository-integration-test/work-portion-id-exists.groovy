databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-07-29-01', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testWorkPortionIdExists, testWorkPortionIdExistsInBatch')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05844db0-e0e4-4423-8966-7f1c6160f000')
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'name', value: 'Georgia State University Business Press [C]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'fd137df2-7308-49a0-b72e-0ea6924249a9')
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '46754660-b627-46b9-a782-3f703b6853c7')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'name', value: 'Access Copyright, The Canadian Copyright Agency')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6aa46f9f-a0c2-4b61-97bc-aa35b7ce6e64')
            column(name: 'name', value: 'SAL usage batch 1')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5ab5e80b-89c0-4d78-9675-54c7ab284450')
            column(name: 'df_usage_batch_uid', value: '6aa46f9f-a0c2-4b61-97bc-aa35b7ce6e64')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '5ab5e80b-89c0-4d78-9675-54c7ab284450')
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
            column(name: 'df_usage_uid', value: '7b5ac9fc-63e2-4162-8d63-953b7023293c')
            column(name: 'df_usage_batch_uid', value: '6aa46f9f-a0c2-4b61-97bc-aa35b7ce6e64')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
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
            column(name: 'states', value: 'SD;VT')
            column(name: 'number_of_views', value: 762)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56069b44-10b1-42d6-9a44-a3fae0029171')
            column(name: 'name', value: 'SAL usage batch 2')
            column(name: 'payment_date', value: '2016-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'sal_fields', value: '{"licensee_account_number": 1000000009, "licensee_name": "Tiger Publications"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c95654c0-a607-4683-878f-99606e90c065')
            column(name: 'df_usage_batch_uid', value: '56069b44-10b1-42d6-9a44-a3fae0029171')
            column(name: 'wr_wrk_inst', value: 369040892)
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-03-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-03-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'c95654c0-a607-4683-878f-99606e90c065')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '7')
            column(name: 'grade_group', value: 'GRADE6_8')
            column(name: 'assessment_name', value: 'FY17 EOC')
            column(name: 'assessment_type', value: 'EOC')
            column(name: 'reported_work_portion_id', value: '1201064IB2199')
            column(name: 'coverage_year', value: '2011-2012')
            column(name: 'scored_assessment_date', value: '2015-02-05')
            column(name: 'question_identifier', value: 'SB245')
            column(name: 'states', value: 'MN;OR')
            column(name: 'number_of_views', value: 254)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3572dcec-b0b8-4a19-a18e-319be139f0d2')
            column(name: 'name', value: 'SAL usage batch 3')
            column(name: 'payment_date', value: '2016-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 1000000009, "licensee_name": "Tiger Publications"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'fc56debb-4265-4f92-b0bb-4189a2c694a8')
            column(name: 'df_usage_batch_uid', value: '3572dcec-b0b8-4a19-a18e-319be139f0d2')
            column(name: 'wr_wrk_inst', value: 876543210)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'standard_number', value: 'Medical Journal')
            column(name: 'standard_number_type', value: 'Medical Journal')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-03-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-03-10 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'fc56debb-4265-4f92-b0bb-4189a2c694a8')
            column(name: 'detail_type', value: 'IB')
            column(name: 'assessment_name', value: 'FY16 EOC')
            column(name: 'assessment_type', value: 'EOC')
            column(name: 'reported_work_portion_id', value: '1201064IB2200')
            column(name: 'coverage_year', value: '2011-2012')
            column(name: 'scored_assessment_date', value: '2015-02-05')
            column(name: 'question_identifier', value: 'SB245')
            column(name: 'states', value: 'MN;OR')
            column(name: 'number_of_views', value: 254)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f646ef53-081a-4216-a025-f20d8b233a62')
            column(name: 'df_usage_batch_uid', value: '3572dcec-b0b8-4a19-a18e-319be139f0d2')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-03-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-03-10 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'f646ef53-081a-4216-a025-f20d8b233a62')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '10')
            column(name: 'assessment_name', value: 'FY16 EOC')
            column(name: 'assessment_type', value: 'EOC')
            column(name: 'question_identifier', value: 'Q1')
            column(name: 'reported_work_portion_id', value: '1201064IB2200')
            column(name: 'coverage_year', value: '2011-2012')
            column(name: 'scored_assessment_date', value: '2015-02-05')
            column(name: 'question_identifier', value: 'SB245')
            column(name: 'states', value: 'MN;OR')
            column(name: 'number_of_views', value: 254)
        }

        rollback {
            dbRollback
        }
    }
}
