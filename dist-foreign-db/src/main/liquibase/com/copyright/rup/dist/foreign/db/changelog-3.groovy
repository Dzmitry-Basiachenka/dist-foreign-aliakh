databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-09-12-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment("B-45044 FDA: Process Post-Distribution Details: add column lm_detail_id to the table df_usage_archive")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'lm_detail_id', type: 'VARCHAR(255)', remarks: 'The identifier of LM detail')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'lm_detail_id')
        }
    }

    changeSet(id: '2018-09-13-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-45896 FDA: Change Product Name - FAS2 (replace CLA_FAS): replace CLA_FAS product family by FAS2 in batch summary report")

        createView(viewName: 'v_batch_summary_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
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
                            case when (u.status_ind != 'ELIGIBLE' and u.status_ind != 'LOCKED') then count(1) else 0 end as non_eligible_details_count,
                            case when (u.status_ind != 'ELIGIBLE' and u.status_ind != 'LOCKED') then sum(u.gross_amount) else 0 end as non_eligible_details_gross_amount,
                            case when (u.product_family = 'NTS') then count(1) else 0 end as  nts_details_count,
                            case when (u.product_family = 'NTS') then sum(u.gross_amount) else 0 end as nts_details_gross_amount,
                            case when (u.status_ind = 'ELIGIBLE' and (u.product_family = 'FAS' or u.product_family = 'FAS2')) then count(1) else 0 end as  fas_and_cla_fas_eligible_details_count,
                            case when (u.status_ind = 'ELIGIBLE' and (u.product_family = 'FAS' or u.product_family = 'FAS2')) then sum(u.gross_amount) else 0 end as  fas_and_cla_fas_eligible_details_gross_amount,
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

        rollback {

            createView(viewName: 'v_batch_summary_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
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
                            case when (u.status_ind != 'ELIGIBLE' and u.status_ind != 'LOCKED') then count(1) else 0 end as non_eligible_details_count,
                            case when (u.status_ind != 'ELIGIBLE' and u.status_ind != 'LOCKED') then sum(u.gross_amount) else 0 end as non_eligible_details_gross_amount,
                            case when (u.product_family = 'NTS') then count(1) else 0 end as  nts_details_count,
                            case when (u.product_family = 'NTS') then sum(u.gross_amount) else 0 end as nts_details_gross_amount,
                            case when (u.status_ind = 'ELIGIBLE' and (u.product_family = 'FAS' or u.product_family = 'CLA_FAS')) then count(1) else 0 end as  fas_and_cla_fas_eligible_details_count,
                            case when (u.status_ind = 'ELIGIBLE' and (u.product_family = 'FAS' or u.product_family = 'CLA_FAS')) then sum(u.gross_amount) else 0 end as  fas_and_cla_fas_eligible_details_gross_amount,
                            case when (u.status_ind = 'LOCKED') then count(1) else 0 end as scenarios_details_count,
                            case when (u.status_ind = 'LOCKED') then sum(u.gross_amount) else 0 end as scenarios_details_gross_amount,
                            case when (u.status_ind = 'LOCKED') then sum(u.net_amount) else 0 end as scenarios_details_net_amount,
                            case when (u.product_family = 'CLA_FAS' and u.payee_account_number = 2000017000) then count(1) else 0 end return_to_cla_details_count,
                            case when (u.product_family = 'CLA_FAS' and u.payee_account_number = 2000017000) then sum(u.gross_amount) else 0 end return_to_cla_details_gross_amount
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

    changeSet(id: '2018-09-13-01', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-45896 FDA: Change Product Name - FAS2 (replace CLA_FAS): update CLA_FAS product family to FAS2 in df_scenario_usage_filter table")

        update(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'product_family', value: 'FAS2')
            where "product_family = 'CLA_FAS'"
        }

        rollback {
            update(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
                column(name: 'product_family', value: 'CLA_FAS')
                where "product_family = 'FAS2'"
            }
        }
    }

    changeSet(id: '2018-09-13-02', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-45896 FDA: Change Product Name - FAS2 (replace CLA_FAS): update CLA_FAS product family to FAS2 in df_rightsholder_discrepancy table")

        update(schemaName: dbAppsSchema, tableName: 'df_rightsholder_discrepancy') {
            column(name: 'product_family', value: 'FAS2')
            where "product_family = 'CLA_FAS'"
        }

        rollback {
            update(schemaName: dbAppsSchema, tableName: 'df_rightsholder_discrepancy') {
                column(name: 'product_family', value: 'CLA_FAS')
                where "product_family = 'FAS2'"
            }
        }
    }

    changeSet(id: '2018-09-13-03', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-45896 FDA: Change Product Name - FAS2 (replace CLA_FAS): update CLA_FAS product family to FAS2 in df_usage_archive table")

        update(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'product_family', value: 'FAS2')
            where "product_family = 'CLA_FAS'"
        }

        rollback {
            update(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'product_family', value: 'CLA_FAS')
                where "product_family = 'FAS2'"
            }
        }
    }

    changeSet(id: '2018-09-13-04', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-45896 FDA: Change Product Name - FAS2 (replace CLA_FAS): update CLA_FAS product family to FAS2 in df_usage table")

        update(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'product_family', value: 'FAS2')
            where "product_family = 'CLA_FAS'"
        }

        rollback {
            update(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'product_family', value: 'CLA_FAS')
                where "product_family = 'FAS2'"
            }
        }
    }
}
