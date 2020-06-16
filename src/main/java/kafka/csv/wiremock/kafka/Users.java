package kafka.csv.wiremock.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import gherkin.deps.com.google.gson.annotations.SerializedName;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "Users_Collection")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Users {
    @SerializedName("size")
    private Long size = null;

    @SerializedName("offset")
    private Long offset = null;

    @SerializedName("limit")
    private Long limit = null;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @SerializedName("results")
    private List<User> results;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public List<User> getResults() {
        return results;
    }

    public void setResults(List<User> results) {
        this.results = results;
    }

}

