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

    changeSet(id: '2020-06-23-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-59772 FDA: Improve RMS integration: add and populate license_type column in the df_grant_priority table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', type: 'VARCHAR(128)', remarks: 'The license type')
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_PHOTOCOPY')
            where "type_of_use = 'NGT_PHOTOCOPY' and grant_product_family = 'TRS' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'ACL')
            where "type_of_use = 'PRINT' and grant_product_family = 'ACL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'JACDCL')
            where "type_of_use = 'PRINT' and grant_product_family = 'JACDCL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'MACL')
            where "type_of_use = 'PRINT' and grant_product_family = 'MACL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'VGW')
            where "type_of_use = 'PRINT' and grant_product_family = 'VGW' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'ACL')
            where "type_of_use = 'DIGITAL' and grant_product_family = 'ACL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'JACDCL')
            where "type_of_use = 'DIGITAL' and grant_product_family = 'JACDCL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'MACL')
            where "type_of_use = 'DIGITAL' and grant_product_family = 'MACL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'VGW')
            where "type_of_use = 'DIGITAL' and grant_product_family = 'VGW' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'AACL')
            where "type_of_use = 'PRINT' and grant_product_family = 'AACL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'AACL')
            where "type_of_use = 'DIGITAL' and grant_product_family = 'AACL' and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_PRINT_COURSE_MATERIALS')
            where "type_of_use = 'NGT_PRINT_COURSE_MATERIALS' and grant_product_family = 'NGT_PRINT_COURSE_MATERIALS' " +
                    "and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            where "type_of_use = 'NGT_ELECTRONIC_COURSE_MATERIALS' and grant_product_family = 'NGT_ELECTRONIC_COURSE_MATERIALS' " +
                    "and product_family in ('FAS', 'FAS2', 'NTS')"
        }

        addNotNullConstraint(schemaName: dbAppsSchema, tableName: 'df_grant_priority', columnName: 'license_type', columnDataType: 'VARCHAR(128)')

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'bedcc0e3-ce44-4ef6-a8fc-d89f82e6d1d4')
            column(name: 'product_family', value: 'AACL')
            column(name: 'grant_product_family', value: 'AACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '0')
            column(name: 'license_type', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '97f1ad37-7358-435b-b390-3913d8b1cb72')
            column(name: 'product_family', value: 'AACL')
            column(name: 'grant_product_family', value: 'AACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '1')
            column(name: 'license_type', value: 'AACL')
        }

        rollback {
            dropColumn(schemaName: dbAppsSchema, tableName: 'df_grant_priority', columnName: 'license_type')

            delete(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                where "df_grant_priority_uid in ('bedcc0e3-ce44-4ef6-a8fc-d89f82e6d1d4','97f1ad37-7358-435b-b390-3913d8b1cb72')"
            }
        }
    }
}
