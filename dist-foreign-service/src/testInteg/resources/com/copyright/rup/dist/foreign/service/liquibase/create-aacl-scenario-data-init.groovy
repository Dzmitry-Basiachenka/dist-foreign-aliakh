databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-03-17-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Insert test data for testCreateAaclScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '60080587-a225-439c-81af-f016cb33aeac')
            column(name: 'rh_account_number', value: 2000133267)
            column(name: 'name', value: '101 Communications, Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b0e6b1f6-89e9-4767-b143-db0f49f32769')
            column(name: 'rh_account_number', value: 2000073957)
            column(name: 'name', value: '1st Contact Publishing')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '9905f006-a3e1-4061-b3d4-e7ece191103f')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '019acfde-91be-43aa-8871-6305642bcb2c')
            column(name: 'rh_account_number', value: 1000024497)
            column(name: 'name', value: 'White Horse Press')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '753bd683-1db2-47ec-8332-136139c512d0')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool')
            column(name: 'total_amount', value: 100.00)
            column(name: 'created_datetime', value: '2020-01-03 11:00:00-04')
            column(name: 'created_by_user', value: 'coordinator@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '28a6098d-abda-4e4b-853b-a173cbe22073')
            column(name: 'df_fund_pool_uid', value: '753bd683-1db2-47ec-8332-136139c512d0')
            column(name: 'df_aggregate_licensee_class_id', value: 141)
            column(name: 'gross_amount', value: 80.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '8e001626-2532-42ca-b6d7-d665f6cb795a')
            column(name: 'df_fund_pool_uid', value: '753bd683-1db2-47ec-8332-136139c512d0')
            column(name: 'df_aggregate_licensee_class_id', value: 143)
            column(name: 'gross_amount', value: 20.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'ea7b6e8d-8454-4052-b639-c0fdb0a3145c')
            column(name: 'name', value: 'AACL batch')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 5)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4aa7d89f-9721-4ad3-a9ab-e267743a2851')
            column(name: 'df_usage_batch_uid', value: 'ea7b6e8d-8454-4052-b639-c0fdb0a3145c')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'rh_account_number', value: 2000133267)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL comment 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '4aa7d89f-9721-4ad3-a9ab-e267743a2851')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd5331924-72e9-43e7-9a7b-446ac09cff53')
            column(name: 'df_usage_batch_uid', value: 'ea7b6e8d-8454-4052-b639-c0fdb0a3145c')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'rh_account_number', value: 2000073957)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL comment 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'd5331924-72e9-43e7-9a7b-446ac09cff53')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2017)
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'ALL')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a96d23a9-a8e7-48e2-9f0e-c0a1ce3a0eab')
            column(name: 'df_usage_batch_uid', value: 'ea7b6e8d-8454-4052-b639-c0fdb0a3145c')
            column(name: 'wr_wrk_inst', value: 100010768)
            column(name: 'rh_account_number', value: 1000024497)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 1)
            column(name: 'comment', value: 'AACL comment 3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'a96d23a9-a8e7-48e2-9f0e-c0a1ce3a0eab')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2017)
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'number_of_pages', value: 1)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'f517f5b9-5f34-41f6-8984-67eeadf65ad1')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'usage_period', value: 2017)
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'number_of_copies', value: 10)
            column(name: 'number_of_pages', value: 12)
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 2.71)
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2b969cb4-4257-4674-b329-f627d60f5c0d')
            column(name: 'df_usage_batch_uid', value: 'ea7b6e8d-8454-4052-b639-c0fdb0a3145c')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'rh_account_number', value: 2000073957)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL baseline usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '2b969cb4-4257-4674-b329-f627d60f5c0d')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2017)
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'ALL')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 2.71)
            column(name: 'baseline_uid', value: 'f517f5b9-5f34-41f6-8984-67eeadf65ad1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c71c0c80-57c0-43d1-9c40-432c7cd112c6')
            column(name: 'df_usage_batch_uid', value: 'ea7b6e8d-8454-4052-b639-c0fdb0a3145c')
            column(name: 'wr_wrk_inst', value: 100010768)
            column(name: 'rh_account_number', value: 2000073957)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 1)
            column(name: 'comment', value: 'AACL baseline usage 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '9aeb87ed-5000-4bee-abcb-e3dfc4f84235')
            column(name: 'wr_wrk_inst', value: 100010768)
            column(name: 'usage_period', value: 2017)
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'number_of_copies', value: 1)
            column(name: 'number_of_pages', value: 1)
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 1.71)
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'c71c0c80-57c0-43d1-9c40-432c7cd112c6')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2017)
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'number_of_pages', value: 1)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 1.71)
            column(name: 'baseline_uid', value: '9aeb87ed-5000-4bee-abcb-e3dfc4f84235')
        }
    }
}
