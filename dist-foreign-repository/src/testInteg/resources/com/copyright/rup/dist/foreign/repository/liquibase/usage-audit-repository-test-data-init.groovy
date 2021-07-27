databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2017-11-30-00', author: 'Aliaksandra_Bayanouskaya <abayanouskaya@copyright.com>') {
        comment('Inserting test data for UsageAuditRepositoryIntegrationTest')

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

        //testFindNtsBatchStatistic
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e687b952-2b4c-4316-bbc7-2c07eb2dcd1b')
            column(name: 'name', value: 'NTS batch statistic')
            column(name: 'rro_account_number', value: 7001832491)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-12-04T15:00:00Z')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'cc51ced4-ba96-494e-b119-ed0578a4a5d9')
            column(name: 'name', value: 'Scenario for NTS batch statistic')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9606c52c-66de-4267-b298-ca5b69b7581d')
            column(name: 'df_usage_batch_uid', value: 'e687b952-2b4c-4316-bbc7-2c07eb2dcd1b')
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
            column(name: 'df_usage_fas_uid', value: '9606c52c-66de-4267-b298-ca5b69b7581d')
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
            column(name: 'df_usage_uid', value: 'd55daf59-8b4a-469a-8abf-9178d49288bc')
            column(name: 'df_usage_batch_uid', value: 'e687b952-2b4c-4316-bbc7-2c07eb2dcd1b')
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
            column(name: 'df_usage_fas_uid', value: 'd55daf59-8b4a-469a-8abf-9178d49288bc')
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
            column(name: 'df_usage_uid', value: 'c069ba24-d636-487d-b21a-7da212ebebe8')
            column(name: 'df_usage_batch_uid', value: 'e687b952-2b4c-4316-bbc7-2c07eb2dcd1b')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '0102112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 55.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c069ba24-d636-487d-b21a-7da212ebebe8')
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
            column(name: 'df_usage_uid', value: '7e11f349-8705-4019-9b39-bbb1d3d92a95')
            column(name: 'df_usage_batch_uid', value: 'e687b952-2b4c-4316-bbc7-2c07eb2dcd1b')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'ELIGIBLE_FOR_NTS')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 150.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7e11f349-8705-4019-9b39-bbb1d3d92a95')
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
            column(name: 'df_usage_archive_uid', value: 'a8d5a6c3-e60e-4b43-829d-dad376be47e4')
            column(name: 'df_usage_batch_uid', value: 'e687b952-2b4c-4316-bbc7-2c07eb2dcd1b')
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
            column(name: 'df_scenario_uid', value: 'cc51ced4-ba96-494e-b119-ed0578a4a5d9')
            column(name: 'payee_account_number', value: 2000205131)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a8d5a6c3-e60e-4b43-829d-dad376be47e4')
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
            column(name: 'df_usage_archive_uid', value: 'abd996ea-8e93-444e-8f9f-74b16f3d0f21')
            column(name: 'df_usage_batch_uid', value: 'e687b952-2b4c-4316-bbc7-2c07eb2dcd1b')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 2000205131)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'df_scenario_uid', value: 'cc51ced4-ba96-494e-b119-ed0578a4a5d9')
            column(name: 'payee_account_number', value: 2000205131)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'abd996ea-8e93-444e-8f9f-74b16f3d0f21')
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
            column(name: 'df_usage_archive_uid', value: 'dcf81b2e-e238-40cf-9cf0-cd7fe829b13c')
            column(name: 'df_usage_batch_uid', value: 'e687b952-2b4c-4316-bbc7-2c07eb2dcd1b')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 2000205131)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'df_scenario_uid', value: 'cc51ced4-ba96-494e-b119-ed0578a4a5d9')
            column(name: 'payee_account_number', value: 2000205131)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'dcf81b2e-e238-40cf-9cf0-cd7fe829b13c')
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
            column(name: 'df_usage_archive_uid', value: 'a95a99c7-6eaf-44b1-921b-651e5722fca4')
            column(name: 'df_usage_batch_uid', value: 'e687b952-2b4c-4316-bbc7-2c07eb2dcd1b')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2002 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 2000205131)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'df_scenario_uid', value: 'cc51ced4-ba96-494e-b119-ed0578a4a5d9')
            column(name: 'payee_account_number', value: 2000205131)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a95a99c7-6eaf-44b1-921b-651e5722fca4')
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
            column(name: 'df_usage_archive_uid', value: '289c9993-3f53-4d46-8730-c19fd0dda49f')
            column(name: 'df_usage_batch_uid', value: 'e687b952-2b4c-4316-bbc7-2c07eb2dcd1b')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2003 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 2000205131)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'df_scenario_uid', value: 'cc51ced4-ba96-494e-b119-ed0578a4a5d9')
            column(name: 'payee_account_number', value: 2000205131)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '289c9993-3f53-4d46-8730-c19fd0dda49f')
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
            column(name: 'df_usage_archive_uid', value: '807a8633-bd96-4c33-b59d-39a829435cb5')
            column(name: 'df_usage_batch_uid', value: 'e687b952-2b4c-4316-bbc7-2c07eb2dcd1b')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2004 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 2000205131)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'net_amount', value: 68.00)
            column(name: 'service_fee', value: 0.32)
            column(name: 'service_fee_amount', value: 32.00)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'df_scenario_uid', value: 'cc51ced4-ba96-494e-b119-ed0578a4a5d9')
            column(name: 'payee_account_number', value: 2000205131)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '807a8633-bd96-4c33-b59d-39a829435cb5')
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
            column(name: 'df_usage_audit_uid', value: '92022e88-e72e-4fd3-a9b8-56838cd7f0d9')
            column(name: 'df_usage_uid', value: '9606c52c-66de-4267-b298-ca5b69b7581d')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'Usage was created based on Market(s): \'Bus\', Fund Pool Period: 2000-2015')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'fa35f950-8e32-4ac7-9f99-8e48381d425f')
            column(name: 'df_usage_uid', value: 'd55daf59-8b4a-469a-8abf-9178d49288bc')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'Usage was created based on Market(s): \'Bus\', Fund Pool Period: 2000-2015')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7575c2c4-5770-444b-90df-778da5e7b791')
            column(name: 'df_usage_uid', value: 'c069ba24-d636-487d-b21a-7da212ebebe8')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'Usage was created based on Market(s): \'Bus\', Fund Pool Period: 2000-2015')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '88730a7b-a741-4eff-b12a-0ddcca0a1db6')
            column(name: 'df_usage_uid', value: '7e11f349-8705-4019-9b39-bbb1d3d92a95')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'Usage was created based on Market(s): \'Bus\', Fund Pool Period: 2000-2015')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '15daa8a1-1143-4147-b806-569bf00c811e')
            column(name: 'df_usage_uid', value: 'a8d5a6c3-e60e-4b43-829d-dad376be47e4')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'Usage was created based on Market(s): \'Bus\', Fund Pool Period: 2000-2015')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '5adf50a4-e2c0-425a-b52a-8546a18db9b9')
            column(name: 'df_usage_uid', value: 'abd996ea-8e93-444e-8f9f-74b16f3d0f21')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'Usage was created based on Market(s): \'Bus\', Fund Pool Period: 2000-2015')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'b53fd25f-7cbe-494e-bc53-995511916768')
            column(name: 'df_usage_uid', value: 'abd996ea-8e93-444e-8f9f-74b16f3d0f21')
            column(name: 'action_type_ind', value: 'RH_FOUND')
            column(name: 'action_reason', value: 'Rightsholder account 1000011835 was found in RMS')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '75f797b0-d99e-46a8-8f28-4ac12c3867ba')
            column(name: 'df_usage_uid', value: 'dcf81b2e-e238-40cf-9cf0-cd7fe829b13c')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'Usage was created based on Market(s): \'Bus\', Fund Pool Period: 2000-2015')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'b632f4f8-7561-4873-8465-d883c72021b2')
            column(name: 'df_usage_uid', value: 'dcf81b2e-e238-40cf-9cf0-cd7fe829b13c')
            column(name: 'action_type_ind', value: 'RH_FOUND')
            column(name: 'action_reason', value: 'Rightsholder account 1000011835 was found in RMS')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '6d71bf96-45dc-45a5-9740-51aa2873846a')
            column(name: 'df_usage_uid', value: 'dcf81b2e-e238-40cf-9cf0-cd7fe829b13c')
            column(name: 'action_type_ind', value: 'ELIGIBLE')
            column(name: 'action_reason', value: 'Usage has become eligible based on US rightsholder tax country')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'c6074f52-4f53-4e5e-a241-876c325aaf86')
            column(name: 'df_usage_uid', value: 'a95a99c7-6eaf-44b1-921b-651e5722fca4')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'Usage was created based on Market(s): \'Bus\', Fund Pool Period: 2000-2015')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '3806027f-0ddf-4e59-83ef-4947846971ff')
            column(name: 'df_usage_uid', value: 'a95a99c7-6eaf-44b1-921b-651e5722fca4')
            column(name: 'action_type_ind', value: 'RH_FOUND')
            column(name: 'action_reason', value: 'Rightsholder account 1000011835 was found in RMS')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '3686925e-b578-4c11-9e19-e271e39c8261')
            column(name: 'df_usage_uid', value: 'a95a99c7-6eaf-44b1-921b-651e5722fca4')
            column(name: 'action_type_ind', value: 'ELIGIBLE')
            column(name: 'action_reason', value: 'Usage has become eligible based on US rightsholder tax country')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'c04f3013-c94c-426b-a178-c3e8761ecfb2')
            column(name: 'df_usage_uid', value: '289c9993-3f53-4d46-8730-c19fd0dda49f')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'Usage was created based on Market(s): \'Bus\', Fund Pool Period: 2000-2015')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '82e68411-d79f-4038-8c2e-81cda54d3283')
            column(name: 'df_usage_uid', value: '289c9993-3f53-4d46-8730-c19fd0dda49f')
            column(name: 'action_type_ind', value: 'RH_FOUND')
            column(name: 'action_reason', value: 'Rightsholder account 1000011835 was found in RMS')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'b3011aa7-05a5-43b6-b1f7-d556266ae61d')
            column(name: 'df_usage_uid', value: '289c9993-3f53-4d46-8730-c19fd0dda49f')
            column(name: 'action_type_ind', value: 'ELIGIBLE')
            column(name: 'action_reason', value: 'Usage has become eligible based on US rightsholder tax country')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '55cfda7d-c641-4343-a943-95c3db4ed1a7')
            column(name: 'df_usage_uid', value: '807a8633-bd96-4c33-b59d-39a829435cb5')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'Usage was created based on Market(s): \'Bus\', Fund Pool Period: 2000-2015')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1c0e5538-b8e4-43ee-a610-626ab7077a0c')
            column(name: 'df_usage_uid', value: '807a8633-bd96-4c33-b59d-39a829435cb5')
            column(name: 'action_type_ind', value: 'RH_FOUND')
            column(name: 'action_reason', value: 'Rightsholder account 1000011835 was found in RMS')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '3c81da29-1396-45d3-8169-27c32feac8ba')
            column(name: 'df_usage_uid', value: '807a8633-bd96-4c33-b59d-39a829435cb5')
            column(name: 'action_type_ind', value: 'ELIGIBLE')
            column(name: 'action_reason', value: 'Usage has become eligible based on US rightsholder tax country')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        // testGetUsageStatistic
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f687b952-2b4c-4316-bbc7-2c07eb2dcd1c')
            column(name: 'name', value: 'Test batch for usage timings')
            column(name: 'rro_account_number', value: 7001832491)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-12-04T15:00:00Z')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3fb43e60-3352-4db4-9080-c30b8a6f6600')
            column(name: 'df_usage_batch_uid', value: 'f687b952-2b4c-4316-bbc7-2c07eb2dcd1c')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 80.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3fb43e60-3352-4db4-9080-c30b8a6f6600')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10T15:00:00Z')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 1960)
            column(name: 'market_period_to', value: 1960)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '85ee285e-1ea8-408e-9a0a-e65bc4cb5bbf')
            column(name: 'df_usage_uid', value: '3fb43e60-3352-4db4-9080-c30b8a6f6600')
            column(name: 'action_type_ind', value: 'ELIGIBLE')
            column(name: 'action_reason', value: 'Usage has become eligible')
            column(name: 'created_datetime', value: '2019-02-14 11:45:03.721492+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'f33b5590-4431-437c-bf5b-076bb60d321f')
            column(name: 'df_usage_uid', value: '3fb43e60-3352-4db4-9080-c30b8a6f6600')
            column(name: 'action_type_ind', value: 'RH_FOUND')
            column(name: 'action_reason', value: 'Rightsholder account 1000004090 was found in RMS')
            column(name: 'created_datetime', value: '2019-02-14 11:45:02.645621+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '4210ebb1-7f96-4347-bf84-b1cae37e70e8')
            column(name: 'df_usage_uid', value: '3fb43e60-3352-4db4-9080-c30b8a6f6600')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test batch for usage timings\' Batch')
            column(name: 'created_datetime', value: '2019-02-14 11:45:01.52369+03')
        }

        rollback ''
    }

    changeSet(id: '2019-06-24-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for testDeleteByScenarioId')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c905546a-6405-467d-a7ce-d4b19e5f7d5f')
            column(name: 'name', value: 'NTS Batch with SCENARIO_EXCLUDED usages')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'payment_date', value: '2019-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'fccc8c31-5259-472a-a9ca-508a8ed1cbc0')
            column(name: 'name', value: 'Test NTS scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount":300.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '7f6899c5-b68c-4afb-8012-6bd93f238ec0')
            column(name: 'df_scenario_uid', value: 'fccc8c31-5259-472a-a9ca-508a8ed1cbc0')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '7f6899c5-b68c-4afb-8012-6bd93f238ec0')
            column(name: 'df_usage_batch_uid', value: 'c905546a-6405-467d-a7ce-d4b19e5f7d5f')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ea85a226-8a4b-45e3-82f8-1233a9cd7ecb')
            column(name: 'df_usage_batch_uid', value: 'c905546a-6405-467d-a7ce-d4b19e5f7d5f')
            column(name: 'df_scenario_uid', value: 'fccc8c31-5259-472a-a9ca-508a8ed1cbc0')
            column(name: 'wr_wrk_inst', value: 122267677)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 900.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'service_fee_amount', value: 288.00)
            column(name: 'net_amount', value: 612.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ea85a226-8a4b-45e3-82f8-1233a9cd7ecb')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4b5751aa-6258-44c6-b839-a1ec0edfcf4d')
            column(name: 'df_usage_batch_uid', value: 'c905546a-6405-467d-a7ce-d4b19e5f7d5f')
            column(name: 'wr_wrk_inst', value: 642267671)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'SCENARIO_EXCLUDED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4b5751aa-6258-44c6-b839-a1ec0edfcf4d')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 100)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '71f2422f-92c4-4ef5-bd91-d5192797f9ee')
            column(name: 'df_usage_uid', value: 'ea85a226-8a4b-45e3-82f8-1233a9cd7ecb')
            column(name: 'action_type_ind', value: 'ELIGIBLE')
            column(name: 'action_reason', value: 'Usage has become eligible')
            column(name: 'created_datetime', value: '2051-02-14 11:45:03.721492+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '9e18bdcd-a2b7-4eda-b287-dd0b1acd3854')
            column(name: 'df_usage_uid', value: '4b5751aa-6258-44c6-b839-a1ec0edfcf4d')
            column(name: 'action_type_ind', value: 'ELIGIBLE_FOR_NTS')
            column(name: 'action_reason', value: 'Detail was made eligible for NTS because sum of gross amounts, grouped by Wr Wrk Inst, is less than 100')
            column(name: 'created_datetime', value: '2051-02-14 11:46:01.52369+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'd77613dc-66ee-4bff-b47f-f5b960d98393')
            column(name: 'df_usage_uid', value: '4b5751aa-6258-44c6-b839-a1ec0edfcf4d')
            column(name: 'action_type_ind', value: 'EXCLUDED_FROM_SCENARIO')
            column(name: 'action_reason', value: 'Usage was excluded from scenario')
            column(name: 'created_datetime', value: '2051-02-14 11:49:02.645621+03')
        }
    }

    changeSet(id: '2020-09-25-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testDeleteByBatchId')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '129b0e2d-8c2a-4c66-8dcf-456106c823d8')
            column(name: 'name', value: 'SAL usage batch 4')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2015)
            column(name: 'initial_usages_count', value: 3)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '51e60822-5b4c-4fa8-9922-05e93065f216')
            column(name: 'df_usage_batch_uid', value: '129b0e2d-8c2a-4c66-8dcf-456106c823d8')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Science News for students')
            column(name: 'rh_account_number', value: 1000000026)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '51e60822-5b4c-4fa8-9922-05e93065f216')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1101024IB2192')
            column(name: 'reported_article', value: 'Learning in Your Sleep')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9793bfb8-2628-485a-9ceb-0d7652691a1b')
            column(name: 'df_usage_batch_uid', value: '129b0e2d-8c2a-4c66-8dcf-456106c823d8')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-02-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '9793bfb8-2628-485a-9ceb-0d7652691a1b')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '11')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'assessment_name', value: 'FY16 Smarter Balanaced ELA')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1101024IB2192')
            column(name: 'coverage_year', value: '2012-2013')
            column(name: 'scored_assessment_date', value: '2015-10-03')
            column(name: 'question_identifier', value: 'SB7663')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 762)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ad91d122-aedc-4b41-a09e-f7f055f5cb85')
            column(name: 'df_usage_batch_uid', value: '129b0e2d-8c2a-4c66-8dcf-456106c823d8')
            column(name: 'wr_wrk_inst', value: 369040892)
            column(name: 'rh_account_number', value: 2000017004)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'created_datetime', value: '2015-03-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2015-03-14 12:00:00+00')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'record_version', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: 'ad91d122-aedc-4b41-a09e-f7f055f5cb85')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '7')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'assessment_name', value: 'FY17 EOC')
            column(name: 'assessment_type', value: 'EOC')
            column(name: 'reported_work_portion_id', value: '1101024IB2192')
            column(name: 'coverage_year', value: '2011-2012')
            column(name: 'scored_assessment_date', value: '2015-02-05')
            column(name: 'question_identifier', value: 'SB245')
            column(name: 'states', value: 'MN,OR')
            column(name: 'number_of_views', value: 254)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '395e1fc4-4634-472e-84fd-155230b96df6')
            column(name: 'df_usage_uid', value: '51e60822-5b4c-4fa8-9922-05e93065f216')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test batch for usage timings\' Batch')
            column(name: 'created_datetime', value: '2019-02-14 11:45:01.52369+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '1231229e-67dc-48f9-aa48-21393fbe5800')
            column(name: 'df_usage_uid', value: '9793bfb8-2628-485a-9ceb-0d7652691a1b')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test batch for usage timings\' Batch')
            column(name: 'created_datetime', value: '2019-02-14 11:45:01.52369+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '8cbc9d4d-62b3-4431-9876-b2cfcaeffcf2')
            column(name: 'df_usage_uid', value: 'ad91d122-aedc-4b41-a09e-f7f055f5cb85')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test batch for usage timings\' Batch')
            column(name: 'created_datetime', value: '2019-02-14 11:45:01.52369+03')
        }
    }
}
