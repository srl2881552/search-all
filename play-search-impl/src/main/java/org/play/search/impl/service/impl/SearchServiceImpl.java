package org.play.search.impl.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.GroupingSearch;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopFieldCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.play.search.impl.entity.Search;
import org.play.search.impl.service.SearchService;
import org.play.search.impl.tools.SearchPageBean;
import org.play.search.impl.tools.PinyinAnalyzer;
import org.play.search.impl.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;



@Service("searchService")
public class SearchServiceImpl implements SearchService{

//	@Autowired
//	private VideoService videoService;
//	@Autowired
//	private UsersService usersService;
//	@Autowired
//	private VideoCategoryService videoCategoryService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	public void saveIndex(Search model) {
		// TODO Auto-generated method stub
		// 
		String index_path=Tools.getContextValue()+File.separator+"Indexes";
		//String index_path = "d:/playSearch" + File.separator + "Indexes"		+ File.separator + searchDoc;
		File index = new File(index_path);
		if (!index.exists()) {
			index.mkdirs();
		}
		IndexWriter indexWriter=null;
		
		try {
			Directory dir = FSDirectory.open(index.toPath());
			indexWriter = new IndexWriter(dir,
					new IndexWriterConfig(new PinyinAnalyzer()));
			Document doc = new Document();
			doc.add(new StringField("id", model.getId(),
					Field.Store.YES));
			doc.add(new StringField("user_id", model.getUserId(),
					Field.Store.YES));
			doc.add(new StringField("nick_name", model.getNickName(),
					Field.Store.YES));
			doc.add(new TextField("title", model.getTitle(), Field.Store.YES));
			doc.add(new TextField("about", model.getAbout(), Field.Store.YES));
			doc.add(new TextField("tag", model.getTag()==null?"":model.getTag(), Field.Store.YES));
			doc.add(new StringField("cate_id", model.getCateId(),
					Field.Store.YES));
			doc.add(new SortedDocValuesField("cate_id", new BytesRef(model.getCateId())));
			doc.add(new StringField("cate_name", model.getCateName(),
					Field.Store.YES));
			doc.add(new StringField("video_path", model.getVideoPath(),
					Field.Store.YES));
			doc.add(new StringField("status", model.getStatus().toString(), Field.Store.YES));
			doc.add(new StringField("message", model.getMessage(),
					Field.Store.YES));
			doc.add(new LongField("create_at", model.getCreateAt().getTime(),
					Field.Store.YES));
			doc.add(new NumericDocValuesField("create_at", model.getCreateAt().getTime()));
			doc.add(new StringField("top_image", model.getTopImage(),
					Field.Store.YES));
			doc.add(new StringField("image_name", model.getImageName(),
					Field.Store.YES));
			doc.add(new StringField("video_stream", model.getVideoStream(), Field.Store.YES));
			doc.add(new LongField("chick", model.getChick(), Field.Store.YES));
			doc.add(new NumericDocValuesField("chick", model.getChick()));
			doc.add(new StringField("type", model.getType().toString(), Field.Store.YES));
			doc.add(new StringField("length", model.getVideoLength(), Field.Store.YES));
			indexWriter.addDocument(doc);
			
			indexWriter.commit();
			System.out.println("提交");
			indexWriter.close();
		} catch (Exception e) {
			try {
				e.printStackTrace();
				indexWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
	}

	public void updateIndex(Search model) {
		// TODO Auto-generated method stub
		// 
		String index_path=Tools.getContextValue()+File.separator+"Indexes";
		//String index_path = "d:/playSearch" + File.separator + "Indexes"+ File.separator + searchDoc;
		File index = new File(index_path);
		if (!index.exists()) {
			index.mkdirs();
		}
		try {
			Directory dir = FSDirectory.open(index.toPath());
			IndexWriter indexWriter = new IndexWriter(dir,
					new IndexWriterConfig(new PinyinAnalyzer()));
			Document doc = new Document();
			doc.add(new StringField("id", model.getId(),
					Field.Store.YES));
			doc.add(new StringField("user_id", model.getUserId(),
					Field.Store.YES));
			doc.add(new StringField("nick_name", model.getNickName(),
					Field.Store.YES));
			doc.add(new TextField("title", model.getTitle(), Field.Store.YES));
			doc.add(new TextField("about", model.getAbout(), Field.Store.YES));
			doc.add(new TextField("tag", model.getTag()==null?"":model.getTag(), Field.Store.YES));
			doc.add(new StringField("cate_id", model.getCateId(),
					Field.Store.YES));
			doc.add(new SortedDocValuesField("cate_id", new BytesRef(model.getCateId())));
			doc.add(new StringField("cate_name", model.getCateName(),
					Field.Store.YES));
			doc.add(new StringField("video_path", model.getVideoPath(),
					Field.Store.YES));
			doc.add(new StringField("status", model.getStatus().toString(), Field.Store.YES));
			doc.add(new StringField("message", model.getMessage(),
					Field.Store.YES));
			doc.add(new LongField("create_at", model.getCreateAt().getTime(),
					Field.Store.YES));
			doc.add(new NumericDocValuesField("create_at", model.getCreateAt().getTime()));
			doc.add(new StringField("top_image", model.getTopImage(),
					Field.Store.YES));
			doc.add(new StringField("image_name", model.getImageName(),
					Field.Store.YES));
			doc.add(new StringField("video_stream", model.getVideoStream(), Field.Store.YES));
			doc.add(new LongField("chick", model.getChick(), Field.Store.YES));
			doc.add(new NumericDocValuesField("chick", model.getChick()));
			doc.add(new StringField("type", model.getType().toString(), Field.Store.YES));
			doc.add(new StringField("length", model.getVideoLength(), Field.Store.YES));
			indexWriter.updateDocument(
					new Term("id", model.getId()), doc);
			indexWriter.commit();
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeIndex(String id) {
		// TODO Auto-generated method stub
		// 
		String index_path=Tools.getContextValue()+File.separator+"Indexes";
		//String index_path = "d:/playSearch" + File.separator + "Indexes"+ File.separator + searchDoc;
		File index = new File(index_path);
		if (!index.exists()) {
			index.mkdirs();
		}
		try {
			Directory dir = FSDirectory.open(index.toPath());
			IndexWriter indexWriter = new IndexWriter(dir,
					new IndexWriterConfig(new PinyinAnalyzer()));

			indexWriter.deleteDocuments(new Term("id", id));
			indexWriter.commit();
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeAll() {
		// TODO Auto-generated method stub
		String index_path=Tools.getContextValue()+File.separator+"Indexes";
		File index=new File(index_path);
		if(!index.exists())
		{
			index.mkdirs();
		}
		try {
			Directory dir = FSDirectory.open(index.toPath());
			IndexWriter indexWriter = new IndexWriter(dir,new IndexWriterConfig(new PinyinAnalyzer()));
			indexWriter.deleteAll();
			indexWriter.commit();
			indexWriter.close();
			System.out.println("清空结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refresh(List<Search> searchs) {
		// TODO Auto-generated method stub
		removeAll();
		for(Search search:searchs)
		{	
			saveIndex(search);
		}
		
		//执行查询表记录操作，保存索引
	}

	public SearchPageBean<Search> searchLiveSingle(String field,
			SortField sortField, String search, int curPage, int pageSize) {
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="d:/home/play"+File.separator+"Indexes"+File.separator+searchDoc;
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			BooleanQuery query = new BooleanQuery();  
			TermQuery termQuery = new TermQuery(new Term(field, search));    
		    query.add(termQuery,Occur.MUST);  
		    termQuery = new TermQuery(new Term("type", "1"));  
		    query.add(termQuery,Occur.MUST);
		    termQuery = new TermQuery(new Term("status", "1"));  
		    query.add(termQuery,Occur.MUST);
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}

	public SearchPageBean<Search> searchLiveMultiple(SortField sortField, String search, int curPage, int pageSize) {
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="d:/home/play"+File.separator+"Indexes"+File.separator+searchDoc;
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			BooleanQuery query = new BooleanQuery();  
		      
		    TermQuery termQuery = new TermQuery(new Term("title", search));    
		    query.add(termQuery,Occur.SHOULD);  
		      
		    termQuery = new TermQuery(new Term("about", search));  
		    query.add(termQuery,Occur.SHOULD);   
		    termQuery = new TermQuery(new Term("type", "1"));  
		    query.add(termQuery,Occur.MUST);
		    termQuery = new TermQuery(new Term("status", "1"));  
		    query.add(termQuery,Occur.MUST);
		    
			
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}

	public SearchPageBean<Search> searchLiveCate(SortField sortField,
			String search, int curPage, int pageSize) {
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="d:/home/play"+File.separator+"Indexes"+File.separator+searchDoc;
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			
	        BooleanQuery query = new BooleanQuery();  
		      
		    TermQuery termQuery = new TermQuery(new Term("cate_id", search));    
		    query.add(termQuery,Occur.MUST);  
		    termQuery = new TermQuery(new Term("type", "1"));  
		    query.add(termQuery,Occur.MUST);
		    termQuery = new TermQuery(new Term("status", "1"));  
		    query.add(termQuery,Occur.MUST);
			
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}

	public SearchPageBean<Search> searchLiveRange(SortField sortField, Long startTime, Long endTime, int curPage,int pageSize) {
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="d:/home/play"+File.separator+"Indexes"+File.separator+searchDoc;
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			BooleanQuery query = new BooleanQuery();  
			NumericRangeQuery<Long> queryTime = NumericRangeQuery.newLongRange("create_at",startTime,endTime,true, true);  
			query.add(queryTime, Occur.MUST);
			TermQuery termQuery = new TermQuery(new Term("status", "1"));
			query.add(termQuery,Occur.MUST);
		    termQuery = new TermQuery(new Term("type", "1"));  
		    query.add(termQuery,Occur.MUST);
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}
	public SearchPageBean<Search> searchVideoSingle(String field,
			SortField sortField, String search, int curPage, int pageSize) {
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="d:/home/play"+File.separator+"Indexes"+File.separator+searchDoc;
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			BooleanQuery query = new BooleanQuery();  
			TermQuery termQuery = new TermQuery(new Term(field, search));    
		    query.add(termQuery,Occur.MUST);  
		    termQuery = new TermQuery(new Term("type", "2"));  
		    query.add(termQuery,Occur.MUST);
		    termQuery = new TermQuery(new Term("status", "1"));  
		    query.add(termQuery,Occur.MUST);
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}

	public SearchPageBean<Search> searchVideoMultiple(SortField sortField, String search, int curPage, int pageSize) {
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="d:/home/play"+File.separator+"Indexes"+File.separator+searchDoc;
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			BooleanQuery query = new BooleanQuery();  
		      
		    TermQuery termQuery = new TermQuery(new Term("title", search));    
		    query.add(termQuery,Occur.SHOULD);  
		      
		    termQuery = new TermQuery(new Term("about", search));  
		    query.add(termQuery,Occur.SHOULD);   
		    termQuery = new TermQuery(new Term("type", "2"));  
		    query.add(termQuery,Occur.MUST);
		    termQuery = new TermQuery(new Term("status", "1"));  
		    query.add(termQuery,Occur.MUST);
		    
			
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}

	public SearchPageBean<Search> searchVideoCate(SortField sortField,
			String search, int curPage, int pageSize) {
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="/Users/renlongsong/Downloads/play/Indexes";
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			
			BooleanQuery query = new BooleanQuery();  
		      
		    TermQuery termQuery = new TermQuery(new Term("cate_id", search));    
		    query.add(termQuery,Occur.MUST);  
		    termQuery = new TermQuery(new Term("type", "2"));  
		    query.add(termQuery,Occur.MUST);
		    termQuery = new TermQuery(new Term("status", "1"));  
		    query.add(termQuery,Occur.MUST);
			
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}

	public SearchPageBean<Search> searchVideoRange(SortField sortField, Long startTime, Long endTime, int curPage,int pageSize) {
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="d:/home/play"+File.separator+"Indexes"+File.separator+searchDoc;
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			BooleanQuery query = new BooleanQuery();  
			NumericRangeQuery<Long> queryTime = NumericRangeQuery.newLongRange("create_at",startTime,endTime,true, true);  
			query.add(queryTime, Occur.MUST);
			TermQuery termQuery = new TermQuery(new Term("status", "1"));
			query.add(termQuery,Occur.MUST);
		    termQuery = new TermQuery(new Term("type", "2"));  
		    query.add(termQuery,Occur.MUST);
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}

	@Override
	public SearchPageBean<Search> searchSingle(String field, SortField sortField,
			String search, int curPage, int pageSize) {
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="d:/home/play"+File.separator+"Indexes"+File.separator+searchDoc;
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			BooleanQuery query = new BooleanQuery();  
			TermQuery termQuery = new TermQuery(new Term(field, search));    
		    query.add(termQuery,Occur.MUST);  
		    termQuery = new TermQuery(new Term("status", "1"));  
		    query.add(termQuery,Occur.MUST);
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}

	@Override
	public SearchPageBean<Search> searchMultiple(SortField sortField, String search,
			int curPage, int pageSize) {
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="d:/home/play"+File.separator+"Indexes";
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			BooleanQuery query = new BooleanQuery();  
		      
		    TermQuery termQuery = new TermQuery(new Term("title", search));    
		    query.add(termQuery,Occur.MUST);  
		      
		    termQuery = new TermQuery(new Term("about", search));  
		    query.add(termQuery,Occur.MUST);   
		    termQuery = new TermQuery(new Term("status", "1"));  
		    query.add(termQuery,Occur.MUST);
		    
			
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}

	@Override
	public SearchPageBean<Search> searchCate(SortField sortField, String search,
			int curPage, int pageSize) {
		try {
			//String index_path=Tools.getContextValue("play/search/home")+File.separator+"Indexes";
			String index_path="d:/home/play"+File.separator+"Indexes";
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			
			BooleanQuery query = new BooleanQuery();  
		      
		    TermQuery termQuery = new TermQuery(new Term("cate_id", search));    
		    query.add(termQuery,Occur.MUST);  
		    termQuery = new TermQuery(new Term("status", "1"));  
		    query.add(termQuery,Occur.MUST);
			
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}

	@Override
	public SearchPageBean<Search> searchRange(SortField sortField, Long startTime,
			Long endTime, int curPage, int pageSize) {
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="d:/home/play"+File.separator+"Indexes"+File.separator+searchDoc;
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			BooleanQuery query = new BooleanQuery();  
			NumericRangeQuery<Long> queryTime = NumericRangeQuery.newLongRange("create_at",startTime,endTime,true, true);  
			query.add(queryTime, Occur.MUST);
			TermQuery termQuery = new TermQuery(new Term("status", "1"));
			query.add(termQuery,Occur.MUST);
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}
	public static void main(String[] args) {
		SearchServiceImpl impl=new SearchServiceImpl();
		SortField sortField=new SortField("create_at", Type.LONG, true);
		impl.searchVideoAll(sortField, 1, 20);
	}

	@Override
	public SearchPageBean<Search> searchVideoAll(SortField sortField, int curPage,
			int pageSize) {
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="/Users/renlongsong/Downloads/play/Indexes";
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			BooleanQuery query = new BooleanQuery();  
		    TermQuery termQuery = new TermQuery(new Term("type", "2"));  
		    query.add(termQuery,Occur.MUST);
		    termQuery = new TermQuery(new Term("status", "1"));  
		    query.add(termQuery,Occur.MUST);
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}

	@Override
	public SearchPageBean<Search> searchLiveAll(SortField sortField, int curPage,
			int pageSize) {
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="d:/home/play"+File.separator+"Indexes";
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			BooleanQuery query = new BooleanQuery();  
		    TermQuery termQuery = new TermQuery(new Term("type", "1"));  
		    query.add(termQuery,Occur.MUST);
		    termQuery = new TermQuery(new Term("status", "1"));  
		    query.add(termQuery,Occur.MUST);
			Sort sort = new Sort(sortField);
			int start = (curPage - 1) * pageSize;
			int hm = start + pageSize;
			TopFieldCollector c = TopFieldCollector.create(sort, hm, false,
					false, false);
			searcher.search(query, c);
			ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
			if (hits == null || hits.length < 1)
			{
				return new SearchPageBean<Search>();
			}
			List<Search> docs = new ArrayList<Search>();

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			SearchPageBean<Search> bean = new SearchPageBean<Search>(pageSize, curPage,c.getTotalHits(), docs);
			return bean;
		} catch (Exception e) {
			return new SearchPageBean<Search>();
		}
	}

	@Override
	public List<Search> searchConform(SortField sortField, String search) {
		List<Search> docs = new ArrayList<Search>();
		try {
			String index_path=Tools.getContextValue()+File.separator+"Indexes";
			//String index_path="d:/home/play"+File.separator+"Indexes";
			File index=new File(index_path);
			if(!index.exists())
			{
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			BooleanQuery query = new BooleanQuery();  
		      
			TermQuery termQuery = new TermQuery(new Term("cate_id", search));    
		    query.add(termQuery,Occur.MUST);  
		    termQuery = new TermQuery(new Term("status", "1"));  
		    query.add(termQuery,Occur.MUST);
			
			Sort sort = new Sort(sortField);
			
			ScoreDoc[] hits=searcher.search(query, 6, sort).scoreDocs;
			
			if (hits == null || hits.length < 1)
			{
				return docs;
			}
			

			for (ScoreDoc scoreDoc : hits) 
			{
				Search doc = new Search();
				Document document = searcher.doc(scoreDoc.doc);
				doc.setId(document.get("id"));
				doc.setUserId(document.get("user_id"));
				doc.setTitle(document.get("title"));
				doc.setAbout(document.get("about"));
				doc.setCateId(document.get("cate_id"));
				doc.setVideoPath(document.get("video_path"));
				doc.setStatus(Integer.valueOf(document.get("status")));
				doc.setMessage(document.get("message"));
				doc.setCreateAt(new Date(Long.valueOf(document.get("create_at"))));
				doc.setTopImage(document.get("top_image"));
				doc.setImageName(document.get("image_name"));
				doc.setVideoStream(document.get("video_stream"));
				doc.setChick(Long.valueOf(document.get("chick")));
				doc.setNickName(document.get("nick_name"));
				doc.setCateName(document.get("cate_name"));
				doc.setVideoLength(document.get("length"));
				docs.add(doc);
			}
			
			return docs;
		} catch (Exception e) {
			return docs;
		}
	}

	@Override
	public List<Search> searchGroup(SortField sortField, String type,
			Integer indexCount) {
		List<Search> searchs = new ArrayList<Search>();
		try {
			String index_path = Tools.getContextValue()
					+ File.separator + "Indexes";
			// String index_path="d:/home/play"+File.separator+"Indexes";
			File index = new File(index_path);
			if (!index.exists()) {
				index.mkdirs();
			}
			Directory fileDirectory = FSDirectory.open(index.toPath());
			IndexReader fileReader = DirectoryReader.open(fileDirectory);
			IndexSearcher searcher = new IndexSearcher(fileReader);
			GroupingSearch groupingSearch = new GroupingSearch("cate_id");
			groupingSearch.setGroupSort(new Sort(SortField.FIELD_SCORE));
			// groupingSearch.setGroupSort(new Sort(new SortField("chick",
			// Type.LONG, true)));
			groupingSearch.setSortWithinGroup(new Sort(new SortField("chick",
					Type.LONG, true)));
			groupingSearch.setFillSortFields(true);
			groupingSearch.setCachingInMB(128.0, true);
			groupingSearch.setAllGroups(true);
			// groupingSearch.setAllGroupHeads(true);
			groupingSearch.setGroupDocsLimit(1);

			BooleanQuery query = new BooleanQuery();
			TermQuery termQuery = new TermQuery(new Term("type", type));
			query.add(termQuery, Occur.MUST);
			termQuery = new TermQuery(new Term("status", "1"));
			query.add(termQuery, Occur.MUST);
			TopGroups<BytesRef> result = groupingSearch.search(searcher, query,
					0, indexCount);

			System.out.println("搜索命中数：" + result.totalHitCount);
			System.out.println("搜索结果分组数：" + result.groups.length);

			for (GroupDocs<BytesRef> groupDocs : result.groups) {
				System.out.println("分组：" + groupDocs.groupValue.utf8ToString());
				System.out.println("组内记录：" + groupDocs.totalHits);

				// System.out.println("groupDocs.scoreDocs.length:" +
				// groupDocs.scoreDocs.length);
				for (ScoreDoc scoreDoc : groupDocs.scoreDocs) {
					Search doc = new Search();
					Document document = searcher.doc(scoreDoc.doc);
					doc.setId(document.get("id"));
					doc.setUserId(document.get("user_id"));
					doc.setTitle(document.get("title"));
					doc.setAbout(document.get("about"));
					doc.setCateId(document.get("cate_id"));
					doc.setVideoPath(document.get("video_path"));
					doc.setStatus(Integer.valueOf(document.get("status")));
					doc.setMessage(document.get("message"));
					doc.setCreateAt(new Date(Long.valueOf(document
							.get("create_at"))));
					doc.setTopImage(document.get("top_image"));
					doc.setImageName(document.get("image_name"));
					doc.setVideoStream(document.get("video_stream"));
					doc.setChick(Long.valueOf(document.get("chick")));
					doc.setNickName(document.get("nick_name"));
					doc.setCateName(document.get("cate_name"));
					doc.setVideoLength(document.get("length"));
					searchs.add(doc);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return searchs;
		}
		return searchs;
	}
}
