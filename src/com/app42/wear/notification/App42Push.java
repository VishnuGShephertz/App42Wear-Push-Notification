package com.app42.wear.notification;

import java.util.ArrayList;
/**
 * 
 * @author Vishnu Garg
 * @Shephertz
 * 
 */
public class App42Push {

	public PushCode getCode() {
		return code;
	}

	public App42Push(String message,String title,PushCode pushCode){
		this.code=pushCode;
		this.title=title;
		this.message=message;
	}
	public void setCode(PushCode code) {
		this.code = code;
	}

	public String getBigText() {
		return bigText;
	}

	public void setBigText(String bigText) {
		this.bigText = bigText;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setPageNotifications(ArrayList<PageNotification> pageNotifications) {
		this.pageNotifications = pageNotifications;
	}


	private PushCode code;
	private String title;
	private String message;
	private String bigText;
	private String image;
	private ArrayList<PageNotification> pageNotifications;

	public ArrayList<PageNotification> getPageNotifications() {
		return pageNotifications;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public String getbigText() {
		return bigText;
	}

	public String getImage() {
		return image;
	}
	public PageNotification createPageNotification(String pageTitle, String pageSummary,
			String pageContent){
		return new PageNotification(pageTitle, pageSummary, pageContent);
	}

	public class PageNotification {
		private String pageTitle;
		private String pageSummary;
		private String pageContent;

		public PageNotification(String pageTitle, String pageSummary,
				String pageContent) {
			super();
			this.pageTitle = pageTitle;
			this.pageSummary = pageSummary;
			this.pageContent = pageContent;
		}

		public String getPageTitle() {
			return pageTitle;
		}

		public String getpageSummary() {
			return pageSummary;
		}

		public String getPageContent() {
			return pageContent;
		}

	}

}
