package com.copyright.rup.dist.foreign.service.impl.chain;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utils class to operate with Job Info.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/25/2021
 *
 * @author Ihar Suvorau
 */
public final class JobInfoUtils {

    private JobInfoUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Merges list of {@link JobInfo}s to single {@link JobInfo}.
     *
     * @param jobInfos list of {@link JobInfo}s
     * @return job info result
     */
    public static JobInfo mergeJobResults(List<JobInfo> jobInfos) {
        JobStatusEnum status = jobInfos.stream().anyMatch(jobInfo -> JobStatusEnum.FINISHED == jobInfo.getStatus())
            ? JobStatusEnum.FINISHED
            : JobStatusEnum.SKIPPED;
        String result = jobInfos.stream().map(JobInfo::getResult).collect(Collectors.joining("; "));
        return new JobInfo(status, result);
    }
}
