databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2017-17-02-00', author: 'Mikalai_Bezmen <mbezmen@copyright.com>') {
        comment('Insert predefined data for ViewScenarioUiTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd2b9c16d-230a-414f-9ffb-acdb676fac0c')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '35000')
            column(name: 'updated_datetime', value: '2017-02-21 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3cc619da-6218-47ef-959e-5a3f19e392a4')
            column(name: 'name', value: 'AccessCopyright_11Dec16')
            column(name: 'rro_account_number', value: '2000017004')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: '2016')
            column(name: 'gross_amount', value: '35000')
            column(name: 'updated_datetime', value: '2017-02-14 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'name', value: 'JAACC_11Dec16')
            column(name: 'rro_account_number', value: '7001440663')
            column(name: 'payment_date', value: '2018-08-16')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'gross_amount', value: '35000')
            column(name: 'updated_datetime', value: '2017-02-07 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '366f0fa6-b4c5-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_batch_uid', value: 'd2b9c16d-230a-414f-9ffb-acdb676fac0c')
            column(name: 'detail_id', value: '6997788888')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '2500')
            column(name: 'gross_amount', value: '35000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f9da71d0-9b07-4204-8355-549a4206ebc5')
            column(name: 'df_usage_batch_uid', value: '3cc619da-6218-47ef-959e-5a3f19e392a4')
            column(name: 'detail_id', value: '6997788885')
            column(name: 'wr_wrk_inst', value: '244614835')
            column(name: 'work_title', value: '15th International Conference on Environmental Degradation of Materials in Nuclear Power Systems Water Reactors')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'article', value: 'First-Week Protein and Energy Intakes Are Associated With 18-Month Developmental Outcomes in Extremely Low Birth Weight Infants')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'publisher', value: 'John Wiley & Sons')
            column(name: 'publication_date', value: '2011-05-10')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'number_of_copies', value: '1600')
            column(name: 'reported_value', value: '1560')
            column(name: 'gross_amount', value: '4764.40')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e6fba668-a840-4c4a-9332-2c1703bd63e0')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'detail_id', value: '6997788884')
            column(name: 'wr_wrk_inst', value: '345870577')
            column(name: 'work_title', value: '10 Years Plant Molecular Biology')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'publisher', value: 'McGraw-Hill')
            column(name: 'publication_date', value: '2009-12-31')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: 'Mirjam H. Hüberli')
            column(name: 'number_of_copies', value: '2630')
            column(name: 'reported_value', value: '1280.00')
            column(name: 'gross_amount', value: '3274.85')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '76ba5480-b3f8-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: '366f0fa6-b4c5-11e7-abc4-cec278b6b50a')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'CADRA_11Dec16' Batch")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7ab3eaa4-b3fa-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: 'f9da71d0-9b07-4204-8355-549a4206ebc5')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'AccessCopyright_11Dec16' Batch")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '6e30a5ce-b3fa-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: 'e6fba668-a840-4c4a-9332-2c1703bd63e0')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'JAACC_11Dec16' Batch")
        }

        // borders between existed changesets, because now rollback can be executed only on a single changeset

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'e9c9f51b-6048-4474-848a-2db1c410e463')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'name', value: 'British Film Institute (BFI)')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5bcf2c37-2f32-48e9-90fe-c9d75298eeed')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8a0dbf78-d9c9-49d9-a895-05f55cfc8329')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '6eb8ddf3-f08e-4c17-a0c5-5173d43a1625')
            column(name: 'rh_account_number', value: '1000008666')
            column(name: 'name', value: 'CCH')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '9905f006-a3e1-4061-b3d4-e7ece191103f')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '46754660-b627-46b9-a782-3f703b6853c7')
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'name', value: 'Access Copyright, The Canadian Copyright Agency')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05c4714b-291d-4e38-ba4a-35307434acfb')
            column(name: 'rh_account_number', value: '7000813806')
            column(name: 'name', value: 'CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: '7001440663')
            column(name: 'name', value: 'JAACC, Japan Academic Association for Copyright Clearance [T]')
        }

        // borders between existed changesets, because now rollback can be executed only on a single changeset

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b87071c4')
            column(name: 'name', value: 'Scenario 03/16/2017')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
            column(name: 'created_datetime', value: '2017-03-16')
            column(name: 'updated_datetime', value: '2017-03-17')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '92cfce61-9532-448e-a2bb-2ba23636debb')
            column(name: 'name', value: 'Scenario 02/15/2017')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: '')
            column(name: 'created_datetime', value: '2017-02-15')
            column(name: 'updated_datetime', value: '2017-02-16')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b2f3bf86-8343-4f78-bdb5-20a47c6a52b8')
            column(name: 'name', value: 'Scenario 03/15/2017')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: '')
            column(name: 'created_datetime', value: '2017-03-15')
            column(name: 'updated_datetime', value: '2017-03-15')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '47943793-4f9a-47b1-b2a8-e95a87aa58e6')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b87071c4')
            column(name: 'detail_id', value: '6907823886')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '2000017003')
            column(name: 'payee_account_number', value: '2000017003')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'is_rh_participating_flag', value: 'true')
            column(name: 'reported_value', value: '100.00')
            column(name: 'gross_amount', value: '255.85')
            column(name: 'net_amount', value: '214.91')
            column(name: 'service_fee_amount', value: '40.94')
            column(name: 'service_fee', value: '0.1600')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3846ead0-87ae-4d9b-8dfe-1b985d78c061')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '92cfce61-9532-448e-a2bb-2ba23636debb')
            column(name: 'detail_id', value: '6907723886')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '2000017003')
            column(name: 'payee_account_number', value: '2000017003')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '200.00')
            column(name: 'gross_amount', value: '511.69')
            column(name: 'net_amount', value: '347.95')
            column(name: 'service_fee_amount', value: '163.74')
            column(name: 'service_fee', value: '0.32')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b7ee7e4c-ec30-400b-ba49-70aaf8a4940e')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: 'b2f3bf86-8343-4f78-bdb5-20a47c6a52b8')
            column(name: 'detail_id', value: '3907723886')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '2000017003')
            column(name: 'payee_account_number', value: '2000017003')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '250.00')
            column(name: 'gross_amount', value: '639.62')
            column(name: 'net_amount', value: '434.94')
            column(name: 'service_fee_amount', value: '204.68')
            column(name: 'service_fee', value: '0.32')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '222c1e92-b409-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: '47943793-4f9a-47b1-b2a8-e95a87aa58e6')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'JAACC_11Dec16' Batch")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '2b4e257e-b409-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: '3846ead0-87ae-4d9b-8dfe-1b985d78c061')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'JAACC_11Dec16' Batch")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '3c80739c-b409-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: 'b7ee7e4c-ec30-400b-ba49-70aaf8a4940e')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'JAACC_11Dec16' Batch")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '7f95ddb3-d3d2-4a40-b370-f5be358b8146')
            column(name: 'df_scenario_uid', value: '92cfce61-9532-448e-a2bb-2ba23636debb')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'action_reason', value: '')
            column(name: 'created_datetime', value: '2017-02-15')
            column(name: 'created_by_user', value: 'fda_spec@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '009f22cb-4d05-410a-b8a5-cb951f17285a')
            column(name: 'df_scenario_uid', value: 'b2f3bf86-8343-4f78-bdb5-20a47c6a52b8')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'action_reason', value: '')
            column(name: 'created_datetime', value: '2017-03-15')
            column(name: 'created_by_user', value: 'fda_spec@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: 'eecea40e-82dd-4a14-bffd-a03686d514de')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b87071c4')
            column(name: 'action_type_ind', value: 'ADDED_USAGES')
            column(name: 'action_reason', value: '')
            column(name: 'created_datetime', value: '2017-03-16')
            column(name: 'created_by_user', value: 'fda_spec@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '2a699680-53a2-4d38-af72-ad7dea068205')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b87071c4')
            column(name: 'action_type_ind', value: 'SUBMITTED')
            column(name: 'action_reason', value: 'submitted')
            column(name: 'created_datetime', value: '2017-03-17')
            column(name: 'created_by_user', value: 'fda_spec@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_audit') {
            column(name: 'df_scenario_audit_uid', value: '193c7216-a814-4f7e-bfbe-ae91659ac34f')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b87071c4')
            column(name: 'action_type_ind', value: 'REJECTED')
            column(name: 'action_reason', value: 'rejected')
            column(name: 'created_datetime', value: '2017-03-18')
            column(name: 'created_by_user', value: 'fda_manager@copyright.com')
        }

        // borders between existed changesets, because now rollback can be executed only on a single changeset

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'name', value: 'Scenario for viewing')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
            column(name: 'created_datetime', value: '2017-03-17')
            column(name: 'updated_datetime', value: '2017-03-17')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '47943793-4f9a-47b1-b2a8-e95a87aa58g6')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'detail_id', value: '6907853886')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '1000008666')
            column(name: 'payee_account_number', value: '1000008666')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '3000.00')
            column(name: 'gross_amount', value: '7675.44')
            column(name: 'net_amount', value: '5219.30')
            column(name: 'service_fee_amount', value: '2456.14')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3846ead0-87ae-4d9b-8dfe-1b985d78c062')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'detail_id', value: '6607723886')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '1000008666')
            column(name: 'payee_account_number', value: '1000008666')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '5000.00')
            column(name: 'gross_amount', value: '12792.40')
            column(name: 'net_amount', value: '8698.83')
            column(name: 'service_fee_amount', value: '4093.57')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b7ee7e4c-ec30-400b-ba49-70aaf8a4941e')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'detail_id', value: '3907723836')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'payee_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '2500.00')
            column(name: 'gross_amount', value: '6396.19')
            column(name: 'net_amount', value: '4349.41')
            column(name: 'service_fee_amount', value: '2046.78')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b7ee7e4c-ec30-400b-ba49-70aaf8a4942e')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'detail_id', value: '3907723856')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'payee_account_number', value: '1000002797')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '500.00')
            column(name: 'gross_amount', value: '1279.24')
            column(name: 'net_amount', value: '869.88')
            column(name: 'service_fee_amount', value: '409.36')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '6aea1b34-b409-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: '47943793-4f9a-47b1-b2a8-e95a87aa58g6')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'JAACC_11Dec16' Batch")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7aa31ab2-b409-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: '3846ead0-87ae-4d9b-8dfe-1b985d78c062')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'JAACC_11Dec16' Batch")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '8b490944-b409-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: 'b7ee7e4c-ec30-400b-ba49-70aaf8a4941e')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'JAACC_11Dec16' Batch")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '97afdf1e-b409-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: 'b7ee7e4c-ec30-400b-ba49-70aaf8a4942e')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'JAACC_11Dec16' Batch")
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_rightsholder'){}
            delete(schemaName: dbAppsSchema, tableName: 'df_scenario_audit'){}
            delete(schemaName: dbAppsSchema, tableName: 'df_usage_audit'){}
            delete(schemaName: dbAppsSchema, tableName: 'df_usage'){}
            delete(schemaName: dbAppsSchema, tableName: 'df_usage_archive'){}
            delete(schemaName: dbAppsSchema, tableName: 'df_usage_batch'){}
            delete(schemaName: dbAppsSchema, tableName: 'df_scenario'){}
        }
    }
}
