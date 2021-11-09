package com.copyright.rup.dist.foreign.ui.rest.gen.api;

import com.copyright.rup.dist.foreign.ui.rest.gen.model.BatchStats;
import com.copyright.rup.dist.foreign.ui.rest.gen.model.Error;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A delegate to be called by the {@link StatisticBatchApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */

public interface StatisticBatchApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * @see StatisticBatchApi#getBatchesStatistic
     */
    default ResponseEntity<BatchStats> getBatchesStatistic(String name,
        String date,
        String dateFrom,
        String dateTo) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"date\" : \"date\",  \"statistic\" : [ {    \"batchName\" : \"batchName\",    \"ntsWithdrawnPercent\" : 1.0246457001441578,    \"worksNotFoundCount\" : 2,    \"rhNotFoundCount\" : 1,    \"totalCount\" : 0,    \"rhNotFoundAmount\" : 6.84685269835264,    \"matchedAmount\" : 5.962133916683182,    \"matchedPercent\" : 5.637376656633329,    \"sendForRaCount\" : 8,    \"worksNotFoundPercent\" : 9.301444243932576,    \"rhFoundPercent\" : 5.025004791520295,    \"eligiblePercent\" : 6.683562403749608,    \"eligibleCount\" : 9,    \"paidCount\" : 3,    \"ntsWithdrawnAmount\" : 1.2315135367772556,    \"ntsWithdrawnCount\" : 7,    \"rhFoundAmount\" : 4.965218492984954,    \"eligibleAmount\" : 9.369310271410669,    \"rhFoundCount\" : 1,    \"matchedCount\" : 1,    \"multipleMatchingAmount\" : 2.027123023002322,    \"multipleMatchingPercent\" : 4.145608029883936,    \"totalAmount\" : 6.027456183070403,    \"sendForRaAmount\" : 9.018348186070783,    \"worksNotFoundAmount\" : 7.061401241503109,    \"paidPercent\" : 1.284659006116532,    \"rhNotFoundPercent\" : 7.457744773683766,    \"sendForRaPercent\" : 6.438423552598547,    \"multipleMatchingCount\" : 3,    \"paidAmount\" : 6.965117697638846  }, {    \"batchName\" : \"batchName\",    \"ntsWithdrawnPercent\" : 1.0246457001441578,    \"worksNotFoundCount\" : 2,    \"rhNotFoundCount\" : 1,    \"totalCount\" : 0,    \"rhNotFoundAmount\" : 6.84685269835264,    \"matchedAmount\" : 5.962133916683182,    \"matchedPercent\" : 5.637376656633329,    \"sendForRaCount\" : 8,    \"worksNotFoundPercent\" : 9.301444243932576,    \"rhFoundPercent\" : 5.025004791520295,    \"eligiblePercent\" : 6.683562403749608,    \"eligibleCount\" : 9,    \"paidCount\" : 3,    \"ntsWithdrawnAmount\" : 1.2315135367772556,    \"ntsWithdrawnCount\" : 7,    \"rhFoundAmount\" : 4.965218492984954,    \"eligibleAmount\" : 9.369310271410669,    \"rhFoundCount\" : 1,    \"matchedCount\" : 1,    \"multipleMatchingAmount\" : 2.027123023002322,    \"multipleMatchingPercent\" : 4.145608029883936,    \"totalAmount\" : 6.027456183070403,    \"sendForRaAmount\" : 9.018348186070783,    \"worksNotFoundAmount\" : 7.061401241503109,    \"paidPercent\" : 1.284659006116532,    \"rhNotFoundPercent\" : 7.457744773683766,    \"sendForRaPercent\" : 6.438423552598547,    \"multipleMatchingCount\" : 3,    \"paidAmount\" : 6.965117697638846  } ],  \"dateTo\" : \"dateTo\",  \"dateFrom\" : \"dateFrom\"}");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
