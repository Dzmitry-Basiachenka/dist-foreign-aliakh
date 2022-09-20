databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2021-02-16-00', author: 'Ihar Suvorau <isuvorau@copyright.com>') {
        comment('Inserting test data for testFindUsageBatchStatusesAacl')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'name', value: 'AACL in progress batch')
            column(name: 'payment_date', value: '2021-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'initial_usages_count', value: 7)
            column(name: 'baseline_years', value: 0)
            column(name: 'created_datetime', value: '2021-02-14 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '327ecb4d-e091-41ef-a0ab-56201331b0c6')
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'status_ind', value: 'NEW')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '327ecb4d-e091-41ef-a0ab-56201331b0c6')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2018)
            column(name: 'usage_source', value: 'Feb 2018 TUR')
            column(name: 'number_of_pages', value: 341)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'e3e9d0e7-717f-4cdc-8e30-e0ec57badb14')
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'wr_wrk_inst', value: 109040891)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 300)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'e3e9d0e7-717f-4cdc-8e30-e0ec57badb14')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 200)
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '81c8e8da-0888-4f9c-91aa-a94ea18fc1e4')
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'wr_wrk_inst', value: 109040891)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'gross_amount', value: 100.00)
            column(name: 'status_ind', value: 'WORK_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 300)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '81c8e8da-0888-4f9c-91aa-a94ea18fc1e4')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 200)
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '58b39805-9eb9-48b0-8081-b25ac3dc7335')
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'wr_wrk_inst', value: 109040891)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 300)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '58b39805-9eb9-48b0-8081-b25ac3dc7335')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 200)
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: 'f9286815-0760-4aa0-a801-f78b362f5e8e')
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'wr_wrk_inst', value: 109040891)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'status_ind', value: 'RH_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 300)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: 'f9286815-0760-4aa0-a801-f78b362f5e8e')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 200)
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '9f14014c-2b72-4c2e-9751-fb8ecb0123f0')
            column(name: 'df_usage_batch_uid', value: 'f77ab6ea-56d3-45dc-8926-9a8cd448f229')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'rh_account_number', value: 7000000002)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '9f14014c-2b72-4c2e-9751-fb8ecb0123f0')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '3d7c9de0-3d14-42e4-a500-fb10344a77ff')
            column(name: 'name', value: 'AACL completed batch')
            column(name: 'payment_date', value: '2021-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'fiscal_year', value: 2021)
            column(name: 'initial_usages_count', value: 8)
            column(name: 'baseline_years', value: 0)
            column(name: 'created_datetime', value: '2021-02-15 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '570c19f3-85bd-4495-82d7-dc74dc3b809b')
            column(name: 'df_usage_batch_uid', value: '3d7c9de0-3d14-42e4-a500-fb10344a77ff')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'status_ind', value: 'WORK_NOT_FOUND')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 1)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '570c19f3-85bd-4495-82d7-dc74dc3b809b')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2018)
            column(name: 'usage_source', value: 'Feb 2018 TUR')
            column(name: 'number_of_pages', value: 341)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '8afd52fb-ab50-47fd-8a2f-3b5564a23055')
            column(name: 'df_usage_batch_uid', value: '3d7c9de0-3d14-42e4-a500-fb10344a77ff')
            column(name: 'wr_wrk_inst', value: 109040891)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 300)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '8afd52fb-ab50-47fd-8a2f-3b5564a23055')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 200)
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '0804536d-d240-480f-9558-c860894cafd1')
            column(name: 'df_usage_batch_uid', value: '3d7c9de0-3d14-42e4-a500-fb10344a77ff')
            column(name: 'wr_wrk_inst', value: 109040891)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'gross_amount', value: 100.00)
            column(name: 'status_ind', value: 'WORK_RESEARCH')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 300)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '0804536d-d240-480f-9558-c860894cafd1')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'usage_period', value: 2021)
            column(name: 'usage_source', value: 'Feb 2021 TUR')
            column(name: 'number_of_pages', value: 200)
            column(name: 'right_limitation', value: 'DIGITAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '13ca08ff-5e52-4154-9654-c51b3ecdb8bf')
            column(name: 'df_usage_batch_uid', value: '3d7c9de0-3d14-42e4-a500-fb10344a77ff')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'rh_account_number', value: 7000000002)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '13ca08ff-5e52-4154-9654-c51b3ecdb8bf')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage') {
            column(name: 'df_usage_uid', value: '98b0754c-cf62-44c3-8754-e46e8096012c')
            column(name: 'df_usage_batch_uid', value: '3d7c9de0-3d14-42e4-a500-fb10344a77ff')
            column(name: 'wr_wrk_inst', value: 269040891)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'rh_account_number', value: 2580011451)
            column(name: 'rh_account_number', value: 7000000002)
            column(name: 'status_ind', value: 'ELIGIBLE')
            column(name: 'product_family', value: 'AACL')
            column(name: 'number_of_copies', value: 10)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '98b0754c-cf62-44c3-8754-e46e8096012c')
            column(name: 'institution', value: 'CORNELL UNIVERSITY')
            column(name: 'usage_period', value: 2015)
            column(name: 'usage_source', value: 'Feb 2015 TUR')
            column(name: 'number_of_pages', value: 12)
            column(name: 'right_limitation', value: 'PRINT')
            column(name: 'detail_licensee_class_id', value: 113)
            column(name: 'df_publication_type_uid', value: '1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e')
        }

        rollback {
            dbRollback
        }
    }
}
