databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2024-02-02-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testWriteNtsPreServiceFeeFundCsvReport, testWriteNtsPreServiceFeeFundCsvReportEmpty')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'fcdc5d17-6e36-45ad-a352-0d7f7955527c')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '9d7aa059-4697-4038-981e-b196614cb3e7')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Fund1')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 7150.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '74a5bbcb-dbbd-4e0d-91af-4fe15307a7ac')
            column(name: 'name', value: 'FAS batch')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'gross_amount', value: 150.00)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b2fbb9ba-e643-47be-b9e7-d6e94f7348b0')
            column(name: 'df_usage_batch_uid', value: '74a5bbcb-dbbd-4e0d-91af-4fe15307a7ac')
            column(name: 'wr_wrk_inst', value: 987654321)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b2fbb9ba-e643-47be-b9e7-d6e94f7348b0')
            column(name: 'df_fund_pool_uid', value: '9d7aa059-4697-4038-981e-b196614cb3e7')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2022-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2020)
            column(name: 'market_period_to', value: 2021)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a822f0fb-bb32-4fa7-ad95-d4d97566464e')
            column(name: 'df_usage_batch_uid', value: '74a5bbcb-dbbd-4e0d-91af-4fe15307a7ac')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'standard_number', value: '2192-3558')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a822f0fb-bb32-4fa7-ad95-d4d97566464e')
            column(name: 'df_fund_pool_uid', value: '9d7aa059-4697-4038-981e-b196614cb3e7')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2023-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2020)
            column(name: 'market_period_to', value: 2021)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '03741637-1fed-47db-b308-4ef1f6a3e8dd')
            column(name: 'df_usage_batch_uid', value: '74a5bbcb-dbbd-4e0d-91af-4fe15307a7ac')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '03741637-1fed-47db-b308-4ef1f6a3e8dd')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2020)
            column(name: 'market_period_to', value: 2021)
            column(name: 'reported_value', value: 20.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '77226320-a50a-4b30-b33e-6a3e68af49d0')
            column(name: 'name', value: 'Batch with NTS')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ebc80150-13c3-49ca-b2a5-8715ff1f7a3e')
            column(name: 'df_usage_batch_uid', value: '77226320-a50a-4b30-b33e-6a3e68af49d0')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'comment', value: 'DIN EN 779:2013')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ebc80150-13c3-49ca-b2a5-8715ff1f7a3e')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'Network for Science')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 500.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd9fb4aad-def5-4dd8-88e5-e8e0c596058d')
            column(name: 'name', value: 'FAS batch archived usages')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'gross_amount', value: 7000.00)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '03e74d03-07d1-4068-ad5a-e825926546ac')
            column(name: 'name', value: 'FAS scenario with archived usages')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '08ce12cd-132b-4bbe-bce3-d5836d0f0c9d')
            column(name: 'df_scenario_uid', value: '03e74d03-07d1-4068-ad5a-e825926546ac')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'eca21f26-a116-4c5f-974f-be7a337337ce')
            column(name: 'df_usage_batch_uid', value: 'd9fb4aad-def5-4dd8-88e5-e8e0c596058d')
            column(name: 'df_scenario_uid', value: '03e74d03-07d1-4068-ad5a-e825926546ac')
            column(name: 'wr_wrk_inst', value: 345870577)
            column(name: 'work_title', value: '10 Years Plant Molecular Biology')
            column(name: 'system_title', value: '10 Years Plant Molecular Biology')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'payee_account_number', value: 1000005413)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'number_of_copies', value: 2630)
            column(name: 'gross_amount', value: 2125.00)
            column(name: 'net_amount', value: 1445.00)
            column(name: 'service_fee_amount', value: 680.0768)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'standard_number_type', value: 'VALISBN10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'eca21f26-a116-4c5f-974f-be7a337337ce')
            column(name: 'df_fund_pool_uid', value: '9d7aa059-4697-4038-981e-b196614cb3e7')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'McGraw-Hill')
            column(name: 'publication_date', value: '2009-12-31')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2019)
            column(name: 'author', value: 'Mirjam H. Hüberli')
            column(name: 'reported_value', value: 1280.00)
            column(name: 'reported_standard_number', value: '12345XX-123124')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '075d6c50-6721-46e7-a647-deaeafa74515')
            column(name: 'df_usage_batch_uid', value: 'd9fb4aad-def5-4dd8-88e5-e8e0c596058d')
            column(name: 'df_scenario_uid', value: '03e74d03-07d1-4068-ad5a-e825926546ac')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 4825.00)
            column(name: 'net_amount', value: 4615.00)
            column(name: 'service_fee_amount', value: 21720.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'standard_number_type', value: 'VALISBN10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '075d6c50-6721-46e7-a647-deaeafa74515')
            column(name: 'df_fund_pool_uid', value: '9d7aa059-4697-4038-981e-b196614cb3e7')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 9900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '12a0bafc-607b-467c-9bc1-fc34ce83b58c')
            column(name: 'df_usage_batch_uid', value: 'd9fb4aad-def5-4dd8-88e5-e8e0c596058d')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '0102112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '12a0bafc-607b-467c-9bc1-fc34ce83b58c')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10T15:00:00Z')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 1960)
            column(name: 'market_period_to', value: 1960)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        rollback {
            dbRollback
        }
    }
}
