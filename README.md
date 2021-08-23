14.1.10
-
* CDP-1010: FDA: ACL: Multiple Edit: Reported Standard Number and Reported Title are cleared for edited usages
* CDP-1009 FDA: EDIT UDM Usage: Exception occurs when changing populated Action reason and Ineligible reason fields to empty value
* B-68058: FDA: Log and display single usage edits in the UDM Audit: implement logic to log usage assignment
* B-68820: FDA: Tech Debt: FDA: add OPS_REVIEW and SPECIALIST_REVIEW statuses to UDM filters
* B-68820: FDA: Tech Debt: FDA: disable UDM Edit buttons on click to avoid message duplication
* B-68058: FDA & UDM: Log and display single usage edits in the UDM Audit: adjust UdmAuditFieldsToValueMap to store strings

14.1.9
-
* B-68058: FDA & UDM: Log and display single usage edits in the UDM Audit: implement logic to log single usage edit
* CDP-1008: FDA: ACL: Multiple Edit: Researcher is able to edit usages in forbidden statuses if at least one usage in allowed status is selected additionally
* B-65870: FDA & UDM: Bulk update: implement service logic to update usages
* B-65870: FDA & UDM: Bulk update: implement update usages logic for researcher role
* B-65870: FDA & UDM: Bulk update: implement update usages logic for specialist/manager roles
* B-67678: FDA & UDM: Usage Period update: retrieve periods from usages to display on UDM period filter

14.1.8
-
* B-65870: FDA & UDM: Bulk update: implement validation for fields on UDM multiple edit windows
* B-65864: FDA: Update the usage information for Researcher role: unassign the usage from a user when the user updates usage to NEW status
* B-68820: Tech Debt: FDA: apply new pattern for local SNS/SQS properties

14.1.7
-
* B-65870: FDA & UDM: Bulk update: add Multiple Edit button to UDM tab
* B-65870: FDA & UDM: Bulk update: implement Multiple Edit UDM Usages window for researcher role
* B-65870: FDA & UDM: Bulk update: implement Multiple Edit UDM Usages window for manager/specialist roles
* B-65864: FDA: Update the usage information for Researcher role: implement opening for edit only UDM usages in statuses WORK_NOT_FOUND, RH_NOT_FOUND
* B-65864: FDA: Update the usage information for Researcher role: implements saving UDM usages only in statuses <original status>, OPS_REVIEW, SPECIALIST_REVIEW, NEW
* B-65864: FDA: Update the usage information for Researcher role: implement Edit UDM usage dialog
* B-67678: FDA & UDM: Usage Period update - tech story: update backend logic to display and filter by period using df_udm_usage table
* B-67678: FDA & UDM: Usage Period update - tech story: introduce and populate period column in df_udm_usage table
* B-68059: FDA & UDM: Change rules for getting ACL rights from RH's: change name of ACL (UDM) product family to ACL_UDM in order to have ability use it in placeholders in qa-fda
* B-68059: FDA & UDM: Change rules for getting ACL rights from RH's: modify logic for getting grants
* B-68059: FDA & UDM: Change rules for getting ACL rights from RH's: apply 54.1.+ version of dist-common
* B-67321: Tech Debt: FDA: fix issue related to inconsistent state exception
* B-67321: Tech Debt: FDA: update rup-vaadin version
* B-67321: Tech Debt: FDA: move logic to check processed usages from Edit UDM Window to UDM Usage Window
* B-67321: Tech Debt: FDA: Increase column size of df_usage_audit.action_reason, df_udm_audit.action_reason columns
* B-67321: Tech Debt: FDA: remove redundant fiscal_year populating in test data
* B-67321: Tech Debt: FDA: clean-up test data for CSV reports integration tests
* B-67321: Tech Debt: FDA: adjust test data initialization in json files to avoid quotes for integer values on repository/service layer
* B-67321: Tech Debt: FDA: clear standard_number, system_title if work was not found after Edit on UDM tab

14.1.6
-
* B-67321 Tech Debt: FDA: handle low timeouts during processing FAS usages in status NEW
* B-67321: FDA: implement logic to check is usage processing completed before edit can be applied
* B-67321: Dist - Tech Debt: FDA: edit window annualized copies calculation is incorrect for large numbers
* B-67321 Tech Debt: FDA: handle low timeouts during processing SAL usages in status NEW
* B-67321 Dist - Tech Debt: FDA: adjust test data initialization in groovy files to avoid quotes for integer values on repository/service layer
* B-67321 Tech Debt: FDA: fix Checkstyle error

14.1.5
-
* CDP-1003 Dynamic CAS Service URL (Foreign Distributions)
* B-67321 Tech Debt: FDA: fix embedded tomcat security vulnerability CVE-2021-33037

14.1.4
-
* CDP-1006 FDA: Export ACL Usages: data is not exported correctly if at least one usage with null annualized copies value is present in filtered data
* B-67328 Tech Debt: FDA: decrease user log-in time by skipping a redundant service call

14.1.3
-
* CDP-1000 FDA: Edit UDM usage: Usages processing in case of setting NEW status is unstable
* CDP-1001 FDA: Edit UDM usage: Exception occurs after setting Annual or Statistical multiplier values as 1000 or more
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: make changes based on comments in CR-DIST-FOREIGN-249
* B-67328 Dist - Tech Debt: FDA: fix to avoid using deprecated addListener() method

14.1.2
-
* CDP-1000 FDA: Edit UDM usages: Usages processing in case of setting NEW status is unstable
* CDP-999 FDA: Edit UDM usage: Exception occurs if a user makes the Wr Wrk Inst blank and tries to save the changes
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: apply refresh window only after Edited usage was saved
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: add id values for editable fields on Edit Usage Window
* B-67328 Tech Debt: FDA: fix expiration time equals null in PrmCountryCacheService
* B-67543 FDA: 'Action Reason' and 'Ineligible Reason' columns in DB: make changes based on comments in CR-DIST-FOREIGN-243
* B-57779 FDA& UDM: View and Delete UDM Loaded Batches: make changes based on comments in CR-DIST-FOREIGN-246

14.1.1
-
* B-577779: FDA& UDM: View and Delete UDM Loaded Batches: fix disable delete button when use unselect  batch
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: apply annualized copies calculation after multipliers or quantity were changed on Edit window
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: apply permissions for Edit UDM usage button
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: apply validation for company ID field using verify button on Edit window
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: enable editing logic only for assigned for user UDM usages
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: implement logic to process NEW usages in PI after edit was applied
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: implement logic to store updated usage to database
* B-66132 FDA & UDM: Export functionality for UDM: make changes based on comments in CR-DIST-FOREIGN-245
* B-66422 FDA & UDM: Assign/Un-assign usages for research: make changes based on comments in CR-DIST-FOREIGN-247
* B-66422 FDA & UDM: Assign/Un-assign usages for research: make unassignment allowed only for usages that are assigned to current user
* B-67328 Tech Debt: FDA: fix security vulnerability CVE-2021-22118
* B-68188: FDA&UDM: Enable IP Address visible for Specialist: exclude ip adress column visible for view only role

14.1.0
-
* B*57779: FDA& UDM: View and Delete UDM Loaded Batches: implement query to delete UDM usaga batch details
* B-57779: FDA& UDM: View and Delete UDM Loaded Batches: implement View window for UDM batches
* B-57779: FDA& UDM: View and Delete UDM Loaded Batches: implement controller logic related to delete UDM batch
* B-57779: FDA& UDM: View and Delete UDM Loaded Batches: implement logic to delete UDM batch
* B-57779: FDA& UDM: View and Delete UDM Loaded Batches: implement query to check that batch processing is completed
* B-57779: FDA& UDM: View and Delete UDM Loaded Batches: implement query to delete UDM usage audit
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: add research URL column on UI for UDM usages grid
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: add research URL column to df_udm_usage table
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: bind read only fields to selected UDM usage on Edit window
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: implement initial edit UDM usage window
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: implement plain validation rules for editable fields on UDM edit usage window
* B-65865 FDA & UDM: update the usage information - Specialist/Manager role: populate comboBox fields on edit UDM usage window
* B-66132 FDA & UDM: Export functionality for UDM: add export button on UI
* B-66132 FDA & UDM: Export functionality for UDM: adjust controller logic with methods for getting export UDM usages stream source for each role
* B-66132 FDA & UDM: Export functionality for UDM: fix detail licensee class fields in UDM usage handlers
* B-66132 FDA & UDM: Export functionality for UDM: implement UdmReportRepository logic for csv export of UDM usages
* B-66132 FDA & UDM: Export functionality for UDM: implement UdmReportService logic for csv export of UDM usages
* B-66132 FDA & UDM: Export functionality for UDM: implement csv report handlers for each role
* B-66422 FDA & UDM: Assign/Un-assign usages for research: add permission and menu to allow assignment/un-assignment of usages
* B-66422 FDA & UDM: Assign/Un-assign usages for research: adjust UDM usages widget to assign/unassign usages and remove permission for assignment
* B-66422 FDA & UDM: Assign/Un-assign usages for research: implement service and repository logic
* B-66422 FDA & UDM: Assign/Un-assign usages for research: make usages selection role specific
* B-67203 FDA: 'Action Reason' column on the UI: add column Action Reason to UDM usages table
* B-67203 FDA: 'Action Reason' column on the UI: add sorting by column Action Reason in UDM usages table
* B-67328 Tech Debt: FDA: fix security vulnerabilities CVE-2019-25028, CVE-2021-31403, CVE-2021-31409
* B-67543 FDA: 'Action Reason' and 'Ineligible Reason' columns in DB: implement Liquibase script to create df_udm_action_reason, df_udm_ineligible_reason tables
* B-67543 FDA: 'Action Reason' and 'Ineligible Reason' columns in DB: implement service methods to read Action Reasons and Ineligible Reasons
* B-67543 FDA: 'Action Reason' and 'Ineligible Reason' columns in DB: refactor backend to use df_udm_action_reason, df_udm_ineligible_reason tables
* B-68188: FDA&UDM: Enable IP Address visible for Specialist: adjust logic related to visible ip address column for specialist role on UDM tab

13.1.35
-
* B-66635 FDA & UDM: Create UDM shell - UI Filtering - Part 2: update validation error message for decimal multiplier fields
* CDP-994 FDA: Audit in UDM: Audit for matching by host idno contains reported standard number instead of found host idno

13.1.34
-
* B-65859 FDA: UDM in ACL Audit: make changes based on comments in code review
* CDP-993 FDA: UDM tab: Search results are not cleaned up after uploading a new batch

13.1.33
-
* B-65859 FDA: UDM in ACL Audit: make changes based on comments in code review
* B-65860: FDA & UDM: Role-specific UDM view: make changes based on comments in CR-DIST-FOREIGN-237
* B-66635 FDA & UDM: Create UDM shell - UI Filtering - Part 2: make changes based on comments in CR-DIST-FOREIGN-240
* B-67395 FDA & UDM: Create UDM shell - UI Search: make changes based on comments in CR-DIST-FOREIGN-241

13.1.32
-
* B-65859 FDA: UDM in ACL Audit: implement adding audit records during background processing steps
* B-65860: FDA & UDM: Role-specific UDM view: make changes based on comments in CR-DIST-FOREIGN-237
* B-66635 FDA & UDM: Create UDM shell - UI Filtering - Part 2: apply trim for additional UDM filters
* B-66635 FDA & UDM: Create UDM shell - UI Filtering - Part 2: disable Usage Origin filter for Researcher role
* B-67459: FDA & UDM: Validate Uploaded Country: make changes based on comments in CR-DIST-FOREIGN-239

13.1.31
-
* B-67395 FDA & UDM: Create UDM shell - UI Search: adjust sql queries for getting by UDM usage filter with searchValue
* B-66635 FDA & UDM: Create UDM shell - UI Filtering - Part 2: implement logic to restore applied filter after filter window was closed

13.1.30
-
* B-66635 FDA & UDM: Create UDM shell - UI Filtering - Part 2: Adjust repository logic to load usages by new filters
* B-66635 FDA & UDM: Create UDM shell - UI Filtering - Part 2: apply backend logic to populate filters
* B-66635 FDA & UDM: Create UDM shell - UI Filtering - Part 2: apply permissions for UDM additional filters
* B-66635 FDA & UDM: Create UDM shell - UI Filtering - Part 2: implement logic to populate applied usage filter
* B-66635 FDA & UDM: Create UDM shell - UI Filtering - Part 2: implement validation rules for additional UDM filters
* B-67395 FDA & UDM: Create UDM shell - UI Search: add  search widget to UDM usage tab
* B-67395 FDA & UDM: Create UDM shell - UI Search: add Comment column on UI
* B-67395 FDA & UDM: Create UDM shell - UI Search: add comment field to UdmUsage, UdmUsageDto
* B-67395 FDA & UDM: Create UDM shell - UI Search: add search value field to UdmUsageFilter
* B-67459: FDA & UDM: Validate Upload Country: fix validator test
* B-67459: FDA & UDM: Validate Uploaded Country: implement business validation

13.1.29
-
* B-65859 FDA: UDM in ACL Audit: implement widget to show audit table
* B-65860: FDA & UDM: Role-specific UDM view: adjust logic related to hidden columns based on Permissions on UDM Usage Tab
* B-65860: FDA & UDM: Role-specific UDM view: adjust logic related to hide all product families excepting ACL and hide all tabs excepting UDM for Researcher role and set visible upload button for spesialist role
* B-66635 FDA & UDM: Create UDM shell - UI Filtering - Part 2: adjust logic to load Detail LCs
* B-66635 FDA & UDM: Create UDM shell - UI Filtering - Part 2: implement logic to load assignees
* B-66635 FDA & UDM: Create UDM shell - UI Filtering - Part 2: implement logic to load publication types and formats
* B-66635 FDA & UDM: Create UDM shell - UI Filtering: implement filter widget for additional UDM filters
* B-67395 FDA & UDM: Create UDM shell - UI Search: add comment column to df_udm_usage table
* clipboardPatchFile

13.1.28
-
* B-54393 FDA: Modify/Update AACL licensee classes: fix LicenseeClassRepositoryIntegrationTest
* B-54393 FDA: Modify/Update AACL licensee classes: implement liquibase script to add AACL licensee classes for EXU2 enrollment profile
* B-65859 FDA: UDM in ACL Audit: implement Liquibase script to create df_udm_audit table
* B-65859 FDA: UDM in ACL Audit: implement service and repository
* B-65859 FDA: UDM in ACL Audit: rename foreign key constraint for df_udm_audit table
* B-65860: FDA & UDM: Role-specific UDM view: add Researcher role and add permissions for new and current roles
* B-65860: FDA & UDM: Role-specific UDM view: add new FDA_RESEACHER_PERMISSION, FDA_MANAGER_PERMISSION, FDA_SPECIALIST_PERMISSION and FDA_VIEW_ONLY_PERMISSION permissions to security utils
* B-66635 FDA & UDM: Create UDM shell - UI Filtering: implement window with additional UDM filters
* B-67327 Tech Debt: FDA: remove quotes from integer values for product family specific fields
* B-67327: Tech Debt: FDA: CsvReportsIntegrationTest: separate by product family

13.1.27
-
* B-66633 FDA & UDM: PI matching for newly uploaded ACL usages: make changes based on comments in CR-DIST-FOREIGN-235
* B-66633 FDA & UDM: PI matching for newly uploaded ACL usages: make changes based on comments in CR-DIST-FOREIGN-235
* B-66634 FDA & UDM: RMS Rights: FDA: make changes based on comments in CR-DIST-FOREIGN-236
* B-66754 FDA & UDM: Pull Company Information from Telesales: make changes based on comments in CR-DIST-FOREIGN-233
* B-67105 FDA & UDM: UDM TOU Mapping: make changes based on comments in CR-DIST-FOREIGN-232
* B-67327 Tech Debt: FDA: remove quotes from integer values for FAS product family
* B-67327 Tech Debt: FDA: remove quotes from integer values for amount fields
* B-67327 Tech Debt: FDA: remove quotes from integer values for batch related fields
* Update release notes and revving up build version for 13.1.26

13.1.26
-
* CDP-992 FDA: df.service.getRightsQuartzJob: WORK_FOUND UDM usages are not processed by getRights job
* B-67327 Tech Debt: FDA: remove quotes from integer values for rightsholder related fields
* B-67327 Tech Debt: FDA: refactor integration tests to remove redundant code and 'chunk' in names
* B-67327 Tech Debt: FDA: remove quotes from integer values for aacl usage repository integration test
* B-67327 Tech Debt: FDA: revert fix of vulnerability CVE-2021-22112 associated with dependency org.springframework.security
* B-67327 Tech Debt: FDA: refactor usage processing to remove redundant code and 'chunk' in names
* B-66754 FDA & UDM: Pull Company Information from Telesales: implement TelesalesCacheService to get company information

13.1.25
-
* B-65859 FDA: UDM normalize annual multiplier: calculate annualized copies after uploading UDM usages
* B-66633 FDA & UDM: PI matching for newly uploaded ACL usages: implement job configuration for UDM usages
* B-66634 FDA & UDM: RMS Rights: fix failed build
* B-66634 FDA & UDM: RMS Rights: implement background job
* B-66634 FDA & UDM: RMS Rights: implement logic for getting rights
* B-66754 FDA & UDM: Pull Company Information from Telesales: drop df_company table
* B-66754 FDA & UDM: Pull Company Information from Telesales: populate company information for UDM usages and display on UI
* B-67327 Dist - Tech Debt: FDA: fix vulnerability CVE-2021-22112 associated with dependency org.springframework.security
* FDA & UDM: Pull Company Information from Telesales: fix LoadUdmUsagesIntegrationTest

13.1.24
-
* B-65858 FDA & UDM: Load ACL usage data: make changes in comments in CR-DIST-FOREIGN-230
* B-67378 FDA & UDM: Detail/Aggregate licensee mapping: implement liquibase script to add description and product_family columns into df_aggregate_licensee_class  and df_detail_licensee_class tables
* B-66633: FDA & UDM: PI matching for newly uploaded ACL usages: add WORK_FOUND and WORK_NOT_FOUND statuses to filter widget and implement update processed usages logic
* B-67378 FDA & UDM: Detail/Aggregate licensee mapping: insert ACL licensee classes mapping into df_aggregate_licensee_class and df_detail_licensee_class tables
* B-66634 FDA & UDM: RMS Rights: refactor chain executor to support UDM usages
* B-67105 FDA & UDM: UDM TOU Mapping: create df_udm_tou_mapping table
* B-66754 FDA & UDM: Pull Company Information from Telesales: implement liquibase script to create df_company table
* B-66633 FDA & UDM: PI matching for newly uploaded ACL usages: refine work matching service to support UDM usages
* B-65859 FDA: UDM normalize annual multiplier: calculate annual multiplier after uploading UDM usages
* B-66634 FDA & UDM: RMS Rights: refactor processors to support UDM usages and implement repository and service logic that will be used by UDM processors
* B-67105 FDA & UDM: UDM TOU Mapping: implement repository layer for UDM TOU mapping
* B-66633 FDA & UDM: PI matching for newly uploaded ACL usages: implement matching consumer for UDM usages
* B-67105 FDA & UDM: UDM TOU Mapping: implement service layer for UDM TOU mapping
* B-66634 FDA & UDM: RMS Rights: implement serializer and deserializer for UDM usages
* B-66754 FDA & UDM: Pull Company Information from Telesales: add getCompanyInformation method to TelesalesService
* B-66634 FDA & UDM: RMS Rights: Add RH_FOUND and RH_NOT_FOUND statuses to the filter
* B-66633 FDA & UDM: PI matching for newly uploaded ACL usages: introduce common UDM usage producer and apply it for matching processor
* B-66754 FDA & UDM: Pull Company Information from Telesales: implement CompanyIdValidator
* B-66634 FDA & UDM: RMS Rights: implement processor and consumer for getting rights
* B-67105 FDA & UDM: UDM TOU Mapping: implement validator for reported type of uses
* B-66633 FDA & UDM: PI matching for newly uploaded ACL usages: introduce UDM matching queue to get work information
* B-66633 FDA & UDM: PI matching for newly uploaded ACL usages: implement UDM work information validator
* B-66754 FDA & UDM: Pull Company Information from Telesales: apply CompanyIdValidator on UDM usages upload step

13.1.23
-
* B-65858 FDA & UDM: Load ACL usage data: make changes in comments in CR-DIST-FOREIGN-230
* B-65858: FDA & UDM: Load ACL usage data: revert removed code
* B-67103: FDA & UDM: Create UDM shell - UI Filtering - Part 1: make changes based on comments in CR-DIST-FOREIGN-229
* B-67103: FDA & UDM: Create UDM shell - UI Filtering - Part 1: reset filter after loading udm batch
* CDP-981 FDA: Upgrade to Gradle 4 / RUP Gradle Plugins 6: apply RUP Common 12 version
* CDP-990 FDA: Upload UDM usage batch: fix validation errors
* CDP-990 FDA: Upload UDM usage batch: fix validations

13.1.22
-
* B-66740 FDA: DB changes for UDM: add assignee column to df_udm_usage table
* B-64613 FDA & UDM: Create UDM shell - UI: introduce Assignee column on UI, apply common pattern for date columns
* B-65858: FDA & UDM: Load ACL usage data: implement logic related to is batch name exists
* B-67325 Tech Debt: FDA: add derby.log and styles.css.gz files to gitignore
* B-65858 FDA & UDM: Load ACL usage data: implement default value for annual multiplier

13.1.21
-
* B-64613 FDA & UDM: Create UDM shell - UI: make changes based on comments in CR-DIST-FOREIGN-228
* B-64613 FDA & UDM: Create UDM shell - UI: remove sorting from licensee classes columns
* B-65858 FDA & UDM: Load ACL usage data: implement integration test to cover uploading of UDM usages
* B-65858: FDA & UDM: Load ACL usage data: implement upload udm usages logic
* B-67103: FDA & UDM: Create UDM shell - UI Filtering - Part 1: apply udm filter to select udm usages
* B-67103: FDA & UDM: Create UDM shell - UI Filtering - Part 1: fix failed UdmUsageRepositoryIntegrationTest.testFindDtosByFilter
* B-67103: FDA & UDM: Create UDM shell - UI Filtering - Part 1: implement repository layer changes for UDM filters
* B-67103: FDA & UDM: Create UDM shell - UI Filtering - Part 1: implement service layer changes for UDM filters
* B-67325 Tech Debt: FDA: fix the bug of not changing tabs from Usages to Scenarios after creating a scenario
* B-67325 Tech Debt: FDA: update commons-io to 2.7 version

13.1.20
-
* B-64613 FDA & UDM: Create UDM shell - UI: implement backend logic to retrieve usages to display
* B-65858 FDA & UDM: Load ACL usage data: implement validators for original detail id, survey dates and quantity
* B-65858: FDA & UDM: Load ACL usage data: fix checkstyle issue
* B-65858: FDA & UDM: Load ACL usage data: implement UDM usage CSV processor and  logic related to download error file if upload was failed
* B-66740 FDA: DB changes for UDM: update statistical_multiplier, annualized_copies fields
* B-67103: FDA & UDM: Create UDM shell - UI Filtering - Part 1: implement UDM filters widget

13.1.19
-
* B-64613 FDA & UDM: Create UDM shell - UI: initial UI implementation for ACL UDM usages
* B-64613 FDA & UDM: Create UDM shell - UI: introduce UdmUsageDto domain object and apply it for UDM usages grid
* B-65858 FDA & UDM: Load ACL usage data: implement repository for UDM usage batch
* B-65858 FDA & UDM: Load ACL usage data: implement repository for UDM usages
* B-65858 FDA & UDM: Load ACL usage data: implement services for UDM batch and usages
* B-65858: FDA & UDM: Load ACL usage data: implement domain for UDM usage and UDM usage batch
* B-65858: FDA & UDM: Load ACL usage data: implement upload window
* B-66740 FDA: DB changes for UDM: add survey_email column to df_udm_usage table
* B-66740 FDA: DB changes for UDM: implement Liquibase script to create df_udm_usage_batch, df_udm_usage tables
* B-66740 FDA: DB changes for UDM: make changes based on comments in CR-DIST-FOREIGN-227
* B-66740 FDA: DB changes for UDM: remove not null constraint for df_udm_usage.statistical_multiplier column
* Revert "B-66740 FDA: DB changes for UDM: add survey_email column to df_udm_usage table"
* Update release notes and Revving up build version for version 13.1.18

13.1.18
-
* CDP-981 - Fix empty manifest in war file issue

13.1.17
-
* B-66932 Tech Debt: FDA: update postgres common scripts version for party db to 4.0.0

13.1.16
-
* CDP-981 Please upgrade to Gradle 4, RUP Gradle Plugins 6
* B-65633 Tech Debt: FDA: fix CVE-2021-25329 vulnerability issue
* B-64714 FDA: Show user friendly message on batch deletion in processing mode: make changes based on comments in CR-DIST-FOREIGN-226

13.1.15
-
* B-64714 FDA: Show user friendly message on batch deletion in processing mode: implement notification window to prevent deleting of in progress batches
* B-64714 FDA: Show user friendly message on batch deletion in processing mode: implement backend logic to identifying batch completion status
* B-65718 FDA: Batch Processing View: make changes based on comments in CR-DIST-FOREIGN-225

13.1.14
-
* CDP-959 FDA: Batch status tab: Usage batch is displayed on Batch status tab after sending scenario containing this usage batch to LM
* B-65718 FDA: Batch Processing View: add NON_STM_RH and US_TAX_COUNTRY statuses to NTS batch status
* B-65718 FDA: Batch Processing View: adjust logic to get Usage Batches for Batch Status tab based on number of days property
* B-65718 FDA: Batch Processing View: populate initial_usages_count column for test data on service layer
* B-65718 FDA: Batch Processing View: populate initial_usages_count column for test data on repository layer
* B-65718 FDA: Batch Processing View: introduce empty style name for batch status table

13.1.13
-
* B-65718 FDA: Batch Processing View: implement backend logic to get batch status for AACL product family
* B-65718 FDA: Batch Processing View: implement backend logic to get batch status for SAL product family
* B-65718 FDA: Batch Processing View: update upload batch functionality to store initial usages count for AACL/SAL product families

13.1.12
-
* B-65718 FDA: Batch Processing View: implement backend logic to get batch status for NTS product family
* B-65718 FDA: Batch Processing View: make number of days as configurable property for getting Batch Status information
* B-65718 FDA: Batch Processing View: update upload batch functionality to store initial usages count for FAS/FAS2 product families
* B-65718 FDA: Batch Processing View: update upload batch functionality to store initial usages count for NTS product family

13.1.11
-
*  B-65718 FDA: Batch Processing View: implement Batch Status widget for FAS
* B-65718 FDA: Batch Processing View: apply logic to retrieve batches not associated with scenarios for provided start date
* B-65718 FDA: Batch Processing View: fix Checkstyle issue
* B-65718 FDA: Batch Processing View: implement Batch Status tab on UI
* B-65718 FDA: Batch Processing View: implement Batch Status widget for AACL and SAL
* B-65718 FDA: Batch Processing View: implement Batch Status widget for NTS
* B-65718 FDA: Batch Processing View: implement Usage batch status service

13.1.10
-
* B-65718 FDA: Batch Processing View: implement usage batch status repository layer for FAS product family

13.1.9
-
* B-64694 FDA: Update CCC logo: update RUP-VAADIN to 9.0.+ version
* B-65718 FDA: Batch Processing View: implement liquibase script to add usages_count column for df_usage_batch table
* B-65718 FDA: Batch Processing View: introduce UsageBatchStatus domain object

13.1.8
-
* B-58402 FDA:  Add ability to search by Payee Name or Payee Acct #: adjust service logic to search by Payee Name and Account #
* B-58402 FDA: Add ability to search by Payee Name or Payee Acct #: adjust search prompt for Scenario view
* B-64632 FDA: Edit Scenario name: make changes based on comments in CR-DIST-FOREIGN-223

13.1.7
-
* B-64632 FDA: Edit Scenario name: implement service logic to update scenario name

13.1.6
-
* B-57881 Tech Debt: FDA: fixing a year formatting error in files' names
* B-64632 FDA: Edit Scenario name: apply permissions for Edit scenario name action
* B-64632 FDA: Edit Scenario name: implement window for edit scenario name
* B-64632 FDA: Edit Scenario name: introduce Edit name button for all product families

13.1.5
-
* B-59009 FDA: Update SAL usages with distribution data from LM: make changes based on comments in CR-DIST-FOREIGN-221
* B-63991 FDA: Audit Tab Export for SAL: implement repository for report
* B-63991 FDA: Audit Tab Export for SAL: implement service for report
* B-57881 Tech Debt: FDA: unify look and feel of filter widgets

13.1.4
-
* B-63961 FDA: Audit Tab modifications for SAL: make changes based on comments in CR-DIST-FOREIGN-220

13.1.3
-
* B-59009 FDA: Update SAL usages with distribution data from LM: fix null SalUsage when insert of post distribution usage

13.1.2
-
*  B-59009 FDA: Update SAL usages with distribution data from LM: implement service logic and adjust paid consumer to update SAL paid usages

13.1.1
-
* B-63961 FDA: Audit Tab modifications for SAL: implement repositories to search and filter audit records
* B-56615 FDA: Reporting SAL to RC: make changes based on comments in CR-DIST-FOREIGN-219
* B-63961 FDA: Audit Tab modifications for SAL: adjust widths of the grid id columns
* B-60056 FDA: Enhancements for additional Fund Pool view: make changes based on comments in CR-DIST-FOREIGN-218

13.1.0
-
* B-56615 FDA: Reporting SAL to RC: Adjust reporting to RC to report SAL
* B-56615 FDA: Reporting SAL to RC: adjust repository logic to retrieve SAL usage data
* B-56615 FDA: Reporting SAL to RC: fix failed build
* B-58562 FDA: Update the item bank detail for denied SAL usage exceptions: make changes based on comments in CR-DIST-FOREIGN-217
* B-59009 FDA: Update SAL usages with distribution data from LM: implement logic to insert SAL paid usages
* B-59009 FDA: Update SAL usages with distribution data from LM: implement repository logic related to get usages for updating paid information
* B-60056 FDA: Enhancements for additional Fund Pool view: add Comment column to View NTS Pre-Service Fee Funds modal window
* B-60056 FDA: Enhancements for additional Fund Pool view: make Comments field mandatory and rename to Comment on Create NTS Pre-Service Fee Funds window
* B-60056 FDA: Enhancements for additional Fund Pool view: make changes based on comments in CR-DIST-FOREIGN-218
* B-60056 FDA: Enhancements for additional Fund Pool view: rename Delete menu item and Delete NTS Pre-Service Fee Funds modal window for additional Fund Pool
* B-62520 Tech Debt: FDA: fix myBatis vulnerability CVE-2020-26945
* B-62520 Tech Debt: FDA: fix security vulnerability CVE-2020-17527
* B-62520 Tech Debt: FDA: migration to RUP Common 11
* B-63961 FDA: Audit Tab modifications for SAL: implement Audit filter widget
* B-63961 FDA: Audit Tab modifications for SAL: implement Audit tab
* B-63961 FDA: Audit Tab modifications for SAL: implement Audit table widget
* B-63961 FDA: Audit Tab modifications for SAL: implement Licensee multi-select widget
* B-63961 FDA: Audit Tab modifications for SAL: implement services to search and filter audit records

12.1.31
-
* B-62520 Tech Debt: FDA: change data type for reported_publication_date

12.1.30
-
* B-58562 FDA: Update the item bank detail for denied SAL usage exceptions: adjust audit for IB details for RH update action
* B-58562 FDA: Update the item bank detail for denied SAL usage exceptions: trim extra spaces for RH Account # field on Update Rightsholder modal window
* CDP-955 FDA: SAL: Update Rightsholder: RH name is not displayed on usages tab when updated RH account number is absent in the database

12.1.29
-
* B-58562 FDA: Update the item bank detail for denied SAL usage exceptions: remove redundant changes for isValidFilteredUsageStatus method
* B-61833 FDA: SAL historical item bank detail report: make changes based on comments in CR-DIST-FOREIGN-216
* CDP-953 FDA: SAL: Update Rightsholders: 'null' is displayed instead of RH account number in the message on 'Confirm action' window

12.1.28
-
* B-58562 FDA: Update the item bank detail for denied SAL usage exceptions: apply service logic to update RH for selected detail and add validation that status filter is applied on Usages tab
* B-58562 FDA: Update the item bank detail for denied SAL usage exceptions: implement Update Rightsholder modal window
* B-58562 FDA: Update the item bank detail for denied SAL usage exceptions: remove redundant verification for applied usage status filter
* B-61833 FDA: SAL historical item bank detail report: implement selection only licensees that have historical IB details
* Revert "B-61833 FDA: SAL historical item bank detail report: implement selection only licensees that have historical IB details"

12.1.27
-
* B-61833 FDA: SAL historical item bank detail report: implement CSV report generation
* B-62520 Tech Debt: FDA: adjust ordering of amount columns for SAL fund pool report

12.1.26
-
* B-58562 FDA: Update the item bank detail for denied SAL usage exceptions: add applied filter validation for displaying IB Details for RH Update modal window
* B-58562 FDA: Update the item bank detail for denied SAL usage exceptions: remove lazy loading and implement search on IB Details for RH Update modal window
* B-61833 FDA: SAL historical item bank detail report: implement MyBatis report handler
* B-61833 FDA: SAL historical item bank detail report: implement backend to load values into Licensee dropdown
* B-62520 Tech Debt: FDA: apply 53.0 dist common version
* B-63949 FDA: Export AACL payee exclusions: add export button to Exclude detail by Payee window
* B-63949 FDA: Export AACL payee exclusions: implement service logic for report generation

12.1.25
-
* B-62519 Tech Debt: FDA: update embedded tomcat version to 8.5.59
* B-58562 FDA: Update the item bank detail for denied SAL usage exceptions: add Update Rightsholders button on Usages tab and apply permission for Specialist
* B-61657 FDA: Get RightsHolder for all permission types for FAS/FAS2 & NTS: adjust logic for getting rights by sending SPECIAL_REQUEST
* B-61833 FDA: SAL historical item bank detail report: implement widget to select Licensee and Period End Date
* B-58562 FDA: Update the item bank detail for denied SAL usage exceptions: implement IB Details for RH Update modal window

12.1.24
-
* B-62519 Tech Debt: FDA: rename minimum threshold filter on Exclude by Payee windows for FAS and AACL product families

12.1.23
-
* B-58784 FDA: Send SAL scenarios to LM: rename Send to LM window to Choose Scenarios to Send to LM
* CDP-952 FDA: SAL Fund Pools Report: if date received of the fund pool falls on the last week of the distribution year and the subsequent year starts from Thursday - Sunday this fund pool is included in the report of subsequent year

12.1.22
-
* B-58784 FDA: Send SAL scenarios to LM: rename Send to LM button to Choose Scenarios
* B-63875 FDA: Exclude payees from an AACL scenario: add audit for exclude usages by payee action

12.1.21
-
* B-58784 FDA: Send SAL scenarios to LM: make changes based on comments in CR-DIST-FOREIGN-211
* B-59008 FDA: Send SAL scenario for approval: make changes based on comments in CR-DIST-FOREIGN-209
* B-59935 FDA: SAL fund pool Report: make changes based on comments in CR-DIST-FOREIGN-212
* B-63875 FDA: Exclude payees from an AACL scenario: adjust AACL workflow test to cover case with excluded payees
* B-63875 FDA: Exclude payees from an AACL scenario: display payee information on Exclude Payees window without filtering by default
* CDP-949 FDA: Exclude Payees from AACL scenario: System looks at the detailed licensee class instead of aggregate licensee class while validating remaining usages after exclusion
* CDP-950 FDA: SAL Fund Pools Report: exception occurs when report is generated for fund pools having licensee account number> 2 147 483 647
* CDP-951 FDA: Exclude Payees from AACL scenario: System doesn't reset gross, net and service fee amounts for excluded usages

12.1.20
-
* B-59935 FDA: SAL fund pool Report: fix issue to get licensee account number column
* B-59935 FDA: SAL fund pool Report: implement repository logic to generate report
* B-60185 FDA: SAL Undistributed Liabilities report: make changes based on comments in CR-DIST-FOREIGN-210
* B-63874 FDA: Modify Logic for AACL Scenario Creation: exclude zero amount usages from AACL scenario
* B-63875 FDA: Exclude payees from an AACL scenario: implement logic to exclude details from scenario and recalculate amounts
* B-63875 FDA: Exclude payees from an AACL scenario: implement validation for excluded payees to have ability to distribute money within Agg LC

12.1.19
-
* B-58784 FDA: Send SAL scenarios to LM: implement service logic to send SAL usages to LM
* B-58784 FDA: Send SAL scenarios to LM: revert removed assert in SendScenarioToLmTest
* B-59935 FDA: SAL fund pool Report: implement service logic and create report handler and DTO
* B-63875 FDA: Exclude payees from an AACL scenario: implement service logic to retrieve data for exclude

12.1.18
-
* B-58784 FDA: Send SAL scenarios to LM: implement button to open Send to LM window
* B-58784 FDA: Send SAL scenarios to LM: implement window to select and send scenarios to LM
* B-59935 FDA: SAL fund pool Report: implement controller logic and create modal window for report
* B-60185 FDA: SAL Undistributed Liabilities report: implement SalUndistributedLiabilitiesReportHandler for writing report
* B-60185 FDA: SAL Undistributed Liabilities report: implement repository logic for generating report
* B-63875 FDA: Exclude payees from an AACL scenario: implement Exclude Payees filter widget
* B-63875 FDA: Exclude payees from an AACL scenario: implement Exclude Payees window for AACL product family

12.1.17
-
* B-57885 FDA: SAL Exports: Scenario-specific usage details: make changes based on comments in CR-DIST-FOREIGN-206
* B-57890 AT FDA: Modify the scenario drill-down view for SAL: make changes based on commrnts in CR-DIST-FOREIGN-207
* B-59008 FDA: Send SAL scenario for approval: implement ui logic related to add buttons
* B-60185 FDA: SAL Undistributed Liabilities report: add Undistributed Liabilities Reconciliation Report menu item under Reports tab
* B-60185 FDA: SAL Undistributed Liabilities report: create SalUndistributedLiabilitiesReconciliationReportDto domain for matching data from database
* B-62518 Tech Debt: FDA: rollback the version upgrade of vaadin-spring dependency
* B-62518 Tech Debt: FDA: update tomcat version to 8.5.58
* B-62518 Tech Debt: upgrade rup-vaadin and vaadin-spring versions
* B-63874 FDA: Modify Logic for AACL Scenario Creation: remove title cutoff amount from UI and backend

12.1.16
-
* CDP-947 FDA: Liabilities Summary by Rightsholder and Work Report: 'Work Title' column contains Reported Title instead of System Title
* B-58563 FDA: SAL liabilities summary by RH and Work report: make changes based on comments in CR-DIST-FOREIGN-204
* B-57890 FDA: Modify the scenario drill-down view for SAL: implement  repository logic to find RH's information for populate drill down window
* B-60438 FDA: SAL liabilities by RH report with splits and counts: make changes based on comments in CR-DIST-FOREIGN-205
* B-57893 FDA: Scenario tab modifications for SAL metadata panel: make changes based on comments in CR-DIST-FOREIGN-203

12.1.15
-
* B-60438 FDA: SAL liabilities by RH report with splits and counts: implement repository logic and query
* B-58563 FDA: SAL liabilities summary by RH and Work report: implement repository
* B-58563 FDA: SAL liabilities summary by RH and Work report: implement service logic for generating report
* B-60438 FDA: SAL liabilities by RH report with splits and counts: implement service logic to generate report
* B-58563 FDA: SAL liabilities summary by RH and Work report: use the correct id for common scenarios selecting widget

12.1.14
-
* B-57893 FDA: Scenario tab modifications for SAL metadata panel: implement UI to display scenario metadata information)
* B-60438 FDA: SAL liabilities by RH report with splits and counts: implement modal window for selecting scenarios for generating report
* B-57890 FDA: Modify the scenario drill-down view for SAL: implement drill-down window
* B-58563 FDA: SAL liabilities summary by RH and Work report: implement menu item and controller
* B-57890 FDA: Modify the scenario drill-down view for SAL: implement service logic to get RH's information for populate drill down window
* B-60438 FDA: SAL liabilities by RH report with splits and counts: implement report handler and domain
* B-58563 FDA: SAL liabilities summary by RH and Work report: use order during selection of scenarios
* B-57886 FDA: Calculate SAL Scenario: implement service logic for calculating scenario

12.1.13
-
* B-57885 FDA: SAL Exports: Scenario-specific usage details: implement backend logic to generate CSV file
* B-57885 FDA: SAL Exports: Scenario-specific usage details: implement repository logic to generate export usages
* B-57887 FDA: modify the scenario pop up view for SAL: implement view scenario window with abitility to export Rightsholder details
* B-57894 FDA: Delete SAL scenario: refactor scenarios widget to delete scenario
* B-57897 FDA: Create SAL Scenario: add integration test to verify scenario creating
* B-60438 FDA: SAL liabilities by RH report with splits and counts: implement reports tab and common interfaces
* B-62518 Tech Debt: FDA: adjust grants priority mapping based on PPS-263

12.1.12
-
* B-57772 Tech Debt: FDA: implement service to delete SAL scenario
* B-57772 Tech Debt: FDA: move common logic related to open window of create scenario to common usage window
* B-58518 FDA: Load SAL usage data: make changes based on comments in CR-DIST-FOREIGN-199
* CDP-946 FDA: Load Sal Fund Pool: Wrong calculation of grade amounts during fund pool creation

12.1.11
-
* B-57772 Tech Debt: FDA: fix  CVE-2020-5421 vulnerability
* B-57772 Tech Debt: FDA: fix CVE-2019-13990 vulnerability
* B-57897 FDA: Create SAL Scenario: make changes based on comments in CR-DIST-FOREIGN-196
* B-57917 FDA: Load SAL fund pool: Make changes based on comments in CR-DIST-FOREIGN-200

12.1.10
-
* B-57772 FDA: Tech Debt: delete permissions FDA_LOAD_AACL_FUND_POOL, FDA_DELETE_AACL_FUND_POOL and use instead of them FDA_LOAD_FUND_POOL, FDA_DELETE_FUND_POOL
* B-57772 Tech Debt: FDA: fix CVE-2019-13990 vulnerability
* B-57903 FDA: View and Delete SAL batch: make changes based on comments in CR-DIST-FOREIGN-195
* B-58887 FDA: View and Delete SAL fund pool: make sorting on View/Delete SAL fund pool case-insensitive for Assessment Name, License Name columns

12.1.9
-
* B-57897 FDA: Create SAL Scenario: implement validation for Detail Type filter
* CDP-942: FDA: Delete SAL Usage Bach: Usages from deleted usage batch are still displayed filtered on ‘Usages’ table in case the usage batch is previously filtered on ‘Usages’ tab
* CDP-943 FDA: View SAL Fund Pool: Item Bank Split %, Service fee % and Item Bank Gross Amount columns values mismatch
* CDP-944 FDA: Load SAL Fund Pool window: Exception occurs after typing leading or trailing space into any numeric field
* CDP-945 FDA: Create SAL scenario: while validation of Usage Batch and Fund Pool Licensee Account # matching, the System takes Licensee Account # of the last uploaded SAL batch

12.1.8
-
* B-57772 Tech Debt: FDA: clear usage filters after item bank or usage data is loaded
* B-57897 FDA: Create SAL Scenario: implement service logic to create scenario
* B-57903 FDA: View and Delete SAL batch:  CDP-942: FDA: Delete SAL Usage Bach: Usages from deleted usage batch are still displayed filtered on ‘Usages’ table in case the usage batch is previously filtered on ‘Usages’ tab
* B-57917 FDA: Load SAL fund pool: Adjust SAL Fund Pool calculation and rename Total Amount and Item Bank Amount columns on Load Fund Pool window
* CDP-941 FDA: Load SAL Item Bank: User with 'Manager' and 'View Only' roles is able to load Item Bank

12.1.7
-
* B-57897 FDA: Create SAL Scenario: implement grade group and licensees validations
* B-57898 FDA: SAL usage tab export: make changes based on comments in CR-DIST-FOREIGN-193
* B-57903 FDA: View and Delete SAL batch: implement service logic to delete sal batch
* B-57903 FDA: View and Delete SAL batch: rename method
* B-57917 FDA: Load SAL fund pool: Implement SAL Fund Pool calculation
* B-57917 FDA: Load SAL fund pool: Implement load SAL Fund Pool logic
* B-58518 FDA: Load SAL usage data: implement UI
* B-58518 FDA: Load SAL usage data: implement logic for retrieving SAL batches for uploading usage data
* B-58887 FDA: View and Delete SAL fund pool: make changes based on comments in CR-DIST-FOREIGN-194

12.1.6
-
* B-57897 FDA: Create SAL Scenario: implement validation for filtered data to create SAL scenario
* B-57897 FDA: Create SAL Scenario: retrieve SAL fund pools to display on create scenario window
* B-57903 FDA: View and Delete SAL batch: create view-delete SAL batch modal window
* B-57903 FDA: View and Delete SAL batch: implement repository logic to remove usage batch
* B-57917 FDA: Load SAL fund pool: Implement ui components and validation on Load Fund Pool window
* B-58518 FDA: Load SAL usage data: implement service logic for saving usages
* B-58833 FDA: Generate a Grade Group for SAL usages: populate grade group for UD usages
* B-58887 FDA: View and Delete SAL fund pool: use correct date formatting

12.1.5
-
* B-57772 Tech Debt: FDA: implement partitioning for excluding usages from AACL scenario during calculation
* B-57903 FDA: View and Delete SAL batch: implement repository logic to remove usage details
* B-58518 FDA: Load SAL usage data: implement repository logic for saving SAL usage data detail
* B-58887 FDA: View and Delete SAL fund pool: implement View Fund Pool window

12.1.4
-
* B-57891 FDA: Scenario tab modifications for SAL main view: implement the tab
* B-57891 FDA: Scenario tab modifications for SAL main view: make changes based on comments in CR-DIST-FOREIGN-192
* B-57897 FDA: Create SAL Scenario: fix CreateSalScenarioWindowTest
* B-57897 FDA: Create SAL Scenario: implement SAL scenario specific fields
* B-57897 FDA: Create SAL Scenario: implement create SAL scenario window
* B-57898 FDA: SAL usage tab export: add export button
* B-57898 FDA: SAL usage tab export: implement backend logic to export usage batch
* B-57903 FDA: View and Delete SAL batch: implement repository logic to check is exists usage detail in the Item Bank
* B-57917 FDA: Load SAL fund pool: Implement Fund Pool menu
* B-57917 FDA: Load SAL fund pool: Implement liquibase script to add sal_fields column to df_fund_pool table and implement SAL fund pool fields domain
* B-57917 FDA: Load SAL fund pool: Implement mapper and handler for SAL fund pool fields
* B-57917 FDA: Load SAL fund pool: Implement repository logic to store SAL fund pool data
* B-58518 FDA: Load SAL usage data: fix incorrect file in CSV integration test
* B-58518 FDA: Load SAL usage data: implement CSV processor
* B-58518 FDA: Load SAL usage data: implement grade validator for UD detail
* B-58518 FDA: Load SAL usage data: implement logic for retrieving IB detail grade by work portion id
* B-58518 FDA: Load SAL usage data: implement work portion id validator
* B-58833 FDA: Generate a Grade Group for SAL usages: populate grade group for Item Bank usages
* B-58887 FDA: View and Delete SAL fund pool: add permission to delete Fund Pool
* B-58887 FDA: View and Delete SAL fund pool: implement service and repository methods
* B-58887 FDA: View and Delete SAL fund pool: use the correct name for the Liquibase script to add permission to delete Fund Pool

12.1.3
-
* B-62030 FDA: Migrate to Amazon Corretto: use the new version of dist-common

12.1.2
-
* CDP-939 FDA: SAL Workflow: System sends SAL-usages in RH_NOT_FOUND status for rights assignment using scheduled job

12.1.1
-
* B-59202 Tech Debt: FDA: fix security vulnerabilities CVE-2020-24616
* CDP-939 FDA: SAL Workflow: System sends SAL-usages in RH_NOT_FOUND status for rights assignment using scheduled job

12.1.0
-
* B-57902 FDA: Get rights information from RMS for SAL usages (get grants): add statues to Usages tab filter
* B-57901 FDA: SAL Retrieve system title, standard number, and standard number type: implement SAL matching consumer
* B-61574 FDA: Get special request rights for rights holder reconciliation: update dist-common version to 51.4.+
* B-57901 FDA: SAL Retrieve system title, standard number, and standard number type: implement service logic for sending SAL usage for matching
* B-57902 FDA: Get rights information from RMS for SAL usages (get grants): implement service logic for getting rights
* B-57901 FDA: SAL Retrieve system title, standard number, and standard number type: implement SAL workflow integration test
* B-57902 FDA: Get rights information from RMS for SAL usages (get grants): add processors for getting rights, adjust integration tests
* B-57900 FDA: Create SAL batch: update log message for inserting SAL item bank details
* B-57902 FDA: Get rights information from RMS for SAL usages (get grants): remove WORK_NOT_FOUND status from SAL filter view
* B-57902 FDA: Get rights information from RMS for SAL usages (get grants): make changes based on comments in CR-DIST-FOREIGN-191
* B-57901 FDA: SAL Retrieve system title, standard number, and standard number type: revert WORK_NOT_FOUND status to usage tab for SAL product family
* B-57901 FDA: SAL Retrieve system title, standard number, and standard number type: adjust upload item bank for SAL product family
* B-57901 FDA: SAL Retrieve system title, standard number, and standard number type: make changes based on comments in CR-DIST-FOREIGN-190
* CDP-937 FDA: Delete NTS Scenario: Payee Account #/Name are not cleared after deleting scenario

11.1.9
-
* 58546 FDA: Exclude RH's within a NTS scenario: make changes based on CR-DIST-FOREIGN-189
* B-57900 FDA: Create SAL batch: make changes based on comments in CR-DIST-FOREIGN-188

11.1.8
-
* CDP-930 FDA: Load SAL Item Bank: There is no validation for uniqueness of Reported Work Portion ID in the system

11.1.7
-
* 58546 FDA: Exclude RH's within a NTS scenario: fix export and search are not disabled for empty NTS scenarios, empty scenario label is not displayed
* B-59201 Tech Debt: FDA: fix issue with checkstyle
* B-59201 Tech Debt: FDA: update PI node and cluster values
* CDP-929: FDA: Exclude from NTS scenario: Wrong calculation of redistributed money for gross and service fee amounts

11.1.6
-
* B-57883 FDA: SAL UI usage view: make changes based on comments in CR-DIST-FOREIGN-186
* B-57900 FDA: Create SAL batch (load item bank): implement backend logic to save item bank with usages to database
* B-57900 FDA: Create SAL batch (load item bank): implement integration test to cover upload SAL item bank usages functionality, update property for sales_info healthcheck
* B-58546 FDA: Exclude RH's within a NTS scenario: create Exclude Details By Rightsholder modal view
* B-58903 FDA: NTS service fee true-up report: adjust formula for actual service fee value
* B-58903 FDA: NTS service fee true-up report: make changes based on comments in CR-DIST-FOREIGN-187

11.1.5
-
* B-57883 FDA: SAL UI usage view: remove NOT NULL constraints from scored_assessment_date, question_identifier, states, number_of_views columns of df_usage_sal table
* B-57900 FDA: Create SAL batch (load item bank): apply Usage CSV processor to get validation messages for uploaded file
* B-57900 FDA: Create SAL batch (load item bank): introduce SAL item bank usages CSV processor
* B-58546 FDA: Exclude RH's within a NTS scenario: implement service logic to exclude RH's, relocate amounts and write audit
* B-58903 FDA: NTS service fee true-up report: implement UI for report

11.1.4
-
* B-57883 FDA: SAL UI usage view: implement Liquibase script to create df_usage_sal table
* B-57883 FDA: SAL UI usage view: implement MyBatis mapping to map SAL domain objects to database records; reorder fields of SAL usages
* B-57883 FDA: SAL UI usage view: implement columns sorting in SAL usage grid
* B-57883 FDA: SAL UI usage view: implement domain objects
* B-57883 FDA: SAL UI usage view: implement empty Usages tab
* B-57883 FDA: SAL UI usage view: implement filters on Usages tab
* B-57883 FDA: SAL UI usage view: implement grid on Usages tab
* B-57883 FDA: SAL UI usage view: implement repository to read SAL usages
* B-57883 FDA: SAL UI usage view: implement service to read SAL usages
* B-57900 FDA: Create SAL batch (load item bank): implement upload Item Bank window
* B-57900 FDA: Create SAL batch (load item bank): introduce SAL Usage Batch menu bar, apply telesales integration to get licensee name
* B-57900 FDA: Create SAL batch (load item bank): introduce sal_fields column in df_usage_batch  table to store SAL related info
* B-58546 FDA: Exclude RH's within a NTS scenario: add Exclude by RH button to NTS scenario tab
* B-58546 FDA: Exclude RH's within a NTS scenario: implement logic to exclude details by RH from scenario
* B-58546 FDA: Exclude RH's within a NTS scenario: implement logic to get RH's of scenario
* B-58546 FDA: Exclude RH's within a NTS scenario: implement logic to reallocate funds from excluded RH
* B-58903 FDA: NTS service fee true-up report: implement logic for generating report

11.1.3
-
* CDP-924 FDA: Service Fee True-Up Report for FAS/FAS2: AACL data is displayed in the report

11.1.2
-
* B-59200 Tech Debt: FDA: fix CVE-2020-9484 and CVE-2020-11996 vulnerabilities
* B-59200 Tech Debt: FDA: fix CVE-2018-8088 and CVE-2020-1941 vulnerabilities by removing unused dependencies
* B-59200 Tech Debt: FDA: fix CVE-2020-9488 vulnerability
* B-59200 Tech Debt: FDA: apply last version of rup-vaadin
* B-59200 Tech Debt: FDA: adjust matching for works to search by doi using normalized standard number

11.1.1
-
* B-58527 FDA: Matching enhancements for works: make changes based on comments in CR-DIST-FOREIGN-185
* CDP-923 FDA: FAS/FAS2 Exclude Payees: Previously marked payees checkboxes are cleared when Search string is applied or canceled
* Revert "CDP-923 FDA: FAS/FAS2 Exclude Payees: Previously marked payees checkboxes are cleared when Search string is applied or canceled"

11.1.0
-
* B-57817 FDA: Update the FDA integration with the latest version of Elastic Search for the RUP ES API: migrate to rup-es-api 11.4.+
* B-58527 FDA: Matching enhancements for works: Remove matching by 'idno' and add by 'doi' and 'stdid'
* B57743 CDP: Migrating Oracle integrations from ActiveMQ to SQS: FDA: apply new dist-common version 51.3.+
* CDP-923 FDA: FAS/FAS2 Exclude Payees: Previously marked payees checkboxes are cleared when Search string is applied or canceled

10.1.16
-
* CDP-922 FDA: FAS Exclude Details by Payee filter error

10.1.15
-
* B-59772 FDA: Improve RMS integration: make changes based on comments in CR-DIST-FOREIGN-181

10.1.14
-
* B-58556 FDA: Tax Notification Report in FDA: make report sorting case insensitive
* B-57599 FDA: Exclude Payees within an FAS scenario: make changes based on comments in CR-DIST-FOREIGN-179

10.1.13
-
* B-58556 FDA: Tax Notification Report in FDA: add sorting for report entries

10.1.12
-
* B-58556 FDA: Tax Notification Report in FDA: add styles to window components
* B-58556 FDA: Tax Notification Report in FDA: make changes based on comments in CR-DIST-FOREIGN-180
* B-58559 FDA: NTS undistributed liabilities reconciliation report: add NTS  Undistributed Liabilities Reconciliation Report to UI
* B-58559 FDA: NTS undistributed liabilities reconciliation report: adjust  service logic for generating report
* B-58559 FDA: NTS undistributed liabilities reconciliation report: implement query for retrieving report data
* B-59199 Tech Debt: FDA: implement logging HTTP POST body and headers in error message
* B-59772 FDA: Improve RMS integration: get license types from db

10.1.11
-
* B-58556 FDA: Tax Notification Report in FDA: make scenario sorting case insensitive, adjust 'number of days' field validation message
* B-59772 FDA: Improve RMS integration: refactor RightsService.updateAaclRights to read rights for list of wrWrkInst
* B-59772 FDA: Improve RMS integration: Refactor RightsServices to read  rights by list of wrWrkInst for FAS/FAS2/NTS product families

10.1.10
-
* B-58556 FDA: Tax Notification Report in FDA: adjust service logic so case, when no information was received from Oracle, is handled correctly
* B-59199 Tech Debt: FDA: fix vulnerabilities postgres
* B-58557 FDA: Ability to Export FAS/FAS2 Excluded Payee: make changes based on comments in CR-DIST-FOREIGN-176
* B-59772 FDA: Improve RMS integration: implement RUP property to switch off RMS rights caching
* B-58556 FDA: Tax Notification Report in FDA: implement UI for report
* B-59560 FDA:  View/Export NTS withdrawn details on FAS/FAS2 usage tab: make changes based on CR-DIST-FOREIGN-178
* B-58556 FDA: Tax Notification Report in FDA: adjust 'number of days' field validation message

10.1.9
-
* B-58556 FDA: Tax Notification Report in FDA: get rid of using MyBatis API in ReportService
* B-58556 FDA: Tax Notification Report in FDA: implement service logic for generating CSV report
* B-58557 FDA: Ability to Export FAS/FAS2 Excluded Payee: correct headers of the 'Service Fee Amount', 'Net Amt in USD' columns
* B-59772 FDA: Improve RMS integration: FDA: implement sending license types in RMS grants request for FAS/FAS2/NTS product families
* B-59772 FDA: Improve RMS integration: refactor updating rights for AACL product family by sending sending rights statuses and license types
* CDP-921: FDA: FAS Batch Summary Report: TO_BE_DISTRIBUTED usages are displayed in the '# non-Eligible Details' and 'Gross USD non-Eligible Details' columns of the report

10.1.8
-
* B-57599 FDA: Exclude Payees within an FAS/FAS2 scenario: implement participating statuses validation for different scenarios
* B-57599 FDA: Exclude Payees within an FAS/FAS2 scenario: move exclude payees logic to Scenarios tab
* B-58556 FDA: Tax Notification Report in FDA: get country from PRM
* B-58556 FDA: Tax Notification Report in FDA: implement service logic for getting RH Tax Information
* B-59199 Tech Debt: FDA: eliminate support of dist.foreign.usages.chunks property
* B-59772 FDA: Improve RMS integration: add and populate license_type column in the df_grant_priority table
* B-59772 FDA: Improve RMS integration: fix failed build
* B-59772 FDA: Improve RMS integration: implement sending rights statuses and license types in RMS grants request; refactoring RmsRightsCacheService to support rights statuses and license types
* CDP-920 FDA: FAS Batch Summary Report: NTSWITHDRAWN and TOBE_DISTRIBUTED usages are not displayed in the '# Details NTS' and 'Gross USD NTS' columns of the report

10.1.7
-
* B-57599 FDA: Exclude Payees within an FAS/FAS2 scenario: apply scenarios filter on Exclude Payees window
* B-57599 FDA: Exclude Payees within an FAS/FAS2 scenario: exclude payee and redesignate to NTS withdrawn for the multiple scenarios
* B-58556 FDA: Tax Notification Report in FDA: implement repository logic for getting RH/payee pairs from scenario
* B-58557 FDA: Ability to Export FAS/FAS2 Excluded Payee: apply multiple scenarios filter
* B-58558 FDA: AACL undistributed liabilities report: Implement report on UI and add sorting
* B-58558 FDA: AACL undistributed liabilities report: Remove incorrect and redundant test data for testAaclUndistributedLiabilitiesCsvReport
* B-58560 FDA: View/Export NTS withdrawn details on FAS/FAS2 usage tab: adjust backend logic related to change statuses and product families
* B-58560 FDA: View/Export NTS withdrawn details on FAS/FAS2 usage tab: remove change product family in redisignate to NTS_WITHDRAWN by payees
* B-58560 FDA: View/Export NTS withdrawn details on FAS/FAS2 usage tab: update historical data, change product family from NTS to FAS/FAS2 for details with NTS_WITHDRAWN & TO_BE_DISTRIBUTED statuses for archived usages

10.1.6
-
* B-57599 FDA: Exclude Payees within an FAS/FAS2 scenario: implement scenarios filter for FAS/FAS2 product families
* B-58556 FDA: Tax Notification Report in FDA: Implement RH Tax Information REST integration
* B-58556 FDA: Tax Notification Report in FDA: add support for TAXBENEFICIALOWNER preference
* B-58556 FDA: Tax Notification Report in FDA: rename OracleRhTaxService to OracleRhTaxCountryService
* B-58556 FDA: Tax Notification Report in FDA: use getCharset() method and fix findbugs warning
* B-58557 FDA: Ability to Export FAS/FAS2 Excluded Payee: implement Export button on Exclude by Payee modal window
* B-58557 FDA: Ability to Export FAS/FAS2 Excluded Payee: implement service for exporting payee information
* B-58558 FDA: AACL undistributed liabilities report: Add query to retrieve data for the report and report handler
* B-58560 FDA: View/Export NTS withdrawn details on FAS/FAS2 usage tab: move NTS_WITHDRAWN and TO_BE_DISTRIBUTED statuses from NTS to FAS/FAS2 product family on usage and audit tabs
* B-58560 FDA: View/Export NTS withdrawn details on FAS/FAS2 usage tab: update historical data, change product family from NTS to FAS/FAS2 for details with NTS_WITHDRAWN & TO_BE_DISTRIBUTED statuses
* B-59199 Tech Debt: update standard dependencies version set to v5_7
* CDP-916 Upgrade Foreign Distributions to Spring 5

10.1.5
-
* B-55952 FDA: Post distribution and split usage detail reporting enhancement: make changes based on comments in CR-DIST-FOREIGN-175
* B-56849 Tech Debt: FDA: adjust report verification using executor so test doesn't get stuck in case method under test fails with exception
* B-56849 Tech Debt: FDA: adjust scenario audit verification in integration tests
* B-56849 Tech Debt: FDA: fix CVE-2020-9484 vulnerability
* B-56849 Tech Debt: FDA: remove obsolete TODO comments
* CDP-912 FDA: User is not able to view scenario after FDA receiving Split information of thousands of details from that scenario

10.1.4
-
* B-55952 FDA: Post distribution and split usage detail reporting enhancement: adjust paid information processing
* B-55952 FDA: Post distribution and split usage detail reporting enhancement: adjust workflow tests
* B-56849 Tech Debt: FDA: replace SnsNotificationMessageConverter by jackson parser, resolve todo in DuplicateInFileValidator
* B-56849 Tech Debt: implement min by title cutoff amount with exclusion integration test
* B-59011 FDA: Reporting AACL to RC: Make changes based on CR-DIST-FOREIGN-174
* B-59011 FDA: Reporting AACL to RC: separate assertion step for paid usages to AACL workflow integration test

10.1.3
-
* B-56234 FDA: AACL export of baseline data: make changes based on comments in CR-DIST-FOREIGN-172
* B-56282 FDA: Send AACL scenario to LM: Make changes based on comments in CR-DIST-FOREIGN-173
* B-56849 Tech Debt: FDA: use AACL repository methods for AACL specific logic
* B-59011 FDA: Reporting AACL to RC: implement workflow integration test for AACL product family
* CDP-911 FDA: Send AACL scenario to LM: All audit records for AACL usages are deleted after sending scenario to LM

10.1.2
-
* B-56849 Tech Debt: FDA: remove RMS chunk processing, add transactional for rights consumers
* B-59011 FDA: Reporting AACL to RC: Implement logic to receive paid information from LM

10.1.1
-
* B-56282 FDA: Send AACL scenario to LM: Add 'Send to LM' button
* B-56282 FDA: Send AACL scenario to LM: Add AACL case to SendScenarioToLmTest

10.1.0
-
* B-56234 FDA: AACL export of baseline data: add baseline usages report to AACL report tab
* B-56234 FDA: AACL export of baseline data: implement repository logic for report generation
* B-56282 FDA: Send AACL scenario to LM: Add a specific method for getting usages for sending to LM
* B-56282 FDA: Send AACL scenario to LM: Adjust repository logic for deleting scenario usages
* B-56282 FDA: Send AACL scenario to LM: Enable view scenario functionality for scenario with archived usages
* B-56282 FDA: Send AACL scenario to LM: Implement logic for copying scenario usages to baseline
* B-56282 FDA: Send AACL scenario to LM: Implement logic for deleting scenario usages
* B-56282 FDA: Send AACL scenario to LM: Implement service logic for sending AACL scenario to LM
* B-56615 FDA: Reporting AACL to RC: adjust integration test to send report to crm
* B-56849 Tech Debt: FDA: get RMS grants by batches for chunk usage processors
* B-56849 Tech Debt: FDA: move FAS scenario specific logic to separate service
* B-56849 Tech Debt: FDA: move NTS scenario specific logic to separate service
* B-58238 FDA: Filter criteria modifications for searching: adjust filters on Audit tab to search by exact match
* B-58238 FDA: Filter criteria modifications for searching: make changes based on comments in CR-DIST-FOREIGN-171
* B-58238 FDA: Filter criteria modifications for searching: make changes based on comments in CR-DIST-FOREIGN-171
* B-58288 FDA: Batch processing performance enhancements: make changes based on comments in CR-DIST-FOREIGN-170
* CDP-910 FDA: AACL P-PRE usage details are classified but show status of WORK_RESEARCH

9.1.30
-
* CDP-910 FDA: AACL P-PRE usage details are classified but show status of WORK_RESEARCH
* B-56849 Tech Debt: FDA: get RMS grants by batches for chunk usage processors

9.1.29
- 
* B-58288 FDA: Batch processing performance enhancements: make changes based on comments in CR-DIST-FOREIGN-170
* B-56848 Tech Debt: FDA: move getScenarioNameByFundPoolId method to AaclScenarioService

9.1.28
-
* B-54909 FDA: Work Shares by Aggregate Licensee Class: make changes based on comments in CR-DIST-FOREIGN-168
* B-55007 FDA: AACL Exports: Scenario-specific usage details: make changes based on CR-DIST-FOREIGN-169
* B-56848 Tech Debt: FDA: adjust Ownership Adjustment Report window width
* B-56848 Tech Debt: FDA: fix unit tests for Ownership Adjustment Report window
* B-56848 Tech Debt: FDA: remove obsolete TODO comment
* B-56848 Tech Debt: FDA: remove redundant TODO
* B-56848 Tech Debt: FDA: remove reported value from Scenario domain
* B-56848 Tech Debt: FDA: update dist-common version to 49.6.+
* B-58288 FDA: Batch processing performance enhancements: Refactor NtsWorkflowChunkIntegrationTest
* B-58288 FDA: Batch processing performance enhancements: make changes based on comments in CR-DIST-FOREIGN-170
* B-58288 FDA: Batch processing performance enhancements: refactor CreateNtsBatchChunkIntegrationTest to verify STM RH consumer to use REST calls with list arguments
* B-58288 FDA: Batch processing performance enhancements: refactor STM RH consumer to use REST calls with list arguments

9.1.27
-
* B-56848 Tech Debt: FDA: add index to rh_account_number column in df_rightsholder table to speed up classification process
* B-56848 Tech Debt: FDA: add sorting by batch_name to withdrawn batch_summary_report
* B-58288 FDA: Batch processing performance enhancements: Refactor RH Tax logic to process usages by chunks
* CDP-909: FDA: df.service.getRightsQuartzJob: Job updates usages for NTS only, ignoring other product families

9.1.26
-
* B-58288 FDA: Batch processing performance enhancements: use chunk-based serializers/deserializers and use header-based routing in Camel configuration

9.1.25
-
* B-56848 Tech Debt: FDA: Replace OwnershipAdjustmentReportWidget with ScenarioReportWidget
* B-56848 Tech Debt: FDA: adjust work classification report
* B-56848 Tech Debt: FDA: apply updated_date ordering for classification, add filtering by FAS/FAS2 product families and update updatate_datetime column for SC migrated usages
* B-56848 Tech Debt: FDA: system disallows user to create scenario without applied batch filter
* B-58288 FDA: Batch processing performance enhancements: implement AWS SQS consumers
* B-58288 FDA: Batch processing performance enhancements: implement Camel configuration
* B-58288 FDA: Batch processing performance enhancements: refactor service methods to switch between main and alternative chain executors
* CDP-908: Default values of Agg LC ID, Agg LC Enrollment, Agg LC Discipline are exported in spite of the fact that Licensee Class Mapping was adjusted while the scenario creation

9.1.24
-
* B-54909 FDA: AACL Reports: Work Shares by Aggregate Licensee Class: implement repository logic for report generation
* B-54910 FDA: AACL Reports - Summary of Work Shares by Aggregate Licensee Class: Implement report widget and controller
* B-54910 FDA: AACL Reports - Summary of Work Shares by Aggregate Licensee Class: fix report name and adjust window size
* B-55007 FDA: AACL Exports: Scenario-specific usage details: adjust repository logic to export detais scenario
* B-56278 FDA: Submit and Approve an AACL scenario: make changes based on comments in CR-DIST-FOREIGN-167
* B-58288 FDA: Batch processing performance enhancements: implement chain processors
* B-58288 FDA: Batch processing performance enhancements: implement job processors

9.1.23
-
* B-54910 FDA: AACL Reports - Summary of Work Shares by Aggregate Licensee Class: Implement service and repository logic
* B-55007 FDA: AACL Exports: Scenario-specific usage details: implement handler to export details of scenario
* B-55007 FDA: AACL Exports: Scenario-specific usage details: implement service logic related to export details scenario
* B-56278 FDA: Submit and Approve an AACL scenario: Adjust logic to retrieve fund pools for scenario creation
* B-56278 FDA: Submit and Approve an AACL scenario: adjust 'Fund pool cannot be deleted' message on View AACL fund pool window
* B-58288 FDA: Batch processing performance enhancements: implement AWS SQS producers
* B-58288 FDA: Batch processing performance enhancements: implement AWS SQS serializer/deserializer, marshaller/unmarshaller

9.1.22
-
*  B-56847 Tech Debt: FDA: adjust AaclUsage domain object to remove redundant fields for Agg and LC class detail
* B-54909 FDA: AACL Reports: Work Shares by Aggregate Licensee Class: implement common widget and controller for scenario specific reports
* B-55007 FDA: AACL Exports: Scenario-specific usage details: implement Export Details  button to scenario  window
* B-56278 FDA: Submit and Approve an AACL scenario: Add buttons
* B-56847 Tech Debt: FDA: adjust AaclUsage domain object to avoid redundant initialization
* B-56847 Tech Debt: FDA: adjust AaclUsage domain object to remove redundant fields for usage and publication type weights
* B-56847 Tech Debt: FDA: adjust sort properties on Usages tab for AACL product family
* B-56847 Tech Debt: FDA: handle exception during canceling of CSV reports generation
* B-56847 Tech Debt: FDA: update dist-common version to 49.5.+
* B-56848 Tech Debt: FDA: change dist-common version to 49.4.+
* B-56913 FDA: Modify the scenario drill-down view for AACL: Make changes based on comments in CR-DIST-FOREIGN-163
* B-58288 FDA: Batch processing performance enhancements: implement performance logger for chain execuror

9.1.21
-
* B-56267 FDA: Delete AACL Scenario: make changes based on CR-DIST-FOREIGN-166
* B-56756 FDA: Audit Tab Modification for AACL: make changes base on CR-DIST-FOREIGN-153
* B-56756 FDA: Audit Tab Modification for AACL: make changes based on CR-DIST-FOREIGN-153
* B-57242 FDA: Scenario Tab modifications for AACL Metadata Panel: make changes based on CR-DIST-FOREIGN-165
* B-57242 FDA: Scenario Tab modifications for AACL Metadata Panel: make changes based on CR-DIST-FOREIGN-165
* CDP-904 FDA: ‘Usage Age Weights’ link on Scenario metadata panel for AACL product family: ‘DEFAULT WEIGHT’ column displays invalid values for associated scenario
* CDP-905 FDA AACL: View Usage Batch: Exception occurs while trying to search a batch by search string

9.1.20
-
* B-52335 FDA: Create AACL Scenario: make changes based on comments in CR-DIST-FOREIGN-157
* B-55090 FDA: AACL Calculate Scenario: add volume, value and total shares columns in database and populate them during scenario calculation
* B-55090 FDA: AACL Calculate Scenario: make changes based on comments in CR-DIST-FOREIGN-156
* B-56267 FDA: Delete AACL Scenario: implement repository logic to delete usages from scenario
* B-58160 FDA: Create AACL Scenario: LOCKED usages are fixed to a Scenario until Scenario is deleted: make changes based on comment in CR-DIST-FOREIGN-164

9.1.19
-
* B-54999 FDA: View scenario-specific usage age weights for AACL: implement link from scenario metadata panel to Usage Age Weights View window
* B-56267 FDA: Delete AACL Scenario: implement service logic to delete AACL scenario
* B-56847 Tech Debt: FDA: change buttons order, add placeholder between buttons in Licensee Class Mapping window
* CDP-903 FDA: Scenario drill-down view for AACL Product Family: Sorting by 'GROSS AMT IN USD', 'SERVICE FEE AMOUNT', 'NET AMT IN USD' columns doesn't work

9.1.18
-
* B-54999 FDA: View scenario-specific usage age weights for AACL: implement default weights column for Usage Age Weights View window
* B-55721 FDA: Scenario Tab metadata panel AACL Pub Type Weights View: implement default weights column for Pub Type Weights View window
* B-55721 FDA: Scenario Tab metadata panel AACL Pub Type Weights View: implement link from scenario metadata panel to Pub Type Weights View window
* B-56267 FDA: Delete AACL Scenario: implement delete button on scenario tab

9.1.17
-
* B-55090 FDA: AACL Calculate Scenario: adjust logic for getting payees only for calculated usages
* B-55090 FDA: AACL Calculate Scenario: extend test data for AACL scenario integration test to cover SCENARIO_EXCLUDED usages
* B-58160 FDA: Create AACL Scenario: LOCKED usages are fixed to a Scenario until Scenario is deleted: add checks for batches
* CDP-901 FDA: Scenario drill-down view for AACL Product Family: Service fee % is displayed as 0.0

9.1.16
-
* B-55090 FDA: AACL Calculate Scenario: add volume_weight and value_weight columns to df_usage_aacl table and populate them during scenario creation
* B-56847 Tech Debt: FDA: implement proportional columns resizing for tables in Licensee Class Mapping, Fund Pool windows for AACL scenarios
* B-56913 FDA: Modify the scenario drill-down view for AACL: Implement service and repository logic
* B-56913 FDA: Modify the scenario drill-down view for AACL: fix changed gross amount calculation

9.1.15
-
* B-55090 FDA: AACL Calculate Scenario: add scenario_excluded status to Usages and Audit tabs
* B-55090 FDA: AACL Calculate Scenario: implement second round of calculation to calculate gross, net and service fee amount
* B-55091 FDA: Modify the scenario popup view for AACL: add permission for viewing scenario

9.1.14
-
* B-57245 FDA: Scenario Tab modifications for AACL Licensee Class Mapping View: implement link from scenario metadata panel to Licensee Class Mapping View window
* B-57244 FDA: Scenario Tab modifications for AACL Fund Pool View: implement link from scenario metadata panel to Fund Pool View window
* B-57242 FDA: Scenario Tab modifications for AACL Metadata Panel: display scenario specific information and implement logic to display scenario-specific details  on metadata panel
* B-55091 FDA: Modify the scenario popup view for AACL: implement View Scenario window
* B-55091 FDA: Modify the scenario popup view for AACL: adjust imports in AaclScenariosControllerTest
* B-56913 FDA: Modify the scenario drill-down view for AACL: Implement Drill Down By Rightsholder window
* B-55090 FDA: AACL Calculate Scenario: implement first round of calculation to find usages under minimum
* B-55008 FDA: AACL Exports Scenario Results by RH Account: add 'Export' button
* B-57242 FDA: Scenario Tab modifications for AACL Metadata Panel: adjust ordering of amounts and remove Reported Total Amount on scenario metadata panel for FAS/FAS2/NTS product families

9.1.13
-
* B-56844 Tech Debt: FDA: make ProductFamilyProvider serializable to avoid warnings in logs
* B-56844 Tech Debt: FDA: remove temporary source code

9.1.12
-
* B-56844 Tech Debt: FDA: Fix security vulnerabilities CVE-2020-9546, CVE-2020-9547, CVE-2020-9548 in jackson-databind
* B-56844 Tech Debt: FDA: Fix security vulnerability CVE-2020-1938 in tomcat-embed-core
* CDP-898 FDA: Upload AACL Usage Batch: RH_NOT_FOUND usages are removed from ‘df_usage’ table and are not removed from ‘df_usage_aacl’ table

9.1.11
-
* B-52335 FDA: Create AACL Scenario: Adjust scenario logging
* B-52335 FDA: Create AACL Scenario: remove unused parameter from logger
* B-56527 FDA: Add "Gross" to "Amt in USD" for all product families: refactor Exclude Details By Payee table
* B-56527 FDA: Add "Gross" to "Amt in USD" for all product families: refactor Refresh Scenario table
* B-56527 FDA: Add "Gross" to "Amt in USD" for all product families: refactor scenario by RH drilldown tables
* B-56527 FDA: Add "Gross" to "Amt in USD" for all product families: refactor scenario table, export scenario and export scenario details CSV reports
* B-56943 FDA: AACL map detailed licensee classes to aggregate licensee classes: avoid inserting invalid values in aggregate class id dropdown

9.1.10
-
* Revving up build version
* Update release notes for version 9.1.9
* B-56844 Tech Debt: FDA: adjust widths of headers in AACL usages table
* B-57613 FDA: Audit Tab modifications for AACL: fix issue related to sort by service fee amount
* CDP-897 FDA: Create AACL Scenario: User is able to delete batches and fund pools that have already been added to a Scenario
* CDP-896 FDA: Create AACL Scenario: AACL usages attached to a created scenario can be displayed on ‘Usages’ tab in LOCKED status
* B-56527 FDA:  Add "Gross" to "Amt in USD" for all product families: refactor usages table and export usages CSV report
* B-56527 FDA: Add "Gross" to "Amt in USD" for all product families: refactor FAS/FAS2 “Send for Research” and “Load Researched Details” functionalities
* B-57816 FDA: Get Payee for AACL scenario: implement logic to get and populate payee for AACL usages
* B-57613 FDA: Audit Tab modifications for AACL: fix issue related to Period And Date and Pub Type columns
* B-56527 FDA: Add "Gross" to "Amt in USD" for all product families: refactor FAS/FAS2 Usage Batch upload
* B-57849 FDA: Add service fee amount to metadata panel all product families: add service fee amount label to metadata panel
* B-56527 FDA: Add "Gross" to "Amt in USD" for all product families: refactor usages audit table and export usages CSV report
* B-52335 FDA: Create AACL Scenario: include usage period filter when checking for invalid rightsholders

9.1.9
-
* B-52335 FDA: Create AACL Scenario: implement logic for validating licensee classes mapping
* B-52335 FDA: Create AACL Scenario: fire ScenarioCreateEvent when scenario is created
* B-56844 Tech Debt: FDA: adjust validation rules for amounts fields on scenario creation windows
* B-56844 Tech Debt: FDA: adjust validation rules for empty entered values
* B-56844 Tech Debt: FDA: remove ProductFamilyFilterWidget class
* B-56844 Tech Debt: FDA: rename publication_type_uid columns to df_publication_type_uid in df_usage_aacl, df_usage_baseline_aacl tables
* B-56844 Tech Debt: FDA: rename publication_type_uid columns to df_publication_type_uid in df_usage_aacl, df_usage_baseline_aacl tables
* B-56844 Tech Debt: FDA: rename table column from Name to Pub Type in Pub Type Weights window
* B-57241 FDA: Scenario Tab modifications for AACL Main View: implement Scenarios tab
* B-57745 FDA: AACL Licensee Class Name changes: adjust Agg LC ID column name for uploading AACL fund pool

9.1.8
-
* B-27613 FDA: Audit Tab modifications for AACL: adjust find for audit query in AACL usage mapper
* B-52335 FDA: Create AACL Scenario: cover scenario creation with integration test
* B-52335 FDA: Create AACL Scenario: implement logic for creating scenario
* B-52335 FDA: Create AACL Scenario: implement logic for updating pub type weights
* B-52335 FDA: Create AACL Scenario: store usage period in scenario filter
* B-56844 Tech Debt: FDA: rename NTS_EXLCUDED status to SCENARIO_EXCLUDED
* B-56943 FDA: AACL map detailed licensee classes to aggregate licensee: apply read only mode for aggregate licensee class window
* B-56943 FDA: AACL map detailed licensee classes to aggregate licensee: make changes based on comments in CR-DIST-FOREIGN-151
* B-56947 FDA: AACL view and edit scenario Pub Type Weights: make Usage Age Weights and Publication Weights non-resizable
* B-56947 FDA: AACL view and edit scenario Pub Type Weights: make changes based on comments in CR-DIST-FOREIGN-150
* B-56947 FDA: AACL view and edit scenario Pub Type Weights: make changes based on comments in CR-DIST-FOREIGN-150
* B-56947 FDA: AACL view and edit scenario Pub Type Weights: make changes based on comments in CR-DIST-FOREIGN-150
* B-56949 FDA: Edit usage age weights for AACL while creating a scenario: adjust Usage Age Weights window to use it in view and edit modes
* B-57613 FDA: Audit Tab modifications for AACL: adjust logic related to  Audit filter for AACL product family
* B-57746 FDA: AACL Usage tab updates for Licensee class: rename licensee class related columns on usage export and classification
* CDP-894 FDA: Pub Type Weights, Usage Age Weights: Exception occurs in case leading or trailing spaces are entered when editing Weights
* CDP-895 FDA: Create AACL Scenario: Exception occurs when the user tries to create scenario with 'Usage Period' filter applied

9.1.7
-
* B-52332 FDA: Add baseline usage details to usage batch: adjust integration tests
* B-54437 Tech Debt: FDA: replace Publication Type column name to Pub Type on UI
* B-52332 FDA: Add baseline usage details to usage batch: use product family specific getUsagesByIds service method
* B-52332 FDA: Add baseline usage details to usage batch: rename baselineFlag field in AaclUsage entity to baseline
* B-57437 Tech Debt: FDA: update Postgres driver version to 42.2.10
* B-52332 FDA: Add baseline usage details to usage batch: make changes based on comments in CR-DIST-FOREIGN-149
* B-52332 FDA: Add baseline usage details to usage batch: remove obsolete TODO comment
* B-57437 Tech Debt: FDA: move usage-related methods from UsageBatchService to UsageService, AaclUsageService, NtsUsageService
* B-56947 FDA: AACL view and edit scenario Pub Type Weights: delete Pub Type 'Other' and add Pub Type Weights
* B-27613 FDA: Audit Tab modifications for AACL: remove is_baseline_flag column and add baseline_uid column
* B-56947 FDA: AACL view and edit scenario Pub Type Weights: implement the domain object for Pub Types and the object specific to AACL scenario fields
* B-56943 FDA: AACL map detailed licensee classes to aggregate licensee classes: implement AACL scenario parameter widget
* B-27613 FDA: Audit Tab modifications for AACL: adjust insert basline usage query and fix issue related to rename is_baseline_flag to baselineId colunm
* B-56947 FDA: AACL view and edit scenario Pub Type Weights: implement serializers/deserializers for AACl scenario objects
* B-56947 FDA: AACL view and edit scenario Pub Type Weights: add aacl_fields column to df_scenario table
* B-56947 FDA: AACL view and edit scenario Pub Type Weights: implement repository for AACl scenario objects
* B-56949 FDA: Edit usage age weights for AACL while creating a scenario: implement service and repository logic to get default usage age weights
* B-52335 FDA: Create AACL Scenario: add titleCutoffAmount and fundPoolId to AaclFields
* B-56947 FDA: AACL view and edit scenario Pub Type Weights: implement backend for Pub Type Weights
* B-56844 Tech Debt: FDA: upgrade rup-vaadin to 8.1.+
* B-52335 FDA: Create AACL Scenario: add 'Add To Scenario' button to AACL usage tab
* B-27613 FDA: Audit Tab modifications for AACL: implement repository logic related to audit export for AACL product family
* B-56943 FDA: AACL map detailed licensee classes to aggregate licensee classes: implement aggregate licensee class mapping window
* B-27613 FDA: Audit Tab modifications for AACL: implement controller layer and add audit widget
* B-56949 FDA: Edit usage age weights for AACL while creating a scenario: implement Usage Age Weights window
* B-57745 FDA: AACL Licensee Class Name changes: implement liquibase script to update aggregate licensee class table, apply new columns on UI
* B-52335 FDA: Create AACL Scenario: implement Create AACL Scenario window
* B-56947 FDA: AACL view and edit scenario Pub Type Weights: implement Pub Type Weights window
* B-52335 FDA: Create AACL Scenario: require batch filter and batch processing completion for creating AACL scenario
* B-56943 FDA: AACL map detailed licensee classes to aggregate licensee classes: adjust column names, replace aggregate name column on aggregate mapping window
* B-52335 FDA: Create AACL Scenario: add Usage Age Weights and Publication Type Weights windows to the Create Scenario window
* B-52335 FDA: Create AACL Scenario: Implement logic for adding usages to scenario by UsageFilter
* B-56949 FDA: Edit usage age weights for AACL while creating a scenario: adjust AACL scenario serializer and deserializer to store and retrieve UsageAge objects
* B-56943 FDA: AACL map detailed licensee classes to aggregate licensee: implement backend logic for retrieving default aggregate licensee mapping
* B-56844 Tech Debt: FDA: Fix security vulnerability CVE-2020-8840 in jackson-databind
* B-27613 FDA: Audit Tab modifications for AACL: temporary fix of audit tab issue for AACL product family
* B-56943 FDA: AACL map detailed licensee classes to aggregate licensee: add detail licensee classes to AACL scenario fields and serialization
* B-27613 FDA: Audit Tab modifications for AACL: fix build related to audit tab for aacl product family
* B-27613 FDA: Audit Tab modifications for AACL: fix checkstyle issue

9.1.6
-
* B-52332 FDA: Add baseline usage details to usage batch: adjust AACL batch insertion logic to pull usages from baseline

9.1.5
-
* B-52332 FDA: Add baseline usage details to usage batch: adjust chain processing to make RH_FOUND usages from baseline ELIGIBLE
* B-52332 FDA: Add baseline usage details to usage batch: implement repository logic for pulling usages from baseline
* B-52332 FDA: Add baseline usage details to usage batch: resolve issue related to parse number of baseline years value
* B-57437 FDA: Tech Debt: make Usage Batch name unique across product family
* B-57437 FDA: Tech Debt: make Usage Batch name unique across product family
* CDP-892 FDA: PI Matching: System Title is not updated to actual one in case pi matching is performed by Title only and HostIdno was returned

9.1.4
-
* B-52332 FDA: Add baseline usage details to usage batch: add original_publication_type column to df_usage_aacl
* B-52332 FDA: Add baseline usage details to usage batch: adjust AACL usage inserting so baseline usage fields are considered, change detailLicenseeClassId field type to Integer in AaclUsage
* B-52332 FDA: Add baseline usage details to usage batch: use PublicationType entity instead of string value in AaclUsage
* B-523332 FDA: Add baseline usage details to usage batch: add Number of Baseline Years columns to the view AACL batch modal window
* B-523332 FDA: Add baseline usage details to usage batch: add Number of Baseline Years field into modal window and adjust completed message
* B-56530 FDA: Avoid matching to article or chapter level works: adjust audit records for usages matched by host idnos

9.1.3
-
* B-52332 FDA: Add baseline usage details to usage batch: add baseline_years column into df_usage_batch
* B-52332 FDA: Add baseline usage details to usage batch: add is_baseline_flag and publication_type_weight columns into df_usage_aacl
* B-52332 FDA: Add baseline usage details to usage batch: create df_usage_baseline_aacl table
* B-52332 FDA: Add baseline usage details to usage batch: make insertAaclBatch method return inserted usages
* B-56530 FDA: Avoid matching to article or chapter level works: adjust service logic for PI integration in order to match works by hostIdno

9.1.2
-
* B-52334 FDA: Load AACL Fund Pool: fix log message
* B-56093 Tech Debt: FDA: modify data types of detail_licensee_class_id and aggregate_licensee_class_id columns
* B-56093 Tech Debt: FDA: refactor classes/methods to use NtsFundPool instead of AdditionalFunds, NtsUsageBatch instead of FundPool in services and repositories
* B-56093 Tech Debt: FDA: remove enabled property from commonScripts
* B-56093 Tech Debt: FDA: rename NtsFundPool class to NtsFields and move to UsageBatch, rename fund_pool columns in database to nts_fields
* B-56093 Tech Debt: FDA: update postgres driver version to 42.2.9
* B-56265 FDA: View & Delete AACL Batch: Align the grid id on View AACL Usage Batch window with the same grid for other product families
* B-56265 FDA: View & Delete AACL Batch: Make changes based on comments in CR-DIST-FOREIGN-146

9.1.1
-
* B-56093 Tech Debt: FDA: move FAS specific logic to separate service and repository on Usage level
* B-56093 Tech Debt: FDA: move refresh and reconcile functionality to FAS specific classes
* B-56093 Tech Debt: FDA: refactor classes/methods names to use AdditionalFund instead of PreServiceFeeFund
* B-56265 FDA: View & Delete AACL Batch: Implement service and repository logic to remove AACL usage batch
* Revert "B-56093 Tech Debt: FDA: move FAS specific logic to separate service and repository on Usage level"
* Revert "Revert "B-56093 Tech Debt: FDA: move FAS specific logic to separate service and repository on Usage level""

9.1.0
-
* B-52334 FDA: Load AACL Fund Pool: add product_family column into df_fund_pool table and rename withdrawn_amount to total_amount
* B-52334 FDA: Load AACL Fund Pool: create df_fund_pool_aacl, df_fund_pool_detail_aacl tables
* B-52334 FDA: Load AACL Fund Pool: implement Upload button
* B-52334 FDA: Load AACL Fund Pool: implement file upload dialog
* B-52334 FDA: Load AACL Fund Pool: implement fund pool CSV file parsing
* B-52334 FDA: Load AACL Fund Pool: implement fund pool name uniqueness validation
* B-52334 FDA: Load AACL Fund Pool: implement fund pool service and repository
* B-52334 FDA: Load AACL Fund Pool: make changes based on comments in CR-DIST-FOREIGN-145
* B-52334 FDA: Load AACL Fund Pool: make changes based on comments in CR-DIST-FOREIGN-145
* B-52334 FDA: Load AACL Fund Pool: make changes based on comments in CR-DIST-FOREIGN-145
* B-52334 FDA: Load AACL Fund Pool: remove df_fund_pool_aacl table and use df_fund_pool table instead
* B-54725 FDA: View/Delete AACL Fund Pool: fix AACL fund pool detail retrieving logic
* B-54725 FDA: View/Delete AACL Fund Pool: implement View Fund Pool Details window, add total amount column to View Fund Pool window
* B-54725 FDA: View/Delete AACL Fund Pool: implement View Fund Pool window
* B-54725 FDA: View/Delete AACL Fund Pool: implement repository/service logic for deleting AACL fund pool
* B-54725 FDA: View/Delete AACL Fund Pool: implement repository/service logic for reading AACL fund pool
* B-54725 FDA: View/Delete AACL Fund Pool: make changes based on comments in CR-DIST-FOREIGN-145
* B-54725 FDA: View/Delete AACL Fund Pool: remove missing setFundPoolId method usage
* B-55624 FDA: Modify Usage Tab export for AACL: add button to export AACL usages to CSV file
* B-55624 FDA: Modify Usage Tab export for AACL: implement controller logic related to export AACL usages to CSV file
* B-55624 FDA: Modify Usage Tab export for AACL: implement repository logic
* B-55624 FDA: Modify Usage Tab export for AACL: implement service logic related to export AACL usages to CSV file
* B-55624: FDA: Modify Usage Tab export for AACL: make changes based on CR-DIST-FOREIGN-144
* B-56093 Tech Debt: FDA: introduce separate FDA repository and mapper for FAS specific logic
* B-56093 Tech Debt: FDA: introduce specific methods for NTS and FAS product families for sending scenarios to LM
* B-56093 Tech Debt: FDA: move NTS and FAS specific services to corresponding packages
* B-56093 Tech Debt: FDA: move NTS specific logic to separate service and repository on Usage level
* B-56093 Tech Debt: FDA: move NTS specific logic to separate service and repository on Usage level
* B-56093 Tech Debt: FDA: move NTS specific logic to separate service and repository on Usage level and fix issue related to check style
* B-56093 Tech Debt: FDA: refresh context after each integration test class invocation
* B-56093 Tech Debt: FDA: remove resolved TODO from IFundPoolRepository
* B-56093 Tech Debt: FDA: rename NTS fund pool handler, serializer and deserializer
* B-56093 Tech Debt: FDA: unify naming of methods in FundPoolService
* B-56093 Tech Debt: FDA: update commonScripts version to 3.5.2
* B-56093 Tech Debt: FDA: update commonScripts version to 3.5.3
* B-56265 FDA: View & Delete AACL Batch: implement window to view and delete AACL batches
* CDP-889 FDA: Undistributed Liabilities Reconciliation Report for FAS/FAS2: NTS RROs are displayed in the report
* CDP-890 FDA: NTS classification: There are duplicates on classification window for 30k+ unique works

8.1.23
-
* B-52329 FDA: Load classified AACL usages: add publication type to AaclClassifiedUsage toString() method
* B-52329 FDA: Load classified AACL usages: adjust logic so Comment field is updated during the upload
* B-56092 Tech Debt: FDA: move NTS update methods to separate service and repository

8.1.22
-
* B-52329 FDA: Load classified AACL usages: make changes based on CR-DIST-FOREIGN-143
* B-56092 Tech Debt: FDA: move NTS select methods to separate service and repository

8.1.21
-
* B-56092 Tech Debt: FDA: move NTS delete methods to separate service and repository
* B-56092 Tech Debt: FDA: apply 49.3.+ version of dist-common
* CDP-884 FDA: Load classified AACL details: Audit records are not deleted from FDA database for disqualified usages

8.1.20
-
* B-52239 FDA: Load classified AACL usages: implement service logic to handle uploaded classified usage
* B-52329 FDA: Load classified AACL usages: add button for uploading classified AACL usages
* B-52329 FDA: Load classified AACL usages: adjust query in IAaclUsageMapper related to update usages
* B-52329 FDA: Load classified AACL usages: apply classified usage validators
* B-52329 FDA: Load classified AACL usages: enable sorting for Detail Licensee Class Id, Enrollment Profile, Discipline and Publication Type columns on UI
* B-52329 FDA: Load classified AACL usages: implement repository logic to handle uploaded classified usage
* B-52329 FDA: Load classified AACL usages: implement window for uploading classified AACL usages
* B-52329 FDA: Load classified AACL usages: make classified AACL usage validator case insensitive
* B-52329 FDA: Load classified AACL usages: use service method in controller to process classified AACL usages
* B-56092 Tech Debt: FDA: OWASP Dependency Check: Security Vulnerabilities in jackson-databind
* B-56092 Tech Debt: FDA: implement templates for NTS mapper, repository, service
* B-56092 Tech Debt: FDA: move NTS insert methods to separate service and repository
* B-56092 Tech Debt: FDA: replace deprecated @TransactionConfiguration with @Rollback in integration tests
* CDP-881 FDA: Incorrect data set presented in scenario's details report for NTS distribution in production
* CDP-882 FDA: Scenarios tab: Exception occurs after selecting empty scenario (which doesn't have any usage)

8.1.19
-
* B-52329 FDA: FDA: Load classified AACL usages: implement Discipline and Enrollment Profile validator
* B-52329 FDA: Load classified AACL usages: add publication_type_uid and detail_licensee_class_id columns in df_usage_aacl
* B-52329 FDA: Load classified AACL usages: implement publication type validator
* B-52333 FDA: Send AACL usages for classification: make changes based on comments in CR-DIST-FOREIGN-142
* B-56092 Tech Debt: FDA: apply insert and delete AACL specific methods on service layer
* B-56092 Tech Debt: FDA: move AACL related logic for getting usage dtos and count to separate repository and service
* B-56092 Tech Debt: FDA: move update processed AACL usages logic to AACL related classes
* B-56092 Tech Debt: FDA: place getAaclUsagePeriods method to AACL specific service
* CDP-879 FDA: Send for Classification: Exception occurs when the user tries to send AACL usages for classification with applied 'Usage Period' fiter
* CDP-880 FDA: Research Status Report for FAS/FAS2: AACL batches are displayed in the report

8.1.18
-
* B-52329 FDA: Load classified AACL usages: implement classified usages csv processor

8.1.17
-
* B-52329 FDA: Load classified AACL usages: implement classified usage validator
* B-52329 FDA: Load classified AACL usages: implement wrWrkInst validator
* B-52333 FDA: Send AACL usages for classification: add party-changelog-8 changelog to the main-party-changelog

8.1.16
-
* B-52330 FDA: Get Rights Information from RMS for AACL usages: make changes based on comments in CR-DIST-FOREIGN-140
* B-52333 FDA: Send AACL usages for classification: Adjust repository logic and add service logic for sending AACL usages for classification, UI button and permissions
* B-52333 FDA: Send AACL usages for classification: fix batch period end date being always empty in exported CSV
* B-54664 FDA: AACL Retrieve System Title, Standard Number and Standard Number Type: make changes based on comments in CR-DIST-FOREIGN-139
* B-55751 FDA: Load Pub Types: create NOT NULL constraint for name column in df_publication_type table
* B-55751 FDA: Load Pub Types: fix database script
* B-56092 Tech Debt: FDA: Apply last version of dist-common
* B-56092 Tech Debt: FDA: eliminate obsolete dist.foreign.product_families property
* B-56092 Tech Debt: FDA: introduce separate repository for AACL product family
* B-56092 Tech Debt: FDA: move aacl related repository methods to AaclUsageRepository

8.1.15
-
* B-52330 FDA: Get Rights Information from RMS for AACL usages (get grants): introduce rights processor for AACL product family
* B-52330 FDA: Get Rights Information from RMS for AACL usages (get grants): refine cache service to process AACL product family
* B-55751 FDA: Load Pub Types: create df_publication_type table and populate columns
* B-56092 Tech Debt: FDA: Apply last version of dist-common
* B-52333 FDA: Send AACL usages for classification: Add repository functionality to export AACL usages
* B-55751 FDA: Load Pub Types: make changes based on comments in CR-DIST-FOREIGN-141

8.1.14
-
* B-54664 FDA: AACL Retrieve System Title, Standard Number and Standard Number Type: implement service logic for sending AACL usage for matching
* B-54664 FDA: AACL Retrieve System Title, Standard Number and Standard Number Type: adjust integration tests to cover matching for AACL
* B-52330 FDA: Get Rights Information from RMS for AACL usages (get grants): implement RMS service to apply grants for AACL product family

8.1.13
-
* B-54664 FDA: AACL Retrieve System Title, Standard Number and Standard Number Type: add AACL workflow test
* B-54664 FDA: AACL Retrieve System Title, Standard Number and Standard Number Type: implement service logic for matching usages by Wr Wrk Inst
* B-55538 FDA: Create Licensee Class Mapping: create and populate df_aggregate_licensee_class table
* B-55538 FDA: Create Licensee Class Mapping: create and populate df_detail_licensee_class table
* B-56091 Tech Debt: FDA: resize grid's columns size once scroll is disappeared on View Usage Batch and View Fund Pool windows

8.1.12
-
* B-55412 FDA: Load AACL Usage Data: make changes based on comments in CR-DIST-FOREIGN-136
* B-55412 FDA: Load AACL Usage Data: rename Distrubution Period field to Period End Date (YYYY) on Upload Usage Batch window
* B-56091 FDA: fix sorting on Scenarios, Usage History, View Usage Batch and View Fund Pool grids
* B-56091 Tech Debt: FDA: Show batches for selected product family only in Summary of Market report window
* B-56091 Tech Debt: FDA: Update Tomcat version to 8.5.50 in order to resolve vulnerability issues
* B-56091 Tech Debt: FDA: apply common CSV converter
* CDP-872 FDA: Ownership Adjustment Report: It is able to run report for NTS scenarios from FAS/FAS2 product families global filter
* CDP-876 FDA: FAS usage batch record not loaded - title beginning with #
* Revert "CDP-872 FDA: Ownership Adjustment Report: It is able to run report for NTS scenarios from FAS/FAS2 product families global filter"

8.1.11
-
* B-55070 FDA: Modify Usage Tab View for AACL: fill value of Period End Date column in Usages table
* B-55070 FDA: Modify Usage Tab View for AACL: replace in Usage filter Period End Date date widget with Usage Period combobox widget
* B-55412 FDA: Load AACL Usage Data: rename period field on AACL upload window, populate usage periods as a year
* B-56091 Tech Debt: FDA: Update dist-common version to 48.4.+
* B-56091 Tech Debt: Fix comments in CR-DIST-FOREIGN-135
* CDP-872 FDA: Ownership Adjustment Report: It is able to run report for NTS scenarios from FAS/FAS2 product families global filter
* CDP-873 FDA: Audit tab: Search result displays records for all product families if no any filter is applied
* CDP-874 FDA: Audit tab: Rightsholders for all product families are displayed on 'Rightsholders' filter despite of selected product family

8.1.10
-
* B-55070 FDA: Modify Usage Tab View for AACL: implement service methods
* B-55412 FDA: Load AACL Usage Data: adjust logic for parsing AACL usage batch to upload columns in any order
* B-56091 Tech Debt: FDA: adjust logic for update rightsholder job to update information about discrepancies
* B-56091 Tech Debt: FDA: resolve issue related to duplicate fundpool name

8.1.9
-
* B-55070 FDA: Modify Usage Tab View for AACL: implement repository methods and MyBatis mappings
* B-55412 FDA: Load AACL Usage Data: implement logic for inserting batch with usages in database
* B-56091 Tech Debt: FDA: Update dist-common version to 48.3.+

8.1.8
-
* B-55070 FDA: Modify Usage Tab View for AACL: add AACL product family in the dropdown
* B-55070 FDA: Modify Usage Tab View for AACL: fix PMD rule, increase number of allowed fields
* B-55070 FDA: Modify Usage Tab View for AACL: implement usages filter on Usage tab
* B-55070 FDA: Modify Usage Tab View for AACL: implement usages table on Usage tab
* B-55412 FDA: Load AACL Usage Data: implement AACL usage csv processor
* B-55412 FDA: Load AACL Usage Data: implement liquibase script to create df_usage_aacl table
* B-55412 FDA: Load AACL Usage Data: implement logic to insert AACL usages
* B-55412 FDA: Load AACL Usage Data: introduce AACL batch upload window
* B-55412 FDA: Load AACL Usage Data: introduce AACL usage domain
* CDP-869 FDA: Exclude by Payee: There are duplicates with participating flag ‘N’ in ‘Exclude Details by Payee’ table after scenario refresh with usages with the same RH and Payee

8.1.7
-

8.1.6
-
* B-55626 FDA: Globalization of Product Family: adjust export on audit tab
* B-55626 FDA: Globalization of Product Family: fix wrong behavior of clear Audit filter button
* B-55626 FDA: Globalization of Product Family: implement product specific Audit tab
* B-55836 FDA: Database changes to support AACL: make changes based on comments in CR-DIST-FOREIGN-133
* CDP-858 FDA: Summary of Market Report: Exception occurs when the user tries to generate Summary of Market Report for an NTS batch
* CDP-868 FDA: NTS_EXCLUDED usages are not deleted from df_usage_fas table after sending scenario to LM
* CDP-869 FDA: Exclude by Payee: There are duplicates with participating flag ‘N’ in ‘Exclude Details by Payee’ table after scenario refresh with usages with the same RH and Payee
* CDP-870 FDA: NTS Scenario view: Export and search are not disabled for empty NTS scenarios, empty scenario label is not displayed

8.1.5
-
* B-55626 FDA: Globalization of Product Family: adjust and reorganize drill down widgets and controllers
* B-55626 FDA: Globalization of Product Family: adjust and reorganize scenario widgets and controllers
* B-55626 FDA: Globalization of Product Family: adjust and reorganize scenarios widgets and controllers
* B-55626 FDA: Globalization of Product Family: adjust upload researched and exported usages functionalities
* B-55626 FDA: Globalization of Product Family: adjust usage filter widget and controller to make them product family specific
* B-55626 FDA: Globalization of Product Family: fix failed build
* B-55626 FDA: Globalization of Product Family: implement product specific Reports tab
* B-55626 FDA: Globalization of Product Family: reorganize columns in refresh scenario window and in exported from scneario usages files
* B-55626 FDA: Globalization of Product Family: reorganize common components for Usages tab and introduce product family specific packages
* B-55626 FDA: Globalization of Product Family: reorganize methods in usage related controllers by product families

8.1.4
-
* B-55836 FDA: Database changes to support AACL: adjust queries for deleting usages to remove additional FAS fields

8.1.3
-
* B-55626 FDA: Globalization of Product Family: Add FAS Scenarios widget and controller
* B-55626 FDA: Globalization of Product Family: Add NTS Scenarios widget and controller
* B-55626 FDA: Globalization of Product Family: Add common Scenarios widget and controller
* B-55626 FDA: Globalization of Product Family: Add interfaces for Scenarios widgets and controllers
* B-55626 FDA: Globalization of Product Family: Add mediators for Scenarios widgets
* B-55626 FDA: Globalization of Product Family: Adjust expected scenario version in RefreshScenarioTest
* B-55626 FDA: Globalization of Product Family: Adjust findWithAmountsAndLastAction and findArchivedWithAmountsAndLastAction repository methods to retrieve product family and common entity fields
* B-55626 FDA: Globalization of Product Family: Fix refresh functionality
* B-55626 FDA: Globalization of Product Family: Make scenario detail export product family specific
* B-55626 FDA: Globalization of Product Family: Use product family specific widgets in Scenarios tab
* B-55626 FDA: Globalization of Product Family: fix FAS and NTS usage csv report handlers

8.1.2
-
* B-55560 Tech Debt: FDA: update CAS url property, update PI index property
* B-55626 FDA: Globalization of Product Family: Add FAS Scenario widget and controller
* B-55626 FDA: Globalization of Product Family: Add NTS Scenario widget and controller, remove NTS related test from FasScenarioMediatorTest
* B-55626 FDA: Globalization of Product Family: Add common Scenario widget and controller
* B-55626 FDA: Globalization of Product Family: Add interfaces for Drill-Down by Rightsholder widgets and controllers
* B-55626 FDA: Globalization of Product Family: Add interfaces for product family specific Scenario controllers and widgets
* B-55626 FDA: Globalization of Product Family: Fix expected exception message in CommonControllerProviderTest
* B-55626 FDA: Globalization of Product Family: Fix expected exception message in CommonControllerProviderTest
* B-55626 FDA: Globalization of Product Family: Implement NTS Drill-Down by Rightsholder widget and controller
* B-55626 FDA: Globalization of Product Family: Implement common and FAS Drill-Down by Rightsholder widget and controller
* B-55626 FDA: Globalization of Product Family: Move isScenarioEmpty method to IFasScenarioController
* B-55626 FDA: Globalization of Product Family: adjust export functionality on Usages tab to generate product specific reports
* B-55626 FDA: Globalization of Product Family: fix test data for CsvReportsIntegrationTest
* B-55626 FDA: Globalization of Product Family: get rid of redundant usage of product family provider
* B-55626 FDA: Globalization of Product Family: implement FAS and NTS usages mediators
* B-55626 FDA: Globalization of Product Family: implement FAS usage controller and widget
* B-55626 FDA: Globalization of Product Family: implement NTS usage controller and widget
* B-55626 FDA: Globalization of Product Family: implement common logic for switching between product families
* B-55626 FDA: Globalization of Product Family: implement common usages widget and controller
* B-55626 FDA: Globalization of Product Family: reorganize approach to selecting product family specific controller
* B-55626 FDA: Globalization of Product Family: replace UsagesWidget and UsagesController with product specific implementations
* B-55836 FDA: Database changes to support AACL: add columns to df_usage_fas and fix default value
* B-55836 FDA: Database changes to support AACL: add primary key into df_usage_fas
* B-55836 FDA: Database changes to support AACL: adjust adding usages to scenario for NTS
* B-55836 FDA: Database changes to support AACL: adjust additional fund workflow for NTS
* B-55836 FDA: Database changes to support AACL: adjust logic for exporting usages from scenario
* B-55836 FDA: Database changes to support AACL: adjust logic for getting archived usages by ids for marking as paid
* B-55836 FDA: Database changes to support AACL: adjust logic for inserting NTS usages
* B-55836 FDA: Database changes to support AACL: adjust logic for inserting paid usages
* B-55836 FDA: Database changes to support AACL: adjust logic for summary market report
* B-55836 FDA: Database changes to support AACL: adjust logic for viewing usages by scenario
* B-55836 FDA: Database changes to support AACL: adjust logic to audit filter
* B-55836 FDA: Database changes to support AACL: adjust new table for specific fields for FAS
* B-55836 FDA: Database changes to support AACL: adjust queries for deleting usages from scenarios
* B-55836 FDA: Database changes to support AACL: adjust queries related to getting usages by rightsholders
* B-55836 FDA: Database changes to support AACL: adjust usage batch workflow for FAS
* B-55836 FDA: Database changes to support AACL: adjust usage export workflow for FAS, NTS
* B-55836 FDA: Database changes to support AACL: apply sorting base on new column for scenario usages, remove temp functionality
* B-55836 FDA: Database changes to support AACL: create new database for FAS specific fields
* B-55836 FDA: Database changes to support AACL: create new database for FAS specific fields
* B-55836 FDA: Database changes to support AACL: remove redundant FAS specific columns from df_usage and df_usage_archived tables
* B-55836 FDA: Database changes to support AACL: remove unused repository method for inserting archived usages

8.1.1
-
* B-55560 Tech Debt: FDA: adjust sorting by String values on work classification window
* CDP-852 FDA: Several exceptions while sending FAS/FAS2 details to CRM in production: revert reading rights distribution from CRM before writing them
* CDP-855 FDA production: Various performance issues related to NTS Classification
* CDP-855 FDA production: Various performance issues related to NTS Classification: apply lazy loading for work classifications based on number of selected works
* CDP-860 FDA: Assign Classification: Exception occurs when the user tries to update the classification of a work

8.1.0
-
* B-53554 Tech Debt: CDP: upgrade jackson-databind version to 2.9.10.1, upgrade dist-common version to 48.2.+, update Spring XML schema version in config files
* B-53554 Tech Debt: FDA: add consistent data for CsvReportIntegrationTest
* B-53554 Tech Debt: FDA: replace property in web.xml related to aplly new rup auth version and apply new spring version 4.3.25.RELEASE
* B-53554 Tech Debt: FDA: apply new rup auth version 8.+
* B-53752 FDA: NTS withdrawn batch summary report: make changes based on CR-DIST-FOREIGN-131
* B-53752 FDA: NTS withdrawn batch summary report: add NTS Withdrawn Batch Summary Report to Reports tab
* B-53752 FDA: NTS withdrawn batch summary report: implement service logic for generating batch summary report
* B-53752 FDA: NTS withdrawn batch summary report: implement repository logic for generating batch summary report
* B-53752 FDA: NTS withdrawn batch summary report: implement NTS withdrawn report handler
* B-53752 FDA: NTS withdrawn batch summary report: create DB view for generating batch summary report
* B-53554 Tech Debt: FDA: Apply rup-vaadin 8

7.1.14
-
* CDP-860 FDA: Assign Classification: Exception occurs when the user tries to update the classification of a work

7.1.13
-
* CDP-855 FDA production: Various performance issues related to NTS Classification: apply lazy loading for work classifications based on number of selected works
* CDP-855 FDA production: Various performance issues related to NTS Classification

7.1.12
-
* B-53554 Tech Debt: FDA: upgrade jackson-databind version to 2.9.10.1, upgrade dist-common version to 48.2.+, update Spring XML schema version in config files
* CDP-852 FDA: Several exceptions while sending FAS/FAS2 details to CRM in production: revert reading rights distribution from CRM before writing them

7.1.11
-
* B-50006 FDA: Exclude details from FAS scenario at the Payee level: make changes based on comments in CR-DIST-FOREIGN-130

7.1.10
-
* B-50006 FDA: Exclude details from FAS scenario at the Payee level: make changes based on comments in CR-DIST-FOREIGN-130
* B-54041 Tech Debt:  FDA: place Load Date column right after Payment Date in FAS batch summary report

7.1.9
-
* B-54041 Tech Debt: FDA: get and store RRO information into RHs table while loading NTS Fund Pool
* B-54041 Tech Debt: LM: apply new dist-common version 47.1.+

7.1.8
-
* B-50006 FDA: Exclude details from FAS scenario at the Payee level: replace column Service Fee % with Participating and add Clear button on Exclude Details By Payee window
* B-54041 Tech Debt: FDA: fix 'No log4j2 configuration file found' log error message during startup

7.1.7
-
* B-50006 FDA: Exclude details from FAS scenario at the Payee level: implement validation for Minimum Threshold field, adjust sorting of amounts fields on Exclude Details By Payee window

7.1.6
-
* B-46037 FDA: Exclude STM RHs from the NTS batch: make changes based on comments in CR-DIST-FOREIGN-128
* B-50006 FDA: Exclude details from FAS scenario at the Payee level: implement service logic to exclude and redesignate details
* B-51245 FDA: Update Classification View to include 2 new columns: make changes based on CR-DIST-FOREIGN-127

7.1.5
-
* B-51245 FDA: Update Classification View to include 2 new columns: repair CsvreportsIntegrationTest
* B-54104 FDA: Add Date Loaded column to the Batch Summary Report: Add Date Loaded column to the Report
* B-50006 FDA: Exclude details from FAS scenario at the Payee level: implement logic to populate grid on Exclude Details By Payee window
* B-50006 FDA: Exclude details from FAS scenario at the Payee level: implement filter on Exclude Details by Payee window
* CDP-844 FDA: Export from Works Classification window: report exported from Works Classification window doesn’t contain Classification Date and Classified By columns

7.1.4
-
* B-50006 FDA: Exclude details from FAS scenario at the Payee level: implement Exclude Details by Payee window
* B-46037 FDA: Exclude STM RHs from the NTS batch: implement service logic for excluding usage with STM RHs
* B-54040 Tech Debt: FDA: adjust logic for applying rh participation status during scenario creation

7.1.3
-
* B-50006 FDA: Exclude details from FAS scenario at the Payee level: implement service logic to retrieve payee participation status during scenario creation and refreshing
* B-51245 FDA: Update Classification View to include 2 new columns: fix sql exception when user clicks mark as STM
* B-54040 Tech Debt: FDA: apply updated rollup service to get roll up by batches
* B-50006 FDA: Exclude details from FAS scenario at the Payee level: implement service logic to retrieve payee participation status during reconciliation
* B-50006 FDA: Exclude details from FAS scenario at the Payee level: adjust View Scenario window
* B-51245 FDA: Update Classification View to include 2 new columns: add format date in Classified Date column
* B-51245 FDA: Update Classification View to include 2 new columns: fix WorkClassificationWindowTest

7.1.2
-
* B-46037 FDA: Exclude STM RHs from the NTS batch: add Exclude STM RHs checkbox to Load Fund Pool Window
* B-46037 FDA: Exclude STM RHs from the NTS batch: add excludingStm flag to FundPool, add repository and service methods to get batch by id
* B-46037 FDA: Exclude STM RHs from the NTS batch: implement service logic for getting IS-RH-STM-IPRO preference
* B-46037 FDA: Exclude STM RHs from the NTS batch: use correct values in UsageBatchRepositoryIntegrationTest.testFindById
* B-50006 FDA: Exclude details from FAS scenario at the Payee level: add is_payee_participating column to df_usage table and adjust repository logic to store payee participating flag
* B-51245 FDA: Update Classification View to include 2 new columns: add updated date and updated by user columns to Works Classification view
* B-51245 FDA: Update Classification View to include 2 new columns: replace properties in config file
* B-54040 Tech Debt: FDA: Update dist-common version to 45.1.+
* B-54040 Tech Debt: FDA: get and store RRO information into RHs table while loading NTS Fund Pool
* Revert "B-54040 Tech Debt: FDA: get and store RRO information into RHs table while loading NTS Fund Pool"

7.1.1
-
* B-54040 Tech Debt: FDA: Increase consumer latch awaiting timeout in integration tests
* CDP-824 FDA: Upgrade to RUP Common 7, update dist-common version to 45.0.+

7.1.0
-
* B-51662 Tech Debt: FDA: replace Camel redelivery policies with AWS redrive policies
* B-51779 FDA: Mitigate CRM job failure due to connection issues: make changes based on comments in CR-DIST-FOREIGN-124
* B-53571 Tech Debt: FDA: Use SnsNotificationMessageConverter from dist-common
* B-53571 Tech Debt: FDA: apply dist-common 42.0.2 version
* B-53571 Tech Debt: FDA: rename liquibase test data file for SendFasToCrmintegrationTest
* B-53893 Tech Debt: FDA: resolve vulnerability issue related to postgres driver
* B-53893 Tech Debt: FDA: revert postgres driver version to 42.2.1
* B-53893 Tech Debt: FDA: update commonSchema version to 3.5.0 for rup-db-postgres dependency
* B-53893 Tech Debt: FDA: update rupJdbc.standardDependencies.postgresDriver.version to 42.2.6 to resolve vulnerability issue related to postgres driver
* B-53893 Tech Debt: apply 6.7.+ rup-common version
* CDP-811 FDA: Load researched details: Usage remains in WORK_FOUND status after researched details upload, if another usage with the same wr_wrk_inst is already present in usage batch with any different detail status

6.1.21
-
* Revving up build version

6.0.20
-
* CDP-807 FDA: Send to LM: Duplicate messages are sent to LM for scenarios having more than 2500 usages

6.0.19
-
* B-51779 FDA: Mitigate CRM job failure due to connection issues: implement reading rights distribution from CRM
* B-51779 FDA: Mitigate CRM job failure due to connection issues: refactor sending usages to CRM by reading rights distribution before writing them
* B-53571 Tech Debt: FDA: resolve Security Vulnerabilities in jackson-databind dependency

6.0.18
-
* B-53527 FDA: Remove the standard number requirement when loading researched works: Remove standard number validation, read standard number type from researched CSV

6.0.17
-
* B-51809 FDA: Consume updated rightsholders from CDP Pre-Distribution: make changes based on comments in CR-DIST-FOREIGN-124

6.0.16
-
* B-51809 FDA: Consume updated rightsholders from CDP Pre-Distribution: implement details update after RH change without Split process
* B-51809 FDA: Consume updated rightsholders from CDP Pre-Distribution: implement details update after RH change with Split process

6.0.15
-
* B-51662 Tech Debt: FDA: Skip from Splunk alerts the case, when user cancels report download in browser
* B-51662 Tech Debt: FDA: Update release notes for 6.0.14 version

6.0.14
-
* B-51662 Tech Debt: FDA: display empty batch gross amount for NTS product family
* B-51662 Tech Debt: FDA: apply rup-vaadin 7.8.0
* B-52861 FDA: Update fields on Audit Tab: make changes based on comments in CR-DIST-FOREIGN-123

6.0.13
-
* B-43660 Tech Debt: FDA: Update release notes for version 6.0.12
* B-51662 Tech Debt: FDA: display empty batch gross amount for NTS product family

6.0.12
-
* B-52861 FDA: Update fields on Audit Tab: add columns in audit tab

6.0.11
-
* B-52861 FDA: Update fields on Audit Tab: Add reported_value and batch_gross_amount columns to repository methods that retrieve usages for audit
* CDP-795 FDA: Export scenario details: 'Batch amt in USD' column displays gross usage amt in exported file instead of gross batch amount

6.0.10
-
* B-51660 Tech Debt: FDA: Update amounts in SendFasToCrmIntegrationTest
* B-51660 Tech Debt: FDA: mock OffsetDateTime.now() in unit tests
* B-51660 Tech Debt: FDA: update PRM urls
* B-52861 FDA: Update fields on Audit Tab: FDA: Rename "Gross Amt in USD" column

6.0.9
-
* B-51660 Tech Debt: FDA: resolve declaration access warnings
* B-51660 Tech Debt: FDA: resolve security vulnerabilities in tomcat

6.0.8
-
* B-44258 FDA: Distribute NTS through LM/CDP: implement test to receive paid usages from LM for NTS
* B-50690 FDA: Report NTS distributions to RC: Adjust NtsWorkflowIntegrationTest to accept paid info and report to CRM
* B-50690 FDA: Report NTS distributions to RC: make changes based on comments in CR-DIST-FOREIGN-121
* B-51660 Tech Debt: FDA: replace Set with EnumSet in status widget filter
* B-51660 Tech Debt: FDA: resolve declaration redundancy warnings on UI layer
* B-51660 Tech Debt: FDA: resolve declaration redundancy warnings on repository layer
* B-51660 Tech Debt: FDA: resolve declaration redundancy warnings on service layer
* B-52269 FDA: Allow users to export list of NTS eligible works with classifications: make changes based on comments in CR-DIST-FOREIGN-120

6.0.7
-
* B-51660 Tech Debt: FDA: resolve final modifier declaration warnings
* B-51660 Tech Debt: FDA: resolve functional expression warning
* B-51660 Tech Debt: FDA: resolve redundant throws warnings
* B-52269 FDA: Allow users to export list of NTS eligible works with
classifications: add Export button to Work Classification window, allow
to export works classification

6.0.6
-
* B-50690 FDA: Report NTS distributions to RC: Avoid sending serviceNameReporting field for NTS, adjust SendToCrmIntegrationTest to cover NTS case
* B-51660 Tech Debt: FDA: eliminated deprecated API usage
* B-51660 Tech Debt: FDA: resolve JUnit warnings
* B-51660 Tech Debt: FDA: resolve Security Vulnerability issue with com.mchange:c3p0 dependency
* B-51660 Tech Debt: FDA: resolve declaration redundancy warnings in audit and usage filter widgets
* B-51660 Tech Debt: FDA: resolve javadoc errors
* B-51661 Tech Debt: FDA: resolve groovy style warnings
* B-52269 FDA: Allow users to export list of NTS eligible works with classifications: implement service logic to export works classification

6.0.5
-
* B-51661 Tech Debt: FDA: refactoring reports to use OnDemandFileDownloader constructor with arguments without Vaadin dependencies

6.0.4
-
* B-51661 Tech Debt: FDA: fix issue
* B-51661 Tech Debt: FDA: move common assert usage methods to service tеst helper
* B-51661 Tech Debt: FDA: move repository logic related to exporting usages to report repository and mapper
* B-51661 Tech Debt: FDA: rename all methods in CreateNtsBatchIntegrationTest
* CDP-777 FDA: Drill-down by Rightsholder on Scenario View: Gross Amt in USD column is not populated for FAS/FAS2 scenarios

6.0.3
-
* B-51661 Tech Debt: FDA: add environment, host, IP for notification for Scheduled jobs
* B-51661 Tech Debt: FDA: introduce separate mapper and repository for report generation
* CDP-776 FDA: Send NTS scenario to LM: Audit records for NTS usages deleted after sending NTS scenario to LM are still presented in df_usage_audit table

6.0.2
-
* B-23120 FDA: Apply additional Pre Service fee funds: make changes based on comments in CR-DIST-FOREIGN-116
* B-51661 Tech Debt: FDA: Move assertion methods to service test helper
* B-51661 Tech Debt: FDA: fix checkstyle issue
* B-51661 Tech Debt: FDA: reuse components for generating reports from Common
* B-52145 FDA: Send NTS details to LM: make changes based on comments in CR-DIST-FOREIGN-118
* CDP-773 FDA: Send NTS scenario to LM: Newly created usages aren't displayed on Rightsholder drill-down after NTS scenario is sent to LM
* CDP-774 FDA: Create NTS scenario: Payee is not received from PRM for NTS scenario
* CDP-775 FDA: Exception occurs when user tries to delete batch associated with additional funds, after scenario containing these funds is sent to LM

6.0.1
-
* B-23120 FDA: Apply additional preservice fee funds: implement functionality to display only available for attaching to scenario Pre Service fee funds
* B-23120 FDA: Apply additional preservice fee funds: implement logic for moving TO_BE_DISTRIBUTED usage to archive table during sending scenario to LM
* B-51661 Tech Debt: FDA: create ServiceTestHelper and move REST-expectation methods
* B-51661 Tech Debt: FDA: introduce common approach for generating CSV reports
* B-52145 FDA: Send NTS details to LM: implement integration test for sending NTS scenario to LM
* B-52145 FDA: Send NTS detals to LM: implement service logic for grouping NTS usages by RH and sending to LM
* CDP-769 FDA: Delete batch/fundpool functionality is available for Manager and View Only roles

6.0.0
-
* B-23118 FDA: Calculate the service fee for NTS details for each RH: Implement query to retrieve usages for NTS scenario service fee calculation
* B-23118 FDA: Calculate the service fee for NTS details for each RH: Implement service logic to calculate service fee for NTS details
* B-23118 FDA: Calculate the service fee for NTS details for each RH: Implement service logic to calculate service fee for NTS details
* B-23120 FDA: Apply additional preservice fee funds: add Pre/Post Service fee amounts and Pre Service fee fund fields on create NTS scenario window
* B-23120 FDA: Apply additional preservice fee funds: add verification for NTS fields
* B-23120 FDA: Apply additional preservice fee funds: apply pre serivce fee calculation for NTS scenarios creation process
* B-23120 FDA: Apply additional preservice fee funds: prevent deleting of Fund pool associated with scenarios
* B-48867 FDA: Apply additional post-service fee funds: Adjust service logic to distribute Post Service Fee Amount
* B-48867 FDA: Apply additional post-service fee funds: refactor CreateScenarioIntegrationTest to be able to assert usages amounts
* B-48867 FDA: Apply additional post-service fee funds: split NtsWorkflowIntegrationTest into CreateNtsBatchIntegrationTest and NtsScenarioWorkflowIntegrationTest

5.1.44
-
* CDP-766 FDA: Works Classification window: System doesn't apply 'Select All' works selection after clearing search

5.1.43
-
* CDP-764 FDA: Payee information is not populated while getting rollups after reconciliation
* CDP-765 FDA: Assign classification: Exception occurs when the user tries to assign classification for all works in 'Works Classification' window

5.1.42
-
* B-51553 FDA: Export from RH reconcile view: make changes based on
comments in CR-DIST-FOREIGN-111
* B-51659 Tech Debt: FDA: reuse service logic instead repositories for
creating Withdrawn Funds, updating usages during RA job
* CDP-762 FDA: View Usage Batch: Sorting on ‘Create Date’ column is
incorrect

5.1.41
-
* B-51659 Tech Debt: FDA: make changes based on comments in CR-DIST-FOREIGN-109
* B-50669 FDA: View NTS Fund Pools Information: make changes based on comments in CR-DIST-FOREIGN-113
* CDP-757 FDA: Export from RH reconcile view: Ownership Adjustment Report displays unapproved rightsholder discrepancies for scenarios in APPROVED, SENT_TO_LM and ARCHIVED statuses
* B-51659 Tech Debt: FDA: adjust grids on View Fund Pool, View Usage Batch and Work Classification windows to make them resizable
* B-51659 Tech Debt: FDA: use common components for Job Status notification logic from dist-common
* B-50684 FDA: Send FAS Batch Name to RMS as RMS Job Name: make changes based on comments in CR-DIST-FOREIGN-112
* B-51553 FDA: Export from RH reconcile view: make changes based on comments in CR-DIST-FOREIGN-111
* CDP-758 FDA: NTS Withdrawn determination: Usages with the same wr wrk inst get both NTS_WITHDRAWN and RH_NOT_FOUND statuses

5.1.40
-
* B-50669 FDA: View NTS Fund Pools Information: Remove Delete Usage Batch window and button from Usages tab
* B-50669 FDA: View NTS Fund Pools Information: add View Usage Batch and View Fund Pool windows
* B-51553 FDA: Export from RH reconcile view: implement the Ownership Adjustment Report dialog called from Reports tab to select scenario

5.1.39
-
* B-50684 FDA: Send FAS Batch Name to RMS as RMS Job Name: adjust logic for RA job to send batch name as job name parameter
* B-51553 FDA: Export from RH reconcile view: implement UI on Reconcile Rightsholders dialog to generate report
* B-51659 Tech Debt: FDA: configure sqs/sns endpoints in rup-env.properties file instead of context file

5.1.38
-
* B-50669 FDA: View NTS Fund Pools Information: add Usage Batch and Fund Pool menu bars, remove Load Usage Batch and Load Fund Pool buttons
* B-51552 FDA: Update NTS Withdrawn rules related to Rights Assignment: implement integration test to cover upload usage functionality for different statuses
* B-51552 FDA: Update NTS Withdrawn rules related to Rights Assignment: implement service logic to mark usage as NTS_WITHDRAWN if total gross amount by Wr Wrk Inst under minimum

5.1.37
-
* B-50684 FDA: Send FAS Batch Name to RMS as RMS Job Name: implement service methods for getting batch and works information for usages available for RA
* B-51553 FDA: Export from RH reconcile view: implement service method to generate report
* B-51659 Tech Debt: FDA: make changes based on comments in CR-DIST-FOREIGN-108

5.1.36
-
* B-50684 FDA: Send FAS Batch Name to RMS as RMS Job Name: implement repository method for getting map of batch names to set of Wr Wrk Insts available for RA
* B-51570 FDA : Add audit for RH update after RH Reconciliation: add audit if RH was updated during RH reconciliation

5.1.35
-
* B-50635 FDA: Consume Oracle A/P service to determine RH tax country:
make changes based on comments in CR-DIST-FOREIGN-106
* B-51256 FDA: Generate notification for Scheduled jobs: adjust jobs that
use JobExecutor to store job execution result in context
* B-51256 FDA: Generate notifications for Scheduled jobs: send
notifications about execution status for Send for RA, Update Rights for
sent for RA usages and Send to CRM jobs
* B-51256 FDA: Generate notifications for Scheduled jobs: send
notifications about execution status for Update RHs job
* B-51258 FDA: Adjust Batch Summary Report to be FAS specific: rename CLA
to FAS2 in report
* B-51364 Tech Debt: FDA: OWASP Dependency Check: Security Vulnerabilities
in guava
* B-51364 Tech Debt: FDA: adjust paid message deserialiser, introduce
rh_uid validation for paid messages, apply logic for populating account
numbers based on ids

5.1.34
-
* B-50350 CDP: Generate notification for Scheduled jobs: fix
dist.foreign.endpoint.job.status.topic property
* B-51256 FDA: Generate notification for Scheduled jobs: implement job
status topic, producer and listener

5.1.33
-
* B-51254 FDA: Enhance FAS Batch reimport logic: apply Wr Wrk Inst
validation for uploading researched usages instead of Standard Number
* B-51254 FDA: Enhance FAS Batch reimport logic: refine headers validation
message, implement integration tests to cover reimport batch
functionality
* B-51364 Tech Debt: FDA: add text “Note: If you modify an already
classified work, please be sure to reload the batch.” to the Works
Classification window
* CDP-741 FDA - FAS Scenario Export buttons in P_QA. One needs to be
renamed
* CDP-741 FDA - FAS Scenario Export buttons in P_QA. One needs to be
renamed

5.1.32
-
* B-50635 FDA: Consume Oracle A/P service to determine RH tax country:
adjust service logic to get RH tax information using new service
* B-51254 FDA: Enhance FAS Batch reimport logic: apply Title and Standard
Number validation if Wr Wrk Inst is not null
* B-51254 FDA: Enhance FAS Batch reimport logic: populate standard number
and work title if empty during PI matching
* B-51254 FDA: Enhance FAS Batch reimport logic: rename Reported Value
header for exported and imported files
* B-51364 Tech Debt: FDA: migrate to the latest versions of prm services

5.1.31
-
* B-51254 FDA: Enhance FAS Batch reimport logic: apply common usage
processor for uploading usage batch, apply headers validation
* B-51258 FDA: Adjust Batch Summary Report to be FAS specific: adjust
report view to include only FAS and CLA batches
* B-51258 FDA: Adjust Batch Summary Report to be FAS specific: adjust UI,
report filename and type names
* CDP-749 FDA: Scenarios table: All scenarios have the same (current)
'Create Date'

5.1.30
-
* B-49464 Tech Debt: FDA: replace rh account number by uid for FDA-LM
integration

5.1.29
-
* B-51364 Tech Debt: FDA: Add Amt in USD column on RH DrillDown window

5.1.28
-
* CDP-735 FDA: NTS Scenario: Several scenarios can be created for the same NTS batch with full RRO fund pool (STM and Non-STM amounts) applied to each scenario

5.1.27
-
* CDP-735 FDA: NTS Scenario: Several scenarios can be created for the same NTS batch with full RRO fund pool (STM and Non-STM amounts) applied to each scenario
* CDP-737 FDA: NTS Scenario: Usages marked as BELLETRISTIC or UNCLASSIFIED after scenario creation, remain in ELIGIBLE status after scenario deletion

5.1.26
-
* B-48300 FDA: Remove under minimum RHs and reallocate funds proportionally: make changes based on comments in CR-DIST-FOREIGN-103
* B-49019 FDA: Create NTS Scenario: make changes based on comments in CR-DIST-FOREIGN-102
* B-49464 Tech Debt: FDA: disable exclude details button for NTS scenarios
* CDP-722 FDA: NTS classification: Usage remains in ELIGIBLE status after it was reclassified as BELLETRISTIC
* CDP-736 FDA: Create NTS scenario: Scenario can be created if batch contains usages with UNCLASSIFIED status

5.1.25
-
* B-46740 FDA: Ensure that there is at least one rightsholder for the corresponding fund pool: make changes based on comments in CR-DIST-FOREIGN-104
* B-49019 FDA: Create NTS Scenario: make changes based on comments in CR-DIST-FOREIGN-102
* B-49464 Tech Debt: FDA: remove parameter fmt from get RH tax url
* B-49464 Tech Debt: FDA: replace '.' with '_' in URLs and unify them for CRM REST

5.1.24
-
* B-48300 FDA: Remove under minimum RHs and reallocate funds proportionally: add scenario creation and sending to LM steps for NTS workflow test
* B-49464 Tech Debt: FDA: create separate urls for Oracle REST
* CDP-716 FDA: Details remain eligible after deleting classification on associated work
* CDP-733 FDA: Scenario metadata panel: ‘RH minimum Amount’ element isn’t displayed on scenario metadata panel right after NTS scenario creation

5.1.23
-
* B-46740 FDA: Ensure that there is at least one rightsholder for the corresponding fund pool: reject NTS scenario creation in case when there is no at least one RH for the corresponding fund pool or there are unclassified usages in selected batches
* CDP-728 FDA: Grants prioritiy: ACLPRINT and ACLDIGITAL product families should be replaced with ACL, JACDCL, MACL, VGW

5.1.22
-
* B-46718 FDA: Apply RRO fund pool to an associated batch: calculate NTS gross amount
* B-48300 FDA: Remove under minimum RHs and reallocate funds proportionally: implement logic for recalculating amount for usages above minimum RH gross amount
* B-49019 FDA: Create NTS Scenario: make changes based on comments in CR-DIST-FOREIGN-102
* B-49464 Tech Debt: FDA: add HTML id 'rh-minimum-amount-field' to the field RH Minimum Amount in CreateNtsScenarioWindow
* CDP-732 FDA: Load Fund Pool: No UNCLASSIFIED usages are included in a Fund Pool in case 'STM Amount' or 'NON-STM Amount' field has 0 value

5.1.21
-
* B-48300 FDA: Remove under minimum RHs and reallocate funds proportionally: implement delete NTS scenario functionality
* B-48300 FDA: Remove under minimum RHs and reallocate funds proportionally: implement logic for removing NTS_EXCLUDED usages during sending scenario to LM
* B-48300 FDA: Remove under minimum RHs and reallocate funds proportionally: remove unused import from scenarios controller
* CDP-719 FDA: Create Scenario: User is able to create scenarios with the same name using leading and trailing spaces
* CDP-727 FDA: Scenario metadata panel: Scenario owner is ‘SYSTEM’ instead of user created it

5.1.20
-
* B-46740 FDA: Ensure that there is at least one rightsholder for the corresponding fund pool: exclude STM/Non-STM usages during loading NTS Fund Pool if corresponding Fund Pool Amount is zero
* B-49019 FDA: Create NTS Scenario: implement NTS scenario creation test method in CreateScenarioTest

5.1.19
-
* B-46740 FDA: Ensure that there is at least one rightsholder for the corresponding fund pool: adjust logic for excluding under minimum unclassified usages by greatest STM or Non-STM Cutoff Amount
* B-49019 FDA: Create NTS Scenario: disable refresh and reconcile buttons on scenarios tab for NTS product family
* B-49019 FDA: Create NTS Scenario: implement service methods to save and load NTS scenarios
* B-49019 FDA: Create NTS Scenario: refactor Scenario Metadata Panel to show value of the field rhMinimumAmount

5.1.18
-
* B-48300 FDA: Remove under minimum RHs and reallocate funds proportionally: introduce NTS_EXCLUDED status in usage and audit filters
* B-49463 Tech Debt: FDA: create separate urls for CRM REST
* CDP-715 FDA: Gross amount should be 0 for NTS details that are not associated to any scenario
* CDP-717 NTS: User is not allowed to enter 0 in STM/Non-STM Amount and STM/Non-STM Minimum amount fields on Load fund pool window

5.1.17
-
* B-49019 FDA: Create NTS Scenario: add nts_fields column to the table df_scenario
* B-49019 FDA: Create NTS Scenario: implement Liqubase script to add JSONB column nts_fields_holder to the table df_scenario
* B-49019 FDA: Create NTS Scenario: implement repository methods to save and load the field rhMinimumAmount
* B-49019 FDA: Create NTS Scenario: refactor Create Scenario dialog to allow zero to be a valid value for the field rhMinimumAmount
* B-50007 FDA: Export Rightsholders from Scenario View: add service logic for export scenario rightsholders
* B-50007 FDA: Export Rightsholders from Scenario View: adjust Export Rightsholders button to export scenario Rhs, rename Export Rightsholders button to Export Scenario
* B-50007 FDA: Export Rightsholders from Scenario View: fix checkstyle error in ScenarioWidgetTest
* B-50007 FDA: Export Rightsholders from Scenario View: rename scenario view Export button to Export Details, add button mocked Export Rightsholders button to scenario view

5.1.16
-
* B-49019 FDA: Create NTS Scenario: implement Create Scenario dialog
* B-49463 Tech Debt: FDA: increase width of Filtered Batches window and place value of Total gross to the rights as for all amounts
* B-49463 Tech Debt: FDA: rename Batches Selector to Batches filter and Save button to Continue on Assign Classification step

5.1.15
-
* B-49463 Tech Debt: FDA: consume dates in ISO format while receiving paid usages message
* B-49463 Tech Debt: FDA: implement selecting and deselecting rows on Works Classification window grid by rows clicking

5.1.14
-
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: make changes based on comments in CR-DIST-FOREIGN-99
* B-50004 FDA: Add Standard Number Type to the usage for classification: make changes based on comments in CR-DIST-FOREIGN-100

5.1.13
-
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: make changes based on comments in CR-DIST-FOREIGN-99
* B-49463 Tech Debt: FDA: move default 18.5% service fee from df_rro_estimated_service_fee_percentage to rup-env.property
* CDP-708 FDA: NTS WD Status and Undistributed liability report don't agree

5.1.12
-
* B-46739 FDA: Remove NTS titles that do not meet the minimum from the fund pool batch: adjust service logic for excluding usages that do not meet fund pool minimum
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: make changes based on comments in CR-DIST-FOREIGN-99
* B-49462 FDA: Add parameters dateFrom and dateTo to the batch statistic REST: make changes based on comments in CR-DIST-FOREIGN-95: delete fields for the CREATED and LOADED statuses

5.1.11
-
*  CDP-707 FDA: Create Additional Funds: fix sorting on Delete UsageBatch/PreService fee fund windows
* B-48995 FDA: Delete NTS withdrawn fund pool: make changes based on comments in CR-DIST-FOREIGN-98
* B-49462 FDA: Add parameters dateFrom and dateTo to the batch statistic REST: correct test for the method UsageAuditService.getBatchesStatisticByDateFromAndDateTo
* B-49462 FDA: Add parameters dateFrom and dateTo to the batch statistic REST: make changes based on comments in CR-DIST-FOREIGN-95
* CDP-707 FDA: Create Additional Funds: Invalid sorting on 'Filtered batches' dialog

5.1.10
-
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: Implement moving from current dialog to its parent dialog by clicking on the Cancel button
* B-50004 FDA: Add Standard Number Type to the usage for classification: adjust logic of uploading researched usages

5.1.9
-
*  B-48760 FDA: Create NTS PreService fee additional fund pool from NTS withdrawn details: add withdrawn_amount column to df_fund_pool table
* B-46739 FDA: Remove NTS titles that do not meet the minimum from the fund pool batch: fix checkstyle issue
* B-46739 FDA: Remove NTS titles that do not meet the minimum from the fund pool batch: make changes based on comments in CR-DIST-FOREIGN-97
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: Implement service to create NTS withdrawn fund pool
* B-49463 Tech Debt: FDA: rename repository method for getting unclassified usages by Wr Wrk Insts
* B-50004 FDA: Add Standard Number Type to the usage for classification: add Standard Number Type column to csv template to upload usage batch
* B-50004 FDA: Add Standard Number Type to the usage for classification: apply Wr Wrk Inst validator to uploaded usages

5.1.8
-
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: Implement the Create Fund Pool dialog
* B-48995 FDA: Delete NTS withdrawn fund pool: prevent deleting batch that associated with additional funds
* B-49463 Tech Debt: FDA: adjust confirmation message during applying classification without unclassified usages
* B-50004 FDA: Add Standard Number Type to the usage for classification: Ensure standard number type from PI is in uppercase
* B-50004 FDA: Add Standard Number Type to the usage for classification: implement validator to verify Wr Wrk Inst

5.1.7
-
* B-46739 FDA: Remove NTS titles that do not meet the minimum from the fund pool batch: implement service logic for excluding details under minimum cutoff amount
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: Implement CSV export from the Filtered Batches dialog
* B-48995 FDA: Delete NTS withdrawn fund pool: implement backend logic to delete additional funds
* B-49463 Tech Debt: FDA: adjust thread executor on batch loading to start processing of usages instead of keep processing task waiting in queue
* B-49463 Tech Debt: FDA: ignore redundand org.apache.catalina.util.SessionIdGeneratorBase and org.jasig.cas.client.session.SingleSignOutHandler warn messages
* B-50004 FDA: Add Standard Number Type to the usage for classification: Preserve standard number type during creating split paid usage, adjust workflow tests with usage verification
* B-50004 FDA: Add Standard Number Type to the usage for classification: retrieve Work by Wr Wrk Inst from PI

5.1.6
-
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: Implement the Filtered Batches dialog
* B-48995 FDA: Delete NTS withdrawn fund pool: implement backend logic to select all additional funds to display on UI
* B-49463 Tech Debt: FDA: fix javadoc warnings
* B-50004 FDA: Add Standard Number Type to the usage for classification: Adjust UsageRepository and UsageArchiveRepository with processing standard number type
* B-50004 FDA: Add Standard Number Type to the usage for classification: Adjust researched usage CSV validation to accept standard number type column
* B-50004 FDA: Add Standard Number Type to the usage for classification: adjust logic of matching functionality
* CDP-703 FDA: Usage batch statistic: System displays statistic for all batches when certain dates are indicated

5.1.5
-
* B-48760 FDA: Create NTS PreService fee additional fund pool from NTS withdrawn details: implement additional funds menu bar and apply permissions for it
* B-50004 FDA: Add Standard Number Type to the usage for classification: add standard number type column to exported usages files
* B-50004 FDA: Add Standard Number Type to the usage for classification: Adjust usage serialization/deserialization with processing standard number type field
* B-48995 FDA: Delete NTS withdrawn fund pool: implement delete NTS fund window
* CDP-706 FDA: Exception occurs during creation FAS scenario
* B-48760 FDA: Create NTS PreService fee additional fund pool from NTS withdrawn details: add df_fund_pool_uid column to df_usage and df_usage archive tables
* B-46739 FDA: Remove NTS titles that do not meet the minimum from the fund pool batch: implement repository method for deleting usages that are under minimum cutoff amount
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: Implement backend to load usage batches by FAS/FAS2 product families, previously not included in NTS withdrawn fund pools
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: Implement the Batches Filter dialog
* B-50004 FDA: Add Standard Number Type to the usage for classification: Adjust WorkClassificationRepository to process standard number type field

5.1.4
-
* B-46039 FDA: Exclude Belletristic titles from NTS Batch: FDA: change WorkClassificationService debug level from info to debug
* B-46739 FDA: Remove NTS titles that do not meet the minimum from the fund pool batch: implement repository method for calculating STM/Non-STM Cutoff Amounts
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: Implement domain object
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: Implement repository
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: implement Liqubase script to create table df_fund_pool
* B-48760 FDA: Create NTS Pre-service fee additional fund pool from NTS withdrawn details: increase size of column status_ind of tables df_usage and df_usage_archive to 32 characters
* B-50004 FDA: Add Standard Number Type to the usage for classification: Add standard number type field to Usage, UsageDto and WorkClassification entities
* B-50004 FDA: Add Standard Number Type to the usage for classification: Add standard_number_type column to df_usage and df_usage_archive tables
* B-50004 FDA: Add Standard Number Type to the usage for classification: add Standard Number Type column to UI grids and adjust queries to sort by this column
* B-50061 FDA: Update Undistributed Liabilities reconciliation report to display NTS withdrawn amounts separately: adjust Undistributed Report service logic to include Gross Undistributed Withdrawn Amt in FDA column with 0 value
* B-50061 FDA: Update Undistributed Liabilities reconciliation report to display NTS withdrawn amounts separately: adjust Undistributed report database query to calculate nts withdrawn gross amount

5.1.3
-
* B-49462 Tech Debt: FDA: remove ActiveMQ dependencies from the application
* B-46039 FDA: Exclude Belletristic titles from NTS Batch: FDA: make changes based on comments in CR-DIST-FOREIGN-92
* B-46040 FDA: Assign title classification to titles in NTS distribution: make changes based on comments in CR-DIST-FOREIGN-94
* B-49017 FDA: Address the number of buttons on the Usage tab: make changes based on comments in CR-DIST-FOREIGN-93

5.1.2
-
* B-46040 FDA: Assign title classification to titles in NTS distribution: create index by wr_wrk_inst in df_usage_archive table
* B-46040 FDA: Assign title classification to titles in NTS distribution: implement works classification controller, apply service logic to UI
* B-49462 Tech Debt: FDA: add parameters dateFrom, dateTo to the batch statistic REST

5.1.1
-
* B-46040 FDA: Assign title classification to titles in NTS distribution: implement repository logic for selecting and updating works classification
* B-46040 FDA: Assign title classification to titles in NTS distribution: implement service logic for assign classification functionality
* B-49017 FDA: Address the number of buttons on the Usage tab: adjust Filters on Usage tab and Delete Usage Batch modal window to display data considering selected Product Family

5.1.0
-
* B-46039 FDA: Exclude Belletristic titles from NTS Batch: adjust NTS batch usages processing strategy to process usage work classification after processing usage Rh Ilegibility
* B-46039 FDA: Exclude Belletristic titles from NTS Batch: adjust query for selecting usages for fund pool to exclude belletristic
* B-46039 FDA: Exclude Belletristic titles from NTS Batch: implement Classification usage processor, add UNCLASSIFIED usage status
* B-46039 FDA: Exclude Belletristic titles from NTS Batch: implement repository and service methods for getting wrWrkInst classification
* B-46039 FDA: Exclude Belletristic titles from NTS Batch: implement service method for usages classification updates
* B-46040 FDA: Assign title classification to titles in NTS distribution: implement NTS batch selector and works classification windows
* B-46040 FDA: Assign title classification to titles in NTS distribution: implement liquibase changeset with permission, add assign classification button on UI
* B-46040 FDA: Assign title classification to titles in NTS distribution: implement liquibase to create df_work_classification table
* B-49017 FDA: Address the number of buttons on the Usage tab: add Product Family dropdown to Usage tab and display action buttons related to selected Product Family
* B-49017 FDA: Address the number of buttons on the Usage tab: add product_family column into df_usage_batch table and populate historical data
* B-49462 Tech Debt: FDA: remove unused nts batch creation audit reason from UsageService#insertNtsUsages method
* B-49462 Tech Debt: FDA: remove unused service method getUsagesForNtsBatch

4.1.45
-
* CDP-695 FDA: Usage batch statistic: Exception occurs during running usage batch statistic, if batch is processing
* CDP-696 FDA: Usage batch statistic: Exception occurs during running usage batch statistic without 'name' field

4.1.44
-
* B-49462 Tech Debt: FDA: adjust grant product family value for NGT_ELECTRONIC_COURSE_MATERIALS and NGT_PRINT_COURSE_MATERIALS type of uses
* CDP-688: FDA: Sometimes by creation of NTS batch some usages remain in state RH_FOUND
* CDP-692 FDA: Usage batch statistic: Exception occurs during running usage batch statistic with invalid date format

4.1.43
-
*  B-49462 Tech Debt: FDA: remove environment prefix from internal SQS endoints
* B-49462 Tech Debt: FDA: Rename service and repository methods after skipping audit writing during loading Fund Pool batch
* B-49462 Tech Debt: FDA: adjust loggers during processing NTS usage batch
* B-49462 Tech Debt: FDA: profile distinct usage processors, remove ForeignPerformanceAspect
* B-49462 Tech Debt: FDA: remove usage audit deletion while removing non ELIGIBLE NTS usage
* B-49462 Tech Debt: FDA: update dist-common to 38.3.+ version
* CDP-691 FDA: Usage statistic: Exception occurs during running usage statistic with invalid usageId

4.1.42
-
* B-49462 Tech Debt: FDA: improve logging during sending usages for getting rights after loading NTS batch
* B-49462 Tech Debt: FDA: update dist-common to 38.2.+ version
* B-49462 Tech Debt: FDA: update grant_product_family value in df_grant_priority table for NGT_ELECTRONIC_COURSE_MATERIALS and NGT_PRINT_COURSE_MATERIALS type of uses
* B-49462 Tech Debt: FDA: remove adding audit from NTS usage processing and split common RightsConsumer into NtsRightsConsumer and FasRightsConsumer

4.1.41
-
* CDP-685 FDA: Load Fund Pool: 'Server Connection Lost' appears during creation NTS batch: use single thread to send usages for getting rights after loading NTS batch

4.1.40
-
* B-49462 Tech Debt: FDA: implement Swagger Codegen REST service to get usage statistic
* B-49462 Tech Debt: FDA: update batch statistic REST to get statistic for usages that are ELIGIBLE or WORK_FOUND during loading of FAS batch
* B-49462 Tech Debt: FDA: update dist-common to 38.1.+ version
* CDP-685 FDA: Load Fund Pool: 'Server Connection Lost' appears during creation NTS batch
* Revert "B-49462 Tech Debt: FDA: Add repository and service methods to retrieve usages by status and product family using paging"

4.1.39
-
* B-49462 Tech Debt: FDA: Add repository and service methods to get usage ids by status and product family
* B-49462 Tech Debt: FDA: Add repository and service methods to retrieve usages by status and product family using paging
* B-49462 Tech Debt: FDA: Retrieve usages by partitions in AbstractJobProcessor
* B-49462 Tech Debt: FDA: implement database backend for REST service to get usage timings statistic
* B-49462 Tech Debt: FDA: send uploaded FAS and newly created NTS usages to queues in backgroud thread
* CDP-686 FDA: RHs information is not populated while getting roll ups

4.1.38
-
* B-49461 Tech Debt: FDA: populate RHs information while getting roll ups
* B-49461 Tech Debt: FDA: adjust logging of usages during processing — Pavel_Liakh / githubweb
* B-49461 Tech Debt: FDA: fix integration tests failure due to imposibility of autoproxying of target test builder classes by overriding performance aspect context for tests — Pavel_Liakh / githubweb
* CDP-678 FDA: Load Fund Pool batch: ‘Out of memory’ error occurs while loading Fund Pool batch with usages count more than 200K

4.1.37
-
* B-49461 Tech Debt: FDA: hardcode rupCamel.jackson version to 2.9.8 to avoid vulnerability issues
* CDP-670 FDA: Duplicate audit for usages after creation fund pool with more than 110K usages
* B-47964 FDA: Tag details with category supplied by RRO when loading batches/receipts: make changes based on comments in CR-DIST-FOREIGN-89
* B-47646 FDA: Migrate to SQS for internal queues: make changes based on comments in CR-DIST-FOREIGN-88
* CDP-673 FDA: Usage batch statistic is not running for batches with NTS product family

4.1.36
-
* B-49461 Tech Debt: FDA: adjust NTS batch creation logic to populate new usages reported value from archived usage gross amount
* B-48636 FDA: Consume RMS Rights Service that provides mapping between Product and Product Family: add grant_product_family column into df_grant_priority table and drop market and distribution columns
* B-48636 FDA: Consume RMS Rights Service that provides mapping between Product and Product Family: update dist-common version to 38.0.+

4.1.35
-
* B-49461 Tech Debt: FDA: Change date format in batch statistic REST service to yyyy-MM-dd
* B-49461 Tech Debt: FDA: implement REST service by Swagger Codegen to get batch statistic

4.1.34
-
* B-49461 Tech Debt: FDA: Add ability to change logging level for Spring Security
* B-49461 Tech Debt: FDA: use SqsClientMock from Common, update dist-common dependency version to 36.0.+

4.1.33
-
* B-49461 Tech Debt: FDA: Change REST services URL prefix to /api/
* B-49461 Tech Debt: FDA: Use Swagger Codegen to change implementation of REST services from servlet to Spring MVC
* B-49461 Tech Debt: FDA: Add Swagger Codegen dependencies to the project
* B-49461 Tech Debt: FDA: apply read and connect timeout for spring RestTemplate

4.1.32
-
* CDP-660 FDA: Exception occurs during running manual jobs
* B-49461 Tech Debt: FDA: update dist-common version to 35.0.+
* B-48636 FDA: Consume RMS Rights Service that provides mapping between Product and Product Family: apply new common grants service for RMS integration
* B-47964 FDA: Tag details with category supplied by RRO when loading batches/receipts: add Comment column to refresh scenario window and increase width of Comment column in all widgets 

4.1.31
-
* B-47964 FDA: Tag details with category supplied by RRO when loading batches/receipts: adjust NTS usage batch creation logic
* B-47964 FDA: Tag details with category supplied by RRO when loading batches/receipts: adjust receive paid usage functionality
* B-49461 Tech Debt: FDA: move RmsAllRightsCacheService into integration module

4.1.30
-
* B-46540 FDA: Migrate usage from SC: add changeset to remove not-null constraints in df_usage_archive table from payee_account_number and is_rh_participating_flag columns
* B-47964 FDA: Tag details with category supplied by RRO when loading batches/receipts: adjust logic of movement usages to archive
* CDP-657 FDA: Usage with title or standard number which are present in PI gets WORK_NOT_FOUND status after PI matching
* B-47964 FDA: Tag details with category supplied by RRO when loading batches/receipts: adjust upload researched usages functionality

4.1.29
-
* B-49648 FDA: Escape reserved characters when loading a usage batch: add javadoc for buildQueryString method
* B-47964 FDA: Tag details with category supplied by RRO when loading batches/receipts: FDA: add Comment column to UI tables
* B-47964 FDA: Tag details with category supplied by RRO when loading batches/receipts: FDA: add Comment column to exports
* B-47964 FDA: Tag details with category supplied by RRO when loading batches/receipts: upload usages with Comment column
* B-49461 Tech Debt: FDA: Implement REST service to process scheduled jobs
* B-49648 FDA: Escape reserved characters when loading a usage batch: apply logic for escaping special characters during PI matching

4.1.28
-
* B-47964 FDA: Tag details with category supplied by RRO when loading batches/receipts: FDA: and comment column to db tables
* B-49040 Tech Debt: FDA: Add Swagger Codegen to implement REST services
* B-49040 Tech Debt: FDA: Set correct value of basePath for Swagger Codegen configuration
* B-49040 Tech Debt: FDA: Update project configuration to support REST services implemented by Swagger Codegen
* B-49461 Tech Debt: FDA: reduce Camel redelivery policy delay, apply redelivery for all consuming routes

4.1.27
-
* B-48910 FDA: Migrate to SQS for internal queues: Remove all ActiveMQ dependencies from the project

4.1.26
-
* B-48910 FDA: Migrate to SQS for internal queues: Migrate Camel route for the endpoint activemq:df.eligibility to SQS
* B-48910 FDA: Migrate to SQS for internal queues: Migrate Camel route for the endpoint activemq:df.matching to SQS

4.1.25
-
* B-48910 FDA: Migrate to SQS for internal queues: Migrate Camel route for the endpoint activemq:df.rights to SQS

4.1.24
-
* B-48910 FDA: Migrate to SQS for internal queues: Migrate Camel route for the endpoint activemq:df.tax to SQS

4.1.23
-
* B-48535 FDA: Utilize SQS/SNS for internal distribution apps communication: reuse common sqs and sns mocks
* B-48535 FDA: Utilize SQS/SNS for internal distribution apps communication: adjust tomcatRun to override AWS prefix property on local environment
* B-48535 FDA: Utilize SQS SNS for internal distribution apps communication: make changes based on comments in CR-DIST-FOREIGN-87
* CDP-646 FDA: Scenario is incorrectly showing 10% service fee for FAS details paid to CLA and CLA members
* B-47646 Tech Debt: FDA: rename properties names from dist.common.integration.rest to dist.common.rest
* B-47646 Tech Debt: FDA: create separate URLs for RMS services

4.1.22
-
* B-48535 FDA: Utilize SQS/SNS for internal distribution apps communication: apply common sqs and sns config

4.1.21
-
* CDP-643 FDA: Paid information from LM is not consumed by FDA

4.1.20
-
* B-46034 FDA: Exclude Ceased/Lost/NTS Refused RHs from the NTS proxy: make changes based on comments in CR-DIST-FOREIGN-86
* B-46034 FDA: Exclude Ceased/Lost/NTS Refused RHs from the NTS proxy: make changes based on comments in CR-DIST-FOREIGN-86
* B-47587 FDA: Utilize SQS/SNS for internal distribution apps communication: introduce subscriber for SQS SNS communication, adjust camel config to consume paid information from SQS queue
* B-47646 Tech Debt: FDA: Implement repository method to delete archived usages
* Revert "B-46034 FDA: Exclude Ceased/Lost/NTS Refused RHs from the NTS proxy: make changes based on comments in CR-DIST-FOREIGN-86"

4.1.19
-
* B-47646 Tech Debt: FDA: change visibility modifier of setter in AbstractUsageJobProcessor
* B-47646 Tech Debt: FDA: make changes based on comments in CR-DIST-FOREIGN-85
* B-47646 Tech Debt: FDA: resolve Security Vulnerability in camel-spring

4.1.18
-
* B-46034 FDA: Exclude Ceased/Lost/NTS Refused RHs from the NTS proxy: Implement Job for handling ISRHDISTINELIGIBLE flag for NTS usages
* B-47587 FDA: Utilize SQS/SNS for internal distribution apps communication: adjust sending of usages to LM to use SQS/SNS
* B-47646 Tech Debt: FDA: resolve security vulnerabilities related to org.apache.tomcat.embed

4.1.17
-
* B-46034 FDA: Exclude Ceased/Lost/NTS Refused RHs from the NTS proxy: add US_TAX_COUNTRY status and action type, change audit message for eligible NTS usages
* B-46034 FDA: Exclude Ceased/Lost/NTS Refused RHs from the NTS proxy: remove US_TAX_COUNTRY status from filters

4.1.16
-
* B-46034 FDA: Exclude Ceased/Lost/NTS Refused RHs from the NTS proxy: add US_TAX_COUNTRY status and action type, change audit message for eligible NTS usages
* B-46034 FDA: Exclude Ceased/Lost/NTS Refused RHs from the NTS proxy: implement RH eligibility serializer and deserializer

4.1.15
-
* B-46034 FDA: Exclude Ceased/Lost/NTS Refused RHs from the NTS proxy: adjust PrmIntegrationService to get ISRHDISTINELIGIBLE preference
* B-47645 Tech Debt: FDA: add DeleteUsageProcessor
* B-47645 Tech Debt: FDA: add RhTaxProcessor
* B-47645 Tech Debt: FDA: adjust RhTaxJob to use common workflow executor
* B-47645 Tech Debt: FDA: adjust ntsRightsProcessor config
* B-47645 Tech Debt: FDA: adjust rhTaxProcessor config
* B-47645 Tech Debt: FDA: move time to leave property setup to constructor in OracleCacheService
* B-47645 Tech Debt: FDA: replace RmsRightsAssignmentService type with type from dist-common
* B-47645 Tech Debt: FDA: use ntsRightsProcessor in RightsConsumer, remove NTS specific logic from RightsService updateRight
* B-47645 Tech Debt: FDA: use rhTaxProcessor in rhTaxConsumer, remove usage update logic from RhTaxService
* B-48319 FDA: Apply optimization for getting grants from RMS: rename CommonDiscrepanciesService to CommonDiscrepancyService and RmsGrantsService to RmsGrantService
* B-48525 FDA: Update Logic for getting FAS Rightsholders: Implement Liquibase script to add new records into the table df_grant_priority

4.1.14
-
* B-47645 Tech Debt: FDA: apply discrepancies partitioning changes from Common
* B-47645 Tech Debt: FDA: make changes based on comments in CR-DIST-FOREIGN-83
* B-48319 FDA: Apply optimization for getting grants from RMS: make changes based on comments in CR-DIST-FOREIGN-84
* B-48321 FDA: Apply common workflow approach for processing FAS usages: adjust send for RA functionality
* B-48321 FDA: Apply common workflow approach for processing FAS usages: fix ordering of audit items with the same date of creation and implement load researched usages integration test

4.1.13
-
* B-48318 FDA: Plug new service for Roll-up: create separate URLs for PRM REST services
* B-48319 FDA: Apply optimization for getting grants from RMS: apply common rms processor for RightsService, replace usages of distribution constants by local implementation
* B-48321 FDA: Apply common workflow approach for processing FAS usages: adjust load researched usages functionality
* B-48321 FDA: Apply common workflow approach for processing FAS usages: implement executor to run processors and adjust matching and rights jobs

4.1.12
-
* B-48319 FDA: Apply optimization for getting grants from RMS: apply common method for building discrepancies based on priorities from dist common
* B-48321 FDA: Apply common workflow approach for processing FAS usages: implement matching processor and adjust matching consumer

4.1.11
-
* B-48321 FDA: Apply common workflow approach for processing FAS usages: implement rights processor and adjust rights consumer
* RDSC-647 FDA: One of usages with the same Wr_wrk_inst remains in 'WORK_FOUND' status after batch upload, if rightsholder for it is absent in database

4.1.10
-
* B-47645 Tech Debt: FDA: Change version of embedded PostgreSQL for integration tests
* B-47645 Tech Debt: FDA: Implement Liquibase script to add primary keys constraint to the column df_rightsholder_uid instead of the column rh_account_number
* B-48319 FDA/LM: Apply optimization for getting grants from RMS: add Liquibase changeset to create grant_priority table and populate it, write IT for GrantPriorityRepository
* B-48321 FDA: Apply common workflow approach for processing FAS usages: implement Eligibility processor

4.1.9
-
* RDSC-648 FDA: PI job sends the same message several times while processing usages

4.1.8
-
* B-46032 FDA: Exclude non US RHs from the NTS proxy: implement workflow test for NTS product family
* B-46036 FDA: Exclude non US RH from the NTS proxy: make changes based on comments in CR-DIST-FOREIGN-81
* B-47644 Tech Debt: FDA: adjust PI integration service to get information about multiple matching, add multiple matching to statistic servlet
* B-47644 Tech Debt: FDA: refactor integration tests after refactoring PrmRollUpCacheService to be inherited from AbstractCacheService
* B-47644 Tech Debt: FDA: remove job rest filter configuration from web.xml

4.1.7
-
* Revert B-47644 Tech Debt: FDA: refactor integration tests after refactoring PrmRollUpCacheService to be inherited from AbstractCacheService

4.1.6
-
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: make changes based on comments in CR-DIST-FOREIGN-79
* B-47644 Tech Debt: FDA: Implement foundation for NTS usages processing
* B-47644 Tech Debt: FDA: adjust API for AT adding method to remove archived usages and its audit by batch id
* B-47644 Tech Debt: FDA: remove constraint for applying Statuses filter from Create Scenario functionality
* RDSC-644 FDA: Audit is not deleted from Database for NTS usage, that was deleted due to non-US RH and RH was not found in RMS

4.1.5
-
* B-47644 Tech Debt: FDA: remove constraint for applying status filter from Sent for Research functionality
* B-47644 Tech Debt: FDA: rename caching services name suffix from Proxy to Cache
* RDSC-645 FDA: Newly uploaded Fiscal Year is not present in 'Fiscal Year To' filter on Usage Tab

4.1.4
-
* B-47644 Tech Debt: FDA: add embedded ActiveMQ to WorkflowIntegrationTest to provide ability to interact with the queues df.matching, df.rights
* B-47644 Tech Debt: FDA: align rup-env properties with common approach for naming
* B-47644 Tech Debt: FDA: remove redundant class RightsholderPreferences
* B-47644 Tech Debt: FDA: update WorkflowIntegrationTest to cover transfer of usages from status WORK_FOUND to ELIGIBLE in the method UsageBatchService.sendForGettingRights

4.1.3
-
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: calculate and save fiscal year, remove redundant fields from usages during creating NTS batch
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: implement liquibase script with permission to load fund pool
* B-46032 FDA: Update RHs in targeted FAS usage details: Adjust NTS batch creation logic to send created usages for getting rights
* B-46036 FDA: Exclude non US RHs from the NTS proxy: add audit for US tax country NTS usages
* B-46036 FDA: Exclude non-US RHs from the NTS proxy: fix NPE on rh tax processing adjusting update rights method for nts usages to set RH account number to usage before sending usage for Rh Tax processing

4.1.2
-
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: implement service logic for loading fund pool and inserting associated usages
* B-46036 FDA: Exclude non US RHs from the NTS proxy: implement service logic to verify RH tax country for NTS usages
* B-46036 FDA: Exclude non-US RHs from the NTS proxy: Implemnt job to send RH_FOUND usages to eligibility queue

4.1.1
-
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: Refactor fields of fund pool domain object
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: Update batch domain object to store new fields
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: Update batch repository to store new fields in database
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: add liquibase changelog file for FDA of version 4
* B-46036 FDA: Exclude non US RHs from the NTS proxy: implement consumer and producer for tax queue

4.1.0
-
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: implement window for loading fund pool
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: implement service method to get eligible markets
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: implement load fund pool button
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: implement validation for markets widget and periods on load fund pool window
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: Update batch domain object to store new fields
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: implement repository layer for selecting archive details for NTS batch creation
* B-23202 FDA: Create a NTS batch of FAS usage details as specified by RRO Receipt: add ids for components on Load Fund Pool window
* B-46036 FDA: Exclude non-US RHs from the NTS proxy: implement OracleService for getting Rhs country code
* B-46036 FDA: Exclude non US RHs from the NTS proxy: introduce RH_FOUND status
* B-46036 FDA: Exclude non US RHs from the NTS proxy: implement method to delete usage by its identifier

3.1.35
-
* RDSC-646 FDA: For usages that have CLA as Payee service fee changes to 16% after applying rollups during reconciliation process

3.1.34
-
* B-43660 Tech Debt: FDA: set correct version of the dist-common dependency
* B-47644 Tech Debt FDA: use new cached PRM preferences service to read preferences only by selected preference code

3.1.33
-
* B-45289 FDA: Report FAS split payments to RC: Make changes based on comments in CR-DIST-FOREIGN-76
* B-47837 FDA: Neartime processing for Works match and Get RHs: make changes based on comments in CR-DIST-FOREIGN-78
* RDSC-640 FDA: Usages are grouped by the same title after PI matching, ignoring standard number presence for 1 of them

3.1.32
-
* B-47837 FDA: Neartime processing for Works match and Get RHs: apply redelivery policy only for RupRuntimeException during PI matching and GetRights processing
* RDSC-639 FDA: System doesn't populate 'NTS' product family for usages with 'NTS_WITHDRAWN' status

3.1.31
-
* B-47837 FDA: Near time processing for Works match and Get RHs: Add caching for getting rightsholders by wrWrkInsts
* B-47837 FDA: Neartime processing for Works match and Get RHs: fix issue with calculating total amount if batch was removed during processing
* B-47837 FDA: Neartime processing for Works match and Get RHs: refine caching functionality for PI matching on order to use common service

3.1.30
-
* B-47837 FDA: Near-time processing for Works match and Get RHs: adjust service logic of Get Rights Job to send usages for getting rights information via queue
* B-47837 FDA: Neartime processing for Works match and Get RHs: apply logic for processing details independently during PI matching in order to apply concurrent processing
* RDSC-637 FDA: Unable to update service fee for scenario without rightholders updates

3.1.29
-
* B-47837 FDA: Neartime processing for Works match and Get RHs: adjust service logic for Works Matching Job to send usages for matching via df.matching queue
* RDSC-636 FDA: batch processing could produce RH_NOT_FOUND usages without wr_wrk_inst

3.1.28
-
* B-47643 Tech Debt: FDA: implement service for getting RHs preferences from PRM by RH ids and preference codes
* RDSC-636 FDA: batch processing could produce RH_NOT_FOUND usages without wr_wrk_inst

3.1.27
-
* B-47837 FDA: Neartime processing for Works match and Get RHs: add caching functionality for PI matching

3.1.26
-
* B-47837 FDA: Neartime processing for Works match and Get RHs: apply concurrent consumers for Matching and Rights queues

3.1.25
-
* B-47837 FDA: Neartime processing for Works match and Get RHs: rename classes related to getting Rights, add independent serializer and deserializer for getting Rights

3.1.24
-
* B-45289 FDA: Report FAS split payments to RC: update service logic for consuming paid split usages from LM
* B-47836 FDA: Preserve leading zeros in Standard numbers: adjust Usages/Send for Research export, Scenario Usages export and Audit export reports to handle Standard numbers that starts with 0
* B-47837 FDA: Neartime processing for Works match and Get RHs: implement producer and consumer for getting Rights
* CDP-576 Upgrade PostgreSQL driver to 42.2.1: update version for party-db module

3.1.23
-
* B-45289 FDA: Report FAS split payments to RC: adjust service logic for receiving post-distribution usages from LM
* B-45289 FDA: Report FAS split payments to RC: update rightsholder for paid usages
* B-47643 Tech Debt: FDA: update logback configuration to use common logging approach
* B-47837 FDA: Neartime processing for Works match and Get RHs: implement producer and consumer for PI matching

3.1.22
-
* B-45289 FDA: Report FAS split payments to RC: adjust service logic for consuming paid data with splits information from LM
* B-45289 FDA: Report FAS split payments to RC: adjust updating paid usages functionality
* B-46887 FDA: Make unmatched FAS details available for NTS additional fund pool: fix comments in CR-DIST-FOREIGN-75
* B-47643 Tech Debt: FDA: update dist-common to 21.4.+

3.1.21
-
* RDSC-633 Upgrade to RUP Common 6

3.1.20
-
* B-45289 FDA: Report FAS split payments to RC: update service logic for sending usages to LM to send service fee and collected amounts
* B-46887 FDA: Make unmatched FAS details available for NTS additional fund pool: Adjust batch summary report to exclude NTS_WITHDRAWN from non-eligible details
* B-46887 FDA: Make unmatched FAS details available for NTS additional fund pool: add NTS_WITHDRAWN status to the system and UI filters
* B-46887 FDA: Make unmatched FAS details available for NTS additional fund pool: add liquibase script for set NTS_WITHDRAWN status for historical NTS usages
* B-46887 FDA: Make unmatched FAS details available for NTS additional fund pool: adjust scenario creation logic on Usages tab to do not check if selected product family is NTS
* B-46887 FDA: Make unmatched FAS details available for NTS additional fund pool: adjust works matching service logic to set NTS_WITHDRAWN status for NTS usages

3.1.19
-
* RDSC-623 FDA: Reconciliation: Discrepancies are displayed only for one record when there are works with the same Wr Wrk Inst, but different Work Title

3.1.18
-
* CDP-578 Upgrade PostgreSQL driver to 42.2.1 (dist-foreign)

3.1.17
-
* B-45519 FDA: update legacy integration service URL for integration with CRM
* B-45519 Tech Debt: FDA: Adjust integration tests for parsing CSV files
* B-45519 Tech Debt: FDA: make changes based on comments in CR-DIST-FOREIGN-73

3.1.16
-
* B-45519 Tech Debt: FDA: adjust handling of errors during parsing incorrect message in CRM integration

3.1.15
-
* B-45519 Tech Debt: FDA: set up default time zone for tests to avoid comparison failures for dates

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
