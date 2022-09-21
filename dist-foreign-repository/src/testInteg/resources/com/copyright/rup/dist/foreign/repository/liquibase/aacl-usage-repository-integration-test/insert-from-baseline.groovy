databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-27-02-01', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Insert test data for testInsertFromBaseline')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6e6f656a-e080-4426-b8ea-985b69f8814d')
            column(name: 'name', value: 'AACL batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '0085ceb9-6d2a-4a03-87a5-84e369dec6e1')
            column(name: 'wr_wrk_inst', value: 107039807)
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2019 FR')
            column(name: 'number_of_copies', value: 10)
            column(name: 'number_of_pages', value: 15)
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 1.71)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '5bcb69c5-5758-4cd2-8ca0-c8d63d3a9d18')
            column(name: 'wr_wrk_inst', value: 246997864)
            column(name: 'usage_period', value: 2018)
            column(name: 'usage_source', value: 'Aug 2018 FR')
            column(name: 'number_of_copies', value: 20)
            column(name: 'number_of_pages', value: 25)
            column(name: 'detail_licensee_class_id', value: 195)
            column(name: 'original_publication_type', value: 'Book series')
            column(name: 'df_publication_type_uid', value: 'a3dff475-fc06-4d8c-b7cc-f093073ada6f')
            column(name: 'publication_type_weight', value: 4.29)
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '819ad2fc-24fe-45b6-965f-5df633a57dba')
            column(name: 'wr_wrk_inst', value: 123986581)
            column(name: 'usage_period', value: 2016)
            column(name: 'usage_source', value: 'Aug 2016 FR')
            column(name: 'number_of_copies', value: 30)
            column(name: 'number_of_pages', value: 35)
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'publication_type_weight', value: 2)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage 3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'c7cb9533-0720-43ff-abfb-6e4513a942d6')
            column(name: 'wr_wrk_inst', value: 310652370)
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Aug 2015 FR')
            column(name: 'number_of_copies', value: 40)
            column(name: 'number_of_pages', value: 45)
            column(name: 'detail_licensee_class_id', value: 141)
            column(name: 'original_publication_type', value: 'Fiction or Poetry')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 3.5)
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage 4')
        }

        rollback {
            dbRollback
        }
    }
}
