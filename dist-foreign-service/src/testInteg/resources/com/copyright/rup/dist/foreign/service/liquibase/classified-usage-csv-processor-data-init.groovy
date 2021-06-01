databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-01-24-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for ClassifiedUsageCsvProcessorIntegrationTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'name', value: 'Test Batch')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'AACL')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 12)
        }

        // Usage with all fields valid and Detail Licensee Class Id missing in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '455e2dd0-4107-434f-ba3c-fb5ccd6f2585')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'Valid usage with Detail Licensee Class Id missing in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '455e2dd0-4107-434f-ba3c-fb5ccd6f2585')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage with all fields valid and Detail Licensee Class Id present in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a65d100a-3e7c-4b35-903e-2463fb53f41d')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'Valid usage with  Detail Licensee Class Id present in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'a65d100a-3e7c-4b35-903e-2463fb53f41d')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage with 'disqualified' Publication Type in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '10253f6a-bb89-4738-a0d2-ea49fdd94d66')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'Valid usage with \'disqualified\' Publication Type in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '10253f6a-bb89-4738-a0d2-ea49fdd94d66')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage with 'Disqualified' Discipline in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '195af195-8cde-4129-a961-c410ec010549')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'Valid usage with \'Disqualified\' Discipline in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '195af195-8cde-4129-a961-c410ec010549')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage with 'DISQUALIFIED' Enrollment Profile in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4b83a43a-b5fe-4ad3-a8ad-f491a4e58dbc')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'Valid usage with \'DISQUALIFIED\' Enrollment Profile in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '4b83a43a-b5fe-4ad3-a8ad-f491a4e58dbc')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage not in WORK_RESEARCH status
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5825ff1e-ae3a-472d-8efa-a13bc80988c0')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'Usage not in WORK_RESEARCH status')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '5825ff1e-ae3a-472d-8efa-a13bc80988c0')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage without Publication Type in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1208f434-3d98-49d5-bdc6-baa611d2d006')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'Usage without Publication Type in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '1208f434-3d98-49d5-bdc6-baa611d2d006')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage without Discipline in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ed11b836-186b-449a-9aee-d2af81eca7d4')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'Usage without Discipline in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'ed11b836-186b-449a-9aee-d2af81eca7d4')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage without Enrollment Profile in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3411c5d9-9896-4b3f-9f2e-2c7cbd81e5aa')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'Usage without Enrollment Profile in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '3411c5d9-9896-4b3f-9f2e-2c7cbd81e5aa')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage with different and non-existing Wr Wrk Inst in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '975990bd-3595-4c24-a40a-ca8636987915')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'Usage with non-existing Wr Wrk Inst in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '975990bd-3595-4c24-a40a-ca8636987915')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage with non-existing Discipline/Enrollment Profile pair in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ae8d7b70-a293-4973-bae6-172589e0cdc0')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'Usage with non-existing Discipline/Enrollment Profile pair in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'ae8d7b70-a293-4973-bae6-172589e0cdc0')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        // Usage with non-existing Publication Type in CSV
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1c277135-fe48-4bf9-922e-3cabe637f1af')
            column(name: 'df_usage_batch_uid', value: 'd716d5e5-b21f-4fa9-9558-63859f166315')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'Usage with non-existing Publication Type in CSV')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '1c277135-fe48-4bf9-922e-3cabe637f1af')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        rollback ""
    }
}
