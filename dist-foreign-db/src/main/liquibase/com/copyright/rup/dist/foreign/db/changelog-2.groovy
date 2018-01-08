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
            column(name: 'service_fee', value: null)
            where "df_scenario_uid is null"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'service_fee', value: null)
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
}
