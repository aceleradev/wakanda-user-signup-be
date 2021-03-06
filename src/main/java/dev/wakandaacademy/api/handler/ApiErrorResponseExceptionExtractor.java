package dev.wakandaacademy.api.handler;

import java.util.Optional;

import org.springframework.stereotype.Component;

import dev.wakandaacademy.api.handler.domain.ApiErrorResponse;
import dev.wakandaacademy.api.handler.domain.ObjectError;

@Component
public class ApiErrorResponseExceptionExtractor {

	public ApiErrorResponse getApiResponse(Exception e, long defaultCode) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setDescription(e.getMessage());
		apiErrorResponse.setCode(defaultCode);
		apiErrorResponse.setMessage(e.getLocalizedMessage());
		createErros(apiErrorResponse, e);
		return apiErrorResponse;
	}

	private void createErros(ApiErrorResponse apiErrorResponse, Exception e) {
		ObjectError objectError = new ObjectError();
		objectError.setMessage(e.getMessage());
		String nativeMessage = Optional.ofNullable(e.getCause())
										.map(Throwable::getMessage)
										.orElse(e.getLocalizedMessage());
		objectError.setNativeMessage(nativeMessage);
		objectError.setCode(apiErrorResponse.getCode());
		apiErrorResponse.addErrorsItem(objectError);
	}

	
}
