databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-12-29-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Insert test data for testUpdateToEligibleByIdsNoWorkUpdates, testUpdateToEligibleByIdsWithWorkUpdates')

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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '5ae8da82-7abd-4572-9d9d-f35665fedc19')
            column(name: 'name', value: 'ACLCI usage batch 1')
            column(name: 'payment_date', value: '2022-06-30')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'aclci_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'eba21e40-cbdc-4114-a765-40508ae6d74a')
            column(name: 'df_usage_batch_uid', value: '5ae8da82-7abd-4572-9d9d-f35665fedc19')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'status_ind', value: 'ELIGIBLE')
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
            column(name: 'df_usage_aclci_uid', value: 'eba21e40-cbdc-4114-a765-40508ae6d74a')
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
            column(name: 'grade_group', value: 'GRADE_M')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4c81e637-3fe4-4777-a486-5dd8e43215b8')
            column(name: 'df_usage_batch_uid', value: '5ae8da82-7abd-4572-9d9d-f35665fedc19')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'status_ind', value: 'WORK_NOT_GRANTED')
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
            column(name: 'df_usage_aclci_uid', value: '4c81e637-3fe4-4777-a486-5dd8e43215b8')
            column(name: 'coverage_period', value: '2020-2022')
            column(name: 'license_type', value: 'CURR_REUSE_K12')
            column(name: 'reported_media_type', value: 'Text')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'reported_article', value: 'Medical Journal')
            column(name: 'reported_standard_number', value: '978-1-873047-26-2')
            column(name: 'reported_author', value: 'Associated Press')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2021-02-14')
            column(name: 'reported_grade', value: 'K')
            column(name: 'grade_group', value: 'GRADE_E')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '50fe503f-3cf9-49f4-a4ae-817e979eb613')
            column(name: 'df_usage_batch_uid', value: '5ae8da82-7abd-4572-9d9d-f35665fedc19')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
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
            column(name: 'df_usage_aclci_uid', value: '50fe503f-3cf9-49f4-a4ae-817e979eb613')
            column(name: 'coverage_period', value: '2020-2022')
            column(name: 'license_type', value: 'CURR_REUSE_K12')
            column(name: 'reported_media_type', value: 'Text')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'reported_article', value: 'Medical Journal')
            column(name: 'reported_standard_number', value: '978-1-873047-26-2')
            column(name: 'reported_author', value: 'Associated Press')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2021-02-14')
            column(name: 'reported_grade', value: 'K')
            column(name: 'grade_group', value: 'GRADE_M')
        }

        rollback {
            dbRollback
        }
    }
}
