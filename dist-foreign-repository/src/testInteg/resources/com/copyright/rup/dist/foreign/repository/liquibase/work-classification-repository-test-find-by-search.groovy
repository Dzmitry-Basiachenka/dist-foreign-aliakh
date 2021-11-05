databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-11-30-03', author: 'Uladzislau Shalamitski <ushalamitsski@copyright.com>') {
        comment('Inserting test data for testFindBySearch and testFindCountBySearch')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '14edbc9b-1473-4fc9-95f6-07b3ef45e851')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '074c0f1f-2457-49a9-ad72-2a85d07ce90e')
            column(name: 'rh_account_number', value: 1000000002)
            column(name: 'name', value: 'Royal Society of Victoria')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '8d3b077a-44be-48b4-ad0e-bf5de1cf97b0')
            column(name: 'wr_wrk_inst', value: 111111111)
            column(name: 'classification', value: 'NON-STM')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2019-02-01 00:00:00.000-0000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '0b3f6bb1-40b7-4d11-ba53-d54e9d67e61f')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'classification', value: 'NON-STM')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2019-02-01 00:00:00.000-0000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '84ba864e-716a-4103-bcd7-180563695f50')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'classification', value: 'STM')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2019-02-01 00:00:00.000-0000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '06b21198-daaf-40e1-a501-5b97deeecc0d')
            column(name: 'wr_wrk_inst', value: 123345258)
            column(name: 'classification', value: 'NON-STM')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2019-02-01 00:00:00.000-0000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'b7737221-72c3-47b1-b1a2-d25b8f50500b')
            column(name: 'wr_wrk_inst', value: 122410079)
            column(name: 'classification', value: 'STM')
            column(name: 'updated_by_user', value: 'user@copyright.com')
            column(name: 'updated_datetime', value: '2019-02-01 00:00:00.000-0000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'b7737221-72c3-47b1-b1a2-d25b8f50500b')
            column(name: 'name', value: 'AccessCopyright_11Dec16')
            column(name: 'rro_account_number', value: 2000017004)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-08-16')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '06b21198-daaf-40e1-a501-5b97deeecc0d')
            column(name: 'name', value: 'Scenario name 2')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'The description of scenario 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '8a06905f-37ae-4e1f-8550-245277f8165c')
            column(name: 'df_usage_batch_uid', value: 'b7737221-72c3-47b1-b1a2-d25b8f50500b')
            column(name: 'df_scenario_uid', value: '06b21198-daaf-40e1-a501-5b97deeecc0d')
            column(name: 'wr_wrk_inst', value: 123345258)
            column(name: 'work_title', value: '15th International Conference on Environmental Degradation')
            column(name: 'system_title', value: '15th International Conference on Environmental Degradation')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: 1600)
            column(name: 'gross_amount', value: 35000.00)
            column(name: 'net_amount', value: 23800.00)
            column(name: 'service_fee_amount', value: 11200.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '8a06905f-37ae-4e1f-8550-245277f8165c')
            column(name: 'article', value: 'First-Week Protein and Energy Intakes Are Associated With 18-Month Developmental Outcomes in Extremely Low Birth Weight Infants')
            column(name: 'publisher', value: 'John Wiley & Sons')
            column(name: 'publication_date', value: '2011-05-10')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2019)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 1560)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '41e08f32-9d23-44fc-92cd-51ff34d5b5fe')
            column(name: 'name', value: 'NTS batch')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'product_family', value: 'NTS')
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'ac5f668f-bc75-4a89-a8d4-39a89e6a973a')
            column(name: 'name', value: 'Scenario name 2')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'The description of scenario 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ca775af7-7b6a-4f21-88a1-9dc7fd456ec1')
            column(name: 'df_usage_batch_uid', value: '41e08f32-9d23-44fc-92cd-51ff34d5b5fe')
            column(name: 'df_scenario_uid', value: 'ac5f668f-bc75-4a89-a8d4-39a89e6a973a')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: 1600)
            column(name: 'gross_amount', value: 35000.00)
            column(name: 'net_amount', value: 23800.00)
            column(name: 'service_fee_amount', value: 11200.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ca775af7-7b6a-4f21-88a1-9dc7fd456ec1')
            column(name: 'article', value: 'First-Week Protein and Energy Intakes Are Associated With 18-Month Developmental Outcomes in Extremely Low Birth Weight Infants')
            column(name: 'publisher', value: 'John Wiley & Sons')
            column(name: 'publication_date', value: '2011-05-10')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2019)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 1560)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'e0a039e9-bf98-4f26-a216-a4c9660b09b5')
            column(name: 'df_usage_batch_uid', value: '41e08f32-9d23-44fc-92cd-51ff34d5b5fe')
            column(name: 'df_scenario_uid', value: 'ac5f668f-bc75-4a89-a8d4-39a89e6a973a')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: 'Corporate identity manuals')
            column(name: 'system_title', value: 'Corporate identity manuals')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'number_of_copies', value: 1600)
            column(name: 'gross_amount', value: 35000.00)
            column(name: 'net_amount', value: 23800.00)
            column(name: 'service_fee_amount', value: 11200.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'updated_datetime', value: '2019-06-30 10:00:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e0a039e9-bf98-4f26-a216-a4c9660b09b5')
            column(name: 'article', value: 'First-Week Protein and Energy Intakes Are Associated With 18-Month Developmental Outcomes in Extremely Low Birth Weight Infants')
            column(name: 'publisher', value: 'John Wiley & Sons')
            column(name: 'publication_date', value: '2011-05-10')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2019)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 1560)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '403b378d-7678-4f1d-95cd-bff21b3a9998')
            column(name: 'df_usage_batch_uid', value: '41e08f32-9d23-44fc-92cd-51ff34d5b5fe')
            column(name: 'df_scenario_uid', value: 'ac5f668f-bc75-4a89-a8d4-39a89e6a973a')
            column(name: 'wr_wrk_inst', value: 244614835)
            column(name: 'work_title', value: 'future of children')
            column(name: 'system_title', value: 'future of children')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'number_of_copies', value: 1600)
            column(name: 'gross_amount', value: 35000.00)
            column(name: 'net_amount', value: 23800.00)
            column(name: 'service_fee_amount', value: 11200.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '403b378d-7678-4f1d-95cd-bff21b3a9998')
            column(name: 'article', value: 'First-Week Protein and Energy Intakes Are Associated With 18-Month Developmental Outcomes in Extremely Low Birth Weight Infants')
            column(name: 'publisher', value: 'John Wiley & Sons')
            column(name: 'publication_date', value: '2011-05-10')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2019)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 1560)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c2065264-b8b2-44d0-ad69-3536794ff857')
            column(name: 'name', value: 'FAS2 batch')
            column(name: 'rro_account_number', value: 2000017000)
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'product_family', value: 'FAS2')
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ae01ac7d-12f9-458b-b675-a3ed04aa4198')
            column(name: 'df_usage_batch_uid', value: 'c2065264-b8b2-44d0-ad69-3536794ff857')
            column(name: 'df_scenario_uid', value: 'ac5f668f-bc75-4a89-a8d4-39a89e6a973a')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: 'Corporate identity manuals')
            column(name: 'system_title', value: 'Corporate identity manuals')
            column(name: 'rh_account_number', value: 1000000002)
            column(name: 'payee_account_number', value: 1000000002)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'number_of_copies', value: 1600)
            column(name: 'gross_amount', value: 35000.00)
            column(name: 'net_amount', value: 23800.00)
            column(name: 'service_fee_amount', value: 11200.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'updated_datetime', value: '2019-07-30 10:00:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ae01ac7d-12f9-458b-b675-a3ed04aa4198')
            column(name: 'article', value: 'First-Week Protein and Energy Intakes Are Associated With 18-Month Developmental Outcomes in Extremely Low Birth Weight Infants')
            column(name: 'publisher', value: 'John Wiley & Sons')
            column(name: 'publication_date', value: '2011-05-10')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2019)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 1560)
        }

        // NTS product should not be used in classification
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'f29b0a2a-3e6e-4244-9637-64f716964498')
            column(name: 'df_scenario_uid', value: 'ac5f668f-bc75-4a89-a8d4-39a89e6a973a')
            column(name: 'wr_wrk_inst', value: 122410079)
            column(name: 'work_title', value: 'Corporate identity manuals')
            column(name: 'system_title', value: 'Corporate identity manuals')
            column(name: 'rh_account_number', value: 1000000002)
            column(name: 'payee_account_number', value: 1000000002)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'number_of_copies', value: 1600)
            column(name: 'gross_amount', value: 35000.00)
            column(name: 'net_amount', value: 23800.00)
            column(name: 'service_fee_amount', value: 11200.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'updated_datetime', value: '2019-07-30 10:00:00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f29b0a2a-3e6e-4244-9637-64f716964498')
            column(name: 'article', value: 'First-Week Protein and Energy Intakes Are Associated With 18-Month Developmental Outcomes in Extremely Low Birth Weight Infants')
            column(name: 'publisher', value: 'John Wiley & Sons')
            column(name: 'publication_date', value: '2011-05-10')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2019)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 1560)
        }

        rollback {
            dbRollback
        }
    }
}
