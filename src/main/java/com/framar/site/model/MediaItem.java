package com.framar.site.model;

public class MediaItem {
	private final String type;		// image ili video
	private final String src;
	private final String poster;		//opcionalno (thumbnail za video
	
	public MediaItem(String type, String src, String poster) {
		this.type = type;
		this.src = src;
		 this.poster = poster;
	}
	
	public String getType() { return type; }
	public String getSrc() { return src; }
	public String getPoster() { return poster; }
	
	public static MediaItem image(String src) {
		return new MediaItem("image", src, null);
	}
	
	public static MediaItem video(String src) {
		return new MediaItem("video", src, null);
	}
	
	public static MediaItem video(String src, String poster) {
		return new MediaItem("video", src, poster);
	}
}
