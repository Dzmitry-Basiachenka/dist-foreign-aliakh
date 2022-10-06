databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-09-06-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testFindCountByFilter, testFindDtosByFilter')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '0ee5f787-5e96-442b-bf3a-f879a74e5a0a')
            column(name: 'name', value: 'UDM Batch 2019')
            column(name: 'period', value: 201906)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '771522d3-ccfe-2189-b314-cd6f87ab6689')
            column(name: 'df_udm_usage_batch_uid', value: '0ee5f787-5e96-442b-bf3a-f879a74e5a0a')
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
            column(name: 'type_of_use', value: 'PRINT')
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
            column(name: 'df_udm_usage_batch_uid', value: '701f42dc-7f13-4449-97b0-725aa5a339e0')
            column(name: 'name', value: 'UDM Batch 2021 1')
            column(name: 'period', value: 202106)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '1a1522d3-9dfe-4884-58aa-caaf8792112c')
            column(name: 'df_udm_usage_batch_uid', value: '701f42dc-7f13-4449-97b0-725aa5a339e0')
            column(name: 'original_detail_id', value: 'OGN554GHHSG007')
            column(name: 'period', value: 201906)
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
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
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'is_baseline_flag', value: true)
            column(name: 'baseline_created_by_user', value: 'jjohn@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-14 12:00:00+00')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '4a1522d3-9dfe-4884-b314-cd6f87922936')
            column(name: 'df_udm_usage_batch_uid', value: '701f42dc-7f13-4449-97b0-725aa5a339e0')
            column(name: 'original_detail_id', value: 'OGN554GHHSG008')
            column(name: 'period', value: 202012)
            column(name: 'period_end_date', value: '2020-12-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002854)
            column(name: 'wr_wrk_inst', value: 20008506)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Colloids and surfaces. B, Biointerfaces')
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
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 2)
            column(name: 'quantity', value: 2)
            column(name: 'annualized_copies', value: 4)
            column(name: 'is_baseline_flag', value: true)
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'baseline_created_by_user', value: 'wjohn@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-15 12:00:00+00')
            column(name: 'created_by_user', value: 'wjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-15 12:00:00+00')
            column(name: 'updated_by_user', value: 'wjohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '5c795dfc-94fb-4296-997c-6f36e0a673dc')
            column(name: 'df_udm_usage_batch_uid', value: '701f42dc-7f13-4449-97b0-725aa5a339e0')
            column(name: 'original_detail_id', value: 'OGN554GHHSG010 !@#$%^&*()_+-=?/\\\'"}{][<>')
            column(name: 'period', value: 202012)
            column(name: 'period_end_date', value: '2020-12-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002854)
            column(name: 'wr_wrk_inst', value: 20008506)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Colloids and surfaces. B, Biointerfaces !@#$%^&*()_+-=?/\\\'"}{][<>')
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
            column(name: 'survey_country', value: 'Portugal !@#$%^&*()_+-=?/\\\'"}{][<>')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'reported_type_of_use', value: 'COPY_FOR_MYSELF')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 2)
            column(name: 'quantity', value: 2)
            column(name: 'annualized_copies', value: 4)
            column(name: 'is_baseline_flag', value: true)
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'baseline_created_by_user', value: 'wjohn@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-15 12:00:00+00')
            column(name: 'created_by_user', value: 'wjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-15 12:00:00+00')
            column(name: 'updated_by_user', value: 'wjohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '6f8957c2-8100-4283-ab2a-d410d3736db0')
            column(name: 'df_udm_usage_batch_uid', value: '701f42dc-7f13-4449-97b0-725aa5a339e0')
            column(name: 'original_detail_id', value: 'OGN554GHHSG011')
            column(name: 'period', value: 202012)
            column(name: 'period_end_date', value: '2020-12-31')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002854)
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Not Specified')
            column(name: 'language', value: 'English')
            column(name: 'company_id', value: 1138)
            column(name: 'company_name', value: 'Alcon Laboratories, Inc.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: 'c6615155-f82b-402c-8f22-77e2722ae448')
            column(name: 'ip_address', value: '192.168.211.211')
            column(name: 'survey_country', value: 'Germany')
            column(name: 'usage_date', value: '2020-05-10')
            column(name: 'survey_start_date', value: '2020-04-20')
            column(name: 'survey_end_date', value: '2020-05-15')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'FAX_PHOTOCOPIES')
            column(name: 'quantity', value: 3)
            column(name: 'annual_multiplier', value: 10)
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annualized_copies', value: 30.00000)
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
