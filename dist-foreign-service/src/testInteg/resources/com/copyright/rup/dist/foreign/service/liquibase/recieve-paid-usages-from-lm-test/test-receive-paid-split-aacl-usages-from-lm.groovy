databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2018-09-13-00', author: 'Pavel Liakh <pliakh@copyright.com>') {
        comment('Inserting test data for testReceivePaidSplitAaclUsagesFromLm')

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: 'de1d65f6-10c6-462c-bd97-44fcfc976934')
            column(name: 'name', value: 'Paid Scenario')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'description', value: 'Scenario already sent to LM')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '2e3d35ce-28a2-4368-a4f3-1d6a87f3b7c2')
            column(name: 'name', value: 'AACL batch')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'AACL')
            column(name: 'initial_usages_count', value: 3)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '6e8172d6-c16f-4522-8606-e55db1b8e5a4')
            column(name: 'df_scenario_uid', value: 'de1d65f6-10c6-462c-bd97-44fcfc976934')
            column(name: 'df_usage_batch_uid', value: '2e3d35ce-28a2-4368-a4f3-1d6a87f3b7c2')
            column(name: 'product_family', value: 'AACL')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'number_of_copies', value: 300)
            column(name: 'gross_amount', value: 500.00)
            column(name: 'net_amount', value: 420.00)
            column(name: 'service_fee_amount', value: 80.00)
            column(name: 'service_fee', value: 0.25)
            column(name: 'comment', value: 'AACL archived usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '6e8172d6-c16f-4522-8606-e55db1b8e5a4')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'usage_period', value: 2017)
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'number_of_pages', value: 341)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '30771d65-8b91-42e5-865c-0c73fddbee0c')
            column(name: 'df_scenario_uid', value: 'de1d65f6-10c6-462c-bd97-44fcfc976934')
            column(name: 'df_usage_batch_uid', value: '2e3d35ce-28a2-4368-a4f3-1d6a87f3b7c2')
            column(name: 'product_family', value: 'AACL')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'SENT_TO_LM')
            column(name: 'standard_number', value: '4680262122277')
            column(name: 'standard_number_type', value: 'VALISBN10')
            column(name: 'number_of_copies', value: 20)
            column(name: 'gross_amount', value: 1500.00)
            column(name: 'net_amount', value: 1125.00)
            column(name: 'service_fee_amount', value: 375.00)
            column(name: 'service_fee', value: 0.25)
            column(name: 'comment', value: 'AACL archived usage')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_aacl') {
            column(name: 'df_usage_aacl_uid', value: '30771d65-8b91-42e5-865c-0c73fddbee0c')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'usage_period', value: 2017)
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'number_of_pages', value: 30)
            column(name: 'right_limitation', value: 'ALL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '7d8534bc-118c-4144-9bc4-9f376f99c854')
            column(name: 'df_scenario_uid', value: 'de1d65f6-10c6-462c-bd97-44fcfc976934')
            column(name: 'df_usage_batch_uid', value: '2e3d35ce-28a2-4368-a4f3-1d6a87f3b7c2')
            column(name: 'product_family', value: 'AACL')
            column(name: 'wr_wrk_inst', value: 243904752)
            column(name: 'system_title', value: 'Biological Journal')
            column(name: 'rh_account_number', value: 1000003821)
            column(name: 'period_end_date', value: '2019-06-30')
            column(name: 'status_ind', value: 'SENT_TO_LM')
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
            column(name: 'df_usage_aacl_uid', value: '7d8534bc-118c-4144-9bc4-9f376f99c854')
            column(name: 'institution', value: 'University of Michigan')
            column(name: 'original_publication_type', value: 'Scholarly Journal')
            column(name: 'usage_period', value: 2017)
            column(name: 'usage_source', value: 'Feb 2017 TUR')
            column(name: 'detail_licensee_class_id', value: 143)
            column(name: 'df_publication_type_uid', value: '68fd94c0-a8c0-4a59-bfe3-6674c4b12199')
            column(name: 'number_of_pages', value: 341)
            column(name: 'right_limitation', value: 'ALL')
        }

        rollback {
            dbRollback
        }
    }
}
