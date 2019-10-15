databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-10-09-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-50006 FDA: Exclude details from FAS scenario at the Payee level: add is_payee_participating_flag column to df_usage table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'is_payee_participating_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Payee participating flag') {
                constraints(nullable: false)
            }
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'is_payee_participating_flag')
        }
    }

    changeSet(id: '2019-10-14-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-54104 FDA: Add Date Loaded column to the Batch Summary Report")

        createView(viewName: 'v_fas_batch_summary_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
            """select
                rro.name rro_name,
                rro.rh_account_number rro_account_number,
                report.batch_name,
                report.payment_date,
                report.gross_amount,
                report.non_eligible_details_count,
                report.non_eligible_details_gross_amount,
                report.nts_details_count,
                report.nts_details_gross_amount,
                report.fas_and_cla_fas_eligible_details_count,
                report.fas_and_cla_fas_eligible_details_gross_amount,
                report.scenarios_details_count,
                report.scenarios_details_gross_amount,
                report.scenarios_details_net_amount,
                report.return_to_cla_details_count,
                report.return_to_cla_details_gross_amount,
                report.created_datetime
            from 
                (select
                    nested_report.rro_account_number,
                    nested_report.batch_name as batch_name,
                    nested_report.payment_date,
                    nested_report.gross_amount,
                    sum(nested_report.non_eligible_details_count) non_eligible_details_count,
                    sum(nested_report.non_eligible_details_gross_amount) non_eligible_details_gross_amount,
                    sum(nested_report.nts_details_count) nts_details_count,
                    sum(nested_report.nts_details_gross_amount) nts_details_gross_amount,
                    sum(nested_report.fas_and_cla_fas_eligible_details_count) fas_and_cla_fas_eligible_details_count,
                    sum(nested_report.fas_and_cla_fas_eligible_details_gross_amount) fas_and_cla_fas_eligible_details_gross_amount,
                    sum(nested_report.scenarios_details_count) scenarios_details_count,
                    sum(nested_report.scenarios_details_gross_amount) scenarios_details_gross_amount,
                    sum(nested_report.scenarios_details_net_amount) scenarios_details_net_amount,
                    sum(nested_report.return_to_cla_details_count) return_to_cla_details_count,
                    sum(nested_report.return_to_cla_details_gross_amount) return_to_cla_details_gross_amount,
                    nested_report.created_datetime
                from
                    (select 
                        b.rro_account_number,
                        b.name as batch_name,
                        b.payment_date,
                        b.gross_amount,
                        case when (u.status_ind not in ('ELIGIBLE','LOCKED','NTS_WITHDRAWN')) then count(1) else 0 end as non_eligible_details_count,
                        case when (u.status_ind not in ('ELIGIBLE','LOCKED','NTS_WITHDRAWN')) then sum(u.gross_amount) else 0 end as non_eligible_details_gross_amount,
                        case when (u.product_family = 'NTS') then count(1) else 0 end as nts_details_count,
                        case when (u.product_family = 'NTS') then sum(u.gross_amount) else 0 end as nts_details_gross_amount,
                        case when (u.status_ind = 'ELIGIBLE') then count(1) else 0 end as fas_and_cla_fas_eligible_details_count,
                        case when (u.status_ind = 'ELIGIBLE') then sum(u.gross_amount) else 0 end as fas_and_cla_fas_eligible_details_gross_amount,
                        case when (u.status_ind = 'LOCKED') then count(1) else 0 end as scenarios_details_count,
                        case when (u.status_ind = 'LOCKED') then sum(u.gross_amount) else 0 end as scenarios_details_gross_amount,
                        case when (u.status_ind = 'LOCKED') then sum(u.net_amount) else 0 end as scenarios_details_net_amount,
                        case when (u.product_family = 'FAS2' and u.payee_account_number = 2000017000) then count(1) else 0 end return_to_cla_details_count,
                        case when (u.product_family = 'FAS2' and u.payee_account_number = 2000017000) then sum(u.gross_amount) else 0 end return_to_cla_details_gross_amount,
                        b.created_datetime
                    from ${dbAppsSchema}.df_usage u
                    join ${dbAppsSchema}.df_usage_batch b on b.df_usage_batch_uid = u.df_usage_batch_uid
                    where b.product_family in ('FAS','FAS2') 
                    group by b.rro_account_number, b.name, b.payment_date, b.gross_amount, u.status_ind, u.product_family, u.payee_account_number, b.created_datetime) as nested_report
                group by nested_report.rro_account_number, nested_report.batch_name, nested_report.payment_date, nested_report.gross_amount, nested_report.created_datetime) as report
            left join ${dbAppsSchema}.df_rightsholder rro on rro.rh_account_number = report.rro_account_number
            where non_eligible_details_count != 0 or fas_and_cla_fas_eligible_details_count != 0 or scenarios_details_count != 0
            order by report.payment_date, report.batch_name"""
        }

        rollback {

            dropView(viewName: 'v_fas_batch_summary_report', schemaName: dbAppsSchema)

            createView(viewName: 'v_fas_batch_summary_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
                """select
                    rro.name rro_name,
                    rro.rh_account_number rro_account_number,
                    report.batch_name,
                    report.payment_date,
                    report.gross_amount,
                    report.non_eligible_details_count,
                    report.non_eligible_details_gross_amount,
                    report.nts_details_count,
                    report.nts_details_gross_amount,
                    report.fas_and_cla_fas_eligible_details_count,
                    report.fas_and_cla_fas_eligible_details_gross_amount,
                    report.scenarios_details_count,
                    report.scenarios_details_gross_amount,
                    report.scenarios_details_net_amount,
                    report.return_to_cla_details_count,
                    report.return_to_cla_details_gross_amount
                from 
                    (select
                        nested_report.rro_account_number,
                        nested_report.batch_name as batch_name,
                        nested_report.payment_date,
                        nested_report.gross_amount,
                        sum(nested_report.non_eligible_details_count) non_eligible_details_count,
                        sum(nested_report.non_eligible_details_gross_amount) non_eligible_details_gross_amount,
                        sum(nested_report.nts_details_count) nts_details_count,
                        sum(nested_report.nts_details_gross_amount) nts_details_gross_amount,
                        sum(nested_report.fas_and_cla_fas_eligible_details_count) fas_and_cla_fas_eligible_details_count,
                        sum(nested_report.fas_and_cla_fas_eligible_details_gross_amount) fas_and_cla_fas_eligible_details_gross_amount,
                        sum(nested_report.scenarios_details_count) scenarios_details_count,
                        sum(nested_report.scenarios_details_gross_amount) scenarios_details_gross_amount,
                        sum(nested_report.scenarios_details_net_amount) scenarios_details_net_amount,
                        sum(nested_report.return_to_cla_details_count) return_to_cla_details_count,
                        sum(nested_report.return_to_cla_details_gross_amount) return_to_cla_details_gross_amount
                    from
                        (select 
                            b.rro_account_number,
                            b.name as batch_name,
                            b.payment_date,
                            b.gross_amount,
                            case when (u.status_ind not in ('ELIGIBLE','LOCKED','NTS_WITHDRAWN')) then count(1) else 0 end as non_eligible_details_count,
                            case when (u.status_ind not in ('ELIGIBLE','LOCKED','NTS_WITHDRAWN')) then sum(u.gross_amount) else 0 end as non_eligible_details_gross_amount,
                            case when (u.product_family = 'NTS') then count(1) else 0 end as nts_details_count,
                            case when (u.product_family = 'NTS') then sum(u.gross_amount) else 0 end as nts_details_gross_amount,
                            case when (u.status_ind = 'ELIGIBLE' and (u.product_family = 'FAS' or u.product_family = 'FAS2')) then count(1) else 0 end as fas_and_cla_fas_eligible_details_count,
                            case when (u.status_ind = 'ELIGIBLE' and (u.product_family = 'FAS' or u.product_family = 'FAS2')) then sum(u.gross_amount) else 0 end as fas_and_cla_fas_eligible_details_gross_amount,
                            case when (u.status_ind = 'LOCKED') then count(1) else 0 end as scenarios_details_count,
                            case when (u.status_ind = 'LOCKED') then sum(u.gross_amount) else 0 end as scenarios_details_gross_amount,
                            case when (u.status_ind = 'LOCKED') then sum(u.net_amount) else 0 end as scenarios_details_net_amount,
                            case when (u.product_family = 'FAS2' and u.payee_account_number = 2000017000) then count(1) else 0 end return_to_cla_details_count,
                            case when (u.product_family = 'FAS2' and u.payee_account_number = 2000017000) then sum(u.gross_amount) else 0 end return_to_cla_details_gross_amount
                        from ${dbAppsSchema}.df_usage u
                        join ${dbAppsSchema}.df_usage_batch b on b.df_usage_batch_uid = u.df_usage_batch_uid
                        group by b.rro_account_number, b.name, b.payment_date, b.gross_amount, u.status_ind, u.product_family, u.payee_account_number) as nested_report
                    group by nested_report.rro_account_number, nested_report.batch_name, nested_report.payment_date, nested_report.gross_amount) as report
                left join ${dbAppsSchema}.df_rightsholder rro on rro.rh_account_number = report.rro_account_number
                where non_eligible_details_count != 0 or fas_and_cla_fas_eligible_details_count != 0 or scenarios_details_count != 0
                order by report.payment_date, report.batch_name"""
            }
        }
    }
}
