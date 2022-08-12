databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-08-12-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testFindUsageAgeWeightsByScenarioId, testFindAclPublicationTypesByScenarioId, testFindDetailLicenseeClassesByScenarioId')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '8f7a3c37-e1bb-4a08-b90d-d3a5f4f1eb55')
            column(name: 'name', value: 'ACL Fund Pool 202012')
            column(name: 'period', value: 202012)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '7d552b60-78d0-4f50-9c7e-d992086562c8')
            column(name: 'name', value: 'ACL Usage Batch 202012')
            column(name: 'distribution_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '62663808-091c-4244-ae09-74fe3f5bfe3c')
            column(name: 'name', value: 'ACL Grant Set 202012')
            column(name: 'grant_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'cf1b6b34-0a67-4177-a456-4429f20fe2c5')
            column(name: 'df_acl_fund_pool_uid', value: '8f7a3c37-e1bb-4a08-b90d-d3a5f4f1eb55')
            column(name: 'df_acl_usage_batch_uid', value: '7d552b60-78d0-4f50-9c7e-d992086562c8')
            column(name: 'df_acl_grant_set_uid', value: '62663808-091c-4244-ae09-74fe3f5bfe3c')
            column(name: 'name', value: 'ACL Scenario 202012')
            column(name: 'description', value: 'some description')
            column(name: 'period_end_date', value: 202012)
            column(name: 'status_ind', value: 'ADDED_USAGES')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'copied_from', value: "Another Scenario")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '349b47b6-30e5-40d6-b6f3-9c5b9f919ba4')
            column(name: 'df_acl_scenario_uid', value: 'cf1b6b34-0a67-4177-a456-4429f20fe2c5')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '1a533285-e636-432f-9fd2-851a26a191b0')
            column(name: 'df_acl_scenario_uid', value: 'cf1b6b34-0a67-4177-a456-4429f20fe2c5')
            column(name: 'period_prior', value: 1)
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '38b7eb53-1ded-438c-8f4e-82432dbf894e')
            column(name: 'df_acl_scenario_uid', value: 'cf1b6b34-0a67-4177-a456-4429f20fe2c5')
            column(name: 'df_publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '8f2b05ac-5c33-4170-bf2a-398d76ed2464')
            column(name: 'df_acl_scenario_uid', value: 'cf1b6b34-0a67-4177-a456-4429f20fe2c5')
            column(name: 'df_publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '50ab9dd0-17a9-4518-889e-20206d4efd27')
            column(name: 'df_acl_scenario_uid', value: 'cf1b6b34-0a67-4177-a456-4429f20fe2c5')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '36441c1e-8b16-406d-8208-5d061007f81e')
            column(name: 'df_acl_scenario_uid', value: 'cf1b6b34-0a67-4177-a456-4429f20fe2c5')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'aggregate_licensee_class_id', value: 52)
        }

        rollback {
            dbRollback
        }
    }
}
