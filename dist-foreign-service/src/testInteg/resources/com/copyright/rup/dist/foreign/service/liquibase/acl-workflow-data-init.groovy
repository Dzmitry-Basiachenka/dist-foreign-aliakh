databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-10-21-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting data for testAclWorkflow')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'a0405f18-b5c9-43a0-a877-d064117e08a6')
            column(name: 'name', value: 'UDM Batch 2019')
            column(name: 'period', value: 201912)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        // Work 123059057 will be populated as included into 202006 period
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'a2a2b9c3-dc88-449d-b58f-f28b0eeb21e5')
            column(name: 'df_udm_usage_batch_uid', value: 'a0405f18-b5c9-43a0-a877-d064117e08a6')
            column(name: 'original_detail_id', value: 'OGN555GHASG001')
            column(name: 'period', value: '201912')
            column(name: 'period_end_date', value: '2019-12-31')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000024950)
            column(name: 'wr_wrk_inst', value: 123059057)
            column(name: 'reported_title', value: 'BIOCHEMISTRY (MOSCOW)')
            column(name: 'system_title', value: 'BIOCHEMISTRY (MOSCOW)')
            column(name: 'reported_standard_number', value: '978-0-271-01750-1')
            column(name: 'standard_number', value: '978-0-271-01750-1')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 454984566)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'survey_respondent', value: 'a671342a-ff05-4d6a-94ce-5876e950a656')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2019-09-10')
            column(name: 'survey_start_date', value: '2019-09-10')
            column(name: 'survey_end_date', value: '2020-06-30')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 2)
            column(name: 'quantity', value: 2)
            column(name: 'annualized_copies', value: 4)
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'is_baseline_flag', value: true)
            column(name: 'created_by_user', value: 'wjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-15 12:00:00+00')
            column(name: 'updated_by_user', value: 'wjohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:00:00+00')
        }

        // Work 200008506 will be populated as not included into 202006 period and haven't been populated yet
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '0aa2b9c3-dc88-449d-b58f-f28b0eeb21e5')
            column(name: 'df_udm_usage_batch_uid', value: 'a0405f18-b5c9-43a0-a877-d064117e08a6')
            column(name: 'original_detail_id', value: 'OGN554GHASG002')
            column(name: 'period', value: '201912')
            column(name: 'period_end_date', value: '2019-12-31')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002854)
            column(name: 'wr_wrk_inst', value: 200008506)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'reported_standard_number', value: '1873-2233')
            column(name: 'standard_number', value: '1873-2233')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 454984566)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'survey_respondent', value: 'a671342a-ff05-4d6a-94ce-5876e950a656')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2019-09-10')
            column(name: 'survey_start_date', value: '2019-09-10')
            column(name: 'survey_end_date', value: '2020-06-30')
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

        // Work 398898506 won't be populated as it is not in baseline
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '02a2b9c3-d3455-449d-b58f-f28b0eeb21e5')
            column(name: 'df_udm_usage_batch_uid', value: 'a0405f18-b5c9-43a0-a877-d064117e08a6')
            column(name: 'original_detail_id', value: 'OGN554GHASG003')
            column(name: 'period', value: '201912')
            column(name: 'period_end_date', value: '2019-12-31')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002854)
            column(name: 'wr_wrk_inst', value: 398898506)
            column(name: 'reported_title', value: 'Hollow Nights')
            column(name: 'system_title', value: 'Hollow Nights')
            column(name: 'reported_standard_number', value: '1833-2233')
            column(name: 'standard_number', value: '1833-2233')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 454984566)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'survey_respondent', value: 'a671342a-ff05-4d6a-94ce-5876e950a656')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2019-09-10')
            column(name: 'survey_start_date', value: '2019-09-10')
            column(name: 'survey_end_date', value: '2020-06-30')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 2)
            column(name: 'quantity', value: 2)
            column(name: 'annualized_copies', value: 4)
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'is_baseline_flag', value: false)
            column(name: 'created_by_user', value: 'wjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-15 12:00:00+00')
            column(name: 'updated_by_user', value: 'wjohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'ad49e2f2-f345-447a-a069-3657a19f4a01')
            column(name: 'name', value: 'UDM Batch 201606')
            column(name: 'period', value: 201606)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: 'a0fb5f24-23ed-494c-9ddb-f8660c376dfc')
            column(name: 'period', value: 201606)
            column(name: 'status_ind', value: "NEW")
            column(name: 'rh_account_number', value: 1000005535)
            column(name: 'wr_wrk_inst', value: 10003378)
            column(name: 'system_title', value: 'Amsterdam tomorrow')
            column(name: 'standard_number', value: '1873-7765')
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'assignee', value: 'jjohn@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: 'a0fb5f24-23ed-494c-5689-f8660c376dfc')
            column(name: 'period', value: 201606)
            column(name: 'status_ind', value: "NEW")
            column(name: 'rh_account_number', value: 1000000322)
            column(name: 'wr_wrk_inst', value: 876543210)
            column(name: 'system_title', value: 'Paris tonique')
            column(name: 'standard_number', value: '12345XX-79069')
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'assignee', value: 'jjohn@copyright.com')
        }

        // Work 398898506 won't be populated as it has already been populated it prior periods
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'a6e42f40-8966-36c4-8c0f-c6c1ec1829a9')
            column(name: 'df_udm_usage_batch_uid', value: 'ad49e2f2-f345-447a-a069-3657a19f4a01')
            column(name: 'original_detail_id', value: 'OGN654GHASG004')
            column(name: 'period', value: '201612')
            column(name: 'period_end_date', value: '2016-12-31')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000005535)
            column(name: 'wr_wrk_inst', value: 10003378)
            column(name: 'reported_title', value: 'Amsterdam tomorrow')
            column(name: 'system_title', value: 'Amsterdam tomorrow')
            column(name: 'reported_standard_number', value: '0927-7765')
            column(name: 'standard_number', value: '1873-7768')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
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
            column(name: 'df_udm_value_uid', value: 'a0fb5f24-23ed-494c-9ddb-f8660c376dfc')
            column(name: 'is_baseline_flag', value: true)
            column(name: 'baseline_created_by_user', value: 'wjohn@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-15 12:00:00+00')
            column(name: 'created_by_user', value: 'wjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-15 12:00:00+00')
            column(name: 'updated_by_user', value: 'wjohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:00:00+00')
        }

        // Work 876543210 will be populated as it is included into 202006 period
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'a6e425996-ef81-36c4-8c0f-c6c1ec1829a9')
            column(name: 'df_udm_usage_batch_uid', value: 'ad49e2f2-f345-447a-a069-3657a19f4a01')
            column(name: 'original_detail_id', value: 'OGN654GHASG005')
            column(name: 'period', value: '201612')
            column(name: 'period_end_date', value: '2016-12-31')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000000322)
            column(name: 'wr_wrk_inst', value: 876543210)
            column(name: 'reported_title', value: 'Paris tonique')
            column(name: 'system_title', value: 'Paris tonique')
            column(name: 'reported_standard_number', value: '12345XX-79069')
            column(name: 'standard_number', value: '12345XX-79069')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
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
            column(name: 'df_udm_value_uid', value: 'a0fb5f24-23ed-494c-5689-f8660c376dfc')
            column(name: 'baseline_created_by_user', value: 'wjohn@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-15 12:00:00+00')
            column(name: 'created_by_user', value: 'wjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-15 12:00:00+00')
            column(name: 'updated_by_user', value: 'wjohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'ad99e2f2-2666-447a-a069-3657a19f4a01')
            column(name: 'name', value: 'UDM Batch 201306')
            column(name: 'period', value: 201306)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        // Work 789003378 won't be populated as age weight is 0
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'a6e42f40-ef81-36c4-8c0f-c6c1ec1829a9')
            column(name: 'df_udm_usage_batch_uid', value: 'ad49e2f2-f345-447a-a069-3657a19f4a01')
            column(name: 'original_detail_id', value: 'OGN654GHASG006')
            column(name: 'period', value: '201306')
            column(name: 'period_end_date', value: '2013-12-31')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000005535)
            column(name: 'wr_wrk_inst', value: 789003378)
            column(name: 'reported_title', value: 'Bright Nights')
            column(name: 'system_title', value: 'Bright Nights')
            column(name: 'reported_standard_number', value: '0923-7765')
            column(name: 'standard_number', value: '0923-7768')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
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

        rollback {
            dbRollback
        }
    }
}