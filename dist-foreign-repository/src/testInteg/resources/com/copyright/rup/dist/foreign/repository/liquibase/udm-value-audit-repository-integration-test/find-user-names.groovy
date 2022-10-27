databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-10-27-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testFindUserNames')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '14edbc9b-1473-4fc9-95f6-07b3ef45e851')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: 'fd10d851-c222-4547-ad21-d46e3d669a62')
            column(name: 'period', value: 202106)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985899)
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'publication_type_uid', value: 'f1f523ca-1b46-4d3a-842d-99252785187c')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 5.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'price_in_usd', value: 2.5000000000)
            column(name: 'price_year', value: 2021)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'content', value: 2)
            column(name: 'content_source', value: 'Book')
            column(name: 'content_flag', value: true)
            column(name: 'content_unit_price', value: 6.0000000000)
            column(name: 'content_unit_price_flag', value: true)
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 2)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
            column(name: 'comment', value: 'Comment')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2021-09-11T00:00:00-04:00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-09-11T00:00:00-04:00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: '10e5a506-daa8-4657-b846-f89495581782')
            column(name: 'df_udm_value_uid', value: 'fd10d851-c222-4547-ad21-d46e3d669a62')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'UDM Value batch for period \'2021\' was populated')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: 'ed682997-682d-46f7-87a5-53fe4414f857')
            column(name: 'df_udm_value_uid', value: 'fd10d851-c222-4547-ad21-d46e3d669a62')
            column(name: 'action_type_ind', value: 'PUBLISH_TO_BASELINE')
            column(name: 'action_reason', value: 'UDM Value batch for period \'2021\' was published to baseline')
            column(name: 'created_by_user', value: 'ajohn@copyright.com')
            column(name: 'updated_by_user', value: 'ajohn@copyright.com')
            column(name: 'created_datetime', value: '2021-02-16 12:00:00+00')
            column(name: 'updated_datetime', value: '2021-02-16 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: '3913b1ea-2c73-4e99-906b-3110ec20df76')
            column(name: 'df_udm_value_uid', value: 'fd10d851-c222-4547-ad21-d46e3d669a62')
            column(name: 'action_type_ind', value: 'VALUE_EDIT')
            column(name: 'action_reason', value: 'The field \'Pub Type\' was edited. Old Value is not specified. New Value is \'BK2 - Book series\'')
            column(name: 'created_by_user', value: 'ajohn@copyright.com')
            column(name: 'updated_by_user', value: 'ajohn@copyright.com')
            column(name: 'created_datetime', value: '2021-02-16 12:10:00+00')
            column(name: 'updated_datetime', value: '2021-02-16 12:10:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: 'a0636c54-9f74-4e05-bbdb-241ce2532358')
            column(name: 'df_udm_value_uid', value: 'fd10d851-c222-4547-ad21-d46e3d669a62')
            column(name: 'action_type_ind', value: 'VALUE_EDIT')
            column(name: 'action_reason', value: 'The field \'Content Flag\' was edited. Old Value is \'N\'. New Value is \'Y\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2021-02-16 12:10:00+00')
            column(name: 'updated_datetime', value: '2021-02-16 12:10:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: '319ad925-55b6-4b2b-a270-635ecda777fe')
            column(name: 'df_udm_value_uid', value: 'fd10d851-c222-4547-ad21-d46e3d669a62')
            column(name: 'action_type_ind', value: 'VALUE_EDIT')
            column(name: 'action_reason', value: 'The field \'Content Source\' was edited. Old Value is not specified. New Value is \'Book\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2021-02-16 12:10:00+00')
            column(name: 'updated_datetime', value: '2021-02-16 12:10:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: '9012ea95-3eab-410c-ba0c-0045dd0ef735')
            column(name: 'df_udm_value_uid', value: 'fd10d851-c222-4547-ad21-d46e3d669a62')
            column(name: 'action_type_ind', value: 'VALUE_EDIT')
            column(name: 'action_reason', value: 'The field \'Content\' was edited. Old Value is not specified. New Value is \'2\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2021-02-16 12:10:00+00')
            column(name: 'updated_datetime', value: '2021-02-16 12:10:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: '1b6271e8-c5ba-4ec2-b1f6-913cc4a81161')
            column(name: 'df_udm_value_uid', value: 'fd10d851-c222-4547-ad21-d46e3d669a62')
            column(name: 'action_type_ind', value: 'VALUE_EDIT')
            column(name: 'action_reason', value: 'The field \'Content Unit Price\' was edited. Old Value is not specified. New Value is \'6.00\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2021-02-16 12:10:00+00')
            column(name: 'updated_datetime', value: '2021-02-16 12:10:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: '82cb5454-60d2-4193-a153-786cb8b5bc55')
            column(name: 'df_udm_value_uid', value: 'fd10d851-c222-4547-ad21-d46e3d669a62')
            column(name: 'action_type_ind', value: 'VALUE_EDIT')
            column(name: 'action_reason', value: 'The field \'CUP Flag\' was edited. Old Value is \'N\'. New Value is \'Y\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2021-02-16 12:10:00+00')
            column(name: 'updated_datetime', value: '2021-02-16 12:10:00+00')
        }

        rollback {
            dbRollback
        }
    }
}
