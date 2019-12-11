databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-01-31-00', author: 'Darya Baraukova <dbaraukova@copyright.com>') {
        comment('Inserting test data for send usages for rights assignment test')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '01219a91-d033-40db-a9ba-d717513a3b65')
            column(name: 'name', value: 'SENT_FOR_RA_TEST')
            column(name: 'rro_account_number', value: '1000023401')
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2018-01-24')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '1394.00')
            column(name: 'updated_datetime', value: '2018-01-24 08:20:19.813522-05')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '935612bc-cf10-4ef7-8d46-47890fccdba8')
            column(name: 'df_usage_batch_uid', value: '01219a91-d033-40db-a9ba-d717513a3b65')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'gross_amount', value: '70.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '935612bc-cf10-4ef7-8d46-47890fccdba8')
            column(name: 'article', value: 'Appendix: The Principles of Newspeak')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2015')
            column(name: 'author', value: 'Aarseth, Espen J.')
            column(name: 'reported_value', value: '70.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3ec01afa-7f8f-4551-9f3d-22b1975f8314')
            column(name: 'df_usage_batch_uid', value: '01219a91-d033-40db-a9ba-d717513a3b65')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'gross_amount', value: '25.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '3ec01afa-7f8f-4551-9f3d-22b1975f8314')
            column(name: 'article', value: 'Appendix: The Principles of Newspeak')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2015')
            column(name: 'author', value: 'Aarseth, Espen J.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '857cdd83-a88d-4777-9e91-d2386adbd146')
            column(name: 'df_usage_batch_uid', value: '01219a91-d033-40db-a9ba-d717513a3b65')
            column(name: 'wr_wrk_inst', value: '123194821')
            column(name: 'work_title', value: 'Wissenschaft & Forschung Japan')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'gross_amount', value: '99.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '857cdd83-a88d-4777-9e91-d2386adbd146')
            column(name: 'article', value: 'Appendix: The Principles of Newspeak')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2015')
            column(name: 'author', value: 'Aarseth, Espen J.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd82aaf46-8837-4e59-a158-d485d01f9a16')
            column(name: 'df_usage_batch_uid', value: '01219a91-d033-40db-a9ba-d717513a3b65')
            column(name: 'wr_wrk_inst', value: '946768461')
            column(name: 'work_title', value: '1984')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd82aaf46-8837-4e59-a158-d485d01f9a16')
            column(name: 'article', value: 'Appendix: The Principles of Newspeak')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2015')
            column(name: 'author', value: 'Aarseth, Espen J.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '55710948-f203-4547-92b9-3c4526ac32c5')
            column(name: 'df_usage_batch_uid', value: '01219a91-d033-40db-a9ba-d717513a3b65')
            column(name: 'wr_wrk_inst', value: '946768461')
            column(name: 'work_title', value: '1984')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'gross_amount', value: '200.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '55710948-f203-4547-92b9-3c4526ac32c5')
            column(name: 'article', value: 'Appendix: The Principles of Newspeak')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2015')
            column(name: 'author', value: 'Aarseth, Espen J.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '427f017c-688b-4c89-9560-c3ea01e55134')
            column(name: 'df_usage_batch_uid', value: '01219a91-d033-40db-a9ba-d717513a3b65')
            column(name: 'wr_wrk_inst', value: '946768462')
            column(name: 'work_title', value: '1984')
            column(name: 'status_ind', value: 'RH_NOT_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'gross_amount', value: '300.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '427f017c-688b-4c89-9560-c3ea01e55134')
            column(name: 'article', value: 'Appendix: The Principles of Newspeak')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2015')
            column(name: 'author', value: 'Aarseth, Espen J.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '1c17cae4-4188-4176-b31d-926d5c6f10d3')
            column(name: 'df_usage_batch_uid', value: '01219a91-d033-40db-a9ba-d717513a3b65')
            column(name: 'wr_wrk_inst', value: '127778305')
            column(name: 'work_title', value: '1984')
            column(name: 'rh_account_number', value: '1000009522')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'gross_amount', value: '150.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '1c17cae4-4188-4176-b31d-926d5c6f10d3')
            column(name: 'article', value: 'Appendix: The Principles of Newspeak')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ,Bus,Doc,S')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2015')
            column(name: 'author', value: 'Aarseth, Espen J.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '75e17c8c-8782-4071-95d9-0c15ffd0005d')
            column(name: 'df_usage_batch_uid', value: '01219a91-d033-40db-a9ba-d717513a3b65')
            column(name: 'wr_wrk_inst', value: '122799407')
            column(name: 'work_title', value: '(En)gendering the war on terror : war stories and camouflaged politics')
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'gross_amount', value: '250.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '75e17c8c-8782-4071-95d9-0c15ffd0005d')
            column(name: 'article', value: 'between orientalism and fundamentalism')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2015')
            column(name: 'author', value: 'Aarseth, Espen J.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '7608e81e-0b43-4c21-9407-b14177ee7aa6')
            column(name: 'df_usage_batch_uid', value: '01219a91-d033-40db-a9ba-d717513a3b65')
            column(name: 'wr_wrk_inst', value: '123565461')
            column(name: 'work_title', value: 'Annals of internal medicine')
            column(name: 'status_ind', value: 'SENT_FOR_RA')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'gross_amount', value: '150.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: '7608e81e-0b43-4c21-9407-b14177ee7aa6')
            column(name: 'article', value: 'Appendix: The Principles of Newspeak')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2015')
            column(name: 'author', value: 'Aarseth, Espen J.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd4ab919f-37a7-46b2-bd73-610cced3bb7b')
            column(name: 'df_usage_batch_uid', value: '01219a91-d033-40db-a9ba-d717513a3b65')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'standard_number', value: '9780000000000')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_fas') {
            column(name: 'df_usage_fas_uid', value: 'd4ab919f-37a7-46b2-bd73-610cced3bb7b')
            column(name: 'publication_date', value: '3000-12-12')
            column(name: 'market', value: 'Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2015')
        }
    }
}
