databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-09-28-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testUpdateAssignee, testUpdateAssigneeToNull')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '17c79b33-0949-484f-aea4-1f765cc1c019')
            column(name: 'period', value: 202106)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'wr_wrk_inst', value: 123160519)
            column(name: 'system_title', value: 'Washington post')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'price', value: 5.0000000000)
            column(name: 'price_in_usd', value: 5.0000000000)
            column(name: 'price_year', value: 2021)
            column(name: 'price_access_type', value: 'Print only')
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_comment', value: 'price comment 1')
            column(name: 'content', value: 60)
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 1)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 10.0000000000)
            column(name: 'comment', value: 'Comment_assignment_1')
            column(name: 'content_comment', value: 'Content_comment_1')
            column(name: 'updated_datetime', value: '2021-09-11')
            column(name: 'updated_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: 'b8da90e6-f4f3-4782-860a-2c061f858574')
            column(name: 'period', value: 202112)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'rh_account_number', value: 1000009975)
            column(name: 'wr_wrk_inst', value: 123160510)
            column(name: 'system_title', value: 'History & criticism')
            column(name: 'standard_number', value: '1873-5555')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 10.0000000000)
            column(name: 'price_in_usd', value: 5.0000000000)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'List Price')
            column(name: 'content', value: 20)
            column(name: 'currency', value: 'EUR')
            column(name: 'currency_exchange_rate', value: 2)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 10.0000000000)
            column(name: 'comment', value: 'Comment_assignment_2')
            column(name: 'content_comment', value: 'Content_comment_2')
            column(name: 'updated_datetime', value: '2021-09-11')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
        }

        rollback {
            dbRollback
        }
    }
}
