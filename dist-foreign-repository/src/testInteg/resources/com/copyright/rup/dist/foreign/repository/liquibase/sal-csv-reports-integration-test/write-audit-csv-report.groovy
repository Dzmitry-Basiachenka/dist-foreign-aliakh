databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-12-28-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testWriteAuditCsvReport, testWriteAuditCsvReportSearchBySqlLikePattern')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'f78b7a00-856d-4df6-aee6-0fa07e5a9f17')
            column(name: 'rh_account_number', value: 2000173934)
            column(name: 'name', value: 'Amy Mandelker')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '79702b06-3848-419b-9eae-726ac1f11875')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool for Audit Report test')
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
            column(name: 'name', value: 'SAL Usage Batch for Audit Report test')
            column(name: 'payment_date', value: '2018-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 2000017003, "licensee_name": "ProLitteris"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'f8ea52bf-8822-43a4-80bc-e6dde96f857b')
            column(name: 'name', value: 'SAL Scenario for Audit Report test')
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

        rollback {
            dbRollback
        }
    }
}
