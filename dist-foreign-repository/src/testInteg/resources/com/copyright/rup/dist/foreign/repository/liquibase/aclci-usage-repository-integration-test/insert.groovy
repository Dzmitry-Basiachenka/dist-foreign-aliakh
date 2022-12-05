databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-12-08-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testInsert')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'fd137df2-7308-49a0-b72e-0ea6924249a9')
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '2d3f2f15-901e-4d7b-bb6c-4af542d3e504')
            column(name: 'name', value: 'ACLCI batch')
            column(name: 'product_family', value: 'ACLCI')
            column(name: 'payment_date', value: '2022-06-30')
        }

        rollback {
            dbRollback
        }
    }
}
