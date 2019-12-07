package com.example.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryStateCapitals {
    @NotNull
    private String country;

    @Valid
    private List<StateCapitals> stateCapitals;

    @NotNull
    @ApiModelProperty(notes = "URL to show more information about the country")
    private String moreInformation;

    @Override
    public String toString(){
        return MoreObjects.toStringHelper(this)
                .add("country", country)
                .add("stateCapitals", CollectionUtils.isEmpty(stateCapitals) ? stateCapitals :
                        stateCapitals.stream().filter(Objects::nonNull).map(Objects::toString).collect(Collectors.joining(",")))
                .add("moreInformation", moreInformation)
                .toString();
    }
}
