databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-01-06-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Insert test data for testUsagesByStatusReport, testUsagesByStatusEmptyReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'fbd7b557-6087-4eab-9ed4-6a3c311f4a65')
            column(name: 'name', value: 'UDM Batch 2020 June')
            column(name: 'period', value: 202206)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '1ab899a3-0673-40e6-87b4-07092bb25b67')
            column(name: 'df_udm_usage_batch_uid', value: 'fbd7b557-6087-4eab-9ed4-6a3c311f4a65')
            column(name: 'original_detail_id', value: 'OGN674GHHSB001')
            column(name: 'period', value: 202206)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'reported_standard_number', value: '1008902112377654XX')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'language', value: 'Portugal')
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
            column(name: 'quantity', value: 1)
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '158dc9e8-99a1-4c5a-9cef-cf07b7e74cd7')
            column(name: 'df_udm_usage_batch_uid', value: 'fbd7b557-6087-4eab-9ed4-6a3c311f4a65')
            column(name: 'original_detail_id', value: 'OGN674GHHSB002')
            column(name: 'period', value: 202206)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
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
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'quantity', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '94e3c819-106b-48f5-af84-3f22b829907a')
            column(name: 'df_udm_usage_batch_uid', value: 'fbd7b557-6087-4eab-9ed4-6a3c311f4a65')
            column(name: 'original_detail_id', value: 'OGN674GHHSB003')
            column(name: 'period', value: 202206)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
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
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'quantity', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'df7c554d-34f6-4703-8a99-fb2019d3b786')
            column(name: 'df_udm_usage_batch_uid', value: 'fbd7b557-6087-4eab-9ed4-6a3c311f4a65')
            column(name: 'original_detail_id', value: 'OGN674GHHSB004')
            column(name: 'period', value: 202206)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'assignee', value: 'jjohn@copyright.com')
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
            column(name: 'quantity', value: 1)
            column(name: 'reported_type_of_use', value: 'FAX_PHOTOCOPIES')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'fb80bbfb-6f81-4147-988b-685a1a217b4c')
            column(name: 'df_udm_usage_batch_uid', value: 'fbd7b557-6087-4eab-9ed4-6a3c311f4a65')
            column(name: 'original_detail_id', value: 'OGN674GHHSB005')
            column(name: 'period', value: 202206)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'wr_wrk_inst', value: 306985861)
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
            column(name: 'quantity', value: 1)
            column(name: 'reported_type_of_use', value: 'FAX_PHOTOCOPIES')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'f7c056aa-bc98-49b4-ab12-66ce90b16b89')
            column(name: 'df_udm_usage_batch_uid', value: 'fbd7b557-6087-4eab-9ed4-6a3c311f4a65')
            column(name: 'original_detail_id', value: 'OGN674GHHSB006')
            column(name: 'period', value: 202206)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
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
            column(name: 'quantity', value: 1)
            column(name: 'reported_type_of_use', value: 'FAX_PHOTOCOPIES')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '1d8068ed-5bec-4c2e-ba96-30403c3ec79d')
            column(name: 'df_udm_usage_batch_uid', value: 'fbd7b557-6087-4eab-9ed4-6a3c311f4a65')
            column(name: 'original_detail_id', value: 'OGN674GHHSB007')
            column(name: 'period', value: 202206)
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
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '4daae0f7-6541-4d28-ae90-bb44002e31ce')
            column(name: 'df_udm_usage_batch_uid', value: 'fbd7b557-6087-4eab-9ed4-6a3c311f4a65')
            column(name: 'original_detail_id', value: 'OGN674GGFSB008')
            column(name: 'period', value: 202206)
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
            column(name: 'is_baseline_flag', value: false)
            column(name: 'detail_licensee_class_id', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '7596e527-b50f-4706-8e07-3f39813e799f')
            column(name: 'df_udm_usage_batch_uid', value: 'fbd7b557-6087-4eab-9ed4-6a3c311f4a65')
            column(name: 'original_detail_id', value: 'OGN674GHHSB009')
            column(name: 'period', value: 202206)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'status_ind', value: 'OPS_REVIEW')
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
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'quantity', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '9c52f32c-80fa-4753-8535-913098cc7818')
            column(name: 'df_udm_usage_batch_uid', value: 'fbd7b557-6087-4eab-9ed4-6a3c311f4a65')
            column(name: 'original_detail_id', value: 'OGN674GHHSB010')
            column(name: 'period', value: 202206)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'status_ind', value: 'OPS_REVIEW')
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
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'quantity', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '898b4d3a-0baf-4d26-a24f-e04ffb139f49')
            column(name: 'df_udm_usage_batch_uid', value: 'fbd7b557-6087-4eab-9ed4-6a3c311f4a65')
            column(name: 'original_detail_id', value: 'OGN674GHHSB011')
            column(name: 'period', value: 202206)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'status_ind', value: 'OPS_REVIEW')
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
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'quantity', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '338e1fc2-4254-4dcf-9e95-fa749eb27434')
            column(name: 'df_udm_usage_batch_uid', value: 'fbd7b557-6087-4eab-9ed4-6a3c311f4a65')
            column(name: 'original_detail_id', value: 'OGN674GHHSB012')
            column(name: 'period', value: 202206)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'SPECIALIST_REVIEW')
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
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'quantity', value: 1)
        }

        rollback {
            dbRollback
        }
    }
}
