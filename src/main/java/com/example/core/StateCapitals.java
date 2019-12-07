package com.example.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StateCapitals {
    @NotNull
    private String state;
    @NotNull
    private String capital;
    @NotNull
    private String largestCity;

    @Override
    public String toString(){
        return MoreObjects.toStringHelper(this)
                .add("state", state)
                .add("capital", capital)
                .add("largestCity", largestCity)
                .toString();
    }
}
