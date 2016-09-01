package com.skymxc.demo.lession_12_practice;

public class Result<T> {
	public T data;
	public ERRORNO error;

	public enum ERRORNO {
		NO_ERROR("没有错误"), 
		PAGE_NOT_FOUND("404：页面未找到"),
		SERVER_ERROR("500:服务器错误"),
		PARAM_PAGE_NULL("请求菜单时page参数是必须的"),
		PARAM_PHONE_NULL("手机号为空"),
		PARAM_PASSWORD_NULL("密码为空"),
		PARAM_TYPE_NULL("用户类型为空"),
		PARAM_PROGRAMME_TYPE_NULL("课程类型为空"),
		MISSING_PARAMETER("缺少必要的参数"),
		PHONE_HAS_REGISTERED("账号已注册"),
		EMAIL_HAS_REGISTERED("邮箱已注册"),
		PHONE_IS_UNREGISTERED("账号未注册"),
		EMAIL_IS_UNREGISTERED("邮箱未注册"),
		USER_TYPE_ERROR("用户类型错误"),
		PASSWORD_ERROR("密码错误错误"),
		PARAM_VERIFICATION_CODE_NULL("验证码为空"),
		PHONE_VERIFICATION_FAILURE("验证失败"),
		PHONE_VERIFICATION_FORMAT_ERROR("手机号码格式错误"),
		VERIFICATION_FREQUENTLY("请求校验验证码频繁"),
		UNKNOWN_LOGIN_TYPE("登录类型未识别"),
		PARAM_EMAIL_NULL("邮箱地址为空"),
		PARAM_USER_ID_NULL("用户ID为空"),
		PARAM_SUGGESTION_CONTENT_NULL("用户建议为空"),
		PARAM_USER_NULL("该用户不存在"),
		PARAM_CLASSES_NULL("该班级不存在"),
		PARAM_CLASSES_NAME_FOUNT("已存在该名字的班级"),
		PARAM_USERClASSES_FOUNT("该用户已加入该班级"),
		PROGRAMME_NOT_FOUND("课程不存在"),
		COLLECTION_TYPE_ID_NULL("收藏类型ID为空"),
		COLLECTION_FOUNT("该收藏已存在"),
		PARAM_CLASSES_ID_NULL("班级ID为空"),
		PARAM_CLASSIFICATION_ID_NULL("分类ID为空"),
		PARAM_PROGRAMME_TYPE_ID_NULL("课程类型ID为空"),
		PARAM_PROGRAMME_ID_NULL("课程ID为空"),
		PARAM_USERLESSONS_FOUND("该用户已加入该班级"),
		PARAM_EXAMINATION_TYPE_ID_NULL("卷子类型ID为空");	
		
		private String description;

		private ERRORNO(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return description;
		}
	}
}
