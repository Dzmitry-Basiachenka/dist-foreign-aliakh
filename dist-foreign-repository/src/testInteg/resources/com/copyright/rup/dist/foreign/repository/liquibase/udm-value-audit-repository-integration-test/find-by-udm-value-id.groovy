databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-12-01-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindByUdmValueId, testInsert')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '14edbc9b-1473-4fc9-95f6-07b3ef45e851')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '16040f00-8564-4482-ab67-9965483a8a9f')
            column(name: 'period', value: 202106)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985899)
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 5.0000000000)
            column(name: 'price_in_usd', value: 2.5000000000)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'content', value: 60)
            column(name: 'content_source', value: 'Book')
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 2)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 10.0000000000)
            column(name: 'comment', value: 'Comment')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2021-09-11T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-09-11T00:00:00-04:00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: 'e7370736-60c0-4283-9948-717d075f152f')
            column(name: 'df_udm_value_uid', value: '16040f00-8564-4482-ab67-9965483a8a9f')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'UDM Value batch for period \'2021\' was populated')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-03-15 11:41:52.735531+03')
        }

        rollback {
            dbRollback
        }
    }
}
