package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.CustomerType;
import com.ak.domain.Customer;
import com.ak.domain.Company;
import com.ak.repository.CustomerTypeRepository;
import com.ak.service.CustomerTypeService;
import com.ak.web.rest.errors.ExceptionTranslator;
import com.ak.service.dto.CustomerTypeCriteria;
import com.ak.service.CustomerTypeQueryService;

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

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @Autowired
    private CustomerTypeService customerTypeService;

    @Autowired
    private CustomerTypeQueryService customerTypeQueryService;

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
        final CustomerTypeResource customerTypeResource = new CustomerTypeResource(customerTypeService, customerTypeQueryService);
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCustomerTypesByIdFiltering() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        Long id = customerType.getId();

        defaultCustomerTypeShouldBeFound("id.equals=" + id);
        defaultCustomerTypeShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCustomerTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where name equals to DEFAULT_NAME
        defaultCustomerTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the customerTypeList where name equals to UPDATED_NAME
        defaultCustomerTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where name not equals to DEFAULT_NAME
        defaultCustomerTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the customerTypeList where name not equals to UPDATED_NAME
        defaultCustomerTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustomerTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the customerTypeList where name equals to UPDATED_NAME
        defaultCustomerTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where name is not null
        defaultCustomerTypeShouldBeFound("name.specified=true");

        // Get all the customerTypeList where name is null
        defaultCustomerTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where name contains DEFAULT_NAME
        defaultCustomerTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the customerTypeList where name contains UPDATED_NAME
        defaultCustomerTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where name does not contain DEFAULT_NAME
        defaultCustomerTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the customerTypeList where name does not contain UPDATED_NAME
        defaultCustomerTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCustomerTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where description equals to DEFAULT_DESCRIPTION
        defaultCustomerTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the customerTypeList where description equals to UPDATED_DESCRIPTION
        defaultCustomerTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCustomerTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultCustomerTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the customerTypeList where description not equals to UPDATED_DESCRIPTION
        defaultCustomerTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCustomerTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCustomerTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the customerTypeList where description equals to UPDATED_DESCRIPTION
        defaultCustomerTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCustomerTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where description is not null
        defaultCustomerTypeShouldBeFound("description.specified=true");

        // Get all the customerTypeList where description is null
        defaultCustomerTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where description contains DEFAULT_DESCRIPTION
        defaultCustomerTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the customerTypeList where description contains UPDATED_DESCRIPTION
        defaultCustomerTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCustomerTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultCustomerTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the customerTypeList where description does not contain UPDATED_DESCRIPTION
        defaultCustomerTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCustomerTypesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where isActive equals to DEFAULT_IS_ACTIVE
        defaultCustomerTypeShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the customerTypeList where isActive equals to UPDATED_IS_ACTIVE
        defaultCustomerTypeShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCustomerTypesByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultCustomerTypeShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the customerTypeList where isActive not equals to UPDATED_IS_ACTIVE
        defaultCustomerTypeShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCustomerTypesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultCustomerTypeShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the customerTypeList where isActive equals to UPDATED_IS_ACTIVE
        defaultCustomerTypeShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCustomerTypesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList where isActive is not null
        defaultCustomerTypeShouldBeFound("isActive.specified=true");

        // Get all the customerTypeList where isActive is null
        defaultCustomerTypeShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerTypesByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        customerType.addCustomer(customer);
        customerTypeRepository.saveAndFlush(customerType);
        Long customerId = customer.getId();

        // Get all the customerTypeList where customer equals to customerId
        defaultCustomerTypeShouldBeFound("customerId.equals=" + customerId);

        // Get all the customerTypeList where customer equals to customerId + 1
        defaultCustomerTypeShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerTypesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);
        Company company = CompanyResourceIT.createEntity(em);
        em.persist(company);
        em.flush();
        customerType.setCompany(company);
        customerTypeRepository.saveAndFlush(customerType);
        Long companyId = company.getId();

        // Get all the customerTypeList where company equals to companyId
        defaultCustomerTypeShouldBeFound("companyId.equals=" + companyId);

        // Get all the customerTypeList where company equals to companyId + 1
        defaultCustomerTypeShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerTypeShouldBeFound(String filter) throws Exception {
        restCustomerTypeMockMvc.perform(get("/api/customer-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCustomerTypeMockMvc.perform(get("/api/customer-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerTypeShouldNotBeFound(String filter) throws Exception {
        restCustomerTypeMockMvc.perform(get("/api/customer-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerTypeMockMvc.perform(get("/api/customer-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
        customerTypeService.save(customerType);

        int databaseSizeBeforeUpdate = customerTypeRepository.findAll().size();

        // Update the customerType
        CustomerType updatedCustomerType = customerTypeRepository.findById(customerType.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerType are not directly saved in db
        em.detach(updatedCustomerType);
        updatedCustomerType
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
        customerTypeService.save(customerType);

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
