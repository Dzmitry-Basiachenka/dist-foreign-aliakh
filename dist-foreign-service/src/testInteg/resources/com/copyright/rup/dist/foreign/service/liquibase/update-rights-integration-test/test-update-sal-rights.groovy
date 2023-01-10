databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-08-20-00', author: 'Stanislau Rudak<srudak@copyright.com>') {
        comment('Inserting test data for testUpdateSalRights')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '73ebd1d1-20f7-41ce-84e7-e064034e7564')
            column(name: 'name', value: 'SAL Usage Batch 2020')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 3)
            column(name: 'sal_fields', value: '{"licensee_name": "RGS Energy Group, Inc.", "licensee_account_number": 5588}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'dcb53a42-7e8d-4a4a-8d72-6f794be2731c')
            column(name: 'df_usage_batch_uid', value: '73ebd1d1-20f7-41ce-84e7-e064034e7564')
            column(name: 'wr_wrk_inst', value: 122769471)
            column(name: 'standard_number', value: '978-0-7474-0150-6')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'system_title', value: 'life and death of Mozart')
            column(name: 'work_title', value: 'life and death of Mozart')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'dcb53a42-7e8d-4a4a-8d72-6f794be2731c')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: 'K')
            column(name: 'assessment_name', value: 'Spring2014 Eng Lang/Mathy')
            column(name: 'reported_work_portion_id', value: 'AT 805a1300-2232-4761-aad8-3b1e03a219d1')
            column(name: 'reported_standard_number', value: '978-0-87664-361-7')
            column(name: 'reported_author', value: 'Linda J. S. Allen')
            column(name: 'reported_publisher', value: 'Rosen')
            column(name: 'reported_publication_date', value: '2010-07-07 00:00:00.0')
            column(name: 'reported_page_range', value: '25-30')
            column(name: 'reported_vol_number_series', value: '55(2)')
            column(name: 'reported_media_type', value: 'Image')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '094749c5-08fa-4f57-8c3b-ecbc334a5c2a')
            column(name: 'df_usage_batch_uid', value: '73ebd1d1-20f7-41ce-84e7-e064034e7564')
            column(name: 'wr_wrk_inst', value: 243618757)
            column(name: 'standard_number', value: '978-0-947731-91-5')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'system_title', value: 'Midbus')
            column(name: 'work_title', value: 'Midbus')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '094749c5-08fa-4f57-8c3b-ecbc334a5c2a')
            column(name: 'detail_type', value: 'IB')
            column(name: 'assessment_name', value: 'Spring2015 Eng Lang/Mathy')
            column(name: 'reported_work_portion_id', value: 'AT 0af85c0d-c7f5-41c7-9fb9-059297c6b1b5')
            column(name: 'reported_standard_number', value: '978-2-84096-163-5')
            column(name: 'reported_author', value: 'Linda J. S. Allen')
            column(name: 'reported_publisher', value: 'Rosen')
            column(name: 'reported_publication_date', value: '2010-07-08 00:00:00.0')
            column(name: 'reported_page_range', value: '25-30')
            column(name: 'reported_vol_number_series', value: '55(2)')
            column(name: 'reported_media_type', value: 'Image')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'coverage_year', value: '2015-2016')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ecf46bea-2baa-40c1-a5e1-769c78865b2c')
            column(name: 'df_usage_batch_uid', value: '73ebd1d1-20f7-41ce-84e7-e064034e7564')
            column(name: 'wr_wrk_inst', value: 140160102)
            column(name: 'standard_number', value: '978-84-08-04782-7')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'system_title', value: 'Rimas')
            column(name: 'work_title', value: 'Rimas')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'ecf46bea-2baa-40c1-a5e1-769c78865b2c')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '2')
            column(name: 'assessment_name', value: 'Spring2014 Eng Lang/Mathy')
            column(name: 'reported_work_portion_id', value: 'AT 2fe76634-5851-42d4-852d-6b1dd2e87100')
            column(name: 'reported_standard_number', value: '1040-1776')
            column(name: 'reported_author', value: 'Linda J. S. Allen')
            column(name: 'reported_publisher', value: 'Rosen')
            column(name: 'reported_publication_date', value: '2010-07-09 00:00:00.0')
            column(name: 'reported_page_range', value: '25-30')
            column(name: 'reported_vol_number_series', value: '55(2)')
            column(name: 'reported_media_type', value: 'Text')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        rollback {
            dbRollback
        }
    }
}
