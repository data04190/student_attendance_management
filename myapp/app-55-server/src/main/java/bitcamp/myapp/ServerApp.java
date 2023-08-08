package bitcamp.myapp;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.config.AppConfig;
import bitcamp.util.ApplicationContext;
import bitcamp.util.DispatcherServlet;
import bitcamp.util.HttpServletRequest;
import bitcamp.util.HttpServletResponse;
import bitcamp.util.HttpSession;
import bitcamp.util.SqlSessionFactoryProxy;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.NettyOutbound;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

public class ServerApp {

  public static final String MYAPP_SESSION_ID = "myapp_session_id";

  ApplicationContext iocContainer;
  DispatcherServlet dispatcherServlet;
  Map<String, HttpSession> sessionMap = new HashMap<>();

  int port;

  public ServerApp(int port) throws Exception {
    this.port = port;
    iocContainer = new ApplicationContext(AppConfig.class);
    dispatcherServlet = new DispatcherServlet(iocContainer);

  }

  public void close() throws Exception {

  }

  public static void main(String[] args) throws Exception {
    ServerApp app = new ServerApp(8888);
    app.execute();
    app.close();
  }

  public void execute() throws Exception {
    DisposableServer server = HttpServer.create().port(8888)
        .handle((request, response) -> processRequest(request, response)).bindNow();
    System.out.println("서버 실행됨!");

    server.onDispose().block();
    System.out.println("서버 종료됨!");
  }

  private NettyOutbound processRequest(HttpServerRequest request, HttpServerResponse response) {
    System.out.println(response.getClass().getName());
    HttpServletRequest request2 = new HttpServletRequest(request);
    HttpServletResponse response2 = new HttpServletResponse(response);

    try {
      // 클라이언트 세션 ID 알아내기
      String sessionId = null;
      boolean firstVisit = false;

      // 클라이언트가 보낸 쿠키들 중에서 세션ID가 있는지 확인
      List<Cookie> cookies = request2.allCookies().get(MYAPP_SESSION_ID);
      if (cookies != null) {
        sessionId = cookies.get(0).value();
      } else {
        sessionId = UUID.randomUUID().toString();
        firstVisit = true;
      }

      HttpSession session = sessionMap.get(sessionId);
      if (session == null) {
        session = new HttpSession(sessionId);

        sessionMap.put(sessionId, session);
      }

      request2.setSession(session);

      if (firstVisit) {
        response.addCookie(new DefaultCookie(MYAPP_SESSION_ID, sessionId));
      }

      String servletPath = request2.getServletPath();

      // favicon.ico 요청에 대한 응답
      if (servletPath.equals("/favicon.ico")) {
        response.addHeader("Content-Type", "image/vnd.microsoft.icon");
        return response
            .sendFile(Path.of(ServerApp.class.getResource("/static/favicon.ico").toURI()));
      }

      // welcome 파일 또는 HTML 파일 요청할 떄
      if (servletPath.endsWith("/") || servletPath.endsWith(".html")) {
        String resourcePath = String.format("/static%s%s", servletPath,
            (servletPath.endsWith("/") ? "index.html" : ""));

        response.addHeader("Content-Type", "text/html;charset=UTF-8");
        return response.sendFile(Path.of(ServerApp.class.getResource(resourcePath).toURI()));
      }

      if (request.isFormUrlencoded()) {
        // POST 방식으로 요청했다면,
        return response.sendString(request.receive().aggregate().asString().map(body -> {
          try {
            request2.parseFormBody(body);
            dispatcherServlet.service(request2, response2);
          } catch (Exception e) {
            e.printStackTrace();
          }
          response.addHeader("Content-Type", response2.getContentType());
          return response2.getContent();
        }));

      } else {
        // GET 방식으로 요청했다면,
        dispatcherServlet.service(request2, response2);
        response.addHeader("Content-Type", response2.getContentType());
        return response.sendString(Mono.just(response2.getContent()));
      }

    } catch (Exception e) {
      e.printStackTrace();
      return response.sendString(Mono.just(response2.getContent()));
    } finally {
      SqlSessionFactoryProxy sqlSessionFactoryProxy =
          (SqlSessionFactoryProxy) iocContainer.getBean(SqlSessionFactory.class);
      sqlSessionFactoryProxy.clean();
    }
  }

}
