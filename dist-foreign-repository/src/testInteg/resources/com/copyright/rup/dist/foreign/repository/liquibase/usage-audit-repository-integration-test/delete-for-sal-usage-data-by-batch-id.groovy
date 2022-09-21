databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2017-11-30-02', author: 'Aliaksandra_Bayanouskaya <abayanouskaya@copyright.com>') {
        comment('Inserting test data for testDeleteForSalUsageDataByBatchId')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '129b0e2d-8c2a-4c66-8dcf-456106c823d8')
            column(name: 'name', value: 'SAL usage batch 4')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2015)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '51e60822-5b4c-4fa8-9922-05e93065f216')
            column(name: 'df_usage_batch_uid', value: '129b0e2d-8c2a-4c66-8dcf-456106c823d8')
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
            column(name: 'df_usage_sal_uid', value: '51e60822-5b4c-4fa8-9922-05e93065f216')
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
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9793bfb8-2628-485a-9ceb-0d7652691a1b')
            column(name: 'df_usage_batch_uid', value: '129b0e2d-8c2a-4c66-8dcf-456106c823d8')
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
            column(name: 'df_usage_sal_uid', value: '9793bfb8-2628-485a-9ceb-0d7652691a1b')
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
            column(name: 'df_usage_uid', value: 'ad91d122-aedc-4b41-a09e-f7f055f5cb85')
            column(name: 'df_usage_batch_uid', value: '129b0e2d-8c2a-4c66-8dcf-456106c823d8')
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
            column(name: 'df_usage_sal_uid', value: 'ad91d122-aedc-4b41-a09e-f7f055f5cb85')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '395e1fc4-4634-472e-84fd-155230b96df6')
            column(name: 'df_usage_uid', value: '51e60822-5b4c-4fa8-9922-05e93065f216')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test batch for usage timings\' Batch')
            column(name: 'created_datetime', value: '2019-02-14 11:45:01.52369+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1231229e-67dc-48f9-aa48-21393fbe5800')
            column(name: 'df_usage_uid', value: '9793bfb8-2628-485a-9ceb-0d7652691a1b')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test batch for usage timings\' Batch')
            column(name: 'created_datetime', value: '2019-02-14 11:45:01.52369+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '8cbc9d4d-62b3-4431-9876-b2cfcaeffcf2')
            column(name: 'df_usage_uid', value: 'ad91d122-aedc-4b41-a09e-f7f055f5cb85')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test batch for usage timings\' Batch')
            column(name: 'created_datetime', value: '2019-02-14 11:45:01.52369+03')
        }

        rollback {
            dbRollback
        }
    }
}
