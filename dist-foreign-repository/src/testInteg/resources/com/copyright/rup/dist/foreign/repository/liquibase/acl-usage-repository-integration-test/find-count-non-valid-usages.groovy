databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2022-07-06-01', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testFindInvalidUsages')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'a764abd9-7391-481a-b4fe-755452fd3935')
            column(name: 'name', value: 'ACL Grant Set 202112')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'd18a7fba-e41d-4325-ad19-cbfc088a4e94')
            column(name: 'df_acl_grant_set_uid', value: 'a764abd9-7391-481a-b4fe-755452fd3935')
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
            column(name: 'df_acl_grant_detail_uid', value: 'aeebb5e8-65f5-4fe6-bfb3-33575309f342')
            column(name: 'df_acl_grant_set_uid', value: 'a764abd9-7391-481a-b4fe-755452fd3935')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'type_of_use_status', value: 'Print Only')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '36d8901e-1f3b-4e68-8c95-1f8b02740ed2')
            column(name: 'name', value: 'ACL Usage Batch 2022')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: 'dc62f3d2-f81d-44a1-b89b-ddbfe159c86b')
            column(name: 'df_acl_usage_batch_uid', value: '36d8901e-1f3b-4e68-8c95-1f8b02740ed2')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202012)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0008')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 10.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 2)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_set') {
            column(name: 'df_acl_grant_set_uid', value: 'c5d66bb6-99c5-4fb5-a30b-22555dd685ea')
            column(name: 'name', value: 'ACL Grant Set 202112 - 2')
            column(name: 'grant_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'license_type', value: 'ACL')
            column(name: 'is_editable', value: false)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '07cbca09-aa7e-47c5-bfd2-abca57d380ea')
            column(name: 'df_acl_grant_set_uid', value: 'c5d66bb6-99c5-4fb5-a30b-22555dd685ea')
            column(name: 'grant_status', value: 'DENY')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Print&Digital')
            column(name: 'wr_wrk_inst', value: 122820555)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '3b8c1621-f973-4c30-bac7-14956c3e1628')
            column(name: 'df_acl_grant_set_uid', value: 'c5d66bb6-99c5-4fb5-a30b-22555dd685ea')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Print&Digital')
            column(name: 'wr_wrk_inst', value: 122820666)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: false)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: '4b02353b-f891-4ad7-8a75-0e7f711f0232')
            column(name: 'df_acl_grant_set_uid', value: 'c5d66bb6-99c5-4fb5-a30b-22555dd685ea')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'a31b0206-1014-4a13-990b-3b0374c3e186')
            column(name: 'df_acl_grant_set_uid', value: 'c5d66bb6-99c5-4fb5-a30b-22555dd685ea')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 123822477)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_grant_detail') {
            column(name: 'df_acl_grant_detail_uid', value: 'e6e506ec-0d4c-48ae-9d31-bcee1c85781d')
            column(name: 'df_acl_grant_set_uid', value: 'c5d66bb6-99c5-4fb5-a30b-22555dd685ea')
            column(name: 'grant_status', value: 'GRANT')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'type_of_use_status', value: 'Digital Only')
            column(name: 'wr_wrk_inst', value: 227738245)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'rh_account_number', value: 1000028511)
            column(name: 'is_eligible', value: true)
            column(name: 'created_datetime', value: '2021-01-30T00:00:00-04:00')
            column(name: 'updated_datetime', value: '2021-01-31T00:00:00-04:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'c653d257-4180-4fb4-9119-f001063e4a56')
            column(name: 'name', value: 'ACL Usage Batch 2021')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: true)
        }

        // ACL usage without content unit price
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: 'dfc2c9d1-422a-4832-a673-38a782148ea0')
            column(name: 'df_acl_usage_batch_uid', value: 'c653d257-4180-4fb4-9119-f001063e4a56')
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'CCC')
            column(name: 'period', value: 202112)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0009')
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'annualized_copies', value: 1)
            column(name: 'quantity', value: 2)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        // ACL usage without publication type uid
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: 'dae138a7-5cb6-482c-943a-e7140ca5859f')
            column(name: 'df_acl_usage_batch_uid', value: 'c653d257-4180-4fb4-9119-f001063e4a56')
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202112)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0010')
            column(name: 'wr_wrk_inst', value: 123822477)
            column(name: 'system_title', value: 'The Portland business journal')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'content_unit_price', value: 9.0000000000)
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 2)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-10 13:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 13:00:00+00')
        }

        // ACL usage without content unit price and publication type uid
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '0131106e-1a96-411a-8fc6-19ae749cf22a')
            column(name: 'df_acl_usage_batch_uid', value: 'c653d257-4180-4fb4-9119-f001063e4a56')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202106)
            column(name: 'original_detail_id', value: 'OGN994GHHSB987')
            column(name: 'wr_wrk_inst', value: 227738245)
            column(name: 'system_title', value: 'The Wall Street journal')
            column(name: 'detail_licensee_class_id', value: 4)
            column(name: 'survey_country', value: 'France')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 3)
            column(name: 'quantity', value: 2)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-10 14:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 14:00:00+00')
        }

        //Non granted
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '4628bc76-f2a5-459f-91e3-97d8e0af6af4')
            column(name: 'df_acl_usage_batch_uid', value: 'c653d257-4180-4fb4-9119-f001063e4a56')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0011')
            column(name: 'wr_wrk_inst', value: 122820555)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 2)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        //Non eligible
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '2653e7c3-6fbc-4de1-a6e0-9aceeeb39506')
            column(name: 'df_acl_usage_batch_uid', value: 'c653d257-4180-4fb4-9119-f001063e4a56')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0015')
            column(name: 'wr_wrk_inst', value: 122820666)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        //Bigger quantity
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '2dc5f0b8-ab25-4146-a24c-633a1b9983dd')
            column(name: 'df_acl_usage_batch_uid', value: 'c653d257-4180-4fb4-9119-f001063e4a56')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0015')
            column(name: 'wr_wrk_inst', value: 122820777)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 2000)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        //Weight = 0
        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '26d2795c-3ee6-438b-a37f-c117da8dc351')
            column(name: 'df_acl_usage_batch_uid', value: 'c653d257-4180-4fb4-9119-f001063e4a56')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 201012)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0015')
            column(name: 'wr_wrk_inst', value: 122820777)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '5e1db7a6-7d9c-4fb9-9e41-00a4c9e493fc')
            column(name: 'df_acl_usage_batch_uid', value: 'c653d257-4180-4fb4-9119-f001063e4a56')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 201812)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0015')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'content_unit_price', value: 11.0000000000)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        rollback {
            dbRollback
        }
    }
}
