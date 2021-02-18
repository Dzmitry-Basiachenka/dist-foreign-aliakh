databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-02-01-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for ReconcileRightsholdersTest no discrepancies')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'be66bae9-fa89-499a-8485-fb9e445bedd9')
            column(name: 'name', value: 'Test Reconcile rightsholders 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'name', value: 'Test Batch 1')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '1000.00')
            column(name: 'initial_usages_count', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'fcdaea01-2439-4c51-b3e2-23649cf710c7')
            column(name: 'df_usage_batch_uid', value: '31ddaa1a-e60b-44ce-a968-0ca262870358')
            column(name: 'df_scenario_uid', value: 'be66bae9-fa89-499a-8485-fb9e445bedd9')
            column(name: 'wr_wrk_inst', value: '471137470')
            column(name: 'work_title', value: 'Sunbeams')
            column(name: 'rh_account_number', value: '1000003821')
            column(name: 'payee_account_number', value: '1000003821')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '1000.00')
            column(name: 'service_fee_amount', value: '320.00')
            column(name: 'net_amount', value: '680.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'fcdaea01-2439-4c51-b3e2-23649cf710c7')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "d50d8097-b2dd-441b-bf3e-84eac1b3902a")
            column(name: "df_usage_uid", value: "fcdaea01-2439-4c51-b3e2-23649cf710c7")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'Test Batch 1'")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '05dc9217-26d4-46ca-aa6e-18572591f3c8')
            column(name: 'rh_account_number', value: '1000003821')
            column(name: 'name', value: 'Abbey Publications, Inc. [L]')
        }
    }

    changeSet(id: '2018-02-01-01', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for ReconcileRightsholdersTest with discrepancies')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '04263c90-cb54-44f0-b354-a901586e5801')
            column(name: 'name', value: 'Test Reconcile rightsholders 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '10150cf3-9820-41e0-8ea6-97105b6eaf45')
            column(name: 'name', value: 'Test Batch 2')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '15000.00')
            column(name: 'initial_usages_count', value: 6)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4713282c-c698-4ffb-8de1-44863d48954f')
            column(name: 'df_usage_batch_uid', value: '10150cf3-9820-41e0-8ea6-97105b6eaf45')
            column(name: 'df_scenario_uid', value: '04263c90-cb54-44f0-b354-a901586e5801')
            column(name: 'wr_wrk_inst', value: '127778305')
            column(name: 'work_title', value: 'Sunbeams')
            column(name: 'rh_account_number', value: '7000515031')
            column(name: 'payee_account_number', value: '7000515031')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: '10')
            column(name: 'gross_amount', value: '5000.00')
            column(name: 'service_fee_amount', value: '1600.00')
            column(name: 'net_amount', value: '3400.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '4713282c-c698-4ffb-8de1-44863d48954f')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '5000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "914f7ff1-f0d8-4b7e-8d0d-a9e96382fc35")
            column(name: "df_usage_uid", value: "4713282c-c698-4ffb-8de1-44863d48954f")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'Test Batch 2'")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'daf2483b-a7b4-415b-81d2-adb328423661')
            column(name: 'df_usage_batch_uid', value: '10150cf3-9820-41e0-8ea6-97105b6eaf45')
            column(name: 'df_scenario_uid', value: '04263c90-cb54-44f0-b354-a901586e5801')
            column(name: 'wr_wrk_inst', value: '122861189')
            column(name: 'work_title', value: 'Sunbeams')
            column(name: 'rh_account_number', value: '2000152614')
            column(name: 'payee_account_number', value: '2000152614')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: '1')
            column(name: 'gross_amount', value: '1000.00')
            column(name: 'service_fee_amount', value: '320.00')
            column(name: 'net_amount', value: '680.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'daf2483b-a7b4-415b-81d2-adb328423661')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '1000.00')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "de353de1-9002-4143-bddd-c18db07fa74c")
            column(name: "df_usage_uid", value: "daf2483b-a7b4-415b-81d2-adb328423661")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'Test Batch 2'")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f9f5d608-c6e7-49dd-b658-174522b0549e')
            column(name: 'df_usage_batch_uid', value: '10150cf3-9820-41e0-8ea6-97105b6eaf45')
            column(name: 'df_scenario_uid', value: '04263c90-cb54-44f0-b354-a901586e5801')
            column(name: 'wr_wrk_inst', value: '123647460')
            column(name: 'work_title', value: 'Sunbeams')
            column(name: 'rh_account_number', value: '2000152614')
            column(name: 'payee_account_number', value: '2000152614')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: '50')
            column(name: 'gross_amount', value: '200.00')
            column(name: 'service_fee_amount', value: '64.00')
            column(name: 'net_amount', value: '136.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f9f5d608-c6e7-49dd-b658-174522b0549e')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "9c83c9ec-2823-4f60-87bf-0ff3c00c76b1")
            column(name: "df_usage_uid", value: "f9f5d608-c6e7-49dd-b658-174522b0549e")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'Test Batch 2'")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd2da6044-7ff7-4b5d-984a-69978b9e0678')
            column(name: 'df_usage_batch_uid', value: '10150cf3-9820-41e0-8ea6-97105b6eaf45')
            column(name: 'df_scenario_uid', value: '04263c90-cb54-44f0-b354-a901586e5801')
            column(name: 'wr_wrk_inst', value: '122799407')
            column(name: 'work_title', value: 'Sunbeams')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'payee_account_number', value: '1000000026')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '1800.00')
            column(name: 'service_fee_amount', value: '576.00')
            column(name: 'net_amount', value: '1224.00')
            column(name: 'service_fee', value: '0.32000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd2da6044-7ff7-4b5d-984a-69978b9e0678')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '1800.00')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "71207b2f-d6da-4771-a1e4-020c3e81d589")
            column(name: "df_usage_uid", value: "d2da6044-7ff7-4b5d-984a-69978b9e0678")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'Test Batch 2'")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f1d2c084-973b-4c88-9b45-d4060d87b4ba')
            column(name: 'df_usage_batch_uid', value: '10150cf3-9820-41e0-8ea6-97105b6eaf45')
            column(name: 'df_scenario_uid', value: '04263c90-cb54-44f0-b354-a901586e5801')
            column(name: 'wr_wrk_inst', value: '123636551')
            column(name: 'work_title', value: 'Sunbeams')
            column(name: 'rh_account_number', value: '1000000322')
            column(name: 'payee_account_number', value: '1000000322')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: '12')
            column(name: 'gross_amount', value: '4500.00')
            column(name: 'service_fee_amount', value: '720.00')
            column(name: 'net_amount', value: '3870.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'f1d2c084-973b-4c88-9b45-d4060d87b4ba')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '4500.00')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "ffb049eb-1c0f-48eb-afa8-e4671ecbbf9c")
            column(name: "df_usage_uid", value: "f1d2c084-973b-4c88-9b45-d4060d87b4ba")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'Test Batch 2'")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cf2b4a25-d786-4fee-9c7f-5bec12b017c1')
            column(name: 'df_usage_batch_uid', value: '10150cf3-9820-41e0-8ea6-97105b6eaf45')
            column(name: 'df_scenario_uid', value: '04263c90-cb54-44f0-b354-a901586e5801')
            column(name: 'wr_wrk_inst', value: '123642505')
            column(name: 'work_title', value: 'Sunbeams')
            column(name: 'rh_account_number', value: '1000000322')
            column(name: 'payee_account_number', value: '1000000322')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '452365874521235XX')
            column(name: 'number_of_copies', value: '100')
            column(name: 'gross_amount', value: '2500.00')
            column(name: 'service_fee_amount', value: '400.00')
            column(name: 'net_amount', value: '2100.00')
            column(name: 'service_fee', value: '0.16000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'cf2b4a25-d786-4fee-9c7f-5bec12b017c1')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'reported_value', value: '2500.00')
        }

        insert(schemaName: dbAppsSchema, tableName: "df_usage_audit") {
            column(name: "df_usage_audit_uid", value: "d811a761-639c-450b-899f-6a45d65c58bb")
            column(name: "df_usage_uid", value: "cf2b4a25-d786-4fee-9c7f-5bec12b017c1")
            column(name: "action_type_ind", value: "LOADED")
            column(name: "action_reason", value: "Uploaded in 'Test Batch 2'")
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder_discrepancy') {
            column(name: 'df_rightsholder_discrepancy_uid', value: 'e4877bab-090b-49c0-adaa-6720217ea37d')
            column(name: 'df_scenario_uid', value: '04263c90-cb54-44f0-b354-a901586e5801')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'old_rh_account_number', value: '1000009997')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'status_ind', value: 'IN_PROGRESS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '1281cdca-a838-4047-802c-850d46fd51b6')
            column(name: 'rh_account_number', value: '7000515031')
            column(name: 'name', value: '3media Group')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'cd1d10f8-c625-4634-afee-9b983071e725')
            column(name: 'rh_account_number', value: '2000152614')
            column(name: 'name', value: '24 Images')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '01ec97ae-8607-4494-b7b5-158aae3ebd5b')
            column(name: 'rh_account_number', value: '1000000026')
            column(name: 'name', value: 'Georgia State University Business Press [C]')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'e395cf0e-312f-425c-a37d-4a7ab824b8f6')
            column(name: 'rh_account_number', value: '1000000322')
            column(name: 'name', value: 'American College of Physicians - Journals')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '00d4ae90-5fe7-47bf-ace1-781c8d76d4da')
            column(name: 'rh_account_number', value: '1000001820')
            column(name: 'name', value: 'Delhi Medical Assn')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '3259b121-8b0e-4b9e-8d7a-88707cf418a4')
            column(name: 'rh_account_number', value: '2000017000')
            column(name: 'name', value: 'CEDRO, Centro Espanol de Derechos Reprograficos')
        }
    }
}
