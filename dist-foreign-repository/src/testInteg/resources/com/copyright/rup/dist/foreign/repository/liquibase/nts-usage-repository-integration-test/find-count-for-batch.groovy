databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-03-07-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for findCountForBatch')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1add4836-332c-4dfb-8eb5-d71db01fd275')
            column(name: 'name', value: 'Archived scenario')
            column(name: 'status_ind', value: 'ARCHIVED')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd685ddcb-c323-41ce-9c44-1dfdeaedcab7')
            column(name: 'name', value: 'Archived batch 2')
            column(name: 'rro_account_number', value: 7000813806)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 15000.00)
            column(name: 'initial_usages_count', value: 5)
        }

        // included
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '5e9df606-fbd4-4619-94b0-9e1770203101')
            column(name: 'df_usage_batch_uid', value: 'd685ddcb-c323-41ce-9c44-1dfdeaedcab7')
            column(name: 'df_scenario_uid', value: '1add4836-332c-4dfb-8eb5-d71db01fd275')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904753)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1176.9160)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '63b3da58-ac6b-4946-bd67-37a251769467')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5e9df606-fbd4-4619-94b0-9e1770203101')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        // excluded by market_period_to
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '79d240ee-7676-414a-895c-8796cc2d497a')
            column(name: 'df_usage_batch_uid', value: 'd685ddcb-c323-41ce-9c44-1dfdeaedcab7')
            column(name: 'df_scenario_uid', value: '1add4836-332c-4dfb-8eb5-d71db01fd275')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904753)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: 'bfa206bb-83fa-4c71-9a89-99f743018237')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '79d240ee-7676-414a-895c-8796cc2d497a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2014)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        // included
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '979b1729-3933-490b-af83-6db03599fe5e')
            column(name: 'df_usage_batch_uid', value: 'd685ddcb-c323-41ce-9c44-1dfdeaedcab7')
            column(name: 'df_scenario_uid', value: '1add4836-332c-4dfb-8eb5-d71db01fd275')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 243904753)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '4c56f661-5bef-4c82-8526-fd987b5455f8')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '979b1729-3933-490b-af83-6db03599fe5e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2016)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '4f50cfb6-680f-4bcd-bc67-4c93c7989e1f')
            column(name: 'wr_wrk_inst', value: 243904753)
            column(name: 'classification', value: 'NON-STM')
        }

        // excluded by market
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '29409f02-e84a-48e5-9b31-d05668c40050')
            column(name: 'df_usage_batch_uid', value: 'd685ddcb-c323-41ce-9c44-1dfdeaedcab7')
            column(name: 'df_scenario_uid', value: '1add4836-332c-4dfb-8eb5-d71db01fd275')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 632876488)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: 'b8688ed5-3986-4f41-8a16-d33cd44e2ab5')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '29409f02-e84a-48e5-9b31-d05668c40050')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: 2016)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        // excluded as belletristic
        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '9e52e1f1-85f4-4d92-a6b8-c9a22c4cdab0')
            column(name: 'df_usage_batch_uid', value: 'd685ddcb-c323-41ce-9c44-1dfdeaedcab7')
            column(name: 'df_scenario_uid', value: '1add4836-332c-4dfb-8eb5-d71db01fd275')
            column(name: 'product_family', value: 'FAS')
            column(name: 'wr_wrk_inst', value: 632876488)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.16000)
            column(name: 'check_number', value: '578945')
            column(name: 'check_date', value: '2016-11-03')
            column(name: 'ccc_event_id', value: '53256')
            column(name: 'distribution_name', value: 'FDA March 17')
            column(name: 'distribution_date', value: '2016-11-03')
            column(name: 'lm_detail_id', value: '83bb6666-7484-49d6-8ed1-c653286590f3')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '9e52e1f1-85f4-4d92-a6b8-c9a22c4cdab0')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2016-11-03')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2016)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 3000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'b1f04ae8-fe13-42c2-a43e-4de85ade10c7')
            column(name: 'wr_wrk_inst', value: 632876488)
            column(name: 'classification', value: 'BELLETRISTIC')
        }

        rollback {
            dbRollback
        }
    }
}
