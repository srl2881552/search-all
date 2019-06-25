package org.play.search.impl.api;

import java.util.List;

import org.play.search.impl.entity.Video;
import org.play.search.impl.entity.VideoCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "play-video-api")
public interface VideoApi {
	@RequestMapping(value = "/video/getVideoAll",method = RequestMethod.POST)
    List<Video> getVideoAll();
	@RequestMapping(value = "/videoCategory/getCateById",method = RequestMethod.POST)
	VideoCategory getCateById(@RequestParam(value = "id", required = true) String id);
	

}
