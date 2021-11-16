databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-11-17-04', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindNameByNtsFundPoolId')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b2dec214-8ffb-4164-852c-3b1b8047f02c')
            column(name: 'name', value: 'FAS Batch with NTS usage 2')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'gross_amount', value: 50)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '8cb9092d-a0f7-474e-a13b-af1a134e4c86')
            column(name: 'name', value: 'Sent to LM NTS scenario with audit')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'The description of scenario 8')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2017-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2018-05-15 11:41:52.735531+03')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 300.00, "pre_service_fee_amount": 50.00,' +
                    '"post_service_fee_amount": 100.00, "pre_service_fee_fund_uid": "815d6736-a34e-4fc8-96c3-662a114fa7f2"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '815d6736-a34e-4fc8-96c3-662a114fa7f2')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'NTS Fund Pool 4')
            column(name: 'total_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '6ef849e4-68be-47c7-abc4-50440b60d789')
            column(name: 'df_usage_batch_uid', value: 'b2dec214-8ffb-4164-852c-3b1b8047f02c')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 50.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6ef849e4-68be-47c7-abc4-50440b60d789')
            column(name: 'df_fund_pool_uid', value: '815d6736-a34e-4fc8-96c3-662a114fa7f2')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 50)
        }

        rollback {
            dbRollback
        }
    }
}
