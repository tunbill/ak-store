package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.Company;
import com.ak.domain.Industry;
import com.ak.domain.Province;
import com.ak.repository.CompanyRepository;
import com.ak.service.CompanyService;
import com.ak.web.rest.errors.ExceptionTranslator;
import com.ak.service.dto.CompanyCriteria;
import com.ak.service.CompanyQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.ak.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@SpringBootTest(classes = AkApp.class)
public class CompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_EMAIL = "]@#V.cl";
    private static final String UPDATED_EMAIL = "O@x._";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_NUM_OF_USERS = 1;
    private static final Integer UPDATED_NUM_OF_USERS = 2;
    private static final Integer SMALLER_NUM_OF_USERS = 1 - 1;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Instant DEFAULT_TIME_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TIME_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;
    private static final Long SMALLER_USER_ID = 1L - 1L;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyQueryService companyQueryService;

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

    private MockMvc restCompanyMockMvc;

    private Company company;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyResource companyResource = new CompanyResource(companyService, companyQueryService);
        this.restCompanyMockMvc = MockMvcBuilders.standaloneSetup(companyResource)
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
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .email(DEFAULT_EMAIL)
            .startDate(DEFAULT_START_DATE)
            .numOfUsers(DEFAULT_NUM_OF_USERS)
            .type(DEFAULT_TYPE)
            .isActive(DEFAULT_IS_ACTIVE)
            .timeCreated(DEFAULT_TIME_CREATED)
            .timeModified(DEFAULT_TIME_MODIFIED)
            .userId(DEFAULT_USER_ID);
        return company;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .startDate(UPDATED_START_DATE)
            .numOfUsers(UPDATED_NUM_OF_USERS)
            .type(UPDATED_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .timeCreated(UPDATED_TIME_CREATED)
            .timeModified(UPDATED_TIME_MODIFIED)
            .userId(UPDATED_USER_ID);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompany.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCompany.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testCompany.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompany.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCompany.getNumOfUsers()).isEqualTo(DEFAULT_NUM_OF_USERS);
        assertThat(testCompany.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCompany.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testCompany.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testCompany.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
        assertThat(testCompany.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company with an existing ID
        company.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setName(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].numOfUsers").value(hasItem(DEFAULT_NUM_OF_USERS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].timeCreated").value(hasItem(DEFAULT_TIME_CREATED.toString())))
            .andExpect(jsonPath("$.[*].timeModified").value(hasItem(DEFAULT_TIME_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.numOfUsers").value(DEFAULT_NUM_OF_USERS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.timeCreated").value(DEFAULT_TIME_CREATED.toString()))
            .andExpect(jsonPath("$.timeModified").value(DEFAULT_TIME_MODIFIED.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }


    @Test
    @Transactional
    public void getCompaniesByIdFiltering() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        Long id = company.getId();

        defaultCompanyShouldBeFound("id.equals=" + id);
        defaultCompanyShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompaniesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name equals to DEFAULT_NAME
        defaultCompanyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the companyList where name equals to UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name not equals to DEFAULT_NAME
        defaultCompanyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the companyList where name not equals to UPDATED_NAME
        defaultCompanyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCompanyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the companyList where name equals to UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name is not null
        defaultCompanyShouldBeFound("name.specified=true");

        // Get all the companyList where name is null
        defaultCompanyShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByNameContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name contains DEFAULT_NAME
        defaultCompanyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the companyList where name contains UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name does not contain DEFAULT_NAME
        defaultCompanyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the companyList where name does not contain UPDATED_NAME
        defaultCompanyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCompaniesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address equals to DEFAULT_ADDRESS
        defaultCompanyShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the companyList where address equals to UPDATED_ADDRESS
        defaultCompanyShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address not equals to DEFAULT_ADDRESS
        defaultCompanyShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the companyList where address not equals to UPDATED_ADDRESS
        defaultCompanyShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCompanyShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the companyList where address equals to UPDATED_ADDRESS
        defaultCompanyShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address is not null
        defaultCompanyShouldBeFound("address.specified=true");

        // Get all the companyList where address is null
        defaultCompanyShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByAddressContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address contains DEFAULT_ADDRESS
        defaultCompanyShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the companyList where address contains UPDATED_ADDRESS
        defaultCompanyShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address does not contain DEFAULT_ADDRESS
        defaultCompanyShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the companyList where address does not contain UPDATED_ADDRESS
        defaultCompanyShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllCompaniesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email equals to DEFAULT_EMAIL
        defaultCompanyShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the companyList where email equals to UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompaniesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email not equals to DEFAULT_EMAIL
        defaultCompanyShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the companyList where email not equals to UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompaniesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the companyList where email equals to UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompaniesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email is not null
        defaultCompanyShouldBeFound("email.specified=true");

        // Get all the companyList where email is null
        defaultCompanyShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByEmailContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email contains DEFAULT_EMAIL
        defaultCompanyShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the companyList where email contains UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompaniesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email does not contain DEFAULT_EMAIL
        defaultCompanyShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the companyList where email does not contain UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllCompaniesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where startDate equals to DEFAULT_START_DATE
        defaultCompanyShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the companyList where startDate equals to UPDATED_START_DATE
        defaultCompanyShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where startDate not equals to DEFAULT_START_DATE
        defaultCompanyShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the companyList where startDate not equals to UPDATED_START_DATE
        defaultCompanyShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultCompanyShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the companyList where startDate equals to UPDATED_START_DATE
        defaultCompanyShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where startDate is not null
        defaultCompanyShouldBeFound("startDate.specified=true");

        // Get all the companyList where startDate is null
        defaultCompanyShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultCompanyShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the companyList where startDate is greater than or equal to UPDATED_START_DATE
        defaultCompanyShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where startDate is less than or equal to DEFAULT_START_DATE
        defaultCompanyShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the companyList where startDate is less than or equal to SMALLER_START_DATE
        defaultCompanyShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where startDate is less than DEFAULT_START_DATE
        defaultCompanyShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the companyList where startDate is less than UPDATED_START_DATE
        defaultCompanyShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where startDate is greater than DEFAULT_START_DATE
        defaultCompanyShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the companyList where startDate is greater than SMALLER_START_DATE
        defaultCompanyShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllCompaniesByNumOfUsersIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where numOfUsers equals to DEFAULT_NUM_OF_USERS
        defaultCompanyShouldBeFound("numOfUsers.equals=" + DEFAULT_NUM_OF_USERS);

        // Get all the companyList where numOfUsers equals to UPDATED_NUM_OF_USERS
        defaultCompanyShouldNotBeFound("numOfUsers.equals=" + UPDATED_NUM_OF_USERS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNumOfUsersIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where numOfUsers not equals to DEFAULT_NUM_OF_USERS
        defaultCompanyShouldNotBeFound("numOfUsers.notEquals=" + DEFAULT_NUM_OF_USERS);

        // Get all the companyList where numOfUsers not equals to UPDATED_NUM_OF_USERS
        defaultCompanyShouldBeFound("numOfUsers.notEquals=" + UPDATED_NUM_OF_USERS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNumOfUsersIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where numOfUsers in DEFAULT_NUM_OF_USERS or UPDATED_NUM_OF_USERS
        defaultCompanyShouldBeFound("numOfUsers.in=" + DEFAULT_NUM_OF_USERS + "," + UPDATED_NUM_OF_USERS);

        // Get all the companyList where numOfUsers equals to UPDATED_NUM_OF_USERS
        defaultCompanyShouldNotBeFound("numOfUsers.in=" + UPDATED_NUM_OF_USERS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNumOfUsersIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where numOfUsers is not null
        defaultCompanyShouldBeFound("numOfUsers.specified=true");

        // Get all the companyList where numOfUsers is null
        defaultCompanyShouldNotBeFound("numOfUsers.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByNumOfUsersIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where numOfUsers is greater than or equal to DEFAULT_NUM_OF_USERS
        defaultCompanyShouldBeFound("numOfUsers.greaterThanOrEqual=" + DEFAULT_NUM_OF_USERS);

        // Get all the companyList where numOfUsers is greater than or equal to UPDATED_NUM_OF_USERS
        defaultCompanyShouldNotBeFound("numOfUsers.greaterThanOrEqual=" + UPDATED_NUM_OF_USERS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNumOfUsersIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where numOfUsers is less than or equal to DEFAULT_NUM_OF_USERS
        defaultCompanyShouldBeFound("numOfUsers.lessThanOrEqual=" + DEFAULT_NUM_OF_USERS);

        // Get all the companyList where numOfUsers is less than or equal to SMALLER_NUM_OF_USERS
        defaultCompanyShouldNotBeFound("numOfUsers.lessThanOrEqual=" + SMALLER_NUM_OF_USERS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNumOfUsersIsLessThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where numOfUsers is less than DEFAULT_NUM_OF_USERS
        defaultCompanyShouldNotBeFound("numOfUsers.lessThan=" + DEFAULT_NUM_OF_USERS);

        // Get all the companyList where numOfUsers is less than UPDATED_NUM_OF_USERS
        defaultCompanyShouldBeFound("numOfUsers.lessThan=" + UPDATED_NUM_OF_USERS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNumOfUsersIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where numOfUsers is greater than DEFAULT_NUM_OF_USERS
        defaultCompanyShouldNotBeFound("numOfUsers.greaterThan=" + DEFAULT_NUM_OF_USERS);

        // Get all the companyList where numOfUsers is greater than SMALLER_NUM_OF_USERS
        defaultCompanyShouldBeFound("numOfUsers.greaterThan=" + SMALLER_NUM_OF_USERS);
    }


    @Test
    @Transactional
    public void getAllCompaniesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where type equals to DEFAULT_TYPE
        defaultCompanyShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the companyList where type equals to UPDATED_TYPE
        defaultCompanyShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where type not equals to DEFAULT_TYPE
        defaultCompanyShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the companyList where type not equals to UPDATED_TYPE
        defaultCompanyShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCompanyShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the companyList where type equals to UPDATED_TYPE
        defaultCompanyShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where type is not null
        defaultCompanyShouldBeFound("type.specified=true");

        // Get all the companyList where type is null
        defaultCompanyShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByTypeContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where type contains DEFAULT_TYPE
        defaultCompanyShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the companyList where type contains UPDATED_TYPE
        defaultCompanyShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where type does not contain DEFAULT_TYPE
        defaultCompanyShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the companyList where type does not contain UPDATED_TYPE
        defaultCompanyShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllCompaniesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where isActive equals to DEFAULT_IS_ACTIVE
        defaultCompanyShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the companyList where isActive equals to UPDATED_IS_ACTIVE
        defaultCompanyShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultCompanyShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the companyList where isActive not equals to UPDATED_IS_ACTIVE
        defaultCompanyShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultCompanyShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the companyList where isActive equals to UPDATED_IS_ACTIVE
        defaultCompanyShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where isActive is not null
        defaultCompanyShouldBeFound("isActive.specified=true");

        // Get all the companyList where isActive is null
        defaultCompanyShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByTimeCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where timeCreated equals to DEFAULT_TIME_CREATED
        defaultCompanyShouldBeFound("timeCreated.equals=" + DEFAULT_TIME_CREATED);

        // Get all the companyList where timeCreated equals to UPDATED_TIME_CREATED
        defaultCompanyShouldNotBeFound("timeCreated.equals=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTimeCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where timeCreated not equals to DEFAULT_TIME_CREATED
        defaultCompanyShouldNotBeFound("timeCreated.notEquals=" + DEFAULT_TIME_CREATED);

        // Get all the companyList where timeCreated not equals to UPDATED_TIME_CREATED
        defaultCompanyShouldBeFound("timeCreated.notEquals=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTimeCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where timeCreated in DEFAULT_TIME_CREATED or UPDATED_TIME_CREATED
        defaultCompanyShouldBeFound("timeCreated.in=" + DEFAULT_TIME_CREATED + "," + UPDATED_TIME_CREATED);

        // Get all the companyList where timeCreated equals to UPDATED_TIME_CREATED
        defaultCompanyShouldNotBeFound("timeCreated.in=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTimeCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where timeCreated is not null
        defaultCompanyShouldBeFound("timeCreated.specified=true");

        // Get all the companyList where timeCreated is null
        defaultCompanyShouldNotBeFound("timeCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByTimeModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where timeModified equals to DEFAULT_TIME_MODIFIED
        defaultCompanyShouldBeFound("timeModified.equals=" + DEFAULT_TIME_MODIFIED);

        // Get all the companyList where timeModified equals to UPDATED_TIME_MODIFIED
        defaultCompanyShouldNotBeFound("timeModified.equals=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTimeModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where timeModified not equals to DEFAULT_TIME_MODIFIED
        defaultCompanyShouldNotBeFound("timeModified.notEquals=" + DEFAULT_TIME_MODIFIED);

        // Get all the companyList where timeModified not equals to UPDATED_TIME_MODIFIED
        defaultCompanyShouldBeFound("timeModified.notEquals=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTimeModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where timeModified in DEFAULT_TIME_MODIFIED or UPDATED_TIME_MODIFIED
        defaultCompanyShouldBeFound("timeModified.in=" + DEFAULT_TIME_MODIFIED + "," + UPDATED_TIME_MODIFIED);

        // Get all the companyList where timeModified equals to UPDATED_TIME_MODIFIED
        defaultCompanyShouldNotBeFound("timeModified.in=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTimeModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where timeModified is not null
        defaultCompanyShouldBeFound("timeModified.specified=true");

        // Get all the companyList where timeModified is null
        defaultCompanyShouldNotBeFound("timeModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where userId equals to DEFAULT_USER_ID
        defaultCompanyShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the companyList where userId equals to UPDATED_USER_ID
        defaultCompanyShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompaniesByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where userId not equals to DEFAULT_USER_ID
        defaultCompanyShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the companyList where userId not equals to UPDATED_USER_ID
        defaultCompanyShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompaniesByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultCompanyShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the companyList where userId equals to UPDATED_USER_ID
        defaultCompanyShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompaniesByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where userId is not null
        defaultCompanyShouldBeFound("userId.specified=true");

        // Get all the companyList where userId is null
        defaultCompanyShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where userId is greater than or equal to DEFAULT_USER_ID
        defaultCompanyShouldBeFound("userId.greaterThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the companyList where userId is greater than or equal to UPDATED_USER_ID
        defaultCompanyShouldNotBeFound("userId.greaterThanOrEqual=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompaniesByUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where userId is less than or equal to DEFAULT_USER_ID
        defaultCompanyShouldBeFound("userId.lessThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the companyList where userId is less than or equal to SMALLER_USER_ID
        defaultCompanyShouldNotBeFound("userId.lessThanOrEqual=" + SMALLER_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompaniesByUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where userId is less than DEFAULT_USER_ID
        defaultCompanyShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);

        // Get all the companyList where userId is less than UPDATED_USER_ID
        defaultCompanyShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompaniesByUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where userId is greater than DEFAULT_USER_ID
        defaultCompanyShouldNotBeFound("userId.greaterThan=" + DEFAULT_USER_ID);

        // Get all the companyList where userId is greater than SMALLER_USER_ID
        defaultCompanyShouldBeFound("userId.greaterThan=" + SMALLER_USER_ID);
    }


    @Test
    @Transactional
    public void getAllCompaniesByIndustryIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        Industry industry = IndustryResourceIT.createEntity(em);
        em.persist(industry);
        em.flush();
        company.setIndustry(industry);
        companyRepository.saveAndFlush(company);
        Long industryId = industry.getId();

        // Get all the companyList where industry equals to industryId
        defaultCompanyShouldBeFound("industryId.equals=" + industryId);

        // Get all the companyList where industry equals to industryId + 1
        defaultCompanyShouldNotBeFound("industryId.equals=" + (industryId + 1));
    }


    @Test
    @Transactional
    public void getAllCompaniesByProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        Province province = ProvinceResourceIT.createEntity(em);
        em.persist(province);
        em.flush();
        company.setProvince(province);
        companyRepository.saveAndFlush(company);
        Long provinceId = province.getId();

        // Get all the companyList where province equals to provinceId
        defaultCompanyShouldBeFound("provinceId.equals=" + provinceId);

        // Get all the companyList where province equals to provinceId + 1
        defaultCompanyShouldNotBeFound("provinceId.equals=" + (provinceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyShouldBeFound(String filter) throws Exception {
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].numOfUsers").value(hasItem(DEFAULT_NUM_OF_USERS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].timeCreated").value(hasItem(DEFAULT_TIME_CREATED.toString())))
            .andExpect(jsonPath("$.[*].timeModified").value(hasItem(DEFAULT_TIME_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));

        // Check, that the count call also returns 1
        restCompanyMockMvc.perform(get("/api/companies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyShouldNotBeFound(String filter) throws Exception {
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyMockMvc.perform(get("/api/companies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompany() throws Exception {
        // Initialize the database
        companyService.save(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .startDate(UPDATED_START_DATE)
            .numOfUsers(UPDATED_NUM_OF_USERS)
            .type(UPDATED_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .timeCreated(UPDATED_TIME_CREATED)
            .timeModified(UPDATED_TIME_MODIFIED)
            .userId(UPDATED_USER_ID);

        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompany)))
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompany.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCompany.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCompany.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompany.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCompany.getNumOfUsers()).isEqualTo(UPDATED_NUM_OF_USERS);
        assertThat(testCompany.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCompany.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testCompany.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testCompany.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
        assertThat(testCompany.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Create the Company

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyService.save(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc.perform(delete("/api/companies/{id}", company.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
