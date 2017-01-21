package com.copyright.rup.dist.foreign;

import static org.junit.Assert.assertTrue;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import com.openpojo.validation.utils.ValidationHelper;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * Verifies {@link Object#equals(Object)}, {@link Object#hashCode()},
 * {@link Object#toString()}, getters, setters methods for domain classes.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/16/17
 *
 * @author Aliaksei Pchelnikau
 */
@RunWith(Parameterized.class)
public class DomainVerifierTest {

    private static PojoValidator sPojoValidator;

    private Class classToVerify;

    /**
     * Constructs new test for given class.
     *
     * @param classToVerify class to verify
     */
    public DomainVerifierTest(Class classToVerify) {
        this.classToVerify = classToVerify;
    }

    @BeforeClass
    public static void beforeClass() {
        sPojoValidator = new PojoValidator();

        sPojoValidator.addRule(new NoStaticExceptFinalRule());
        sPojoValidator.addRule(new NoFieldShadowingRule());
        sPojoValidator.addRule(new GetterMustExistRule());
        sPojoValidator.addRule(new SetterMustExistRule());

        sPojoValidator.addTester(new SetterTester());
        sPojoValidator.addTester(new GetterTester());
        sPojoValidator.addTester(new EqualsHashCodeTester());
        sPojoValidator.addTester(new ToStringTester());
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] testData = new Object[][]{
            {UsageFilter.class},
            {UsageBatch.class},
            {UsageDetail.class}
        };
        return Arrays.asList(testData);
    }

    @Test
    public void testPojoStructureAndBehavior() {
        sPojoValidator.runValidation(PojoClassFactory.getPojoClass(classToVerify));
    }

    private static class EqualsHashCodeTester implements Tester {

        @Override
        public void run(PojoClass pojoClass) {
            EqualsVerifier.forClass(pojoClass.getClazz())
                .suppress(Warning.NONFINAL_FIELDS)
                .usingGetClass()
                .verify();
        }
    }

    private static class ToStringTester implements Tester {

        @Override
        public void run(PojoClass pojoClass) {
            Object classInstance = ValidationHelper.getMostCompleteInstance(pojoClass);
            pojoClass.getPojoFields().stream()
                .filter(pojoField -> !pojoField.isStatic())
                .forEach(pojoField -> {
                    Object fieldValue = RandomFactory.getRandomValue(pojoField.getType());
                    pojoField.set(classInstance, fieldValue);
                });
            String stringRepresentation = classInstance.toString();
            assertTrue(StringUtils.contains(stringRepresentation, pojoClass.getClazz().getSimpleName()));
            pojoClass.getPojoFields().stream()
                .filter(pojoField -> !pojoField.isStatic())
                .forEach(pojoField -> {
                    assertTrue(StringUtils.contains(stringRepresentation, pojoField.getName()));
                    assertTrue(StringUtils.contains(stringRepresentation, pojoField.get(classInstance).toString()));
                });
        }
    }
}
