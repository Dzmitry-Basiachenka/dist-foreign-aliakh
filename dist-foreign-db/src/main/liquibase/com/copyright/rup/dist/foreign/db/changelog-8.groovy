databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-10-30-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-53752 FDA: Create NTS withdrawn batch summary report: create DB view for generating batch summary report")

        createView(viewName: 'v_nts_withdrawn_batch_summary_report', schemaName: dbAppsSchema) {
            """select
                rro.name rro_name,
                rro.rh_account_number rro_account_number,
                report.batch_name,
                report.payment_date,
                report.gross_amount,
                report.nts_details_count,
                report.nts_details_gross_amount,
                report.created_datetime
            from 
                (select
                    nested_report.rro_account_number,
                    nested_report.batch_name as batch_name,
                    nested_report.payment_date,
                    nested_report.gross_amount,
                    sum(nested_report.nts_details_count) nts_details_count,
                    sum(nested_report.nts_details_gross_amount) nts_details_gross_amount,
                    nested_report.created_datetime
                from
                    (select 
                        b.rro_account_number,
                        b.name as batch_name,
                        b.payment_date,
                        b.gross_amount,
                        count(1) as nts_details_count,
                        sum(u.gross_amount) as nts_details_gross_amount,
                        b.created_datetime
                    from ${dbAppsSchema}.df_usage u
                    join ${dbAppsSchema}.df_usage_batch b on b.df_usage_batch_uid = u.df_usage_batch_uid
                    where u.status_ind = 'NTS_WITHDRAWN'
                    group by b.rro_account_number, b.name, b.payment_date, b.gross_amount, u.product_family, b.created_datetime) as nested_report
                group by nested_report.rro_account_number, nested_report.batch_name, nested_report.payment_date, nested_report.gross_amount, nested_report.created_datetime) as report
            left join ${dbAppsSchema}.df_rightsholder rro on rro.rh_account_number = report.rro_account_number"""
        }

        rollback {
            // automatic rollback
        }
    }

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

        sql("""insert into ${dbAppsSchema}.df_usage_fas
            (df_usage_fas_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, reported_value)              
            select df_usage_archive_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, reported_value
            from ${dbAppsSchema}.df_usage_archive where product_family='FAS'"""
        )

        sql("""insert into ${dbAppsSchema}.df_usage_fas
            (df_usage_fas_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, reported_value)                
            select df_usage_archive_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, reported_value
            from ${dbAppsSchema}.df_usage_archive where product_family='FAS2'"""
        )

        sql("""insert into ${dbAppsSchema}.df_usage_fas
            (df_usage_fas_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, reported_value)                
            select df_usage_archive_uid, article, author, publisher, publication_date, market, market_period_from, market_period_to, df_fund_pool_uid,
            is_rh_participating_flag, reported_value
            from ${dbAppsSchema}.df_usage_archive where product_family='NTS'"""
        )

        rollback {
            dropTable(tableName: 'df_usage_fas', schemaName: dbAppsSchema)
        }
    }
}
