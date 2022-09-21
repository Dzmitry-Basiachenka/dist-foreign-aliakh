databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-03-17-01', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testFindForAudit')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'fd137df2-7308-49a0-b72e-0ea6924249a9')
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'c480ba67-82f3-4aad-a966-4586da4898e6')
            column(name: 'rh_account_number', value: 1000000027)
            column(name: 'name', value: 'Georgia State University Business Press [C]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9e0f99e4-1e95-488d-a0c5-ff1353c84e39')
            column(name: 'name', value: 'AACL batch 1 for Audit')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2000)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f89f016d-0cc7-46b6-9f3f-63d2439458d5')
            column(name: 'df_usage_batch_uid', value: '9e0f99e4-1e95-488d-a0c5-ff1353c84e39')
            column(name: 'wr_wrk_inst', value: 269040892)
            column(name: 'rh_account_number', value: 1000000027)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL comment 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'f89f016d-0cc7-46b6-9f3f-63d2439458d5')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '49680a3e-2986-44f5-943c-3701d80f2d3d')
            column(name: 'df_usage_batch_uid', value: '9e0f99e4-1e95-488d-a0c5-ff1353c84e39')
            column(name: 'wr_wrk_inst', value: 122830309)
            column(name: 'rh_account_number', value: 1000011450)
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'STDID')
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'number_of_copies', value: 100)
            column(name: 'payee_account_number', value: 1000011450)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '49680a3e-2986-44f5-943c-3701d80f2d3d')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Aug 2020 TUR')
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'a4b26f19-674e-4874-865d-62be5962658e')
            column(name: 'wr_wrk_inst', value: 123986581)
            column(name: 'usage_period', value: 2040)
            column(name: 'usage_source', value: 'Aug 2040 FR')
            column(name: 'number_of_copies', value: 30)
            column(name: 'number_of_pages', value: 6)
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'publication_type_weight', value: 2)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '870ee1dc-8596-409f-8ffe-717d17a33c9e')
            column(name: 'df_usage_batch_uid', value: '9e0f99e4-1e95-488d-a0c5-ff1353c84e39')
            column(name: 'product_family', value: 'AACL')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'PAID')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'STDID')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 20')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: 'ced40f77-704a-4b77-ae46-2698ef408df4')
            column(name: 'comment', value: 'AACL archived usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '870ee1dc-8596-409f-8ffe-717d17a33c9e')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2040)
            column(name: 'usage_source', value: 'Feb 2040 TUR')
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'number_of_pages', value: 341)
            column(name: 'right_limitation', value: 'ALL')
            column(name: 'baseline_uid', value: 'a4b26f19-674e-4874-865d-62be5962658e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '450aa265-2019-4caf-9c91-9e4d358f12a0')
            column(name: 'name', value: 'AACL Usage Batch')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '59e641bf-48a8-432b-a112-9953b7b7a62e')
            column(name: 'name', value: 'AACL Scenario Distribution 2020')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 108}, {"detailLicenseeClassId": 173, "aggregateLicenseeClassId": 113}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c7c0bbf9-e0a3-4d90-95af-aa9849e69404')
            column(name: 'df_usage_batch_uid', value: '450aa265-2019-4caf-9c91-9e4d358f12a0')
            column(name: 'df_scenario_uid', value: '59e641bf-48a8-432b-a112-9953b7b7a62e')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000000027)
            column(name: 'payee_account_number', value: 1000000027)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'c7c0bbf9-e0a3-4d90-95af-aa9849e69404')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 171)
            column(name: 'value_weight', value: 24.0000000)
            column(name: 'volume_weight', value: 5.0000000)
            column(name: 'volume_share', value: 50.0000000)
            column(name: 'value_share', value: 60.0000000)
            column(name: 'total_share', value: 2.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '02359982-ba49-4f64-9899-39ca9ae564be')
            column(name: 'df_usage_uid', value: 'f89f016d-0cc7-46b6-9f3f-63d2439458d5')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AACL batch 1\' Batch')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '56cc26ea-4502-4f6c-9adf-78471c798cd9')
            column(name: 'df_usage_uid', value: '49680a3e-2986-44f5-943c-3701d80f2d3d')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AACL batch 1\' Batch')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '94493ced-4e16-421d-a7f1-fab886c0443d')
            column(name: 'df_usage_uid', value: '870ee1dc-8596-409f-8ffe-717d17a33c9e')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AACL batch 1\' Batch')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '73889185-bf63-48a1-bc6c-212a767e6894')
            column(name: 'df_usage_uid', value: 'f89f016d-0cc7-46b6-9f3f-63d2439458d5')
            column(name: 'action_type_ind', value: 'WORK_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst 269040892 was found by standard number 1008902112377654XX')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '2b3b2ae1-6835-4926-b1b8-fefd9114b95b')
            column(name: 'df_usage_uid', value: '49680a3e-2986-44f5-943c-3701d80f2d3d')
            column(name: 'action_type_ind', value: 'WORK_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst 122830309 was found by standard number 1008902112377654XX')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '4bfc367e-3c5c-4cbb-9265-dfdfefa6c1c5')
            column(name: 'df_usage_uid', value: '870ee1dc-8596-409f-8ffe-717d17a33c9e')
            column(name: 'action_type_ind', value: 'WORK_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst 269040892 was found by standard number 1008902112377654XX')
        }

        rollback {
            dbRollback
        }
    }
}
