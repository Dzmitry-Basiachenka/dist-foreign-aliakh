databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-08-31-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testRemoveScenarioData')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '591639e8-92e0-42d9-97d7-b848d51d4b23')
            column(name: 'name', value: 'ACL Fund Pool 202012')
            column(name: 'period', value: 202012)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'd15f7d2d-2bfd-406f-85d6-e38c7261ac80')
            column(name: 'name', value: 'ACL Usage Batch 202012')
            column(name: 'distribution_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '26aa0758-dd8f-4e53-814e-d8879ab1a283')
            column(name: 'name', value: 'ACL Grant Set 202012')
            column(name: 'grant_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '8e4f30ca-48c8-4349-8d5a-ffc393a74a30')
            column(name: 'df_acl_fund_pool_uid', value: '591639e8-92e0-42d9-97d7-b848d51d4b23')
            column(name: 'df_acl_usage_batch_uid', value: 'd15f7d2d-2bfd-406f-85d6-e38c7261ac80')
            column(name: 'df_acl_grant_set_uid', value: '26aa0758-dd8f-4e53-814e-d8879ab1a283')
            column(name: 'name', value: 'ACL Scenario 202012')
            column(name: 'description', value: 'some description')
            column(name: 'period_end_date', value: 202012)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'copied_from', value: "Another Scenario")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '6faee2c3-439f-4988-9b8d-c01fd004fd5e')
            column(name: 'df_acl_scenario_uid', value: '8e4f30ca-48c8-4349-8d5a-ffc393a74a30')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '42213bec-4003-48b5-9618-a940cf46e7f9')
            column(name: 'df_acl_scenario_uid', value: '8e4f30ca-48c8-4349-8d5a-ffc393a74a30')
            column(name: 'period_prior', value: 1)
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '29bb130a-82ad-42eb-9c16-4b49b242b718')
            column(name: 'df_acl_scenario_uid', value: '8e4f30ca-48c8-4349-8d5a-ffc393a74a30')
            column(name: 'df_publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'dd624c1a-25e7-44ff-89d9-671df78e38ff')
            column(name: 'df_acl_scenario_uid', value: '8e4f30ca-48c8-4349-8d5a-ffc393a74a30')
            column(name: 'df_publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '1230bfe5-6919-498a-9aab-3d52c43db96b')
            column(name: 'df_acl_scenario_uid', value: '8e4f30ca-48c8-4349-8d5a-ffc393a74a30')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '4873539c-87c7-4508-be28-c993c91f9023')
            column(name: 'df_acl_scenario_uid', value: '8e4f30ca-48c8-4349-8d5a-ffc393a74a30')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'aggregate_licensee_class_id', value: 52)
        }

        rollback {
            dbRollback
        }
    }
}
