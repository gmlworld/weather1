package com.gongml.weather1.entity;

import lombok.Data;

@Data
public class FileByte {
	private String contentDisposition;
	private String contentType;
	private byte[] content;
}
