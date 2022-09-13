databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2017-10-16-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('B-29372 Audit for details: upload/delete usages: Implement liquibase script for df_usage_audit table')

        createTable(tableName: 'df_usage_audit', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing usage audit actions') {
            column(name: 'df_usage_audit_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage audit action') {
                constraints(nullable: false)
            }
            column(name: 'df_usage_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage') {
                constraints(nullable: false)
            }
            column(name: 'df_scenario_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario')
            column(name: 'scenario_name', type: 'VARCHAR(255)', remarks: 'The name of scenario')
            column(name: 'action_type_ind', type: 'VARCHAR(32)', remarks: 'Usage action type index') {
                constraints(nullable: false)
            }
            column(name: 'action_reason', type: 'VARCHAR(1024)', remarks: 'Usage action reason')
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1',
                    remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM',
                    remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()',
                    remarks: 'The date and time this record was created') {
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

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_usage_audit', tablespace: dbIndexTablespace,
                columnNames: 'df_usage_audit_uid', constraintName: 'df_usage_audit_pk')

        addForeignKeyConstraint(constraintName: 'fk_df_usage_2_df_usage_audit',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_usage_audit',
                baseColumnNames: 'df_usage_uid',
                referencedTableName: 'df_usage',
                referencedColumnNames: 'df_usage_uid')

        addForeignKeyConstraint(constraintName: 'fk_df_scenario_2_df_usage_audit',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_usage_audit',
                baseColumnNames: 'df_scenario_uid',
                referencedTableName: 'df_scenario',
                referencedColumnNames: 'df_scenario_uid')

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2017-10-18-00', author: 'Aliaksandra Bayanouskaya <abayanouskaya@copyright.com>') {
        comment('B-34404 FDA: Integrate with PRM to get Payee for each detail: implement liquibase script to add' +
                ' df_rightsholder_uid column to df_rightsholder table')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', type: 'character varying(255)', remarks: 'The rightsholder uid')
        }

        sql("""update ${dbAppsSchema}.df_rightsholder set df_rightsholder_uid = rh_account_number""")

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_rightsholder', columnName: 'df_rightsholder_uid',
                columnDataType: 'character varying(255)')

        addUniqueConstraint(schemaName: dbAppsSchema, tableName: 'df_rightsholder', columnNames: 'df_rightsholder_uid',
                constraintName: 'uk_df_rightsholder')

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_rightsholder', columnName: 'df_rightsholder_uid')
        }
    }

    changeSet(id: '2017-10-30-00', author: 'Aliaksandr Radkevich <aradkevich@copyright.com>') {
        comment('B-34404 FDA: Integrate with PRM to get Payee for each detail: ' +
                'add payee_account_number column to df_usage_archive table')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'payee_account_number', type: 'NUMERIC(22,0)', remarks: 'Payee account number') {
                constraints(nullable: false)
            }
        }

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2017-11-01-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('B-37941 FDA: Foundation for calculating service fee on each eligible FAS detail: ' +
                'add is_rh_participating_flag column and update default value of service_fee column ' +
                'in df_usage and df_usage_archive tables, add service_fee_total column into df_scenario table')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'is_rh_participating_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'RH participating flag') {
                constraints(nullable: false)
            }
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'is_rh_participating_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'RH participating flag') {
                constraints(nullable: false)
            }
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'service_fee_total', type: 'DECIMAL(38,10)', defaultValue: 0.0000000000, remarks: 'The sum of usages service fee amounts included in scenario') {
                constraints(nullable: false)
            }
        }

        addDefaultValue(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'service_fee', defaultValue: '0.32000')
        addDefaultValue(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'service_fee', defaultValue: '0.32000')

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'is_rh_participating_flag')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'is_rh_participating_flag')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_scenario', columnName: 'service_fee_total')

            addDefaultValue(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'service_fee', defaultValue: '0.00000')
            addDefaultValue(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'service_fee', defaultValue: '0.00000')
        }
    }

    changeSet(id: '2017-11-08-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('B-37941 FDA: Foundation for calculating service fee on each eligible FAS detail: ' +
                'remove default value and NOT NULL constraint from df_usage and df_usage_archive tables')

        sql("alter table ${dbAppsSchema}.df_usage alter service_fee drop default")
        sql("alter table ${dbAppsSchema}.df_usage_archive alter service_fee drop default")

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'service_fee', columnDataType: 'DECIMAL(6,5)')
        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'service_fee', columnDataType: 'DECIMAL(6,5)')

        update(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'service_fee', value: 'null')
            where "df_scenario_uid is null"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'service_fee', value: 'null')
            where "df_scenario_uid is null"
        }

        rollback {
            addDefaultValue(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'service_fee', defaultValue: '0.00000')
            addDefaultValue(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'service_fee', defaultValue: '0.00000')

            update(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'service_fee', value: 0.00000)
                where "df_scenario_uid is null"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'service_fee', value: 0.00000)
                where "df_scenario_uid is null"
            }

            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'service_fee', columnDataType: 'DECIMAL(6,5)')
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'service_fee', columnDataType: 'DECIMAL(6,5)')
        }
    }

    changeSet(id: '2017-11-10-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-36162 Backend for Identifying and excluding details for rightsholders that roll up to the source RRO: " +
                "remove 'fk_df_scenario_2_df_usage_audit' constraint from df_usage_audit table")

        dropForeignKeyConstraint(baseTableSchemaName: dbAppsSchema, baseTableName: 'df_usage_audit',
                constraintName: 'fk_df_scenario_2_df_usage_audit')

        rollback {
            addForeignKeyConstraint(constraintName: 'fk_df_scenario_2_df_usage_audit',
                    baseTableSchemaName: dbAppsSchema,
                    referencedTableSchemaName: dbAppsSchema,
                    baseTableName: 'df_usage_audit',
                    baseColumnNames: 'df_scenario_uid',
                    referencedTableName: 'df_scenario',
                    referencedColumnNames: 'df_scenario_uid')
        }
    }

    changeSet(id: '2017-11-13-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-36162 Backend for Identifying and excluding details for rightsholders that roll up to the source RRO: " +
                "remove net_total, gross_total, reported_total, service_fee_total columns from df_scenario table")

        dropColumn(schemaName: dbAppsSchema, tableName: 'df_scenario', columnName: 'net_total')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_scenario', columnName: 'gross_total')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_scenario', columnName: 'reported_total')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_scenario', columnName: 'service_fee_total')

        rollback {
            addColumn(schemaName: dbAppsSchema, tableName: 'df_scenario') {
                column(name: 'net_total', type: 'DECIMAL(38,10)', defaultValue: 0.0000000000, remarks: 'The sum of usages net amounts included in scenario') {
                    constraints(nullable: false)
                }
                column(name: 'gross_total', type: 'DECIMAL(38,10)', defaultValue: 0.0000000000, remarks: 'The sum of gross amounts included in scenario') {
                    constraints(nullable: false)
                }
                column(name: 'reported_total', type: 'DECIMAL(38,2)', defaultValue: 0.00, remarks: 'The sum of reported values included in scenario') {
                    constraints(nullable: false)
                }
                column(name: 'service_fee_total', type: 'DECIMAL(38,10)', defaultValue: 0.0000000000, remarks: 'The sum of usages service fee amounts included in scenario') {
                    constraints(nullable: false)
                }
            }
        }
    }

    changeSet(id: '2017-11-15-00', author: 'Aliaksandr Radkevich <aradkevich@copyright.com>') {
        comment('B-38293 Tech Debt, Refactoring, and Demo Feedback: add index by df_usage_uid to df_usage_audit')

        createIndex(indexName: 'ix_df_usage_audit_df_usage_uid', schemaName: dbAppsSchema, tableName: 'df_usage_audit',
                tablespace: dbIndexTablespace) {
            column(name: 'df_usage_uid')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_usage_audit_df_usage_uid")
        }
    }

    changeSet(id: '2017-12-13-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('B-29371 Audit for scenarios: write liquibase script for creating df_scenario_audit table')

        createTable(tableName: 'df_scenario_audit', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing scenario audit actions') {
            column(name: 'df_scenario_audit_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario audit action') {
                constraints(nullable: false)
            }
            column(name: 'df_scenario_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario')
            column(name: 'action_type_ind', type: 'VARCHAR(32)', remarks: 'Scenario action type index') {
                constraints(nullable: false)
            }
            column(name: 'action_reason', type: 'VARCHAR(1024)', remarks: 'Scenario action reason')
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1',
                    remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM',
                    remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()',
                    remarks: 'The date and time this record was created') {
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

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_scenario_audit', tablespace: dbIndexTablespace,
                columnNames: 'df_scenario_audit_uid', constraintName: 'pk_df_scenario_audit')

        addForeignKeyConstraint(constraintName: 'fk_df_scenario_2_df_scenario_audit',
                baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_scenario_audit',
                baseColumnNames: 'df_scenario_uid',
                referencedTableName: 'df_scenario',
                referencedColumnNames: 'df_scenario_uid')

        createIndex(indexName: 'ix_df_scenario_audit_df_scenario_uid', schemaName: dbAppsSchema,
                tableName: 'df_scenario_audit', tablespace: dbIndexTablespace) {
            column(name: 'df_scenario_uid')
        }

        rollback {
            dropTable(tableName: 'df_scenario_audit', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2018-01-08-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-22206 Send FAS Scenario to LM (backend): remove fk_df_usage_2_df_usage_audit constraint from df_usage_audit table")

        dropForeignKeyConstraint(baseTableSchemaName: dbAppsSchema, baseTableName: 'df_usage_audit',
                constraintName: 'fk_df_usage_2_df_usage_audit')

        rollback ""
    }

    changeSet(id: '2018-01-16-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-39287 Tech Debt, Refactoring, and Demo Feedback: add missing not null constrains to df_usage, " +
                "df_usage_archive, df_scenario_audit tables")

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_scenario_audit',
                columnName: 'df_scenario_uid', columnDataType: 'VARCHAR(255)')
        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage',
                columnName: 'work_title', columnDataType: 'VARCHAR(2000)')
        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage',
                columnName: 'standard_number', columnDataType: 'VARCHAR(1000)')
        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                columnName: 'df_scenario_uid', columnDataType: 'VARCHAR(255)')
        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                columnName: 'work_title', columnDataType: 'VARCHAR(2000)')
        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                columnName: 'standard_number', columnDataType: 'VARCHAR(1000)')

        rollback {
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_scenario_audit',
                    columnName: 'df_scenario_uid', columnDataType: 'VARCHAR(255)')
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage',
                    columnName: 'work_title', columnDataType: 'VARCHAR(2000)')
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage',
                    columnName: 'standard_number', columnDataType: 'VARCHAR(1000)')
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                    columnName: 'df_scenario_uid', columnDataType: 'VARCHAR(255)')
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                    columnName: 'work_title', columnDataType: 'VARCHAR(2000)')
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                    columnName: 'standard_number', columnDataType: 'VARCHAR(1000)')
        }
    }

    changeSet(id: '2018-01-26-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-09525 Refine upload logic and introduce new statuses: remove not null constraints of work_title columns")

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage',
                columnName: 'work_title', columnDataType: 'VARCHAR(2000)')

        rollback {
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage',
                    columnName: 'work_title', columnDataType: 'VARCHAR(2000)')
        }
    }

    changeSet(id: '2018-02-14-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment("B-41176 FDA: get paid information from LM: " +
                "add columns for storing paid information into df_usage_archive table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'distribution_name', type: 'VARCHAR(255)', remarks: 'Distribution name')
            column(name: 'distribution_date', type: 'TIMESTAMPTZ', remarks: 'Distribution date')
            column(name: 'period_end_date', type: 'TIMESTAMPTZ', remarks: 'Period end date')
            column(name: 'ccc_event_id', type: 'VARCHAR(255)', remarks: 'CCC event identifier')
            column(name: 'check_number', type: 'VARCHAR(128)', remarks: 'Check number')
            column(name: 'check_date', type: 'TIMESTAMPTZ', remarks: 'Check date')
        }

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2018-02-14-01', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-41251 Introduce product family (foundation): add product_family column to df_usage and df_usage_archive tables")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'product_family', type: 'VARCHAR(128)', remarks: 'Product family', value: 'FAS')
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'product_family', type: 'VARCHAR(128)', remarks: 'Product family', value: 'FAS')
        }

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage',
                columnName: 'product_family', columnDataType: 'VARCHAR(128)')
        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                columnName: 'product_family', columnDataType: 'VARCHAR(128)')

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'product_family')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'product_family')
        }
    }

    changeSet(id: '2018-02-19-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment("B-41295 FDA: Relax validation rules for uploading Usage data: " +
                "drop NOT NULL constraint for standard_number column in df_usage and df_usage_archive tables")

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage',
                columnName: 'standard_number', columnDataType: 'VARCHAR(1000)')
        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                columnName: 'standard_number', columnDataType: 'VARCHAR(1000)')

        rollback {
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage',
                    columnName: 'standard_number', columnDataType: 'VARCHAR(1000)')
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                    columnName: 'standard_number', columnDataType: 'VARCHAR(1000)')
        }
    }

    changeSet(id: '2018-03-05-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-40635 FDA: Refresh in-progress scenario with newly eligible details: adding Liquibase scripts to store filters")

        createTable(tableName: 'df_scenario_usage_filter', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing usages filters') {

            column(name: 'df_scenario_usage_filter_uid', type: 'VARCHAR(255)', remarks: 'The unique identifier for usage filter') {
                constraints(nullable: false)
            }
            column(name: 'df_scenario_uid', type: 'VARCHAR(255)', remarks: 'The unique identifier of related scenario') {
                constraints(nullable: false)
            }
            column(name: 'product_family', type: 'VARCHAR(255)', remarks: 'The product family') {
                constraints(nullable: false)
            }
            column(name: 'status_ind', type: 'VARCHAR(16)', remarks: 'The status of usage') {
                constraints(nullable: false)
            }
            column(name: 'payment_date', type: 'DATE', remarks: 'The payment date') {
                constraints(nullable: false)
            }
            column(name: 'fiscal_year', type: 'NUMERIC(4,0)', remarks: 'The fiscal year') {
                constraints(nullable: false)
            }
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1', remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(256)', remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created') {
                constraints(nullable: false)
            }
            column(name: 'updated_by_user', type: 'VARCHAR(256)', remarks: 'The user name who updated this record; when a record is first created, this will be the same as the created_by_user') {
                constraints(nullable: false)
            }
            column(name: 'updated_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created; when a record is first created, this will be the same as the created_datetime') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter', tablespace: dbIndexTablespace,
                columnNames: 'df_scenario_usage_filter_uid', constraintName: 'pk_df_scenario_usage_filter_uid')

        addForeignKeyConstraint(constraintName: 'fk_df_scenario_usage_filter_2_df_scenario',
                baseTableSchemaName: dbAppsSchema, baseTableName: 'df_scenario_usage_filter', baseColumnNames: 'df_scenario_uid',
                referencedTableSchemaName: dbAppsSchema, referencedTableName: 'df_scenario', referencedColumnNames: 'df_scenario_uid')

        createTable(tableName: 'df_scenario_usage_filter_to_rh_account_numbers_map', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table to map one to many relation between usage filter and rightsholder account numbers') {

            column(name: 'df_scenario_usage_filter_uid', type: 'VARCHAR(255)', remarks: 'The unique identifier of usage filter') {
                constraints(nullable: false)
            }
            column(name: 'rh_account_number', type: 'NUMERIC(22,0)', remarks: 'The rightsholder account number') {
                constraints(nullable: false)
            }
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1', remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(256)', remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created') {
                constraints(nullable: false)
            }
            column(name: 'updated_by_user', type: 'VARCHAR(256)', remarks: 'The user name who updated this record; when a record is first created, this will be the same as the created_by_user') {
                constraints(nullable: false)
            }
            column(name: 'updated_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created; when a record is first created, this will be the same as the created_datetime') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(tableName: 'df_scenario_usage_filter_to_rh_account_numbers_map', columnNames: 'df_scenario_usage_filter_uid, rh_account_number',
                constraintName: 'pk_df_scenario_usage_filter_to_rh_account_numbers_map', schemaName: dbAppsSchema, tablespace: dbDataTablespace)

        addForeignKeyConstraint(constraintName: 'fk_filter_to_rh_account_numbers_map_2_df_scenario_usage_filter',
                baseTableSchemaName: dbAppsSchema, baseTableName: 'df_scenario_usage_filter_to_rh_account_numbers_map', baseColumnNames: 'df_scenario_usage_filter_uid',
                referencedTableSchemaName: dbAppsSchema, referencedTableName: 'df_scenario_usage_filter', referencedColumnNames: 'df_scenario_usage_filter_uid')
        addForeignKeyConstraint(constraintName: 'fk_filter_to_rh_account_numbers_map_2_df_rightsholder',
                baseTableSchemaName: dbAppsSchema, baseTableName: 'df_scenario_usage_filter_to_rh_account_numbers_map', baseColumnNames: 'rh_account_number',
                referencedTableSchemaName: dbAppsSchema, referencedTableName: 'df_rightsholder', referencedColumnNames: 'rh_account_number')

        createTable(tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table to map one to many relation between usage filter and usage batches ids') {

            column(name: 'df_scenario_usage_filter_uid', type: 'VARCHAR(255)', remarks: 'The unique identifier of usage filter') {
                constraints(nullable: false)
            }
            column(name: 'df_usage_batch_uid', type: 'VARCHAR(255)', remarks: 'The unique identifier of usage batch') {
                constraints(nullable: false)
            }
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1', remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(256)', remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created') {
                constraints(nullable: false)
            }
            column(name: 'updated_by_user', type: 'VARCHAR(256)', remarks: 'The user name who updated this record; when a record is first created, this will be the same as the created_by_user') {
                constraints(nullable: false)
            }
            column(name: 'updated_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()', remarks: 'The date and time this record was created; when a record is first created, this will be the same as the created_datetime') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map', columnNames: 'df_scenario_usage_filter_uid, df_usage_batch_uid',
                constraintName: 'pk_df_scenario_usage_filter_to_usage_batches_ids_map', schemaName: dbAppsSchema, tablespace: dbDataTablespace)

        addForeignKeyConstraint(constraintName: 'fk_filter_to_usage_batches_ids_map_2_df_scenario_usage_filter',
                baseTableSchemaName: dbAppsSchema, baseTableName: 'df_scenario_usage_filter_to_usage_batches_ids_map', baseColumnNames: 'df_scenario_usage_filter_uid',
                referencedTableSchemaName: dbAppsSchema, referencedTableName: 'df_scenario_usage_filter', referencedColumnNames: 'df_scenario_usage_filter_uid')
        addForeignKeyConstraint(constraintName: 'fk_filter_to_usage_batches_ids_map_2_df_usage_batch',
                baseTableSchemaName: dbAppsSchema, baseTableName: 'df_scenario_usage_filter_to_usage_batches_ids_map', baseColumnNames: 'df_usage_batch_uid',
                referencedTableSchemaName: dbAppsSchema, referencedTableName: 'df_usage_batch', referencedColumnNames: 'df_usage_batch_uid')

        rollback {
            dropTable(tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_scenario_usage_filter_to_rh_account_numbers_map', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_scenario_usage_filter', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2018-03-14-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-40635 FDA: Refresh in-progress scenario with newly eligible details: remove unnecessary 'not null' constraints")

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter',
                columnName: 'status_ind', columnDataType: 'VARCHAR(16)')
        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter',
                columnName: 'payment_date', columnDataType: 'DATE')
        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter',
                columnName: 'fiscal_year', columnDataType: 'NUMERIC(4,0)')

        rollback {
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter',
                    columnName: 'status_ind', columnDataType: 'VARCHAR(16)')
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter',
                    columnName: 'payment_date', columnDataType: 'DATE')
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter',
                    columnName: 'fiscal_year', columnDataType: 'NUMERIC(4,0)')
        }
    }

    changeSet(id: '2018-03-22-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-40635 FDA: Refresh in-progress scenario with newly eligible details: remove fk_filter_to_usage_batches_ids_map_2_df_usage_batch foreign key")

        preConditions(onFail: 'MARK_RAN') {
            foreignKeyConstraintExists(schemaName: dbAppsSchema, foreignKeyTableName: 'df_scenario_usage_filter_to_usage_batches_ids_map',
                    foreignKeyName: 'fk_filter_to_usage_batches_ids_map_2_df_usage_batch')
        }

        dropForeignKeyConstraint(baseTableSchemaName: dbAppsSchema, baseTableName: 'df_scenario_usage_filter_to_usage_batches_ids_map',
                constraintName: 'fk_filter_to_usage_batches_ids_map_2_df_usage_batch')

        rollback ""
    }

    changeSet(id: '2018-03-23-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-40635 FDA: Refresh in-progress scenario with newly eligible details: remove fk_filter_to_rh_account_numbers_map_2_df_rightsholder foreign key")

        preConditions(onFail: 'MARK_RAN') {
            foreignKeyConstraintExists(schemaName: dbAppsSchema, foreignKeyTableName: 'df_scenario_usage_filter_to_rh_account_numbers_map',
                    foreignKeyName: 'fk_filter_to_rh_account_numbers_map_2_df_rightsholder')
        }

        dropForeignKeyConstraint(baseTableSchemaName: dbAppsSchema, baseTableName: 'df_scenario_usage_filter_to_rh_account_numbers_map',
                constraintName: 'fk_filter_to_rh_account_numbers_map_2_df_rightsholder')

        rollback ""
    }

    changeSet(id: '2018-03-29-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-41014 [ALL] Tech Debt, Refactoring, and Demo Feedback: remove df_scenario_uid and scenario_name columns from df_usage_audit table")

        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_audit', columnName: 'df_scenario_uid')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_audit', columnName: 'scenario_name')

        rollback {
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
                column(name: 'df_scenario_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario')
                column(name: 'scenario_name', type: 'VARCHAR(255)', remarks: 'The name of scenario')
            }
        }
    }

    changeSet(id: '2018-04-11-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('B-42473 Spike: FDA: Refine PI integration to improve matching results: add indexes by standard_number and work_title to df_usage')

        createIndex(indexName: 'ix_df_usage_standard_number', schemaName: dbAppsSchema, tableName: 'df_usage', tablespace: dbIndexTablespace) {
            column(name: 'standard_number')
        }

        createIndex(indexName: 'ix_df_usage_work_title', schemaName: dbAppsSchema, tableName: 'df_usage', tablespace: dbIndexTablespace) {
            column(name: 'work_title')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_usage_standard_number")
            sql("drop index ${dbAppsSchema}.ix_df_usage_work_title")
        }
    }

    changeSet(id: '2018-04-17-00', author: 'Ihar Suvorau <isuvorau@copyright.com') {
        comment('B-42925 FDA: Tune Performance for Reconciling Rightsholders: add df_rightsholder_discrepancy table')

        createTable(tableName: 'df_rightsholder_discrepancy', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'The table to store rightsholder discrepancies for scenario reconciliation') {

            column(name: 'df_rightsholder_discrepancy_uid', type: 'VARCHAR(255)', remarks: 'The rightsholder discrepancy identifier') {
                constraints(nullable: false)
            }

            column(name: 'df_scenario_uid', type: 'VARCHAR(255)', remarks: 'The Scenario identifier') {
                constraints(nullable: false)
            }

            column(name: 'wr_wrk_inst', type: 'NUMERIC(15,0)', remarks: 'The work identifier') {
                constraints(nullable: false)
            }

            column(name: 'old_rh_account_number', type: 'NUMERIC(22,0)', remarks: 'The old rightsholder account number') {
                constraints(nullable: false)
            }

            column(name: 'new_rh_account_number', type: 'NUMERIC(22,0)', remarks: 'The new rightsholder account number')

            column(name: 'work_title', type: 'VARCHAR(2000)', remarks: 'The rightsholder discrepancy work title')

            column(name: 'product_family', type: 'VARCHAR(128)', remarks: 'The rightsholder discrepancy product family') {
                constraints(nullable: false)
            }

            column(name: 'status_ind', type: 'VARCHAR(16)', remarks: 'The rightsholder discrepancy status') {
                constraints(nullable: false)
            }

            column(name: 'record_version', type: 'INTEGER', defaultValue: '1',
                    remarks: 'The latest version of this record, used for optimistic locking') {
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

        addPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_rightsholder_discrepancy', tablespace: dbIndexTablespace,
                columnNames: 'df_rightsholder_discrepancy_uid', constraintName: 'pk_df_rightsholder_discrepancy')

        addForeignKeyConstraint(constraintName: 'fk_df_rightsholder_discrepancy_2_df_scenario',
                baseTableSchemaName: dbAppsSchema,
                baseTableName: 'df_rightsholder_discrepancy',
                baseColumnNames: 'df_scenario_uid',
                referencedTableSchemaName: dbAppsSchema,
                referencedTableName: 'df_scenario',
                referencedColumnNames: 'df_scenario_uid')

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2018-04-25-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-42928 FDA: Replace user-generated detail ID with system-generated detail ID: " +
                "drop NOT NULL constraint from detail_id column in df_usage and df_usage_archive tables")

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage',
                columnName: 'detail_id', columnDataType: 'NUMERIC(15,0)')
        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                columnName: 'detail_id', columnDataType: 'NUMERIC(15,0)')

        rollback {
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage',
                    columnName: 'detail_id', columnDataType: 'NUMERIC(15,0)')
            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                    columnName: 'detail_id', columnDataType: 'NUMERIC(15,0)')
        }
    }

    changeSet(id: '2018-05-03-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Create tables for quartz job data storage")

        createTable(tableName: 'df_qrtz_job_details', schemaName: dbAppsSchema, tablespace: dbDataTableSpace,
                remarks: 'Service table for quartz job data storage') {
            column(name: 'sched_name', type: 'VARCHAR(120)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'job_name', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'job_group', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(250)', remarks: '')
            column(name: 'job_class_name', type: 'VARCHAR(250)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'is_durable', type: 'BOOLEAN', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'is_nonconcurrent', type: 'BOOLEAN', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'is_update_data', type: 'BOOLEAN', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'requests_recovery', type: 'BOOLEAN', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'job_data', type: 'BLOB', remarks: '') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(constraintName: 'pk_df_qrtz_job_details', schemaName: dbAppsSchema, tableName: 'df_qrtz_job_details',
                tablespace: dbIndexTableSpace, columnNames: 'sched_name, job_name, job_group')

        createTable(tableName: 'df_qrtz_triggers', schemaName: dbAppsSchema, tablespace: dbDataTableSpace,
                remarks: 'Service table for quartz job data storage') {
            column(name: 'sched_name', type: 'VARCHAR(120)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_name', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_group', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'job_name', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'job_group', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(250)', remarks: '')
            column(name: 'next_fire_time', type: 'BIGINT', remarks: '')
            column(name: 'prev_fire_time', type: 'BIGINT', remarks: '')
            column(name: 'priority', type: 'INTEGER', remarks: '')
            column(name: 'trigger_state', type: 'VARCHAR(16)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_type', type: 'VARCHAR(8)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'start_time', type: 'BIGINT', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'end_time', type: 'BIGINT', remarks: '')
            column(name: 'calendar_name', type: 'VARCHAR(200)', remarks: '')
            column(name: 'misfire_instr', type: 'SMALLINT', remarks: '')
            column(name: 'job_data', type: 'BLOB', remarks: '') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(constraintName: 'pk_df_qrtz_triggers', schemaName: dbAppsSchema, tableName: 'df_qrtz_triggers',
                tablespace: dbIndexTableSpace, columnNames: 'sched_name,trigger_name,trigger_group')
        addForeignKeyConstraint(constraintName: 'fk_df_qrtz_triggers_2_df_qrtz_job_details',
                baseTableSchemaName: dbAppsSchema, baseTableName: 'df_qrtz_triggers',
                baseColumnNames: 'sched_name,job_name,job_group', referencedTableSchemaName: dbAppsSchema,
                referencedTableName: 'df_qrtz_job_details', referencedColumnNames: 'sched_name,job_name,job_group')

        createTable(tableName: 'df_qrtz_simple_triggers', schemaName: dbAppsSchema, tablespace: dbDataTableSpace,
                remarks: 'Service table for quartz job data storage') {
            column(name: 'sched_name', type: 'VARCHAR(120)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_name', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_group', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'repeat_count', type: 'BIGINT', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'repeat_interval', type: 'BIGINT', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'times_triggered', type: 'BIGINT', remarks: '') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(constraintName: 'pk_df_qrtz_simple_triggers', schemaName: dbAppsSchema,
                tableName: 'df_qrtz_simple_triggers', tablespace: dbIndexTableSpace,
                columnNames: 'sched_name,trigger_name,trigger_group')
        addForeignKeyConstraint(constraintName: 'fk_df_qrtz_simple_triggers_2_df_qrtz_triggers',
                baseTableSchemaName: dbAppsSchema, baseTableName: 'df_qrtz_simple_triggers',
                baseColumnNames: 'sched_name,trigger_name,trigger_group',
                referencedTableSchemaName: dbAppsSchema,
                referencedTableName: 'df_qrtz_triggers', referencedColumnNames: 'sched_name,trigger_name,trigger_group')

        createTable(tableName: 'df_qrtz_cron_triggers', schemaName: dbAppsSchema, tablespace: dbDataTableSpace,
                remarks: 'Service table for quartz job data storage') {
            column(name: 'sched_name', type: 'VARCHAR(120)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_name', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_group', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'cron_expression', type: 'VARCHAR(120)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'time_zone_id', type: 'VARCHAR(80)', remarks: '') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(constraintName: 'pk_df_qrtz_cron_triggers', schemaName: dbAppsSchema,
                tableName: 'df_qrtz_cron_triggers', tablespace: dbIndexTableSpace,
                columnNames: 'sched_name,trigger_name,trigger_group')
        addForeignKeyConstraint(constraintName: 'fk_df_qrtz_cron_triggers_2_df_qrtz_triggers',
                baseTableSchemaName: dbAppsSchema, baseTableName: 'df_qrtz_cron_triggers',
                baseColumnNames: 'sched_name,trigger_name,trigger_group',
                referencedTableSchemaName: dbAppsSchema, referencedTableName: 'df_qrtz_triggers',
                referencedColumnNames: 'sched_name,trigger_name,trigger_group')

        createTable(tableName: 'df_qrtz_simprop_triggers', schemaName: dbAppsSchema, tablespace: dbDataTableSpace,
                remarks: 'Service table for quartz job data storage') {
            column(name: 'sched_name', type: 'VARCHAR(120)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_name', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_group', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'str_prop_1', type: 'VARCHAR(512)', remarks: '')
            column(name: 'str_prop_2', type: 'VARCHAR(512)', remarks: '')
            column(name: 'str_prop_3', type: 'VARCHAR(512)', remarks: '')
            column(name: 'int_prop_1', type: 'INT', remarks: '')
            column(name: 'int_prop_2', type: 'INT', remarks: '')
            column(name: 'long_prop_1', type: 'BIGINT', remarks: '')
            column(name: 'long_prop_2', type: 'BIGINT', remarks: '')
            column(name: 'dec_prop_1', type: 'NUMERIC(13,4)', remarks: '')
            column(name: 'dec_prop_2', type: 'NUMERIC(13,4)', remarks: '')
            column(name: 'bool_prop_1', type: 'BOOLEAN', remarks: '')
            column(name: 'bool_prop_2', type: 'BOOLEAN', remarks: '')
        }

        addPrimaryKey(constraintName: 'pk_df_qrtz_simprop_triggers', schemaName: dbAppsSchema,
                tableName: 'df_qrtz_simprop_triggers', tablespace: dbIndexTableSpace,
                columnNames: 'sched_name,trigger_name,trigger_group')
        addForeignKeyConstraint(constraintName: 'fk_df_qrtz_simprop_triggers_2_df_qrtz_triggers',
                baseTableSchemaName: dbAppsSchema, baseTableName: 'df_qrtz_simprop_triggers',
                baseColumnNames: 'sched_name,trigger_name,trigger_group',
                referencedTableSchemaName: dbAppsSchema, referencedTableName: 'df_qrtz_triggers',
                referencedColumnNames: 'sched_name,trigger_name,trigger_group')

        createTable(tableName: 'df_qrtz_blob_triggers', schemaName: dbAppsSchema, tablespace: dbDataTableSpace,
                remarks: 'Service table for quartz job data storage') {
            column(name: 'sched_name', type: 'VARCHAR(120)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_name', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_group', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'blob_data', type: 'BLOB', remarks: '')
        }

        addPrimaryKey(constraintName: 'pk_df_qrtz_blob_triggers', schemaName: dbAppsSchema,
                tableName: 'df_qrtz_blob_triggers', tablespace: dbIndexTableSpace,
                columnNames: 'sched_name,trigger_name,trigger_group')
        addForeignKeyConstraint(constraintName: 'fk_df_qrtz_blob_triggers_2_df_qrtz_triggers',
                baseTableSchemaName: dbAppsSchema, baseTableName: 'df_qrtz_blob_triggers',
                baseColumnNames: 'sched_name,trigger_name,trigger_group',
                referencedTableSchemaName: dbAppsSchema, referencedTableName: 'df_qrtz_triggers',
                referencedColumnNames: 'sched_name,trigger_name,trigger_group')

        createTable(tableName: 'df_qrtz_calendars', schemaName: dbAppsSchema, tablespace: dbDataTableSpace,
                remarks: 'Service table for quartz job data storage') {
            column(name: 'sched_name', type: 'VARCHAR(120)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'calendar_name', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'calendar', type: 'BLOB', remarks: '') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(constraintName: 'pk_df_qrtz_calendars', schemaName: dbAppsSchema, tableName: 'df_qrtz_calendars',
                tablespace: dbIndexTableSpace, columnNames: 'sched_name,calendar_name')

        createTable(tableName: 'df_qrtz_paused_trigger_grps', schemaName: dbAppsSchema, tablespace: dbDataTableSpace,
                remarks: 'Service table for quartz job data storage') {
            column(name: 'sched_name', type: 'VARCHAR(120)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_group', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(constraintName: 'pk_df_qrtz_paused_trigger_grps', schemaName: dbAppsSchema,
                tableName: 'df_qrtz_paused_trigger_grps', tablespace: dbIndexTableSpace,
                columnNames: 'sched_name,trigger_group')

        createTable(tableName: 'df_qrtz_fired_triggers', schemaName: dbAppsSchema, tablespace: dbDataTableSpace,
                remarks: 'Service table for quartz job data storage') {
            column(name: 'sched_name', type: 'VARCHAR(120)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'entry_id', type: 'VARCHAR(95)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_name', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'trigger_group', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'instance_name', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'fired_time', type: 'BIGINT', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'sched_time', type: 'BIGINT', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'priority', type: 'INTEGER', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'state', type: 'VARCHAR(16)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'job_name', type: 'VARCHAR(200)', remarks: '')
            column(name: 'job_group', type: 'VARCHAR(200)', remarks: '')
            column(name: 'is_nonconcurrent', type: 'BOOLEAN', remarks: '')
            column(name: 'requests_recovery', type: 'BOOLEAN', remarks: '')
        }

        addPrimaryKey(constraintName: 'pk_df_qrtz_fired_triggers', schemaName: dbAppsSchema,
                tableName: 'df_qrtz_fired_triggers', tablespace: dbIndexTableSpace,
                columnNames: 'sched_name,entry_id')

        createTable(tableName: 'df_qrtz_scheduler_state', schemaName: dbAppsSchema, tablespace: dbDataTableSpace,
                remarks: 'Service table for quartz job data storage') {
            column(name: 'sched_name', type: 'VARCHAR(120)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'instance_name', type: 'VARCHAR(200)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'last_checkin_time', type: 'BIGINT', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'checkin_interval', type: 'BIGINT', remarks: '') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(constraintName: 'pk_df_qrtz_scheduler_state', schemaName: dbAppsSchema,
                tableName: 'df_qrtz_scheduler_state', tablespace: dbIndexTableSpace,
                columnNames: 'sched_name,instance_name')

        createTable(tableName: 'df_qrtz_locks', schemaName: dbAppsSchema, tablespace: dbDataTableSpace,
                remarks: 'Service table for quartz job data storage') {
            column(name: 'sched_name', type: 'VARCHAR(120)', remarks: '') {
                constraints(nullable: false)
            }
            column(name: 'lock_name', type: 'VARCHAR(40)', remarks: '') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(constraintName: 'pk_df_qrtz_locks', schemaName: dbAppsSchema, tableName: 'df_qrtz_locks',
                tablespace: dbIndexTableSpace, columnNames: 'sched_name,lock_name')

        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_job_details',
                indexName: 'idx_qrtz_j_req_recovery', unique: false) {
            column(name: 'sched_name')
            column(name: 'requests_recovery')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_job_details',
                indexName: 'idx_qrtz_j_grp', unique: false) {
            column(name: 'sched_name')
            column(name: 'job_group')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_triggers',
                indexName: 'idx_qrtz_t_j', unique: false) {
            column(name: 'sched_name')
            column(name: 'job_name')
            column(name: 'job_group')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_triggers',
                indexName: 'idx_qrtz_t_jg', unique: false) {
            column(name: 'sched_name')
            column(name: 'job_group')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_triggers',
                indexName: 'idx_qrtz_t_c', unique: false) {
            column(name: 'sched_name')
            column(name: 'calendar_name')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_triggers',
                indexName: 'idx_qrtz_t_g', unique: false) {
            column(name: 'sched_name')
            column(name: 'trigger_group')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_triggers',
                indexName: 'idx_qrtz_t_state', unique: false) {
            column(name: 'sched_name')
            column(name: 'trigger_state')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_triggers',
                indexName: 'idx_qrtz_t_n_state', unique: false) {
            column(name: 'sched_name')
            column(name: 'trigger_name')
            column(name: 'trigger_group')
            column(name: 'trigger_state')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_triggers',
                indexName: 'idx_qrtz_t_n_g_state', unique: false) {
            column(name: 'sched_name')
            column(name: 'trigger_group')
            column(name: 'trigger_state')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_triggers',
                indexName: 'idx_qrtz_t_next_fire_time', unique: false) {
            column(name: 'sched_name')
            column(name: 'next_fire_time')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_triggers',
                indexName: 'idx_qrtz_t_nft', unique: false) {
            column(name: 'sched_name')
            column(name: 'trigger_state')
            column(name: 'next_fire_time')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_triggers',
                indexName: 'idx_qrtz_t_nft_misfire', unique: false) {
            column(name: 'sched_name')
            column(name: 'misfire_instr')
            column(name: 'next_fire_time')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_triggers',
                indexName: 'idx_qrtz_t_nft_st_misfire', unique: false) {
            column(name: 'sched_name')
            column(name: 'misfire_instr')
            column(name: 'next_fire_time')
            column(name: 'trigger_state')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_triggers',
                indexName: 'idx_qrtz_t_nft_st_misfire_grp', unique: false) {
            column(name: 'sched_name')
            column(name: 'misfire_instr')
            column(name: 'next_fire_time')
            column(name: 'trigger_state')
            column(name: 'trigger_group')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_fired_triggers',
                indexName: 'idx_qrtz_ft_trig_inst_name', unique: false) {
            column(name: 'sched_name')
            column(name: 'instance_name')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_fired_triggers',
                indexName: 'idx_qrtz_ft_inst_job_req_rcvry', unique: false) {
            column(name: 'sched_name')
            column(name: 'instance_name')
            column(name: 'requests_recovery')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_fired_triggers',
                indexName: 'idx_qrtz_ft_j_g', unique: false) {
            column(name: 'sched_name')
            column(name: 'job_name')
            column(name: 'job_group')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_fired_triggers',
                indexName: 'idx_qrtz_ft_jg', unique: false) {
            column(name: 'sched_name')
            column(name: 'job_group')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_fired_triggers',
                indexName: 'idx_qrtz_ft_t_g', unique: false) {
            column(name: 'sched_name')
            column(name: 'trigger_name')
            column(name: 'trigger_group')
        }
        createIndex(schemaName: dbAppsSchema, tablespace: dbIndexTablespace, tableName: 'df_qrtz_fired_triggers',
                indexName: 'idx_qrtz_ft_tg', unique: false) {
            column(name: 'sched_name')
            column(name: 'trigger_group')
        }

        rollback {
            dropTable(tableName: 'df_qrtz_locks', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_qrtz_scheduler_state', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_qrtz_fired_triggers', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_qrtz_paused_trigger_grps', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_qrtz_calendars', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_qrtz_blob_triggers', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_qrtz_simprop_triggers', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_qrtz_cron_triggers', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_qrtz_simple_triggers', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_qrtz_triggers', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_qrtz_job_details', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2018-05-04-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-42928 FDA: Replace user-generated detail ID with system-generated detail ID: " +
                "remove detail_id column from df_usage and df_usage_archive tables")

        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'detail_id')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'detail_id')

        rollback {
            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
                column(name: 'detail_id', type: 'NUMERIC(15,0)', remarks: 'The usage identifier in TF')
            }
            addUniqueConstraint(constraintName: 'iu_detail_id',
                    schemaName: dbAppsSchema,
                    tablespace: dbDataTablespace,
                    tableName: 'df_usage',
                    columnNames: 'detail_id')

            addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'detail_id', type: 'NUMERIC(15,0)', remarks: 'The usage identifier in TF')
            }
            addUniqueConstraint(constraintName: 'iu_archive_detail_id',
                    schemaName: dbAppsSchema,
                    tablespace: dbDataTablespace,
                    tableName: 'df_usage_archive',
                    columnNames: 'detail_id')
        }
    }

    changeSet(id: '2018-05-07-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("RDSC-587 FDA: Exception is shown during sending usage detail with empty Work Title to Liability Manager")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'system_title', type: 'VARCHAR(2000)', remarks: 'The work title sent to LM')
        }
        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'system_title', type: 'VARCHAR(2000)', remarks: 'The work title sent to LM')
        }

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                columnName: 'work_title', columnDataType: 'VARCHAR(2000)')

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage', columnName: 'system_title')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive', columnName: 'system_title')
        }
    }

    changeSet(id: '2018-05-11-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-37305 FDA: Undistributed liabilities reconciliation report: implement table for storing service fee % for rro. Insert data")

        createTable(tableName: 'df_rro_estimated_service_fee_percentage', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'The table to store estimated Service Fee % for rro') {
            column(name: 'rro_account_number', type: 'NUMERIC(22,0)', remarks: 'rro account number')
            column(name: 'esimated_service_fee', type: 'DECIMAL(6,5)', defaultValue: 0.00000, remarks: 'The estimated service fee') {
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

        addPrimaryKey(constraintName: 'pk_df_rro_estimated_service_fee_percentage', schemaName: dbAppsSchema,
                tableName: 'df_rro_estimated_service_fee_percentage', tablespace: dbIndexTableSpace, columnNames: 'rro_account_number')

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000017004')
            column(name: 'esimated_service_fee', value: '0.19500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000108983')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000017006')
            column(name: 'esimated_service_fee', value: '0.22000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7001726973')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000017007')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7000896777')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000017001')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000017000')
            column(name: 'esimated_service_fee', value: '0.19000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7000581909')
            column(name: 'esimated_service_fee', value: '0.19500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000017008')
            column(name: 'esimated_service_fee', value: '0.19000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000027354')
            column(name: 'esimated_service_fee', value: '0.19500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000072783')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000017011')
            column(name: 'esimated_service_fee', value: '0.20500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000073833')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7001298418')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7000340078')
            column(name: 'esimated_service_fee', value: '0.22500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000017012')
            column(name: 'esimated_service_fee', value: '0.19000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7000757545')
            column(name: 'esimated_service_fee', value: '0.17500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7000849816')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000017010')
            column(name: 'esimated_service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7000340130')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7000478527')
            column(name: 'esimated_service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000017005')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000128767')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7001438813')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7000676925')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000045828')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7000582241')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000017003')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7000478504')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7001498587')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '7000800336')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000046269')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage') {
            column(name: 'rro_account_number', value: '2000017002')
            column(name: 'esimated_service_fee', value: '0.18500')
        }

        rollback {
            dropTable(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage')
        }
    }

    changeSet(id: '2018-05-15-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment("RDSC-587 FDA: Exception is shown during sending usage detail with empty Work Title to Liability Manager: " +
                "populate system_title in df_usage_archive table and add NOT NULL constraint")

        sql("""update ${dbAppsSchema}.df_usage_archive
               set system_title = coalesce(work_title, 'Unidentified')""")

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                columnName: 'system_title', columnDataType: 'VARCHAR(2000)')

        rollback {
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                    columnName: 'system_title', columnDataType: 'VARCHAR(2000)')
        }
    }

    changeSet(id: '2018-05-15-01', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-37305 FDA: Undistributed liabilities reconciliation report: fix misprint in column estimated_service_fee")

        renameColumn(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage',
                oldColumnName: 'esimated_service_fee', newColumnName: 'estimated_service_fee')

        rollback {
            renameColumn(schemaName: dbAppsSchema, tableName: 'df_rro_estimated_service_fee_percentage',
                    oldColumnName: 'estimated_service_fee', newColumnName: 'esimated_service_fee')
        }
    }

    changeSet(id: '2018-05-21-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-43660 Tech Debt: FDA: implement liquibase script for populating base columns by default values")

        addDefaultValue(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter',
                columnName: 'created_by_user', defaultValue: 'SYSTEM')
        addDefaultValue(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter',
                columnName: 'updated_by_user', defaultValue: 'SYSTEM')

        addDefaultValue(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_rh_account_numbers_map',
                columnName: 'created_by_user', defaultValue: 'SYSTEM')
        addDefaultValue(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_rh_account_numbers_map',
                columnName: 'updated_by_user', defaultValue: 'SYSTEM')

        addDefaultValue(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map',
                columnName: 'created_by_user', defaultValue: 'SYSTEM')
        addDefaultValue(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map',
                columnName: 'updated_by_user', defaultValue: 'SYSTEM')

        rollback {
            sql("alter table ${dbAppsSchema}.df_scenario_usage_filter alter created_by_user drop default")
            sql("alter table ${dbAppsSchema}.df_scenario_usage_filter alter updated_by_user drop default")

            sql("alter table ${dbAppsSchema}.df_scenario_usage_filter_to_rh_account_numbers_map alter created_by_user drop default")
            sql("alter table ${dbAppsSchema}.df_scenario_usage_filter_to_rh_account_numbers_map alter updated_by_user drop default")

            sql("alter table ${dbAppsSchema}.df_scenario_usage_filter_to_usage_batches_ids_map alter created_by_user drop default")
            sql("alter table ${dbAppsSchema}.df_scenario_usage_filter_to_usage_batches_ids_map alter updated_by_user drop default")
        }
    }

    changeSet(id: '2018-05-23-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment("B-43663: FDA: Measure performance of critical areas: " +
                "add index by df_scenario_uid in df_usage and df_usage_archive tables")

        createIndex(indexName: 'ix_df_usage_df_scenario_uid', schemaName: dbAppsSchema, tableName: 'df_usage',
                tablespace: dbIndexTablespace) {
            column(name: 'df_scenario_uid')
        }

        createIndex(indexName: 'ix_df_usage_archive_df_scenario_uid', schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                tablespace: dbIndexTablespace) {
            column(name: 'df_scenario_uid')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_usage_archive_df_scenario_uid")
            sql("drop index ${dbAppsSchema}.ix_df_usage_df_scenario_uid")
        }
    }

    changeSet(id: '2018-05-31-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-43639 FDA: Research Status Report: implement view to store data for Research Status Report")

        createView(viewName: 'v_research_status_report', schemaName: dbAppsSchema) {
            """select
                    nestedReport.batch_name, 
                    nestedReport.rro_account_number,
                    nestedReport.rro_name,
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
                            rh.name rro_name,
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
                        left join ${dbAppsSchema}.df_rightsholder rh on b.rro_account_number = rh.rh_account_number  
                        where status_ind in ('WORK_NOT_FOUND', 'WORK_RESEARCH', 'SENT_FOR_RA','RH_NOT_FOUND')
                        group by b.name, rro_account_number, rh.name, payment_date, b.gross_amount, u.status_ind) nestedReport
                group by nestedReport.batch_name, nestedReport.rro_account_number, nestedReport.rro_name, nestedReport.payment_date, nestedReport.gross_amount
                order by nestedReport.payment_date desc, nestedReport.batch_name"""
        }

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2018-06-01-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-30192 FDA: Batch Summary Report (DVPR Replacement): implement view to generate report")

        createView(viewName: 'v_batch_summary_report', schemaName: dbAppsSchema) {
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
                order by report.payment_date desc, report.batch_name"""
        }

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2018-06-04-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-44096 FDA: Address performance issues related to export and scenario functionality: " +
                "improve performance of usages filtering by batch")

        createIndex(indexName: 'ix_df_usage_df_usage_batch_uid', schemaName: dbAppsSchema, tableName: 'df_usage',
                tablespace: dbIndexTablespace) {
            column(name: 'df_usage_batch_uid')
        }

        createIndex(indexName: 'ix_df_usage_archive_df_usage_batch_uid', schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                tablespace: dbIndexTablespace) {
            column(name: 'df_usage_batch_uid')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_usage_archive_df_usage_batch_uid")
            sql("drop index ${dbAppsSchema}.ix_df_usage_df_usage_batch_uid")
        }
    }

    changeSet(id: '2018-06-06-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-43639 FDA: Research Status Report: make changes based on comments in CR-DIST-FOREIGN-62")

        createView(viewName: 'v_research_status_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
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
                order by nestedReport.payment_date desc, nestedReport.batch_name"""
        }

        rollback {
            createView(viewName: 'v_research_status_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
                """select
                    nestedReport.batch_name, 
                    nestedReport.rro_account_number,
                    nestedReport.rro_name,
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
                            rh.name rro_name,
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
                        left join ${dbAppsSchema}.df_rightsholder rh on b.rro_account_number = rh.rh_account_number  
                        where status_ind in ('WORK_NOT_FOUND', 'WORK_RESEARCH', 'SENT_FOR_RA','RH_NOT_FOUND')
                        group by b.name, rro_account_number, rh.name, payment_date, b.gross_amount, u.status_ind) nestedReport
                group by nestedReport.batch_name, nestedReport.rro_account_number, nestedReport.rro_name, nestedReport.payment_date, nestedReport.gross_amount
                order by nestedReport.payment_date desc, nestedReport.batch_name"""
            }
        }
    }

    changeSet(id: '2018-06-07-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('B-43661 Tech Debt: FDA: update status from LOCKED to SENT_TO_LM for usages in df_usage_archive table')

        update(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'status_ind', value: 'SENT_TO_LM')
            where "status_ind = 'LOCKED'"
        }

        rollback {
            update(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
                column(name: 'status_ind', value: 'LOCKED')
                where "status_ind = 'SENT_TO_LM'"
            }
        }
    }

    changeSet(id: '2018-06-12-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('B-43661 Tech Debt: FDA: set status NEW and update related columns to usages with wr_wrk_inst more than 9 symbols')

        update(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'status_ind', value: 'NEW')
            column(name: 'wr_wrk_inst', value: 'null')
            column(name: 'rh_account_number', value: 'null')
            column(name: 'df_scenario_uid', value: 'null')
            column(name: 'payee_account_number', value: 'null')
            column(name: 'net_amount', value: '0.0000000000')
            column(name: 'service_fee', value: 'null')
            column(name: 'service_fee_amount', value: '0.0000000000')
            column(name: 'is_rh_participating_flag', value: 'false')
            where "wr_wrk_inst > 999999999"
        }

        rollback ""
    }

    changeSet(id: '2018-06-20-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-43662 Tech Debt: FDA: adjust data ordering for Batch Summary and Research Status reports")

        createView(viewName: 'v_research_status_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
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
        }

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

        rollback {
            createView(viewName: 'v_research_status_report', replaceIfExists: 'true', schemaName: dbAppsSchema) {
                """select
                    nestedReport.batch_name, 
                    nestedReport.rro_account_number,
                    nestedReport.rro_name,
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
                            rh.name rro_name,
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
                        left join ${dbAppsSchema}.df_rightsholder rh on b.rro_account_number = rh.rh_account_number  
                        where status_ind in ('WORK_NOT_FOUND', 'WORK_RESEARCH', 'SENT_FOR_RA','RH_NOT_FOUND')
                        group by b.name, rro_account_number, rh.name, payment_date, b.gross_amount, u.status_ind) nestedReport
                group by nestedReport.batch_name, nestedReport.rro_account_number, nestedReport.rro_name, nestedReport.payment_date, nestedReport.gross_amount
                order by nestedReport.payment_date desc, nestedReport.batch_name"""
            }

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
                order by report.payment_date desc, report.batch_name"""
            }
        }
    }
}
