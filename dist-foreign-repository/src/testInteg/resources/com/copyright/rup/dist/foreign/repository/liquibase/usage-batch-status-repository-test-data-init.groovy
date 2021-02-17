databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-02-10-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testFindUsageBatchStatusesFas')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'cf56b889-82fe-4990-b111-9c56ce986281')
            column(name: 'name', value: 'FAS in progress batch')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: '2022')
            column(name: 'gross_amount', value: '600.00')
            column(name: 'initial_usages_count', value: '9')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f1243803-a0f4-453d-8f8d-da784f00d275')
            column(name: 'df_usage_batch_uid', value: 'cf56b889-82fe-4990-b111-9c56ce986281')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'standard_number', value: '10457143')
            column(name: 'product_family', value: 'FAS')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f1243803-a0f4-453d-8f8d-da784f00d275')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '320121ee-5d27-4125-a0e8-9f00ee8a78e0')
            column(name: 'df_usage_batch_uid', value: 'cf56b889-82fe-4990-b111-9c56ce986281')
            column(name: 'wr_wrk_inst', value: '122267672')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '0804709114')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '320121ee-5d27-4125-a0e8-9f00ee8a78e0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '726424dc-2f2e-479a-93eb-09b256bfed9b')
            column(name: 'df_usage_batch_uid', value: 'cf56b889-82fe-4990-b111-9c56ce986281')
            column(name: 'work_title', value: 'Speculum')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: '150.01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '726424dc-2f2e-479a-93eb-09b256bfed9b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '150.01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '480b6309-7d24-4a50-b000-46cbe37db5a1')
            column(name: 'df_usage_batch_uid', value: 'cf56b889-82fe-4990-b111-9c56ce986281')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: '49.99')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '480b6309-7d24-4a50-b000-46cbe37db5a1')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '49.99')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1bb5ed88-1c04-4a8f-aaec-90909f7cc259')
            column(name: 'df_usage_batch_uid', value: 'cf56b889-82fe-4990-b111-9c56ce986281')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '1bb5ed88-1c04-4a8f-aaec-90909f7cc259')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cdcd7f35-a049-4519-b94c-3a8d94dd2531')
            column(name: 'df_usage_batch_uid', value: 'cf56b889-82fe-4990-b111-9c56ce986281')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'cdcd7f35-a049-4519-b94c-3a8d94dd2531')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ec908f1b-b349-4905-ad88-08a047619ecd')
            column(name: 'df_usage_batch_uid', value: 'cf56b889-82fe-4990-b111-9c56ce986281')
            column(name: 'wr_wrk_inst', value: '251235125')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1228902112377655XX')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ec908f1b-b349-4905-ad88-08a047619ecd')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '55708c2d-0a03-4bfd-ba62-fce0447c3ffe')
            column(name: 'df_usage_batch_uid', value: 'cf56b889-82fe-4990-b111-9c56ce986281')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '55708c2d-0a03-4bfd-ba62-fce0447c3ffe')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '65a522b6-b6b4-4476-8e22-786a7f032db4')
            column(name: 'df_usage_batch_uid', value: 'cf56b889-82fe-4990-b111-9c56ce986281')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '65a522b6-b6b4-4476-8e22-786a7f032db4')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '515a78e7-2a92-4b15-859a-fd9f70e80982')
            column(name: 'name', value: 'FAS completed batch')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: '2022')
            column(name: 'gross_amount', value: '700.00')
            column(name: 'initial_usages_count', value: '8')
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '12faaaa6-1e12-4306-8aab-1da694f97cf6')
            column(name: 'df_usage_batch_uid', value: '515a78e7-2a92-4b15-859a-fd9f70e80982')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '12faaaa6-1e12-4306-8aab-1da694f97cf6')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a9227794-e334-4c77-b659-a9ca30a90ac2')
            column(name: 'df_usage_batch_uid', value: '515a78e7-2a92-4b15-859a-fd9f70e80982')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a9227794-e334-4c77-b659-a9ca30a90ac2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2017')
            column(name: 'reported_value', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '12c551c1-59f4-4210-9ab6-974645b4b7c8')
            column(name: 'df_usage_batch_uid', value: '515a78e7-2a92-4b15-859a-fd9f70e80982')
            column(name: 'wr_wrk_inst', value: '251235125')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1228902112377655XX')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '12c551c1-59f4-4210-9ab6-974645b4b7c8')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f4c9c082-9684-49f2-8936-e487e6ebba6b')
            column(name: 'df_usage_batch_uid', value: '515a78e7-2a92-4b15-859a-fd9f70e80982')
            column(name: 'wr_wrk_inst', value: '251235125')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1228902112377655XX')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f4c9c082-9684-49f2-8936-e487e6ebba6b')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8725dee1-4a2e-44ec-9bcb-2d7fce13e12b')
            column(name: 'df_usage_batch_uid', value: '515a78e7-2a92-4b15-859a-fd9f70e80982')
            column(name: 'wr_wrk_inst', value: '251235125')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1228902112377655XX')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8725dee1-4a2e-44ec-9bcb-2d7fce13e12b')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'dab64021-aff2-43d3-8613-558d15c4333e')
            column(name: 'df_usage_batch_uid', value: '515a78e7-2a92-4b15-859a-fd9f70e80982')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'dab64021-aff2-43d3-8613-558d15c4333e')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5007dfe1-f865-475b-88ae-79b960b85a38')
            column(name: 'df_usage_batch_uid', value: '515a78e7-2a92-4b15-859a-fd9f70e80982')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5007dfe1-f865-475b-88ae-79b960b85a38')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8b3217d7-78d6-4098-91ad-313e366ba2cc')
            column(name: 'df_usage_batch_uid', value: '515a78e7-2a92-4b15-859a-fd9f70e80982')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8b3217d7-78d6-4098-91ad-313e366ba2cc')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100.00')
        }
    }

    changeSet(id: '2021-02-15-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testFindUsageBatchIdsByProductFamilyAndStartDateFrom')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '38b6ae7e-20e4-46cf-a684-8d3c7c67a940')
            column(name: 'name', value: 'FAS associated with scenario batch')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: '2022')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'initial_usages_count', value: '2')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'e350b2c2-1102-435b-a8b8-e80516b7d792')
            column(name: 'name', value: 'FAS Scenario 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c0ae8a59-17cb-4fea-821d-9429e06b751e')
            column(name: 'df_usage_batch_uid', value: '38b6ae7e-20e4-46cf-a684-8d3c7c67a940')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c0ae8a59-17cb-4fea-821d-9429e06b751e')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3c0c5ae2-0d6a-4f7d-b530-5f89f58ac796')
            column(name: 'df_usage_batch_uid', value: '38b6ae7e-20e4-46cf-a684-8d3c7c67a940')
            column(name: 'df_scenario_uid', value: 'e350b2c2-1102-435b-a8b8-e80516b7d792')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3c0c5ae2-0d6a-4f7d-b530-5f89f58ac796')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0e5af78f-5b2e-469c-9bdc-2e5e396f1436')
            column(name: 'name', value: 'FAS associated with scenario batch 2')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: '2022')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'initial_usages_count', value: '1')
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '032640d3-44f4-4dd0-8bcd-168f55c6a59b')
            column(name: 'name', value: 'FAS 2 Scenario')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4ebb6197-bb64-4405-bace-00116332d583')
            column(name: 'df_usage_batch_uid', value: '0e5af78f-5b2e-469c-9bdc-2e5e396f1436')
            column(name: 'df_scenario_uid', value: '032640d3-44f4-4dd0-8bcd-168f55c6a59b')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4ebb6197-bb64-4405-bace-00116332d583')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100.00')
        }
    }

    changeSet(id: '2021-02-15-01', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testFindUsageBatchStatusesNts')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a34417b5-12c1-48e2-9aed-d3861b49545b')
            column(name: 'name', value: 'NTS in progress batch')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2021-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2021')
            column(name: 'initial_usages_count', value: '7')
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c09aa888-85a5-4377-8c7a-85d84d255b5a')
            column(name: 'df_usage_batch_uid', value: 'a34417b5-12c1-48e2-9aed-d3861b49545b')
            column(name: 'wr_wrk_inst', value: '122267677')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c09aa888-85a5-4377-8c7a-85d84d255b5a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'a3b1f680-6f30-4e99-98fb-5547957da80f')
            column(name: 'df_usage_batch_uid', value: 'a34417b5-12c1-48e2-9aed-d3861b49545b')
            column(name: 'wr_wrk_inst', value: '122267677')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'a3b1f680-6f30-4e99-98fb-5547957da80f')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4c7f2465-9f6a-4dc7-85aa-eada6392a7e2')
            column(name: 'df_usage_batch_uid', value: 'a34417b5-12c1-48e2-9aed-d3861b49545b')
            column(name: 'wr_wrk_inst', value: '642267671')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4c7f2465-9f6a-4dc7-85aa-eada6392a7e2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '92818564-32b1-418c-bec3-28ae03f80455')
            column(name: 'df_usage_batch_uid', value: 'a34417b5-12c1-48e2-9aed-d3861b49545b')
            column(name: 'wr_wrk_inst', value: '642267671')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '92818564-32b1-418c-bec3-28ae03f80455')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '100')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '359de82f-374b-4d53-88ab-0be3982b22aa')
            column(name: 'name', value: 'NTS completed batch')
            column(name: 'rro_account_number', value: '1000000001')
            column(name: 'payment_date', value: '2021-01-11')
            column(name: 'product_family', value: 'NTS')
            column(name: 'fiscal_year', value: '2021')
            column(name: 'initial_usages_count', value: '5')
            column(name: 'nts_fields', value: '{"markets": ["Univ"], "stm_amount": 1000, "non_stm_amount": 1000, "stm_minimum_amount": 50, ' +
                    '"fund_pool_period_to": 2017, "fund_pool_period_from": 2013, "non_stm_minimum_amount": 7}')
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5f491fe4-2f1a-434f-b536-92350c623dab')
            column(name: 'df_usage_batch_uid', value: '359de82f-374b-4d53-88ab-0be3982b22aa')
            column(name: 'wr_wrk_inst', value: '122267677')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5f491fe4-2f1a-434f-b536-92350c623dab')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9522a3cc-5526-48e5-b4f0-182a6c8ccdb1')
            column(name: 'df_usage_batch_uid', value: '359de82f-374b-4d53-88ab-0be3982b22aa')
            column(name: 'wr_wrk_inst', value: '122267677')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9522a3cc-5526-48e5-b4f0-182a6c8ccdb1')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '900')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'aee126ca-f143-44c4-9e65-3ca80ba59875')
            column(name: 'df_usage_batch_uid', value: '359de82f-374b-4d53-88ab-0be3982b22aa')
            column(name: 'wr_wrk_inst', value: '122267677')
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'UNCLASSIFIED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'aee126ca-f143-44c4-9e65-3ca80ba59875')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '900')
        }
    }


    changeSet(id: '2021-02-16-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testFindUsageBatchStatusesAacl')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'name', value: 'AACL in progress batch')
            column(name: 'payment_date', value: '2021-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: '2021')
            column(name: 'initial_usages_count', value: '7')
            column(name: 'baseline_years', value: '0')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '327ecb4d-e091-41ef-a0ab-56201331b0c6')
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '327ecb4d-e091-41ef-a0ab-56201331b0c6')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2018')
            column(name: 'usage_source', value: 'Feb 2018 TUR')
            column(name: 'number_of_pages', value: '341')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e3e9d0e7-717f-4cdc-8e30-e0ec57badb14')
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'wr_wrk_inst', value: '109040891')
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '300')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'e3e9d0e7-717f-4cdc-8e30-e0ec57badb14')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: '2021')
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: '200')
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '81c8e8da-0888-4f9c-91aa-a94ea18fc1e4')
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'wr_wrk_inst', value: '109040891')
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '300')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '81c8e8da-0888-4f9c-91aa-a94ea18fc1e4')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: '2021')
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: '200')
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '58b39805-9eb9-48b0-8081-b25ac3dc7335')
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'wr_wrk_inst', value: '109040891')
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '300')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '58b39805-9eb9-48b0-8081-b25ac3dc7335')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: '2021')
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: '200')
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f9286815-0760-4aa0-a801-f78b362f5e8e')
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'wr_wrk_inst', value: '109040891')
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '300')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'f9286815-0760-4aa0-a801-f78b362f5e8e')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: '2021')
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: '200')
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9f14014c-2b72-4c2e-9751-fb8ecb0123f0')
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'rh_account_number', value: '7000000002')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '9f14014c-2b72-4c2e-9751-fb8ecb0123f0')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2015')
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '113')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3d7c9de0-3d14-42e4-a500-fb10344a77ff')
            column(name: 'name', value: 'AACL completed batch')
            column(name: 'payment_date', value: '2021-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: '2021')
            column(name: 'initial_usages_count', value: '8')
            column(name: 'baseline_years', value: '0')
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '570c19f3-85bd-4495-82d7-dc74dc3b809b')
            column(name: 'df_usage_batch_uid', value: '3d7c9de0-3d14-42e4-a500-fb10344a77ff')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '570c19f3-85bd-4495-82d7-dc74dc3b809b')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2018')
            column(name: 'usage_source', value: 'Feb 2018 TUR')
            column(name: 'number_of_pages', value: '341')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8afd52fb-ab50-47fd-8a2f-3b5564a23055')
            column(name: 'df_usage_batch_uid', value: '3d7c9de0-3d14-42e4-a500-fb10344a77ff')
            column(name: 'wr_wrk_inst', value: '109040891')
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '300')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '8afd52fb-ab50-47fd-8a2f-3b5564a23055')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: '2021')
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: '200')
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0804536d-d240-480f-9558-c860894cafd1')
            column(name: 'df_usage_batch_uid', value: '3d7c9de0-3d14-42e4-a500-fb10344a77ff')
            column(name: 'wr_wrk_inst', value: '109040891')
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'gross_amount', value: '100.00')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '300')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '0804536d-d240-480f-9558-c860894cafd1')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: '2021')
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: '200')
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '13ca08ff-5e52-4154-9654-c51b3ecdb8bf')
            column(name: 'df_usage_batch_uid', value: '3d7c9de0-3d14-42e4-a500-fb10344a77ff')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'rh_account_number', value: '7000000002')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '13ca08ff-5e52-4154-9654-c51b3ecdb8bf')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2015')
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '113')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '98b0754c-cf62-44c3-8754-e46e8096012c')
            column(name: 'df_usage_batch_uid', value: '3d7c9de0-3d14-42e4-a500-fb10344a77ff')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'rh_account_number', value: '7000000002')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '98b0754c-cf62-44c3-8754-e46e8096012c')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2015')
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '113')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
        }
    }
}
