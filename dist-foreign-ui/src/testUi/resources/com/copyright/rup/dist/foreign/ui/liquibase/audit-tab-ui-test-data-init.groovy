databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-05-02-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Insert predefined data for AuditTabUiTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'd2b9c16d-230a-414f-9ffb-acdb676fac0c')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '15000')
            column(name: 'updated_datetime', value: '2017-02-21 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '366f0fa6-b4c5-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_batch_uid', value: 'd2b9c16d-230a-414f-9ffb-acdb676fac0c')
            column(name: 'detail_id', value: '6997788888')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '2700')
            column(name: 'gross_amount', value: '9000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f9da71d0-9b07-4204-8355-549a4206ebc5')
            column(name: 'df_usage_batch_uid', value: 'd2b9c16d-230a-414f-9ffb-acdb676fac0c')
            column(name: 'detail_id', value: '6997788885')
            column(name: 'wr_wrk_inst', value: '244614835')
            column(name: 'work_title', value: '15th International Conference on Environmental Degradation of Materials in Nuclear Power Systems Water Reactors')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'First-Week Protein and Energy Intakes Are Associated With 18-Month Developmental Outcomes in Extremely Low Birth Weight Infants')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'publisher', value: 'John Wiley & Sons')
            column(name: 'publication_date', value: '2011-05-10')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'number_of_copies', value: '1600')
            column(name: 'reported_value', value: '1800')
            column(name: 'gross_amount', value: '6000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '76ba5480-b3f8-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: '366f0fa6-b4c5-11e7-abc4-cec278b6b50a')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'CADRA_11Dec16' Batch")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7ab3eaa4-b3fa-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: 'f9da71d0-9b07-4204-8355-549a4206ebc5')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'CADRA_11Dec16' Batch")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3cc619da-6218-47ef-959e-5a3f19e392a4')
            column(name: 'name', value: 'AccessCopyright_11Dec16')
            column(name: 'rro_account_number', value: '2000017004')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: '2016')
            column(name: 'gross_amount', value: '10000')
            column(name: 'updated_datetime', value: '2017-02-14 11:41:52.735531+03')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'description', value: 'The description of scenario')
            column(name: 'created_datetime', value: '2017-01-01')
            column(name: 'updated_datetime', value: '2017-01-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'df_usage_batch_uid', value: '3cc619da-6218-47ef-959e-5a3f19e392a4')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'detail_id', value: '6997788886')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'payee_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '9900')
            column(name: 'gross_amount', value: '7500.00')
            column(name: 'service_fee_amount', value: '2400.00')
            column(name: 'net_amount', value: '5100.00')
            column(name: 'service_fee', value: '0.32')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '49ed6163-e992-499a-8879-827928bef327')
            column(name: 'df_usage_batch_uid', value: '3cc619da-6218-47ef-959e-5a3f19e392a4')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'detail_id', value: '6997788882')
            column(name: 'wr_wrk_inst', value: '108738286')
            column(name: 'work_title', value: '2001 tax legislation: law, explanation, and analysis : Economic Growth and Tax Relief Reconciliation Act of 2001')
            column(name: 'rh_account_number', value: '1000008666')
            column(name: 'payee_account_number', value: '1000008666')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'Google Makes Super')
            column(name: 'standard_number', value: '1008902002377656XX')
            column(name: 'publisher', value: 'CCH Inc')
            column(name: 'publication_date', value: '1999-09-27')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: '愛染恭子')
            column(name: 'number_of_copies', value: '16')
            column(name: 'is_rh_participating_flag', value: 'false')
            column(name: 'reported_value', value: '850')
            column(name: 'gross_amount', value: '2500.00')
            column(name: 'service_fee_amount', value: '800.00')
            column(name: 'net_amount', value: '2700.00')
            column(name: 'service_fee', value: '0.32')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7ab4eaa4-b3fa-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: '49ed6163-e992-499a-8879-827928bef327')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'AccessCopyright_11Dec16' Batch")
            column(name: 'created_datetime', value: '2017-01-01')
            column(name: 'updated_datetime', value: '2017-01-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7ab3eaa4-b3fa-12e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'action_type_ind', value: 'LOADED')
            column(name: 'action_reason', value: "Uploaded in 'AccessCopyright_11Dec16' Batch")
            column(name: 'created_datetime', value: '2017-01-01')
            column(name: 'updated_datetime', value: '2017-01-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7ab2eaa4-b3fa-11e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: '49ed6163-e992-499a-8879-827928bef327')
            column(name: 'action_type_ind', value: 'RH_FOUND')
            column(name: 'action_reason', value: "Rightsholder account <1000002859> was found in RMS")
            column(name: 'created_datetime', value: '2017-02-01')
            column(name: 'updated_datetime', value: '2017-02-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_audit') {
            column(name: 'df_usage_audit_uid', value: '7ab3ea44-b3fa-12e7-abc4-cec278b6b50a')
            column(name: 'df_usage_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'action_type_ind', value: 'RH_FOUND')
            column(name: 'action_reason', value: "Rightsholder account <1000008666> was found in RMS")
            column(name: 'created_datetime', value: '2017-02-01')
            column(name: 'updated_datetime', value: '2017-02-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05c4714b-291d-4e38-ba4a-35307434acfb')
            column(name: 'rh_account_number', value: '7000813806')
            column(name: 'name', value: 'CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '46754660-b627-46b9-a782-3f703b6853c7')
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'name', value: 'Access Copyright, The Canadian Copyright Agency')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '9905f006-a3e1-4061-b3d4-e7ece191103f')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '5bcf2c37-2f32-48e9-90fe-c9d75298eeed')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '6eb8ddf3-f08e-4c17-a0c5-5173d43a1625')
            column(name: 'rh_account_number', value: '1000008666')
            column(name: 'name', value: 'CCH')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_rightsholder'){}
            delete(schemaName: dbAppsSchema, tableName: 'df_scenario_audit'){}
            delete(schemaName: dbAppsSchema, tableName: 'df_usage_audit'){}
            delete(schemaName: dbAppsSchema, tableName: 'df_usage'){}
            delete(schemaName: dbAppsSchema, tableName: 'df_usage_archive'){}
            delete(schemaName: dbAppsSchema, tableName: 'df_usage_batch'){}
            delete(schemaName: dbAppsSchema, tableName: 'df_scenario'){}
        }
    }
}
