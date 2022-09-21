databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-12-16-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserts test data for testClearProxyValues')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '33e6952d-3630-4f65-ac7e-d98feeb95d08')
            column(name: 'period', value: 211012)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 823333789)
            column(name: 'system_title', value: 'Redfords and Mitchells')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 100)
            column(name: 'price_in_usd', value: 100)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 1)
            column(name: 'content_flag', value: true)
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 1)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 100)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'comment', value: 'Comment')
            column(name: 'content_source', value: 'Book')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2021-09-20T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-09-20T00:00:00-04:00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '98f01d86-cb9f-4673-b409-00015df4bdc7')
            column(name: 'period', value: 211006)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 153363156)
            column(name: 'system_title', value: 'Travor and Co')
            column(name: 'standard_number', value: '1722-7773')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_flag', value: false)
            column(name: 'content_flag', value: false)
            column(name: 'content_unit_price', value: 300)
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'comment', value: 'Comment')
            column(name: 'updated_datetime', value: '2021-09-18T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-09-18T00:00:00-04:00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '57f0198d-0366-4a8e-a833-e40986a37981')
            column(name: 'period', value: 211012)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 153363156)
            column(name: 'system_title', value: 'Travor and Co')
            column(name: 'standard_number', value: '1722-7773')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_flag', value: false)
            column(name: 'content_flag', value: false)
            column(name: 'content_unit_price', value: 200)
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'comment', value: 'Comment')
            column(name: 'updated_datetime', value: '2021-09-18T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-09-18T00:00:00-04:00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: 'e6b6588f-df06-4c63-b9c0-4b4f1e711945')
            column(name: 'period', value: 211012)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006859)
            column(name: 'wr_wrk_inst', value: 153363156)
            column(name: 'system_title', value: 'Travor and Co')
            column(name: 'standard_number', value: '1722-7773')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_flag', value: false)
            column(name: 'content_flag', value: false)
            column(name: 'content_unit_price', value: 200)
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'comment', value: 'Comment')
            column(name: 'updated_datetime', value: '2021-09-18T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-09-18T00:00:00-04:00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        rollback {
            dbRollback
        }
    }
}
