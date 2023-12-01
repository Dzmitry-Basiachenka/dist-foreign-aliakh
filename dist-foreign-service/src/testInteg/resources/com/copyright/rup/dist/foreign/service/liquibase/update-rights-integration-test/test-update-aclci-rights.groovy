databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-12-09-00', author: 'Dzmitry Basiachenka<dbasiachenka@copyright.com>') {
        comment('Inserting test data for testUpdateAclciRights')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3b9aaa5a-0fef-4b89-859a-3e9c620c7f88')
            column(name: 'name', value: 'ACLCI Usage Batch 2022')
            column(name: 'payment_date', value: '2022-06-30')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'initial_usages_count', value: 3)
            column(name: 'sal_fields', value: '{"licensee_name": "RGS Energy Group, Inc.", "licensee_account_number": 5588}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '019af1aa-c178-467c-9015-c2d18db85229')
            column(name: 'df_usage_batch_uid', value: '3b9aaa5a-0fef-4b89-859a-3e9c620c7f88')
            column(name: 'wr_wrk_inst', value: 122769471)
            column(name: 'standard_number', value: '978-0-7474-0151-6')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'system_title', value: 'life and death of Mozart')
            column(name: 'work_title', value: 'life and death of Mozart')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'ACLCI')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aclci') {
            column(name: 'df_usage_aclci_uid', value: '019af1aa-c178-467c-9015-c2d18db85229')
            column(name: 'coverage_period', value: '2014-2015')
            column(name: 'license_type', value: 'CURR_REPUB_K12')
            column(name: 'reported_media_type', value: 'Image')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'reported_grade', value: 'K')
            column(name: 'grade_group', value: 'GRADE_E')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'db86af7e-c2ae-4cc6-b797-6214298b7113')
            column(name: 'df_usage_batch_uid', value: '3b9aaa5a-0fef-4b89-859a-3e9c620c7f88')
            column(name: 'wr_wrk_inst', value: 243618757)
            column(name: 'standard_number', value: '978-0-947731-92-5')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'system_title', value: 'Midbus')
            column(name: 'work_title', value: 'Midbus')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'ACLCI')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aclci') {
            column(name: 'df_usage_aclci_uid', value: 'db86af7e-c2ae-4cc6-b797-6214298b7113')
            column(name: 'coverage_period', value: '2015-2016')
            column(name: 'license_type', value: 'CURR_REPUB_K12')
            column(name: 'reported_media_type', value: 'Video')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'reported_grade', value: 'K')
            column(name: 'grade_group', value: 'GRADE_E')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '65d36e80-8b5c-42cf-b543-4b9ee0aed0cb')
            column(name: 'df_usage_batch_uid', value: '3b9aaa5a-0fef-4b89-859a-3e9c620c7f88')
            column(name: 'wr_wrk_inst', value: 140160102)
            column(name: 'standard_number', value: '978-84-08-04783-7')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'system_title', value: 'Rimas')
            column(name: 'work_title', value: 'Rimas')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'ACLCI')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aclci') {
            column(name: 'df_usage_aclci_uid', value: '65d36e80-8b5c-42cf-b543-4b9ee0aed0cb')
            column(name: 'coverage_period', value: '2014-2015')
            column(name: 'license_type', value: 'CURR_REPUB_HE')
            column(name: 'reported_media_type', value: 'Text')
            column(name: 'media_type_weight', value: 1)
            column(name: 'reported_grade', value: 'HE')
            column(name: 'grade_group', value: 'GRADE_HE')
        }

        rollback {
            dbRollback
        }
    }
}
