databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-06-15-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testFindDtosByAllFilters, testFindCountByAllFilters, testFindDtosByFilter, testFindCountByFilter')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '80452178-e250-415f-b3e4-71a48ca3e218')
            column(name: 'name', value: 'UDM Batch 2021 to test filtering')
            column(name: 'period', value: 202106)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '9c7f64a7-95c1-4825-ad85-ff0672d252c4')
            column(name: 'df_udm_usage_batch_uid', value: '80452178-e250-415f-b3e4-71a48ca3e218')
            column(name: 'original_detail_id', value: 'OGN674GHHSB0107')
            column(name: 'period', value: '202106')
            column(name: 'period_end_date', value: '2021-06-30')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'rh_account_number', value: 1000002612)
            column(name: 'wr_wrk_inst', value: 254327612)
            column(name: 'reported_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'system_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'reported_standard_number', value: '1008902112377654XX')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'language', value: 'English')
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Albany International Corp.')
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'survey_respondent', value: 'c6615155-f82b-402c-8f22-77e2722ae448')
            column(name: 'ip_address', value: 'ip192.168.211.211')
            column(name: 'survey_country', value: 'United States')
            column(name: 'usage_date', value: '2020-05-10')
            column(name: 'survey_start_date', value: '2020-04-20')
            column(name: 'survey_end_date', value: '2020-05-15')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'COPY_FOR_MYSELF')
            column(name: 'quantity', value: 1)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annualized_copies', value: 1.00000)
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-10 12:00:00+00')
            column(name: 'record_version', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'b989e02b-1f1d-4637-b89e-dc99938a51b9')
            column(name: 'df_udm_usage_batch_uid', value: '80452178-e250-415f-b3e4-71a48ca3e218')
            column(name: 'original_detail_id', value: 'OGN674GHHSB0108')
            column(name: 'period', value: '202106')
            column(name: 'period_end_date', value: '2021-06-30')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'rh_account_number', value: 1000002612)
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
            column(name: 'survey_country', value: 'United States')
            column(name: 'usage_date', value: '2020-05-10')
            column(name: 'survey_start_date', value: '2020-04-20')
            column(name: 'survey_end_date', value: '2020-05-15')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'COPY_FOR_MYSELF')
            column(name: 'quantity', value: 3)
            column(name: 'annual_multiplier', value: 25)
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annualized_copies', value: 75.00000)
            column(name: 'comment', value: 'Assigned to wjohn for review')
            column(name: 'research_url', value: 'google.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-10 12:00:00+00')
            column(name: 'record_version', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'c3e3082f-9c3e-4e14-9640-c485a9eae24f')
            column(name: 'df_udm_usage_batch_uid', value: '80452178-e250-415f-b3e4-71a48ca3e218')
            column(name: 'original_detail_id', value: 'OGN674GHHSB0109')
            column(name: 'period', value: '202106')
            column(name: 'period_end_date', value: '2021-06-30')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'rh_account_number', value: 1000002612)
            column(name: 'wr_wrk_inst', value: 254327612)
            column(name: 'reported_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'system_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'reported_standard_number', value: '1008902112377654XX')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'language', value: 'English')
            column(name: 'company_id', value: 1138)
            column(name: 'company_name', value: 'Alcon Laboratories, Inc.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: 'c6615155-f82b-402c-8f22-77e2722ae448')
            column(name: 'ip_address', value: 'ip192.168.211.211')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-08-20')
            column(name: 'survey_end_date', value: '2020-10-15')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'quantity', value: 17)
            column(name: 'annual_multiplier', value: 25)
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annualized_copies', value: 425.00000)
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-10 12:00:00+00')
        }

        rollback {
            dbRollback
        }
    }
}
