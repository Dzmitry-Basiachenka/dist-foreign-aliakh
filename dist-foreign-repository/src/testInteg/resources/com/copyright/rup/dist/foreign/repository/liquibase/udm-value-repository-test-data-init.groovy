databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2021-09-23-01', author: 'Azarenka Anton <aazarenka@copyright.com>') {
        comment('Inserting test data for testFindPeriods')

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '9fecc917-7e45-4e4a-91f4-cb57c69d24a3')
            column(name: 'period', value: 202106)
            column(name: 'status_ind', value: "NEW")
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 5.000)
            column(name: 'price_year', value: 2021)
            column(name: 'currency', value: "USD")
            column(name: 'currency_exchange_rate', value: 1.05)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_value') {
            column(name: 'df_udm_value_uid', value: '803a67fd-ded7-4847-93a5-7a1ee57dbb35')
            column(name: 'period', value: 201912)
            column(name: 'status_ind', value: "NEW")
            column(name: 'rh_account_number', value: 1000002859)
            column(name: 'wr_wrk_inst', value: 306985867)
            column(name: 'system_title', value: 'Tenside, surfactants, detergents')
            column(name: 'standard_number', value: '1873-7773')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'publication_type_uid', value: '076f2c40-f524-405d-967a-3840df2b57df')
            column(name: 'assignee', value: 'jjohn@copyright.com')
            column(name: 'price', value: 5.000)
            column(name: 'price_year', value: 2021)
            column(name: 'currency', value: "USD")
            column(name: 'currency_exchange_rate', value: 1.05)
            column(name: 'currency_exchange_rate_date', value: '2021-09-10')
        }
    }
}
