databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-10-05-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("B-73116 FDA: CUP Flag: add content_unit_price_flag column to df_udm_value and df_udm_value_baseline tables")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'content_unit_price_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Content Unit Price Flag') {
                constraints(nullable: false)
            }
        }

        addColumn(schemaName: dbAppsSchema, tableName: 'df_udm_value_baseline') {
            column(name: 'content_unit_price_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Content Unit Price Flag') {
                constraints(nullable: false)
            }
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_udm_value', columnName: 'content_unit_price_flag')
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_udm_value_baseline', columnName: 'content_unit_price_flag')
        }
    }

    changeSet(id: '2022-10-13-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("B-75009 FDA: Liabilities by RH Report: add payee_account_number column to df_acl_share_detail table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'payee_account_number', type: 'NUMERIC(22,0)', remarks: 'The payee account number')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail', columnName: 'payee_account_number')
        }
    }

    changeSet(id: '2022-10-31-00', author: 'aazarenka <aazarenka@copyright.com>') {
        comment("B-57810 FDA: Send ACL scenario to LM: add df_acl_scenario_detail_archived")

        createTable(tableName: 'df_acl_scenario_detail_archived', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL scenario archived details') {

            column(name: 'df_acl_scenario_detail_archived_uid', type: 'VARCHAR(255)', remarks: 'The identifier of archived scenario detail') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_scenario_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario') {
                constraints(nullable: false)
            }
            column(name: 'period_end_date', type: 'NUMERIC(6)', remarks: 'The period in YYYYMM format') {
                constraints(nullable: false)
            }
            column(name: 'original_detail_id', type: 'VARCHAR(50)', remarks: 'The original detail id') {
                constraints(nullable: false)
            }
            column(name: 'wr_wrk_inst', type: 'NUMERIC(15)', remarks: 'The Wr Wrk Inst') {
                constraints(nullable: false)
            }
            column(name: 'system_title', type: 'VARCHAR(2000)', remarks: 'The system title') {
                constraints(nullable: false)
            }
            column(name: 'detail_licensee_class_id', type: 'INTEGER', remarks: 'The identifier of Detail Licensee Class') {
                constraints(nullable: false)
            }
            column(name: 'publication_type_uid', type: 'VARCHAR(255)', remarks: 'The identifier of Publication Type') {
                constraints(nullable: false)
            }
            column(name: 'pub_type_weight', type: 'NUMERIC(10,2)', remarks: 'Publication Type Weight')
            column(name: 'content_unit_price', type: 'NUMERIC (38,10)', remarks: 'The content unit price') {
                constraints(nullable: false)
            }
            column(name: 'usage_quantity', type: 'NUMERIC(38)', remarks: 'The usage quantity') {
                constraints(nullable: false)
            }
            column(name: 'usage_age_weight', type: 'numeric(10,5)', remarks: 'The usage age weight') {
                constraints(nullable: false)
            }
            column(name: 'weighted_copies', type: 'NUMERIC (38,10)', remarks: 'The weighted copies') {
                constraints(nullable: false)
            }
            column(name: 'survey_country', type: 'VARCHAR(100)', remarks: 'The survey country') {
                constraints(nullable: false)
            }
            column(name: 'reported_type_of_use', type: 'VARCHAR(128)', remarks: 'The reported type of use')
            column(name: 'type_of_use', type: 'VARCHAR(128)', remarks: 'The type of use')
            column(name: 'content_unit_price_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Content Unit Price Flag') {
                constraints(nullable: false)
            }
            column(name: 'price', type: 'NUMERIC(38,10)', remarks: 'Price')
            column(name: 'price_flag', type: 'BOOLEAN', defaultValue: false, remarks: 'Price Flag') {
                constraints(nullable: false)
            }
            column(name: 'content', type: 'NUMERIC (38, 10)', remarks: 'Content')
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

        addPrimaryKey(schemaName: dbAppsSchema,
                tablespace: dbIndexTablespace,
                tableName: 'df_acl_scenario_detail_archived',
                columnNames: 'df_acl_scenario_detail_archived_uid',
                constraintName: 'pk_df_acl_scenario_detail_archived_uid')

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2022-10-31-01', author: 'aazarenka <aazarenka@copyright.com>') {
        comment("B-57810 FDA: Send ACL scenario to LM: add df_acl_share_detail_archived")

        createTable(tableName: 'df_acl_share_detail_archived', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing ACL scenario share archived details') {

            column(name: 'df_acl_share_detail_archived_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario share archived detail') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_scenario_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario') {
                constraints(nullable: false)
            }
            column(name: 'df_acl_scenario_detail_archived_uid', type: 'VARCHAR(255)', remarks: 'The identifier of scenario archived detail') {
                constraints(nullable: false)
            }
            column(name: 'type_of_use', type: 'VARCHAR(128)', remarks: 'The type of use') {
                constraints(nullable: false)
            }
            column(name: 'rh_account_number', type: 'NUMERIC(22)', remarks: 'The rightsholder account number') {
                constraints(nullable: false)
            }
            column(name: 'payee_account_number', type: 'NUMERIC(22,0)', remarks: 'The payee account number')
            column(name: 'aggregate_licensee_class_id', type: 'INTEGER', remarks: 'The identifier of Aggregate Licensee Class') {
                constraints(nullable: false)
            }
            column(name: 'volume_weight', type: 'NUMERIC (38,10)', remarks: 'The volume weight')
            column(name: 'value_weight', type: 'NUMERIC (38,10)', remarks: 'The value weight')
            column(name: 'value_weight_denominator', type: 'NUMERIC (38,10)', remarks: 'The value weight denominator')
            column(name: 'volume_weight_denominator', type: 'NUMERIC (38,10)', remarks: 'The volume weight denominator')
            column(name: 'value_share', type: 'NUMERIC (38,10)', remarks: 'The value share')
            column(name: 'volume_share', type: 'NUMERIC (38,10)', remarks: 'The volume share')
            column(name: 'net_amount', type: 'NUMERIC (38,10)', remarks: 'The net amount')
            column(name: 'gross_amount', type: 'NUMERIC (38,10)', remarks: 'TThe gross amount')
            column(name: 'service_fee_amount', type: 'NUMERIC (38,10)', remarks: 'The service fee amount')
            column(name: 'detail_share', type: 'NUMERIC (38,10)', remarks: 'Detail share')
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
                tableName: 'df_acl_share_detail_archived',
                columnNames: 'df_acl_share_detail_archived_uid',
                constraintName: 'pk_df_acl_share_detail_archived_uid')

        rollback {
            //automatic rollback
        }
    }

    changeSet(id: '2022-10-31-02', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-57810 FDA: Send ACL scenario to LM: rename usage_quantity column to number_of_copies in " +
                "df_acl_scenario_detail_archived table")

        modifyDataType(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail_archived', columnName: 'usage_quantity', newDataType: 'numeric(38,5)')

        renameColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail_archived', oldColumnName: 'usage_quantity',
                newColumnName: 'number_of_copies', columnDataType: 'NUMERIC(38,5)')

        rollback {

            renameColumn(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail_archived', oldColumnName: 'number_of_copies',
                    newColumnName: 'usage_quantity', columnDataType: 'NUMERIC(38, 5)')

            modifyDataType(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail_archived', columnName: 'usage_quantity', newDataType: 'numeric(38)')
        }
    }
}
