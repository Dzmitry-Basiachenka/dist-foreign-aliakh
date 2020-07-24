databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2020-07-29-00', author: 'Aliaksandr Liakh <aliakh@copyright.com>') {
        comment("B-57883 FDA: SAL UI usage view: create df_usage_sal table")

        createTable(tableName: 'df_usage_sal', schemaName: dbAppsSchema, tablespace: dbDataTablespace,
                remarks: 'Table for storing specific fields of usages with SAL product family') {

            column(name: 'df_usage_sal_uid', type: 'VARCHAR(255)', remarks: 'The identifier of usage')
            column(name: 'detail_type', type: 'VARCHAR(32)', remarks: 'The detail type') {
                constraints(nullable: false)
            }
            column(name: 'grade', type: 'VARCHAR(32)', remarks: 'The grade')
            column(name: 'grade_group', type: 'VARCHAR(32)', remarks: 'The grade group')
            column(name: 'assessment_name', type: 'VARCHAR(1000)', remarks: 'The assessment name') {
                constraints(nullable: false)
            }
            column(name: 'assessment_type', type: 'VARCHAR(1000)', remarks: 'The assessment type') {
                constraints(nullable: false)
            }
            column(name: 'reported_work_portion_id', type: 'VARCHAR(1000)', remarks: 'The reported work portion id') {
                constraints(nullable: false)
            }
            column(name: 'reported_title', type: 'VARCHAR(1000)', remarks: 'The reported article or chapter title')
            column(name: 'reported_article', type: 'VARCHAR(1000)', remarks: 'The reported article or chapter title')
            column(name: 'reported_standard_number', type: 'VARCHAR(1000)', remarks: 'The reported_standard_number')
            column(name: 'reported_author', type: 'VARCHAR(1000)', remarks: 'The reported author')
            column(name: 'reported_publisher', type: 'VARCHAR(1000)', remarks: 'The reported publisher')
            column(name: 'reported_publication_date', type: 'TIMESTAMPTZ', remarks: 'The reported publication date')
            column(name: 'reported_page_range', type: 'VARCHAR(1000)', remarks: 'The reported page range')
            column(name: 'reported_vol_number_series', type: 'VARCHAR(1000)', remarks: 'The reported vol/number/series')
            column(name: 'reported_media_type', type: 'VARCHAR(16)', remarks: 'The reported media type')
            column(name: 'media_type_weight', type: 'NUMERIC(38,1)', remarks: 'The media type weight')
            column(name: 'coverage_year', type: 'VARCHAR(100)', remarks: 'The coverage year') {
                constraints(nullable: false)
            }
            column(name: 'scored_assessment_date', type: 'TIMESTAMPTZ', remarks: 'The scored assessment date') {
                constraints(nullable: false)
            }
            column(name: 'question_identifier', type: 'VARCHAR(1000)', remarks: 'The question identifier') {
                constraints(nullable: false)
            }
            column(name: 'states', type: 'VARCHAR(1000)', remarks: 'The states') {
                constraints(nullable: false)
            }
            column(name: 'number_of_views', type: 'NUMERIC(38)', remarks: 'The number of views') {
                constraints(nullable: false)
            }
            column(name: 'record_version', type: 'INTEGER', defaultValue: '1',
                    remarks: 'The latest version of this record, used for optimistic locking') {
                constraints(nullable: false)
            }
            column(name: 'created_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM',
                    remarks: 'The user name who created this record') {
                constraints(nullable: false)
            }
            column(name: 'created_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()',
                    remarks: 'The date and time this record was created') {
                constraints(nullable: false)
            }
            column(name: 'updated_by_user', type: 'VARCHAR(320)', defaultValue: 'SYSTEM',
                    remarks: 'The user name who updated this record; when a record is first created, this will be the same as the created_by_user') {
                constraints(nullable: false)
            }
            column(name: 'updated_datetime', type: 'TIMESTAMPTZ', defaultValueDate: 'now()',
                    remarks: 'The date and time this record was created; when a record is first created, this will be the same as the created_datetime') {
                constraints(nullable: false)
            }
        }

        rollback {
            dropTable(tableName: 'df_usage_sal', schemaName: dbAppsSchema)
        }
    }
}
