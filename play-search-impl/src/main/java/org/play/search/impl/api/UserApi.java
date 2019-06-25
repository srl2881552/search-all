package org.play.search.impl.api;

import org.play.search.impl.entity.Users;
import org.play.search.impl.entity.VideoCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "play-user-api")
public interface UserApi {
	@RequestMapping(value = "/user/getUsersById",method = RequestMethod.POST)
	Users getUsersById(@RequestParam(value = "id", required = true) String id);
}
