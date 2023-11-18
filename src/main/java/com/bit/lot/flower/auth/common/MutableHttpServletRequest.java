package com.bit.lot.flower.auth.common;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public final class MutableHttpServletRequest extends HttpServletRequestWrapper {

  private final Map<String, String> customHeaders;

  public MutableHttpServletRequest(HttpServletRequest request) {
    super(request);
    this.customHeaders = new HashMap<>();
  }

  public void putHeader(String name, String value) {
    this.customHeaders.put(name, value);
  }

  @Override
  public Enumeration<String> getHeaders(String name) {
    Set<String> set = new HashSet<>();
    Optional.ofNullable(customHeaders.get(name)).ifPresent(h -> set.add(h));
    Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaders(name);
    while (e.hasMoreElements()) {
      String n = e.nextElement();
      set.add(n);
    }
    Optional.ofNullable(customHeaders.get(name)).ifPresent(h -> set.add(h));
    return Collections.enumeration(set);
  }

  public Enumeration<String> getHeaderNames() {
    Set<String> set = new HashSet<>(customHeaders.keySet());

    @SuppressWarnings("unchecked")
    Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
    while (e.hasMoreElements()) {
      String n = e.nextElement();
      set.add(n);
    }
    return Collections.enumeration(set);
  }
}
