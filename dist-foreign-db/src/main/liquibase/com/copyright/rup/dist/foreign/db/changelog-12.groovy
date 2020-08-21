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

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '7e46693e-ec28-405d-9c82-5811d0f5c8bd')
            column(name: 'product_family', value: 'SAL')
            column(name: 'grant_product_family', value: 'SAL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '1')
            column(name: 'license_type', value: 'SAL')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                where "df_grant_priority_uid in ('84c12ad6-a2b0-41e6-be90-9142f8f2a830','7e46693e-ec28-405d-9c82-5811d0f5c8bd')"
            }
        }
    }
}
