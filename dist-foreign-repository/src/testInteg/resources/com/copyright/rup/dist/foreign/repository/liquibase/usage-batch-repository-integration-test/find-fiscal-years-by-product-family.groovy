databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-02-09-08', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindFiscalYearsByProductFamily')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '760757c2-2031-4f5d-a007-df4ba2a87e40')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'updated_datetime', value: '2017-02-14 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '89cac5a1-3e38-4fcd-8f1c-4aa15d93ab5e')
            column(name: 'name', value: 'AccessCopyright_11Dec16')
            column(name: 'rro_account_number', value: 2000017004)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-08-16')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'updated_datetime', value: '2017-02-21 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '5f5a5b65-7b02-49da-95ba-e2a3f5d723c8')
            column(name: 'name', value: 'JAACC_11Dec16')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'updated_datetime', value: '2017-02-11 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9039c236-544b-483e-aa0e-3483553453ab')
            column(name: 'name', value: 'NEW_26_OCT_2017')
            column(name: 'rro_account_number', value: 7000813800)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-10-30')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'updated_datetime', value: '2017-10-26 14:49:52.735531+03')
        }

        rollback {
            dbRollback
        }
    }
}
