package com.adaptris.interview;

/**
 * <p>
 * A key-value pair of <code>String</code>s. Used mainly to get round
 * <i>Castor</i>'s <code>Map</code> problems. 
 * </p>
 */
public class KeyValuePair {

  private String pairKey;
  private String pairValue;

  public KeyValuePair() {
    this.setKey("");
    this.setValue("");
  }

  public KeyValuePair(String key, String value) {
    this.setKey(key);
    this.setValue(value);
  }

  public void setKey(String key) {
      pairKey = key;
  }

  public String getKey() {
    return pairKey;
  }

  
  public void setValue(String value) {
      pairValue = value;
  }

  public String getValue() {
    return pairValue;
  }

  public String toString() {
    return "key [" + pairKey + "] value [" + pairValue + "]";
  }
}
