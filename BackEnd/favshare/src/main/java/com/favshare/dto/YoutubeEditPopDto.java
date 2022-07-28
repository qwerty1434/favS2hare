package com.favshare.dto;

import java.time.LocalDateTime;

import com.favshare.entity.FeedEntity;
import com.favshare.entity.PopEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YoutubeEditPopDto {
	//userId, youtubeId, startSecond, endSecond, bgm(심화과정), title, content,feedId
	
	private int userId;
	private int youtubeId;
	private int feedId;
	private String name;
	private int startSecond;
	private int endSecond; 
	private String content;
	private LocalDateTime createDate;
	private int views;
	
	public YoutubeEditPopDto(PopEntity popEntity, FeedEntity feedEntity) {
		this.userId = popEntity.getUserEntity().getId();
		this.youtubeId = popEntity.getYoutubeEntity().getId();
		this.feedId = feedEntity.getId();
		this.name = popEntity.getName();
		this.startSecond = popEntity.getStartSecond();
		this.endSecond = popEntity.getEndSecond();
		this.content = popEntity.getContent();
		this.createDate = LocalDateTime.now();
		this.views = 0;
	}
}
