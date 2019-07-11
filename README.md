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
* B-47644 Tech Debt: FDA: adjust API for AT adding method to remove archived usages and it's audit by batch id
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
