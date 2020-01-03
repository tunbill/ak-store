package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.Employee;
import com.ak.domain.Invoice;
import com.ak.domain.Department;
import com.ak.repository.EmployeeRepository;
import com.ak.service.EmployeeService;
import com.ak.web.rest.errors.ExceptionTranslator;
import com.ak.service.dto.EmployeeCriteria;
import com.ak.service.EmployeeQueryService;

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
import java.math.BigDecimal;
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
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@SpringBootTest(classes = AkApp.class)
public class EmployeeResourceIT {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEX = 1;
    private static final Integer UPDATED_SEX = 2;
    private static final Integer SMALLER_SEX = 1 - 1;

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTHDAY = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_IDENTITY_CARD = "AAAAAAAAAA";
    private static final String UPDATED_IDENTITY_CARD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_IDENTITY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IDENTITY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_IDENTITY_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_IDENTITY_ISSUE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTITY_ISSUE = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TAX_CODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SALARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALARY = new BigDecimal(2);
    private static final BigDecimal SMALLER_SALARY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SALARY_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALARY_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SALARY_RATE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SALARY_SECURITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALARY_SECURITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_SALARY_SECURITY = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_NUM_OF_DEPENDS = 1;
    private static final Integer UPDATED_NUM_OF_DEPENDS = 2;
    private static final Integer SMALLER_NUM_OF_DEPENDS = 1 - 1;

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "G@-`.+^";
    private static final String UPDATED_EMAIL = "&W@Jd.Zz";

    private static final String DEFAULT_BANK_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NODES = "AAAAAAAAAA";
    private static final String UPDATED_NODES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Instant DEFAULT_TIME_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TIME_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_USER_ID_CREATED = 1L;
    private static final Long UPDATED_USER_ID_CREATED = 2L;
    private static final Long SMALLER_USER_ID_CREATED = 1L - 1L;

    private static final Long DEFAULT_USER_ID_MODIFIED = 1L;
    private static final Long UPDATED_USER_ID_MODIFIED = 2L;
    private static final Long SMALLER_USER_ID_MODIFIED = 1L - 1L;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeQueryService employeeQueryService;

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

    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeResource employeeResource = new EmployeeResource(employeeService, employeeQueryService);
        this.restEmployeeMockMvc = MockMvcBuilders.standaloneSetup(employeeResource)
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
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .companyId(DEFAULT_COMPANY_ID)
            .code(DEFAULT_CODE)
            .fullName(DEFAULT_FULL_NAME)
            .sex(DEFAULT_SEX)
            .birthday(DEFAULT_BIRTHDAY)
            .identityCard(DEFAULT_IDENTITY_CARD)
            .identityDate(DEFAULT_IDENTITY_DATE)
            .identityIssue(DEFAULT_IDENTITY_ISSUE)
            .position(DEFAULT_POSITION)
            .taxCode(DEFAULT_TAX_CODE)
            .salary(DEFAULT_SALARY)
            .salaryRate(DEFAULT_SALARY_RATE)
            .salarySecurity(DEFAULT_SALARY_SECURITY)
            .numOfDepends(DEFAULT_NUM_OF_DEPENDS)
            .phone(DEFAULT_PHONE)
            .mobile(DEFAULT_MOBILE)
            .email(DEFAULT_EMAIL)
            .bankAccount(DEFAULT_BANK_ACCOUNT)
            .bankName(DEFAULT_BANK_NAME)
            .nodes(DEFAULT_NODES)
            .isActive(DEFAULT_IS_ACTIVE)
            .timeCreated(DEFAULT_TIME_CREATED)
            .timeModified(DEFAULT_TIME_MODIFIED)
            .userIdCreated(DEFAULT_USER_ID_CREATED)
            .userIdModified(DEFAULT_USER_ID_MODIFIED);
        return employee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .companyId(UPDATED_COMPANY_ID)
            .code(UPDATED_CODE)
            .fullName(UPDATED_FULL_NAME)
            .sex(UPDATED_SEX)
            .birthday(UPDATED_BIRTHDAY)
            .identityCard(UPDATED_IDENTITY_CARD)
            .identityDate(UPDATED_IDENTITY_DATE)
            .identityIssue(UPDATED_IDENTITY_ISSUE)
            .position(UPDATED_POSITION)
            .taxCode(UPDATED_TAX_CODE)
            .salary(UPDATED_SALARY)
            .salaryRate(UPDATED_SALARY_RATE)
            .salarySecurity(UPDATED_SALARY_SECURITY)
            .numOfDepends(UPDATED_NUM_OF_DEPENDS)
            .phone(UPDATED_PHONE)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .bankName(UPDATED_BANK_NAME)
            .nodes(UPDATED_NODES)
            .isActive(UPDATED_IS_ACTIVE)
            .timeCreated(UPDATED_TIME_CREATED)
            .timeModified(UPDATED_TIME_MODIFIED)
            .userIdCreated(UPDATED_USER_ID_CREATED)
            .userIdModified(UPDATED_USER_ID_MODIFIED);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testEmployee.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEmployee.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testEmployee.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testEmployee.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testEmployee.getIdentityCard()).isEqualTo(DEFAULT_IDENTITY_CARD);
        assertThat(testEmployee.getIdentityDate()).isEqualTo(DEFAULT_IDENTITY_DATE);
        assertThat(testEmployee.getIdentityIssue()).isEqualTo(DEFAULT_IDENTITY_ISSUE);
        assertThat(testEmployee.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testEmployee.getTaxCode()).isEqualTo(DEFAULT_TAX_CODE);
        assertThat(testEmployee.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testEmployee.getSalaryRate()).isEqualTo(DEFAULT_SALARY_RATE);
        assertThat(testEmployee.getSalarySecurity()).isEqualTo(DEFAULT_SALARY_SECURITY);
        assertThat(testEmployee.getNumOfDepends()).isEqualTo(DEFAULT_NUM_OF_DEPENDS);
        assertThat(testEmployee.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testEmployee.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testEmployee.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testEmployee.getNodes()).isEqualTo(DEFAULT_NODES);
        assertThat(testEmployee.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testEmployee.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testEmployee.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
        assertThat(testEmployee.getUserIdCreated()).isEqualTo(DEFAULT_USER_ID_CREATED);
        assertThat(testEmployee.getUserIdModified()).isEqualTo(DEFAULT_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void createEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee with an existing ID
        employee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].identityCard").value(hasItem(DEFAULT_IDENTITY_CARD)))
            .andExpect(jsonPath("$.[*].identityDate").value(hasItem(DEFAULT_IDENTITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].identityIssue").value(hasItem(DEFAULT_IDENTITY_ISSUE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].taxCode").value(hasItem(DEFAULT_TAX_CODE)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].salaryRate").value(hasItem(DEFAULT_SALARY_RATE.intValue())))
            .andExpect(jsonPath("$.[*].salarySecurity").value(hasItem(DEFAULT_SALARY_SECURITY.intValue())))
            .andExpect(jsonPath("$.[*].numOfDepends").value(hasItem(DEFAULT_NUM_OF_DEPENDS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].nodes").value(hasItem(DEFAULT_NODES)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].timeCreated").value(hasItem(DEFAULT_TIME_CREATED.toString())))
            .andExpect(jsonPath("$.[*].timeModified").value(hasItem(DEFAULT_TIME_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].userIdCreated").value(hasItem(DEFAULT_USER_ID_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userIdModified").value(hasItem(DEFAULT_USER_ID_MODIFIED.intValue())));
    }
    
    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.identityCard").value(DEFAULT_IDENTITY_CARD))
            .andExpect(jsonPath("$.identityDate").value(DEFAULT_IDENTITY_DATE.toString()))
            .andExpect(jsonPath("$.identityIssue").value(DEFAULT_IDENTITY_ISSUE))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.taxCode").value(DEFAULT_TAX_CODE))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.intValue()))
            .andExpect(jsonPath("$.salaryRate").value(DEFAULT_SALARY_RATE.intValue()))
            .andExpect(jsonPath("$.salarySecurity").value(DEFAULT_SALARY_SECURITY.intValue()))
            .andExpect(jsonPath("$.numOfDepends").value(DEFAULT_NUM_OF_DEPENDS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.nodes").value(DEFAULT_NODES))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.timeCreated").value(DEFAULT_TIME_CREATED.toString()))
            .andExpect(jsonPath("$.timeModified").value(DEFAULT_TIME_MODIFIED.toString()))
            .andExpect(jsonPath("$.userIdCreated").value(DEFAULT_USER_ID_CREATED.intValue()))
            .andExpect(jsonPath("$.userIdModified").value(DEFAULT_USER_ID_MODIFIED.intValue()));
    }


    @Test
    @Transactional
    public void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        Long id = employee.getId();

        defaultEmployeeShouldBeFound("id.equals=" + id);
        defaultEmployeeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyId equals to DEFAULT_COMPANY_ID
        defaultEmployeeShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the employeeList where companyId equals to UPDATED_COMPANY_ID
        defaultEmployeeShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCompanyIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyId not equals to DEFAULT_COMPANY_ID
        defaultEmployeeShouldNotBeFound("companyId.notEquals=" + DEFAULT_COMPANY_ID);

        // Get all the employeeList where companyId not equals to UPDATED_COMPANY_ID
        defaultEmployeeShouldBeFound("companyId.notEquals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultEmployeeShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the employeeList where companyId equals to UPDATED_COMPANY_ID
        defaultEmployeeShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyId is not null
        defaultEmployeeShouldBeFound("companyId.specified=true");

        // Get all the employeeList where companyId is null
        defaultEmployeeShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultEmployeeShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the employeeList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultEmployeeShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultEmployeeShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the employeeList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultEmployeeShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyId is less than DEFAULT_COMPANY_ID
        defaultEmployeeShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the employeeList where companyId is less than UPDATED_COMPANY_ID
        defaultEmployeeShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where companyId is greater than DEFAULT_COMPANY_ID
        defaultEmployeeShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the employeeList where companyId is greater than SMALLER_COMPANY_ID
        defaultEmployeeShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where code equals to DEFAULT_CODE
        defaultEmployeeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the employeeList where code equals to UPDATED_CODE
        defaultEmployeeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where code not equals to DEFAULT_CODE
        defaultEmployeeShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the employeeList where code not equals to UPDATED_CODE
        defaultEmployeeShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultEmployeeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the employeeList where code equals to UPDATED_CODE
        defaultEmployeeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where code is not null
        defaultEmployeeShouldBeFound("code.specified=true");

        // Get all the employeeList where code is null
        defaultEmployeeShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByCodeContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where code contains DEFAULT_CODE
        defaultEmployeeShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the employeeList where code contains UPDATED_CODE
        defaultEmployeeShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where code does not contain DEFAULT_CODE
        defaultEmployeeShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the employeeList where code does not contain UPDATED_CODE
        defaultEmployeeShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName equals to DEFAULT_FULL_NAME
        defaultEmployeeShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the employeeList where fullName equals to UPDATED_FULL_NAME
        defaultEmployeeShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName not equals to DEFAULT_FULL_NAME
        defaultEmployeeShouldNotBeFound("fullName.notEquals=" + DEFAULT_FULL_NAME);

        // Get all the employeeList where fullName not equals to UPDATED_FULL_NAME
        defaultEmployeeShouldBeFound("fullName.notEquals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultEmployeeShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the employeeList where fullName equals to UPDATED_FULL_NAME
        defaultEmployeeShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName is not null
        defaultEmployeeShouldBeFound("fullName.specified=true");

        // Get all the employeeList where fullName is null
        defaultEmployeeShouldNotBeFound("fullName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByFullNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName contains DEFAULT_FULL_NAME
        defaultEmployeeShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the employeeList where fullName contains UPDATED_FULL_NAME
        defaultEmployeeShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName does not contain DEFAULT_FULL_NAME
        defaultEmployeeShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the employeeList where fullName does not contain UPDATED_FULL_NAME
        defaultEmployeeShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeesBySexIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sex equals to DEFAULT_SEX
        defaultEmployeeShouldBeFound("sex.equals=" + DEFAULT_SEX);

        // Get all the employeeList where sex equals to UPDATED_SEX
        defaultEmployeeShouldNotBeFound("sex.equals=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySexIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sex not equals to DEFAULT_SEX
        defaultEmployeeShouldNotBeFound("sex.notEquals=" + DEFAULT_SEX);

        // Get all the employeeList where sex not equals to UPDATED_SEX
        defaultEmployeeShouldBeFound("sex.notEquals=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySexIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sex in DEFAULT_SEX or UPDATED_SEX
        defaultEmployeeShouldBeFound("sex.in=" + DEFAULT_SEX + "," + UPDATED_SEX);

        // Get all the employeeList where sex equals to UPDATED_SEX
        defaultEmployeeShouldNotBeFound("sex.in=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySexIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sex is not null
        defaultEmployeeShouldBeFound("sex.specified=true");

        // Get all the employeeList where sex is null
        defaultEmployeeShouldNotBeFound("sex.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesBySexIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sex is greater than or equal to DEFAULT_SEX
        defaultEmployeeShouldBeFound("sex.greaterThanOrEqual=" + DEFAULT_SEX);

        // Get all the employeeList where sex is greater than or equal to UPDATED_SEX
        defaultEmployeeShouldNotBeFound("sex.greaterThanOrEqual=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySexIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sex is less than or equal to DEFAULT_SEX
        defaultEmployeeShouldBeFound("sex.lessThanOrEqual=" + DEFAULT_SEX);

        // Get all the employeeList where sex is less than or equal to SMALLER_SEX
        defaultEmployeeShouldNotBeFound("sex.lessThanOrEqual=" + SMALLER_SEX);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySexIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sex is less than DEFAULT_SEX
        defaultEmployeeShouldNotBeFound("sex.lessThan=" + DEFAULT_SEX);

        // Get all the employeeList where sex is less than UPDATED_SEX
        defaultEmployeeShouldBeFound("sex.lessThan=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySexIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where sex is greater than DEFAULT_SEX
        defaultEmployeeShouldNotBeFound("sex.greaterThan=" + DEFAULT_SEX);

        // Get all the employeeList where sex is greater than SMALLER_SEX
        defaultEmployeeShouldBeFound("sex.greaterThan=" + SMALLER_SEX);
    }


    @Test
    @Transactional
    public void getAllEmployeesByBirthdayIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthday equals to DEFAULT_BIRTHDAY
        defaultEmployeeShouldBeFound("birthday.equals=" + DEFAULT_BIRTHDAY);

        // Get all the employeeList where birthday equals to UPDATED_BIRTHDAY
        defaultEmployeeShouldNotBeFound("birthday.equals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthdayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthday not equals to DEFAULT_BIRTHDAY
        defaultEmployeeShouldNotBeFound("birthday.notEquals=" + DEFAULT_BIRTHDAY);

        // Get all the employeeList where birthday not equals to UPDATED_BIRTHDAY
        defaultEmployeeShouldBeFound("birthday.notEquals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthdayIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthday in DEFAULT_BIRTHDAY or UPDATED_BIRTHDAY
        defaultEmployeeShouldBeFound("birthday.in=" + DEFAULT_BIRTHDAY + "," + UPDATED_BIRTHDAY);

        // Get all the employeeList where birthday equals to UPDATED_BIRTHDAY
        defaultEmployeeShouldNotBeFound("birthday.in=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthday is not null
        defaultEmployeeShouldBeFound("birthday.specified=true");

        // Get all the employeeList where birthday is null
        defaultEmployeeShouldNotBeFound("birthday.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthdayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthday is greater than or equal to DEFAULT_BIRTHDAY
        defaultEmployeeShouldBeFound("birthday.greaterThanOrEqual=" + DEFAULT_BIRTHDAY);

        // Get all the employeeList where birthday is greater than or equal to UPDATED_BIRTHDAY
        defaultEmployeeShouldNotBeFound("birthday.greaterThanOrEqual=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthdayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthday is less than or equal to DEFAULT_BIRTHDAY
        defaultEmployeeShouldBeFound("birthday.lessThanOrEqual=" + DEFAULT_BIRTHDAY);

        // Get all the employeeList where birthday is less than or equal to SMALLER_BIRTHDAY
        defaultEmployeeShouldNotBeFound("birthday.lessThanOrEqual=" + SMALLER_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthdayIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthday is less than DEFAULT_BIRTHDAY
        defaultEmployeeShouldNotBeFound("birthday.lessThan=" + DEFAULT_BIRTHDAY);

        // Get all the employeeList where birthday is less than UPDATED_BIRTHDAY
        defaultEmployeeShouldBeFound("birthday.lessThan=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthdayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthday is greater than DEFAULT_BIRTHDAY
        defaultEmployeeShouldNotBeFound("birthday.greaterThan=" + DEFAULT_BIRTHDAY);

        // Get all the employeeList where birthday is greater than SMALLER_BIRTHDAY
        defaultEmployeeShouldBeFound("birthday.greaterThan=" + SMALLER_BIRTHDAY);
    }


    @Test
    @Transactional
    public void getAllEmployeesByIdentityCardIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityCard equals to DEFAULT_IDENTITY_CARD
        defaultEmployeeShouldBeFound("identityCard.equals=" + DEFAULT_IDENTITY_CARD);

        // Get all the employeeList where identityCard equals to UPDATED_IDENTITY_CARD
        defaultEmployeeShouldNotBeFound("identityCard.equals=" + UPDATED_IDENTITY_CARD);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityCardIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityCard not equals to DEFAULT_IDENTITY_CARD
        defaultEmployeeShouldNotBeFound("identityCard.notEquals=" + DEFAULT_IDENTITY_CARD);

        // Get all the employeeList where identityCard not equals to UPDATED_IDENTITY_CARD
        defaultEmployeeShouldBeFound("identityCard.notEquals=" + UPDATED_IDENTITY_CARD);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityCardIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityCard in DEFAULT_IDENTITY_CARD or UPDATED_IDENTITY_CARD
        defaultEmployeeShouldBeFound("identityCard.in=" + DEFAULT_IDENTITY_CARD + "," + UPDATED_IDENTITY_CARD);

        // Get all the employeeList where identityCard equals to UPDATED_IDENTITY_CARD
        defaultEmployeeShouldNotBeFound("identityCard.in=" + UPDATED_IDENTITY_CARD);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityCardIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityCard is not null
        defaultEmployeeShouldBeFound("identityCard.specified=true");

        // Get all the employeeList where identityCard is null
        defaultEmployeeShouldNotBeFound("identityCard.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByIdentityCardContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityCard contains DEFAULT_IDENTITY_CARD
        defaultEmployeeShouldBeFound("identityCard.contains=" + DEFAULT_IDENTITY_CARD);

        // Get all the employeeList where identityCard contains UPDATED_IDENTITY_CARD
        defaultEmployeeShouldNotBeFound("identityCard.contains=" + UPDATED_IDENTITY_CARD);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityCardNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityCard does not contain DEFAULT_IDENTITY_CARD
        defaultEmployeeShouldNotBeFound("identityCard.doesNotContain=" + DEFAULT_IDENTITY_CARD);

        // Get all the employeeList where identityCard does not contain UPDATED_IDENTITY_CARD
        defaultEmployeeShouldBeFound("identityCard.doesNotContain=" + UPDATED_IDENTITY_CARD);
    }


    @Test
    @Transactional
    public void getAllEmployeesByIdentityDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityDate equals to DEFAULT_IDENTITY_DATE
        defaultEmployeeShouldBeFound("identityDate.equals=" + DEFAULT_IDENTITY_DATE);

        // Get all the employeeList where identityDate equals to UPDATED_IDENTITY_DATE
        defaultEmployeeShouldNotBeFound("identityDate.equals=" + UPDATED_IDENTITY_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityDate not equals to DEFAULT_IDENTITY_DATE
        defaultEmployeeShouldNotBeFound("identityDate.notEquals=" + DEFAULT_IDENTITY_DATE);

        // Get all the employeeList where identityDate not equals to UPDATED_IDENTITY_DATE
        defaultEmployeeShouldBeFound("identityDate.notEquals=" + UPDATED_IDENTITY_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityDate in DEFAULT_IDENTITY_DATE or UPDATED_IDENTITY_DATE
        defaultEmployeeShouldBeFound("identityDate.in=" + DEFAULT_IDENTITY_DATE + "," + UPDATED_IDENTITY_DATE);

        // Get all the employeeList where identityDate equals to UPDATED_IDENTITY_DATE
        defaultEmployeeShouldNotBeFound("identityDate.in=" + UPDATED_IDENTITY_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityDate is not null
        defaultEmployeeShouldBeFound("identityDate.specified=true");

        // Get all the employeeList where identityDate is null
        defaultEmployeeShouldNotBeFound("identityDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityDate is greater than or equal to DEFAULT_IDENTITY_DATE
        defaultEmployeeShouldBeFound("identityDate.greaterThanOrEqual=" + DEFAULT_IDENTITY_DATE);

        // Get all the employeeList where identityDate is greater than or equal to UPDATED_IDENTITY_DATE
        defaultEmployeeShouldNotBeFound("identityDate.greaterThanOrEqual=" + UPDATED_IDENTITY_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityDate is less than or equal to DEFAULT_IDENTITY_DATE
        defaultEmployeeShouldBeFound("identityDate.lessThanOrEqual=" + DEFAULT_IDENTITY_DATE);

        // Get all the employeeList where identityDate is less than or equal to SMALLER_IDENTITY_DATE
        defaultEmployeeShouldNotBeFound("identityDate.lessThanOrEqual=" + SMALLER_IDENTITY_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityDate is less than DEFAULT_IDENTITY_DATE
        defaultEmployeeShouldNotBeFound("identityDate.lessThan=" + DEFAULT_IDENTITY_DATE);

        // Get all the employeeList where identityDate is less than UPDATED_IDENTITY_DATE
        defaultEmployeeShouldBeFound("identityDate.lessThan=" + UPDATED_IDENTITY_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityDate is greater than DEFAULT_IDENTITY_DATE
        defaultEmployeeShouldNotBeFound("identityDate.greaterThan=" + DEFAULT_IDENTITY_DATE);

        // Get all the employeeList where identityDate is greater than SMALLER_IDENTITY_DATE
        defaultEmployeeShouldBeFound("identityDate.greaterThan=" + SMALLER_IDENTITY_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByIdentityIssueIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityIssue equals to DEFAULT_IDENTITY_ISSUE
        defaultEmployeeShouldBeFound("identityIssue.equals=" + DEFAULT_IDENTITY_ISSUE);

        // Get all the employeeList where identityIssue equals to UPDATED_IDENTITY_ISSUE
        defaultEmployeeShouldNotBeFound("identityIssue.equals=" + UPDATED_IDENTITY_ISSUE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityIssueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityIssue not equals to DEFAULT_IDENTITY_ISSUE
        defaultEmployeeShouldNotBeFound("identityIssue.notEquals=" + DEFAULT_IDENTITY_ISSUE);

        // Get all the employeeList where identityIssue not equals to UPDATED_IDENTITY_ISSUE
        defaultEmployeeShouldBeFound("identityIssue.notEquals=" + UPDATED_IDENTITY_ISSUE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityIssueIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityIssue in DEFAULT_IDENTITY_ISSUE or UPDATED_IDENTITY_ISSUE
        defaultEmployeeShouldBeFound("identityIssue.in=" + DEFAULT_IDENTITY_ISSUE + "," + UPDATED_IDENTITY_ISSUE);

        // Get all the employeeList where identityIssue equals to UPDATED_IDENTITY_ISSUE
        defaultEmployeeShouldNotBeFound("identityIssue.in=" + UPDATED_IDENTITY_ISSUE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityIssueIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityIssue is not null
        defaultEmployeeShouldBeFound("identityIssue.specified=true");

        // Get all the employeeList where identityIssue is null
        defaultEmployeeShouldNotBeFound("identityIssue.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByIdentityIssueContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityIssue contains DEFAULT_IDENTITY_ISSUE
        defaultEmployeeShouldBeFound("identityIssue.contains=" + DEFAULT_IDENTITY_ISSUE);

        // Get all the employeeList where identityIssue contains UPDATED_IDENTITY_ISSUE
        defaultEmployeeShouldNotBeFound("identityIssue.contains=" + UPDATED_IDENTITY_ISSUE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIdentityIssueNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where identityIssue does not contain DEFAULT_IDENTITY_ISSUE
        defaultEmployeeShouldNotBeFound("identityIssue.doesNotContain=" + DEFAULT_IDENTITY_ISSUE);

        // Get all the employeeList where identityIssue does not contain UPDATED_IDENTITY_ISSUE
        defaultEmployeeShouldBeFound("identityIssue.doesNotContain=" + UPDATED_IDENTITY_ISSUE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where position equals to DEFAULT_POSITION
        defaultEmployeeShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the employeeList where position equals to UPDATED_POSITION
        defaultEmployeeShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where position not equals to DEFAULT_POSITION
        defaultEmployeeShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the employeeList where position not equals to UPDATED_POSITION
        defaultEmployeeShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultEmployeeShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the employeeList where position equals to UPDATED_POSITION
        defaultEmployeeShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where position is not null
        defaultEmployeeShouldBeFound("position.specified=true");

        // Get all the employeeList where position is null
        defaultEmployeeShouldNotBeFound("position.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByPositionContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where position contains DEFAULT_POSITION
        defaultEmployeeShouldBeFound("position.contains=" + DEFAULT_POSITION);

        // Get all the employeeList where position contains UPDATED_POSITION
        defaultEmployeeShouldNotBeFound("position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where position does not contain DEFAULT_POSITION
        defaultEmployeeShouldNotBeFound("position.doesNotContain=" + DEFAULT_POSITION);

        // Get all the employeeList where position does not contain UPDATED_POSITION
        defaultEmployeeShouldBeFound("position.doesNotContain=" + UPDATED_POSITION);
    }


    @Test
    @Transactional
    public void getAllEmployeesByTaxCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where taxCode equals to DEFAULT_TAX_CODE
        defaultEmployeeShouldBeFound("taxCode.equals=" + DEFAULT_TAX_CODE);

        // Get all the employeeList where taxCode equals to UPDATED_TAX_CODE
        defaultEmployeeShouldNotBeFound("taxCode.equals=" + UPDATED_TAX_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTaxCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where taxCode not equals to DEFAULT_TAX_CODE
        defaultEmployeeShouldNotBeFound("taxCode.notEquals=" + DEFAULT_TAX_CODE);

        // Get all the employeeList where taxCode not equals to UPDATED_TAX_CODE
        defaultEmployeeShouldBeFound("taxCode.notEquals=" + UPDATED_TAX_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTaxCodeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where taxCode in DEFAULT_TAX_CODE or UPDATED_TAX_CODE
        defaultEmployeeShouldBeFound("taxCode.in=" + DEFAULT_TAX_CODE + "," + UPDATED_TAX_CODE);

        // Get all the employeeList where taxCode equals to UPDATED_TAX_CODE
        defaultEmployeeShouldNotBeFound("taxCode.in=" + UPDATED_TAX_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTaxCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where taxCode is not null
        defaultEmployeeShouldBeFound("taxCode.specified=true");

        // Get all the employeeList where taxCode is null
        defaultEmployeeShouldNotBeFound("taxCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByTaxCodeContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where taxCode contains DEFAULT_TAX_CODE
        defaultEmployeeShouldBeFound("taxCode.contains=" + DEFAULT_TAX_CODE);

        // Get all the employeeList where taxCode contains UPDATED_TAX_CODE
        defaultEmployeeShouldNotBeFound("taxCode.contains=" + UPDATED_TAX_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTaxCodeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where taxCode does not contain DEFAULT_TAX_CODE
        defaultEmployeeShouldNotBeFound("taxCode.doesNotContain=" + DEFAULT_TAX_CODE);

        // Get all the employeeList where taxCode does not contain UPDATED_TAX_CODE
        defaultEmployeeShouldBeFound("taxCode.doesNotContain=" + UPDATED_TAX_CODE);
    }


    @Test
    @Transactional
    public void getAllEmployeesBySalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary equals to DEFAULT_SALARY
        defaultEmployeeShouldBeFound("salary.equals=" + DEFAULT_SALARY);

        // Get all the employeeList where salary equals to UPDATED_SALARY
        defaultEmployeeShouldNotBeFound("salary.equals=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary not equals to DEFAULT_SALARY
        defaultEmployeeShouldNotBeFound("salary.notEquals=" + DEFAULT_SALARY);

        // Get all the employeeList where salary not equals to UPDATED_SALARY
        defaultEmployeeShouldBeFound("salary.notEquals=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary in DEFAULT_SALARY or UPDATED_SALARY
        defaultEmployeeShouldBeFound("salary.in=" + DEFAULT_SALARY + "," + UPDATED_SALARY);

        // Get all the employeeList where salary equals to UPDATED_SALARY
        defaultEmployeeShouldNotBeFound("salary.in=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is not null
        defaultEmployeeShouldBeFound("salary.specified=true");

        // Get all the employeeList where salary is null
        defaultEmployeeShouldNotBeFound("salary.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is greater than or equal to DEFAULT_SALARY
        defaultEmployeeShouldBeFound("salary.greaterThanOrEqual=" + DEFAULT_SALARY);

        // Get all the employeeList where salary is greater than or equal to UPDATED_SALARY
        defaultEmployeeShouldNotBeFound("salary.greaterThanOrEqual=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is less than or equal to DEFAULT_SALARY
        defaultEmployeeShouldBeFound("salary.lessThanOrEqual=" + DEFAULT_SALARY);

        // Get all the employeeList where salary is less than or equal to SMALLER_SALARY
        defaultEmployeeShouldNotBeFound("salary.lessThanOrEqual=" + SMALLER_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is less than DEFAULT_SALARY
        defaultEmployeeShouldNotBeFound("salary.lessThan=" + DEFAULT_SALARY);

        // Get all the employeeList where salary is less than UPDATED_SALARY
        defaultEmployeeShouldBeFound("salary.lessThan=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is greater than DEFAULT_SALARY
        defaultEmployeeShouldNotBeFound("salary.greaterThan=" + DEFAULT_SALARY);

        // Get all the employeeList where salary is greater than SMALLER_SALARY
        defaultEmployeeShouldBeFound("salary.greaterThan=" + SMALLER_SALARY);
    }


    @Test
    @Transactional
    public void getAllEmployeesBySalaryRateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salaryRate equals to DEFAULT_SALARY_RATE
        defaultEmployeeShouldBeFound("salaryRate.equals=" + DEFAULT_SALARY_RATE);

        // Get all the employeeList where salaryRate equals to UPDATED_SALARY_RATE
        defaultEmployeeShouldNotBeFound("salaryRate.equals=" + UPDATED_SALARY_RATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salaryRate not equals to DEFAULT_SALARY_RATE
        defaultEmployeeShouldNotBeFound("salaryRate.notEquals=" + DEFAULT_SALARY_RATE);

        // Get all the employeeList where salaryRate not equals to UPDATED_SALARY_RATE
        defaultEmployeeShouldBeFound("salaryRate.notEquals=" + UPDATED_SALARY_RATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryRateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salaryRate in DEFAULT_SALARY_RATE or UPDATED_SALARY_RATE
        defaultEmployeeShouldBeFound("salaryRate.in=" + DEFAULT_SALARY_RATE + "," + UPDATED_SALARY_RATE);

        // Get all the employeeList where salaryRate equals to UPDATED_SALARY_RATE
        defaultEmployeeShouldNotBeFound("salaryRate.in=" + UPDATED_SALARY_RATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salaryRate is not null
        defaultEmployeeShouldBeFound("salaryRate.specified=true");

        // Get all the employeeList where salaryRate is null
        defaultEmployeeShouldNotBeFound("salaryRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salaryRate is greater than or equal to DEFAULT_SALARY_RATE
        defaultEmployeeShouldBeFound("salaryRate.greaterThanOrEqual=" + DEFAULT_SALARY_RATE);

        // Get all the employeeList where salaryRate is greater than or equal to UPDATED_SALARY_RATE
        defaultEmployeeShouldNotBeFound("salaryRate.greaterThanOrEqual=" + UPDATED_SALARY_RATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salaryRate is less than or equal to DEFAULT_SALARY_RATE
        defaultEmployeeShouldBeFound("salaryRate.lessThanOrEqual=" + DEFAULT_SALARY_RATE);

        // Get all the employeeList where salaryRate is less than or equal to SMALLER_SALARY_RATE
        defaultEmployeeShouldNotBeFound("salaryRate.lessThanOrEqual=" + SMALLER_SALARY_RATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryRateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salaryRate is less than DEFAULT_SALARY_RATE
        defaultEmployeeShouldNotBeFound("salaryRate.lessThan=" + DEFAULT_SALARY_RATE);

        // Get all the employeeList where salaryRate is less than UPDATED_SALARY_RATE
        defaultEmployeeShouldBeFound("salaryRate.lessThan=" + UPDATED_SALARY_RATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalaryRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salaryRate is greater than DEFAULT_SALARY_RATE
        defaultEmployeeShouldNotBeFound("salaryRate.greaterThan=" + DEFAULT_SALARY_RATE);

        // Get all the employeeList where salaryRate is greater than SMALLER_SALARY_RATE
        defaultEmployeeShouldBeFound("salaryRate.greaterThan=" + SMALLER_SALARY_RATE);
    }


    @Test
    @Transactional
    public void getAllEmployeesBySalarySecurityIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salarySecurity equals to DEFAULT_SALARY_SECURITY
        defaultEmployeeShouldBeFound("salarySecurity.equals=" + DEFAULT_SALARY_SECURITY);

        // Get all the employeeList where salarySecurity equals to UPDATED_SALARY_SECURITY
        defaultEmployeeShouldNotBeFound("salarySecurity.equals=" + UPDATED_SALARY_SECURITY);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalarySecurityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salarySecurity not equals to DEFAULT_SALARY_SECURITY
        defaultEmployeeShouldNotBeFound("salarySecurity.notEquals=" + DEFAULT_SALARY_SECURITY);

        // Get all the employeeList where salarySecurity not equals to UPDATED_SALARY_SECURITY
        defaultEmployeeShouldBeFound("salarySecurity.notEquals=" + UPDATED_SALARY_SECURITY);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalarySecurityIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salarySecurity in DEFAULT_SALARY_SECURITY or UPDATED_SALARY_SECURITY
        defaultEmployeeShouldBeFound("salarySecurity.in=" + DEFAULT_SALARY_SECURITY + "," + UPDATED_SALARY_SECURITY);

        // Get all the employeeList where salarySecurity equals to UPDATED_SALARY_SECURITY
        defaultEmployeeShouldNotBeFound("salarySecurity.in=" + UPDATED_SALARY_SECURITY);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalarySecurityIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salarySecurity is not null
        defaultEmployeeShouldBeFound("salarySecurity.specified=true");

        // Get all the employeeList where salarySecurity is null
        defaultEmployeeShouldNotBeFound("salarySecurity.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalarySecurityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salarySecurity is greater than or equal to DEFAULT_SALARY_SECURITY
        defaultEmployeeShouldBeFound("salarySecurity.greaterThanOrEqual=" + DEFAULT_SALARY_SECURITY);

        // Get all the employeeList where salarySecurity is greater than or equal to UPDATED_SALARY_SECURITY
        defaultEmployeeShouldNotBeFound("salarySecurity.greaterThanOrEqual=" + UPDATED_SALARY_SECURITY);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalarySecurityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salarySecurity is less than or equal to DEFAULT_SALARY_SECURITY
        defaultEmployeeShouldBeFound("salarySecurity.lessThanOrEqual=" + DEFAULT_SALARY_SECURITY);

        // Get all the employeeList where salarySecurity is less than or equal to SMALLER_SALARY_SECURITY
        defaultEmployeeShouldNotBeFound("salarySecurity.lessThanOrEqual=" + SMALLER_SALARY_SECURITY);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalarySecurityIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salarySecurity is less than DEFAULT_SALARY_SECURITY
        defaultEmployeeShouldNotBeFound("salarySecurity.lessThan=" + DEFAULT_SALARY_SECURITY);

        // Get all the employeeList where salarySecurity is less than UPDATED_SALARY_SECURITY
        defaultEmployeeShouldBeFound("salarySecurity.lessThan=" + UPDATED_SALARY_SECURITY);
    }

    @Test
    @Transactional
    public void getAllEmployeesBySalarySecurityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salarySecurity is greater than DEFAULT_SALARY_SECURITY
        defaultEmployeeShouldNotBeFound("salarySecurity.greaterThan=" + DEFAULT_SALARY_SECURITY);

        // Get all the employeeList where salarySecurity is greater than SMALLER_SALARY_SECURITY
        defaultEmployeeShouldBeFound("salarySecurity.greaterThan=" + SMALLER_SALARY_SECURITY);
    }


    @Test
    @Transactional
    public void getAllEmployeesByNumOfDependsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where numOfDepends equals to DEFAULT_NUM_OF_DEPENDS
        defaultEmployeeShouldBeFound("numOfDepends.equals=" + DEFAULT_NUM_OF_DEPENDS);

        // Get all the employeeList where numOfDepends equals to UPDATED_NUM_OF_DEPENDS
        defaultEmployeeShouldNotBeFound("numOfDepends.equals=" + UPDATED_NUM_OF_DEPENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNumOfDependsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where numOfDepends not equals to DEFAULT_NUM_OF_DEPENDS
        defaultEmployeeShouldNotBeFound("numOfDepends.notEquals=" + DEFAULT_NUM_OF_DEPENDS);

        // Get all the employeeList where numOfDepends not equals to UPDATED_NUM_OF_DEPENDS
        defaultEmployeeShouldBeFound("numOfDepends.notEquals=" + UPDATED_NUM_OF_DEPENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNumOfDependsIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where numOfDepends in DEFAULT_NUM_OF_DEPENDS or UPDATED_NUM_OF_DEPENDS
        defaultEmployeeShouldBeFound("numOfDepends.in=" + DEFAULT_NUM_OF_DEPENDS + "," + UPDATED_NUM_OF_DEPENDS);

        // Get all the employeeList where numOfDepends equals to UPDATED_NUM_OF_DEPENDS
        defaultEmployeeShouldNotBeFound("numOfDepends.in=" + UPDATED_NUM_OF_DEPENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNumOfDependsIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where numOfDepends is not null
        defaultEmployeeShouldBeFound("numOfDepends.specified=true");

        // Get all the employeeList where numOfDepends is null
        defaultEmployeeShouldNotBeFound("numOfDepends.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByNumOfDependsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where numOfDepends is greater than or equal to DEFAULT_NUM_OF_DEPENDS
        defaultEmployeeShouldBeFound("numOfDepends.greaterThanOrEqual=" + DEFAULT_NUM_OF_DEPENDS);

        // Get all the employeeList where numOfDepends is greater than or equal to UPDATED_NUM_OF_DEPENDS
        defaultEmployeeShouldNotBeFound("numOfDepends.greaterThanOrEqual=" + UPDATED_NUM_OF_DEPENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNumOfDependsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where numOfDepends is less than or equal to DEFAULT_NUM_OF_DEPENDS
        defaultEmployeeShouldBeFound("numOfDepends.lessThanOrEqual=" + DEFAULT_NUM_OF_DEPENDS);

        // Get all the employeeList where numOfDepends is less than or equal to SMALLER_NUM_OF_DEPENDS
        defaultEmployeeShouldNotBeFound("numOfDepends.lessThanOrEqual=" + SMALLER_NUM_OF_DEPENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNumOfDependsIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where numOfDepends is less than DEFAULT_NUM_OF_DEPENDS
        defaultEmployeeShouldNotBeFound("numOfDepends.lessThan=" + DEFAULT_NUM_OF_DEPENDS);

        // Get all the employeeList where numOfDepends is less than UPDATED_NUM_OF_DEPENDS
        defaultEmployeeShouldBeFound("numOfDepends.lessThan=" + UPDATED_NUM_OF_DEPENDS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNumOfDependsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where numOfDepends is greater than DEFAULT_NUM_OF_DEPENDS
        defaultEmployeeShouldNotBeFound("numOfDepends.greaterThan=" + DEFAULT_NUM_OF_DEPENDS);

        // Get all the employeeList where numOfDepends is greater than SMALLER_NUM_OF_DEPENDS
        defaultEmployeeShouldBeFound("numOfDepends.greaterThan=" + SMALLER_NUM_OF_DEPENDS);
    }


    @Test
    @Transactional
    public void getAllEmployeesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phone equals to DEFAULT_PHONE
        defaultEmployeeShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the employeeList where phone equals to UPDATED_PHONE
        defaultEmployeeShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phone not equals to DEFAULT_PHONE
        defaultEmployeeShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the employeeList where phone not equals to UPDATED_PHONE
        defaultEmployeeShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultEmployeeShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the employeeList where phone equals to UPDATED_PHONE
        defaultEmployeeShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phone is not null
        defaultEmployeeShouldBeFound("phone.specified=true");

        // Get all the employeeList where phone is null
        defaultEmployeeShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phone contains DEFAULT_PHONE
        defaultEmployeeShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the employeeList where phone contains UPDATED_PHONE
        defaultEmployeeShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phone does not contain DEFAULT_PHONE
        defaultEmployeeShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the employeeList where phone does not contain UPDATED_PHONE
        defaultEmployeeShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobile equals to DEFAULT_MOBILE
        defaultEmployeeShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the employeeList where mobile equals to UPDATED_MOBILE
        defaultEmployeeShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobile not equals to DEFAULT_MOBILE
        defaultEmployeeShouldNotBeFound("mobile.notEquals=" + DEFAULT_MOBILE);

        // Get all the employeeList where mobile not equals to UPDATED_MOBILE
        defaultEmployeeShouldBeFound("mobile.notEquals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultEmployeeShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the employeeList where mobile equals to UPDATED_MOBILE
        defaultEmployeeShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobile is not null
        defaultEmployeeShouldBeFound("mobile.specified=true");

        // Get all the employeeList where mobile is null
        defaultEmployeeShouldNotBeFound("mobile.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByMobileContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobile contains DEFAULT_MOBILE
        defaultEmployeeShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the employeeList where mobile contains UPDATED_MOBILE
        defaultEmployeeShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobile does not contain DEFAULT_MOBILE
        defaultEmployeeShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the employeeList where mobile does not contain UPDATED_MOBILE
        defaultEmployeeShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email equals to DEFAULT_EMAIL
        defaultEmployeeShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email not equals to DEFAULT_EMAIL
        defaultEmployeeShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the employeeList where email not equals to UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email is not null
        defaultEmployeeShouldBeFound("email.specified=true");

        // Get all the employeeList where email is null
        defaultEmployeeShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByEmailContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email contains DEFAULT_EMAIL
        defaultEmployeeShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the employeeList where email contains UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email does not contain DEFAULT_EMAIL
        defaultEmployeeShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the employeeList where email does not contain UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllEmployeesByBankAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccount equals to DEFAULT_BANK_ACCOUNT
        defaultEmployeeShouldBeFound("bankAccount.equals=" + DEFAULT_BANK_ACCOUNT);

        // Get all the employeeList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultEmployeeShouldNotBeFound("bankAccount.equals=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccount not equals to DEFAULT_BANK_ACCOUNT
        defaultEmployeeShouldNotBeFound("bankAccount.notEquals=" + DEFAULT_BANK_ACCOUNT);

        // Get all the employeeList where bankAccount not equals to UPDATED_BANK_ACCOUNT
        defaultEmployeeShouldBeFound("bankAccount.notEquals=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankAccountIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccount in DEFAULT_BANK_ACCOUNT or UPDATED_BANK_ACCOUNT
        defaultEmployeeShouldBeFound("bankAccount.in=" + DEFAULT_BANK_ACCOUNT + "," + UPDATED_BANK_ACCOUNT);

        // Get all the employeeList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultEmployeeShouldNotBeFound("bankAccount.in=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccount is not null
        defaultEmployeeShouldBeFound("bankAccount.specified=true");

        // Get all the employeeList where bankAccount is null
        defaultEmployeeShouldNotBeFound("bankAccount.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByBankAccountContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccount contains DEFAULT_BANK_ACCOUNT
        defaultEmployeeShouldBeFound("bankAccount.contains=" + DEFAULT_BANK_ACCOUNT);

        // Get all the employeeList where bankAccount contains UPDATED_BANK_ACCOUNT
        defaultEmployeeShouldNotBeFound("bankAccount.contains=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankAccountNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccount does not contain DEFAULT_BANK_ACCOUNT
        defaultEmployeeShouldNotBeFound("bankAccount.doesNotContain=" + DEFAULT_BANK_ACCOUNT);

        // Get all the employeeList where bankAccount does not contain UPDATED_BANK_ACCOUNT
        defaultEmployeeShouldBeFound("bankAccount.doesNotContain=" + UPDATED_BANK_ACCOUNT);
    }


    @Test
    @Transactional
    public void getAllEmployeesByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName equals to DEFAULT_BANK_NAME
        defaultEmployeeShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the employeeList where bankName equals to UPDATED_BANK_NAME
        defaultEmployeeShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName not equals to DEFAULT_BANK_NAME
        defaultEmployeeShouldNotBeFound("bankName.notEquals=" + DEFAULT_BANK_NAME);

        // Get all the employeeList where bankName not equals to UPDATED_BANK_NAME
        defaultEmployeeShouldBeFound("bankName.notEquals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultEmployeeShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the employeeList where bankName equals to UPDATED_BANK_NAME
        defaultEmployeeShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName is not null
        defaultEmployeeShouldBeFound("bankName.specified=true");

        // Get all the employeeList where bankName is null
        defaultEmployeeShouldNotBeFound("bankName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByBankNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName contains DEFAULT_BANK_NAME
        defaultEmployeeShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the employeeList where bankName contains UPDATED_BANK_NAME
        defaultEmployeeShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName does not contain DEFAULT_BANK_NAME
        defaultEmployeeShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the employeeList where bankName does not contain UPDATED_BANK_NAME
        defaultEmployeeShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeesByNodesIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nodes equals to DEFAULT_NODES
        defaultEmployeeShouldBeFound("nodes.equals=" + DEFAULT_NODES);

        // Get all the employeeList where nodes equals to UPDATED_NODES
        defaultEmployeeShouldNotBeFound("nodes.equals=" + UPDATED_NODES);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNodesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nodes not equals to DEFAULT_NODES
        defaultEmployeeShouldNotBeFound("nodes.notEquals=" + DEFAULT_NODES);

        // Get all the employeeList where nodes not equals to UPDATED_NODES
        defaultEmployeeShouldBeFound("nodes.notEquals=" + UPDATED_NODES);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNodesIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nodes in DEFAULT_NODES or UPDATED_NODES
        defaultEmployeeShouldBeFound("nodes.in=" + DEFAULT_NODES + "," + UPDATED_NODES);

        // Get all the employeeList where nodes equals to UPDATED_NODES
        defaultEmployeeShouldNotBeFound("nodes.in=" + UPDATED_NODES);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNodesIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nodes is not null
        defaultEmployeeShouldBeFound("nodes.specified=true");

        // Get all the employeeList where nodes is null
        defaultEmployeeShouldNotBeFound("nodes.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByNodesContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nodes contains DEFAULT_NODES
        defaultEmployeeShouldBeFound("nodes.contains=" + DEFAULT_NODES);

        // Get all the employeeList where nodes contains UPDATED_NODES
        defaultEmployeeShouldNotBeFound("nodes.contains=" + UPDATED_NODES);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNodesNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nodes does not contain DEFAULT_NODES
        defaultEmployeeShouldNotBeFound("nodes.doesNotContain=" + DEFAULT_NODES);

        // Get all the employeeList where nodes does not contain UPDATED_NODES
        defaultEmployeeShouldBeFound("nodes.doesNotContain=" + UPDATED_NODES);
    }


    @Test
    @Transactional
    public void getAllEmployeesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where isActive equals to DEFAULT_IS_ACTIVE
        defaultEmployeeShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the employeeList where isActive equals to UPDATED_IS_ACTIVE
        defaultEmployeeShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultEmployeeShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the employeeList where isActive not equals to UPDATED_IS_ACTIVE
        defaultEmployeeShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultEmployeeShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the employeeList where isActive equals to UPDATED_IS_ACTIVE
        defaultEmployeeShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where isActive is not null
        defaultEmployeeShouldBeFound("isActive.specified=true");

        // Get all the employeeList where isActive is null
        defaultEmployeeShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByTimeCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where timeCreated equals to DEFAULT_TIME_CREATED
        defaultEmployeeShouldBeFound("timeCreated.equals=" + DEFAULT_TIME_CREATED);

        // Get all the employeeList where timeCreated equals to UPDATED_TIME_CREATED
        defaultEmployeeShouldNotBeFound("timeCreated.equals=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTimeCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where timeCreated not equals to DEFAULT_TIME_CREATED
        defaultEmployeeShouldNotBeFound("timeCreated.notEquals=" + DEFAULT_TIME_CREATED);

        // Get all the employeeList where timeCreated not equals to UPDATED_TIME_CREATED
        defaultEmployeeShouldBeFound("timeCreated.notEquals=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTimeCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where timeCreated in DEFAULT_TIME_CREATED or UPDATED_TIME_CREATED
        defaultEmployeeShouldBeFound("timeCreated.in=" + DEFAULT_TIME_CREATED + "," + UPDATED_TIME_CREATED);

        // Get all the employeeList where timeCreated equals to UPDATED_TIME_CREATED
        defaultEmployeeShouldNotBeFound("timeCreated.in=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTimeCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where timeCreated is not null
        defaultEmployeeShouldBeFound("timeCreated.specified=true");

        // Get all the employeeList where timeCreated is null
        defaultEmployeeShouldNotBeFound("timeCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByTimeModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where timeModified equals to DEFAULT_TIME_MODIFIED
        defaultEmployeeShouldBeFound("timeModified.equals=" + DEFAULT_TIME_MODIFIED);

        // Get all the employeeList where timeModified equals to UPDATED_TIME_MODIFIED
        defaultEmployeeShouldNotBeFound("timeModified.equals=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTimeModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where timeModified not equals to DEFAULT_TIME_MODIFIED
        defaultEmployeeShouldNotBeFound("timeModified.notEquals=" + DEFAULT_TIME_MODIFIED);

        // Get all the employeeList where timeModified not equals to UPDATED_TIME_MODIFIED
        defaultEmployeeShouldBeFound("timeModified.notEquals=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTimeModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where timeModified in DEFAULT_TIME_MODIFIED or UPDATED_TIME_MODIFIED
        defaultEmployeeShouldBeFound("timeModified.in=" + DEFAULT_TIME_MODIFIED + "," + UPDATED_TIME_MODIFIED);

        // Get all the employeeList where timeModified equals to UPDATED_TIME_MODIFIED
        defaultEmployeeShouldNotBeFound("timeModified.in=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTimeModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where timeModified is not null
        defaultEmployeeShouldBeFound("timeModified.specified=true");

        // Get all the employeeList where timeModified is null
        defaultEmployeeShouldNotBeFound("timeModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdCreated equals to DEFAULT_USER_ID_CREATED
        defaultEmployeeShouldBeFound("userIdCreated.equals=" + DEFAULT_USER_ID_CREATED);

        // Get all the employeeList where userIdCreated equals to UPDATED_USER_ID_CREATED
        defaultEmployeeShouldNotBeFound("userIdCreated.equals=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdCreated not equals to DEFAULT_USER_ID_CREATED
        defaultEmployeeShouldNotBeFound("userIdCreated.notEquals=" + DEFAULT_USER_ID_CREATED);

        // Get all the employeeList where userIdCreated not equals to UPDATED_USER_ID_CREATED
        defaultEmployeeShouldBeFound("userIdCreated.notEquals=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdCreated in DEFAULT_USER_ID_CREATED or UPDATED_USER_ID_CREATED
        defaultEmployeeShouldBeFound("userIdCreated.in=" + DEFAULT_USER_ID_CREATED + "," + UPDATED_USER_ID_CREATED);

        // Get all the employeeList where userIdCreated equals to UPDATED_USER_ID_CREATED
        defaultEmployeeShouldNotBeFound("userIdCreated.in=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdCreated is not null
        defaultEmployeeShouldBeFound("userIdCreated.specified=true");

        // Get all the employeeList where userIdCreated is null
        defaultEmployeeShouldNotBeFound("userIdCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdCreatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdCreated is greater than or equal to DEFAULT_USER_ID_CREATED
        defaultEmployeeShouldBeFound("userIdCreated.greaterThanOrEqual=" + DEFAULT_USER_ID_CREATED);

        // Get all the employeeList where userIdCreated is greater than or equal to UPDATED_USER_ID_CREATED
        defaultEmployeeShouldNotBeFound("userIdCreated.greaterThanOrEqual=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdCreatedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdCreated is less than or equal to DEFAULT_USER_ID_CREATED
        defaultEmployeeShouldBeFound("userIdCreated.lessThanOrEqual=" + DEFAULT_USER_ID_CREATED);

        // Get all the employeeList where userIdCreated is less than or equal to SMALLER_USER_ID_CREATED
        defaultEmployeeShouldNotBeFound("userIdCreated.lessThanOrEqual=" + SMALLER_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdCreatedIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdCreated is less than DEFAULT_USER_ID_CREATED
        defaultEmployeeShouldNotBeFound("userIdCreated.lessThan=" + DEFAULT_USER_ID_CREATED);

        // Get all the employeeList where userIdCreated is less than UPDATED_USER_ID_CREATED
        defaultEmployeeShouldBeFound("userIdCreated.lessThan=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdCreatedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdCreated is greater than DEFAULT_USER_ID_CREATED
        defaultEmployeeShouldNotBeFound("userIdCreated.greaterThan=" + DEFAULT_USER_ID_CREATED);

        // Get all the employeeList where userIdCreated is greater than SMALLER_USER_ID_CREATED
        defaultEmployeeShouldBeFound("userIdCreated.greaterThan=" + SMALLER_USER_ID_CREATED);
    }


    @Test
    @Transactional
    public void getAllEmployeesByUserIdModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdModified equals to DEFAULT_USER_ID_MODIFIED
        defaultEmployeeShouldBeFound("userIdModified.equals=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the employeeList where userIdModified equals to UPDATED_USER_ID_MODIFIED
        defaultEmployeeShouldNotBeFound("userIdModified.equals=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdModified not equals to DEFAULT_USER_ID_MODIFIED
        defaultEmployeeShouldNotBeFound("userIdModified.notEquals=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the employeeList where userIdModified not equals to UPDATED_USER_ID_MODIFIED
        defaultEmployeeShouldBeFound("userIdModified.notEquals=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdModified in DEFAULT_USER_ID_MODIFIED or UPDATED_USER_ID_MODIFIED
        defaultEmployeeShouldBeFound("userIdModified.in=" + DEFAULT_USER_ID_MODIFIED + "," + UPDATED_USER_ID_MODIFIED);

        // Get all the employeeList where userIdModified equals to UPDATED_USER_ID_MODIFIED
        defaultEmployeeShouldNotBeFound("userIdModified.in=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdModified is not null
        defaultEmployeeShouldBeFound("userIdModified.specified=true");

        // Get all the employeeList where userIdModified is null
        defaultEmployeeShouldNotBeFound("userIdModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdModifiedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdModified is greater than or equal to DEFAULT_USER_ID_MODIFIED
        defaultEmployeeShouldBeFound("userIdModified.greaterThanOrEqual=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the employeeList where userIdModified is greater than or equal to UPDATED_USER_ID_MODIFIED
        defaultEmployeeShouldNotBeFound("userIdModified.greaterThanOrEqual=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdModifiedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdModified is less than or equal to DEFAULT_USER_ID_MODIFIED
        defaultEmployeeShouldBeFound("userIdModified.lessThanOrEqual=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the employeeList where userIdModified is less than or equal to SMALLER_USER_ID_MODIFIED
        defaultEmployeeShouldNotBeFound("userIdModified.lessThanOrEqual=" + SMALLER_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdModifiedIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdModified is less than DEFAULT_USER_ID_MODIFIED
        defaultEmployeeShouldNotBeFound("userIdModified.lessThan=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the employeeList where userIdModified is less than UPDATED_USER_ID_MODIFIED
        defaultEmployeeShouldBeFound("userIdModified.lessThan=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdModifiedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userIdModified is greater than DEFAULT_USER_ID_MODIFIED
        defaultEmployeeShouldNotBeFound("userIdModified.greaterThan=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the employeeList where userIdModified is greater than SMALLER_USER_ID_MODIFIED
        defaultEmployeeShouldBeFound("userIdModified.greaterThan=" + SMALLER_USER_ID_MODIFIED);
    }


    @Test
    @Transactional
    public void getAllEmployeesByInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Invoice invoice = InvoiceResourceIT.createEntity(em);
        em.persist(invoice);
        em.flush();
        employee.addInvoice(invoice);
        employeeRepository.saveAndFlush(employee);
        Long invoiceId = invoice.getId();

        // Get all the employeeList where invoice equals to invoiceId
        defaultEmployeeShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the employeeList where invoice equals to invoiceId + 1
        defaultEmployeeShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        employee.setDepartment(department);
        employeeRepository.saveAndFlush(employee);
        Long departmentId = department.getId();

        // Get all the employeeList where department equals to departmentId
        defaultEmployeeShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the employeeList where department equals to departmentId + 1
        defaultEmployeeShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].identityCard").value(hasItem(DEFAULT_IDENTITY_CARD)))
            .andExpect(jsonPath("$.[*].identityDate").value(hasItem(DEFAULT_IDENTITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].identityIssue").value(hasItem(DEFAULT_IDENTITY_ISSUE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].taxCode").value(hasItem(DEFAULT_TAX_CODE)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].salaryRate").value(hasItem(DEFAULT_SALARY_RATE.intValue())))
            .andExpect(jsonPath("$.[*].salarySecurity").value(hasItem(DEFAULT_SALARY_SECURITY.intValue())))
            .andExpect(jsonPath("$.[*].numOfDepends").value(hasItem(DEFAULT_NUM_OF_DEPENDS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].nodes").value(hasItem(DEFAULT_NODES)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].timeCreated").value(hasItem(DEFAULT_TIME_CREATED.toString())))
            .andExpect(jsonPath("$.[*].timeModified").value(hasItem(DEFAULT_TIME_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].userIdCreated").value(hasItem(DEFAULT_USER_ID_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userIdModified").value(hasItem(DEFAULT_USER_ID_MODIFIED.intValue())));

        // Check, that the count call also returns 1
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeService.save(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .companyId(UPDATED_COMPANY_ID)
            .code(UPDATED_CODE)
            .fullName(UPDATED_FULL_NAME)
            .sex(UPDATED_SEX)
            .birthday(UPDATED_BIRTHDAY)
            .identityCard(UPDATED_IDENTITY_CARD)
            .identityDate(UPDATED_IDENTITY_DATE)
            .identityIssue(UPDATED_IDENTITY_ISSUE)
            .position(UPDATED_POSITION)
            .taxCode(UPDATED_TAX_CODE)
            .salary(UPDATED_SALARY)
            .salaryRate(UPDATED_SALARY_RATE)
            .salarySecurity(UPDATED_SALARY_SECURITY)
            .numOfDepends(UPDATED_NUM_OF_DEPENDS)
            .phone(UPDATED_PHONE)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .bankName(UPDATED_BANK_NAME)
            .nodes(UPDATED_NODES)
            .isActive(UPDATED_IS_ACTIVE)
            .timeCreated(UPDATED_TIME_CREATED)
            .timeModified(UPDATED_TIME_MODIFIED)
            .userIdCreated(UPDATED_USER_ID_CREATED)
            .userIdModified(UPDATED_USER_ID_MODIFIED);

        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployee)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testEmployee.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEmployee.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testEmployee.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testEmployee.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testEmployee.getIdentityCard()).isEqualTo(UPDATED_IDENTITY_CARD);
        assertThat(testEmployee.getIdentityDate()).isEqualTo(UPDATED_IDENTITY_DATE);
        assertThat(testEmployee.getIdentityIssue()).isEqualTo(UPDATED_IDENTITY_ISSUE);
        assertThat(testEmployee.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testEmployee.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testEmployee.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testEmployee.getSalaryRate()).isEqualTo(UPDATED_SALARY_RATE);
        assertThat(testEmployee.getSalarySecurity()).isEqualTo(UPDATED_SALARY_SECURITY);
        assertThat(testEmployee.getNumOfDepends()).isEqualTo(UPDATED_NUM_OF_DEPENDS);
        assertThat(testEmployee.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testEmployee.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testEmployee.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testEmployee.getNodes()).isEqualTo(UPDATED_NODES);
        assertThat(testEmployee.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testEmployee.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testEmployee.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
        assertThat(testEmployee.getUserIdCreated()).isEqualTo(UPDATED_USER_ID_CREATED);
        assertThat(testEmployee.getUserIdModified()).isEqualTo(UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Create the Employee

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeService.save(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
