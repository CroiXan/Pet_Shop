package poc.petshop.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import poc.petshop.demo.model.ParsedInt;

public class ServiceUtilsTest {

    @Test
    public void notStringValidMothTest(){

        ServiceUtils serviceUtils = new ServiceUtils();

        ParsedInt result = serviceUtils.validateMonth("not-validmonth");

        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertEquals("valor de mes no valido", result.getErrorMessage());

    }

    @Test
    public void notValidMonthRange(){

        ServiceUtils serviceUtils = new ServiceUtils();

        ParsedInt result = serviceUtils.validateMonth("30");

        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertEquals("numero de mes no valido", result.getErrorMessage());

    }

    @Test
    public void validMonthTest(){

        ServiceUtils serviceUtils = new ServiceUtils();

        ParsedInt result = serviceUtils.validateMonth("10");

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(10, result.getResultInt());

    }

}
