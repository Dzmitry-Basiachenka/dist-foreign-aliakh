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

    changeSet(id: '2019-03-28-00', author: 'Ihar Suvorau<isuvorau@copyright.com>') {
        comment('B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details:' +
                'add df_fund_pool_uid column with foreign key to df_usage, df_usage_archive tables')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_fund_pool_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool')
        }

        addForeignKeyConstraint(constraintName: 'fk_df_usage_2_df_fund_pool',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_usage',
                baseColumnNames: 'df_fund_pool_uid',
                referencedTableName: 'df_fund_pool',
                referencedColumnNames: 'df_fund_pool_uid')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_fund_pool_uid', type: 'VARCHAR(255)', remarks: 'The identifier of fund pool')
        }

        addForeignKeyConstraint(constraintName: 'fk_df_usage_archive_2_df_fund_pool',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_usage_archive',
                baseColumnNames: 'df_fund_pool_uid',
                referencedTableName: 'df_fund_pool',
                referencedColumnNames: 'df_fund_pool_uid')

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2019-04-03-00', author: 'Ihar Suvorau<isuvorau@copyright.com>') {
        comment('B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: ' +
                'add withdrawn_amount column to df_fund_pool table')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'withdrawn_amount', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'NTS withdrawn amount of fund pool') {
                constraints(nullable: false)
            }
        }

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2019-04-05-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: " +
                "rename primary key of the table df_fund_pool to follow database code standards")

        sql("alter table ${dbAppsSchema}.df_fund_pool rename constraint df_fund_pool_pk to pk_df_fund_pool")

        rollback {
            sql("alter table ${dbAppsSchema}.df_fund_pool rename constraint pk_df_fund_pool to df_fund_pool_pk")
        }
    }

    changeSet(id: '2019-04-08-00', author: 'Uladzislau Shalamitski<ushalamitski@copyright.com>') {
        comment('B-49463 FDA: Tech Debt: remove records from df_rro_estimated_service_fee_percentage with 18.5% estimated_service_fee')

        delete(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            where "estimated_service_fee = 0.18500"
        }

        rollback {
            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '2000017005')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '2000128767')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '7001438813')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '7000676925')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '2000045828')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '7000582241')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '2000017003')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '7000478504')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '7001498587')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '7000800336')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '2000046269')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '2000017002')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '7000340130')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '7000849816')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '2000073833')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '7001298418')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '2000072783')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '7001726973')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '2000017007')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '7000896777')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '2000017001')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '2000108983')
                column(name: 'estimated_service_fee', value: '0.18500')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
                column(name: 'rro_account_number', value: '7000813806')
                column(name: 'estimated_service_fee', value: '0.18500')
            }
        }
    }

    changeSet(id: '2019-04-17-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-49019 FDA: Create NTS Scenario: add nts_fields column to the table df_scenario")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'nts_fields', type: 'JSONB', remarks: 'The fields of NTS scenarios')
        }

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2019-04-24-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("CDP-728 FDA: Grants prioritiy: replace ACLPRINT and ACLDIGITAL product families with ACL, JACDCL, MACL, VGW")

        sql("delete from ${dbAppsSchema}.df_grant_priority")

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'd9309c58-50bd-4ab9-9c12-bcefd752ce43')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'TRS')
            column(name: 'type_of_use', value: 'NGT_PHOTOCOPY')
            column(name: 'priority', value: '0')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'e3389fff-1cdc-44f5-9bb2-ccd195eced26')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'TRS')
            column(name: 'type_of_use', value: 'NGT_PHOTOCOPY')
            column(name: 'priority', value: '0')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '8003d592-9c63-489f-bda3-72be51ce3ad6')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'TRS')
            column(name: 'type_of_use', value: 'NGT_PHOTOCOPY')
            column(name: 'priority', value: '0')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '1174d1d0-f3bb-4e80-8b92-08ab97457a97')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '8332cdb1-9b61-4c57-9c4b-d6acd6a0d07a')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '63311aac-ffa0-456b-ae6e-32f8c3fed08f')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '37e18a20-27d0-4185-b662-01c0448d6814')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'JACDCL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '8cfc0428-984d-44f0-93ba-ee0b968b1b7b')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'JACDCL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'de705039-7fe2-4b17-b0b2-29df7d543ebf')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'JACDCL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'a2c5004e-7b30-45b1-ae35-0ba81691e327')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'MACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '1e2bbab1-1b91-4deb-b482-f83a5e7ed359')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'MACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '2b7223fe-8b94-4a12-ae72-4f4fe1c8e67e')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'MACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '4d381817-e9ea-46a1-872c-efcb7f123a9d')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'VGW')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '4')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '19d0e3f0-2b85-498a-bc1a-447e65b621db')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'VGW')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '4')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '7be0e2dc-7ba3-48b5-98f5-b5a98a3141b7')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'VGW')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '4')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '36e54a54-baae-46c4-a4f5-ae097510d3d6')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'ACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '5d3096b6-d066-4477-97bb-0d232c6c5f8b')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'ACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'e880644a-c9a0-4932-a0db-41ee344ecb52')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'ACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '4102d52e-20d6-4cec-a2a7-11fcbe8017d4')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'JACDCL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '6')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'd24c9930-2c02-4e99-a928-4437275d75ed')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'JACDCL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '6')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '56b46588-7241-4949-9353-48eb93f5fc3a')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'JACDCL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '6')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '5a7213ac-8535-45ce-be2e-5ae96e759afb')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'MACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '7')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'e6353235-1764-4196-86c4-7936e83696dd')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'MACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '7')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '882aa6dd-8093-4259-8a3e-d958789a6f87')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'MACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '7')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '0e408e6f-30e4-4224-aac3-5cbadb7ab251')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'VGW')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '8')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '91311f75-eed1-4465-841f-774f5f041328')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'VGW')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '8')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'ff7270dd-5b26-44f8-a5f6-72b2f71295c2')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'VGW')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '8')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'c01dd974-d7b9-489e-91e2-2b4174eec41d')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'AACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '9')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'c73571a9-86b1-4739-8979-58897afd7809')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'AACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '9')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'fa9d5c79-d551-4f27-aab6-540c16dc6fe3')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'AACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '9')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '311caf82-e43f-402e-b4ff-a54363d0f19f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'AACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '85db0f2f-4f02-4054-97f3-5d470d29ecb4')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'AACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '8d79e173-cf13-4c2b-b920-38d31665ede1')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'AACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '40f97da2-79f6-4917-b683-1cfa0fccd669')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'NGT_PRINT_COURSE_MATERIALS')
            column(name: 'type_of_use', value: 'NGT_PRINT_COURSE_MATERIALS')
            column(name: 'priority', value: '11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '5549a633-57a9-49b6-a54d-9cbb1e0d13c9')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'NGT_PRINT_COURSE_MATERIALS')
            column(name: 'type_of_use', value: 'NGT_PRINT_COURSE_MATERIALS')
            column(name: 'priority', value: '11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'NGT_PRINT_COURSE_MATERIALS')
            column(name: 'type_of_use', value: 'NGT_PRINT_COURSE_MATERIALS')
            column(name: 'priority', value: '11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '2ad646de-d6c0-4eae-9a85-9c65f5c7e46b')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            column(name: 'type_of_use', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            column(name: 'priority', value: '12')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'b1b7c100-42f8-49f7-ab9f-a89e92a011c1')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            column(name: 'type_of_use', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            column(name: 'priority', value: '12')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            column(name: 'type_of_use', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            column(name: 'priority', value: '12')
        }

        rollback {

            sql("delete from ${dbAppsSchema}.df_grant_priority")

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: 'd9309c58-50bd-4ab9-9c12-bcefd752ce43')
                column(name: 'product_family', value: 'FAS')
                column(name: 'grant_product_family', value: 'TRS')
                column(name: 'type_of_use', value: 'NGT_PHOTOCOPY')
                column(name: 'priority', value: '0')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: 'e3389fff-1cdc-44f5-9bb2-ccd195eced26')
                column(name: 'product_family', value: 'FAS2')
                column(name: 'grant_product_family', value: 'TRS')
                column(name: 'type_of_use', value: 'NGT_PHOTOCOPY')
                column(name: 'priority', value: '0')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '8003d592-9c63-489f-bda3-72be51ce3ad6')
                column(name: 'product_family', value: 'NTS')
                column(name: 'grant_product_family', value: 'TRS')
                column(name: 'type_of_use', value: 'NGT_PHOTOCOPY')
                column(name: 'priority', value: '0')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '1174d1d0-f3bb-4e80-8b92-08ab97457a97')
                column(name: 'product_family', value: 'FAS')
                column(name: 'grant_product_family', value: 'ACLPRINT')
                column(name: 'type_of_use', value: 'PRINT')
                column(name: 'priority', value: '1')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '8332cdb1-9b61-4c57-9c4b-d6acd6a0d07a')
                column(name: 'product_family', value: 'FAS2')
                column(name: 'grant_product_family', value: 'ACLPRINT')
                column(name: 'type_of_use', value: 'PRINT')
                column(name: 'priority', value: '1')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '63311aac-ffa0-456b-ae6e-32f8c3fed08f')
                column(name: 'product_family', value: 'NTS')
                column(name: 'grant_product_family', value: 'ACLPRINT')
                column(name: 'type_of_use', value: 'PRINT')
                column(name: 'priority', value: '1')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '2367d120-e982-4ee6-a67f-7d0dd0192c69')
                column(name: 'product_family', value: 'FAS')
                column(name: 'grant_product_family', value: 'ACLDIGITAL')
                column(name: 'type_of_use', value: 'DIGITAL')
                column(name: 'priority', value: '2')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '52945c7b-2de1-4a3e-9da6-d92309f7feeb')
                column(name: 'product_family', value: 'FAS2')
                column(name: 'grant_product_family', value: 'ACLDIGITAL')
                column(name: 'type_of_use', value: 'DIGITAL')
                column(name: 'priority', value: '2')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '429ebca7-1265-4451-adfe-197fcbecf6bf')
                column(name: 'product_family', value: 'NTS')
                column(name: 'grant_product_family', value: 'ACLDIGITAL')
                column(name: 'type_of_use', value: 'DIGITAL')
                column(name: 'priority', value: '2')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: 'c01dd974-d7b9-489e-91e2-2b4174eec41d')
                column(name: 'product_family', value: 'FAS')
                column(name: 'grant_product_family', value: 'AACL')
                column(name: 'type_of_use', value: 'PRINT')
                column(name: 'priority', value: '3')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: 'c73571a9-86b1-4739-8979-58897afd7809')
                column(name: 'product_family', value: 'FAS2')
                column(name: 'grant_product_family', value: 'AACL')
                column(name: 'type_of_use', value: 'PRINT')
                column(name: 'priority', value: '3')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: 'fa9d5c79-d551-4f27-aab6-540c16dc6fe3')
                column(name: 'product_family', value: 'NTS')
                column(name: 'grant_product_family', value: 'AACL')
                column(name: 'type_of_use', value: 'PRINT')
                column(name: 'priority', value: '3')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '311caf82-e43f-402e-b4ff-a54363d0f19f')
                column(name: 'product_family', value: 'FAS')
                column(name: 'grant_product_family', value: 'AACL')
                column(name: 'type_of_use', value: 'DIGITAL')
                column(name: 'priority', value: '4')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '85db0f2f-4f02-4054-97f3-5d470d29ecb4')
                column(name: 'product_family', value: 'FAS2')
                column(name: 'grant_product_family', value: 'AACL')
                column(name: 'type_of_use', value: 'DIGITAL')
                column(name: 'priority', value: '4')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '8d79e173-cf13-4c2b-b920-38d31665ede1')
                column(name: 'product_family', value: 'NTS')
                column(name: 'grant_product_family', value: 'AACL')
                column(name: 'type_of_use', value: 'DIGITAL')
                column(name: 'priority', value: '4')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '40f97da2-79f6-4917-b683-1cfa0fccd669')
                column(name: 'product_family', value: 'FAS')
                column(name: 'grant_product_family', value: 'NGT_PRINT_COURSE_MATERIALS')
                column(name: 'type_of_use', value: 'NGT_PRINT_COURSE_MATERIALS')
                column(name: 'priority', value: '5')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '5549a633-57a9-49b6-a54d-9cbb1e0d13c9')
                column(name: 'product_family', value: 'FAS2')
                column(name: 'grant_product_family', value: 'NGT_PRINT_COURSE_MATERIALS')
                column(name: 'type_of_use', value: 'NGT_PRINT_COURSE_MATERIALS')
                column(name: 'priority', value: '5')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
                column(name: 'product_family', value: 'NTS')
                column(name: 'grant_product_family', value: 'NGT_PRINT_COURSE_MATERIALS')
                column(name: 'type_of_use', value: 'NGT_PRINT_COURSE_MATERIALS')
                column(name: 'priority', value: '5')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '2ad646de-d6c0-4eae-9a85-9c65f5c7e46b')
                column(name: 'product_family', value: 'FAS')
                column(name: 'grant_product_family', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
                column(name: 'type_of_use', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
                column(name: 'priority', value: '6')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: 'b1b7c100-42f8-49f7-ab9f-a89e92a011c1')
                column(name: 'product_family', value: 'FAS2')
                column(name: 'grant_product_family', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
                column(name: 'type_of_use', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
                column(name: 'priority', value: '6')
            }

            insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'df_grant_priority_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
                column(name: 'product_family', value: 'NTS')
                column(name: 'grant_product_family', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
                column(name: 'type_of_use', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
                column(name: 'priority', value: '6')
            }
        }
    }

    changeSet(id: '2019-05-13-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-51258 FDA: Adjust Batch Summary Report to be FAS specific: adjust report view to include only FAS and CLA batches")

        dropView(viewName: 'v_batch_summary_report', schemaName: dbAppsSchema)

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
                        case when (u.status_ind = 'ELIGIBLE') then count(1) else 0 end as fas_and_cla_fas_eligible_details_count,
                        case when (u.status_ind = 'ELIGIBLE') then sum(u.gross_amount) else 0 end as fas_and_cla_fas_eligible_details_gross_amount,
                        case when (u.status_ind = 'LOCKED') then count(1) else 0 end as scenarios_details_count,
                        case when (u.status_ind = 'LOCKED') then sum(u.gross_amount) else 0 end as scenarios_details_gross_amount,
                        case when (u.status_ind = 'LOCKED') then sum(u.net_amount) else 0 end as scenarios_details_net_amount,
                        case when (u.product_family = 'FAS2' and u.payee_account_number = 2000017000) then count(1) else 0 end return_to_cla_details_count,
                        case when (u.product_family = 'FAS2' and u.payee_account_number = 2000017000) then sum(u.gross_amount) else 0 end return_to_cla_details_gross_amount
                    from ${dbAppsSchema}.df_usage u
                    join ${dbAppsSchema}.df_usage_batch b on b.df_usage_batch_uid = u.df_usage_batch_uid
                    where b.product_family in ('FAS','FAS2') 
                    group by b.rro_account_number, b.name, b.payment_date, b.gross_amount, u.status_ind, u.product_family, u.payee_account_number) as nested_report
                group by nested_report.rro_account_number, nested_report.batch_name, nested_report.payment_date, nested_report.gross_amount) as report
            left join ${dbAppsSchema}.df_rightsholder rro on rro.rh_account_number = report.rro_account_number
            where non_eligible_details_count != 0 or fas_and_cla_fas_eligible_details_count != 0 or scenarios_details_count != 0
            order by report.payment_date, report.batch_name"""
        }

        rollback {

            dropView(viewName: 'v_fas_batch_summary_report', schemaName: dbAppsSchema)

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

    changeSet(id: '2019-05-30-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("CDP-757 FDA: Export from RH reconcile view: Ownership Adjustment Report displays unapproved " +
                "rightsholder discrepancies for scenarios in APPROVED, SENT_TO_LM and ARCHIVED statuses")

        update(schemaName: dbAppsSchema, tableName: 'df_rightsholder_discrepancy') {
            column(name: 'status_ind', value: 'DRAFT')
            where "status_ind = 'IN_PROGRESS'"
        }

        rollback {
            update(schemaName: dbAppsSchema, tableName: 'df_rightsholder_discrepancy') {
                column(name: 'status_ind', value: 'IN_PROGRESS')
                where "status_ind = 'DRAFT'"
            }
        }
    }
}
