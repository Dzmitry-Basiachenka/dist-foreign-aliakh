databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-05-27-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testExecuteInternal test')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '37338ed1-7083-45e2-a96b-5872a7de3a98')
            column(name: 'rh_account_number', value: 2000139286)
            column(name: 'name', value: '2HC [T]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'd311340c-60e8-4df1-bbe1-788ba2ed9a15')
            column(name: 'rh_account_number', value: 1000023401)
            column(name: 'name', value: 'American City Business Journals, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e92a3622-0f16-49f8-bdc0-dacd9ade1245')
            column(name: 'name', value: 'Test Usage Batch to verify GetRightsJob')
            column(name: 'rro_account_number', value: 1000023401)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-05-27')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '29ab73e6-2256-429d-bf36-e52315303165')
            column(name: 'df_usage_batch_uid', value: 'e92a3622-0f16-49f8-bdc0-dacd9ade1245')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '876543210')
            column(name: 'work_title', value: 'Medical Journal')
            column(name: 'system_title', value: 'Medical Journal')
            column(name: 'standard_number', value: '978-0-7695-2365-2')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '29ab73e6-2256-429d-bf36-e52315303165')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2021)
            column(name: 'market_period_to', value: 2022)
            column(name: 'reported_value', value: 100.00)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: 'e41926ac-519a-4d8c-9788-8287d4080406')
            column(name: 'name', value: 'Test UDM Usage Batch to verify GetRightsJob')
            column(name: 'period', value: 202112)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '748223ba-cabd-481f-8c05-4fcdea550cb7')
            column(name: 'df_udm_usage_batch_uid', value: 'e41926ac-519a-4d8c-9788-8287d4080406')
            column(name: 'original_detail_id', value: 'OGN555GHASG002')
            column(name: 'period', value: 202112)
            column(name: 'period_end_date', value: '2022-05-27')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'wr_wrk_inst', value: 658824345)
            column(name: 'reported_title', value: 'Medical Journal')
            column(name: 'system_title', value:  'Medical Journal')
            column(name: 'reported_standard_number', value: '1008902112377654XX')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Medical Journal')
            column(name: 'language', value: 'German')
            column(name: 'company_id', value: 454984566)
            column(name: 'company_name', value: 'Skadden, Arps, Slate, Meagher & Flom LLP')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'survey_respondent', value: 'a671342a-ff05-4d6a-94ce-5876e950a656')
            column(name: 'ip_address', value: '24.12.119.203')
            column(name: 'survey_country', value: 'United States')
            column(name: 'usage_date', value: '2020-09-10')
            column(name: 'survey_start_date', value: '2020-09-10')
            column(name: 'survey_end_date', value: '2021-06-30')
            column(name: 'annual_multiplier', value: 1)
            column(name: 'statistical_multiplier', value: 0.10000)
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'annualized_copies', value: 1)
            column(name: 'quantity', value: 1)
        }

        rollback {
            dbRollback
        }
    }
}
