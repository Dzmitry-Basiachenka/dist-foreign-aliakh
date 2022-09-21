databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2019-03-27-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindByIdForNts, testFindByIdForAacl, testDelete')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'b5b64c3a-55d2-462e-b169-362dca6a4dd7')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Q1 2019 100%')
            column(name: 'comment', value: 'some comment')
            column(name: 'total_amount', value: 50.00)
            column(name: 'updated_datetime', value: '2019-03-27 16:35:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '49060c9b-9cc2-4b93-b701-fffc82eb28b0')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: 10.00)
            column(name: 'updated_datetime', value: '2019-03-26 16:35:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a163cca7-8eeb-449c-8a3c-29ff3ec82e58')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 10)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b2d4646f-9ecb-4b64-bb47-384de8d3f7f1')
            column(name: 'df_usage_batch_uid', value: 'a163cca7-8eeb-449c-8a3c-29ff3ec82e58')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 25)
            column(name: 'gross_amount', value: 10.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b2d4646f-9ecb-4b64-bb47-384de8d3f7f1')
            column(name: 'df_fund_pool_uid', value: '49060c9b-9cc2-4b93-b701-fffc82eb28b0')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
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
