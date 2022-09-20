databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-13-02-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for testDeleteByBatchId')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '940ca71c-fd90-4ffd-aa20-b293c0f49891')
            column(name: 'name', value: 'AACL batch')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8919261f-ff5d-4a66-a5b9-02138ab13e11')
            column(name: 'df_usage_batch_uid', value: '940ca71c-fd90-4ffd-aa20-b293c0f49891')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '8919261f-ff5d-4a66-a5b9-02138ab13e11')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3088f483-d1ef-4a6e-a076-a291152762c9')
            column(name: 'df_usage_batch_uid', value: '940ca71c-fd90-4ffd-aa20-b293c0f49891')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '3088f483-d1ef-4a6e-a076-a291152762c9')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2019 FR')
            column(name: 'number_of_pages', value: 400)
            column(name: 'right_limitation', value: 'ALL')
        }

        rollback {
            dbRollback
        }
    }
}
