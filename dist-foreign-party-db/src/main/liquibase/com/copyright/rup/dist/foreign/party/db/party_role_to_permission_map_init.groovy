databaseChangeLog {

    changeSet(id: '2017-01-13-02', author: 'Aliaksei Pchelnikau <apchelnikau@copyright.com>') {
        comment("Map Permissions to Roles for Foreign Distribution Application")

        // Mapping for FDA view only role
        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-view-only')
            column(name: 'cm_permission_uid', value: 'baseline-fda-access-application')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-view-only' and cm_permission_uid = 'baseline-fda-access-application'"
            }
        }
    }
}
