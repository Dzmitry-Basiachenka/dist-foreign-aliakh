databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-08-21-00', author: 'Stanislau Rudak <srudak@copyright.com>') {
        comment('Inserting data for testSalWorkflow')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '85f864f2-30a5-4215-ac4f-f1f541901218')
            column(name: 'rh_account_number', value: '1000000322')
            column(name: 'name', value: 'American College of Physicians - Journals')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f')
            column(name: 'rh_account_number', value: '1000024950')
            column(name: 'name', value: '101 Communications, Ltd.')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '077383cf-8d9b-42ac-bdac-073cde78fa1e')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool with 100% IB split')
            column(name: 'total_amount', value: '1000.00')
            column(name: 'sal_fields', value: '{"date_received": "09/28/2020", "assessment_name": "FY2020 COG", "licensee_account_number": "4444", ' +
                    '"licensee_name": "Truman State University", "grade_K_5_number_of_students": 0, "grade_6_8_number_of_students": 0, ' +
                    '"grade_9_12_number_of_students": 0, "gross_amount": 1000.00, "item_bank_amount": 750.00, "item_bank_gross_amount": 750.00, ' +
                    '"grade_K_5_gross_amount": 0.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 0.00, "item_bank_split_percent": 1.00000, "service_fee": 0.25000}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_fund_pool') {
            column(name: 'df_fund_pool_uid', value: '1aa3a67a-b206-4953-96e7-a9c213db2902')
            column(name: 'product_family', value: 'SAL')
            column(name: 'name', value: 'SAL Fund Pool with 20% IB split')
            column(name: 'total_amount', value: '1000.00')
            column(name: 'sal_fields', value: '{"date_received": "09/28/2020", "assessment_name": "FY2020 COG", "licensee_account_number": "4444", ' +
                    '"licensee_name": "Truman State University", "grade_K_5_number_of_students": 10, "grade_6_8_number_of_students": 0, ' +
                    '"grade_9_12_number_of_students": 5, "gross_amount": 1000.00, "item_bank_amount": 200.00, "item_bank_gross_amount": 200.00, ' +
                    '"grade_K_5_gross_amount": 600.00, "grade_6_8_gross_amount": 0.00, "grade_9_12_gross_amount": 200.00, "item_bank_split_percent": 0.20000, "service_fee": 0.25000}')
        }

        rollback ""
    }
}
