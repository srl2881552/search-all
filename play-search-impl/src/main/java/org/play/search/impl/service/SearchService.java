package org.play.search.impl.service;

import java.util.List;

import org.apache.lucene.search.SortField;
import org.play.search.impl.entity.Search;
import org.play.search.impl.tools.SearchPageBean;

public interface SearchService {

	/**
	 * 保存索引
	 * @param searchDoc
	 * @param model
	 */
	public void saveIndex(Search model);
	/**
	 * 更新索引
	 * @param searchDoc
	 * @param model
	 */
	public void updateIndex(Search model);
	/**
	 * 删除索引
	 * @param searchDoc
	 * @param model
	 */
	public void removeIndex(String id);
	/**
	 * 清空索引
	 * @param searchDoc
	 * @param model
	 */
	public void removeAll();
	/**
	 * 刷新全量索引
	 * @param searchDoc
	 * @param model
	 */
	public void refresh(List<Search> searchs);
	/**
	 * 单域搜索
	 * @param searchDoc
	 * @param model
	 */
	public SearchPageBean<Search> searchLiveSingle(String field, SortField sortField,String search, int curPage, int pageSize);
	/**
	 * 多域搜索
	 * @param searchDoc
	 * @param model
	 */
	public SearchPageBean<Search> searchLiveMultiple(SortField sortField,String search, int curPage, int pageSize);
	/**
	 * 类型搜索
	 * @param searchDoc
	 * @param model
	 */
	public SearchPageBean<Search> searchLiveCate(SortField sortField,String search, int curPage, int pageSize);
	/**
	 * 时间范围搜索
	 * @param searchDoc
	 * @param model
	 */
	public SearchPageBean<Search> searchLiveRange(SortField sortField,Long startTime,Long endTime, int curPage, int pageSize);
	
	
	/**
	 * 单域搜索
	 * @param searchDoc
	 * @param model
	 */
	public SearchPageBean<Search> searchVideoSingle(String field, SortField sortField,String search, int curPage, int pageSize);
	/**
	 * 多域搜索
	 * @param searchDoc
	 * @param model
	 */
	public SearchPageBean<Search> searchVideoMultiple(SortField sortField,String search, int curPage, int pageSize);
	/**
	 * 类型搜索
	 * @param searchDoc
	 * @param model
	 */
	public SearchPageBean<Search> searchVideoCate(SortField sortField,String search, int curPage, int pageSize);
	/**
	 * 类型搜索
	 * @param searchDoc
	 * @param model
	 */
	public SearchPageBean<Search> searchVideoAll(SortField sortField, int curPage, int pageSize);
	/**
	 * 类型搜索
	 * @param searchDoc
	 * @param model
	 */
	public SearchPageBean<Search> searchLiveAll(SortField sortField, int curPage, int pageSize);
	/**
	 * 时间范围搜索
	 * @param searchDoc
	 * @param model
	 */
	public SearchPageBean<Search> searchVideoRange(SortField sortField,Long startTime,Long endTime, int curPage, int pageSize);
	
	public SearchPageBean<Search> searchSingle(String field, SortField sortField,String search, int curPage, int pageSize);
	/**
	 * 多域搜索
	 * @param searchDoc
	 * @param model
	 */
	public SearchPageBean<Search> searchMultiple(SortField sortField,String search, int curPage, int pageSize);
	/**
	 * 类型搜索
	 * @param searchDoc
	 * @param model
	 */
	public SearchPageBean<Search> searchCate(SortField sortField,String search, int curPage, int pageSize);
	/**
	 * 时间范围搜索
	 * @param searchDoc
	 * @param model
	 */
	public SearchPageBean<Search> searchRange(SortField sortField,Long startTime,Long endTime, int curPage, int pageSize);
	/**
	 * 时间范围搜索
	 * @param searchDoc
	 * @param model
	 */
	public List<Search> searchConform(SortField sortField,String search);
	
	public List<Search> searchGroup(SortField sortField,String type,Integer indexCount);
	
}
