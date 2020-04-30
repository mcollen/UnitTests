/*
 * $Id: TestURLString.java,v 1.4 2004/03/18 13:58:50 lchan Exp $
 */

package com.adaptris.interview;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author lchan
 */
public class TestURLString extends TestCase {

  private static Log logR = LogFactory.getLog(TestURLString.class);
  
  private static String testUrl =
    "http://myuser:mypassword@localhost:8888//url";
  private static String username = "myuser";
  private static String password = "mypassword";
  private static String host = "localhost";
  private static int port = 8888;
  private static String file = "/url";
  private static String protocol = "http";

  public TestURLString(java.lang.String testName) {
    super(testName);
  }

  public static void main(java.lang.String[] args) {
    junit.textui.TestRunner.run(suite());
  }

  public static Test suite() {
    TestSuite suite = new TestSuite(TestURLString.class);
    return suite;
  }

  public void testUrl() {
    try {
      URLString url = new URLString(testUrl);
      assertEquals(protocol, url.getProtocol());
      assertEquals(username, url.getUsername());
      assertEquals(password, url.getPassword());
      assertEquals(host, url.getHost());
      assertEquals(port, url.getPort());
      assertEquals(file, url.getFile());
      assertEquals(testUrl, url.toString());      
    }
    catch (Exception e) {
      fail(e.getMessage());
    }
  }

  public void testUrlNonUrl() {
    try {
      URLString url = new URLString("config.xml");
      logR.trace(url.getFile());
      assertEquals("config.xml", url.getFile());
      
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
}
