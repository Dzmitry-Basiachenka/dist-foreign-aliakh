databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-01-20-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testWriteUsagesForClassificationAndFindIds')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'aed882d5-7625-4039-8781-a6676e11c579')
            column(name: 'name', value: 'AACL batch')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1208f434-3d98-49d5-bdc6-baa611d2d006')
            column(name: 'df_usage_batch_uid', value: 'aed882d5-7625-4039-8781-a6676e11c579')
            column(name: 'wr_wrk_inst', value: 122825976)
            column(name: 'system_title', value: 'Ecotoxicology')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000003578)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '1208f434-3d98-49d5-bdc6-baa611d2d006')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0ac10a6f-1cf3-45b5-8d3b-0b4b0777a8e0')
            column(name: 'df_usage_batch_uid', value: 'aed882d5-7625-4039-8781-a6676e11c579')
            column(name: 'wr_wrk_inst', value: 122820420)
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '00087475')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 7001413934)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '0ac10a6f-1cf3-45b5-8d3b-0b4b0777a8e0')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2018 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '67d36799-5523-474d-91f6-2e12756a4918')
            column(name: 'df_usage_batch_uid', value: 'aed882d5-7625-4039-8781-a6676e11c579')
            column(name: 'wr_wrk_inst', value: 109713043)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '67d36799-5523-474d-91f6-2e12756a4918')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2018 TUR')
            column(name: 'number_of_pages', value: 6)
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2020-01-28-00', author: 'Ihar Suvorau<isuvorau@copyright.com>') {
        comment('Inserting AACL test data for testWriteResearchStatusCsvReport which should not be displayed in reports')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd08c250e-a8d8-491b-84d6-3945fd07be78')
            column(name: 'name', value: 'AACL Usage Batch 2015')
            column(name: 'product_family', value: 'AACL')
            column(name: 'payment_date', value: '2015-06-30')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '94ef6c15-bcd1-41ab-a65e-6dccec4a3213')
            column(name: 'df_usage_batch_uid', value: 'd08c250e-a8d8-491b-84d6-3945fd07be78')
            column(name: 'wr_wrk_inst', value: 122803735)
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '94ef6c15-bcd1-41ab-a65e-6dccec4a3213')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: 12)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '731ce234-0c44-41f3-971c-e5de3be7ab91')
            column(name: 'df_usage_batch_uid', value: 'd08c250e-a8d8-491b-84d6-3945fd07be78')
            column(name: 'wr_wrk_inst', value: 130297955)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 1)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '731ce234-0c44-41f3-971c-e5de3be7ab91')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: 199)
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2020-02-05-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testWriteAaclUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '600ad926-e7dd-4086-b283-87e6579395ce')
            column(name: 'name', value: 'AACL batch 2020')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8315e53b-0a7e-452a-a62c-17fe959f3f84')
            column(name: 'df_usage_batch_uid', value: '600ad926-e7dd-4086-b283-87e6579395ce')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL classified usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '8315e53b-0a7e-452a-a62c-17fe959f3f84')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '64194cf9-177f-4220-9eb5-01040324b8b2')
            column(name: 'df_usage_batch_uid', value: '600ad926-e7dd-4086-b283-87e6579395ce')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL classified usage 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '64194cf9-177f-4220-9eb5-01040324b8b2')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'detail_licensee_class_id', value: 120)
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'abced58b-fff2-470b-99fc-11f2a3ae098a')
            column(name: 'df_usage_batch_uid', value: '600ad926-e7dd-4086-b283-87e6579395ce')
            column(name: 'wr_wrk_inst', value: 122820420)
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '00087475')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 7001413934)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'abced58b-fff2-470b-99fc-11f2a3ae098a')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2018 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2020-03-11-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testWriteAuditAaclCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '2eb52c26-b555-45ae-b8c5-21289dfeeac4')
            column(name: 'wr_wrk_inst', value: 123986581)
            column(name: 'usage_period', value: 2016)
            column(name: 'usage_source', value: 'Aug 2016 FR')
            column(name: 'number_of_copies', value: 30)
            column(name: 'number_of_pages', value: 6)
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'publication_type_weight', value: 2)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage')
            column(name: 'updated_datetime', value: '2020-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '29689635-c6ff-483c-972d-09eb2febb9e0')
            column(name: 'name', value: 'AACL batch 2020 for audit')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c794662f-a5d6-4b86-8955-582723631656')
            column(name: 'df_usage_batch_uid', value: '29689635-c6ff-483c-972d-09eb2febb9e0')
            column(name: 'wr_wrk_inst', value: 122830308)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL usage 1')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'c794662f-a5d6-4b86-8955-582723631656')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2019 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3c96f468-abaa-4db7-9004-4012d8ba8e0d')
            column(name: 'df_usage_batch_uid', value: '29689635-c6ff-483c-972d-09eb2febb9e0')
            column(name: 'wr_wrk_inst', value: 123986581)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 1000011451)
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'comment', value: 'AACL audit usage 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '3c96f468-abaa-4db7-9004-4012d8ba8e0d')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'usage_period', value: 2016)
            column(name: 'usage_source', value: 'Aug 201 FR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
            column(name: 'baseline_uid', value: '2eb52c26-b555-45ae-b8c5-21289dfeeac4')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '027aa879-d963-41f1-bacf-db22ebe3584a')
            column(name: 'df_usage_batch_uid', value: '29689635-c6ff-483c-972d-09eb2febb9e0')
            column(name: 'wr_wrk_inst', value: 122820420)
            column(name: 'system_title', value: 'Castanea')
            column(name: 'standard_number', value: '00087475')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'rh_account_number', value: 7001413934)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 20)
            column(name: 'comment', value: 'AACL comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '027aa879-d963-41f1-bacf-db22ebe3584a')
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Aug 2018 TUR')
            column(name: 'number_of_pages', value: 6)
            column(name: 'right_limitation', value: 'ALL')
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2020-04-22-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for testWriteWorkSharesByAggLcClassSummaryCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8fb69838-9f62-456f-ad52-58b55d71c305')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '8aafed93-6964-41f6-be6e-f5e628c03ece')
            column(name: 'rh_account_number', value: 1000011881)
            column(name: 'name', value: 'William B. Eerdmans Publishing Company')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3a8eed5d-a2f2-47d2-9cba-b047d9947706')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 1 for Work Shares by Aggregate Licensee Class Summary Report test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '65e72126-2d74-4018-a06a-7fa4c81f0b33')
            column(name: 'df_fund_pool_uid', value: '3a8eed5d-a2f2-47d2-9cba-b047d9947706')
            column(name: 'df_aggregate_licensee_class_id', value: 171)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'name', value: 'AACL Usage Batch 1 for Work Shares by Aggregate Licensee Class Summary Report test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 4)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
            column(name: 'name', value: 'AACL Scenario 1 for Work Shares by Aggregate Licensee Class Summary Report test')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "fund_pool_uid": "3a8eed5d-a2f2-47d2-9cba-b047d9947706", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8eb9dbbc-3535-42cc-8094-2d90849952e2')
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '8eb9dbbc-3535-42cc-8094-2d90849952e2')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 680.0000000000)
            column(name: 'volume_weight', value: 10.0000000000)
            column(name: 'volume_share', value: 0.4098360656)
            column(name: 'value_share', value: 0.3970571062)
            column(name: 'total_share', value: 0.4095917984)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e951defc-79c7-48d0-b7c7-958df4bdf2cb')
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
            column(name: 'wr_wrk_inst', value: 124181386)
            column(name: 'work_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'system_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000011881)
            column(name: 'payee_account_number', value: 1000011881)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'e951defc-79c7-48d0-b7c7-958df4bdf2cb')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 791.8000000000)
            column(name: 'volume_weight', value: 3.7000000000)
            column(name: 'volume_share', value: 0.1516393443)
            column(name: 'value_share', value: 0.4623379657)
            column(name: 'total_share', value: 0.3093360901)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f6411672-02d2-4e2e-8682-69f5ad7db8c4')
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'f6411672-02d2-4e2e-8682-69f5ad7db8c4')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 240.8000000000)
            column(name: 'volume_weight', value: 10.7000000000)
            column(name: 'volume_share', value: 0.4385245902)
            column(name: 'value_share', value: 0.1406049282)
            column(name: 'total_share', value: 0.2810721113)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5408ee8b-30e0-416f-ada8-cbf08d62b26e')
            column(name: 'df_usage_batch_uid', value: '31e3e4c5-83cb-4989-a82e-d9f80a8b893c')
            column(name: 'df_scenario_uid', value: '42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '5408ee8b-30e0-416f-ada8-cbf08d62b26e')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'value_weight', value: 240.8000000000)
            column(name: 'volume_weight', value: 10.7000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'total_share', value: 1.0000000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '149ab28f-c795-4e29-9418-815c87dec127')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 2 for Work Shares by Aggregate Licensee Class Summary Report test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'f88db923-ed05-4980-bb1d-8ae327824346')
            column(name: 'df_fund_pool_uid', value: '3a8eed5d-a2f2-47d2-9cba-b047d9947706')
            column(name: 'df_aggregate_licensee_class_id', value: 171)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'name', value: 'AACL Usage Batch 2 for Work Shares by Aggregate Licensee Class Summary Report test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 4)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
            column(name: 'name', value: 'AACL Scenario 2 for Work Shares by Aggregate Licensee Class Summary Report test')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "fund_pool_uid": "149ab28f-c795-4e29-9418-815c87dec127", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '3ae57b51-691d-4a97-95dd-434304325654')
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'check_number', value: 578945)
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '3ae57b51-691d-4a97-95dd-434304325654')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 680.0000000000)
            column(name: 'volume_weight', value: 10.0000000000)
            column(name: 'volume_share', value: 0.4098360656)
            column(name: 'value_share', value: 0.3970571062)
            column(name: 'total_share', value: 0.4095917984)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '4444ed28-cba5-4bea-9923-64a6264bca9d')
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
            column(name: 'wr_wrk_inst', value: 124181386)
            column(name: 'work_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'system_title', value: 'history of Christianity in Asia, Africa, and Latin America')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000011881)
            column(name: 'payee_account_number', value: 1000011881)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'check_number', value: 578945)
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '4444ed28-cba5-4bea-9923-64a6264bca9d')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 791.8000000000)
            column(name: 'volume_weight', value: 3.7000000000)
            column(name: 'volume_share', value: 0.1516393443)
            column(name: 'value_share', value: 0.4623379657)
            column(name: 'total_share', value: 0.3093360901)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'af7a5a9c-363c-4360-8fc0-7a318528f431')
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'check_number', value: 578945)
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'af7a5a9c-363c-4360-8fc0-7a318528f431')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 110)
            column(name: 'value_weight', value: 240.8000000000)
            column(name: 'volume_weight', value: 10.7000000000)
            column(name: 'volume_share', value: 0.4385245902)
            column(name: 'value_share', value: 0.1406049282)
            column(name: 'total_share', value: 0.2810721113)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '190a8e32-37ed-4fe2-987a-b481849c7939')
            column(name: 'df_usage_batch_uid', value: '96842167-5444-4064-b84a-800e626cbb71')
            column(name: 'df_scenario_uid', value: '3704ecc3-927b-4b30-a860-6c58e7654c5e')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'check_number', value: 578945)
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '190a8e32-37ed-4fe2-987a-b481849c7939')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 180)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'value_weight', value: 240.8000000000)
            column(name: 'volume_weight', value: 10.7000000000)
            column(name: 'volume_share', value: 1.0000000000)
            column(name: 'value_share', value: 1.0000000000)
            column(name: 'total_share', value: 1.0000000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2020-04-23-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Insert test data for testWriteAaclScenarioUsagesCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'ffc1587c-9b05-4681-a3cc-dc02cec7fadc')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool for Export Detail Scenario test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '879229c9-82c5-4e82-8516-8366dd0e18ee')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'name', value: 'British Film Institute (BFI)')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '8474cceb-2367-4d03-b240-5ea6d2819a53')
            column(name: 'df_fund_pool_uid', value: 'ffc1587c-9b05-4681-a3cc-dc02cec7fadc')
            column(name: 'df_aggregate_licensee_class_id', value: 171)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '354f8342-0bf0-4a89-aa9a-6f4428a29e9d')
            column(name: 'name', value: 'AACL Usage Batch for Export Details Scenario test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '153b80ba-85e6-48ee-b5c3-c81664827e8a')
            column(name: 'name', value: 'AACL Scenario for Export Details Scenario test')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '13212981-431f-4311-97d5-1a39bc252afc')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_copies', value: 10)
            column(name: 'number_of_pages', value: 12)
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 1.71)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage')
            column(name: 'updated_datetime', value: '2020-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '53079eb1-ddfb-4d9b-a914-4402cb4b0f49')
            column(name: 'df_usage_batch_uid', value: '354f8342-0bf0-4a89-aa9a-6f4428a29e9d')
            column(name: 'df_scenario_uid', value: '153b80ba-85e6-48ee-b5c3-c81664827e8a')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '53079eb1-ddfb-4d9b-a914-4402cb4b0f49')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 171)
            column(name: 'value_weight', value: 0.1000000)
            column(name: 'volume_weight', value: 0.2000000)
            column(name: 'volume_share', value: 0.3000000)
            column(name: 'value_share', value: 0.4000000)
            column(name: 'total_share', value: 0.5000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7ec3e2df-5274-4eba-a493-b9502db11f4c')
            column(name: 'df_usage_batch_uid', value: '354f8342-0bf0-4a89-aa9a-6f4428a29e9d')
            column(name: 'df_scenario_uid', value: '153b80ba-85e6-48ee-b5c3-c81664827e8a')
            column(name: 'wr_wrk_inst', value: 109040891)
            column(name: 'work_title', value: 'Biological Journal')
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'net_amount', value: 75.00)
            column(name: 'service_fee_amount', value: 25.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 300)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '7ec3e2df-5274-4eba-a493-b9502db11f4c')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 200)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'value_weight', value: 0.6000000)
            column(name: 'volume_weight', value: 0.7000000)
            column(name: 'volume_share', value: 0.8000000)
            column(name: 'value_share', value: 0.9000000)
            column(name: 'total_share', value: 0.56000000)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'baseline_uid', value: '13212981-431f-4311-97d5-1a39bc252afc')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '04c60c30-c11b-4cd4-9525-7a42793b00a6')
            column(name: 'df_scenario_uid', value: '153b80ba-85e6-48ee-b5c3-c81664827e8a')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '04c60c30-c11b-4cd4-9525-7a42793b00a6')
            column(name: 'df_usage_batch_uid', value: '354f8342-0bf0-4a89-aa9a-6f4428a29e9d')
        }

        //archived scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '0b28a1ff-ee07-4087-8980-ad7e7ea493f8')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool for Export Detail Archived Scenario test')
            column(name: 'total_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '21c1240c-61f0-4aa7-a7d8-e4965415f80b')
            column(name: 'rh_account_number', value: 258001168)
            column(name: 'name', value: 'Delmar Learning, a division of Cengage Learning')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '1ec28821-2402-4063-bf2e-3bd0a20a8ed1')
            column(name: 'df_fund_pool_uid', value: '0b28a1ff-ee07-4087-8980-ad7e7ea493f8')
            column(name: 'df_aggregate_licensee_class_id', value: 171)
            column(name: 'gross_amount', value: 1000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '1e56b3b1-40a9-4f4f-8e2f-868caaba8693')
            column(name: 'name', value: 'AACL Usage Batch For Export Details Archived Scenario Test')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5429c31b-ffd1-4a7f-9b24-8c7809417fce')
            column(name: 'name', value: 'AACL Scenario For Export Details Archived Scenario Test')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}, {"period": 2019, "weight": 0.75}], "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 113, "aggregateLicenseeClassId": 141}, {"detailLicenseeClassId": 110, "aggregateLicenseeClassId": 143}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '44f90e4f-3038-4738-a41a-4e989d80b0f2')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_copies', value: 10)
            column(name: 'number_of_pages', value: 12)
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
            column(name: 'publication_type_weight', value: 1.71)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'AACL baseline usage')
            column(name: 'updated_datetime', value: '2020-02-14 11:45:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '04a64d17-eab8-4191-b162-f9c12e540795')
            column(name: 'df_usage_batch_uid', value: '1e56b3b1-40a9-4f4f-8e2f-868caaba8693')
            column(name: 'df_scenario_uid', value: '5429c31b-ffd1-4a7f-9b24-8c7809417fce')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'payee_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2020-11-03')
            column(name: 'ccc_event_id', value: '53257')
            column(name: 'distribution_name', value: 'AACL March 40')
            column(name: 'distribution_date', value: '2020-11-03')
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '04a64d17-eab8-4191-b162-f9c12e540795')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2019)
            column(name: 'usage_source', value: 'Feb 2019 TUR')
            column(name: 'number_of_pages', value: 200)
            column(name: 'right_limitation', value: 'DIGITAL')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'value_weight', value: 0.7900000)
            column(name: 'volume_weight', value: 0.5900000)
            column(name: 'volume_share', value: 0.4500000)
            column(name: 'value_share', value: 0.0780000)
            column(name: 'total_share', value: 0.9500000)
            column(name: 'original_publication_type', value: 'Textbook')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'baseline_uid', value: '44f90e4f-3038-4738-a41a-4e989d80b0f2')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '89e290d3-c656-4b23-9e20-1daa975eeb19')
            column(name: 'df_scenario_uid', value: '5429c31b-ffd1-4a7f-9b24-8c7809417fce')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ARCHIVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: '89e290d3-c656-4b23-9e20-1daa975eeb19')
            column(name: 'df_usage_batch_uid', value: '1e56b3b1-40a9-4f4f-8e2f-868caaba8693')
        }

        rollback {
            dbRollback
        }
    }

    changeSet(id: '2020-06-17-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment('Insert test data for Undistributed Liabilities Report test')

        //Should be included into report as it isn't associated with any scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '2a3aac29-6694-48fe-8c5d-c6709614ae73')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 1 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 100.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'a463d517-0887-4d68-9422-a99e8997ddd5')
            column(name: 'df_fund_pool_uid', value: '2a3aac29-6694-48fe-8c5d-c6709614ae73')
            column(name: 'df_aggregate_licensee_class_id', value: 171)
            column(name: 'gross_amount', value: 100.00)
        }

        //Should be included into report as it is associated with SUBMITTED scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '6b2ba3de-f2a7-4d9b-8da1-d84118ddba30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 2 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '044f4190-5c0e-41e5-994b-983b7810ea74')
            column(name: 'df_fund_pool_uid', value: '6b2ba3de-f2a7-4d9b-8da1-d84118ddba30')
            column(name: 'df_aggregate_licensee_class_id', value: 141)
            column(name: 'gross_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '4d6adcc5-9852-4322-b946-88e0ba977620')
            column(name: 'name', value: 'AACL Usage Batch 2 for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '43242b46-1a85-4938-9865-b9b354b6ae44')
            column(name: 'name', value: 'AACL Scenario 2 for Undistributed Liabilities Report test')
            column(name: 'status_ind', value: 'SUBMITTED')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}], "fund_pool_uid": "6b2ba3de-f2a7-4d9b-8da1-d84118ddba30", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3a70aab1-221c-46cd-89e2-417d0765fba2')
            column(name: 'df_usage_batch_uid', value: '4d6adcc5-9852-4322-b946-88e0ba977620')
            column(name: 'df_scenario_uid', value: '43242b46-1a85-4938-9865-b9b354b6ae44')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '3a70aab1-221c-46cd-89e2-417d0765fba2')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 171)
            column(name: 'value_weight', value: 5.0000000)
            column(name: 'volume_weight', value: 54.0000000)
            column(name: 'volume_share', value: 1.0000000)
            column(name: 'value_share', value: 1.0000000)
            column(name: 'total_share', value: 1.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        //Should be included into report as it is associated with APPROVED scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '404ba914-3c57-4551-867b-8bd4a1fdd8f2')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 3 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '24ddaead-40b3-47d9-af51-826878a6443e')
            column(name: 'df_fund_pool_uid', value: '404ba914-3c57-4551-867b-8bd4a1fdd8f2')
            column(name: 'df_aggregate_licensee_class_id', value: 141)
            column(name: 'gross_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '1225cfc8-6b5e-48f2-b1a3-e4a887446532')
            column(name: 'name', value: 'AACL Usage Batch 3 for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '50fcaabc-b12f-421d-aaf1-0b4e147d7540')
            column(name: 'name', value: 'AACL Scenario 3 for Undistributed Liabilities Report test')
            column(name: 'status_ind', value: 'APPROVED')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}], "fund_pool_uid": "404ba914-3c57-4551-867b-8bd4a1fdd8f2", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description 3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f5bc77ec-1be3-4867-a5ef-98b0a49b0d4e')
            column(name: 'df_usage_batch_uid', value: '1225cfc8-6b5e-48f2-b1a3-e4a887446532')
            column(name: 'df_scenario_uid', value: '50fcaabc-b12f-421d-aaf1-0b4e147d7540')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 155)
            column(name: 'comment', value: 'AACL Scenario Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'f5bc77ec-1be3-4867-a5ef-98b0a49b0d4e')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 171)
            column(name: 'value_weight', value: 5.0000000)
            column(name: 'volume_weight', value: 54.0000000)
            column(name: 'volume_share', value: 1.0000000)
            column(name: 'value_share', value: 1.0000000)
            column(name: 'total_share', value: 1.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        //Shouldn't be included into report as it is associated with SENT_TO_LM scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '66bbea66-84e7-41cd-a5aa-9fd43f03dd5a')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 4 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '9124723e-bbd0-4750-bfad-764e7b5601e5')
            column(name: 'df_fund_pool_uid', value: '66bbea66-84e7-41cd-a5aa-9fd43f03dd5a')
            column(name: 'df_aggregate_licensee_class_id', value: 141)
            column(name: 'gross_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '6fcdd8f0-bb76-4dfa-9afe-00352cdef0d3')
            column(name: 'name', value: 'AACL Usage Batch 4 for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'c5524e13-49a7-4057-8842-e8c3a8ad78cf')
            column(name: 'name', value: 'AACL Scenario 4 for Undistributed Liabilities Report test')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}], "fund_pool_uid": "66bbea66-84e7-41cd-a5aa-9fd43f03dd5a", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description 4')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '2c540f3a-0d43-4ca9-b1d2-73bb85774e43')
            column(name: 'df_usage_batch_uid', value: '6fcdd8f0-bb76-4dfa-9afe-00352cdef0d3')
            column(name: 'df_scenario_uid', value: 'c5524e13-49a7-4057-8842-e8c3a8ad78cf')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'number_of_copies', value: 155)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '2c540f3a-0d43-4ca9-b1d2-73bb85774e43')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 171)
            column(name: 'value_weight', value: 5.0000000)
            column(name: 'volume_weight', value: 54.0000000)
            column(name: 'volume_share', value: 1.0000000)
            column(name: 'value_share', value: 1.0000000)
            column(name: 'total_share', value: 1.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        //Shouldn't be included into report as it is associated with ARCHIVED scenario
        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '3c79d8ee-42ef-4973-bdad-0a27d75504c9')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 5 for Undistributed Liabilities Report test')
            column(name: 'total_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'ead693bc-e108-4d95-b660-17492b178823')
            column(name: 'df_fund_pool_uid', value: '3c79d8ee-42ef-4973-bdad-0a27d75504c9')
            column(name: 'df_aggregate_licensee_class_id', value: 141)
            column(name: 'gross_amount', value: 200.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3bf8b930-a2cb-4af0-99e2-e69edd176450')
            column(name: 'name', value: 'AACL Usage Batch 5 for Undistributed Liabilities Report test')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '75fe53a0-ccf4-404c-a4b5-143c88599fa0')
            column(name: 'name', value: 'AACL Scenario 5 for Undistributed Liabilities Report test')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'aacl_fields', value: '{"usageAges": [{"period": 2020, "weight": 1.00}], "fund_pool_uid": "3c79d8ee-42ef-4973-bdad-0a27d75504c9", "publicationTypes": [{"name": "Book", "weight": 1.00},{"name": "Business or Trade Journal", "weight": 1.50},{"name": "Consumer Magazine", "weight": 1.00},{"name": "News Source", "weight": 4.00},{"name": "STMA Journal", "weight": 1.10}],"detailLicenseeClasses": [{"detailLicenseeClassId": 171, "aggregateLicenseeClassId": 141}]}')
            column(name: 'description', value: 'AACL Scenario Description 5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '381fcb77-8e28-4ab5-8f2d-ee295c2cf9e7')
            column(name: 'df_usage_batch_uid', value: '3bf8b930-a2cb-4af0-99e2-e69edd176450')
            column(name: 'df_scenario_uid', value: '75fe53a0-ccf4-404c-a4b5-143c88599fa0')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: 1000002797)
            column(name: 'payee_account_number', value: 1000002797)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
            column(name: 'number_of_copies', value: 155)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '381fcb77-8e28-4ab5-8f2d-ee295c2cf9e7')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2020)
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 171)
            column(name: 'value_weight', value: 5.0000000)
            column(name: 'volume_weight', value: 54.0000000)
            column(name: 'volume_share', value: 1.0000000)
            column(name: 'value_share', value: 1.0000000)
            column(name: 'total_share', value: 1.0000000)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        rollback {
            dbRollback
        }
    }
}
