package com.scy.base.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//常用工具方法类和正则匹配

public class Utility {
	public static final String REGEXEMAIL = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	public static final String REGEXMOBILE = "^[1][3-9][0-9]{9}$";
	public static final String REGEXNAME = "^[\\u4e00-\\u9fa5A-Za-z]{2,30}$";
	public static final String REGEXCHINESECHARACTER = "[\u4e00-\u9fa5]";
	public static final String REGEXIDCARD = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
	
	public static final String[] FILESIZEARRY = { "B", "KB", "MB", "GB" };
	
	public static boolean isEmailAddr(String email) {
		if (email == null)
			return false;
		return email.matches(REGEXEMAIL);
	}
	
	public static boolean isMobile(String mobile) {
		if (mobile == null)
			return false;
		return mobile.matches(REGEXMOBILE);
	}
	
	public static boolean isUrl(String url) {
		try {
			new URL(url);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}
	}
	
	public static boolean isName(String name) {
		if (name == null) {
			return false;
		}
		return name.matches(REGEXNAME);
	}
	
	public static boolean isContainChineseCharacter(String text) {
		if (text == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(REGEXCHINESECHARACTER);
		Matcher matcher = pattern.matcher(text);
		return matcher.find();
	}
	
	public static String getFileExtensionWithDot(String srcFile) {
		String extension = "";
		
		int i = srcFile.lastIndexOf('.');
		if (i > 0) {
			extension = srcFile.substring(i);
		}
		return extension;
	}
	
	public static String getFileName(String srcFile) {
		String fileName = "";
		
		int i = srcFile.lastIndexOf('.');
		if (i > 0) {
			fileName = srcFile.substring(0, i);
		}
		return fileName;
	}
	
	public static boolean isEmptyString(String src) {
		if (src != null && !src.trim().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public static final String DEFAULT_SPLITER = "<spliter/>";
	
	public static String joinString(List<?> objects, String delimiter) {
		if (objects == null || objects.isEmpty() || delimiter == null) {
			return null;
		}
		
		StringBuilder strBlder = new StringBuilder();
		for (int i = 0; i < objects.size(); i++) {
			strBlder.append(String.valueOf(objects.get(i)));
			
			if (i < objects.size() - 1) {
				strBlder.append(delimiter);
			}
		}
		return strBlder.toString();
	}
	
	public static String joinString(int[] objects, String delimiter) {
		if (objects == null || objects.length == 0 || delimiter == null) {
			return null;
		}
		
		StringBuilder strBlder = new StringBuilder();
		for (int i = 0; i < objects.length; i++) {
			strBlder.append(String.valueOf(objects[i]));
			
			if (i < objects.length - 1) {
				strBlder.append(delimiter);
			}
		}
		return strBlder.toString();
	}
	
	public static String joinString(Object[] objects, String delimiter) {
		if (objects == null || objects.length == 0 || delimiter == null) {
			return null;
		}
		
		StringBuilder strBlder = new StringBuilder();
		for (int i = 0; i < objects.length; i++) {
			strBlder.append(String.valueOf(objects[i]));
			
			if (i < objects.length - 1) {
				strBlder.append(delimiter);
			}
		}
		return strBlder.toString();
	}
	
	public static String[] splitString(String toSplit, String delimiter) {
		if (toSplit == null || delimiter == null) {
			return null;
		}
		
		return toSplit.split(delimiter);
	}
	
	/**
	 * 产生随机的三位数
	 * 
	 * @return
	 */
	public static String getThree() {
		return getRandomNum(3);
	}
	
	public static String getRandomNum(int len) {
		if (len < 1)
			return "0";
		return String.valueOf(java.util.concurrent.ThreadLocalRandom.current().nextLong(Math.round(Math.pow(10, len - 1)), Math.round(Math.pow(10, len))));
	}
	
	public static boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static String getHostName(String url) {
		String hostName = "";
		URI uri;
		try {
			uri = new URI(url);
			hostName = uri.getHost();
			if (!isEmptyString(hostName)) {
				hostName = hostName.startsWith("www.") ? hostName.substring(4) : hostName;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return hostName;
	}
	
	public static String getHostUrl(String url) {
		String hostUrl = "";
		URI uri;
		try {
			uri = new URI(url);
			hostUrl = uri.getScheme() + "://" + uri.getHost();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return hostUrl;
	}
	
	public static String getPathFromUrl(String url) {
		String path = null;
		
		if (isEmptyString(url) || !isUrl(url)) {
			return path;
		}
		
		try {
			path = new URL(url).getPath();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return path;
	}
	
	public static String formatId(Integer id) {
		if (id == null) {
			return null;
		}
		return String.format("%06d", id);
	}
	
	public static String getFriendlyFileSize(Long size) {
		if (size == null) {
			return null;
		}
		
		int sizeLevel = 0;
		float tempSize = size;
		while (tempSize > 1024) {
			tempSize = tempSize / 1024;
			sizeLevel++;
		}
		
		return String.format("%.2f ", tempSize) + FILESIZEARRY[sizeLevel];
	}
	
	// 随机生成字符串
	public static String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder();
		Random randCase = new Random();
		Random randData = new Random();
		int data = 0;
		for (int i = 0; i < length; i++) {
			int index = randCase.nextInt(3);
			switch (index) {
				case 0:
					data = randData.nextInt(10);// 仅仅会生成0~9
					sb.append(data);
					break;
				case 1:
					data = randData.nextInt(26) + 65;// 保证只会产生65~90之间的整数
					sb.append((char) data);
					break;
				case 2:
					data = randData.nextInt(26) + 97;// 保证只会产生97~122之间的整数
					sb.append((char) data);
					break;
			}
		}
		return sb.toString();
	}
	
	public static boolean isIdCardValid(String idCard) {
		if (idCard == null)
			return false;
		return idCard.matches(REGEXIDCARD);
	}
	
	public static boolean matchPattern(String sourceString, List<Pattern> patterns) {
		if (CollectionUtils.isNullOrEmpty(patterns) || sourceString == null)
			return false;
		for (Pattern pattern : patterns) {
			Matcher mather = pattern.matcher(sourceString);
			if (mather.matches()) {
				return true;
			}
		}
		return false;
	}
	
}
