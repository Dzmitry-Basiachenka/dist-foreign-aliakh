databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-02-17-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserting test data for writeNtsFundPoolsCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8f9d3a0e-dc2e-412d-8d81-2b1a8e64faf4')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '6c8d5520-5584-4ca5-918e-3dc1ed0c90a5')
            column(name: 'rh_account_number', value: 1000000004)
            column(name: 'name', value: 'Computers for Design and Construction')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b5a9658e-9b97-430b-8fde-92d7dc54f704')
            column(name: 'name', value: 'NTS Distribution 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '85ba864e-b1f9-4857-8f9d-17a1de9f5811')
            column(name: 'df_scenario_uid', value: 'b5a9658e-9b97-430b-8fde-92d7dc54f704')
            column(name: 'product_family', value: 'NTS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '7188eb35-2bfa-4e5e-93f9-64a39f321e01')
            column(name: 'name', value: 'NTS fund pool 1')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2022-09-10')
            column(name: 'fiscal_year', value: 2023)
            column(name: 'product_family', value: 'NTS')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":7,"stm_amount":700,"stm_minimum_amount":50,"non_stm_amount":5000,"fund_pool_period_from":2023,"markets":["Bus","Doc Del"],"fund_pool_period_to":2023}')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-09-10 13:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '977c10bb-ca6d-4f11-a0fb-bd79ffcc5a0c')
            column(name: 'df_usage_batch_uid', value: '7188eb35-2bfa-4e5e-93f9-64a39f321e01')
            column(name: 'df_scenario_uid', value: 'b5a9658e-9b97-430b-8fde-92d7dc54f704')
            column(name: 'wr_wrk_inst', value: 9876543212)
            column(name: 'work_title', value: 'future of children')
            column(name: 'system_title', value: 'future of children')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'service_fee', value: 0.0)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '85ba864e-b1f9-4857-8f9d-17a1de9f5811')
            column(name: 'df_usage_batch_uid', value: '7188eb35-2bfa-4e5e-93f9-64a39f321e01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'c0e56e3f-92cf-42b1-a2f7-c2ad66cb2e2e')
            column(name: 'name', value: 'NTS Distribution 2')
            column(name: 'status_ind', value: 'APPROVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'dca17b92-18d3-47fb-af93-ce21f692980f')
            column(name: 'df_scenario_uid', value: 'c0e56e3f-92cf-42b1-a2f7-c2ad66cb2e2e')
            column(name: 'product_family', value: 'NTS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'fa3aa657-555d-4d8b-8651-f76b7ad1b94f')
            column(name: 'name', value: 'NTS fund pool 2')
            column(name: 'rro_account_number', value: 1000000004)
            column(name: 'payment_date', value: '2021-09-10')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'product_family', value: 'NTS')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":7,"stm_amount":350,"stm_minimum_amount":50,"non_stm_amount":10000,"fund_pool_period_from":2022,"markets":["Bus", "Lib", "Edu", "Doc Del", "Gov"],"fund_pool_period_to":2022}')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-09-10 13:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a3d574f2-7e4b-47a2-a993-5643441476e7')
            column(name: 'df_usage_batch_uid', value: 'fa3aa657-555d-4d8b-8651-f76b7ad1b94f')
            column(name: 'df_scenario_uid', value: 'c0e56e3f-92cf-42b1-a2f7-c2ad66cb2e2e')
            column(name: 'wr_wrk_inst', value: 9876543212)
            column(name: 'work_title', value: 'future of children')
            column(name: 'system_title', value: 'future of children')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'service_fee', value: 0.0)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'dca17b92-18d3-47fb-af93-ce21f692980f')
            column(name: 'df_usage_batch_uid', value: 'fa3aa657-555d-4d8b-8651-f76b7ad1b94f')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5d7ed3fe-64b9-45ce-85ee-9b85eb458eb3')
            column(name: 'name', value: 'NTS Distribution 3')
            column(name: 'status_ind', value: 'SENT_TO_LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '1cccf479-b968-4478-86d6-d6244d4c5533')
            column(name: 'df_scenario_uid', value: '5d7ed3fe-64b9-45ce-85ee-9b85eb458eb3')
            column(name: 'product_family', value: 'NTS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '5cbd0aa8-372a-4750-9152-0c1800c404c2')
            column(name: 'name', value: 'NTS fund pool 3')
            column(name: 'rro_account_number', value: 1000000004)
            column(name: 'payment_date', value: '2021-09-10')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'product_family', value: 'NTS')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":7,"stm_amount":350,"stm_minimum_amount":50,"non_stm_amount":10000,"fund_pool_period_from":2022,"markets":["Bus", "Lib", "Edu", "Doc Del", "Gov"],"fund_pool_period_to":2022}')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 13:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '1ab96dfb-e9b9-4de3-872d-6b0074edf3fe')
            column(name: 'df_usage_batch_uid', value: '5cbd0aa8-372a-4750-9152-0c1800c404c2')
            column(name: 'df_scenario_uid', value: '5d7ed3fe-64b9-45ce-85ee-9b85eb458eb3')
            column(name: 'wr_wrk_inst', value: 9876543212)
            column(name: 'work_title', value: 'future of children')
            column(name: 'system_title', value: 'future of children')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'service_fee', value: 0.0)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '1cccf479-b968-4478-86d6-d6244d4c5533')
            column(name: 'df_usage_batch_uid', value: '5cbd0aa8-372a-4750-9152-0c1800c404c2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e40c0d3e-7030-41b7-a5a6-dbe4f5dd63fa')
            column(name: 'name', value: 'NTS fund pool 4')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2023-01-02')
            column(name: 'fiscal_year', value: 2023)
            column(name: 'product_family', value: 'NTS')
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":0,"stm_amount":100,"stm_minimum_amount":0,"non_stm_amount":1000,"fund_pool_period_from":2023,"markets":["Doc Del"],"fund_pool_period_to":2023}')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2023-01-02 13:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1c66309d-7314-4903-b636-8b7095c7b5ba')
            column(name: 'df_usage_batch_uid', value: 'e40c0d3e-7030-41b7-a5a6-dbe4f5dd63fa')
            column(name: 'wr_wrk_inst', value: 9876543212)
            column(name: 'work_title', value: 'future of children')
            column(name: 'system_title', value: 'future of children')
            column(name: 'rh_account_number', value: 2000017001)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'service_fee', value: 0.0)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'ed04e6b1-45e9-4d4d-8950-02af0fc62db9')
            column(name: 'name', value: 'AACL batch')
            column(name: 'payment_date', value: '2023-06-30')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2023)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":7,"stm_amount":350,"stm_minimum_amount":50,"non_stm_amount":10000,"fund_pool_period_from":2022,"markets":["Bus", "Lib", "Edu", "Doc Del", "Gov"],"fund_pool_period_to":2022}')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 13:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a970176e-211c-4cfc-9d75-cf6f0e74b0c8')
            column(name: 'df_usage_batch_uid', value: 'ed04e6b1-45e9-4d4d-8950-02af0fc62db9')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 1)
        }

        rollback {
            dbRollback
        }
    }
}
