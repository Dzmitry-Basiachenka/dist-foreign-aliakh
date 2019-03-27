databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-03-11-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-46040 FDA: Assign title classification to titles in NTS distribution: add permission to assign classification")

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-assign-classification')
            column(name: 'permission_name', value: 'FDA_ASSIGN_CLASSIFICATION')
            column(name: 'permission_descr', value: 'Permission to assign classification')
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
            column(name: 'cm_permission_uid', value: 'baseline-fda-assign-classification')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-specialist' and cm_permission_uid = 'baseline-fda-assign-classification'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-assign-classification'"
            }
        }
    }

    changeSet(id: '2019-03-27-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: " +
                "add permission to Create and Delete additional Funds")

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-create-delete-fund')
            column(name: 'permission_name', value: 'FDA_CREATE_DELETE_FUND')
            column(name: 'permission_descr', value: 'Permission to create and delete additional Funds')
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
            column(name: 'cm_permission_uid', value: 'baseline-fda-create-delete-fund')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-specialist' and cm_permission_uid = 'baseline-fda-create-delete-fund'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-create-delete-fund'"
            }
        }
    }
}
