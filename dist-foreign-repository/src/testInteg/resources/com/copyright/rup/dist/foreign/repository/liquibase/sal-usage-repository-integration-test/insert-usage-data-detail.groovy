databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-09-25-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testInsertUsageDataDetail')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '48b77da2-c223-40c9-a655-bef4dbe7a807')
            column(name: 'name', value: 'SAL usage batch 4')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'sal_fields', value: '{"licensee_name": "Truman State University", "licensee_account_number": "4444"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '527e2779-2146-4f20-ad38-e795db220189')
            column(name: 'df_usage_batch_uid', value: '48b77da2-c223-40c9-a655-bef4dbe7a807')
            column(name: 'wr_wrk_inst', value: 876543210)
            column(name: 'work_title', value: 'Med. Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'standard_number', value: '978-0-7695-2365-2')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000000001)
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
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2014-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'detail_type', value: 'IB')
        }

        rollback {
            dbRollback
        }
    }
}
