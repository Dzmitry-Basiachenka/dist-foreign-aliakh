databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-02-05-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testWriteUsagesCsvReport, testWriteUsagesEmptyCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '600ad926-e7dd-4086-b283-87e6579395ce')
            column(name: 'name', value: 'AACL batch 2020')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8315e53b-0a7e-452a-a62c-17fe959f3f84')
            column(name: 'df_usage_batch_uid', value: '600ad926-e7dd-4086-b283-87e6579395ce')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '8315e53b-0a7e-452a-a62c-17fe959f3f84')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '64194cf9-177f-4220-9eb5-01040324b8b2')
            column(name: 'df_usage_batch_uid', value: '600ad926-e7dd-4086-b283-87e6579395ce')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL classified usage 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '64194cf9-177f-4220-9eb5-01040324b8b2')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'detail_licensee_class_id', value: 120)
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'abced58b-fff2-470b-99fc-11f2a3ae098a')
            column(name: 'df_usage_batch_uid', value: '600ad926-e7dd-4086-b283-87e6579395ce')
            column(name: 'wr_wrk_inst', value: 122820420)
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '00087475')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 7001413934)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'abced58b-fff2-470b-99fc-11f2a3ae098a')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2018 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        rollback {
            dbRollback
        }
    }
}
