package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPool.AclciFields;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Verifies {@link AclciFundPoolFieldsJsonMapper}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/28/2022
 *
 * @author Mikita Maistrenka
 */
public class AclciFundPoolFieldsJsonMapperTest {

    private final AclciFundPoolFieldsJsonMapper jsonMapper = new AclciFundPoolFieldsJsonMapper();

    @Test
    public void testDeserialize() throws IOException {
        String json = TestUtils.fileToString(this.getClass(), "aclci_fund_pool_fields.json");
        assertEquals(buildAclciFields(), jsonMapper.deserialize(json));
    }

    @Test
    public void testSerialize() throws IOException {
        FundPool.AclciFields aclciFields = buildAclciFields();
        String actualJson = jsonMapper.serialize(aclciFields);
        assertEquals(aclciFields, jsonMapper.deserialize(actualJson));
    }

    @Test
    public void testDeserializeNull() throws IOException {
        assertNull(jsonMapper.deserialize(null));
    }

    @Test
    public void testSerializeNull() throws IOException {
        assertNull(jsonMapper.serialize(null));
    }

    private FundPool.AclciFields buildAclciFields() {
        FundPool.AclciFields aclciFields = new AclciFields();
        aclciFields.setCoverageYears("2022-2023");
        aclciFields.setGradeKto2NumberOfStudents(2);
        aclciFields.setGrade3to5NumberOfStudents(5);
        aclciFields.setGrade6to8NumberOfStudents(8);
        aclciFields.setGrade9to12NumberOfStudents(12);
        aclciFields.setGradeHeNumberOfStudents(20);
        aclciFields.setGrossAmount(new BigDecimal("1000.0"));
        aclciFields.setCurriculumGrossAmount(new BigDecimal("200.0"));
        aclciFields.setGradeKto2GrossAmount(new BigDecimal("34.04"));
        aclciFields.setGrade3to5GrossAmount(new BigDecimal("85.11"));
        aclciFields.setGrade6to8GrossAmount(new BigDecimal("136.17"));
        aclciFields.setGrade9to12GrossAmount(new BigDecimal("204.26"));
        aclciFields.setGradeHeGrossAmount(new BigDecimal("340.43"));
        aclciFields.setCurriculumSplitPercent(new BigDecimal("0.2"));
        return aclciFields;
    }
}
