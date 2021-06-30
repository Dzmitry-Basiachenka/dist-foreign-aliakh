databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-06-30-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-66422 FDA & UDM: Assign/Un-assign usages for research: add permission to allow assignment/un-assignment of usages")

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-assign-usage')
            column(name: 'permission_name', value: 'FDA_ASSIGN_USAGE')
            column(name: 'permission_descr', value: 'Permission to assign/un-assign UDM usages for research')
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
            column(name: 'cm_permission_uid', value: 'baseline-fda-assign-usage')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-manager')
            column(name: 'cm_permission_uid', value: 'baseline-fda-assign-usage')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-researcher')
            column(name: 'cm_permission_uid', value: 'baseline-fda-assign-usage')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid in ('baseline-fda-distribution-specialist', 'baseline-fda-distribution-manager', 'baseline-fda-distribution-researcher') " +
                        "and cm_permission_uid = 'baseline-fda-assign-usage'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-assign-usage'"
            }
        }
    }
}
