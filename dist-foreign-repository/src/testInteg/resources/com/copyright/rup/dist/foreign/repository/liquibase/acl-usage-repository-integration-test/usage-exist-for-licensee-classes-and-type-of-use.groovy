databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-07-22-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testUsageExistForLicenseeClassesAndTypeOfUse')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '55adc071-8bbb-46be-a56e-0daba0883a97')
            column(name: 'name', value: 'ACL Usage Batch 2022')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '3f4e09b9-0f96-46f5-a2f6-0ca312bbd201')
            column(name: 'df_acl_usage_batch_uid', value: '55adc071-8bbb-46be-a56e-0daba0883a97')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202212)
            column(name: 'original_detail_id', value: 'OGN675GHHHB0110')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: 'be07df25-159b-4b65-93f7-9f81e6fc513b')
            column(name: 'df_acl_usage_batch_uid', value: '55adc071-8bbb-46be-a56e-0daba0883a97')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202212)
            column(name: 'original_detail_id', value: 'OGN675GHHHB0111')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '83d9e8c0-01db-4c2a-9ef6-a800537fa7b9')
            column(name: 'name', value: 'ACL Usage Batch 2021')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: 'cfb5794a-bde2-433d-8f14-34e60b095a96')
            column(name: 'df_acl_usage_batch_uid', value: '83d9e8c0-01db-4c2a-9ef6-a800537fa7b9')
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'CCC')
            column(name: 'period', value: 202112)
            column(name: 'original_detail_id', value: 'OGN675GHHHB0112')
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 10.0000000000)
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'annualized_copies', value: 1)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '5e8da1ba-3b20-4f8e-96ed-f9506da3b87a')
            column(name: 'name', value: 'ACL Grant Set 202112')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'ce916fd7-a0bf-4d58-adb8-0728c5253672')
            column(name: 'df_acl_grant_set_uid', value: '5e8da1ba-3b20-4f8e-96ed-f9506da3b87a')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'a10df60d-412b-42cb-b2c4-dcb56b1c041f')
            column(name: 'df_acl_grant_set_uid', value: '5e8da1ba-3b20-4f8e-96ed-f9506da3b87a')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'system_title', value: 'Castanea')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        rollback {
            dbRollback
        }
    }
}
