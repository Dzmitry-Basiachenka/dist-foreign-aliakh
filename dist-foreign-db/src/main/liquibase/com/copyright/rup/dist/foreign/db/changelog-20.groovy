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
}
