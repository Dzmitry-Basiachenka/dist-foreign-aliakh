databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-11-03-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for testFindUsagePeriodsByFilter')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '31ac3937-157b-48c2-86b2-db28356fc868')
            column(name: 'name', value: 'AACL batch 1 for testFindUsagePeriodsByFilter')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e0fb5363-5dd4-434a-8d25-59edf9c6b2ce')
            column(name: 'df_usage_batch_uid', value: '31ac3937-157b-48c2-86b2-db28356fc868')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'e0fb5363-5dd4-434a-8d25-59edf9c6b2ce')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '44fdf430-4cd0-4a32-98df-88f6c995ab78')
            column(name: 'df_usage_batch_uid', value: '31ac3937-157b-48c2-86b2-db28356fc868')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '44fdf430-4cd0-4a32-98df-88f6c995ab78')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Aug 2019 FR')
            column(name: 'number_of_pages', value: 400)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '87279dd4-e100-4b72-a561-49e7effe8238')
            column(name: 'name', value: 'AACL batch 2 for testFindUsagePeriodsByFilter')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '77f3a72b-bbdd-4966-a11c-357b50282749')
            column(name: 'df_usage_batch_uid', value: '87279dd4-e100-4b72-a561-49e7effe8238')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '77f3a72b-bbdd-4966-a11c-357b50282749')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e7c2dc02-b89b-4932-ac27-1fe59c99086c')
            column(name: 'df_usage_batch_uid', value: '87279dd4-e100-4b72-a561-49e7effe8238')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'e7c2dc02-b89b-4932-ac27-1fe59c99086c')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2010)
            column(name: 'usage_source', value: 'Aug 2019 FR')
            column(name: 'number_of_pages', value: 400)
            column(name: 'right_limitation', value: 'ALL')
        }

        rollback {
            dbRollback
        }
    }
}
