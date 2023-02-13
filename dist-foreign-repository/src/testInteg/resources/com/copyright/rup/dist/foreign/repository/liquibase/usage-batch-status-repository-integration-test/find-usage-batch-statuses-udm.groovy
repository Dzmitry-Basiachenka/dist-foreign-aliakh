databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-02-10-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testFindUsageBatchStatusesUdm')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'a7dcce36-d02a-4b28-91e0-a81e0da442f2')
            column(name: 'name', value: 'UDM Batch 2022')
            column(name: 'period', value: 202206)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '999b8243-b971-4f43-bd70-2b14262fb7e2')
            column(name: 'df_udm_usage_batch_uid', value: 'a7dcce36-d02a-4b28-91e0-a81e0da442f2')
            column(name: 'original_detail_id', value: 'OGN674GHHSB002')
            column(name: 'period', value: 202206)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7766')
            column(name: 'standard_number', value: '1873-7774')
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
            column(name: 'df_udm_usage_uid', value: '5b306473-0d89-4f99-98b1-4d899c19ba15')
            column(name: 'df_udm_usage_batch_uid', value: 'a7dcce36-d02a-4b28-91e0-a81e0da442f2')
            column(name: 'original_detail_id', value: 'OGN554GHHSG009')
            column(name: 'period', value: 202206)
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002854)
            column(name: 'wr_wrk_inst', value: 20008506)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'reported_standard_number', value: '0927-7767')
            column(name: 'standard_number', value: '1873-7775')
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
            column(name: 'baseline_created_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '2bc7c9fc-883d-4230-ba5c-df01abfad9fc')
            column(name: 'name', value: 'UDM Batch 2021')
            column(name: 'period', value: 202106)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
            column(name: 'created_datetime', value: '2022-02-13 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-13 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '5aad5183-3f86-4b46-a19a-030d4a87cc52')
            column(name: 'df_udm_usage_batch_uid', value: '2bc7c9fc-883d-4230-ba5c-df01abfad9fc')
            column(name: 'original_detail_id', value: 'OGN554GHHSG006')
            column(name: 'period', value: 202106)
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'wr_wrk_inst', value: 254327612)
            column(name: 'reported_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'reported_standard_number', value: '1008902112377654XX')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'language', value: 'Portugal')
            column(name: 'company_id', value: 214984577)
            column(name: 'survey_respondent', value: '8ca07787-dae1-4599-943c-75f86eabd213')
            column(name: 'ip_address', value: '192.168.211.211')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2021-09-10')
            column(name: 'survey_start_date', value: '2021-09-10')
            column(name: 'survey_end_date', value: '2021-12-31')
            column(name: 'reported_type_of_use', value: 'PHOTOCOPY')
            column(name: 'annualized_copies', value: 3)
            column(name: 'quantity', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '2e6ed286-70e3-4fe2-8e53-59230fb5be29')
            column(name: 'df_udm_usage_batch_uid', value: '2bc7c9fc-883d-4230-ba5c-df01abfad9fc')
            column(name: 'original_detail_id', value: 'OGN554GHHSG007')
            column(name: 'period', value: 202106)
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'wr_wrk_inst', value: 254327613)
            column(name: 'reported_title', value: 'Current topics in library and information practice')
            column(name: 'reported_standard_number', value: '1008902112377655XX')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Current topics in library and information practice')
            column(name: 'language', value: 'Portugal')
            column(name: 'company_id', value: 1138)
            column(name: 'company_name', value: 'Alcon Laboratories, Inc.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: '8ca07787-dae1-4599-943c-75f86eabd213')
            column(name: 'ip_address', value: '192.168.211.211')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2021-09-10')
            column(name: 'survey_start_date', value: '2021-09-10')
            column(name: 'survey_end_date', value: '2021-12-31')
            column(name: 'reported_type_of_use', value: 'PHOTOCOPY')
            column(name: 'annualized_copies', value: 3)
            column(name: 'quantity', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '5b0d5ed0-5681-4ed4-beb6-680456ba88da')
            column(name: 'df_udm_usage_batch_uid', value: '2bc7c9fc-883d-4230-ba5c-df01abfad9fc')
            column(name: 'original_detail_id', value: 'OGN674GHHSB007')
            column(name: 'period', value: 202106)
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
            column(name: 'df_udm_usage_uid', value: '5fc87048-c62f-45bd-ba7c-837b0b6c0aa8')
            column(name: 'df_udm_usage_batch_uid', value: '2bc7c9fc-883d-4230-ba5c-df01abfad9fc')
            column(name: 'original_detail_id', value: 'OGN554GHHSG008')
            column(name: 'period', value: 202106)
            column(name: 'period_end_date', value: '2020-12-30')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'wr_wrk_inst', value: 254327612)
            column(name: 'reported_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'system_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'reported_standard_number', value: '1008902112377654XX')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'language', value: 'English')
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Albany International Corp.')
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'survey_respondent', value: 'c6615155-f82b-402c-8f22-77e2722ae448')
            column(name: 'ip_address', value: '192.168.211.211')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-05-10')
            column(name: 'survey_start_date', value: '2020-04-20')
            column(name: 'survey_end_date', value: '2020-05-15')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'annualized_copies', value: 4)
            column(name: 'quantity', value: 3)
            column(name: 'annual_multiplier', value: 25)
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'annualized_copies', value: 300)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '38ffa94d-f015-4f65-9270-9298c6f35a68')
            column(name: 'df_udm_usage_batch_uid', value: '2bc7c9fc-883d-4230-ba5c-df01abfad9fc')
            column(name: 'original_detail_id', value: 'OGN554GHHSG010 !@#$%^&*()_+-=?/\\\'"}{][<>')
            column(name: 'period', value: 202106)
            column(name: 'period_end_date', value: '2020-12-30')
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
            column(name: 'quantity', value: 0)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '9af9ef2f-ab69-429a-a6a0-288a901907a0')
            column(name: 'df_udm_usage_batch_uid', value: '2bc7c9fc-883d-4230-ba5c-df01abfad9fc')
            column(name: 'original_detail_id', value: 'OGN554GHHSS001')
            column(name: 'period', value: 202106)
            column(name: 'period_end_date', value: '2021-06-30')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 20008506)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7768')
            column(name: 'standard_number', value: '1873-7776')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Not Specified')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 454984566)
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'survey_respondent', value: 'a671342a-ff05-4d6a-94ce-5876e950a656')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'United States')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'annual_multiplier', value: 1)
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'annualized_copies', value: 1)
            column(name: 'quantity', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'cc2af2d8-646f-47a1-8bae-8cc0b4c05d01')
            column(name: 'df_udm_usage_batch_uid', value: '2bc7c9fc-883d-4230-ba5c-df01abfad9fc')
            column(name: 'original_detail_id', value: 'OGN674GHHSB006')
            column(name: 'period', value: 202106)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7769')
            column(name: 'standard_number', value: '1873-7777')
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
            column(name: 'df_udm_usage_uid', value: '33876bba-cf6a-412e-b4e4-4378c86d728b')
            column(name: 'df_udm_usage_batch_uid', value: '2bc7c9fc-883d-4230-ba5c-df01abfad9fc')
            column(name: 'original_detail_id', value: 'OGN674GHHSB1287')
            column(name: 'period', value: 202106)
            column(name: 'period_end_date', value: '2020-12-31')
            column(name: 'status_ind', value: 'OPS_REVIEW')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'wr_wrk_inst', value: 10002508)
            column(name: 'reported_title', value: 'Tenside, surfactants, detergents')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7710')
            column(name: 'standard_number', value: '1873-7778')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'English')
            column(name: 'company_id', value: 1138)
            column(name: 'company_name', value: 'Alcon Laboratories, Inc.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: 'c6615155-f82b-402c-8f22-77e2722ae448')
            column(name: 'ip_address', value: '192.168.211.211')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-05-10')
            column(name: 'survey_start_date', value: '2020-04-20')
            column(name: 'survey_end_date', value: '2020-05-15')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'annualized_copies', value: 4)
            column(name: 'quantity', value: 3)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'annualized_copies', value: 300)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'a7f7c522-480e-45da-a552-1fa2f0c5f861')
            column(name: 'df_udm_usage_batch_uid', value: '2bc7c9fc-883d-4230-ba5c-df01abfad9fc')
            column(name: 'original_detail_id', value: 'OGN674GHHSB012')
            column(name: 'period', value: 202106)
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

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'b809b629-7023-4126-8277-4854b5ab496a')
            column(name: 'df_udm_usage_batch_uid', value: '2bc7c9fc-883d-4230-ba5c-df01abfad9fc')
            column(name: 'original_detail_id', value: 'OGN554GHHSG011')
            column(name: 'period', value: 202106)
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
            column(name: 'baseline_created_datetime', value: '2022-02-13 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '67ecbb20-6188-44e9-a59c-d24ee64440c5')
            column(name: 'name', value: 'UDM Batch 2020')
            column(name: 'period', value: 202006)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2020-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '6135ffd6-3136-4a70-9cd2-3d29a2be2e83')
            column(name: 'df_udm_usage_batch_uid', value: '67ecbb20-6188-44e9-a59c-d24ee64440c5')
            column(name: 'original_detail_id', value: 'OGN674GHHSB003')
            column(name: 'period', value: 202006)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7771')
            column(name: 'standard_number', value: '1873-7779')
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
