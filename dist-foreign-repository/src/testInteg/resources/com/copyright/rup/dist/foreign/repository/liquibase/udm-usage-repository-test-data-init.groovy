databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-04-29-01', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testInsertUsageBatch')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'aa5751aa-2858-38c6-b0d9-51ec0edfcf4f')
            column(name: 'name', value: 'UDM Batch 2021 1')
            column(name: 'period', value: '202106')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }
    }

    changeSet(id: '2021-04-29-02', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserting test data for testIsOriginalDetailIdExist')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'bb5751aa-2f56-38c6-b0d9-45ec0edfcf4a')
            column(name: 'name', value: 'UDM Batch 2021 2')
            column(name: 'period', value: '202106')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'cc3269aa-2f56-21c7-b0d1-34dd0edfcf5a')
            column(name: 'df_udm_usage_batch_uid', value: 'bb5751aa-2f56-38c6-b0d9-45ec0edfcf4a')
            column(name: 'original_detail_id', value: 'efa79eef-07e0-4981-a834-4979de7e5a9c')
            column(name: 'period_end_date', value: '2025-09-10')
            column(name: 'status_ind', value: "NEW")
            column(name: 'wr_wrk_inst', value: '306985867')
            column(name: 'reported_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '1873-7773')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'format')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: '454984566')
            column(name: 'survey_respondent', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'ip_address', value: 'ip24.12.119.203')
            column(name: 'survey_country', value: 'USA')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-09-10')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'quantity', value: '10')
        }
    }
}
