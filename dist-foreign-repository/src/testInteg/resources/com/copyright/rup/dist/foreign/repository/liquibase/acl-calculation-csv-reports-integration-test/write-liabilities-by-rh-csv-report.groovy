databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-10-18-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testWriteAclLiabilitiesByRhReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'c60ad014-cacb-4aa8-b23d-5de2df08ac10')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'afecb021-0020-496b-bccb-98c1b0b46945')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '80c8bff6-2fdf-4df4-bb45-107d02bba45d')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'name', value: 'Greenleaf Publishing')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'db3bd9d8-56ce-4e06-80d2-2948ff4114ea')
            column(name: 'rh_account_number', value: 1000000004)
            column(name: 'name', value: 'Computers for Design and Construction')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '99991909-744c-4766-ad67-fdc9e2c043eb')
            column(name: 'rh_account_number', value: 1000002901)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '3218fc1a-f20b-40e1-9bf5-d46703949e2c')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '95116329-bcfa-4598-9daf-02916de37d91')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '9d40638a-251e-4a77-9650-8bed832128c5')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'ae0910b1-1fb0-420a-96c9-76a18ebca229')
            column(name: 'df_acl_fund_pool_uid', value: '9d40638a-251e-4a77-9650-8bed832128c5')
            column(name: 'df_acl_usage_batch_uid', value: '3218fc1a-f20b-40e1-9bf5-d46703949e2c')
            column(name: 'df_acl_grant_set_uid', value: '95116329-bcfa-4598-9daf-02916de37d91')
            column(name: 'name', value: 'ACL Scenario 10/05/202212')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'fcc1fa74-f1ba-4044-a222-cc77b81ee70a')
            column(name: 'name', value: 'MACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '728ad4a1-b5fd-47da-8a6d-3413f6313cc0')
            column(name: 'name', value: 'MACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '49c52e95-ca96-400e-a73c-d5732a5d1301')
            column(name: 'df_acl_fund_pool_uid', value: '728ad4a1-b5fd-47da-8a6d-3413f6313cc0')
            column(name: 'df_acl_usage_batch_uid', value: '3218fc1a-f20b-40e1-9bf5-d46703949e2c')
            column(name: 'df_acl_grant_set_uid', value: 'fcc1fa74-f1ba-4044-a222-cc77b81ee70a')
            column(name: 'name', value: 'MACL Scenario 10/05/202212')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-01-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-01-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '3146d441-7a9d-4d99-bc46-d0a7efe89b2a')
            column(name: 'name', value: 'VGW Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'c89ec7b2-570e-4f26-b913-e24e0e94748d')
            column(name: 'name', value: 'VGW Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '3ecfcf3b-1be0-4bfa-bc55-44add8432df9')
            column(name: 'df_acl_fund_pool_uid', value: 'c89ec7b2-570e-4f26-b913-e24e0e94748d')
            column(name: 'df_acl_usage_batch_uid', value: '3218fc1a-f20b-40e1-9bf5-d46703949e2c')
            column(name: 'df_acl_grant_set_uid', value: '3146d441-7a9d-4d99-bc46-d0a7efe89b2a')
            column(name: 'name', value: 'VGW Scenario 10/05/202212')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'e366ff1d-0476-40e2-bfec-758ff1e75eb2')
            column(name: 'name', value: 'JACDCL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'JACDCL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '7dd4383b-d302-4789-87bb-b095e89c8088')
            column(name: 'name', value: 'JACDCL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'JACDCL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'ce858f53-eea8-4273-888e-ab983f88f59b')
            column(name: 'df_acl_fund_pool_uid', value: '7dd4383b-d302-4789-87bb-b095e89c8088')
            column(name: 'df_acl_usage_batch_uid', value: '3218fc1a-f20b-40e1-9bf5-d46703949e2c')
            column(name: 'df_acl_grant_set_uid', value: 'e366ff1d-0476-40e2-bfec-758ff1e75eb2')
            column(name: 'name', value: 'JACDCL Scenario 10/05/202212')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'JACDCL')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '7e6cc293-8b31-4ac0-9ff3-ec977fcc60d8')
            column(name: 'df_acl_scenario_uid', value: 'ae0910b1-1fb0-420a-96c9-76a18ebca229')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '482ca04c-2596-4cad-9da0-265d8f76f528')
            column(name: 'df_acl_scenario_uid', value: 'ae0910b1-1fb0-420a-96c9-76a18ebca229')
            column(name: 'df_acl_scenario_detail_uid', value: '7e6cc293-8b31-4ac0-9ff3-ec977fcc60d8')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.1000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'ccfe7f73-4ee2-419b-ae12-9cf343b1ab0b')
            column(name: 'df_acl_scenario_uid', value: 'ae0910b1-1fb0-420a-96c9-76a18ebca229')
            column(name: 'df_acl_scenario_detail_uid', value: '7e6cc293-8b31-4ac0-9ff3-ec977fcc60d8')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 2000017010)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 42.1000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '73223d65-c1f7-4344-b933-6d20357f7024')
            column(name: 'df_acl_scenario_uid', value: '49c52e95-ca96-400e-a73c-d5732a5d1301')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'b86f1529-9fc1-4412-ae6f-42c9d8dce2f3')
            column(name: 'df_acl_scenario_uid', value: '49c52e95-ca96-400e-a73c-d5732a5d1301')
            column(name: 'df_acl_scenario_detail_uid', value: '73223d65-c1f7-4344-b933-6d20357f7024')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 2000017010)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.2000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 15.8000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '83eecb6a-67f3-4f39-94b5-4dc12c72c19b')
            column(name: 'df_acl_scenario_uid', value: '49c52e95-ca96-400e-a73c-d5732a5d1301')
            column(name: 'df_acl_scenario_detail_uid', value: '73223d65-c1f7-4344-b933-6d20357f7024')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 42.2000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.8000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '6c2499a4-7491-4642-b19e-b8a2c62ba3bc')
            column(name: 'df_acl_scenario_uid', value: '3ecfcf3b-1be0-4bfa-bc55-44add8432df9')
            column(name: 'period_end_date', value: 202206)
            column(name: 'original_detail_id', value: 'OGN674GHHSB208')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '455486f7-54d1-4b99-8b67-8a1699039d01')
            column(name: 'df_acl_scenario_uid', value: '3ecfcf3b-1be0-4bfa-bc55-44add8432df9')
            column(name: 'df_acl_scenario_detail_uid', value: '6c2499a4-7491-4642-b19e-b8a2c62ba3bc')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'payee_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.3000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 15.7000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '8de916cc-140c-45c4-9cae-efb7bcc8ced7')
            column(name: 'df_acl_scenario_uid', value: '3ecfcf3b-1be0-4bfa-bc55-44add8432df9')
            column(name: 'df_acl_scenario_detail_uid', value: '6c2499a4-7491-4642-b19e-b8a2c62ba3bc')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'payee_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 42.3000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.7000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'c6cbb0e2-b356-4180-ae93-da128f5862a3')
            column(name: 'df_acl_scenario_uid', value: 'ce858f53-eea8-4273-888e-ab983f88f59b')
            column(name: 'period_end_date', value: 202206)
            column(name: 'original_detail_id', value: 'OGN674GHHSB308')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '9c4be525-f9b5-4c3f-8707-0bd98f207be6')
            column(name: 'df_acl_scenario_uid', value: 'ce858f53-eea8-4273-888e-ab983f88f59b')
            column(name: 'df_acl_scenario_detail_uid', value: 'c6cbb0e2-b356-4180-ae93-da128f5862a3')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'payee_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.4000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 15.6000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '58c6b380-4e10-406e-9f08-e6977b567a57')
            column(name: 'df_acl_scenario_uid', value: 'ce858f53-eea8-4273-888e-ab983f88f59b')
            column(name: 'df_acl_scenario_detail_uid', value: 'c6cbb0e2-b356-4180-ae93-da128f5862a3')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'payee_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 42.4000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.6000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '327b06c9-23c5-4455-a54d-eb235d50de52')
            column(name: 'df_acl_scenario_uid', value: 'ce858f53-eea8-4273-888e-ab983f88f59b')
            column(name: 'period_end_date', value: 202206)
            column(name: 'original_detail_id', value: 'OGN674GHHSB309')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '317d28fc-9e8b-48b8-ae62-d408e52c6e95')
            column(name: 'df_acl_scenario_uid', value: 'ce858f53-eea8-4273-888e-ab983f88f59b')
            column(name: 'df_acl_scenario_detail_uid', value: '327b06c9-23c5-4455-a54d-eb235d50de52')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'rh_account_number', value: 1000000004)
            column(name: 'payee_account_number', value: 1000002901)
            column(name: 'aggregate_licensee_class_id', value: 51)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_weight_denominator', value: 1.0000000000)
            column(name: 'value_weight_denominator', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 10.4000000000)
            column(name: 'gross_amount', value: 9.0000000000)
            column(name: 'service_fee_amount', value: 15.6000000000)
        }

        rollback {
            dbRollback
        }
    }
}
