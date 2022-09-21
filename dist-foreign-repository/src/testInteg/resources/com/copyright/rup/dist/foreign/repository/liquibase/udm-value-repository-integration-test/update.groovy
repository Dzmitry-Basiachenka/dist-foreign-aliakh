databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-10-21-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserts test data for testUpdate')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '869d1891-5c90-4786-8875-d6c6c12532f0')
            column(name: 'period', value: 211212)
            column(name: 'status_ind', value: 'RESEARCH_COMPLETE')
            column(name: 'rh_account_number', value: 100006858)
            column(name: 'wr_wrk_inst', value: 823333788)
            column(name: 'system_title', value: 'Redfords and Mitchells')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 60)
            column(name: 'price_in_usd', value: 60)
            column(name: 'price_year', value: 2100)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 5)
            column(name: 'content_flag', value: true)
            column(name: 'currency_exchange_rate', value: 1)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'content_unit_price', value: 12)
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'content_source', value: 'Book')
            column(name: 'content_comment', value: 'Content comment')
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
