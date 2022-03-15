databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-03-11-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Insert test data for testUsableDetailsByCountryReport, testUsableDetailsByCountryEmptyReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '8f97882a-e5b7-49cc-aa52-8477f99e35bb')
            column(name: 'name', value: 'UDM Batch 2020 June')
            column(name: 'period', value: 202006)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '04d27946-b388-4933-ab38-20c7723dbb23')
            column(name: 'df_udm_usage_batch_uid', value: '8f97882a-e5b7-49cc-aa52-8477f99e35bb')
            column(name: 'original_detail_id', value: 'OGN674GIUSB0133')
            column(name: 'period', value: '202006')
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7765')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'English')
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Albany International Corp.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: '6300a9f8-8da1-4476-95dd-9c4eb474f435')
            column(name: 'ip_address', value: '192.168.211.210')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-05-10')
            column(name: 'survey_start_date', value: '2020-04-20')
            column(name: 'survey_end_date', value: '2020-05-15')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'FAX_PHOTOCOPIES')
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'annual_multiplier', value: 12)
            column(name: 'quantity', value: 3)
            column(name: 'annualized_copies', value: 36)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '25796c67-e241-4626-b9ac-1ec2e3bfe2c6')
            column(name: 'df_udm_usage_batch_uid', value: '8f97882a-e5b7-49cc-aa52-8477f99e35bb')
            column(name: 'original_detail_id', value: 'OGN674GXVSB002')
            column(name: 'period', value: '202006')
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'wr_wrk_inst', value: 254327612)
            column(name: 'reported_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'system_title', value: 'Current topics in library and information practice')
            column(name: 'reported_standard_number', value: '1008902112377654XX')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'language', value: 'Portugal')
            column(name: 'comment', value: 'Comment')
            column(name: 'research_url', value: 'google.com')
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Albany International Corp.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: 'c91a71a2-4156-417b-ae60-0715d5782380')
            column(name: 'ip_address', value: '192.168.211.211')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-04-10')
            column(name: 'survey_end_date', value: '2020-05-10')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'annual_multiplier', value: 12)
            column(name: 'quantity', value: 3)
            column(name: 'annualized_copies', value: 36)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'a6722904-6c3c-4ac5-a1ae-6ee38f55eef3')
            column(name: 'df_udm_usage_batch_uid', value: '8f97882a-e5b7-49cc-aa52-8477f99e35bb')
            column(name: 'original_detail_id', value: 'OGN674GABSB011')
            column(name: 'period', value: '202006')
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'INELIGIBLE')
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'None')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'format')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Albany International Corp.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: 'd1f8e0b0-d214-487e-811b-873311d187a9')
            column(name: 'ip_address', value: '24.15.200.201')
            column(name: 'survey_country', value: 'United States')
            column(name: 'usage_date', value: '2020-05-10')
            column(name: 'survey_start_date', value: '2020-03-10')
            column(name: 'survey_end_date', value: '2020-04-10')
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'annual_multiplier', value: 12)
            column(name: 'quantity', value: 0)
            column(name: 'annualized_copies', value: 0)
            column(name: 'df_udm_ineligible_reason_uid', value: '18fbee56-2f5c-450a-999e-54903c0bfb23')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'f6b746c7-f1bb-41dc-a242-c87b827d69a0')
            column(name: 'df_udm_usage_batch_uid', value: '8f97882a-e5b7-49cc-aa52-8477f99e35bb')
            column(name: 'original_detail_id', value: 'OGN674GMNSB123')
            column(name: 'period', value: '202006')
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'INELIGIBLE')
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'None')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'format')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Albany International Corp.')
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'survey_respondent', value: 'd1f8e0b0-d214-487e-811b-873311d187a9')
            column(name: 'ip_address', value: '24.15.200.201')
            column(name: 'survey_country', value: 'United States')
            column(name: 'usage_date', value: '2020-05-10')
            column(name: 'survey_start_date', value: '2020-03-10')
            column(name: 'survey_end_date', value: '2020-04-10')
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'annual_multiplier', value: 12)
            column(name: 'quantity', value: 0)
            column(name: 'annualized_copies', value: 0)
            column(name: 'df_udm_ineligible_reason_uid', value: '18fbee56-2f5c-450a-999e-54903c0bfb23')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'b6bb1f56-c5c9-4292-9bb8-da02aabdee41')
            column(name: 'name', value: 'UDM Batch 2020 June1')
            column(name: 'period', value: 202106)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'c19e0534-641c-4093-8d09-8d03cfb00730')
            column(name: 'df_udm_usage_batch_uid', value: 'b6bb1f56-c5c9-4292-9bb8-da02aabdee41')
            column(name: 'original_detail_id', value: 'OGN674GGFSB008')
            column(name: 'period', value: '202106')
            column(name: 'period_end_date', value: '2020-06-30')
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
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Albany International Corp.')
            column(name: 'survey_respondent', value: '515e61c2-f228-4c3f-92e2-2e6c1a1192a3')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'quantity', value: 2)
            column(name: 'annualized_copies', value: 2)
            column(name: 'is_baseline_flag', value: true)
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'baseline_created_by_user', value: 'user@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-15 12:00:00+00')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'd593873d-d39c-4691-80ee-0d92ebeeb1d8')
            column(name: 'df_udm_usage_batch_uid', value: 'b6bb1f56-c5c9-4292-9bb8-da02aabdee41')
            column(name: 'original_detail_id', value: 'OGN674GJHSB657')
            column(name: 'period', value: '202106')
            column(name: 'period_end_date', value: '2020-06-30')
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
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Albany International Corp.')
            column(name: 'survey_respondent', value: '515e61c2-f228-4c3f-92e2-2e6c1a1192a3')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'quantity', value: 2)
            column(name: 'annualized_copies', value: 2)
            column(name: 'is_baseline_flag', value: true)
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'baseline_created_by_user', value: 'user@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-15 12:00:00+00')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '5c68287a-4dfe-4443-9061-db31817f903c')
            column(name: 'df_udm_usage_batch_uid', value: 'b6bb1f56-c5c9-4292-9bb8-da02aabdee41')
            column(name: 'original_detail_id', value: 'OGN674GLKSB546')
            column(name: 'period', value: '202106')
            column(name: 'period_end_date', value: '2020-06-30')
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
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Albany International Corp.')
            column(name: 'survey_respondent', value: '515e61c2-f228-4c3f-92e2-2e6c1a1192a3')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'quantity', value: 2)
            column(name: 'annualized_copies', value: 2)
            column(name: 'is_baseline_flag', value: true)
            column(name: 'detail_licensee_class_id', value: 4)
            column(name: 'baseline_created_by_user', value: 'user@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-15 12:00:00+00')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'f7ef400c-98d8-4182-a05a-c50b714d9ac2')
            column(name: 'name', value: 'UDM Batch 2021 December with unset Period filter')
            column(name: 'period', value: 202112)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'ce86d8e2-1ea7-4cf7-877d-bd8a151feccc')
            column(name: 'df_udm_usage_batch_uid', value: 'f7ef400c-98d8-4182-a05a-c50b714d9ac2')
            column(name: 'original_detail_id', value: 'OGN674GSASB009')
            column(name: 'period', value: '202112')
            column(name: 'period_end_date', value: '2021-12-31')
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
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Albany International Corp.')
            column(name: 'survey_respondent', value: '515e61c2-f228-4c3f-92e2-2e6c1a1192a3')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2021-09-10')
            column(name: 'survey_start_date', value: '2021-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'quantity', value: 2)
            column(name: 'annualized_copies', value: 2)
            column(name: 'is_baseline_flag', value: true)
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'baseline_created_by_user', value: 'user@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-15 12:00:00+00')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '2c5e98f5-613e-433e-b03b-efe136718629')
            column(name: 'name', value: 'UDM Batch 2020 June with Created Time UDM Usage for unset Date filter')
            column(name: 'period', value: 202006)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'a3fdf089-30ab-41dd-af0b-f57e356fef5c')
            column(name: 'df_udm_usage_batch_uid', value: '2c5e98f5-613e-433e-b03b-efe136718629')
            column(name: 'original_detail_id', value: 'OGXN674GTRSB412')
            column(name: 'period', value: '202006')
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7765')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Albany International Corp.')
            column(name: 'survey_respondent', value: 'f86412b1-a555-4acf-96f9-b909b139533f')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'United States')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-01-01')
            column(name: 'survey_end_date', value: '2020-01-30')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'quantity', value: 1)
            column(name: 'annualized_copies', value: 1)
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'is_baseline_flag', value: true)
            column(name: 'baseline_created_by_user', value: 'jjohn@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-06-14 12:00:00+00')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-01-01 12:00:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-01-14 12:00:00+00')
        }

        rollback {
            dbRollback
        }
    }
}
