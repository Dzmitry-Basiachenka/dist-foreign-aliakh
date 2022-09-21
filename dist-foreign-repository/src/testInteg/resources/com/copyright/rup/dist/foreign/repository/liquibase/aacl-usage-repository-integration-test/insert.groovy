databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-11-09-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testInsert')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'fd137df2-7308-49a0-b72e-0ea6924249a9')
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6e6f656a-e080-4426-b8ea-985b69f8814d')
            column(name: 'name', value: 'AACL batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 2)
        }

        rollback {
            dbRollback
        }
    }
}
