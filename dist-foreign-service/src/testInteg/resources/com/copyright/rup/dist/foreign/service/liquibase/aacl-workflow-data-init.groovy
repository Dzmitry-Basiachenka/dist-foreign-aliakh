databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-03-03-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting data for testAaclWorkflow')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '60080587-a225-439c-81af-f016cb33aeac')
            column(name: 'rh_account_number', value: 1000024950)
            column(name: 'name', value: '101 Communications, Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a02af8f9-2ee4-4045-8bb5-79529fc087a6')
            column(name: 'rh_account_number', value: 7001508482)
            column(name: 'name', value: '2000 BC Publishing Ltd')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '4f466373-e8de-4b7d-8137-faba84ccafc2')
            column(name: 'wr_wrk_inst', value: 100009840)
            column(name: 'usage_period', value: 2018)
            column(name: 'usage_source', value: 'Aug 2018 FR')
            column(name: 'number_of_copies', value: 10)
            column(name: 'number_of_pages', value: 15)
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 1.71)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage Comment 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'd20e6f4b-bcb5-46f2-a247-165a8909e726')
            column(name: 'wr_wrk_inst', value: 100010768)
            column(name: 'usage_period', value: 2018)
            column(name: 'usage_source', value: 'Aug 2018 FR')
            column(name: 'number_of_copies', value: 20)
            column(name: 'number_of_pages', value: 25)
            column(name: 'detail_licensee_class_id', value: 195)
            column(name: 'original_publication_type', value: 'Book series')
            column(name: 'df_publication_type_uid', value: 'a3dff475-fc06-4d8c-b7cc-f093073ada6f')
            column(name: 'publication_type_weight', value: 4.29)
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage Comment 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'e783ce65-63b4-4a07-9c8d-442b56a5d076')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'usage_period', value: 2016)
            column(name: 'usage_source', value: 'Aug 2016 FR')
            column(name: 'number_of_copies', value: 30)
            column(name: 'number_of_pages', value: 35)
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'publication_type_weight', value: 2)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage Comment 3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'dea79da5-7fbd-4a4d-8c91-c71615a30d59')
            column(name: 'wr_wrk_inst', value: 100009840)
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Aug 2015 FR')
            column(name: 'number_of_copies', value: 40)
            column(name: 'number_of_pages', value: 45)
            column(name: 'detail_licensee_class_id', value: 164)
            column(name: 'original_publication_type', value: 'Database or Reference Book')
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'publication_type_weight', value: 1.47)
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage that should not be pulled')
        }

        rollback {
            dbRollback
        }
    }
}
