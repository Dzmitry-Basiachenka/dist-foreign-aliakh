databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-05-19-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment('Inserting test data for testFindAaclUsagesByIdsAndStatus')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '2e3d35ce-28a2-4368-a4f3-1d6a87f3b7c2')
            column(name: 'name', value: 'AACL batch')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: 'c6410335-0745-480d-9494-7ec773918233')
            column(name: 'wr_wrk_inst', value: 123986599)
            column(name: 'usage_period', value: 2040)
            column(name: 'usage_source', value: 'Aug 2040 FR')
            column(name: 'number_of_copies', value: 30)
            column(name: 'number_of_pages', value: 6)
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'publication_type_weight', value: 2)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_baseline_aacl') {
            column(name: 'df_usage_baseline_aacl_uid', value: '0f31bd85-397c-4a10-948b-7b97c0767815')
            column(name: 'wr_wrk_inst', value: 123986581)
            column(name: 'usage_period', value: 2050)
            column(name: 'usage_source', value: 'Aug 2050 FR')
            column(name: 'number_of_copies', value: 10)
            column(name: 'number_of_pages', value: 10)
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'df_publication_type_uid', value: '46634907-882e-4f91-b1ad-f57db945aff7')
            column(name: 'publication_type_weight', value: 2)
            column(name: 'institution', value: 'BIOLA UNIVERSITY')
            column(name: 'comment', value: 'Baseline usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '6e8172d6-c16f-4522-8606-e55db1b8e5a4')
            column(name: 'df_usage_batch_uid', value: '2e3d35ce-28a2-4368-a4f3-1d6a87f3b7c2')
            column(name: 'product_family', value: 'AACL')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'number_of_copies', value: 300)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 80.00)
            column(name: 'service_fee_amount', value: 420.00)
            column(name: 'service_fee', value: 0.25)
            column(name: 'comment', value: 'AACL archived usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '6e8172d6-c16f-4522-8606-e55db1b8e5a4')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'usage_period', value: 2040)
            column(name: 'usage_source', value: 'Feb 2040 TUR')
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'number_of_pages', value: 341)
            column(name: 'right_limitation', value: 'ALL')
            column(name: 'baseline_uid', value: 'c6410335-0745-480d-9494-7ec773918233')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '1537f313-975e-420e-b745-95f2808a388a')
            column(name: 'df_usage_batch_uid', value: '2e3d35ce-28a2-4368-a4f3-1d6a87f3b7c2')
            column(name: 'product_family', value: 'AACL')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'system_title', value: 'Snap to grid')
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'LOCKED')
            column(name: 'standard_number', value: '1008902112317555XX')
            column(name: 'standard_number_type', value: 'STDID')
            column(name: 'number_of_copies', value: 100)
            column(name: 'gross_amount', value: 1000.00)
            column(name: 'net_amount', value: 160.00)
            column(name: 'service_fee_amount', value: 840.00)
            column(name: 'service_fee', value: 0.25)
            column(name: 'comment', value: 'AACL archived usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '1537f313-975e-420e-b745-95f2808a388a')
            column(name: 'institution', value: 'University of Chicago')
            column(name: 'usage_period', value: 2040)
            column(name: 'usage_source', value: 'Feb 2040 TUR')
            column(name: 'detail_licensee_class_id', value: 108)
            column(name: 'df_publication_type_uid', value: '2fe9c0a0-7672-4b56-bc64-9d4125fecf6e')
            column(name: 'number_of_pages', value: 100)
            column(name: 'right_limitation', value: 'ALL')
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'baseline_uid', value: '0f31bd85-397c-4a10-948b-7b97c0767815')
        }

        rollback {
            dbRollback
        }
    }
}
