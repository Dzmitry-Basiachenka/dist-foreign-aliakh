databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-01-20-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testWriteUsagesForClassificationAndFindIds, testWriteUsagesForClassificationAndFindIdsEmptyReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'aed882d5-7625-4039-8781-a6676e11c579')
            column(name: 'name', value: 'AACL batch')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1208f434-3d98-49d5-bdc6-baa611d2d006')
            column(name: 'df_usage_batch_uid', value: 'aed882d5-7625-4039-8781-a6676e11c579')
            column(name: 'wr_wrk_inst', value: 122825976)
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '1208f434-3d98-49d5-bdc6-baa611d2d006')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0ac10a6f-1cf3-45b5-8d3b-0b4b0777a8e0')
            column(name: 'df_usage_batch_uid', value: 'aed882d5-7625-4039-8781-a6676e11c579')
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
            column(name: 'df_usage_aacl_uid', value: '0ac10a6f-1cf3-45b5-8d3b-0b4b0777a8e0')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2018 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '67d36799-5523-474d-91f6-2e12756a4918')
            column(name: 'df_usage_batch_uid', value: 'aed882d5-7625-4039-8781-a6676e11c579')
            column(name: 'wr_wrk_inst', value: 109713043)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '67d36799-5523-474d-91f6-2e12756a4918')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2018 TUR')
            column(name: 'number_of_pages', value: 6)
        }

        rollback {
            dbRollback
        }
    }
}
