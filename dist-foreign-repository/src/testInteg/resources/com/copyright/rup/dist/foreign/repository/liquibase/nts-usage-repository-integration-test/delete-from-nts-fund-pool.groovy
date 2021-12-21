databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-03-29-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testDeleteFromNtsFundPool')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0f00e96b-eed7-4f26-8004-3370ec30da45')
            column(name: 'name', value: 'FAS batch test delete NTS fund pool')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2022-02-12')
            column(name: 'fiscal_year', value: 2022)
            column(name: 'gross_amount', value: 10.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ba95f0b3-dc94-4925-96f2-93d05db9c469')
            column(name: 'df_usage_batch_uid', value: '0f00e96b-eed7-4f26-8004-3370ec30da45')
            column(name: 'work_title', value: 'Cell Biology')
            column(name: 'system_title', value: 'Cell Biology')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ba95f0b3-dc94-4925-96f2-93d05db9c469')
            column(name: 'df_fund_pool_uid', value: '3fef25b0-c0d1-4819-887f-4c6acc01390e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 10)
        }

        rollback {
            dbRollback
        }
    }
}
