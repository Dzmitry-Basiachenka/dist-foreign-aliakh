databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2023-10-04-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("CDP-1887 FDA: Update value batch requirements for ACL works from previous periods: " +
                "add index by wr_wrk_inst for df_acl_grant_detail table")

        createIndex(indexName: 'ix_df_acl_grant_detail_wr_wrk_inst', schemaName: dbAppsSchema,
                tableName: 'df_acl_grant_detail', tablespace: dbIndexTablespace) {
            column(name: 'wr_wrk_inst')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_acl_grant_detail_wr_wrk_inst")
        }
    }

    changeSet(id: '2023-10-09-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("CDP-1887 FDA: Update value batch requirements for ACL works from previous periods: " +
                "add wr_wrk_inst field to df_acl_share_detail table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'wr_wrk_inst', type: 'NUMERIC(15)', remarks: 'The Wr Wrk Inst')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail', columnName: 'wr_wrk_inst')
        }
    }

    changeSet(id: '2023-10-13-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("CDP-1887 FDA: Update value batch requirements for ACL works from previous periods: " +
                "add index by type_of_use and df_acl_scenario_detail_uid to df_acl_share_detail table")

        createIndex(indexName: 'ix_df_acl_scenario_detail_uid_type_of_use', schemaName: dbAppsSchema,
                tableName: 'df_acl_share_detail', tablespace: dbIndexTablespace) {
            column(name: 'df_acl_scenario_detail_uid')
            column(name: 'type_of_use')
        }

        rollback {
            sql("drop index ${dbAppsSchema}.ix_df_acl_scenario_detail_uid_type_of_use")
        }
    }

    changeSet(id: '2023-10-20-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-80341 - FDA & ACLCI: Update the file upload with an additional field: add new " +
                "reported_number_of_students column")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aclci') {
            column(name: 'reported_number_of_students', type: 'NUMERIC(8)', remarks: 'Reported Number of Students',
                    defaultValue: '0'){
                constraints(nullable: false)
            }
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_aclci', columnName: 'reported_number_of_students')
        }
    }

    changeSet(id: '2023-11-11-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("CDP-1630 FDA: Implement improvements from SAR Review: add missing database index " +
                "for table df_udm_value_audit")

        createIndex(indexName: 'ix_df_udm_value_audit_df_udm_value_uid', schemaName: dbAppsSchema,
                tableName: 'df_udm_value_audit', tablespace: dbIndexTablespace) {
            column(name: 'df_udm_value_uid')
        }

        rollback {
            sql("DROP INDEX ${dbAppsSchema}.ix_df_udm_value_audit_df_udm_value_uid")
        }
    }

    changeSet(id: '2023-11-16-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("CDP-1630 FDA: Implement improvements from SAR Review: " +
                "add database index by rh_account_number for table df_usage")

        createIndex(indexName: 'ix_df_usage_rh_account_number', schemaName: dbAppsSchema,
                tableName: 'df_usage', tablespace: dbIndexTablespace) {
            column(name: 'rh_account_number')
        }

        rollback {
            sql("DROP INDEX ${dbAppsSchema}.ix_df_usage_rh_account_number")
        }
    }

    changeSet(id: '2023-11-17-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("CDP-1630 FDA: Implement improvements from SAR Review: " +
                "add database index by status_ind for table df_usage")

        createIndex(indexName: 'ix_df_usage_status_ind', schemaName: dbAppsSchema,
                tableName: 'df_usage', tablespace: dbIndexTablespace) {
            column(name: 'status_ind')
        }

        rollback {
            sql("DROP INDEX ${dbAppsSchema}.ix_df_usage_status_ind")
        }
    }

    changeSet(id: '2023-11-30-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("CDP-1630 FDA: CDP-1771 Tech Debt: remove: drop denominator columns from df_acl_share_detail table")

        dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail', columnName: 'value_weight_denominator')
        dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail', columnName: 'volume_weight_denominator')

        rollback {
            addColumn(tableName: 'df_acl_share_detail', schemaName: dbAppsSchema) {
                column(name: 'value_weight_denominator', type: 'NUMERIC (38,10)', remarks: 'The value weight denominator')
                column(name: 'volume_weight_denominator', type: 'NUMERIC (38,10)', remarks: 'The volume weight denominator')
            }
        }
    }

    changeSet(id: '2023-12-20-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("CDP-1968 FDA: ACL - Populate payees at point of Grant Set population: " +
                "add payee_account_number column to df_acl_grant_detail table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'payee_account_number', type: 'NUMERIC(22)', remarks: 'The payee account number')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail', columnName: 'payee_account_number')
        }
    }
}
