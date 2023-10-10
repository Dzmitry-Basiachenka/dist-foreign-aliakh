databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-10-27-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testUpdateStatus')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'a1f11569-563a-4af6-b7f8-7cbb31cbfcaa')
            column(name: 'name', value: 'ACL Fund Pool 202012')
            column(name: 'period', value: 202012)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '17899822-2d9f-46e6-ae41-c6cd9c0bee75')
            column(name: 'name', value: 'ACL Usage Batch 202012')
            column(name: 'distribution_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '3e410961-e802-4a81-a481-b6311cd9ce26')
            column(name: 'name', value: 'ACL Grant Set 202012')
            column(name: 'grant_period', value: 202012)
            column(name: 'periods', value: '[202006, 202012]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '3beec0c7-6783-4481-9648-c02f4ec1da5a')
            column(name: 'df_acl_fund_pool_uid', value: 'a1f11569-563a-4af6-b7f8-7cbb31cbfcaa')
            column(name: 'df_acl_usage_batch_uid', value: '17899822-2d9f-46e6-ae41-c6cd9c0bee75')
            column(name: 'df_acl_grant_set_uid', value: '3e410961-e802-4a81-a481-b6311cd9ce26')
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
            column(name: 'df_acl_scenario_uid', value: '3beec0c7-6783-4481-9648-c02f4ec1da5a')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '42213bec-4003-48b5-9618-a940cf46e7f9')
            column(name: 'df_acl_scenario_uid', value: '3beec0c7-6783-4481-9648-c02f4ec1da5a')
            column(name: 'period_prior', value: 1)
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '29bb130a-82ad-42eb-9c16-4b49b242b718')
            column(name: 'df_acl_scenario_uid', value: '3beec0c7-6783-4481-9648-c02f4ec1da5a')
            column(name: 'df_publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'dd624c1a-25e7-44ff-89d9-671df78e38ff')
            column(name: 'df_acl_scenario_uid', value: '3beec0c7-6783-4481-9648-c02f4ec1da5a')
            column(name: 'df_publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '1230bfe5-6919-498a-9aab-3d52c43db96b')
            column(name: 'df_acl_scenario_uid', value: '3beec0c7-6783-4481-9648-c02f4ec1da5a')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '4873539c-87c7-4508-be28-c993c91f9023')
            column(name: 'df_acl_scenario_uid', value: '3beec0c7-6783-4481-9648-c02f4ec1da5a')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'aggregate_licensee_class_id', value: 52)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '2e2e0eab-228c-4353-b613-6e23c126a959')
            column(name: 'df_acl_scenario_uid', value: '3beec0c7-6783-4481-9648-c02f4ec1da5a')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'GGN974GHHSB101')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'pub_type_weight', value: 1.9)
            column(name: 'content_unit_price', value: 6.3000000000)
            column(name: 'number_of_copies', value: 5)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 5.0000000000)
            column(name: 'survey_country', value: 'United States')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '0b64f2ca-9ca5-4cd6-9805-57412dfc73ed')
            column(name: 'df_acl_scenario_uid', value: '3beec0c7-6783-4481-9648-c02f4ec1da5a')
            column(name: 'df_acl_scenario_detail_uid', value: '2e2e0eab-228c-4353-b613-6e23c126a959')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 5.0000000000)
            column(name: 'value_weight', value: 59.8500000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
        }

        rollback {
            dbRollback
        }
    }
}
