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

    changeSet(id: '2017-02-22-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-29980 Add Specialist and Manager Role for FDA: inserts Manager and Specialist roles for " +
                "Foreign Distribution Application")

        insert(schemaName: dbCommonSchema, tableName: 'cm_role') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-specialist')
            column(name: 'role_name', value: 'FDA_DISTRIBUTION_SPECIALIST')
            column(name: 'role_descr', value: 'Distribution specialist role for FDA')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-manager')
            column(name: 'role_name', value: 'FDA_DISTRIBUTION_MANAGER')
            column(name: 'role_descr', value: 'Distribution manager role for FDA')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role') {
                where "cm_role_uid in ('baseline-fda-distribution-specialist', 'baseline-fda-distribution-manager')"
            }
        }
    }
}
