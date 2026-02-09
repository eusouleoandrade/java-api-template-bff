package com.project.bff.shared.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MsgUtilTest {

    @DisplayName("Methods without parameters return expected code and message")
    @Test
    public void testNoArgMethods() {
        // Arrange & Act & Assert
        assertThat(MsgUtil.INTERNAL_SERVER_ERROR()).containsExactly("COD0001", "Internal server error.");
        assertThat(MsgUtil.DATA_BASE_SERVER_ERROR()).containsExactly("COD0003", "Database server error.");
        assertThat(MsgUtil.RESPONSE_SUCCEEDED_MESSAGE()).containsExactly("COD0007", "Request processed.");
        assertThat(MsgUtil.RESPONSE_FAILED_PROCESS_REQUEST()).containsExactly("COD0010", "Failed to process the request.");
        assertThat(MsgUtil.BAD_REQUEST()).containsExactly("COD0011", "Bad request.");
    }

    @DisplayName("X0_IS_REQUIRED formats parameter into message")
    @Test
    public void testX0IsRequired() {
        // Arrange & Act & Assert
        assertThat(MsgUtil.X0_IS_REQUIRED("name")).containsExactly("COD0002", "name is required.");
        assertThat(MsgUtil.X0_IS_REQUIRED("")).containsExactly("COD0002", " is required.");
        assertThat(MsgUtil.X0_IS_REQUIRED(null)).containsExactly("COD0002", "null is required.");
    }

    @DisplayName("DATA_OF_X0_X1_NOT_FOUND formats params including nulls")
    @Test
    public void testDataOfX0X1NotFound() {
        // Arrange & Act & Assert
        assertThat(MsgUtil.DATA_OF_X0_X1_NOT_FOUND("User", "123")).containsExactly("COD0004", "Data of User 123 not found.");
        assertThat(MsgUtil.DATA_OF_X0_X1_NOT_FOUND(null, "123")).containsExactly("COD0004", "Data of null 123 not found.");
        assertThat(MsgUtil.DATA_OF_X0_X1_NOT_FOUND(null, null)).containsExactly("COD0004", "Data of null null not found.");
    }

    @DisplayName("IDENTIFIER_X0_IS_INVALID formats parameter")
    @Test
    public void testIdentifierInvalid() {
        // Arrange & Act & Assert
        assertThat(MsgUtil.IDENTIFIER_X0_IS_INVALID("abc")).containsExactly("COD0005", "Identifier abc is invalid.");
        assertThat(MsgUtil.IDENTIFIER_X0_IS_INVALID(null)).containsExactly("COD0005", "Identifier null is invalid.");
    }

    @DisplayName("FAILED_TO_UPDATE_X0 formats parameter")
    @Test
    public void testFailedToUpdate() {
        // Arrange & Act & Assert
        assertThat(MsgUtil.FAILED_TO_UPDATE_X0("Order")).containsExactly("COD0006", "Failed to update Order.");
        assertThat(MsgUtil.FAILED_TO_UPDATE_X0(null)).containsExactly("COD0006", "Failed to update null.");
    }

    @DisplayName("OBJECT_X0_IS_NULL formats parameter")
    @Test
    public void testObjectIsNull() {
        // Arrange & Act & Assert
        assertThat(MsgUtil.OBJECT_X0_IS_NULL("myObj")).containsExactly("COD0008", "Object myObj is null.");
        assertThat(MsgUtil.OBJECT_X0_IS_NULL(null)).containsExactly("COD0008", "Object null is null.");
    }

    @DisplayName("FAILED_TO_REMOVE_X0 formats parameter")
    @Test
    public void testFailedToRemove() {
        // Arrange & Act & Assert
        assertThat(MsgUtil.FAILED_TO_REMOVE_X0("Entity")).containsExactly("COD0009", "Failed to remove Entity.");
        assertThat(MsgUtil.FAILED_TO_REMOVE_X0(null)).containsExactly("COD0009", "Failed to remove null.");
    }

    @DisplayName("X0_MUST_CONTAIN_X1_CHARACTERS formats parameters")
    @Test
    public void testX0MustContainX1Characters() {
        // Arrange & Act & Assert
        assertThat(MsgUtil.X0_MUST_CONTAIN_X1_CHARACTERS("field", "10")).containsExactly("COD0012", "field must contain 10 characters.");
        assertThat(MsgUtil.X0_MUST_CONTAIN_X1_CHARACTERS(null, null)).containsExactly("COD0012", "null must contain null characters.");
    }

    @DisplayName("FAILED_TO_INTEGRATE_WITH_X0 formats parameter")
    @Test
    public void testFailedToIntegrateWithX0() {
        // Arrange & Act & Assert
        assertThat(MsgUtil.FAILED_TO_INTEGRATE_WITH_X0("ServiceA")).containsExactly("COD0013", "Failed to integrate with ServiceA.");
        assertThat(MsgUtil.FAILED_TO_INTEGRATE_WITH_X0(null)).containsExactly("COD0013", "Failed to integrate with null.");
    }

    @DisplayName("SERVICE_FAILURE_X0 formats parameter")
    @Test
    public void testServiceFailureX0() {
        // Arrange & Act & Assert
        assertThat(MsgUtil.SERVICE_FAILURE_X0("ServiceB")).containsExactly("COD0014", "Service failure ServiceB.");
        assertThat(MsgUtil.SERVICE_FAILURE_X0(null)).containsExactly("COD0014", "Service failure null.");
    }

    @DisplayName("Instantiate MsgUtil to default constructor")
    @Test
    public void testInstantiateMsgUtil() {
        // Arrange & Act & Assert
        MsgUtil instance = new MsgUtil();
        assertThat(instance).isNotNull();
    }
}
