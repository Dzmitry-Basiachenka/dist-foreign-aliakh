databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-02-09-10', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testFindSalLicensees')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '623b992d-0a10-4d20-bec5-92faab5af032')
            column(name: 'name', value: 'SAL Batch With New and RH Not Found usages')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2015)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 5588, "licensee_name": "RGS Energy Group"}')
            column(name: 'updated_datetime', value: '2017-01-10 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9b34d1da-8da5-4e79-9c6a-9de2192df4ad')
            column(name: 'name', value: 'SAL usage batch attached to a scenario')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2015)
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value: '{"licensee_account_number": 7001293454, "licensee_name": "Synergy Publishers"}')
            column(name: 'updated_datetime', value: '2017-01-09 11:41:52.735531+03')
        }

        rollback {
            dbRollback
        }
    }
}
