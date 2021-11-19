databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-05-18-01', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testUpdateProcessedUsage')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '630b0a63-d0f5-46dc-bdec-0b9b81c5d2dc')
            column(name: 'name', value: 'UDM Batch for test update processed usages')
            column(name: 'period', value: 202206)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '0dfba00c-6d72-4a0e-8f84-f7c365432f85')
            column(name: 'df_udm_usage_batch_uid', value: '630b0a63-d0f5-46dc-bdec-0b9b81c5d2dc')
            column(name: 'original_detail_id', value: 'OGL674GHHSR555')
            column(name: 'period', value: '202206')
            column(name: 'period_end_date', value: '2022-06-30')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'wr_wrk_inst', value: 254327612)
            column(name: 'reported_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'reported_standard_number', value: '1008902112377654XX')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'language', value: 'Portugal')
            column(name: 'company_id', value: 1138)
            column(name: 'company_name', value: 'Alcon Laboratories, Inc.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: 'c6615155-f82b-402c-8f22-77e2722ae448')
            column(name: 'ip_address', value: 'ip192.168.211.211')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2021-09-10')
            column(name: 'survey_start_date', value: '2019-09-10')
            column(name: 'survey_end_date', value: '2019-09-11')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'annualized_copies', value: 4)
            column(name: 'quantity', value: 3)
            column(name: 'updated_by_user', value: 'system@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-10 12:00:00+00')
        }

        rollback {
            dbRollback
        }
    }
}
