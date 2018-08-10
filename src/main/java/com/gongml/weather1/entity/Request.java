package com.gongml.weather1.entity;

import lombok.Data;

import java.nio.charset.Charset;
import java.util.Map;

@Data
public class Request {
    private String uri;
    private String requestProtocol;
    private Map<String, String> params;
    private Map<String, String> headers;
    private Charset charset;
}
