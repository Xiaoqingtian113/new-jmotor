package com.zhucq.mobile.appium;

import java.io.Serializable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsByXPath;

import io.appium.java_client.FindsByAccessibilityId;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.FindsByIosClassChain;
import io.appium.java_client.FindsByIosNSPredicate;
import io.appium.java_client.FindsByIosUIAutomation;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class JMotorBy extends By {

	public static By getBy(String locateType, String exp) {
		By by = null;
		switch (locateType) {
		case "id":
			by = id(exp);
			break;
		case "xpath":
			by = xpath(exp);
			break;
		case "name":
			by = name(exp);
			break;
		case "tagName":
			by = tagName(exp);
			break;
		case "partialLinkText":
			by = partialLinkText(exp);
			break;
		case "linkText":
			by = linkText(exp);
			break;
		case "className":
			by = className(exp);
			break;
		case "cssSelector":
			by = cssSelector(exp);
			break;
		case "accessibilityId":
			by = accessibilityId(exp);
			break;
		case "androidUiAutomator":
			by = androidUiAutomator(exp);
			break;
		case "iosClassChain":
			by = iosClassChain(exp);
			break;
		case "iosNSPredicate":
			by = iosNSPredicate(exp);
			break;
		case "iosUIAutomation":
			by = iosUIAutomation(exp);
			break;
		default:
			by = null;
			break;
		}
		return by;
	}

	/**
	 * @param accessibilityId
	 *            The value of the "accessibilityId" attribute to search for
	 * @return a By which locates elements by the value of the "accessibilityId"
	 *         attribute.
	 */
	public static By accessibilityId(final String accessibilityId) {
		if (accessibilityId == null)
			throw new IllegalArgumentException("Cannot find elements with a null accessibilityId attribute.");

		return new ByAccessibilityId(accessibilityId);
	}

	public static class ByAccessibilityId extends By implements Serializable {
		private static final long serialVersionUID = -2782746965735241756L;
		private final String accessibilityId;

		public ByAccessibilityId(String accessibilityId) {
			this.accessibilityId = accessibilityId;
		}
		
		@Override
		public List<WebElement> findElements(SearchContext context) {
			if (context instanceof FindsByAccessibilityId)
				return ((FindsByAccessibilityId) context).findElementsByAccessibilityId(accessibilityId);
			return ((FindsByXPath) context).findElementsByXPath(".//*[@content-desc = '" + accessibilityId + "']");
		}

		@Override
		public WebElement findElement(SearchContext context) {
			if (context instanceof FindsByAccessibilityId)
				return ((FindsByAccessibilityId) context).findElementByAccessibilityId(accessibilityId);
			return ((FindsByXPath) context).findElementByXPath(".//*[@content-desc = '" + accessibilityId + "']");
		}

		@Override
		public String toString() {
			return "By.accessibilityId: " + accessibilityId;
		}
	}
	
	/**
	 * @param androidUiAutomator
	 *            The value of the "androidUiAutomator" attribute to search for
	 * @return a By which locates elements by the value of the
	 *         "androidUiAutomator" attribute.
	 */
	public static By androidUiAutomator(final String androidUiAutomator) {
		if (androidUiAutomator == null)
			throw new IllegalArgumentException("Cannot find elements with a null androidUiAutomator attribute.");

		return new ByAndroidUiAutomator(androidUiAutomator);
	}

	public static class ByAndroidUiAutomator extends By implements Serializable {
		private static final long serialVersionUID = 2506983403746696934L;
		private final String androidUiAutomator;

		public ByAndroidUiAutomator(String androidUiAutomator) {
			this.androidUiAutomator = androidUiAutomator;
		}

		@Override
		public List<WebElement> findElements(SearchContext context) {
			return ((FindsByAndroidUIAutomator) context).findElementsByAndroidUIAutomator(androidUiAutomator);
		}

		@Override
		public WebElement findElement(SearchContext context) {
			return ((FindsByAndroidUIAutomator) context).findElementByAndroidUIAutomator(androidUiAutomator);
		}

		@Override
		public String toString() {
			return "By.androidUiAutomator: " + androidUiAutomator;
		}
	}
	
	/**
	 * @param iosClassChain
	 *            The value of the "iosClassChain" attribute to search for
	 * @return a By which locates elements by the value of the
	 *         "iosClassChain" attribute.
	 */
	public static By iosClassChain(final String iosClassChain) {
		if (iosClassChain == null)
			throw new IllegalArgumentException("Cannot find elements with a null androidUiAutomator attribute.");

		return new ByIosClassChain(iosClassChain);
	}

	public static class ByIosClassChain extends By implements Serializable {
		private static final long serialVersionUID = 4615384599982056981L;
		private final String iosClassChain;

		public ByIosClassChain(String iosClassChain) {
			this.iosClassChain = iosClassChain;
		}

		@Override
		public List<WebElement> findElements(SearchContext context) {
			return ((FindsByIosClassChain) context).findElementsByIosClassChain(iosClassChain);
		}

		@Override
		public WebElement findElement(SearchContext context) {
			return ((FindsByIosClassChain) context).findElementByIosClassChain(iosClassChain);
		}

		@Override
		public String toString() {
			return "By.iosClassChain: " + iosClassChain;
		}
	}
	
	/**
	 * @param iosNSPredicate
	 *            The value of the "iosNSPredicate" attribute to search for
	 * @return a By which locates elements by the value of the
	 *         "iosNSPredicate" attribute.
	 */
	public static By iosNSPredicate(final String iosNSPredicate) {
		if (iosNSPredicate == null)
			throw new IllegalArgumentException("Cannot find elements with a null iosNSPredicate attribute.");

		return new ByIosNSPredicate(iosNSPredicate);
	}

	public static class ByIosNSPredicate extends By implements Serializable {
		private static final long serialVersionUID = 884792943242404733L;
		private final String iosNSPredicate;

		public ByIosNSPredicate(String iosNSPredicate) {
			this.iosNSPredicate = iosNSPredicate;
		}

		@Override
		public List<WebElement> findElements(SearchContext context) {
			return ((FindsByIosNSPredicate) context).findElementsByIosNsPredicate(iosNSPredicate);
		}

		@Override
		public WebElement findElement(SearchContext context) {
			return ((FindsByIosNSPredicate) context).findElementByIosNsPredicate(iosNSPredicate);
		}

		@Override
		public String toString() {
			return "By.iosNSPredicate: " + iosNSPredicate;
		}
	}
	
	/**
	 * @param iosUIAutomation
	 *            The value of the "iosUIAutomation" attribute to search for
	 * @return a By which locates elements by the value of the
	 *         "iosUIAutomation" attribute.
	 */
	public static By iosUIAutomation(final String iosUIAutomation) {
		if (iosUIAutomation == null)
			throw new IllegalArgumentException("Cannot find elements with a null iosUIAutomation attribute.");

		return new ByIosUIAutomation(iosUIAutomation);
	}

	public static class ByIosUIAutomation extends By implements Serializable {
		private static final long serialVersionUID = 1487471025468715257L;
		private final String iosUIAutomation;

		public ByIosUIAutomation(String iosUIAutomation) {
			this.iosUIAutomation = iosUIAutomation;
		}

		@Override
		public List<WebElement> findElements(SearchContext context) {
			return ((FindsByIosUIAutomation) context).findElementsByIosUIAutomation(iosUIAutomation);
		}

		@Override
		public WebElement findElement(SearchContext context) {
			return ((FindsByIosUIAutomation) context).findElementByIosUIAutomation(iosUIAutomation);
		}

		@Override
		public String toString() {
			return "By.iosUIAutomation: " + iosUIAutomation;
		}
	}
}
