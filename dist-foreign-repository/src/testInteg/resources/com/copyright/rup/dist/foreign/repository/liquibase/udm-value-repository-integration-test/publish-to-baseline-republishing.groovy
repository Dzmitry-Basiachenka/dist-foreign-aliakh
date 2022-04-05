databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-04-05-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserts test data for testPublishToBaselineRepublishing')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: 'b0cfa83a-6249-4aff-aac8-2567eb15fb9e')
            column(name: 'period', value: 202206)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 153363156)
            column(name: 'system_title', value: 'Travor and Co')
            column(name: 'standard_number', value: '1722-7773')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 500)
            column(name: 'price_in_usd', value: 22)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 5)
            column(name: 'content_flag', value: true)
            column(name: 'currency', value: 'CZK')
            column(name: 'currency_exchange_rate', value: 0.044)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 4.4)
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'comment', value: 'Comment')
            column(name: 'content_source', value: 'Book')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2021-09-11T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-09-11T00:00:00-04:00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '3ee0c4f7-001b-4970-a484-4bf028e5eb27')
            column(name: 'period', value: 202206)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 569856369)
            column(name: 'system_title', value: 'Malcolm Gruel Jr')
            column(name: 'standard_number', value: '1722-7773')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 120)
            column(name: 'price_in_usd', value: 120)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 5)
            column(name: 'content_flag', value: true)
            column(name: 'currency_exchange_rate', value: 1)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 24)
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'comment', value: 'Comment')
            column(name: 'content_source', value: 'Book')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2021-09-11T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2021-09-11T00:00:00-04:00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_baseline') {
            column(name: 'df_udm_value_baseline_uid', value: '3ee0c4f7-001b-4970-a484-4bf028e5eb27')
            column(name: 'period', value: 202206)
            column(name: 'wr_wrk_inst', value: 569856369)
            column(name: 'system_title', value: 'Malcolm Gruel Jr')
            column(name: 'price', value: 60)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 5)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 12)
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'comment', value: 'Comment')
            column(name: 'updated_datetime', value: '2021-09-11T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-09-11T00:00:00-04:00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        rollback {
            dbRollback
        }
    }
}
