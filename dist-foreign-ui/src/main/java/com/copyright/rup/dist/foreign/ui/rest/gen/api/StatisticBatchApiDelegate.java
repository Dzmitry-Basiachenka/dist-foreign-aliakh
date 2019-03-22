package com.copyright.rup.dist.foreign.ui.rest.gen.api;

import com.copyright.rup.dist.foreign.ui.rest.gen.model.BatchStats;
import com.copyright.rup.dist.foreign.ui.rest.gen.model.Error;
    import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
    import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

    import javax.validation.Valid;
    import javax.validation.constraints.*;

    import java.io.IOException;

    import javax.servlet.http.HttpServletRequest;
import java.util.List;
    import java.util.Optional;

/**
* A delegate to be called by the {@link StatisticBatchApiController}}.
* Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
*/
@Validated
public interface StatisticBatchApiDelegate {

    /**
    * @see StatisticBatchApi#getBatchesStatistic
    */
    ResponseEntity<BatchStats
> getBatchesStatistic(


String name,
    @Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$") 


String date,
    @Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$") 


String dateFrom,
    @Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$") 


String dateTo);

}
