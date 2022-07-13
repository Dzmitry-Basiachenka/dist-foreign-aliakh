package com.copyright.rup.dist.foreign.domain.common.util;

import com.copyright.rup.dist.common.util.LogUtils.ILogWrapper;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.UsageAge;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides methods to log information for FDA related objects.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/08/18
 *
 * @author Ihar Suvorau
 */
public final class ForeignLogUtils {

    private static final String NULL_STRING = "NULL";
    private static final String LIST_DELIMITER = ", ";

    private ForeignLogUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Wraps the {@link Scenario} object and provides name and status for log message.
     *
     * @param scenario instance of {@link Scenario}
     * @return instance of {@link ILogWrapper}
     */
    public static ILogWrapper scenario(Scenario scenario) {
        return new ILogWrapper() {
            @Override
            public String toString() {
                return null == scenario
                    ? "Scenario={NULL}"
                    : String.format("ScenarioName='%s', Status='%s'", scenario.getName(), scenario.getStatus());
            }
        };
    }

    /**
     * Wraps the {@link AclScenario} object and provides name and status for log message.
     *
     * @param aclScenario instance of {@link AclScenario}
     * @return instance of {@link ILogWrapper}
     */
    public static ILogWrapper aclScenario(AclScenario aclScenario) {
        return new ILogWrapper() {
            @Override
            public String toString() {
                return null == aclScenario
                    ? "AclScenario={NULL}"
                    : String.format("AclScenarioName='%s', Status='%s', AclScenarioFields[FundPoolId='%s', " +
                        "UsageBatchId='%s', GrantSetId='%s', UsageAges=%s, PublicationTypes=%s, " +
                        "DetailLicenseeClasses=%s]",
                    aclScenario.getName(),
                    aclScenario.getStatus(),
                    aclScenario.getFundPoolId(),
                    aclScenario.getUsageBatchId(),
                    aclScenario.getGrantSetId(),
                    formatUsageAges(aclScenario.getUsageAges()),
                    formatAclPublicationTypes(aclScenario.getPublicationTypes()),
                    formatDetailLicenseeClasses(aclScenario.getDetailLicenseeClasses()));
            }
        };
    }

    /**
     * Wraps the {@link AaclFields} object and provides information for log message.
     *
     * @param aaclFields instance of {@link AaclFields}
     * @return instance of {@link ILogWrapper}
     */
    public static ILogWrapper scenarioAaclFields(AaclFields aaclFields) {
        return new ILogWrapper() {
            @Override
            public String toString() {
                String result;
                if (null == aaclFields) {
                    result = "AaclFields={NULL}";
                } else {
                    result = String.format(
                        "AaclFields[FundPoolId=%s, UsageAges=%s, PublicationTypes=%s, DetailLicenseeClasses=%s]",
                        aaclFields.getFundPoolId(),
                        formatUsageAges(aaclFields.getUsageAges()),
                        formatPublicationTypes(aaclFields.getPublicationTypes()),
                        formatDetailLicenseeClasses(aaclFields.getDetailLicenseeClasses()));
                }
                return result;
            }
        };
    }

    private static String formatUsageAges(List<UsageAge> usageAges) {
        String result;
        if (null == usageAges) {
            result = NULL_STRING;
        } else {
            result = usageAges.stream()
                .map(usageAge -> null == usageAge
                    ? NULL_STRING :
                    String.format("[Period=%s, Weight=%s]", usageAge.getPeriod(), usageAge.getWeight()))
                .collect(Collectors.joining(LIST_DELIMITER));
        }
        return result;
    }

    private static String formatPublicationTypes(List<PublicationType> publicationTypes) {
        String result;
        if (null == publicationTypes) {
            result = NULL_STRING;
        } else {
            result = publicationTypes.stream()
                .map(publicationType -> null == publicationType
                    ? NULL_STRING :
                    String.format("[Name=%s, Weight=%s]", publicationType.getName(), publicationType.getWeight()))
                .collect(Collectors.joining(LIST_DELIMITER));
        }
        return result;
    }

    private static String formatAclPublicationTypes(List<AclPublicationType> publicationTypes) {
        String result;
        if (null == publicationTypes) {
            result = NULL_STRING;
        } else {
            result = publicationTypes.stream()
                .map(publicationType -> null == publicationType
                    ? NULL_STRING :
                    String.format("[Name=%s, Weight=%s, Period=%s]", publicationType.getName(),
                        publicationType.getWeight(), publicationType.getPeriod()))
                .collect(Collectors.joining(LIST_DELIMITER));
        }
        return result;
    }

    private static String formatDetailLicenseeClasses(List<DetailLicenseeClass> detailLicenseeClasses) {
        String result;
        if (null == detailLicenseeClasses) {
            result = NULL_STRING;
        } else {
            result = detailLicenseeClasses.stream()
                .map(ForeignLogUtils::formatDetailLicenseeClass)
                .collect(Collectors.joining(LIST_DELIMITER));
        }
        return result;
    }

    private static String formatDetailLicenseeClass(DetailLicenseeClass detailLicenseeClass) {
        String result;
        if (null == detailLicenseeClass) {
            result = NULL_STRING;
        } else {
            result = String.format("[DLC=%s, ALC=%s]", detailLicenseeClass.getId(),
                null == detailLicenseeClass.getAggregateLicenseeClass()
                    ? NULL_STRING
                    : detailLicenseeClass.getAggregateLicenseeClass().getId());
        }
        return result;
    }
}
