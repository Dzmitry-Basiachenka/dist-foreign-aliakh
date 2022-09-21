databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-01-27-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindWrWrkInstToSystemTitleMap')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '69efdf26-b01e-4815-ab58-b9e1e62e7265')
            column(name: 'name', value: 'UDM Batch 2019')
            column(name: 'period', value: 201906)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'f9ae0f84-72ac-4096-9970-b47514786918')
            column(name: 'df_udm_usage_batch_uid', value: '69efdf26-b01e-4815-ab58-b9e1e62e7265')
            column(name: 'original_detail_id', value: 'OGN554GHHSG009')
            column(name: 'period', value: 201906)
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002854)
            column(name: 'wr_wrk_inst', value: 20008506)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'reported_standard_number', value: '0927-7765')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 454984566)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'survey_respondent', value: 'a671342a-ff05-4d6a-94ce-5876e950a656')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 2)
            column(name: 'quantity', value: 2)
            column(name: 'annualized_copies', value: 4)
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'is_baseline_flag', value: true)
            column(name: 'baseline_created_by_user', value: 'wjohn@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-15 12:00:00+00')
            column(name: 'created_by_user', value: 'wjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-15 12:00:00+00')
            column(name: 'updated_by_user', value: 'wjohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '1fd9d0b6-1611-46f7-82bd-d57d90223ebc')
            column(name: 'name', value: 'UDM Batch 2020')
            column(name: 'period', value: 202012)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '5ac0b9d9-02aa-4a67-b669-198358e61377')
            column(name: 'df_udm_usage_batch_uid', value: '1fd9d0b6-1611-46f7-82bd-d57d90223ebc')
            column(name: 'original_detail_id', value: 'OGN554GHHSG007')
            column(name: 'period', value: 202012)
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'Tenside, surfactants, detergents')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7765')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 454984566)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'survey_respondent', value: 'a671342a-ff05-4d6a-94ce-5876e950a656')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'United States')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'quantity', value: 1)
            column(name: 'annualized_copies', value: 1)
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'is_baseline_flag', value: true)
            column(name: 'baseline_created_by_user', value: 'jjohn@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-14 12:00:00+00')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-14 12:00:00+00')
        }

        rollback {
            dbRollback
        }
    }
}
