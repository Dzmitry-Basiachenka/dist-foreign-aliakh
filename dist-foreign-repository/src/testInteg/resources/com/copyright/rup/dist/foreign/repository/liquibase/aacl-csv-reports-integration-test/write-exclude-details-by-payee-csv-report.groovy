databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-02-10-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Insert test data for testWriteExcludeDetailsByPayeeCsvReportNoData, testWriteExcludeDetailsByPayeeCsvReportEmptyFilter, ' +
                'testWriteExcludeDetailsByPayeeCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8fb69838-9f62-456f-ad52-58b55d71c305')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8aafed93-6964-41f6-be6e-f5e628c03ece')
            column(name: 'rh_account_number', value: 1000011881)
            column(name: 'name', value: 'William B. Eerdmans Publishing Company')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '872714ee-51f0-4f37-9408-6b72f3e0b081')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool for Write Aacl Exclude Details By Payee Csv Report test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '4eaa3d24-ed4e-427a-b490-147e83990c4f')
            column(name: 'df_fund_pool_uid', value: '872714ee-51f0-4f37-9408-6b72f3e0b081')
            column(name: 'df_aggregate_licensee_class_id', value: 171)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '50db6fa9-82ed-418b-9d7b-6c65eaedddc2')
            column(name: 'name', value: 'AACL Usage Batch for Write Aacl Exclude Details By Payee Csv Report test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 4)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '281ee7fa-7a94-48f9-9865-10ff69e94f07')
            column(name: 'name', value: 'AACL Scenario for Write Aacl Exclude Details By Payee Csv Report test')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "fund_pool_uid": "872714ee-51f0-4f37-9408-6b72f3e0b081", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8e7661c3-2a79-4504-92d9-576a5817c7b2')
            column(name: 'df_usage_batch_uid', value: '50db6fa9-82ed-418b-9d7b-6c65eaedddc2')
            column(name: 'df_scenario_uid', value: '281ee7fa-7a94-48f9-9865-10ff69e94f07')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '8e7661c3-2a79-4504-92d9-576a5817c7b2')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 680.0000000000)
            column(name: 'volume_weight', value: 10.0000000000)
            column(name: 'volume_share', value: 0.4098360656)
            column(name: 'value_share', value: 0.3970571062)
            column(name: 'total_share', value: 0.4095917984)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1cdcc16a-d633-4c54-8714-f06355a7f7af')
            column(name: 'df_usage_batch_uid', value: '50db6fa9-82ed-418b-9d7b-6c65eaedddc2')
            column(name: 'df_scenario_uid', value: '281ee7fa-7a94-48f9-9865-10ff69e94f07')
            column(name: 'wr_wrk_inst', value: 124181386)
            column(name: 'work_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'system_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000011881)
            column(name: 'payee_account_number', value: 1000011881)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '1cdcc16a-d633-4c54-8714-f06355a7f7af')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 791.8000000000)
            column(name: 'volume_weight', value: 3.7000000000)
            column(name: 'volume_share', value: 0.1516393443)
            column(name: 'value_share', value: 0.4623379657)
            column(name: 'total_share', value: 0.3093360901)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c5dd16b5-f61e-474d-b584-87bebae98b36')
            column(name: 'df_usage_batch_uid', value: '50db6fa9-82ed-418b-9d7b-6c65eaedddc2')
            column(name: 'df_scenario_uid', value: '281ee7fa-7a94-48f9-9865-10ff69e94f07')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'c5dd16b5-f61e-474d-b584-87bebae98b36')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 240.8000000000)
            column(name: 'volume_weight', value: 10.7000000000)
            column(name: 'volume_share', value: 0.4385245902)
            column(name: 'value_share', value: 0.1406049282)
            column(name: 'total_share', value: 0.2810721113)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0d7ef44c-f990-44ac-9c80-cfe9590684b9')
            column(name: 'df_usage_batch_uid', value: '50db6fa9-82ed-418b-9d7b-6c65eaedddc2')
            column(name: 'df_scenario_uid', value: '281ee7fa-7a94-48f9-9865-10ff69e94f07')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '0d7ef44c-f990-44ac-9c80-cfe9590684b9')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'value_weight', value: 240.8000000000)
            column(name: 'volume_weight', value: 10.7000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'total_share', value: 1.0000000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        rollback {
            dbRollback
        }
    }
}
