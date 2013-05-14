/**
 * 
 */
package com.plxue.study.lucene.file;

import java.io.File;

import org.apache.lucene.codecs.lucene41.Lucene41PostingsBaseFormat;
import org.apache.lucene.codecs.lucene41.Lucene41PostingsFormat;
import org.apache.lucene.index.IndexFileNames;
import org.apache.lucene.index.SegmentInfo;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.IOContext.Context;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.SimpleFSDirectory;

/**
 * @author matrix
 *
 */
public class IndexDocReaderTest {

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    Directory dir = new SimpleFSDirectory(new File("index"));
    IndexInput input = dir.openInput(
      IndexFileNames.segmentFileName("", "", Lucene41PostingsFormat.DOC_EXTENSION), 
      new IOContext(Context.READ));
  }

}
