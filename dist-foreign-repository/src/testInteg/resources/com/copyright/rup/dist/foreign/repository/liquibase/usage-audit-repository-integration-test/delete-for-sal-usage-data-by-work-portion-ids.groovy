databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-02-14-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testDeleteForSalUsageDataByWorkPortionIds')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a0028bb6-7e84-420d-934f-eafa5ed0c5d6')
            column(name: 'name', value: 'SAL usage batch 4')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2015)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7a87ddf6-1a11-4f6f-8f52-96f520d2f859')
            column(name: 'df_usage_batch_uid', value: 'a0028bb6-7e84-420d-934f-eafa5ed0c5d6')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '7a87ddf6-1a11-4f6f-8f52-96f520d2f859')
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
            column(name: 'df_usage_uid', value: 'ebb0e28c-6c59-417d-9586-d252025e217d')
            column(name: 'df_usage_batch_uid', value: 'a0028bb6-7e84-420d-934f-eafa5ed0c5d6')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'ebb0e28c-6c59-417d-9586-d252025e217d')
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
            column(name: 'df_usage_uid', value: '078a6760-61d6-4d4e-a283-a67f7766d80c')
            column(name: 'df_usage_batch_uid', value: 'a0028bb6-7e84-420d-934f-eafa5ed0c5d6')
            column(name: 'wr_wrk_inst', value: 369040892)
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '078a6760-61d6-4d4e-a283-a67f7766d80c')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '7')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY17 EOC')
            column(name: 'assessment_type', value: 'EOC')
            column(name: 'reported_work_portion_id', value: '1101024IB2193')
            column(name: 'coverage_year', value: '2011-2012')
            column(name: 'scored_assessment_date', value: '2015-02-05')
            column(name: 'question_identifier', value: 'SB245')
            column(name: 'states', value: 'MN,OR')
            column(name: 'number_of_views', value: 254)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '660221fd-42e5-4ad2-b3ec-6c2056cf29c9')
            column(name: 'df_usage_uid', value: '7a87ddf6-1a11-4f6f-8f52-96f520d2f859')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test batch for usage timings\' Batch')
            column(name: 'created_datetime', value: '2019-02-14 11:45:01.52369+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'c497f1b9-ba53-4b18-a079-f3815f6e14bb')
            column(name: 'df_usage_uid', value: 'ebb0e28c-6c59-417d-9586-d252025e217d')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test batch for usage timings\' Batch')
            column(name: 'created_datetime', value: '2019-02-14 11:45:01.52369+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '5b92a477-c454-4dee-b8fa-dd4029cb76c0')
            column(name: 'df_usage_uid', value: '078a6760-61d6-4d4e-a283-a67f7766d80c')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test batch for usage timings\' Batch')
            column(name: 'created_datetime', value: '2019-02-14 11:45:01.52369+03')
        }

        rollback {
            dbRollback
        }
    }
}
