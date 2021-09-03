package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.repository.api.IPublicationTypeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Integration test for {@link IPublicationTypeRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/21/2020
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@Transactional
public class PublicationTypeRepositoryIntegrationTest {

    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String ACL_PRODUCT_FAMILY = "ACL";
    private static final String DEFAULT_WEIGHT = "1.00";

    @Autowired
    private IPublicationTypeRepository publicationTypeRepository;

    @Test
    public void testIsExistByNameAacl() {
        assertTrue(publicationTypeRepository.isExistForProductFamily("Book", AACL_PRODUCT_FAMILY));
        assertTrue(publicationTypeRepository.isExistForProductFamily("boOK", AACL_PRODUCT_FAMILY));
        assertFalse(publicationTypeRepository.isExistForProductFamily("Books", AACL_PRODUCT_FAMILY));
    }

    @Test
    public void testIsExistByNameAcl() {
        assertTrue(publicationTypeRepository.isExistForProductFamily("BK", ACL_PRODUCT_FAMILY));
        assertTrue(publicationTypeRepository.isExistForProductFamily("bk2", ACL_PRODUCT_FAMILY));
        assertFalse(publicationTypeRepository.isExistForProductFamily("BK3", ACL_PRODUCT_FAMILY));
    }

    @Test
    public void testFindPublicationTypesAacl() {
        List<PublicationType> pubTypes = publicationTypeRepository.findByProductFamily(AACL_PRODUCT_FAMILY);
        assertNotNull(pubTypes);
        assertEquals(5, pubTypes.size());
        assertEquals(buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "Book", DEFAULT_WEIGHT),
            pubTypes.get(0));
        assertEquals(buildPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "Business or Trade Journal", "1.50"),
            pubTypes.get(1));
        assertEquals(buildPublicationType("46634907-882e-4f91-b1ad-f57db945aff7", "Consumer Magazine", DEFAULT_WEIGHT),
            pubTypes.get(2));
        assertEquals(buildPublicationType("a3dff475-fc06-4d8c-b7cc-f093073ada6f", "News Source", "4.00"),
            pubTypes.get(3));
        assertEquals(buildPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", "STMA Journal", "1.10"),
            pubTypes.get(4));
    }

    @Test
    public void testFindPublicationTypesAcl() {
        List<PublicationType> pubTypes = publicationTypeRepository.findByProductFamily(ACL_PRODUCT_FAMILY);
        assertNotNull(pubTypes);
        assertEquals(10, pubTypes.size());
        assertEquals(buildPublicationTypeWithDescription("73876e58-2e87-485e-b6f3-7e23792dd214", "BK", "Book",
            ACL_PRODUCT_FAMILY, DEFAULT_WEIGHT), pubTypes.get(0));
        assertEquals(buildPublicationTypeWithDescription("f1f523ca-1b46-4d3a-842d-99252785187c", "BK2", "Book series",
            ACL_PRODUCT_FAMILY, DEFAULT_WEIGHT), pubTypes.get(1));
        assertEquals(buildPublicationTypeWithDescription("aef4304b-6722-4047-86e0-8c84c72f096d", "NL", "Newsletter",
            ACL_PRODUCT_FAMILY, "1.90"), pubTypes.get(2));
        assertEquals(buildPublicationTypeWithDescription("076f2c40-f524-405d-967a-3840df2b57df", "NP", "Newspaper",
            ACL_PRODUCT_FAMILY, "3.50"), pubTypes.get(3));
        assertEquals(buildPublicationTypeWithDescription("ad8df236-5200-4acf-be55-cf82cd342f14", "OT", "Other",
            ACL_PRODUCT_FAMILY, DEFAULT_WEIGHT), pubTypes.get(4));
        assertEquals(buildPublicationTypeWithDescription("34574f62-7922-48b9-b798-73bf5c3163da", "SJ",
            "Scholarly Journal", ACL_PRODUCT_FAMILY, "1.30"), pubTypes.get(5));
        assertEquals(buildPublicationTypeWithDescription("9c5c6797-a861-44ae-ada9-438acb20334d", "STND", "Standards",
            ACL_PRODUCT_FAMILY, DEFAULT_WEIGHT), pubTypes.get(6));
        assertEquals(buildPublicationTypeWithDescription("c0db0a37-9854-495f-99b7-1e3486c232cb", "TG",
            "Trade Magazine/Journal", ACL_PRODUCT_FAMILY, "1.90"), pubTypes.get(7));
        assertEquals(buildPublicationTypeWithDescription("0a4bcf78-95cb-445e-928b-e48ad12acfd2", "TGB",
            "Trade and Business News", ACL_PRODUCT_FAMILY, "1.90"), pubTypes.get(8));
        assertEquals(buildPublicationTypeWithDescription("56e31ea2-2f32-43a5-a0a7-9b1ecb1e73fe", "TGC",
            "Consumer magazine", ACL_PRODUCT_FAMILY, "2.70"), pubTypes.get(9));
    }

    private PublicationType buildPublicationTypeWithDescription(String id, String name, String description,
                                                                String productFamily, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setId(id);
        pubType.setName(name);
        pubType.setDescription(description);
        pubType.setProductFamily(productFamily);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }

    private PublicationType buildPublicationType(String id, String name, String weight) {
        return buildPublicationTypeWithDescription(id, name, null, AACL_PRODUCT_FAMILY, weight);
    }
}
