databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-12-04-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-55836 FDA: Database changes to support AACL Create database for FAS specific fields")

        createTable(tableName: 'df_usage_fas', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing specific fields of usages with FAS/FAS2/NTS product family') {

            column(name: 'df_usage_fas_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage')
            column(name: 'article', type: 'VARCHAR(1000)', remarks: 'The article')
            column(name: 'author', type: 'VARCHAR(1000)', remarks: 'The author')
            column(name: 'publisher', type: 'VARCHAR(1000)', remarks: 'The publisher')
            column(name: 'publication_date', type: 'DATE', remarks: 'The publication date')
            column(name: 'market', type: 'VARCHAR(200)', remarks: 'The market')
            column(name: 'market_period_from', type: 'NUMERIC(4,0)', remarks: 'The beginning period of when the usage occured')
            column(name: 'market_period_to', type: 'NUMERIC(4,0)', remarks: 'The ending period of when the usage occured')
            column(name: 'df_fund_pool_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool')
            column(name: 'is_rh_participating_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'RH participating flag')
            column(name: 'is_payee_participating_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Payee participating flag')
            column(name: 'reported_value', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The amount in original currency')
        }

        sql("""insert into ${dbAppsSchema}.df_usage_fas              
            select df_usage_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, is_payee_participating_flag, reported_value
            from ${dbAppsSchema}.df_usage where product_family='FAS'"""
        )

        sql("""insert into ${dbAppsSchema}.df_usage_fas                
            select df_usage_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, is_payee_participating_flag, reported_value
            from ${dbAppsSchema}.df_usage where product_family='FAS2'"""
        )

        sql("""insert into ${dbAppsSchema}.df_usage_fas                
            select df_usage_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, is_payee_participating_flag, reported_value
            from ${dbAppsSchema}.df_usage where product_family='NTS'"""
        )

        rollback {
            dropTable(tableName: 'df_usage_fas', schemaName: dbAppsSchema)
        }
    }
}
