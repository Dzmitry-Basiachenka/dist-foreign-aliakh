databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-10-30-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-53752 FDA: Create NTS withdrawn batch summary report: Create DB view for generating batch summary report")

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
}
