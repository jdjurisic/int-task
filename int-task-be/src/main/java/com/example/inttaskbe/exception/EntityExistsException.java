package com.example.inttaskbe.exception;

public class EntityExistsException extends MyException {
	private static final long serialVersionUID = 6687184056879350879L;
	private final Object entity;

	public EntityExistsException(Object entity, String message) {
		super(message);
		this.entity = entity;
	}

	public Object getEntity() {
		return entity;
	}
}
