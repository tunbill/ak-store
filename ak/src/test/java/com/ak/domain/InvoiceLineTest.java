package com.ak.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ak.web.rest.TestUtil;

public class InvoiceLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceLine.class);
        InvoiceLine invoiceLine1 = new InvoiceLine();
        invoiceLine1.setId(1L);
        InvoiceLine invoiceLine2 = new InvoiceLine();
        invoiceLine2.setId(invoiceLine1.getId());
        assertThat(invoiceLine1).isEqualTo(invoiceLine2);
        invoiceLine2.setId(2L);
        assertThat(invoiceLine1).isNotEqualTo(invoiceLine2);
        invoiceLine1.setId(null);
        assertThat(invoiceLine1).isNotEqualTo(invoiceLine2);
    }
}
