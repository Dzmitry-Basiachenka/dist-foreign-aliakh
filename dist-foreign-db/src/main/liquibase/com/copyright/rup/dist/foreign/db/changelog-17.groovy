databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-12-28-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment("B-68577 FDA: Load ACLCI fund pool: add aclci_fields column to df_fund_pool")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'aclci_fields', type: 'JSONB', remarks: 'The fields of ACLCI fund pool')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_fund_pool', columnName: 'aclci_fields')
        }
    }

    changeSet(id: '2023-03-01-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment("B-76925 FDA: Deleted works column for editing wrWrkInsts: add work_deleted_flag column to df_acl_usage table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'work_deleted_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Work was soft delete from MDWMS') {
                constraints(nullable: false)
            }
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_usage', columnName: 'work_deleted_flag')
        }
    }

    changeSet(id: '2023-03-07-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("B-77080 FDA: Show Reported Standard Number and Reported Title on UI & Exports: add reported_standard_number column to df_usage_fas table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'reported_standard_number', type: 'VARCHAR(1000)', remarks: 'The reported standard number')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_fas', columnName: 'reported_standard_number')
        }
    }

    changeSet(id: '2023-04-19-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-78666 FDA: Add columns to NTS WD Batch Summary Report: add columns to view v_nts_withdrawn_batch_summary_report")

        dropView(viewName: 'v_nts_withdrawn_batch_summary_report', schemaName: dbAppsSchema)

        createView(viewName: 'v_nts_withdrawn_batch_summary_report', schemaName: dbAppsSchema) {
            """select
                rro.name rro_name,
                b.rro_account_number,
                b.name as batch_name,
                b.payment_date,
                b.gross_amount,
                sum(case when u.status_ind = 'NTS_WITHDRAWN' then 1 else 0 end) as nts_details_count,
                sum(case when u.status_ind = 'NTS_WITHDRAWN' then u.gross_amount else 0 end) as nts_details_gross_amount,
                sum(case when u.status_ind = 'TO_BE_DISTRIBUTED' then 1 else 0 end) as to_be_distributed_details_count,
                sum(case when u.status_ind = 'TO_BE_DISTRIBUTED' then u.gross_amount else 0 end) as to_be_distributed_details_gross_amount,
                b.created_datetime
            from ${dbAppsSchema}.df_usage u
            join ${dbAppsSchema}.df_usage_batch b on b.df_usage_batch_uid = u.df_usage_batch_uid
            left join ${dbAppsSchema}.df_rightsholder rro on rro.rh_account_number = b.rro_account_number
            where u.status_ind in ('NTS_WITHDRAWN', 'TO_BE_DISTRIBUTED')
            group by rro.name, b.rro_account_number, b.name, b.payment_date, b.gross_amount, b.created_datetime"""
        }

        rollback {
            dropView(viewName: 'v_nts_withdrawn_batch_summary_report', schemaName: dbAppsSchema)

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
                from (
                    select
                        nested_report.rro_account_number,
                        nested_report.batch_name as batch_name,
                        nested_report.payment_date,
                        nested_report.gross_amount,
                        sum(nested_report.nts_details_count) nts_details_count,
                        sum(nested_report.nts_details_gross_amount) nts_details_gross_amount,
                        nested_report.created_datetime
                    from (
                        select
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
                        group by b.rro_account_number, b.name, b.payment_date, b.gross_amount, u.product_family, b.created_datetime
                    ) as nested_report
                    group by nested_report.rro_account_number, nested_report.batch_name, nested_report.payment_date, nested_report.gross_amount, nested_report.created_datetime
                ) as report
                left join ${dbAppsSchema}.df_rightsholder rro on rro.rh_account_number = report.rro_account_number"""
            }
        }
    }
}
