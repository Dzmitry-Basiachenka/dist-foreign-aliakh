databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-06-29-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testDeleteByBatchId')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'a42efa41-1531-49b2-b065-a40168082a84')
            column(name: 'name', value: 'UDM Batch 2021 June 2')
            column(name: 'period', value: 202406)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '081dbeb4-ec1d-4519-882d-704acb68d8fa')
            column(name: 'df_udm_usage_batch_uid', value: 'a42efa41-1531-49b2-b065-a40168082a84')
            column(name: 'original_detail_id', value: 'OGN674GHHSB002')
            column(name: 'period', value: '202406')
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
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'United States')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-09-10')
            column(name: 'annual_multiplier', value: 1)
            column(name: 'statistical_multiplier', value: 0.10000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'annualized_copies', value: 1)
            column(name: 'quantity', value: 10)
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2020-02-10 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: '37c48b9d-1d1f-400e-ade0-3c9b8539a94b')
            column(name: 'df_udm_usage_uid', value: '081dbeb4-ec1d-4519-882d-704acb68d8fa')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'UDM Batch 2021 June 2\' Batch')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        rollback {
            dbRollback
        }
    }
}
