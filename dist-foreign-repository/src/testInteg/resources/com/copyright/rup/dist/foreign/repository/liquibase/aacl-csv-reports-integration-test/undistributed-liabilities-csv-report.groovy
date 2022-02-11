databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-06-17-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for testUndistributedLiabilitiesCsvReport')

        //Should be included into report as it isn't associated with any scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '2a3aac29-6694-48fe-8c5d-c6709614ae73')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 1 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'a463d517-0887-4d68-9422-a99e8997ddd5')
            column(name: 'df_fund_pool_uid', value: '2a3aac29-6694-48fe-8c5d-c6709614ae73')
            column(name: 'df_aggregate_licensee_class_id', value: 171)
            column(name: 'gross_amount', value: 100.00)
        }

        //Should be included into report as it is associated with SUBMITTED scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '6b2ba3de-f2a7-4d9b-8da1-d84118ddba30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 2 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '044f4190-5c0e-41e5-994b-983b7810ea74')
            column(name: 'df_fund_pool_uid', value: '6b2ba3de-f2a7-4d9b-8da1-d84118ddba30')
            column(name: 'df_aggregate_licensee_class_id', value: 141)
            column(name: 'gross_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '4d6adcc5-9852-4322-b946-88e0ba977620')
            column(name: 'name', value: 'AACL Usage Batch 2 for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '43242b46-1a85-4938-9865-b9b354b6ae44')
            column(name: 'name', value: 'AACL Scenario 2 for Undistributed Liabilities Report test')
            column(name: 'status_ind', value: 'SUBMITTED')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}], "fund_pool_uid": "6b2ba3de-f2a7-4d9b-8da1-d84118ddba30", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3a70aab1-221c-46cd-89e2-417d0765fba2')
            column(name: 'df_usage_batch_uid', value: '4d6adcc5-9852-4322-b946-88e0ba977620')
            column(name: 'df_scenario_uid', value: '43242b46-1a85-4938-9865-b9b354b6ae44')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
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
            column(name: 'df_usage_aacl_uid', value: '3a70aab1-221c-46cd-89e2-417d0765fba2')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 171)
            column(name: 'value_weight', value: 5.0000000)
            column(name: 'volume_weight', value: 54.0000000)
            column(name: 'volume_share', value: 1.0000000)
            column(name: 'value_share', value: 1.0000000)
            column(name: 'total_share', value: 1.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        //Should be included into report as it is associated with APPROVED scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '404ba914-3c57-4551-867b-8bd4a1fdd8f2')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 3 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '24ddaead-40b3-47d9-af51-826878a6443e')
            column(name: 'df_fund_pool_uid', value: '404ba914-3c57-4551-867b-8bd4a1fdd8f2')
            column(name: 'df_aggregate_licensee_class_id', value: 141)
            column(name: 'gross_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '1225cfc8-6b5e-48f2-b1a3-e4a887446532')
            column(name: 'name', value: 'AACL Usage Batch 3 for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '50fcaabc-b12f-421d-aaf1-0b4e147d7540')
            column(name: 'name', value: 'AACL Scenario 3 for Undistributed Liabilities Report test')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}], "fund_pool_uid": "404ba914-3c57-4551-867b-8bd4a1fdd8f2", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description 3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f5bc77ec-1be3-4867-a5ef-98b0a49b0d4e')
            column(name: 'df_usage_batch_uid', value: '1225cfc8-6b5e-48f2-b1a3-e4a887446532')
            column(name: 'df_scenario_uid', value: '50fcaabc-b12f-421d-aaf1-0b4e147d7540')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
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
            column(name: 'df_usage_aacl_uid', value: 'f5bc77ec-1be3-4867-a5ef-98b0a49b0d4e')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 171)
            column(name: 'value_weight', value: 5.0000000)
            column(name: 'volume_weight', value: 54.0000000)
            column(name: 'volume_share', value: 1.0000000)
            column(name: 'value_share', value: 1.0000000)
            column(name: 'total_share', value: 1.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        //Shouldn't be included into report as it is associated with SENT_TO_LM scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '66bbea66-84e7-41cd-a5aa-9fd43f03dd5a')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 4 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '9124723e-bbd0-4750-bfad-764e7b5601e5')
            column(name: 'df_fund_pool_uid', value: '66bbea66-84e7-41cd-a5aa-9fd43f03dd5a')
            column(name: 'df_aggregate_licensee_class_id', value: 141)
            column(name: 'gross_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6fcdd8f0-bb76-4dfa-9afe-00352cdef0d3')
            column(name: 'name', value: 'AACL Usage Batch 4 for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'c5524e13-49a7-4057-8842-e8c3a8ad78cf')
            column(name: 'name', value: 'AACL Scenario 4 for Undistributed Liabilities Report test')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}], "fund_pool_uid": "66bbea66-84e7-41cd-a5aa-9fd43f03dd5a", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description 4')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '2c540f3a-0d43-4ca9-b1d2-73bb85774e43')
            column(name: 'df_usage_batch_uid', value: '6fcdd8f0-bb76-4dfa-9afe-00352cdef0d3')
            column(name: 'df_scenario_uid', value: 'c5524e13-49a7-4057-8842-e8c3a8ad78cf')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'number_of_copies', value: 155)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '2c540f3a-0d43-4ca9-b1d2-73bb85774e43')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 171)
            column(name: 'value_weight', value: 5.0000000)
            column(name: 'volume_weight', value: 54.0000000)
            column(name: 'volume_share', value: 1.0000000)
            column(name: 'value_share', value: 1.0000000)
            column(name: 'total_share', value: 1.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        //Shouldn't be included into report as it is associated with ARCHIVED scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3c79d8ee-42ef-4973-bdad-0a27d75504c9')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 5 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'ead693bc-e108-4d95-b660-17492b178823')
            column(name: 'df_fund_pool_uid', value: '3c79d8ee-42ef-4973-bdad-0a27d75504c9')
            column(name: 'df_aggregate_licensee_class_id', value: 141)
            column(name: 'gross_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3bf8b930-a2cb-4af0-99e2-e69edd176450')
            column(name: 'name', value: 'AACL Usage Batch 5 for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '75fe53a0-ccf4-404c-a4b5-143c88599fa0')
            column(name: 'name', value: 'AACL Scenario 5 for Undistributed Liabilities Report test')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}], "fund_pool_uid": "3c79d8ee-42ef-4973-bdad-0a27d75504c9", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description 5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '381fcb77-8e28-4ab5-8f2d-ee295c2cf9e7')
            column(name: 'df_usage_batch_uid', value: '3bf8b930-a2cb-4af0-99e2-e69edd176450')
            column(name: 'df_scenario_uid', value: '75fe53a0-ccf4-404c-a4b5-143c88599fa0')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'number_of_copies', value: 155)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '381fcb77-8e28-4ab5-8f2d-ee295c2cf9e7')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 171)
            column(name: 'value_weight', value: 5.0000000)
            column(name: 'volume_weight', value: 54.0000000)
            column(name: 'volume_share', value: 1.0000000)
            column(name: 'value_share', value: 1.0000000)
            column(name: 'total_share', value: 1.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        rollback {
            dbRollback
        }
    }
}
