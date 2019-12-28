package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.InvoiceLine;
import com.ak.domain.Invoice;
import com.ak.domain.Item;
import com.ak.domain.Company;
import com.ak.repository.InvoiceLineRepository;
import com.ak.service.InvoiceLineService;
import com.ak.web.rest.errors.ExceptionTranslator;
import com.ak.service.dto.InvoiceLineCriteria;
import com.ak.service.InvoiceLineQueryService;

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

import com.ak.domain.enumeration.ProcessStatus;
/**
 * Integration tests for the {@link InvoiceLineResource} REST controller.
 */
@SpringBootTest(classes = AkApp.class)
public class InvoiceLineResourceIT {

    private static final Integer DEFAULT_DISPLAY_ORDER = 1;
    private static final Integer UPDATED_DISPLAY_ORDER = 2;
    private static final Integer SMALLER_DISPLAY_ORDER = 1 - 1;

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;
    private static final Double SMALLER_QUANTITY = 1D - 1D;

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;
    private static final Double SMALLER_RATE = 1D - 1D;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_DISCOUNT_PCT = 1D;
    private static final Double UPDATED_DISCOUNT_PCT = 2D;
    private static final Double SMALLER_DISCOUNT_PCT = 1D - 1D;

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final ProcessStatus DEFAULT_STATUS = ProcessStatus.OPEN;
    private static final ProcessStatus UPDATED_STATUS = ProcessStatus.CLOSE;

    @Autowired
    private InvoiceLineRepository invoiceLineRepository;

    @Autowired
    private InvoiceLineService invoiceLineService;

    @Autowired
    private InvoiceLineQueryService invoiceLineQueryService;

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

    private MockMvc restInvoiceLineMockMvc;

    private InvoiceLine invoiceLine;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceLineResource invoiceLineResource = new InvoiceLineResource(invoiceLineService, invoiceLineQueryService);
        this.restInvoiceLineMockMvc = MockMvcBuilders.standaloneSetup(invoiceLineResource)
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
    public static InvoiceLine createEntity(EntityManager em) {
        InvoiceLine invoiceLine = new InvoiceLine()
            .displayOrder(DEFAULT_DISPLAY_ORDER)
            .itemName(DEFAULT_ITEM_NAME)
            .unitName(DEFAULT_UNIT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .rate(DEFAULT_RATE)
            .amount(DEFAULT_AMOUNT)
            .discountPct(DEFAULT_DISCOUNT_PCT)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .status(DEFAULT_STATUS);
        return invoiceLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceLine createUpdatedEntity(EntityManager em) {
        InvoiceLine invoiceLine = new InvoiceLine()
            .displayOrder(UPDATED_DISPLAY_ORDER)
            .itemName(UPDATED_ITEM_NAME)
            .unitName(UPDATED_UNIT_NAME)
            .quantity(UPDATED_QUANTITY)
            .rate(UPDATED_RATE)
            .amount(UPDATED_AMOUNT)
            .discountPct(UPDATED_DISCOUNT_PCT)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .status(UPDATED_STATUS);
        return invoiceLine;
    }

    @BeforeEach
    public void initTest() {
        invoiceLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceLine() throws Exception {
        int databaseSizeBeforeCreate = invoiceLineRepository.findAll().size();

        // Create the InvoiceLine
        restInvoiceLineMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLine)))
            .andExpect(status().isCreated());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceLine testInvoiceLine = invoiceLineList.get(invoiceLineList.size() - 1);
        assertThat(testInvoiceLine.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);
        assertThat(testInvoiceLine.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testInvoiceLine.getUnitName()).isEqualTo(DEFAULT_UNIT_NAME);
        assertThat(testInvoiceLine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testInvoiceLine.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testInvoiceLine.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testInvoiceLine.getDiscountPct()).isEqualTo(DEFAULT_DISCOUNT_PCT);
        assertThat(testInvoiceLine.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testInvoiceLine.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInvoiceLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceLineRepository.findAll().size();

        // Create the InvoiceLine with an existing ID
        invoiceLine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceLineMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLine)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInvoiceLines() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList
        restInvoiceLineMockMvc.perform(get("/api/invoice-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].discountPct").value(hasItem(DEFAULT_DISCOUNT_PCT.doubleValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getInvoiceLine() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get the invoiceLine
        restInvoiceLineMockMvc.perform(get("/api/invoice-lines/{id}", invoiceLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceLine.getId().intValue()))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME))
            .andExpect(jsonPath("$.unitName").value(DEFAULT_UNIT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.discountPct").value(DEFAULT_DISCOUNT_PCT.doubleValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getInvoiceLinesByIdFiltering() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        Long id = invoiceLine.getId();

        defaultInvoiceLineShouldBeFound("id.equals=" + id);
        defaultInvoiceLineShouldNotBeFound("id.notEquals=" + id);

        defaultInvoiceLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInvoiceLineShouldNotBeFound("id.greaterThan=" + id);

        defaultInvoiceLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInvoiceLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByDisplayOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where displayOrder equals to DEFAULT_DISPLAY_ORDER
        defaultInvoiceLineShouldBeFound("displayOrder.equals=" + DEFAULT_DISPLAY_ORDER);

        // Get all the invoiceLineList where displayOrder equals to UPDATED_DISPLAY_ORDER
        defaultInvoiceLineShouldNotBeFound("displayOrder.equals=" + UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDisplayOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where displayOrder not equals to DEFAULT_DISPLAY_ORDER
        defaultInvoiceLineShouldNotBeFound("displayOrder.notEquals=" + DEFAULT_DISPLAY_ORDER);

        // Get all the invoiceLineList where displayOrder not equals to UPDATED_DISPLAY_ORDER
        defaultInvoiceLineShouldBeFound("displayOrder.notEquals=" + UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDisplayOrderIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where displayOrder in DEFAULT_DISPLAY_ORDER or UPDATED_DISPLAY_ORDER
        defaultInvoiceLineShouldBeFound("displayOrder.in=" + DEFAULT_DISPLAY_ORDER + "," + UPDATED_DISPLAY_ORDER);

        // Get all the invoiceLineList where displayOrder equals to UPDATED_DISPLAY_ORDER
        defaultInvoiceLineShouldNotBeFound("displayOrder.in=" + UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDisplayOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where displayOrder is not null
        defaultInvoiceLineShouldBeFound("displayOrder.specified=true");

        // Get all the invoiceLineList where displayOrder is null
        defaultInvoiceLineShouldNotBeFound("displayOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDisplayOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where displayOrder is greater than or equal to DEFAULT_DISPLAY_ORDER
        defaultInvoiceLineShouldBeFound("displayOrder.greaterThanOrEqual=" + DEFAULT_DISPLAY_ORDER);

        // Get all the invoiceLineList where displayOrder is greater than or equal to UPDATED_DISPLAY_ORDER
        defaultInvoiceLineShouldNotBeFound("displayOrder.greaterThanOrEqual=" + UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDisplayOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where displayOrder is less than or equal to DEFAULT_DISPLAY_ORDER
        defaultInvoiceLineShouldBeFound("displayOrder.lessThanOrEqual=" + DEFAULT_DISPLAY_ORDER);

        // Get all the invoiceLineList where displayOrder is less than or equal to SMALLER_DISPLAY_ORDER
        defaultInvoiceLineShouldNotBeFound("displayOrder.lessThanOrEqual=" + SMALLER_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDisplayOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where displayOrder is less than DEFAULT_DISPLAY_ORDER
        defaultInvoiceLineShouldNotBeFound("displayOrder.lessThan=" + DEFAULT_DISPLAY_ORDER);

        // Get all the invoiceLineList where displayOrder is less than UPDATED_DISPLAY_ORDER
        defaultInvoiceLineShouldBeFound("displayOrder.lessThan=" + UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDisplayOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where displayOrder is greater than DEFAULT_DISPLAY_ORDER
        defaultInvoiceLineShouldNotBeFound("displayOrder.greaterThan=" + DEFAULT_DISPLAY_ORDER);

        // Get all the invoiceLineList where displayOrder is greater than SMALLER_DISPLAY_ORDER
        defaultInvoiceLineShouldBeFound("displayOrder.greaterThan=" + SMALLER_DISPLAY_ORDER);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByItemNameIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where itemName equals to DEFAULT_ITEM_NAME
        defaultInvoiceLineShouldBeFound("itemName.equals=" + DEFAULT_ITEM_NAME);

        // Get all the invoiceLineList where itemName equals to UPDATED_ITEM_NAME
        defaultInvoiceLineShouldNotBeFound("itemName.equals=" + UPDATED_ITEM_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByItemNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where itemName not equals to DEFAULT_ITEM_NAME
        defaultInvoiceLineShouldNotBeFound("itemName.notEquals=" + DEFAULT_ITEM_NAME);

        // Get all the invoiceLineList where itemName not equals to UPDATED_ITEM_NAME
        defaultInvoiceLineShouldBeFound("itemName.notEquals=" + UPDATED_ITEM_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByItemNameIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where itemName in DEFAULT_ITEM_NAME or UPDATED_ITEM_NAME
        defaultInvoiceLineShouldBeFound("itemName.in=" + DEFAULT_ITEM_NAME + "," + UPDATED_ITEM_NAME);

        // Get all the invoiceLineList where itemName equals to UPDATED_ITEM_NAME
        defaultInvoiceLineShouldNotBeFound("itemName.in=" + UPDATED_ITEM_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByItemNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where itemName is not null
        defaultInvoiceLineShouldBeFound("itemName.specified=true");

        // Get all the invoiceLineList where itemName is null
        defaultInvoiceLineShouldNotBeFound("itemName.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoiceLinesByItemNameContainsSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where itemName contains DEFAULT_ITEM_NAME
        defaultInvoiceLineShouldBeFound("itemName.contains=" + DEFAULT_ITEM_NAME);

        // Get all the invoiceLineList where itemName contains UPDATED_ITEM_NAME
        defaultInvoiceLineShouldNotBeFound("itemName.contains=" + UPDATED_ITEM_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByItemNameNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where itemName does not contain DEFAULT_ITEM_NAME
        defaultInvoiceLineShouldNotBeFound("itemName.doesNotContain=" + DEFAULT_ITEM_NAME);

        // Get all the invoiceLineList where itemName does not contain UPDATED_ITEM_NAME
        defaultInvoiceLineShouldBeFound("itemName.doesNotContain=" + UPDATED_ITEM_NAME);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByUnitNameIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where unitName equals to DEFAULT_UNIT_NAME
        defaultInvoiceLineShouldBeFound("unitName.equals=" + DEFAULT_UNIT_NAME);

        // Get all the invoiceLineList where unitName equals to UPDATED_UNIT_NAME
        defaultInvoiceLineShouldNotBeFound("unitName.equals=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByUnitNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where unitName not equals to DEFAULT_UNIT_NAME
        defaultInvoiceLineShouldNotBeFound("unitName.notEquals=" + DEFAULT_UNIT_NAME);

        // Get all the invoiceLineList where unitName not equals to UPDATED_UNIT_NAME
        defaultInvoiceLineShouldBeFound("unitName.notEquals=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByUnitNameIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where unitName in DEFAULT_UNIT_NAME or UPDATED_UNIT_NAME
        defaultInvoiceLineShouldBeFound("unitName.in=" + DEFAULT_UNIT_NAME + "," + UPDATED_UNIT_NAME);

        // Get all the invoiceLineList where unitName equals to UPDATED_UNIT_NAME
        defaultInvoiceLineShouldNotBeFound("unitName.in=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByUnitNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where unitName is not null
        defaultInvoiceLineShouldBeFound("unitName.specified=true");

        // Get all the invoiceLineList where unitName is null
        defaultInvoiceLineShouldNotBeFound("unitName.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoiceLinesByUnitNameContainsSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where unitName contains DEFAULT_UNIT_NAME
        defaultInvoiceLineShouldBeFound("unitName.contains=" + DEFAULT_UNIT_NAME);

        // Get all the invoiceLineList where unitName contains UPDATED_UNIT_NAME
        defaultInvoiceLineShouldNotBeFound("unitName.contains=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByUnitNameNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where unitName does not contain DEFAULT_UNIT_NAME
        defaultInvoiceLineShouldNotBeFound("unitName.doesNotContain=" + DEFAULT_UNIT_NAME);

        // Get all the invoiceLineList where unitName does not contain UPDATED_UNIT_NAME
        defaultInvoiceLineShouldBeFound("unitName.doesNotContain=" + UPDATED_UNIT_NAME);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where quantity equals to DEFAULT_QUANTITY
        defaultInvoiceLineShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the invoiceLineList where quantity equals to UPDATED_QUANTITY
        defaultInvoiceLineShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where quantity not equals to DEFAULT_QUANTITY
        defaultInvoiceLineShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the invoiceLineList where quantity not equals to UPDATED_QUANTITY
        defaultInvoiceLineShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultInvoiceLineShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the invoiceLineList where quantity equals to UPDATED_QUANTITY
        defaultInvoiceLineShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where quantity is not null
        defaultInvoiceLineShouldBeFound("quantity.specified=true");

        // Get all the invoiceLineList where quantity is null
        defaultInvoiceLineShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultInvoiceLineShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the invoiceLineList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultInvoiceLineShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultInvoiceLineShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the invoiceLineList where quantity is less than or equal to SMALLER_QUANTITY
        defaultInvoiceLineShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where quantity is less than DEFAULT_QUANTITY
        defaultInvoiceLineShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the invoiceLineList where quantity is less than UPDATED_QUANTITY
        defaultInvoiceLineShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where quantity is greater than DEFAULT_QUANTITY
        defaultInvoiceLineShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the invoiceLineList where quantity is greater than SMALLER_QUANTITY
        defaultInvoiceLineShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByRateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where rate equals to DEFAULT_RATE
        defaultInvoiceLineShouldBeFound("rate.equals=" + DEFAULT_RATE);

        // Get all the invoiceLineList where rate equals to UPDATED_RATE
        defaultInvoiceLineShouldNotBeFound("rate.equals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where rate not equals to DEFAULT_RATE
        defaultInvoiceLineShouldNotBeFound("rate.notEquals=" + DEFAULT_RATE);

        // Get all the invoiceLineList where rate not equals to UPDATED_RATE
        defaultInvoiceLineShouldBeFound("rate.notEquals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByRateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where rate in DEFAULT_RATE or UPDATED_RATE
        defaultInvoiceLineShouldBeFound("rate.in=" + DEFAULT_RATE + "," + UPDATED_RATE);

        // Get all the invoiceLineList where rate equals to UPDATED_RATE
        defaultInvoiceLineShouldNotBeFound("rate.in=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where rate is not null
        defaultInvoiceLineShouldBeFound("rate.specified=true");

        // Get all the invoiceLineList where rate is null
        defaultInvoiceLineShouldNotBeFound("rate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where rate is greater than or equal to DEFAULT_RATE
        defaultInvoiceLineShouldBeFound("rate.greaterThanOrEqual=" + DEFAULT_RATE);

        // Get all the invoiceLineList where rate is greater than or equal to UPDATED_RATE
        defaultInvoiceLineShouldNotBeFound("rate.greaterThanOrEqual=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where rate is less than or equal to DEFAULT_RATE
        defaultInvoiceLineShouldBeFound("rate.lessThanOrEqual=" + DEFAULT_RATE);

        // Get all the invoiceLineList where rate is less than or equal to SMALLER_RATE
        defaultInvoiceLineShouldNotBeFound("rate.lessThanOrEqual=" + SMALLER_RATE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByRateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where rate is less than DEFAULT_RATE
        defaultInvoiceLineShouldNotBeFound("rate.lessThan=" + DEFAULT_RATE);

        // Get all the invoiceLineList where rate is less than UPDATED_RATE
        defaultInvoiceLineShouldBeFound("rate.lessThan=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where rate is greater than DEFAULT_RATE
        defaultInvoiceLineShouldNotBeFound("rate.greaterThan=" + DEFAULT_RATE);

        // Get all the invoiceLineList where rate is greater than SMALLER_RATE
        defaultInvoiceLineShouldBeFound("rate.greaterThan=" + SMALLER_RATE);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where amount equals to DEFAULT_AMOUNT
        defaultInvoiceLineShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the invoiceLineList where amount equals to UPDATED_AMOUNT
        defaultInvoiceLineShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where amount not equals to DEFAULT_AMOUNT
        defaultInvoiceLineShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the invoiceLineList where amount not equals to UPDATED_AMOUNT
        defaultInvoiceLineShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultInvoiceLineShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the invoiceLineList where amount equals to UPDATED_AMOUNT
        defaultInvoiceLineShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where amount is not null
        defaultInvoiceLineShouldBeFound("amount.specified=true");

        // Get all the invoiceLineList where amount is null
        defaultInvoiceLineShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultInvoiceLineShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the invoiceLineList where amount is greater than or equal to UPDATED_AMOUNT
        defaultInvoiceLineShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where amount is less than or equal to DEFAULT_AMOUNT
        defaultInvoiceLineShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the invoiceLineList where amount is less than or equal to SMALLER_AMOUNT
        defaultInvoiceLineShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where amount is less than DEFAULT_AMOUNT
        defaultInvoiceLineShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the invoiceLineList where amount is less than UPDATED_AMOUNT
        defaultInvoiceLineShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where amount is greater than DEFAULT_AMOUNT
        defaultInvoiceLineShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the invoiceLineList where amount is greater than SMALLER_AMOUNT
        defaultInvoiceLineShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByDiscountPctIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where discountPct equals to DEFAULT_DISCOUNT_PCT
        defaultInvoiceLineShouldBeFound("discountPct.equals=" + DEFAULT_DISCOUNT_PCT);

        // Get all the invoiceLineList where discountPct equals to UPDATED_DISCOUNT_PCT
        defaultInvoiceLineShouldNotBeFound("discountPct.equals=" + UPDATED_DISCOUNT_PCT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDiscountPctIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where discountPct not equals to DEFAULT_DISCOUNT_PCT
        defaultInvoiceLineShouldNotBeFound("discountPct.notEquals=" + DEFAULT_DISCOUNT_PCT);

        // Get all the invoiceLineList where discountPct not equals to UPDATED_DISCOUNT_PCT
        defaultInvoiceLineShouldBeFound("discountPct.notEquals=" + UPDATED_DISCOUNT_PCT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDiscountPctIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where discountPct in DEFAULT_DISCOUNT_PCT or UPDATED_DISCOUNT_PCT
        defaultInvoiceLineShouldBeFound("discountPct.in=" + DEFAULT_DISCOUNT_PCT + "," + UPDATED_DISCOUNT_PCT);

        // Get all the invoiceLineList where discountPct equals to UPDATED_DISCOUNT_PCT
        defaultInvoiceLineShouldNotBeFound("discountPct.in=" + UPDATED_DISCOUNT_PCT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDiscountPctIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where discountPct is not null
        defaultInvoiceLineShouldBeFound("discountPct.specified=true");

        // Get all the invoiceLineList where discountPct is null
        defaultInvoiceLineShouldNotBeFound("discountPct.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDiscountPctIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where discountPct is greater than or equal to DEFAULT_DISCOUNT_PCT
        defaultInvoiceLineShouldBeFound("discountPct.greaterThanOrEqual=" + DEFAULT_DISCOUNT_PCT);

        // Get all the invoiceLineList where discountPct is greater than or equal to UPDATED_DISCOUNT_PCT
        defaultInvoiceLineShouldNotBeFound("discountPct.greaterThanOrEqual=" + UPDATED_DISCOUNT_PCT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDiscountPctIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where discountPct is less than or equal to DEFAULT_DISCOUNT_PCT
        defaultInvoiceLineShouldBeFound("discountPct.lessThanOrEqual=" + DEFAULT_DISCOUNT_PCT);

        // Get all the invoiceLineList where discountPct is less than or equal to SMALLER_DISCOUNT_PCT
        defaultInvoiceLineShouldNotBeFound("discountPct.lessThanOrEqual=" + SMALLER_DISCOUNT_PCT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDiscountPctIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where discountPct is less than DEFAULT_DISCOUNT_PCT
        defaultInvoiceLineShouldNotBeFound("discountPct.lessThan=" + DEFAULT_DISCOUNT_PCT);

        // Get all the invoiceLineList where discountPct is less than UPDATED_DISCOUNT_PCT
        defaultInvoiceLineShouldBeFound("discountPct.lessThan=" + UPDATED_DISCOUNT_PCT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDiscountPctIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where discountPct is greater than DEFAULT_DISCOUNT_PCT
        defaultInvoiceLineShouldNotBeFound("discountPct.greaterThan=" + DEFAULT_DISCOUNT_PCT);

        // Get all the invoiceLineList where discountPct is greater than SMALLER_DISCOUNT_PCT
        defaultInvoiceLineShouldBeFound("discountPct.greaterThan=" + SMALLER_DISCOUNT_PCT);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultInvoiceLineShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the invoiceLineList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultInvoiceLineShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where accountNumber not equals to DEFAULT_ACCOUNT_NUMBER
        defaultInvoiceLineShouldNotBeFound("accountNumber.notEquals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the invoiceLineList where accountNumber not equals to UPDATED_ACCOUNT_NUMBER
        defaultInvoiceLineShouldBeFound("accountNumber.notEquals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultInvoiceLineShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the invoiceLineList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultInvoiceLineShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where accountNumber is not null
        defaultInvoiceLineShouldBeFound("accountNumber.specified=true");

        // Get all the invoiceLineList where accountNumber is null
        defaultInvoiceLineShouldNotBeFound("accountNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoiceLinesByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultInvoiceLineShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the invoiceLineList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultInvoiceLineShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultInvoiceLineShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the invoiceLineList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultInvoiceLineShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where status equals to DEFAULT_STATUS
        defaultInvoiceLineShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the invoiceLineList where status equals to UPDATED_STATUS
        defaultInvoiceLineShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where status not equals to DEFAULT_STATUS
        defaultInvoiceLineShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the invoiceLineList where status not equals to UPDATED_STATUS
        defaultInvoiceLineShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultInvoiceLineShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the invoiceLineList where status equals to UPDATED_STATUS
        defaultInvoiceLineShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList where status is not null
        defaultInvoiceLineShouldBeFound("status.specified=true");

        // Get all the invoiceLineList where status is null
        defaultInvoiceLineShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);
        Invoice invoice = InvoiceResourceIT.createEntity(em);
        em.persist(invoice);
        em.flush();
        invoiceLine.setInvoice(invoice);
        invoiceLineRepository.saveAndFlush(invoiceLine);
        Long invoiceId = invoice.getId();

        // Get all the invoiceLineList where invoice equals to invoiceId
        defaultInvoiceLineShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the invoiceLineList where invoice equals to invoiceId + 1
        defaultInvoiceLineShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByItemIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);
        Item item = ItemResourceIT.createEntity(em);
        em.persist(item);
        em.flush();
        invoiceLine.setItem(item);
        invoiceLineRepository.saveAndFlush(invoiceLine);
        Long itemId = item.getId();

        // Get all the invoiceLineList where item equals to itemId
        defaultInvoiceLineShouldBeFound("itemId.equals=" + itemId);

        // Get all the invoiceLineList where item equals to itemId + 1
        defaultInvoiceLineShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);
        Company company = CompanyResourceIT.createEntity(em);
        em.persist(company);
        em.flush();
        invoiceLine.setCompany(company);
        invoiceLineRepository.saveAndFlush(invoiceLine);
        Long companyId = company.getId();

        // Get all the invoiceLineList where company equals to companyId
        defaultInvoiceLineShouldBeFound("companyId.equals=" + companyId);

        // Get all the invoiceLineList where company equals to companyId + 1
        defaultInvoiceLineShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoiceLineShouldBeFound(String filter) throws Exception {
        restInvoiceLineMockMvc.perform(get("/api/invoice-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].discountPct").value(hasItem(DEFAULT_DISCOUNT_PCT.doubleValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restInvoiceLineMockMvc.perform(get("/api/invoice-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoiceLineShouldNotBeFound(String filter) throws Exception {
        restInvoiceLineMockMvc.perform(get("/api/invoice-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoiceLineMockMvc.perform(get("/api/invoice-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInvoiceLine() throws Exception {
        // Get the invoiceLine
        restInvoiceLineMockMvc.perform(get("/api/invoice-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceLine() throws Exception {
        // Initialize the database
        invoiceLineService.save(invoiceLine);

        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();

        // Update the invoiceLine
        InvoiceLine updatedInvoiceLine = invoiceLineRepository.findById(invoiceLine.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceLine are not directly saved in db
        em.detach(updatedInvoiceLine);
        updatedInvoiceLine
            .displayOrder(UPDATED_DISPLAY_ORDER)
            .itemName(UPDATED_ITEM_NAME)
            .unitName(UPDATED_UNIT_NAME)
            .quantity(UPDATED_QUANTITY)
            .rate(UPDATED_RATE)
            .amount(UPDATED_AMOUNT)
            .discountPct(UPDATED_DISCOUNT_PCT)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .status(UPDATED_STATUS);

        restInvoiceLineMockMvc.perform(put("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoiceLine)))
            .andExpect(status().isOk());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);
        InvoiceLine testInvoiceLine = invoiceLineList.get(invoiceLineList.size() - 1);
        assertThat(testInvoiceLine.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);
        assertThat(testInvoiceLine.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testInvoiceLine.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
        assertThat(testInvoiceLine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testInvoiceLine.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testInvoiceLine.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testInvoiceLine.getDiscountPct()).isEqualTo(UPDATED_DISCOUNT_PCT);
        assertThat(testInvoiceLine.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testInvoiceLine.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceLine() throws Exception {
        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();

        // Create the InvoiceLine

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceLineMockMvc.perform(put("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLine)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoiceLine() throws Exception {
        // Initialize the database
        invoiceLineService.save(invoiceLine);

        int databaseSizeBeforeDelete = invoiceLineRepository.findAll().size();

        // Delete the invoiceLine
        restInvoiceLineMockMvc.perform(delete("/api/invoice-lines/{id}", invoiceLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
