databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-02-27-00', author: 'Aliaksandr Radkevich <aradkevich@copyright.com>') {
        comment('Inserting test data for WorksMatchingQuartzJobIntegrationTest')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'name', value: 'Get Works Test')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'payment_date', value: '2018-01-11')
            column(name: 'fiscal_year', value: '2018')
            column(name: 'gross_amount', value: '35000')
        }

        // no standard number, no title, more than $100
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'aa8642d5-ac6f-4f38-8c93-5fef55dd37ce')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'rh_account_number', value: '1000024497')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'd906069c-3266-11e8-b467-0ed5f89f718b')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'rh_account_number', value: '1000024497')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '10')
            column(name: 'reported_value', value: '110.00')
            column(name: 'gross_amount', value: '110.00')
        }

        // no standard number, no title, less than $100
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '77a10e10-8154-49c1-88b8-7f5f0ad86c08')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'rh_account_number', value: '1000024497')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '99.00')
            column(name: 'gross_amount', value: '99.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3d85b292-3600-48e8-ba39-37d193afdfa6')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'rh_account_number', value: '1000024497')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '99.00')
            column(name: 'gross_amount', value: '99.00')
        }

        // standard number, no title, work found
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4773573f-acd7-424f-8667-2828d30b5738')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '978-0-271-01750-1')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00')
            column(name: 'gross_amount', value: '90.63')
        }

        // standard number, no title, more than $100
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0ff04fa7-245c-4663-835b-48a7e0e7d9f9')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '0-325-01548-2')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'number_of_copies', value: '90')
            column(name: 'reported_value', value: '50.00')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'efd7813c-e4d6-41e3-824c-b22035af31d5')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '0-325-01548-2')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'number_of_copies', value: '90')
            column(name: 'reported_value', value: '51.00')
            column(name: 'gross_amount', value: '51.00')
        }

        // one detail with standard number, equal titles, work found by title
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '363ac3fe-20db-4db7-a967-57963c98aa05')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'work_title', value: 'Forbidden rites')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'number_of_copies', value: '90')
            column(name: 'reported_value', value: '101.00')
            column(name: 'gross_amount', value: '101.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'cec47d5f-0df9-43df-a1e2-76b03fa0ce96')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'work_title', value: 'Forbidden rites')
            column(name: 'standard_number', value: '0-325-01548-2')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'number_of_copies', value: '90')
            column(name: 'reported_value', value: '101.00')
            column(name: 'gross_amount', value: '101.00')
        }

        // standard number, no title, less than $100
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '2186a1ed-62d1-4034-a6a6-ea9e61835b60')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '0-271-01751-1')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'number_of_copies', value: '90')
            column(name: 'reported_value', value: '50.00')
            column(name: 'gross_amount', value: '50.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8850b2f3-9fef-47da-a8f9-11143a892741')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '0-271-01751-1')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'number_of_copies', value: '90')
            column(name: 'reported_value', value: '49.00')
            column(name: 'gross_amount', value: '49.00')
        }

        // no standard number, title, work found in PI
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c9ad10c6-eaeb-4485-b04b-d23d265f7bb5')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Forbidden rites')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '25')
            column(name: 'reported_value', value: '5000.00')
            column(name: 'gross_amount', value: '4531.33')
        }

        // no standard number, title, more than $100
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4336ca5e-44fe-46a9-996a-55bdd5967191')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Annuaire de la communication en Rhône-Alpes')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'number_of_copies', value: '60')
            column(name: 'reported_value', value: '49.00')
            column(name: 'gross_amount', value: '49.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'dc9b3bb8-4135-472a-a7c3-50d800b88829')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Annuaire de la communication en Rhône-Alpes')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'number_of_copies', value: '60')
            column(name: 'reported_value', value: '50.00')
            column(name: 'gross_amount', value: '50.00')
        }

        // no standard number, title, less than $100 by title
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '223985fd-8033-42e6-afaf-4da73be804cf')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Solar Cells')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '60.00')
            column(name: 'gross_amount', value: '49.22')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '4abbb5ea-516f-4b60-bfc4-e8a2d274bb34')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Solar Cells')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '60.00')
            column(name: 'gross_amount', value: '50.77')
        }

        // no standard number, title, work not found
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '68fb88b0-ed9b-4c36-93bc-2bf0b51c7223')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Fall guys in the florentine flood')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '101.00')
            column(name: 'gross_amount', value: '101.00')
        }

        // standard number, title, work found
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c25a3be4-138c-47b2-b7b6-41029b063679')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Solar Cells')
            column(name: 'standard_number', value: '978-0-271-01750-1')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '500.00')
            column(name: 'gross_amount', value: '500.00')
        }

        // standard number, title, less than $100
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'c9ecd9ce-043e-4982-85c1-8e3aa97eaed6')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Solar Cells')
            column(name: 'standard_number', value: '0-08-027365-3')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '99.00')
            column(name: 'gross_amount', value: '99.00')
        }

        // standard number, title + no title, more than $100
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '6e6c1852-70c3-4900-9b3d-47c6d3add697')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Solar Cells')
            column(name: 'standard_number', value: '10.1353/PGN.1999.0081')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '33.00')
            column(name: 'gross_amount', value: '33.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '182c7557-e67a-4ac9-8f73-61972f1f5abb')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'standard_number', value: '10.1353/PGN.1999.0081')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '67.01')
            column(name: 'gross_amount', value: '67.01')
        }

        // one standard number, 2 equal titles
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8d4006a3-cbae-48ab-a1e2-e0d5bd204f4d')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Proceedings of a symposium')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '40.00')
            column(name: 'gross_amount', value: '40.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '792252e1-9182-4e1a-a6dc-2b27b0e92c18')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Proceedings of a symposium')
            column(name: 'standard_number', value: '978-0-08-027365-5')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '100.00')
            column(name: 'gross_amount', value: '100.00')
        }

        // 3 equal standard numbers, 2 equal titles, 1 unique title, sum of gross amounts = 100
        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '032aaf56-61d1-4656-abe1-de2889f1214e')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Kieckhefer, Richard, Forbidden Rites: A Necromancer\'s Manual of the Fifteenth Century')
            column(name: 'standard_number', value: 'ETOCRN066582498')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '30.00')
            column(name: 'gross_amount', value: '30.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '58d7b428-d1fc-45b0-b190-ddef5e997f7d')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Kieckhefer, Richard, Forbidden Rites: A Necromancer\'s Manual of the Fifteenth Century')
            column(name: 'standard_number', value: 'ETOCRN066582498')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '20.00')
            column(name: 'gross_amount', value: '20.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0949c35c-53f9-4aec-92d2-1e728235cd00')
            column(name: 'df_usage_batch_uid', value: '55d014d1-b1d9-428a-be8a-10defe718ae5')
            column(name: 'work_title', value: 'Kieckhefer, Richard, Forbidden Rites')
            column(name: 'standard_number', value: 'ETOCRN066582498')
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'FAS')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '100')
            column(name: 'reported_value', value: '50.00')
            column(name: 'gross_amount', value: '50.00')
        }

        rollback ""
    }
}
