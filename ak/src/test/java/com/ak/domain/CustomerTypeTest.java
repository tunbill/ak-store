package com.ak.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ak.web.rest.TestUtil;

public class CustomerTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerType.class);
        CustomerType customerType1 = new CustomerType();
        customerType1.setId(1L);
        CustomerType customerType2 = new CustomerType();
        customerType2.setId(customerType1.getId());
        assertThat(customerType1).isEqualTo(customerType2);
        customerType2.setId(2L);
        assertThat(customerType1).isNotEqualTo(customerType2);
        customerType1.setId(null);
        assertThat(customerType1).isNotEqualTo(customerType2);
    }
}
