package com.forum.responsemodels;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseModel<T> {

	private Status status;
	
	private T data;
	
	private String message;
	
	public Status getStatus() {
		return status;
	}

	public T getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	private ResponseModel(Status status, T data, String message) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
		
	}
	
	private ResponseModel() {
		// TODO Auto-generated constructor stub
	}
	
	private static Gson getGsonObject() {

		Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().addSerializationExclusionStrategy(new ExclusionStrategy() {

			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				//

				if (f.getAnnotation(GetIt.class) != null) {
					return false;
				} else if (f.getAnnotation(OneToMany.class) != null) {
					return true;
				}else if (f.getAnnotation(ManyToMany.class) != null) {
					return true;
				} 
				else if (f.getAnnotation(SkipIt.class) != null) {
					return true;
				}

				else {
					return false;
				}
			}

			@Override
			public boolean shouldSkipClass(Class<?> arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		}).create();

		return gson;
	}
	
	private static Gson getGsonObjectUserInfoGet() {

		Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().addSerializationExclusionStrategy(new ExclusionStrategy() {

			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				//

				if (f.getAnnotation(GetIt.class) != null) {
					return false;
				} else if (f.getAnnotation(OneToMany.class) != null) {
					return true;
				}else if (f.getAnnotation(ManyToMany.class) != null) {
					return true;
				}
				else if (f.getAnnotation(UserInfoGet.class) != null) {
					return false;
				} 
				else if (f.getAnnotation(SkipIt.class) != null) {
					return true;
				}

				else {
					return false;
				}
			}

			@Override
			public boolean shouldSkipClass(Class<?> arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		}).create();

		return gson;
	}
	
	private static Gson getGsonObjectFcmGet() {

		Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().addSerializationExclusionStrategy(new ExclusionStrategy() {

			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				//

				if (f.getAnnotation(GetIt.class) != null) {
					return false;
				} else if (f.getAnnotation(OneToMany.class) != null) {
					return true;
				}else if (f.getAnnotation(ManyToMany.class) != null) {
					return true;
				}
				else if (f.getAnnotation(FcmGet.class) != null) {
					return false;
				} 
				else if (f.getAnnotation(SkipIt.class) != null) {
					return true;
				}

				else {
					return false;
				}
			}

			@Override
			public boolean shouldSkipClass(Class<?> arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		}).create();

		return gson;
	}
	
	private static Gson getGsonObjectAdminAndUserInfoGet() {

		Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().addSerializationExclusionStrategy(new ExclusionStrategy() {

			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				//

				if (f.getAnnotation(AdminSkip.class) != null) {
					return true;
				}
				else if (f.getAnnotation(GetIt.class) != null) {
					return false;
				} else if (f.getAnnotation(OneToMany.class) != null) {
					return true;
				}else if (f.getAnnotation(ManyToMany.class) != null) {
					return true;
				}
				else if (f.getAnnotation(UserInfoGet.class) != null) {
					return false;
				}else if (f.getAnnotation(AdminGet.class) != null) {
					return false;
				} 
				else if (f.getAnnotation(SkipIt.class) != null) {
					return true;
				}

				else {
					return false;
				}
			}

			@Override
			public boolean shouldSkipClass(Class<?> arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		}).create();

		return gson;
	}

	public static  <T> ResponseModel<T>  getResponseObject(Status status, T t , String message) {
		
		ResponseModel<T> responseModel = new ResponseModel<T>(status, t, message);
		
		return responseModel;
	}
	
	public static  <T> String  getResponse(Status status, T t , String message) {
		ResponseModel<T> responseModel = new ResponseModel<T>(status, t, message);
		
		return getGsonObject().toJson(responseModel);
	}
	
	/*public static  <T> String  getResponse(Status status, T t , String message, Integer currentUserId) {
		ResponseModel<T> responseModel = new ResponseModel<T>(status, t, message);
		responseModel.currentUserId = currentUserId;
		
		return getGsonObject().toJson(responseModel);
	}*/
	
	
	
	public static  <T> String  getResponse(Status status, String message) {
		
		return getResponse(status, null, message);
		
	}
	
	public enum Status {
		SUCCESS, FAIL, UNAUTHORIZE, FB_UNAUTHORIZE
	}
	
	@Target({ElementType.FIELD, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface SkipIt {

	}
	
	@Target({ElementType.FIELD, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface FcmGet {

	}
	
	@Target({ElementType.FIELD, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface GetIt {

	}
	
	@Target({ElementType.FIELD, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface UserInfoGet {

	}
	
	@Target({ElementType.FIELD, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface AdminSkip {

	}
	
	@Target({ElementType.FIELD, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface AdminGet {

	}
}
