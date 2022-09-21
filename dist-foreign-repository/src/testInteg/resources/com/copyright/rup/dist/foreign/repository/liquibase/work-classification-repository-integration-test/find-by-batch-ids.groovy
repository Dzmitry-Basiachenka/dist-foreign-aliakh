databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-11-30-02', author: 'Uladzislau Shalamitski <ushalamitsski@copyright.com>') {
        comment('Inserting test data for testFindByBatches and testFindCountByBatches')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '074c0f1f-2457-49a9-ad72-2a85d07ce90e')
            column(name: 'rh_account_number', value: 1000000002)
            column(name: 'name', value: 'Royal Society of Victoria')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '14edbc9b-1473-4fc9-95f6-07b3ef45e851')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '8d3b077a-44be-26cc-ad0e-bf5de1cf97b0')
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

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'name', value: 'NTS fund pool')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'product_family', value: 'NTS')
            column(name: 'initial_usages_count', value: 3)
            column(name: 'nts_fields', value: '{"non_stm_minimum_amount":7,"stm_amount":700,"stm_minimum_amount":50,"non_stm_amount":5000,"fund_pool_period_from":2010,"markets":["Bus","Doc Del"],"fund_pool_period_to":2012}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '85093193-00d9-436b-8fbc-078511b1d335')
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'standard_number_type', value: 'VALISBN13')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 500.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '85093193-00d9-436b-8fbc-078511b1d335')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2012)
            column(name: 'market_period_to', value: 2014)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b51340ad-cf32-4c38-8445-4455e4ae81eb')
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: 'Corporate identity manuals')
            column(name: 'system_title', value: 'Corporate identity manuals')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 500.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'b51340ad-cf32-4c38-8445-4455e4ae81eb')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2010)
            column(name: 'market_period_to', value: 2011)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5f956b4b-3b09-457f-a306-f36fc55710af')
            column(name: 'df_usage_batch_uid', value: 'e17ebc80-e74e-436d-ba6e-acf3d355b7ff')
            column(name: 'wr_wrk_inst', value: 244614835)
            column(name: 'work_title', value: 'future of children')
            column(name: 'system_title', value: 'future of children')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 500.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5f956b4b-3b09-457f-a306-f36fc55710af')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Bus')
            column(name: 'market_period_from', value: 2010)
            column(name: 'market_period_to', value: 2011)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 500)
        }

        rollback {
            dbRollback
        }
    }
}
