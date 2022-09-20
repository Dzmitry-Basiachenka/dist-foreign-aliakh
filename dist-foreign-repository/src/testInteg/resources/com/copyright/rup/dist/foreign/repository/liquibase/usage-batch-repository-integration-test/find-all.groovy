databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-02-09-01', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindAll')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '949a3b84-b559-459a-9de9-315447c6d1f7')
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
            column(name: 'df_usage_batch_uid', value: 'b74ec8a7-8c0d-437f-aa1f-77a32931e7c7')
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
            column(name: 'df_usage_batch_uid', value: 'e6210618-a5af-4557-81e5-5754e3aa2fba')
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
            column(name: 'df_usage_batch_uid', value: '2cd32412-dd17-4ab0-9b1b-296d48ad7db7')
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
