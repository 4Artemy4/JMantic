package org.jmantic.scmemory.websocketmemory.sync;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jmantic.scmemory.websocketmemory.message.request.GetLinkContentRequest;
import org.jmantic.scmemory.websocketmemory.message.request.RequestType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Michael
 * @since 0.0.1
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class GetLinkContentRequestImpl extends AbstractScRequest implements GetLinkContentRequest {
    @JsonProperty("payload")
    private List<GetContentStruct> contentStructs;

    private static class GetContentStruct {
        @JsonProperty("command")
        String command = "get";
        @JsonProperty("addr")
        long address;

        public GetContentStruct(long address) {
            this.address = address;
        }

        @JsonIgnore
        @Override
        public String toString() {
            return "GetContentStruct{" +
                    "command='" + command + '\'' +
                    ", address=" + address +
                    '}';
        }
    }

    public GetLinkContentRequestImpl() {
        super(1, RequestType.CONTENT);
        contentStructs = new ArrayList<>();
    }

    @JsonIgnore
    @Override
    public boolean addToRequest(List<Long> addresses) {
        List<GetContentStruct> structs = addresses.stream().map(GetContentStruct::new).collect(Collectors.toList());
        return contentStructs.addAll(structs);
    }

    @JsonIgnore
    @Override
    public boolean addAddressToRequest(long address) {
        GetContentStruct struct = new GetContentStruct(address);
        return contentStructs.add(struct);
    }

    @JsonIgnore
    @Override
    public void resetRequest() {
        contentStructs.clear();
    }

    @JsonIgnore
    @Override
    public boolean isEmpty() {
        return contentStructs.isEmpty();
    }

    @JsonIgnore
    @Override
    public String toString() {
        return "GetLinkContentRequestImpl{" +
                "requestId=" + getRequestId() +
                ", requestType=" + getRequestType() +
                ", contentStructs=" + contentStructs +
                '}';
    }
}
