databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-01-29-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for LoadClassifiedUsagesIntegrationTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '77cfa2dd-efac-48a9-bd5b-98659ff2265a')
            column(name: 'rh_account_number', value: '7001413934')
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a7e971e0-2bd1-484e-b769-4712752a5441')
            column(name: 'name', value: 'Test Batch')
            column(name: 'product_family', value: 'AACL')
            column(name: 'payment_date', value: '2019-06-30')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd5973116-2ea0-4808-80f5-93016e24cfa4')
            column(name: 'df_usage_batch_uid', value: 'a7e971e0-2bd1-484e-b769-4712752a5441')
            column(name: 'wr_wrk_inst', value: '122825976')
            column(name: 'rh_account_number', value: '7001413934')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'd5973116-2ea0-4808-80f5-93016e24cfa4')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2018 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '833aa413-ee36-4f1c-bea1-ec7a0f6e507d')
            column(name: 'df_usage_batch_uid', value: 'a7e971e0-2bd1-484e-b769-4712752a5441')
            column(name: 'wr_wrk_inst', value: '122825976')
            column(name: 'rh_account_number', value: '1000003578')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '833aa413-ee36-4f1c-bea1-ec7a0f6e507d')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        rollback ""
    }
}
