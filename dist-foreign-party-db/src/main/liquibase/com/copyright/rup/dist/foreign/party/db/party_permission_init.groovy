databaseChangeLog {

    changeSet(id: '2017-01-13-00', author: 'Aliaksei Pchelnikau <apchelnikau@copyright.com>') {
        comment("Inserts Permissions for Foreign Distribution Application")

        //Permission to access FDA application
        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-access-application')
            column(name: 'permission_name', value: 'FDA_ACCESS_APPLICATION')
            column(name: 'permission_descr', value: 'Permission to access application')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-access-application'"
            }
        }
    }

    changeSet(id: '2017-02-22-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-29980 Add Specialist and Manager Role for FDA: insert load and delete usage permissions for " +
                "Foreign Distribution Application")

        //Permission to load usage
        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-load-usage')
            column(name: 'permission_name', value: 'FDA_LOAD_USAGE')
            column(name: 'permission_descr', value: 'Permission to load usage')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }
        //Permission to delete usage
        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-delete-usage')
            column(name: 'permission_name', value: 'FDA_DELETE_USAGE')
            column(name: 'permission_descr', value: 'Permission to delete usage')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid in ('baseline-fda-load-usage', 'baseline-fda-delete-usage')"
            }
        }
    }

    changeSet(id: '2017-02-28-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-29980 Add Specialist and Manager Role for FDA: insert create/edit scenario permission for Foreign Distribution Application")

        //Permission to create/edit scenario
        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-create-edit-scenario')
            column(name: 'permission_name', value: 'FDA_CREATE_EDIT_SCENARIO')
            column(name: 'permission_descr', value: 'Permission to create/edit scenario')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-create-edit-scenario'"
            }
        }
    }

    changeSet(id: '2017-03-16-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-30734 Delete an FAS Scenario: insert delete scenario permission for Foreign Distribution Application")

        //Permission to delete scenario
        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-delete-scenario')
            column(name: 'permission_name', value: 'FDA_DELETE_SCENARIO')
            column(name: 'permission_descr', value: 'Permission to delete scenario')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-delete-scenario'"
            }
        }
    }
}
