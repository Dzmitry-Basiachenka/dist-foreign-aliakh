databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-09-23-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-58887 FDA: View and Delete SAL fund pool: add permission to delete Fund Pool")

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-delete-fund-pool')
            column(name: 'permission_name', value: 'FDA_DELETE_FUND_POOL')
            column(name: 'permission_descr', value: 'Permission to delete fund pool')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-specialist')
            column(name: 'cm_permission_uid', value: 'baseline-fda-delete-fund-pool')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-specialist' and cm_permission_uid = 'baseline-fda-delete-fund-pool'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-delete-fund-pool'"
            }
        }
    }

    changeSet(id: '2020-10-06-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-57772 FDA: Tech Debt: delete permissions FDA_LOAD_AACL_FUND_POOL, FDA_DELETE_AACL_FUND_POOL " +
                "and use instead of them FDA_LOAD_FUND_POOL, FDA_DELETE_FUND_POOL")

        delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            where "cm_role_uid = 'baseline-fda-distribution-specialist' and cm_permission_uid = 'baseline-fda-load-aacl-fund-pool'"
        }

        delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            where "cm_permission_uid = 'baseline-fda-load-aacl-fund-pool'"
        }

        delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            where "cm_role_uid = 'baseline-fda-distribution-specialist' and cm_permission_uid = 'baseline-fda-delete-aacl-fund-pool'"
        }

        delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            where "cm_permission_uid = 'baseline-fda-delete-aacl-fund-pool'"
        }

        rollback {
            insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                column(name: 'cm_permission_uid', value: 'baseline-fda-load-aacl-fund-pool')
                column(name: 'permission_name', value: 'FDA_LOAD_AACL_FUND_POOL')
                column(name: 'permission_descr', value: 'Permission to load AACL fund pool')
                column(name: 'cm_application_area_uid', value: 'FDA')
                column(name: 'cm_permission_type_uid', value: 'ACTION')
                column(name: 'created_by_user', value: 'system')
                column(name: 'updated_by_user', value: 'system')
                column(name: 'created_datetime', value: 'now()')
                column(name: 'updated_datetime', value: 'now()')
                column(name: 'record_version', value: '1')
            }

            insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                column(name: 'cm_role_uid', value: 'baseline-fda-distribution-specialist')
                column(name: 'cm_permission_uid', value: 'baseline-fda-load-aacl-fund-pool')
                column(name: 'is_permitted_flag', value: 'true')
                column(name: 'created_by_user', value: 'system')
                column(name: 'updated_by_user', value: 'system')
                column(name: 'created_datetime', value: 'now()')
                column(name: 'updated_datetime', value: 'now()')
                column(name: 'record_version', value: '1')
            }

            insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                column(name: 'cm_permission_uid', value: 'baseline-fda-delete-aacl-fund-pool')
                column(name: 'permission_name', value: 'FDA_DELETE_AACL_FUND_POOL')
                column(name: 'permission_descr', value: 'Permission to delete AACL fund pool')
                column(name: 'cm_application_area_uid', value: 'FDA')
                column(name: 'cm_permission_type_uid', value: 'ACTION')
                column(name: 'created_by_user', value: 'system')
                column(name: 'updated_by_user', value: 'system')
                column(name: 'created_datetime', value: 'now()')
                column(name: 'updated_datetime', value: 'now()')
                column(name: 'record_version', value: '1')
            }

            insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                column(name: 'cm_role_uid', value: 'baseline-fda-distribution-specialist')
                column(name: 'cm_permission_uid', value: 'baseline-fda-delete-aacl-fund-pool')
                column(name: 'is_permitted_flag', value: 'true')
                column(name: 'created_by_user', value: 'system')
                column(name: 'updated_by_user', value: 'system')
                column(name: 'created_datetime', value: 'now()')
                column(name: 'updated_datetime', value: 'now()')
                column(name: 'record_version', value: '1')
            }
        }
    }
}
