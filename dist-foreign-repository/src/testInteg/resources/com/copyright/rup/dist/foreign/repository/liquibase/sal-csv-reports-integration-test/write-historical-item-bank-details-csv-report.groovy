databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2020-11-26-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment('Insert test data for testWriteHistoricalItemBankDetailsCsvReport')

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '7d81d977-a96b-49d2-b08b-fb8089aed030')
            column(name: 'rh_account_number', value: 2000017128)
            column(name: 'name', value: 'Academia Sinica')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '867d42c1-f55c-47e4-91e9-973aae806fac')
            column(name: 'rh_account_number', value: 1000017527)
            column(name: 'name', value: 'Sphere Books')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_rightsholder') {
            column(name: 'df_rightsholder_uid', value: '4d8fe2f4-29d3-4f01-ac2b-ede81cd7ae5d')
            column(name: 'rh_account_number', value: 7000256354)
            column(name: 'name', value: 'TransUnion LLC')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '55df79f3-7e3f-4d74-9931-9aa513195816')
            column(name: 'name', value: 'SAL Historical Item Bank Details report Usage Batch 1')
            column(name: 'payment_date', value: '2019-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2019)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'sal_fields', value: '{"licensee_account_number": 2000017003, "licensee_name": "ProLitteris"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '38daeed3-e4ee-4b09-b6ec-ef12a12bcd34')
            column(name: 'df_usage_batch_uid', value: '55df79f3-7e3f-4d74-9931-9aa513195816')
            column(name: 'wr_wrk_inst', value: 122973671)
            column(name: 'rh_account_number', value: 7000256354)
            column(name: 'payee_account_number', value: 1000017527)
            column(name: 'work_title', value: 'Statements')
            column(name: 'system_title', value: 'Statements')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 200.00)
            column(name: 'net_amount', value: 150.00)
            column(name: 'service_fee_amount', value: 50.00)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '38daeed3-e4ee-4b09-b6ec-ef12a12bcd34')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Statements')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: 'abd219c1-caae-4542-84e3-f9f4dba0d03b')
            column(name: 'name', value: 'SAL Historical Item Bank Details report Usage Batch 2')
            column(name: 'payment_date', value: '2020-06-30')
            column(name: 'product_family', value: 'SAL')
            column(name: 'fiscal_year', value: 2020)
            column(name: 'initial_usages_count', value: 1)
            column(name: 'sal_fields', value: '{"licensee_account_number": 2000017003, "licensee_name": "ProLitteris"}')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_archive') {
            column(name: 'df_usage_archive_uid', value: '161890bc-318c-4042-9bd4-c12c4e2a2b02')
            column(name: 'df_usage_batch_uid', value: 'abd219c1-caae-4542-84e3-f9f4dba0d03b')
            column(name: 'wr_wrk_inst', value: 180047973)
            column(name: 'rh_account_number', value: 2000017128)
            column(name: 'payee_account_number', value: 1000017527)
            column(name: 'work_title', value: 'Microeconomics')
            column(name: 'system_title', value: 'Microeconomics')
            column(name: 'standard_number', value: '09639292')
            column(name: 'standard_number_type', value: 'VALISSN')
            column(name: 'status_ind', value: 'ARCHIVED')
            column(name: 'product_family', value: 'SAL')
            column(name: 'gross_amount', value: 400.00)
            column(name: 'net_amount', value: 300.00)
            column(name: 'service_fee_amount', value: 100.00)
            column(name: 'service_fee', value: 0.25000)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '161890bc-318c-4042-9bd4-c12c4e2a2b02')
            column(name: 'detail_type', value: 'IB')
            column(name: 'grade', value: '5')
            column(name: 'grade_group', value: 'ITEM_BANK')
            column(name: 'assessment_name', value: 'FY16 AIR')
            column(name: 'reported_work_portion_id', value: '1201001IB2362')
            column(name: 'reported_article', value: 'Microeconomics')
            column(name: 'reported_standard_number', value: '450220996')
            column(name: 'reported_author', value: 'Stephen Ornes')
            column(name: 'reported_publisher', value: 'Associated Press')
            column(name: 'reported_publication_date', value: '2015-01-02')
            column(name: 'reported_page_range', value: '14-17')
            column(name: 'reported_vol_number_series', value: 'Vol 17, Issue 2')
            column(name: 'reported_media_type', value: 'TEXT')
            column(name: 'media_type_weight', value: 1.0)
            column(name: 'coverage_year', value: '2014-2015')
        }

        rollback {
            dbRollback
        }
    }
}
