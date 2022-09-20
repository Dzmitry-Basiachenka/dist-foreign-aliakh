databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2017-11-30-05', author: 'Aliaksandra_Bayanouskaya <abayanouskaya@copyright.com>') {
        comment('Inserting test data for testFindUsageStatistic')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f687b952-2b4c-4316-bbc7-2c07eb2dcd1c')
            column(name: 'name', value: 'Test batch for usage timings')
            column(name: 'rro_account_number', value: 7001832491)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-12-04T15:00:00Z')
            column(name: 'fiscal_year', value: 2018)
            column(name: 'gross_amount', value: 200.00)
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3fb43e60-3352-4db4-9080-c30b8a6f6600')
            column(name: 'df_usage_batch_uid', value: 'f687b952-2b4c-4316-bbc7-2c07eb2dcd1c')
            column(name: 'wr_wrk_inst', value: 123336161)
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'system_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 2502232)
            column(name: 'gross_amount', value: 80.00)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3fb43e60-3352-4db4-9080-c30b8a6f6600')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10T15:00:00Z')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: 1960)
            column(name: 'market_period_to', value: 1960)
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'reported_value', value: 2500)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '85ee285e-1ea8-408e-9a0a-e65bc4cb5bbf')
            column(name: 'df_usage_uid', value: '3fb43e60-3352-4db4-9080-c30b8a6f6600')
            column(name: 'action_type_ind', value: 'ELIGIBLE')
            column(name: 'action_reason', value: 'Usage has become eligible')
            column(name: 'created_datetime', value: '2019-02-14 11:45:03.721492+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: 'f33b5590-4431-437c-bf5b-076bb60d321f')
            column(name: 'df_usage_uid', value: '3fb43e60-3352-4db4-9080-c30b8a6f6600')
            column(name: 'action_type_ind', value: 'RH_FOUND')
            column(name: 'action_reason', value: 'Rightsholder account 1000004090 was found in RMS')
            column(name: 'created_datetime', value: '2019-02-14 11:45:02.645621+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '4210ebb1-7f96-4347-bf84-b1cae37e70e8')
            column(name: 'df_usage_uid', value: '3fb43e60-3352-4db4-9080-c30b8a6f6600')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: 'Uploaded in \'Test batch for usage timings\' Batch')
            column(name: 'created_datetime', value: '2019-02-14 11:45:01.52369+03')
        }

        rollback {
            dbRollback
        }
    }
}
