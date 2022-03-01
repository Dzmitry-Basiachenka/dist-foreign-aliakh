databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2017-11-30-03', author: 'Aliaksandra_Bayanouskaya <abayanouskaya@copyright.com>') {
        comment('Inserting test data for testDeleteForArchivedByBatchId')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-94d3458a666a')
            column(name: 'name', value: 'AccessCopyright_11Dec16')
            column(name: 'rro_account_number', value: 2000017004)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-08-16')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3ab5e80b-89c0-4d78-9675-54c7ab284450')
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 35000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3ab5e80b-89c0-4d78-9675-54c7ab284450')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '8a5afe55-5a5f-4893-aee1-96ac1361c033')
            column(name: 'df_usage_uid', value: '3ab5e80b-89c0-4d78-9675-54c7ab284450')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test Batch 1\' Batch')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        // testDeleteForArchivedByBatchId
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '422d33c0-4594-451e-a1ca-412c023299aa')
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000009522)
            column(name: 'payee_account_number', value: 1000009522)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 50.00)
            column(name: 'net_amount', value: 34.00)
            column(name: 'service_fee_amount', value: 16.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2011-03-15 11:41:52.735531+03')
            column(name: 'distribution_name', value: 'FDA March 11')
            column(name: 'distribution_date', value: '2011-03-15 11:41:52.735531+03')
            column(name: 'period_end_date', value: '2011-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '422d33c0-4594-451e-a1ca-412c023299aa')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 30.86)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'ef9d1e0e-4000-488a-a870-84b6c1c38ff6')
            column(name: 'df_usage_uid', value: '422d33c0-4594-451e-a1ca-412c023299aa')
            column(name: 'action_type_ind', value: 'PAID')
            column(name: 'action_reason', value: 'Usage has been created based on Split process')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        //testFindFasBatchStatistic
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '275c0dd4-ffff-41ea-b68b-d35539ad3b6e')
            column(name: 'name', value: 'FAS batch statistic')
            column(name: 'rro_account_number', value: 7001832491)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-12-04T15:00:00Z')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '4d6ac86e-f730-420b-a36f-c482fb54e80f')
            column(name: 'name', value: 'Scenario for FAS batch statistic')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5ed3ed4f-fdd4-4521-bbe5-27870dc8bd62')
            column(name: 'df_usage_batch_uid', value: '275c0dd4-ffff-41ea-b68b-d35539ad3b6e')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 80.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5ed3ed4f-fdd4-4521-bbe5-27870dc8bd62')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10T15:00:00Z')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 1960)
            column(name: 'market_period_to', value: 1960)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e0b83b52-734a-439c-8e6b-2345c2aaa8e2')
            column(name: 'df_usage_batch_uid', value: '275c0dd4-ffff-41ea-b68b-d35539ad3b6e')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 2000205131)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 110.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e0b83b52-734a-439c-8e6b-2345c2aaa8e2')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10T15:00:00Z')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 1960)
            column(name: 'market_period_to', value: 1960)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '366eca1a-4974-4fce-a585-b9635b5a71c9')
            column(name: 'df_usage_batch_uid', value: '275c0dd4-ffff-41ea-b68b-d35539ad3b6e')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '0102112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 55.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '366eca1a-4974-4fce-a585-b9635b5a71c9')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10T15:00:00Z')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 1960)
            column(name: 'market_period_to', value: 1960)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '08c85ce4-15bf-4ae4-a3b0-b82742ac4357')
            column(name: 'df_usage_batch_uid', value: '275c0dd4-ffff-41ea-b68b-d35539ad3b6e')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'ELIGIBLE_FOR_NTS')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 150.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '08c85ce4-15bf-4ae4-a3b0-b82742ac4357')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10T15:00:00Z')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 1960)
            column(name: 'market_period_to', value: 1960)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'fb41b86e-755d-437c-8834-8044e73d72e8')
            column(name: 'df_usage_batch_uid', value: '275c0dd4-ffff-41ea-b68b-d35539ad3b6e')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 2000205131)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'net_amount', value: 34.00)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 16.00)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'df_scenario_uid', value: '4d6ac86e-f730-420b-a36f-c482fb54e80f')
            column(name: 'payee_account_number', value: 2000205131)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'fb41b86e-755d-437c-8834-8044e73d72e8')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10T15:00:00Z')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 1960)
            column(name: 'market_period_to', value: 1960)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'acb55e9a-d956-4a49-8662-8f6a2b2f3048')
            column(name: 'df_usage_batch_uid', value: '275c0dd4-ffff-41ea-b68b-d35539ad3b6e')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 2000205131)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'df_scenario_uid', value: '4d6ac86e-f730-420b-a36f-c482fb54e80f')
            column(name: 'payee_account_number', value: 2000205131)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'acb55e9a-d956-4a49-8662-8f6a2b2f3048')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10T15:00:00Z')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 1960)
            column(name: 'market_period_to', value: 1960)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '0427f1de-2894-4a1d-b154-b1bf0e91192c')
            column(name: 'df_usage_batch_uid', value: '275c0dd4-ffff-41ea-b68b-d35539ad3b6e')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 2000205131)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'df_scenario_uid', value: '4d6ac86e-f730-420b-a36f-c482fb54e80f')
            column(name: 'payee_account_number', value: 2000205131)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '0427f1de-2894-4a1d-b154-b1bf0e91192c')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10T15:00:00Z')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 1961)
            column(name: 'market_period_to', value: 1961)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '39105ac2-d274-44bb-a2a0-244e2c0aaacb')
            column(name: 'df_usage_batch_uid', value: '275c0dd4-ffff-41ea-b68b-d35539ad3b6e')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2002 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 2000205131)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'df_scenario_uid', value: '4d6ac86e-f730-420b-a36f-c482fb54e80f')
            column(name: 'payee_account_number', value: 2000205131)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '39105ac2-d274-44bb-a2a0-244e2c0aaacb')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10T15:00:00Z')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 1961)
            column(name: 'market_period_to', value: 1961)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '488bf58f-a123-4fdf-96dd-fcf510bce840')
            column(name: 'df_usage_batch_uid', value: '275c0dd4-ffff-41ea-b68b-d35539ad3b6e')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2003 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 2000205131)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'df_scenario_uid', value: '4d6ac86e-f730-420b-a36f-c482fb54e80f')
            column(name: 'payee_account_number', value: 2000205131)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '488bf58f-a123-4fdf-96dd-fcf510bce840')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10T15:00:00Z')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 1970)
            column(name: 'market_period_to', value: 1970)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ccd30bca-f76d-48cb-853b-c6911c6f9608')
            column(name: 'df_usage_batch_uid', value: '275c0dd4-ffff-41ea-b68b-d35539ad3b6e')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2004 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 2000205131)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'df_scenario_uid', value: '4d6ac86e-f730-420b-a36f-c482fb54e80f')
            column(name: 'payee_account_number', value: 2000205131)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ccd30bca-f76d-48cb-853b-c6911c6f9608')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10T15:00:00Z')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 1961)
            column(name: 'market_period_to', value: 1961)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '3dc79138-1f53-40f2-acf9-3e157e9ff91a')
            column(name: 'df_usage_uid', value: '5ed3ed4f-fdd4-4521-bbe5-27870dc8bd62')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'FAS batch statistic\'')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '9798902e-9600-4330-afb7-af6c9c6f8182')
            column(name: 'df_usage_uid', value: '5ed3ed4f-fdd4-4521-bbe5-27870dc8bd62')
            column(name: 'action_type_ind', value: 'WORK_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst 480382914 was found by standard number 1008902112377654XX')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'f08651fe-22e6-49a8-ba08-d87e5e4056ec')
            column(name: 'df_usage_uid', value: '5ed3ed4f-fdd4-4521-bbe5-27870dc8bd62')
            column(name: 'action_type_ind', value: 'RH_NOT_FOUND')
            column(name: 'action_reason', value: 'Rightsholder account 2000205131 was not found in RMS')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '5785ed67-2286-4611-b88a-98599e274d65')
            column(name: 'df_usage_uid', value: '5ed3ed4f-fdd4-4521-bbe5-27870dc8bd62')
            column(name: 'action_type_ind', value: 'SENT_FOR_RA')
            column(name: 'action_reason', value: 'Sent for RA: job id \'3e66a95c-e13d-4267-8asgrgfdee75dc77\'')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'aeb3d82a-8941-421f-b3db-1e6e7dc5e00f')
            column(name: 'df_usage_uid', value: 'e0b83b52-734a-439c-8e6b-2345c2aaa8e2')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'FAS batch statistic\'')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '51601e1e-b432-4dc5-85d5-a91a2f31cfba')
            column(name: 'df_usage_uid', value: 'e0b83b52-734a-439c-8e6b-2345c2aaa8e2')
            column(name: 'action_type_ind', value: 'WORK_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst 480382914 was found by standard number 1008902112377654XX')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '348c9fd0-6cc7-4a35-86e3-f815e60c13dc')
            column(name: 'df_usage_uid', value: 'e0b83b52-734a-439c-8e6b-2345c2aaa8e2')
            column(name: 'action_type_ind', value: 'RH_FOUND')
            column(name: 'action_reason', value: 'Rightsholder account 2000205131 was found in RMS')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '0e2d10a7-ec3c-4d78-adc8-563618709273')
            column(name: 'df_usage_uid', value: '366eca1a-4974-4fce-a585-b9635b5a71c9')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'FAS batch statistic\'')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '8d42519d-9035-45eb-9bc9-163678124dc2')
            column(name: 'df_usage_uid', value: '366eca1a-4974-4fce-a585-b9635b5a71c9')
            column(name: 'action_type_ind', value: 'WORK_NOT_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst was not found by Standard Number 0102112377654XX')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1145b683-0916-4e96-a786-02e3222bb451')
            column(name: 'df_usage_uid', value: '08c85ce4-15bf-4ae4-a3b0-b82742ac4357')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'FAS batch statistic\'')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '23f6b325-38ae-4a9d-87f8-5cf95ffe1d99')
            column(name: 'df_usage_uid', value: '08c85ce4-15bf-4ae4-a3b0-b82742ac4357')
            column(name: 'action_type_ind', value: 'ELIGIBLE_FOR_NTS')
            column(name: 'action_reason', value: 'Detail was made eligible for NTS because sum of gross amounts, grouped by standard number, is less than \$100')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7c7297a1-eec4-4ea8-9ce9-6f44e8502266')
            column(name: 'df_usage_uid', value: 'fb41b86e-755d-437c-8834-8044e73d72e8')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'FAS batch statistic\'')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'f91dd54c-f41f-439d-97d1-46611b767a5f')
            column(name: 'df_usage_uid', value: 'acb55e9a-d956-4a49-8662-8f6a2b2f3048')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'FAS batch statistic\'')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '0e836bdb-7375-40d3-a78e-850a3336b029')
            column(name: 'df_usage_uid', value: 'acb55e9a-d956-4a49-8662-8f6a2b2f3048')
            column(name: 'action_type_ind', value: 'PAID')
            column(name: 'action_reason', value: 'Usage has been paid according to information from the LM')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1ce67a8a-c39c-4b08-b8cd-8a3eca32b4ca')
            column(name: 'df_usage_uid', value: '0427f1de-2894-4a1d-b154-b1bf0e91192c')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'FAS batch statistic\'')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'b6b5b9cb-5270-4e9a-bcb6-1f6ff2014a01')
            column(name: 'df_usage_uid', value: '0427f1de-2894-4a1d-b154-b1bf0e91192c')
            column(name: 'action_type_ind', value: 'PAID')
            column(name: 'action_reason', value: 'Usage has been paid according to information from the LM')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'efb9f370-ed79-4da3-9ac1-90a000d1c977')
            column(name: 'df_usage_uid', value: '0427f1de-2894-4a1d-b154-b1bf0e91192c')
            column(name: 'action_type_ind', value: 'ARCHIVED')
            column(name: 'action_reason', value: 'Usage was sent to CRM')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '0592bb5f-3c7d-4aeb-94bb-cd4d1b1d74eb')
            column(name: 'df_usage_uid', value: '39105ac2-d274-44bb-a2a0-244e2c0aaacb')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'FAS batch statistic\'')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '082e032f-fbb6-43fc-808b-93d94c87c823')
            column(name: 'df_usage_uid', value: '39105ac2-d274-44bb-a2a0-244e2c0aaacb')
            column(name: 'action_type_ind', value: 'PAID')
            column(name: 'action_reason', value: 'Usage has been paid according to information from the LM')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'd952e47d-bcdc-438c-bed8-e183c0f29486')
            column(name: 'df_usage_uid', value: '39105ac2-d274-44bb-a2a0-244e2c0aaacb')
            column(name: 'action_type_ind', value: 'ARCHIVED')
            column(name: 'action_reason', value: 'Usage was sent to CRM')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'a3d4075b-b5a4-4934-a21a-cdc8dac4b760')
            column(name: 'df_usage_uid', value: '488bf58f-a123-4fdf-96dd-fcf510bce840')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'FAS batch statistic\'')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7082173b-d900-47fa-8ed7-a1147ac69399')
            column(name: 'df_usage_uid', value: '488bf58f-a123-4fdf-96dd-fcf510bce840')
            column(name: 'action_type_ind', value: 'PAID')
            column(name: 'action_reason', value: 'Usage has been paid according to information from the LM')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7d90e8ba-3436-40ad-9658-cba7dda627bd')
            column(name: 'df_usage_uid', value: '488bf58f-a123-4fdf-96dd-fcf510bce840')
            column(name: 'action_type_ind', value: 'ARCHIVED')
            column(name: 'action_reason', value: 'Usage was sent to CRM')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'd53bc93b-aa93-4c4c-b462-ff1afdad9c96')
            column(name: 'df_usage_uid', value: 'ccd30bca-f76d-48cb-853b-c6911c6f9608')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'FAS batch statistic\'')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'c38a5172-c325-4e74-a6d6-11a77dcdba03')
            column(name: 'df_usage_uid', value: 'ccd30bca-f76d-48cb-853b-c6911c6f9608')
            column(name: 'action_type_ind', value: 'PAID')
            column(name: 'action_reason', value: 'Usage has been paid according to information from the LM')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '6adc83d8-1e33-4651-94ea-008e53bd10cb')
            column(name: 'df_usage_uid', value: 'ccd30bca-f76d-48cb-853b-c6911c6f9608')
            column(name: 'action_type_ind', value: 'ARCHIVED')
            column(name: 'action_reason', value: 'Usage was sent to CRM')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        rollback {
            dbRollback
        }
    }
}
