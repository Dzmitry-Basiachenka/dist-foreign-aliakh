package com.copyright.rup.dist.foreign.service.impl.chain;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;

import org.junit.Test;

import java.util.List;

/**
 * Verifies {@link JobInfoUtils}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/25/2021
 *
 * @author Ihar Suvorau
 */
public class JobInfoUtilsTest {

    @Test
    public void testMergeJobResultsSkipped() {
        JobInfo finishedInfo = new JobInfo(JobStatusEnum.SKIPPED, "ProductFamily=FAS, Reason=There are no usages");
        JobInfo skippedInfo = new JobInfo(JobStatusEnum.SKIPPED, "ProductFamily=FAS2, Reason=There are no usages");
        JobInfo resultInfo = JobInfoUtils.mergeJobResults(List.of(finishedInfo, skippedInfo));
        assertEquals(JobStatusEnum.SKIPPED, resultInfo.getStatus());
        assertEquals("ProductFamily=FAS, Reason=There are no usages; ProductFamily=FAS2, Reason=There are no usages",
            resultInfo.getResult());
    }

    @Test
    public void testMergeJobResultsFinished() {
        JobInfo finishedInfo = new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=FAS, UsagesCount=2");
        JobInfo skippedInfo = new JobInfo(JobStatusEnum.SKIPPED, "ProductFamily=FAS2, Reason=There are no usages");
        JobInfo resultInfo = JobInfoUtils.mergeJobResults(List.of(finishedInfo, skippedInfo));
        assertEquals(JobStatusEnum.FINISHED, resultInfo.getStatus());
        assertEquals("ProductFamily=FAS, UsagesCount=2; ProductFamily=FAS2, Reason=There are no usages",
            resultInfo.getResult());
    }
}
