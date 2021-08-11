databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-06-30-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-67543 FDA: 'Action Reason' and 'Ineligible Reason' columns in DB: create df_udm_action_reason, df_udm_ineligible_reason tables")

        createTable(tableName: 'df_udm_action_reason', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing UDM usage action reasons') {

            column(name: 'df_udm_action_reason_uid', type: 'VARCHAR(255)', remarks: 'The identifier of UDM usage action reason') {
                constraints(nullable: false)
            }
            column(name: 'action_reason', type: 'VARCHAR(255)', remarks: 'Usage action reason') {
                constraints(nullable: false)
            }
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

        addPrimaryKey(schemaName: dbAppsSchema,
                tablespace: dbIndexTablespace,
                tableName: 'df_udm_action_reason',
                columnNames: 'df_udm_action_reason_uid',
                constraintName: 'pk_df_udm_action_reason_uid')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: '97fd8093-7f36-4a09-99f1-1bfe36a5c3f4')
            column(name: 'action_reason', value: 'Arbitrary RFA search result order')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: 'd7258aa1-801c-408f-8fff-685e5519a8db')
            column(name: 'action_reason', value: 'Metadata does not match Wr Wrk Inst')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: 'ccbd22af-32bf-4162-8145-d49eae14c800')
            column(name: 'action_reason', value: 'User is not reporting a Mkt Rsch Rpt')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: 'dd47a747-d6f6-4b53-af85-a4eb456d9b2e')
            column(name: 'action_reason', value: 'Survey location suggests wrong work chosen')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: 'afbe0339-c246-44a9-94ef-499a089aa939')
            column(name: 'action_reason', value: 'Replaced deleted work ID with surviving work ID')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: 'be6ece83-4739-479d-b468-5dcea822e1f8')
            column(name: 'action_reason', value: 'Incorrect/inappropriate Det Lic Class')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: 'bcb471ca-47c3-42a9-a4bc-18dc40e22c0f')
            column(name: 'action_reason', value: 'Survey length incorrectly documented')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: 'e27a95b4-2a3a-456c-a39e-5bac4ce2503e')
            column(name: 'action_reason', value: 'Accepting reported work based on user location')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: '5c4e5450-f566-4b88-bf40-cfc9cec7e69b')
            column(name: 'action_reason', value: 'Misc - See Comments')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: '4b6b3058-7608-433a-8041-3e5ad2601735')
            column(name: 'action_reason', value: 'Multiple works found')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: '89072d4d-ff4c-485b-995f-02f6dea61d61')
            column(name: 'action_reason', value: 'RH cannot be determined')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: 'bf3e9fdb-0573-4c4a-8194-741d433c6b56')
            column(name: 'action_reason', value: 'RH not participating')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: '61c3e557-6f26-491c-879c-b03d91927f73')
            column(name: 'action_reason', value: 'Title cannot be determined')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: '401560c0-4e38-4515-b515-d32c6d3dc4f7')
            column(name: 'action_reason', value: 'Used existing format')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: '04b99a98-56d3-4f59-bfcb-2c72d18ebbbc')
            column(name: 'action_reason', value: 'Created new work')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: 'dad6911c-3695-419c-96c0-adf8792699e3')
            column(name: 'action_reason', value: 'Assigned a Wr Wrk Inst')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: '8842c02a-a867-42b0-9533-681122e7478f')
            column(name: 'action_reason', value: 'Assigned Rights')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: '2993db0e-8ac9-46af-a4b0-10deefe4af7d')
            column(name: 'action_reason', value: 'Unidentifiable publisher')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: '1c8f6e43-2ca8-468d-8700-ce855e6cd8c0')
            column(name: 'action_reason', value: 'Aggregated Content')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_action_reason') {
            column(name: 'df_udm_action_reason_uid', value: 'f6ca01a9-b658-4258-86e6-d5ccd21fbe2c')
            column(name: 'action_reason', value: 'RH or work type on RRO’s Excluded list')
        }

        createTable(tableName: 'df_udm_ineligible_reason', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing UDM usage ineligible reasons') {

            column(name: 'df_udm_ineligible_reason_uid', type: 'VARCHAR(255)', remarks: 'The identifier of UDM usage ineligible reason') {
                constraints(nullable: false)
            }
            column(name: 'ineligible_reason', type: 'VARCHAR(255)', remarks: 'Usage ineligible reason') {
                constraints(nullable: false)
            }
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

        addPrimaryKey(schemaName: dbAppsSchema,
                tablespace: dbIndexTablespace,
                tableName: 'df_udm_ineligible_reason',
                columnNames: 'df_udm_ineligible_reason_uid',
                constraintName: 'pk_df_udm_ineligible_reason_uid')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
            column(name: 'df_udm_ineligible_reason_uid', value: 'b60a726a-39e8-4303-abe1-6816da05b858')
            column(name: 'ineligible_reason', value: 'Invalid survey')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
            column(name: 'df_udm_ineligible_reason_uid', value: '0d5a129c-0f8f-4e48-98b2-8b980cdb9333')
            column(name: 'ineligible_reason', value: 'Misc - See Comments')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
            column(name: 'df_udm_ineligible_reason_uid', value: '53f8661b-bf27-47a4-b30c-7666c0df02c5')
            column(name: 'ineligible_reason', value: 'RH cannot be determined')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
            column(name: 'df_udm_ineligible_reason_uid', value: '18fbee56-2f5c-450a-999e-54903c0bfb23')
            column(name: 'ineligible_reason', value: 'No Reported Use')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
            column(name: 'df_udm_ineligible_reason_uid', value: 'fd2f2dea-4018-48ee-a630-b8dfedbe857b')
            column(name: 'ineligible_reason', value: 'Public Domain')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
            column(name: 'df_udm_ineligible_reason_uid', value: '9a3adbf4-9fdb-41ba-9701-97de34a120c4')
            column(name: 'ineligible_reason', value: 'RH Denied/Refused/CRD/Lost')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
            column(name: 'df_udm_ineligible_reason_uid', value: 'b901cf15-e836-4b08-9732-191ee4bba14a')
            column(name: 'ineligible_reason', value: 'Systematic Copying')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
            column(name: 'df_udm_ineligible_reason_uid', value: 'e837be16-43ad-4241-b0df-44ecceae2b46')
            column(name: 'ineligible_reason', value: 'User-owned content')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
            column(name: 'df_udm_ineligible_reason_uid', value: 'cf1b711d-8c57-407c-b178-a8a2411c87e5')
            column(name: 'ineligible_reason', value: 'Unauthorized use')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
            column(name: 'df_udm_ineligible_reason_uid', value: 'a4df53dd-26d9-4a0e-956c-e95543707674')
            column(name: 'ineligible_reason', value: 'Work not found')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
            column(name: 'df_udm_ineligible_reason_uid', value: 'b040d59b-72c7-42fc-99d2-d406d5ea60f3')
            column(name: 'ineligible_reason', value: 'Multiple works found')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
            column(name: 'df_udm_ineligible_reason_uid', value: '659f48e1-ef67-4d12-b568-436efdf4ec70')
            column(name: 'ineligible_reason', value: 'Title cannot be determined')
        }

        rollback {
            dropTable(tableName: 'df_udm_action_reason', schemaName: dbAppsSchema)
            dropTable(tableName: 'df_udm_ineligible_reason', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2021-07-01-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-65865 FDA & UDM: update the usage information - Specialist/Manager role: add research_url column to " +
                "df_udm_usage table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'research_url', type: 'VARCHAR(1000)', remarks: 'Research URL')
        }

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2021-07-02-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-67543 FDA: 'Action Reason' and 'Ineligible Reason' columns in DB: add df_udm_action_reason_uid, df_udm_ineligible_reason_uid columns to df_udm_usage table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_action_reason_uid', type: 'VARCHAR(256)', remarks: 'The identifier of action reason')
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'df_udm_ineligible_reason_uid', type: 'VARCHAR(256)', remarks: 'The identifier of ineligible reason')
        }

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_udm_usage',
                baseColumnNames: 'df_udm_action_reason_uid',
                referencedTableName: 'df_udm_action_reason',
                referencedColumnNames: 'df_udm_action_reason_uid',
                constraintName: 'fk_df_udm_usage_2_df_udm_action_reason')

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_udm_usage',
                baseColumnNames: 'df_udm_ineligible_reason_uid',
                referencedTableName: 'df_udm_ineligible_reason',
                referencedColumnNames: 'df_udm_ineligible_reason_uid',
                constraintName: 'fk_df_udm_usage_2_df_udm_ineligible_reason')

        dropColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'ineligible_reason')

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'df_udm_action_reason_uid')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'df_udm_ineligible_reason_uid')

            addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
                column(name: 'ineligible_reason', type: 'VARCHAR(100)', remarks: 'The ineligible reason')
            }
        }
    }

    changeSet(id: '2021-07-09-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-67543 FDA: 'Action Reason' and 'Ineligible Reason' columns in DB: modify column size of df_udm_action_reason_uid, df_udm_ineligible_reason_uid columns in df_udm_usage table")

        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'df_udm_action_reason_uid', newDataType: 'VARCHAR(255)')
        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'df_udm_ineligible_reason_uid', newDataType: 'VARCHAR(255)')

        rollback {
            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'df_udm_action_reason_uid', newDataType: 'VARCHAR(256)')
            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'df_udm_ineligible_reason_uid', newDataType: 'VARCHAR(256)')
        }
    }

    changeSet(id: '2021-08-05-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-67321 Tech Debt: FDA: Increase column size of df_usage_audit.action_reason, df_udm_audit.action_reason columns")

        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_usage_audit', columnName: 'action_reason', newDataType: 'VARCHAR(3000)')
        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_udm_audit', columnName: 'action_reason', newDataType: 'VARCHAR(2000)')

        rollback {
            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_usage_audit', columnName: 'action_reason', newDataType: 'VARCHAR(1024)')
            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_udm_audit', columnName: 'action_reason', newDataType: 'VARCHAR(1024)')
        }
    }

    changeSet(id: '2021-08-11-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-67678 FDA & UDM: Usage Period update - tech story: add period column to df_udm_usage table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'period', type: 'NUMERIC(6)', remarks: 'The UDM period in YYYYMM format')
        }

        sql("""update ${dbAppsSchema}.df_udm_usage u 
                set period = b.period
                from ${dbAppsSchema}.df_udm_usage_batch b
                where u.df_udm_usage_batch_uid = b.df_udm_usage_batch_uid""")

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'period', columnDataType: 'NUMERIC(6)')

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage', columnName: 'period')
        }
    }
}
