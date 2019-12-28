package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.Terms;
import com.ak.repository.TermsRepository;
import com.ak.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ak.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TermsResource} REST controller.
 */
@SpringBootTest(classes = AkApp.class)
public class TermsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DAY_OF_MONTH_DUE = 1;
    private static final Integer UPDATED_DAY_OF_MONTH_DUE = 2;

    private static final Integer DEFAULT_DUE_NEXT_MONTH_DAYS = 1;
    private static final Integer UPDATED_DUE_NEXT_MONTH_DAYS = 2;

    private static final Integer DEFAULT_DISCOUNT_DAY_OF_MONTH = 1;
    private static final Integer UPDATED_DISCOUNT_DAY_OF_MONTH = 2;

    private static final Double DEFAULT_DISCOUNT_PCT = 1D;
    private static final Double UPDATED_DISCOUNT_PCT = 2D;

    @Autowired
    private TermsRepository termsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTermsMockMvc;

    private Terms terms;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TermsResource termsResource = new TermsResource(termsRepository);
        this.restTermsMockMvc = MockMvcBuilders.standaloneSetup(termsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Terms createEntity(EntityManager em) {
        Terms terms = new Terms()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .dayOfMonthDue(DEFAULT_DAY_OF_MONTH_DUE)
            .dueNextMonthDays(DEFAULT_DUE_NEXT_MONTH_DAYS)
            .discountDayOfMonth(DEFAULT_DISCOUNT_DAY_OF_MONTH)
            .discountPct(DEFAULT_DISCOUNT_PCT);
        return terms;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Terms createUpdatedEntity(EntityManager em) {
        Terms terms = new Terms()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .dayOfMonthDue(UPDATED_DAY_OF_MONTH_DUE)
            .dueNextMonthDays(UPDATED_DUE_NEXT_MONTH_DAYS)
            .discountDayOfMonth(UPDATED_DISCOUNT_DAY_OF_MONTH)
            .discountPct(UPDATED_DISCOUNT_PCT);
        return terms;
    }

    @BeforeEach
    public void initTest() {
        terms = createEntity(em);
    }

    @Test
    @Transactional
    public void createTerms() throws Exception {
        int databaseSizeBeforeCreate = termsRepository.findAll().size();

        // Create the Terms
        restTermsMockMvc.perform(post("/api/terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(terms)))
            .andExpect(status().isCreated());

        // Validate the Terms in the database
        List<Terms> termsList = termsRepository.findAll();
        assertThat(termsList).hasSize(databaseSizeBeforeCreate + 1);
        Terms testTerms = termsList.get(termsList.size() - 1);
        assertThat(testTerms.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTerms.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTerms.getDayOfMonthDue()).isEqualTo(DEFAULT_DAY_OF_MONTH_DUE);
        assertThat(testTerms.getDueNextMonthDays()).isEqualTo(DEFAULT_DUE_NEXT_MONTH_DAYS);
        assertThat(testTerms.getDiscountDayOfMonth()).isEqualTo(DEFAULT_DISCOUNT_DAY_OF_MONTH);
        assertThat(testTerms.getDiscountPct()).isEqualTo(DEFAULT_DISCOUNT_PCT);
    }

    @Test
    @Transactional
    public void createTermsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = termsRepository.findAll().size();

        // Create the Terms with an existing ID
        terms.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermsMockMvc.perform(post("/api/terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(terms)))
            .andExpect(status().isBadRequest());

        // Validate the Terms in the database
        List<Terms> termsList = termsRepository.findAll();
        assertThat(termsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = termsRepository.findAll().size();
        // set the field null
        terms.setName(null);

        // Create the Terms, which fails.

        restTermsMockMvc.perform(post("/api/terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(terms)))
            .andExpect(status().isBadRequest());

        List<Terms> termsList = termsRepository.findAll();
        assertThat(termsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTerms() throws Exception {
        // Initialize the database
        termsRepository.saveAndFlush(terms);

        // Get all the termsList
        restTermsMockMvc.perform(get("/api/terms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terms.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dayOfMonthDue").value(hasItem(DEFAULT_DAY_OF_MONTH_DUE)))
            .andExpect(jsonPath("$.[*].dueNextMonthDays").value(hasItem(DEFAULT_DUE_NEXT_MONTH_DAYS)))
            .andExpect(jsonPath("$.[*].discountDayOfMonth").value(hasItem(DEFAULT_DISCOUNT_DAY_OF_MONTH)))
            .andExpect(jsonPath("$.[*].discountPct").value(hasItem(DEFAULT_DISCOUNT_PCT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTerms() throws Exception {
        // Initialize the database
        termsRepository.saveAndFlush(terms);

        // Get the terms
        restTermsMockMvc.perform(get("/api/terms/{id}", terms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(terms.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dayOfMonthDue").value(DEFAULT_DAY_OF_MONTH_DUE))
            .andExpect(jsonPath("$.dueNextMonthDays").value(DEFAULT_DUE_NEXT_MONTH_DAYS))
            .andExpect(jsonPath("$.discountDayOfMonth").value(DEFAULT_DISCOUNT_DAY_OF_MONTH))
            .andExpect(jsonPath("$.discountPct").value(DEFAULT_DISCOUNT_PCT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTerms() throws Exception {
        // Get the terms
        restTermsMockMvc.perform(get("/api/terms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerms() throws Exception {
        // Initialize the database
        termsRepository.saveAndFlush(terms);

        int databaseSizeBeforeUpdate = termsRepository.findAll().size();

        // Update the terms
        Terms updatedTerms = termsRepository.findById(terms.getId()).get();
        // Disconnect from session so that the updates on updatedTerms are not directly saved in db
        em.detach(updatedTerms);
        updatedTerms
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .dayOfMonthDue(UPDATED_DAY_OF_MONTH_DUE)
            .dueNextMonthDays(UPDATED_DUE_NEXT_MONTH_DAYS)
            .discountDayOfMonth(UPDATED_DISCOUNT_DAY_OF_MONTH)
            .discountPct(UPDATED_DISCOUNT_PCT);

        restTermsMockMvc.perform(put("/api/terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTerms)))
            .andExpect(status().isOk());

        // Validate the Terms in the database
        List<Terms> termsList = termsRepository.findAll();
        assertThat(termsList).hasSize(databaseSizeBeforeUpdate);
        Terms testTerms = termsList.get(termsList.size() - 1);
        assertThat(testTerms.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTerms.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTerms.getDayOfMonthDue()).isEqualTo(UPDATED_DAY_OF_MONTH_DUE);
        assertThat(testTerms.getDueNextMonthDays()).isEqualTo(UPDATED_DUE_NEXT_MONTH_DAYS);
        assertThat(testTerms.getDiscountDayOfMonth()).isEqualTo(UPDATED_DISCOUNT_DAY_OF_MONTH);
        assertThat(testTerms.getDiscountPct()).isEqualTo(UPDATED_DISCOUNT_PCT);
    }

    @Test
    @Transactional
    public void updateNonExistingTerms() throws Exception {
        int databaseSizeBeforeUpdate = termsRepository.findAll().size();

        // Create the Terms

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermsMockMvc.perform(put("/api/terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(terms)))
            .andExpect(status().isBadRequest());

        // Validate the Terms in the database
        List<Terms> termsList = termsRepository.findAll();
        assertThat(termsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTerms() throws Exception {
        // Initialize the database
        termsRepository.saveAndFlush(terms);

        int databaseSizeBeforeDelete = termsRepository.findAll().size();

        // Delete the terms
        restTermsMockMvc.perform(delete("/api/terms/{id}", terms.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Terms> termsList = termsRepository.findAll();
        assertThat(termsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
