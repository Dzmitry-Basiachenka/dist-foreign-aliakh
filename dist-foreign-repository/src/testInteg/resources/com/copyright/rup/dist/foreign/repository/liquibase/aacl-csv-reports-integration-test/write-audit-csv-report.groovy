databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-03-11-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testWriteAuditCsvReport, testWriteAuditEmptyCsvReport, testWriteAuditCsvReportSearchBySqlLikePattern')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '2eb52c26-b555-45ae-b8c5-21289dfeeac4')
            column(name: 'wr_wrk_inst', value: 123986581)
            column(name: 'usage_period', value: 2016)
            column(name: 'usage_source', value: 'Aug 2016 FR')
            column(name: 'number_of_copies', value: 30)
            column(name: 'number_of_pages', value: 6)
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'publication_type_weight', value: 2)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage')
            column(name: 'updated_datetime', value: '2020-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '29689635-c6ff-483c-972d-09eb2febb9e0')
            column(name: 'name', value: 'AACL batch 2020 for audit')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c794662f-a5d6-4b86-8955-582723631656')
            column(name: 'df_usage_batch_uid', value: '29689635-c6ff-483c-972d-09eb2febb9e0')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'c794662f-a5d6-4b86-8955-582723631656')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3c96f468-abaa-4db7-9004-4012d8ba8e0d')
            column(name: 'df_usage_batch_uid', value: '29689635-c6ff-483c-972d-09eb2febb9e0')
            column(name: 'wr_wrk_inst', value: 123986581)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'comment', value: 'AACL audit usage 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '3c96f468-abaa-4db7-9004-4012d8ba8e0d')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'usage_period', value: 2016)
            column(name: 'usage_source', value: 'Aug 201 FR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
            column(name: 'baseline_uid', value: '2eb52c26-b555-45ae-b8c5-21289dfeeac4')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '027aa879-d963-41f1-bacf-db22ebe3584a')
            column(name: 'df_usage_batch_uid', value: '29689635-c6ff-483c-972d-09eb2febb9e0')
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
            column(name: 'df_usage_aacl_uid', value: '027aa879-d963-41f1-bacf-db22ebe3584a')
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
