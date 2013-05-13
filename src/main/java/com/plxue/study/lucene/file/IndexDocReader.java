/**
 * 
 */
package com.plxue.study.lucene.file;

import java.io.File;

import org.apache.lucene.index.IndexFileNames;
import org.apache.lucene.index.SegmentInfo;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.SimpleFSDirectory;

/**
 * @author matrix
 *
 */
public class IndexDocReader {

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    Directory dir = new SimpleFSDirectory(new File("index"));
//    SegmentInfo segmentInfo = new 
//    IndexInput input = dir.openInput(IndexFileNames.segmentFileName(segmentName, segmentSuffix, ext), context)
  }

}
