package com.copyright.rup.dist.foreign.ui.rest.gen.api;

import com.copyright.rup.dist.foreign.ui.rest.gen.model.BatchStatistic;
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
* A delegate to be called by the {@link StatisticApiController}}.
* Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
*/
@Validated
public interface StatisticApiDelegate {

    /**
    * @see StatisticApi#getBatchStatistic
    */
    ResponseEntity<BatchStatistic
> getBatchStatistic(@NotNull 


String name,
    @Pattern(regexp="^\\d{2}/\\d{2}/\\d{4}$") 


String date);

}
