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
            column(name: 'df_rightsholder_uid', value: '85f864f2-30a5-4215-ac4f-f1f541901218')
            column(name: 'rh_account_number', value: 1000000322)
            column(name: 'name', value: 'American College of Physicians - Journals')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'bf6bca99-f26d-4612-9eb5-d9ba554eacba')
            column(name: 'name', value: 'Test Usage Batch to verify WorksMatchingJob')
            column(name: 'rro_account_number', value: 1000023401)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-05-27')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '03f307ac-81d1-4ab5-b037-9bd2ca899aab')
            column(name: 'df_usage_batch_uid', value: 'bf6bca99-f26d-4612-9eb5-d9ba554eacba')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1906011')
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '03f307ac-81d1-4ab5-b037-9bd2ca899aab')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2021)
            column(name: 'market_period_to', value: 2022)
            column(name: 'reported_value', value: 100.00)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '2beb2f08-3ed5-4cfb-96e8-a67e77bf7bfe')
            column(name: 'name', value: 'Test UDM Usage Batch to verify WorksMatchingJob')
            column(name: 'period', value: 202112)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'e6343e5a-2075-45e0-96da-5f9f4ba15f5c')
            column(name: 'df_udm_usage_batch_uid', value: '2beb2f08-3ed5-4cfb-96e8-a67e77bf7bfe')
            column(name: 'original_detail_id', value: 'OGN555GHASG001')
            column(name: 'period', value: 202112)
            column(name: 'period_end_date', value: '2022-05-27')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'wr_wrk_inst', value: 854030732)
            column(name: 'reported_title', value: 'Technical Journal')
            column(name: 'reported_standard_number', value: '2998622115929154XX')
            column(name: 'reported_pub_type', value: 'Book')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Technical Journal')
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
