package com.copyright.rup.dist.foreign.ui.rest.gen.api;

import com.copyright.rup.dist.foreign.ui.rest.gen.model.Error;
import com.copyright.rup.dist.foreign.ui.rest.gen.model.UsageStat;
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
 * A delegate to be called by the {@link StatisticUsageApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
public interface StatisticUsageApiDelegate {
    /**
     * @see StatisticUsageApi#getUsageStatistic
     */
    ResponseEntity<UsageStat> getUsageStatistic(String usageId);

}
