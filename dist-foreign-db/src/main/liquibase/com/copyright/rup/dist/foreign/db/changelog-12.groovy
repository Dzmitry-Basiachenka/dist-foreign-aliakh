databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-08-20-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment("B-57902 FDA: Get rights information from RMS for SAL usages (get grants): " +
                "add grant priority data for SAL product family")

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '84c12ad6-a2b0-41e6-be90-9142f8f2a830')
            column(name: 'product_family', value: 'SAL')
            column(name: 'grant_product_family', value: 'SAL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '0')
            column(name: 'license_type', value: 'SAL')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                where "df_grant_priority_uid = '84c12ad6-a2b0-41e6-be90-9142f8f2a830'"
            }
        }
    }

    changeSet(id: '2020-09-23-00', author: 'Uladzislau Shalamitski <ushalamitski@copyright.com>') {
        comment("B-57917 FDA: Load SAL fund pool: add sal_fields column to df_fund_pool")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'sal_fields', type: 'JSONB', remarks: 'The fields of SAL fund pool')
        }

        rollback {
            // automatic rollback
        }
    }

    changeSet(id: '2020-09-24-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment("B-57897 FDA: Create SAL Scenario: add sal_fields column to df_scenario table")

        addColumn(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'sal_fields', type: 'JSONB', remarks: 'The fields specific for SAL scenario')
        }

        rollback {
            // automatic rollback
        }
    }
}
