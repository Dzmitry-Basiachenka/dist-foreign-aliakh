databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-03-27-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-48760 FDA: Create NTS Fund Pool from NTS withdrawn details: " +
                "Inserting test data for testFindById, testDelete, testFindAll")

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'b5b64c3a-55d2-462e-b169-362dca6a4dd7')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Q1 2019 100%')
            column(name: 'comment', value: 'some comment')
            column(name: 'total_amount', value: '50.00')
            column(name: 'updated_datetime', value: '2019-03-27 16:35:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '49060c9b-9cc2-4b93-b701-fffc82eb28b0')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Test fund')
            column(name: 'comment', value: 'test comment')
            column(name: 'total_amount', value: '10.00')
            column(name: 'updated_datetime', value: '2019-03-26 16:35:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a163cca7-8eeb-449c-8a3c-29ff3ec82e58')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b2d4646f-9ecb-4b64-bb47-384de8d3f7f1')
            column(name: 'df_usage_batch_uid', value: 'a163cca7-8eeb-449c-8a3c-29ff3ec82e58')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '25')
            column(name: 'gross_amount', value: '10.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b2d4646f-9ecb-4b64-bb47-384de8d3f7f1')
            column(name: 'df_fund_pool_uid', value: '49060c9b-9cc2-4b93-b701-fffc82eb28b0')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '10')
        }
    }

    changeSet(id: '2019-06-17-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("Inserting test data for findNtsNotAttachedToScenario")

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '62760b59-40af-461d-b145-5536e6ef481b')
            column(name: 'name', value: 'FAS Distribution 2019')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 300.00, "pre_service_fee_fund_uid": "b5b64c3a-55d2-462e-b169-362dca6a4dd7"}')
        }
    }

    changeSet(id: '2019-06-24-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("Inserting test data for testFindNamesByUsageBatchId")

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '1a615c47-531a-4a27-a4f3-a5bd3d5a4b1c')
            column(name: 'name', value: 'CADRA_12Dec16')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '99.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'a40132c0-d724-4450-81d2-456e67ff6f64')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'Archived Pre-Service fee fund')
            column(name: 'total_amount', value: '99.00')
            column(name: 'updated_datetime', value: '2019-03-26 16:35:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '22af0aa6-0ce9-4a51-9535-78dc811044b4')
            column(name: 'df_usage_batch_uid', value: '1a615c47-531a-4a27-a4f3-a5bd3d5a4b1c')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: '25')
            column(name: 'gross_amount', value: '99.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '22af0aa6-0ce9-4a51-9535-78dc811044b4')
            column(name: 'df_fund_pool_uid', value: 'a40132c0-d724-4450-81d2-456e67ff6f64')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: '99')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'd4834496-b680-4cc8-b4bc-295440b39c59')
            column(name: 'name', value: 'FAS Distribution 2019-2')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 300.00, "pre_service_fee_fund_uid": "a40132c0-d724-4450-81d2-456e67ff6f64"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '63b45167-a6ce-4cd5-84c6-5167916aee98')
            column(name: 'name', value: 'CADRA_13Dec16')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '150.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ea4be90e-a0a8-4e72-b1e3-28545c687ae8')
            column(name: 'df_usage_batch_uid', value: '63b45167-a6ce-4cd5-84c6-5167916aee98')
            column(name: 'df_scenario_uid', value: 'd4834496-b680-4cc8-b4bc-295440b39c59')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: '1')
            column(name: 'gross_amount', value: '150.00')
            column(name: 'net_amount', value: '126.00')
            column(name: 'service_fee_amount', value: '24.00')
            column(name: 'service_fee', value: '0.16000')
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-03-11')
            column(name: 'period_end_date', value: '2016-03-11')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ea4be90e-a0a8-4e72-b1e3-28545c687ae8')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2016')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '150')
        }
    }

    changeSet(id: '2020-02-06-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for findDetailsByFundPoolId')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'ce9c1258-6d29-4224-a4e6-6f03b6aeef53')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 1')
            column(name: 'total_amount', value: '31.20')
            column(name: 'created_datetime', value: '2020-01-02 11:00:00-04')
            column(name: 'created_by_user', value: 'coordinator@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '100ce91c-49c1-4197-9f7a-23a8210d5706')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 2')
            column(name: 'total_amount', value: '71.10')
            column(name: 'created_datetime', value: '2020-01-03 11:00:00-04')
            column(name: 'created_by_user', value: 'coordinator@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '44709581-fb7e-4d72-9427-dd5681f24fc5')
            column(name: 'df_fund_pool_uid', value: 'ce9c1258-6d29-4224-a4e6-6f03b6aeef53')
            column(name: 'df_aggregate_licensee_class_id', value: '108')
            column(name: 'gross_amount', value: '10.95')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: 'a6a2c928-297d-4584-9fb6-abf41c84e5e4')
            column(name: 'df_fund_pool_uid', value: 'ce9c1258-6d29-4224-a4e6-6f03b6aeef53')
            column(name: 'df_aggregate_licensee_class_id', value: '110')
            column(name: 'gross_amount', value: '20.25')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '50216473-2713-4b52-b40f-dfc2d2aef755')
            column(name: 'df_fund_pool_uid', value: '100ce91c-49c1-4197-9f7a-23a8210d5706')
            column(name: 'df_aggregate_licensee_class_id', value: '111')
            column(name: 'gross_amount', value: '30.35')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '4ff44b56-6b2f-4b00-8989-9bbc23796254')
            column(name: 'df_fund_pool_uid', value: '100ce91c-49c1-4197-9f7a-23a8210d5706')
            column(name: 'df_aggregate_licensee_class_id', value: '113')
            column(name: 'gross_amount', value: '40.75')
        }
    }

    changeSet(id: '2020-02-10-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Inserting test data for testInsertDetail')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '6d38454b-ce71-4b0e-8ecf-436d23dc6c3e')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL fund pool to verify details inserting')
            column(name: 'created_datetime', value: '2020-01-03 11:00:00-04')
            column(name: 'created_by_user', value: 'coordinator@copyright.com')
        }
    }

    changeSet(id: '2020-04-22-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting test data for testFindAaclNotAttachedToScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'a0c64de1-5b05-4162-83c8-1800402118e6')
            column(name: 'product_family', value: 'AACL')
            column(name: 'name', value: 'AACL Fund Pool 3')
            column(name: 'total_amount', value: '10.00')
            column(name: 'created_datetime', value: '2020-01-05 11:00:00-04')
            column(name: 'created_by_user', value: 'coordinator@copyright.com')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool_detail') {
            column(name: 'df_fund_pool_detail_uid', value: '9e34cd20-7158-45c6-8250-0d4feaeaa742')
            column(name: 'df_fund_pool_uid', value: 'a0c64de1-5b05-4162-83c8-1800402118e6')
            column(name: 'df_aggregate_licensee_class_id', value: '108')
            column(name: 'gross_amount', value: '10.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a4407ece-c1b1-48ad-a961-2439cada4e13')
            column(name: 'name', value: 'AACL Usage Batch 1')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: '2020')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '450ed474-1919-460b-9485-ccea0f53ce46')
            column(name: 'name', value: 'AACL Scenario 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'aacl_fields', value: '{"fund_pool_uid": "a0c64de1-5b05-4162-83c8-1800402118e6", "title_cutoff_amount": 0, "usageAges": [{"period": 2020, "weight": 1.00}], "publicationTypes": [{"name": "Book", "weight": 1.00}],"detailLicenseeClasses": [{"detailLicenseeClassId": 108, "aggregateLicenseeClassId": 108}]}')
            column(name: 'description', value: 'AACL Scenario Description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '54e37b92-53a8-49a3-b731-340366efcc53')
            column(name: 'df_usage_batch_uid', value: 'a4407ece-c1b1-48ad-a961-2439cada4e13')
            column(name: 'df_scenario_uid', value: '450ed474-1919-460b-9485-ccea0f53ce46')
            column(name: 'wr_wrk_inst', value: '269040891')
            column(name: 'work_title', value: 'Snap to grid')
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'standard_number', value: '9780262122269')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'rh_account_number', value: '2580011451')
            column(name: 'payee_account_number', value: "2580011451")
            column(name: 'gross_amount', value: '200.00')
            column(name: 'net_amount', value: '150.00')
            column(name: 'service_fee_amount', value: '50.00')
            column(name: 'service_fee', value: '0.25000')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: '155')
            column(name: 'comment', value: 'AACL Comment')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '54e37b92-53a8-49a3-b731-340366efcc53')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: '2020')
            column(name: 'usage_source', value: 'Feb 2020 TUR')
            column(name: 'number_of_pages', value: '100')
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: '171')
            column(name: 'value_weight', value: '24.0000000')
            column(name: 'volume_weight', value: '5.0000000')
            column(name: 'volume_share', value: '50.0000000')
            column(name: 'value_share', value: '60.0000000')
            column(name: 'total_share', value: '2.0000000')
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'publication_type_weight', value: 1.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'f260168c-501b-483a-ba6d-8705dc4e4d06')
            column(name: 'df_scenario_uid', value: '450ed474-1919-460b-9485-ccea0f53ce46')
            column(name: 'product_family', value: 'AACL')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter_to_usage_batches_ids_map') {
            column(name: 'df_scenario_usage_filter_uid', value: 'f260168c-501b-483a-ba6d-8705dc4e4d06')
            column(name: 'df_usage_batch_uid', value: 'a4407ece-c1b1-48ad-a961-2439cada4e13')
        }
    }

    changeSet(id: '2020-09-28-00', author: 'Ihar Suvorau <srudak@copyright.com>') {
        comment('Inserting test data for testFindAaclNotAttachedToScenario')

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '1ea65e2a-22c1-4a96-b55b-b6b4fd7d51ed')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 1')
            column(name: 'total_amount', value: '1000.00')
            column(name: 'sal_fields', value: '{"date_received": "09/28/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 5, "grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_gross_amount": 200.01, "grade_K_5_gross_amount": 533.33, "grade_6_8_gross_amount": 266.66, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
            column(name: 'created_datetime', value: '2020-09-28 11:00:00-04')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '15c3023d-1e68-4b7d-bfe3-18e85806b167')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 2')
            column(name: 'total_amount', value: '100.00')
            column(name: 'sal_fields', value: '{"date_received": "09/29/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 2, "grade_6_8_number_of_students": 1, "grade_9_12_number_of_students": 0, "gross_amount": 100.00, "item_bank_gross_amount": 20.01, "grade_K_5_gross_amount": 53.33, "grade_6_8_gross_amount": 26.66, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
            column(name: 'created_datetime', value: '2020-09-29 11:00:00-04')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '8bc02d1f-35f8-4626-b206-0e9ffc8d9f97')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool 3')
            column(name: 'total_amount', value: '10.00')
            column(name: 'sal_fields', value: '{"date_received": "12/30/2020", "assessment_name": "FY2020 COG", "licensee_account_number": 1000008985, "licensee_name": "FarmField Inc.", "grade_K_5_number_of_students": 2, "grade_6_8_number_of_students": 1, "grade_9_12_number_of_students": 0, "gross_amount": 10.00, "item_bank_gross_amount": 2.01, "grade_K_5_gross_amount": 5.33, "grade_6_8_gross_amount": 2.66, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
            column(name: 'created_datetime', value: '2020-09-30 11:00:00-04')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '3c7b100c-0e30-4381-bd06-787e058af6f1')
            column(name: 'name', value: 'SAL Scenario 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'sal_fields', value: '{"fund_pool_uid": "8bc02d1f-35f8-4626-b206-0e9ffc8d9f97"}')
        }
    }
}
