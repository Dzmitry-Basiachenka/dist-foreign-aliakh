databaseChangeLog {

    changeSet(id: '2017-01-12-00', author: 'Aliaksei Pchelnikau <apchelnikau@copyright.com>') {
        comment("Inserts Application area")

        insert(schemaName: dbCommonSchema, tableName: 'cm_application_area') {
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'application_area_descr', value: 'Foreign Distribution Application')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_application_area') {
                where "cm_application_area_uid = 'FDA'"
            }
        }
    }
}
