databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-02-16-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testUpdateUsagesWorkFound')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'c6b95aee-9a32-4172-acfc-d1f67013df4a')
            column(name: 'name', value: 'FAS Usage Batch')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2023-02-12')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'gross_amount', value: 2000.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '5be457bd-12c3-42d2-b62e-cc2f3e056566')
            column(name: 'df_usage_batch_uid', value: 'c6b95aee-9a32-4172-acfc-d1f67013df4a')
            column(name: 'wr_wrk_inst', value: 244614835)
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'system_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '2558902245377325XX')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'number_of_copies', value: 1600)
            column(name: 'gross_amount', value: 35000.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '5be457bd-12c3-42d2-b62e-cc2f3e056566')
            column(name: 'article', value: 'DIN EN 779:2013')
            column(name: 'publisher', value: 'John Wiley & Sons')
            column(name: 'publication_date', value: '2011-05-10')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2019)
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'reported_value', value: 1560)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'da1a1603-ea75-4933-8f63-818e46f2d49a')
            column(name: 'df_usage_batch_uid', value: 'c6b95aee-9a32-4172-acfc-d1f67013df4a')
            column(name: 'wr_wrk_inst', value: 345870577)
            column(name: 'work_title', value: '10 Years Plant Molecular Biology')
            column(name: 'system_title', value: '10 Years Plant Molecular Biology')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'number_of_copies', value: 2630)
            column(name: 'gross_amount', value: 2125.24)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'da1a1603-ea75-4933-8f63-818e46f2d49a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'McGraw-Hill')
            column(name: 'publication_date', value: '2009-12-31')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2019)
            column(name: 'author', value: 'Mirjam H. Hüberli')
            column(name: 'reported_value', value: 1280.00)
        }

        rollback {
            dbRollback
        }
    }
}
