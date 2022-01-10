databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-01-10-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Insert test data for testCompletedAssignmentsReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '0f7d2ea3-41ac-41fc-a16d-1a9498db53a2')
            column(name: 'name', value: 'UDM Batch 2020 June')
            column(name: 'period', value: 202006)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'daaa2c77-1d67-4d38-9439-6f8407e0e49c')
            column(name: 'df_udm_usage_batch_uid', value: '0f7d2ea3-41ac-41fc-a16d-1a9498db53a2')
            column(name: 'original_detail_id', value: 'OGN174GHHSB001')
            column(name: 'period', value: 202006)
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
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'quantity', value: 1)
            column(name: 'annualized_copies', value: 1)
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'created_by_user', value: 'ajohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_by_user', value: 'ajohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: '679ea8cb-c4e5-4be8-8fb2-d1e7a95f082f')
            column(name: 'df_udm_usage_uid', value: 'daaa2c77-1d67-4d38-9439-6f8407e0e49c')
            column(name: 'action_type_ind', value: 'UNASSIGN')
            column(name: 'action_reason', value: 'Usage was unassigned from \'ajohn@copyright.com\'')
            column(name: 'created_by_user', value: 'ajohn@copyright.com')
            column(name: 'updated_by_user', value: 'ajohn@copyright.com')
            column(name: 'created_datetime', value: '2020-07-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'acfb7fd7-30ce-42fa-9f13-42bf800b51f5')
            column(name: 'df_udm_usage_batch_uid', value: '0f7d2ea3-41ac-41fc-a16d-1a9498db53a2')
            column(name: 'original_detail_id', value: 'OGN174GHHSB002')
            column(name: 'period', value: 202006)
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
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'quantity', value: 1)
            column(name: 'annualized_copies', value: 1)
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'created_by_user', value: 'ajohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_by_user', value: 'ajohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: 'e33bef75-a2e7-4cdf-9831-c618d933bb97')
            column(name: 'df_udm_usage_uid', value: 'acfb7fd7-30ce-42fa-9f13-42bf800b51f5')
            column(name: 'action_type_ind', value: 'UNASSIGN')
            column(name: 'action_reason', value: 'Usage was unassigned from \'ajohn@copyright.com\'')
            column(name: 'created_by_user', value: 'ajohn@copyright.com')
            column(name: 'updated_by_user', value: 'ajohn@copyright.com')
            column(name: 'created_datetime', value: '2020-07-16 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '39b0155e-bfbf-47f1-b1e6-5d03abf8c860')
            column(name: 'df_udm_usage_batch_uid', value: '0f7d2ea3-41ac-41fc-a16d-1a9498db53a2')
            column(name: 'original_detail_id', value: 'OGN174GHHSB003')
            column(name: 'period', value: 202006)
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
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'quantity', value: 1)
            column(name: 'annualized_copies', value: 1)
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: 'ac695139-bba3-4b74-9537-d88394371cda')
            column(name: 'df_udm_usage_uid', value: '39b0155e-bfbf-47f1-b1e6-5d03abf8c860')
            column(name: 'action_type_ind', value: 'UNASSIGN')
            column(name: 'action_reason', value: 'Usage was unassigned from \'jjohn@copyright.com\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-07-16 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '2f976688-37b0-4346-a5d9-13933e0f8cbc')
            column(name: 'df_udm_usage_batch_uid', value: '0f7d2ea3-41ac-41fc-a16d-1a9498db53a2')
            column(name: 'original_detail_id', value: 'OGN174GHHSB004')
            column(name: 'period', value: 202006)
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
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'quantity', value: 1)
            column(name: 'annualized_copies', value: 1)
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_datetime', value: '2020-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: '2f976688-37b0-4346-a5d9-13933e0f8cbc')
            column(name: 'df_udm_usage_uid', value: '39b0155e-bfbf-47f1-b1e6-5d03abf8c860')
            column(name: 'action_type_ind', value: 'UNASSIGN')
            column(name: 'action_reason', value: 'Usage was unassigned from \'jjohn@copyright.com\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-08-16 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: 'b7c74c9f-d747-49cf-b469-4dd16b30ae59')
            column(name: 'period', value: 202006)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 823333781)
            column(name: 'system_title', value: 'Redfords and Mitchells')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'price', value: 150)
            column(name: 'price_in_usd', value: 150)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 1)
            column(name: 'content_flag', value: true)
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 1)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 150)
            column(name: 'publication_type_uid', value: 'ad8df236-5200-4acf-be55-cf82cd342f14')
            column(name: 'comment', value: 'Comment')
            column(name: 'content_source', value: 'Book')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2021-09-20T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2021-09-20T00:00:00-04:00')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: '47e65d54-1020-4d5b-b50d-cb4f4d36ba14')
            column(name: 'df_udm_value_uid', value: 'b7c74c9f-d747-49cf-b469-4dd16b30ae59')
            column(name: 'action_type_ind', value: 'UNASSIGN')
            column(name: 'action_reason', value: 'Value was unassigned from \'jjohn@copyright.com\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-07-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '51b840d8-01f3-47f0-af01-0f236c90e37d')
            column(name: 'period', value: 202006)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 823333782)
            column(name: 'system_title', value: 'Redfords and Mitchells')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'price', value: 150)
            column(name: 'price_in_usd', value: 150)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 1)
            column(name: 'content_flag', value: true)
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 1)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 150)
            column(name: 'publication_type_uid', value: 'ad8df236-5200-4acf-be55-cf82cd342f14')
            column(name: 'comment', value: 'Comment')
            column(name: 'content_source', value: 'Book')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2021-09-20T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'wjohn@copyright.com')
            column(name: 'created_datetime', value: '2021-09-20T00:00:00-04:00')
            column(name: 'created_by_user', value: 'wjohn@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: 'ea3c3ddb-5831-47db-9d2e-ce53a4ec15c8')
            column(name: 'df_udm_value_uid', value: '51b840d8-01f3-47f0-af01-0f236c90e37d')
            column(name: 'action_type_ind', value: 'UNASSIGN')
            column(name: 'action_reason', value: 'Value was unassigned from \'wjohn@copyright.com\'')
            column(name: 'created_by_user', value: 'wjohn@copyright.com')
            column(name: 'updated_by_user', value: 'wjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-07-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '4174d605-f2eb-4618-ba94-d6cc47a19215')
            column(name: 'period', value: 202006)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 823333783)
            column(name: 'system_title', value: 'Redfords and Mitchells')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'price', value: 150)
            column(name: 'price_in_usd', value: 150)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 1)
            column(name: 'content_flag', value: true)
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 1)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 150)
            column(name: 'publication_type_uid', value: 'ad8df236-5200-4acf-be55-cf82cd342f14')
            column(name: 'comment', value: 'Comment')
            column(name: 'content_source', value: 'Book')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2021-09-20T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'wjohn@copyright.com')
            column(name: 'created_datetime', value: '2021-09-20T00:00:00-04:00')
            column(name: 'created_by_user', value: 'wjohn@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: '2fffeefc-721a-4450-83e2-fed4dff60913')
            column(name: 'df_udm_value_uid', value: '4174d605-f2eb-4618-ba94-d6cc47a19215')
            column(name: 'action_type_ind', value: 'UNASSIGN')
            column(name: 'action_reason', value: 'Value was unassigned from \'wjohn@copyright.com\'')
            column(name: 'created_by_user', value: 'wjohn@copyright.com')
            column(name: 'updated_by_user', value: 'wjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-07-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '94164245-5e0d-4d6d-84d8-1294724d32f3')
            column(name: 'period', value: 202006)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 823333785)
            column(name: 'system_title', value: 'Redfords and Mitchells')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'price', value: 150)
            column(name: 'price_in_usd', value: 150)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 1)
            column(name: 'content_flag', value: true)
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 1)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 150)
            column(name: 'publication_type_uid', value: 'ad8df236-5200-4acf-be55-cf82cd342f14')
            column(name: 'comment', value: 'Comment')
            column(name: 'content_source', value: 'Book')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2021-09-20T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'kjohn@copyright.com')
            column(name: 'created_datetime', value: '2021-09-20T00:00:00-04:00')
            column(name: 'created_by_user', value: 'kjohn@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: '5b8f10a4-79c6-4d6a-b8ee-5a8736fc922a')
            column(name: 'df_udm_value_uid', value: '94164245-5e0d-4d6d-84d8-1294724d32f3')
            column(name: 'action_type_ind', value: 'UNASSIGN')
            column(name: 'action_reason', value: 'Value was unassigned from \'kjohn@copyright.com\'')
            column(name: 'created_by_user', value: 'kjohn@copyright.com')
            column(name: 'updated_by_user', value: 'kjohn@copyright.com')
            column(name: 'created_datetime', value: '2020-07-15 11:41:52.735531+03')
        }

        rollback {
            dbRollback
        }
    }
}
