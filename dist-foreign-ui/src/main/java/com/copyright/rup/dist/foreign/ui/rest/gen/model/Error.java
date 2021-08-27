package com.copyright.rup.dist.foreign.ui.rest.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Error
 **/

/**
 * Error
 */
@ApiModel(description = "Error")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Error   {
    @JsonProperty("error")
    




    private String error = null;

    @JsonProperty("message")
    




    private String message = null;

    @JsonProperty("stackTrace")
    




    private String stackTrace = null;

    public Error error(String error) {
        this.error = error;
        return this;
    }

     /**
     * Get error
     * @return error
    **/
    @ApiModelProperty(example = "ERROR_CODE", value = "")
    public String getError() {
        return error;
    }

    public void setError(String error) {
        if(error == null) {
            this.error = null;
        } else {
            this.error = error;
        }
    }

    public Error message(String message) {
        this.message = message;
        return this;
    }

     /**
     * Get message
     * @return message
    **/
    @ApiModelProperty(example = "Field value is not valid. Field&#x3D;name, Value&#x3D;null, Reason&#x3D;must not be null", value = "")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if(message == null) {
            this.message = null;
        } else {
            this.message = message;
        }
    }

    public Error stackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }

     /**
     * Get stackTrace
     * @return stackTrace
    **/
    @ApiModelProperty(example = "java.lang.NullPointerException\\n\\tat java.lang.Thread.run(Thread.java:748)", value = "")
    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        if(stackTrace == null) {
            this.stackTrace = null;
        } else {
            this.stackTrace = stackTrace;
        }
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
        
        sb.append("        error: ").append(toIndentedString(error)).append("\n");
        sb.append("        message: ").append(toIndentedString(message)).append("\n");
        sb.append("        stackTrace: ").append(toIndentedString(stackTrace)).append("\n");
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
        return o.toString().replace("\n", "\n        ");
    }
}

