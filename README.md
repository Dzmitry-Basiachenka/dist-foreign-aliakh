21.0.6
-
* CDP-1678 FDA: Vaadin Migration: NTS - Scenarios Tab Main View: migrate Exclude Rightsholder window
* CDP-1684 FDA: Vaadin Migration: AACL - Audit Tab: introduce Audit tab
* CDP-1682 FDA: Vaadin Migration: AACL - Usages Tab: migrate ClassifiedUsagesUploadWindow
* CDP-1678 FDA: Vaadin Migration: NTS - Scenarios Tab Main View: implement action for Edit Name button
* CDP-1678 FDA: Vaadin Migration: NTS - Scenarios Tab Main View: implement action for Delete button
* CDP-1684 FDA: Vaadin Migration: AACL - Audit Tab: migrate filter panel
* CDP-2233 Tech Debt: FDA: optimize queries to insert usages and shares for ACL scenario
* CDP-1678 FDA: Vaadin Migration: NTS - Scenarios Tab Main View: implement actions for reject, approve, submit, and send to LM
* CDP-1684 FDA: Vaadin Migration: AACL - Audit Tab: migrate audit view

21.0.5
-
* CDP-1682 FDA: Vaadin Migration: AACL - Usages Tab: migrate AACL usages tab
* CDP-1682 FDA: Vaadin Migration: AACL - Usages Tab: migrate AaclUsageBatchUploadWindow
* CDP-1682 FDA: Vaadin Migration: AACL - Usages Tab: migrate ViewAaclFundPoolDetailsWindow
* CDP-1682 FDA: Vaadin Migration: AACL - Usages Tab: migrate ViewAaclFundPoolWindow
* CDP-1682 FDA: Vaadin Migration: AACL - Usages Tab: migrate ViewAaclUsageBatchWindow
* CDP-1682 FDA: Vaadin Migration: AACL - Usages Tab: migrate AaclFundPoolUploadWindow
* CDP-1678 FDA: Vaadin Migration: NTS - Scenarios Tab Main View: migrate View Scenario window and controller logic
* CDP-1678 FDA: Vaadin Migration: NTS - Scenarios Tab Main View: migrate Scenarios widget and controller logic
* CDP-1678 FDA: Vaadin Migration: NTS - Scenarios Tab Main View: migrate Drill Down by Rightsholder window
* CDP-1681 FDA: Vaadin Migration: NTS - Reports Tab: migrate logic for NTS Service True-Up report
* CDP-1681 FDA: Vaadin Migration: NTS - Reports Tab: refactor build report menu logic and rewrite tests
* CDP-1681 FDA: Vaadin Migration: NTS - Reports Tab: migrate logic for NTS Fund Pools report
* CDP-1681 FDA: Vaadin Migration: NTS - Reports Tab: migrate logic for NTS Pre Service Fee Fund report
* CDP-1681 FDA: Vaadin Migration: NTS - Reports Tab: arrange the classes associated with the report and add missed test
* CDP-1679 FDA: Vaadin Migration: NTS - Audit Tab: refine filter applied action
* CDP-2433 FDA (Vaadin23): NTS works classification: Classification of a work is applied to all previously classified works on the 'Works Classification' window
* CDP-2326 FDA: Update how we repopulate new value batch with works that are outside of the assigned distribution period: update SQL query to get works to be populated
* CDP-2057 FDA: Sonarqube improvements S4: decrease sonarqube bugs
* CDP-2233 Tech Debt: FDA: simplify query to get invalid usages for ACL scenario creation
* CDP-2233 Tech Debt: FDA: refine validation process on Upload windows to avoid double validation
* CDP-2233 Tech Debt: FDA: introduce Grid column enum and apply for Usage widgets
* CDP-2233 Tech Debt: FDA: apply Grid column enum to audit widgets
* CDP-2233 Tech Debt: FDA: apply Grid column enum to view usage batch windows
* CDP-2233 Tech Debt: FDA: replace VaadinSession scope with VaadinIU to avoid multitab UI issues

21.0.4
-
* CDP-1676 FDA: Vaadin Migration: FAS/FAS2 - Reports Tab: make changes based on comments in code review
* CDP-1674 FDA: Vaadin Migration: FAS/FAS2 - Audit Tab: make changes based on comments
* CDP-1677 FDA: Vaadin Migration: NTS - Usages Tab: refactor UsageBatchUploadWindow to use binging correctly
* CDP-1677 FDA: Vaadin Migration: NTS - Usages Tab: refactor FundPoolLoadWindow to use binging correctly
* CDP-1677 FDA: Vaadin Migration: NTS - Usages Tab: make changes based on comments in code review
* CDP-1678 FDA: Vaadin Migration: NTS - Scenarios Tab Main View: add Scenarios tab on UI
* CDP-1679 FDA: Vaadin Migration: NTS - Audit Tab: introduce NTS audit widget
* CDP-1679 FDA: Vaadin Migration: NTS - Audit Tab: implement filters panel
* CDP-1680 FDA: Vaadin Migration: NTS - Batch Status Tab: implement tab and grid
* CDP-1681 FDA: Vaadin Migration: NTS - Reports Tab: add Reports tab
* CDP-1681 FDA: Vaadin Migration: NTS - Reports Tab: migrate logic for Nts Withdrawn Batch Summary report
* CDP-1681 FDA: Vaadin Migration: NTS - Reports Tab: migrate logic for Nts Undistributed Liabilities report
* CDP-1681 FDA: Vaadin Migration: NTS - Reports Tab: migrate logic for Tax Notification report
* CDP-1682 FDA: Vaadin Migration: AACL - Usages Tab: migrate the main widget and controller
* CDP-1682 FDA: Vaadin Migration: AACL - Usages Tab: migrate interfaces of widgets and controllers
* CDP-2232 Tech Debt: FDA: decrease number of SonarQube issues
* CDP-2232 Tech Debt: FDA: replace deprecated setMaxComponentWidth() method with setWidthFull()
* CDP-2232 Tech Debt: FDA: refactor methods for adding grid columns in VUI module

21.0.3
-
* CDP -1677 FDA: Vaadin Migration: NTS - Usages Tab: migrate Load Fund Pool widget
* CDP -1677 FDA: Vaadin Migration: NTS - Usages Tab: migrate View Additional Funds widget
* CDP -1677 FDA: Vaadin Migration: NTS - Usages Tab: apply common components and styles on Batch Upload window
* CDP -1677 FDA: Vaadin Migration: NTS - Usages Tab: migrate CreateAdditionalFundWindow
* CDP -1677 FDA: Vaadin Migration: NTS - Usages Tab: migrate AdditionalFundFilteredBatchesWindow
* CDP -1677 FDA: Vaadin Migration: NTS - Usages Tab: migrate AdditionalFundBatchesFilterWindow
* CDP -1677 FDA: Vaadin Migration: NTS - Usages Tab: migrate WorkClassificationWindow widget
* CDP -1677 FDA: Vaadin Migration: NTS - Usages Tab: migrate NtsUsageBatchSelectorWidget widget
* CDP -1677 FDA: Vaadin Migration: NTS - Usages Tab: migrate CreateNtsScenarioWindow widget
* CDP -1677 FDA: Vaadin Migration: NTS - Usages Tab: implement markets validation on NTS upload Fund Pool window
* CDP -1677 FDA: Vaadin Migration: NTS - Usages Tab: adjust the size and position of widgets
* CDP -1676 FDA: Vaadin Migration: FAS/FAS2 - Reports Tab: migrate logic for undistributed liabilities report
* CDP -1676 FDA: Vaadin Migration: FAS/FAS2 - Reports Tab: migrate logic for FAS batch summary report
* CDP -1676 FDA: Vaadin Migration: FAS/FAS2 - Reports Tab: migrate logic for Research Status Report
* CDP -1676 FDA: Vaadin Migration: FAS/FAS2 - Reports Tab: migrate logic for Ownership Adjustment Report
* CDP -1676 FDA: Vaadin Migration: FAS/FAS2 - Reports Tab: migrate logic for Summary Market Report
* CDP -1676 FDA: Vaadin Migration: FAS/FAS2 - Reports Tab: migrate logic for Tax Notification report
* CDP -1676 FDA: Vaadin Migration: FAS/FAS2 - Reports Tab: migrate logic for Fas Service Fee True up Report
* CDP -1674 FDA: Vaadin Migration: FAS/FAS2 - Audit Tab: add Audit tab on UI
* CDP -1674 FDA: Vaadin Migration: FAS/FAS2 - Audit Tab: migrate Audit filter widget and controller logic
* CDP -1674 FDA: Vaadin Migration: FAS/FAS2 - Audit Tab: migrate Audit widget and controller logic
* CDP -1674 FDA: Vaadin Migration: FAS/FAS2 - Audit Tab: migrate Usage History window
* CDP -1674 FDA: Vaadin Migration: FAS/FAS2 - Audit Tab: migrate Applied Audit filter widget
* CDP -2232 Tech Debt: FDA: update styles for global product family combobox
* CDP -2232 Tech Debt: FDA: update status filter to display it in correct order
* CDP -2232 Tech Debt: FDA: fix sonar bugs for new code

21.0.2
-
* CDP-2323 FDA: Edit scenario name: Scenario name can be changed to the one already existing in the system
* CDP-2322 FDA: Usages filter: Selected checkboxes become unchecked if search is applied on the filter window
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: make changes based on comments in code review
* CDP-1675 FDA: Vaadin Migration: FAS/FAS2 - Batch Status Tab: implement tab and grid
* CDP-1676 FDA: Vaadin Migration: FAS/FAS2 - Reports Tab: init Reports Tab on the Tabs panel
* CDP-1677 FDA: Vaadin Migration: NTS - Usages Tab: migrate interfaces of widgets and controllers
* CDP-1677 FDA: Vaadin Migration: NTS - Usages Tab: migrate the main widget and controller
* CDP-1677 FDA: Vaadin Migration: NTS - Usages Tab: migrate View Fund Pool widget
* CDP-1170 FDA: NTS: Report of Pre-Service Fee Funds: add report item to Reports tab
* CDP-1170 FDA: NTS: Report of Pre-Service Fee Funds: implement window 'NTS Pre-Service Fee Fund Report'
* CDP-1170 FDA: NTS: Report of Pre-Service Fee Funds: implement controller logic to get all NTS pre-service fee funds
* CDP-1170 FDA: NTS: Report of Pre-Service Fee Funds: implement NTS Pre-Service Fee Fund Report handler and domain object
* CDP-1170 FDA: NTS: Report of Pre-Service Fee Funds: implement back-end logic to generate report
* CDP-2231 FDA: Tech Debt: fix height of vertical layouts with grids
* CDP-2231 FDA: Tech Debt: revise SearchWidget implementation
* CDP-2232 FDA: Tech Debt: remove initial validation errors on ResearchedUsagesUpload and FasEditUsages windows
* CDP-2327 FDA: Tech Debt: fix Vaadin UI issues

21.0.1
-
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: adjust the size and position of widgets
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: introduce init method for VerticalLayout and applied to Upload Usage batch window
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: remove default validation messages on UploadUsageBatch window
* CDP-2052 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Actions: improve usage batch menu button
* CDP-1673 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Main View: add permissions for buttons on Scenarios tab
* CDP-1673 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Main View: refine methods for testing grid items and footer items
* CDP-1673 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Main View: make changes based on comments
* CDP-2252 FDA: Usages tab dissappears after switcihng to FAS2 product family
* CDP-2055: Sonarqube improvements: reuse base classes for static method calls
* CDP-2055: Sonarqube improvements S2: FDA: decrease number of critical code smells
* CDP-2231: FDA: Tech Debt: replace VerticalLayout constructions with init method from VaadinUtils

21.0.0
-
* CDP-2158 FDA: Tech Debt: fix critical SonarQube code smell '"Serializable" classes should have a "serialVersionUID"'
* CDP-2158 FDA: Tech Debt: fix critical SonarQube code smell 'String literals should not be duplicated'
* CDP-2158 FDA: Tech Debt: fix critical SonarQube code smell 'Local-Variable Type Inference should be used'7
* CDP-2158 FDA: Tech Debt: decrease number of critical code smells
* CDP-2158 FDA: Tech Debt: fix security vulnerability CVE-2023-44487
* CDP-2055: Sonarqube improvements: fix bug with redundant boxing to parse Long
* CDP-1968 FDA: ACL - Populate payees at point of Grant Set population: fix SonarQube bug 'Methods should not call same-class methods with incompatible "@Transactional" values'
* CDP-1768 Tech Debt: FDA: update jackson to latest version
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: migrate window 'Upload Usage Batch'
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: migrate window 'Upload Researched Details'
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: migrate button 'Send for Research'
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: migrate window 'Create Scenario'
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: migrate window 'Update Usages'
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: migrate interfaces of widgets and controllers
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: migrate validators and formatters
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: migrate filter widgets
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: replace amount text fields with BigDecimal fields
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: revise filter save listeners to have uniform interface
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: migrate filter widgets and controllers
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: migrate usages widget
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: migrate window 'Upload Usage Batch'
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: implement LongField component and apply to WrWrkInst and Rh fields
* CDP-1672 FDA: Vaadin Migration: FAS/FAS2 - Usages Tab: adjust the size and position of widgets
* CDP-2052 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Actions: migrate Edit Scenario name window
* CDP-2052 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Actions: migrate Exclude Payees Window frame
* CDP-2052 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Actions: migrate Exclude Payees Filter Window
* CDP-2052 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Actions: migrate Exclude Payees grid
* CDP-2052 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Actions: implement common scenario classes
* CDP-2052 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Actions: implement common drill down by rightsholder classes
* CDP-2052 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Actions: implement refresh scenario window
* CDP-2052 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Actions: migrate reconcile rightsholder window
* CDP-2052 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Actions: implement delete, sentToLm, aprove, reject actions
* CDP-2052 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Actions: migrate apply button action on exclude payee filter widget
* CDP-2052 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Actions: adjust product family select
* CDP-2052 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Actions: migrate exclude rightsholder window
* CDP-1673 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Main View: migrate Scenarios widget and controller logic
* CDP-1673 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Main View: migrate Scenario History widget and controller logic
* CDP-1673 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Main View: migrate Scenario widget and controller logic
* CDP-1673 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Main View: migrate exports to Scenario widget
* CDP-1673 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Main View: migrate Drill Down by RH widget and controller logic
* CDP-1673 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Main View: add permissions for buttons on View Scenario window
* CDP-1673 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Main View: add menu button to FAS scenario mediator
* CDP-1673 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Main View: migrate Exclude Details by Source RRO window
* CDP-1673 FDA: Vaadin Migration: FAS/FAS2 - Scenarios Tab Main View: add Scenarios tab on UI

20.0.20
-
* CDP-1968 FDA: Populate payees at point of Grant Set population: refactor grant detail service do not refresh rightholders
* CDP-1968 FDA: Populate payees at point of Grant Set population: fix Refresh Payees button permissions

20.0.19
-
* CDP-1968 FDA: Populate payees at point of Grant Set population: implement widget and controller to refresh payees
* CDP-1968 FDA: Populate payees at point of Grant Set population: remove unnecessary parameter
* CDP-1968 FDA: Populate payees at point of Grant Set population: implement refreshing payees when grant details uploaded
* CDP-1968 FDA: Populate payees at point of Grant Set population: implement refreshing payees when grant details edited
* CDP-1968 FDA: Populate payees at point of Grant Set population: refactor scenario creation service to use pre-populated payees
* CDP-1968 FDA: Populate payees at point of Grant Set population: fix pdm rules in ACL Scenario usage repository integration test

20.0.18
-
* CDP-1620 CDP: SonarQube metric improvements: fix bug 'Constructor makes call to non-final method' in ForeignUi class
* CDP-1620 CDP: SonarQube metric improvements: fix bug 'Method of Singleton class writes to a field in an unsynchronized manner' in ForeignUi class
* CDP-1620 FDA: SonarQube metric improvements: fix bug 'An ExecutorService isn't shutdown before the reference to it is lost' in AbstractUsageChainExecutors class
* CDP-1620 FDA: SonarQube metric improvements: fix SonarQube code smells
* CDP-1620 FDA: SonarQube metric improvements: fix SonarQube bug 'Class UdmValueService holds a map-type field currencyCodesToCurrencyNamesMap, but uses it as only a List'
* CDP-1620 FDA: SonarQube metric improvements: fix SonarQube bug 'Method UdmUsageFiltersWindow.$deserializeLambda$(SerializedLambda) is too long to be compiled by the JIT'
* CDP-1620 FDA: SonarQube metric improvements: fix SonarQube bug 'Method UdmEditUsageWindow.$deserializeLambda$(SerializedLambda) is too long to be compiled by the JIT'
* CDP-1620 FDA: SonarQube metric improvements: fix SonarQube bug 'Method $deserializeLambda$(SerializedLambda) is too long to be compiled by the JIT' in classes UdmEditUsageWindow, UdmEditValueWindow
* CDP-1620 FDA: SonarQube metric improvements: fix SonarQube bug 'Class UdmAnnualMultiplierCalculator holds a map-type field differenceInDaysToAnnualMultiplierMap, but uses it as only a List'
* CDP-1620 CDP: SonarQube metric improvements: upgrade tomcat version to fix CVE-2023-46589
* CDP-1620 FDA: SonarQube metric improvements: remove initialization of ExecutorService through PostConstruct in AbstractUsageChainExecutor
* CDP-1892 FDA: Edit ACL Scenario Names in FDA: add Edit Name button on UI and apply permissions
* CDP-1892 FDA: Edit ACL Scenario Names in FDA: implement Edit ACL Scenario Name window
* CDP-1892 FDA: Edit ACL Scenario Names in FDA: implement back-end logic to edit ACL Scenario name
* CDP-1892 FDA: Edit ACL Scenario Names in FDA: make changes based on comments
* CDP-1968 FDA: Populate payees at point of Grant Set population: implement service to populate payees during the grant set population
* CDP-1968 FDA: Populate payees at point of Grant Set population: add payee_account_number column to df_acl_grant_detail table
* CDP-1968 FDA: Populate payees at point of Grant Set population: modify repository to populate payees during the grant set population
* CDP-1968 FDA: Populate payees at point of Grant Set population: implement service to refresh payees
* CDP-2158 Tech Debt: FDA: decrease number of critical code smells
* CDP-1771 Tech Debt: FDA: improve view Select component in FDA and CDP
* CDP-2158 Tech Debt: FDA: fix issue with line longer 120

20.0.17
-
* CDP-2110: FDA: ACLCI Usages: ‘9’ grade is mapped to ‘Middle’ grade group instead of ‘HS’ grade group when uploading usage batch

20.0.16
-
* CDP-2079 FDA UDM: Edit value: Redundant audit message appears in case currency was changed and then set back
* CDP-2092 FDA: ACLCI remap grades to new grade groups: update grade group mapping
* CDP-1632 FDA: Initialize UI Framework: make changes based on comment in code review
* CDP-1630 FDA: Implement improvements from SAR Review: fix issue 'Class has a circular dependency with other classes' for ScenarioUsageFilter
* CDP-1630 FDA: Implement improvements from SAR Review: fix issue 'Java native synchronization can limit system scalability' in RightsholderService
* CDP-1771 Tech Debt: remove denominators column from share table

20.0.14
-
* CDP-2074: FDA ACL: Clear Price and Content on Edit Value window: Price and Content flags display 'Y' value in case price and content were cleared by 'Clear' buttons

20.0.13
-
* CDP-1630 FDA: Implement improvements from SAR Review: fix issue 'System contains class cyclic dependencies' for class ForeignErrorHandler
* CDP-1630 FDA: Implement improvements from SAR Review: fix issue 'System contains class cyclic dependencies' for class MaximizeModalWindowManagerTest
* CDP-1632 FDA: Initialize UI Framework: migrate main view and mediator for main view
* CDP-1630 FDA: Implement improvements from SAR Review: fix issue 'System contains class cyclic dependencies' for ForeignErrorHandler in UI module
* B-80927 - FDA & ACL: Manually enter proxy CUP and update CUP Flag: make changes based on comments in code review
* CDP-1630 FDA: Implement improvements from SAR Review: fix issue 'System contains class cyclic dependencies' for SalUpdateRighstholderWindow
* CDP-1630 FDA: Implement improvements from SAR Review: fix issue 'System contains class cyclic dependencies' for IAdditionalFundBatchesFilterWindow and 'Constructor makes call to non-final method' in ForeignUi
* CDP-1630 FDA: Implement improvements from SAR Review: add CommonDialog class to prevent 'Class has a circular dependency with other classes' issue
* CDP-1632 FDA: Initialize UI Framework: migrate header and product family select
* CDP-1630 FDA: Implement improvements from SAR Review: fix issue 'Class has a circular dependency with other classes' for CreateAdditionalFundWindow
* CDP-1630 FDA: Implement improvements from SAR Review: fix issue 'Class has a circular dependency with other classes' for AclciUsageUpdateWindow and FasUpdateUsageWindow
* CDP-1630 FDA: Implement improvements from SAR Review: fix issue 'Class has a circular dependency with other classes' for ConfirmActionDialogWindow

20.0.12
-
* CDP-1632 FDA: Initialize UI Framework: add configuration files
* CDP-1630 FDA: Implement improvements from SAR Review: add database index by rh_account_number for table df_usage
* CDP-1632 FDA: Initialize UI Framework: add styles
* CDP-1630 FDA: Implement improvements from SAR Review: add database index by status_ind for table df_usage
* B-80927 - FDA & ACL: Manually enter proxy CUP and update CUP Flag: hide edit button from manager, add requered validator for action reason message
* CDP-1276 FDA: Ability to clear Price and Content fields on Value : add styles to differentiate between the Clear buttons in the Price and Content sections

20.0.11
-
* CDP-1632 FDA: Initialize UI Framework: add module, base packages, and initialize build.gradlew file
* CDP-1276 FDA: Ability to clear Price and Content fields on Value : implement cleaning the Price section
* CDP-1276 FDA: Ability to clear Price and Content fields on Value : implement cleaning the Content section
* CDP-1630 FDA: Implement improvements from SAR Review: add missing database index for table df_udm_value_audit
* CDP-1632 FDA: Initialize UI Framework: add common vaadin modules
* B-80927 - FDA & ACL: Manually enter proxy CUP and update CUP Flag: change CUP flag validation message
* B-80927 - FDA & ACL: Manually enter proxy CUP and update CUP Flag: hide edit button from researcher

20.0.10
-
* B-80927 - FDA & ACL: Manually enter proxy CUP and update CUP Flag: implement backend logic to check value batch for ability to recalculate proxy values

20.0.9
-
* CDP-1769: PI 20 Sprint 3 Tech Debt fix CVE-2023-44487
* B-80927 - FDA & ACL: Manually enter proxy CUP and update CUP Flag: add edit button
* B-80927 - FDA & ACL: Manually enter proxy CUP and update CUP Flag: implement logic to be able to update CUP and CUP flag

20.0.8
-
* B-80341 - FDA & ACLCI: Update the file upload with an additional field: add new field to the export report

20.0.7
-
* CDP-1887 FDA: Update value batch requirements for ACL works from previous periods: update SQL to retrive rightsholder specific details with correct payees and amounts

20.0.6
-
* B-80348 - FDA & ACLCI: Update Grade Groups to include new values: add new Grade Group values
* CDP-1946: FDA: updateRightsholdersQuartzJob: Payees that are present in ACL shares only are not updated by the job
* B-80341 - FDA & ACLCI: Update the file upload with an additional field: implement validator for new field
* B-80341 - FDA & ACLCI: Update the file upload with an additional field: implement repository logic to preserve number of students column
* B-80341 - FDA & ACLCI: Update the file upload with an additional field: fix integration tests
* B-80341 - FDA & ACLCI: Update the file upload with an additional field: add Reported Number of Students column to the UI

20.0.5
-
* CDP-1887 FDA: Update value batch requirements for ACL works from previous periods: add index by TOU, detail ID to ACL shares table

20.0.4
-
* CDP-1887 FDA: Update value batch requirements for ACL works from previous periods: remove WrWrkInst population from liquibase script

20.0.3
-
* CDP-1887 FDA: Update value batch requirements for ACL works from previous periods: introduce WrWrkInst column in ACL shares to improve performance

20.0.2
-
* CDP-1887 FDA: Update value batch requirements for ACL works from previous periods: update ACL calculation tab queries to use materialization

20.0.1
-
* CDP-1617 FDA: SonarQube metric improvements: Fix SonarQube bug 'Remove this "@Scope" annotation'
* CDP-1617 FDA: SonarQube metric improvements: Fix SonarQube bug 'Rename this method; there is a "private" method in the parent class with the same name'
* CDP-1617 FDA: SonarQube metric improvements: Fix SonarQube bug 'Method check a map with containsKey(), before using get()'
* CDP-1887 FDA: Update value batch requirements for ACL works from previous periods: update value populating logic to include all works without values; add index by WrWrkInst to grant detail table
* CDP-1887 FDA: Update value batch requirements for ACL works from previous periods: update ACL workflow integration test
* B-80473 FDA: Add Last Comment field to value edit screen: modify View UDM Value widget

20.0.0
-
* B-80473 FDA: Add Last Comment field to value edit screen: modify Edit UDM Value widget
* B-79027 FDA: ACL is default product on opening the UI: change order in Product Family dropdown
* CDP-1616 SonarQube metric improvements: fix bugs with critical status
* CDP-1617 SonarQube metric improvements: Fix vulnerabilities and high priority bugs in FDA

19.0.3
-
* CDP-1778: FDA ACL: Add Detailed Licensee Classes to FDA
* CDP-1778: FDA ACL: Add Detailed Licensee Classes to FDA: implement integration test
* CDP-1778: FDA ACL: Add Detailed Licensee Classes to FDA: implement integration service test

19.0.2
-
* CDP-1166 FDA: SonarQube Report Improvements: add SonarQube configuration
* CDP-1166 FDA: SonarQube Report Improvements: fix SonarQube bug 'Method builds a list from one element using Arrays.asList'
* CDP-1166 FDA: SonarQube Report Improvements: fix SonarQube bug 'Method converts String to boxed primitive using excessive boxing'
* CDP-1166 FDA: SonarQube Report Improvements: fix SonarQube bug 'Class uses an ordinary set or map with an enum class as the key'
* CDP-1166 FDA: SonarQube Report Improvements: fix SonarQube bug 'Class defines List based fields but uses them like Sets'
* CDP-1166 FDA: SonarQube Report Improvements: fix SonarQube bug 'Method declares an identity lambda function rather than using Function.identity()'
* CDP-1166 FDA: SonarQube Report Improvements: fix SonarQube bug 'Method appears to call the same method on the same object redundantly'
* CDP-1166 FDA: SonarQube Report Improvements: fix SonarQube bug 'Method of Singleton class writes to a field in an unsynchronized manner'
* CDP-1166 FDA: SonarQube Report Improvements: fix SonarQube bug 'Class defines fields that are used only as locals'
* CDP-1166 FDA: SonarQube Report Improvements: fix SonarQube bug 'Class defines List based fields but uses them like Sets'
* CDP-1166 FDA: SonarQube Report Improvements: fix SonarQube bug 'Constructor makes call to non-final method'
* CDP-1166 FDA: SonarQube Report Improvements: fix SonarQube bug 'Class auto wires the same object into two separate fields in a class hierarchy'
* CDP-1166 FDA: SonarQube Report Improvements: fix SonarQube bug 'Optional value should only be accessed after calling isPresent()'
* CDP-1824: Clean up logs to improve space in splunk: remove micrometer configuration for repositories
* CDP-1824: Clean up logs to improve space in splunk: update configuration for service layer
* CDP-1760 Tech Debt: improve performance of ACL tab selection

19.0.1
-
* B-81045 FDA: Ability to Export Value Queue List View: add Export button
* B-81045 FDA: Ability to Export Value Queue List View: implement report handler
* B-81045 FDA: Ability to Export Value Queue List View: implement repository logic for generating values export
* B-81044 FDA: Ability to Export Baseline Values List View: add Export button
* B-81044 FDA: Ability to Export Baseline Values List View: implement the report handler
* B-81044 FDA: Ability to Export Baseline Values List View: implement the repository
* B-80266 FDA: Add Ineligible reason Non participating RH to drop down: add ineligible reason Non participating RH
* B-78643 Tech Debt: fix security vulnerabilities CVE-2023-34981, CVE-2023-33546
* B-78643 Tech Debt: fix security vulnerability CVE-2023-2976

19.0.0
-
* B-75922 FDA: Move from Perf4j to Micron Meter: update Gradle project
* B-75922 FDA: Move from Perf4j to Micron Meter: update spring cofiguration
* B-75922 FDA: Move from Perf4j to Micron Meter: update logging configuration file
* B-75922 FDA: Move from Perf4j to Micron Meter: update repository
* B-75922 FDA: Move from Perf4j to Micron Meter: update repository
* B-75922 FDA: Move from Perf4j to Micron Meter: update service
* B-75922 FDA: Move from Perf4j to Micron Meter: update integration
* B-80117 FDA: TOU for FAS: implement grant priorities for FAS TOU

18.0.4
-
* B-78642 Tech Debt: FDA: fix security vulnerability CVE-2023-28709
* B-78914 Migrate ShareCalc ACL data to FDA - Scenario Inputs: add index by batch ID to ACL usages table

18.0.3
-
* B-79569 ACL: Revise the process to find rights in an updated hierarchy: update grant_product_family for ACLCI product family

18.0.2
-
* CDP-1146: FDA: Revised process of getting UDM rights: 'License type' payload in the rights request contains product family instead of type of use for transactional products

18.0.1
-
* CDP-1143 FDA: SAL: Usages tab: ‘View Fund Pool’ window: Sorting on ‘ITEM BANK SPLIT %’ column is executed as string
* B-80143 FDA application name change: Change name of application from Foreign Distribution to Federated Distribution
* B-79524 FDA: ACL: Multi-select filter for Action Reason: implement Action Reason filter widget
* B-79524 FDA: ACL: Multi-select filter for Action Reason: add Action Reason filter on UDM Usages tab
* B-79524 FDA: ACL: Multi-select filter for Action Reason: update Applied Filters panel with Action Reason filter
* B-79524 FDA: ACL: Multi-select filter for Action Reason: update SQL query to  select UDM usages by filter
* B-79524 FDA: ACL: Multi-select filter for Action Reason: make changes based on comments
* B-79569 ACL: Revise the process to find rights in an updated hierarchy: implement query for ACL UDM USAGE
* B-79569 ACL: Revise the process to find rights in an updated hierarchy: improve integration tests after adding data
* B-77996 Tech Debt: FDA: fix vulnerabilities CVE-2023-20862, CVE-2023-20863, CVE-2023-28708

18.0.0
-
* B-78666 FDA: Add columns to NTS WD Batch Summary Report: add number and gross amount columns for details with status TO_BE_DISTRIBUTED
* B-57778 FDA: ACLCI usage tab export: implement Export button
* B-57778 FDA: ACLCI usage tab export: implement ACLCI report repository
* B-57778 FDA: ACLCI usage tab export: update report service
* B-68574 FDA: View and Delete ACLCI batch: implement view usage batch window
* B-68574 FDA: View and Delete ACLCI batch: implement back end logic to delete Usage Batch
* B-68574 FDA: View and Delete ACLCI batch: apply permissions for delete button
* B-77995 Tech Debt: FDA: apply Function.identity() instead of no-op lambda method
* B-77994 Tech Debt: FDA: fix SonarQube new code bugs
* B-77995 Tech Debt: FDA: replace copy constructor setters call with direct assingment
* B-77995 Tech Debt: FDA: use expected size constructor for ArrayList for CSV report handlers
* B-77995 Tech Debt: FDA: avoid redundant boxing for boolean values

17.0.18
-
* CDP-1132 FDA: UDM Values Tab: Exception occurs on Pub Types filter window when search is performed

17.0.17
-
* B-77079 FDA: Comparison by Aggregate Licensee Class and Title Report: rename Total Amount columns
* B-77994 Tech Debt: FDA: fix vulnerabilities CVE-2023-20860, CVE-2023-20861
* B-77994 Tech Debt: FDA: fix security vulnerability CVE-2022-1471

17.0.16
-
* B-77108 FDA: Fund Pools by Aggregate Licensee Class Report: make changes based on comments on code review
* B-77994 Tech Debt: FDA: introduce constants for default amount equal to  BigDecimal.ZERO
* B-77079 FDA: Comparison by Aggregate Licensee Class and Title Report: make changes based on comments in code review
* B-77079 FDA: Comparison by Aggregate Licensee Class and Title Report: refine formatting amounts and rounding percents
* B-77079 FDA: Comparison by Aggregate Licensee Class and Title Report: rename scenario filter widgets

17.0.15
-
* B-76852 FDA: switch integrations for paid liabilities to SNS/SQS FIFO: apply dist-common configuration
* B-77108 FDA: Fund Pools by Aggregate Licensee Class Report: update sort order, disable export button on period changes
* B-77079 FDA: Comparison by Aggregate Licensee Class and Title Report: implement backend logic to generate report
* B-77079 FDA: Comparison by Aggregate Licensee Class and Title Report: add selected scenarios to report metadata
* B-77994 Tech Debt: FDA: update paid Usages consumer to copy reported standard number for new details

17.0.14
-
* B-76640 FDA: Send notification email when details arrive to LM from FDA: implement headers scenarioId, scenarioName, productFamily, numberOfMessages, sendDate during sending scenarios to LM
* B-77082 FDA: Show Created By and Created Date on View Fund Pool screen: add Created By and Created Date columns to SAL Fund Pool view window and export file
* B-77079 FDA: Comparison by Aggregate Licensee Class and Title Report: implement report window
* B-77108 FDA: Fund Pools by Aggregate Licensee Class Report: implement backend to populate filters
* B-77108 FDA: Fund Pools by Aggregate Licensee Class Report: implement window to generate report
* B-77108 FDA: Fund Pools by Aggregate Licensee Class Report: implement backend to generate report
* B-76852 FDA: switch integrations for paid liabilities to SNS/SQS FIFO: migrate to SNS/SQS FIFO
* B-76852 FDA: switch integrations for paid liabilities to SNS/SQS FIFO: add performance logs
* B-76852 FDA: switch integrations for paid liabilities to SNS/SQS FIFO: refine policy configuration in PaidUsageSubscriber
* B-77994 Tech Debt: FDA: add missing columns to ACL Calculation usage export file

17.0.13
-
* B-77993 Tech Debt: FDA: replace Google Guava by Java 11 features
* B-77993 Tech Debt: FDA: introduce common PI matching service to init and destroy RupEsApi in single place
* B-77080 FDA: Show Reported Standard Number and Reported Title on UI & Exports: refine batch upload for exported usages
* B-77080 FDA: Show Reported Standard Number and Reported Title on UI & Exports: add reported standard number to view scenario window
* B-77080 FDA: Show Reported Standard Number and Reported Title on UI & Exports: update corresponding tests

17.0.12
-
* B-77080 FDA: Show Reported Standard Number and Reported Title on UI and Exports: refine Reconcile Rightsholder tests with checking Work Title field
* B-77080 FDA: Show Reported Standard Number and Reported Title on UI and Exports: add Reported Standard Number and Reported Title columns on Refresh Scenario window
* B-77080 FDA: Show Reported Standard Number and Reported Title on UI and Exports: exclude Work Title from FAS usages update
* B-77993 Tech Debt: FDA: refactor applied filter backend logic at usage tab for all product families
* B-77993 Tech Debt: FDA: refactor applied filter backend logic at audit tab for all product families

17.0.11
-
* B-77141 FDA: Display all filtered criteria for filters: add display applied filters on Proxy Value tab for ACL
* B-77141 FDA: Display all filtered criteria for filters: make changes based on comments in code review
* B-76925 FDA: Deleted works column for editing wrwrkinsts: add MDWMS deleted column to Acl usage export
* B-76925 FDA: Deleted works column for editing wrwrkinsts: make changes based on comments in code review
* B-77080 FDA: Show Reported Standard Number and Reported Title on UI and Exports: add Reported Standard Number and Reported Title columns for Scenario Details export
* B-77080 FDA: Show Reported Standard Number and Reported Title on UI and Exports: add Reported Standard Number and Reported Title columns on Usages tab and export
* B-77080 FDA: Show Reported Standard Number and Reported Title on UI and Exports: add Reported Standard Number and Reported Title columns on Audit tab and export
* B-77080 FDA: Show Reported Standard Number and Reported Title on UI and Exports: update logic for inserting FAS usages with reported title field
* B-77080 FDA: Show Reported Standard Number and Reported Title on UI and Exports: update logic for inserting FAS usages with reported standard number field
* B-77080 FDA: Show Reported Standard Number and Reported Title on UI and Exports: update discrepancy building with System Title in DiscrepancyBuilder

17.0.10
-
* B-76925 FDA: Deleted works column for editing wrwrkinsts: implement PI integration logic
* B-76925 FDA: Deleted works column for editing wrwrkinsts: refactor back end logic related to populating Acl Usage Batch
* B-77141 FDA: Display all filtered criteria for filters: add display applied filters on Audit tab for FAS/FAS2, NTS, AACL, SAL

17.0.9
-
* B-76925 FDA: Deleted works column for editing wrwrkinsts: implement Liquibase script to add isDeletedFlag
* B-76925 FDA: Deleted works column for editing wrwrkinsts: add column MDWMS Deleted on UI
* B-76925 FDA: Deleted works column for editing wrwrkinsts: add filter and applied filter functionality for MDWMS Deleted
* B-76925 FDA: Deleted works column for editing wrwrkinsts: add property for dpdel PI index
* B-76696 FDA: Migration to Open Search: migrate from ES to OpenSearch
* B-76696 FDA: Migration to Open Search: rename credentials properties for OpenSearch
* B-77141 FDA: Display all filtered criteria for filters: add display applied filters on Usage tab for FAS/FAS2, NTS, AACL, SAL
* B-77992 Tech Debt: FDA: fix sorting by status in Update Usages grids
* B-77992 Tech Debt: FDA: refine width of system title column in  update usages windows for FAS, SAL, ACLCI

17.0.8
-
* B-78452 FDA: Adjust system to receive array for Market from RMS: change tests and test data that represents RMS responses
* B-77992 Tech Debt: FDA: add auto disable functionality to Delete,Exclude, Confirm, Update, Update Rightsholder buttons

17.0.7
-
* B-77083 FDA: Add ability to exclude details: make changes based on comments in code review
* B-56525 FDA: Fundpool data Report: add test cases to the integration test
* B-76876 FDA: Edit usage details: apply the changes to FAS2 usages

17.0.6
-
* CDP-1120 FDA: SAL: Usages tab: Usages table does not remove selections in some cases
* CDP-1119 FDA: SAL: Scenarios tab: Choose Scenarios windows: All selected checkboxes become deselected after unchecking at least one of them filtered by search field
* B-56525 FDA: Fundpool data Report: implement UI and controller to generate report
* B-56525 FDA: Fundpool data Report: implement back end logic
* B-76766 FDA: Create Batch Status subtab for UDM tab: make changes based on comments
* B-76695 FDA: Export list of fund pools: make changes based on comments
* B-77081 FDA: Allow multi select when approving SAL scenarios: make changes based on comments in code review
* B-77081 FDA: Allow multi select when approving SAL scenarios: hide the Select All checkbox in the empty grid
* B-76876 FDA: Edit usage details: implement getting rights for usages
* B-76876 FDA: Edit usage details: implement closing Update Usages window
* B-76876 FDA: Edit usage details: implement service to get work by Wr Wrk Inst
* B-77992 Tech Debt: FDA: add implementation of test in AclciUsageControllerTest
* B-77992 Tech Debt: FDA: implement filter tests for Acl Fund Pool repository

17.0.5
-
* B-77081 FDA: Allow multi select when approving SAL scenarios: implement Choose Scenarios to Reject window
* B-76695 FDA: Export list of fund pools: create integration test to verify Fund Pools export
* B-76766 FDA: Create Batch Status subtab for UDM tab: implement repository logic to get batch statuses
* B-77083 FDA: Add ability to exclude Details: implement backend logic to verify UD details attached to selected usages
* B-77083 FDA: Add ability to exclude Details: implement backend functionality to delete usages from database
* B-76876 FDA: Edit usage details: implement usages threshold processing
* B-76876 FDA: Edit usage details: implement repository to update usages
* B-76876 FDA: Edit usage details: implement service to update usages

17.0.4
-
* B-76657 FDA: Approval restriction: implement prohibition of approving scenario for the user who performed the submitting
* B-77083 FDA: Add ability to exclude Details: implement Exclude Details button with permissions
* B-77083 FDA: Add ability to exclude Details: fix checkstyle issue with line length
* B-77083 FDA: Add ability to exclude Details: implement Rightsholders filter
* B-77083 FDA: Add ability to exclude Details: implement multi-select functionality
* B-76695 FDA: Export list of fund pools: create an Export button in SAL fund pool view window
* B-76695 FDA: Export list of fund pools: implement controller logic to generate Fund Pools export list
* B-76695 FDA: Export list of fund pools: implement service logic to generate Fund Pools export list
* B-76695 FDA: Export list of fund pools: create handler to export Fund Pool in csv file
* B-76695 FDA: Export list of fund pools: implement repository logic to generate Fund Pools export list
* B-77081 FDA: Allow multi select when approving SAL scenarios: Extract common logic from SalSendToLmWindow and introduce common window
* B-77081 FDA: Allow multi select when approving SAL scenarios: implement Choose Scenarios to Submit for Approval window
* B-77081 FDA: Allow multi select when approving SAL scenarios: implement backend logic to handle actions for list of scenarios
* B-77081 FDA: Allow multi select when approving SAL scenarios: implement Choose Scenarios to Approve window and rename Choose Scenarios button to Send to LM
* B-76766 FDA: Create Batch Status subtab for UDM tab: add Batch Status subtab on UI
* B-76766 FDA: Create Batch Status subtab for UDM tab: implement controller logic to get batch statuses
* B-76766 FDA: Create Batch Status subtab for UDM tab: implement service logic to get batch statuses
* B-76876 FDA: Edit usage details: implement the Update Usages button
* B-76876 FDA: Edit usage details: implement the Update Usages window
* B-76876 FDA: Edit usage details: implement the Edit multiple FAS Usages window
* B-76876 FDA: Edit usage details: implement validation messages for selected usages count
* B-68612 Tech Debt: FDA: fix Aclci fund pool load window size
* B-77924 Tech Debt: FDA: decrease margin at button on update ACLCI usages window
* B-77924 Tech Debt: FDA: introduce common Deserializer for JsonB fields
* B-77924 Tech Debt: FDA: add assertion for field style name to verifyTextField in UiTestHelper
* B-77924 Tech Debt: FDA: split DomainVerifierTest into separate tests
* B-77924 Tech Debt: FDA: set auto disabled for buttons Exclude by RH, Exclude By Payee, Exclude Details, Redesignate Details
* B-77924 Tech Debt: FDA: fix key word for error messages in response from CRM
* B-77924 Tech Debt: FDA: add auto disable to Upload, Approve, View buttons in AACL, ACLCI, FAS, NTS and SAL
* B-77924 Tech Debt: FDA: fix Sal Update Righstholder Window size
* B-77924 Tech Debt: FDA: add auto disable to Save button in SAL, ACL, ACLCI, FAS

17.0.3
-
* B-68577 FDA: Load ACLCI fund pool: extend range of amount of student fields

17.0.2
-
* B-77418 Tech Debt: FDA: replace Amount Zero Validator to Amount Validator for all ACL filters
* B-68612 Tech Debt: FDA: replace Arrays.asList() by List.of() in dist-foreign-ui package
* CDP-1115 FDA: ACLCI: Load Fund Pool window: When entering at least 4 numbers into any Grade field the system automatically adds comma as number separator

17.0.1
-
* B-68554 FDA: ACLCI UI usage view: implement controller to save fund pool
* B-68554 FDA: ACLCI UI usage view: refactor the dialog
* B-75591 FDA: ACLCI usage editing: apply permissions for Update Usages button
* B-77418 Tech Debt: FDA: split test data into separate methods
* B-77418 Tech Debt: FDA: add service logic to delete ACLCI fund pool
* B-77418 Tech Debt: FDA: revert parentheses in groovy file
* B-68612 Tech Debt: FDA: replace Collections.addAll() by constructor initialization, direct static fields calls by methods and change method references
* B-77418 Tech Debt: FDA: change id fields in AclciMultipleEditUsagesWindow
* B-68612 Tech Debt: FDA: replace Arrays.asList() by List.of() in packages dist-foreign-domain, dist-foreign-integration, dist-foreign-service
* B-68612 Tech Debt: FDA: replace Arrays.asList() by List.of()
* B-68612 Tech Debt: FDA: replace Collections.emptyMap() by Map.of()
* B-68612 Tech Debt: FDA: replace Collections.emptyList() by List.of()

17.0.0
-
* B-68577 FDA: Load ACLCI fund pool: implement domain object
* B-68577 FDA: Load ACLCI fund pool: implement Liquibase script to save ACLCI fund pool fields
* B-68577 FDA: Load ACLCI fund pool: implement controllers
* B-68577 FDA: Load ACLCI fund pool: implement MyBatis mapper to save fund pool and repository integration test
* B-68577 FDA: Load ACLCI fund pool: implement service to save fund pool
* B-68577 FDA: Load ACLCI fund pool: implement load fund pool menu item
* B-68577 FDA: Load ACLCI fund pool: implement serializer, deserializer, JSON handler
* B-75591 FDA: ACLCI usage editing: add Update Usages button to ACLCI Usages tab
* B-75591 FDA: ACLCI usage editing: implement window to update usages
* B-75591 FDA: ACLCI usage editing: implement backend logic to update usages
* B-75591 FDA: ACLCI usage editing: implement window to update rightsholder and wr wrk inst
* B-75591 FDA: ACLCI usage editing: implement service logic to update usages
* B-68554 FDA: ACLCI UI usage view: implement load fund pool dialog
* B-68554 FDA: ACLCI UI usage view: implement coverage years validator
* B-68554 FDA: ACLCI UI usage view: implement service to calculate fund pool amounts
* B-68554 FDA: ACLCI UI usage view: rename domain object
* B-68576 FDA: Generate a Grade Group for ACLCI usages: Implement Grade Group column
* B-68576 FDA: Generate a Grade Group for ACLCI usages: add sorting for column Grade Group
* B-68576 FDA: Generate a Grade Group for ACLCI usages: Implement logic to get grade group based on usage grade
* B-77418 Tech Debt: FDA: refactor SalGradeGroupEnum class
* B-77418 Tech Debt: FDA: replace org.junit.Assert.assertThat by org.hamcrest.MatcherAssert.assertThat
* B-77418 Tech Debt: FDA: replace com.google.common.collect.ImmutableList.of() by java.util.List.of()
* B-77418 Tech Debt: FDA: add ordering for scenarios in metadata ACL reports
* B-77418 Tech Debt: FDA: refactor deserialization logic for NTS batch, scenario JSON fields and SAL fund pool fields

16.0.23
-
* B-75669 FDA: ACLCI UI usage view filters: make changes based on comments in code review
* B-68555 FDA: Load ACLCI usage batch: expand coverage_period column
* B-68555 FDA: Load ACLCI usage batch: implement workflow integration test
* B-68612 Tech Debt: FDA: change column width on ACLCI usage widget
* CDP-1100 FDA: Upgrade to Java 11

16.0.22
-
* B-68555 FDA: Load ACLCI usage batch: implement permissions
* B-68555 FDA: Load ACLCI usage batch: implement grade validator
* B-76548 FDA: ACLCI Media Type Weights: update the CSV processor to set Media Type Weight

16.0.21
-
* B-75669 FDA: ACLCI UI usage view filters: implement widget, controller and add LicenseTypes filed to UsageFilter
* B-75669 FDA: ACLCI UI usage view filters: implement backend logic to retrieve usages to display and delete Reported Page Range and Reported VolNumberSeries
* B-71444 FDA: Eligible Annual Copies Report: adjust logic to get periods from UDM usage batch and adjust query to generate report
* B-68556 FDA: Find RH information for ACLCI usages: make changes based on comments
* B-68555 FDA: Load ACLCI usage batch: implement load batch dialog
* B-68555 FDA: Load ACLCI usage batch: implement usage controller

16.0.20
-
* B-68557 FDA: ACLCI Retrieve from PI Index: implement ACLCI matching consumer and service logic to retrieve data for matching
* B-68556 FDA: Find RH information for ACLCI usages (get grants): implement logic for getting rights
* B-68556 FDA: Find RH information for ACLCI usages (get grants): implement consumer and processor for getting rights
* B-68556 FDA: Find RH information for ACLCI usages (get grants): update AclciUsage domain with batch period and date field
* B-71444 FDA: Eligible Annual Copies Report (for Power BI): implement UI logic to generate report
* B-71444 FDA: Eligible Annual Copies Report (for Power BI): implement backend logic to generate report
* B-71444 FDA: Eligible Annual Copies Report (for Power BI): implement backend logic to get information from DB for generationg report
* B-74953 FDA: Work Shares by Aggregate Licensee Class: implement SQL query to generate report
* B-74953 FDA: Work Shares by Aggregate Licensee Class: add button to generate report on metadata panel
* B-74953 FDA: Work Shares by Aggregate Licensee Class: implement domain object and report data handler
* B-74953 FDA: Work Shares by Aggregate Licensee Class: implement service layer to generate report
* B-68555 FDA: Load ACLCI usage batch: implement Liquibase script
* B-68555 FDA: Load ACLCI usage batch: implement domain objects
* B-68555 FDA: Load ACLCI usage batch: implement repository for usages
* B-68555 FDA: Load ACLCI usage batch: implement services for usage batches and usages
* B-68555 FDA: Load ACLCI usage batch: implement CSV processor for the uploaded usages
* B-68555 FDA: Load ACLCI usage batch: refactor usage license type from string to enum
* B-68555 FDA: Load ACLCI usage batch: implement repository for ACLCI usage batch fields
* B-68555 FDA: Load ACLCI usage batch: implement workflow test
* B-74822 Tech Debt: FDA: split test data into separate files for each test method
* B-74822 Tech Debt: FDA: fix security vulnerabilities CVE-2022-25857, CVE-2022-38749, CVE-2022-38750, CVE-2022-38751, CVE-2022-38752, CVE-2022-41854

16.0.19
-
* B-73331 FDA: Provide TOU information to RC for SAL details: remove IB/UD postfix after uid for SAL
* B-74822 Tech Debt: FDA: fix audit records order for sending scenario to LM
* B-74822 Tech Debt: FDA: update Scenario name validation to allow 255 length
* B-74822 Tech Debt: FDA: remove SENT TO LM status from ‘Status’ filter on Calculations Scenarios tab
* B-74822 Tech Debt: FDA: add index by ccc_event_id for df_usage_archive table
* B-74822 Tech Debt: FDA: rename Reported Standard Number to Reported Standard Number or Image ID Number for SAL and ACLCI PF

16.0.18
-
* B-74623 FDA: Send distribution date to the Datamart: Adjust logic to send distribution date and TOU to the CRM
* B-74822 Tech Debt: FDA: implement AclWorkflowIntegrationTest
* B-74822 Tech Debt: FDA: refine columns width on ACL scenarios tab

16.0.17
-
* B-68554 FDA: ACLCI UI usage view: implement ACLCI tab
* B-68554 FDA: ACLCI UI usage view: implement ACLCI grid
* B-72659 FDA: Allow multi-edit when making non-granted works Eligible: hide the Select All checkbox in the grid is empty
* B-74646 FDA: View all scenario specific usage details: increase columns width for Print/Digital Payee Account # and Price
* B-74822 Tech Debt: FDA: add TOU column id for footer joining on Results by Rightsholder: Usage Details window

16.0.16
-
* B-72659 FDA: Allow multi-edit when making non-granted works Eligible: refactor the usages repository
* B-72659 FDA: Allow multi-edit when making non-granted works Eligible: refactor the usages service
* B-72659 FDA: Allow multi-edit when making non-granted works Eligible: refactor the widgets
* B-74646 FDA: View all scenario specific usage details: implement common window for ACL scenario details by rightsholder and all ACL scenario details, replace export details button
* B-74646 FDA: View all scenario specific usage details: implement backend logic
* B-68554 FDA: ACLCI UI usage view: implement ACLCI filter widget and controller
* B-68554 FDA: ACLCI UI usage view: implement ACLCI usage widget and controller

16.0.15
-
* CDP 1103: FDA: ACL Grant Set tab: Grant Details Count always displays '0' value
* B-74821 Tech Debt: FDA: add materialization for queries to insert details and shares during scenario creation
* B-74821 Tech Debt: FDA: add index by df_acl_grant_set_uid for df_acl_grant_detail table

16.0.14
-
* CDP-1102 FDA: Export usages without Pub Type/CUP: Records in the exported file are duplicated for usages having 2 eligible grants (Print&Digital)

16.0.13
-
* CDP-1101 FDA: ACL: Create Scenario window: Non-editable scenario can be created with editable Usage Batch and Grant Set if Editable checkbox is deselected after populating usage batch and grant set fields
* B-74476 FDA: Export for Detail ID (Usages with no Pub Type/CUP): implement the report controller
* B-74476 FDA: Export for Detail ID (Usages with no Pub Type/CUP): implement the report widget
* B-74414 FDA: Delete Calculation Tab Usage Batch: adjust permissions for view/delete Usage Batch
* B-57810 FDA: Send ACL scenario to LM: adjust logic to display metadata panel and view window for archived scenarios
* B-57810 FDA: Send ACL scenario to LM: adjust logic to export scenario rightsholder for ARCHIVED scenarios
* B-57810 FDA: Send ACL scenario to LM: remove logic for archiving details after sending to LM
* B-74821 Tech Debt: FDA: fix security vulnerabilities CVE-2022-31690, CVE-2022-31692

16.0.12
-
* B-74414 FDA: Delete Calculation Tab Usage Batch: make changes based on comments in code review
* B-75349 FDA: Add filters for ACL scenarios view: implement backend logic to retrieve data for scenario filters and to retrieve data for scenario by filter
* B-74476 FDA: Export for Detail ID (Usages with no Pub Type/CUP): implement the report repository
* B-74476 FDA: Export for Detail ID (Usages with no Pub Type/CUP): implement the report service
* B-57810 FDA: Send ACL scenario to LM: adjust logic for creating message for sending to LM
* B-57810 FDA: Send ACL scenario to LM: Implement logic to get scenario details to send to LM
* B-57810 FDA: Send ACL scenario to LM: implement backend logic to send details to LM
* B-57810 FDA: Send ACL scenario to LM: implement logic to delete scenario details
* B-74821 Tech Debt: FDA: update size for ACL report window, for UDM filter window
* B-74821 Tech Debt: FDA: revert code coverage gradle config
* B-74821 Tech Debt: FDA: update size for ACL report window, for UDM filter window
* B-74821 Tech Debt: FDA: revert code coverage gradle config
* B-74821 Tech Debt: FDA: fix security vulnerability CVE-2022-42003

16.0.11
-
* B-61502 FDA: Tax Notification Report for ACL: implement report menu item
* B-61502 FDA: Tax Notification Report for ACL: implement report file name
* B-57804 FDA: Submit and Approve an ACL scenario: make changes based on comments in code review
* B-57810 FDA: Send ACL scenario to LM: Implement logic to move scenario details to archive tables
* B-75349 FDA: Add filters for ACL scenarios view: implement ACL Scenario Filter widget and controller
* B-57685 FDA: Baseline Value updates report: implement data handler to generate CSV report

16.0.10
-
* B-74414 FDA: Delete Calculation Tab Usage Batch: implement view window and menu item
* B-74414 FDA: Delete Calculation Tab Usage Batch: implement backend logic to delete ACL Usage Batch
* B-57804 FDA: Submit and Approve an ACL scenario: create Approve, Submit and Reject buttons
* B-57804 FDA: Submit and Approve an ACL scenario: implement logic to validate scenario
* B-57804 FDA: Submit and Approve an ACL scenario: implement logic to update scenario state in database
* B-57804 FDA: Submit and Approve an ACL scenario: implement logic to apply permissions for new buttons
* B-61502 FDA: Tax Notification Report for ACL: implementation of reading necessary scenarios
* B-61502 FDA: Tax Notification Report for ACL: implement reading rightsholders and payees information
* B-61502 FDA: Tax Notification Report for ACL: refactor the existing report service to use ACL rightsholders and payees information
* B-57685 FDA: Baseline Value updates report: implement backend logic to generate report
* B-57685 FDA: Baseline Value updates report: implement backend logic to populate report filters
* B-57685 FDA: Baseline Value updates report: implement window to generate report and add report button to UDM report tab
* B-57685 FDA: Baseline Value updates report: implement query to retrieve information from database to generate report
* B-57810 FDA: Send ACL scenario to LM: Add Sent to LM button and apply permission
* B-57810 FDA: Send ACL scenario to LM: add new archived tables for scenario details
* B-75349 FDA: Add filters for ACL scenarios view: remove Created Date column from Scenarios view
* B-75349 FDA: Add filters for ACL scenarios view: implement ACL Scenario Filter domain object

16.0.9
-
* B-75009 FDA: Liabilities by RH Report: display the newest payee in case of difference in chosen scenarios

16.0.8
-
* B-74954 FDA: Summary of Work Shares by Agg Lic Class Report: separate report button from main metadata panel
* B-75009 FDA: Liabilities by RH Report: implement data handler to generate CSV file
* B-67527 FDA: Create Approver role for ACL: Modify ForeignSecurityUtils to add the Approver role and permissions
* B-67527 FDA: Create Approver role for ACL: delete permission FDA_VIEW_SCENARIO
* B-75336 FDA: Add Payee to the Scenario by RH view: add logging to service for getting payees and fix sorting in populatePayees integration test
* B-75336 FDA: Add Payee to the Scenario by RH view: implement logic to update payee in scenario share details
* B-75336 FDA: Add Payee to the Scenario by RH view: implement backend logic for adding Payee to the Scenario by RH view
* B-75336 FDA: Add Payee to the Scenario by RH view: add Payees to Export Scenario View by RHs
* B-75336 FDA: Add Payee to the Scenario by RH view: add payees to export scenario details
* B-59197 Tech Debt: FDA: extend window widths for view ACL Fund Pool and Grant Set windows
* B-59197 Tech Debt: FDA: introduce reported TOU on drill down details window
* B-59197 Tech Debt: FDA: remove redundant method for report generation from UDM report tab
* B-59197 Tech Debt: FDA: update queries for populating, copying, adding to scenario ACL usages with Price and Content flags

16.0.7
-
* B-73712 FDA: Add more fields to Calculation Usages: add filter on TOU, Reported TOU on the Calculations tab, Usages sub-tab
* B-73712 FDA: Add more fields to Calculation Usages: rename the methods that read ReportedTOU to getReportedTypeOfUses
* B-73712 FDA: Add more fields to Calculation Usages: reorder Channel and TypeOfUse comboboxes on the UDM Usages filters window
* B-75336 FDA: Add Payee to the Scenario by RH view: implement logic to get Rightsholders related to ACL scenario for getting payees
* B-75336 FDA: Add Payee to the Scenario by RH view: implement UI for adding Payee to the Scenario by RH view
* B-75336 FDA: Add Payee to the Scenario by RH view: implement logic to get payees from PRM system
* B-75009 FDA: Liabilities by RH Report: implement queries to retrieve information from database to generate report
* B-75009 FDA: Liabilities by RH Report: implement backend logic to generate report

16.0.6
-
* B-73712 FDA: Add more fields to Calculation Usages: add columns to the Details by RH view: Price, Content, Reported TOU, TO
* B-73712 FDA: Add more fields to Calculation Usages: add columns to the Export Details report: Price, Content, Reported TOU, TOU
* B-73712 FDA: Add more fields to Calculation Usages: add columns to the Calculations tab, Usages sub-tab: Price, Content, Reported TOU, TOU
* B-73116 FDA: CUP Flag: add CUP flag filter to More Filters on Calculations Usages tab
* B-73116 FDA: CUP Flag: update query for publishing UDM Value to baseline with CUP flag
* B-73116 FDA: CUP Flag: update queries for populating and copy of ACL Usages with CUP flag
* B-73116 FDA: CUP Flag: update query for adding ACL usages to scenario with CUP flag
* B-75010 FDA: Liability Details Report: implement handler to generate report
* B-75295 FDA: Liabilities by Agg Lic Class Report: implement totals and metadata report info
* B-75009 FDA: Liabilities by RH Report: add payee_account_number column to df_acl_share_detail table
* B-75009 FDA: Liabilities by RH Report: implement Liabilities by RH Report button on Report tab
* B-74954 FDA: Summary of Work Shares by Agg Lic Class Report: remove Supplier from AclScenariosController for reportInfo initialization
* B-74954 FDA: Summary of Work Shares by Agg Lic Class Report: rename report to project style and refactor test data for integration test
* B-74954 FDA: Summary of Work Shares by Agg Lic Class Report: make changes based on comments in code review

16.0.5
-
* B-64703 FDA: improve performance on UI filtering/data processing: improve filtering by usage batch on Audit tab
* B-67527 FDA: Create Approver role for ACL: add Approver role with access to FDA application
* B-74415 Grant set name and period for grant set view: implement logic to display new Grant Set Name column on grant sets tab
* B-74415 Grant set name and period for grant set view: implement logic to display grant sets periods column on view grant set window
* B-75295 FDA: Liabilities by Agg Lic Class Report: implement reports sub-tab under Calculations tab
* B-75295 FDA: Liabilities by Agg Lic Class Report: implement common ACL reports window
* B-75295 FDA: Liabilities by Agg Lic Class Report: implement backend logic to populate report filters
* B-75295 FDA: Liabilities by Agg Lic Class Report: set period filter required to generate report
* B-75295 FDA: Liabilities by Agg Lic Class Report: implement dto domain object and CSV report handler
* B-75295 FDA: Liabilities by Agg Lic Class Report: implement common method to generate report info
* B-75295 FDA: Liabilities by Agg Lic Class Report: implement backend logic to generate report
* B-75010 FDA: Liability Details Report: implement Liability Details Report button
* B-75010 FDA: Liability Details Report: implement queries to get information from DB to generate report
* B-75010 FDA: Liability Details Report: implement backend logic to generate report
* B-74954 FDA: Summary of Work Shares by Agg Lic Class Report: implement domain object for ACL Calculation report metadata
* B-74954 FDA: Summary of Work Shares by Agg Lic Class Report: implement backend logic
* B-74954 FDA: Summary of Work Shares by Agg Lic Class Report: implement ui and change report metadata headers and report date format in metadata
* B-73116 FDA: CUP Flag: add CUP flag column to UDM Values tab
* B-73116 FDA: CUP Flag: add content_unit_price_flag column to df_udm_value and df_udm_value_baseline tables
* B-73116 FDA: CUP Flag: add CUP flag column to UDM Baseline Values tab
* B-73116 FDA: CUP Flag: add CUP flag column to Calculations Usages tab and ACL Usages Export report
* B-73116 FDA: CUP Flag: add CUP flag on UDM Edit Value and UDM View Value windows
* B-73116 FDA: CUP Flag: add CUP and CUP flag filters to More Filters on UDM Values tab
* B-73116 FDA: CUP Flag: add CUP flag filter to More Filters on UDM Baseline Values tab
* B-73712 FDA: Add more fields to Calculation Usages: add columns to the UDM tab, Usages sub-tab: Reported TOU, TOU
* B-73712 FDA: Add more fields to Calculation Usages: add columns to the UDM tab, Baseline sub-tab: Reported TOU, TOU
* B-73712 FDA: Add more fields to Calculation Usages: add filter on TOU, Reported TOU on the Calculations tab, Usages sub-tab
* B-73712 FDA: Add more fields to Calculation Usages: add filter on TOU, Reported TOU on the Calculations tab, Baseline sub-tab
* B-73712 FDA: Add more fields to Calculation Usages: replace the TOU widget filter with the combobox

16.0.4
-
* B-75436 FDA: Gradle upgrade for FDA: upgrade to Gradle 5 / RUP Gradle Plugins 7
* B-75436 FDA: Gradle upgrade for FDA: apply ignored PMD rules
* B-74271 Tech Debt: FDA: add unit tests to verify grid items for NTS windows
* B-74271 Tech Debt: FDA: adjust columns width on ACL Scenario drill down details window
* CDP-1099: FDA: Results By Rightsholder - Usage Details window: 'Usage Period' column is populated with 'Period End Date' instead of 'Usage Period' date

16.0.3
-
* B-74271 Tech Debt: FDA: remove lazy loading from View ACL Scenario window
* B-74271 Tech Debt: FDA: add unit tests to verify grid items for FAS scenario windows
* B-74271 Tech Debt: FDA: adjust naming for meta info panel on drill down windows and add new Usage Detail Id column on usage detail window
* B-74271 Tech Debt: FDA: adjust meta layout with dynamic label width on ACL Scenario Drill Down windows

16.0.2
-
* B-57802 FDA: Delete ACL Scenario: make changes based on comments in code review
* B-71854 FDA: Export Scenario View by RHs: implement repository logic to generate ACL scenario by RHs report
* B-73151 FDA: View Agg Lic Class by Rightsholder: change System Title by Wr Wrk Inst filter parameter
* B-73151 FDA: View Agg Lic Class by Rightsholder: make changes based on comments in code review
* B-73151 FDA: View Agg Lic Class by Rightsholder: add auto disabled for hyperlink button and adjust columns size
* B-73149 FDA: View Titles by Rightsholder: add auto disabled of # of Titles button and adjust columns size on ACL Scenario Drill Down Titles window
* B-73150 FDA: Details view by Title and Agg Lic Class: make changes based on comments in code review
* B-73150 FDA: Details view by Title and Agg Lic Class: add sort for columns and change System Title to Wr Wrk Inst filter parameter
* B-74271 Tech Debt: FDA: fix double click issue for ACL scenario buttons
* B-74271 Tech Debt: FDA: fix double click issue for ACL buttons
* B-74271 Tech Debt: FDA: add unit tests to verify grid items for ACL udm windows

16.0.1
-
* B-57802 FDA: Delete ACL Scenario: resolve issue with nulpointer exception when scenarios tab is empty
* B-71854 FDA: Export Scenario View by RHs: add Export button in ACL Scenario window
* B-71854 FDA: Export Scenario View by RHs: implement service logic to generate ACL scenario by RHs report
* B-73151 FDA: View Agg Lic Class by Rightsholder: move hyperlink to Agg Lc CL id and add sort for columns
* B-73149 FDA: View Titles by Rightsholder: move hyperlink to Wr Wrk Inst and add sort for amount columns
* CDP-1097: NTS: Create Additional Fund: Opening 'Batches filter' modal window takes much time
* B-74271 Tech Debt: FDA: display number of works instead of titles on View ACL Scenario window

16.0.0
-
* B-73150 FDA: Details view by Title and Agg Lic Class: implement Usage Details by Title and Agg Lic Class repository
* B-73150 FDA: Details view by Title and Agg Lic Class: implement Usage Details by Title and Agg Lic Class widget
* B-73150 FDA: Details view by Title and Agg Lic Class: implement Usage Details by Title and Agg Lic Class controller and service
* B-73150 FDA: Details view by Title and Agg Lic Class: implement meta info layout in the window
* B-73150 FDA: Details view by Title and Agg Lic Class: implement unit tests for hyperlinks from 'Results by Rightsholder: Title' window
* B-73150 FDA: Details view by Title and Agg Lic Class: implement unit tests for hyperlinks from 'Results by Rightsholder: Aggregate Licensee Class' window
* B-73150 FDA: Details view by Title and Agg Lic Class: implement unit tests for hyperlinks from View Scenario window
* B-73150 FDA: Details view by Title and Agg Lic Class: implement totals row in the window
* B-73150 FDA: Details view by Title and Agg Lic Class: implement resizing the windows according to their order
* B-73150 FDA: Details view by Title and Agg Lic Class: modify footer formatting if usage details have only single type of use
* B-73151 FDA: View Agg Lic Class by Rightsholder: implement widget and controller
* B-73151 FDA: View Agg Lic Class by Rightsholder: implement meta info layout on window
* B-73151 FDA: View Agg Lic Class by Rightsholder: implement backend logic and rename styles for window
* B-73151 FDA: View Agg Lic Class by Rightsholder: add test cases for testFindRightsholderAggLcClassResults
* B-73149 FDA: View Titles by Rightsholder: implement service logic for  ACL Scenario Drill Down Titles window
* B-73149 FDA: View Titles by Rightsholder: implement totals row and meta info layout on ACL Scenario Drill Down Titles window
* B-73149 FDA: View Titles by Rightsholder: implement repository logic for  ACL Scenario Drill Down Titles window
* B-73149 FDA: View Titles by Rightsholder: implement domain object for View Titles by RH window
* B-73149 FDA: View Titles by Rightsholder: implement View Titles by RH window
* B-57802 FDA: Delete ACL Scenario: implement delete button on UI
* B-57802 FDA: Delete ACL Scenario: implement backend logic to delete scenario
* B-57802 FDA: Delete ACL Scenario: implement mediator to control permissions
* B-74271 Tech Debt: FDA: add unit tests to verify grid items for ACL usage windows
* B-74270 Tech Debt: FDA: update unit tests for Usage Age Weight Window
* B-74270 Tech Debt: FDA: update unit tests for Acl Usage Age Weight, Acl Publication Type Weights windows
* B-74271 Tech Debt: FDA: add unit tests to verify grid items for ACL scenario windows
* B-74271 Tech Debt: FDA: improve performance of viewing ACL scenarios

15.1.45
-
* B-74270 FDA: Tech Debt: implement unit tests for the AACL scenarios metadata panel
* B-74270 FDA: Tech Debt: update borders of the Create Acl Grant Set window
* B-74687 FDA: Fine tune performance for ACL components: add index by df_acl_scenario_detail_uid for ACL share detail table

15.1.44
-
* CDP-1085 FDA UAT: Change the calculation from Number of reported copies to Annualized Copies: update scenario creation validation to use valid grants
* B-74687 FDA: Fine tune performance for ACL components: add index by RH Account # for ACL share detail table
* B-71853 FDA: Scenario Tab - ACL Usage Age Weights View: make changes based on comments in code review
* B-74841 Tech Debt: FDA: adjust display of values with trailing "0" on UI and in export reports for Calculations tab
* B-74841 Tech Debt: FDA: adjust display of values with trailing "0" on UI for UDM tab
* B-74270 Tech Debt: FDA: change order of the links in the AACL metadata panel

15.1.43
-
* B-74270 Tech Debt: FDA: PubTypeWeight change field formatting and add unit test for verify ACL scenario details values
* B-74687 FDA: Fine tune performance for ACL components: improve the performance of selecting and viewing ACL scenarios
* B-74841 FDA: Adjust CUP values to allow 10 decimal points: refine display of values on UI and in export reports for ACL product family

15.1.42
-
* B-57806 FDA: Scenario Tab - ACL Licensee Class Mapping View: add Default Agg LC ID, Default Agg LC Name columns to Licensee Class Mapping window
* CDP-1084 FDA: ACL: Calculations tab: Scenarios subtab: Editable checkbox becomes available for Manager when 'Period' or 'License Type' field is populated on 'Create Scenario' window
* CDP-1085 FDA UAT: Change the calculation from Number of reported copies to Annualized Copies: add usages to scenario based on type_of_use field in case of Pring/Digital only grant status
* CDP-1085 FDA UAT: Change the calculation from Number of reported copies to Annualized Copies: replace usage quantity field by annualized_copies and rename it to numberOfCopeis in database
* B-74687 FDA: Fine tune performance for ACL components: improve the performance of selecting and viewing ACL scenarios
* B-74270 Tech Debt: FDA: fix security vulnerabilities CVE-2022-22976, CVE-2022-22978 associated with dependency org.springframework.security
* B-74270 Tech Debt: FDA: extend comoboxes on aggregate licensee classes scenario window
* B-74270 Tech Debt: FDA: change ACL Usage Age Weight Window size
* B-74270 Tech Debt: FDA: Usage Age weight remove excess 0 on view scenario rightsholder details window

15.1.41
-
* B-72122 FDA: Copy ACL Scenario: add style to copy from field and adjust logic to reset fields
* B-57780 FDA: Migrate historical ACL baseline Usage & Value Data from Sharecalc: drop not null constraint from rh_account_number column in df_udm_value table
* B-72124 FDA: Copy ACL Usage Batch: implement backend logic to create copy of selected batch
* B-72123 FDA: Copy ACL Grant Set: add filter clear after copy Grant Set and update filling fields logic

15.1.40
-
* B-57806 FDA: Scenario Tab - ACL Licensee Class Mapping View: modify the metadata panel to show Licensee Class Mapping window
* B-72122 FDA: Copy ACL Scenario: implement logic to update metadata panel
* B-72124 FDA: Copy ACL Usage Batch: add copy from option to create usage batch window
* B-72123 FDA: Copy ACL Grant Set: implement backend logic and update filling UI components

15.1.39
-
* B-57798 FDA: View scenario specific usage details by RH for ACL: make changes based on comments in code review
* B-57798 FDA: View scenario specific usage details by RH for ACL: remove trailing zeros from shares and weighted copies columns
* B-71852 FDA: Scenario modifications for ACL - View Pub Type Weights: modify the repository to read Pub Type Weights
* B-71852 FDA: Scenario modifications for ACL - View Pub Type Weights: modify the metadata panel to show Pub Type Weights window
* B-71853 FDA: Scenario Tab - ACL Usage Age Weights View: modify the metadata panel to show Usage Age Weights window
* B-72122 FDA: Copy ACL Scenario: adjust create scenario window
* B-72122 FDA: Copy ACL Scenario: adjust logic to get information about scenario
* B-72123 FDA: Copy ACL Grant Set: adjust create Grant Set window
* B-74687 FDA: Fine tune performance for ACL components: add indexes by df_acl_scenario_uid to scenario related columns
* B-74269 FDA: Tech Debt: extract scenario usages related queries from IAclScenarioMapper to AclScenarioUsageMapper
* B-74269 FDA: Tech Debt: update formatting in ACL scenario usage mapper

15.1.38
-
* B-74269 FDA: Tech Debt: move Liquibase scripts for AclScenarioUsageRepositoryIntegrationTest into the correct folder
* B-57782 FDA: ACL edit scenario Pub Type Weights: allow zeros as Pub Type Weights

15.1.37
-
* B-74269 FDA: Tech Debt: extract scenario related queries from IAclUsageMapper to separate IAclScenarioUsageMapper part 1
* B-74353 FDA: Saving Pub Type Weights: make changes based on code review
* B-61503 FDA: Edit Aggregate Licensee Class Scenario Inputs: rename grid column captions
* B-74269 Tech Debt: improve performance of loading ACL scenarios
* B-57795 FDA: Calculate ACL Scenario: update calculation and validation to reflect licensee class mapping
* B-74269 FDA: Tech Debt: extract scenario related queries from IAclUsageMapper to separate IAclScenarioUsageMapper part 2
* B-57795 FDA: Calculate ACL Scenario: remove usages with zero amount from scenario
* B-74269 FDA: Tech Debt: extract scenario usages related queries from IAclScenarioMapper to AclScenarioUsageMapper
* B-57795 FDA: Calculate ACL Scenario: drop foreign key from df_acl_share_detail table to df_acl_scenario_detail

15.1.36
-
* B-74353 FDA: Saving Pub Type Weights: implement backend logic to update Pub Type Weights
* B-71855 FDA: Export Scenario specific usage details: add Export Details button to ACL Single Scenario window
* B-71855 FDA: Export Scenario specific usage details: implement service logic for ACL scenario details export
* CDP-1082 FDA: Delete ACL Fund Pool / Grant Set: Exception occurs after trying to delete Fund Pool or Grant Set that is attached to a scenario
* B-71855 FDA: Export Scenario specific usage details: implement repository logic for ACL scenario details export
* B-57795 FDA: Calculate ACL Scenario: implement validation to have usages with corresponding licensee classes to distribute all amounts

15.1.35
-
* B-61503 FDA: Edit Aggregate Licensee Class Scenario Inputs: update logic to fill Aggregate license class Id dropdown box
* B-57781 FDA: Edit usage age weights for ACL while creating a scenario: adjust logic to validate usage batch
* B-61503 FDA: Edit Aggregate Licensee Class Scenario Inputs: fix pmdTest failed
* B-57782 FDA: ACL edit scenario Pub Type Weights: handle Pub Type Weights for non-editable scenarios
* CDP-1083 FDA: ACL: Create Scenario: When creating ACL scenario the system takes pub type weights added for previously created scenario
* B-57798 FDA: View scenario specific usage details by RH for ACL: implement repository logic to retrieve ACL scenario details by scenario id and RH account number

15.1.34
-
* B-74353 FDA: Saving Pub Type Weights: Create df_acl_history_pub_type_weight database and populate it 
* B-57782 FDA: ACL edit scenario Pub Type Weights: implement widget to edit scenario pub types weights 
* B-57795 FDA: Calculate ACL Scenario: distribute SQL queries to insert details and shares separately 
* B-74353 FDA: Saving Pub Type Weights: adjust logic to get ACL history pub type and implement logic to insert new data 
* B-74269 Tech Debt: FDA: remove obsolete TODOs 
* B-57782 FDA: ACL edit scenario Pub Type Weights: update scenario creation widget to show pub types weights dialog 
* B-57798 FDA: View scenario specific usage details by RH for ACL: add reported_type_of_use, content_unit_price_flag, price, price_flag, content, content_flag columns to df_acl_scenario_detail and df_acl_usage tables 
* B-57798 FDA: View scenario specific usage details by RH for ACL: implement ACL scenario detail dto domain object 
* B-61503 FDA: Edit Aggregate Licensee Class Scenario Inputs: implement Licensee classes mapping and implement logic to get default values for mapping 
* B-57782 FDA: ACL edit scenario Pub Type Weights: implement saving scenario pub types weights 
* B-57782 FDA: ACL edit scenario Pub Type Weights: fix failed test 
* B-57782 FDA: ACL edit scenario Pub Type Weights: fix failed test 
* B-57795 FDA: Calculate ACL Scenario: populate Pub Type Weight for scenario details 
* B-57798 FDA: View scenario specific usage details by RH for ACL: implement ACL drill down by rightsholder window and controller logic 
* B-61503 FDA: Edit Aggregate Licensee Class Scenario Inputs: implement logic to save mapping 
* B-57798 FDA: View scenario specific usage details by RH for ACL: implement service logic to retrieve ACL scenario details by scenario id and RH account number 
* B-57781 FDA: Edit usage age weights for ACL while creating a scenario: implement window for editing usage age weights 
* B-57782 FDA: ACL edit scenario Pub Type Weights: implement Add Pub Type Weight dialog 
* B-57781 FDA: Edit usage age weights for ACL while creating a scenario: implement validation rules for editing usage age weights 
* B-57795 FDA: Calculate ACL Scenario: implement backend logic to calculate detail shares and amounts 
* B-57782 FDA: ACL edit scenario Pub Type Weights: implement adding new rows in the Pub Type Weights dialog 

15.1.33
-
* B-74268 FDA: Tech Debt: make 'SYSTEM' user after setting NEW status by researcher
* B-74268 Tech Debt: FDA: change Scenario name to Scenario Name for all windows
* B-74268 Tech Debt: FDA: change apps schemas in mappers to schema property
* B-74268 FDA: Tech Debt: unify methods to assert AACL usages in tests
* B-57800 FDA: Scenario Tab modifications for ACL Metadata Panel: make changes based on comments in code review
* B-74268 FDA: Tech Debt: unify dates formatting
* B-57783 FDA: Create an ACL Scenario: make changes based on code review
* B-57783 FDA: Create an ACL Scenario: Adjust validation logic to usages that are eligible, granted and with usage age weight > 0 will be verified before create scenario

15.1.32
-
* FDA: ACL: Calculations tab: Scenarios subtab: There are duplicate records in df_acl_share_detail table after scenario creation

15.1.31
-
* B-74268 FDA: Tech Debt: implement integration tests for UpdateRightsholdersJob
* B-74268 FDA: Tech Debt: unify methods to assert SAL usages in tests
* B-57783 FDA: Create an ACL Scenario: implement validation for ACL usages by content unit price and publication type in Create Scenario window
* B-57783 FDA: Create an ACL Scenario: add integration test and fix queries to add scenario details and get them

15.1.30
-
* B-74268 FDA: Tech Debt: add explicit order to results of method usageArchiveRepository.findByIdAndStatus for tests only
* B-57796 FDA: View single scenario for ACL: refine logic to enable/disable View button
* B-57783 FDA: Create an ACL Scenario: fix issue with duplicate data during creation scenario

15.1.29
-
* B-57796 FDA: View single scenario for ACL: add View button in Scenarios sub-tab
* B-57783 FDA: Create an ACL Scenario: implement logic to get data from database to create scenario window
* B-57800 FDA: Scenario Tab modifications for ACL Metadata Panel: implement repository to read scenario metadata
* B-57800 FDA: Scenario Tab modifications for ACL Metadata Panel: implement service and controller to read scenario metadata
* B-57800 FDA: Scenario Tab modifications for ACL Metadata Panel: implement # of RH, # of works widgets to scenario metadata panel
* B-57800 FDA: Scenario Tab modifications for ACL Metadata Panel: implement Selection Criteria widgets to scenario metadata panel
* B-57796 FDA: View single scenario for ACL: implement window for view single scenario
* B-57783 FDA: Create an ACL Scenario: create tables for mapping weights and license classes
* B-57796 FDA: View single scenario for ACL: implement service logic to retrieve records by scenario for single scenario window
* B-57800 FDA: Scenario Tab modifications for ACL Metadata Panel: implement Description widget to scenario metadata panel
* B-57800 FDA: Scenario Tab modifications for ACL Metadata Panel: rename some classes to show that they can be used for different product families
* B-57783 FDA: Create an ACL Scenario: adjust domain ACL Scenario and implement logic to getting default values usage age weights
* B-74268 FDA: Tech Debt: move methods assertUsage and similar to ServiceTestHelper
* B-57800 FDA: Scenario Tab modifications for ACL Metadata Panel: implement View All Actions widget to scenario metadata panel
* B-57783 FDA: Create an ACL Scenario: implement queries to insert scenario data
* B-57800 FDA: Scenario Tab modifications for ACL Metadata Panel: fix the query to read total amounts
* B-57800 FDA: Scenario Tab modifications for ACL Metadata Panel: implement unit tests
* B-57796 FDA: View single scenario for ACL: implement repository logic to retrieve records by scenario for single scenario window
* B-57783 FDA: Create an ACL Scenario: implement logic to populate scenario tables in database
* B-74268 FDA: Tech Debt: rename detailId to valueId as sort property
* B-57783 FDA: Create an ACL Scenario: implement service logic to insert scenario and scenario data

15.1.28
-
* B-72775 Tech Debt: FDA: update integration tests for WorksMatchingJob, GetRightsJob
* B-72775 Tech Debt: FDA: improve UI unit tests
* B-72776 FDA: Tech Debt: add database index by Period for AACL usage table 
* B-72776 Tech Debt: FDA: reuse verify text field method from UI test class in UI unit tests 
* B-72776 Tech Debt: FDA: update integration tests for GetRightsSentForRaJob 
* B-72776 Tech Debt: FDA: check additional filters windows and remove empty Event Listeners 
* B-72776 Tech Debt: FDA: reuse verify label method from UI test class in UI unit tests 
* B-72776 Tech Debt: FDA: reuse verify load click listener, verify window, verify menu bar methods from UI test class in UI unit tests 
* B-72776 Tech Debt: FDA: fix issue with not refreshable combo boxes during create AACL scenario 
* B-72776 Tech Debt: FDA: update integration tests for SendToCrmJob 

15.1.27
-
* CDP-1080 FDA: AACL: Add to scenario: Licensee Class Mapping window: Dropdown element in AGG LC ID column disappears in case selecting some value from it, but default
* B-72776 Tech Debt: FDA: add database index by Period for AACL usage table
* B-72776 Tech Debt: FDA: reuse verify text field method from UI test class in UI unit tests
* B-72776 Tech Debt: FDA: reuse verify label method from UI test class in UI unit tests
* B-72776 Tech Debt: FDA: reuse verify load click listener, verify window, verify menu bar methods from UI test class in UI unit tests
* B-72776 Tech Debt: FDA: check additional filters windows and remove empty Event Listeners
* B-72776 Tech Debt: FDA: update integration tests for GetRightsSentForRaJob
* B-72776 Tech Debt: FDA: update integration tests for SendToCrmJob
* B-72776 Tech Debt: FDA: update integration tests for WorksMatchingJob, GetRightsJob

15.1.26
-
* B-72775 Tech Debt: FDA: add sort of detail licensee classes in UDM Usage Applied panel
* B-72775 Tech Debt: FDA: add sort for multi-select filters in UDM Baseline Applied panels
* B-72775 Tech Debt: FDA: add sort for multi-select filters in UDM Value Applied panel
* B-72775 Tech Debt: FDA: add sort for multi-select filters in Applied panels for Calculations tab
* B-72775 Tech Debt: FDA: implement integration tests for WorksMatchingJob
* B-72775 Tech Debt: FDA: implement integration tests for GetRightsJob
* B-72775 Tech Debt: FDA: implement integration tests for RhTaxJob
* B-72775 Tech Debt: FDA: improve UI unit tests
* B-72775 Tech Debt: FDA: implement integration tests for StmRhJob
* B-72775 Tech Debt: FDA: implement integration tests for RhEligibilityJob

15.1.25
-
* B-57776 FDA: View/Delete ACL Fund Pools: make changes based om comments in codereview
* B-72775 Tech Debt: FDA: apply new 6_8 rup dependencies version
* B-72775 Tech Debt: FDA: apply view only permissions to View menu item in Fund Pool and Grant Detail widgets
* B-72775 Tech Debt: FDA: refactor unit tests of button click handlers
* B-72775 Tech Debt: FDA: refine order of selected items in Period Filter widget
* B-72775 Tech Debt: FDA: refine order of UDM batch names on Applied filter panel_
* B-72775 Tech Debt: FDA: refine order of string values on UDM Usage Applied filter panel
* B-72775 Tech Debt: FDA: apply correct width for columns in Calculations Tab
* B-72775 Tech Debt: FDA: make ‘Save’ button disabled when all changes discarded
* B-72775 Tech Debt: FDA: revert sort of items in Period Filter widget and add sort for periods in UDM Usage Applied panel
* B-72775 Tech Debt: FDA: add threshold for select all check box on ACL usages grid

15.1.24
-
* B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle: add negative amount validation, and apply validation for details on UI
* B-57776 FDA: View/Delete ACL Fund Pools: implement View/Delete window
* B-57776 FDA: View/Delete ACL Fund Pools: Implement logic to remove  fund pool from database base
* B-57776 FDA: View/Delete ACL Fund Pools: Implement backend logic to remove fund pool
* B-69895 FDA: Usage batch edits: fix validation for Annualized Copies field
* B-69895 FDA: Usage batch edits: refine range for Annualized Copies field
* B-69208 FDA: UAT Feedback Adjust Usage Batch Creation: update backend logic to include all baseline usages
* B-69208 FDA: UAT Feedback Adjust Usage Batch Creation: update logic to retrieve usages to display on UI
* B-72775 Tech Debt: FDA: refine grid style name for ACL fund pool filter
* B-72775 Tech Debt: FDA: refine logic of Amount validators to prohibit spaces string

15.1.23
-
* B-69895 FDA: Usage batch edits: implement sql query to update ACL usages
* B-72983 FDA: Fund Pool filters: apply ordering for amount fields
* B-72775 Tech Debt: FDA: refine grid style name for ACL fund pool

15.1.22
-
* B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle: implement JSON validator
* B-72983 FDA: Fund Pool filters: implement backend logic
* B-69895 FDA: Usage batch edits: implement service and controller logic to update ACL usages
* B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle: implement validator to find duplicate Detail Licensee Class Id, Type of Use pairs
* B-72784 FDA: View Fund Pool details & Export: implement export functionality

15.1.21
-
* B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle: implement Liquibase script
* B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle: update domain objects
* B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle: implement messages deserializer and unmarshaller
* B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle: implement messages consumer
* B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle: implement Camel configuration
* B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle: implement repository to create LDMT fund pools
* B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle: implement Camel configuration
* B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle: implement service to create LDMT fund pools
* B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle: update widget to create LDMT fund pools
* B-72784 FDA: View Fund Pool details & Export: implement fund pool details UI grid
* B-72784 FDA: View Fund Pool details & Export: implement AclFundPoolFilter domain object
* B-72784 FDA: View Fund Pool details & Export: implement backend logic to retrieve data based on applied filters
* B-72784 FDA: View Fund Pool details & Export: add service logic to apply filters to get data
* B-69895 FDA: Usage batch edits: implement edit usage window
* B-69895 FDA: Usage batch edits: add editable field to ACL usage dto object
* B-69895 FDA: Usage batch edits: add Edit button and multi selection mode on Usages tab
* B-72983 FDA: Fund Pool filters: implement UI Part of filters
* B-72983 FDA: Fund Pool filters: implement applied filter widget and rename filters

15.1.20
-
* B-72774 FDA: Tech Debt: update amount validator to allow up to 10 digits after the decimal point
* B-72774 Tech Debt: FDA: replace sorting by Det LC ID in Verified Details by Source report
* B-72774 FDA: Tech Debt: introduce common method for verify Grid editable field error message
* B-72774 Tech Debt: FDA: Move common logic to common class in unit test
* B-64276 FDA: Fund Pool creation: fix issue with wrong detail licensee class id validation

15.1.19
-
* B-64276 FDA: Fund Pool creation: apply permissions for fund pool tab

15.1.18
-
* B-64276 FDA: Fund Pool creation: implement logic to insert fund pool
* B-72774 FDA: Tech Debt: change implementation methods of tests for validation fields and verification error messages
* B-64276 FDA: Fund Pool creation: implement business validators

15.1.17
-
* B-72774 FDA: Tech Debt: delete PUBLISHED value status 
* B-69894 FDA: Usage batch filters: implement numeric widgets to additional filter window
* B-64276 FDA: Fund Pool creation: create DB tables to preserve ACL fund pool and ACL fund pool details 
* B-69894 FDA: Usage batch filters: implement text widgets to additional filter window
* B-64276 FDA: Fund Pool creation: implement logic to insert fund pool
* B-64276 FDA: Fund Pool creation: implement logic to process upload file

15.1.16
-
* B-69894 FDA: Usage batch filters: implement domain object
* B-69894 FDA: Usage batch filters: implement MyBatis mapping
* B-69894 FDA: Usage batch filters: implement empty additional filter window
* B-69894 FDA: Usage batch filters: implement Usage Batch Name as a required filter
* B-69894 FDA: Usage batch filters: implement repository
* B-69894 FDA: Usage batch filters: add not null constrains to columns system_title, content_unit_price, annualized_copies in table df_acl_usage
* B-69894 FDA: Usage batch filters: implement applied filters widget
* B-69894 FDA: Usage batch filters: use applied filters widget
* B-69894 FDA: Usage batch filters: implement multiselect widgets to additional filter window
* B-69894 FDA: Usage batch filters: implement dropdown widgets to additional filter window
* B-72823 FDA: Values by Status report: implement filter window for report generation
* B-72823 FDA: Values by Status report: implement backend logic to generate report
* B-71997 FDA: Usage Details by Status report: implement backend logic to generate report
* B-71997 FDA: Usage Details by Status report: implement window to generate report
* B-64276 FDA: Fund Pool creation: implement fund pool tab
* B-64276 FDA: Fund Pool creation: Implement create  fund pool window with fields validation
* B-71754 FDA: Uploading works to a Grant set: add manual upload flag to exported file
* B-72773 Tech Debt: FDA: apply new 6_7 rup dependencies version

15.1.15
-
* CDP-1047 FDA: Upload works to a Grant Set: Exception occurs after uploading a grant record with TOU that is specified not in upper case

15.1.14
-
* B-69901 FDA: Export usage batch: implement usage export functionality on ACL Usages tab

15.1.13
-
* B-72773: Tech Debt: FDA: rename captions for Rightsholder and Status multiple filters
* B-71795 FDA: Create ACL usage batch: clean filter after usage batch creation
* CDP-1047 FDA: Upload works to a Grant Set: Exception occurs after uploading a grant record with TOU that is specified not in upper case
* B-68113 FDA&ACL: Get ineligible RH's from PRM and update the Grant Se: update users name in tests data
* B-72773 Tech Debt: FDA: fix issue related to validation message on usage filter window
* B-71754 FDA: Uploading works to a Grant set: display only editable Grant Sets to select on upload window
* B-72773 FDA: Tech Debt: clear filter after creating ACL grant set and uploading ACL grant details

15.1.12
-
* CDP-1045 FDA: ACL Grant Set: 'Updated Date' column doesn't change it's value after editing grant record
* CDP-1046 FDA: Create scenario for any product family: User is not redirected to scenarios tab after confirming scenario creation
* B-68113 FDA: Get ineligible RH's from PRM and update the Grant Set: implement integration tests for create grant details
* B-71754 FDA: Uploading works to a Grant set: implement duplicate in file validation
* B-71754 FDA: Uploading works to a Grant set: apply ineligible service for upload grant details functionality
* B-71795 FDA: Create ACL usage batch: fix window appearance at default zoom level in a browser
* B-71795 FDA: Create ACL usage batch: use the correct table df_udm_value_baseline
* B-71795 FDA: Create ACL usage batch: implement filter update after usage batch creation
* B-72772 FDA: Tech Debt: remove quotes around period field in Liquibase scripts

15.1.11
-
* B-72772 FDA: Tech Debt: remove quotes around period field in Liquibase scripts  
* B-72772 Tech Debt: introduce common report repository with common logic for report generation 
* B-72772 Tech Debt: remove unused constants 
* B-72772 FDA: Tech Debt: apply default order by wr_wrk_inst for ACL grant details 
* B-71755 FDA: View/Delete Grant Set: update window caption 
* B-72772 FDA: Tech Debt: split report repository by product families 
* B-72772 FDA: Tech Debt: Introduce common date filter validator for report widgets 
* B-72772 Tech Debt: adjust UI unit tests 
* B-72772 FDA: Tech Debt: fix multiselect widgets in additional filters after clicking button Clear 
* B-72772 FDA: Tech Debt: split report mapper by product families 
* B-71795 FDA: Create ACL usage batch: implement domain objects 
* B-71795 FDA: Create ACL usage batch: implement Liquibase script 
* B-71795 FDA: Create ACL usage batch: rename common classes that handle set of periods across different repositories 
* B-70731 FDA: Researcher Role enhancement for values: change value status visibility depending on role 
* B-71754 FDA: Uploading works to a Grant set: implement UI components to upload Grant Details 
* B-55079 FDA: UI usage batch view: implement ACL usages widget and add Usages sub-tab on Calculations tab 
* B-68113 FDA: Get ineligible RH's from PRM and update the Grant Set: implement domain model for backend logic 
* B-71795 FDA: Create ACL usage batch: implement MyBatis mappings 
* B-55079 FDA: UI usage batch view: implement filter widget for ACL usages 
* B-71795 FDA: Create ACL usage batch: implement usage batch and usage repositories 
* B-71754 FDA & ACL: Uploading works to a Grant set: implement CSV processor to get grants from file 
* B-71795 FDA: Create ACL usage batch: implement usage batch and usage services 
* B-55079 FDA: UI usage batch view: add Usage Batch Name filter for ACL usages 
* B-71754 FDA & ACL: Uploading works to a Grant set: implement grant detail duplicate validation 
* B-55079 FDA: UI usage batch view: implement backend logic to get all ACL usage batches 
* B-71795 FDA: Create ACL usage batch: implement usage controller 
* B-71754 FDA: Uploading works to a Grant set: apply CSV processor on upload window 
* B-71795 FDA: Create ACL usage batch: remove redundant imports 

15.1.10
-
* B-71755 FDA: View/Delete Grant Set: update window caption
* B-68113 FDA: Get ineligible RH's from PRM and update the Grant Set: implement domain model for backend logic
* B-70731 FDA: Researcher Role enhancement for values: change value status visibility depending on role
* B-71795 FDA: Create ACL usage batch: implement domain objects
* B-71795 FDA: Create ACL usage batch: implement Liquibase script
* B-71795 FDA: Create ACL usage batch: rename common classes that handle set of periods across different repositories
* B-71795 FDA: Create ACL usage batch: implement MyBatis mappings
* B-71795 FDA: Create ACL usage batch: implement usage batch and usage repositories
* B-71795 FDA: Create ACL usage batch: implement usage batch and usage services
* B-71795 FDA: Create ACL usage batch: implement usage controller
* B-71795 FDA: Create ACL usage batch: remove redundant imports
* B-71754 FDA: Uploading works to a Grant set: apply CSV processor on upload window
* B-71754 FDA: Uploading works to a Grant set: implement UI components to upload Grant Details
* B-71754 FDA: Uploading works to a Grant set: implement grant detail duplicate validation
* B-71754 FDA: Uploading works to a Grant set: implement CSV processor to get grants from file
* B-55079 FDA: UI usage batch view: implement ACL usages widget and add Usages sub-tab on Calculations tab
* B-55079 FDA: UI usage batch view: implement filter widget for ACL usages
* B-55079 FDA: UI usage batch view: add Usage Batch Name filter for ACL usages
* B-55079 FDA: UI usage batch view: implement backend logic to get all ACL usage batches
* B-72772 FDA: Tech Debt: introduce common report repository with common logic for report generation
* B-72772 FDA: Tech Debt: apply default order by wr_wrk_inst for ACL grant details
* B-72772 FDA: Tech Debt: split report repository by product families
* B-72772 FDA: Tech Debt: Introduce common date filter validator for report widgets
* B-72772 FDA: Tech Debt: fix multiselect widgets in additional filters after clicking button Clear
* B-72772 FDA: Tech Debt: split report mapper by product families

15.1.9
-
* B-71755 FDA: View/Delete Grant Set: implement repositories to remove grant set and grant details
* B-67667 FDA: Completed Assignments by Employee Report: rename report prefix
* B-71755 FDA: View/Delete Grant Set: implement services to remove grant set and grant details
* B-72772 FDA: Tech Debt: reuse common logic in ACL grant detail additional filters
* B-71755 FDA: View/Delete Grant Set: implement grant set controller
* B-71755 FDA: View/Delete Grant Set: implement grant set widget
* B-71755 FDA: View/Delete Grant Set: implement View menu item
* CDP-1044 FDA: Edit Grants Window: Exception occurs after clicking 'Verify' button, if 'RH Account #' field is empty
* B-71755 FDA: View/Delete Grant Set: make button Delete available for Specialist only

15.1.8
-
* B-71753 FDA: Export ACL Grant Set: implement integration test to cover report
* B-70851 FDA: Report for usage edits in baseline: make changes based on comments in code review
* B-70851 FDA: Report for usage edits in baseline: rename report prefix
* CDP-1043: FDA: Edit ACL Grant Set: Exception occurs after setting grant status to 'Deny' for Print Only or Digital Only grants

15.1.7
-
* B-71753 FDA: Export ACL Grant Set: implement export button for Grant Set tab
* B-71752 FDA: Edit ACL Grant Set: implement logic to update TOU status
* B-71021 FDA: Usable Details by Country Report: change sort order
* B-71021 FDA: Usable Details by Country Report: make changes based on comments in code review
* B-71753 FDA: Export ACL Grant Set: implement backend logic to generate report
* B-70851 FDA: Report for usage edits in baseline: implement query to generate the report
* B-71752 FDA: Edit ACL Grant Set: implement query to update ACL grant
* B-72772 Tech Debt: FDA: update rightsholders job to include UDM tables

15.1.6
-
* B-71752 FDA: Edit ACL Grant Set: implement edit window
* B-71752 FDA: Edit ACL Grant Set: adjust logic to get grants details
* B-71752 FDA: Edit ACL Grant Set: add Edit button to Grant Set tab
* B-71752 FDA: Edit ACL Grant Set: adjust Edit Grants Window and implement validations for fields
* B-71752 FDA: Edit ACL Grant Set: implement logic to verify RH in PRM
* B-71790 FDA: Grant Set Filters: add fields for AclGrantDetailFilter
* B-71790 FDA: Grant Set Filters: implement applied filter panel for ACL grant details
* B-71790 FDA: Grant Set Filters: update SQL query to retrive grant details by applied filter
* B-71790 FDA: Grant Set Filters: refine AclGrantDetailFilterWidget and implement AclGrantDetailFiltersWindow
* B-70851 FDA: Report for usage edits in baseline: implement domain object
* B-70851 FDA: Report for usage edits in baseline: implement repository
* B-70851 FDA: Report for usage edits in baseline: implement service
* B-70851 FDA: Report for usage edits in baseline: implement widget and controller
* B-70851 FDA: Report for usage edits in baseline: implement menu item
* B-71021 FDA: Usable Details by Country Report: Implement filter window for report generation
* B-71021 FDA: Usable Details by Country Report: Refactor a condition for availability export button
* B-71021 FDA: Usable Details by Country Report: Implement report handler and SQL select
* B-71021 FDA: Usable Details by Country Report: Implement service and export csv report functionality

15.1.5
-
* B-72269 FDA: Tech Debt: move Liquibase scripts for UdmUsageAuditRepositoryIntegrationTest into a separate folder
* B-72269 FDA: Tech Debt: move Liquibase scripts for ResearchedUsagesCsvProcessorIntegrationTest, SalUsageDataCsvProcessorIntegrationTest, UdmValueAuditRepositoryIntegrationTest into a separate folder 
* B-72269 FDA: Tech Debt: move Liquibase scripts for ClassifiedUsageCsvProcessorIntegrationTest, SalItemBankCsvProcessorIntegrationTest, UsageCsvProcessorIntegrationTest into separate folders 
* B-72269 Tech Debt: fix edit comment field issue 

15.1.4
-
* B-70867 FDA: Wr Wrk Inst change set status New: refactor error message
* B-72269 FDA: Tech Debt: move common logic to common class to avoid duplicate logic
* B-72269 FDA: Tech Debt: move Liquibase scripts for SalCsvReportsIntegrationTest into a separate folder
* B-72269 FDA: Tech Debt: move Liquibase scripts for UsageAuditRepositoryIntegrationTest into separate folder
* B-72269 FDA: Tech Debt: move Liquibase scripts for UdmCsvReportsIntegrationTest into a separate folder
* B-72269 FDA: Tech Debt: resolve issue with lost comment field  during multiple edit of usage for researcher role
* B-72269 FDA: Tech Debt: move Liquibase scripts for UdmBatchRepositoryIntegrationTest into separate folder
 
15.1.3
-
* B-70887 FDA: Edit functionality modification on multi edit: Refactor multi edit of not assigned usages to specialist or manager
* B-70867 FDA: WrWrkInst change status to new: fix issue with null values
* B-70867 FDA: Wr Wrk Inst change set status New: Add validation for Detail Status after changing WR Wrk Inst for researchers multi edit
* B-70867 FDA: Wr Wrk Inst change set status New: fix validation for Detail Status after changing WR Wrk Inst for researchers single edit
* B-70816 FDA: Investigate(fix) issues with FDA RMS integration: reimplement RmsRightCacheService

15.1.2
-
* B-70074 FDA: Tech Debt: move UDM value filter window to binder approach
* B-70074 FDA: Tech Debt: move Liquibase scripts for AaclCsvReportsIntegrationTest into a separate folder
* B-70074 FDA: Tech Debt: move UDM usage filter window to binder approach
* B-70074 FDA: Tech Debt: move common logic to common class
* B-70074 FDA: Tech Debt: fix unit tests for additional filters windows and refactor UdmValueFilter
* B-70074 FDA: Tech Debt: move Liquibase scripts for FasCsvReportsIntegrationTest into a separate folder
* B-70074 Tech Debt: FDA: adjust unit tests in UI module
* B-70074 FDA: Tech Debt: move Liquibase scripts for UsageBatchRepositoryIntegrationTest into separate folder
* B-70074 FDA: Tech Debt: move Liquibase scripts for NtsCsvReportsIntegrationTest into a separate folder
* B-70867 FDA: WrWrkInst change set status New: Add validation for Detail Status after changing WR Wrk Inst or Reported Title or Reported Standard Number single edit
* B-70867 FDA: WrWrkInst change set status New: Add validation for Detail Status after changing WR Wrk Inst or Reported Title or Reported Standard Number multi edit

15.1.1
-
* B-69659 FDA: Previous Period Research Status for Work Values: update backend logic to update only corresponding to period works
* B-57767 FDA: Get Rights Information from RMS for ACL usages: adjust logic to get grants from RMS
* B-71751 FDA: Create and View ACL Grant Set: add mediator for AclGrantDetailWidget
* B-70074 Tech Debt: FDA: fix security vulnerability CVE-2022-23181
* B-70074 Tech Debt: FDA: adjust filter validation on UDM usages tab
* B-70074 Tech Debt: FDA: fixing date range validation in Weekly Survey and Survey Licensee reports
* B-70074 Tech Debt: FDA: adjust filter validation on UDM tabs
* B-70074 Tech Debt: FDA: move Liquibase scripts for UsageBatchStatusRepositoryIntegrationTest into separate folders
* B-70074 Tech Debt: FDA: move Liquibase scripts for UsageArchiveRepositoryIntegrationTest into a separate folder

15.1.0
-
* B-71751 FDA: Create and View ACL Grant Set: implement domain objects
* B-71751 FDA: Create and View ACL Grant Set: implement Liquibase script
* B-71751 FDA: Create and View ACL Grant Set: add Calculations tab and implement AclCalculationWidget
* B-71751 FDA: Create and View ACL Grant Set: implement backend to save grant set
* B-71751 FDA: Create and View ACL Grant Set: implement AclGrantDetailDto object
* B-71751 FDA: Create and View ACL Grant Set: implement backend to save grant details
* B-71751 FDA: Create and View ACL Grant Set: migrate grant set  periods from List to Set
* B-71751 FDA: Create and View ACL Grant Set: add grant_status column and delete comment column
* B-71751 FDA: Create and View ACL Grant Set: implement backend to create grant set with grant details
* B-71751 FDA: Create and View ACL Grant Set: add Grant Set sub tab and implement AclGrantDetailWidget
* B-71751 FDA: Create and View ACL Grant Set: implement backend to check for the existence of a grant set with that name
* B-71751 FDA: Create and View ACL Grant Set: implement returning the number of inserted grant details
* B-71751 FDA: Create and View ACL Grant Set: implement CreateAclGrantSetWindow
* B-71751 FDA: Create and View ACL Grant Set: disable Reports sub tab on Calcultations tab
* B-71771 FDA: Grant Set Filter: implement repository
* B-71771 FDA: Grant Set Filter: implement service
* B-71771 FDA: Grant Set Filter: implement service and repository to read all grant sets
* B-71771 FDA: Grant Set Filter: implement widget to filter grant sets
* B-71771 FDA: Grant Set Filter: apply widget to filter grant sets
* B-57767 FDA: Get Rights Information from RMS for ACL usages (get grants): make changes based on code review
* B-57767 FDA: Get Rights Information from RMS for ACL usages (get grants): implement integration test for service to get grants from RMS
* B-57767 FDA: Get Rights Information from RMS for ACL usages (get grants): refactor logic to populate grants
* B-57767 FDA: Get Rights Information from RMS for ACL usages (get grants): implement service to get and populate grants
* B-69659 FDA: Previous Period Research Status for Work Values: implement backend logic to set Researched in Prev Period status
* B-70074 FDA: Tech Debt: move UDM baseline usage filter window to binder approach
* B-70074 FDA: Tech Debt: add database indexes by Wr Wrk Inst and Period for UDM baseline value table

14.1.51
-
* CDP-1041 FDA: AACL: Baseline Usages report: Exported report contains baseline usages with unexpected periods

14.1.50
-
* B-69423 FDA: Filter functionality modification: make changes based on comments in code review
* B-70073 FDA: Tech Debt: rename pub types filter widget caption
* B-70073 Tech Debt: FDA: log unassign audit record for automatic research unassign during edit

14.1.49
-
* B-69423 FDA: Filter functionality modification: apply common fields formatting on UMD usage filters window
* B-70073 FDA: Tech Debt: add sorting for writeAaclBaselineUsagesCsvReport
* B-69423 FDA: Filter functionality modification: update Applied Filter widget for numeric fields on Values tab
* B-69423 FDA: Filter functionality modification: apply common logic in UdmBaselineFiltersWindow

14.1.48
-
* B-67667 FDA: Completed Assignments by Employee Report: update report menu id
* B-69423 FDA: Filter functionality modification: implement operations for RH Account #, RH Name in Values tab
* B-69423 FDA: Filter functionality modification: update UdmBaselineValueFiltersWindow to follow common pattern
* B-69423 FDA: Filter functionality modification: apply common formatting for Rh acccount number layout on Values filter window
* B-69423 FDA: Filter functionality modification: implement operations for Price Comment, Last Price Comment, Content Comment, Last Content Comment in Values tab
* B-69423 FDA: Filter functionality modification: add IS_NULL operator for flag fields on Values tab
* B-69423 FDA: Filter functionality modification: replace Pub Type filter to multi-select Pub Type filter on Values tab
* B-69423 FDA: Filter functionality modification: implement operations for Price, Price in USD, Content in Values tab
* B-69423 FDA: Filter functionality modification: refactor UDM Value Filters window, remove redundant logic to disable combobox options for Last Value Period filter
* B-69423 FDA: Filter functionality modification: update backend logic for Last Value Period filter
* B-69423 FDA: Filter functionality modification: implement operations for Comment in Values tab
* B-69423 FDA: Filter functionality modification: replace Pub Type filter to multi-select Pub Type filter on Baseline Values tab
* B-69423 FDA: Filter functionality modification: implement Last Comment column in Values grid
* B-69423 FDA: Filter functionality modification: implement operations for Last Comment in Values tab
* B-69423 FDA: Filter functionality modification: remove IS_NULL value from Price Flag and Content Flag on Values and Baseline Values tabs
* B-69423 FDA: Filter functionality modification: apply common filters logic for UDM usages tab filters
* B-70073 FDA: Tech Debt: refactor UdmCsvReportsIntegrationTest for Weekly Survey Report
* B-70073 FDA: Tech Debt: add filters for testWriteUdmSurveyLicenseeCsvReport and not suitable cases, add unset Date case for testVerifiedDetailsBySourceReport

14.1.47
-
* B-69423 FDA: Filter functionality modification: implement operators for Wr Wrk Inst, Comment in Baseline Values tab
* B-69423 FDA: Filter functionality modification: implement operations for Wr Wrk Inst in Values tab
* B-69423 FDA: Filter functionality modification: implement operations for System Title, System Standard Number in Values tab
* B-69423 FDA: Filter functionality modification: implement operators for Price Flag, Content Flag in Baseline Values tab
* B-70571 FDA: Verified Details by Source Report: make changes based code review
* CDP-1039 FDA: ACL: empty usages and value fields that were edited are not treated by the system the same as ‘null’ fields while sorting
* CDP-1040 FDA: ACL: Usages sub-tab: Additional filters: 'Usage Detail ID' filter searches records by 'Detail ID' field instead of 'Usage Detail ID' field

14.1.46
-
* B-69423 FDA: Filter functionality modification: implement operations for Survey Country, Language in Usages tab
* B-69423 FDA: Filter functionality modification: implement operations for Usage Detail ID, Survey Country in Baseline tab
* B-69423 FDA: Filter functionality modification: implement operations for Usage Detail ID in Usages tab
* B-69423 FDA: Filter functionality modification: implement operations for Reported Title, System Title in Usages tab
* B-69423 FDA: Filter functionality modification: implement operations for System Title, Price, Content, Content Unit Price in Baseline Values tab
* B-69423 FDA: Filter functionality modification: implement operations for Survey Respondent in Usages tab
* B-69423 FDA: Filter functionality modification: adjust Usage filters window view
* B-69423 FDA: Filter functionality modification: adjust Baseline filters window view
* B-70571 FDA: Verified Details by Source Report: add test cases for UdmCsvReportsIntegrationTest
* B-70571 FDA: Verified Details by Source Report: make changes based manual testing
* B-70073 Tech Debt: FDA: fix validation error message on UDM usages tab for ineligible status
* CDP-1038 FDA: ACL: Usages sub-tab: Additional filters: Exception occurs when entering value more than max int32 one in ‘Company ID’ filter

14.1.45
-
* B-70571 FDA: Verified Details by Source Report: implement controller
* B-69423 FDA: Filter functionality modification: implement operations for Company ID, Company Name in Usages tab
* B-69423 FDA: Filter functionality modification: implement operations for System Title in Baseline tab
* CDP-1037 FDA: Weekly Survey Report: 'Received Date From' and 'Received Date To' filters search data by 'Survey Start Date' instead of 'Load Date'

14.1.44
-
* B-69423 FDA: Filter functionality modification: implement operations for Annualized Copies in Baseline tab
* B-69423 FDA: Filter functionality modification: implement operations for Wr Wrk Inst in Baseline tab
* B-70571 FDA: Verified Details by Source Report: implement service
* B-67667 FDA: Completed Assignments by Employee Report: implement backend logic to generate report
* CDP-1036 FDA: Weekly Survey Report: Detail is considered as usable if Reported Title = 'none' (only 'None' is considered as unusable, should be case insensitive)

14.1.43
-
* B-69423 FDA: Filter functionality modification: implement the Unassigned option for the Assignees widgets
* B-69423 FDA: Filter functionality modification: implement operations for Annual Multiplier, Annualized Copies, Statistical Multiplier, Quantity in Usages tab
* B-69423 FDA: Filter functionality modification: rename UdmFilterWidget to UdmUsageFilterWidget; fix spelling errors
* B-69423 FDA: Filter functionality modification: implement operations for Wr Wrk Inst in Usages tab
* B-67667 FDA Reports: Completed Assignments by Employee Report: implement filter window for report generation
* B-67667 FDA Reports: Completed Assignments by Employee Report: implement logic to populate usernames filter
* B-67667 FDA Reports: Completed Assignments by Employee Report: implement User Name filter widget
* B-67667 FDA Reports: Completed Assignments by Employee Report: update action reason type for UNASSIGN action for UDM usages and values
* B-70570 FDA: Survey Report Licensee: implement backend logic for survey licensee report
* B-70570 FDA: Survey Report Licensee: implement UI and service logic to generate report Survey Licensee Report
* B-70570 FDA: Survey Report Licensee: move survey start date column at the report
* B-70571 FDA: Verified Details by Source Report: implement repository
* B-70072 Tech Debt: FDA: refine logic for UDM filter windows with select all button
* B-70072 Tech Debt: FDA: implement common method to verify comboboxes
* B-70072 Tech Debt: FDA: Rename class UiCommonHelper to UiTestHelper
* B-70072 Tech Debt: FDA: Use static imports for class UiTestHelper
* B-70072 Tech Debt: FDA: Implement common method to verify multi-select widgets
* B-70072 Tech Debt: FDA: implement common method to verify labels
* B-70072 Tech Debt: FDA: Reuse common method to verify buttons panel
* B-70072 Tech Debt: FDA: display Reports tab for all roles
* B-70073 Tech Debt: FDA: apply 6_5 rup standard dependencies version set
* B-70072 Tech Debt: FDA: introduce common UDM report widget and adjust weekly survey report

14.1.42
-
* B-69779 FDA: FDA & UDM: View filters improvements: fix System Title filter on Baseline Values tab
* B-70072 Tech Debt: FDA: introduce common UDM mapper for filtering
* B-69779 FDA: FDA & UDM: View filters improvements: make changes based on code review
* B-70072: FDA: Tech Debt: Refactor ACL workflow integration test

14.1.41
-
* B-67668 FDA: 'By Date Received' Survey Report by Date Range: refactor main report window
* B-67668 FDA: "By Date Received" Survey Report by Date Range: adjust visibility of the tab and the menu item
* B-68617 FDA: Filter functionality modification - Part 2: apply case-insensitive find of records for Value filters
* CDP-1034  FDA: Weekly Survey Report: '# of usable rows reported by registered users' and '# of usable rows reported by unregistered users' columns don't comprise rows with Reported Title = NULL
* B-69779 FDA: FDA & UDM: View filters improvements: fix Pub Type filter on Baseline Values tab
* B-68617 FDA: Filter functionality modification - Part 2: apply case-insensitive find of records for Baseline filters
* B-67668 FDA: "By Date Received" Survey Report by Date Range: make changes based on comments in code review
* CDP-1035 FDA: ACL: UDM Values: exception occurs when sorting Value ID column
* B-68617 FDA: Filter functionality modification - Part 2: apply case-insensitive find of records for Baseline Values filters

14.1.40
-
* B-70072 FDA: Tech Debt: move Liquibase scripts for SalUsageRepositoryIntegrationTest into a separate folder
* B-70072 FDA: Tech Debt: move Liquibase scripts for FundPoolRepositoryIntegrationTest into a separate folder
* Revving up build version and update release notes for 14.1.39 version
* B-68617 FDA: Filter functionality modification - Part 2: replace period filter to multi-select period filter on Baseline tab
* B-70072 FDA: Tech Debt: move Liquibase scripts for ScenarioRepositoryIntegrationTest into a separate folder
* B-70072 FDA: Tech Debt: move Liquibase scripts for RightsholderRepositoryIntegrationTest, RightsholderDiscrepancyRepositoryIntegrationTest into separate folders
* B-70072 FDA: Tech Debt: move Liquibase scripts for ScenarioAuditRepositoryIntegrationTest, ScenarioUsageFilterRepositoryIntegrationTest into separate folders
* B-70072 FDA: Tech Debt: move Liquibase scripts for UdmUsageRepositoryIntegrationTest into a separate folder
* B-70072 FDA: Tech Debt: move Liquibase scripts for UdmValueRepositoryIntegrationTest into a separate folder
* CDP-1033 FDA: ACL: Baseline Values: applied ‘Pub Type’ filter is ignored when applying additional filters
* B-70072 FDA: Tech Debt: move Liquibase scripts for UsageBatchStatusRepositoryIntegrationTest, WorkClassificationRepositoryIntegrationTest into separate folders
* B-68617 FDA: Filter functionality modification - Part 2: apply case insensitive find of records for Usage filters
* B-69779 FDA: FDA & UDM: View filters improvements: fix display of filter fields with IS_NULL operator

14.1.39
-
* B-69779 FDA: FDA & UDM: View filters improvements: implement applied filter panel for UDM values tab and fix display date on usage tab
* B-67668 FDA: "By Date Received" Survey Report by Date Range: hide UDM Reports tab after switching to another product family
* B-67668 FDA: "By Date Received" Survey Report by Date Range: add period filter widget
* B-68617 FDA: Filter functionality modification - Part 2: apply SelectAll button to all multi-select filters
* B-67668 FDA: "By Date Received" Survey Report by Date Range: implement validation
* B-69878 FDA & UDM: Delete usages from Baseline: implement backend logic to delete baseline usages
* B-69779 FDA: FDA & UDM: View filters improvements: implement applied filter panel for UDM baseline tab
* B-68617 FDA: Filter functionality modification - Part 2: replace period filter to multi-select period filter on Usages tab
* B-70072 FDA: Tech Debt: move Liquibase scripts for repository integration tests into a separate folder
* B-67668 FDA: "By Date Received" Survey Report by Date Range: implement Channel, Usage Origin filters
* B-69779 FDA: FDA & UDM: View filters improvements: implement applied filter panel for UDM baseline values

14.1.38
-
* B-68257 [Value] FDA & UDM: View proxy values: make changes based on code review comments 
* B-68617 FDA: Filter functionality modification - Part 2: move currency filter to more filters, pub type filter to main filters on Values tab 
* B-70072 Tech Debt: FDA: fix security vulnerabilities CVE-2020-21913, CVE-2021-44228 
* B-68617 FDA: Filter functionality modification - Part 2: move pub type filter to main filters on Baseline Values tab 
* B-67668 FDA: "By Date Received" Survey Report by Date Range: implement domain object 
* B-67668 FDA: "By Date Received" Survey Report by Date Range: implement repository 
* B-70072 Tech Debt: FDA: refine logic to highlight invalid fields on Value edit window 
* B-67668 FDA: "By Date Received" Survey Report by Date Range: implement service 
* B-69779 FDA: View filters improvements: implement common filter panel for applied filters 
* B-67668 FDA: "By Date Received" Survey Report by Date Range: implement controller and widget, without filters Channels, Usage Origins, Periods 
* B-70072 Tech Debt: clear proxy value if publication type was removed 
* B-67668 FDA: "By Date Received" Survey Report by Date Range: implement Reports tab and the report menu item 
* B-68617 FDA: Filter functionality modification - Part 2: add Price Flag, Content Flag, Price Comment, Content Comment filters to more filters window on Values tab 
* B-67668 FDA: "By Date Received" Survey Report by Date Range: rename widget UdmValuePeriodFilterWidget  to PeriodFilterWidget 
* B-69779 FDA: View filters improvements: inplement applied filter widget for UDM usages tab 
* B-68617 FDA: Filter functionality modification - Part 2: implement common filter window for multi-select filters 
* B-69878 FDA & UDM: Delete usages from Baseline: implement UI components to delete baseline usages 
* B-70072 Tech Debt: add value audit for proxy calculation action
* B-67668 FDA: "By Date Received" Survey Report by Date Range: implement MyBatis mapping

14.1.37
-
* CDP-1032 FDA: ACL: Value Audit: Value audit contains redundant entries related to trimming zero values in decimal part after editing
* B-70805 FDA: Audit Value Changes: extract class for formatting currency fields
* B-68257 FDA: View proxy values: apply amount sorting by Content Unit Price

14.1.36
-
* B-65964 FDA: Calculate and apply proxy values: make changes based on comments in code review
* B-70805 FDA: Audit Value Changes: Implement backend to write edit audit
* B-65868 Tech Debt: FDA: adjust ACL integration tests
* B-65868 Tech Debt: FDA: add refresh step after usage and value publishing to baseline
* B-65868 Tech Debt: FDA: fix validate message on multiple edit usage window

14.1.35
-
* B-70805 FDA: Audit Value Changes: Implement backend to write edit audit
* B-70805 FDA: Audit Value Changes: add unit and integration tests
* B-70805 FDA: Audit Value Changes: introduce audit for value batch creation and publishing to baseline
* B-69863 FDA: View baseline values: fix clear periods value when applied additional filters
* B-68257 FDA: View proxy values: fix sorting by Content Unit Price on Proxy tab
* B-68257 FDA: View proxy values: implement logic to generate exports
* B-69757 FDA: Edit functionality modification (Usages and Values): apply confirmation window for UDM baseline usage remove and write a reason to audit
* B-69757 FDA: Edit functionality modification (Usages and Values): exclude remove from baseline for publish to baseline logic

14.1.34
-
* B-70805 FDA: Audit Value Changes: Implement backend to write assign/unassign audit
* B-69757 FDA: Edit functionality modification (Usages and Values): remove UDM usage from baseline after single edit
* B-69757 FDA: Edit functionality modification (Usages and Values): remove edit button from UDM Value tab
* B-69863 FDA: View baseline values: implement query to filter UDM baseline values and implement service and controller logic
* B-68257 [Value] FDA & UDM: View proxy values: implement logic to retrieve proxy values
* B-65868 Tech Debt: FDA: refine assign logic in UdmUsageWidget

14.1.33
-
* B-69757 FDA: Edit functionality modification (Usages and Values): remove edit button from UDM Usage tab
* B-69757 FDA: Edit functionality modification (Usages and Values): implement view UDM value window
* B-69757 FDA: Edit functionality modification (Usages and Values): remove notification windows for Researcher during usage single edit
* B-69863 FDA: View baseline values: implement validators on baseline values filters window
* B-70805 FDA: Audit Value Changes: implement frontend to show audit window
* B-70805 FDA: Audit Value Changes: Implement audit window
* B-70805 FDA: Audit Value Changes: Implement Liquibase script to create table df_udm_value_audit
* B-68257 [Value] FDA & UDM: View proxy values: implement logic to get proxy values periods
* B-68257 [Value] FDA & UDM: View proxy values: Implement UI filters
* CDP-1030 FDA: Publish values to baseline: 'Price' instead of 'Price in USD' gets published as the 'Price' parameter to the baseline
* CDP-1031 FDA: Publish values to baseline: Exception occurs after trying to publish values, for which proxies were applied

14.1.32
-
* B-69863 FDA: View baseline values: implement logic to get baseline value periods
* B-69863 FDA: View baseline values: implement UDM baseline values filters window
* B-69863 FDA: View baseline values: implement periods filter modal window
* B-69863 FDA: View baseline values: implement UDM Baseline Value Filter
* B-65964 FDA: Calculate and apply proxy values: implement frontend to apply proxy values
* B-65964 FDA: Calculate and apply proxy values: implement Liquibase script to create table df_udm_proxy_values
* B-65964 FDA: Calculate and apply proxy values: implement single period selection dialog
* B-65964 FDA: Calculate and apply proxy values: implement button Calculate Proxies
* B-65964 FDA: Calculate and apply proxy values: implement backend to apply proxy values
* B-65964 FDA: Calculate and apply proxy values: implement backend to calculate proxy values
* B-68257 FDA & UDM: View proxy values: Implement UI tab for proxy values
* B-69757 FDA: Edit functionality modification (Usages and Values): modify single edit functionality for UDM Usage
* B-69757 FDA: Edit functionality modification (Usages and Values): implement view UDM usage window
* B-65868 Tech Debt: FDA: apply lazy filter widget for Rightsholders filters

14.1.31
-
* B-70501 Tech Debt: FDA: fix security vulnerability CVE-2021-22119
* B-70501 Tech Debt: FDA: fix security vulnerabilities CVE-2021-33609, CVE-2021-22119
* B-69880 FDA: Swagger 3 for FDA & LM: migrate to Swagger 3
* B-70307 FDA: Test Containers for PostGres: move service integration tests to test containers
* B-70307 FDA: Test Containers for PostGres: move repository integration tests to test containers
* B-68823 Tech Debt: FDA: apply amount validators to UDM product family
* B-68823 Tech Debt: FDA: apply amount validators to SAL product family
* B-68823 Tech Debt: FDA: apply amount validators to NTS product family
* B-68823 Tech Debt: FDA: apply amount validators to FAS product family
* B-68823 Tech Debt: FDA: implement amount validators
* B-65962 [Value] FDA&UDM: Create and Populate Value batch: adjust populate value batch logic to populate value ids by parts

14.1.30
-
* B-67673 FDA: Value Edits: limit number of digits before decimal point in Price, Content fields
* B-68823 Tech Debt: FDA: allow zero filter for Price and Price in USD filters on Values tab
* B-68823 Tech Debt: FDA: apply required validator for all product families

14.1.29
-
* CDP-1021 FDA: ACL: Edit Value: Exception occurs when user edits newly populated value and then selects it
* B-68260 [Value] FDA & UDM: Publish values to baseline: implement workflow test for ACL product family

14.1.28
-
* CDP-1020 FDA: ACL: UDM Values Edit: Price in USD is calculated on the basis of ‘exchangeRateValue’ instead of ‘inverseExchangeRateValue'
* CDP-1019 FDA: ACL: Edit Value: Exception occurs when Price in USD contains comma in the amount’ of Currency Exchange Rate REST-call
* CDP-1018 FDA: Populate Value Batch: Exception occures after trying to repopulate values, if there are no any new records available
* B-69421: FDA & UDM: Select all (Usages, Values): make changes based on comments in CR-DIST-FOREIGN-268

14.1.27
-
* B-67673 FDA: Value Edits: implement behavior of the Content Unit Price field
* B-67673 FDA: Value Edits: implement backend to save values
* B-67673 FDA: Value Edits: implement saving calculated fields
* B-67673 FDA: Value Edits: update font size for panel caption
* CDP-1017 FDA: ACL: Values tab: Pub Type is displayed as result of PublicationType.toString method

14.1.26
-
* B-68260 FDA & UDM: Publish values to baseline: adjust baseline usages by adding reference to corresponding value id
* B-69421 FDA & UDM: Select all (Usages, Values): fix wrong hiding SelectAllCheckBox on UDM Values tab after closing View window
* B-67673 FDA: Value Edits: implement behavior of the Price Flag, Content Flag fields
* B-67673 FDA: Value Edits: implement behavior of the Price in USD field
* B-67673 FDA: Value Edits: apply css styles for panel captions on Edit Value window
* B-69729 FDA & UDM: View single record: fix issue related to deselect record after close view window for view only role
* B-69729 FDA & UDM: View single record: make changes based on comments in CR-DIST-FOREIGN-267
* B-68823 FDA: Tech Debt: remove standard number type from UDM values
* B-68823 FDA: Tech Debt: fix issue related to filter values by system title, stundard number, rh name with underscore

14.1.25
-
* B-68260 [Value] FDA & UDM: Publish values to baseline: Implement logic to publish values to baseline
* B-69729: FDA & UDM: View single record: implement view baseline window and add logic to open view value window
* B-69421: FDA & UDM: Select all (Usages, Values): fix wrong hiding SelectAllCheckBox on UDM Usages tab after closing View and Audit windows
* B-69421: FDA & UDM: Select all (Usages, Values): refine service and controller logic for UDM Value
  B-67673 FDA: Value Edits: implement fields in Price section
* B-67673 FDA: Value Edits: implement fields in Work Information, General, Publication Type sections

14.1.24
-
* B-67673 FDA: Value Edits: implement the Edit Value button
* B-67673 FDA: Value Edits: implement fields layout
* B-67673 FDA: Value Edits: implement Edit UDM Value window
* B-69421 FDA & UDM: Select all (Usages, Values): refine service and controller logic for UDM Usage
* B-69421 FDA & UDM: Select all (Usages, Values): add select all checkbox on UDM Usages tab
* B-69729 FDA & UDM: View single record: implement logic related to usage view  modal window open  by double mouse click
* B-69220 [Value] FDA & UDM: Populate Price Type and Price Access Type: create and populate price type tables
* B-69739 [Value] FDA & UDM: Add Price Source & Content Source to Values UI: adjust UdmValueDto and repository logic to get fields from DB
* B-69739 [Value] FDA & UDM: Add Price Source & Content Source to Values UI: adjust UI to display new Columns
* B-69739 [Value] FDA & UDM: Add Price Source & Content Source to Values UI: adjust UI to display new columns
* B-68260 [Value] FDA & UDM: Publish values to baseline: Implement db table for baseline values and add df_udm_value_uid column to df_udm_usage table
* B-68260 [Value] FDA & UDM: Publish values to baseline: Implement window for publishing values to baseline
* B-68260 [Value] FDA & UDM: Publish values to baseline: Implement verification logic for publishing to baseline

14.1.23
-
* B-65962 [Value] FDA&UDM: Create and Populate Value batch: make changes based on comments in CR-DIST-FOREIGN-264
* B-65962 [Value] FDA&UDM: Create and Populate Value batch: add indexes by wr_wrk_inst and period for UDM tables
* B-69502 FDA: Value filters part 2 (additional filters): make changes based on comments in code review
* B-68822 Tech Debt: FDA: apply convertDateToString method from BaseCsvReportWrite to handlers

14.1.22
-
* B-68822 FDA: Tech Debt: change formatting of Content, Last Content columns, fix sorting of boolean columns  in UDM Values grid
* B-68822 FDA: Tech Debt: fix failed test after refactoring boolean columns in UDM Values grid from primitives to objects
* CDP-1014: FDA: UDM Values filters: Values filtered by partial system title value and operator ‘EQUALS’ are displayed on UI

14.1.21
-
* B-68616 FDA & UDM: export baseline data: make changes based on comments in CR-DIST-FOREIGN-261
* B-65962 [Value] FDA&UDM: Create and Populate Value batch: switch off cache for getting grants for values
* B-68259 [Value] FDA & UDM: Assign/Unassign work values for research: make changes based on comments in CR-DIST-FOREIGN-262
* B-67687 [Value] FDA&UDM: Value filters (basic filters): fix wrong test data, fix UDM value filter window, fix escapeSqlLikePattern for filter expression and fix UDM value table change true/false on Y/N
* B-69502 FDA: Value filters part 2 (additional filters): implement backend to filter Last Price Flag, Last Content Flag
* B-69502 FDA: Value filters part 2 (additional filters): implement backend to filter Comment, Last Price Comment, Last Content Comment
* B-69502 FDA: Value filters part 2 (additional filters): fix UI to filter Price, Price in USD

14.1.20
-
* B-65962 [Value] FDA&UDM: Create and Populate Value batch: implement query to find unpublished values from usages baseline
* B-65962 [Value] FDA&UDM: Create and Populate Value batch: implement logic to insert values into db
* B-65962 [Value] FDA&UDM: Create and Populate Value batch: adjust query that finds not populated values
* B-67687: [Value] FDA&UDM: Value filters (basic filters): adjust UI fields on UDM filter values and implement filter validator
* CDP-1013: FDA: Edit UDM usages Researcher role: Wr Wrk Inst field becomes mandatory while editing WORK_NOT_FOUND usages
* B-69502 FDA: Value filters part 2 (additional filters): implement backend to filter Last Value Periods by dates

14.1.19
-
* B-68259: [Value] FDA & UDM: Assign/Unassign work values for research: add Assignment Menu Bar on values tab
* B-69502 FDA: Value filters part 2 (additional filters): implement UI to filter Price, Price in USD
* B-69502 FDA: Value filters part 2 (additional filters): implement UI to filter Last Price Flag, Last Content Flag
* B-69502 FDA: Value filters part 2 (additional filters): implement UI to filter Comment, Last Price Comment, Last Content Comment
* B-67687: [Value] FDA&UDM: Value filters (basic filters): implement query to filter values
* B-69502 FDA: Value filters part 2 (additional filters): implement UI to filter Content
* B-65962 [Value] FDA&UDM: Create and Populate Value batch: implement query to find unpublished values from usages baseline
* B-67687: [Value] FDA&UDM: Value filters (basic filters): implement service logic to get UDM values by filter
* B-68259: [Value] FDA & UDM: Assign/Unassign work values for research: implement backend logic to Assign/Unassign work values
* B-67687: [Value] FDA&UDM: Value filters (basic filters): implement controller logic
* B-69502 FDA: Value filters part 2 (additional filters): implement backend to filter Assignees

14.1.18
-
* B-67687 [Value] FDA & UDM: Value filters (basic filters): implement query and service logic to get value periods
* B-67687 [Value] FDA & UDM: Value filters (basic filters): implement Periods filter window
* B-67687 [Value] FDA & UDM: Value filters (basic filters): add filter fields to main filter panel
* B-67687 [Value] FDA & UDM: Value filters (basic filters): create domain model to Values filter
* B-65962 [Value] FDA & UDM: Create and Populate Value batch: Implement db table for work values
* B-65962 [Value] FDA & UDM: Create and Populate Value batch: implement window to populate value batch
* B-65962 [Value] FDA & UDM: Create and Populate Value batch: Implement db table for UDM age weights
* B-68616 FDA & UDM: export baseline data: implement backend logic to export baseline data
* B-68616 FDA & UDM: export baseline data: introduce Export button for baseline usages tab
* B-69502 FDA: Value filters part 2 (additional filters): implement UI to filter Last Value Period by dates
* B-69502 FDA: Value filters part 2 (additional filters): implement UI for 'More Filters' window
* B-67691 [BONUS] [Value] FDA & UDM: Connect with foreign Exchange rest service: implement RFEX service to get exchange rate
* B-67685 [BONUS] [Value] FDA & UDM: Populate the list of currencies: implement service logic to get list of available currencies
* B-67685 [BONUS] [Value] FDA & UDM: Populate the list of currencies: introduce property with list of currencies
* B-68822 Tech Debt: FDA: introduce separate packages for UDM subtabs in UI module
* B-68821 Tech Debt: FDA: change name of Create User column to Created By on UI for all product families
* B-68821 Tech Debt: FDA: move all validation error messages to properties for ACL
* B-68821 Tech Debt: FDA: implement required validator and apply it for FAS
* B-68821 Tech Debt: FDA: change name of Create User column to Created By on UI for all product families

14.1.17
-
* B-68367: FDA & UDM: view baseline usages: implement refresh functionality for UDM sub-tabs
* B-68367: FDA & UDM: view baseline usages: move search widget to separate layout on UDM usages window
* B-68821: Tech Debt: FDA: move all validation error messages to properties for AACL
* B-68821: Tech Debt: FDA: move all validation error messages to properties for SAL
* B-65866: FDA & UDM: publish usages to baseline: make changes based on CR-DIST-FOREIGN-259
* B-65866: FDA & UDM: publish usages to baseline: add logic to close Publish to Baseline window after click button Publish

14.1.16
-
* B-68367 FDA & UDM: view baseline usages: resize columns on Baseline and Values tab, swap Updated By/Date columns on Baseline view
* B-68830 FDA & UDM - Baseline view filters: implement backend logic to retrieve baseline periods
* B-68821 Tech Debt: FDA: apply desc order for periods filter on UDM tabs

14.1.15
-
* B-68821 Tech Debt: FDA: update CSS for nested tabs
* B-68821 Tech Debt: FDA: fix order of Detail Licensee Classes in UDM single edit, UDM multiple edit dropdowns
* B-68821 Tech Debt: FDA: apply desc order for periods filter on UDM tabs
* B-68821 Tech Debt: FDA: move all validation error messages to properties for FAS/FAS2
* B-68821 Tech Debt: FDA: fix Quantity validation message for case of 0 or negative value to “Field value should be positive number” in UDM single edit, UDM multiple edit
* B-68821 Tech Debt: FDA: move all validation error messages to properties for NTS
* B-68367 FDA & UDM: view baseline usages: disable hidden feature for DETAIL ID on UDM Baseline sub-tab
* B-65866: FDA & UDM: publish usages to baseline: add validation to avoid update udm usages in baseline  for researcher role and fix audit message
* B-65866: FDA & UDM: publish usages to baseline: add validation to avoid delete batch if the batch has once usage in baseline
* B-68830 FDA & UDM - Baseline view filters: implement backend logic to retrieve baseline usages using filters from db

14.1.14
-
* B-66785: Value FDA & UDM: Create Publication Types table: add description and product_family columns into df_publication_type table
* B-68830: FDA & UDM - Baseline view filters: implement baseline filter controller and widget
* B-68830: FDA & UDM - Baseline view filters: implement main filters
* B-68830: FDA & UDM - Baseline view filters: implement widget for aggregate licensee classes and update logic for retrieval aggregate licensee classes
* B-68830: FDA & UDM - Baseline view filters: implement additional filters window
* B-65866: FDA & UDM: publish usages to baseline: implement repository logic related to publish and remove to baseline
* B-65866: FDA & UDM: publish usages to baseline: implement controller logic to publish to baseline
* B-65866: FDA & UDM: publish usages to baseline: implement repository logic related to publish and remove to baseline
* B-65866: FDA & UDM: publish usages to baseline: implement service logic to publish to baseline and remove from baseline UDM usages
* B-65866: FDA & UDM: publish usages to baseline: implement Publish button
* B-65866: FDA & UDM: publish usages to baseline: add new columns to df_udm_usage table
* B-65866: FDA & UDM: publish usages to baseline: implement modal window to publish to baseline
* B-65866: FDA & UDM: publish usages to baseline: implement controller logic to publish to baseline
* B-65963: FDA: UI view for values: implement decimal formatting
* B-65963: FDA: UI view for values: add Values subtab to UDM tab
* B-65963: FDA: UI view for values: implement widget and controller
* B-65963: FDA: UI view for values: implement empty filters panel
* B-68367: FDA & UDM: view baseline usages: implement baseline domain object
* B-68367: FDA & UDM: view baseline usages: add Baseline sub-tab to UDM tab
* B-68367: FDA & UDM: view baseline usages: implement Baseline sub-tab 
* B-68367: FDA & UDM: view baseline usages: fix double initialization of UDM widgets
* B-68821: FDA: Tech Debt: implement nested tabs inside UDM tab
* B-68821: FDA: Tech Debt: update CSS for nested tabs

14.1.13
-
* B-68058: FDA&UDM: Log and display single usage edits in the UDM Audit: make changes based on comments in CR-DIST-FOREIGN-254
* B-69243: FDA&UDM Improvements: Editing usage details: introduce validation for work information on UDM single edit window
* B-68820: Tech Debt: FDA: fix vulnerability CVE-2019-10219 associated with dependency org.hibernate:hibernate-validator:5.2.4.Final

14.1.12
-
* B-69243: FDA&UDM Improvements: Editing usage details: implement validation for Ineligible reason and Status fields for multiple Usage Edit
* B-68058: FDA&UDM: Log and display single usage edits in the UDM Audit: increase column size of df_udm_audit.action_reason column
* B-68058: FDA&UDM: Log and display single usage edits in the UDM Audit: implement logic to select usage for which the history window was opened and adjust logic for generating audit records in widgets
* B-65870: FDA&UDM: Bulk update: make changes based on comments in CR-DIST-FOREIGN-253

14.1.11
-
* B-69243: FDA&UDM Improvements: Editing usage details: implement validation for Ineligible reason and Status fields for single Usage Edit
* B-69243: FDA&UDM Improvements: Editing usage details: apply maximum 5 decimal places validation for statistical multiplier field
* B-68058: FDA&UDM: Log and display single usage edits in the UDM Audit: implement logic to log multiple usage edit

14.1.10
-
* CDP-1010: FDA: ACL: Multiple Edit: Reported Standard Number and Reported Title are cleared for edited usages
* CDP-1009: FDA: EDIT UDM Usage: Exception occurs when changing populated Action reason and Ineligible reason fields to empty value
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
* B-65718 FDA: Batch Processing View: implement Batch Status widget for FAS
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
* B-59009 FDA: Update SAL usages with distribution data from LM: implement service logic and adjust paid consumer to update SAL paid usages

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
* B-57903 FDA: View and Delete SAL batch: CDP-942: FDA: Delete SAL Usage Bach: Usages from deleted usage batch are still displayed filtered on ‘Usages’ table in case the usage batch is previously filtered on ‘Usages’ tab
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
* B-56847 Tech Debt: FDA: adjust AaclUsage domain object to remove redundant fields for Agg and LC class detail
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
* CDP-707 FDA: Create Additional Funds: fix sorting on Delete UsageBatch/PreService fee fund windows
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
* B-48760 FDA: Create NTS PreService fee additional fund pool from NTS withdrawn details: add withdrawn_amount column to df_fund_pool table
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
* B-49462 Tech Debt: FDA: remove environment prefix from internal SQS endoints
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
