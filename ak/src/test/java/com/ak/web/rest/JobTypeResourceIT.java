package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.JobType;
import com.ak.repository.JobTypeRepository;
import com.ak.service.JobTypeService;
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
 * Integration tests for the {@link JobTypeResource} REST controller.
 */
@SpringBootTest(classes = AkApp.class)
public class JobTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Autowired
    private JobTypeService jobTypeService;

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

    private MockMvc restJobTypeMockMvc;

    private JobType jobType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobTypeResource jobTypeResource = new JobTypeResource(jobTypeService);
        this.restJobTypeMockMvc = MockMvcBuilders.standaloneSetup(jobTypeResource)
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
    public static JobType createEntity(EntityManager em) {
        JobType jobType = new JobType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return jobType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobType createUpdatedEntity(EntityManager em) {
        JobType jobType = new JobType()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        return jobType;
    }

    @BeforeEach
    public void initTest() {
        jobType = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobType() throws Exception {
        int databaseSizeBeforeCreate = jobTypeRepository.findAll().size();

        // Create the JobType
        restJobTypeMockMvc.perform(post("/api/job-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobType)))
            .andExpect(status().isCreated());

        // Validate the JobType in the database
        List<JobType> jobTypeList = jobTypeRepository.findAll();
        assertThat(jobTypeList).hasSize(databaseSizeBeforeCreate + 1);
        JobType testJobType = jobTypeList.get(jobTypeList.size() - 1);
        assertThat(testJobType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testJobType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createJobTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobTypeRepository.findAll().size();

        // Create the JobType with an existing ID
        jobType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobTypeMockMvc.perform(post("/api/job-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobType)))
            .andExpect(status().isBadRequest());

        // Validate the JobType in the database
        List<JobType> jobTypeList = jobTypeRepository.findAll();
        assertThat(jobTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobTypeRepository.findAll().size();
        // set the field null
        jobType.setName(null);

        // Create the JobType, which fails.

        restJobTypeMockMvc.perform(post("/api/job-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobType)))
            .andExpect(status().isBadRequest());

        List<JobType> jobTypeList = jobTypeRepository.findAll();
        assertThat(jobTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobTypes() throws Exception {
        // Initialize the database
        jobTypeRepository.saveAndFlush(jobType);

        // Get all the jobTypeList
        restJobTypeMockMvc.perform(get("/api/job-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getJobType() throws Exception {
        // Initialize the database
        jobTypeRepository.saveAndFlush(jobType);

        // Get the jobType
        restJobTypeMockMvc.perform(get("/api/job-types/{id}", jobType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingJobType() throws Exception {
        // Get the jobType
        restJobTypeMockMvc.perform(get("/api/job-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobType() throws Exception {
        // Initialize the database
        jobTypeService.save(jobType);

        int databaseSizeBeforeUpdate = jobTypeRepository.findAll().size();

        // Update the jobType
        JobType updatedJobType = jobTypeRepository.findById(jobType.getId()).get();
        // Disconnect from session so that the updates on updatedJobType are not directly saved in db
        em.detach(updatedJobType);
        updatedJobType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restJobTypeMockMvc.perform(put("/api/job-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobType)))
            .andExpect(status().isOk());

        // Validate the JobType in the database
        List<JobType> jobTypeList = jobTypeRepository.findAll();
        assertThat(jobTypeList).hasSize(databaseSizeBeforeUpdate);
        JobType testJobType = jobTypeList.get(jobTypeList.size() - 1);
        assertThat(testJobType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJobType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingJobType() throws Exception {
        int databaseSizeBeforeUpdate = jobTypeRepository.findAll().size();

        // Create the JobType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobTypeMockMvc.perform(put("/api/job-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobType)))
            .andExpect(status().isBadRequest());

        // Validate the JobType in the database
        List<JobType> jobTypeList = jobTypeRepository.findAll();
        assertThat(jobTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobType() throws Exception {
        // Initialize the database
        jobTypeService.save(jobType);

        int databaseSizeBeforeDelete = jobTypeRepository.findAll().size();

        // Delete the jobType
        restJobTypeMockMvc.perform(delete("/api/job-types/{id}", jobType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobType> jobTypeList = jobTypeRepository.findAll();
        assertThat(jobTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
