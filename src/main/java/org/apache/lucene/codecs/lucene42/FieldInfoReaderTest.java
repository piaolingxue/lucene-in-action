/**
 * 
 */
package org.apache.lucene.codecs.lucene42;

import java.io.File;

import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.SimpleFSDirectory;

/**
 * @author matrix
 *
 */
public class FieldInfoReaderTest {

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    Directory dir = new SimpleFSDirectory(new File("index"));
    Lucene42FieldInfosReader reader = new Lucene42FieldInfosReader();
    FieldInfos fieldInfos = reader.read(dir, "_0", IOContext.READ);
  }

}
