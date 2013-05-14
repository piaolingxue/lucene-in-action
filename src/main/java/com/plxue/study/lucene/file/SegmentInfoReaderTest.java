/**
 * 
 */
package com.plxue.study.lucene.file;

import java.io.File;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.index.SegmentInfo;
import org.apache.lucene.index.SegmentInfoPerCommit;
import org.apache.lucene.index.SegmentInfos;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author matrix
 *
 */
public class SegmentInfoReaderTest {
  private static Logger LOG = LoggerFactory.getLogger(SegmentInfoReaderTest.class);

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    PropertyConfigurator.configure("log4j.properties");
    Directory dir = new SimpleFSDirectory(new File("index"));
    SegmentInfos segmentInfo = new SegmentInfos();
    segmentInfo.read(dir);
    List<SegmentInfoPerCommit> segs = segmentInfo.asList();
    for (SegmentInfoPerCommit seg : segs) {
      SegmentInfo info = seg.info;
      Codec codec = info.getCodec();
      LOG.info("fieldInfoFormat:" + codec.fieldInfosFormat());
      LOG.info("docValuesFormat:" + codec.docValuesFormat());
      LOG.info("liveDocsFormat:" + codec.liveDocsFormat());
      LOG.info("normsFormat:" + codec.normsFormat());
      LOG.info("postingsFormat:" + codec.postingsFormat());
      LOG.info("segmentInfoFormat:" + codec.segmentInfoFormat());
      LOG.info("storedFieldsFormat:" + codec.storedFieldsFormat());
      LOG.info("termVectorsFormat:" + codec.termVectorsFormat());
    }
  }

}
