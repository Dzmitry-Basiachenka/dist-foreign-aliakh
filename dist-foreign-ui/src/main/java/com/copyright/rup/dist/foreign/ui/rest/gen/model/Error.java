package com.copyright.rup.dist.foreign.ui.rest.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Error
 */
@ApiModel(description = "Error")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Error   {
  @JsonProperty("error")
  private String error;

  @JsonProperty("message")
  private String message;

  @JsonProperty("stackTrace")
  private String stackTrace;

  public Error error(String error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   * @return error
  */
  @ApiModelProperty(example = "ERROR_CODE", value = "")


  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public Error message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  */
  @ApiModelProperty(example = "Field value is not valid. Field=name, Value=null, Reason=must not be null", value = "")


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Error stackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
    return this;
  }

  /**
   * Get stackTrace
   * @return stackTrace
  */
  @ApiModelProperty(example = "java.lang.NullPointerException\\n\\tat java.lang.Thread.run(Thread.java:748)", value = "")


  public String getStackTrace() {
    return stackTrace;
  }

  public void setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error error = (Error) o;
    return Objects.equals(this.error, error.error) &&
        Objects.equals(this.message, error.message) &&
        Objects.equals(this.stackTrace, error.stackTrace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, message, stackTrace);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    stackTrace: ").append(toIndentedString(stackTrace)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

