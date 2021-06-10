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
    
    changeSet(id: '2021-06-09-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-65860 FDA & UDM: Role-specific UDM view: add researcher role and permissions for new and current roles")

        insert(schemaName: dbCommonSchema, tableName: 'cm_role') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-researcher')
            column(name: 'role_name', value: 'FDA_RESEARCHER')
            column(name: 'role_descr', value: 'Researcher role for FDA')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-researcher-permission')
            column(name: 'permission_name', value: 'FDA_RESEARCHER_PERMISSION')
            column(name: 'permission_descr', value: 'Permission for researcher role')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-researcher')
            column(name: 'cm_permission_uid', value: 'baseline-fda-researcher-permission')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-manager-permission')
            column(name: 'permission_name', value: 'FDA_MANAGER_PERMISSION')
            column(name: 'permission_descr', value: 'Permission for manager role')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-manager')
            column(name: 'cm_permission_uid', value: 'baseline-fda-manager-permission')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-specialist-permission')
            column(name: 'permission_name', value: 'FDA_SPECIALIST_PERMISSION')
            column(name: 'permission_descr', value: 'Permission for specialist role')
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
            column(name: 'cm_permission_uid', value: 'baseline-fda-specialist-permission')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-view-only-permission')
            column(name: 'permission_name', value: 'FDA_VIEW_ONLY_PERMISSION')
            column(name: 'permission_descr', value: 'Permission for view-only role')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-view-only')
            column(name: 'cm_permission_uid', value: 'baseline-fda-view-only-permission')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role') {
                where "cm_role_uid = 'baseline-fda-distribution-researcher'"
            }

            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid in ('baseline-fda-view-only', 'baseline-fda-distribution-specialist'," +
                        "'baseline-fda-distribution-manager', 'baseline-fda-distribution-researcher') " +
                        "and cm_permission_uid in ('baseline-fda-view-only-permission', 'baseline-fda-specialist-permission'" +
                        "'baseline-fda-manager-permission', 'baseline-fda-researcher-permission')"
            }

            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid in ('baseline-fda-view-only-permission', 'baseline-fda-specialist-permission'" +
                        "'baseline-fda-manager-permission', 'baseline-fda-researcher-permission')"
            }
        }
    }

    changeSet(id: '2021-06-11-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-65860 FDA & UDM: Role-specific UDM view: add permission to access FDA application for researcher role")

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-researcher')
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
                where "cm_role_uid = 'baseline-fda-distribution-researcher'"
            }
        }
    }
}
