databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-12-01-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testUpdateRhAccountNumberAndStatusByIds')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '33c9b62f-80dc-4583-8504-3757c80b4aec')
            column(name: 'name', value: 'SAL Usage Batch for RH update')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '83e26dc4-87af-464d-9edc-bb37611947fa')
            column(name: 'df_usage_batch_uid', value: '33c9b62f-80dc-4583-8504-3757c80b4aec')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'WORK_NOT_GRANTED')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '83e26dc4-87af-464d-9edc-bb37611947fa')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'assessment_type', value: 'AIR')
            column(name: 'reported_work_portion_id', value: '1101001IB2362')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'states', value: 'CA;WV')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'number_of_views', value: 1765)
            column(name: 'scored_assessment_date', value: '2015-07-01')
            column(name: 'question_identifier', value: 'SB9')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '497ef524-242b-49b3-9b47-c64082f6895d')
            column(name: 'df_usage_batch_uid', value: '33c9b62f-80dc-4583-8504-3757c80b4aec')
            column(name: 'wr_wrk_inst', value: 278850020)
            column(name: 'work_title', value: 'Click')
            column(name: 'system_title', value: 'Click')
            column(name: 'standard_number', value: '1094-4273')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'WORK_NOT_GRANTED')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '497ef524-242b-49b3-9b47-c64082f6895d')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '3')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'E2E Questar - MOUT')
            column(name: 'reported_work_portion_id', value: '177xE2EQUEMOUTIB5')
            column(name: 'reported_article', value: 'Keep in Touch')
            column(name: 'reported_standard_number', value: '1094-4273')
            column(name: 'reported_author', value: 'Rachel Young')
            column(name: 'reported_publisher', value: 'Cricket Media')
            column(name: 'reported_publication_date', value: '2016')
            column(name: 'reported_media_type', value: 'Text')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2018-2019')
        }

        rollback {
            dbRollback
        }
    }
}
