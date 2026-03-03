package com.framar.site.model;

import java.util.List;

public class Project {
	private final String slug;
	private final String coverImage;
	private final String title;
	private final String location;
	private final String year;
	private final String type;
	private final String description;
	private final List<MediaItem> media; //putanje do slika u /static
	
	public Project(String slug,String coverImage, String title, String location, String year, String type, String description, List<MediaItem> media) {
		this.slug = slug;
		this.coverImage = coverImage;
		this.title = title;
		this.location = location;
		this.year = year;
		this.type = type;
		this.description = description;
		this.media = media;
	}
	
	public String getSlug() { return slug;}
	public String getCoverImage() {return coverImage; }
	public String getTitle() { return title;}
	public String getLocation() { return location;}
	public String getYear() { return year;}
	public String getType() { return type;}
	public String getDescription() { return description;}
	public List<MediaItem> getMedia()  { return media;}

}
