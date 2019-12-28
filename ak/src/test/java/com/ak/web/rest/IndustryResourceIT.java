package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.Industry;
import com.ak.repository.IndustryRepository;
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
 * Integration tests for the {@link IndustryResource} REST controller.
 */
@SpringBootTest(classes = AkApp.class)
public class IndustryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private IndustryRepository industryRepository;

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

    private MockMvc restIndustryMockMvc;

    private Industry industry;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndustryResource industryResource = new IndustryResource(industryRepository);
        this.restIndustryMockMvc = MockMvcBuilders.standaloneSetup(industryResource)
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
    public static Industry createEntity(EntityManager em) {
        Industry industry = new Industry()
            .name(DEFAULT_NAME);
        return industry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Industry createUpdatedEntity(EntityManager em) {
        Industry industry = new Industry()
            .name(UPDATED_NAME);
        return industry;
    }

    @BeforeEach
    public void initTest() {
        industry = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndustry() throws Exception {
        int databaseSizeBeforeCreate = industryRepository.findAll().size();

        // Create the Industry
        restIndustryMockMvc.perform(post("/api/industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industry)))
            .andExpect(status().isCreated());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeCreate + 1);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createIndustryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = industryRepository.findAll().size();

        // Create the Industry with an existing ID
        industry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustryMockMvc.perform(post("/api/industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industry)))
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryRepository.findAll().size();
        // set the field null
        industry.setName(null);

        // Create the Industry, which fails.

        restIndustryMockMvc.perform(post("/api/industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industry)))
            .andExpect(status().isBadRequest());

        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIndustries() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList
        restIndustryMockMvc.perform(get("/api/industries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getIndustry() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get the industry
        restIndustryMockMvc.perform(get("/api/industries/{id}", industry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(industry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingIndustry() throws Exception {
        // Get the industry
        restIndustryMockMvc.perform(get("/api/industries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndustry() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeUpdate = industryRepository.findAll().size();

        // Update the industry
        Industry updatedIndustry = industryRepository.findById(industry.getId()).get();
        // Disconnect from session so that the updates on updatedIndustry are not directly saved in db
        em.detach(updatedIndustry);
        updatedIndustry
            .name(UPDATED_NAME);

        restIndustryMockMvc.perform(put("/api/industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndustry)))
            .andExpect(status().isOk());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();

        // Create the Industry

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndustryMockMvc.perform(put("/api/industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industry)))
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIndustry() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeDelete = industryRepository.findAll().size();

        // Delete the industry
        restIndustryMockMvc.perform(delete("/api/industries/{id}", industry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
