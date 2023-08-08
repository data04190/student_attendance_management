package bitcamp.util;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

  String sessionId;
  Map<String, Object> attrMap = new HashMap<>();

  public HttpSession(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getId() {
    return this.sessionId;
  }

  public void setAttribute(String name, Object value) {
    attrMap.put(name, value);
  }

  public Object getAttribute(String name) {
    return attrMap.get(name);
  }
}
