package com.project.bff.application.interfaces.useCases;

import com.project.bff.application.dtos.responses.GetAddressUseCaseResponse;
import com.project.bff.shared.notifications.interfaces.INotifiable;

public interface IGetAddressUseCase extends INotifiable, IUseCaseWithRequest<String, GetAddressUseCaseResponse> {
}