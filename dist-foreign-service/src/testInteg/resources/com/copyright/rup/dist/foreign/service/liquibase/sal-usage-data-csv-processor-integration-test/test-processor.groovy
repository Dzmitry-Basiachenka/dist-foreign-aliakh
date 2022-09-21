databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-09-24-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testProcessor, testProcessorForNegativePathBusinessValidation')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '10ddbb20-1b13-434d-8347-6db3f840e70f')
            column(name: 'name', value: 'SAL batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value:'{"licensee_name": "Truman State University", "licensee_account_number": "4444"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '54cbf219-5835-4637-bd1e-599f52321b74')
            column(name: 'df_usage_batch_uid', value: '10ddbb20-1b13-434d-8347-6db3f840e70f')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'comment', value: 'SAL comment 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '54cbf219-5835-4637-bd1e-599f52321b74')
            column(name: 'assessment_name', value: 'Spring2014 Eng Lang/Mathy')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'grade', value: '2')
            column(name: 'reported_work_portion_id', value: 'A1')
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
            column(name: 'df_usage_uid', value: '76185ca9-f61a-4d6e-b61e-4ae2edcd1d50')
            column(name: 'df_usage_batch_uid', value: '10ddbb20-1b13-434d-8347-6db3f840e70f')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'comment', value: 'SAL comment 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '76185ca9-f61a-4d6e-b61e-4ae2edcd1d50')
            column(name: 'assessment_name', value: 'Spring2014 Eng Lang/Mathy')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'reported_work_portion_id', value: 'A2')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3ad19d09-c54b-4cf8-95a9-0322a39df829')
            column(name: 'name', value: 'SAL batch 2')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'sal_fields', value:'{"licensee_name": "Truman State University", "licensee_account_number": "4444"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '44f47584-62e2-4cbe-8cd7-20e091e68307')
            column(name: 'df_usage_batch_uid', value: '3ad19d09-c54b-4cf8-95a9-0322a39df829')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
            column(name: 'comment', value: 'SAL comment 3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '44f47584-62e2-4cbe-8cd7-20e091e68307')
            column(name: 'assessment_name', value: 'Spring2014 Eng Lang/Mathy')
            column(name: 'coverage_year', value: '2014-2015')
            column(name: 'reported_work_portion_id', value: 'A3')
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
