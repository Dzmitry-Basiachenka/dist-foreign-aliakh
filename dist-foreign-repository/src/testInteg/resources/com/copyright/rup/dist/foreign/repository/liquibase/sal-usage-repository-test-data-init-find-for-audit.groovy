databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-12-18-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testFindForAudit')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05844db0-e0e4-4423-8966-7f1c6160f000')
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'name', value: 'Georgia State University Business Press [C]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'f78b7a00-856d-4df6-aee6-0fa07e5a9f17')
            column(name: 'rh_account_number', value: 2000173934)
            column(name: 'name', value: 'Amy Mandelker')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '57d0d356-65a1-4169-acab-a522475fb4d5')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool to test audit 1')
            column(name: 'total_amount', value: 1000.00)
            column(name: 'sal_fields', value: '{"date_received": "10/08/2020", "assessment_name": "FY2020 EOC", ' +
                    '"licensee_account_number": 7001573389, "licensee_name": "Questar Assessment", ' +
                    '"grade_K_5_number_of_students": 0, "grade_6_8_number_of_students": 100, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 1000.00, "item_bank_gross_amount": 200.00, ' +
                    '"grade_K_5_gross_amount": 0.00, "grade_6_8_gross_amount": 800.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd67611c2-ee99-445b-9ce3-e798e2ed640a')
            column(name: 'name', value: 'SAL Usage Batch to test audit 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001573389, "licensee_name": "Questar Assessment"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b27d5717-7d94-45e8-9f99-bde681d52c03')
            column(name: 'name', value: 'SAL Scenario to test audit 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "57d0d356-65a1-4169-acab-a522475fb4d5"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '230faf46-16d7-45fe-8b78-b869aac3fceb')
            column(name: 'df_scenario_uid', value: 'b27d5717-7d94-45e8-9f99-bde681d52c03')
            column(name: 'product_family', value: 'SAL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '230faf46-16d7-45fe-8b78-b869aac3fceb')
            column(name: 'df_usage_batch_uid', value: 'd67611c2-ee99-445b-9ce3-e798e2ed640a')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7f82f654-d906-4cc3-83cf-0409c69a0891')
            column(name: 'df_usage_batch_uid', value: 'd67611c2-ee99-445b-9ce3-e798e2ed640a')
            column(name: 'df_scenario_uid', value: 'b27d5717-7d94-45e8-9f99-bde681d52c03')
            column(name: 'wr_wrk_inst', value: 131858485)
            column(name: 'rh_account_number', value: 2000173934)
            column(name: 'payee_account_number', value: 1000011450)
            column(name: 'work_title', value: 'Leseleiter')
            column(name: 'system_title', value: 'Leseleiter')
            column(name: 'standard_number', value: '978-3-592-10520-9')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 200.0000)
            column(name: 'net_amount', value: 150.0000)
            column(name: 'service_fee', value: 0.2500)
            column(name: 'service_fee_amount', value: 50.0000)
            column(name: 'comment', value: 'SAL comment 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '7f82f654-d906-4cc3-83cf-0409c69a0891')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '6')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY19 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2161')
            column(name: 'reported_article', value: 'Leseleiter')
            column(name: 'reported_standard_number', value: '978-3-592-10520-9')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'Image')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'coverage_year', value: '2018-2019')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c75305bc-9458-40bc-9926-004a47b072fc')
            column(name: 'df_usage_batch_uid', value: 'd67611c2-ee99-445b-9ce3-e798e2ed640a')
            column(name: 'df_scenario_uid', value: 'b27d5717-7d94-45e8-9f99-bde681d52c03')
            column(name: 'wr_wrk_inst', value: 133335135)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 2000173934)
            column(name: 'work_title', value: 'Rimas')
            column(name: 'system_title', value: 'Rimas')
            column(name: 'standard_number', value: '9788408047827')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 800.0000)
            column(name: 'net_amount', value: 600.0000)
            column(name: 'service_fee', value: 0.2500)
            column(name: 'service_fee_amount', value: 200.0000)
            column(name: 'comment', value: 'SAL comment 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'c75305bc-9458-40bc-9926-004a47b072fc')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '6')
            column(name: 'grade_group', value: 'GRADE6_8')
            column(name: 'assessment_name', value: 'FY18 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2161')
            column(name: 'reported_article', value: 'Rimas')
            column(name: 'reported_standard_number', value: '9788408047827')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'Image')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD;VT')
            column(name: 'number_of_views', value: 5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '79702b06-3848-419b-9eae-726ac1f11875')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool to test audit 2')
            column(name: 'total_amount', value: 2000.00)
            column(name: 'sal_fields', value: '{"date_received": "10/08/2020", "assessment_name": "FY2020 EOC", ' +
                    '"licensee_account_number": 2000017003, "licensee_name": "ProLitteris", ' +
                    '"grade_K_5_number_of_students": 0, "grade_6_8_number_of_students": 100, "grade_9_12_number_of_students": 0, ' +
                    '"gross_amount": 2000.00, "item_bank_gross_amount": 400.00, ' +
                    '"grade_K_5_gross_amount": 0.00, "grade_6_8_gross_amount": 1600.00, "grade_9_12_gross_amount": 0.00, ' +
                    '"item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a375c049-1289-4c85-994b-b2bd8ac043cf')
            column(name: 'name', value: 'SAL Usage Batch to test audit 2')
            column(name: 'payment_date', value: '2018-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 2000017003, "licensee_name": "ProLitteris"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'f8ea52bf-8822-43a4-80bc-e6dde96f857b')
            column(name: 'name', value: 'SAL Scenario to test audit 2')
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
            column(name: 'product_family', value: 'SAL')
            column(name: 'wr_wrk_inst', value: 123013764)
            column(name: 'work_title', value: 'Telling stories')
            column(name: 'system_title', value: 'Telling stories')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'period_end_date', value: '2018-06-30')
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
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
            column(name: 'comment', value: 'SAL comment 3')
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
            column(name: 'product_family', value: 'SAL')
            column(name: 'wr_wrk_inst', value: 123013764)
            column(name: 'work_title', value: 'Telling stories')
            column(name: 'system_title', value: 'Telling stories')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'period_end_date', value: '2018-06-30')
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
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
            column(name: 'comment', value: 'SAL comment 3')
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

        rollback {
            dbRollback
        }
    }
}
