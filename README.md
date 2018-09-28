3.1.14
-
* B-45518 Tech Debt: FDA: Adjust search by special symbols, update dist-common version to 20.2.+

3.1.13
-

3.1.12
-
* B-45518 Tech Debt: FDA: fix empty grid background image on audit tab
* RDSC-620 FDA: After CRM job runs, paid FAS2 details change status to ARCHIVED but absent in CRM

3.1.11
-
* B-45044 FDA: Process Post-Distribution Details: adjust integration workflow test with post-distribution usage
* B-45044 FDA: Process Post-Distribution Details: make changes based on comments in CR-DIST-FOREIGN-71, add test case to CsvReportsIntegrationTest for post-distribution usage
* B-45518 Tech Debt: FDA: update dist-common version to 20.0.0

3.1.10
-
* B-45044 FDA: Process Post-Distribution Details: adjust audit logic to show post-distribution usages
* B-45044 FDA: Process Post-Distribution Details: adjust integration tests for receiving paid information from LM, for reporting to CRM and for reading usage information from DB
* B-45518 Tech Debt: FDA: remove DirtiesContext annotation for service integration tests to speedup test execution
* B-45518 Tech Debt: FDA: set maxParrallelForks for test tasks

3.1.9
-
* B-45044 FDA: Process Post-Distribution Details: adjust update paid info logic to create post-distribution paid-information, remove batch_uid and scenario_uid not-null constrains in usage archive table
* B-45896 FDA: Change Product Name: implement liquibase script to update product family for historical data
* B-45896 FDA: Change Product Name: rearrange changesets based on database structure to update tables with lowest data first
* B-45896 FDA: Change Product Name: rename CLA_FAS product family to FAS2 on UI and reports
* B-45902 FDA: FAS Display product to RC for FAS: add serviceNameReporting field to CRM rights request
* B-45902 FDA: FAS Display product to RC for FAS: update dist-common version to 19.0+, update RMS url to PDEV address

3.1.8
-
* B-45044 FDA: Process Post-Distribution Details: move liquibase script to new changelog

3.1.7
-
* B-45517 Tech Debt: FDA: refine reconciliation process to store discrepancies by batches

3.1.6
-
* B-45517 Tech Debt: FDA: update dist-common version to 17.0.+ and rup-vaadin version to 7.2.+

3.1.5
-
* B-19334 FDA: Summary of Market Report: make changes based on comments in CR-DIST-FOREIGN-69

3.1.4
-
* B-19334 FDA: Summary of Market Report: adjust summary market widget to display list of batches instead of batches filter
* B-19334 FDA: Summary of Market Report: implement repository layer to generate summary market report

3.1.3
-
* B-19334 FDA: Summary of Market Report: implement widget and controller to generate report
* B-45516 Tech Debt: FDA: remove unused imports from AuditCsvReportHandler
* B-45516 Tech Debt: remove getBeanOffsetDateTime method from AuditCsvReportHandler

3.1.2
-
* B-44118 FDA: Add System title to the reports and the UI: make changes based on comments in CR-DIST-FOREIGN-68
* RDSC-616 FDA: Exception is shown during uploading Researched Details with System Title more than 2000 symbols

3.1.1
-
* B-44118 FDA: Add System title to the reports and the UI: add System Title to Refresh Scenario window on Scenarios tab
* B-44118 FDA: Add System title to the reports and the UI: adjust upload of researched details to consume System Title column, make Title column optional, make System Title column required
* B-45018 Tech Debt: FDA: set NewYork timezone as default for PaidUsageDeserializerTest

3.1.0
-
* B-44118 FDA: Add System title to the reports and the UI: add System Title to Usages and Audit tab, add System Title to Drill Down by RH on scenario view, adjust search on audit tab to search by system title
* B-44118 FDA: Add System title to the reports and the UI: add System Title to export from Usages tab, to Send for research export, to export from Audit tab and to export from Drill Down by RH on scenario view
* B-45018 Tech Debt: FDA: add logs for multiply matching by MainTitle during PI works matching job
* B-45516 Tech Debt: FDA: Add logback-test.xml without jmxConfigurator
* B-45516 Tech Debt: FDA: Adjust logback configuration: include logback-webapp.xml from rup-common in logback config to have jmxConfigurator, add logger com.copyright.* classes, refine JMS log level setup

2.1.59
- B-45018 Tech Debt: FDA: update dist-common version

2.1.58
-
* B-45018 Tech Debt: FDA: add configuration for root log level
* B-45018 Tech Debt: FDA: add log level configuration for PRM integration
* B-45018 Tech Debt: FDA: apply new index for PI integration

2.1.57
-
* B-45018 Tech Debt: LM: Add configurable loggers for non-CCC JMS related logic for debug needs
* B-45018 Tech Debt: adjust consuming of paid information to consume check_date, distribution_date and period_end_date either in ISO or in long format

2.1.56
-
* RDSC-611 FDA: Service Fee True-up Report miss data if sending date to LM and 'To Date' are equal

2.1.55
-
* B-45018 Tech Debt: FDA: Use 16.+ version of dist-common

2.1.54
-
* RDSC-604 FDA: Exception while creating scenario in case incorrect PRM configurations

2.1.53
-
* B-44722 FDA: Service Fee True-up Report: fix query, clean up code for undistributed liabilities reconciliation report

2.1.52
-
* B-44722 FDA: Service Fee True-up Report: add integration test
* B-44722 FDA: Service Fee True-up Report: remove clear buttons from date widgets

2.1.51
-
* B-44722 FDA: Service Fee True-up Report: add repository and query to generate report
* B-45018 Tech Debt: FDA: Fix ActiveMQ configuration issue related to preconfigured PooledConnectionFactory not being used

2.1.50
-
* B-42074 FDA: Upgrade FDA RUP ES API Integration with new Elastic Search version: update rupEsApi to 9+ version and configure cluster, node and index for PI integration
* B-43662 Tech Debt: FDA: remove redundant v-label-white-space-normal CSS definition
* B-44722 FDA: Service Fee True-up Report: add ui to generate report, change order of items in reports menu

2.1.49
-
* B-43662 Tech Debt: FDA: adjust PI integration service log in case of multiply results
* B-43662 Tech Debt: FDA: update dist-common version to 15, apply HealthCheckSetupContextListener, remove HealthCheckFilter
* B-44370 LM: Performance improvements of critical areas: Increase version of disc-common dependency
* B-44370 LM: Performance improvements of critical areas: Make changes based on comments in CR-DIST-FOREIGN-66
* B-44723 FDA: Update Undistributed Liabilities Report: adjust sql query for data selecting data and report handler for report generation
* RDSC-600 FDA: RHs discrepancies are not cleared from the table if reconciliation process is interrupted

2.1.48
-
* B-43662 Tech Debt: FDA: add pagination to export usages on Usages and Audit tabs and View Scenario window to resolve out of memory exception

2.1.47
-
* B-43661 Tech Debt: FDA: fix style name and sorting for Type column in Usage History window
* B-43662 Tech Debt: FDA: add rounding to two decimal places for all reports
* B-43662 Tech Debt: FDA: adjust data ordering for Batch Summary and Research Status reports
* B-44370 LM: Performance improvements of critical areas: Unify discrepancy creation functionality in FDA and LM
* B-44370 LM: Performance improvements of critical areas: Update Spring configuration after removing class RmsIntegrationService

2.1.46
-
* B-43661 Tech Debt: FDA: make changes based on comments in CR-DIST-FOREIGN-65
* B-43661 Tech Debt: FDA: use RupContextUtils and PerformanceAspect from dist-common, clean-up redundant profiled annotations
* B-43863 FDA: Modify matching to target IDNO Type: make changes based on comments in CR-DIST-FOREIGN-64
* RDSC-596 FDA: Right assignment job fails for usages with WrWrkInst length > 9 symbols
* RDSC-600 FDA: RHs discrepancies are not cleared from the table if reconciliation process is interrupted

2.1.45
-
* B-43661 Tech Debt: FDA: Apply JobRunnerRestFilter from dist-common
* B-43661 Tech Debt: FDA: change status from LOCKED to SENT TO LM for usages that were sent to LM
* B-43863 FDA: Modify matching to target IDNO Type: implement logic to match works by IDNO and mainTitle only for usages with the same workTitle in case of multiply titles for single IDNO

2.1.44
-
* B-30192 FDA: Batch Summary Report (DVPR Replacement): make changes based on comments in CR-DIST-FOREIGN-62
* B-43661 Tech Debt: FDA: Add date-time info to exported scenario usages file name
* B-43661 Tech Debt: FDA: remove unused com.copyright.rup.dist.common.integration logger from logback configuration
* B-43661 Tech Debt: FDA: remove unused logLevelIntegrationCommon log level variable from logback configuration
* B-43863 FDA: Modify matching to target IDNO Type: adjust service logic to match by IDNO Types in case of multiple matching results
* RDSC-597 FDA: Scenario status doesn't changed to ARCHIVED if details were sent to CRM in different time

2.1.43
-
* B-30192 FDA: Batch Summary Report (DVPR Replacement): Implement view to retrieve data for report
* B-30192 FDA: Batch Summary Report (DVPR Replacement): make changes on UI and add service logic and handler to generate report
* B-43639 FDA: Research Status Report: implement handler and repository logic to generate report
* B-43639 FDA: Research Status Report: implement view to store report data, implement ui part and integration test to cover report generation
* B-43660 Tech Debt: FDA: clean-up release notes and remove revving up build version and release notes related commits
* B-43661 Tech Debt: FDA: Refine JobRunnerRestFilter Rest API to send job status in response body
* B-43661 Tech Debt: FDA: Refine export functionality to adjust file name of exported file
* B-43661 Tech Debt: FDA: add expectations of rest template error handler modifying to RmsRightAssignmentServiceTest test
* B-43661 Tech Debt: FDA: replace topic with virtual topic
* B-43661 Tech Debt: FDA: update dist-common version to 14.+
* B-43661 Tech Debt: FDA: update rup-postgres plugins common scripts version to 2.1.0
* B-44096 FDA: Address performance issues related to export and scenario functionality: improve performance of usages filtering by batch
* RDSC-595 FDA: PI job: Product family is set incorrectly for details with the same Standard Number but with different product family

2.1.42
-
* B-43661 Tech Debt: FDA: update dependencies to use rup-plugin 5.+
* B-43661 Tech Debt: FDA: update jackson-module-jaxb-annotations version to rup-camel-jackson version to avoid CVE-2016-7051
* B-44096 FDA: Address performance issues related to export and scenario functionality: Add implementation of checking scenario emptiness for scenarios in states SENT_TO_LM, ARCHIVED

2.1.41
-
* B-37305 FDA: undistributed liabilities reconciliation report: make changes based on comments in CR-DIST-FOREIGN-61
* B-43660 Tech Debt: FDA: add index by df_scenario_uid in df_usage and df_usage_archive tables
* B-43660 Tech Debt: FDA: apply DistCsvProcessor and BaseCsvReportHandler from dist-common
* B-43660 Tech Debt: FDA: apply HealthCheckFilter
* B-43660 Tech Debt: FDA: fix failing build
* B-44096 FDA: Address performance issues related to export and scenario functionality: Improve performance of checking scenario emptiness
* RDSC-590 FDA: Usage details are displayed as blank during scrolling Usage Batch with a lot of details: show loading indicator during tables scrolling

# foreign-distribution-application
