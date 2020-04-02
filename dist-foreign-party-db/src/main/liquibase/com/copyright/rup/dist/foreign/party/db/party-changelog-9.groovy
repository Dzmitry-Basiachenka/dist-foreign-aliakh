databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-02-04-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-52334 FDA: Load AACL Fund Pool: add permission to load AACL Fund Pool")

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

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-specialist' and cm_permission_uid = 'baseline-fda-load-aacl-fund-pool'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-load-aacl-fund-pool'"
            }
        }
    }

    changeSet(id: '2020-02-04-01', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment("B-54725 FDA: View/Delete AACL Fund Pool: add permission to delete AACL Fund Pool")

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

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-specialist' and cm_permission_uid = 'baseline-fda-delete-aacl-fund-pool'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-delete-aacl-fund-pool'"
            }
        }
    }

    changeSet(id: '2020-04-02-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment("B-55091 FDA: Modify the scenario popup view for AACL: add permission to view scenario")

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-view-scenario')
            column(name: 'permission_name', value: 'FDA_VIEW_SCENARIO')
            column(name: 'permission_descr', value: 'Permission to view scenario')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-view-only')
            column(name: 'cm_permission_uid', value: 'baseline-fda-view-scenario')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-specialist')
            column(name: 'cm_permission_uid', value: 'baseline-fda-view-scenario')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-manager')
            column(name: 'cm_permission_uid', value: 'baseline-fda-view-scenario')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid in ('baseline-fda-distribution-specialist', 'baseline-fda-distribution-manager', 'baseline-fda-view-only') " +
                        "and cm_permission_uid = 'baseline-fda-view-scenario'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-view-scenario'"
            }
        }
    }
}
