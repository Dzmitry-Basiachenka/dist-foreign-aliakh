databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-06-26-00', author: 'Pavel Liakh<pliakh@copyright.com>') {
        comment('Inserting test data for UpdateRightsholders test')

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

    changeSet(id: '2020-01-15-00', author: 'Ihar Suvorau<isuvorau@copyright.com>') {
        comment('Inserting test data for UpdateAaclRight test')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'eb4b7bdb-3164-4e60-8d63-cae40c76de6e')
            column(name: 'name', value: 'AACL Usage Batch 2015')
            column(name: 'product_family', value: 'AACL')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b23cb103-9242-4d58-a65d-2634b3e5a8cf')
            column(name: 'df_usage_batch_uid', value: 'eb4b7bdb-3164-4e60-8d63-cae40c76de6e')
            column(name: 'wr_wrk_inst', value: 122803735)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'b23cb103-9242-4d58-a65d-2634b3e5a8cf')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: 12)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7e7b97d1-ad60-4d47-915b-2834c5cc056a')
            column(name: 'df_usage_batch_uid', value: 'eb4b7bdb-3164-4e60-8d63-cae40c76de6e')
            column(name: 'wr_wrk_inst', value: 130297955)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 1)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '7e7b97d1-ad60-4d47-915b-2834c5cc056a')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: 199)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '10c9a60f-28b6-466c-975c-3ea930089a9e')
            column(name: 'df_usage_batch_uid', value: 'eb4b7bdb-3164-4e60-8d63-cae40c76de6e')
            column(name: 'wr_wrk_inst', value: 200208329)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 18)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '10c9a60f-28b6-466c-975c-3ea930089a9e')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: 180)
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2020-08-20-00', author: 'Stanislau Rudak<srudak@copyright.com>') {
        comment('Inserting test data for testUpdateSalRights test')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '73ebd1d1-20f7-41ce-84e7-e064034e7564')
            column(name: 'name', value: 'SAL Usage Batch 2020')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 3)
            column(name: 'sal_fields', value: '{"licensee_name": "RGS Energy Group, Inc.", "licensee_account_number": 5588}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'dcb53a42-7e8d-4a4a-8d72-6f794be2731c')
            column(name: 'df_usage_batch_uid', value: '73ebd1d1-20f7-41ce-84e7-e064034e7564')
            column(name: 'wr_wrk_inst', value: 122769471)
            column(name: 'standard_number', value: '978-0-7474-0150-6')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'system_title', value: 'life and death of Mozart')
            column(name: 'work_title', value: 'life and death of Mozart')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'dcb53a42-7e8d-4a4a-8d72-6f794be2731c')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: 'K')
            column(name: 'assessment_name', value: 'Spring2014 Eng Lang/Mathy')
            column(name: 'reported_work_portion_id', value: 'AT 805a1300-2232-4761-aad8-3b1e03a219d1')
            column(name: 'reported_standard_number', value: '978-0-87664-361-7')
            column(name: 'reported_author', value: 'Linda J. S. Allen')
            column(name: 'reported_publisher', value: 'Rosen')
            column(name: 'reported_publication_date', value: '2010-07-07 00:00:00.0')
            column(name: 'reported_page_range', value: '25-30')
            column(name: 'reported_vol_number_series', value: '55(2)')
            column(name: 'reported_media_type', value: 'Image')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '094749c5-08fa-4f57-8c3b-ecbc334a5c2a')
            column(name: 'df_usage_batch_uid', value: '73ebd1d1-20f7-41ce-84e7-e064034e7564')
            column(name: 'wr_wrk_inst', value: 243618757)
            column(name: 'standard_number', value: '978-0-947731-91-5')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'system_title', value: 'Midbus')
            column(name: 'work_title', value: 'Midbus')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '094749c5-08fa-4f57-8c3b-ecbc334a5c2a')
            column(name: 'detail_type', value: 'IB')
            column(name: 'assessment_name', value: 'Spring2015 Eng Lang/Mathy')
            column(name: 'reported_work_portion_id', value: 'AT 0af85c0d-c7f5-41c7-9fb9-059297c6b1b5')
            column(name: 'reported_standard_number', value: '978-2-84096-163-5')
            column(name: 'reported_author', value: 'Linda J. S. Allen')
            column(name: 'reported_publisher', value: 'Rosen')
            column(name: 'reported_publication_date', value: '2010-07-08 00:00:00.0')
            column(name: 'reported_page_range', value: '25-30')
            column(name: 'reported_vol_number_series', value: '55(2)')
            column(name: 'reported_media_type', value: 'Image')
            column(name: 'media_type_weight', value: 0.3)
            column(name: 'coverage_year', value: '2015-2016')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ecf46bea-2baa-40c1-a5e1-769c78865b2c')
            column(name: 'df_usage_batch_uid', value: '73ebd1d1-20f7-41ce-84e7-e064034e7564')
            column(name: 'wr_wrk_inst', value: 140160102)
            column(name: 'standard_number', value: '978-84-08-04782-7')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'system_title', value: 'Rimas')
            column(name: 'work_title', value: 'Rimas')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'ecf46bea-2baa-40c1-a5e1-769c78865b2c')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '2')
            column(name: 'assessment_name', value: 'Spring2014 Eng Lang/Mathy')
            column(name: 'reported_work_portion_id', value: 'AT 2fe76634-5851-42d4-852d-6b1dd2e87100')
            column(name: 'reported_standard_number', value: '1040-1776')
            column(name: 'reported_author', value: 'Linda J. S. Allen')
            column(name: 'reported_publisher', value: 'Rosen')
            column(name: 'reported_publication_date', value: '2010-07-09 00:00:00.0')
            column(name: 'reported_page_range', value: '25-30')
            column(name: 'reported_vol_number_series', value: '55(2)')
            column(name: 'reported_media_type', value: 'Text')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2021-05-24-00', author: 'Uladzislau Shalamitski<ushalamitski@copyright.com>') {
        comment('Inserting test data for testUpdateUdmRights test')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage_batch') {
            column(name: 'df_udm_usage_batch_uid', value: '1b6055be-fc4e-4b49-aeab-28563366c9fd')
            column(name: 'name', value: 'UDM Batch 2020')
            column(name: 'period', value: 202006)
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: 'acb53a42-7e8d-4a4a-8d72-6f794be2731c')
            column(name: 'df_udm_usage_batch_uid', value: '1b6055be-fc4e-4b49-aeab-28563366c9fd')
            column(name: 'original_detail_id', value: 'OGN674GHHSB0101')
            column(name: 'period', value: '202006')
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'wr_wrk_inst', value: 122769421)
            column(name: 'reported_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'system_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'reported_standard_number', value: '1008902112377654XX')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'language', value: 'English')
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Alcon Laboratories, Inc.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: 'c6615155-f82b-402c-8f22-77e2722ae448')
            column(name: 'ip_address', value: 'ip192.168.211.211')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-05-10')
            column(name: 'survey_start_date', value: '2020-04-20')
            column(name: 'survey_end_date', value: '2020-05-15')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'annualized_copies', value: 4)
            column(name: 'quantity', value: 3)
            column(name: 'annual_multiplier', value: 25)
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'annualized_copies', value: 300)
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-10 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '074749c5-08fa-4f57-8c3b-ecbc334a5c2a')
            column(name: 'df_udm_usage_batch_uid', value: '1b6055be-fc4e-4b49-aeab-28563366c9fd')
            column(name: 'original_detail_id', value: 'OGN674GHHSB0102')
            column(name: 'period', value: '202006')
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'wr_wrk_inst', value: 210001899)
            column(name: 'reported_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'system_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'reported_standard_number', value: '1008902112377654XX')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'language', value: 'English')
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Alcon Laboratories, Inc.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: 'c6615155-f82b-402c-8f22-77e2722ae448')
            column(name: 'ip_address', value: 'ip192.168.211.211')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-05-10')
            column(name: 'survey_start_date', value: '2020-04-20')
            column(name: 'survey_end_date', value: '2020-05-15')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'annualized_copies', value: 4)
            column(name: 'quantity', value: 3)
            column(name: 'annual_multiplier', value: 25)
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'annualized_copies', value: 300)
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-10 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_usage_uid', value: '1b348196-2193-46d7-b9df-2ba835189131')
            column(name: 'df_udm_usage_batch_uid', value: '1b6055be-fc4e-4b49-aeab-28563366c9fd')
            column(name: 'original_detail_id', value: 'OGN674GHHSB0103')
            column(name: 'period', value: '202006')
            column(name: 'period_end_date', value: '2020-06-30')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'assignee', value: 'wjohn@copyright.com')
            column(name: 'wr_wrk_inst', value: 210001133)
            column(name: 'reported_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'system_title', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'reported_standard_number', value: '1008902112377654XX')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'reported_pub_type', value: 'Not Shared')
            column(name: 'publication_format', value: 'Digital')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'language', value: 'English')
            column(name: 'company_id', value: 1136)
            column(name: 'company_name', value: 'Alcon Laboratories, Inc.')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_respondent', value: 'c6615155-f82b-402c-8f22-77e2722ae448')
            column(name: 'ip_address', value: 'ip192.168.211.211')
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'usage_date', value: '2020-05-10')
            column(name: 'survey_start_date', value: '2020-04-20')
            column(name: 'survey_end_date', value: '2020-05-15')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'annualized_copies', value: 4)
            column(name: 'quantity', value: 3)
            column(name: 'annual_multiplier', value: 25)
            column(name: 'statistical_multiplier', value: 1)
            column(name: 'annualized_copies', value: 300)
        }

        rollback {
            dbRollback
        }
    }
}
