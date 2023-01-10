databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2018-06-26-00', author: 'Pavel Liakh<pliakh@copyright.com>') {
        comment('Inserting test data for testUpdateRightsSentForRaUsages')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '75e057ac-7c24-4ae7-a0f5-aa75ea0895e6')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'name', value: 'Zoological Society of Pakistan [T]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'd311340c-60e8-4df1-bbe1-788ba2ed9a15')
            column(name: 'rh_account_number', value: 1000023401)
            column(name: 'name', value: 'American City Business Journals, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'abf348f3-f6d7-4d24-b087-4812b78e140d')
            column(name: 'rh_account_number', value: 1000010077)
            column(name: 'name', value: 'Cambridge University Press - US - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a088a427-0739-4fc7-b621-9a77eb83244a')
            column(name: 'rh_account_number', value: 1000010088)
            column(name: 'name', value: 'Zoological Society of Poland')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '38f41b1f-989d-42c2-9ea2-94b3565bea0f')
            column(name: 'name', value: 'Test_RMS_get_rights')
            column(name: 'rro_account_number', value: 1000023401)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-26')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 5000.00)
            column(name: 'initial_usages_count', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ff321d96-04bd-11e8-ba89-0ed5f89f718b')
            column(name: 'df_usage_batch_uid', value: '38f41b1f-989d-42c2-9ea2-94b3565bea0f')
            column(name: 'wr_wrk_inst', value: 127778306)
            column(name: 'work_title', value: 'ACP journal club')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 124.9783785347)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ff321d96-04bd-11e8-ba89-0ed5f89f718b')
            column(name: 'article', value: 'Appendix: The Principles of Newspeak')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ,Bus,Doc,S')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'author', value: 'Aarseth, Espen J.')
            column(name: 'reported_value', value: 30.86)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '37c4d727-caeb-4a7f-b11a-34e313b0bfcc')
            column(name: 'df_usage_batch_uid', value: '38f41b1f-989d-42c2-9ea2-94b3565bea0f')
            column(name: 'wr_wrk_inst', value: 127778305)
            column(name: 'work_title', value: 'ACP journal club')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 390.1883929693)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '37c4d727-caeb-4a7f-b11a-34e313b0bfcc')
            column(name: 'article', value: 'Appendix: The Principles of Newspeak')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ,Bus,Doc,S')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'author', value: 'Aarseth, Espen J.')
            column(name: 'reported_value', value: 30.86)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2de40e13-d353-44ce-b6bb-a11383ba9fb9')
            column(name: 'df_usage_batch_uid', value: '38f41b1f-989d-42c2-9ea2-94b3565bea0f')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 390.1883929693)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2de40e13-d353-44ce-b6bb-a11383ba9fb9')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'reported_value', value: 30.86)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b77e72d6-ef71-4f4b-a00b-5800e43e5bee')
            column(name: 'df_usage_batch_uid', value: '38f41b1f-989d-42c2-9ea2-94b3565bea0f')
            column(name: 'wr_wrk_inst', value: 254030731)
            column(name: 'work_title', value: 'Akropolites: Nicaean Empire')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 282.4630168157)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b77e72d6-ef71-4f4b-a00b-5800e43e5bee')
            column(name: 'article', value: 'between orientalism and fundamentalism')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'author', value: 'Aarseth, Espen J.')
            column(name: 'reported_value', value: 22.34)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8aded52d-9507-4883-ab4c-fd2e029298af')
            column(name: 'df_usage_batch_uid', value: '38f41b1f-989d-42c2-9ea2-94b3565bea0f')
            column(name: 'wr_wrk_inst', value: 254030731)
            column(name: 'work_title', value: 'Akropolites: Nicaean Empire')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 118.9783790616)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8aded52d-9507-4883-ab4c-fd2e029298af')
            column(name: 'article', value: 'between orientalism and fundamentalism')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'author', value: 'Aarseth, Espen J.')
            column(name: 'reported_value', value: 9.41)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '74ded52a-4454-1225-ab4c-fA2e029298af')
            column(name: 'df_usage_batch_uid', value: '38f41b1f-989d-42c2-9ea2-94b3565bea0f')
            column(name: 'wr_wrk_inst', value: 658824345)
            column(name: 'work_title', value: 'ACP journal club')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 118.9783790616)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '74ded52a-4454-1225-ab4c-fA2e029298af')
            column(name: 'article', value: 'between orientalism and fundamentalism')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'author', value: 'Aarseth, Espen J.')
            column(name: 'reported_value', value: 9.41)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3a6b6f25-9f68-4da7-be4f-dd65574f5168')
            column(name: 'df_usage_batch_uid', value: '38f41b1f-989d-42c2-9ea2-94b3565bea0f')
            column(name: 'wr_wrk_inst', value: 488824345)
            column(name: 'work_title', value: 'ACP journal club')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 118.9783790616)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3a6b6f25-9f68-4da7-be4f-dd65574f5168')
            column(name: 'article', value: 'between orientalism and fundamentalism')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'author', value: 'Aarseth, Espen J.')
            column(name: 'reported_value', value: 9.41)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '11853c83-780a-4533-ad01-dde87c8b8592')
            column(name: 'df_usage_batch_uid', value: '38f41b1f-989d-42c2-9ea2-94b3565bea0f')
            column(name: 'wr_wrk_inst', value: 122824345)
            column(name: 'work_title', value: 'ACP journal club')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 1040.5866734081)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '11853c83-780a-4533-ad01-dde87c8b8592')
            column(name: 'article', value: 'between orientalism and fundamentalism')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'author', value: 'Aarseth, Espen J.')
            column(name: 'reported_value', value: 82.30)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e6378e17-b0c9-420f-aa5c-a653156339d2')
            column(name: 'df_usage_batch_uid', value: '38f41b1f-989d-42c2-9ea2-94b3565bea0f')
            column(name: 'wr_wrk_inst', value: 854030732)
            column(name: 'work_title', value: '(En)gendering the war on terror : war stories and camouflaged politics')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 1382.4756606371)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e6378e17-b0c9-420f-aa5c-a653156339d2')
            column(name: 'article', value: 'war stories and camouflaged politics')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'author', value: 'Aarseth, Espen J.')
            column(name: 'reported_value', value: 109.34)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '19ca7776-48c8-472e-acfe-d49b6e8780ce')
            column(name: 'df_usage_batch_uid', value: '38f41b1f-989d-42c2-9ea2-94b3565bea0f')
            column(name: 'wr_wrk_inst', value: 346768461)
            column(name: 'work_title', value: 'ACI structural journal')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'service_fee_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 1395.1194841293)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '19ca7776-48c8-472e-acfe-d49b6e8780ce')
            column(name: 'article', value: 'war stories and camouflaged politics')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2015)
            column(name: 'author', value: 'Aarseth, Espen J.')
            column(name: 'reported_value', value: 110.34)
            column(name: 'is_rh_participating_flag', value: 'FALSE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'ade32bd9-4c0f-4835-b980-6372b89c9caf')
            column(name: 'name', value: 'NTS getRights')
            column(name: 'rro_account_number', value: 1000023401)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-26')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 84.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ede81bc0-a756-43a2-b236-05a0184384f4')
            column(name: 'df_usage_batch_uid', value: 'ade32bd9-4c0f-4835-b980-6372b89c9caf')
            column(name: 'wr_wrk_inst', value: 786768461)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '112317622XX')
            column(name: 'gross_amount', value: 84.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ede81bc0-a756-43a2-b236-05a0184384f4')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'market', value: 'Univ,Bus,Doc,S')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 16.00)
        }

        rollback {
            dbRollback
        }
    }
}
