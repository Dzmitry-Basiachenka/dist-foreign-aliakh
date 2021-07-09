databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-07-05-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("Insert test data for testWriteUdmUsageCsvReportSpecialistManagerRoles, testWriteUdmUsageCsvReportResearcherRole, testWriteUdmUsageCsvReportViewRole")

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '087f523f-182d-4ff3-a890-5377206bbb94')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'a23681ae-1cf7-44ee-b09b-6fc06779e05c')
            column(name: 'name', value: 'UDM Batch 2020 June')
            column(name: 'period', value: 202006)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'f562892a-f03e-4b78-bae2-ab60ec31311c')
            column(name: 'df_udm_usage_batch_uid', value: 'a23681ae-1cf7-44ee-b09b-6fc06779e05c')
            column(name: 'original_detail_id', value: 'OGN674GHHSB0133')
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
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'survey_respondent', value: '6300a9f8-8da1-4476-95dd-9c4eb474f435')
            column(name: 'ip_address', value: '192.168.211.210')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-05-10')
            column(name: 'survey_start_date', value: '2020-04-20')
            column(name: 'survey_end_date', value: '2020-05-15')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'FAX_PHOTOCOPIES')
            column(name: 'annualized_copies', value: 4)
            column(name: 'quantity', value: 3)
            column(name: 'annual_multiplier', value: 12)
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'annualized_copies', value: 300)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2020-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '1e5d8496-9861-4794-be7b-4357004db799')
            column(name: 'df_udm_usage_batch_uid', value: 'a23681ae-1cf7-44ee-b09b-6fc06779e05c')
            column(name: 'original_detail_id', value: 'OGN674GHHSB002')
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
            column(name: 'company_id', value: 1138)
            column(name: 'company_name', value: 'Alcon Laboratories, Inc.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: 'c91a71a2-4156-417b-ae60-0715d5782380')
            column(name: 'ip_address', value: '192.168.211.211')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-04-10')
            column(name: 'survey_end_date', value: '2020-05-10')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'annualized_copies', value: 4)
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'quantity', value: 3)
            column(name: 'annual_multiplier', value: 12)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2020-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: '3504a963-e4d7-45bf-969a-f617570110c5')
            column(name: 'df_udm_usage_uid', value: 'f562892a-f03e-4b78-bae2-ab60ec31311c')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'UDM Batch 2020 June\' Batch')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: 'f66fb783-4e3b-4372-a30a-91c22acf1f3d')
            column(name: 'df_udm_usage_uid', value: '1e5d8496-9861-4794-be7b-4357004db799')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'UDM Batch 2020 June\' Batch')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '94b644cb-ab57-4825-b985-c51734a5aa1e')
            column(name: 'name', value: 'UDM Batch 2021 December')
            column(name: 'period', value: 202112)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'eb564f79-2430-48a4-901a-e137bafee0ad')
            column(name: 'df_udm_usage_batch_uid', value: '94b644cb-ab57-4825-b985-c51734a5aa1e')
            column(name: 'original_detail_id', value: 'OGN674GHHSB0134')
            column(name: 'period_end_date', value: '2021-12-31')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7766')
            column(name: 'standard_number', value: '1873-7774')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'English')
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Albany International Corp.')
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'survey_respondent', value: 'e0b2837e-53bd-4e93-9a83-afad0f81b896')
            column(name: 'ip_address', value: '192.168.211.22')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2021-12-25')
            column(name: 'survey_start_date', value: '2021-11-01')
            column(name: 'survey_end_date', value: '2021-12-15')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'annualized_copies', value: 4)
            column(name: 'quantity', value: 3)
            column(name: 'annual_multiplier', value: 6)
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'annualized_copies', value: 300)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '680685f8-6a37-4413-8bf2-f877abe4080e')
            column(name: 'df_udm_usage_batch_uid', value: '94b644cb-ab57-4825-b985-c51734a5aa1e')
            column(name: 'original_detail_id', value: 'OGN674GHHSB003')
            column(name: 'period_end_date', value: '2021-12-31')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'wr_wrk_inst', value: 254327612)
            column(name: 'reported_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'reported_standard_number', value: '1008902112377654XX')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'language', value: 'Portugal')
            column(name: 'comment', value: 'Comment')
            column(name: 'research_url', value: 'google.com')
            column(name: 'company_id', value: 1138)
            column(name: 'company_name', value: 'Alcon Laboratories, Inc.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: 'c6615155-f82b-402c-8f22-77e2722ae448')
            column(name: 'ip_address', value: '192.168.211.23')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2021-09-10')
            column(name: 'survey_start_date', value: '2021-04-10')
            column(name: 'survey_end_date', value: '2021-05-10')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'annualized_copies', value: 4)
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'quantity', value: 3)
            column(name: 'annual_multiplier', value: 12)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: '1f5f80d9-fe0e-4823-803d-8758ffc7d0b8')
            column(name: 'df_udm_usage_uid', value: 'eb564f79-2430-48a4-901a-e137bafee0ad')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'UDM Batch 2021 December\' Batch')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: '3db589d9-70a2-497b-9fec-1b84e154d026')
            column(name: 'df_udm_usage_uid', value: '680685f8-6a37-4413-8bf2-f877abe4080e')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'UDM Batch 2021 December\' Batch')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'a524f8a9-2c95-43ea-8c25-b9f38b1c758e')
            column(name: 'name', value: 'UDM Batch 2022 June')
            column(name: 'period', value: 202206)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '975ed4dc-b560-4e89-bd6d-6a5a18f14fa3')
            column(name: 'df_udm_usage_batch_uid', value: 'a524f8a9-2c95-43ea-8c25-b9f38b1c758e')
            column(name: 'original_detail_id', value: 'OGN674GHHSB004')
            column(name: 'period_end_date', value: '2022-06-30')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'wr_wrk_inst', value: 254327612)
            column(name: 'reported_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'reported_standard_number', value: '1008902112377664XX')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'language', value: 'Portugal')
            column(name: 'comment', value: 'Comment')
            column(name: 'research_url', value: 'google.com')
            column(name: 'company_id', value: 1138)
            column(name: 'company_name', value: 'Alcon Laboratories, Inc.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: 'ff55f2eb-d92a-4a9f-a081-2b6bb9e014cf')
            column(name: 'ip_address', value: '192.168.211.30')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2022-09-10')
            column(name: 'survey_start_date', value: '2022-03-15')
            column(name: 'survey_end_date', value: '2022-05-15')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'annualized_copies', value: 4)
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'quantity', value: 3)
            column(name: 'annual_multiplier', value: 6)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: 'de47fbb2-bf94-46fc-984a-c2b583d8a68e')
            column(name: 'df_udm_usage_uid', value: '975ed4dc-b560-4e89-bd6d-6a5a18f14fa3')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'UDM Batch 2022 June\' Batch')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
        }

        rollback ""
    }
}