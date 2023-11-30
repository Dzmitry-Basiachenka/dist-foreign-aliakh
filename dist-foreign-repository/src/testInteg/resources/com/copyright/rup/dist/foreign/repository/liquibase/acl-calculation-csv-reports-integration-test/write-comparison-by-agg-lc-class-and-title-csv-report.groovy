databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-03-28-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserting test data for testWriteAclComparisonByAggLcClassAndTitleReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'c34d6d86-d8a4-4ce4-81d3-bad54ba2000f')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5c9e61b8-f519-44c1-bb18-9a5112c36431')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1b5d2ab7-4934-491f-a16f-7a0cf86acbd1')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'name', value: 'Greenleaf Publishing')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'f00b2170-74c6-4002-a5f1-8fa9c5dcddd7')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '4159b638-44dc-4e87-a449-6eafaed78fc1')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '770c41a6-db1d-4a0a-b5cf-0d81c78aac99')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '7fa61f70-3310-4ec0-8a50-bdd794e2fd08')
            column(name: 'df_acl_fund_pool_uid', value: '770c41a6-db1d-4a0a-b5cf-0d81c78aac99')
            column(name: 'df_acl_usage_batch_uid', value: 'f00b2170-74c6-4002-a5f1-8fa9c5dcddd7')
            column(name: 'df_acl_grant_set_uid', value: '4159b638-44dc-4e87-a449-6eafaed78fc1')
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
            column(name: 'df_acl_scenario_detail_uid', value: '6e2a15ac-c1c9-4fc8-bee8-909b88dd266c')
            column(name: 'df_acl_scenario_uid', value: '7fa61f70-3310-4ec0-8a50-bdd794e2fd08')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN1ACL2022')
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
            column(name: 'df_acl_share_detail_uid', value: 'bee31a35-c90f-48e1-8342-9fb05c2283f9')
            column(name: 'df_acl_scenario_uid', value: '7fa61f70-3310-4ec0-8a50-bdd794e2fd08')
            column(name: 'df_acl_scenario_detail_uid', value: '6e2a15ac-c1c9-4fc8-bee8-909b88dd266c')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 84.1000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '1991ce71-8645-41de-84ea-c6fcd5bcf732')
            column(name: 'df_acl_scenario_uid', value: '7fa61f70-3310-4ec0-8a50-bdd794e2fd08')
            column(name: 'df_acl_scenario_detail_uid', value: '6e2a15ac-c1c9-4fc8-bee8-909b88dd266c')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 42.1000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '011c2714-e3e7-4148-a77c-10889b2cad9c')
            column(name: 'df_acl_scenario_uid', value: '7fa61f70-3310-4ec0-8a50-bdd794e2fd08')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN2ACL2022')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 18)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
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
            column(name: 'df_acl_share_detail_uid', value: '92d0018d-2b4e-487f-a9f1-d22c08067c82')
            column(name: 'df_acl_scenario_uid', value: '7fa61f70-3310-4ec0-8a50-bdd794e2fd08')
            column(name: 'df_acl_scenario_detail_uid', value: '011c2714-e3e7-4148-a77c-10889b2cad9c')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 18)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '95131c1d-c52f-4f2f-b69f-8d292d5e77c7')
            column(name: 'df_acl_scenario_uid', value: '7fa61f70-3310-4ec0-8a50-bdd794e2fd08')
            column(name: 'df_acl_scenario_detail_uid', value: '011c2714-e3e7-4148-a77c-10889b2cad9c')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 18)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 0.0000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '7f050d8a-cb2c-4b8b-9b93-6c9b2b2f89aa')
            column(name: 'name', value: 'ACL Usage Batch 202312')
            column(name: 'distribution_period', value: 202312)
            column(name: 'periods', value: '[202306, 202312]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'c5672c1b-f10b-409d-b6aa-e34139fc97ae')
            column(name: 'name', value: 'ACL Grant Set 202312')
            column(name: 'grant_period', value: 202312)
            column(name: 'periods', value: '[202306, 202312]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '52e03555-9878-4fd4-8165-c9bd986edebd')
            column(name: 'name', value: 'ACL Fund Pool 202312')
            column(name: 'period', value: 202312)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'ec2e8521-a55b-4b70-ad9d-9b07ddfa0b3e')
            column(name: 'df_acl_fund_pool_uid', value: '52e03555-9878-4fd4-8165-c9bd986edebd')
            column(name: 'df_acl_usage_batch_uid', value: '7f050d8a-cb2c-4b8b-9b93-6c9b2b2f89aa')
            column(name: 'df_acl_grant_set_uid', value: 'c5672c1b-f10b-409d-b6aa-e34139fc97ae')
            column(name: 'name', value: 'ACL Scenario 202312')
            column(name: 'period_end_date', value: 202312)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2023-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2023-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'f0b0c2f2-1613-4019-ae9d-ee229b3ef1e1')
            column(name: 'df_acl_scenario_uid', value: 'ec2e8521-a55b-4b70-ad9d-9b07ddfa0b3e')
            column(name: 'period_end_date', value: 202312)
            column(name: 'original_detail_id', value: 'OGN1ACL2023')
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
            column(name: 'df_acl_share_detail_uid', value: 'f119b480-d5a3-4bfd-81e8-c173463a05be')
            column(name: 'df_acl_scenario_uid', value: 'ec2e8521-a55b-4b70-ad9d-9b07ddfa0b3e')
            column(name: 'df_acl_scenario_detail_uid', value: 'f0b0c2f2-1613-4019-ae9d-ee229b3ef1e1')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 200.000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'cfaa77fc-d5f0-4c18-9b5c-e6cab60e3ca1')
            column(name: 'df_acl_scenario_uid', value: 'ec2e8521-a55b-4b70-ad9d-9b07ddfa0b3e')
            column(name: 'df_acl_scenario_detail_uid', value: 'f0b0c2f2-1613-4019-ae9d-ee229b3ef1e1')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 100.0000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'f7dbafd6-a154-4312-be62-46a600dd50b1')
            column(name: 'df_acl_scenario_uid', value: 'ec2e8521-a55b-4b70-ad9d-9b07ddfa0b3e')
            column(name: 'period_end_date', value: 202312)
            column(name: 'original_detail_id', value: 'OGN2ACL2023')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Technology review')
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
            column(name: 'df_acl_share_detail_uid', value: 'd393312b-09c8-4f4d-8739-f70fb4f0f863')
            column(name: 'df_acl_scenario_uid', value: 'ec2e8521-a55b-4b70-ad9d-9b07ddfa0b3e')
            column(name: 'df_acl_scenario_detail_uid', value: 'f7dbafd6-a154-4312-be62-46a600dd50b1')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 200.000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '61dfbbfd-f9bf-4494-b379-e7973a3070f1')
            column(name: 'df_acl_scenario_uid', value: 'ec2e8521-a55b-4b70-ad9d-9b07ddfa0b3e')
            column(name: 'period_end_date', value: 202312)
            column(name: 'original_detail_id', value: 'OGN1ACL2023')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 18)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
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
            column(name: 'df_acl_share_detail_uid', value: 'bc11cb35-4bb4-4c40-b4b0-ce265daec769')
            column(name: 'df_acl_scenario_uid', value: 'ec2e8521-a55b-4b70-ad9d-9b07ddfa0b3e')
            column(name: 'df_acl_scenario_detail_uid', value: '61dfbbfd-f9bf-4494-b379-e7973a3070f1')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 18)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 100.000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '00a46c7e-7440-4985-893f-0c6bfc981356')
            column(name: 'df_acl_scenario_uid', value: 'ec2e8521-a55b-4b70-ad9d-9b07ddfa0b3e')
            column(name: 'df_acl_scenario_detail_uid', value: '61dfbbfd-f9bf-4494-b379-e7973a3070f1')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 18)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 100.0000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '1fd34276-d0a3-4b66-aa3c-f8aca5b95c5e')
            column(name: 'name', value: 'MACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '99ae4a95-8d30-4be3-8fac-be920bda79c4')
            column(name: 'name', value: 'MACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '2d3ad553-8e47-41b2-ac23-964fcbecc64d')
            column(name: 'name', value: 'MACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'c283e1db-5fea-4ad6-bb54-5069ba0ecc1e')
            column(name: 'df_acl_fund_pool_uid', value: '2d3ad553-8e47-41b2-ac23-964fcbecc64d')
            column(name: 'df_acl_usage_batch_uid', value: '1fd34276-d0a3-4b66-aa3c-f8aca5b95c5e')
            column(name: 'df_acl_grant_set_uid', value: '99ae4a95-8d30-4be3-8fac-be920bda79c4')
            column(name: 'name', value: 'MACL Scenario 202212')
            column(name: 'period_end_date', value: 202212)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '91ba36dc-69b3-48ba-a50f-36343b53078a')
            column(name: 'df_acl_scenario_uid', value: 'c283e1db-5fea-4ad6-bb54-5069ba0ecc1e')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN1MACL2022')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
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
            column(name: 'df_acl_share_detail_uid', value: '0d1fe77f-450d-476c-a13f-584f9dcf4d75')
            column(name: 'df_acl_scenario_uid', value: 'c283e1db-5fea-4ad6-bb54-5069ba0ecc1e')
            column(name: 'df_acl_scenario_detail_uid', value: '91ba36dc-69b3-48ba-a50f-36343b53078a')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 56)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 80.0000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '7c269de1-9b87-4f49-aa8e-548d34ec9bf0')
            column(name: 'df_acl_scenario_uid', value: 'c283e1db-5fea-4ad6-bb54-5069ba0ecc1e')
            column(name: 'df_acl_scenario_detail_uid', value: '91ba36dc-69b3-48ba-a50f-36343b53078a')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 56)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 40.0000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'dae9ef7a-f6c4-43f7-98b7-b41f7ac66256')
            column(name: 'df_acl_scenario_uid', value: 'c283e1db-5fea-4ad6-bb54-5069ba0ecc1e')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN2MACL2022')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 23)
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
            column(name: 'df_acl_share_detail_uid', value: 'aee9218c-70c9-451d-9475-beb06f141f37')
            column(name: 'df_acl_scenario_uid', value: 'c283e1db-5fea-4ad6-bb54-5069ba0ecc1e')
            column(name: 'df_acl_scenario_detail_uid', value: 'dae9ef7a-f6c4-43f7-98b7-b41f7ac66256')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 23)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 200.0000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'c2dbeb63-88cc-4bd5-9113-944f78bb9238')
            column(name: 'df_acl_scenario_uid', value: 'c283e1db-5fea-4ad6-bb54-5069ba0ecc1e')
            column(name: 'df_acl_scenario_detail_uid', value: 'dae9ef7a-f6c4-43f7-98b7-b41f7ac66256')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 23)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 300.0000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'cbd440d4-8c86-46fa-98eb-76944a582a49')
            column(name: 'df_acl_scenario_uid', value: 'c283e1db-5fea-4ad6-bb54-5069ba0ecc1e')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN3MACL2022')
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
            column(name: 'df_acl_share_detail_uid', value: '533134e2-8a06-44db-8a08-3ee06efc64d2')
            column(name: 'df_acl_scenario_uid', value: 'c283e1db-5fea-4ad6-bb54-5069ba0ecc1e')
            column(name: 'df_acl_scenario_detail_uid', value: 'cbd440d4-8c86-46fa-98eb-76944a582a49')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 50.5545555555)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '7c955190-df57-411a-8d51-ada8796dff15')
            column(name: 'df_acl_scenario_uid', value: 'c283e1db-5fea-4ad6-bb54-5069ba0ecc1e')
            column(name: 'df_acl_scenario_detail_uid', value: 'cbd440d4-8c86-46fa-98eb-76944a582a49')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 60.3333333333)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '35256821-159a-4e64-be78-9313148e1eed')
            column(name: 'name', value: 'MACL Usage Batch 202312')
            column(name: 'distribution_period', value: 202312)
            column(name: 'periods', value: '[202306, 202312]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '6e92215c-bf01-406b-a976-909282295b27')
            column(name: 'name', value: 'MACL Grant Set 202312')
            column(name: 'grant_period', value: 202312)
            column(name: 'periods', value: '[202306, 202312]')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '5754d4f2-6f6c-4fd1-9ea6-ef2177f48fe3')
            column(name: 'name', value: 'MACL Fund Pool 202312')
            column(name: 'period', value: 202312)
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'c42237cf-dec7-43e3-a5d6-4427b6d24773')
            column(name: 'df_acl_fund_pool_uid', value: '5754d4f2-6f6c-4fd1-9ea6-ef2177f48fe3')
            column(name: 'df_acl_usage_batch_uid', value: '35256821-159a-4e64-be78-9313148e1eed')
            column(name: 'df_acl_grant_set_uid', value: '6e92215c-bf01-406b-a976-909282295b27')
            column(name: 'name', value: 'MACL Scenario 202312')
            column(name: 'period_end_date', value: 202312)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'MACL')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2023-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2023-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '30b2abb4-9cec-426e-91b2-c3ea75e29779')
            column(name: 'df_acl_scenario_uid', value: 'c42237cf-dec7-43e3-a5d6-4427b6d24773')
            column(name: 'period_end_date', value: 202312)
            column(name: 'original_detail_id', value: 'OGN1MACL2023')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 22)
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
            column(name: 'df_acl_share_detail_uid', value: '2f0edc72-1a3f-4856-9ed9-7240fc720cc3')
            column(name: 'df_acl_scenario_uid', value: 'c42237cf-dec7-43e3-a5d6-4427b6d24773')
            column(name: 'df_acl_scenario_detail_uid', value: '30b2abb4-9cec-426e-91b2-c3ea75e29779')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 56)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 75.0000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '0124362b-dbe0-4488-bdcd-6f3b5ed21ece')
            column(name: 'df_acl_scenario_uid', value: 'c42237cf-dec7-43e3-a5d6-4427b6d24773')
            column(name: 'df_acl_scenario_detail_uid', value: '30b2abb4-9cec-426e-91b2-c3ea75e29779')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 56)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 50.0000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'c2ed3895-5b83-4031-b240-309b8fc3b338')
            column(name: 'df_acl_scenario_uid', value: 'c42237cf-dec7-43e3-a5d6-4427b6d24773')
            column(name: 'period_end_date', value: 202312)
            column(name: 'original_detail_id', value: 'OGN2MACL2023')
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
            column(name: 'df_acl_share_detail_uid', value: '1655f596-55c3-4004-922d-6f46d1abd86c')
            column(name: 'df_acl_scenario_uid', value: 'c42237cf-dec7-43e3-a5d6-4427b6d24773')
            column(name: 'df_acl_scenario_detail_uid', value: 'c2ed3895-5b83-4031-b240-309b8fc3b338')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 33.3434343434)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'ae892583-44c9-4adb-af67-fdd78ca25a9e')
            column(name: 'df_acl_scenario_uid', value: 'c42237cf-dec7-43e3-a5d6-4427b6d24773')
            column(name: 'df_acl_scenario_detail_uid', value: 'c2ed3895-5b83-4031-b240-309b8fc3b338')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 120.2354234453)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '2954291d-d07c-4481-8d5b-6dfcf37cf0b2')
            column(name: 'name', value: 'VGW Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '7c8de4e2-78d3-4bf0-8b25-a9fbe8654746')
            column(name: 'name', value: 'VGW Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '0803be2e-ccbe-421d-9050-b4b7e737adb4')
            column(name: 'name', value: 'VGW Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'df_acl_fund_pool_uid', value: '0803be2e-ccbe-421d-9050-b4b7e737adb4')
            column(name: 'df_acl_usage_batch_uid', value: '2954291d-d07c-4481-8d5b-6dfcf37cf0b2')
            column(name: 'df_acl_grant_set_uid', value: '7c8de4e2-78d3-4bf0-8b25-a9fbe8654746')
            column(name: 'name', value: 'VGW Scenario 202212')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '7ee01940-6991-4ca6-8157-79ffa7836e11')
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN1VGW2022')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 23)
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
            column(name: 'df_acl_share_detail_uid', value: 'f7d30a55-6972-42a8-aa88-968c1f98e836')
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'df_acl_scenario_detail_uid', value: '7ee01940-6991-4ca6-8157-79ffa7836e11')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 23)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 5.0000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '6dd5e99c-561c-4cd0-8e07-c4aa6d1a1040')
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'df_acl_scenario_detail_uid', value: '7ee01940-6991-4ca6-8157-79ffa7836e11')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 23)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 6.0000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'd045bda4-ee8e-437c-86ae-38cd7758be11')
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN2VGW2022')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Technology review')
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
            column(name: 'df_acl_share_detail_uid', value: '4680ec9e-aa09-4ed5-84b9-a6256f40ff54')
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'df_acl_scenario_detail_uid', value: 'd045bda4-ee8e-437c-86ae-38cd7758be11')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 1.0000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: 'dfcca389-f165-444b-bd4f-b97a79705934')
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'df_acl_scenario_detail_uid', value: 'd045bda4-ee8e-437c-86ae-38cd7758be11')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 1)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 2.0000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'aae6826f-1b00-4c03-afce-0d9e6c427741')
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN3VGW2022')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 23)
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
            column(name: 'df_acl_share_detail_uid', value: '914b33a6-3cd7-484a-ade4-7955987a1861')
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'df_acl_scenario_detail_uid', value: 'aae6826f-1b00-4c03-afce-0d9e6c427741')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'aggregate_licensee_class_id', value: 23)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 37.1100000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'e99c8513-0190-4ec9-b43d-2520295c9a3b')
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'period_end_date', value: 202212)
            column(name: 'original_detail_id', value: 'OGN4VGW2022')
            column(name: 'wr_wrk_inst', value: 123822477)
            column(name: 'system_title', value: 'Portland business journal')
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
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
            column(name: 'df_acl_share_detail_uid', value: 'f261e869-15db-4b19-b8f7-655a40179b05')
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'df_acl_scenario_detail_uid', value: 'e99c8513-0190-4ec9-b43d-2520295c9a3b')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 123822477)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 56)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'e5f5623c-f9a3-4b17-8f06-3d662e3aaa91')
            column(name: 'name', value: 'VGW Usage Batch 202312')
            column(name: 'distribution_period', value: 202312)
            column(name: 'periods', value: '[202306, 202312]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '2b5ce949-9be1-495b-8dba-97d1f3e1b462')
            column(name: 'name', value: 'VGW Grant Set 202312')
            column(name: 'grant_period', value: 202312)
            column(name: 'periods', value: '[202306, 202312]')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '6921fe34-dea5-4425-9ce2-abd20231ad43')
            column(name: 'name', value: 'VGW Fund Pool 202312')
            column(name: 'period', value: 202312)
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '2bd594f8-7289-4d6e-be46-9df909f36263')
            column(name: 'df_acl_fund_pool_uid', value: '6921fe34-dea5-4425-9ce2-abd20231ad43')
            column(name: 'df_acl_usage_batch_uid', value: 'e5f5623c-f9a3-4b17-8f06-3d662e3aaa91')
            column(name: 'df_acl_grant_set_uid', value: '2b5ce949-9be1-495b-8dba-97d1f3e1b462')
            column(name: 'name', value: 'VGW Scenario 202312')
            column(name: 'period_end_date', value: 202312)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'Description')
            column(name: 'license_type', value: 'VGW')
            column(name: 'is_editable', value: true)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2023-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2023-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '1b6bda3b-acad-42f4-bb58-1a5dcebc6281')
            column(name: 'df_acl_scenario_uid', value: '2bd594f8-7289-4d6e-be46-9df909f36263')
            column(name: 'period_end_date', value: 202312)
            column(name: 'original_detail_id', value: 'OGN1VGW2023')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'system_title', value: 'Aerospace America')
            column(name: 'detail_licensee_class_id', value: 23)
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
            column(name: 'df_acl_share_detail_uid', value: 'a8dd59a3-11c5-476c-be99-8ae585e25167')
            column(name: 'df_acl_scenario_uid', value: '2bd594f8-7289-4d6e-be46-9df909f36263')
            column(name: 'df_acl_scenario_detail_uid', value: '1b6bda3b-acad-42f4-bb58-1a5dcebc6281')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 23)
            column(name: 'volume_weight', value: 1.0000000000)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 23.0000000000)
            column(name: 'gross_amount', value: 100.0000000000)
            column(name: 'service_fee_amount', value: 16.0000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: 'df_acl_share_detail_uid', value: '0b398ff9-f98d-4d30-bce0-28e8fb40909b')
            column(name: 'df_acl_scenario_uid', value: '2bd594f8-7289-4d6e-be46-9df909f36263')
            column(name: 'df_acl_scenario_detail_uid', value: '1b6bda3b-acad-42f4-bb58-1a5dcebc6281')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 122813964)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 23)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 23.0000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '2fa2e61f-064f-41aa-b4b3-c905b9845553')
            column(name: 'df_acl_scenario_uid', value: '2bd594f8-7289-4d6e-be46-9df909f36263')
            column(name: 'period_end_date', value: 202312)
            column(name: 'original_detail_id', value: 'OGN2VGW2023')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 23)
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
            column(name: 'df_acl_share_detail_uid', value: 'c34028de-190f-4f9f-b31c-f39dd38f3ae1')
            column(name: 'df_acl_scenario_uid', value: '2bd594f8-7289-4d6e-be46-9df909f36263')
            column(name: 'df_acl_scenario_detail_uid', value: '2fa2e61f-064f-41aa-b4b3-c905b9845553')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'aggregate_licensee_class_id', value: 23)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'net_amount', value: 37.1200000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }


        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '33948d7a-f207-474b-9a33-816abecc2e81')
            column(name: 'df_acl_scenario_uid', value: '2bd594f8-7289-4d6e-be46-9df909f36263')
            column(name: 'period_end_date', value: 202312)
            column(name: 'original_detail_id', value: 'OGN3VGW2023')
            column(name: 'wr_wrk_inst', value: 123822477)
            column(name: 'system_title', value: 'Portland business journal')
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'publication_type_uid', value: 'aef4304b-6722-4047-86e0-8c84c72f096d')
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
            column(name: 'df_acl_share_detail_uid', value: '9bf13824-a3f1-4c70-be56-20000db3255a')
            column(name: 'df_acl_scenario_uid', value: '2bd594f8-7289-4d6e-be46-9df909f36263')
            column(name: 'df_acl_scenario_detail_uid', value: '33948d7a-f207-474b-9a33-816abecc2e81')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'wr_wrk_inst', value: 123822477)
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'aggregate_licensee_class_id', value: 57)
            column(name: 'volume_weight', value: 1.0000013432)
            column(name: 'value_weight', value: 1.0000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'detail_share', value: 1.0000000000)
            column(name: 'gross_amount', value: 50.0000000000)
            column(name: 'service_fee_amount', value: 7.9000000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '34b48a11-c3e4-4a80-8573-4ab55cc224e7')
            column(name: 'df_acl_scenario_uid', value: '7fa61f70-3310-4ec0-8a50-bdd794e2fd08')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'e59b6da0-d3ac-41bd-b301-f82ad7ceb409')
            column(name: 'df_acl_scenario_uid', value: '7fa61f70-3310-4ec0-8a50-bdd794e2fd08')
            column(name: 'detail_licensee_class_id', value: 18)
            column(name: 'aggregate_licensee_class_id', value: 18)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '84526e5a-890f-4799-a3c1-af6a0b55e589')
            column(name: 'df_acl_scenario_uid', value: 'ec2e8521-a55b-4b70-ad9d-9b07ddfa0b3e')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'cb0fe2d3-9417-4587-b584-4c0327d918e4')
            column(name: 'df_acl_scenario_uid', value: 'ec2e8521-a55b-4b70-ad9d-9b07ddfa0b3e')
            column(name: 'detail_licensee_class_id', value: 18)
            column(name: 'aggregate_licensee_class_id', value: 18)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'f16cba1f-4bf6-4b0e-a122-10fab7c0b6b9')
            column(name: 'df_acl_scenario_uid', value: 'c283e1db-5fea-4ad6-bb54-5069ba0ecc1e')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '8fa5727b-7c01-465d-b794-2d863cac0b6f')
            column(name: 'df_acl_scenario_uid', value: 'c283e1db-5fea-4ad6-bb54-5069ba0ecc1e')
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'aggregate_licensee_class_id', value: 56)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '2898b0e0-5f17-4918-8005-8974a146b91c')
            column(name: 'df_acl_scenario_uid', value: 'c283e1db-5fea-4ad6-bb54-5069ba0ecc1e')
            column(name: 'detail_licensee_class_id', value: 23)
            column(name: 'aggregate_licensee_class_id', value: 23)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'd3126990-9a5f-4dd1-a7ec-032dfa830b22')
            column(name: 'df_acl_scenario_uid', value: 'c42237cf-dec7-43e3-a5d6-4427b6d24773')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '691930b3-0f9f-4ec4-994b-68b180f7b58d')
            column(name: 'df_acl_scenario_uid', value: 'c42237cf-dec7-43e3-a5d6-4427b6d24773')
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'aggregate_licensee_class_id', value: 56)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'ca723b02-f214-4df4-8a97-5d4c9862d07d')
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '961a49f1-7c39-4a61-b497-8b9e1eb36051')
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'aggregate_licensee_class_id', value: 56)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '980ee8a2-8b0e-4bb8-a09d-4c62f7feccb5')
            column(name: 'df_acl_scenario_uid', value: '2050bc92-4900-403b-88bc-a05ec33c5de4')
            column(name: 'detail_licensee_class_id', value: 23)
            column(name: 'aggregate_licensee_class_id', value: 23)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '8a627d7e-1278-4df0-a2a2-b15f061ca46f')
            column(name: 'df_acl_scenario_uid', value: '2bd594f8-7289-4d6e-be46-9df909f36263')
            column(name: 'detail_licensee_class_id', value: 22)
            column(name: 'aggregate_licensee_class_id', value: 57)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: '5edd23ab-ecac-4b6f-b119-7d814b736c87')
            column(name: 'df_acl_scenario_uid', value: '2bd594f8-7289-4d6e-be46-9df909f36263')
            column(name: 'detail_licensee_class_id', value: 23)
            column(name: 'aggregate_licensee_class_id', value: 23)
        }

        rollback {
            dbRollback
        }
    }
}
