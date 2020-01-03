package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.Invoice;
import com.ak.domain.InvoiceLine;
import com.ak.domain.Customer;
import com.ak.domain.Terms;
import com.ak.domain.Employee;
import com.ak.repository.InvoiceRepository;
import com.ak.service.InvoiceService;
import com.ak.web.rest.errors.ExceptionTranslator;
import com.ak.service.dto.InvoiceCriteria;
import com.ak.service.InvoiceQueryService;

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

import com.ak.domain.enumeration.ProcessStatus;
/**
 * Integration tests for the {@link InvoiceResource} REST controller.
 */
@SpringBootTest(classes = AkApp.class)
public class InvoiceResourceIT {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_INVOICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INVOICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_INVOICE_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_BILLING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PO_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PO_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRODUCT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRODUCT_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VAT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VAT_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_VAT_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DISCOUNT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_DISCOUNT_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL = new BigDecimal(1 - 1);

    private static final ProcessStatus DEFAULT_STATUS = ProcessStatus.OPEN;
    private static final ProcessStatus UPDATED_STATUS = ProcessStatus.CLOSE;

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
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceQueryService invoiceQueryService;

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

    private MockMvc restInvoiceMockMvc;

    private Invoice invoice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceResource invoiceResource = new InvoiceResource(invoiceService, invoiceQueryService);
        this.restInvoiceMockMvc = MockMvcBuilders.standaloneSetup(invoiceResource)
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
    public static Invoice createEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .companyId(DEFAULT_COMPANY_ID)
            .invoiceNo(DEFAULT_INVOICE_NO)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .billingAddress(DEFAULT_BILLING_ADDRESS)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .poNumber(DEFAULT_PO_NUMBER)
            .notes(DEFAULT_NOTES)
            .productTotal(DEFAULT_PRODUCT_TOTAL)
            .vatTotal(DEFAULT_VAT_TOTAL)
            .discountTotal(DEFAULT_DISCOUNT_TOTAL)
            .total(DEFAULT_TOTAL)
            .status(DEFAULT_STATUS)
            .timeCreated(DEFAULT_TIME_CREATED)
            .timeModified(DEFAULT_TIME_MODIFIED)
            .userIdCreated(DEFAULT_USER_ID_CREATED)
            .userIdModified(DEFAULT_USER_ID_MODIFIED);
        return invoice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createUpdatedEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .companyId(UPDATED_COMPANY_ID)
            .invoiceNo(UPDATED_INVOICE_NO)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .poNumber(UPDATED_PO_NUMBER)
            .notes(UPDATED_NOTES)
            .productTotal(UPDATED_PRODUCT_TOTAL)
            .vatTotal(UPDATED_VAT_TOTAL)
            .discountTotal(UPDATED_DISCOUNT_TOTAL)
            .total(UPDATED_TOTAL)
            .status(UPDATED_STATUS)
            .timeCreated(UPDATED_TIME_CREATED)
            .timeModified(UPDATED_TIME_MODIFIED)
            .userIdCreated(UPDATED_USER_ID_CREATED)
            .userIdModified(UPDATED_USER_ID_MODIFIED);
        return invoice;
    }

    @BeforeEach
    public void initTest() {
        invoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoice() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isCreated());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate + 1);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testInvoice.getInvoiceNo()).isEqualTo(DEFAULT_INVOICE_NO);
        assertThat(testInvoice.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInvoice.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testInvoice.getBillingAddress()).isEqualTo(DEFAULT_BILLING_ADDRESS);
        assertThat(testInvoice.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testInvoice.getPoNumber()).isEqualTo(DEFAULT_PO_NUMBER);
        assertThat(testInvoice.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testInvoice.getProductTotal()).isEqualTo(DEFAULT_PRODUCT_TOTAL);
        assertThat(testInvoice.getVatTotal()).isEqualTo(DEFAULT_VAT_TOTAL);
        assertThat(testInvoice.getDiscountTotal()).isEqualTo(DEFAULT_DISCOUNT_TOTAL);
        assertThat(testInvoice.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testInvoice.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInvoice.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testInvoice.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
        assertThat(testInvoice.getUserIdCreated()).isEqualTo(DEFAULT_USER_ID_CREATED);
        assertThat(testInvoice.getUserIdModified()).isEqualTo(DEFAULT_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void createInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice with an existing ID
        invoice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].billingAddress").value(hasItem(DEFAULT_BILLING_ADDRESS)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].poNumber").value(hasItem(DEFAULT_PO_NUMBER)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].productTotal").value(hasItem(DEFAULT_PRODUCT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].vatTotal").value(hasItem(DEFAULT_VAT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].discountTotal").value(hasItem(DEFAULT_DISCOUNT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].timeCreated").value(hasItem(DEFAULT_TIME_CREATED.toString())))
            .andExpect(jsonPath("$.[*].timeModified").value(hasItem(DEFAULT_TIME_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].userIdCreated").value(hasItem(DEFAULT_USER_ID_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userIdModified").value(hasItem(DEFAULT_USER_ID_MODIFIED.intValue())));
    }
    
    @Test
    @Transactional
    public void getInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoice.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.invoiceNo").value(DEFAULT_INVOICE_NO))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.billingAddress").value(DEFAULT_BILLING_ADDRESS))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.poNumber").value(DEFAULT_PO_NUMBER))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.productTotal").value(DEFAULT_PRODUCT_TOTAL.intValue()))
            .andExpect(jsonPath("$.vatTotal").value(DEFAULT_VAT_TOTAL.intValue()))
            .andExpect(jsonPath("$.discountTotal").value(DEFAULT_DISCOUNT_TOTAL.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.timeCreated").value(DEFAULT_TIME_CREATED.toString()))
            .andExpect(jsonPath("$.timeModified").value(DEFAULT_TIME_MODIFIED.toString()))
            .andExpect(jsonPath("$.userIdCreated").value(DEFAULT_USER_ID_CREATED.intValue()))
            .andExpect(jsonPath("$.userIdModified").value(DEFAULT_USER_ID_MODIFIED.intValue()));
    }


    @Test
    @Transactional
    public void getInvoicesByIdFiltering() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        Long id = invoice.getId();

        defaultInvoiceShouldBeFound("id.equals=" + id);
        defaultInvoiceShouldNotBeFound("id.notEquals=" + id);

        defaultInvoiceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInvoiceShouldNotBeFound("id.greaterThan=" + id);

        defaultInvoiceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInvoiceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInvoicesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where companyId equals to DEFAULT_COMPANY_ID
        defaultInvoiceShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the invoiceList where companyId equals to UPDATED_COMPANY_ID
        defaultInvoiceShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCompanyIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where companyId not equals to DEFAULT_COMPANY_ID
        defaultInvoiceShouldNotBeFound("companyId.notEquals=" + DEFAULT_COMPANY_ID);

        // Get all the invoiceList where companyId not equals to UPDATED_COMPANY_ID
        defaultInvoiceShouldBeFound("companyId.notEquals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultInvoiceShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the invoiceList where companyId equals to UPDATED_COMPANY_ID
        defaultInvoiceShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where companyId is not null
        defaultInvoiceShouldBeFound("companyId.specified=true");

        // Get all the invoiceList where companyId is null
        defaultInvoiceShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultInvoiceShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the invoiceList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultInvoiceShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultInvoiceShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the invoiceList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultInvoiceShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where companyId is less than DEFAULT_COMPANY_ID
        defaultInvoiceShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the invoiceList where companyId is less than UPDATED_COMPANY_ID
        defaultInvoiceShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where companyId is greater than DEFAULT_COMPANY_ID
        defaultInvoiceShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the invoiceList where companyId is greater than SMALLER_COMPANY_ID
        defaultInvoiceShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }


    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNo equals to DEFAULT_INVOICE_NO
        defaultInvoiceShouldBeFound("invoiceNo.equals=" + DEFAULT_INVOICE_NO);

        // Get all the invoiceList where invoiceNo equals to UPDATED_INVOICE_NO
        defaultInvoiceShouldNotBeFound("invoiceNo.equals=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNo not equals to DEFAULT_INVOICE_NO
        defaultInvoiceShouldNotBeFound("invoiceNo.notEquals=" + DEFAULT_INVOICE_NO);

        // Get all the invoiceList where invoiceNo not equals to UPDATED_INVOICE_NO
        defaultInvoiceShouldBeFound("invoiceNo.notEquals=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNoIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNo in DEFAULT_INVOICE_NO or UPDATED_INVOICE_NO
        defaultInvoiceShouldBeFound("invoiceNo.in=" + DEFAULT_INVOICE_NO + "," + UPDATED_INVOICE_NO);

        // Get all the invoiceList where invoiceNo equals to UPDATED_INVOICE_NO
        defaultInvoiceShouldNotBeFound("invoiceNo.in=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNo is not null
        defaultInvoiceShouldBeFound("invoiceNo.specified=true");

        // Get all the invoiceList where invoiceNo is null
        defaultInvoiceShouldNotBeFound("invoiceNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByInvoiceNoContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNo contains DEFAULT_INVOICE_NO
        defaultInvoiceShouldBeFound("invoiceNo.contains=" + DEFAULT_INVOICE_NO);

        // Get all the invoiceList where invoiceNo contains UPDATED_INVOICE_NO
        defaultInvoiceShouldNotBeFound("invoiceNo.contains=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNoNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNo does not contain DEFAULT_INVOICE_NO
        defaultInvoiceShouldNotBeFound("invoiceNo.doesNotContain=" + DEFAULT_INVOICE_NO);

        // Get all the invoiceList where invoiceNo does not contain UPDATED_INVOICE_NO
        defaultInvoiceShouldBeFound("invoiceNo.doesNotContain=" + UPDATED_INVOICE_NO);
    }


    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate equals to DEFAULT_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.equals=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.equals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate not equals to DEFAULT_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.notEquals=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate not equals to UPDATED_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.notEquals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate in DEFAULT_INVOICE_DATE or UPDATED_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.in=" + DEFAULT_INVOICE_DATE + "," + UPDATED_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.in=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate is not null
        defaultInvoiceShouldBeFound("invoiceDate.specified=true");

        // Get all the invoiceList where invoiceDate is null
        defaultInvoiceShouldNotBeFound("invoiceDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate is greater than or equal to DEFAULT_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.greaterThanOrEqual=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate is greater than or equal to UPDATED_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.greaterThanOrEqual=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate is less than or equal to DEFAULT_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.lessThanOrEqual=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate is less than or equal to SMALLER_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.lessThanOrEqual=" + SMALLER_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate is less than DEFAULT_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.lessThan=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate is less than UPDATED_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.lessThan=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate is greater than DEFAULT_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.greaterThan=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate is greater than SMALLER_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.greaterThan=" + SMALLER_INVOICE_DATE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate equals to DEFAULT_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate equals to UPDATED_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate not equals to DEFAULT_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.notEquals=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate not equals to UPDATED_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.notEquals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the invoiceList where dueDate equals to UPDATED_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is not null
        defaultInvoiceShouldBeFound("dueDate.specified=true");

        // Get all the invoiceList where dueDate is null
        defaultInvoiceShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is greater than or equal to DEFAULT_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.greaterThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate is greater than or equal to UPDATED_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.greaterThanOrEqual=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is less than or equal to DEFAULT_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.lessThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate is less than or equal to SMALLER_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.lessThanOrEqual=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is less than DEFAULT_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate is less than UPDATED_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is greater than DEFAULT_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.greaterThan=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate is greater than SMALLER_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.greaterThan=" + SMALLER_DUE_DATE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByBillingAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where billingAddress equals to DEFAULT_BILLING_ADDRESS
        defaultInvoiceShouldBeFound("billingAddress.equals=" + DEFAULT_BILLING_ADDRESS);

        // Get all the invoiceList where billingAddress equals to UPDATED_BILLING_ADDRESS
        defaultInvoiceShouldNotBeFound("billingAddress.equals=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByBillingAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where billingAddress not equals to DEFAULT_BILLING_ADDRESS
        defaultInvoiceShouldNotBeFound("billingAddress.notEquals=" + DEFAULT_BILLING_ADDRESS);

        // Get all the invoiceList where billingAddress not equals to UPDATED_BILLING_ADDRESS
        defaultInvoiceShouldBeFound("billingAddress.notEquals=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByBillingAddressIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where billingAddress in DEFAULT_BILLING_ADDRESS or UPDATED_BILLING_ADDRESS
        defaultInvoiceShouldBeFound("billingAddress.in=" + DEFAULT_BILLING_ADDRESS + "," + UPDATED_BILLING_ADDRESS);

        // Get all the invoiceList where billingAddress equals to UPDATED_BILLING_ADDRESS
        defaultInvoiceShouldNotBeFound("billingAddress.in=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByBillingAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where billingAddress is not null
        defaultInvoiceShouldBeFound("billingAddress.specified=true");

        // Get all the invoiceList where billingAddress is null
        defaultInvoiceShouldNotBeFound("billingAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByBillingAddressContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where billingAddress contains DEFAULT_BILLING_ADDRESS
        defaultInvoiceShouldBeFound("billingAddress.contains=" + DEFAULT_BILLING_ADDRESS);

        // Get all the invoiceList where billingAddress contains UPDATED_BILLING_ADDRESS
        defaultInvoiceShouldNotBeFound("billingAddress.contains=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByBillingAddressNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where billingAddress does not contain DEFAULT_BILLING_ADDRESS
        defaultInvoiceShouldNotBeFound("billingAddress.doesNotContain=" + DEFAULT_BILLING_ADDRESS);

        // Get all the invoiceList where billingAddress does not contain UPDATED_BILLING_ADDRESS
        defaultInvoiceShouldBeFound("billingAddress.doesNotContain=" + UPDATED_BILLING_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllInvoicesByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultInvoiceShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the invoiceList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultInvoiceShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where accountNumber not equals to DEFAULT_ACCOUNT_NUMBER
        defaultInvoiceShouldNotBeFound("accountNumber.notEquals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the invoiceList where accountNumber not equals to UPDATED_ACCOUNT_NUMBER
        defaultInvoiceShouldBeFound("accountNumber.notEquals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultInvoiceShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the invoiceList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultInvoiceShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where accountNumber is not null
        defaultInvoiceShouldBeFound("accountNumber.specified=true");

        // Get all the invoiceList where accountNumber is null
        defaultInvoiceShouldNotBeFound("accountNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultInvoiceShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the invoiceList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultInvoiceShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultInvoiceShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the invoiceList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultInvoiceShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllInvoicesByPoNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where poNumber equals to DEFAULT_PO_NUMBER
        defaultInvoiceShouldBeFound("poNumber.equals=" + DEFAULT_PO_NUMBER);

        // Get all the invoiceList where poNumber equals to UPDATED_PO_NUMBER
        defaultInvoiceShouldNotBeFound("poNumber.equals=" + UPDATED_PO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPoNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where poNumber not equals to DEFAULT_PO_NUMBER
        defaultInvoiceShouldNotBeFound("poNumber.notEquals=" + DEFAULT_PO_NUMBER);

        // Get all the invoiceList where poNumber not equals to UPDATED_PO_NUMBER
        defaultInvoiceShouldBeFound("poNumber.notEquals=" + UPDATED_PO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPoNumberIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where poNumber in DEFAULT_PO_NUMBER or UPDATED_PO_NUMBER
        defaultInvoiceShouldBeFound("poNumber.in=" + DEFAULT_PO_NUMBER + "," + UPDATED_PO_NUMBER);

        // Get all the invoiceList where poNumber equals to UPDATED_PO_NUMBER
        defaultInvoiceShouldNotBeFound("poNumber.in=" + UPDATED_PO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPoNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where poNumber is not null
        defaultInvoiceShouldBeFound("poNumber.specified=true");

        // Get all the invoiceList where poNumber is null
        defaultInvoiceShouldNotBeFound("poNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByPoNumberContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where poNumber contains DEFAULT_PO_NUMBER
        defaultInvoiceShouldBeFound("poNumber.contains=" + DEFAULT_PO_NUMBER);

        // Get all the invoiceList where poNumber contains UPDATED_PO_NUMBER
        defaultInvoiceShouldNotBeFound("poNumber.contains=" + UPDATED_PO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPoNumberNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where poNumber does not contain DEFAULT_PO_NUMBER
        defaultInvoiceShouldNotBeFound("poNumber.doesNotContain=" + DEFAULT_PO_NUMBER);

        // Get all the invoiceList where poNumber does not contain UPDATED_PO_NUMBER
        defaultInvoiceShouldBeFound("poNumber.doesNotContain=" + UPDATED_PO_NUMBER);
    }


    @Test
    @Transactional
    public void getAllInvoicesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where notes equals to DEFAULT_NOTES
        defaultInvoiceShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the invoiceList where notes equals to UPDATED_NOTES
        defaultInvoiceShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllInvoicesByNotesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where notes not equals to DEFAULT_NOTES
        defaultInvoiceShouldNotBeFound("notes.notEquals=" + DEFAULT_NOTES);

        // Get all the invoiceList where notes not equals to UPDATED_NOTES
        defaultInvoiceShouldBeFound("notes.notEquals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllInvoicesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultInvoiceShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the invoiceList where notes equals to UPDATED_NOTES
        defaultInvoiceShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllInvoicesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where notes is not null
        defaultInvoiceShouldBeFound("notes.specified=true");

        // Get all the invoiceList where notes is null
        defaultInvoiceShouldNotBeFound("notes.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByNotesContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where notes contains DEFAULT_NOTES
        defaultInvoiceShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the invoiceList where notes contains UPDATED_NOTES
        defaultInvoiceShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllInvoicesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where notes does not contain DEFAULT_NOTES
        defaultInvoiceShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the invoiceList where notes does not contain UPDATED_NOTES
        defaultInvoiceShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }


    @Test
    @Transactional
    public void getAllInvoicesByProductTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where productTotal equals to DEFAULT_PRODUCT_TOTAL
        defaultInvoiceShouldBeFound("productTotal.equals=" + DEFAULT_PRODUCT_TOTAL);

        // Get all the invoiceList where productTotal equals to UPDATED_PRODUCT_TOTAL
        defaultInvoiceShouldNotBeFound("productTotal.equals=" + UPDATED_PRODUCT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByProductTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where productTotal not equals to DEFAULT_PRODUCT_TOTAL
        defaultInvoiceShouldNotBeFound("productTotal.notEquals=" + DEFAULT_PRODUCT_TOTAL);

        // Get all the invoiceList where productTotal not equals to UPDATED_PRODUCT_TOTAL
        defaultInvoiceShouldBeFound("productTotal.notEquals=" + UPDATED_PRODUCT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByProductTotalIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where productTotal in DEFAULT_PRODUCT_TOTAL or UPDATED_PRODUCT_TOTAL
        defaultInvoiceShouldBeFound("productTotal.in=" + DEFAULT_PRODUCT_TOTAL + "," + UPDATED_PRODUCT_TOTAL);

        // Get all the invoiceList where productTotal equals to UPDATED_PRODUCT_TOTAL
        defaultInvoiceShouldNotBeFound("productTotal.in=" + UPDATED_PRODUCT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByProductTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where productTotal is not null
        defaultInvoiceShouldBeFound("productTotal.specified=true");

        // Get all the invoiceList where productTotal is null
        defaultInvoiceShouldNotBeFound("productTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByProductTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where productTotal is greater than or equal to DEFAULT_PRODUCT_TOTAL
        defaultInvoiceShouldBeFound("productTotal.greaterThanOrEqual=" + DEFAULT_PRODUCT_TOTAL);

        // Get all the invoiceList where productTotal is greater than or equal to UPDATED_PRODUCT_TOTAL
        defaultInvoiceShouldNotBeFound("productTotal.greaterThanOrEqual=" + UPDATED_PRODUCT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByProductTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where productTotal is less than or equal to DEFAULT_PRODUCT_TOTAL
        defaultInvoiceShouldBeFound("productTotal.lessThanOrEqual=" + DEFAULT_PRODUCT_TOTAL);

        // Get all the invoiceList where productTotal is less than or equal to SMALLER_PRODUCT_TOTAL
        defaultInvoiceShouldNotBeFound("productTotal.lessThanOrEqual=" + SMALLER_PRODUCT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByProductTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where productTotal is less than DEFAULT_PRODUCT_TOTAL
        defaultInvoiceShouldNotBeFound("productTotal.lessThan=" + DEFAULT_PRODUCT_TOTAL);

        // Get all the invoiceList where productTotal is less than UPDATED_PRODUCT_TOTAL
        defaultInvoiceShouldBeFound("productTotal.lessThan=" + UPDATED_PRODUCT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByProductTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where productTotal is greater than DEFAULT_PRODUCT_TOTAL
        defaultInvoiceShouldNotBeFound("productTotal.greaterThan=" + DEFAULT_PRODUCT_TOTAL);

        // Get all the invoiceList where productTotal is greater than SMALLER_PRODUCT_TOTAL
        defaultInvoiceShouldBeFound("productTotal.greaterThan=" + SMALLER_PRODUCT_TOTAL);
    }


    @Test
    @Transactional
    public void getAllInvoicesByVatTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vatTotal equals to DEFAULT_VAT_TOTAL
        defaultInvoiceShouldBeFound("vatTotal.equals=" + DEFAULT_VAT_TOTAL);

        // Get all the invoiceList where vatTotal equals to UPDATED_VAT_TOTAL
        defaultInvoiceShouldNotBeFound("vatTotal.equals=" + UPDATED_VAT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vatTotal not equals to DEFAULT_VAT_TOTAL
        defaultInvoiceShouldNotBeFound("vatTotal.notEquals=" + DEFAULT_VAT_TOTAL);

        // Get all the invoiceList where vatTotal not equals to UPDATED_VAT_TOTAL
        defaultInvoiceShouldBeFound("vatTotal.notEquals=" + UPDATED_VAT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatTotalIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vatTotal in DEFAULT_VAT_TOTAL or UPDATED_VAT_TOTAL
        defaultInvoiceShouldBeFound("vatTotal.in=" + DEFAULT_VAT_TOTAL + "," + UPDATED_VAT_TOTAL);

        // Get all the invoiceList where vatTotal equals to UPDATED_VAT_TOTAL
        defaultInvoiceShouldNotBeFound("vatTotal.in=" + UPDATED_VAT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vatTotal is not null
        defaultInvoiceShouldBeFound("vatTotal.specified=true");

        // Get all the invoiceList where vatTotal is null
        defaultInvoiceShouldNotBeFound("vatTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vatTotal is greater than or equal to DEFAULT_VAT_TOTAL
        defaultInvoiceShouldBeFound("vatTotal.greaterThanOrEqual=" + DEFAULT_VAT_TOTAL);

        // Get all the invoiceList where vatTotal is greater than or equal to UPDATED_VAT_TOTAL
        defaultInvoiceShouldNotBeFound("vatTotal.greaterThanOrEqual=" + UPDATED_VAT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vatTotal is less than or equal to DEFAULT_VAT_TOTAL
        defaultInvoiceShouldBeFound("vatTotal.lessThanOrEqual=" + DEFAULT_VAT_TOTAL);

        // Get all the invoiceList where vatTotal is less than or equal to SMALLER_VAT_TOTAL
        defaultInvoiceShouldNotBeFound("vatTotal.lessThanOrEqual=" + SMALLER_VAT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vatTotal is less than DEFAULT_VAT_TOTAL
        defaultInvoiceShouldNotBeFound("vatTotal.lessThan=" + DEFAULT_VAT_TOTAL);

        // Get all the invoiceList where vatTotal is less than UPDATED_VAT_TOTAL
        defaultInvoiceShouldBeFound("vatTotal.lessThan=" + UPDATED_VAT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vatTotal is greater than DEFAULT_VAT_TOTAL
        defaultInvoiceShouldNotBeFound("vatTotal.greaterThan=" + DEFAULT_VAT_TOTAL);

        // Get all the invoiceList where vatTotal is greater than SMALLER_VAT_TOTAL
        defaultInvoiceShouldBeFound("vatTotal.greaterThan=" + SMALLER_VAT_TOTAL);
    }


    @Test
    @Transactional
    public void getAllInvoicesByDiscountTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discountTotal equals to DEFAULT_DISCOUNT_TOTAL
        defaultInvoiceShouldBeFound("discountTotal.equals=" + DEFAULT_DISCOUNT_TOTAL);

        // Get all the invoiceList where discountTotal equals to UPDATED_DISCOUNT_TOTAL
        defaultInvoiceShouldNotBeFound("discountTotal.equals=" + UPDATED_DISCOUNT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discountTotal not equals to DEFAULT_DISCOUNT_TOTAL
        defaultInvoiceShouldNotBeFound("discountTotal.notEquals=" + DEFAULT_DISCOUNT_TOTAL);

        // Get all the invoiceList where discountTotal not equals to UPDATED_DISCOUNT_TOTAL
        defaultInvoiceShouldBeFound("discountTotal.notEquals=" + UPDATED_DISCOUNT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountTotalIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discountTotal in DEFAULT_DISCOUNT_TOTAL or UPDATED_DISCOUNT_TOTAL
        defaultInvoiceShouldBeFound("discountTotal.in=" + DEFAULT_DISCOUNT_TOTAL + "," + UPDATED_DISCOUNT_TOTAL);

        // Get all the invoiceList where discountTotal equals to UPDATED_DISCOUNT_TOTAL
        defaultInvoiceShouldNotBeFound("discountTotal.in=" + UPDATED_DISCOUNT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discountTotal is not null
        defaultInvoiceShouldBeFound("discountTotal.specified=true");

        // Get all the invoiceList where discountTotal is null
        defaultInvoiceShouldNotBeFound("discountTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discountTotal is greater than or equal to DEFAULT_DISCOUNT_TOTAL
        defaultInvoiceShouldBeFound("discountTotal.greaterThanOrEqual=" + DEFAULT_DISCOUNT_TOTAL);

        // Get all the invoiceList where discountTotal is greater than or equal to UPDATED_DISCOUNT_TOTAL
        defaultInvoiceShouldNotBeFound("discountTotal.greaterThanOrEqual=" + UPDATED_DISCOUNT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discountTotal is less than or equal to DEFAULT_DISCOUNT_TOTAL
        defaultInvoiceShouldBeFound("discountTotal.lessThanOrEqual=" + DEFAULT_DISCOUNT_TOTAL);

        // Get all the invoiceList where discountTotal is less than or equal to SMALLER_DISCOUNT_TOTAL
        defaultInvoiceShouldNotBeFound("discountTotal.lessThanOrEqual=" + SMALLER_DISCOUNT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discountTotal is less than DEFAULT_DISCOUNT_TOTAL
        defaultInvoiceShouldNotBeFound("discountTotal.lessThan=" + DEFAULT_DISCOUNT_TOTAL);

        // Get all the invoiceList where discountTotal is less than UPDATED_DISCOUNT_TOTAL
        defaultInvoiceShouldBeFound("discountTotal.lessThan=" + UPDATED_DISCOUNT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discountTotal is greater than DEFAULT_DISCOUNT_TOTAL
        defaultInvoiceShouldNotBeFound("discountTotal.greaterThan=" + DEFAULT_DISCOUNT_TOTAL);

        // Get all the invoiceList where discountTotal is greater than SMALLER_DISCOUNT_TOTAL
        defaultInvoiceShouldBeFound("discountTotal.greaterThan=" + SMALLER_DISCOUNT_TOTAL);
    }


    @Test
    @Transactional
    public void getAllInvoicesByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total equals to DEFAULT_TOTAL
        defaultInvoiceShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the invoiceList where total equals to UPDATED_TOTAL
        defaultInvoiceShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total not equals to DEFAULT_TOTAL
        defaultInvoiceShouldNotBeFound("total.notEquals=" + DEFAULT_TOTAL);

        // Get all the invoiceList where total not equals to UPDATED_TOTAL
        defaultInvoiceShouldBeFound("total.notEquals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultInvoiceShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the invoiceList where total equals to UPDATED_TOTAL
        defaultInvoiceShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total is not null
        defaultInvoiceShouldBeFound("total.specified=true");

        // Get all the invoiceList where total is null
        defaultInvoiceShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total is greater than or equal to DEFAULT_TOTAL
        defaultInvoiceShouldBeFound("total.greaterThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the invoiceList where total is greater than or equal to UPDATED_TOTAL
        defaultInvoiceShouldNotBeFound("total.greaterThanOrEqual=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total is less than or equal to DEFAULT_TOTAL
        defaultInvoiceShouldBeFound("total.lessThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the invoiceList where total is less than or equal to SMALLER_TOTAL
        defaultInvoiceShouldNotBeFound("total.lessThanOrEqual=" + SMALLER_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total is less than DEFAULT_TOTAL
        defaultInvoiceShouldNotBeFound("total.lessThan=" + DEFAULT_TOTAL);

        // Get all the invoiceList where total is less than UPDATED_TOTAL
        defaultInvoiceShouldBeFound("total.lessThan=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total is greater than DEFAULT_TOTAL
        defaultInvoiceShouldNotBeFound("total.greaterThan=" + DEFAULT_TOTAL);

        // Get all the invoiceList where total is greater than SMALLER_TOTAL
        defaultInvoiceShouldBeFound("total.greaterThan=" + SMALLER_TOTAL);
    }


    @Test
    @Transactional
    public void getAllInvoicesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where status equals to DEFAULT_STATUS
        defaultInvoiceShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the invoiceList where status equals to UPDATED_STATUS
        defaultInvoiceShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where status not equals to DEFAULT_STATUS
        defaultInvoiceShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the invoiceList where status not equals to UPDATED_STATUS
        defaultInvoiceShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultInvoiceShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the invoiceList where status equals to UPDATED_STATUS
        defaultInvoiceShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where status is not null
        defaultInvoiceShouldBeFound("status.specified=true");

        // Get all the invoiceList where status is null
        defaultInvoiceShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByTimeCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where timeCreated equals to DEFAULT_TIME_CREATED
        defaultInvoiceShouldBeFound("timeCreated.equals=" + DEFAULT_TIME_CREATED);

        // Get all the invoiceList where timeCreated equals to UPDATED_TIME_CREATED
        defaultInvoiceShouldNotBeFound("timeCreated.equals=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTimeCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where timeCreated not equals to DEFAULT_TIME_CREATED
        defaultInvoiceShouldNotBeFound("timeCreated.notEquals=" + DEFAULT_TIME_CREATED);

        // Get all the invoiceList where timeCreated not equals to UPDATED_TIME_CREATED
        defaultInvoiceShouldBeFound("timeCreated.notEquals=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTimeCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where timeCreated in DEFAULT_TIME_CREATED or UPDATED_TIME_CREATED
        defaultInvoiceShouldBeFound("timeCreated.in=" + DEFAULT_TIME_CREATED + "," + UPDATED_TIME_CREATED);

        // Get all the invoiceList where timeCreated equals to UPDATED_TIME_CREATED
        defaultInvoiceShouldNotBeFound("timeCreated.in=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTimeCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where timeCreated is not null
        defaultInvoiceShouldBeFound("timeCreated.specified=true");

        // Get all the invoiceList where timeCreated is null
        defaultInvoiceShouldNotBeFound("timeCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByTimeModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where timeModified equals to DEFAULT_TIME_MODIFIED
        defaultInvoiceShouldBeFound("timeModified.equals=" + DEFAULT_TIME_MODIFIED);

        // Get all the invoiceList where timeModified equals to UPDATED_TIME_MODIFIED
        defaultInvoiceShouldNotBeFound("timeModified.equals=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTimeModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where timeModified not equals to DEFAULT_TIME_MODIFIED
        defaultInvoiceShouldNotBeFound("timeModified.notEquals=" + DEFAULT_TIME_MODIFIED);

        // Get all the invoiceList where timeModified not equals to UPDATED_TIME_MODIFIED
        defaultInvoiceShouldBeFound("timeModified.notEquals=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTimeModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where timeModified in DEFAULT_TIME_MODIFIED or UPDATED_TIME_MODIFIED
        defaultInvoiceShouldBeFound("timeModified.in=" + DEFAULT_TIME_MODIFIED + "," + UPDATED_TIME_MODIFIED);

        // Get all the invoiceList where timeModified equals to UPDATED_TIME_MODIFIED
        defaultInvoiceShouldNotBeFound("timeModified.in=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTimeModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where timeModified is not null
        defaultInvoiceShouldBeFound("timeModified.specified=true");

        // Get all the invoiceList where timeModified is null
        defaultInvoiceShouldNotBeFound("timeModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdCreated equals to DEFAULT_USER_ID_CREATED
        defaultInvoiceShouldBeFound("userIdCreated.equals=" + DEFAULT_USER_ID_CREATED);

        // Get all the invoiceList where userIdCreated equals to UPDATED_USER_ID_CREATED
        defaultInvoiceShouldNotBeFound("userIdCreated.equals=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdCreated not equals to DEFAULT_USER_ID_CREATED
        defaultInvoiceShouldNotBeFound("userIdCreated.notEquals=" + DEFAULT_USER_ID_CREATED);

        // Get all the invoiceList where userIdCreated not equals to UPDATED_USER_ID_CREATED
        defaultInvoiceShouldBeFound("userIdCreated.notEquals=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdCreated in DEFAULT_USER_ID_CREATED or UPDATED_USER_ID_CREATED
        defaultInvoiceShouldBeFound("userIdCreated.in=" + DEFAULT_USER_ID_CREATED + "," + UPDATED_USER_ID_CREATED);

        // Get all the invoiceList where userIdCreated equals to UPDATED_USER_ID_CREATED
        defaultInvoiceShouldNotBeFound("userIdCreated.in=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdCreated is not null
        defaultInvoiceShouldBeFound("userIdCreated.specified=true");

        // Get all the invoiceList where userIdCreated is null
        defaultInvoiceShouldNotBeFound("userIdCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdCreatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdCreated is greater than or equal to DEFAULT_USER_ID_CREATED
        defaultInvoiceShouldBeFound("userIdCreated.greaterThanOrEqual=" + DEFAULT_USER_ID_CREATED);

        // Get all the invoiceList where userIdCreated is greater than or equal to UPDATED_USER_ID_CREATED
        defaultInvoiceShouldNotBeFound("userIdCreated.greaterThanOrEqual=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdCreatedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdCreated is less than or equal to DEFAULT_USER_ID_CREATED
        defaultInvoiceShouldBeFound("userIdCreated.lessThanOrEqual=" + DEFAULT_USER_ID_CREATED);

        // Get all the invoiceList where userIdCreated is less than or equal to SMALLER_USER_ID_CREATED
        defaultInvoiceShouldNotBeFound("userIdCreated.lessThanOrEqual=" + SMALLER_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdCreatedIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdCreated is less than DEFAULT_USER_ID_CREATED
        defaultInvoiceShouldNotBeFound("userIdCreated.lessThan=" + DEFAULT_USER_ID_CREATED);

        // Get all the invoiceList where userIdCreated is less than UPDATED_USER_ID_CREATED
        defaultInvoiceShouldBeFound("userIdCreated.lessThan=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdCreatedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdCreated is greater than DEFAULT_USER_ID_CREATED
        defaultInvoiceShouldNotBeFound("userIdCreated.greaterThan=" + DEFAULT_USER_ID_CREATED);

        // Get all the invoiceList where userIdCreated is greater than SMALLER_USER_ID_CREATED
        defaultInvoiceShouldBeFound("userIdCreated.greaterThan=" + SMALLER_USER_ID_CREATED);
    }


    @Test
    @Transactional
    public void getAllInvoicesByUserIdModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdModified equals to DEFAULT_USER_ID_MODIFIED
        defaultInvoiceShouldBeFound("userIdModified.equals=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the invoiceList where userIdModified equals to UPDATED_USER_ID_MODIFIED
        defaultInvoiceShouldNotBeFound("userIdModified.equals=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdModified not equals to DEFAULT_USER_ID_MODIFIED
        defaultInvoiceShouldNotBeFound("userIdModified.notEquals=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the invoiceList where userIdModified not equals to UPDATED_USER_ID_MODIFIED
        defaultInvoiceShouldBeFound("userIdModified.notEquals=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdModified in DEFAULT_USER_ID_MODIFIED or UPDATED_USER_ID_MODIFIED
        defaultInvoiceShouldBeFound("userIdModified.in=" + DEFAULT_USER_ID_MODIFIED + "," + UPDATED_USER_ID_MODIFIED);

        // Get all the invoiceList where userIdModified equals to UPDATED_USER_ID_MODIFIED
        defaultInvoiceShouldNotBeFound("userIdModified.in=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdModified is not null
        defaultInvoiceShouldBeFound("userIdModified.specified=true");

        // Get all the invoiceList where userIdModified is null
        defaultInvoiceShouldNotBeFound("userIdModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdModifiedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdModified is greater than or equal to DEFAULT_USER_ID_MODIFIED
        defaultInvoiceShouldBeFound("userIdModified.greaterThanOrEqual=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the invoiceList where userIdModified is greater than or equal to UPDATED_USER_ID_MODIFIED
        defaultInvoiceShouldNotBeFound("userIdModified.greaterThanOrEqual=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdModifiedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdModified is less than or equal to DEFAULT_USER_ID_MODIFIED
        defaultInvoiceShouldBeFound("userIdModified.lessThanOrEqual=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the invoiceList where userIdModified is less than or equal to SMALLER_USER_ID_MODIFIED
        defaultInvoiceShouldNotBeFound("userIdModified.lessThanOrEqual=" + SMALLER_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdModifiedIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdModified is less than DEFAULT_USER_ID_MODIFIED
        defaultInvoiceShouldNotBeFound("userIdModified.lessThan=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the invoiceList where userIdModified is less than UPDATED_USER_ID_MODIFIED
        defaultInvoiceShouldBeFound("userIdModified.lessThan=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUserIdModifiedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where userIdModified is greater than DEFAULT_USER_ID_MODIFIED
        defaultInvoiceShouldNotBeFound("userIdModified.greaterThan=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the invoiceList where userIdModified is greater than SMALLER_USER_ID_MODIFIED
        defaultInvoiceShouldBeFound("userIdModified.greaterThan=" + SMALLER_USER_ID_MODIFIED);
    }


    @Test
    @Transactional
    public void getAllInvoicesByInvoiceLineIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        InvoiceLine invoiceLine = InvoiceLineResourceIT.createEntity(em);
        em.persist(invoiceLine);
        em.flush();
        invoice.addInvoiceLine(invoiceLine);
        invoiceRepository.saveAndFlush(invoice);
        Long invoiceLineId = invoiceLine.getId();

        // Get all the invoiceList where invoiceLine equals to invoiceLineId
        defaultInvoiceShouldBeFound("invoiceLineId.equals=" + invoiceLineId);

        // Get all the invoiceList where invoiceLine equals to invoiceLineId + 1
        defaultInvoiceShouldNotBeFound("invoiceLineId.equals=" + (invoiceLineId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        invoice.setCustomer(customer);
        invoiceRepository.saveAndFlush(invoice);
        Long customerId = customer.getId();

        // Get all the invoiceList where customer equals to customerId
        defaultInvoiceShouldBeFound("customerId.equals=" + customerId);

        // Get all the invoiceList where customer equals to customerId + 1
        defaultInvoiceShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByTermsIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        Terms terms = TermsResourceIT.createEntity(em);
        em.persist(terms);
        em.flush();
        invoice.setTerms(terms);
        invoiceRepository.saveAndFlush(invoice);
        Long termsId = terms.getId();

        // Get all the invoiceList where terms equals to termsId
        defaultInvoiceShouldBeFound("termsId.equals=" + termsId);

        // Get all the invoiceList where terms equals to termsId + 1
        defaultInvoiceShouldNotBeFound("termsId.equals=" + (termsId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        invoice.setEmployee(employee);
        invoiceRepository.saveAndFlush(invoice);
        Long employeeId = employee.getId();

        // Get all the invoiceList where employee equals to employeeId
        defaultInvoiceShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the invoiceList where employee equals to employeeId + 1
        defaultInvoiceShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoiceShouldBeFound(String filter) throws Exception {
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].billingAddress").value(hasItem(DEFAULT_BILLING_ADDRESS)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].poNumber").value(hasItem(DEFAULT_PO_NUMBER)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].productTotal").value(hasItem(DEFAULT_PRODUCT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].vatTotal").value(hasItem(DEFAULT_VAT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].discountTotal").value(hasItem(DEFAULT_DISCOUNT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].timeCreated").value(hasItem(DEFAULT_TIME_CREATED.toString())))
            .andExpect(jsonPath("$.[*].timeModified").value(hasItem(DEFAULT_TIME_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].userIdCreated").value(hasItem(DEFAULT_USER_ID_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userIdModified").value(hasItem(DEFAULT_USER_ID_MODIFIED.intValue())));

        // Check, that the count call also returns 1
        restInvoiceMockMvc.perform(get("/api/invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoiceShouldNotBeFound(String filter) throws Exception {
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoiceMockMvc.perform(get("/api/invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInvoice() throws Exception {
        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);

        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Update the invoice
        Invoice updatedInvoice = invoiceRepository.findById(invoice.getId()).get();
        // Disconnect from session so that the updates on updatedInvoice are not directly saved in db
        em.detach(updatedInvoice);
        updatedInvoice
            .companyId(UPDATED_COMPANY_ID)
            .invoiceNo(UPDATED_INVOICE_NO)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .poNumber(UPDATED_PO_NUMBER)
            .notes(UPDATED_NOTES)
            .productTotal(UPDATED_PRODUCT_TOTAL)
            .vatTotal(UPDATED_VAT_TOTAL)
            .discountTotal(UPDATED_DISCOUNT_TOTAL)
            .total(UPDATED_TOTAL)
            .status(UPDATED_STATUS)
            .timeCreated(UPDATED_TIME_CREATED)
            .timeModified(UPDATED_TIME_MODIFIED)
            .userIdCreated(UPDATED_USER_ID_CREATED)
            .userIdModified(UPDATED_USER_ID_MODIFIED);

        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoice)))
            .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testInvoice.getInvoiceNo()).isEqualTo(UPDATED_INVOICE_NO);
        assertThat(testInvoice.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInvoice.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testInvoice.getBillingAddress()).isEqualTo(UPDATED_BILLING_ADDRESS);
        assertThat(testInvoice.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testInvoice.getPoNumber()).isEqualTo(UPDATED_PO_NUMBER);
        assertThat(testInvoice.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testInvoice.getProductTotal()).isEqualTo(UPDATED_PRODUCT_TOTAL);
        assertThat(testInvoice.getVatTotal()).isEqualTo(UPDATED_VAT_TOTAL);
        assertThat(testInvoice.getDiscountTotal()).isEqualTo(UPDATED_DISCOUNT_TOTAL);
        assertThat(testInvoice.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testInvoice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInvoice.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testInvoice.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
        assertThat(testInvoice.getUserIdCreated()).isEqualTo(UPDATED_USER_ID_CREATED);
        assertThat(testInvoice.getUserIdModified()).isEqualTo(UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Create the Invoice

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);

        int databaseSizeBeforeDelete = invoiceRepository.findAll().size();

        // Delete the invoice
        restInvoiceMockMvc.perform(delete("/api/invoices/{id}", invoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
