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

    changeSet(id: '2020-11-03-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("Insert test data for testFindUsagePeriodsByFilter")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '31ac3937-157b-48c2-86b2-db28356fc868')
            column(name: 'name', value: 'AACL batch 1 for testFindUsagePeriodsByFilter')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e0fb5363-5dd4-434a-8d25-59edf9c6b2ce')
            column(name: 'df_usage_batch_uid', value: '31ac3937-157b-48c2-86b2-db28356fc868')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'rh_account_number', value: '1000011451')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'e0fb5363-5dd4-434a-8d25-59edf9c6b2ce')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: '6')
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '44fdf430-4cd0-4a32-98df-88f6c995ab78')
            column(name: 'df_usage_batch_uid', value: '31ac3937-157b-48c2-86b2-db28356fc868')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'rh_account_number', value: '1000011451')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '44fdf430-4cd0-4a32-98df-88f6c995ab78')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Aug 2019 FR')
            column(name: 'number_of_pages', value: '400')
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '87279dd4-e100-4b72-a561-49e7effe8238')
            column(name: 'name', value: 'AACL batch 2 for testFindUsagePeriodsByFilter')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '77f3a72b-bbdd-4966-a11c-357b50282749')
            column(name: 'df_usage_batch_uid', value: '87279dd4-e100-4b72-a561-49e7effe8238')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'rh_account_number', value: '1000011451')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '77f3a72b-bbdd-4966-a11c-357b50282749')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: '6')
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e7c2dc02-b89b-4932-ac27-1fe59c99086c')
            column(name: 'df_usage_batch_uid', value: '87279dd4-e100-4b72-a561-49e7effe8238')
            column(name: 'wr_wrk_inst', value: '122830308')
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'rh_account_number', value: '1000011451')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'e7c2dc02-b89b-4932-ac27-1fe59c99086c')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2010')
            column(name: 'usage_source', value: 'Aug 2019 FR')
            column(name: 'number_of_pages', value: '400')
            column(name: 'right_limitation', value: 'ALL')
        }
    }

    changeSet(id: '2020-03-12-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment("Insert test data for testAddToScenarioByFilter")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '5ceb887e-502e-463a-ae94-f925feff35d8')
            column(name: 'name', value: 'AACL batch 5')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: '2019')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0cd30b3e-ae74-466a-a7b1-a2d891b2123e')
            column(name: 'df_usage_batch_uid', value: '5ceb887e-502e-463a-ae94-f925feff35d8')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '0cd30b3e-ae74-466a-a7b1-a2d891b2123e')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9342062f-568e-4c27-8f33-c010a2afe61e')
            column(name: 'df_usage_batch_uid', value: '5ceb887e-502e-463a-ae94-f925feff35d8')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '9342062f-568e-4c27-8f33-c010a2afe61e')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2017')
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e21bcd1f-8040-4b44-93c7-4af732ac1916')
            column(name: 'df_usage_batch_uid', value: '5ceb887e-502e-463a-ae94-f925feff35d8')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'e21bcd1f-8040-4b44-93c7-4af732ac1916')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        // a scenario to add usage to
        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '09f85d7d-3a37-45b2-ab6e-7a341c3f115c')
            column(name: 'name', value: 'AACL Scenario 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'AACL Scenario Description 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'df1667ee-d3f3-4e1d-9dee-443f974b249e')
            column(name: 'df_scenario_uid', value: '09f85d7d-3a37-45b2-ab6e-7a341c3f115c')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }
    }

    changeSet(id: '2020-03-17-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment("Insert test data for testUpdatePublicationTypeWeight")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'b012c81c-2370-458e-a6cb-349b66d08e6e')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'usage_period', value: '2015')
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_copies', value: '10')
            column(name: 'number_of_pages', value: '12')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: '1.71')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a87b82ca-cfca-463d-96e9-fa856618c389')
            column(name: 'name', value: 'AACL batch 6')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: '2019')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '66d10c81-705e-4996-89f4-11e1635c4c31')
            column(name: 'name', value: 'AACL Scenario 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'AACL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '248add96-93d0-428d-9fe2-2e46c237a88b')
            column(name: 'df_usage_batch_uid', value: 'a87b82ca-cfca-463d-96e9-fa856618c389')
            column(name: 'df_scenario_uid', value: '66d10c81-705e-4996-89f4-11e1635c4c31')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '248add96-93d0-428d-9fe2-2e46c237a88b')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '161652a5-d822-493b-8242-d35dc881646f')
            column(name: 'df_usage_batch_uid', value: 'a87b82ca-cfca-463d-96e9-fa856618c389')
            column(name: 'df_scenario_uid', value: '66d10c81-705e-4996-89f4-11e1635c4c31')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '161652a5-d822-493b-8242-d35dc881646f')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2017')
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '61b7dc5a-aaec-482c-8c16-fe48a9464059')
            column(name: 'df_usage_batch_uid', value: 'a87b82ca-cfca-463d-96e9-fa856618c389')
            column(name: 'df_scenario_uid', value: '66d10c81-705e-4996-89f4-11e1635c4c31')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL baseline usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '61b7dc5a-aaec-482c-8c16-fe48a9464059')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2015')
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: '1.71')
            column(name: 'baseline_uid', value: 'b012c81c-2370-458e-a6cb-349b66d08e6e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '9a1fa971-90ae-4744-ab93-6f584e08bc1e')
            column(name: 'df_scenario_uid', value: '66d10c81-705e-4996-89f4-11e1635c4c31')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '9a1fa971-90ae-4744-ab93-6f584e08bc1e')
            column(name: 'df_usage_batch_uid', value: 'a87b82ca-cfca-463d-96e9-fa856618c389')
        }
    }

    changeSet(id: '2020-03-17-01', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("Insert test data for Audit")

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'c480ba67-82f3-4aad-a966-4586da4898e6')
            column(name: 'rh_account_number', value: '1000000027')
            column(name: 'name', value: 'Georgia State University Business Press [C]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '9e0f99e4-1e95-488d-a0c5-ff1353c84e39')
            column(name: 'name', value: 'AACL batch 1 for Audit')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: '2000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f89f016d-0cc7-46b6-9f3f-63d2439458d5')
            column(name: 'df_usage_batch_uid', value: '9e0f99e4-1e95-488d-a0c5-ff1353c84e39')
            column(name: 'wr_wrk_inst', value: '269040892')
            column(name: 'rh_account_number', value: '1000000027')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL comment 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'f89f016d-0cc7-46b6-9f3f-63d2439458d5')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '49680a3e-2986-44f5-943c-3701d80f2d3d')
            column(name: 'df_usage_batch_uid', value: '9e0f99e4-1e95-488d-a0c5-ff1353c84e39')
            column(name: 'wr_wrk_inst', value: '122830309')
            column(name: 'rh_account_number', value: '1000011450')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'STDID')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'number_of_copies', value: '100')
            column(name: 'payee_account_number', value: '1000011450')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '20')
            column(name: 'comment', value: 'AACL usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '49680a3e-2986-44f5-943c-3701d80f2d3d')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Aug 2020 TUR')
            column(name: 'detail_licensee_class_id', value: '143')
            column(name: 'publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'number_of_pages', value: '6')
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'a4b26f19-674e-4874-865d-62be5962658e')
            column(name: 'wr_wrk_inst', value: '123986581')
            column(name: 'usage_period', value: '2040')
            column(name: 'usage_source', value: 'Aug 2040 FR')
            column(name: 'number_of_copies', value: '30')
            column(name: 'number_of_pages', value: '6')
            column(name: 'detail_licensee_class_id', value: '143')
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'publication_type_weight', value: '2')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '870ee1dc-8596-409f-8ffe-717d17a33c9e')
            column(name: 'df_usage_batch_uid', value: '9e0f99e4-1e95-488d-a0c5-ff1353c84e39')
            column(name: 'product_family', value: 'AACL')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'PAID')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'STDID')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '500.00')
            column(name: 'net_amount', value: '80.00')
            column(name: 'service_fee_amount', value: '420.00')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL_March_40')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: 'ced40f77-704a-4b77-ae46-2698ef408df4')
            column(name: 'comment', value: 'AACL archived usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '870ee1dc-8596-409f-8ffe-717d17a33c9e')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2040')
            column(name: 'usage_source', value: 'Feb 2040 TUR')
            column(name: 'detail_licensee_class_id', value: '143')
            column(name: 'publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'number_of_pages', value: '341')
            column(name: 'right_limitation', value: 'ALL')
            column(name: 'baseline_uid', value: 'a4b26f19-674e-4874-865d-62be5962658e')
        }


        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '02359982-ba49-4f64-9899-39ca9ae564be')
            column(name: 'df_usage_uid', value: 'f89f016d-0cc7-46b6-9f3f-63d2439458d5')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AACL batch 1\' Batch')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '56cc26ea-4502-4f6c-9adf-78471c798cd9')
            column(name: 'df_usage_uid', value: '49680a3e-2986-44f5-943c-3701d80f2d3d')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AACL batch 1\' Batch')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '94493ced-4e16-421d-a7f1-fab886c0443d')
            column(name: 'df_usage_uid', value: '870ee1dc-8596-409f-8ffe-717d17a33c9e')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'AACL batch 1\' Batch')
            column(name: 'created_by_user', value: 'SYSTEM')
            column(name: 'updated_by_user', value: 'SYSTEM')
            column(name: 'created_datetime', value: '2012-03-15 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '73889185-bf63-48a1-bc6c-212a767e6894')
            column(name: 'df_usage_uid', value: 'f89f016d-0cc7-46b6-9f3f-63d2439458d5')
            column(name: 'action_type_ind', value: 'WORK_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst 269040892 was found by standard number 1008902112377654XX')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '2b3b2ae1-6835-4926-b1b8-fefd9114b95b')
            column(name: 'df_usage_uid', value: '49680a3e-2986-44f5-943c-3701d80f2d3d')
            column(name: 'action_type_ind', value: 'WORK_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst 122830309 was found by standard number 1008902112377654XX')
            column(name: 'created_datetime', value: '2019-04-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '4bfc367e-3c5c-4cbb-9265-dfdfefa6c1c5')
            column(name: 'df_usage_uid', value: '870ee1dc-8596-409f-8ffe-717d17a33c9e')
            column(name: 'action_type_ind', value: 'WORK_FOUND')
            column(name: 'action_reason', value: 'Wr Wrk Inst 269040892 was found by standard number 1008902112377654XX')
            column(name: 'created_datetime', value: '2019-04-01')
        }
    }

    changeSet(id: '2020-03-19-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment("Insert test data for testUsagesExistByDetailLicenseeClassAndFilter")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd1108958-44cc-4bb4-9bb5-66fcf5b42104')
            column(name: 'name', value: 'AACL batch 7')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: '2019')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8fc0c7d9-111d-42af-9595-6d36d9791b8f')
            column(name: 'df_usage_batch_uid', value: 'd1108958-44cc-4bb4-9bb5-66fcf5b42104')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '8fc0c7d9-111d-42af-9595-6d36d9791b8f')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2019')
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b05ecb89-4195-4829-9a8a-0ec2166baf69')
            column(name: 'df_usage_batch_uid', value: 'd1108958-44cc-4bb4-9bb5-66fcf5b42104')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'b05ecb89-4195-4829-9a8a-0ec2166baf69')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2017')
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '108')
            column(name: 'publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'fb128d7c-432d-49d9-935c-6c92fc88c9c3')
            column(name: 'df_usage_batch_uid', value: 'd1108958-44cc-4bb4-9bb5-66fcf5b42104')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '10')
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'fb128d7c-432d-49d9-935c-6c92fc88c9c3')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: '2015')
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: '12')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '113')
            column(name: 'publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
        }
    }
}
