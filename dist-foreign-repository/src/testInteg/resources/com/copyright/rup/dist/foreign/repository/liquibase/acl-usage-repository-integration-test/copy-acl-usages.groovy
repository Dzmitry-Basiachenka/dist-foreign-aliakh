databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-08-09-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testCopyAclUsages')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: 'e0cc3eb3-5c85-4745-b868-e83529864d1a')
            column(name: 'name', value: 'ACL Usage Batch 2022')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '4d8708a6-c9ad-42fa-a1fa-3a705532aae6')
            column(name: 'df_acl_usage_batch_uid', value: 'e0cc3eb3-5c85-4745-b868-e83529864d1a')
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'CCC')
            column(name: 'period', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0111')
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'survey_country', value: 'United States')
            column(name: 'price', value: 20.0000000000)
            column(name: 'content', value: 2.0000000000)
            column(name: 'content_unit_price', value: 10.0000000000)
            column(name: 'content_unit_price_flag', value: false)
            column(name: 'reported_type_of_use', value: 'PRINT_COPIES')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'annualized_copies', value: 1.00000)
            column(name: 'quantity', value: 10)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '0d4f2035-0ced-4e87-adbb-1e17f238a270')
            column(name: 'df_acl_usage_batch_uid', value: 'e0cc3eb3-5c85-4745-b868-e83529864d1a')
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'Rightsdirect')
            column(name: 'period', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0112')
            column(name: 'wr_wrk_inst', value: 123822477)
            column(name: 'system_title', value: 'The Portland business journal')
            column(name: 'detail_licensee_class_id', value: 2)
            column(name: 'survey_country', value: 'Portugal')
            column(name: 'publication_type_uid', value: '73876e58-2e87-485e-b6f3-7e23792dd214')
            column(name: 'price', value: 27.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content', value: 3.0000000000)
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 9.0000000000)
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'reported_type_of_use', value: 'FAX_PHOTOCOPIES')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'annualized_copies', value: 2.00000)
            column(name: 'quantity', value: 11)
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-10 13:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-14 13:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '3516305e-d3ad-413b-819e-a390b81d4aa7')
            column(name: 'name', value: 'ACL Usage Batch 2022 Copy')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        rollback {
            dbRollback
        }
    }
}
