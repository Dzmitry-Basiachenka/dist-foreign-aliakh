databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2022-02-11-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment('Inserting test data for testWriteArchivedScenarioRightsholderTotalsCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '60241909-744c-4766-ad67-fdc9e2c043eb')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '30e4ea0e-c3e8-4713-98e2-e9a7d8ff8c0a')
            column(name: 'name', value: 'JAACC_11Dec16')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'gross_amount', value: 70000.00)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '9adcbc00-6fcc-4f6d-842a-134581484ddf')
            column(name: 'name', value: 'FAS scenario with archived usages')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario description')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '40ffb5fa-b270-45bf-9bb8-fb7e0d3a2052')
            column(name: 'df_scenario_uid', value: '9adcbc00-6fcc-4f6d-842a-134581484ddf')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'ELIGIBLE')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: 'f108a821-beda-4789-b7bc-3d456895d49b')
            column(name: 'df_usage_batch_uid', value: '30e4ea0e-c3e8-4713-98e2-e9a7d8ff8c0a')
            column(name: 'df_scenario_uid', value: '9adcbc00-6fcc-4f6d-842a-134581484ddf')
            column(name: 'wr_wrk_inst', value: 345870577)
            column(name: 'work_title', value: '10 Years Plant Molecular Biology')
            column(name: 'system_title', value: '10 Years Plant Molecular Biology')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'payee_account_number', value: 1000005413)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'number_of_copies', value: 2630)
            column(name: 'gross_amount', value: 2125.24)
            column(name: 'net_amount', value: 1445.1632)
            column(name: 'service_fee_amount', value: 680.0768)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'standard_number_type', value: 'VALISBN10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f108a821-beda-4789-b7bc-3d456895d49b')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'McGraw-Hill')
            column(name: 'publication_date', value: '2009-12-31')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: 2015)
            column(name: 'market_period_to', value: 2019)
            column(name: 'author', value: 'Mirjam H. Hüberli')
            column(name: 'reported_value', value: 1280.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '27a769ad-fc6b-4648-b1e9-b11985d9fe0a')
            column(name: 'df_usage_batch_uid', value: '30e4ea0e-c3e8-4713-98e2-e9a7d8ff8c0a')
            column(name: 'df_scenario_uid', value: '9adcbc00-6fcc-4f6d-842a-134581484ddf')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'system_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 250232)
            column(name: 'gross_amount', value: 67874.80)
            column(name: 'net_amount', value: 46154.80)
            column(name: 'service_fee_amount', value: 21720.00)
            column(name: 'service_fee', value: 0.32000)
            column(name: 'standard_number_type', value: 'VALISBN10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '27a769ad-fc6b-4648-b1e9-b11985d9fe0a')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 9900)
        }

        rollback {
            dbRollback
        }
    }
}
