databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-02-22-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data to receive paid usages from LM')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '4924da00-ee87-41b3-9aed-caa5c5ba94f1')
            column(name: 'name', value: 'Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c392b841-33a6-4268-861a-2c4334a7fabc')
            column(name: 'name', value: 'Paid batch')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'e783caf7-586d-434c-baaa-2ddf09afee77')
            column(name: 'df_usage_batch_uid', value: 'c392b841-33a6-4268-861a-2c4334a7fabc')
            column(name: 'df_scenario_uid', value: '4924da00-ee87-41b3-9aed-caa5c5ba94f1')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 7001832491)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'net_amount', value: 840.00)
            column(name: 'service_fee_amount', value: 160.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e783caf7-586d-434c-baaa-2ddf09afee77')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 6000)
        }

        rollback ""
    }

    changeSet(id: '2018-09-13-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data to receive post-distribution paid usages from LM')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '19de64a0-f4d6-4ddf-9d00-882325e39c58')
            column(name: 'name', value: 'Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '244ecf91-bc53-4cf3-a3c4-170019125b46')
            column(name: 'name', value: 'Post Distribution Paid batch')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'ef058a3f-b60e-429b-b6e3-14d386eb86ba')
            column(name: 'df_usage_batch_uid', value: '244ecf91-bc53-4cf3-a3c4-170019125b46')
            column(name: 'df_scenario_uid', value: '19de64a0-f4d6-4ddf-9d00-882325e39c58')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'payee_account_number', value: 1000000001)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'net_amount', value: 1680.00)
            column(name: 'service_fee_amount', value: 320.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'comment', value: 'usage from usages.csv')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'ef058a3f-b60e-429b-b6e3-14d386eb86ba')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 6000)
        }

        rollback ""
    }

    changeSet(id: '2019-07-10-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data testReceivePaidNtsUsagesFromLm')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05dc9217-26d4-46ca-aa6e-18572591f3c8')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'name', value: 'Abbey Publications, Inc. [L]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1f8074bd-9af7-42ec-95b1-853cddc32f40')
            column(name: 'name', value: 'NTS ReceivePaidUsageFromLM')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'nts_fields', value: '{"rh_minimum_amount": 30.00}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'cc391849-8da7-467f-bf43-90e836578283')
            column(name: 'df_scenario_uid', value: '1f8074bd-9af7-42ec-95b1-853cddc32f40')
            column(name: 'product_family', value: 'NTS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '84cd1b64-d1eb-455f-a52c-f9dd0da2e356')
            column(name: 'df_scenario_uid', value: '1f8074bd-9af7-42ec-95b1-853cddc32f40')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'payee_account_number', value: 1000003821)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 90.63)
            column(name: 'service_fee_amount', value: 29.00)
            column(name: 'net_amount', value: 61.63)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'created_datetime', value: '2016-03-12')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '84cd1b64-d1eb-455f-a52c-f9dd0da2e356')
            column(name: 'reported_value', value: 0.00)
        }

        // will not be updated
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'c70b06a4-dfdf-4fe3-8ad9-f90d973f566f')
            column(name: 'df_scenario_uid', value: '1f8074bd-9af7-42ec-95b1-853cddc32f40')
            column(name: 'wr_wrk_inst', value: 151811999)
            column(name: 'work_title', value: 'NON-TITLE NTS')
            column(name: 'system_title', value: 'NON-TITLE NTS')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'payee_account_number', value: 7000429266)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'NTS')
            column(name: 'gross_amount', value: 13503.37)
            column(name: 'net_amount', value: 9182.28)
            column(name: 'service_fee_amount', value: 4321.07)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'created_datetime', value: '2016-03-12')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'c70b06a4-dfdf-4fe3-8ad9-f90d973f566f')
            column(name: 'reported_value', value: 0.00)
        }
    }

    changeSet(id: '2020-05-19-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data testReceivePaidAaclUsagesFromLm')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'de1d65f6-10c6-462c-bd97-44fcfc976934')
            column(name: 'name', value: 'Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '2e3d35ce-28a2-4368-a4f3-1d6a87f3b7c2')
            column(name: 'name', value: 'AACL batch')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2000)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '6e8172d6-c16f-4522-8606-e55db1b8e5a4')
            column(name: 'df_scenario_uid', value: 'de1d65f6-10c6-462c-bd97-44fcfc976934')
            column(name: 'df_usage_batch_uid', value: '2e3d35ce-28a2-4368-a4f3-1d6a87f3b7c2')
            column(name: 'product_family', value: 'AACL')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'number_of_copies', value: 300)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.25)
            column(name: 'comment', value: 'AACL archived usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '6e8172d6-c16f-4522-8606-e55db1b8e5a4')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'usage_period', value: '2017')
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'detail_licensee_class_id', value: '143')
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'number_of_pages', value: '341')
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '30771d65-8b91-42e5-865c-0c73fddbee0c')
            column(name: 'df_scenario_uid', value: 'de1d65f6-10c6-462c-bd97-44fcfc976934')
            column(name: 'df_usage_batch_uid', value: '2e3d35ce-28a2-4368-a4f3-1d6a87f3b7c2')
            column(name: 'product_family', value: 'AACL')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'number_of_copies', value: 20)
            column(name: 'gross_amount', value: 1500.00)
            column(name: 'net_amount', value: 1125.00)
            column(name: 'service_fee_amount', value: 375.00)
            column(name: 'service_fee', value: 0.25)
            column(name: 'comment', value: 'AACL archived usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '30771d65-8b91-42e5-865c-0c73fddbee0c')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'usage_period', value: '2017')
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'detail_licensee_class_id', value: '143')
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'number_of_pages', value: '30')
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '7d8534bc-118c-4144-9bc4-9f376f99c854')
            column(name: 'df_scenario_uid', value: 'de1d65f6-10c6-462c-bd97-44fcfc976934')
            column(name: 'df_usage_batch_uid', value: '2e3d35ce-28a2-4368-a4f3-1d6a87f3b7c2')
            column(name: 'product_family', value: 'AACL')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'number_of_copies', value: 300)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.25)
            column(name: 'comment', value: 'AACL archived usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '7d8534bc-118c-4144-9bc4-9f376f99c854')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'usage_period', value: '2017')
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'detail_licensee_class_id', value: '143')
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'number_of_pages', value: '341')
            column(name: 'right_limitation', value: 'ALL')
        }
    }
}
