package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.CustomerType;
import com.ak.repository.CustomerTypeRepository;
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
 * Integration tests for the {@link CustomerTypeResource} REST controller.
 */
@SpringBootTest(classes = AkApp.class)
public class CustomerTypeResourceIT {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

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

    private MockMvc restCustomerTypeMockMvc;

    private CustomerType customerType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerTypeResource customerTypeResource = new CustomerTypeResource(customerTypeRepository);
        this.restCustomerTypeMockMvc = MockMvcBuilders.standaloneSetup(customerTypeResource)
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
    public static CustomerType createEntity(EntityManager em) {
        CustomerType customerType = new CustomerType()
            .companyId(DEFAULT_COMPANY_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return customerType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerType createUpdatedEntity(EntityManager em) {
        CustomerType customerType = new CustomerType()
            .companyId(UPDATED_COMPANY_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return customerType;
    }

    @BeforeEach
    public void initTest() {
        customerType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerType() throws Exception {
        int databaseSizeBeforeCreate = customerTypeRepository.findAll().size();

        // Create the CustomerType
        restCustomerTypeMockMvc.perform(post("/api/customer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerType)))
            .andExpect(status().isCreated());

        // Validate the CustomerType in the database
        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerType testCustomerType = customerTypeList.get(customerTypeList.size() - 1);
        assertThat(testCustomerType.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCustomerType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustomerType.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createCustomerTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerTypeRepository.findAll().size();

        // Create the CustomerType with an existing ID
        customerType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerTypeMockMvc.perform(post("/api/customer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerType)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerType in the database
        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTypeRepository.findAll().size();
        // set the field null
        customerType.setName(null);

        // Create the CustomerType, which fails.

        restCustomerTypeMockMvc.perform(post("/api/customer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerType)))
            .andExpect(status().isBadRequest());

        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerTypes() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList
        restCustomerTypeMockMvc.perform(get("/api/customer-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCustomerType() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get the customerType
        restCustomerTypeMockMvc.perform(get("/api/customer-types/{id}", customerType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerType.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerType() throws Exception {
        // Get the customerType
        restCustomerTypeMockMvc.perform(get("/api/customer-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerType() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        int databaseSizeBeforeUpdate = customerTypeRepository.findAll().size();

        // Update the customerType
        CustomerType updatedCustomerType = customerTypeRepository.findById(customerType.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerType are not directly saved in db
        em.detach(updatedCustomerType);
        updatedCustomerType
            .companyId(UPDATED_COMPANY_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);

        restCustomerTypeMockMvc.perform(put("/api/customer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerType)))
            .andExpect(status().isOk());

        // Validate the CustomerType in the database
        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeUpdate);
        CustomerType testCustomerType = customerTypeList.get(customerTypeList.size() - 1);
        assertThat(testCustomerType.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCustomerType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomerType.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerType() throws Exception {
        int databaseSizeBeforeUpdate = customerTypeRepository.findAll().size();

        // Create the CustomerType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerTypeMockMvc.perform(put("/api/customer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerType)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerType in the database
        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerType() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        int databaseSizeBeforeDelete = customerTypeRepository.findAll().size();

        // Delete the customerType
        restCustomerTypeMockMvc.perform(delete("/api/customer-types/{id}", customerType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
