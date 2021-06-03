databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-03-31-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindDetailLicenseeClassesByScenarioId')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '39548ee4-7929-477e-b9d2-bcb1e76f8037')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool')
            column(name: 'total_amount', value: '10.95')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '7dc93e89-d1f4-4721-81be-fd32606e4a66')
            column(name: 'df_fund_pool_uid', value: '39548ee4-7929-477e-b9d2-bcb1e76f8037')
            column(name: 'df_aggregate_licensee_class_id', value: '108')
            column(name: 'gross_amount', value: 10.95)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '91f3c9ba-1b61-4dcc-b087-f88f89d22c35')
            column(name: 'name', value: 'AACL Usage Batch')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '66d10c81-705e-4996-89f4-11e1635c4c31')
            column(name: 'name', value: 'AACL Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'aacl_fields', value: '{"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2f3988e1-7cca-42b2-bdf8-a8850dbf315b')
            column(name: 'df_usage_batch_uid', value: '91f3c9ba-1b61-4dcc-b087-f88f89d22c35')
            column(name: 'df_scenario_uid', value: '66d10c81-705e-4996-89f4-11e1635c4c31')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '2f3988e1-7cca-42b2-bdf8-a8850dbf315b')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'ceef1548-700e-48a2-a732-f9d183b3d2e3')
            column(name: 'df_scenario_uid', value: '66d10c81-705e-4996-89f4-11e1635c4c31')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'ceef1548-700e-48a2-a732-f9d183b3d2e3')
            column(name: 'df_usage_batch_uid', value: '91f3c9ba-1b61-4dcc-b087-f88f89d22c35')
        }

        rollback ""
    }
}
