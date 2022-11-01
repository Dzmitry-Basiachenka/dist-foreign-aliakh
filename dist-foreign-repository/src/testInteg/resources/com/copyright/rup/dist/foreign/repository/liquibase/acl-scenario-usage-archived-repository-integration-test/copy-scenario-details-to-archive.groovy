databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-10-31-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '9162fed4-1502-4bf6-8ab6-585421c122f2')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '959bceb0-1e5e-4669-9ddb-3962af6034df')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '78a46d63-340c-4d8a-8f5b-18641959e885')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '326d59f3-bdea-4579-9100-63d0e76386fe')
            column(name: 'df_acl_fund_pool_uid', value: '9162fed4-1502-4bf6-8ab6-585421c122f2')
            column(name: 'df_acl_usage_batch_uid', value: '959bceb0-1e5e-4669-9ddb-3962af6034df')
            column(name: 'df_acl_grant_set_uid', value: '78a46d63-340c-4d8a-8f5b-18641959e885')
            column(name: 'name', value: 'ACL Scenario 202212')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '0451320c-60b6-4570-b6d3-12e82f62f21c')
            column(name: 'df_acl_scenario_uid', value: '326d59f3-bdea-4579-9100-63d0e76386fe')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHSB107')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'pub_type_weight', value: 1.00)
            column(name: 'content_unit_price', value: 2.0000000000)
            column(name: 'number_of_copies', value: 1.00000)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 1.0000000000)
            column(name: 'survey_country', value: 'United States')
            column(name: 'reported_type_of_use', value: 'COPY_FOR_MYSELF')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'price', value: 10.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 11.0000000000)
            column(name: 'content_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'a5c179d5-7f49-4b12-b006-4ebb088238a4')
            column(name: 'df_acl_scenario_uid', value: '326d59f3-bdea-4579-9100-63d0e76386fe')
            column(name: 'period_end_date', value: 202206)
            column(name: 'original_detail_id', value: 'OGN674GHHSB108')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'pub_type_weight', value: 2.00)
            column(name: 'content_unit_price', value: 3.0000000000)
            column(name: 'number_of_copies', value: 2.00000)
            column(name: 'usage_age_weight', value: 0.99000)
            column(name: 'weighted_copies', value: 0.9000000000)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'price', value: 4.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 2.0000000000)
            column(name: 'content_flag', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'a7cf7f7c-8884-48e1-a6d8-a1315f27dc6f')
            column(name: 'df_acl_scenario_uid', value: '326d59f3-bdea-4579-9100-63d0e76386fe')
            column(name: 'df_acl_scenario_detail_uid', value: 'a5c179d5-7f49-4b12-b006-4ebb088238a4')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.0000000001)
            column(name: 'value_share', value: 0.1000000000)
            column(name: 'net_amount', value: 84.0000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'f250cc16-e95b-4e66-a31a-13068b67d247')
            column(name: 'df_acl_scenario_uid', value: '326d59f3-bdea-4579-9100-63d0e76386fe')
            column(name: 'df_acl_scenario_detail_uid', value: 'a5c179d5-7f49-4b12-b006-4ebb088238a4')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.0000000002)
            column(name: 'value_share', value: 0.2000000000)
            column(name: 'net_amount', value: 6.0000000000)
            column(name: 'gross_amount', value: 10.0000000000)
            column(name: 'service_fee_amount', value: 4.0000000000)
            column(name: 'detail_share', value: 0.8000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '2f55e6df-7be6-4a34-966d-6b53222f4e4b')
            column(name: 'df_acl_scenario_uid', value: '326d59f3-bdea-4579-9100-63d0e76386fe')
            column(name: 'df_acl_scenario_detail_uid', value: '0451320c-60b6-4570-b6d3-12e82f62f21c')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.0000000001)
            column(name: 'value_share', value: 0.1000000000)
            column(name: 'detail_share', value: 0.9000000000)
            column(name: 'net_amount', value: 168.0000000000)
            column(name: 'gross_amount', value: 200.0000000000)
            column(name: 'service_fee_amount', value: 32.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '6faee2c3-439f-4988-9b8d-c01fd004fd5e')
            column(name: 'df_acl_scenario_uid', value: '326d59f3-bdea-4579-9100-63d0e76386fe')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '42213bec-4003-48b5-9618-a940cf46e7f9')
            column(name: 'df_acl_scenario_uid', value: '326d59f3-bdea-4579-9100-63d0e76386fe')
            column(name: 'period_prior', value: 1)
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '29bb130a-82ad-42eb-9c16-4b49b242b718')
            column(name: 'df_acl_scenario_uid', value: '326d59f3-bdea-4579-9100-63d0e76386fe')
            column(name: 'df_publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'dd624c1a-25e7-44ff-89d9-671df78e38ff')
            column(name: 'df_acl_scenario_uid', value: '326d59f3-bdea-4579-9100-63d0e76386fe')
            column(name: 'df_publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
            column(name: 'period', value: '201506')
            column(name: 'weight', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '1230bfe5-6919-498a-9aab-3d52c43db96b')
            column(name: 'df_acl_scenario_uid', value: '326d59f3-bdea-4579-9100-63d0e76386fe')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '4873539c-87c7-4508-be28-c993c91f9023')
            column(name: 'df_acl_scenario_uid', value: '326d59f3-bdea-4579-9100-63d0e76386fe')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'aggregate_licensee_class_id', value: 52)
        }

        rollback {
            dbRollback
        }
    }
}
