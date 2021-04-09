databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-12-04-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: " +
                "add fund_pool column into df_usage_batch table, " +
                "remove not-null constraint from fiscal_year column from df_usage_batch table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'fund_pool', type: 'JSONB', remarks: 'The fund pool')
        }

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch',
            columnName: 'fiscal_year', columnDataType: 'NUMERIC(4,0)')

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch', columnName: 'fund_pool')

            addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch',
                columnName: 'fiscal_year', columnDataType: 'NUMERIC(4,0)')
        }
    }

    changeSet(id: '2018-12-10-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: " +
                "add not-null constraint to fiscal_year column in df_usage_batch table")

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch',
                columnName: 'fiscal_year', columnDataType: 'NUMERIC(4,0)')

        rollback {
            dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_batch',
                    columnName: 'fiscal_year', columnDataType: 'NUMERIC(4,0)')
        }
    }

    changeSet(id: '2018-12-19-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-47645 Tech Debt: FDA: Implement Liquibase script to add primary key constraint " +
                "to the column df_rightsholder_uid instead of the column rh_account_number")

        dropPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_rightsholder',
                constraintName: 'pk_rh_account_number')

        dropUniqueConstraint(schemaName: dbAppsSchema, tableName: 'df_rightsholder', constraintName: 'uk_df_rightsholder')

        addPrimaryKey(tablespace: dbIndexTablespace, schemaName: dbAppsSchema, tableName: 'df_rightsholder',
                columnNames: 'df_rightsholder_uid', constraintName: 'pk_df_rightsholder')

        rollback {
            dropPrimaryKey(schemaName: dbAppsSchema, tableName: 'df_rightsholder', constraintName: 'pk_df_rightsholder')

            addUniqueConstraint(schemaName: dbAppsSchema, tableName: 'df_rightsholder',
                    columnNames: 'df_rightsholder_uid', constraintName: 'uk_df_rightsholder')

            addPrimaryKey(tablespace: dbIndexTablespace, schemaName: dbAppsSchema, tableName: 'df_rightsholder',
                    columnNames: 'rh_account_number', constraintName: 'pk_rh_account_number')
        }
    }

    changeSet(id: '2018-12-19-01', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment("B-48319 FDA/LM: Apply optimization for getting grants from RMS: " +
                "create table df_grant_priority and populate values")

        createTable(tableName: 'df_grant_priority', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'The table to store grants priorities') {

            column(name: 'df_grant_priority_uid', type: 'VARCHAR(255)', remarks: 'The identifier of grant priority') {
                constraints(nullable: false)
            }
            column(name: 'product_family', type: 'VARCHAR(128)', remarks: 'Product family') {
                constraints(nullable: false)
            }
            column(name: 'market', type: 'VARCHAR(128)', remarks: 'Market')
            column(name: 'distribution', type: 'VARCHAR(128)', remarks: 'Distribution type')
            column(name: 'type_of_use', type: 'VARCHAR(128)', remarks: 'Type of use') {
                constraints(nullable: false)
            }
            column(name: 'priority', type: 'INTEGER', remarks: 'Grant priority') {
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

        addPrimaryKey(constraintName: 'pk_df_grant_priority', schemaName: dbAppsSchema,
                tableName: 'df_grant_priority', tablespace: dbIndexTableSpace, columnNames: 'df_grant_priority_uid')

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'd9309c58-50bd-4ab9-9c12-bcefd752ce43')
            column(name: 'product_family', value: 'FAS')
            column(name: 'market', value: 'CORPORATE')
            column(name: 'distribution', value: 'EXTERNAL')
            column(name: 'type_of_use', value: 'NGT_PHOTOCOPY')
            column(name: 'priority', value: '0')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '1174d1d0-f3bb-4e80-8b92-08ab97457a97')
            column(name: 'product_family', value: 'FAS')
            column(name: 'market', value: 'CORPORATE')
            column(name: 'distribution', value: 'INTERNAL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '2367d120-e982-4ee6-a67f-7d0dd0192c69')
            column(name: 'product_family', value: 'FAS')
            column(name: 'market', value: 'CORPORATE')
            column(name: 'distribution', value: 'INTERNAL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'c01dd974-d7b9-489e-91e2-2b4174eec41d')
            column(name: 'product_family', value: 'FAS')
            column(name: 'market', value: 'ACADEMIC')
            column(name: 'distribution', value: 'INTERNAL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '311caf82-e43f-402e-b4ff-a54363d0f19f')
            column(name: 'product_family', value: 'FAS')
            column(name: 'market', value: 'ACADEMIC')
            column(name: 'distribution', value: 'INTERNAL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '4')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'e3389fff-1cdc-44f5-9bb2-ccd195eced26')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'market', value: 'CORPORATE')
            column(name: 'distribution', value: 'EXTERNAL')
            column(name: 'type_of_use', value: 'NGT_PHOTOCOPY')
            column(name: 'priority', value: '0')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '8332cdb1-9b61-4c57-9c4b-d6acd6a0d07a')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'market', value: 'CORPORATE')
            column(name: 'distribution', value: 'INTERNAL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '52945c7b-2de1-4a3e-9da6-d92309f7feeb')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'market', value: 'CORPORATE')
            column(name: 'distribution', value: 'INTERNAL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'c73571a9-86b1-4739-8979-58897afd7809')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'market', value: 'ACADEMIC')
            column(name: 'distribution', value: 'INTERNAL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '85db0f2f-4f02-4054-97f3-5d470d29ecb4')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'market', value: 'ACADEMIC')
            column(name: 'distribution', value: 'INTERNAL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '4')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '8003d592-9c63-489f-bda3-72be51ce3ad6')
            column(name: 'product_family', value: 'NTS')
            column(name: 'market', value: 'CORPORATE')
            column(name: 'distribution', value: 'EXTERNAL')
            column(name: 'type_of_use', value: 'NGT_PHOTOCOPY')
            column(name: 'priority', value: '0')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '63311aac-ffa0-456b-ae6e-32f8c3fed08f')
            column(name: 'product_family', value: 'NTS')
            column(name: 'market', value: 'CORPORATE')
            column(name: 'distribution', value: 'INTERNAL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '429ebca7-1265-4451-adfe-197fcbecf6bf')
            column(name: 'product_family', value: 'NTS')
            column(name: 'market', value: 'CORPORATE')
            column(name: 'distribution', value: 'INTERNAL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'fa9d5c79-d551-4f27-aab6-540c16dc6fe3')
            column(name: 'product_family', value: 'NTS')
            column(name: 'market', value: 'ACADEMIC')
            column(name: 'distribution', value: 'INTERNAL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '8d79e173-cf13-4c2b-b920-38d31665ede1')
            column(name: 'product_family', value: 'NTS')
            column(name: 'market', value: 'ACADEMIC')
            column(name: 'distribution', value: 'INTERNAL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '4')
        }

        rollback {
            dropTable(schemaName: dbAppsSchema, tableName: 'df_grant_priority')
        }
    }

    changeSet(id: '2019-01-09-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-48525 FDA: Update Logic for getting FAS Rightsholders: " +
                "Implement Liquibase script to add new records into the table df_grant_priority")

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '40f97da2-79f6-4917-b683-1cfa0fccd669')
            column(name: 'product_family', value: 'FAS')
            column(name: 'market', value: 'ACADEMIC')
            column(name: 'distribution', value: 'EXTERNAL')
            column(name: 'type_of_use', value: 'NGT_PRINT_COURSE_MATERIALS')
            column(name: 'priority', value: '5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '2ad646de-d6c0-4eae-9a85-9c65f5c7e46b')
            column(name: 'product_family', value: 'FAS')
            column(name: 'market', value: 'ACADEMIC')
            column(name: 'distribution', value: 'EXTERNAL')
            column(name: 'type_of_use', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            column(name: 'priority', value: '6')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '5549a633-57a9-49b6-a54d-9cbb1e0d13c9')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'market', value: 'ACADEMIC')
            column(name: 'distribution', value: 'EXTERNAL')
            column(name: 'type_of_use', value: 'NGT_PRINT_COURSE_MATERIALS')
            column(name: 'priority', value: '5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'b1b7c100-42f8-49f7-ab9f-a89e92a011c1')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'market', value: 'ACADEMIC')
            column(name: 'distribution', value: 'EXTERNAL')
            column(name: 'type_of_use', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            column(name: 'priority', value: '6')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '76ce3849-1f70-40a0-b42b-fa77efbba73f')
            column(name: 'product_family', value: 'NTS')
            column(name: 'market', value: 'ACADEMIC')
            column(name: 'distribution', value: 'EXTERNAL')
            column(name: 'type_of_use', value: 'NGT_PRINT_COURSE_MATERIALS')
            column(name: 'priority', value: '5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')
            column(name: 'product_family', value: 'NTS')
            column(name: 'market', value: 'ACADEMIC')
            column(name: 'distribution', value: 'EXTERNAL')
            column(name: 'type_of_use', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            column(name: 'priority', value: '6')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                where "df_grant_priority_uid in (" +
                        "'40f97da2-79f6-4917-b683-1cfa0fccd669'," +
                        "'2ad646de-d6c0-4eae-9a85-9c65f5c7e46b'," +
                        "'5549a633-57a9-49b6-a54d-9cbb1e0d13c9'," +
                        "'b1b7c100-42f8-49f7-ab9f-a89e92a011c1'," +
                        "'76ce3849-1f70-40a0-b42b-fa77efbba73f'," +
                        "'9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a')"
            }
        }
    }

    changeSet(id: '2019-02-13-00', author: 'Uladzislau Shalamistski <ushalamitski@copyright.com>') {
        comment("B-47964 FDA: Tag details with category supplied by RRO when loading batches/receipts: and comment column to db tables")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'comment', type: 'VARCHAR(100)', remarks: 'Comment')
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'comment', type: 'VARCHAR(100)', remarks: 'Comment')
        }
    }

    changeSet(id: '2019-02-18-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment("B-46540 FDA: Migrate usage from SC: " +
                "remove not-null constraints in table df_usage_archive " +
                "from columns payee_account_number and is_rh_participating_flag")

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                columnName: 'payee_account_number', columnDataType: 'NUMERIC(22,0)')

        dropNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_usage_archive',
                columnName: 'is_rh_participating_flag', columnDataType: 'BOOLEAN')

        rollback ""
    }

    changeSet(id: '2019-02-28-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('B-48636 FDA: Consume RMS Rights Service that provides mapping between Product and Product Family: ' +
                'add grant_product_family column into df_grant_priority table and drop market and distribution columns')

        addColumn(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'grant_product_family', type: 'VARCHAR(128)', remarks: 'Grant Product Family')
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'grant_product_family', value: 'TRS')
            where "market = 'CORPORATE' and distribution = 'EXTERNAL' and type_of_use = 'NGT_PHOTOCOPY'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'grant_product_family', value: 'ACLPRINT')
            where "market = 'CORPORATE' and distribution = 'INTERNAL' and type_of_use = 'PRINT'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'grant_product_family', value: 'ACLDIGITAL')
            where "market = 'CORPORATE' and distribution = 'INTERNAL' and type_of_use = 'DIGITAL'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'grant_product_family', value: 'AACL')
            where "market = 'ACADEMIC' and distribution = 'INTERNAL' and type_of_use in ('PRINT', 'DIGITAL')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'grant_product_family', value: 'RLS')
            where "market = 'ACADEMIC' and distribution = 'EXTERNAL' " +
                    "and type_of_use in ('NGT_PRINT_COURSE_MATERIALS', 'NGT_ELECTRONIC_COURSE_MATERIALS')"
        }

        dropColumn(schemaName: dbAppsSchema, tableName: 'df_grant_priority', columnName: 'market')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_grant_priority', columnName: 'distribution')

        rollback {
            addColumn(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'market', type: 'VARCHAR(128)', remarks: 'Market')
                column(name: 'distribution', type: 'VARCHAR(128)', remarks: 'Distribution type')
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'market', value: 'CORPORATE')
                column(name: 'distribution', value: 'EXTERNAL')
                where "grant_product_family = 'TRS'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'market', value: 'CORPORATE')
                column(name: 'distribution', value: 'INTERNAL')
                where "grant_product_family in ('ACLPRINT', 'ACLDIGITAL')"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'market', value: 'ACADEMIC')
                column(name: 'distribution', value: 'INTERNAL')
                where "grant_product_family = 'AACL'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'market', value: 'ACADEMIC')
                column(name: 'distribution', value: 'EXTERNAL')
                where "grant_product_family = 'RLS'"
            }

            dropColumn(schemaName: dbAppsSchema, tableName: 'df_grant_priority', columnName: 'grant_product_family')
        }
    }

    changeSet(id: '2019-03-13-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('B-49462 Tech Debt: FDA: update grant_product_family value in df_grant_priority table ' +
                'for NGT_ELECTRONIC_COURSE_MATERIALS and NGT_PRINT_COURSE_MATERIALS type of uses')

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'grant_product_family', value: 'ECC')
            where "type_of_use = 'NGT_ELECTRONIC_COURSE_MATERIALS'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'grant_product_family', value: 'APS')
            where "type_of_use = 'NGT_PRINT_COURSE_MATERIALS'"
        }

        rollback {
            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'grant_product_family', value: 'RLS')
                where "type_of_use in ('NGT_ELECTRONIC_COURSE_MATERIALS', 'NGT_PRINT_COURSE_MATERIALS')"
            }
        }
    }

    changeSet(id: '2019-03-14-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('B-49462 Tech Debt: FDA: update grant_product_family value in df_grant_priority table ' +
                'for NGT_ELECTRONIC_COURSE_MATERIALS and NGT_PRINT_COURSE_MATERIALS type of uses')

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'grant_product_family', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            where "type_of_use = 'NGT_ELECTRONIC_COURSE_MATERIALS'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'grant_product_family', value: 'NGT_PRINT_COURSE_MATERIALS')
            where "type_of_use = 'NGT_PRINT_COURSE_MATERIALS'"
        }

        rollback {
            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'grant_product_family', value: 'ECC')
                where "type_of_use = 'NGT_ELECTRONIC_COURSE_MATERIALS'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'grant_product_family', value: 'APS')
                where "type_of_use = 'NGT_PRINT_COURSE_MATERIALS'"
            }
        }
    }
}
