/* $Id: KeyValuePairSet.java,v 1.3 2006/05/18 22:49:58 hfraser Exp $ */
package com.adaptris.interview;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeyValuePairSet {

  private Set keyValuePairs;

  public KeyValuePairSet() {
    keyValuePairs = new HashSet();
  }

  public void addKeyValuePair(KeyValuePair pair) {
    if (keyValuePairs.contains(pair)) {
      keyValuePairs.remove(pair);
    }
    keyValuePairs.add(pair);
  }

  public Set getKeyValuePairs() {
    return keyValuePairs;
  }

  public KeyValuePair getKeyValuePair(String key) {
    return getKeyValuePair(new KeyValuePair(key, ""));
  }

  public void removeKeyValuePair(KeyValuePair kp) {
    if (kp != null) {
      keyValuePairs.remove(kp);
    }
  }
  
  public void removeKeyValuePair(String key) {
    removeKeyValuePair(new KeyValuePair(key, ""));
  }
  
  public boolean contains(KeyValuePair kp) {
    return keyValuePairs.contains(kp);
  }
    
  private KeyValuePair getKeyValuePair(KeyValuePair k) {
    List list = Arrays.asList(keyValuePairs.toArray());
    if (list.contains(k)) {
      return (KeyValuePair) list.get(list.indexOf(k));
    }
    return null;
  }

  public int size() {
    return this.keyValuePairs.size();
  }
}
