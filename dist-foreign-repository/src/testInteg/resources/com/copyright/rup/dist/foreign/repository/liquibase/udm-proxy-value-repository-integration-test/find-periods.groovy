databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-11-24-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Inserts test data for testFindPeriods')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '3332e7c0-e655-4b0e-a0b3-125381a667d6')
            column(name: 'period', value: 211012)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 823333789)
            column(name: 'system_title', value: 'Redfords and Mitchells')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 150)
            column(name: 'price_in_usd', value: 150)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 1)
            column(name: 'content_flag', value: true)
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 1)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 150)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'comment', value: 'Comment')
            column(name: 'content_source', value: 'Book')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2021-09-20T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-09-20T00:00:00-04:00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_proxy_value') {
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'period', value: 211012)
            column(name: 'content_unit_price', value: 150)
            column(name: 'content_unit_price_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '6fd5aa50-7d7d-4ab7-8bd2-6ddcd7f055c2')
            column(name: 'period', value: 211012)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 273337156)
            column(name: 'system_title', value: 'Bread and Butter')
            column(name: 'standard_number', value: '1822-7773')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 50)
            column(name: 'price_in_usd', value: 50)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 1)
            column(name: 'content_flag', value: true)
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 1)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 50)
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'comment', value: 'Comment')
            column(name: 'content_source', value: 'Book')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2021-09-19T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-09-19T00:00:00-04:00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '2bb1d10d-0c9a-4378-ba21-69260515e54f')
            column(name: 'period', value: 211012)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 153363156)
            column(name: 'system_title', value: 'Travor and Co')
            column(name: 'standard_number', value: '1722-7773')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 300)
            column(name: 'price_in_usd', value: 300)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 1)
            column(name: 'content_flag', value: true)
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 1)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 300)
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'comment', value: 'Comment')
            column(name: 'content_source', value: 'Newspaper')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2021-09-18T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-09-18T00:00:00-04:00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_proxy_value') {
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'period', value: 211012)
            column(name: 'content_unit_price', value: 350)
            column(name: 'content_unit_price_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: 'b5a2b3ab-d443-4eed-8c69-a7a1cf442c43')
            column(name: 'period', value: 211006)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 569856369)
            column(name: 'system_title', value: 'Malcolm Gruel Jr')
            column(name: 'standard_number', value: '1722-7773')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 500)
            column(name: 'price_in_usd', value: 500)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 1)
            column(name: 'content_flag', value: true)
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 1)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 500)
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'comment', value: 'Comment')
            column(name: 'content_source', value: 'Newspaper')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2021-09-16T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2021-09-16T00:00:00-04:00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_proxy_value') {
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'period', value: 211006)
            column(name: 'content_unit_price', value: 500)
            column(name: 'content_unit_price_count', value: 1)
        }

        rollback {
            dbRollback
        }
    }
}
