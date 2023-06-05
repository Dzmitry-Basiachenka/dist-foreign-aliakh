databaseChangeLog {
    property(file: 'database.properties')

    changeSet(id: '2023-05-25-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-79569 ACL: Revise the process to find rights in an updated hierarchy: add records to " +
                "df_grant_priority table for ACL_UDM_USAGE")
        //MACL
        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '46d40dcf-775c-4677-80c2-6e18f0c19179')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'MACL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '2')
            column(name: 'license_type', value: 'MACL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '332cfb46-1f69-4b3a-a693-f9f5072dd7bc')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'MACL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '3')
            column(name: 'license_type', value: 'MACL')
        }
        //JACDCL
        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'd6165892-7afd-42de-9d66-36586e3d5e75')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'JACDCL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '4')
            column(name: 'license_type', value: 'JACDCL')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'f8087eb5-845b-45d1-aa31-fcff4a857418')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'JACDCL')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '5')
            column(name: 'license_type', value: 'JACDCL')
        }
        //VGW
        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'b5505b8f-9443-4e81-8e8c-0ffbcb710083')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'VGW')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '6')
            column(name: 'license_type', value: 'VGW')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '3e4f0619-8965-4fba-aa10-774e0b072b4c')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'VGW')
            column(name: 'type_of_use', value: 'PRINT')
            column(name: 'priority', value: '7')
            column(name: 'license_type', value: 'VGW')
        }
        //TRS
        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '8f011493-92e8-48e8-8730-25de360169f7')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'TRS')
            column(name: 'type_of_use', value: 'NGT_PHOTOCOPY')
            column(name: 'priority', value: '8')
            column(name: 'license_type', value: 'TRS')
        }
        //DPS
        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '8c5fe106-5760-46a7-bd42-4b46d0ca4804')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'DPS')
            column(name: 'type_of_use', value: 'NGT_SEND_EMAIL')
            column(name: 'priority', value: '9')
            column(name: 'license_type', value: 'DPS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'a1378788-9469-487b-8c84-1da8868b1817')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'DPS')
            column(name: 'type_of_use', value: 'NGT_INTRANET_POST')
            column(name: 'priority', value: '10')
            column(name: 'license_type', value: 'DPS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '82429ed7-8943-42ba-aad8-b360e5c7e974')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'DPS')
            column(name: 'type_of_use', value: 'NGT_INTERNET_POST')
            column(name: 'priority', value: '11')
            column(name: 'license_type', value: 'DPS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '00f38d31-50e1-44b3-8f30-417ab985c851')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'DPS')
            column(name: 'type_of_use', value: 'NGT_EXTRANET_POST')
            column(name: 'priority', value: '12')
            column(name: 'license_type', value: 'DPS')
        }
        //APS
        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '2a70c424-38bf-460d-be44-a94b3f9a051b')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'APS')
            column(name: 'type_of_use', value: 'NGT_PRINT_COURSE_MATERIALS')
            column(name: 'priority', value: '13')
            column(name: 'license_type', value: 'APS')
        }
        //ECC
        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '4938eb04-24ca-4b0b-b6d4-495bb1db0363')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'ECC')
            column(name: 'type_of_use', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            column(name: 'priority', value: '14')
            column(name: 'license_type', value: 'ECC')
        }
        //SAL
        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '4d8e79fc-34ab-45be-9b9f-17e3e3c3bce8')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'SAL')
            column(name: 'type_of_use', value: 'DIGITAL')
            column(name: 'priority', value: '15')
            column(name: 'license_type', value: 'SAL')
        }
        //ACLCI
        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '5ed5485b-ce7f-4139-8729-0ffe351216da')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'ACLCI')
            column(name: 'type_of_use', value: 'CURR_REPUB_K12')
            column(name: 'priority', value: '16')
            column(name: 'license_type', value: 'ACLCI')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'e4ec7136-f8a6-48f1-bc47-3224e3280e4b')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'ACLCI')
            column(name: 'type_of_use', value: 'CURR_REPUB_HE')
            column(name: 'priority', value: '17')
            column(name: 'license_type', value: 'ACLCI')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '4c493854-d4ec-473d-a48f-dca56e5ac04b')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'ACLCI')
            column(name: 'type_of_use', value: 'CURR_REUSE_K12')
            column(name: 'priority', value: '18')
            column(name: 'license_type', value: 'ACLCI')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'a12883c1-a722-4d94-90f3-d103cb40023a')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'ACLCI')
            column(name: 'type_of_use', value: 'CURR_SHARE_K12')
            column(name: 'priority', value: '19')
            column(name: 'license_type', value: 'ACLCI')
        }
        //RLS
        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '2a2bf47c-55f4-4198-96b2-17d47d6be3a0')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_BOOK')
            column(name: 'priority', value: '20')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '0b5515b9-51bd-44fd-8f12-4d04ba672b4c')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_JOURNAL_MAGAZINE')
            column(name: 'priority', value: '21')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '2059981a-a9e0-4be6-aa35-3be6efae2e65')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_EDU_INSTRUC_PROGRAM')
            column(name: 'priority', value: '22')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'c37b1c6a-a3a2-418e-adb2-fdfa28b03532')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_NEWSPAPER')
            column(name: 'priority', value: '23')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '1ebb3372-fa60-484a-8aad-ac4bfff231d6')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_NEWSLETTER')
            column(name: 'priority', value: '24')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '01f1a533-f279-4fe8-8d0c-40db483969a6')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_THESIS_DISSERTATION')
            column(name: 'priority', value: '25')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'be38ed2b-0063-4fee-9ed3-fb3aeac19a72')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_BROC_PROMO_MATERIAL')
            column(name: 'priority', value: '26')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '894d71d5-20de-40b1-a6c5-4599428e598c')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_PRESENTATION_SLIDES')
            column(name: 'priority', value: '27')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '9205c71d-ae8c-4dd9-962e-3d093035e661')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_CONT_EDU_TRAIN_MATERIALS')
            column(name: 'priority', value: '28')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '04667f96-89b7-48ef-b044-d572ce99cef7')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_TRAINING_MATERIALS')
            column(name: 'priority', value: '29')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '1c84c7ea-34d5-4954-9003-2f525af4dfea')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_OTHR_PUBLISH_PRODUCT')
            column(name: 'priority', value: '30')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'd982ed60-7825-475a-9cbb-ef2b261592f2')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_MEDCOMMS')
            column(name: 'priority', value: '31')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '88a7d6f4-3f82-49a0-8683-cad7f5478605')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_MEDCOMMS_MEDICAL_PROMO')
            column(name: 'priority', value: '32')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '7b3411fa-b7a6-457c-869f-2d1b91330949')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_MEDCOMMS_MEDICALED_NONPROMO')
            column(name: 'priority', value: '33')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: 'ba51bf03-5475-45e8-ab37-9de27afdfd88')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_MEDCOMMS_INTERNAL_TRAIN')
            column(name: 'priority', value: '34')
            column(name: 'license_type', value: 'RLS')
        }

        insert(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'df_grant_priority_uid', value: '48d7a2c4-74aa-435a-af7c-b294adce270a')
            column(name: 'product_family', value: 'ACL_UDM_USAGE')
            column(name: 'grant_product_family', value: 'RLS')
            column(name: 'type_of_use', value: 'NGT_MEDCOMMS_REFMATERIALS')
            column(name: 'priority', value: '35')
            column(name: 'license_type', value: 'RLS')
        }

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                where "product_family = 'ACL_UDM_USAGE' and priority between 2 AND 35"
            }
        }
    }

    changeSet(id: '2023-06-05-00', author: 'Anton Azarenka <aazarenka@copyright.com>') {
        comment("B-79569 ACL: Revise the process to find rights in an updated hierarchy: update values for " +
                "ACL_UDM_USAGES where type of use does not equal PRINT or DIGITAL")

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_PHOTOCOPY')
            where "product_family = 'ACL_UDM_USAGE' and priority = '8'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_SEND_EMAIL')
            where "product_family = 'ACL_UDM_USAGE' and priority = '9'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_INTRANET_POST')
            where "product_family = 'ACL_UDM_USAGE' and priority = '10'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_INTERNET_POST')
            where "product_family = 'ACL_UDM_USAGE' and priority = '11'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_EXTRANET_POST')
            where "product_family = 'ACL_UDM_USAGE' and priority = '12'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_PRINT_COURSE_MATERIALS')
            where "product_family = 'ACL_UDM_USAGE' and priority = '13'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_ELECTRONIC_COURSE_MATERIALS')
            where "product_family = 'ACL_UDM_USAGE' and priority = '14'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'CURR_REPUB_K12')
            where "product_family = 'ACL_UDM_USAGE' and priority = '16'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'CURR_REPUB_HE')
            where "product_family = 'ACL_UDM_USAGE' and priority = '17'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'CURR_REUSE_K12')
            where "product_family = 'ACL_UDM_USAGE' and priority = '18'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'CURR_SHARE_K12')
            where "product_family = 'ACL_UDM_USAGE' and priority = '19'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_BOOK')
            where "product_family = 'ACL_UDM_USAGE' and priority = '20'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_JOURNAL_MAGAZINE')
            where "product_family = 'ACL_UDM_USAGE' and priority = '21'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_EDU_INSTRUC_PROGRAM')
            where "product_family = 'ACL_UDM_USAGE' and priority = '22'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_NEWSPAPER')
            where "product_family = 'ACL_UDM_USAGE' and priority = '23'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_NEWSLETTER')
            where "product_family = 'ACL_UDM_USAGE' and priority = '24'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_THESIS_DISSERTATION')
            where "product_family = 'ACL_UDM_USAGE' and priority = '25'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_BROC_PROMO_MATERIAL')
            where "product_family = 'ACL_UDM_USAGE' and priority = '26'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_PRESENTATION_SLIDES')
            where "product_family = 'ACL_UDM_USAGE' and priority = '27'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_CONT_EDU_TRAIN_MATERIALS')
            where "product_family = 'ACL_UDM_USAGE' and priority = '28'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_TRAINING_MATERIALS')
            where "product_family = 'ACL_UDM_USAGE' and priority = '29'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_OTHR_PUBLISH_PRODUCT')
            where "product_family = 'ACL_UDM_USAGE' and priority = '30'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_MEDCOMMS')
            where "product_family = 'ACL_UDM_USAGE' and priority = '31'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_MEDCOMMS_MEDICAL_PROMO')
            where "product_family = 'ACL_UDM_USAGE' and priority = '32'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_MEDCOMMS_MEDICALED_NONPROMO')
            where "product_family = 'ACL_UDM_USAGE' and priority = '33'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_MEDCOMMS_INTERNAL_TRAIN')
            where "product_family = 'ACL_UDM_USAGE' and priority = '34'"
        }

        update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
            column(name: 'license_type', value: 'NGT_MEDCOMMS_REFMATERIALS')
            where "product_family = 'ACL_UDM_USAGE' and priority = '35'"
        }

        rollback {
            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'TRS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '8'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'DPS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '9'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'DPS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '10'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'DPS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '11'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'DPS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '12'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'APS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '13'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'ECC')
                where "product_family = 'ACL_UDM_USAGE' and priority = '14'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'ACLCI')
                where "product_family = 'ACL_UDM_USAGE' and priority = '16'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'ACLCI')
                where "product_family = 'ACL_UDM_USAGE' and priority = '17'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'ACLCI')
                where "product_family = 'ACL_UDM_USAGE' and priority = '18'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'ACLCI')
                where "product_family = 'ACL_UDM_USAGE' and priority = '19'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '20'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '21'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '22'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '23'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '24'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '25'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '26'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '27'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '28'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '29'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '30'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '31'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '32'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '33'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '34'"
            }

            update(schemaName: dbAppsSchema, tableName: 'df_grant_priority') {
                column(name: 'license_type', value: 'RLS')
                where "product_family = 'ACL_UDM_USAGE' and priority = '35'"
            }
        }
    }
}
