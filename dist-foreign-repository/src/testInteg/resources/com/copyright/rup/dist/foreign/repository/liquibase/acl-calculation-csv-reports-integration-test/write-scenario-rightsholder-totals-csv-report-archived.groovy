databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-11-04-00', author: 'Ihar Suvorau <disuvorau@copyright.com>') {
        comment('Inserting test data for testWriteAclArchivedScenarioRightsholderTotalsCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '14edbc9b-1473-4fc9-95f6-07b3ef45e851')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b2ea68f6-3c15-4ae3-a04a-acdd5a236f0c')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1908ca9a-309d-4a3b-976f-3d20b747dd54')
            column(name: 'rh_account_number', value: 1000009482)
            column(name: 'name', value: 'Daedalus Enterprises Inc')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5252eaad-2e7e-45cc-9784-fb207ffb374f')
            column(name: 'rh_account_number', value: 2000149570)
            column(name: 'name', value: 'National Association for Management')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'd71a5107-306e-4d04-87ea-ea750a27243c')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '0cb14796-c47a-4073-af05-cfb26c2056e6')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '24a33a45-66f0-4193-8719-d24016a57510')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'df_acl_fund_pool_uid', value: 'd71a5107-306e-4d04-87ea-ea750a27243c')
            column(name: 'df_acl_usage_batch_uid', value: '0cb14796-c47a-4073-af05-cfb26c2056e6')
            column(name: 'df_acl_grant_set_uid', value: '24a33a45-66f0-4193-8719-d24016a57510')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail_archived') {
            column(name: 'df_acl_scenario_detail_archived_uid', value: '1d08a689-0fe1-469a-9c7e-cc337880d419')
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHSB107')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content', value: 11.0001230000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 2.0000000000)
            column(name: 'number_of_copies', value: 1.00000)
            column(name: 'usage_age_weight', value: 1.00000)
            column(name: 'weighted_copies', value: 1.0000000000)
            column(name: 'pub_type_weight', value: 1.00)
            column(name: 'survey_country', value: 'United States')
            column(name: 'reported_type_of_use', value: 'COPY_FOR_MYSELF')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail_archived') {
            column(name: 'df_acl_scenario_detail_archived_uid', value: '00a40773-9649-432f-86e0-1872597e3293')
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'period_end_date', value: 202206)
            column(name: 'original_detail_id', value: 'OGN674GHHSB108')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'price', value: 4.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 2.0000000000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 3.0001230000)
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'number_of_copies', value: 2.00000)
            column(name: 'usage_age_weight', value: 0.99000)
            column(name: 'weighted_copies', value: 0.9000000000)
            column(name: 'pub_type_weight', value: 2.00)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'reported_type_of_use', value: 'EMAIL_COPY')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail_archived') {
            column(name: 'df_acl_scenario_detail_archived_uid', value: 'a24c09e8-f3f7-4546-bec7-f80e95735f89')
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'period_end_date', value: 202206)
            column(name: 'original_detail_id', value: 'OGN674GHHSB109')
            column(name: 'wr_wrk_inst', value: 254327612)
            column(name: 'system_title', value: 'Current topics in library and information practice')
            column(name: 'detail_licensee_class_id', value: 4)
            column(name: 'publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'price', value: 5.1234567890)
            column(name: 'content', value: 1.0123456789)
            column(name: 'content_unit_price', value: 6.0123456789)
            column(name: 'number_of_copies', value: 3.00000)
            column(name: 'usage_age_weight', value: 0.99000)
            column(name: 'weighted_copies', value: 5.9000000000)
            column(name: 'pub_type_weight', value: 1.80)
            column(name: 'survey_country', value: 'Portugal')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail_archived') {
            column(name: 'df_acl_share_detail_archived_uid', value: '488c15ad-1936-47d2-8e6b-d8e83b6c4e4c')
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'df_acl_scenario_detail_archived_uid', value: '1d08a689-0fe1-469a-9c7e-cc337880d419')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.5341365462)
            column(name: 'value_share', value: 0.4117647059)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.1000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
            column(name: 'payee_account_number', value: 1000009482)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail_archived') {
            column(name: 'df_acl_share_detail_archived_uid', value: '2e28d7da-3092-4826-b650-71804c7ebe3f')
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'df_acl_scenario_detail_archived_uid', value: '1d08a689-0fe1-469a-9c7e-cc337880d419')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.6666666667)
            column(name: 'value_share', value: 0.1666666667)
            column(name: 'detail_share', value: 0.4166666667)
            column(name: 'net_amount', value: 833.3333334000)
            column(name: 'gross_amount', value: 837.3333334000)
            column(name: 'service_fee_amount', value: 4.0000000000)
            column(name: 'payee_account_number', value: 2000149570)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail_archived') {
            column(name: 'df_acl_share_detail_archived_uid', value: '1a9a8e83-43b1-4acb-93be-872849ecbf4e')
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'df_acl_scenario_detail_archived_uid', value: '00a40773-9649-432f-86e0-1872597e3293')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.1176470588)
            column(name: 'value_share', value: 0.0803212851)
            column(name: 'detail_share', value: 0.0989841720)
            column(name: 'net_amount', value: 202.9175526000)
            column(name: 'gross_amount', value: 234.9175526000)
            column(name: 'service_fee_amount', value: 32.0000000000)
            column(name: 'payee_account_number', value: 2000149570)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail_archived') {
            column(name: 'df_acl_share_detail_archived_uid', value: '60c36bd5-298c-483a-bd5e-41e22e49b0dd')
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'df_acl_scenario_detail_archived_uid', value: 'a24c09e8-f3f7-4546-bec7-f80e95735f89')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 12)
            column(name: 'volume_weight', value: 4.0000000000)
            column(name: 'value_weight', value: 5.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 0.4705882353)
            column(name: 'value_share', value: 0.3855421687)
            column(name: 'detail_share', value: 0.4280652020)
            column(name: 'net_amount', value: 100.0000000000)
            column(name: 'gross_amount', value: 110.0000000000)
            column(name: 'service_fee_amount', value: 10.0000000000)
            column(name: 'payee_account_number', value: 1000028511)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: '9a275414-b02e-40b8-8ad0-e12d964ae53d')
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: 'f604acc5-79cb-47a8-906e-52c23c6b42e4')
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '7d48388b-2341-479b-8dab-cd4f4181de85')
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'df_publication_type_uid', value: "f1f523ca-1b46-4d3a-842d-99252785187c")
            column(name: 'weight', value: 1)
            column(name: 'period', value: 201512)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '155deeae-70e2-4161-91d7-e9b0de06743d')
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '33272e84-609b-4c0e-9042-6c79d4aa0b9c')
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'aggregate_licensee_class_id', value: 51)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '3149bca5-9153-43b4-9b33-191ea4e1725e')
            column(name: 'df_acl_scenario_uid', value: 'cfd805dd-70fa-4635-8d8c-e516a0f35ef9')
            column(name: 'detail_licensee_class_id', value: 4)
            column(name: 'aggregate_licensee_class_id', value: 57)
        }

        rollback {
            dbRollback
        }
    }
}
