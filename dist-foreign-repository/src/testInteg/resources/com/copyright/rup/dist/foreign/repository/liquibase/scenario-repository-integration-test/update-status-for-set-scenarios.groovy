databaseChangeLog {
    property(file: 'database-testInteg.properties')

    changeSet(id: '2023-02-09-00', author: 'Mikita Maistrenka <mmaistrenka@copyright.com>') {
        comment('Inserting test data for testUpdateStatusForSetScenarios')

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '25e8c71b-e07c-46cf-a91f-490f5c033809')
            column(name: 'name', value: 'SAL batch 1')
            column(name: 'product_family', value: 'SAL')
            column(name: 'payment_date', value: '2023-01-01')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '1c8e11d6-262f-43c5-bcc1-d144ed74edf8')
            column(name: 'name', value: 'SAL Distribution 02/09/2023 1')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2023-02-09 12:00:00+00')
            column(name: 'updated_datetime', value: '2023-02-09 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '03f77c25-0c4c-4266-8a48-40f327779f12')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '11')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'assessment_name', value: 'FY16 Smarter Balanaced ELA')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1101024IB2193')
            column(name: 'coverage_year', value: '2022-2023')
            column(name: 'scored_assessment_date', value: '2022-10-03')
            column(name: 'question_identifier', value: 'P401A1')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 15)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: '21ff3cd4-f485-4b0c-be6f-55b57e317743')
            column(name: 'df_scenario_uid', value: '1c8e11d6-262f-43c5-bcc1-d144ed74edf8')
            column(name: 'product_family', value: 'SAL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_batch') {
            column(name: 'df_usage_batch_uid', value: '0b95b06b-451e-4714-acf1-d66e0c97fde3')
            column(name: 'name', value: 'SAL batch 2')
            column(name: 'product_family', value: 'SAL')
            column(name: 'payment_date', value: '2023-01-01')
            column(name: 'initial_usages_count', value: 2)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario') {
            column(name: 'df_scenario_uid', value: '4ffe3de7-d56c-47cc-910c-9687374001a5')
            column(name: 'name', value: 'SAL Distribution 02/09/2023 2')
            column(name: 'status_ind', value: 'IN_PROGRESS')
            column(name: 'created_by_user', value: 'user@copyright.com')
            column(name: 'created_datetime', value: '2023-02-09 12:00:00+00')
            column(name: 'updated_datetime', value: '2023-02-09 12:00:00+00')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_usage_sal') {
            column(name: 'df_usage_sal_uid', value: '81744627-0778-41b8-b8e4-b401ceafc14e')
            column(name: 'detail_type', value: 'UD')
            column(name: 'grade', value: '11')
            column(name: 'grade_group', value: 'GRADE9_12')
            column(name: 'assessment_name', value: 'FY16 Smarter Balanaced ELA')
            column(name: 'assessment_type', value: 'ELA')
            column(name: 'reported_work_portion_id', value: '1101024IB2193')
            column(name: 'coverage_year', value: '2022-2023')
            column(name: 'scored_assessment_date', value: '2022-10-03')
            column(name: 'question_identifier', value: 'P401A1')
            column(name: 'states', value: 'SD,VT')
            column(name: 'number_of_views', value: 15)
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_scenario_usage_filter') {
            column(name: 'df_scenario_usage_filter_uid', value: 'a5c52ca6-0da0-46e9-8a4e-0133a13ddf1d')
            column(name: 'df_scenario_uid', value: '4ffe3de7-d56c-47cc-910c-9687374001a5')
            column(name: 'product_family', value: 'SAL')
        }

        rollback {
            dbRollback
        }
    }
}
