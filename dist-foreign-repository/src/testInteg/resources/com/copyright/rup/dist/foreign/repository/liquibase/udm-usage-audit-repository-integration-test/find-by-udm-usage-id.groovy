databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-06-09-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindByUdmUsageId, testInsert')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '14edbc9b-1473-4fc9-95f6-07b3ef45e851')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '6168bda5-0a16-4bac-be7e-218a4d1feca8')
            column(name: 'name', value: 'UDM Batch 2021 June')
            column(name: 'period', value: 202406)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'e6040f00-8564-4482-ab67-9965483a8a9f')
            column(name: 'df_udm_usage_batch_uid', value: '6168bda5-0a16-4bac-be7e-218a4d1feca8')
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
            column(name: 'publication_format', value: 'format')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: 'e7370736-60c0-4283-9948-717d075f152f')
            column(name: 'df_udm_usage_uid', value: 'e6040f00-8564-4482-ab67-9965483a8a9f')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'UDM Batch 2021 June\' Batch')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        rollback {
            dbRollback
        }
    }
}
