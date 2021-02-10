databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-02-10-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-65718 FDA: Batch Processing View: add initial_usages_count column to df_usage_batch table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'initial_usages_count', type: 'INTEGER', remarks: 'The initial usages count for batch')
        }

        sql("""update ${dbAppsSchema}.df_usage_batch b
                set 
                    initial_usages_count = (select count(1) from apps.df_usage where df_usage_batch_uid = b.df_usage_batch_uid)
                                         + (select count(1) from apps.df_usage_archive where df_usage_batch_uid = b.df_usage_batch_uid)
                where product_family = 'FAS'"""
        )

        sql("""update ${dbAppsSchema}.df_usage_batch b
                set 
                    initial_usages_count = (select count(1) from apps.df_usage where df_usage_batch_uid = b.df_usage_batch_uid)
                                         + (select count(1) from apps.df_usage_archive where df_usage_batch_uid = b.df_usage_batch_uid)
                where product_family = 'FAS2'"""
        )

        sql("""update ${dbAppsSchema}.df_usage_batch b
                set 
                    initial_usages_count = (select count(1) from apps.df_usage where df_usage_batch_uid = b.df_usage_batch_uid)
                                         + (select count(1) from apps.df_usage_archive where df_usage_batch_uid = b.df_usage_batch_uid)
                where product_family = 'NTS'"""
        )

        sql("""update ${dbAppsSchema}.df_usage_batch b
                set 
                    initial_usages_count = (select count(1) from apps.df_usage where df_usage_batch_uid = b.df_usage_batch_uid)
                                         + (select count(1) from apps.df_usage_archive where df_usage_batch_uid = b.df_usage_batch_uid)
                where product_family = 'AACL'"""
        )

        sql("""update ${dbAppsSchema}.df_usage_batch b
                set 
                    initial_usages_count = (select count(1) from apps.df_usage where df_usage_batch_uid = b.df_usage_batch_uid)
                                         + (select count(1) from apps.df_usage_archive where df_usage_batch_uid = b.df_usage_batch_uid)
                where product_family = 'SAL'"""
        )

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_usage_batch', columnName: 'initial_usages_count')
        }
    }
}
