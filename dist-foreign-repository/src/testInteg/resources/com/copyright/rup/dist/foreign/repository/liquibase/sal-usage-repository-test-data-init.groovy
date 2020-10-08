databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-07-29-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for testFindCountByFilter, testFindDtosByFilter, testFindDtosByFilterSort")

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

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '46754660-b627-46b9-a782-3f703b6853c7')
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'name', value: 'Access Copyright, The Canadian Copyright Agency')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6aa46f9f-a0c2-4b61-97bc-aa35b7ce6e64')
            column(name: 'name', value: 'SAL usage batch 1')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2015')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5ab5e80b-89c0-4d78-9675-54c7ab284450')
            column(name: 'df_usage_batch_uid', value: '6aa46f9f-a0c2-4b61-97bc-aa35b7ce6e64')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'rh_account_number', value: '1000000026')
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
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 762)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56069b44-10b1-42d6-9a44-a3fae0029171')
            column(name: 'name', value: 'SAL usage batch 2')
            column(name: 'payment_date', value: '2016-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2015')
            column(name: 'sal_fields', value: '{"licensee_account_number": 1000000009, "licensee_name": "Tiger Publications"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c95654c0-a607-4683-878f-99606e90c065')
            column(name: 'df_usage_batch_uid', value: '56069b44-10b1-42d6-9a44-a3fae0029171')
            column(name: 'wr_wrk_inst', value: '369040892')
            column(name: 'rh_account_number', value: '2000017004')
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
            column(name: 'states', value: 'MN,OR')
            column(name: 'number_of_views', value: 254)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3572dcec-b0b8-4a19-a18e-319be139f0d2')
            column(name: 'name', value: 'SAL usage batch 3')
            column(name: 'payment_date', value: '2016-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2015')
            column(name: 'sal_fields', value: '{"licensee_account_number": 1000000009, "licensee_name": "Tiger Publications"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'fc56debb-4265-4f92-b0bb-4189a2c694a8')
            column(name: 'df_usage_batch_uid', value: '3572dcec-b0b8-4a19-a18e-319be139f0d2')
            column(name: 'wr_wrk_inst', value: '876543210')
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'standard_number', value: 'Medical Journal')
            column(name: 'standard_number_type', value: 'Medical Journal')
            column(name: 'rh_account_number', value: '1000000001')
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
            column(name: 'states', value: 'MN,OR')
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
            column(name: 'states', value: 'MN,OR')
            column(name: 'number_of_views', value: 254)
        }
    }

    changeSet(id: '2020-09-24-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("Insert test data for testUsageDetailsExist")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'cb932497-086d-4a7e-9b34-e9a62f17adab4')
            column(name: 'name', value: 'SAL usage batch with usage details')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2015')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8edafa8e-a395-4775-94c4-b5da6c400228')
            column(name: 'df_usage_batch_uid', value: 'cb932497-086d-4a7e-9b34-e9a62f17adab4')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '8edafa8e-a395-4775-94c4-b5da6c400228')
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
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'number_of_views', value: 1765)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd32374ef-d4ae-41eb-9591-c4bb2ad632fe')
            column(name: 'df_usage_batch_uid', value: 'cb932497-086d-4a7e-9b34-e9a62f17adab4')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'rh_account_number', value: '1000011450')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'd32374ef-d4ae-41eb-9591-c4bb2ad632fe')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b0e669d2-68d0-4add-9946-34215011f74b')
            column(name: 'name', value: 'SAL usage batch with usage details 2')
            column(name: 'payment_date', value: '2016-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2015')
            column(name: 'sal_fields', value: '{"licensee_account_number": 1000000009, "licensee_name": "Tiger Publications"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e3b4dfc5-a208-4b6e-996a-c667c705a08d')
            column(name: 'df_usage_batch_uid', value: 'b0e669d2-68d0-4add-9946-34215011f74b')
            column(name: 'wr_wrk_inst', value: '369040892')
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-03-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-03-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'e3b4dfc5-a208-4b6e-996a-c667c705a08d')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '7')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY17 EOC')
            column(name: 'assessment_type', value: 'EOC')
            column(name: 'reported_work_portion_id', value: '33064IB2190')
            column(name: 'coverage_year', value: '2011-2012')
            column(name: 'scored_assessment_date', value: '2015-02-05')
            column(name: 'question_identifier', value: 'SB245')
            column(name: 'states', value: 'MN,OR')
            column(name: 'number_of_views', value: 254)
        }
    }

    changeSet(id: '2020-09-25-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testInsertUsageDataDetail')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '48b77da2-c223-40c9-a655-bef4dbe7a807')
            column(name: 'name', value: 'SAL usage batch 4')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'sal_fields', value: '{"licensee_name": "Truman State University", "licensee_account_number": "4444"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '527e2779-2146-4f20-ad38-e795db220189')
            column(name: 'df_usage_batch_uid', value: '48b77da2-c223-40c9-a655-bef4dbe7a807')
            column(name: 'wr_wrk_inst', value: '876543210')
            column(name: 'work_title', value: 'Med. Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'standard_number', value: '978-0-7695-2365-2')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: '1000000001')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'comment', value: 'SAL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '527e2779-2146-4f20-ad38-e795db220189')
            column(name: 'assessment_name', value: 'Spring2014 Eng Lang/Mathy')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'grade', value: '2')
            column(name: 'reported_work_portion_id', value: '2402175IB3307')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '978-0-87664-361-7')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: '1.0')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2014-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'detail_type', value: 'IB')
        }
    }

    changeSet(id: '2020-09-25-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("Insert test data for testDeleteUsageData")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '09cc64a7-171a-4921-8d99-500768137cb8')
            column(name: 'name', value: 'SAL usage batch 5')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2015')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a47e2d1b-c09f-4e71-b949-223eb04c90c2')
            column(name: 'df_usage_batch_uid', value: '09cc64a7-171a-4921-8d99-500768137cb8')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'a47e2d1b-c09f-4e71-b949-223eb04c90c2')
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
            column(name: 'df_usage_uid', value: '17f35785-8402-4dfc-83c9-d1bedc2e6364')
            column(name: 'df_usage_batch_uid', value: '09cc64a7-171a-4921-8d99-500768137cb8')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'rh_account_number', value: '1000011450')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '17f35785-8402-4dfc-83c9-d1bedc2e6364')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7a358ad5-cbcb-4912-b1ad-314617662614')
            column(name: 'df_usage_batch_uid', value: '09cc64a7-171a-4921-8d99-500768137cb8')
            column(name: 'wr_wrk_inst', value: '369040892')
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-03-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-03-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '7a358ad5-cbcb-4912-b1ad-314617662614')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '7')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'assessment_name', value: 'FY17 EOC')
            column(name: 'assessment_type', value: 'EOC')
            column(name: 'reported_work_portion_id', value: '1101024IB2192')
            column(name: 'coverage_year', value: '2011-2012')
            column(name: 'scored_assessment_date', value: '2015-02-05')
            column(name: 'question_identifier', value: 'SB245')
            column(name: 'states', value: 'MN,OR')
            column(name: 'number_of_views', value: 254)
        }
    }

    changeSet(id: '2020-09-28-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("Insert test data for testDeleteByBatchId")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b54293db-bfb9-478a-bc13-d70aef5d3ecb')
            column(name: 'name', value: 'SAL usage batch for delete details')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2015')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b89b64d2-30bc-484c-a233-bcb9fd92ab26')
            column(name: 'df_usage_batch_uid', value: 'b54293db-bfb9-478a-bc13-d70aef5d3ecb')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'b89b64d2-30bc-484c-a233-bcb9fd92ab26')
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
            column(name: 'df_usage_uid', value: '4ac5174a-6feb-4c9a-a8cd-7d3cc35ab8e2')
            column(name: 'df_usage_batch_uid', value: 'b54293db-bfb9-478a-bc13-d70aef5d3ecb')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'rh_account_number', value: '1000011450')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '4ac5174a-6feb-4c9a-a8cd-7d3cc35ab8e2')
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

    changeSet(id: '2020-10-01-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("Insert test data for testAddToScenario and testUpdatePayeeByAccountNumber")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '87a8b327-6fcc-417f-8fd6-bb6615103b53')
            column(name: 'name', value: 'SAL batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'sal_fields', value: '{"licensee_name": "Truman State University", "licensee_account_number": "4444"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '1b032d29-21e5-41ce-80d0-9739d10630ee')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 1')
            column(name: 'total_amount', value: '1000.00')
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
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'rh_account_number', value: '1000000001')
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
            column(name: 'media_type_weight', value: '0.3')
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
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'rh_account_number', value: '1000000001')
            column(name: "system_title", value: 'Castanea')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
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
            column(name: 'media_type_weight', value: '0.3')
            column(name: 'reported_author', value: 'Linda J. S. Allen')
            column(name: 'reported_publisher', value: 'Rosen')
            column(name: 'reported_publication_date', value: '2016-11-03')
            column(name: 'reported_page_range', value: '25-30')
            column(name: 'reported_vol_number_series', value: '55(2)')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 27)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ad4caa00-c95a-453e-9253-f9810d84d269')
            column(name: 'df_usage_batch_uid', value: '87a8b327-6fcc-417f-8fd6-bb6615103b53')
            column(name: 'df_scenario_uid', value: '6252afe5-e756-42d4-b96a-708afeda9122')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'rh_account_number', value: '1000000001')
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
            column(name: 'media_type_weight', value: '0.3')
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
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'rh_account_number', value: '1000000001')
            column(name: "system_title", value: 'Castanea')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
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
            column(name: 'media_type_weight', value: '0.3')
            column(name: 'reported_author', value: 'Linda J. S. Allen')
            column(name: 'reported_publisher', value: 'Rosen')
            column(name: 'reported_publication_date', value: '2016-11-03')
            column(name: 'reported_page_range', value: '25-30')
            column(name: 'reported_vol_number_series', value: '55(2)')
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 42)
        }
    }

    changeSet(id: '2020-10-07-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for testDeleteFromScenario")

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '462111b6-5d30-4a43-a35b-14796d34d847')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool to test deletion')
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
            column(name: 'name', value: 'SAL Usage Batch to test deletion')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'c0b30809-4a38-46cc-a0dc-641924d1fc43')
            column(name: 'name', value: 'SAL Scenario to test deletion')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "462111b6-5d30-4a43-a35b-14796d34d847"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd8daeed3-e4ee-4b09-b6ec-ef12a12bcd3d')
            column(name: 'df_usage_batch_uid', value: '85df79f3-7e3f-4d74-9931-9aa513195815')
            column(name: 'df_scenario_uid', value: 'c0b30809-4a38-46cc-a0dc-641924d1fc43')
            column(name: 'wr_wrk_inst', value: '243204754')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'payee_account_number', value: '1000011450')
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'gross_amount', value: '980.00')
            column(name: 'net_amount', value: '667.40')
            column(name: 'service_fee_amount', value: '312.60')
            column(name: 'service_fee', value: '0.32000')
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
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '71d1a5d2-ba9f-48b7-9b09-0516840a07ee')
            column(name: 'df_usage_batch_uid', value: '85df79f3-7e3f-4d74-9931-9aa513195815')
            column(name: 'df_scenario_uid', value: 'c0b30809-4a38-46cc-a0dc-641924d1fc43')
            column(name: 'wr_wrk_inst', value: '243204754')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'payee_account_number', value: '1000011450')
            column(name: 'work_title', value: 'Learning in Your Sleep')
            column(name: 'system_title', value: 'Learning in Your Sleep')
            column(name: "standard_number", value: '09639292')
            column(name: "standard_number_type", value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'gross_amount', value: '980.00')
            column(name: 'net_amount', value: '667.40')
            column(name: 'service_fee_amount', value: '312.60')
            column(name: 'service_fee', value: '0.32000')
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
    }
}
