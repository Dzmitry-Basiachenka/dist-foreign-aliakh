databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-02-17-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserting test data for writeNtsFundPoolsCsvReportEmpty')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: 'fcdc5d17-6e36-45ad-a352-0d7f7955527c')
            column(name: 'rh_account_number', value: 1000000001)
            column(name: 'name', value: 'Rothchild Consultants')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'a3eb038a-7f10-410e-9762-d1ba4c9d42f3')
            column(name: 'name', value: 'FAS batch')
            column(name: 'rro_account_number', value: 1000000001)
            column(name: 'product_family', value: 'FAS')
            column(name: 'payment_date', value: '2021-02-12')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'gross_amount', value: 1500.00)
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '39c9da2a-50f3-4369-af30-a33b76892a03')
            column(name: 'df_usage_batch_uid', value: 'a3eb038a-7f10-410e-9762-d1ba4c9d42f3')
            column(name: 'work_title', value: '2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA')
            column(name: 'status_ind', value: 'TO_BE_DISTRIBUTED')
            column(name: 'product_family', value: 'NTS')
            column(name: 'standard_number', value: '1008902112377654XX')
            column(name: 'number_of_copies', value: 1)
            column(name: 'gross_amount', value: 99.99)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '561bc434-a0ce-416a-8876-65cea5345cac')
            column(name: 'name', value: 'SAL batch')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'initial_usages_count', value: 2)
            column(name: 'sal_fields', value:'{"licensee_name": "Truman State University", "licensee_account_number": "4444"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '3cb3a958-694e-46b9-873f-49fe7fdd6fa4')
            column(name: 'df_usage_batch_uid', value: '561bc434-a0ce-416a-8876-65cea5345cac')
            column(name: 'wr_wrk_inst', value: 123456789)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'SAL')
            column(name: 'comment', value: 'SAL comment 1')
        }

        rollback {
            dbRollback
        }
    }
}
