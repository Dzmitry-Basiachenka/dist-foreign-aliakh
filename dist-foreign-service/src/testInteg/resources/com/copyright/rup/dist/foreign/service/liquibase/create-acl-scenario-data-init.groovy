databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-07-05-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testInsertScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: 'b6d2b116-a113-4013-b59a-fd166b9257d6')
            column(name: 'name', value: 'ACL Fund Pool 202112')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '51823f41-7087-48a0-beb3-d780c7dbf4a4')
            column(name: 'df_acl_fund_pool_uid', value: 'b6d2b116-a113-4013-b59a-fd166b9257d6')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'net_amount', value: 1799.88)
            column(name: 'gross_amount', value: 2015.48)
            column(name: 'is_ldmt', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool_detail') {
            column(name: 'df_acl_fund_pool_detail_uid', value: '310f31af-c20b-44a9-9696-24c24561fbaa')
            column(name: 'df_acl_fund_pool_uid', value: 'b6d2b116-a113-4013-b59a-fd166b9257d6')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'license_type', value: 'ACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'net_amount', value: 450799.01)
            column(name: 'gross_amount', value: 634420.51)
            column(name: 'is_ldmt', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '87ee29b3-fdb6-4d67-a08d-99371aadca77')
            column(name: 'name', value: 'ACL Usage Batch 202112')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202012, 202112, 201006, 201806]')
            column(name: 'is_editable', value: true)
        }

        //will be excluded from scenario by wr_wrk_inst
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '9a59ba38-2303-4299-9934-7b393de7eb27')
            column(name: 'df_acl_usage_batch_uid', value: '87ee29b3-fdb6-4d67-a08d-99371aadca77')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202012)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0110')
            column(name: 'wr_wrk_inst', value: 122820650)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '6497c851-7d5e-4832-9166-124982d08228')
            column(name: 'df_acl_usage_batch_uid', value: '87ee29b3-fdb6-4d67-a08d-99371aadca77')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202112)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0111')
            column(name: 'wr_wrk_inst', value: 823333789)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 5)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        //will be excluded from scenario by quantity
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: 'b7b160b2-80f7-4617-af07-cda7d849fc30')
            column(name: 'df_acl_usage_batch_uid', value: '87ee29b3-fdb6-4d67-a08d-99371aadca77')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202112)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0112')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 2000)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        //will be excluded from scenario by usage age weight
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '4fce47c2-e40e-4c6a-a5bb-5c5e279d2a7c')
            column(name: 'df_acl_usage_batch_uid', value: '87ee29b3-fdb6-4d67-a08d-99371aadca77')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 201006)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0113')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        //will be excluded by Deny
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '19082c05-cc1d-4549-9dcb-da9867be242e')
            column(name: 'df_acl_usage_batch_uid', value: '87ee29b3-fdb6-4d67-a08d-99371aadca77')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 201006)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0114')
            column(name: 'wr_wrk_inst', value: 122820666)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        //will be excluded by ineligible grant
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: 'edc2b656-30d5-4214-a6bc-884c753f65cd')
            column(name: 'df_acl_usage_batch_uid', value: '87ee29b3-fdb6-4d67-a08d-99371aadca77')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 201006)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0115')
            column(name: 'wr_wrk_inst', value: 122820777)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '45db9c9a-6260-4131-acea-2f4a21245af9')
            column(name: 'df_acl_usage_batch_uid', value: '87ee29b3-fdb6-4d67-a08d-99371aadca77')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 201806)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0116')
            column(name: 'wr_wrk_inst', value: 109110189)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'content_unit_price', value: 5.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'df3096cb-eb6f-440f-9450-98293b45e81c')
            column(name: 'name', value: 'ACL Grant Set 202112')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'e384149f-2b9e-4310-9975-9258490a1d4c')
            column(name: 'df_acl_grant_set_uid', value: 'df3096cb-eb6f-440f-9450-98293b45e81c')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Print&Digital')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '739484f4-2672-42ef-b6dd-ab8558a5d5d9')
            column(name: 'df_acl_grant_set_uid', value: 'df3096cb-eb6f-440f-9450-98293b45e81c')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print&Digital')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'c876793e-d6e8-4fbe-8c81-9de8f3747506')
            column(name: 'df_acl_grant_set_uid', value: 'df3096cb-eb6f-440f-9450-98293b45e81c')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 823333789)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '57e53b30-1d8b-4973-a753-2ccbef17f4b1')
            column(name: 'df_acl_grant_set_uid', value: 'df3096cb-eb6f-440f-9450-98293b45e81c')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 823333789)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 2000017000)
            column(name: 'is_eligible', value: false)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '287b4580-a519-4c4c-97fa-5e028f17161e')
            column(name: 'df_acl_grant_set_uid', value: 'df3096cb-eb6f-440f-9450-98293b45e81c')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 109110189)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '5b66b659-a966-496e-be80-014fbf6f6e89')
            column(name: 'df_acl_grant_set_uid', value: 'df3096cb-eb6f-440f-9450-98293b45e81c')
            column(name: 'grant_status', value: 'DENY')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 109110189)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'bdd18b84-80e5-44b1-8ea3-95501c057e63')
            column(name: 'df_acl_grant_set_uid', value: 'df3096cb-eb6f-440f-9450-98293b45e81c')
            column(name: 'grant_status', value: 'DENY')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 122820666)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'eb2952c0-5000-4f9e-af8b-cce0413dc7c3')
            column(name: 'df_acl_grant_set_uid', value: 'df3096cb-eb6f-440f-9450-98293b45e81c')
            column(name: 'grant_status', value: 'DENY')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 122820666)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: false)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '2a173b41-75e3-4478-80ef-157527b18996')
            column(name: 'name', value: 'ACL Fund Pool 202112-2')
            column(name: 'period', value: 202112)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '65b930f1-777d-4a51-b878-bea3c68624d8')
            column(name: 'name', value: 'ACL Usage Batch 202112-2')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '83e881cf-b258-42c1-849e-b2ec32b302b5')
            column(name: 'name', value: 'ACL Grant Set 202112-2')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: '1995d50d-41c6-4e81-8c82-51a983bbecf8')
            column(name: 'df_acl_fund_pool_uid', value: '2a173b41-75e3-4478-80ef-157527b18996')
            column(name: 'df_acl_usage_batch_uid', value: '65b930f1-777d-4a51-b878-bea3c68624d8')
            column(name: 'df_acl_grant_set_uid', value: '83e881cf-b258-42c1-849e-b2ec32b302b5')
            column(name: 'name', value: 'ACL Scenario 202112')
            column(name: 'period_end_date', value: 202112)
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
            column(name: 'created_by_user', value: 'auser@copyright.com')
            column(name: 'updated_by_user', value: 'auser@copyright.com')
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_fund_pool') {
            column(name: 'df_acl_fund_pool_uid', value: '274ad62f-365e-41a6-a169-0e85e04d52d4')
            column(name: 'name', value: 'ACL Fund Pool 202212')
            column(name: 'period', value: 202212)
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_manual', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '2474c9ae-dfaf-404f-b4eb-17b7c88794d2')
            column(name: 'name', value: 'ACL Usage Batch 202212')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: '7e89e5c4-7db6-44b6-9a82-43166ec8da63')
            column(name: 'name', value: 'ACL Grant Set 202212')
            column(name: 'grant_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario') {
            column(name: 'df_acl_scenario_uid', value: 'c65e9c0a-006f-4b79-b828-87d2106330b7')
            column(name: 'df_acl_fund_pool_uid', value: '274ad62f-365e-41a6-a169-0e85e04d52d4')
            column(name: 'df_acl_usage_batch_uid', value: '2474c9ae-dfaf-404f-b4eb-17b7c88794d2')
            column(name: 'df_acl_grant_set_uid', value: '7e89e5c4-7db6-44b6-9a82-43166ec8da63')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: 'eb80bec3-6b46-414a-83a8-40a8b94642c0')
            column(name: 'df_acl_scenario_uid', value: '1995d50d-41c6-4e81-8c82-51a983bbecf8')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '86625299-47a7-4adc-bc65-43c527e3c7af')
            column(name: 'df_acl_scenario_uid', value: '1995d50d-41c6-4e81-8c82-51a983bbecf8')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'fffabfef-3411-4f26-a3de-2e203f38716d')
            column(name: 'df_acl_scenario_uid', value: '1995d50d-41c6-4e81-8c82-51a983bbecf8')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_usage_age_weight') {
            column(name: 'df_acl_scenario_usage_age_weight_uid', value: 'e6eff13e-6566-4585-9859-306b54553fe5')
            column(name: 'df_acl_scenario_uid', value: 'c65e9c0a-006f-4b79-b828-87d2106330b7')
            column(name: 'period_prior', value: 0)
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_pub_type_weight') {
            column(name: 'df_acl_scenario_pub_type_weight_uid', value: '8ad5583b-7999-4a7b-b7aa-526cf32572d3')
            column(name: 'df_acl_scenario_uid', value: 'c65e9c0a-006f-4b79-b828-87d2106330b7')
            column(name: 'df_publication_type_uid', value: "73876e58-2e87-485e-b6f3-7e23792dd214")
            column(name: 'weight', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_licensee_class') {
            column(name: 'df_acl_scenario_licensee_class_uid', value: 'ada3da9b-e49f-449b-8942-f9844f5b8aca')
            column(name: 'df_acl_scenario_uid', value: 'c65e9c0a-006f-4b79-b828-87d2106330b7')
            column(name: 'detail_licensee_class_id', value: 43)
            column(name: 'aggregate_licensee_class_id', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: 'edc2b656-30d5-4214-a6bc-884c753f65cd')
            column(name: 'df_acl_scenario_uid', value: 'c65e9c0a-006f-4b79-b828-87d2106330b7')
            column(name: 'period_end_date', value: 201006)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0116')
            column(name: 'wr_wrk_inst', value: 122820777)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'usage_quantity', value: 10)
            column(name: 'usage_age_weight', value: 1)
            column(name: 'weighted_copies', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_scenario_detail') {
            column(name: 'df_acl_scenario_detail_uid', value: '7fe7cd5a-5094-4ea3-a457-c516edab9637')
            column(name: 'df_acl_scenario_uid', value: 'c65e9c0a-006f-4b79-b828-87d2106330b7')
            column(name: 'period_end_date', value: 201006)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0117')
            column(name: 'wr_wrk_inst', value: 122820777)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'usage_quantity', value: 10)
            column(name: 'usage_age_weight', value: 1)
            column(name: 'weighted_copies', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: "df_acl_share_detail_uid", value: "46847474-5b6c-4c15-bdbb-3211eb8a4d6d")
            column(name: "df_acl_scenario_uid", value: "c65e9c0a-006f-4b79-b828-87d2106330b7")
            column(name: "df_acl_scenario_detail_uid", value: "edc2b656-30d5-4214-a6bc-884c753f65cd")
            column(name: "type_of_use", value: "PRINT")
            column(name: "rh_account_number", value: 123456723)
            column(name: "aggregate_licensee_class_id", value: 1)
            column(name: "volume_weight", value: 10.0000000)
            column(name: "value_weight", value: 100.0000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: "df_acl_share_detail_uid", value: "7f4bab51-2aa6-4e88-8637-e806fd2baa3c")
            column(name: "df_acl_scenario_uid", value: "c65e9c0a-006f-4b79-b828-87d2106330b7")
            column(name: "df_acl_scenario_detail_uid", value: "edc2b656-30d5-4214-a6bc-884c753f65cd")
            column(name: "type_of_use", value: "DIGITAL")
            column(name: "rh_account_number", value: 123456723)
            column(name: "aggregate_licensee_class_id", value: 1)
            column(name: "volume_weight", value: 10.0000000)
            column(name: "value_weight", value: 100.0000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: "df_acl_share_detail_uid", value: "7e1ae49d-307d-428a-9622-3d32899c8967")
            column(name: "df_acl_scenario_uid", value: "c65e9c0a-006f-4b79-b828-87d2106330b7")
            column(name: "df_acl_scenario_detail_uid", value: "7fe7cd5a-5094-4ea3-a457-c516edab9637")
            column(name: "type_of_use", value: "PRINT")
            column(name: "rh_account_number", value: 123456723)
            column(name: "aggregate_licensee_class_id", value: 1)
            column(name: "volume_weight", value: 10.0000000)
            column(name: "value_weight", value: 100.0000000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_share_detail') {
            column(name: "df_acl_share_detail_uid", value: "190a17a4-03e8-412c-93ca-fd5eb97bf868")
            column(name: "df_acl_scenario_uid", value: "c65e9c0a-006f-4b79-b828-87d2106330b7")
            column(name: "df_acl_scenario_detail_uid", value: "7fe7cd5a-5094-4ea3-a457-c516edab9637")
            column(name: "type_of_use", value: "DIGITAL")
            column(name: "rh_account_number", value: 123456723)
            column(name: "aggregate_licensee_class_id", value: 1)
            column(name: "volume_weight", value: 10.0000000)
            column(name: "value_weight", value: 100.0000000)
        }

        rollback {
            dbRollback
        }
    }
}
