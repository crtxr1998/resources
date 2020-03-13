package crtx.reactorx.vertx;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;


public class Verxthandler implements Handler<RoutingContext> {

  @Override
  public void handle(RoutingContext event) {
    HttpServerResponse response = event.response();
    response.putHeader("content-type", "text/plain");

    response.end(Json.encode("Hello  我是自定义Handler"));
  }
}
