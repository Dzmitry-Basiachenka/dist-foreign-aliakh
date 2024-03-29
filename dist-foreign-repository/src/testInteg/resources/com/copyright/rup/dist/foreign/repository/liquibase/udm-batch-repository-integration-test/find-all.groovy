databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-05-05-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testFindAll')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ed5610a9-128d-4bf8-b262-7f75a5a9e657')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '864911e5-34ac-42a5-a4c8-84dc4c24e7b4')
            column(name: 'name', value: 'UDM Batch 2021 1')
            column(name: 'period', value: 202106)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '1dcdcc7c-5680-40e6-894d-9c8587fa5331')
            column(name: 'df_udm_usage_batch_uid', value: '864911e5-34ac-42a5-a4c8-84dc4c24e7b4')
            column(name: 'original_detail_id', value: 'OGN674GHHSB011')
            column(name: 'period', value: 202106)
            column(name: 'period_end_date', value: '2021-06-30')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7765')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'format')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 454984566)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'survey_respondent', value: 'a671342a-ff05-4d6a-94ce-5876e950a656')
            column(name: 'ip_address', value: 'ip24.12.119.203')
            column(name: 'survey_country', value: 'USA')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'annual_multiplier', value: 1)
            column(name: 'statistical_multiplier', value: 0.10000)
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2020-02-10 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '6a4b192c-8f1b-4887-a75d-67688544eb5f')
            column(name: 'name', value: 'UDM Batch 2021 2')
            column(name: 'period', value: 202112)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'CCC')
            column(name: 'updated_datetime', value: '2021-03-01 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '8b432f5e-c659-46e6-a42e-f55dd4733f82')
            column(name: 'df_udm_usage_batch_uid', value: '6a4b192c-8f1b-4887-a75d-67688544eb5f')
            column(name: 'original_detail_id', value: 'OGN674GHHSB012')
            column(name: 'period', value: 202112)
            column(name: 'period_end_date', value: '2021-12-31')
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
            column(name: 'ip_address', value: 'ip192.168.211.211')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2021-09-10')
            column(name: 'survey_start_date', value: '2021-09-10')
            column(name: 'survey_end_date', value: '2021-12-31')
            column(name: 'reported_type_of_use', value: 'PHOTOCOPY')
            column(name: 'annualized_copies', value: 3)
            column(name: 'quantity', value: 3)
            column(name: 'df_udm_ineligible_reason_uid', value: 'a4df53dd-26d9-4a0e-956c-e95543707674')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-10 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'faaab569-35c1-474e-923d-96f4c062a62a')
            column(name: 'name', value: 'UDM Batch 2020 1')
            column(name: 'period', value: 202006)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
            column(name: 'updated_datetime', value: '2021-04-20 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '3451cf3b-bb52-454b-a206-9f268285c356')
            column(name: 'df_udm_usage_batch_uid', value: 'faaab569-35c1-474e-923d-96f4c062a62a')
            column(name: 'original_detail_id', value: 'OGN674GHHSB013')
            column(name: 'period', value: 202006)
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7765')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'format')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 454984566)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'survey_respondent', value: '1b552750-234e-432d-980c-1799babb20ff')
            column(name: 'ip_address', value: 'ip24.12.119.213')
            column(name: 'survey_country', value: 'USA')
            column(name: 'usage_date', value: '2020-05-10')
            column(name: 'survey_start_date', value: '2020-06-12')
            column(name: 'survey_end_date', value: '2020-06-30')
            column(name: 'annual_multiplier', value: 25)
            column(name: 'statistical_multiplier', value: 0.20000)
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2020-02-10 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'c57dbf33-b0b9-4493-bcff-c30fd07ee0e4')
            column(name: 'name', value: 'UDM Batch 2020 2')
            column(name: 'period', value: 202012)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'CCC')
            column(name: 'updated_datetime', value: '2021-06-03 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '1c2d34d3-5dd9-4507-a427-0711ea0d94f2')
            column(name: 'df_udm_usage_batch_uid', value: 'c57dbf33-b0b9-4493-bcff-c30fd07ee0e4')
            column(name: 'original_detail_id', value: 'OGN674GHHSB014')
            column(name: 'period', value: 202012)
            column(name: 'period_end_date', value: '2020-12-31')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7765')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'format')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 454984566)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'survey_respondent', value: '17637a5c-bbbf-4aed-b99e-4c9f8a56219a')
            column(name: 'ip_address', value: 'ip24.12.120.23')
            column(name: 'survey_country', value: 'USA')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-12')
            column(name: 'survey_end_date', value: '2020-12-31')
            column(name: 'annual_multiplier', value: 3)
            column(name: 'statistical_multiplier', value: 0.30000)
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2020-02-10 12:00:00+00')
        }

        rollback {
            dbRollback
        }
    }
}
