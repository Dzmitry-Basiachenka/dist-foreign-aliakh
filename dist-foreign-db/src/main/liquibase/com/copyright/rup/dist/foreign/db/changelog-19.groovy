databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2023-07-10-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-80117 FDA: TOU for FAS: add records to df_grant_priority table for FAS TOU")

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '2f146b9d-a11b-400b-80fb-b71e509d9f09')
            column(name: 'product_family', value: 'FAS')
            column(name: 'grant_product_family', value: 'FAS')
            column(name: 'type_of_use', value: 'FAS')
            column(name: 'license_type', value: 'FAS')
            column(name: 'priority', value: '13')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '10de2ab3-2ba8-45d2-9312-cdfb1e75ee93')
            column(name: 'product_family', value: 'FAS2')
            column(name: 'grant_product_family', value: 'FAS')
            column(name: 'type_of_use', value: 'FAS')
            column(name: 'license_type', value: 'FAS')
            column(name: 'priority', value: '13')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '5b2cb342-78b3-464a-8e9c-026d65ccd2ee')
            column(name: 'product_family', value: 'NTS')
            column(name: 'grant_product_family', value: 'FAS')
            column(name: 'type_of_use', value: 'FAS')
            column(name: 'license_type', value: 'FAS')
            column(name: 'priority', value: '13')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                where "df_grant_priority_uid in (" +
                        "'2f146b9d-a11b-400b-80fb-b71e509d9f09'," +
                        "'10de2ab3-2ba8-45d2-9312-cdfb1e75ee93'," +
                        "'5b2cb342-78b3-464a-8e9c-026d65ccd2ee')"
            }
        }
    }

    changeSet(id: '2023-07-26-00', author: 'Dzmitry Basiachenka <dbasiachenka@copyright.com>') {
        comment("B-80266 FDA: Add Ineligible reason 'Non-participating RH' to drop-down: add 'Non-participating RH' " +
                "reason to df_udm_ineligible_reason table")

        insert(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
            column(name: 'df_udm_ineligible_reason_uid', value: '3dc09f93-e9d9-4e7b-b86f-35db48f4fea0')
            column(name: 'ineligible_reason', value: 'Non-participating RH')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_udm_ineligible_reason') {
                where "df_udm_ineligible_reason_uid = '3dc09f93-e9d9-4e7b-b86f-35db48f4fea0'"
            }
        }
    }
}