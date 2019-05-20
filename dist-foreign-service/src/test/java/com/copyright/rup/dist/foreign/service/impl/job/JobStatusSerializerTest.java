package com.copyright.rup.dist.foreign.service.impl.job;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.job.JobInfo;
import com.copyright.rup.dist.foreign.domain.job.JobStatusEnum;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.time.OffsetDateTime;

/**
 * Verifies {@link JobStatusSerializer}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/15/2019
 *
 * @author Uladzislau Shalamitski
 */
public class JobStatusSerializerTest {

    private final JobStatusSerializer jobInfoSerializer = new JobStatusSerializer();

    @Test
    public void testSerializeJobInfoFinished() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        jobInfoSerializer.serialize(buildJobInfo(JobStatusEnum.FINISHED, "ProductFamily=FAS, UsagesCount=100"),
            jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(TestUtils.fileToString(this.getClass(), "job_status_finished.json").trim(),
            stringWriter.toString());
    }

    @Test
    public void testSerializeJobInfoSkipped() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        jobInfoSerializer.serialize(buildJobInfo(JobStatusEnum.SKIPPED, "Reason=There are no usages"), jsonGenerator,
            new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(TestUtils.fileToString(this.getClass(), "job_status_skipped.json").trim(),
            stringWriter.toString());
    }

    @Test
    public void testSerializeJobInfoFailed() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        jobInfoSerializer.serialize(buildJobInfo(JobStatusEnum.FAILED, "ErrorMessage=NullPointerException: , " +
                "ErrorStackTrace=[java.lang.NullPointerException, " +
                "at java.util.Objects.requireNonNull(Objects.java:23), " +
                "at com.copyright.rup.dist.modeling.feeder.impl.quartz.UnpaidLiabilitiesJob.executeInternal" +
                "(UnpaidLiabilitiesJob.java:32)"), jsonGenerator,
            new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(TestUtils.fileToString(this.getClass(), "job_status_failed.json").trim(), stringWriter.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testSerializeEmptyJobInfo() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(stringWriter);
        jobInfoSerializer.serialize(new JobInfo(), jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
    }

    private JobInfo buildJobInfo(JobStatusEnum statusEnum, String reason) {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setName("df.service.rhEligibilityQuartzJob");
        jobInfo.setStatus(statusEnum);
        jobInfo.setResult(reason);
        jobInfo.setExecutionDatetime(OffsetDateTime.parse("2019-05-05T10:30:25-04:00"));
        return jobInfo;
    }
}
