databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-01-28-01', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testUpdatePayeeByAccountNumber')

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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a87b82ca-cfca-463d-96e9-fa856618c389')
            column(name: 'name', value: 'AACL batch 6')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '66d10c81-705e-4996-89f4-11e1635c4c31')
            column(name: 'name', value: 'AACL Scenario 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'AACL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '248add96-93d0-428d-9fe2-2e46c237a88b')
            column(name: 'df_usage_batch_uid', value: 'a87b82ca-cfca-463d-96e9-fa856618c389')
            column(name: 'df_scenario_uid', value: '66d10c81-705e-4996-89f4-11e1635c4c31')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '248add96-93d0-428d-9fe2-2e46c237a88b')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '161652a5-d822-493b-8242-d35dc881646f')
            column(name: 'df_usage_batch_uid', value: 'a87b82ca-cfca-463d-96e9-fa856618c389')
            column(name: 'df_scenario_uid', value: '66d10c81-705e-4996-89f4-11e1635c4c31')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '161652a5-d822-493b-8242-d35dc881646f')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2017)
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '61b7dc5a-aaec-482c-8c16-fe48a9464059')
            column(name: 'df_usage_batch_uid', value: 'a87b82ca-cfca-463d-96e9-fa856618c389')
            column(name: 'df_scenario_uid', value: '66d10c81-705e-4996-89f4-11e1635c4c31')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL baseline usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '61b7dc5a-aaec-482c-8c16-fe48a9464059')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 1.71)
            column(name: 'baseline_uid', value: 'b012c81c-2370-458e-a6cb-349b66d08e6e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '9a1fa971-90ae-4744-ab93-6f584e08bc1e')
            column(name: 'df_scenario_uid', value: '66d10c81-705e-4996-89f4-11e1635c4c31')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '9a1fa971-90ae-4744-ab93-6f584e08bc1e')
            column(name: 'df_usage_batch_uid', value: 'a87b82ca-cfca-463d-96e9-fa856618c389')
        }

        rollback {
            dbRollback
        }
    }
}
