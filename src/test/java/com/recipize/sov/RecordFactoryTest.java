package com.recipize.sov;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RecordFactoryTest {
    @Test
    public void shouldBuildEmployeeAwareRecord() {
        // Given
        String recordType = "VEST";
        String[] recordArray = new String[]{ recordType };

        EmployeeAwareRecord employeeAwareRecord = mock(EmployeeAwareRecord.class);

        EmployeeAwareRecordFactory employeeAwareRecordFactory = mock(EmployeeAwareRecordFactory.class);
        given(employeeAwareRecordFactory.build(recordArray)).willReturn(employeeAwareRecord);

        RecordFactory recordFactory = new RecordFactory();
        recordFactory.addFactory(recordType, employeeAwareRecordFactory);

        // When
        EmployeeAwareRecord result = recordFactory.build(recordArray);

        // Then
        assertThat(result).isEqualTo(employeeAwareRecord);
        verify(employeeAwareRecordFactory).build(recordArray);
    }

    @Test
    public void shouldThrowExceptionIfUnknownRecordTypeIsProvided() {
        // Given
        String recordType = "BLAH";
        String[] recordArray = new String[]{ recordType };

        RecordFactory recordFactory = new RecordFactory();

        // When
        try {
            recordFactory.build(recordArray);
        } catch (RuntimeException e) {
            assertThat(e)
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Trying to build an unknown record type");
        }
    }
}
