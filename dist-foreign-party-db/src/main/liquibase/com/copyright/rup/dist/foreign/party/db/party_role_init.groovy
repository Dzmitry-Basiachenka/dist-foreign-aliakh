databaseChangeLog {

    changeSet(id: '2017-01-13-01', author: 'Aliaksei Pchelnikau <apchelnikau@copyright.com>') {
        comment("Inserts Roles for Foreign Distribution Application")

        insert(schemaName: dbCommonSchema, tableName: 'cm_role') {
            column(name: 'cm_role_uid', value: 'baseline-fda-view-only')
            column(name: 'role_name', value: 'FDA_VIEW_ONLY')
            column(name: 'role_descr', value: 'View only role for FDA')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role') {
                where "cm_role_uid = 'baseline-fda-view-only'"
            }
        }
    }
}
