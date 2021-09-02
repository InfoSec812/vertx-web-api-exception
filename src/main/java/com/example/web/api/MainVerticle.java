package com.example.web.api;

import com.example.web.services.TodosService;
import com.example.web.services.TodosServiceImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.serviceproxy.ServiceBinder;

/**
 * Main Vert.x Verticle, entrypoint for this application
 */
public class MainVerticle extends AbstractVerticle {

  /**
   * Iterate over the operations in the API Spec and delegate the handlers to the
   * Web API Services
   * @param routeBuilder The OpenAPIv3 RouterBuilder instance
   */
  Future<RouterBuilder> mountRoutes(RouterBuilder routeBuilder) {
    routeBuilder.mountServicesFromExtensions();

    routeBuilder.securityHandler("KeyCloak", this::authHandler);

    return Future.succeededFuture(routeBuilder);
  }

  /**
   * Given an instance of {@link RoutingContext}, try to authenticate the user
   * @param ctx The {@link RoutingContext} of the current request
   */
  private void authHandler(RoutingContext ctx) {
    // TODO: Implement Authentication handler
    ctx.next();
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    TodosService todoService = new TodosServiceImpl();
    ServiceBinder todoSvcBinder = new ServiceBinder(vertx);
    todoSvcBinder.setAddress("api.todos").register(TodosService.class, todoService);

    RouterBuilder.create(vertx, "openapi.yml")
      .compose(this::mountRoutes)
      .compose(this::buildParentRouter)
      .compose(this::buildHttpServer)
      .onFailure(startPromise::fail)
      .onSuccess(startPromise::complete);
  }

  /**
   * Given a {@link Router}, use it as the handler for a newly created HTTP Server
   * @param router The {@link Router} for handling requests
   * @return A {@link Future} of type {@link Void} indicating success or failure
   */
  private Future<Void> buildHttpServer(Router router) {
    return vertx.createHttpServer().requestHandler(router).listen(8080).mapEmpty();
  }

  /**
   * Builds a parent router into which the OpenAPI Router will be mounted as a subrouter
   * @param routerBuilder The {@link RouterBuilder} created from the OpenAPIv3 Spec
   * @return A {@link Router} with the OpenAPIv3 Router mounted
   */
  private Future<Router> buildParentRouter(RouterBuilder routerBuilder) {
    Router parentRouter = Router.router(vertx);
    parentRouter.mountSubRouter("/api/v1", routerBuilder.createRouter());
    return Future.succeededFuture(parentRouter);
  }
}
