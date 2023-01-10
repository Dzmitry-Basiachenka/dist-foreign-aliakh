databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-01-15-00', author: 'Ihar Suvorau<isuvorau@copyright.com>') {
        comment('Inserting test data for testUpdateAaclRights')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'd311340c-60e8-4df1-bbe1-788ba2ed9a15')
            column(name: 'rh_account_number', value: 1000023401)
            column(name: 'name', value: 'American City Business Journals, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'eb4b7bdb-3164-4e60-8d63-cae40c76de6e')
            column(name: 'name', value: 'AACL Usage Batch 2015')
            column(name: 'product_family', value: 'AACL')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b23cb103-9242-4d58-a65d-2634b3e5a8cf')
            column(name: 'df_usage_batch_uid', value: 'eb4b7bdb-3164-4e60-8d63-cae40c76de6e')
            column(name: 'wr_wrk_inst', value: 122803735)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'b23cb103-9242-4d58-a65d-2634b3e5a8cf')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: 12)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7e7b97d1-ad60-4d47-915b-2834c5cc056a')
            column(name: 'df_usage_batch_uid', value: 'eb4b7bdb-3164-4e60-8d63-cae40c76de6e')
            column(name: 'wr_wrk_inst', value: 130297955)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 1)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '7e7b97d1-ad60-4d47-915b-2834c5cc056a')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: 199)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '10c9a60f-28b6-466c-975c-3ea930089a9e')
            column(name: 'df_usage_batch_uid', value: 'eb4b7bdb-3164-4e60-8d63-cae40c76de6e')
            column(name: 'wr_wrk_inst', value: 200208329)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 18)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '10c9a60f-28b6-466c-975c-3ea930089a9e')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: 180)
        }

        rollback {
            dbRollback
        }
    }
}
