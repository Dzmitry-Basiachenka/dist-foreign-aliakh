databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-04-04-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testFindCountByFilter, testFindDtosByFilter, testSortingFindDtosByFilter')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'bc3d734c-09a3-4b08-9fae-09e99665f828')
            column(name: 'name', value: 'ACL Usage Batch 2022')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '8ff48add-0eea-4fe3-81d0-3264c6779936')
            column(name: 'df_acl_usage_batch_uid', value: 'bc3d734c-09a3-4b08-9fae-09e99665f828')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0110')
            column(name: 'wr_wrk_inst', value: 122820638)
            column(name: 'system_title', value: 'Technology review')
            column(name: 'detail_licensee_class_id', value: 1)
            column(name: 'survey_country', value: 'Germany')
            column(name: 'content_unit_price_flag', value: false)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 2)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user1@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'ec80ea9b-ae5c-4a9d-b03a-4e82b599b433')
            column(name: 'name', value: 'ACL Usage Batch 2021')
            column(name: 'distribution_period', value: 202112)
            column(name: 'periods', value: '[202106, 202112]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '0eeef531-b779-4b3b-827d-b44b2261c6db')
            column(name: 'df_acl_usage_batch_uid', value: 'ec80ea9b-ae5c-4a9d-b03a-4e82b599b433')
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'CCC')
            column(name: 'period', value: 202112)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0111')
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 10.0000000000)
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'annualized_copies', value: 1)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '2ba0fab7-746d-41e0-87b5-c2b3997ce0ae')
            column(name: 'df_acl_usage_batch_uid', value: 'ec80ea9b-ae5c-4a9d-b03a-4e82b599b433')
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202112)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0112')
            column(name: 'wr_wrk_inst', value: 123822477)
            column(name: 'system_title', value: 'The Portland business journal')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'content_unit_price', value: 9.0000000000)
            column(name: 'content_unit_price_flag', value: false)
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'annualized_copies', value: 2)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-10 13:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 13:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: 'dfc0f9f4-2c50-4e1f-ad1a-a29fc2f9f4cd')
            column(name: 'df_acl_usage_batch_uid', value: 'ec80ea9b-ae5c-4a9d-b03a-4e82b599b433')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202106)
            column(name: 'original_detail_id', value: 'OGN994GHHSB987')
            column(name: 'wr_wrk_inst', value: 227738245)
            column(name: 'system_title', value: 'The Wall Street journal')
            column(name: 'detail_licensee_class_id', value: 4)
            column(name: 'survey_country', value: 'France')
            column(name: 'publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'content_unit_price', value: 8.0000000000)
            column(name: 'content_unit_price_flag', value: false)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 3)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-10 14:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 14:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '1e9ab4dd-8526-4309-9c54-20226c48cd27')
            column(name: 'df_acl_usage_batch_uid', value: 'ec80ea9b-ae5c-4a9d-b03a-4e82b599b433')
            column(name: 'usage_origin', value: 'SS')
            column(name: 'channel', value: 'CCC')
            column(name: 'period', value: 202106)
            column(name: 'original_detail_id', value: 'OGN554GHHSG005 !@#$%^&*()_+-=?/\\\'"}{][<>')
            column(name: 'wr_wrk_inst', value: 459577481)
            column(name: 'system_title', value: 'The New York times !@#$%^&*()_+-=?/\\\'"}{][<>')
            column(name: 'detail_licensee_class_id', value: 6)
            column(name: 'survey_country', value: 'Spain !@#$%^&*()_+-=?/\\\'"}{][<>')
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'content_unit_price', value: 7.0000000000)
            column(name: 'content_unit_price_flag', value: false)
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'annualized_copies', value: 4)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-10 15:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-14 15:00:00+00')
        }

        rollback {
            dbRollback
        }
    }
}
