databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-05-14-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for testIsValidForClassification, testIsValidForClassificationWithEligibleStatus,' +
                'testIsValidForClassificationWithUsagesFromBaseline, testIsValidForClassificationWithEligibleStatusAndUsagesFromBaseline')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '38718bfe-08bd-4af7-8481-0fe16dcf2750')
            column(name: 'name', value: 'AACL Usage Batch 3')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7d590095-6565-425a-939f-bec73b780d70')
            column(name: 'df_usage_batch_uid', value: '38718bfe-08bd-4af7-8481-0fe16dcf2750')
            column(name: 'wr_wrk_inst', value: 109040891)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 300)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '7d590095-6565-425a-939f-bec73b780d70')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 200)
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '11ab62fa-f1f1-4289-a5ad-5f9cfc219b0a')
            column(name: 'df_usage_batch_uid', value: '38718bfe-08bd-4af7-8481-0fe16dcf2750')
            column(name: 'wr_wrk_inst', value: 1000009522)
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 145)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '11ab62fa-f1f1-4289-a5ad-5f9cfc219b0a')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 250)
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '52d9c8b9-0de9-4578-915a-60ec01243fc4')
            column(name: 'name', value: 'AACL Usage Batch 4')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9085e28f-ce6f-412d-b344-049626341302')
            column(name: 'df_usage_batch_uid', value: '52d9c8b9-0de9-4578-915a-60ec01243fc4')
            column(name: 'wr_wrk_inst', value: 109040891)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 300)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '9085e28f-ce6f-412d-b344-049626341302')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 200)
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '345c4cc7-f6ad-4b12-bb79-62426ba1903e')
            column(name: 'df_usage_batch_uid', value: '52d9c8b9-0de9-4578-915a-60ec01243fc4')
            column(name: 'wr_wrk_inst', value: 1000009522)
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 145)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'b012c81c-2370-458e-a6cb-349b66d08e6e')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_copies', value: 10)
            column(name: 'number_of_pages', value: 12)
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 1.71)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '345c4cc7-f6ad-4b12-bb79-62426ba1903e')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 250)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'baseline_uid', value: 'b012c81c-2370-458e-a6cb-349b66d08e6e')
        }

        rollback {
            dbRollback
        }
    }
}
