databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-01-27-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Insert test data for testIsValidFilteredUsageStatus')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '38e3190a-cf2b-4a2a-8a14-1f6e5f09011c')
            column(name: 'name', value: 'AACL batch 2')
            column(name: 'payment_date', value: '2018-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ce439b92-dfe1-4607-9cba-01394cbfc087')
            column(name: 'df_usage_batch_uid', value: '38e3190a-cf2b-4a2a-8a14-1f6e5f09011c')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 1)
            column(name: 'comment', value: 'AACL NEW status')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'ce439b92-dfe1-4607-9cba-01394cbfc087')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2018)
            column(name: 'usage_source', value: 'Feb 2018 TUR')
            column(name: 'number_of_pages', value: 341)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'adcc460c-c4ae-4750-99e8-b9fe91787ce1')
            column(name: 'name', value: 'AACL batch 3')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '44600a96-5cf5-4e11-80df-d52caddd33aa')
            column(name: 'df_usage_batch_uid', value: 'adcc460c-c4ae-4750-99e8-b9fe91787ce1')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL comment 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '44600a96-5cf5-4e11-80df-d52caddd33aa')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '67750f86-3077-4857-b2f6-af31c3d3d5b1')
            column(name: 'df_usage_batch_uid', value: 'adcc460c-c4ae-4750-99e8-b9fe91787ce1')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL comment 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '67750f86-3077-4857-b2f6-af31c3d3d5b1')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd2ec8772-a343-4c5a-b7e3-7d0ade0be21b')
            column(name: 'df_usage_batch_uid', value: 'adcc460c-c4ae-4750-99e8-b9fe91787ce1')
            column(name: 'wr_wrk_inst', value: 987654321)
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 1)
            column(name: 'comment', value: 'AACL NEW status')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'd2ec8772-a343-4c5a-b7e3-7d0ade0be21b')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2018 TUR')
            column(name: 'number_of_pages', value: 341)
        }

        rollback {
            dbRollback
        }
    }
}
