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
}
