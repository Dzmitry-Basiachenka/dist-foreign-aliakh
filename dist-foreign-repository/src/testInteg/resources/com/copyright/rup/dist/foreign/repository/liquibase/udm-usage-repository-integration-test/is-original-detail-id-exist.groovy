databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-04-29-02', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testIsOriginalDetailIdExist, testSortingFindDtosByFilter')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '14edbc9b-1473-4fc9-95f6-07b3ef45e851')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'bb5751aa-2f56-38c6-b0d9-45ec0edfcf4a')
            column(name: 'name', value: 'UDM Batch 2021 2')
            column(name: 'period', value: 202406)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'cc3269aa-2f56-21c7-b0d1-34dd0edfcf5a')
            column(name: 'df_udm_usage_batch_uid', value: 'bb5751aa-2f56-38c6-b0d9-45ec0edfcf4a')
            column(name: 'original_detail_id', value: 'OGN674GHHSB001')
            column(name: 'period', value: 202406)
            column(name: 'period_end_date', value: '2024-06-30')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7765')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Not Specified')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
            column(name: 'comment', value: 'UDM comment')
            column(name: 'research_url', value: 'google.com')
            column(name: 'company_id', value: 454984566)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'survey_respondent', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'ip_address', value: 'ip24.12.119.203')
            column(name: 'survey_country', value: 'USA')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-09-10')
            column(name: 'annual_multiplier', value: 5)
            column(name: 'statistical_multiplier', value: 0.10000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2020-02-10 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '4b6055be-fc4e-4b49-aeab-28563366c9fd')
            column(name: 'name', value: 'UDM Batch 2020 2')
            column(name: 'period', value: 202506)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '847dfefd-3cf8-4853-8b1b-d59b5cd163e9')
            column(name: 'df_udm_usage_batch_uid', value: '4b6055be-fc4e-4b49-aeab-28563366c9fd')
            column(name: 'original_detail_id', value: 'OGN674GHHSB002')
            column(name: 'period', value: 202506)
            column(name: 'period_end_date', value: '2025-06-30')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'wr_wrk_inst', value: 254327612)
            column(name: 'reported_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'reported_standard_number', value: '1008902112377654XX')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'language', value: 'Portugal')
            column(name: 'comment', value: 'ACL comment')
            column(name: 'research_url', value: 'copyright.com')
            column(name: 'company_id', value: 1138)
            column(name: 'company_name', value: 'Alcon Laboratories, Inc.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: 'c6615155-f82b-402c-8f22-77e2722ae448')
            column(name: 'ip_address', value: 'ip192.168.211.211')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2021-09-10')
            column(name: 'survey_start_date', value: '2021-09-10')
            column(name: 'survey_end_date', value: '2022-09-10')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'annualized_copies', value: 4)
            column(name: 'quantity', value: 3)
            column(name: 'df_udm_action_reason_uid', value: 'ccbd22af-32bf-4162-8145-d49eae14c800')
            column(name: 'df_udm_ineligible_reason_uid', value: 'a4df53dd-26d9-4a0e-956c-e95543707674')
            column(name: 'updated_by_user', value: 'system@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-10 12:00:00+00')
        }

        rollback {
            dbRollback
        }
    }
}
