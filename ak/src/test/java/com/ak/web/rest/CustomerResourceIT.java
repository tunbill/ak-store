package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.Customer;
import com.ak.domain.Invoice;
import com.ak.domain.CustomerType;
import com.ak.domain.Terms;
import com.ak.domain.Company;
import com.ak.repository.CustomerRepository;
import com.ak.service.CustomerService;
import com.ak.web.rest.errors.ExceptionTranslator;
import com.ak.service.dto.CustomerCriteria;
import com.ak.service.CustomerQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.ak.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@SpringBootTest(classes = AkApp.class)
public class CustomerResourceIT {

    private static final Boolean DEFAULT_IS_VENDOR = false;
    private static final Boolean UPDATED_IS_VENDOR = true;

    private static final Long DEFAULT_VENDOR_ID = 1L;
    private static final Long UPDATED_VENDOR_ID = 2L;
    private static final Long SMALLER_VENDOR_ID = 1L - 1L;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "*W@d'.i";
    private static final String UPDATED_EMAIL = "K_@~.'X";

    private static final String DEFAULT_TAX_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TAX_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;
    private static final Double SMALLER_BALANCE = 1D - 1D;

    private static final Double DEFAULT_TOTAL_BALANCE = 1D;
    private static final Double UPDATED_TOTAL_BALANCE = 2D;
    private static final Double SMALLER_TOTAL_BALANCE = 1D - 1D;

    private static final Double DEFAULT_OPEN_BALANCE = 1D;
    private static final Double UPDATED_OPEN_BALANCE = 2D;
    private static final Double SMALLER_OPEN_BALANCE = 1D - 1D;

    private static final Instant DEFAULT_OPEN_BALANCE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPEN_BALANCE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_CREDIT_LIMIT = 1D;
    private static final Double UPDATED_CREDIT_LIMIT = 2D;
    private static final Double SMALLER_CREDIT_LIMIT = 1D - 1D;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NAME = "BBBBBBBBBB";

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
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerQueryService customerQueryService;

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

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerResource customerResource = new CustomerResource(customerService, customerQueryService);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
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
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .isVendor(DEFAULT_IS_VENDOR)
            .vendorId(DEFAULT_VENDOR_ID)
            .code(DEFAULT_CODE)
            .companyName(DEFAULT_COMPANY_NAME)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE)
            .mobile(DEFAULT_MOBILE)
            .fax(DEFAULT_FAX)
            .email(DEFAULT_EMAIL)
            .taxCode(DEFAULT_TAX_CODE)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .bankAccount(DEFAULT_BANK_ACCOUNT)
            .bankName(DEFAULT_BANK_NAME)
            .balance(DEFAULT_BALANCE)
            .totalBalance(DEFAULT_TOTAL_BALANCE)
            .openBalance(DEFAULT_OPEN_BALANCE)
            .openBalanceDate(DEFAULT_OPEN_BALANCE_DATE)
            .creditLimit(DEFAULT_CREDIT_LIMIT)
            .notes(DEFAULT_NOTES)
            .contactName(DEFAULT_CONTACT_NAME)
            .isActive(DEFAULT_IS_ACTIVE)
            .timeCreated(DEFAULT_TIME_CREATED)
            .timeModified(DEFAULT_TIME_MODIFIED)
            .userIdCreated(DEFAULT_USER_ID_CREATED)
            .userIdModified(DEFAULT_USER_ID_MODIFIED);
        return customer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .isVendor(UPDATED_IS_VENDOR)
            .vendorId(UPDATED_VENDOR_ID)
            .code(UPDATED_CODE)
            .companyName(UPDATED_COMPANY_NAME)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .mobile(UPDATED_MOBILE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .taxCode(UPDATED_TAX_CODE)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .bankName(UPDATED_BANK_NAME)
            .balance(UPDATED_BALANCE)
            .totalBalance(UPDATED_TOTAL_BALANCE)
            .openBalance(UPDATED_OPEN_BALANCE)
            .openBalanceDate(UPDATED_OPEN_BALANCE_DATE)
            .creditLimit(UPDATED_CREDIT_LIMIT)
            .notes(UPDATED_NOTES)
            .contactName(UPDATED_CONTACT_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .timeCreated(UPDATED_TIME_CREATED)
            .timeModified(UPDATED_TIME_MODIFIED)
            .userIdCreated(UPDATED_USER_ID_CREATED)
            .userIdModified(UPDATED_USER_ID_MODIFIED);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.isIsVendor()).isEqualTo(DEFAULT_IS_VENDOR);
        assertThat(testCustomer.getVendorId()).isEqualTo(DEFAULT_VENDOR_ID);
        assertThat(testCustomer.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomer.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCustomer.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustomer.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testCustomer.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomer.getTaxCode()).isEqualTo(DEFAULT_TAX_CODE);
        assertThat(testCustomer.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testCustomer.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testCustomer.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testCustomer.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testCustomer.getTotalBalance()).isEqualTo(DEFAULT_TOTAL_BALANCE);
        assertThat(testCustomer.getOpenBalance()).isEqualTo(DEFAULT_OPEN_BALANCE);
        assertThat(testCustomer.getOpenBalanceDate()).isEqualTo(DEFAULT_OPEN_BALANCE_DATE);
        assertThat(testCustomer.getCreditLimit()).isEqualTo(DEFAULT_CREDIT_LIMIT);
        assertThat(testCustomer.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testCustomer.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testCustomer.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testCustomer.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testCustomer.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
        assertThat(testCustomer.getUserIdCreated()).isEqualTo(DEFAULT_USER_ID_CREATED);
        assertThat(testCustomer.getUserIdModified()).isEqualTo(DEFAULT_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCode(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCompanyName(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].isVendor").value(hasItem(DEFAULT_IS_VENDOR.booleanValue())))
            .andExpect(jsonPath("$.[*].vendorId").value(hasItem(DEFAULT_VENDOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].taxCode").value(hasItem(DEFAULT_TAX_CODE)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalBalance").value(hasItem(DEFAULT_TOTAL_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].openBalance").value(hasItem(DEFAULT_OPEN_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].openBalanceDate").value(hasItem(DEFAULT_OPEN_BALANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].creditLimit").value(hasItem(DEFAULT_CREDIT_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].timeCreated").value(hasItem(DEFAULT_TIME_CREATED.toString())))
            .andExpect(jsonPath("$.[*].timeModified").value(hasItem(DEFAULT_TIME_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].userIdCreated").value(hasItem(DEFAULT_USER_ID_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userIdModified").value(hasItem(DEFAULT_USER_ID_MODIFIED.intValue())));
    }
    
    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.isVendor").value(DEFAULT_IS_VENDOR.booleanValue()))
            .andExpect(jsonPath("$.vendorId").value(DEFAULT_VENDOR_ID.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.taxCode").value(DEFAULT_TAX_CODE))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.totalBalance").value(DEFAULT_TOTAL_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.openBalance").value(DEFAULT_OPEN_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.openBalanceDate").value(DEFAULT_OPEN_BALANCE_DATE.toString()))
            .andExpect(jsonPath("$.creditLimit").value(DEFAULT_CREDIT_LIMIT.doubleValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.contactName").value(DEFAULT_CONTACT_NAME))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.timeCreated").value(DEFAULT_TIME_CREATED.toString()))
            .andExpect(jsonPath("$.timeModified").value(DEFAULT_TIME_MODIFIED.toString()))
            .andExpect(jsonPath("$.userIdCreated").value(DEFAULT_USER_ID_CREATED.intValue()))
            .andExpect(jsonPath("$.userIdModified").value(DEFAULT_USER_ID_MODIFIED.intValue()));
    }


    @Test
    @Transactional
    public void getCustomersByIdFiltering() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        Long id = customer.getId();

        defaultCustomerShouldBeFound("id.equals=" + id);
        defaultCustomerShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCustomersByIsVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where isVendor equals to DEFAULT_IS_VENDOR
        defaultCustomerShouldBeFound("isVendor.equals=" + DEFAULT_IS_VENDOR);

        // Get all the customerList where isVendor equals to UPDATED_IS_VENDOR
        defaultCustomerShouldNotBeFound("isVendor.equals=" + UPDATED_IS_VENDOR);
    }

    @Test
    @Transactional
    public void getAllCustomersByIsVendorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where isVendor not equals to DEFAULT_IS_VENDOR
        defaultCustomerShouldNotBeFound("isVendor.notEquals=" + DEFAULT_IS_VENDOR);

        // Get all the customerList where isVendor not equals to UPDATED_IS_VENDOR
        defaultCustomerShouldBeFound("isVendor.notEquals=" + UPDATED_IS_VENDOR);
    }

    @Test
    @Transactional
    public void getAllCustomersByIsVendorIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where isVendor in DEFAULT_IS_VENDOR or UPDATED_IS_VENDOR
        defaultCustomerShouldBeFound("isVendor.in=" + DEFAULT_IS_VENDOR + "," + UPDATED_IS_VENDOR);

        // Get all the customerList where isVendor equals to UPDATED_IS_VENDOR
        defaultCustomerShouldNotBeFound("isVendor.in=" + UPDATED_IS_VENDOR);
    }

    @Test
    @Transactional
    public void getAllCustomersByIsVendorIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where isVendor is not null
        defaultCustomerShouldBeFound("isVendor.specified=true");

        // Get all the customerList where isVendor is null
        defaultCustomerShouldNotBeFound("isVendor.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByVendorIdIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where vendorId equals to DEFAULT_VENDOR_ID
        defaultCustomerShouldBeFound("vendorId.equals=" + DEFAULT_VENDOR_ID);

        // Get all the customerList where vendorId equals to UPDATED_VENDOR_ID
        defaultCustomerShouldNotBeFound("vendorId.equals=" + UPDATED_VENDOR_ID);
    }

    @Test
    @Transactional
    public void getAllCustomersByVendorIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where vendorId not equals to DEFAULT_VENDOR_ID
        defaultCustomerShouldNotBeFound("vendorId.notEquals=" + DEFAULT_VENDOR_ID);

        // Get all the customerList where vendorId not equals to UPDATED_VENDOR_ID
        defaultCustomerShouldBeFound("vendorId.notEquals=" + UPDATED_VENDOR_ID);
    }

    @Test
    @Transactional
    public void getAllCustomersByVendorIdIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where vendorId in DEFAULT_VENDOR_ID or UPDATED_VENDOR_ID
        defaultCustomerShouldBeFound("vendorId.in=" + DEFAULT_VENDOR_ID + "," + UPDATED_VENDOR_ID);

        // Get all the customerList where vendorId equals to UPDATED_VENDOR_ID
        defaultCustomerShouldNotBeFound("vendorId.in=" + UPDATED_VENDOR_ID);
    }

    @Test
    @Transactional
    public void getAllCustomersByVendorIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where vendorId is not null
        defaultCustomerShouldBeFound("vendorId.specified=true");

        // Get all the customerList where vendorId is null
        defaultCustomerShouldNotBeFound("vendorId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByVendorIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where vendorId is greater than or equal to DEFAULT_VENDOR_ID
        defaultCustomerShouldBeFound("vendorId.greaterThanOrEqual=" + DEFAULT_VENDOR_ID);

        // Get all the customerList where vendorId is greater than or equal to UPDATED_VENDOR_ID
        defaultCustomerShouldNotBeFound("vendorId.greaterThanOrEqual=" + UPDATED_VENDOR_ID);
    }

    @Test
    @Transactional
    public void getAllCustomersByVendorIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where vendorId is less than or equal to DEFAULT_VENDOR_ID
        defaultCustomerShouldBeFound("vendorId.lessThanOrEqual=" + DEFAULT_VENDOR_ID);

        // Get all the customerList where vendorId is less than or equal to SMALLER_VENDOR_ID
        defaultCustomerShouldNotBeFound("vendorId.lessThanOrEqual=" + SMALLER_VENDOR_ID);
    }

    @Test
    @Transactional
    public void getAllCustomersByVendorIdIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where vendorId is less than DEFAULT_VENDOR_ID
        defaultCustomerShouldNotBeFound("vendorId.lessThan=" + DEFAULT_VENDOR_ID);

        // Get all the customerList where vendorId is less than UPDATED_VENDOR_ID
        defaultCustomerShouldBeFound("vendorId.lessThan=" + UPDATED_VENDOR_ID);
    }

    @Test
    @Transactional
    public void getAllCustomersByVendorIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where vendorId is greater than DEFAULT_VENDOR_ID
        defaultCustomerShouldNotBeFound("vendorId.greaterThan=" + DEFAULT_VENDOR_ID);

        // Get all the customerList where vendorId is greater than SMALLER_VENDOR_ID
        defaultCustomerShouldBeFound("vendorId.greaterThan=" + SMALLER_VENDOR_ID);
    }


    @Test
    @Transactional
    public void getAllCustomersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where code equals to DEFAULT_CODE
        defaultCustomerShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the customerList where code equals to UPDATED_CODE
        defaultCustomerShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where code not equals to DEFAULT_CODE
        defaultCustomerShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the customerList where code not equals to UPDATED_CODE
        defaultCustomerShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCustomerShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the customerList where code equals to UPDATED_CODE
        defaultCustomerShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where code is not null
        defaultCustomerShouldBeFound("code.specified=true");

        // Get all the customerList where code is null
        defaultCustomerShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByCodeContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where code contains DEFAULT_CODE
        defaultCustomerShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the customerList where code contains UPDATED_CODE
        defaultCustomerShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where code does not contain DEFAULT_CODE
        defaultCustomerShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the customerList where code does not contain UPDATED_CODE
        defaultCustomerShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCustomersByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyName equals to DEFAULT_COMPANY_NAME
        defaultCustomerShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the customerList where companyName equals to UPDATED_COMPANY_NAME
        defaultCustomerShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByCompanyNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyName not equals to DEFAULT_COMPANY_NAME
        defaultCustomerShouldNotBeFound("companyName.notEquals=" + DEFAULT_COMPANY_NAME);

        // Get all the customerList where companyName not equals to UPDATED_COMPANY_NAME
        defaultCustomerShouldBeFound("companyName.notEquals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultCustomerShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the customerList where companyName equals to UPDATED_COMPANY_NAME
        defaultCustomerShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyName is not null
        defaultCustomerShouldBeFound("companyName.specified=true");

        // Get all the customerList where companyName is null
        defaultCustomerShouldNotBeFound("companyName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByCompanyNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyName contains DEFAULT_COMPANY_NAME
        defaultCustomerShouldBeFound("companyName.contains=" + DEFAULT_COMPANY_NAME);

        // Get all the customerList where companyName contains UPDATED_COMPANY_NAME
        defaultCustomerShouldNotBeFound("companyName.contains=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByCompanyNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyName does not contain DEFAULT_COMPANY_NAME
        defaultCustomerShouldNotBeFound("companyName.doesNotContain=" + DEFAULT_COMPANY_NAME);

        // Get all the customerList where companyName does not contain UPDATED_COMPANY_NAME
        defaultCustomerShouldBeFound("companyName.doesNotContain=" + UPDATED_COMPANY_NAME);
    }


    @Test
    @Transactional
    public void getAllCustomersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where address equals to DEFAULT_ADDRESS
        defaultCustomerShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the customerList where address equals to UPDATED_ADDRESS
        defaultCustomerShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCustomersByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where address not equals to DEFAULT_ADDRESS
        defaultCustomerShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the customerList where address not equals to UPDATED_ADDRESS
        defaultCustomerShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCustomersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCustomerShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the customerList where address equals to UPDATED_ADDRESS
        defaultCustomerShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCustomersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where address is not null
        defaultCustomerShouldBeFound("address.specified=true");

        // Get all the customerList where address is null
        defaultCustomerShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByAddressContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where address contains DEFAULT_ADDRESS
        defaultCustomerShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the customerList where address contains UPDATED_ADDRESS
        defaultCustomerShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCustomersByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where address does not contain DEFAULT_ADDRESS
        defaultCustomerShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the customerList where address does not contain UPDATED_ADDRESS
        defaultCustomerShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllCustomersByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phone equals to DEFAULT_PHONE
        defaultCustomerShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the customerList where phone equals to UPDATED_PHONE
        defaultCustomerShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCustomersByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phone not equals to DEFAULT_PHONE
        defaultCustomerShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the customerList where phone not equals to UPDATED_PHONE
        defaultCustomerShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCustomersByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCustomerShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the customerList where phone equals to UPDATED_PHONE
        defaultCustomerShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCustomersByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phone is not null
        defaultCustomerShouldBeFound("phone.specified=true");

        // Get all the customerList where phone is null
        defaultCustomerShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByPhoneContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phone contains DEFAULT_PHONE
        defaultCustomerShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the customerList where phone contains UPDATED_PHONE
        defaultCustomerShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllCustomersByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phone does not contain DEFAULT_PHONE
        defaultCustomerShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the customerList where phone does not contain UPDATED_PHONE
        defaultCustomerShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllCustomersByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where mobile equals to DEFAULT_MOBILE
        defaultCustomerShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the customerList where mobile equals to UPDATED_MOBILE
        defaultCustomerShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCustomersByMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where mobile not equals to DEFAULT_MOBILE
        defaultCustomerShouldNotBeFound("mobile.notEquals=" + DEFAULT_MOBILE);

        // Get all the customerList where mobile not equals to UPDATED_MOBILE
        defaultCustomerShouldBeFound("mobile.notEquals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCustomersByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultCustomerShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the customerList where mobile equals to UPDATED_MOBILE
        defaultCustomerShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCustomersByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where mobile is not null
        defaultCustomerShouldBeFound("mobile.specified=true");

        // Get all the customerList where mobile is null
        defaultCustomerShouldNotBeFound("mobile.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByMobileContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where mobile contains DEFAULT_MOBILE
        defaultCustomerShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the customerList where mobile contains UPDATED_MOBILE
        defaultCustomerShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllCustomersByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where mobile does not contain DEFAULT_MOBILE
        defaultCustomerShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the customerList where mobile does not contain UPDATED_MOBILE
        defaultCustomerShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }


    @Test
    @Transactional
    public void getAllCustomersByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where fax equals to DEFAULT_FAX
        defaultCustomerShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the customerList where fax equals to UPDATED_FAX
        defaultCustomerShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllCustomersByFaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where fax not equals to DEFAULT_FAX
        defaultCustomerShouldNotBeFound("fax.notEquals=" + DEFAULT_FAX);

        // Get all the customerList where fax not equals to UPDATED_FAX
        defaultCustomerShouldBeFound("fax.notEquals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllCustomersByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultCustomerShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the customerList where fax equals to UPDATED_FAX
        defaultCustomerShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllCustomersByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where fax is not null
        defaultCustomerShouldBeFound("fax.specified=true");

        // Get all the customerList where fax is null
        defaultCustomerShouldNotBeFound("fax.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByFaxContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where fax contains DEFAULT_FAX
        defaultCustomerShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the customerList where fax contains UPDATED_FAX
        defaultCustomerShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllCustomersByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where fax does not contain DEFAULT_FAX
        defaultCustomerShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the customerList where fax does not contain UPDATED_FAX
        defaultCustomerShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }


    @Test
    @Transactional
    public void getAllCustomersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where email equals to DEFAULT_EMAIL
        defaultCustomerShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the customerList where email equals to UPDATED_EMAIL
        defaultCustomerShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCustomersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where email not equals to DEFAULT_EMAIL
        defaultCustomerShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the customerList where email not equals to UPDATED_EMAIL
        defaultCustomerShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCustomersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCustomerShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the customerList where email equals to UPDATED_EMAIL
        defaultCustomerShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCustomersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where email is not null
        defaultCustomerShouldBeFound("email.specified=true");

        // Get all the customerList where email is null
        defaultCustomerShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByEmailContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where email contains DEFAULT_EMAIL
        defaultCustomerShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the customerList where email contains UPDATED_EMAIL
        defaultCustomerShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCustomersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where email does not contain DEFAULT_EMAIL
        defaultCustomerShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the customerList where email does not contain UPDATED_EMAIL
        defaultCustomerShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllCustomersByTaxCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where taxCode equals to DEFAULT_TAX_CODE
        defaultCustomerShouldBeFound("taxCode.equals=" + DEFAULT_TAX_CODE);

        // Get all the customerList where taxCode equals to UPDATED_TAX_CODE
        defaultCustomerShouldNotBeFound("taxCode.equals=" + UPDATED_TAX_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomersByTaxCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where taxCode not equals to DEFAULT_TAX_CODE
        defaultCustomerShouldNotBeFound("taxCode.notEquals=" + DEFAULT_TAX_CODE);

        // Get all the customerList where taxCode not equals to UPDATED_TAX_CODE
        defaultCustomerShouldBeFound("taxCode.notEquals=" + UPDATED_TAX_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomersByTaxCodeIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where taxCode in DEFAULT_TAX_CODE or UPDATED_TAX_CODE
        defaultCustomerShouldBeFound("taxCode.in=" + DEFAULT_TAX_CODE + "," + UPDATED_TAX_CODE);

        // Get all the customerList where taxCode equals to UPDATED_TAX_CODE
        defaultCustomerShouldNotBeFound("taxCode.in=" + UPDATED_TAX_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomersByTaxCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where taxCode is not null
        defaultCustomerShouldBeFound("taxCode.specified=true");

        // Get all the customerList where taxCode is null
        defaultCustomerShouldNotBeFound("taxCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByTaxCodeContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where taxCode contains DEFAULT_TAX_CODE
        defaultCustomerShouldBeFound("taxCode.contains=" + DEFAULT_TAX_CODE);

        // Get all the customerList where taxCode contains UPDATED_TAX_CODE
        defaultCustomerShouldNotBeFound("taxCode.contains=" + UPDATED_TAX_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomersByTaxCodeNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where taxCode does not contain DEFAULT_TAX_CODE
        defaultCustomerShouldNotBeFound("taxCode.doesNotContain=" + DEFAULT_TAX_CODE);

        // Get all the customerList where taxCode does not contain UPDATED_TAX_CODE
        defaultCustomerShouldBeFound("taxCode.doesNotContain=" + UPDATED_TAX_CODE);
    }


    @Test
    @Transactional
    public void getAllCustomersByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultCustomerShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the customerList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultCustomerShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountNumber not equals to DEFAULT_ACCOUNT_NUMBER
        defaultCustomerShouldNotBeFound("accountNumber.notEquals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the customerList where accountNumber not equals to UPDATED_ACCOUNT_NUMBER
        defaultCustomerShouldBeFound("accountNumber.notEquals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultCustomerShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the customerList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultCustomerShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountNumber is not null
        defaultCustomerShouldBeFound("accountNumber.specified=true");

        // Get all the customerList where accountNumber is null
        defaultCustomerShouldNotBeFound("accountNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultCustomerShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the customerList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultCustomerShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultCustomerShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the customerList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultCustomerShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllCustomersByBankAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where bankAccount equals to DEFAULT_BANK_ACCOUNT
        defaultCustomerShouldBeFound("bankAccount.equals=" + DEFAULT_BANK_ACCOUNT);

        // Get all the customerList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultCustomerShouldNotBeFound("bankAccount.equals=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomersByBankAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where bankAccount not equals to DEFAULT_BANK_ACCOUNT
        defaultCustomerShouldNotBeFound("bankAccount.notEquals=" + DEFAULT_BANK_ACCOUNT);

        // Get all the customerList where bankAccount not equals to UPDATED_BANK_ACCOUNT
        defaultCustomerShouldBeFound("bankAccount.notEquals=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomersByBankAccountIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where bankAccount in DEFAULT_BANK_ACCOUNT or UPDATED_BANK_ACCOUNT
        defaultCustomerShouldBeFound("bankAccount.in=" + DEFAULT_BANK_ACCOUNT + "," + UPDATED_BANK_ACCOUNT);

        // Get all the customerList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultCustomerShouldNotBeFound("bankAccount.in=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomersByBankAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where bankAccount is not null
        defaultCustomerShouldBeFound("bankAccount.specified=true");

        // Get all the customerList where bankAccount is null
        defaultCustomerShouldNotBeFound("bankAccount.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByBankAccountContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where bankAccount contains DEFAULT_BANK_ACCOUNT
        defaultCustomerShouldBeFound("bankAccount.contains=" + DEFAULT_BANK_ACCOUNT);

        // Get all the customerList where bankAccount contains UPDATED_BANK_ACCOUNT
        defaultCustomerShouldNotBeFound("bankAccount.contains=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomersByBankAccountNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where bankAccount does not contain DEFAULT_BANK_ACCOUNT
        defaultCustomerShouldNotBeFound("bankAccount.doesNotContain=" + DEFAULT_BANK_ACCOUNT);

        // Get all the customerList where bankAccount does not contain UPDATED_BANK_ACCOUNT
        defaultCustomerShouldBeFound("bankAccount.doesNotContain=" + UPDATED_BANK_ACCOUNT);
    }


    @Test
    @Transactional
    public void getAllCustomersByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where bankName equals to DEFAULT_BANK_NAME
        defaultCustomerShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the customerList where bankName equals to UPDATED_BANK_NAME
        defaultCustomerShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByBankNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where bankName not equals to DEFAULT_BANK_NAME
        defaultCustomerShouldNotBeFound("bankName.notEquals=" + DEFAULT_BANK_NAME);

        // Get all the customerList where bankName not equals to UPDATED_BANK_NAME
        defaultCustomerShouldBeFound("bankName.notEquals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultCustomerShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the customerList where bankName equals to UPDATED_BANK_NAME
        defaultCustomerShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where bankName is not null
        defaultCustomerShouldBeFound("bankName.specified=true");

        // Get all the customerList where bankName is null
        defaultCustomerShouldNotBeFound("bankName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByBankNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where bankName contains DEFAULT_BANK_NAME
        defaultCustomerShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the customerList where bankName contains UPDATED_BANK_NAME
        defaultCustomerShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where bankName does not contain DEFAULT_BANK_NAME
        defaultCustomerShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the customerList where bankName does not contain UPDATED_BANK_NAME
        defaultCustomerShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }


    @Test
    @Transactional
    public void getAllCustomersByBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where balance equals to DEFAULT_BALANCE
        defaultCustomerShouldBeFound("balance.equals=" + DEFAULT_BALANCE);

        // Get all the customerList where balance equals to UPDATED_BALANCE
        defaultCustomerShouldNotBeFound("balance.equals=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByBalanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where balance not equals to DEFAULT_BALANCE
        defaultCustomerShouldNotBeFound("balance.notEquals=" + DEFAULT_BALANCE);

        // Get all the customerList where balance not equals to UPDATED_BALANCE
        defaultCustomerShouldBeFound("balance.notEquals=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where balance in DEFAULT_BALANCE or UPDATED_BALANCE
        defaultCustomerShouldBeFound("balance.in=" + DEFAULT_BALANCE + "," + UPDATED_BALANCE);

        // Get all the customerList where balance equals to UPDATED_BALANCE
        defaultCustomerShouldNotBeFound("balance.in=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where balance is not null
        defaultCustomerShouldBeFound("balance.specified=true");

        // Get all the customerList where balance is null
        defaultCustomerShouldNotBeFound("balance.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where balance is greater than or equal to DEFAULT_BALANCE
        defaultCustomerShouldBeFound("balance.greaterThanOrEqual=" + DEFAULT_BALANCE);

        // Get all the customerList where balance is greater than or equal to UPDATED_BALANCE
        defaultCustomerShouldNotBeFound("balance.greaterThanOrEqual=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByBalanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where balance is less than or equal to DEFAULT_BALANCE
        defaultCustomerShouldBeFound("balance.lessThanOrEqual=" + DEFAULT_BALANCE);

        // Get all the customerList where balance is less than or equal to SMALLER_BALANCE
        defaultCustomerShouldNotBeFound("balance.lessThanOrEqual=" + SMALLER_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where balance is less than DEFAULT_BALANCE
        defaultCustomerShouldNotBeFound("balance.lessThan=" + DEFAULT_BALANCE);

        // Get all the customerList where balance is less than UPDATED_BALANCE
        defaultCustomerShouldBeFound("balance.lessThan=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByBalanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where balance is greater than DEFAULT_BALANCE
        defaultCustomerShouldNotBeFound("balance.greaterThan=" + DEFAULT_BALANCE);

        // Get all the customerList where balance is greater than SMALLER_BALANCE
        defaultCustomerShouldBeFound("balance.greaterThan=" + SMALLER_BALANCE);
    }


    @Test
    @Transactional
    public void getAllCustomersByTotalBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where totalBalance equals to DEFAULT_TOTAL_BALANCE
        defaultCustomerShouldBeFound("totalBalance.equals=" + DEFAULT_TOTAL_BALANCE);

        // Get all the customerList where totalBalance equals to UPDATED_TOTAL_BALANCE
        defaultCustomerShouldNotBeFound("totalBalance.equals=" + UPDATED_TOTAL_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByTotalBalanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where totalBalance not equals to DEFAULT_TOTAL_BALANCE
        defaultCustomerShouldNotBeFound("totalBalance.notEquals=" + DEFAULT_TOTAL_BALANCE);

        // Get all the customerList where totalBalance not equals to UPDATED_TOTAL_BALANCE
        defaultCustomerShouldBeFound("totalBalance.notEquals=" + UPDATED_TOTAL_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByTotalBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where totalBalance in DEFAULT_TOTAL_BALANCE or UPDATED_TOTAL_BALANCE
        defaultCustomerShouldBeFound("totalBalance.in=" + DEFAULT_TOTAL_BALANCE + "," + UPDATED_TOTAL_BALANCE);

        // Get all the customerList where totalBalance equals to UPDATED_TOTAL_BALANCE
        defaultCustomerShouldNotBeFound("totalBalance.in=" + UPDATED_TOTAL_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByTotalBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where totalBalance is not null
        defaultCustomerShouldBeFound("totalBalance.specified=true");

        // Get all the customerList where totalBalance is null
        defaultCustomerShouldNotBeFound("totalBalance.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByTotalBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where totalBalance is greater than or equal to DEFAULT_TOTAL_BALANCE
        defaultCustomerShouldBeFound("totalBalance.greaterThanOrEqual=" + DEFAULT_TOTAL_BALANCE);

        // Get all the customerList where totalBalance is greater than or equal to UPDATED_TOTAL_BALANCE
        defaultCustomerShouldNotBeFound("totalBalance.greaterThanOrEqual=" + UPDATED_TOTAL_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByTotalBalanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where totalBalance is less than or equal to DEFAULT_TOTAL_BALANCE
        defaultCustomerShouldBeFound("totalBalance.lessThanOrEqual=" + DEFAULT_TOTAL_BALANCE);

        // Get all the customerList where totalBalance is less than or equal to SMALLER_TOTAL_BALANCE
        defaultCustomerShouldNotBeFound("totalBalance.lessThanOrEqual=" + SMALLER_TOTAL_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByTotalBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where totalBalance is less than DEFAULT_TOTAL_BALANCE
        defaultCustomerShouldNotBeFound("totalBalance.lessThan=" + DEFAULT_TOTAL_BALANCE);

        // Get all the customerList where totalBalance is less than UPDATED_TOTAL_BALANCE
        defaultCustomerShouldBeFound("totalBalance.lessThan=" + UPDATED_TOTAL_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByTotalBalanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where totalBalance is greater than DEFAULT_TOTAL_BALANCE
        defaultCustomerShouldNotBeFound("totalBalance.greaterThan=" + DEFAULT_TOTAL_BALANCE);

        // Get all the customerList where totalBalance is greater than SMALLER_TOTAL_BALANCE
        defaultCustomerShouldBeFound("totalBalance.greaterThan=" + SMALLER_TOTAL_BALANCE);
    }


    @Test
    @Transactional
    public void getAllCustomersByOpenBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where openBalance equals to DEFAULT_OPEN_BALANCE
        defaultCustomerShouldBeFound("openBalance.equals=" + DEFAULT_OPEN_BALANCE);

        // Get all the customerList where openBalance equals to UPDATED_OPEN_BALANCE
        defaultCustomerShouldNotBeFound("openBalance.equals=" + UPDATED_OPEN_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByOpenBalanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where openBalance not equals to DEFAULT_OPEN_BALANCE
        defaultCustomerShouldNotBeFound("openBalance.notEquals=" + DEFAULT_OPEN_BALANCE);

        // Get all the customerList where openBalance not equals to UPDATED_OPEN_BALANCE
        defaultCustomerShouldBeFound("openBalance.notEquals=" + UPDATED_OPEN_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByOpenBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where openBalance in DEFAULT_OPEN_BALANCE or UPDATED_OPEN_BALANCE
        defaultCustomerShouldBeFound("openBalance.in=" + DEFAULT_OPEN_BALANCE + "," + UPDATED_OPEN_BALANCE);

        // Get all the customerList where openBalance equals to UPDATED_OPEN_BALANCE
        defaultCustomerShouldNotBeFound("openBalance.in=" + UPDATED_OPEN_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByOpenBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where openBalance is not null
        defaultCustomerShouldBeFound("openBalance.specified=true");

        // Get all the customerList where openBalance is null
        defaultCustomerShouldNotBeFound("openBalance.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByOpenBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where openBalance is greater than or equal to DEFAULT_OPEN_BALANCE
        defaultCustomerShouldBeFound("openBalance.greaterThanOrEqual=" + DEFAULT_OPEN_BALANCE);

        // Get all the customerList where openBalance is greater than or equal to UPDATED_OPEN_BALANCE
        defaultCustomerShouldNotBeFound("openBalance.greaterThanOrEqual=" + UPDATED_OPEN_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByOpenBalanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where openBalance is less than or equal to DEFAULT_OPEN_BALANCE
        defaultCustomerShouldBeFound("openBalance.lessThanOrEqual=" + DEFAULT_OPEN_BALANCE);

        // Get all the customerList where openBalance is less than or equal to SMALLER_OPEN_BALANCE
        defaultCustomerShouldNotBeFound("openBalance.lessThanOrEqual=" + SMALLER_OPEN_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByOpenBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where openBalance is less than DEFAULT_OPEN_BALANCE
        defaultCustomerShouldNotBeFound("openBalance.lessThan=" + DEFAULT_OPEN_BALANCE);

        // Get all the customerList where openBalance is less than UPDATED_OPEN_BALANCE
        defaultCustomerShouldBeFound("openBalance.lessThan=" + UPDATED_OPEN_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomersByOpenBalanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where openBalance is greater than DEFAULT_OPEN_BALANCE
        defaultCustomerShouldNotBeFound("openBalance.greaterThan=" + DEFAULT_OPEN_BALANCE);

        // Get all the customerList where openBalance is greater than SMALLER_OPEN_BALANCE
        defaultCustomerShouldBeFound("openBalance.greaterThan=" + SMALLER_OPEN_BALANCE);
    }


    @Test
    @Transactional
    public void getAllCustomersByOpenBalanceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where openBalanceDate equals to DEFAULT_OPEN_BALANCE_DATE
        defaultCustomerShouldBeFound("openBalanceDate.equals=" + DEFAULT_OPEN_BALANCE_DATE);

        // Get all the customerList where openBalanceDate equals to UPDATED_OPEN_BALANCE_DATE
        defaultCustomerShouldNotBeFound("openBalanceDate.equals=" + UPDATED_OPEN_BALANCE_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomersByOpenBalanceDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where openBalanceDate not equals to DEFAULT_OPEN_BALANCE_DATE
        defaultCustomerShouldNotBeFound("openBalanceDate.notEquals=" + DEFAULT_OPEN_BALANCE_DATE);

        // Get all the customerList where openBalanceDate not equals to UPDATED_OPEN_BALANCE_DATE
        defaultCustomerShouldBeFound("openBalanceDate.notEquals=" + UPDATED_OPEN_BALANCE_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomersByOpenBalanceDateIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where openBalanceDate in DEFAULT_OPEN_BALANCE_DATE or UPDATED_OPEN_BALANCE_DATE
        defaultCustomerShouldBeFound("openBalanceDate.in=" + DEFAULT_OPEN_BALANCE_DATE + "," + UPDATED_OPEN_BALANCE_DATE);

        // Get all the customerList where openBalanceDate equals to UPDATED_OPEN_BALANCE_DATE
        defaultCustomerShouldNotBeFound("openBalanceDate.in=" + UPDATED_OPEN_BALANCE_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomersByOpenBalanceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where openBalanceDate is not null
        defaultCustomerShouldBeFound("openBalanceDate.specified=true");

        // Get all the customerList where openBalanceDate is null
        defaultCustomerShouldNotBeFound("openBalanceDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByCreditLimitIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where creditLimit equals to DEFAULT_CREDIT_LIMIT
        defaultCustomerShouldBeFound("creditLimit.equals=" + DEFAULT_CREDIT_LIMIT);

        // Get all the customerList where creditLimit equals to UPDATED_CREDIT_LIMIT
        defaultCustomerShouldNotBeFound("creditLimit.equals=" + UPDATED_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    public void getAllCustomersByCreditLimitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where creditLimit not equals to DEFAULT_CREDIT_LIMIT
        defaultCustomerShouldNotBeFound("creditLimit.notEquals=" + DEFAULT_CREDIT_LIMIT);

        // Get all the customerList where creditLimit not equals to UPDATED_CREDIT_LIMIT
        defaultCustomerShouldBeFound("creditLimit.notEquals=" + UPDATED_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    public void getAllCustomersByCreditLimitIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where creditLimit in DEFAULT_CREDIT_LIMIT or UPDATED_CREDIT_LIMIT
        defaultCustomerShouldBeFound("creditLimit.in=" + DEFAULT_CREDIT_LIMIT + "," + UPDATED_CREDIT_LIMIT);

        // Get all the customerList where creditLimit equals to UPDATED_CREDIT_LIMIT
        defaultCustomerShouldNotBeFound("creditLimit.in=" + UPDATED_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    public void getAllCustomersByCreditLimitIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where creditLimit is not null
        defaultCustomerShouldBeFound("creditLimit.specified=true");

        // Get all the customerList where creditLimit is null
        defaultCustomerShouldNotBeFound("creditLimit.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByCreditLimitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where creditLimit is greater than or equal to DEFAULT_CREDIT_LIMIT
        defaultCustomerShouldBeFound("creditLimit.greaterThanOrEqual=" + DEFAULT_CREDIT_LIMIT);

        // Get all the customerList where creditLimit is greater than or equal to UPDATED_CREDIT_LIMIT
        defaultCustomerShouldNotBeFound("creditLimit.greaterThanOrEqual=" + UPDATED_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    public void getAllCustomersByCreditLimitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where creditLimit is less than or equal to DEFAULT_CREDIT_LIMIT
        defaultCustomerShouldBeFound("creditLimit.lessThanOrEqual=" + DEFAULT_CREDIT_LIMIT);

        // Get all the customerList where creditLimit is less than or equal to SMALLER_CREDIT_LIMIT
        defaultCustomerShouldNotBeFound("creditLimit.lessThanOrEqual=" + SMALLER_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    public void getAllCustomersByCreditLimitIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where creditLimit is less than DEFAULT_CREDIT_LIMIT
        defaultCustomerShouldNotBeFound("creditLimit.lessThan=" + DEFAULT_CREDIT_LIMIT);

        // Get all the customerList where creditLimit is less than UPDATED_CREDIT_LIMIT
        defaultCustomerShouldBeFound("creditLimit.lessThan=" + UPDATED_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    public void getAllCustomersByCreditLimitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where creditLimit is greater than DEFAULT_CREDIT_LIMIT
        defaultCustomerShouldNotBeFound("creditLimit.greaterThan=" + DEFAULT_CREDIT_LIMIT);

        // Get all the customerList where creditLimit is greater than SMALLER_CREDIT_LIMIT
        defaultCustomerShouldBeFound("creditLimit.greaterThan=" + SMALLER_CREDIT_LIMIT);
    }


    @Test
    @Transactional
    public void getAllCustomersByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where notes equals to DEFAULT_NOTES
        defaultCustomerShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the customerList where notes equals to UPDATED_NOTES
        defaultCustomerShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllCustomersByNotesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where notes not equals to DEFAULT_NOTES
        defaultCustomerShouldNotBeFound("notes.notEquals=" + DEFAULT_NOTES);

        // Get all the customerList where notes not equals to UPDATED_NOTES
        defaultCustomerShouldBeFound("notes.notEquals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllCustomersByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultCustomerShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the customerList where notes equals to UPDATED_NOTES
        defaultCustomerShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllCustomersByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where notes is not null
        defaultCustomerShouldBeFound("notes.specified=true");

        // Get all the customerList where notes is null
        defaultCustomerShouldNotBeFound("notes.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByNotesContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where notes contains DEFAULT_NOTES
        defaultCustomerShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the customerList where notes contains UPDATED_NOTES
        defaultCustomerShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllCustomersByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where notes does not contain DEFAULT_NOTES
        defaultCustomerShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the customerList where notes does not contain UPDATED_NOTES
        defaultCustomerShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }


    @Test
    @Transactional
    public void getAllCustomersByContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where contactName equals to DEFAULT_CONTACT_NAME
        defaultCustomerShouldBeFound("contactName.equals=" + DEFAULT_CONTACT_NAME);

        // Get all the customerList where contactName equals to UPDATED_CONTACT_NAME
        defaultCustomerShouldNotBeFound("contactName.equals=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByContactNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where contactName not equals to DEFAULT_CONTACT_NAME
        defaultCustomerShouldNotBeFound("contactName.notEquals=" + DEFAULT_CONTACT_NAME);

        // Get all the customerList where contactName not equals to UPDATED_CONTACT_NAME
        defaultCustomerShouldBeFound("contactName.notEquals=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where contactName in DEFAULT_CONTACT_NAME or UPDATED_CONTACT_NAME
        defaultCustomerShouldBeFound("contactName.in=" + DEFAULT_CONTACT_NAME + "," + UPDATED_CONTACT_NAME);

        // Get all the customerList where contactName equals to UPDATED_CONTACT_NAME
        defaultCustomerShouldNotBeFound("contactName.in=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where contactName is not null
        defaultCustomerShouldBeFound("contactName.specified=true");

        // Get all the customerList where contactName is null
        defaultCustomerShouldNotBeFound("contactName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByContactNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where contactName contains DEFAULT_CONTACT_NAME
        defaultCustomerShouldBeFound("contactName.contains=" + DEFAULT_CONTACT_NAME);

        // Get all the customerList where contactName contains UPDATED_CONTACT_NAME
        defaultCustomerShouldNotBeFound("contactName.contains=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByContactNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where contactName does not contain DEFAULT_CONTACT_NAME
        defaultCustomerShouldNotBeFound("contactName.doesNotContain=" + DEFAULT_CONTACT_NAME);

        // Get all the customerList where contactName does not contain UPDATED_CONTACT_NAME
        defaultCustomerShouldBeFound("contactName.doesNotContain=" + UPDATED_CONTACT_NAME);
    }


    @Test
    @Transactional
    public void getAllCustomersByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where isActive equals to DEFAULT_IS_ACTIVE
        defaultCustomerShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the customerList where isActive equals to UPDATED_IS_ACTIVE
        defaultCustomerShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCustomersByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultCustomerShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the customerList where isActive not equals to UPDATED_IS_ACTIVE
        defaultCustomerShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCustomersByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultCustomerShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the customerList where isActive equals to UPDATED_IS_ACTIVE
        defaultCustomerShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCustomersByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where isActive is not null
        defaultCustomerShouldBeFound("isActive.specified=true");

        // Get all the customerList where isActive is null
        defaultCustomerShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByTimeCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where timeCreated equals to DEFAULT_TIME_CREATED
        defaultCustomerShouldBeFound("timeCreated.equals=" + DEFAULT_TIME_CREATED);

        // Get all the customerList where timeCreated equals to UPDATED_TIME_CREATED
        defaultCustomerShouldNotBeFound("timeCreated.equals=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllCustomersByTimeCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where timeCreated not equals to DEFAULT_TIME_CREATED
        defaultCustomerShouldNotBeFound("timeCreated.notEquals=" + DEFAULT_TIME_CREATED);

        // Get all the customerList where timeCreated not equals to UPDATED_TIME_CREATED
        defaultCustomerShouldBeFound("timeCreated.notEquals=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllCustomersByTimeCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where timeCreated in DEFAULT_TIME_CREATED or UPDATED_TIME_CREATED
        defaultCustomerShouldBeFound("timeCreated.in=" + DEFAULT_TIME_CREATED + "," + UPDATED_TIME_CREATED);

        // Get all the customerList where timeCreated equals to UPDATED_TIME_CREATED
        defaultCustomerShouldNotBeFound("timeCreated.in=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllCustomersByTimeCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where timeCreated is not null
        defaultCustomerShouldBeFound("timeCreated.specified=true");

        // Get all the customerList where timeCreated is null
        defaultCustomerShouldNotBeFound("timeCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByTimeModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where timeModified equals to DEFAULT_TIME_MODIFIED
        defaultCustomerShouldBeFound("timeModified.equals=" + DEFAULT_TIME_MODIFIED);

        // Get all the customerList where timeModified equals to UPDATED_TIME_MODIFIED
        defaultCustomerShouldNotBeFound("timeModified.equals=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCustomersByTimeModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where timeModified not equals to DEFAULT_TIME_MODIFIED
        defaultCustomerShouldNotBeFound("timeModified.notEquals=" + DEFAULT_TIME_MODIFIED);

        // Get all the customerList where timeModified not equals to UPDATED_TIME_MODIFIED
        defaultCustomerShouldBeFound("timeModified.notEquals=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCustomersByTimeModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where timeModified in DEFAULT_TIME_MODIFIED or UPDATED_TIME_MODIFIED
        defaultCustomerShouldBeFound("timeModified.in=" + DEFAULT_TIME_MODIFIED + "," + UPDATED_TIME_MODIFIED);

        // Get all the customerList where timeModified equals to UPDATED_TIME_MODIFIED
        defaultCustomerShouldNotBeFound("timeModified.in=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCustomersByTimeModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where timeModified is not null
        defaultCustomerShouldBeFound("timeModified.specified=true");

        // Get all the customerList where timeModified is null
        defaultCustomerShouldNotBeFound("timeModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdCreated equals to DEFAULT_USER_ID_CREATED
        defaultCustomerShouldBeFound("userIdCreated.equals=" + DEFAULT_USER_ID_CREATED);

        // Get all the customerList where userIdCreated equals to UPDATED_USER_ID_CREATED
        defaultCustomerShouldNotBeFound("userIdCreated.equals=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdCreated not equals to DEFAULT_USER_ID_CREATED
        defaultCustomerShouldNotBeFound("userIdCreated.notEquals=" + DEFAULT_USER_ID_CREATED);

        // Get all the customerList where userIdCreated not equals to UPDATED_USER_ID_CREATED
        defaultCustomerShouldBeFound("userIdCreated.notEquals=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdCreated in DEFAULT_USER_ID_CREATED or UPDATED_USER_ID_CREATED
        defaultCustomerShouldBeFound("userIdCreated.in=" + DEFAULT_USER_ID_CREATED + "," + UPDATED_USER_ID_CREATED);

        // Get all the customerList where userIdCreated equals to UPDATED_USER_ID_CREATED
        defaultCustomerShouldNotBeFound("userIdCreated.in=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdCreated is not null
        defaultCustomerShouldBeFound("userIdCreated.specified=true");

        // Get all the customerList where userIdCreated is null
        defaultCustomerShouldNotBeFound("userIdCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdCreatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdCreated is greater than or equal to DEFAULT_USER_ID_CREATED
        defaultCustomerShouldBeFound("userIdCreated.greaterThanOrEqual=" + DEFAULT_USER_ID_CREATED);

        // Get all the customerList where userIdCreated is greater than or equal to UPDATED_USER_ID_CREATED
        defaultCustomerShouldNotBeFound("userIdCreated.greaterThanOrEqual=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdCreatedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdCreated is less than or equal to DEFAULT_USER_ID_CREATED
        defaultCustomerShouldBeFound("userIdCreated.lessThanOrEqual=" + DEFAULT_USER_ID_CREATED);

        // Get all the customerList where userIdCreated is less than or equal to SMALLER_USER_ID_CREATED
        defaultCustomerShouldNotBeFound("userIdCreated.lessThanOrEqual=" + SMALLER_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdCreatedIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdCreated is less than DEFAULT_USER_ID_CREATED
        defaultCustomerShouldNotBeFound("userIdCreated.lessThan=" + DEFAULT_USER_ID_CREATED);

        // Get all the customerList where userIdCreated is less than UPDATED_USER_ID_CREATED
        defaultCustomerShouldBeFound("userIdCreated.lessThan=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdCreatedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdCreated is greater than DEFAULT_USER_ID_CREATED
        defaultCustomerShouldNotBeFound("userIdCreated.greaterThan=" + DEFAULT_USER_ID_CREATED);

        // Get all the customerList where userIdCreated is greater than SMALLER_USER_ID_CREATED
        defaultCustomerShouldBeFound("userIdCreated.greaterThan=" + SMALLER_USER_ID_CREATED);
    }


    @Test
    @Transactional
    public void getAllCustomersByUserIdModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdModified equals to DEFAULT_USER_ID_MODIFIED
        defaultCustomerShouldBeFound("userIdModified.equals=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the customerList where userIdModified equals to UPDATED_USER_ID_MODIFIED
        defaultCustomerShouldNotBeFound("userIdModified.equals=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdModified not equals to DEFAULT_USER_ID_MODIFIED
        defaultCustomerShouldNotBeFound("userIdModified.notEquals=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the customerList where userIdModified not equals to UPDATED_USER_ID_MODIFIED
        defaultCustomerShouldBeFound("userIdModified.notEquals=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdModified in DEFAULT_USER_ID_MODIFIED or UPDATED_USER_ID_MODIFIED
        defaultCustomerShouldBeFound("userIdModified.in=" + DEFAULT_USER_ID_MODIFIED + "," + UPDATED_USER_ID_MODIFIED);

        // Get all the customerList where userIdModified equals to UPDATED_USER_ID_MODIFIED
        defaultCustomerShouldNotBeFound("userIdModified.in=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdModified is not null
        defaultCustomerShouldBeFound("userIdModified.specified=true");

        // Get all the customerList where userIdModified is null
        defaultCustomerShouldNotBeFound("userIdModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdModifiedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdModified is greater than or equal to DEFAULT_USER_ID_MODIFIED
        defaultCustomerShouldBeFound("userIdModified.greaterThanOrEqual=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the customerList where userIdModified is greater than or equal to UPDATED_USER_ID_MODIFIED
        defaultCustomerShouldNotBeFound("userIdModified.greaterThanOrEqual=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdModifiedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdModified is less than or equal to DEFAULT_USER_ID_MODIFIED
        defaultCustomerShouldBeFound("userIdModified.lessThanOrEqual=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the customerList where userIdModified is less than or equal to SMALLER_USER_ID_MODIFIED
        defaultCustomerShouldNotBeFound("userIdModified.lessThanOrEqual=" + SMALLER_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdModifiedIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdModified is less than DEFAULT_USER_ID_MODIFIED
        defaultCustomerShouldNotBeFound("userIdModified.lessThan=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the customerList where userIdModified is less than UPDATED_USER_ID_MODIFIED
        defaultCustomerShouldBeFound("userIdModified.lessThan=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCustomersByUserIdModifiedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where userIdModified is greater than DEFAULT_USER_ID_MODIFIED
        defaultCustomerShouldNotBeFound("userIdModified.greaterThan=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the customerList where userIdModified is greater than SMALLER_USER_ID_MODIFIED
        defaultCustomerShouldBeFound("userIdModified.greaterThan=" + SMALLER_USER_ID_MODIFIED);
    }


    @Test
    @Transactional
    public void getAllCustomersByInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        Invoice invoice = InvoiceResourceIT.createEntity(em);
        em.persist(invoice);
        em.flush();
        customer.addInvoice(invoice);
        customerRepository.saveAndFlush(customer);
        Long invoiceId = invoice.getId();

        // Get all the customerList where invoice equals to invoiceId
        defaultCustomerShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the customerList where invoice equals to invoiceId + 1
        defaultCustomerShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomersByCustomerTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        CustomerType customerType = CustomerTypeResourceIT.createEntity(em);
        em.persist(customerType);
        em.flush();
        customer.setCustomerType(customerType);
        customerRepository.saveAndFlush(customer);
        Long customerTypeId = customerType.getId();

        // Get all the customerList where customerType equals to customerTypeId
        defaultCustomerShouldBeFound("customerTypeId.equals=" + customerTypeId);

        // Get all the customerList where customerType equals to customerTypeId + 1
        defaultCustomerShouldNotBeFound("customerTypeId.equals=" + (customerTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomersByTermsIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        Terms terms = TermsResourceIT.createEntity(em);
        em.persist(terms);
        em.flush();
        customer.setTerms(terms);
        customerRepository.saveAndFlush(customer);
        Long termsId = terms.getId();

        // Get all the customerList where terms equals to termsId
        defaultCustomerShouldBeFound("termsId.equals=" + termsId);

        // Get all the customerList where terms equals to termsId + 1
        defaultCustomerShouldNotBeFound("termsId.equals=" + (termsId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomersByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        Company company = CompanyResourceIT.createEntity(em);
        em.persist(company);
        em.flush();
        customer.setCompany(company);
        customerRepository.saveAndFlush(customer);
        Long companyId = company.getId();

        // Get all the customerList where company equals to companyId
        defaultCustomerShouldBeFound("companyId.equals=" + companyId);

        // Get all the customerList where company equals to companyId + 1
        defaultCustomerShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerShouldBeFound(String filter) throws Exception {
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].isVendor").value(hasItem(DEFAULT_IS_VENDOR.booleanValue())))
            .andExpect(jsonPath("$.[*].vendorId").value(hasItem(DEFAULT_VENDOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].taxCode").value(hasItem(DEFAULT_TAX_CODE)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalBalance").value(hasItem(DEFAULT_TOTAL_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].openBalance").value(hasItem(DEFAULT_OPEN_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].openBalanceDate").value(hasItem(DEFAULT_OPEN_BALANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].creditLimit").value(hasItem(DEFAULT_CREDIT_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].timeCreated").value(hasItem(DEFAULT_TIME_CREATED.toString())))
            .andExpect(jsonPath("$.[*].timeModified").value(hasItem(DEFAULT_TIME_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].userIdCreated").value(hasItem(DEFAULT_USER_ID_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userIdModified").value(hasItem(DEFAULT_USER_ID_MODIFIED.intValue())));

        // Check, that the count call also returns 1
        restCustomerMockMvc.perform(get("/api/customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerShouldNotBeFound(String filter) throws Exception {
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerMockMvc.perform(get("/api/customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .isVendor(UPDATED_IS_VENDOR)
            .vendorId(UPDATED_VENDOR_ID)
            .code(UPDATED_CODE)
            .companyName(UPDATED_COMPANY_NAME)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .mobile(UPDATED_MOBILE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .taxCode(UPDATED_TAX_CODE)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .bankName(UPDATED_BANK_NAME)
            .balance(UPDATED_BALANCE)
            .totalBalance(UPDATED_TOTAL_BALANCE)
            .openBalance(UPDATED_OPEN_BALANCE)
            .openBalanceDate(UPDATED_OPEN_BALANCE_DATE)
            .creditLimit(UPDATED_CREDIT_LIMIT)
            .notes(UPDATED_NOTES)
            .contactName(UPDATED_CONTACT_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .timeCreated(UPDATED_TIME_CREATED)
            .timeModified(UPDATED_TIME_MODIFIED)
            .userIdCreated(UPDATED_USER_ID_CREATED)
            .userIdModified(UPDATED_USER_ID_MODIFIED);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomer)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.isIsVendor()).isEqualTo(UPDATED_IS_VENDOR);
        assertThat(testCustomer.getVendorId()).isEqualTo(UPDATED_VENDOR_ID);
        assertThat(testCustomer.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomer.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCustomer.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustomer.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCustomer.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomer.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testCustomer.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCustomer.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testCustomer.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testCustomer.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testCustomer.getTotalBalance()).isEqualTo(UPDATED_TOTAL_BALANCE);
        assertThat(testCustomer.getOpenBalance()).isEqualTo(UPDATED_OPEN_BALANCE);
        assertThat(testCustomer.getOpenBalanceDate()).isEqualTo(UPDATED_OPEN_BALANCE_DATE);
        assertThat(testCustomer.getCreditLimit()).isEqualTo(UPDATED_CREDIT_LIMIT);
        assertThat(testCustomer.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCustomer.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testCustomer.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testCustomer.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testCustomer.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
        assertThat(testCustomer.getUserIdCreated()).isEqualTo(UPDATED_USER_ID_CREATED);
        assertThat(testCustomer.getUserIdModified()).isEqualTo(UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Create the Customer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
