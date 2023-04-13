databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-12-15-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Insert test data for testDeleteByBatchId')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'e0706545-6b96-489e-88c8-c314f510d4bc')
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'name', value: 'Georgia State University Business Press [C]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b7a5e188-9210-40fa-a04e-ac2051d31883')
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'c81b2533-0ad6-46a3-b236-159230ffce49')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'name', value: 'Access Copyright, The Canadian Copyright Agency')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0c773ba8-9f3f-47d0-bfeb-95ab92c02523')
            column(name: 'name', value: 'ACLCI usage batch 1')
            column(name: 'payment_date', value: '2022-06-30')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'aclci_fields', value: '{"licensee_account_number": 5555, "licensee_name": "Great Lakes Chemical Corporation"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ec6919f3-334d-403c-b3d8-81b67746dab9')
            column(name: 'df_usage_batch_uid', value: '0c773ba8-9f3f-47d0-bfeb-95ab92c02523')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'standard_number', value: '09639292')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
            column(name: 'system_title', value: 'Telling stories')
            column(name: 'comment', value: '1 comment')
            column(name: 'standard_number_type', value: 'VALISSN')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aclci') {
            column(name: 'df_usage_aclci_uid', value: 'ec6919f3-334d-403c-b3d8-81b67746dab9')
            column(name: 'coverage_period', value: '2021-2022')
            column(name: 'license_type', value: 'CURR_REPUB_K12')
            column(name: 'reported_media_type', value: 'Image')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'reported_article', value: 'The Bronze Statue')
            column(name: 'reported_standard_number', value: '978-0-7614-1880-1')
            column(name: 'reported_author', value: 'Hugh Lupton')
            column(name: 'reported_publisher', value: 'Barefoot Books')
            column(name: 'reported_publication_date', value: '2022-02-14')
            column(name: 'reported_grade', value: '6')
            column(name: 'grade_group', value: 'GRADE6_8')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '439c76a2-e9b3-4d94-816f-6c714b126c98')
            column(name: 'df_usage_batch_uid', value: '0c773ba8-9f3f-47d0-bfeb-95ab92c02523')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'standard_number', value: '09639291')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'comment', value: '2 comment')
            column(name: 'standard_number_type', value: 'VALISSNB')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aclci') {
            column(name: 'df_usage_aclci_uid', value: '439c76a2-e9b3-4d94-816f-6c714b126c98')
            column(name: 'coverage_period', value: '2020-2022')
            column(name: 'license_type', value: 'CURR_REUSE_K12')
            column(name: 'reported_media_type', value: 'Text')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'reported_article', value: 'Medical Journal')
            column(name: 'reported_standard_number', value: '978-1-873047-26-2')
            column(name: 'reported_author', value: 'Associated Press')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2021-02-14')
            column(name: 'reported_grade', value: '6')
            column(name: 'grade_group', value: 'GRADE6_5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3fef1e73-96d4-408d-a60c-bfa23dfce319')
            column(name: 'name', value: 'ACLCI usage batch 2')
            column(name: 'payment_date', value: '2021-06-30')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'aclci_fields', value: '{"licensee_account_number": 5588, "licensee_name": "RGS Energy Group, Inc."}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '732cc702-027f-4108-81ab-0f8820857133')
            column(name: 'df_usage_batch_uid', value: '3fef1e73-96d4-408d-a60c-bfa23dfce319')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'standard_number', value: '09639291')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'comment', value: '2 comment')
            column(name: 'standard_number_type', value: 'VALISSNB')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aclci') {
            column(name: 'df_usage_aclci_uid', value: '732cc702-027f-4108-81ab-0f8820857133')
            column(name: 'coverage_period', value: '2020-2022')
            column(name: 'license_type', value: 'CURR_REUSE_K12')
            column(name: 'reported_media_type', value: 'Text')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'reported_article', value: 'Medical Journal')
            column(name: 'reported_standard_number', value: '978-1-873047-26-2')
            column(name: 'reported_author', value: 'Associated Press')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2021-02-14')
            column(name: 'reported_grade', value: '3')
            column(name: 'grade_group', value: 'GRADE3_5')
        }

        rollback {
            dbRollback
        }
    }
}
