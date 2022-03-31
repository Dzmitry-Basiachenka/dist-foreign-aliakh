databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-03-31-01', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testPopulateAclUsages')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '6eb08947-be9e-4c4b-95a3-ca6012cae23d')
            column(name: 'name', value: 'UDM Batch 2021')
            column(name: 'period', value: 202106)
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '316612be-054b-4959-8d04-889d1f19f70b')
            column(name: 'period', value: 202112)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'price', value: 5.0000000000)
            column(name: 'price_in_usd', value: 5.0000000000)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'content', value: 1)
            column(name: 'content_source', value: 'Book')
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 1.0000000000)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 5.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '22241298-5c9e-4222-8fc1-5ee80c0e48f1')
            column(name: 'df_udm_usage_batch_uid', value: '6eb08947-be9e-4c4b-95a3-ca6012cae23d')
            column(name: 'original_detail_id', value: 'OGN674GHHSB007')
            column(name: 'period', value: 202112)
            column(name: 'period_end_date', value: '2021-06-30')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'reported_title', value: 'Tenside, surfactants, detergents')
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'reported_standard_number', value: '1873-7773')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Tenside, surfactants, detergents')
            column(name: 'language', value: 'English')
            column(name: 'company_id', value: 454984566)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'survey_respondent', value: 'f86412b1-a555-4acf-96f9-b909b139533f')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'United States')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'quantity', value: 1)
            column(name: 'statistical_multiplier', value: 1.00000)
            column(name: 'annual_multiplier', value: 1)
            column(name: 'annualized_copies', value: 1)
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'is_baseline_flag', value: true)
            column(name: 'df_udm_value_uid', value: '316612be-054b-4959-8d04-889d1f19f70b')
            column(name: 'baseline_created_by_user', value: 'user@copyright.com')
            column(name: 'baseline_created_datetime', value: '2021-12-31 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '1fa04580-af0a-4c57-8470-37ae9c06bea1')
            column(name: 'name', value: 'ACL Usage Batch 2021')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
