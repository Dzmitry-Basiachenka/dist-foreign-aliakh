databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-04-01-02', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testExcludeZeroAmountUsages')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '9691fe58-b45a-4132-879e-1417eca14c1d')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool')
            column(name: 'total_amount', value: 1000)
            column(name: 'created_datetime', value: '2020-01-03 11:00:00-04')
            column(name: 'created_by_user', value: 'coordinator@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'efdad08b-831c-4ccf-87a5-b12af76bd811')
            column(name: 'df_fund_pool_uid', value: '9691fe58-b45a-4132-879e-1417eca14c1d')
            column(name: 'df_aggregate_licensee_class_id', value: 108)
            column(name: 'gross_amount', value: 500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '55feaaf2-0efa-44e5-93aa-50b34b65f1c0')
            column(name: 'df_fund_pool_uid', value: '9691fe58-b45a-4132-879e-1417eca14c1d')
            column(name: 'df_aggregate_licensee_class_id', value: 111)
            column(name: 'gross_amount', value: 500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'add4c0ae-d12e-4c5e-a04b-7f4b0ada3a23')
            column(name: 'name', value: 'AACL batch 9')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'baseline_years', value: 2)
            column(name: 'initial_usages_count', value: 6)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '8b01939c-abda-4090-86d1-6231fc20f679')
            column(name: 'name', value: 'AACL Scenario 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'aacl_fields', value: '{"fund_pool_uid": "9691fe58-b45a-4132-879e-1417eca14c1d",' +
                    '"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75 }],' +
                    '"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 108},{"detailLicenseeClassId": 111, "aggregateLicenseeClassId": 111},{"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 108}]}')
            column(name: 'description', value: 'AACL Scenario')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9ccf8b43-4ad5-4199-8c7f-c5884f27e44f')
            column(name: 'df_usage_batch_uid', value: 'add4c0ae-d12e-4c5e-a04b-7f4b0ada3a23')
            column(name: 'df_scenario_uid', value: '8b01939c-abda-4090-86d1-6231fc20f679')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000000026)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 1)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '9ccf8b43-4ad5-4199-8c7f-c5884f27e44f')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 2)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'publication_type_weight', value: 1)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'value_weight', value: 24.0000000)
            column(name: 'volume_weight', value: 5.0000000)
            column(name: 'volume_share', value: 50.0000000)
            column(name: 'value_share', value: 60.0000000)
            column(name: 'total_share', value: 2.0000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ccb115c7-3444-4dbb-9540-7541961febdf')
            column(name: 'df_usage_batch_uid', value: 'add4c0ae-d12e-4c5e-a04b-7f4b0ada3a23')
            column(name: 'df_scenario_uid', value: '8b01939c-abda-4090-86d1-6231fc20f679')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'payee_account_number', value: 1000000026)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 2)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'ccb115c7-3444-4dbb-9540-7541961febdf')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 1)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'publication_type_weight', value: 1)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'value_weight', value: 24.0000000)
            column(name: 'volume_weight', value: 5.0000000)
            column(name: 'volume_share', value: 50.0000000)
            column(name: 'value_share', value: 60.0000000)
            column(name: 'total_share', value: 2.0000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '83be7d3e-b4c0-4512-b4d4-230f6392ef5e')
            column(name: 'df_usage_batch_uid', value: 'add4c0ae-d12e-4c5e-a04b-7f4b0ada3a23')
            column(name: 'df_scenario_uid', value: '8b01939c-abda-4090-86d1-6231fc20f679')
            column(name: 'wr_wrk_inst', value: 107039807)
            column(name: 'rh_account_number', value: 7000813806)
            column(name: 'payee_account_number', value: 7000813806)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '83be7d3e-b4c0-4512-b4d4-230f6392ef5e')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 50)
            column(name: 'right_limitation', value: 'ALL')
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'publication_type_weight', value: 1)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'value_weight', value: 24.0000000)
            column(name: 'volume_weight', value: 5.0000000)
            column(name: 'volume_share', value: 50.0000000)
            column(name: 'value_share', value: 60.0000000)
            column(name: 'total_share', value: 2.0000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b28df2d4-359a-4165-8936-fc0c0bdf4ba9')
            column(name: 'df_usage_batch_uid', value: 'add4c0ae-d12e-4c5e-a04b-7f4b0ada3a23')
            column(name: 'df_scenario_uid', value: '8b01939c-abda-4090-86d1-6231fc20f679')
            column(name: 'wr_wrk_inst', value: 107039807)
            column(name: 'rh_account_number', value: 7000813806)
            column(name: 'payee_account_number', value: 7000813806)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 5)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'b28df2d4-359a-4165-8936-fc0c0bdf4ba9')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 10)
            column(name: 'right_limitation', value: 'ALL')
            column(name: 'detail_licensee_class_id', value: 111)
            column(name: 'publication_type_weight', value: 1)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'value_weight', value: 24.0000000)
            column(name: 'volume_weight', value: 5.0000000)
            column(name: 'volume_share', value: 50.0000000)
            column(name: 'value_share', value: 60.0000000)
            column(name: 'total_share', value: 2.0000000)
        }

        //To Exclude by zero amount
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd88c445f-4b4d-45ef-bc95-fdb9c4ba676d')
            column(name: 'df_usage_batch_uid', value: 'add4c0ae-d12e-4c5e-a04b-7f4b0ada3a23')
            column(name: 'df_scenario_uid', value: '8b01939c-abda-4090-86d1-6231fc20f679')
            column(name: 'wr_wrk_inst', value: 107039807)
            column(name: 'rh_account_number', value: 7000813806)
            column(name: 'gross_amount', value: 0.00)
            column(name: 'net_amount', value: 0.00)
            column(name: 'service_fee_amount', value: 0.00)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 5)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'd88c445f-4b4d-45ef-bc95-fdb9c4ba676d')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 10)
            column(name: 'right_limitation', value: 'ALL')
            column(name: 'detail_licensee_class_id', value: 115)
            column(name: 'publication_type_weight', value: 1)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '335dd803-3763-4fd7-ae78-31821a453fbf')
            column(name: 'df_usage_batch_uid', value: 'add4c0ae-d12e-4c5e-a04b-7f4b0ada3a23')
            column(name: 'df_scenario_uid', value: '8b01939c-abda-4090-86d1-6231fc20f679')
            column(name: 'wr_wrk_inst', value: 107039807)
            column(name: 'rh_account_number', value: 7000813806)
            column(name: 'gross_amount', value: 0.00)
            column(name: 'net_amount', value: 0.00)
            column(name: 'service_fee_amount', value: 0.00)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 5)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '335dd803-3763-4fd7-ae78-31821a453fbf')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 10)
            column(name: 'right_limitation', value: 'ALL')
            column(name: 'detail_licensee_class_id', value: 115)
            column(name: 'publication_type_weight', value: 1)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '9588f724-5494-4c26-b432-608b81a7756e')
            column(name: 'df_scenario_uid', value: '8b01939c-abda-4090-86d1-6231fc20f679')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '9588f724-5494-4c26-b432-608b81a7756e')
            column(name: 'df_usage_batch_uid', value: 'add4c0ae-d12e-4c5e-a04b-7f4b0ada3a23')
        }

        rollback {
            dbRollback
        }
    }
}
