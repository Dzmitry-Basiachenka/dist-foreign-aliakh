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
