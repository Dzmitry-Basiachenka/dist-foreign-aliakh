databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2017-11-10-00', author: 'Aliaksandr Radkevich <aradkevich@copyright.com>') {
        comment("B-36162 FDA: Backend for Identifying and excluding details for rightsholders that roll up to the " +
                "source RRO: add permission for excluding usages from a scenario")

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-exclude-from-scenario')
            column(name: 'permission_name', value: 'FDA_EXCLUDE_FROM_SCENARIO')
            column(name: 'permission_descr', value: 'Permission to exclude usages from a scenario')
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
            column(name: 'cm_permission_uid', value: 'baseline-fda-exclude-from-scenario')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-specialist' and cm_permission_uid = 'baseline-fda-exclude-from-scenario'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-exclude-from-scenario'"
            }
        }
    }

    changeSet(id: '2017-12-13-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-26839 Approval workflow for FAS Scenarios: add permission for submit to approval, reject and approve actions for scenario")

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-submit-scenario')
            column(name: 'permission_name', value: 'FDA_SUBMIT_SCENARIO')
            column(name: 'permission_descr', value: 'Permission to submit scenario for the approval')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-approve-scenario')
            column(name: 'permission_name', value: 'FDA_APPROVE_SCENARIO')
            column(name: 'permission_descr', value: 'Permission to approve scenario')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-reject-scenario')
            column(name: 'permission_name', value: 'FDA_REJECT_SCENARIO')
            column(name: 'permission_descr', value: 'Permission to reject scenario')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-specialist')
            column(name: 'cm_permission_uid', value: 'baseline-fda-submit-scenario')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-manager')
            column(name: 'cm_permission_uid', value: 'baseline-fda-approve-scenario')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-manager')
            column(name: 'cm_permission_uid', value: 'baseline-fda-reject-scenario')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-specialist' " +
                        "and cm_permission_uid = 'baseline-fda-submit-scenario'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-manager' and cm_permission_uid in (" +
                        "'baseline-fda-approve-scenario', 'baseline-fda-reject-scenario')"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid in ('baseline-fda-submit-scenario', 'baseline-fda-approve-scenario', " +
                        "'baseline-fda-reject-scenario')"
            }
        }
    }

    changeSet(id: '2018-01-03-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-39911 Send FAS Scenario to LM (UI): add permission for send scenario to LM action")

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-distribute-scenario')
            column(name: 'permission_name', value: 'FDA_DISTRIBUTE_SCENARIO')
            column(name: 'permission_descr', value: 'Permission to to send scenario to LM')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-specialist')
            column(name: 'cm_permission_uid', value: 'baseline-fda-distribute-scenario')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-specialist' " +
                        "and cm_permission_uid = 'baseline-fda-distribute-scenario'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-distribute-scenario'"
            }
        }
    }

    changeSet(id: '2018-01-24-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-29578 Reconcile RHs for IN_PROGRESS scenario: add permission for rightsholders reconciling")

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-reconcile-rightsholders')
            column(name: 'permission_name', value: 'FDA_RECONCILE_RIGHTSHOLDERS')
            column(name: 'permission_descr', value: 'Permission to reconcile rightsholders')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-specialist')
            column(name: 'cm_permission_uid', value: 'baseline-fda-reconcile-rightsholders')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-specialist' " +
                        "and cm_permission_uid = 'baseline-fda-reconcile-rightsholders'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-reconcile-rightsholders'"
            }
        }
    }

    changeSet(id: '2018-03-07-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-40635 Refresh in-progress scenario with newly eligible details: add permission for refreshing scenario")

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-refresh-scenario')
            column(name: 'permission_name', value: 'FDA_REFRESH_SCENARIO')
            column(name: 'permission_descr', value: 'Permission to refresh scenario')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-specialist')
            column(name: 'cm_permission_uid', value: 'baseline-fda-refresh-scenario')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-specialist' " +
                        "and cm_permission_uid = 'baseline-fda-refresh-scenario'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-refresh-scenario'"
            }
        }
    }

    changeSet(id: '2018-03-07-01', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-41626 Send details for research: add permission for sending for work research")

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-send-for-work-research')
            column(name: 'permission_name', value: 'FDA_SEND_FOR_WORK_RESEARCH')
            column(name: 'permission_descr', value: 'Permission for sending for work research')
            column(name: 'cm_application_area_uid', value: 'FDA')
            column(name: 'cm_permission_type_uid', value: 'ACTION')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        insert(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
            column(name: 'cm_role_uid', value: 'baseline-fda-distribution-specialist')
            column(name: 'cm_permission_uid', value: 'baseline-fda-send-for-work-research')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-specialist' " +
                        "and cm_permission_uid = 'baseline-fda-send-for-work-research'"
            }
            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-send-for-work-research'"
            }
        }
    }

    changeSet(id: '2017-03-27-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-41583 FDA Reload researched details: insert load researched usages permissions for Foreign Distribution Application")

        insert(schemaName: dbCommonSchema, tableName: 'cm_permission') {
            column(name: 'cm_permission_uid', value: 'baseline-fda-load-researched-usage')
            column(name: 'permission_name', value: 'FDA_LOAD_RESEARCHED_USAGE')
            column(name: 'permission_descr', value: 'Permission to load researched usage')
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
            column(name: 'cm_permission_uid', value: 'baseline-fda-load-researched-usage')
            column(name: 'is_permitted_flag', value: 'true')
            column(name: 'created_by_user', value: 'system')
            column(name: 'updated_by_user', value: 'system')
            column(name: 'created_datetime', value: 'now()')
            column(name: 'updated_datetime', value: 'now()')
            column(name: 'record_version', value: '1')
        }

        rollback {
            delete(schemaName: dbCommonSchema, tableName: 'cm_role_to_permission_map') {
                where "cm_role_uid = 'baseline-fda-distribution-specialist' and cm_permission_uid = 'baseline-fda-load-researched-usage'"
            }

            delete(schemaName: dbCommonSchema, tableName: 'cm_permission') {
                where "cm_permission_uid = 'baseline-fda-load-researched-usage'"
            }
        }
    }
}
