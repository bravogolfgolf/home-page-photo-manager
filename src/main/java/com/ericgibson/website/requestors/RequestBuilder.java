package com.ericgibson.website.requestors;

import java.util.Map;

public interface RequestBuilder {
    Request create(String type, Map<String, Object> map);
}
