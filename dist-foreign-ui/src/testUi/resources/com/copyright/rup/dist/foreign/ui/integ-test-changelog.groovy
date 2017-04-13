databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2017-17-02-00', author: 'Mikalai_Bezmen <mbezmen@copyright.com>') {
        comment('Inserting Usage Batch with Usages for ui tests')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'name', value: 'CADRA_11Dec16')
            column(name: 'rro_account_number', value: '7000813806')
            column(name: 'payment_date', value: '2017-01-11')
            column(name: 'fiscal_year', value: '2017')
            column(name: 'gross_amount', value: '35000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-94d3458a666a')
            column(name: 'name', value: 'AccessCopyright_11Dec16')
            column(name: 'rro_account_number', value: '2000017004')
            column(name: 'payment_date', value: '2015-09-10')
            column(name: 'fiscal_year', value: '2016')
            column(name: 'gross_amount', value: '35000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'name', value: 'JAACC_11Dec16')
            column(name: 'rro_account_number', value: '7001440663')
            column(name: 'payment_date', value: '2018-08-16')
            column(name: 'fiscal_year', value: '2019')
            column(name: 'gross_amount', value: '35000')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '111111111')
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-93d3458a656a')
            column(name: 'detail_id', value: '6997788888')
            column(name: 'wr_wrk_inst', value: '180382914')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'article', value: 'Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Íñigo López de Mendoza, marqués de Santillana')
            column(name: 'number_of_copies', value: '2502232')
            column(name: 'reported_value', value: '2500')
            column(name: 'gross_amount', value: '13461.54')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '222222222')
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-94d3458a666a')
            column(name: 'detail_id', value: '6997788885')
            column(name: 'wr_wrk_inst', value: '244614835')
            column(name: 'work_title', value: '15th International Conference on Environmental Degradation of Materials in Nuclear Power Systems Water Reactors')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'INELIGIBLE')
            column(name: 'article', value: 'First-Week Protein and Energy Intakes Are Associated With 18-Month Developmental Outcomes in Extremely Low Birth Weight Infants')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'publisher', value: 'John Wiley & Sons')
            column(name: 'publication_date', value: '2011-05-10')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: 'Nanette M. Schwann')
            column(name: 'number_of_copies', value: '1600')
            column(name: 'reported_value', value: '1560')
            column(name: 'gross_amount', value: '8400.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '333333333')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'detail_id', value: '6997788882')
            column(name: 'wr_wrk_inst', value: '108738286')
            column(name: 'work_title', value: '2001 tax legislation: law, explanation, and analysis : Economic Growth and Tax Relief Reconciliation Act of 2001')
            column(name: 'rh_account_number', value: '1000008666')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: 'Google Makes Super')
            column(name: 'standard_number', value: '1008902002377656XX')
            column(name: 'publisher', value: 'CCH Inc')
            column(name: 'publication_date', value: '1999-09-27')
            column(name: 'market', value: 'Bus,Doc Del,Edu,Gov,Lib,Sch,Univ')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: '愛染恭子')
            column(name: 'number_of_copies', value: '16')
            column(name: 'reported_value', value: '850')
            column(name: 'gross_amount', value: '4577.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '444444444')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'detail_id', value: '6997788884')
            column(name: 'wr_wrk_inst', value: '345870577')
            column(name: 'work_title', value: '10 Years Plant Molecular Biology')
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902002377655XX')
            column(name: 'publisher', value: 'McGraw-Hill')
            column(name: 'publication_date', value: '2009-12-31')
            column(name: 'market', value: 'Edu')
            column(name: 'market_period_from', value: '2015')
            column(name: 'market_period_to', value: '2019')
            column(name: 'author', value: 'Mirjam H. Hüberli')
            column(name: 'number_of_copies', value: '2630')
            column(name: 'reported_value', value: '1280.00')
            column(name: 'gross_amount', value: '6892.30')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_usage') {
                where "df_usage_uid in ('111111111', '222222222', '333333333', '444444444')"
            }

            delete(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
                where "df_usage_batch_uid in ('56282dbc-2468-48d4-b926-93d3458a656a', " +
                        "'56282dbc-2468-48d4-b926-94d3458a666a', '56782dbc-2158-48d4-b026-94d3458a666a')"
            }
        }
    }

    changeSet(id: '2017-02-27-00', author: 'Mikalai_Bezmen <mbezmen@copyright.com>') {
        comment('Inserting Rightsholders for ui tests')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'name', value: 'British Film Institute (BFI)')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'name', value: 'John Wiley & Sons - Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'rh_account_number', value: '1000005413')
            column(name: 'name', value: 'Kluwer Academic Publishers - Dordrecht')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'rh_account_number', value: '1000008666')
            column(name: 'name', value: 'CCH')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'name', value: 'IEEE - Inst of Electrical and Electronics Engrs')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'rh_account_number', value: '2000017004')
            column(name: 'name', value: 'Access Copyright, The Canadian Copyright Agency')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'rh_account_number', value: '7000813806')
            column(name: 'name', value: 'CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'rh_account_number', value: '7001440663')
            column(name: 'name', value: 'JAACC, Japan Academic Association for Copyright Clearance [T]')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
                where "rh_account_number in ('1000002797', '1000002859', '1000005413', '1000008666', '1000009997', " +
                        "'2000017004', '7000813806', '7001440663')"
            }
        }
    }

    changeSet(id: '2017-03-16-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting scenario for ui tests')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'name', value: 'Scenario name')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'net_total', value: '15000')
            column(name: 'gross_total', value: '24000')
            column(name: 'reported_total', value: '18000')
            column(name: 'description', value: 'The description of scenario')
            column(name: 'created_datetime', value: '2017-01-01')
            column(name: 'updated_datetime', value: '2017-01-01')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199dss')
            column(name: 'df_usage_batch_uid', value: '56282dbc-2468-48d4-b926-94d3458a666a')
            column(name: 'df_scenario_uid', value: 'b1f0b236-3ae9-4a60-9fab-61db84199d6f')
            column(name: 'detail_id', value: '6997788886')
            column(name: 'wr_wrk_inst', value: '243904752')
            column(name: 'work_title', value: '100 ROAD MOVIES')
            column(name: 'rh_account_number', value: '1000002859')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: 'DIN EN 779:2012')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '250232')
            column(name: 'reported_value', value: '9900')
            column(name: 'gross_amount', value: '11461.54')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_scenario') {
                where "df_scenario_uid = 'b1f0b236-3ae9-4a60-9fab-61db84199d6f'"
            }

            delete(schemaName: dbAppsSchema, tableName: 'df_usage') {
                where "df_usage_uid = 'b1f0b236-3ae9-4a60-9fab-61db84199dss'"
            }
        }
    }

    changeSet(id: '2017-03-16-00', author: 'Aliaksandr Radkevich <aradkevich@copyright.com>') {
        comment('Inserting scenarios for ui tests')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b87071c4')
            column(name: 'name', value: 'Scenario 03/16/2017')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'net_total', value: '9000.00')
            column(name: 'gross_total', value: '10000.00')
            column(name: 'reported_total', value: '11000.00')
            column(name: 'description', value: 'Scenario description')
            column(name: 'created_datetime', value: '2017-03-16')
            column(name: 'updated_datetime', value: '2017-03-17')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '92cfce61-9532-448e-a2bb-2ba23636debb')
            column(name: 'name', value: 'Scenario 02/15/2017')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'net_total', value: '180.00')
            column(name: 'gross_total', value: '200.00')
            column(name: 'reported_total', value: '210.00')
            column(name: 'description', value: '')
            column(name: 'created_datetime', value: '2017-02-15')
            column(name: 'updated_datetime', value: '2017-02-16')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'b2f3bf86-8343-4f78-bdb5-20a47c6a52b8')
            column(name: 'name', value: 'Scenario 03/15/2017')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'net_total', value: '220.00')
            column(name: 'gross_total', value: '250.00')
            column(name: 'reported_total', value: '240.00')
            column(name: 'description', value: '')
            column(name: 'created_datetime', value: '2017-03-15')
            column(name: 'updated_datetime', value: '2017-03-15')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '47943793-4f9a-47b1-b2a8-e95a87aa58e6')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b87071c4')
            column(name: 'detail_id', value: '6907823886')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '2000017003')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'reported_value', value: '100.00')
            column(name: 'gross_amount', value: '100.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3846ead0-87ae-4d9b-8dfe-1b985d78c061')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '92cfce61-9532-448e-a2bb-2ba23636debb')
            column(name: 'detail_id', value: '6907723886')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '2000017003')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'reported_value', value: '200.00')
            column(name: 'gross_amount', value: '210.00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b7ee7e4c-ec30-400b-ba49-70aaf8a4940e')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: 'b2f3bf86-8343-4f78-bdb5-20a47c6a52b8')
            column(name: 'detail_id', value: '3907723886')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '2000017003')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'reported_value', value: '250.00')
            column(name: 'gross_amount', value: '240.00')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_scenario') {
                where "df_scenario_uid in ('5c3c3412-dea5-4572-a894-15a4b87071c4', '92cfce61-9532-448e-a2bb-2ba23636debb'," +
                        " 'b2f3bf86-8343-4f78-bdb5-20a47c6a52b8')"
            }

            delete(schemaName: dbAppsSchema, tableName: 'df_usage') {
                where "df_usage_uid in ('47943793-4f9a-47b1-b2a8-e95a87aa58e6', '3846ead0-87ae-4d9b-8dfe-1b985d78c061'," +
                        " 'b7ee7e4c-ec30-400b-ba49-70aaf8a4940e')"
            }
        }
    }

    changeSet(id: '2017-04-07-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting scenario with usages with different rightsholders for ui tests')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'name', value: 'Scenario for viewing')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'net_total', value: '9000.00')
            column(name: 'gross_total', value: '10530.00')
            column(name: 'reported_total', value: '10530.00')
            column(name: 'description', value: 'Scenario description')
            column(name: 'created_datetime', value: '2017-03-17')
            column(name: 'updated_datetime', value: '2017-03-17')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '47943793-4f9a-47b1-b2a8-e95a87aa58g6')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'detail_id', value: '6907853886')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '1000008666')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'reported_value', value: '3000.00')
            column(name: 'gross_amount', value: '2850.00')
            column(name: 'net_amount', value: '2400')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3846ead0-87ae-4d9b-8dfe-1b985d78c062')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'detail_id', value: '6607723886')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '1000008666')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'reported_value', value: '5000.00')
            column(name: 'gross_amount', value: '4800.00')
            column(name: 'net_amount', value: '2150')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b7ee7e4c-ec30-400b-ba49-70aaf8a4941e')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'detail_id', value: '3907723836')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '1000009997')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'reported_value', value: '2500.00')
            column(name: 'gross_amount', value: '2400.00')
            column(name: 'net_amount', value: '3400')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'b7ee7e4c-ec30-400b-ba49-70aaf8a4942e')
            column(name: 'df_usage_batch_uid', value: '56782dbc-2158-48d4-b026-94d3458a666a')
            column(name: 'df_scenario_uid', value: '5c3c3412-dea5-4572-a894-15a4b870fa39')
            column(name: 'detail_id', value: '3907723856')
            column(name: 'wr_wrk_inst', value: '122235137')
            column(name: 'work_title', value: 'TOMATOES')
            column(name: 'rh_account_number', value: '1000002797')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'article', value: '')
            column(name: 'standard_number', value: '1112202112377654XX')
            column(name: 'publisher', value: 'IEEE')
            column(name: 'publication_date', value: '2013-09-10')
            column(name: 'market', value: 'Doc Del')
            column(name: 'market_period_from', value: '2013')
            column(name: 'market_period_to', value: '2017')
            column(name: 'author', value: 'Philippe de Mézières')
            column(name: 'number_of_copies', value: '200')
            column(name: 'reported_value', value: '500.00')
            column(name: 'gross_amount', value: '480.00')
            column(name: 'net_amount', value: '1050')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_scenario') {
                where "df_scenario_uid = '5c3c3412-dea5-4572-a894-15a4b870fa39'"
            }

            delete(schemaName: dbAppsSchema, tableName: 'df_usage') {
                where "df_usage_uid in ('47943793-4f9a-47b1-b2a8-e95a87aa58g6', '3846ead0-87ae-4d9b-8dfe-1b985d78c062'," +
                        " 'b7ee7e4c-ec30-400b-ba49-70aaf8a4941e', 'b7ee7e4c-ec30-400b-ba49-70aaf8a4942e')"
            }
        }
    }
}
