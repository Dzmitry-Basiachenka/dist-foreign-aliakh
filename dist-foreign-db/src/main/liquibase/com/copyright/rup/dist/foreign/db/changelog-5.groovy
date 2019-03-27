databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-03-06-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-46040 FDA: Assign title classification to titles in NTS distribution: create df_work_classification table")

        createTable(tableName: 'df_work_classification', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'The table to store works classifications') {

            column(name: 'df_work_classification_uid', type: 'VARCHAR(255)', remarks: 'The identifier of work classification') {
                constraints(nullable: false)
            }
            column(name: 'wr_wrk_inst', type: 'NUMERIC(15,0)', remarks: 'Wr Wrk Inst') {
                constraints(nullable: false)
                constraints(unique: true)
            }
            column(name: 'classification', type: 'VARCHAR(128)', remarks: 'Work classification') {
                constraints(nullable: false)
            }
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1', remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM', remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created') {
                constraints(nullable: false)
            }
            column(name: 'updated_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM',
                    remarks: 'The user name who updated this record; when a record is first created, this will be the same as the created_by_user') {
                constraints(nullable: false)
            }
            column(name: 'updated_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()',
                    remarks: 'The date and time this record was created; when a record is first created, this will be the same as the created_datetime') {
                constraints(nullable: false)
            }
        }

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2019-03-11-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('B-49017 FDA: Address the number of buttons on the Usage tab: ' +
                'add product_family column into df_usage_batch table and populate historical data')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'product_family', type: 'VARCHAR(128)', remarks: 'Product Family')
        }

        update(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'product_family', value: 'NTS')
            where "fund_pool is not null"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'product_family', value: 'FAS2')
            where "fund_pool is null and rro_account_number = 2000017000"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'product_family', value: 'FAS')
            where "fund_pool is null and rro_account_number != 2000017000 and product_family is null"
        }

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch',
                columnName: 'product_family', columnDataType: 'VARCHAR(128)')
    
        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch', columnName: 'product_family')
        }
    }

    changeSet(id: '2019-03-22-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-46040 FDA: Assign title classification to titles in NTS distribution: " +
                "add index by wr_wrk_inst in df_usage_archive table")

        createIndex(indexName: 'ix_df_usage_archive_wr_wrk_inst', schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                tablespace: dbIndexTablespace) {
            column(name: 'wr_wrk_inst')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_usage_archive_wr_wrk_inst")
        }
    }

    changeSet(id: '2019-03-27-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: " +
                "Implement Liqubase script to create table df_fund_pool")

        createTable(tableName: 'df_fund_pool', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'The table to store NTS withdrawn fund pool') {

            column(name: 'df_fund_pool_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(255)', remarks: 'The name of fund pool') {
                constraints(nullable: false)
                constraints(unique: true)
            }
            column(name: 'comment', type: 'VARCHAR(2000)', remarks: 'The comment of fund pool') {
                constraints(nullable: true)
            }
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1', remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM', remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created') {
                constraints(nullable: false)
            }
            column(name: 'updated_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM',
                    remarks: 'The user name who updated this record; when a record is first created, this will be the same as the created_by_user') {
                constraints(nullable: false)
            }
            column(name: 'updated_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()',
                    remarks: 'The date and time this record was created; when a record is first created, this will be the same as the created_datetime') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_fund_pool', tablespace: dbIndexTablespace,
                columnNames: 'df_fund_pool_uid', constraintName: 'df_fund_pool_pk')

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2019-03-27-01', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('B-50004 FDA: Add Standard Number Type to the usage for classification: ' +
                'Add standard_number_type column to df_usage and df_usage_archive tables')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'standard_number_type', type: 'VARCHAR(50)', remarks: 'Standard Number Type')
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'standard_number_type', type: 'VARCHAR(50)', remarks: 'Standard Number Type')
        }

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2019-03-27-02', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment("B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: " +
                "increase size of column status_ind of tables df_usage and df_usage_archive")

        def vResearchStatusReportDefinition =
                """select
                nestedReport.batch_name, 
                nestedReport.rro_account_number,
                rh.name rro_name,
                nestedReport.payment_date, 
                nestedReport.gross_amount,
                sum(work_not_found_count) work_not_found_details_count,
                sum(nestedReport.work_not_found) work_not_found_gross_amount,
                sum(researched_count) work_research_details_count,
                sum(nestedReport.researched) work_research_gross_amount,
                sum(sent_for_ra_count) send_for_ra_details_count,
                sum(nestedReport.sent_for_ra) send_for_ra_gross_amount,
                sum(rh_not_found_count) rh_not_found_details_count,
                sum(nestedReport.rh_not_found) rh_not_found_gross_amount
            from(
                select
                    b.name batch_name, 
                    b.rro_account_number,
                    b.payment_date, 
                    b.gross_amount,     
                    case when (u.status_ind = 'WORK_NOT_FOUND') then count(1) else 0 end work_not_found_count,
                    case when (u.status_ind = 'WORK_NOT_FOUND') then sum(u.gross_amount) else 0 end work_not_found,
                    case when (u.status_ind = 'WORK_RESEARCH') then count(1) else 0 end researched_count,
                    case when (u.status_ind = 'WORK_RESEARCH') then sum(u.gross_amount) else 0 end researched,
                    case when (u.status_ind = 'SENT_FOR_RA') then count(1) else 0 end sent_for_ra_count,
                    case when (u.status_ind = 'SENT_FOR_RA') then sum(u.gross_amount) else 0 end sent_for_ra,
                    case when (u.status_ind = 'RH_NOT_FOUND') then count(1) else 0 end rh_not_found_count,
                    case when (u.status_ind = 'RH_NOT_FOUND') then sum(u.gross_amount) else 0 end rh_not_found     
                from ${dbAppsSchema}.df_usage u
                left join ${dbAppsSchema}.df_usage_batch b on u.df_usage_batch_uid = b.df_usage_batch_uid 
                where status_ind in ('WORK_NOT_FOUND', 'WORK_RESEARCH', 'SENT_FOR_RA','RH_NOT_FOUND')
                group by b.name, rro_account_number, payment_date, b.gross_amount, u.status_ind) nestedReport
            left join ${dbAppsSchema}.df_rightsholder rh on nestedReport.rro_account_number = rh.rh_account_number  
            group by nestedReport.batch_name, nestedReport.rro_account_number, rh.name, nestedReport.payment_date, nestedReport.gross_amount
            order by nestedReport.payment_date, nestedReport.batch_name"""

        def vBatchSummaryReportDefinition =
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

        dropView(viewName: 'v_research_status_report', schemaName: dbAppsSchema)
        dropView(viewName: 'v_batch_summary_report', schemaName: dbAppsSchema)

        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'status_ind', newDataType: 'varchar(32)')
        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'status_ind', newDataType: 'varchar(32)')

        createView(viewName: 'v_research_status_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
            vResearchStatusReportDefinition
        }

        createView(viewName: 'v_batch_summary_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
            vBatchSummaryReportDefinition
        }

        rollback {

            dropView(viewName: 'v_research_status_report', schemaName: dbAppsSchema)
            dropView(viewName: 'v_batch_summary_report', schemaName: dbAppsSchema)

            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'status_ind', newDataType: 'varchar(16)')
            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'status_ind', newDataType: 'varchar(16)')

            createView(viewName: 'v_research_status_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
                vResearchStatusReportDefinition
            }

            createView(viewName: 'v_batch_summary_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
                vBatchSummaryReportDefinition
            }
        }
    }
}
