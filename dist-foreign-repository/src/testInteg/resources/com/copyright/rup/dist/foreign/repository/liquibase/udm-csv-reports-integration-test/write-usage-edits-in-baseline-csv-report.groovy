databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-03-15-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testWriteUsageEditsInBaselineCsvReport, testWriteUsageEditsInBaselineEmptyCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'b3d58aa3-8e6d-422a-8371-181c672a019f')
            column(name: 'name', value: 'UDM Batch 2021 June')
            column(name: 'period', value: 202106)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '1f876f61-6750-4488-b280-65112de6d217')
            column(name: 'df_udm_usage_batch_uid', value: 'b3d58aa3-8e6d-422a-8371-181c672a019f')
            column(name: 'original_detail_id', value: 'OGN674GHHSB007')
            column(name: 'period', value: 202106)
            column(name: 'period_end_date', value: '2021-06-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '0927-7765')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 454984566)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'survey_respondent', value: 'f86412b1-a555-4acf-96f9-b909b139533f')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'United States')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'quantity', value: 1)
            column(name: 'annualized_copies', value: 1)
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'is_baseline_flag', value: false)
            column(name: 'baseline_created_by_user', value: 'user@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-04-15 12:01:00+00')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-04-14 11:00:00+00')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-14 12:07:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '0a60b477-66dd-4308-87dc-65a40c01cbec')
            column(name: 'df_udm_usage_batch_uid', value: 'b3d58aa3-8e6d-422a-8371-181c672a019f')
            column(name: 'original_detail_id', value: 'OGN674GHHSB008')
            column(name: 'period', value: 202106)
            column(name: 'period_end_date', value: '2021-06-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002854)
            column(name: 'wr_wrk_inst', value: 20008506)
            column(name: 'reported_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'system_title', value: 'Colloids and surfaces. B, Biointerfaces')
            column(name: 'reported_standard_number', value: '0927-7765')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 454984566)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'survey_respondent', value: '515e61c2-f228-4c3f-92e2-2e6c1a1192a3')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'quantity', value: 2)
            column(name: 'annualized_copies', value: 2)
            column(name: 'is_baseline_flag', value: false)
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'baseline_created_by_user', value: 'user@copyright.com')
            column(name: 'baseline_created_datetime', value: '2020-04-15 12:02:00+00')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-02-15 12:00:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:08:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: '0c6e38d2-6dc9-4c12-a13a-1538a0e2e47d')
            column(name: 'df_udm_usage_uid', value: '1f876f61-6750-4488-b280-65112de6d217')
            column(name: 'action_type_ind', value: 'PUBLISH_TO_BASELINE')
            column(name: 'action_reason', value: 'UDM usage was published to baseline by \'user@copyright.com\'')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-04-15 12:01:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:01:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: '25596c0a-6be9-4b93-ab21-e3ddd434b966')
            column(name: 'df_udm_usage_uid', value: '0a60b477-66dd-4308-87dc-65a40c01cbec')
            column(name: 'action_type_ind', value: 'PUBLISH_TO_BASELINE')
            column(name: 'action_reason', value: 'UDM usage was published to baseline by \'user@copyright.com\'')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-04-15 12:02:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:02:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: '781fbcd8-032f-46fc-8b3e-239aebf0128d')
            column(name: 'df_udm_usage_uid', value: '1f876f61-6750-4488-b280-65112de6d217')
            column(name: 'action_type_ind', value: 'USAGE_EDIT')
            column(name: 'action_reason', value: 'The field \'Comment\' was edited. Old Value is \'some comment\'. New Value is \'another comment\'')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-04-15 12:03:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:03:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: '611c2f99-e6d9-4be4-a31d-efda90ca94d4')
            column(name: 'df_udm_usage_uid', value: '0a60b477-66dd-4308-87dc-65a40c01cbec')
            column(name: 'action_type_ind', value: 'USAGE_EDIT')
            column(name: 'action_reason', value: 'The field \'Reported Pub Type\' was edited. Old Value is \'Book\'. New Value is \'Journal\'')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-04-15 12:04:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:04:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: 'f1732449-1585-4018-bf87-8196874c6c33')
            column(name: 'df_udm_usage_uid', value: '1f876f61-6750-4488-b280-65112de6d217')
            column(name: 'action_type_ind', value: 'REMOVE_FROM_BASELINE')
            column(name: 'action_reason', value: 'some reason')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-04-15 12:05:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:05:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: 'baf2de06-a759-4ae3-996d-e5980b2864ef')
            column(name: 'df_udm_usage_uid', value: '0a60b477-66dd-4308-87dc-65a40c01cbec')
            column(name: 'action_type_ind', value: 'REMOVE_FROM_BASELINE')
            column(name: 'action_reason', value: 'another reason')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-04-15 12:06:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:06:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: '39548fc5-0d92-4e34-a405-cd1837985dff')
            column(name: 'df_udm_usage_uid', value: '1f876f61-6750-4488-b280-65112de6d217')
            column(name: 'action_type_ind', value: 'USAGE_EDIT')
            column(name: 'action_reason', value: 'The field \'Comment\' was edited. Old Value is \'another comment\'. New Value is \'some comment\'')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-04-15 12:07:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:07:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_audit') {
            column(name: 'df_udm_audit_uid', value: '5f922348-d064-42a8-af7e-8496d1d0e596')
            column(name: 'df_udm_usage_uid', value: '0a60b477-66dd-4308-87dc-65a40c01cbec')
            column(name: 'action_type_ind', value: 'USAGE_EDIT')
            column(name: 'action_reason', value: 'The field \'Reported Pub Type\' was edited. Old Value is \'Journal\'. New Value is \'Book\'')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2020-04-15 12:08:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2020-04-15 12:08:00+00')
        }

        rollback {
            dbRollback
        }
    }
}
