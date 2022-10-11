databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-10-11-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-67527 FDA & ACL: Create Approver role for ACL: add Approver role with access to FDA application")

        insert(schemaName: dbCommonSchema, tableName: 'cm_role') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-approver')
            column(name: 'role_name', value: 'FDA_APPROVER')
            column(name: 'role_descr', value: 'Approver role for FDA')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-approver-permission')
            column(name: 'permission_name', value: 'FDA_APPROVER_PERMISSION')
            column(name: 'permission_descr', value: 'Permission for approver role')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-approver')
            column(name: 'cm_permission_uid', value: 'baseline-fda-approver-permission')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-approver')
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
                where "cm_role_uid = 'baseline-fda-distribution-approver'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-approver-permission'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_role') {
                where "cm_role_uid = 'baseline-fda-distribution-approver'"
            }
        }
    }
}
