databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-09-24-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testUsageDetailsExist')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'cb932497-086d-4a7e-9b34-e9a62f17adab4')
            column(name: 'name', value: 'SAL usage batch with usage details')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8edafa8e-a395-4775-94c4-b5da6c400228')
            column(name: 'df_usage_batch_uid', value: 'cb932497-086d-4a7e-9b34-e9a62f17adab4')
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
            column(name: 'states', value: 'SD;VT')
            column(name: 'number_of_views', value: 762)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b0e669d2-68d0-4add-9946-34215011f74b')
            column(name: 'name', value: 'SAL usage batch with usage details 2')
            column(name: 'payment_date', value: '2016-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'sal_fields', value: '{"licensee_account_number": 1000000009, "licensee_name": "Tiger Publications"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e3b4dfc5-a208-4b6e-996a-c667c705a08d')
            column(name: 'df_usage_batch_uid', value: 'b0e669d2-68d0-4add-9946-34215011f74b')
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
            column(name: 'states', value: 'MN;OR')
            column(name: 'number_of_views', value: 254)
        }

        rollback {
            dbRollback
        }
    }
}
