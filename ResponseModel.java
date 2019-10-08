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

public class ResponseModel {

	private ResponseModel() {}

	public static <T> String getResponse(Status status, T t, ResponseMessage message, Integer currentUserId) {

		if(message == null) {
			message = ResponseMessage.DEFAULT;
		}
		
		return getResponse(status, t, message.message, currentUserId, Roles.ROLE_ANONYMOUS);

	}

	public static <T> String getResponse(Status status, T t, ResponseMessage message) {
		//ResponseModel<T> responseModel = new ResponseModel<T>(status, t, message.message, null);
		//return getGsonObject().toJson(responseModel);
		
		if(message == null) {
			message = ResponseMessage.DEFAULT;
		}
		
		return getResponse(status, t, message.message, null, Roles.ROLE_ANONYMOUS);
		
	}
	
	public static String getResponse(Status status, String message) {

		
		return getResponse(status, null, message, null, Roles.ROLE_ANONYMOUS);
		
	}
	
	

	
	public static  <T> String  getResponse(Status status, ResponseMessage responseMessage) {
		
		return getResponse(status, null,responseMessage);
		
	}
	
	
	public static <T> String getResponse(Status status, T t, ResponseMessage message, Roles roles) {
		// TODO Auto-generated method stub
		
		if(message == null) {
			message = ResponseMessage.DEFAULT;
		}
		
		return getResponse(status, t, message.message, null, roles);
	}
	
	public static  <T> String  getResponse(Status status, T t , String message, Integer currentUserId,Roles roles) {
		
		ResponseModel rm = new ResponseModel();
		
		Model<T> responseModel = rm.new Model<T>(status, t, message,null);
		
		switch (roles) {
		case ROLE_ADMIN:
			
			return getGsonObjectAdminAndUserInfoGet().toJson(responseModel);

		case ROLE_USER:
			return getGsonObjectUserInfoGet().toJson(responseModel);
		default:
			return getGsonObject().toJson(responseModel);
		}
		
		
	}

	public static Gson getGsonObject() {

		Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).serializeNulls()
				.disableHtmlEscaping().addSerializationExclusionStrategy(new ExclusionStrategy() {

					List<String> skipFields = new ArrayList<String>();
			
					@Override
					public boolean shouldSkipField(FieldAttributes f) {
						//
						
						String fieldName = f.getDeclaringClass().getName()+"."+f.getName();
						
						if(skipFields.contains(fieldName)) {
							return true;
						}
						
						if (f.getAnnotation(GetIt.class) != null) {
							
							GetIt getIt = f.getAnnotation(GetIt.class);
							for(String v : getIt.skipValues()) {
								skipFields.add(v);
							}
							
							return false;
						} else if (f.getAnnotation(OneToMany.class) != null) {
							return true;
						} else if (f.getAnnotation(ManyToMany.class) != null) {
							return true;
						} else if (f.getAnnotation(SkipIt.class) != null) {
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

		Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).serializeNulls()
				.disableHtmlEscaping().addSerializationExclusionStrategy(new ExclusionStrategy() {

					@Override
					public boolean shouldSkipField(FieldAttributes f) {
						//

						if (f.getAnnotation(GetIt.class) != null) {
							return false;
						} else if (f.getAnnotation(OneToMany.class) != null) {
							return true;
						} else if (f.getAnnotation(ManyToMany.class) != null) {
							return true;
						} else if (f.getAnnotation(UserInfoGet.class) != null) {
							return false;
						} else if (f.getAnnotation(SkipIt.class) != null) {
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

		Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).serializeNulls()
				.disableHtmlEscaping().addSerializationExclusionStrategy(new ExclusionStrategy() {

					@Override
					public boolean shouldSkipField(FieldAttributes f) {
						//

						if (f.getAnnotation(GetIt.class) != null) {
							return false;
						} else if (f.getAnnotation(OneToMany.class) != null) {
							return true;
						} else if (f.getAnnotation(ManyToMany.class) != null) {
							return true;
						} else if (f.getAnnotation(FcmGet.class) != null) {
							return false;
						} else if (f.getAnnotation(SkipIt.class) != null) {
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

		Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).serializeNulls()
				.disableHtmlEscaping().addSerializationExclusionStrategy(new ExclusionStrategy() {

					@Override
					public boolean shouldSkipField(FieldAttributes f) {
						//

						if (f.getAnnotation(AdminSkip.class) != null) {
							return true;
						} else if (f.getAnnotation(GetIt.class) != null) {
							return false;
						} else if (f.getAnnotation(OneToMany.class) != null) {
							return true;
						} else if (f.getAnnotation(ManyToMany.class) != null) {
							return true;
						} else if (f.getAnnotation(UserInfoGet.class) != null) {
							return false;
						} else if (f.getAnnotation(AdminGet.class) != null) {
							return false;
						} else if (f.getAnnotation(SkipIt.class) != null) {
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

	

	/*public enum Status {
		SUCCESS, FAIL, UNAUTHORIZE, FB_UNAUTHORIZE
	}*/
	
	
	private class Model<T>{
		
		private Status status;

		private T data;

		private String message;

		private Integer currentUserId;
		
		Model(Status status, T data, String message, Integer currentUserId) {
			
			this.status = status;
			this.data = data;
			this.message = message;
			this.currentUserId = currentUserId;

		}
		
		

		public Status getStatus() {
			return status;
		}

		public T getData() {
			return data;
		}

		public String getMessage() {
			return message;
		}

		public Integer getCurrentUserId() {
			return currentUserId;
		}

		public void setCurrentUserId(Integer currentUserId) {
			this.currentUserId = currentUserId;
		}
		
		
	}
	
	public enum Status {
		SUCCESS, FAIL, UNAUTHORIZE, REDIRECT, PHONE_OTP, TRADE_COMPLETE
	}

	@Target({ ElementType.FIELD, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface SkipIt {

	}

	@Target({ ElementType.FIELD, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface FcmGet {

	}

	@Target({ ElementType.FIELD, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface GetIt {
		public String[] skipValues() default "";
	}

	@Target({ ElementType.FIELD, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface UserInfoGet {

	}

	@Target({ ElementType.FIELD, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface AdminSkip {

	}

	@Target({ ElementType.FIELD, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface AdminGet {

	}
	
	public enum Roles{
		ROLE_USER, ROLE_ADMIN, ROLE_SUBADMIN ,ROLE_ANONYMOUS
	}

	
}
