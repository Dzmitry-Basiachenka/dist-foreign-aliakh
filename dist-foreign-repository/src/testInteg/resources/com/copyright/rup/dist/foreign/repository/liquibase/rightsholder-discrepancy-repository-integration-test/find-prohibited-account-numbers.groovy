databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2018-04-18-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testFindProhibitedAccountNumbers')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'name', value: 'JAACC_11Dec16')
            column(name: 'rro_account_number', value: 2000017010)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: 2016)
            column(name: 'gross_amount', value: 35000)
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'name', value: 'Scenario name 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario 2')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cf38d390-11bb-4af7-9685-e034c9c32fb6')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'payee_account_number', value: 1000002859)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112317622XX')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 16437.40)
            column(name: 'net_amount', value: 11177.40)
            column(name: 'service_fee_amount', value: 5260.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'cf38d390-11bb-4af7-9685-e034c9c32fb6')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: 9900)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '23876f21-8ab2-4fcb-adf3-777be88eddbb')
            column(name: 'df_usage_batch_uid', value: 'a5b64c3a-55d2-462e-b169-362dca6a4dd6')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'payee_account_number', value: 1000009997)
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 35000.00)
            column(name: 'net_amount', value: 23800.00)
            column(name: 'service_fee_amount', value: 11200.00)
            column(name: 'service_fee', value: 0.32000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '23876f21-8ab2-4fcb-adf3-777be88eddbb')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2014-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 2013)
            column(name: 'market_period_to', value: 2017)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder_discrepancy') {
            column(name: 'df_rightsholder_discrepancy_uid', value: 'f5397618-a0f6-4f5b-8895-5d0d8503db12')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'old_rh_account_number', value: 1000002859)
            column(name: 'new_rh_account_number', value: 1000005413)
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'DRAFT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder_discrepancy') {
            column(name: 'df_rightsholder_discrepancy_uid', value: 'e4877bab-090b-49c0-adaa-6720217ea37d')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'old_rh_account_number', value: 1000009997)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'DRAFT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder_discrepancy') {
            column(name: 'df_rightsholder_discrepancy_uid', value: '4c20732d-729a-4a8e-beee-3d5a282c6c54')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'wr_wrk_inst', value: 180382914)
            column(name: 'old_rh_account_number', value: 1000009997)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'DRAFT')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'ff8b9ac9-5fca-4d57-b74e-26da209c1040')
            column(name: 'rh_account_number', value: 1000009997)
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '14edbc9b-1473-4fc9-95f6-07b3ef45e851')
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'c6237aee-d926-48c3-8be6-7c145610a2db')
            column(name: 'rh_account_number', value: 2000017010)
            column(name: 'name', value: 'JAC, Japan Academic Association for Copyright Clearance, Inc.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a0663c51-87c6-4d03-8683-7640f12ae8c1')
            column(name: 'rh_account_number', value: 1000005413)
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        rollback {
            dbRollback
        }
    }
}
