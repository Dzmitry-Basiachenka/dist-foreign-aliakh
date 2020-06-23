databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-06-17-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-58560 FDA: View/Export NTS withdrawn details on FAS/FAS2 usage tab: change product family " +
                "from NTS to FAS/FAS2 for details with NTS_WITHDRAWN and TO_BE_DISTRIBUTED statuses")

        sql("""update ${dbAppsSchema}.df_usage u set product_family = b.product_family
                from ${dbAppsSchema}.df_usage_batch b
                where u.df_usage_batch_uid = b.df_usage_batch_uid
                and u.status_ind in ('NTS_WITHDRAWN', 'TO_BE_DISTRIBUTED')
                """)

        rollback {
            sql("""update ${dbAppsSchema}.df_usage u set product_family = 'NTS'
                where u.status_ind in ('NTS_WITHDRAWN', 'TO_BE_DISTRIBUTED')
                """)
        }
    }

    changeSet(id: '2020-06-19-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-58560 FDA: View/Export NTS withdrawn details on FAS/FAS2 usage tab: change product family " +
                "from NTS to FAS/FAS2 for details with NTS_WITHDRAWN and TO_BE_DISTRIBUTED statuses for archived details")

        sql("""update ${dbAppsSchema}.df_usage_archive ua set product_family = b.product_family
                from ${dbAppsSchema}.df_usage_batch b, ${dbAppsSchema}.df_usage_fas uf
                where ua.df_usage_batch_uid = b.df_usage_batch_uid
                    and ua.df_usage_archive_uid = uf.df_usage_fas_uid
                    and uf.df_fund_pool_uid is not null
                """)

        rollback {
            sql("""update ${dbAppsSchema}.df_usage_archive ua set product_family = 'NTS'
                from ${dbAppsSchema}.df_usage_fas uf
                where uf.df_usage_fas_uid = ua.df_usage_archive_uid
                    and uf.df_fund_pool_uid is not null
                """)
        }
    }

    changeSet(id: '2020-06-22-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-54104 FDA: Add Date Loaded column to the Batch Summary Report")

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
                        case when (u.status_ind in ('NTS_WITHDRAWN', 'TO_BE_DISTRIBUTED')) then count(1) else 0 end as nts_details_count,
                        case when (u.status_ind in ('NTS_WITHDRAWN', 'TO_BE_DISTRIBUTED')) then sum(u.gross_amount) else 0 end as nts_details_gross_amount,
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
        }
    }

    changeSet(id: '2020-06-23-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-59772 FDA: Improve RMS integration: add and populate license_type column in the df_grant_priority table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', type: 'VARCHAR(128)', remarks: 'The license type')
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_PHOTOCOPY')
            where "type_of_use = 'NGT_PHOTOCOPY' and grant_product_family = 'TRS' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'ACL')
            where "type_of_use = 'PRINT' and grant_product_family = 'ACL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'JACDCL')
            where "type_of_use = 'PRINT' and grant_product_family = 'JACDCL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'MACL')
            where "type_of_use = 'PRINT' and grant_product_family = 'MACL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'VGW')
            where "type_of_use = 'PRINT' and grant_product_family = 'VGW' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'ACL')
            where "type_of_use = 'DIGITAL' and grant_product_family = 'ACL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'JACDCL')
            where "type_of_use = 'DIGITAL' and grant_product_family = 'JACDCL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'MACL')
            where "type_of_use = 'DIGITAL' and grant_product_family = 'MACL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'VGW')
            where "type_of_use = 'DIGITAL' and grant_product_family = 'VGW' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'AACL')
            where "type_of_use = 'PRINT' and grant_product_family = 'AACL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'AACL')
            where "type_of_use = 'DIGITAL' and grant_product_family = 'AACL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_PRINT_COURSE_MATERIALS')
            where "type_of_use = 'NGT_PRINT_COURSE_MATERIALS' and grant_product_family = 'NGT_PRINT_COURSE_MATERIALS' " +
                    "and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            where "type_of_use = 'NGT_ELECTRONIC_COURSE_MATERIALS' and grant_product_family = 'NGT_ELECTRONIC_COURSE_MATERIALS' " +
                    "and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_grant_priority', columnName: 'license_type', columnDataType: 'VARCHAR(128)')

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'bedcc0e3-ce44-4ef6-a8fc-d89f82e6d1d4')
            column(name: 'product_family', value: 'AACL')
            column(name: 'grant_product_family', value: 'AACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '0')
            column(name: 'license_type', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '97f1ad37-7358-435b-b390-3913d8b1cb72')
            column(name: 'product_family', value: 'AACL')
            column(name: 'grant_product_family', value: 'AACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '1')
            column(name: 'license_type', value: 'AACL')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_grant_priority', columnName: 'license_type')

            delete(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                where "df_grant_priority_uid in ('bedcc0e3-ce44-4ef6-a8fc-d89f82e6d1d4','97f1ad37-7358-435b-b390-3913d8b1cb72')"
            }
        }
    }

    changeSet(id: '2020-06-23-01', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("CDP-921: FDA: FAS Batch Summary Report: TO_BE_DISTRIBUTED usages are displayed in the " +
                "'# non-Eligible Details' and 'Gross USD non-Eligible Details' columns of the report")

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
                        case when (u.status_ind not in ('ELIGIBLE','LOCKED','NTS_WITHDRAWN', 'TO_BE_DISTRIBUTED')) then count(1) else 0 end as non_eligible_details_count,
                        case when (u.status_ind not in ('ELIGIBLE','LOCKED','NTS_WITHDRAWN', 'TO_BE_DISTRIBUTED')) then sum(u.gross_amount) else 0 end as non_eligible_details_gross_amount,
                        case when (u.status_ind in ('NTS_WITHDRAWN', 'TO_BE_DISTRIBUTED')) then count(1) else 0 end as nts_details_count,
                        case when (u.status_ind in ('NTS_WITHDRAWN', 'TO_BE_DISTRIBUTED')) then sum(u.gross_amount) else 0 end as nts_details_gross_amount,
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
                        case when (u.status_ind in ('NTS_WITHDRAWN', 'TO_BE_DISTRIBUTED')) then count(1) else 0 end as nts_details_count,
                        case when (u.status_ind in ('NTS_WITHDRAWN', 'TO_BE_DISTRIBUTED')) then sum(u.gross_amount) else 0 end as nts_details_gross_amount,
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
        }
    }
}
