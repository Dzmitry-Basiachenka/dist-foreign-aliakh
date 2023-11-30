databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-04-13-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testWriteUsagesCsvReport, testWriteUsagesEmptyCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5d0ca59d-d258-4d4a-afdb-554e7ee22b49')
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'name', value: 'Georgia State University Business Press [C]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'f61bce28-8fe4-4639-a2a5-eda2a7e45c79')
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '55e796b2-fbb6-4372-a3c4-9965bc431e10')
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'name', value: 'Access Copyright, The Canadian Copyright Agency')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '5ac83c1b-0287-407e-b237-b06734fddec1')
            column(name: 'name', value: 'ACLCI Usage Batch 1')
            column(name: 'payment_date', value: '2022-06-30')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'aclci_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4a4ee7fe-9d58-4f82-986c-99b80df0f193')
            column(name: 'df_usage_batch_uid', value: '5ac83c1b-0287-407e-b237-b06734fddec1')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'system_title', value: 'Telling stories')
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'comment', value: 'some comment')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aclci') {
            column(name: 'df_usage_aclci_uid', value: '4a4ee7fe-9d58-4f82-986c-99b80df0f193')
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
            column(name: 'reported_number_of_students', value: '6')
            column(name: 'grade_group', value: 'GRADE_M')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a33bdc18-77b5-47de-9c5d-1df434c3c6bf')
            column(name: 'df_usage_batch_uid', value: '5ac83c1b-0287-407e-b237-b06734fddec1')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'standard_number', value: '09639291')
            column(name: 'standard_number_type', value: 'VALISSNB')
            column(name: 'comment', value: 'another comment')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aclci') {
            column(name: 'df_usage_aclci_uid', value: 'a33bdc18-77b5-47de-9c5d-1df434c3c6bf')
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
            column(name: 'reported_number_of_students', value: '7')
            column(name: 'grade_group', value: 'GRADE_M')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6e5b6705-a5d0-4993-bec5-5f7560dce5d0')
            column(name: 'name', value: 'ACLCI Usage Batch 2')
            column(name: 'payment_date', value: '2021-06-30')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'aclci_fields', value: '{"licensee_account_number": 1000000009, "licensee_name": "Tiger Publications"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '71a2559b-2dc7-4d4f-b841-0424e3017d63')
            column(name: 'df_usage_batch_uid', value: '6e5b6705-a5d0-4993-bec5-5f7560dce5d0')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'standard_number', value: '09639291')
            column(name: 'standard_number_type', value: 'VALISSNB')
            column(name: 'comment', value: 'another comment')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aclci') {
            column(name: 'df_usage_aclci_uid', value: '71a2559b-2dc7-4d4f-b841-0424e3017d63')
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
            column(name: 'reported_number_of_students', value: '8')
            column(name: 'grade_group', value: 'GRADE_E')
        }

        rollback {
            dbRollback
        }
    }
}
