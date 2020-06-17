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
}
