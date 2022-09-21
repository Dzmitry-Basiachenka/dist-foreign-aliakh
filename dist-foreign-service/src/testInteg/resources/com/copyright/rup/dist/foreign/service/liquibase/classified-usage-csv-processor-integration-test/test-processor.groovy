databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-01-24-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testProcessor')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c7101cd6-cd8e-4585-9e04-5fd4c826ddd3')
            column(name: 'name', value: 'Test Batch')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'AACL')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'initial_usages_count', value: 12)
        }

        // Usage with all fields valid and Detail Licensee Class Id missing in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '455e2dd0-4107-434f-ba3c-fb5ccd6f2585')
            column(name: 'df_usage_batch_uid', value: 'c7101cd6-cd8e-4585-9e04-5fd4c826ddd3')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'Valid usage with Detail Licensee Class Id missing in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '455e2dd0-4107-434f-ba3c-fb5ccd6f2585')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage with all fields valid and Detail Licensee Class Id present in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a65d100a-3e7c-4b35-903e-2463fb53f41d')
            column(name: 'df_usage_batch_uid', value: 'c7101cd6-cd8e-4585-9e04-5fd4c826ddd3')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'Valid usage with  Detail Licensee Class Id present in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'a65d100a-3e7c-4b35-903e-2463fb53f41d')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage with 'disqualified' Publication Type in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '10253f6a-bb89-4738-a0d2-ea49fdd94d66')
            column(name: 'df_usage_batch_uid', value: 'c7101cd6-cd8e-4585-9e04-5fd4c826ddd3')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'Valid usage with \'disqualified\' Publication Type in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '10253f6a-bb89-4738-a0d2-ea49fdd94d66')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage with 'Disqualified' Discipline in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '195af195-8cde-4129-a961-c410ec010549')
            column(name: 'df_usage_batch_uid', value: 'c7101cd6-cd8e-4585-9e04-5fd4c826ddd3')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'Valid usage with \'Disqualified\' Discipline in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '195af195-8cde-4129-a961-c410ec010549')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage with 'DISQUALIFIED' Enrollment Profile in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4b83a43a-b5fe-4ad3-a8ad-f491a4e58dbc')
            column(name: 'df_usage_batch_uid', value: 'c7101cd6-cd8e-4585-9e04-5fd4c826ddd3')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'Valid usage with \'DISQUALIFIED\' Enrollment Profile in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '4b83a43a-b5fe-4ad3-a8ad-f491a4e58dbc')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
        }

        rollback {
            dbRollback
        }
    }
}
