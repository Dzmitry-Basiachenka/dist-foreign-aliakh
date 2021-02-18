databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-01-29-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for LoadClassifiedUsagesIntegrationTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '77cfa2dd-efac-48a9-bd5b-98659ff2265a')
            column(name: 'rh_account_number', value: '7001413934')
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a7e971e0-2bd1-484e-b769-4712752a5441')
            column(name: 'name', value: 'Test Batch')
            column(name: 'product_family', value: 'AACL')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd5973116-2ea0-4808-80f5-93016e24cfa4')
            column(name: 'df_usage_batch_uid', value: 'a7e971e0-2bd1-484e-b769-4712752a5441')
            column(name: 'wr_wrk_inst', value: '122825976')
            column(name: 'rh_account_number', value: '7001413934')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'd5973116-2ea0-4808-80f5-93016e24cfa4')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2018 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '833aa413-ee36-4f1c-bea1-ec7a0f6e507d')
            column(name: 'df_usage_batch_uid', value: 'a7e971e0-2bd1-484e-b769-4712752a5441')
            column(name: 'wr_wrk_inst', value: '122825976')
            column(name: 'rh_account_number', value: '1000003578')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '833aa413-ee36-4f1c-bea1-ec7a0f6e507d')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9e4c3be6-fafd-44f3-af7f-915e861e31c7')
            column(name: 'df_usage_batch_uid', value: 'a7e971e0-2bd1-484e-b769-4712752a5441')
            column(name: 'wr_wrk_inst', value: '122825976')
            column(name: 'rh_account_number', value: '7001413934')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '9e4c3be6-fafd-44f3-af7f-915e861e31c7')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2018 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "806c782e-64fc-4118-957f-6cd914553d16")
            column(name: "df_usage_uid", value: "d5973116-2ea0-4808-80f5-93016e24cfa4")
            column(name: "action_type_ind", value: "RH_FOUND")
            column(name: "action_reason", value: "Rightsholder account 7001413934 was found in RMS")
            column(name: 'created_datetime', value: '2012-03-15 11:42:51.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "920d3c6a-f6b1-4547-9266-8fc713bd1d73")
            column(name: "df_usage_uid", value: "d5973116-2ea0-4808-80f5-93016e24cfa4")
            column(name: "action_type_ind", value: "WORK_FOUND")
            column(name: "action_reason", value: "Wr Wrk Inst 122825976 was found in PI")
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "bfb42fe0-acec-4c72-9c8a-bec7dc334c70")
            column(name: "df_usage_uid", value: "d5973116-2ea0-4808-80f5-93016e24cfa4")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'Test Batch'")
            column(name: 'created_datetime', value: '2012-03-15 11:40:53.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "cbde771e-62d4-4919-b2d6-ed8fd77e0507")
            column(name: "df_usage_uid", value: "833aa413-ee36-4f1c-bea1-ec7a0f6e507d")
            column(name: "action_type_ind", value: "RH_FOUND")
            column(name: "action_reason", value: "Rightsholder account 7001413934 was found in RMS")
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "9e1de31a-200d-4765-b1e7-d82ec5cbe5de")
            column(name: "df_usage_uid", value: "833aa413-ee36-4f1c-bea1-ec7a0f6e507d")
            column(name: "action_type_ind", value: "WORK_FOUND")
            column(name: "action_reason", value: "Wr Wrk Inst 122825976 was found in PI")
            column(name: 'created_datetime', value: '2012-03-15 11:41:53.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "69da57fe-a841-40dc-9e3d-6fb627773efa")
            column(name: "df_usage_uid", value: "833aa413-ee36-4f1c-bea1-ec7a0f6e507d")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'Test Batch'")
            column(name: 'created_datetime', value: '2012-03-15 11:40:54.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "1599b4e5-992d-496d-931f-432ba3e08fbb")
            column(name: "df_usage_uid", value: "9e4c3be6-fafd-44f3-af7f-915e861e31c7")
            column(name: "action_type_ind", value: "RH_FOUND")
            column(name: "action_reason", value: "Rightsholder account 7001413934 was found in RMS")
            column(name: 'created_datetime', value: '2012-03-15 11:42:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "2386214b-b6d1-4bc7-bde5-85d76883fe81")
            column(name: "df_usage_uid", value: "9e4c3be6-fafd-44f3-af7f-915e861e31c7")
            column(name: "action_type_ind", value: "WORK_FOUND")
            column(name: "action_reason", value: "Wr Wrk Inst 122825976 was found in PI")
            column(name: 'created_datetime', value: '2012-03-15 11:41:53.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "64f7258c0-7f23-4cf7-a10d-20acbc6ad4cc")
            column(name: "df_usage_uid", value: "9e4c3be6-fafd-44f3-af7f-915e861e31c7")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'Test Batch'")
            column(name: 'created_datetime', value: '2012-03-15 11:40:54.735531+03')
        }

        rollback ""
    }
}
