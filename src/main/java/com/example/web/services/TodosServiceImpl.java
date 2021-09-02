package com.example.web.services;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;
import io.vertx.serviceproxy.ServiceException;

public class TodosServiceImpl implements TodosService {
	@Override
	public void gettodos(ServiceRequest context, Handler<AsyncResult<ServiceResponse>> resultHandler) {
		throw new ServiceException(501, "Not yet implemented: gettodos");
	}

	@Override
	public void createTodo(JsonObject body, ServiceRequest context, Handler<AsyncResult<ServiceResponse>> resultHandler) {
		throw new ServiceException(501, "Not yet implemented: createTodo");
	}

	@Override
	public void getTodo(String todoId, ServiceRequest context, Handler<AsyncResult<ServiceResponse>> resultHandler) {
		throw new ServiceException(501, "Not yet implemented: getTodo");
	}

	@Override
	public void updateTodo(String todoId, JsonObject body, ServiceRequest context, Handler<AsyncResult<ServiceResponse>> resultHandler) {
		throw new ServiceException(501, "Not yet implemented: updateTodo");
	}

	@Override
	public void deleteTodo(String todoId, ServiceRequest context, Handler<AsyncResult<ServiceResponse>> resultHandler) {
		throw new ServiceException(501, "Not yet implemented: deleteTodo");
	}
}
