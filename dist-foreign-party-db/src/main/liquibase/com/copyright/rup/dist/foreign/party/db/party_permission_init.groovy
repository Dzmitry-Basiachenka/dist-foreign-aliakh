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
}
