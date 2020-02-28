databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-12-27-01', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("Insert test data for testFindDtosByFilter")

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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '38e3190a-cf2b-4a2a-8a14-1f6e5f09011c')
            column(name: 'name', value: 'AACL batch 2')
            column(name: 'payment_date', value: '2018-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: '2018')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'ce439b92-dfe1-4607-9cba-01394cbfc087')
            column(name: 'df_usage_batch_uid', value: '38e3190a-cf2b-4a2a-8a14-1f6e5f09011c')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '1')
            column(name: 'comment', value: 'AACL NEW status')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'ce439b92-dfe1-4607-9cba-01394cbfc087')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2018')
            column(name: 'usage_source', value: 'Feb 2018 TUR')
            column(name: 'number_of_pages', value: '341')
        }
    }

    changeSet(id: '2020-01-27-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment("Insert test data for testIsValidFilteredUsageStatus")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'adcc460c-c4ae-4750-99e8-b9fe91787ce1')
            column(name: 'name', value: 'AACL batch 3')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: '2020')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '44600a96-5cf5-4e11-80df-d52caddd33aa')
            column(name: 'df_usage_batch_uid', value: 'adcc460c-c4ae-4750-99e8-b9fe91787ce1')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL comment 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '44600a96-5cf5-4e11-80df-d52caddd33aa')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '67750f86-3077-4857-b2f6-af31c3d3d5b1')
            column(name: 'df_usage_batch_uid', value: 'adcc460c-c4ae-4750-99e8-b9fe91787ce1')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'rh_account_number', value: '1000011450')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'AACL comment 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '67750f86-3077-4857-b2f6-af31c3d3d5b1')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: '6')
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd2ec8772-a343-4c5a-b7e3-7d0ade0be21b')
            column(name: 'df_usage_batch_uid', value: 'adcc460c-c4ae-4750-99e8-b9fe91787ce1')
            column(name: 'wr_wrk_inst', value: '987654321')
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '1')
            column(name: 'comment', value: 'AACL NEW status')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'd2ec8772-a343-4c5a-b7e3-7d0ade0be21b')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2018 TUR')
            column(name: 'number_of_pages', value: '341')
        }
    }

    changeSet(id: '2020-01-28-01', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("Insert test data for testUpdate")

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '77cfa2dd-efac-48a9-bd5b-98659ff2265a')
            column(name: 'rh_account_number', value: '1000011451')
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '600ad926-e7dd-4086-b283-87e6579395ce')
            column(name: 'name', value: 'AACL batch 4')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8315e53b-0a7e-452a-a62c-17fe959f3f84')
            column(name: 'df_usage_batch_uid', value: '600ad926-e7dd-4086-b283-87e6579395ce')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'rh_account_number', value: '1000011451')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '8315e53b-0a7e-452a-a62c-17fe959f3f84')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: '6')
            column(name: 'right_limitation', value: 'ALL')
        }
    }

    changeSet(id: '2020-13-02-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("Insert test data for testDeleteByBatchId")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '940ca71c-fd90-4ffd-aa20-b293c0f49891')
            column(name: 'name', value: 'AACL batch')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8919261f-ff5d-4a66-a5b9-02138ab13e11')
            column(name: 'df_usage_batch_uid', value: '940ca71c-fd90-4ffd-aa20-b293c0f49891')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'rh_account_number', value: '1000011451')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '8919261f-ff5d-4a66-a5b9-02138ab13e11')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: '6')
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3088f483-d1ef-4a6e-a076-a291152762c9')
            column(name: 'df_usage_batch_uid', value: '940ca71c-fd90-4ffd-aa20-b293c0f49891')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'rh_account_number', value: '1000011451')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '3088f483-d1ef-4a6e-a076-a291152762c9')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2019 FR')
            column(name: 'number_of_pages', value: '400')
            column(name: 'right_limitation', value: 'ALL')
        }
    }

    changeSet(id: '2020-27-02-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment("Insert test data for testFindFromBaseline")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '0085ceb9-6d2a-4a03-87a5-84e369dec6e1')
            column(name: 'wr_wrk_inst', value: '107039807')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2019 FR')
            column(name: 'number_of_copies', value: '10')
            column(name: 'number_of_pages', value: '15')
            column(name: 'detail_licensee_class_id', value: '113')
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: '1.71')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '5bcb69c5-5758-4cd2-8ca0-c8d63d3a9d18')
            column(name: 'wr_wrk_inst', value: '246997864')
            column(name: 'usage_period', value: '2018')
            column(name: 'usage_source', value: 'Aug 2018 FR')
            column(name: 'number_of_copies', value: '20')
            column(name: 'number_of_pages', value: '25')
            column(name: 'detail_licensee_class_id', value: '195')
            column(name: 'original_publication_type', value: 'Book series')
            column(name: 'publication_type_uid', value: 'a3dff475-fc06-4d8c-b7cc-f093073ada6f')
            column(name: 'publication_type_weight', value: '4.29')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '819ad2fc-24fe-45b6-965f-5df633a57dba')
            column(name: 'wr_wrk_inst', value: '123986581')
            column(name: 'usage_period', value: '2016')
            column(name: 'usage_source', value: 'Aug 2016 FR')
            column(name: 'number_of_copies', value: '30')
            column(name: 'number_of_pages', value: '35')
            column(name: 'detail_licensee_class_id', value: '143')
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'publication_type_weight', value: '2')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage 3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'c7cb9533-0720-43ff-abfb-6e4513a942d6')
            column(name: 'wr_wrk_inst', value: '310652370')
            column(name: 'usage_period', value: '2015')
            column(name: 'usage_source', value: 'Aug 2015 FR')
            column(name: 'number_of_copies', value: '40')
            column(name: 'number_of_pages', value: '45')
            column(name: 'detail_licensee_class_id', value: '141')
            column(name: 'original_publication_type', value: 'Fiction or Poetry')
            column(name: 'publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: '3.5')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage 4')
        }
    }
}
