databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-12-15-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Insert test data for testFindCountDtosByFilter, testFindDtosByFilter, testSortingDtosFindByFilter')

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
            column(name: 'df_usage_batch_uid', value: '228c1b83-a69d-4935-9940-4ec51192b140')
            column(name: 'name', value: 'ACLCI usage batch 1')
            column(name: 'payment_date', value: '2022-06-30')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'aclci_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '37b078c7-ce89-46d7-8403-977fa84bccb4')
            column(name: 'df_usage_batch_uid', value: '228c1b83-a69d-4935-9940-4ec51192b140')
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
            column(name: 'df_usage_aclci_uid', value: '37b078c7-ce89-46d7-8403-977fa84bccb4')
            column(name: 'coverage_period', value: '2021-2022')
            column(name: 'reported_number_of_students', value: 2019)
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
            column(name: 'df_usage_uid', value: '865997ed-4c60-4530-972a-37023b7bf509')
            column(name: 'df_usage_batch_uid', value: '228c1b83-a69d-4935-9940-4ec51192b140')
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
            column(name: 'df_usage_aclci_uid', value: '865997ed-4c60-4530-972a-37023b7bf509')
            column(name: 'coverage_period', value: '2020-2022')
            column(name: 'reported_number_of_students', value: 2019)
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
            column(name: 'df_usage_batch_uid', value: 'a6b4c77e-7ee3-48db-b845-202fe6884899')
            column(name: 'name', value: 'ACLCI usage batch 2')
            column(name: 'payment_date', value: '2021-06-30')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'aclci_fields', value: '{"licensee_account_number": 1000000009, "licensee_name": "Tiger Publications"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8ef208d0-d4a7-4f14-93f5-702ca4656b0c')
            column(name: 'df_usage_batch_uid', value: 'a6b4c77e-7ee3-48db-b845-202fe6884899')
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
            column(name: 'df_usage_aclci_uid', value: '8ef208d0-d4a7-4f14-93f5-702ca4656b0c')
            column(name: 'coverage_period', value: '2020-2022')
            column(name: 'license_type', value: 'CURR_REUSE_K12')
            column(name: 'reported_number_of_students', value: 15)
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
