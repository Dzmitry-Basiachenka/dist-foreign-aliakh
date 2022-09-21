databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-05-31-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testUpdateNtsWithdrawnUsagesAndGetIds')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '1d13b04b-4237-4ab7-ab17-4f23e7fb5e94')
            column(name: 'name', value: 'FAS batch test update NTS Withdrawn usages')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'gross_amount', value: 300.00)
            column(name: 'initial_usages_count', value: 4)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2f2ca785-a7d3-4a7f-abd9-2bad80ac71dd')
            column(name: 'df_usage_batch_uid', value: '1d13b04b-4237-4ab7-ab17-4f23e7fb5e94')
            column(name: 'wr_wrk_inst', value: 122267672)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '0804709114')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '2f2ca785-a7d3-4a7f-abd9-2bad80ac71dd')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cbd6768d-a424-476e-b502-a832d9dbe85e')
            column(name: 'df_usage_batch_uid', value: '1d13b04b-4237-4ab7-ab17-4f23e7fb5e94')
            column(name: 'wr_wrk_inst', value: 122267672)
            column(name: 'work_title', value: 'A theory of cognitive dissonance')
            column(name: 'system_title', value: 'A theory of cognitive dissonance')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '0804709114')
            column(name: 'gross_amount', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'cbd6768d-a424-476e-b502-a832d9dbe85e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 25.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e2834925-ede5-4796-a30b-05770a6f04be')
            column(name: 'df_usage_batch_uid', value: '1d13b04b-4237-4ab7-ab17-4f23e7fb5e94')
            column(name: 'wr_wrk_inst', value: 159526527)
            column(name: 'work_title', value: 'Speculum')
            column(name: 'system_title', value: 'Speculum')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 150.01)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e2834925-ede5-4796-a30b-05770a6f04be')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 150.01)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd5e3c637-155a-4c05-999a-31a07e335491')
            column(name: 'df_usage_batch_uid', value: '1d13b04b-4237-4ab7-ab17-4f23e7fb5e94')
            column(name: 'wr_wrk_inst', value: 569526592)
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '10457143')
            column(name: 'gross_amount', value: 99.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd5e3c637-155a-4c05-999a-31a07e335491')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publication_date', value: '2019-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 99.99)
        }

        rollback {
            dbRollback
        }
    }
}
