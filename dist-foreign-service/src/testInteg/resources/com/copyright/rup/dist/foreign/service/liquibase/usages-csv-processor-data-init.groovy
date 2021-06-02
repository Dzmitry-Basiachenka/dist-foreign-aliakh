databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-04-03-00', author: 'Nikita Levyankov <aliakh@copyright.com>') {
        comment('B-41014 [ALL] Tech Debt, Refactoring, and Demo Feedback: introduce CsvProcessorFactory')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd7f890a5-1762-4213-9fd2-9599b2f34bcc')
            column(name: 'name', value: 'Test Batch')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3b2bb7f1-eb4a-46a4-9609-1fa3cb36ae20')
            column(name: 'df_usage_batch_uid', value: 'd7f890a5-1762-4213-9fd2-9599b2f34bcc')
            column(name: 'wr_wrk_inst', value: '123456789')
            column(name: 'product_family', value: 'FAS')
            column(name: 'work_title', value: '1984,Appendix: The Principles of Newspeak')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'standard_number', value: '9780150000000')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'gross_amount', value: 10000.00)
            column(name: 'net_amount', value: 8400.00)
            column(name: 'service_fee_amount', value: 1600.00)
            column(name: 'service_fee', value: 0.16000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3b2bb7f1-eb4a-46a4-9609-1fa3cb36ae20')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'Publisher')
            column(name: 'publication_date', value: '3000-12-22')
            column(name: 'market', value: 'Univ,Bus,Doc,S')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2016')
            column(name: 'author', value: 'Aarseth, Espen J')
            column(name: 'reported_value', value: '10000')
        }

        rollback ""
    }
}
