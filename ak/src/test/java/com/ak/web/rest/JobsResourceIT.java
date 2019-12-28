package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.Jobs;
import com.ak.repository.JobsRepository;
import com.ak.service.JobsService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.ak.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JobsResource} REST controller.
 */
@SpringBootTest(classes = AkApp.class)
public class JobsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_ESTIMATE = 1D;
    private static final Double UPDATED_ESTIMATE = 2D;

    private static final String DEFAULT_INVESTOR = "AAAAAAAAAA";
    private static final String UPDATED_INVESTOR = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private JobsRepository jobsRepository;

    @Autowired
    private JobsService jobsService;

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

    private MockMvc restJobsMockMvc;

    private Jobs jobs;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobsResource jobsResource = new JobsResource(jobsService);
        this.restJobsMockMvc = MockMvcBuilders.standaloneSetup(jobsResource)
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
    public static Jobs createEntity(EntityManager em) {
        Jobs jobs = new Jobs()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .estimate(DEFAULT_ESTIMATE)
            .investor(DEFAULT_INVESTOR)
            .address(DEFAULT_ADDRESS)
            .notes(DEFAULT_NOTES);
        return jobs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jobs createUpdatedEntity(EntityManager em) {
        Jobs jobs = new Jobs()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .estimate(UPDATED_ESTIMATE)
            .investor(UPDATED_INVESTOR)
            .address(UPDATED_ADDRESS)
            .notes(UPDATED_NOTES);
        return jobs;
    }

    @BeforeEach
    public void initTest() {
        jobs = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobs() throws Exception {
        int databaseSizeBeforeCreate = jobsRepository.findAll().size();

        // Create the Jobs
        restJobsMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobs)))
            .andExpect(status().isCreated());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeCreate + 1);
        Jobs testJobs = jobsList.get(jobsList.size() - 1);
        assertThat(testJobs.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testJobs.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobs.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testJobs.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testJobs.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testJobs.getEstimate()).isEqualTo(DEFAULT_ESTIMATE);
        assertThat(testJobs.getInvestor()).isEqualTo(DEFAULT_INVESTOR);
        assertThat(testJobs.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testJobs.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createJobsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobsRepository.findAll().size();

        // Create the Jobs with an existing ID
        jobs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobsMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobs)))
            .andExpect(status().isBadRequest());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobs() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList
        restJobsMockMvc.perform(get("/api/jobs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobs.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].estimate").value(hasItem(DEFAULT_ESTIMATE.doubleValue())))
            .andExpect(jsonPath("$.[*].investor").value(hasItem(DEFAULT_INVESTOR)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }
    
    @Test
    @Transactional
    public void getJobs() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get the jobs
        restJobsMockMvc.perform(get("/api/jobs/{id}", jobs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobs.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.estimate").value(DEFAULT_ESTIMATE.doubleValue()))
            .andExpect(jsonPath("$.investor").value(DEFAULT_INVESTOR))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    public void getNonExistingJobs() throws Exception {
        // Get the jobs
        restJobsMockMvc.perform(get("/api/jobs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobs() throws Exception {
        // Initialize the database
        jobsService.save(jobs);

        int databaseSizeBeforeUpdate = jobsRepository.findAll().size();

        // Update the jobs
        Jobs updatedJobs = jobsRepository.findById(jobs.getId()).get();
        // Disconnect from session so that the updates on updatedJobs are not directly saved in db
        em.detach(updatedJobs);
        updatedJobs
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .estimate(UPDATED_ESTIMATE)
            .investor(UPDATED_INVESTOR)
            .address(UPDATED_ADDRESS)
            .notes(UPDATED_NOTES);

        restJobsMockMvc.perform(put("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobs)))
            .andExpect(status().isOk());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeUpdate);
        Jobs testJobs = jobsList.get(jobsList.size() - 1);
        assertThat(testJobs.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJobs.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobs.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testJobs.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testJobs.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testJobs.getEstimate()).isEqualTo(UPDATED_ESTIMATE);
        assertThat(testJobs.getInvestor()).isEqualTo(UPDATED_INVESTOR);
        assertThat(testJobs.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testJobs.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingJobs() throws Exception {
        int databaseSizeBeforeUpdate = jobsRepository.findAll().size();

        // Create the Jobs

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobsMockMvc.perform(put("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobs)))
            .andExpect(status().isBadRequest());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobs() throws Exception {
        // Initialize the database
        jobsService.save(jobs);

        int databaseSizeBeforeDelete = jobsRepository.findAll().size();

        // Delete the jobs
        restJobsMockMvc.perform(delete("/api/jobs/{id}", jobs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
