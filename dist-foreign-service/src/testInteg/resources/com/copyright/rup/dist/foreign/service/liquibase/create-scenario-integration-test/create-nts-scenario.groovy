databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2019-04-19-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testCreateNtsScenario, testCreateNtsScenarioWithPostServiceFeeAmount')

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: 'fc9ee4ed-519e-41c8-927b-92206b34c8cc')
            column(name: 'wr_wrk_inst', value: 135632563)
            column(name: 'classification', value: 'NON-STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_work_classification') {
            column(name: 'df_work_classification_uid', value: '7c04aac5-ccc5-4abc-b84a-4077dd6ca9a8')
            column(name: 'wr_wrk_inst', value: 145632563)
            column(name: 'classification', value: 'STM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: 'c7ca1ca1-7cd8-49cc-aaeb-ac53fe62d903')
            column(name: 'product_family', value: 'NTS')
            column(name: 'name', value: 'NTS fund pool')
            column(name: 'total_amount', value: 190.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c3b10f93-44f6-4569-9e3e-3423852ef47a')
            column(name: 'name', value: 'FAS_DISTRIBUTION_2019')
            column(name: 'rro_account_number', value: 2000017004)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'gross_amount', value: 190.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd6fc0577-a6de-4425-a377-1e4f3315d49d')
            column(name: 'df_usage_batch_uid', value: 'c3b10f93-44f6-4569-9e3e-3423852ef47a')
            column(name: 'work_title', value: '"CHICKEN BREAST ON GRILL WITH FLAMES"')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 99.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd6fc0577-a6de-4425-a377-1e4f3315d49d')
            column(name: 'df_fund_pool_uid', value: 'c7ca1ca1-7cd8-49cc-aaeb-ac53fe62d903')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 99.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3d60f842-b0a0-4aee-8de5-da433d0da477')
            column(name: 'df_usage_batch_uid', value: 'c3b10f93-44f6-4569-9e3e-3423852ef47a')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'status_ind', value: 'NTS_WITHDRAWN')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1003324112314587XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 91.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3d60f842-b0a0-4aee-8de5-da433d0da477')
            column(name: 'df_fund_pool_uid', value: 'c7ca1ca1-7cd8-49cc-aaeb-ac53fe62d903')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 91.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '26282dbd-3463-58d7-c927-03d3458a656a')
            column(name: 'name', value: 'NTS usage batch')
            column(name: 'rro_account_number', value: 2000017004)
            column(name: 'product_family', value: 'NTS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: 2017)
            column(name: 'initial_usages_count', value: 5)
            column(name: 'nts_fields', value: '{"markets": ["Bus,Univ,Doc Del"], "stm_amount": 10, "non_stm_amount": 20, "stm_minimum_amount": 30, "non_stm_minimum_amount": 40, "fund_pool_period_to": 2017, "fund_pool_period_from": 2017}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3d921c9c-8036-421a-ab05-39cc4d3c3b68')
            column(name: 'df_usage_batch_uid', value: '26282dbd-3463-58d7-c927-03d3458a656a')
            column(name: 'wr_wrk_inst', value: 135632563)
            column(name: 'work_title', value: 'Jazz chants')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10859241')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3d921c9c-8036-421a-ab05-39cc4d3c3b68')
            column(name: 'market', value: 'Bus,Univ,Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 59.30)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '669cf304-0921-41a2-85d5-c3905e77c696')
            column(name: 'df_usage_batch_uid', value: '26282dbd-3463-58d7-c927-03d3458a656a')
            column(name: 'wr_wrk_inst', value: 135632563)
            column(name: 'work_title', value: 'Jazz chants')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10859241')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '669cf304-0921-41a2-85d5-c3905e77c696')
            column(name: 'market', value: 'Bus,Univ,Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 19.20)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6402d5c8-ba80-4966-a7cc-34ba1fdc1d9c')
            column(name: 'df_usage_batch_uid', value: '26282dbd-3463-58d7-c927-03d3458a656a')
            column(name: 'wr_wrk_inst', value: 135632563)
            column(name: 'work_title', value: 'Jazz chants')
            column(name: 'rh_account_number', value: 1000001820)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10859241')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '6402d5c8-ba80-4966-a7cc-34ba1fdc1d9c')
            column(name: 'market', value: 'Bus,Univ,Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 25.96)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '91813777-3dd4-4f5f-bb83-ca145866317d')
            column(name: 'df_usage_batch_uid', value: '26282dbd-3463-58d7-c927-03d3458a656a')
            column(name: 'wr_wrk_inst', value: 145632563)
            column(name: 'work_title', value: 'Wired')
            column(name: 'rh_account_number', value: 1000024497)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10859241')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '91813777-3dd4-4f5f-bb83-ca145866317d')
            column(name: 'market', value: 'Bus,Univ,Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 896.72)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e001c596-a66f-4fd3-b34c-5ef65a215d68')
            column(name: 'df_usage_batch_uid', value: '26282dbd-3463-58d7-c927-03d3458a656a')
            column(name: 'wr_wrk_inst', value: 145632563)
            column(name: 'work_title', value: 'Wired')
            column(name: 'rh_account_number', value: 1000002562)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '10859241')
            column(name: 'gross_amount', value: 0.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'e001c596-a66f-4fd3-b34c-5ef65a215d68')
            column(name: 'market', value: 'Bus,Univ,Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'reported_value', value: 62.41)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '756299b5-02ce-4f76-b0bc-ee2571cf906e')
            column(name: 'rh_account_number', value: 7000429266)
            column(name: 'name', value: 'INSTITUTE OF FILM & TELEVISION STUDIES')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '019acfde-91be-43aa-8871-6305642bcb2c')
            column(name: 'rh_account_number', value: 1000024497)
            column(name: 'name', value: 'White Horse Press')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d15')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        rollback {
            dbRollback
        }
    }
}
