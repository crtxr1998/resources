package crtx.reactorx.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    HttpServer httpServer = vertx.createHttpServer();


    Router router = Router.router(vertx);
    router.get("/vert").handler(new Verxthandler());
    httpServer.requestHandler(router).listen(8888);
    System.out.println("listen on 8888");

  }

}
