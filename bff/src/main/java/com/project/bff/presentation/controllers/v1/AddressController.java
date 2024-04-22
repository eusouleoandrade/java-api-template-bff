package com.project.bff.presentation.controllers.v1;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bff.application.dtos.responses.GetAddressResponse;
import com.project.bff.application.dtos.responses.NotificationMessagesResponse;
import com.project.bff.application.interfaces.useCases.IGetAddressUseCase;
import com.project.bff.application.mappings.GetAddressUseCaseResponseMapping;
import com.project.bff.shared.notifications.contexts.NotificationContext;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/address")
@Tag(name = "Address", description = "Endpoints related to address")
public class AddressController {

    private final Logger logger = LoggerFactory.getLogger(AddressController.class);
    private final IGetAddressUseCase getAddressUseCase;
    private final GetAddressUseCaseResponseMapping getAddressUseCaseResponseMapping;
    private final NotificationContext notificationContext;

    public AddressController(IGetAddressUseCase getAddressUseCase,
            GetAddressUseCaseResponseMapping getAddressUseCaseResponseMapping,
            NotificationContext notificationContext) {

        this.getAddressUseCase = getAddressUseCase;
        this.getAddressUseCaseResponseMapping = getAddressUseCaseResponseMapping;
        this.notificationContext = notificationContext;
    }

    @GetMapping("")
    @Operation(summary = "Get address by cep")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Processed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GetAddressResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) })
    })
    public ResponseEntity<?> get(@RequestParam(name = "cep") String cep) {

        logger.info(String.format("Start controller %s > method get.",
                AddressController.class.getSimpleName()));

        var useCaseResponse = getAddressUseCase.runAsync(cep).join();

        if (getAddressUseCase.hasErrorNotification()) {

            notificationContext.addErrorNotifications(getAddressUseCase);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var response = getAddressUseCaseResponseMapping.convertToAddressModel(useCaseResponse);

        logger.info(String.format("Finishes successfully controller %s > method get.",
                AddressController.class.getSimpleName()));

        return ResponseEntity.ok(new GetAddressResponse(response));
    }
}