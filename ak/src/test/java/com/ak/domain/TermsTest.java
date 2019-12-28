package com.ak.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ak.web.rest.TestUtil;

public class TermsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Terms.class);
        Terms terms1 = new Terms();
        terms1.setId(1L);
        Terms terms2 = new Terms();
        terms2.setId(terms1.getId());
        assertThat(terms1).isEqualTo(terms2);
        terms2.setId(2L);
        assertThat(terms1).isNotEqualTo(terms2);
        terms1.setId(null);
        assertThat(terms1).isNotEqualTo(terms2);
    }
}
