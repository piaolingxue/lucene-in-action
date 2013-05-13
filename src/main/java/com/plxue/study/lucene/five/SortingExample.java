/**
 * 
 */
package com.plxue.study.lucene.five;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 // @author matrix
 // May 8, 2013
 //
 */
public class SortingExample {
	private static Logger LOG = LoggerFactory.getLogger(SortingExample.class);
	
	private Directory directory = null;
	
	public SortingExample(Directory directory) {
		this.directory = directory;
	}
	
	public void displayResults(Query query, Sort sort)
		throws IOException {
		IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(directory));
		
		TopDocs results = searcher.search(query, null, 20, sort, true, true);
		LOG.info("\nResults for:{} sorted by {}", query, sort);
		
		System.out.println(StringUtils.rightPad("Title", 30) +
				StringUtils.rightPad("pubmonth", 10) +
				StringUtils.center("id", 10) + 
				StringUtils.center("score", 15) +
				StringUtils.center("category", 10));
		
		for (ScoreDoc sd : results.scoreDocs) {
			int docID = sd.doc;
			float score = sd.score;
			Document doc = searcher.doc(docID);
			System.out.println(StringUtils.rightPad(
					StringUtils.abbreviate(doc.get("title"), 29), 30) +
					StringUtils.rightPad(doc.get("pubmonth"), 10) +
					StringUtils.center("" + docID, 10) +
					StringUtils.center(String.format("%.2f", score), 15) +
					StringUtils.center(doc.get("category"), 10));
		}
	}

	static String[] words = new String[] {
				"hello",
				"world",
				"map",
				"list",
				"String"
		};
	static Random random = new Random();
	
	static String generateSentence() {
		int len = random.nextInt(words.length) + 1;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; ++i) 
			sb.append(i == 0 ? words[random.nextInt(words.length)]
					: " " + words[random.nextInt(words.length)]);
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		
		Directory d = new SimpleFSDirectory(new File("index"));
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_42, 
				new SmartChineseAnalyzer(Version.LUCENE_42));
		IndexWriter writer = new IndexWriter(d, conf);
		for (int i = 0; i < 10000; ++i) {
			Document doc = new Document();
			doc.add(new TextField("title", generateSentence(), Store.YES));
			doc.add(new TextField("pubmonth", 
					String.valueOf(random.nextInt(2004)), 
					Store.YES));
			doc.add(new TextField("category", "city", Store.YES));
			doc.add(new TextField("value", 
					String.valueOf(random.nextInt()), Store.NO));
			writer.addDocument(doc);
		}
		writer.commit();
		
		
		BooleanQuery query = new BooleanQuery();
		query.add(new TermQuery(new Term("title", "world")), Occur.SHOULD);
		query.add(new TermQuery(new Term("title", "hello")), Occur.SHOULD);
		SortingExample example = new SortingExample(d);
		
		example.displayResults(query, new Sort(new SortField("category", Type.STRING)));
		example.displayResults(query, new Sort(new SortField("value", Type.INT,
				true)));
		example.displayResults(query, new Sort(SortField.FIELD_DOC));
		example.displayResults(query, new Sort(SortField.FIELD_SCORE));
		// score by index order
		example.displayResults(query, Sort.INDEXORDER);
		writer.close();
		d.close();
	}

}
