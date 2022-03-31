package com.app.seachallenge.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

	public static LocalDate parseDateFromString(String date, String pattern) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
			return LocalDate.parse(date, dtf);
		} catch(Exception e) {
			Utils.log.error("Error parse data", e);
		}
		return null;
	}
	
	public static byte[] fromStringToByte(String image) {
		try {
			if(image != null && image.length() > 0) {
				return Base64.getDecoder().decode(image);
			}
        } catch (Exception e) {
        	Utils.log.error("Error get image bytes", e);
        }
		return null;
	}
	
	public static String fromByteToString(byte[] image) {
		try {
			if(image != null && image.length > 0) {
				return Base64.getEncoder().encodeToString(image);
			}
        } catch (Exception e) {
        	Utils.log.error("Error get image string", e);
        }
		return null;
	}
}
