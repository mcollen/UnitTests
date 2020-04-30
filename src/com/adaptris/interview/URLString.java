/*
 * $Id: URLString.java,v 1.8 2004/01/13 09:58:22 lchan Exp $
 */
package com.adaptris.interview;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * A Simple URL parser, that can parse any given URL into it's constituent
 * parts.
 * <p>
 * A URL should be in the form
 * <code> &lt;protocol>://&lt;user:password>@&lt;host:port>/&lt;path>#ref
 *  </code>
 * e.g. http://myname:password@localhost:8080/thisURL
 * 
 * @author $Author: lchan $
 */
public class URLString {

  private String fullURL = null;
  private String protocol;
  private String username;
  private String password;
  private String host;
  private InetAddress hostAddress;
  private boolean hostAddressKnown;
  private int port;
  private String file;
  private String ref;

  private URLString() {
  }

  /**
   * Constructor using a URL
   * 
   * @param url the URL
   */
  public URLString(URL url) {
	 this(url.toString());
  }

  /**
   * Constructor Using a string
   * 
   * @param s the string representation of a url
   */
  public URLString(String s) {
    this();
    hostAddressKnown = false;
    port = -1;
    this.parseString(s);
  }

  /**
   * Return a string representation of the URL
   * 
   * @return string in the usual "url" format e.g. http://localhost:5555/fred
   */
  public String toString() {
    if (fullURL == null) {

      StringBuffer sb = new StringBuffer();

      if (protocol != null) {
        sb.append(protocol);
        sb.append(":");
      }

      if ((username != null) || (host != null)) {
        sb.append("//");

        if (username != null) {
          sb.append(username);

          if (password != null) {
            sb.append(":");
            sb.append(password);
          }

          sb.append("@");
        }

        if (host != null) {
          sb.append(host);
        }

        if (port != -1) {
          sb.append(":");
          sb.append(Integer.toString(port));
        }

        if (file != null) {
          sb.append("/");
        }
      }

      if (file != null) {
        sb.append(file);
      }

      if (ref != null) {
        sb.append("#");
        sb.append(ref);
      }

      fullURL = sb.toString();
    }

    return fullURL;
  }

  /**
   * Get the port associated with this URL
   * 
   * @return the port
   */
  public int getPort() {
    return port;
  }

  /**
   * Get the protocol associated with this URL
   * 
   * @return the protocol
   */
  public String getProtocol() {
    return protocol;
  }

  /**
   * Get get the File associated with this URL
   * 
   * @return the file
   */
  public String getFile() {
    return file;
  }

  /**
   * Get the REF associated with this URL
   * 
   * @return the url reference
   */
  public String getRef() {
    return ref;
  }

  /**
   * Get the Host associated with this URL
   * 
   * @return the host
   */
  public String getHost() {
    return host;
  }

  /**
   * Get the username associated with this URL
   * 
   * @return the user name
   */
  public String getUsername() {
    return username;
  }

  /**
   * Return the password associated with this URL
   * 
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object obj) {

    boolean rc = false;

    do {
      if (!(obj instanceof URLString)) {
        break;
      }

      URLString url = (URLString) obj;

      if ((url.protocol == null) || !url.protocol.equals(protocol)) {
        break;
      }

      InetAddress myAddress = getHostAddress();
      InetAddress theirInetaddress = url.getHostAddress();

      if ((myAddress != null) && (theirInetaddress != null)) {
        if (!myAddress.equals(theirInetaddress)) {
          break;
        }
      }
      else if ((host != null) && (url.host != null)) {
        if (!host.equalsIgnoreCase(url.host)) {
          break;
        }
      }
      else {
        if (host != url.host) {
          break;
        }
      }

      if ((username != url.getUsername())
          && (username == null || !username.equals(url.getUsername()))) {
        break;
      }

      String s = (file != null) ? file : "";
      String s1 = (url.getFile() != null) ? url.getFile() : "";

      if (!s.equals(s1)) {
        break;
      }

      if (port != url.getPort()) {
        break;
      }

      rc = true;
    }
    while (false);

    return rc;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Get the host address
   */
  private InetAddress getHostAddress() {
    if (hostAddressKnown) {
      return hostAddress;
    }
    if (host == null) {
      return null;
    }
    try {
      hostAddress = InetAddress.getByName(host);
    }
    catch (UnknownHostException ex) {
      hostAddress = null;
    }

    hostAddressKnown = true;

    return hostAddress;
  }

  /**
   * Parse the url string
   */
  private void parseString(String s) {
	  System.out.println("HostName URL >>"+s);
    protocol = null;
    file = null;
    ref = null;
    host = null;
    username = null;
    password = null;
    port = -1;

    int i = s.length();
    int j = s.indexOf(':');

    if (j != -1) {
      protocol = s.substring(0, j);
    }
    if (s.regionMatches(j + 1, "//", 0, 2)) {
      String hostportion = null;
      int l = s.indexOf('/', j + 3);
      if (l != -1) {
        hostportion = s.substring(j + 3, l);
        if ((l + 1) < i) {
          file = s.substring(l + 1);
        }
        else {
          file = "";
        }
      }
      else {
        hostportion = s.substring(j + 3);
      }
      int i1 = hostportion.indexOf('@');
      if (i1 != -1) {
        String usernamepw = hostportion.substring(0, i1);
        hostportion = hostportion.substring(i1 + 1);
        int k1 = usernamepw.indexOf(':');
        if (k1 != -1) {
          username = usernamepw.substring(0, k1);
          password = usernamepw.substring(k1 + 1);
        }
        else {
          username = usernamepw;
        }
      }
      int j1;
      if ((hostportion.length() > 0) && (hostportion.charAt(0) == '[')) {
        j1 = hostportion.indexOf(':', hostportion.indexOf(']'));
      }
      else {
        j1 = hostportion.indexOf(':');
      }

      if (j1 != -1) {
        String s3 = hostportion.substring(j1 + 1);
        if (s3.length() > 0) {
          try {
            port = Integer.parseInt(s3);
          }
          catch (NumberFormatException ex) {
            port = GetServiceByName.getPort(s3, "tcp");
          }
        }
        host = hostportion.substring(0, j1);
      }
      else {
        host = hostportion;
      }
    }
    else {
      if ((j + 1) < i) {
        file = s.substring(j + 1);
      }
    }

    int k;

    if ((file != null) && ((k = file.indexOf('#')) != -1)) {
      ref = file.substring(k + 1);
      file = file.substring(0, k);
    }
  }
}