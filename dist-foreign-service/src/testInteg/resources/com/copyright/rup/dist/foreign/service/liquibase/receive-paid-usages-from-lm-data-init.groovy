databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-02-22-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data to receive paid usages from LM')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5b9ab240-17e3-11e8-b566-0800200c9a66')
            column(name: 'name', value: 'Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '64a6ace0-17e3-11e8-b566-0800200c9a66')
            column(name: 'name', value: 'Paid batch')
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: '2020')
            column(name: 'gross_amount', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '6a833980-17e3-11e8-b566-0800200c9a66')
            column(name: 'df_usage_batch_uid', value: '64a6ace0-17e3-11e8-b566-0800200c9a66')
            column(name: 'df_scenario_uid', value: '5b9ab240-17e3-11e8-b566-0800200c9a66')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '6000')
            column(name: 'gross_amount', value: '1000.00')
            column(name: 'net_amount', value: '160.00')
            column(name: 'service_fee_amount', value: '840.00')
            column(name: 'service_fee', value: '0.16000')
        }

        rollback ""
    }
}
