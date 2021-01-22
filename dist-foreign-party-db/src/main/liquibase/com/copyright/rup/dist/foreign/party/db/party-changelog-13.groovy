databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-01-22-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-64632 FDA: Edit Scenario name: add permission to edit scenario name")

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-edit-scenario-name')
            column(name: 'permission_name', value: 'FDA_EDIT_SCENARIO_NAME')
            column(name: 'permission_descr', value: 'Permission to edit scenario name')
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
            column(name: 'cm_permission_uid', value: 'baseline-fda-edit-scenario-name')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-specialist' and cm_permission_uid = 'baseline-fda-edit-scenario-name'"
            }

            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-edit-scenario-name'"
            }
        }
    }
}
