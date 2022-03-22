databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-09-09-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testIsUdmBatchContainsBaselineUsages')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'e133e00c-d8bb-4be2-ae54-a82c9306a224')
            column(name: 'name', value: 'UDM Batch  without baseline')
            column(name: 'period', value: 202012)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'CCC')
            column(name: 'updated_datetime', value: '2021-01-03 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '4af57d81-502b-48a0-9268-36eab1c4add2')
            column(name: 'df_udm_usage_batch_uid', value: 'e133e00c-d8bb-4be2-ae54-a82c9306a224')
            column(name: 'original_detail_id', value: 'OGN674GHHSB1287')
            column(name: 'period', value: 202012)
            column(name: 'period_end_date', value: '2020-12-31')
            column(name: 'status_ind', value: 'OPS_REVIEW')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7765')
            column(name: 'standard_number', value: '1873-7773')
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
            column(name: 'is_baseline_flag', value: false)
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-10 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '5de7aee8-17ca-47b3-a880-ae4e36463e1f')
            column(name: 'name', value: 'UDM Batch with baseline')
            column(name: 'period', value: 202012)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'CCC')
            column(name: 'updated_datetime', value: '2021-01-03 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '8641aa14-703b-40a8-8cd1-603e90e2f800')
            column(name: 'df_udm_usage_batch_uid', value: '5de7aee8-17ca-47b3-a880-ae4e36463e1f')
            column(name: 'original_detail_id', value: 'OGN574GHHSB1387')
            column(name: 'period', value: 202012)
            column(name: 'period_end_date', value: '2020-12-31')
            column(name: 'status_ind', value: 'OPS_REVIEW')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7765')
            column(name: 'standard_number', value: '1873-7773')
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
            column(name: 'is_baseline_flag', value: true)
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
