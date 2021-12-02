package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;
import com.copyright.rup.dist.foreign.service.impl.acl.AclWorkflowIntegrationTestBuilder.Runner;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.UdmCsvProcessor;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.Builder;
import org.apache.commons.lang3.tuple.Pair;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Builder for {@link AclWorkflowIntegrationTest}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/21/21
 *
 * @author Uladzislau Shalamitski
 */
@Component
public class AclWorkflowIntegrationTestBuilder implements Builder<Runner> {

    @Autowired
    private IUdmBatchService udmBatchService;
    @Autowired
    private IUdmUsageService udmUsageService;
    @Autowired
    private IUdmValueService udmValueService;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private SqsClientMock sqsClientMock;
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private List<UdmUsageDto> udmUsageDtos;
    private UdmBatch expectedUdmBatch;
    private int expectedCountOfPublishedUsages;
    private int expectedCountOfPopulatedValues;
    private int expectedCountOfPublishedValues;
    private String pathToUsagesToUpload;
    private String pathToExpectedUsages;
    private String pathToExpectedValues;
    private String pathToExpectedValuesBaseline;
    private List<String> uploadedUsagesIds = new ArrayList<>();
    private List<String> pathsToExpectedAuditItems = new ArrayList<>();
    private Map<String, String> expectedRmsRequestToResponseMap = new HashMap<>();
    private Map<Long, String> expectedPrmAccountNumberToResponseMap = new HashMap<>();

    public AclWorkflowIntegrationTestBuilder withExpectedCountOfPublishedUsages(int countOfPublishedUsages) {
        this.expectedCountOfPublishedUsages = countOfPublishedUsages;
        return this;
    }

    public AclWorkflowIntegrationTestBuilder withExpectedCountOfPopulatedValues(int countOfPopulatedValues) {
        this.expectedCountOfPopulatedValues = countOfPopulatedValues;
        return this;
    }

    public AclWorkflowIntegrationTestBuilder withExpectedCountOfPublishedValues(int countOfPublishedValues) {
        this.expectedCountOfPublishedValues = countOfPublishedValues;
        return this;
    }

    public AclWorkflowIntegrationTestBuilder withUdmBatch(UdmBatch udmBatch) {
        this.expectedUdmBatch = udmBatch;
        return this;
    }

    public AclWorkflowIntegrationTestBuilder withExpectedUsages(String pathToUsages) {
        this.pathToExpectedUsages = pathToUsages;
        return this;
    }

    public AclWorkflowIntegrationTestBuilder withExpectedValues(String pathToValues) {
        this.pathToExpectedValues = pathToValues;
        return this;
    }

    public AclWorkflowIntegrationTestBuilder withExpectedValuesBaseline(String pathToValuesBaseline) {
        this.pathToExpectedValuesBaseline = pathToValuesBaseline;
        return this;
    }

    public AclWorkflowIntegrationTestBuilder withUsagesToUpload(String usagesToUpload) {
        this.pathToUsagesToUpload = usagesToUpload;
        return this;
    }

    public AclWorkflowIntegrationTestBuilder withRmsRequests(Map<String, String> rmsRequestToResponseMap) {
        this.expectedRmsRequestToResponseMap = rmsRequestToResponseMap;
        return this;
    }

    public AclWorkflowIntegrationTestBuilder withPrmRequests(Map<Long, String> prmAccountNumberToResponse) {
        this.expectedPrmAccountNumberToResponseMap = prmAccountNumberToResponse;
        return this;
    }

    public AclWorkflowIntegrationTestBuilder withUsageAuditItems(List<String> pathsToAuditItems) {
        this.pathsToExpectedAuditItems = pathsToAuditItems;
        return this;
    }

    void reset() {
        this.expectedCountOfPublishedUsages = 0;
        this.expectedCountOfPopulatedValues = 0;
        this.expectedCountOfPublishedValues = 0;
        this.expectedUdmBatch = null;
        this.pathToUsagesToUpload = null;
        this.pathToExpectedUsages = null;
        this.pathToExpectedValues = null;
        this.pathToExpectedValuesBaseline = null;
        this.uploadedUsagesIds.clear();
        this.expectedRmsRequestToResponseMap.clear();
        this.expectedPrmAccountNumberToResponseMap.clear();
        this.pathsToExpectedAuditItems.clear();
        this.sqsClientMock.reset();
    }

    @Override
    public Runner build() {
        return new Runner();
    }

    /**
     * Test runner class.
     */
    public class Runner {

        public void run() throws IOException, InterruptedException {
            testHelper.createRestServer();
            testHelper.expectGetRmsRights(expectedRmsRequestToResponseMap);
            expectedPrmAccountNumberToResponseMap.forEach(
                (accountNumber, response) -> testHelper.expectPrmCall(response, accountNumber));
            loadUdmBatch();
            assignUsages();
            publishUsagesToBaseline();
            populateValueBatch();
            publishValuesToBaseline();
            assertUdmUsages();
            assertUdmValues();
            assertUdmValuesBaseline();
            assertUsageAudit();
            testHelper.verifyRestServer();
        }

        private void loadUdmBatch() throws IOException {
            UdmCsvProcessor csvProcessor = csvProcessorFactory.getUdmCsvProcessor();
            DistCsvProcessor.ProcessingResult<UdmUsage> result = csvProcessor.process(getCsvOutputStream());
            assertTrue(result.isSuccessful());
            List<UdmUsage> udmUsages = result.get();
            udmBatchService.insertUdmBatch(expectedUdmBatch, udmUsages);
            udmUsageService.sendForMatching(udmUsages);
            uploadedUsagesIds = udmUsages.stream().map(BaseEntity::getId).collect(Collectors.toList());
        }

        private void assignUsages() {
            UdmUsageFilter filter = new UdmUsageFilter();
            filter.setPeriod(expectedUdmBatch.getPeriod());
            filter.setUsageStatus(UsageStatusEnum.RH_FOUND);
            udmUsageDtos = udmUsageService.getUsageDtos(filter, null, null)
                .stream()
                .peek(udmUsageDto -> udmUsageDto.setAssignee("user@copyright.com"))
                .collect(Collectors.toList());
            udmUsageService.assignUsages(new HashSet<>(udmUsageDtos));
        }

        private void publishUsagesToBaseline() {
            Map<UdmUsageDto, UdmAuditFieldToValuesMap> usageToAuditMap = udmUsageDtos.stream()
                .collect(Collectors.toMap(udmUsageDto -> {
                    udmUsageDto.setStatus(UsageStatusEnum.ELIGIBLE);
                    return udmUsageDto;
                }, udmUsageDto -> {
                    UdmAuditFieldToValuesMap valuesMap = new UdmAuditFieldToValuesMap(udmUsageDto);
                    valuesMap.updateFieldValue("Detail Status", UsageStatusEnum.ELIGIBLE.name());
                    return valuesMap;
                }));
            udmUsageService.updateUsages(usageToAuditMap, false);
            assertEquals(Pair.of(expectedCountOfPublishedUsages, 0),
                udmUsageService.publishUdmUsagesToBaseline(expectedUdmBatch.getPeriod()));
        }

        private void populateValueBatch() {
            assertEquals(expectedCountOfPopulatedValues,
                udmValueService.populateValueBatch(expectedUdmBatch.getPeriod()));
        }

        private void publishValuesToBaseline() {
            UdmValueFilter filter = new UdmValueFilter();
            filter.setPeriods(ImmutableSet.of(expectedUdmBatch.getPeriod()));
            udmValueService.getValueDtos(filter, null, null)
                .forEach(udmValueDto -> {
                    PublicationType publicationType = new PublicationType();
                    publicationType.setId("076f2c40-f524-405d-967a-3840df2b57df");
                    publicationType.setName("NP");
                    udmValueDto.setPublicationType(publicationType);
                    udmValueDto.setPrice(new BigDecimal("100"));
                    udmValueDto.setPriceInUsd(new BigDecimal("150"));
                    udmValueDto.setCurrency("EUR");
                    udmValueDto.setCurrencyExchangeRate(new BigDecimal("1.5"));
                    udmValueDto.setPriceFlag(true);
                    udmValueDto.setContent(BigDecimal.TEN);
                    udmValueDto.setContentFlag(true);
                    udmValueDto.setContentUnitPrice(new BigDecimal("15"));
                    udmValueDto.setStatus(UdmValueStatusEnum.RESEARCH_COMPLETE);
                    udmValueService.updateValue(udmValueDto);
                });
            assertEquals(expectedCountOfPublishedValues,
                udmValueService.publishToBaseline(expectedUdmBatch.getPeriod()));
        }

        private void assertUdmUsages() throws IOException {
            List<UdmUsage> actualUsages = udmUsageService.getUdmUsagesByIds(uploadedUsagesIds);
            List<UdmUsage> expectedUsages = testHelper.loadExpectedUdmUsages(pathToExpectedUsages);
            testHelper.assertUdmUsages(expectedUsages, actualUsages);
        }

        private void assertUdmValues() throws IOException {
            UdmValueFilter filter = new UdmValueFilter();
            filter.setPeriods(ImmutableSet.of(expectedUdmBatch.getPeriod()));
            List<UdmValueDto> actualValueDtos =
                udmValueService.getValueDtos(filter, null, new Sort("wrWrkInst", Sort.Direction.ASC));
            List<UdmValueDto> expectedValueDtos = testHelper.loadExpectedUdmValueDto(pathToExpectedValues);
            testHelper.assertUdmValueDtos(expectedValueDtos, actualValueDtos);
        }

        private void assertUdmValuesBaseline() throws IOException {
            List<UdmValueBaselineDto> actualUdmValues = sqlSessionTemplate
                .selectList("IUdmBaselineValueMapper.findValuesByPeriod", expectedUdmBatch.getPeriod())
                .stream()
                .map(udmValue -> (UdmValueBaselineDto) udmValue)
                .sorted(Comparator.comparing(UdmValueBaselineDto::getWrWrkInst))
                .collect(Collectors.toList());
            List<UdmValueBaselineDto> expectedUdmValues =
                testHelper.loadExpectedValueBaselineDto(pathToExpectedValuesBaseline);
            testHelper.assertValueBaselineDtos(expectedUdmValues, actualUdmValues);
        }

        private void assertUsageAudit() throws IOException {
            assertEquals(pathsToExpectedAuditItems.size(), uploadedUsagesIds.size());
            for (int index = 0; index < pathsToExpectedAuditItems.size(); index++) {
                List<UsageAuditItem> usageAuditItems =
                    testHelper.loadExpectedUsageAuditItems(pathsToExpectedAuditItems.get(index));
                testHelper.assertUdmAudit(uploadedUsagesIds.get(index), usageAuditItems);
            }
        }

        private ByteArrayOutputStream getCsvOutputStream() throws IOException {
            String csvText = TestUtils.fileToString(ServiceTestHelper.class, pathToUsagesToUpload);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.write(csvText, out, StandardCharsets.UTF_8);
            return out;
        }
    }
}
