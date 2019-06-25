package org.play.search.impl.feign;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.play.search.impl.entity.Search;
import org.play.search.impl.service.SearchService;
import org.play.search.impl.tools.SearchPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchFeign {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search/save")
	public void Save(@RequestBody Search search){
		searchService.saveIndex(search);
	}
	
	@RequestMapping("/search/update")
	public void update(@RequestBody Search search){
		searchService.updateIndex(search);
	}
	
	@RequestMapping("/search/delete")
	public void delete(@RequestParam String id){
		searchService.removeIndex(id);
	}
	/**
	 * 清空索引
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/removeAll")
	public void removeAll()
	{
		searchService.removeAll();
	}
	/**
	 * 刷新全量索引
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/refresh")
	public String refresh()
	{
		return "hello word";
	}
	/**
	 * 单域搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchLiveSingle")
	public SearchPageBean<Search> searchLiveSingle(@RequestParam String field,@RequestParam String sortField,@RequestParam String search,@RequestParam int curPage,@RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchLiveSingle(field, sort, search, curPage, pageSize);
	}
	/**
	 * 多域搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchLiveMultiple")
	public SearchPageBean<Search> searchLiveMultiple(@RequestParam String sortField,@RequestParam String search,@RequestParam int curPage,@RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchLiveMultiple(sort, search, curPage, pageSize);
	}
	/**
	 * 类型搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchLiveCate")
	public SearchPageBean<Search> searchLiveCate(@RequestParam String sortField,@RequestParam String search,@RequestParam int curPage,@RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchLiveCate(sort, search, curPage, pageSize);
	}
	/**
	 * 时间范围搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchLiveRange")
	public SearchPageBean<Search> searchLiveRange(@RequestParam String sortField,@RequestParam Long startTime,@RequestParam Long endTime, @RequestParam int curPage, @RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchLiveRange(sort, startTime, endTime, curPage, pageSize);
	}
	
	
	/**
	 * 单域搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchVideoSingle")
	public SearchPageBean<Search> searchVideoSingle(@RequestParam String field, @RequestParam String sortField,@RequestParam String search, @RequestParam int curPage, @RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchVideoSingle(field, sort, search, curPage, pageSize);
	}
	/**
	 * 多域搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchVideoMultiple")
	public SearchPageBean<Search> searchVideoMultiple(@RequestParam String sortField,@RequestParam String search, @RequestParam int curPage, @RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchVideoMultiple(sort, search, curPage, pageSize);
	}
	/**
	 * 类型搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchVideoCate")
	public SearchPageBean<Search> searchVideoCate(@RequestParam String sortField,@RequestParam String search, @RequestParam int curPage, @RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchVideoCate(sort, search, curPage, pageSize);
	}
	/**
	 * 类型搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchVideoAll")
	public SearchPageBean<Search> searchVideoAll(@RequestParam String sortField, @RequestParam int curPage, @RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchVideoAll(sort, curPage, pageSize);
	}
	/**
	 * 类型搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchLiveAll")
	public SearchPageBean<Search> searchLiveAll(@RequestParam String sortField, @RequestParam int curPage, @RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchLiveAll(sort, curPage, pageSize);
	}
	/**
	 * 时间范围搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchVideoRange")
	public SearchPageBean<Search> searchVideoRange(@RequestParam String sortField,@RequestParam Long startTime,@RequestParam Long endTime, @RequestParam int curPage, @RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchVideoRange(sort, startTime, endTime, curPage, pageSize);
	}
	@RequestMapping("/search/searchSingle")
	public SearchPageBean<Search> searchSingle(@RequestParam String field,@RequestParam  String sortField,@RequestParam String search, @RequestParam int curPage, @RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchSingle(field, sort, search, curPage, pageSize);
	}
	/**
	 * 多域搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchMultiple")
	public SearchPageBean<Search> searchMultiple(@RequestParam String sortField,@RequestParam String search, @RequestParam int curPage, @RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchMultiple(sort, search, curPage, pageSize);
	}
	/**
	 * 类型搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchCate")
	public SearchPageBean<Search> searchCate(@RequestParam String sortField,@RequestParam String search, @RequestParam int curPage, @RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchCate(sort, search, curPage, pageSize);
	}
	/**
	 * 时间范围搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchRange")
	public SearchPageBean<Search> searchRange(@RequestParam String sortField,@RequestParam Long startTime,@RequestParam Long endTime, @RequestParam int curPage, @RequestParam int pageSize)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchRange(sort, startTime, endTime, curPage, pageSize);
	}
	/**
	 * 时间范围搜索
	 * @param searchDoc
	 * @param model
	 */
	@RequestMapping("/search/searchConform")
	public List<Search> searchConform(@RequestParam String sortField,@RequestParam String search)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchConform(sort, search);
	}
	@RequestMapping("/search/searchGroup")
	public List<Search> searchGroup(@RequestParam String sortField,@RequestParam String type,@RequestParam Integer indexCount)
	{
		SortField sort=new SortField(sortField, Type.LONG, true);
		return searchService.searchGroup(sort, type, indexCount);
	}
}
