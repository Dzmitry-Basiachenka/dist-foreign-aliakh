databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-10-31-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testWriteBaselineValueUpdatesCsvReport, testWriteBaselineValueUpdatesEmptyCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: 'c61fae87-8e77-4c60-a21b-ea4ffc3812eb')
            column(name: 'period', value: 202206)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'rh_account_number', value: 100006858)
            column(name: 'wr_wrk_inst', value: 823333788)
            column(name: 'system_title', value: 'Redfords and Mitchells')
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'standard_number', value: '1873-7774')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 6.0000000000)
            column(name: 'price_flag', value: true)
            column(name: 'price_in_usd', value: 2.5000000000)
            column(name: 'price_year', value: 2022)
            column(name: 'price_type', value: 'Individual')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'price_source', value: 'http://google.com')
            column(name: 'content_flag', value: false)
            column(name: 'content_unit_price_flag', value: false)
            column(name: 'currency', value: 'USD')
            column(name: 'currency_exchange_rate', value: 2)
            column(name: 'currency_exchange_rate_date', value: '2022-09-10')
            column(name: 'updated_datetime', value: '2022-09-11 12:00:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-09-11 12:00:00+00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: 'd693dc89-d9ec-40aa-af4b-ea3368b722ff')
            column(name: 'df_udm_value_uid', value: 'c61fae87-8e77-4c60-a21b-ea4ffc3812eb')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'UDM Value batch for period \'2022\' was populated')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-15 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: 'b015338d-b7b2-4ded-8777-ad2884eaa7a3')
            column(name: 'df_udm_value_uid', value: 'c61fae87-8e77-4c60-a21b-ea4ffc3812eb')
            column(name: 'action_type_ind', value: 'PUBLISH_TO_BASELINE')
            column(name: 'action_reason', value: 'UDM Value batch for period \'2022\' was published to baseline')
            column(name: 'created_by_user', value: 'ajohn@copyright.com')
            column(name: 'updated_by_user', value: 'ajohn@copyright.com')
            column(name: 'created_datetime', value: '2022-02-16 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-16 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: '49edd70a-58d8-4135-a147-304483049ed9')
            column(name: 'df_udm_value_uid', value: 'c61fae87-8e77-4c60-a21b-ea4ffc3812eb')
            column(name: 'action_type_ind', value: 'VALUE_EDIT')
            column(name: 'action_reason', value: 'The field \'Pub Type\' was edited. Old Value was \'NL - Newsletter\'. New Value is \'NP - Newsletter\'')
            column(name: 'created_by_user', value: 'ajohn@copyright.com')
            column(name: 'updated_by_user', value: 'ajohn@copyright.com')
            column(name: 'created_datetime', value: '2022-02-16 12:10:00+00')
            column(name: 'updated_datetime', value: '2022-02-16 12:10:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '01a40ecd-b06e-4658-9a66-cb48ac53c1ea')
            column(name: 'period', value: 202206)
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
            column(name: 'price_year', value: 2022)
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
            column(name: 'currency_exchange_rate_date', value: '2022-09-10')
            column(name: 'comment', value: 'Comment')
            column(name: 'content_comment', value: 'Content comment')
            column(name: 'updated_datetime', value: '2022-09-11 12:00:00+00')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-09-11 12:00:00+00')
            column(name: 'created_by_user', value: 'user@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: 'a9e503ed-7543-40ff-bf43-0caefa3e662f')
            column(name: 'df_udm_value_uid', value: '01a40ecd-b06e-4658-9a66-cb48ac53c1ea')
            column(name: 'action_type_ind', value: 'CREATED')
            column(name: 'action_reason', value: 'UDM Value batch for period \'2022\' was populated')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2022-02-15 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: '94433126-e284-44b2-85a0-2c7106054352')
            column(name: 'df_udm_value_uid', value: '01a40ecd-b06e-4658-9a66-cb48ac53c1ea')
            column(name: 'action_type_ind', value: 'PUBLISH_TO_BASELINE')
            column(name: 'action_reason', value: 'UDM Value batch for period \'2022\' was published to baseline')
            column(name: 'created_by_user', value: 'ajohn@copyright.com')
            column(name: 'updated_by_user', value: 'ajohn@copyright.com')
            column(name: 'created_datetime', value: '2022-02-16 12:00:00+00')
            column(name: 'updated_datetime', value: '2022-02-16 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: 'cbc43d2c-5473-442c-82b4-65414a72e6c3')
            column(name: 'df_udm_value_uid', value: '01a40ecd-b06e-4658-9a66-cb48ac53c1ea')
            column(name: 'action_type_ind', value: 'VALUE_EDIT')
            column(name: 'action_reason', value: 'The field \'Pub Type\' was edited. Old Value is not specified. New Value is \'BK2 - Book series\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2022-02-16 12:10:00+00')
            column(name: 'updated_datetime', value: '2022-02-16 12:10:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: 'e75cbe9d-e02b-4c19-b367-9725bff6a419')
            column(name: 'df_udm_value_uid', value: '01a40ecd-b06e-4658-9a66-cb48ac53c1ea')
            column(name: 'action_type_ind', value: 'VALUE_EDIT')
            column(name: 'action_reason', value: 'The field \'Content Flag\' was edited. Old Value is \'N\'. New Value is \'Y\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2022-02-16 12:20:00+00')
            column(name: 'updated_datetime', value: '2022-02-16 12:20:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: 'ac47397c-888d-47e0-960e-917e6b2ef4b0')
            column(name: 'df_udm_value_uid', value: '01a40ecd-b06e-4658-9a66-cb48ac53c1ea')
            column(name: 'action_type_ind', value: 'VALUE_EDIT')
            column(name: 'action_reason', value: 'The field \'Content Source\' was edited. Old Value is not specified. New Value is \'Book\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2022-02-16 12:20:01+00')
            column(name: 'updated_datetime', value: '2022-02-16 12:20:01+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: '7890b722-2f41-405e-b0b2-3fa8d3379533')
            column(name: 'df_udm_value_uid', value: '01a40ecd-b06e-4658-9a66-cb48ac53c1ea')
            column(name: 'action_type_ind', value: 'VALUE_EDIT')
            column(name: 'action_reason', value: 'The field \'Content\' was edited. Old Value is not specified. New Value is \'2\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2022-02-16 12:20:02+00')
            column(name: 'updated_datetime', value: '2022-02-16 12:20:02+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: '64accba8-87d4-4b10-87c8-21bafd7913cf')
            column(name: 'df_udm_value_uid', value: '01a40ecd-b06e-4658-9a66-cb48ac53c1ea')
            column(name: 'action_type_ind', value: 'VALUE_EDIT')
            column(name: 'action_reason', value: 'The field \'Content Unit Price\' was edited. Old Value is not specified. New Value is \'6.00\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2022-02-16 12:20:03+00')
            column(name: 'updated_datetime', value: '2022-02-16 12:20:03+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value_audit') {
            column(name: 'df_udm_value_audit_uid', value: 'd213f784-7097-44e0-9458-92e21ba8cb88')
            column(name: 'df_udm_value_uid', value: '01a40ecd-b06e-4658-9a66-cb48ac53c1ea')
            column(name: 'action_type_ind', value: 'VALUE_EDIT')
            column(name: 'action_reason', value: 'The field \'CUP Flag\' was edited. Old Value is \'N\'. New Value is \'Y\'')
            column(name: 'created_by_user', value: 'jjohn@copyright.com')
            column(name: 'updated_by_user', value: 'jjohn@copyright.com')
            column(name: 'created_datetime', value: '2022-02-16 12:20:04+00')
            column(name: 'updated_datetime', value: '2022-02-16 12:20:04+00')
        }

        rollback {
            dbRollback
        }
    }
}
