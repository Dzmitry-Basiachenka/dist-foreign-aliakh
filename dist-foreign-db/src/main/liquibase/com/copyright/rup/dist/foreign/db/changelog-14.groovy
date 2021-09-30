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

    changeSet(id: '2021-08-12-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-68059 FDA & UDM: Change rules for getting ACL rights from RH's: add records to df_grant_priority table for ACL_UDM")

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '6cfc3939-90a7-47aa-b624-537a6c8ee406')
            column(name: 'product_family', value: 'ACL_UDM')
            column(name: 'grant_product_family', value: 'ACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '0')
            column(name: 'license_type', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '1f9376b4-0063-4b4a-b9b9-4c0c4100e38c')
            column(name: 'product_family', value: 'ACL_UDM')
            column(name: 'grant_product_family', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '1')
            column(name: 'license_type', value: 'ACL')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                where "df_grant_priority_uid in ('6cfc3939-90a7-47aa-b624-537a6c8ee406','1f9376b4-0063-4b4a-b9b9-4c0c4100e38c')"
            }
        }
    }

    changeSet(id: '2021-08-24-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-68058 FDA & UDM: Log and display single usage edits in the UDM Audit: increase column size of df_udm_audit.action_reason column")

        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_udm_audit', columnName: 'action_reason', newDataType: 'VARCHAR(9000)')

        rollback {
            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_udm_audit', columnName: 'action_reason', newDataType: 'VARCHAR(2000)')
        }
    }

    changeSet(id: '2021-09-01-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-65866 FDA & UDM: publish usages to baseline: add is_baseline_flag, baseline_created_by_user and " +
                "baseline_created_datetime column to df_udm_usage table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'is_baseline_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Baseline Flag') {
                constraints(nullable: false)
            }
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'baseline_created_by_user', type: 'varchar(320)', remarks: 'The user name who created this record')
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_usage') {
            column(name: 'baseline_created_datetime', type: 'TIMESTAMPTZ', remarks: 'The date and time this record was created')
        }

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2021-09-02-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-66785 [Value] FDA & UDM: Create Publication Types table: add description and product_family columns into df_publication_type table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'description', type: 'VARCHAR(256)', remarks: 'The description')
            column(name: 'product_family', type: 'VARCHAR(128)', remarks: 'The product family')
        }

        update(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'product_family', value: 'AACL')
            where "product_family is null"
        }

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_publication_type', columnName: 'product_family',
                columnDataType: 'VARCHAR(128)')

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_publication_type', columnName: 'description')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_publication_type', columnName: 'product_family')
        }
    }

    changeSet(id: '2021-09-02-01', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-66785 [Value] FDA & UDM: Create Publication Types table: add ACL publication types into df_publication_type table")

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'name', value: 'BK')
            column(name: 'description', value: 'Book')
            column(name: 'product_family', value: 'ACL')
            column(name: 'weight', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'name', value: 'BK2')
            column(name: 'description', value: 'Book series')
            column(name: 'product_family', value: 'ACL')
            column(name: 'weight', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'name', value: 'NL')
            column(name: 'description', value: 'Newsletter')
            column(name: 'product_family', value: 'ACL')
            column(name: 'weight', value: '1.9')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'name', value: 'NP')
            column(name: 'description', value: 'Newspaper')
            column(name: 'product_family', value: 'ACL')
            column(name: 'weight', value: '3.5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: 'ad8df236-5200-4acf-be55-cf82cd342f14')
            column(name: 'name', value: 'OT')
            column(name: 'description', value: 'Other')
            column(name: 'product_family', value: 'ACL')
            column(name: 'weight', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: '34574f62-7922-48b9-b798-73bf5c3163da')
            column(name: 'name', value: 'SJ')
            column(name: 'description', value: 'Scholarly Journal')
            column(name: 'product_family', value: 'ACL')
            column(name: 'weight', value: '1.3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: '9c5c6797-a861-44ae-ada9-438acb20334d')
            column(name: 'name', value: 'STND')
            column(name: 'description', value: 'Standards')
            column(name: 'product_family', value: 'ACL')
            column(name: 'weight', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: 'c0db0a37-9854-495f-99b7-1e3486c232cb')
            column(name: 'name', value: 'TG')
            column(name: 'description', value: 'Trade Magazine/Journal')
            column(name: 'product_family', value: 'ACL')
            column(name: 'weight', value: '1.9')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: '0a4bcf78-95cb-445e-928b-e48ad12acfd2')
            column(name: 'name', value: 'TGB')
            column(name: 'description', value: 'Trade and Business News')
            column(name: 'product_family', value: 'ACL')
            column(name: 'weight', value: '1.9')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
            column(name: 'df_publication_type_uid', value: '56e31ea2-2f32-43a5-a0a7-9b1ecb1e73fe')
            column(name: 'name', value: 'TGC')
            column(name: 'description', value: 'Consumer magazine')
            column(name: 'product_family', value: 'ACL')
            column(name: 'weight', value: '2.7')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_publication_type') {
                where "product_family = 'ACL'"
            }
        }
    }

    changeSet(id: '2021-09-22-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-65962 [Value] FDA&UDM: Create and Populate Value batch: Implement db table for work values")

        createTable(tableName: 'df_udm_value', schemaName: dbAppsSchema, tablespace: dbDataTablespace, remarks: 'Table for storing UDM values') {

            column(name: 'df_udm_value_uid', type: 'VARCHAR(255)', remarks: 'The identifier of UDM value') {
                constraints(nullable: false)
            }
            column(name: 'period', type: 'NUMERIC(6)', remarks: 'The UDM value period in YYYYMM format') {
                constraints(nullable: false)
            }
            column(name: 'status_ind', type: 'VARCHAR(32)', remarks: 'The status of UDM value') {
                constraints(nullable: false)
            }
            column(name: 'rh_account_number', type: 'NUMERIC(22)', remarks: 'Rightsholder account number') {
                constraints(nullable: false)
            }
            column(name: 'wr_wrk_inst', type: 'NUMERIC(15)', remarks: 'Wr Wrk Inst') {
                constraints(nullable: false)
            }
            column(name: 'system_title', type: 'VARCHAR(2000)', remarks: 'System title')
            column(name: 'standard_number', type: 'VARCHAR(1000)', remarks: 'Standard number')
            column(name: 'standard_number_type', type: 'VARCHAR(50)', remarks: 'Standard number type')
            column(name: 'publication_type_uid', type: 'VARCHAR(255)', remarks: 'Publication type uid')
            column(name: 'assignee', type: 'VARCHAR(320)', remarks: 'The assignee')
            column(name: 'price', type: 'NUMERIC(38,10)', remarks: 'Price')
            column(name: 'price_in_usd', type: 'NUMERIC(38,10)', remarks: 'Price in USD')
            column(name: 'price_type', type: 'VARCHAR(100)', remarks: 'Price type')
            column(name: 'price_access_type', type: 'VARCHAR(100)', remarks: 'Price access type')
            column(name: 'price_year', type: 'INTEGER', remarks: 'Price year')
            column(name: 'price_comment', type: 'VARCHAR(1024)', remarks: 'Price comment')
            column(name: 'price_source', type: 'VARCHAR(1000)', remarks: 'Price source')
            column(name: 'price_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Price Flag') {
                constraints(nullable: false)
            }
            column(name: 'comment', type: 'VARCHAR(1024)', remarks: 'Comment')
            column(name: 'currency', type: 'VARCHAR(3)', remarks: 'The currency')
            column(name: 'currency_exchange_rate', type: 'NUMERIC(38,10)', remarks: 'Currency exchange rate')
            column(name: 'currency_exchange_rate_date', type: 'TIMESTAMPTZ', remarks: 'Currency exchange rate date')
            column(name: 'content', type: 'NUMERIC (38, 10)', remarks: 'Content')
            column(name: 'content_unit_price', type: 'NUMERIC (38, 10)', remarks: 'Content unit price')
            column(name: 'content_comment', type: 'VARCHAR(1024)', remarks: 'Content comment')
            column(name: 'content_source', type: 'VARCHAR(1000)', remarks: 'Content source')
            column(name: 'content_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Content Flag') {
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

        addForeignKeyConstraint(baseTableSchemaName: dbAppsSchema,
                referencedTableSchemaName: dbAppsSchema,
                baseTableName: 'df_udm_value',
                baseColumnNames: 'publication_type_uid',
                referencedTableName: 'df_publication_type',
                referencedColumnNames: 'df_publication_type_uid',
                constraintName: 'fk_df_udm_value_2_df_publication_type_uid')
    }

    changeSet(id: '2021-09-22-01', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-65962 [Value] FDA&UDM: Create and Populate Value batch: Implement db table for UDM age weights")

        createTable(tableName: 'df_udm_age_weight', schemaName: dbAppsSchema, tablespace: dbDataTablespace, remarks: 'Table for storing UDM age weights') {

            column(name: 'period_prior', type: 'INTEGER', remarks: 'Prior period') {
                constraints(nullable: false)
            }
            column(name: 'weight', type: 'NUMERIC (10, 5)', remarks: 'Value of age weight') {
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

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 1)
            column(name: 'weight', value: 1)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 2)
            column(name: 'weight', value: 0.8)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 3)
            column(name: 'weight', value: 0.8)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 4)
            column(name: 'weight', value: 0.6)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 5)
            column(name: 'weight', value: 0.6)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 6)
            column(name: 'weight', value: 0.4)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 7)
            column(name: 'weight', value: 0.4)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 8)
            column(name: 'weight', value: 0.2)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 9)
            column(name: 'weight', value: 0.2)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 10)
            column(name: 'weight', value: 0.1)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 11)
            column(name: 'weight', value: 0.05)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 12)
            column(name: 'weight', value: 0)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 13)
            column(name: 'weight', value: 0)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 14)
            column(name: 'weight', value: 0)
        }
        insert(schemaName: dbAppsSchema, tableName: 'df_udm_age_weight') {
            column(name: 'period_prior', value: 15)
            column(name: 'weight', value: 0)
        }

        rollback {
            dropTable(tableName: 'df_udm_age_weight', schemaName: dbAppsSchema)
        }
    }

    changeSet(id: '2021-09-28-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-65962 [Value] FDA&UDM: Create and Populate Value batch: add records to df_grant_priority table for ACL_UDM_VALUE and rename ACL_UDM to ACL_UDM_USAGE")

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'b3ccd3e3-c96e-449e-8d10-1852b5083bf9')
            column(name: 'product_family', value: 'ACL_UDM_VALUE')
            column(name: 'grant_product_family', value: 'ACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '0')
            column(name: 'license_type', value: 'ACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'c9515c05-8e7b-40e2-a131-b41321bcae8a')
            column(name: 'product_family', value: 'ACL_UDM_VALUE')
            column(name: 'grant_product_family', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '1')
            column(name: 'license_type', value: 'ACL')
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            where "product_family = 'ACL_UDM'"
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                where "df_grant_priority_uid in ('b3ccd3e3-c96e-449e-8d10-1852b5083bf9','c9515c05-8e7b-40e2-a131-b41321bcae8a')"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'product_family', value: 'ACL_UDM')
                where "product_family = 'ACL_UDM_USAGE'"
            }
        }
    }

    changeSet(id: '2021-09-30-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-65962 [Value] FDA&UDM: Create and Populate Value batch: add primary keys to df_udm_age_weight and df_udm_value")

        addPrimaryKey(
                tablespace: dbIndexTablespace,
                schemaName: dbAppsSchema,
                tableName: 'df_udm_value',
                columnNames: 'df_udm_value_uid',
                constraintName: 'pk_df_udm_value_uid')

        addPrimaryKey(
                tablespace: dbIndexTablespace,
                schemaName: dbAppsSchema,
                tableName: 'df_udm_age_weight',
                columnNames: 'period_prior',
                constraintName: 'pk_period_prior')
    }
}
