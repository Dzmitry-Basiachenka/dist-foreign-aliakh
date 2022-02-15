databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-02-09-06', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindByProductFamily')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9f7342a7-014a-42a9-b02f-3a31fbb71088')
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
            column(name: 'df_usage_batch_uid', value: '14d39224-ec9e-4d89-b3ed-82a3ddf01d46')
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
            column(name: 'df_usage_batch_uid', value: 'fe8103c0-91a2-4502-b33c-dbdc93f99dcf')
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
            column(name: 'df_usage_batch_uid', value: '59209cce-6334-4056-ad37-7ec6fe4f001a')
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
