databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-06-17-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testExecuteInternal test')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '4f2f9da4-2804-4b87-8df1-690460119c98')
            column(name: 'name', value: 'Test FAS Usage Batch to verify SendToRightsAssignmentJob')
            column(name: 'rro_account_number', value: 1000023401)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-06-17')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'gross_amount', value: 999.99)
            column(name: 'initial_usages_count', value: 8)
        }

        // should change status to NTS_WITHDRAWN because sum of gross amounts for Wr Wrk Inst 180382914 is less than $100
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8e812d97-a7b3-47a0-be4c-fd967ec11e63')
            column(name: 'df_usage_batch_uid', value: '4f2f9da4-2804-4b87-8df1-690460119c98')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'gross_amount', value: 70.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8e812d97-a7b3-47a0-be4c-fd967ec11e63')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2021)
            column(name: 'market_period_to', value: 2021)
            column(name: 'reported_value', value: 70.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '45d55a83-9acf-4e46-b179-179aa8ae05c5')
            column(name: 'df_usage_batch_uid', value: '4f2f9da4-2804-4b87-8df1-690460119c98')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'gross_amount', value: 29.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '45d55a83-9acf-4e46-b179-179aa8ae05c5')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2021)
            column(name: 'market_period_to', value: 2021)
        }

        // should change status to NTS_WITHDRAWN because gross amount for Wr Wrk Inst 123194821 is less than $100
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0acb64aa-32ee-4afb-813b-8241c490717b')
            column(name: 'df_usage_batch_uid', value: '4f2f9da4-2804-4b87-8df1-690460119c98')
            column(name: 'wr_wrk_inst', value: 123194821)
            column(name: 'work_title', value: 'Election law journal')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1533-1296')
            column(name: 'gross_amount', value: 99.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '0acb64aa-32ee-4afb-813b-8241c490717b')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2021)
            column(name: 'market_period_to', value: 2021)
        }

        // should change status to SENT_FOR_RA
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'df0471f5-1f0d-4ce5-967b-53f7f9bd94e3')
            column(name: 'df_usage_batch_uid', value: '4f2f9da4-2804-4b87-8df1-690460119c98')
            column(name: 'wr_wrk_inst', value: 946768461)
            column(name: 'work_title', value: 'Accounting education news')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '0882-956X')
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'df0471f5-1f0d-4ce5-967b-53f7f9bd94e3')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2021)
            column(name: 'market_period_to', value: 2021)
        }

        // should change status to SENT_FOR_RA
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '03a70593-5064-446e-85c5-e277cb549d99')
            column(name: 'df_usage_batch_uid', value: '4f2f9da4-2804-4b87-8df1-690460119c98')
            column(name: 'wr_wrk_inst', value: 946768461)
            column(name: 'work_title', value: 'Accounting education news')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '0882-956X')
            column(name: 'gross_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '03a70593-5064-446e-85c5-e277cb549d99')
            column(name: 'article', value: 'Appendix: The Principles of Newspeak')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2021)
            column(name: 'market_period_to', value: 2021)
        }

        // should change status to SENT_FOR_RA
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7b997ecc-e427-43da-806c-979d85e27bd7')
            column(name: 'df_usage_batch_uid', value: '4f2f9da4-2804-4b87-8df1-690460119c98')
            column(name: 'wr_wrk_inst', value: 946768462)
            column(name: 'work_title', value: 'Aerospace America')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '0740-722X')
            column(name: 'gross_amount', value: 300.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7b997ecc-e427-43da-806c-979d85e27bd7')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2021)
            column(name: 'market_period_to', value: 2021)
        }

        // should be skipped, only RH_NOT_FOUND usages are processed
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9f32b35f-27cd-4f32-b775-ac3f8dab59d2')
            column(name: 'df_usage_batch_uid', value: '4f2f9da4-2804-4b87-8df1-690460119c98')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9781616891282')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9f32b35f-27cd-4f32-b775-ac3f8dab59d2')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2021)
            column(name: 'market_period_to', value: 2021)
        }

        // should be skipped, only RH_NOT_FOUND usages are processed
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7e3f875a-1dc0-4aed-bd7e-2626b1338aa5')
            column(name: 'df_usage_batch_uid', value: '4f2f9da4-2804-4b87-8df1-690460119c98')
            column(name: 'wr_wrk_inst', value: 123565461)
            column(name: 'work_title', value: 'Annals of internal medicine')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1539-3704')
            column(name: 'gross_amount', value: 150.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7e3f875a-1dc0-4aed-bd7e-2626b1338aa5')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2021)
            column(name: 'market_period_to', value: 2021)
        }

        // see CDP-939
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9521474a-2128-416b-8b13-fc6353aa2b36')
            column(name: 'name', value: 'Test SAL Usage Batch to verify SendToRightsAssignmentJob')
            column(name: 'payment_date', value: '2022-06-17')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 5588, "licensee_name": "RGS Energy Group"}')
        }

        // should be skipped, only FAS/FAS2 usages are processed
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b0526981-0751-4953-9506-8cb2790e7f6d')
            column(name: 'df_usage_batch_uid', value: '9521474a-2128-416b-8b13-fc6353aa2b36')
            column(name: 'wr_wrk_inst', value: 100002884)
            column(name: 'work_title', value: 'Journal of petroleum technology')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'b0526981-0751-4953-9506-8cb2790e7f6d')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'assessment_type', value: 'AIR')
            column(name: 'reported_work_portion_id', value: '1101001IB2361')
            column(name: 'reported_article', value: 'Article 1')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2021-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2021')
            column(name: 'scored_assessment_date', value: '2021-03-04')
            column(name: 'question_identifier', value: 'SB9')
            column(name: 'states', value: 'CA,WV')
            column(name: 'number_of_views', value: 1765)
        }

        // should be skipped, only FAS/FAS2 usages are processed
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2f8f9bdc-7cf1-4b94-af3f-b31b61ab85d4')
            column(name: 'df_usage_batch_uid', value: '9521474a-2128-416b-8b13-fc6353aa2b36')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'system_title', value: 'Journal of applied nutrition')
            column(name: 'standard_number', value: '0021-8960')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '2f8f9bdc-7cf1-4b94-af3f-b31b61ab85d4')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '11')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'assessment_name', value: 'FY16 Smarter Balanaced ELA')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1101024IB2192')
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2021-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 762)
        }

        rollback {
            dbRollback
        }
    }
}
