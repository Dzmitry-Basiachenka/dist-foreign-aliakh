databaseChangeLog {
    property(file: 'database.properties')


    changeSet(id: '2019-12-27-01', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for testFindDtosByProductFamilyAaclFilter")

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05844db0-e0e4-4423-8966-7f1c6160f000')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'name', value: 'Georgia State University Business Press [C]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'fd137df2-7308-49a0-b72e-0ea6924249a9')
            column(name: 'rh_account_number', value: '1000011450')
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6e6f656a-e080-4426-b8ea-985b69f8814d')
            column(name: 'name', value: 'AACL batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: '2019')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6c91f04e-60dc-49e0-9cdc-e782e0b923e2')
            column(name: 'df_usage_batch_uid', value: '6e6f656a-e080-4426-b8ea-985b69f8814d')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL comment 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '6c91f04e-60dc-49e0-9cdc-e782e0b923e2')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0b5ac9fc-63e2-4162-8d63-953b7023293c')
            column(name: 'df_usage_batch_uid', value: '6e6f656a-e080-4426-b8ea-985b69f8814d')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'rh_account_number', value: '1000011450')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'AACL comment 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '0b5ac9fc-63e2-4162-8d63-953b7023293c')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: '6')
            column(name: 'right_limitation', value: 'ALL')
        }
    }
}
