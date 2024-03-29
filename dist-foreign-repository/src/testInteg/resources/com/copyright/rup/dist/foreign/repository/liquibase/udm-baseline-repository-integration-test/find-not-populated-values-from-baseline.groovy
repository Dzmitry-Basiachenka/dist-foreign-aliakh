databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-09-27-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testFindNotPopulatedValuesFromBaseline')

        // 201512
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: 'e7285d8f-2fc7-4e44-83e3-ac809f3a7094')
            column(name: 'period', value: 201512)
            column(name: 'status_ind', value: "NEW")
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 10003378)
            column(name: 'system_title', value: 'Amsterdam tomorrow')
            column(name: 'standard_number', value: '0927-7765')
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'assignee', value: 'jjohn@copyright.com')
        }

        // 201506
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: 'ff285d8f-2268c-4e44-83e3-ac809f3a7091')
            column(name: 'period', value: 201506)
            column(name: 'status_ind', value: "NEW")
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 10003378)
            column(name: 'system_title', value: 'Amsterdam tomorrow')
            column(name: 'standard_number', value: '0927-7765')
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'assignee', value: 'jjohn@copyright.com')
        }

        // 201406
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: 'e10ff22f-48f6-4192-bd31-66373e39924e')
            column(name: 'period', value: 201406)
            column(name: 'status_ind', value: "NEW")
            column(name: 'rh_account_number', value: 1000002835)
            column(name: 'wr_wrk_inst', value: 10002508)
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'standard_number', value: '0927-7765')
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'assignee', value: 'jjohn@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '50fb5f24-23ed-494c-9ddb-f8660c376dfc')
            column(name: 'period', value: 201406)
            column(name: 'status_ind', value: "NEW")
            column(name: 'rh_account_number', value: 1000002835)
            column(name: 'wr_wrk_inst', value: 70063308)
            column(name: 'system_title', value: 'Artifacts in video compression')
            column(name: 'standard_number', value: '0927-7765')
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'assignee', value: 'jjohn@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'f0405f18-b5c9-43a0-a877-d064117e08a6')
            column(name: 'name', value: 'UDM Batch 2015')
            column(name: 'period', value: 201512)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        // Wr Wrk Inst: 28908508. Period: 201512. Not in baseline
        // Result for period 201512: work won't be returned by query as it is not in baseline
        // Result for period 201412: work won't be returned by query as it is not in baseline
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '12a2b9c3-dc88-449d-b58f-f28b0eeb21e5')
            column(name: 'df_udm_usage_batch_uid', value: 'f0405f18-b5c9-43a0-a877-d064117e08a6')
            column(name: 'original_detail_id', value: 'OGN555GHASG001')
            column(name: 'period', value: 201512)
            column(name: 'period_end_date', value: '2015-12-31')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002854)
            column(name: 'wr_wrk_inst', value: 28908508)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'reported_standard_number', value: '0927-7765')
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
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
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

        // Wr Wrk Inst: 20008506. Period: 201512. Not populated
        // Result for period 201512: work will be returned by query
        // Result for period 201412: work won't be returned by query
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '0ea2b9c3-dc88-449d-b58f-f28b0eeb21e5')
            column(name: 'df_udm_usage_batch_uid', value: 'f0405f18-b5c9-43a0-a877-d064117e08a6')
            column(name: 'original_detail_id', value: 'OGN554GHASG001')
            column(name: 'period', value: 201512)
            column(name: 'period_end_date', value: '2015-12-31')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002854)
            column(name: 'wr_wrk_inst', value: 20008506)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'reported_standard_number', value: '0927-7765')
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

        // Wr Wrk Inst: 10002508. Period: 201512. Not populated
        // Result for period 201512: work will be returned by query
        // Result for period 201412: work won't be returned by query
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '85cb4606-3960-465e-8e81-bddfc67a8916')
            column(name: 'df_udm_usage_batch_uid', value: 'f0405f18-b5c9-43a0-a877-d064117e08a6')
            column(name: 'original_detail_id', value: 'OGN554GHASG002')
            column(name: 'period', value: 201512)
            column(name: 'period_end_date', value: '2015-12-31')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002835)
            column(name: 'wr_wrk_inst', value: 10002508)
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

        // Wr Wrk Inst: 10003378. Period: 201512. Populated in 201512
        // Result for period 201512: work won't be returned as it has already been populated in current period
        // Result for period 201412: work won't be returned by query
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'c9e42f40-ef81-45c4-8c0f-c6c1ec1829a9')
            column(name: 'df_udm_usage_batch_uid', value: 'f0405f18-b5c9-43a0-a877-d064117e08a6')
            column(name: 'df_udm_value_uid', value: 'e7285d8f-2fc7-4e44-83e3-ac809f3a7094')
            column(name: 'original_detail_id', value: 'OGN554GHASG003')
            column(name: 'period', value: 201512)
            column(name: 'period_end_date', value: '2015-12-31')
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
            column(name: 'is_baseline_flag', value: true)
            column(name: 'baseline_created_by_user', value: 'wjohn@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-15 12:00:00+00')
            column(name: 'created_by_user', value: 'wjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-15 12:00:00+00')
            column(name: 'updated_by_user', value: 'wjohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'bd49e2f2-f345-447a-a069-3657a19f4a01')
            column(name: 'name', value: 'UDM Batch 201506')
            column(name: 'period', value: 201506)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        // Wr Wrk Inst: 10003378. Period: 201506. Populated in 201506
        // Result for period 201512: work won't be returned as it has already been populated in 201512
        // Result for period 201412: work won't be returned by query
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '56e42f40-ef81-36c4-8c0f-c6c1ec1829a9')
            column(name: 'df_udm_usage_batch_uid', value: 'bd49e2f2-f345-447a-a069-3657a19f4a01')
            column(name: 'original_detail_id', value: 'OGN654GHASG003')
            column(name: 'period', value: 201512)
            column(name: 'period_end_date', value: '2015-12-31')
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
            column(name: 'is_baseline_flag', value: true)
            column(name: 'baseline_created_by_user', value: 'wjohn@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-15 12:00:00+00')
            column(name: 'created_by_user', value: 'wjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-15 12:00:00+00')
            column(name: 'updated_by_user', value: 'wjohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:00:00+00')
        }

        // Wr Wrk Inst: 10002508. Period: 201506. Populated in 201406
        // Result for period 201512: work will be returned as included into 201512 period and hasn't been populated in it
        // Result for period 201412: work won't be returned by query as it has already been populated in 201406
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '089b1d0c-b050-4c1f-9322-d9dc17a215c9')
            column(name: 'df_udm_usage_batch_uid', value: 'bd49e2f2-f345-447a-a069-3657a19f4a01')
            column(name: 'original_detail_id', value: 'OGN554GHASG004')
            column(name: 'period', value: 201506)
            column(name: 'period_end_date', value: '2015-06-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002835)
            column(name: 'wr_wrk_inst', value: 10002508)
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

        // Wr Wrk Inst: 70063308. Period: 201506. Populated in 201406
        // Result for period 201512: work will be returned as it was not populated in 201506
        // Result for period 201412: work won't be returned by query as it has already been populated in 201406
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '63c44e26-0872-45a9-931f-4545d1521c59')
            column(name: 'df_udm_usage_batch_uid', value: 'bd49e2f2-f345-447a-a069-3657a19f4a01')
            column(name: 'original_detail_id', value: 'OGN554GHASG007')
            column(name: 'period', value: 201506)
            column(name: 'period_end_date', value: '2015-06-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002835)
            column(name: 'wr_wrk_inst', value: 70063308)
            column(name: 'reported_title', value: 'Artifacts in video compression')
            column(name: 'system_title', value: 'Artifacts in video compression')
            column(name: 'reported_standard_number', value: '0927-7722')
            column(name: 'standard_number', value: '1873-7722')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '34447286-a9eb-48f3-a050-b928ca3113c46')
            column(name: 'name', value: 'UDM Batch 201406')
            column(name: 'period', value: 201406)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        // Wr Wrk Inst: 10002508. Period: 201406. Populated in 201406
        // Result for period 201512: work will be returned as included into current (201512) period and hasn't been populated
        // Result for period 201412: work won't be returned by query as has already been populated in 201406
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '2dacaab5-dca6-459f-bb79-a8a1532e4225')
            column(name: 'df_udm_usage_batch_uid', value: '34447286-a9eb-48f3-a050-b928ca3113c46')
            column(name: 'df_udm_value_uid', value: 'e10ff22f-48f6-4192-bd31-66373e39924e')
            column(name: 'original_detail_id', value: 'OGN554GHASG005')
            column(name: 'period', value: 201406)
            column(name: 'period_end_date', value: '2014-06-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002835)
            column(name: 'wr_wrk_inst', value: 10002508)
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

        // Wr Wrk Inst: 70063308. Period: 201406. Populated in 201406
        // Result for period 201512: won't be returned as it was populated in 201406 and not included into 201512 period
        // Result for period 201412: work won't be returned by query as has already been populated in 201406
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'c4575e31-024e-4f88-9ab9-5ac306bbbfb6')
            column(name: 'df_udm_usage_batch_uid', value: '34447286-a9eb-48f3-a050-b928ca3113c46')
            column(name: 'df_udm_value_uid', value: '50fb5f24-23ed-494c-9ddb-f8660c376dfc')
            column(name: 'original_detail_id', value: 'OGN554GHASG008')
            column(name: 'period', value: 201406)
            column(name: 'period_end_date', value: '2014-06-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002835)
            column(name: 'wr_wrk_inst', value: 70063308)
            column(name: 'system_title', value: 'Artifacts in video compression')
            column(name: 'reported_title', value: 'Artifacts in video compression')
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
            column(name: 'df_udm_usage_batch_uid', value: '09a07725-cc62-4936-a8c0-3cd2c5549148')
            column(name: 'name', value: 'UDM Batch 201006')
            column(name: 'period', value: 201006)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        // Wr Wrk Inst: 10009596. Period: 201006. Hasn't been populated
        // Result for period 201512: will be returned as it has never been populated and period has age weight 0.05
        // Result for period 201412: will be returned as it has never been populated and period has age weight greater than zero
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'dfe0d4ee-f049-45ab-abbb-3573e3f72f0b')
            column(name: 'df_udm_usage_batch_uid', value: '09a07725-cc62-4936-a8c0-3cd2c5549148')
            column(name: 'original_detail_id', value: 'OGN554GHASG009')
            column(name: 'period', value: 201006)
            column(name: 'period_end_date', value: '2010-06-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002835)
            column(name: 'wr_wrk_inst', value: 10009596)
            column(name: 'reported_title', value: 'Brand new band')
            column(name: 'system_title', value: 'Brand new band')
            column(name: 'reported_standard_number', value: '7896-1555')
            column(name: 'standard_number', value: '7896-1555')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'f657d162-8877-490e-a555-5a8f41f8e027')
            column(name: 'name', value: 'UDM Batch 200912')
            column(name: 'period', value: 200912)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        // Wr Wrk Inst: 70001191. Period: 200912. Hasn't been populated
        // Result for period 201512: won't be returned as this period has zero age weight
        // Result for period 201412: will be returned as it has never been populated and period has age weight greater than zero
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '0eb1e4ff-3e86-4c7a-8c64-97ca06c1a75d')
            column(name: 'df_udm_usage_batch_uid', value: 'f657d162-8877-490e-a555-5a8f41f8e027')
            column(name: 'original_detail_id', value: 'OGN554GHASG010')
            column(name: 'period', value: 200912)
            column(name: 'period_end_date', value: '2009-12-31')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002835)
            column(name: 'wr_wrk_inst', value: 70001191)
            column(name: 'reported_title', value: 'Tomatoes, tomatoes, tomatoes!')
            column(name: 'system_title', value: 'Tomatoes, tomatoes, tomatoes!')
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
