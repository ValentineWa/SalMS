package com.salms.salms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Jacksonized
public class SalmsApiResponse <T extends GenericApiResponseContent> {
    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("trace")
    private List<String> trace;

    private T data;
}
