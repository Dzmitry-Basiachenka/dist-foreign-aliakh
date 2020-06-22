databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-06-17-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-58560 FDA: View/Export NTS withdrawn details on FAS/FAS2 usage tab: change product family " +
                "from NTS to FAS/FAS2 for details with NTS_WITHDRAWN and TO_BE_DISTRIBUTED statuses")

        sql("""update ${dbAppsSchema}.df_usage u set product_family = b.product_family
                from ${dbAppsSchema}.df_usage_batch b
                where u.df_usage_batch_uid = b.df_usage_batch_uid
                and u.status_ind in ('NTS_WITHDRAWN', 'TO_BE_DISTRIBUTED')
                """)

        rollback {
            sql("""update ${dbAppsSchema}.df_usage u set product_family = 'NTS'
                where u.status_ind in ('NTS_WITHDRAWN', 'TO_BE_DISTRIBUTED')
                """)
        }
    }

    changeSet(id: '2020-06-19-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-58560 FDA: View/Export NTS withdrawn details on FAS/FAS2 usage tab: change product family " +
                "from NTS to FAS/FAS2 for details with NTS_WITHDRAWN and TO_BE_DISTRIBUTED statuses for archived details")

        sql("""update ${dbAppsSchema}.df_usage_archive ua set product_family = b.product_family
                from ${dbAppsSchema}.df_usage_batch b, ${dbAppsSchema}.df_usage_fas uf
                where ua.df_usage_batch_uid = b.df_usage_batch_uid
                    and ua.df_usage_archive_uid = uf.df_usage_fas_uid
                    and uf.df_fund_pool_uid is not null
                """)

        rollback {
            sql("""update ${dbAppsSchema}.df_usage_archive ua set product_family = 'NTS'
                from ${dbAppsSchema}.df_usage_fas uf
                where uf.df_usage_fas_uid = ua.df_usage_archive_uid
                    and uf.df_fund_pool_uid is not null
                """)
        }
    }
}
