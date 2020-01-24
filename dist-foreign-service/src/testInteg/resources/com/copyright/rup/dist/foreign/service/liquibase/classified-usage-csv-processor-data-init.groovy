databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-01-24-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for ClassifiedUsageCsvProcessorIntegrationTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'name', value: 'Test Batch')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'product_family', value: 'AACL')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0ac10a6f-1cf3-45b5-8d3b-0b4b0777a8e0')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '122820420')
            column(name: 'rh_account_number', value: '7001413934')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '0ac10a6f-1cf3-45b5-8d3b-0b4b0777a8e0')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2018 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1208f434-3d98-49d5-bdc6-baa611d2d006')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '122825976')
            column(name: 'rh_account_number', value: '1000003578')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '1208f434-3d98-49d5-bdc6-baa611d2d006')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        rollback ""
    }
}
