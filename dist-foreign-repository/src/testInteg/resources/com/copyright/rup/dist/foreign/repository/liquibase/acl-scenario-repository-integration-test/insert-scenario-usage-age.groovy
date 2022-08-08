databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-06-30-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testInsertScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '9695d1a0-407b-485d-ad0e-7471f4cb5607')
            column(name: 'name', value: 'ACL Fund Pool 202112')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'fb1d0635-b52d-4d4a-9412-d370481657d8')
            column(name: 'name', value: 'ACL Usage Batch 202112')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '4448e5d8-a008-4481-abb8-e75af1329637')
            column(name: 'name', value: 'ACL Grant Set 202112')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '7318b083-278b-44f3-8d3c-7fd22083443a')
            column(name: 'df_acl_fund_pool_uid', value: '9695d1a0-407b-485d-ad0e-7471f4cb5607')
            column(name: 'df_acl_usage_batch_uid', value: 'fb1d0635-b52d-4d4a-9412-d370481657d8')
            column(name: 'df_acl_grant_set_uid', value: '4448e5d8-a008-4481-abb8-e75af1329637')
            column(name: 'name', value: 'ACL Scenario 201806')
            column(name: 'description', value: 'Description')
            column(name: 'period_end_date', value: 202012)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'copied_from', value: "Copied Scenario Name")
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'fbcc3113-3f5a-41a0-993e-fcdec1032eaa')
            column(name: 'df_acl_scenario_uid', value: '7318b083-278b-44f3-8d3c-7fd22083443a')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '556537fb-0809-45ea-b653-9768d5823111')
            column(name: 'df_acl_scenario_uid', value: '7318b083-278b-44f3-8d3c-7fd22083443a')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        rollback {
            dbRollback
        }
    }
}
