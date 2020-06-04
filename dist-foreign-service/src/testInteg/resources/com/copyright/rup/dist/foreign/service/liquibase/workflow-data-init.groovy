databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2018-04-10-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting rightsholders for workflow test')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '77b111d3-9eea-49af-b815-100b9716c1b3')
            column(name: 'rh_account_number', value: '2000017000')
            column(name: 'name', value: 'CLA, The Copyright Licensing Agency Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '60080587-a225-439c-81af-f016cb33aeac')
            column(name: 'rh_account_number', value: '2000133267')
            column(name: 'name', value: '101 Communications, Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'b0e6b1f6-89e9-4767-b143-db0f49f32769')
            column(name: 'rh_account_number', value: '2000073957')
            column(name: 'name', value: '1st Contact Publishing')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'f366285a-ce46-48b0-96ee-cd35d62fb243')
            column(name: 'rh_account_number', value: '7001508482')
            column(name: 'name', value: '2000 BC Publishing Ltd')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '624dcf73-a30f-4381-b6aa-c86d17198bd5')
            column(name: 'rh_account_number', value: '1000024950')
            column(name: 'name', value: '2D Publications')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '37338ed1-7083-45e2-a96b-5872a7de3a98')
            column(name: 'rh_account_number', value: '2000139286')
            column(name: 'name', value: '2HC [T]')
        }

        rollback ""
    }
}
