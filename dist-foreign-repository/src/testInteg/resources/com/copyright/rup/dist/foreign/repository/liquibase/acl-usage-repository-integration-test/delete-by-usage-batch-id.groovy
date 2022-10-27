databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-10-27-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserting test data for testDeleteByUsageBatchId')

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage_batch') {
            column(name: 'df_acl_usage_batch_uid', value: '8b81ce3e-6342-42a7-a3eb-c955d5e7ebba')
            column(name: 'name', value: 'ACL Usage Batch 2022')
            column(name: 'distribution_period', value: 202212)
            column(name: 'periods', value: '[202206, 202212]')
            column(name: 'is_editable', value: true)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_acl_usage') {
            column(name: 'df_acl_usage_uid', value: '52c239cf-a09a-4c96-9ec1-986fec4266cf')
            column(name: 'df_acl_usage_batch_uid', value: '8b81ce3e-6342-42a7-a3eb-c955d5e7ebba')
            column(name: 'usage_origin', value: 'RFA')
            column(name: 'channel', value: 'CCC')
            column(name: 'period', value: 202212)
            column(name: 'original_detail_id', value: 'OGN674GHHHB0111')
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'detail_licensee_class_id', value: 3)
            column(name: 'survey_country', value: 'United States')
            column(name: 'price', value: 20.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'content_flag', value: false)
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

        rollback {
            dbRollback
        }
    }
}
