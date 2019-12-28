package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.Item;
import com.ak.domain.InvoiceLine;
import com.ak.domain.Unit;
import com.ak.domain.ItemGroup;
import com.ak.domain.Store;
import com.ak.domain.Company;
import com.ak.repository.ItemRepository;
import com.ak.service.ItemService;
import com.ak.web.rest.errors.ExceptionTranslator;
import com.ak.service.dto.ItemCriteria;
import com.ak.service.ItemQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.ak.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ak.domain.enumeration.ItemType;
import com.ak.domain.enumeration.VATTax;
import com.ak.domain.enumeration.PriceMethod;
/**
 * Integration tests for the {@link ItemResource} REST controller.
 */
@SpringBootTest(classes = AkApp.class)
public class ItemResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ItemType DEFAULT_TYPE = ItemType.PRODUCT;
    private static final ItemType UPDATED_TYPE = ItemType.SERVICE;

    private static final Double DEFAULT_SKU_NUM = 1D;
    private static final Double UPDATED_SKU_NUM = 2D;
    private static final Double SMALLER_SKU_NUM = 1D - 1D;

    private static final VATTax DEFAULT_VAT_TAX = VATTax.VAT5;
    private static final VATTax UPDATED_VAT_TAX = VATTax.VAT10;

    private static final Double DEFAULT_IMPORT_TAX = 1D;
    private static final Double UPDATED_IMPORT_TAX = 2D;
    private static final Double SMALLER_IMPORT_TAX = 1D - 1D;

    private static final Double DEFAULT_EXPORT_TAX = 1D;
    private static final Double UPDATED_EXPORT_TAX = 2D;
    private static final Double SMALLER_EXPORT_TAX = 1D - 1D;

    private static final PriceMethod DEFAULT_INVENTORY_PRICE_METHOD = PriceMethod.AVERAGE;
    private static final PriceMethod UPDATED_INVENTORY_PRICE_METHOD = PriceMethod.FIFO;

    private static final String DEFAULT_ACCOUNT_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ITEM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ALLOW_MODIFIED = false;
    private static final Boolean UPDATED_IS_ALLOW_MODIFIED = true;

    private static final String DEFAULT_ACCOUNT_COST = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_COST = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_REVENUE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_REVENUE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_INTERNAL_REVENUE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_INTERNAL_REVENUE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_SALE_RETURN = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_SALE_RETURN = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_SALE_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_SALE_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_AGENCY = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_AGENCY = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_RAW_PRODUCT = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_RAW_PRODUCT = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_COST_DIFFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_COST_DIFFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_DISCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_DISCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_SALE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_SALE_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_DESC = "BBBBBBBBBB";

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;
    private static final Double SMALLER_WEIGHT = 1D - 1D;

    private static final Double DEFAULT_LENGHT = 1D;
    private static final Double UPDATED_LENGHT = 2D;
    private static final Double SMALLER_LENGHT = 1D - 1D;

    private static final Double DEFAULT_WIDE = 1D;
    private static final Double UPDATED_WIDE = 2D;
    private static final Double SMALLER_WIDE = 1D - 1D;

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;
    private static final Double SMALLER_HEIGHT = 1D - 1D;

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_SPECIFICATION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ITEM_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ITEM_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ITEM_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ITEM_IMAGE_CONTENT_TYPE = "image/png";

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
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemQueryService itemQueryService;

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

    private MockMvc restItemMockMvc;

    private Item item;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemResource itemResource = new ItemResource(itemService, itemQueryService);
        this.restItemMockMvc = MockMvcBuilders.standaloneSetup(itemResource)
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
    public static Item createEntity(EntityManager em) {
        Item item = new Item()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .skuNum(DEFAULT_SKU_NUM)
            .vatTax(DEFAULT_VAT_TAX)
            .importTax(DEFAULT_IMPORT_TAX)
            .exportTax(DEFAULT_EXPORT_TAX)
            .inventoryPriceMethod(DEFAULT_INVENTORY_PRICE_METHOD)
            .accountItem(DEFAULT_ACCOUNT_ITEM)
            .isAllowModified(DEFAULT_IS_ALLOW_MODIFIED)
            .accountCost(DEFAULT_ACCOUNT_COST)
            .accountRevenue(DEFAULT_ACCOUNT_REVENUE)
            .accountInternalRevenue(DEFAULT_ACCOUNT_INTERNAL_REVENUE)
            .accountSaleReturn(DEFAULT_ACCOUNT_SALE_RETURN)
            .accountSalePrice(DEFAULT_ACCOUNT_SALE_PRICE)
            .accountAgency(DEFAULT_ACCOUNT_AGENCY)
            .accountRawProduct(DEFAULT_ACCOUNT_RAW_PRODUCT)
            .accountCostDifference(DEFAULT_ACCOUNT_COST_DIFFERENCE)
            .accountDiscount(DEFAULT_ACCOUNT_DISCOUNT)
            .saleDesc(DEFAULT_SALE_DESC)
            .purchaseDesc(DEFAULT_PURCHASE_DESC)
            .weight(DEFAULT_WEIGHT)
            .lenght(DEFAULT_LENGHT)
            .wide(DEFAULT_WIDE)
            .height(DEFAULT_HEIGHT)
            .color(DEFAULT_COLOR)
            .specification(DEFAULT_SPECIFICATION)
            .itemImage(DEFAULT_ITEM_IMAGE)
            .itemImageContentType(DEFAULT_ITEM_IMAGE_CONTENT_TYPE)
            .isActive(DEFAULT_IS_ACTIVE)
            .timeCreated(DEFAULT_TIME_CREATED)
            .timeModified(DEFAULT_TIME_MODIFIED)
            .userIdCreated(DEFAULT_USER_ID_CREATED)
            .userIdModified(DEFAULT_USER_ID_MODIFIED);
        return item;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Item createUpdatedEntity(EntityManager em) {
        Item item = new Item()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .skuNum(UPDATED_SKU_NUM)
            .vatTax(UPDATED_VAT_TAX)
            .importTax(UPDATED_IMPORT_TAX)
            .exportTax(UPDATED_EXPORT_TAX)
            .inventoryPriceMethod(UPDATED_INVENTORY_PRICE_METHOD)
            .accountItem(UPDATED_ACCOUNT_ITEM)
            .isAllowModified(UPDATED_IS_ALLOW_MODIFIED)
            .accountCost(UPDATED_ACCOUNT_COST)
            .accountRevenue(UPDATED_ACCOUNT_REVENUE)
            .accountInternalRevenue(UPDATED_ACCOUNT_INTERNAL_REVENUE)
            .accountSaleReturn(UPDATED_ACCOUNT_SALE_RETURN)
            .accountSalePrice(UPDATED_ACCOUNT_SALE_PRICE)
            .accountAgency(UPDATED_ACCOUNT_AGENCY)
            .accountRawProduct(UPDATED_ACCOUNT_RAW_PRODUCT)
            .accountCostDifference(UPDATED_ACCOUNT_COST_DIFFERENCE)
            .accountDiscount(UPDATED_ACCOUNT_DISCOUNT)
            .saleDesc(UPDATED_SALE_DESC)
            .purchaseDesc(UPDATED_PURCHASE_DESC)
            .weight(UPDATED_WEIGHT)
            .lenght(UPDATED_LENGHT)
            .wide(UPDATED_WIDE)
            .height(UPDATED_HEIGHT)
            .color(UPDATED_COLOR)
            .specification(UPDATED_SPECIFICATION)
            .itemImage(UPDATED_ITEM_IMAGE)
            .itemImageContentType(UPDATED_ITEM_IMAGE_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .timeCreated(UPDATED_TIME_CREATED)
            .timeModified(UPDATED_TIME_MODIFIED)
            .userIdCreated(UPDATED_USER_ID_CREATED)
            .userIdModified(UPDATED_USER_ID_MODIFIED);
        return item;
    }

    @BeforeEach
    public void initTest() {
        item = createEntity(em);
    }

    @Test
    @Transactional
    public void createItem() throws Exception {
        int databaseSizeBeforeCreate = itemRepository.findAll().size();

        // Create the Item
        restItemMockMvc.perform(post("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isCreated());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeCreate + 1);
        Item testItem = itemList.get(itemList.size() - 1);
        assertThat(testItem.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testItem.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testItem.getSkuNum()).isEqualTo(DEFAULT_SKU_NUM);
        assertThat(testItem.getVatTax()).isEqualTo(DEFAULT_VAT_TAX);
        assertThat(testItem.getImportTax()).isEqualTo(DEFAULT_IMPORT_TAX);
        assertThat(testItem.getExportTax()).isEqualTo(DEFAULT_EXPORT_TAX);
        assertThat(testItem.getInventoryPriceMethod()).isEqualTo(DEFAULT_INVENTORY_PRICE_METHOD);
        assertThat(testItem.getAccountItem()).isEqualTo(DEFAULT_ACCOUNT_ITEM);
        assertThat(testItem.isIsAllowModified()).isEqualTo(DEFAULT_IS_ALLOW_MODIFIED);
        assertThat(testItem.getAccountCost()).isEqualTo(DEFAULT_ACCOUNT_COST);
        assertThat(testItem.getAccountRevenue()).isEqualTo(DEFAULT_ACCOUNT_REVENUE);
        assertThat(testItem.getAccountInternalRevenue()).isEqualTo(DEFAULT_ACCOUNT_INTERNAL_REVENUE);
        assertThat(testItem.getAccountSaleReturn()).isEqualTo(DEFAULT_ACCOUNT_SALE_RETURN);
        assertThat(testItem.getAccountSalePrice()).isEqualTo(DEFAULT_ACCOUNT_SALE_PRICE);
        assertThat(testItem.getAccountAgency()).isEqualTo(DEFAULT_ACCOUNT_AGENCY);
        assertThat(testItem.getAccountRawProduct()).isEqualTo(DEFAULT_ACCOUNT_RAW_PRODUCT);
        assertThat(testItem.getAccountCostDifference()).isEqualTo(DEFAULT_ACCOUNT_COST_DIFFERENCE);
        assertThat(testItem.getAccountDiscount()).isEqualTo(DEFAULT_ACCOUNT_DISCOUNT);
        assertThat(testItem.getSaleDesc()).isEqualTo(DEFAULT_SALE_DESC);
        assertThat(testItem.getPurchaseDesc()).isEqualTo(DEFAULT_PURCHASE_DESC);
        assertThat(testItem.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testItem.getLenght()).isEqualTo(DEFAULT_LENGHT);
        assertThat(testItem.getWide()).isEqualTo(DEFAULT_WIDE);
        assertThat(testItem.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testItem.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testItem.getSpecification()).isEqualTo(DEFAULT_SPECIFICATION);
        assertThat(testItem.getItemImage()).isEqualTo(DEFAULT_ITEM_IMAGE);
        assertThat(testItem.getItemImageContentType()).isEqualTo(DEFAULT_ITEM_IMAGE_CONTENT_TYPE);
        assertThat(testItem.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testItem.getTimeCreated()).isEqualTo(DEFAULT_TIME_CREATED);
        assertThat(testItem.getTimeModified()).isEqualTo(DEFAULT_TIME_MODIFIED);
        assertThat(testItem.getUserIdCreated()).isEqualTo(DEFAULT_USER_ID_CREATED);
        assertThat(testItem.getUserIdModified()).isEqualTo(DEFAULT_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void createItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemRepository.findAll().size();

        // Create the Item with an existing ID
        item.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemMockMvc.perform(post("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isBadRequest());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setName(null);

        // Create the Item, which fails.

        restItemMockMvc.perform(post("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isBadRequest());

        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setType(null);

        // Create the Item, which fails.

        restItemMockMvc.perform(post("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isBadRequest());

        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItems() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList
        restItemMockMvc.perform(get("/api/items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(item.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].skuNum").value(hasItem(DEFAULT_SKU_NUM.doubleValue())))
            .andExpect(jsonPath("$.[*].vatTax").value(hasItem(DEFAULT_VAT_TAX.toString())))
            .andExpect(jsonPath("$.[*].importTax").value(hasItem(DEFAULT_IMPORT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].exportTax").value(hasItem(DEFAULT_EXPORT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].inventoryPriceMethod").value(hasItem(DEFAULT_INVENTORY_PRICE_METHOD.toString())))
            .andExpect(jsonPath("$.[*].accountItem").value(hasItem(DEFAULT_ACCOUNT_ITEM)))
            .andExpect(jsonPath("$.[*].isAllowModified").value(hasItem(DEFAULT_IS_ALLOW_MODIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].accountCost").value(hasItem(DEFAULT_ACCOUNT_COST)))
            .andExpect(jsonPath("$.[*].accountRevenue").value(hasItem(DEFAULT_ACCOUNT_REVENUE)))
            .andExpect(jsonPath("$.[*].accountInternalRevenue").value(hasItem(DEFAULT_ACCOUNT_INTERNAL_REVENUE)))
            .andExpect(jsonPath("$.[*].accountSaleReturn").value(hasItem(DEFAULT_ACCOUNT_SALE_RETURN)))
            .andExpect(jsonPath("$.[*].accountSalePrice").value(hasItem(DEFAULT_ACCOUNT_SALE_PRICE)))
            .andExpect(jsonPath("$.[*].accountAgency").value(hasItem(DEFAULT_ACCOUNT_AGENCY)))
            .andExpect(jsonPath("$.[*].accountRawProduct").value(hasItem(DEFAULT_ACCOUNT_RAW_PRODUCT)))
            .andExpect(jsonPath("$.[*].accountCostDifference").value(hasItem(DEFAULT_ACCOUNT_COST_DIFFERENCE)))
            .andExpect(jsonPath("$.[*].accountDiscount").value(hasItem(DEFAULT_ACCOUNT_DISCOUNT)))
            .andExpect(jsonPath("$.[*].saleDesc").value(hasItem(DEFAULT_SALE_DESC)))
            .andExpect(jsonPath("$.[*].purchaseDesc").value(hasItem(DEFAULT_PURCHASE_DESC)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].lenght").value(hasItem(DEFAULT_LENGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].wide").value(hasItem(DEFAULT_WIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].specification").value(hasItem(DEFAULT_SPECIFICATION)))
            .andExpect(jsonPath("$.[*].itemImageContentType").value(hasItem(DEFAULT_ITEM_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].itemImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_ITEM_IMAGE))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].timeCreated").value(hasItem(DEFAULT_TIME_CREATED.toString())))
            .andExpect(jsonPath("$.[*].timeModified").value(hasItem(DEFAULT_TIME_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].userIdCreated").value(hasItem(DEFAULT_USER_ID_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userIdModified").value(hasItem(DEFAULT_USER_ID_MODIFIED.intValue())));
    }
    
    @Test
    @Transactional
    public void getItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get the item
        restItemMockMvc.perform(get("/api/items/{id}", item.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(item.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.skuNum").value(DEFAULT_SKU_NUM.doubleValue()))
            .andExpect(jsonPath("$.vatTax").value(DEFAULT_VAT_TAX.toString()))
            .andExpect(jsonPath("$.importTax").value(DEFAULT_IMPORT_TAX.doubleValue()))
            .andExpect(jsonPath("$.exportTax").value(DEFAULT_EXPORT_TAX.doubleValue()))
            .andExpect(jsonPath("$.inventoryPriceMethod").value(DEFAULT_INVENTORY_PRICE_METHOD.toString()))
            .andExpect(jsonPath("$.accountItem").value(DEFAULT_ACCOUNT_ITEM))
            .andExpect(jsonPath("$.isAllowModified").value(DEFAULT_IS_ALLOW_MODIFIED.booleanValue()))
            .andExpect(jsonPath("$.accountCost").value(DEFAULT_ACCOUNT_COST))
            .andExpect(jsonPath("$.accountRevenue").value(DEFAULT_ACCOUNT_REVENUE))
            .andExpect(jsonPath("$.accountInternalRevenue").value(DEFAULT_ACCOUNT_INTERNAL_REVENUE))
            .andExpect(jsonPath("$.accountSaleReturn").value(DEFAULT_ACCOUNT_SALE_RETURN))
            .andExpect(jsonPath("$.accountSalePrice").value(DEFAULT_ACCOUNT_SALE_PRICE))
            .andExpect(jsonPath("$.accountAgency").value(DEFAULT_ACCOUNT_AGENCY))
            .andExpect(jsonPath("$.accountRawProduct").value(DEFAULT_ACCOUNT_RAW_PRODUCT))
            .andExpect(jsonPath("$.accountCostDifference").value(DEFAULT_ACCOUNT_COST_DIFFERENCE))
            .andExpect(jsonPath("$.accountDiscount").value(DEFAULT_ACCOUNT_DISCOUNT))
            .andExpect(jsonPath("$.saleDesc").value(DEFAULT_SALE_DESC))
            .andExpect(jsonPath("$.purchaseDesc").value(DEFAULT_PURCHASE_DESC))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.lenght").value(DEFAULT_LENGHT.doubleValue()))
            .andExpect(jsonPath("$.wide").value(DEFAULT_WIDE.doubleValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.specification").value(DEFAULT_SPECIFICATION))
            .andExpect(jsonPath("$.itemImageContentType").value(DEFAULT_ITEM_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.itemImage").value(Base64Utils.encodeToString(DEFAULT_ITEM_IMAGE)))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.timeCreated").value(DEFAULT_TIME_CREATED.toString()))
            .andExpect(jsonPath("$.timeModified").value(DEFAULT_TIME_MODIFIED.toString()))
            .andExpect(jsonPath("$.userIdCreated").value(DEFAULT_USER_ID_CREATED.intValue()))
            .andExpect(jsonPath("$.userIdModified").value(DEFAULT_USER_ID_MODIFIED.intValue()));
    }


    @Test
    @Transactional
    public void getItemsByIdFiltering() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        Long id = item.getId();

        defaultItemShouldBeFound("id.equals=" + id);
        defaultItemShouldNotBeFound("id.notEquals=" + id);

        defaultItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItemShouldNotBeFound("id.greaterThan=" + id);

        defaultItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllItemsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where code equals to DEFAULT_CODE
        defaultItemShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the itemList where code equals to UPDATED_CODE
        defaultItemShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllItemsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where code not equals to DEFAULT_CODE
        defaultItemShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the itemList where code not equals to UPDATED_CODE
        defaultItemShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllItemsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where code in DEFAULT_CODE or UPDATED_CODE
        defaultItemShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the itemList where code equals to UPDATED_CODE
        defaultItemShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllItemsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where code is not null
        defaultItemShouldBeFound("code.specified=true");

        // Get all the itemList where code is null
        defaultItemShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByCodeContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where code contains DEFAULT_CODE
        defaultItemShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the itemList where code contains UPDATED_CODE
        defaultItemShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllItemsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where code does not contain DEFAULT_CODE
        defaultItemShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the itemList where code does not contain UPDATED_CODE
        defaultItemShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllItemsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where name equals to DEFAULT_NAME
        defaultItemShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the itemList where name equals to UPDATED_NAME
        defaultItemShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where name not equals to DEFAULT_NAME
        defaultItemShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the itemList where name not equals to UPDATED_NAME
        defaultItemShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where name in DEFAULT_NAME or UPDATED_NAME
        defaultItemShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the itemList where name equals to UPDATED_NAME
        defaultItemShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where name is not null
        defaultItemShouldBeFound("name.specified=true");

        // Get all the itemList where name is null
        defaultItemShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByNameContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where name contains DEFAULT_NAME
        defaultItemShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the itemList where name contains UPDATED_NAME
        defaultItemShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where name does not contain DEFAULT_NAME
        defaultItemShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the itemList where name does not contain UPDATED_NAME
        defaultItemShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllItemsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where description equals to DEFAULT_DESCRIPTION
        defaultItemShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the itemList where description equals to UPDATED_DESCRIPTION
        defaultItemShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllItemsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where description not equals to DEFAULT_DESCRIPTION
        defaultItemShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the itemList where description not equals to UPDATED_DESCRIPTION
        defaultItemShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllItemsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultItemShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the itemList where description equals to UPDATED_DESCRIPTION
        defaultItemShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllItemsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where description is not null
        defaultItemShouldBeFound("description.specified=true");

        // Get all the itemList where description is null
        defaultItemShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where description contains DEFAULT_DESCRIPTION
        defaultItemShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the itemList where description contains UPDATED_DESCRIPTION
        defaultItemShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllItemsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where description does not contain DEFAULT_DESCRIPTION
        defaultItemShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the itemList where description does not contain UPDATED_DESCRIPTION
        defaultItemShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllItemsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where type equals to DEFAULT_TYPE
        defaultItemShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the itemList where type equals to UPDATED_TYPE
        defaultItemShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllItemsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where type not equals to DEFAULT_TYPE
        defaultItemShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the itemList where type not equals to UPDATED_TYPE
        defaultItemShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllItemsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultItemShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the itemList where type equals to UPDATED_TYPE
        defaultItemShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllItemsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where type is not null
        defaultItemShouldBeFound("type.specified=true");

        // Get all the itemList where type is null
        defaultItemShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsBySkuNumIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where skuNum equals to DEFAULT_SKU_NUM
        defaultItemShouldBeFound("skuNum.equals=" + DEFAULT_SKU_NUM);

        // Get all the itemList where skuNum equals to UPDATED_SKU_NUM
        defaultItemShouldNotBeFound("skuNum.equals=" + UPDATED_SKU_NUM);
    }

    @Test
    @Transactional
    public void getAllItemsBySkuNumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where skuNum not equals to DEFAULT_SKU_NUM
        defaultItemShouldNotBeFound("skuNum.notEquals=" + DEFAULT_SKU_NUM);

        // Get all the itemList where skuNum not equals to UPDATED_SKU_NUM
        defaultItemShouldBeFound("skuNum.notEquals=" + UPDATED_SKU_NUM);
    }

    @Test
    @Transactional
    public void getAllItemsBySkuNumIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where skuNum in DEFAULT_SKU_NUM or UPDATED_SKU_NUM
        defaultItemShouldBeFound("skuNum.in=" + DEFAULT_SKU_NUM + "," + UPDATED_SKU_NUM);

        // Get all the itemList where skuNum equals to UPDATED_SKU_NUM
        defaultItemShouldNotBeFound("skuNum.in=" + UPDATED_SKU_NUM);
    }

    @Test
    @Transactional
    public void getAllItemsBySkuNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where skuNum is not null
        defaultItemShouldBeFound("skuNum.specified=true");

        // Get all the itemList where skuNum is null
        defaultItemShouldNotBeFound("skuNum.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsBySkuNumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where skuNum is greater than or equal to DEFAULT_SKU_NUM
        defaultItemShouldBeFound("skuNum.greaterThanOrEqual=" + DEFAULT_SKU_NUM);

        // Get all the itemList where skuNum is greater than or equal to UPDATED_SKU_NUM
        defaultItemShouldNotBeFound("skuNum.greaterThanOrEqual=" + UPDATED_SKU_NUM);
    }

    @Test
    @Transactional
    public void getAllItemsBySkuNumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where skuNum is less than or equal to DEFAULT_SKU_NUM
        defaultItemShouldBeFound("skuNum.lessThanOrEqual=" + DEFAULT_SKU_NUM);

        // Get all the itemList where skuNum is less than or equal to SMALLER_SKU_NUM
        defaultItemShouldNotBeFound("skuNum.lessThanOrEqual=" + SMALLER_SKU_NUM);
    }

    @Test
    @Transactional
    public void getAllItemsBySkuNumIsLessThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where skuNum is less than DEFAULT_SKU_NUM
        defaultItemShouldNotBeFound("skuNum.lessThan=" + DEFAULT_SKU_NUM);

        // Get all the itemList where skuNum is less than UPDATED_SKU_NUM
        defaultItemShouldBeFound("skuNum.lessThan=" + UPDATED_SKU_NUM);
    }

    @Test
    @Transactional
    public void getAllItemsBySkuNumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where skuNum is greater than DEFAULT_SKU_NUM
        defaultItemShouldNotBeFound("skuNum.greaterThan=" + DEFAULT_SKU_NUM);

        // Get all the itemList where skuNum is greater than SMALLER_SKU_NUM
        defaultItemShouldBeFound("skuNum.greaterThan=" + SMALLER_SKU_NUM);
    }


    @Test
    @Transactional
    public void getAllItemsByVatTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where vatTax equals to DEFAULT_VAT_TAX
        defaultItemShouldBeFound("vatTax.equals=" + DEFAULT_VAT_TAX);

        // Get all the itemList where vatTax equals to UPDATED_VAT_TAX
        defaultItemShouldNotBeFound("vatTax.equals=" + UPDATED_VAT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByVatTaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where vatTax not equals to DEFAULT_VAT_TAX
        defaultItemShouldNotBeFound("vatTax.notEquals=" + DEFAULT_VAT_TAX);

        // Get all the itemList where vatTax not equals to UPDATED_VAT_TAX
        defaultItemShouldBeFound("vatTax.notEquals=" + UPDATED_VAT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByVatTaxIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where vatTax in DEFAULT_VAT_TAX or UPDATED_VAT_TAX
        defaultItemShouldBeFound("vatTax.in=" + DEFAULT_VAT_TAX + "," + UPDATED_VAT_TAX);

        // Get all the itemList where vatTax equals to UPDATED_VAT_TAX
        defaultItemShouldNotBeFound("vatTax.in=" + UPDATED_VAT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByVatTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where vatTax is not null
        defaultItemShouldBeFound("vatTax.specified=true");

        // Get all the itemList where vatTax is null
        defaultItemShouldNotBeFound("vatTax.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByImportTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where importTax equals to DEFAULT_IMPORT_TAX
        defaultItemShouldBeFound("importTax.equals=" + DEFAULT_IMPORT_TAX);

        // Get all the itemList where importTax equals to UPDATED_IMPORT_TAX
        defaultItemShouldNotBeFound("importTax.equals=" + UPDATED_IMPORT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByImportTaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where importTax not equals to DEFAULT_IMPORT_TAX
        defaultItemShouldNotBeFound("importTax.notEquals=" + DEFAULT_IMPORT_TAX);

        // Get all the itemList where importTax not equals to UPDATED_IMPORT_TAX
        defaultItemShouldBeFound("importTax.notEquals=" + UPDATED_IMPORT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByImportTaxIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where importTax in DEFAULT_IMPORT_TAX or UPDATED_IMPORT_TAX
        defaultItemShouldBeFound("importTax.in=" + DEFAULT_IMPORT_TAX + "," + UPDATED_IMPORT_TAX);

        // Get all the itemList where importTax equals to UPDATED_IMPORT_TAX
        defaultItemShouldNotBeFound("importTax.in=" + UPDATED_IMPORT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByImportTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where importTax is not null
        defaultItemShouldBeFound("importTax.specified=true");

        // Get all the itemList where importTax is null
        defaultItemShouldNotBeFound("importTax.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByImportTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where importTax is greater than or equal to DEFAULT_IMPORT_TAX
        defaultItemShouldBeFound("importTax.greaterThanOrEqual=" + DEFAULT_IMPORT_TAX);

        // Get all the itemList where importTax is greater than or equal to UPDATED_IMPORT_TAX
        defaultItemShouldNotBeFound("importTax.greaterThanOrEqual=" + UPDATED_IMPORT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByImportTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where importTax is less than or equal to DEFAULT_IMPORT_TAX
        defaultItemShouldBeFound("importTax.lessThanOrEqual=" + DEFAULT_IMPORT_TAX);

        // Get all the itemList where importTax is less than or equal to SMALLER_IMPORT_TAX
        defaultItemShouldNotBeFound("importTax.lessThanOrEqual=" + SMALLER_IMPORT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByImportTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where importTax is less than DEFAULT_IMPORT_TAX
        defaultItemShouldNotBeFound("importTax.lessThan=" + DEFAULT_IMPORT_TAX);

        // Get all the itemList where importTax is less than UPDATED_IMPORT_TAX
        defaultItemShouldBeFound("importTax.lessThan=" + UPDATED_IMPORT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByImportTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where importTax is greater than DEFAULT_IMPORT_TAX
        defaultItemShouldNotBeFound("importTax.greaterThan=" + DEFAULT_IMPORT_TAX);

        // Get all the itemList where importTax is greater than SMALLER_IMPORT_TAX
        defaultItemShouldBeFound("importTax.greaterThan=" + SMALLER_IMPORT_TAX);
    }


    @Test
    @Transactional
    public void getAllItemsByExportTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where exportTax equals to DEFAULT_EXPORT_TAX
        defaultItemShouldBeFound("exportTax.equals=" + DEFAULT_EXPORT_TAX);

        // Get all the itemList where exportTax equals to UPDATED_EXPORT_TAX
        defaultItemShouldNotBeFound("exportTax.equals=" + UPDATED_EXPORT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByExportTaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where exportTax not equals to DEFAULT_EXPORT_TAX
        defaultItemShouldNotBeFound("exportTax.notEquals=" + DEFAULT_EXPORT_TAX);

        // Get all the itemList where exportTax not equals to UPDATED_EXPORT_TAX
        defaultItemShouldBeFound("exportTax.notEquals=" + UPDATED_EXPORT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByExportTaxIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where exportTax in DEFAULT_EXPORT_TAX or UPDATED_EXPORT_TAX
        defaultItemShouldBeFound("exportTax.in=" + DEFAULT_EXPORT_TAX + "," + UPDATED_EXPORT_TAX);

        // Get all the itemList where exportTax equals to UPDATED_EXPORT_TAX
        defaultItemShouldNotBeFound("exportTax.in=" + UPDATED_EXPORT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByExportTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where exportTax is not null
        defaultItemShouldBeFound("exportTax.specified=true");

        // Get all the itemList where exportTax is null
        defaultItemShouldNotBeFound("exportTax.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByExportTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where exportTax is greater than or equal to DEFAULT_EXPORT_TAX
        defaultItemShouldBeFound("exportTax.greaterThanOrEqual=" + DEFAULT_EXPORT_TAX);

        // Get all the itemList where exportTax is greater than or equal to UPDATED_EXPORT_TAX
        defaultItemShouldNotBeFound("exportTax.greaterThanOrEqual=" + UPDATED_EXPORT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByExportTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where exportTax is less than or equal to DEFAULT_EXPORT_TAX
        defaultItemShouldBeFound("exportTax.lessThanOrEqual=" + DEFAULT_EXPORT_TAX);

        // Get all the itemList where exportTax is less than or equal to SMALLER_EXPORT_TAX
        defaultItemShouldNotBeFound("exportTax.lessThanOrEqual=" + SMALLER_EXPORT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByExportTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where exportTax is less than DEFAULT_EXPORT_TAX
        defaultItemShouldNotBeFound("exportTax.lessThan=" + DEFAULT_EXPORT_TAX);

        // Get all the itemList where exportTax is less than UPDATED_EXPORT_TAX
        defaultItemShouldBeFound("exportTax.lessThan=" + UPDATED_EXPORT_TAX);
    }

    @Test
    @Transactional
    public void getAllItemsByExportTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where exportTax is greater than DEFAULT_EXPORT_TAX
        defaultItemShouldNotBeFound("exportTax.greaterThan=" + DEFAULT_EXPORT_TAX);

        // Get all the itemList where exportTax is greater than SMALLER_EXPORT_TAX
        defaultItemShouldBeFound("exportTax.greaterThan=" + SMALLER_EXPORT_TAX);
    }


    @Test
    @Transactional
    public void getAllItemsByInventoryPriceMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where inventoryPriceMethod equals to DEFAULT_INVENTORY_PRICE_METHOD
        defaultItemShouldBeFound("inventoryPriceMethod.equals=" + DEFAULT_INVENTORY_PRICE_METHOD);

        // Get all the itemList where inventoryPriceMethod equals to UPDATED_INVENTORY_PRICE_METHOD
        defaultItemShouldNotBeFound("inventoryPriceMethod.equals=" + UPDATED_INVENTORY_PRICE_METHOD);
    }

    @Test
    @Transactional
    public void getAllItemsByInventoryPriceMethodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where inventoryPriceMethod not equals to DEFAULT_INVENTORY_PRICE_METHOD
        defaultItemShouldNotBeFound("inventoryPriceMethod.notEquals=" + DEFAULT_INVENTORY_PRICE_METHOD);

        // Get all the itemList where inventoryPriceMethod not equals to UPDATED_INVENTORY_PRICE_METHOD
        defaultItemShouldBeFound("inventoryPriceMethod.notEquals=" + UPDATED_INVENTORY_PRICE_METHOD);
    }

    @Test
    @Transactional
    public void getAllItemsByInventoryPriceMethodIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where inventoryPriceMethod in DEFAULT_INVENTORY_PRICE_METHOD or UPDATED_INVENTORY_PRICE_METHOD
        defaultItemShouldBeFound("inventoryPriceMethod.in=" + DEFAULT_INVENTORY_PRICE_METHOD + "," + UPDATED_INVENTORY_PRICE_METHOD);

        // Get all the itemList where inventoryPriceMethod equals to UPDATED_INVENTORY_PRICE_METHOD
        defaultItemShouldNotBeFound("inventoryPriceMethod.in=" + UPDATED_INVENTORY_PRICE_METHOD);
    }

    @Test
    @Transactional
    public void getAllItemsByInventoryPriceMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where inventoryPriceMethod is not null
        defaultItemShouldBeFound("inventoryPriceMethod.specified=true");

        // Get all the itemList where inventoryPriceMethod is null
        defaultItemShouldNotBeFound("inventoryPriceMethod.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByAccountItemIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountItem equals to DEFAULT_ACCOUNT_ITEM
        defaultItemShouldBeFound("accountItem.equals=" + DEFAULT_ACCOUNT_ITEM);

        // Get all the itemList where accountItem equals to UPDATED_ACCOUNT_ITEM
        defaultItemShouldNotBeFound("accountItem.equals=" + UPDATED_ACCOUNT_ITEM);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountItemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountItem not equals to DEFAULT_ACCOUNT_ITEM
        defaultItemShouldNotBeFound("accountItem.notEquals=" + DEFAULT_ACCOUNT_ITEM);

        // Get all the itemList where accountItem not equals to UPDATED_ACCOUNT_ITEM
        defaultItemShouldBeFound("accountItem.notEquals=" + UPDATED_ACCOUNT_ITEM);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountItemIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountItem in DEFAULT_ACCOUNT_ITEM or UPDATED_ACCOUNT_ITEM
        defaultItemShouldBeFound("accountItem.in=" + DEFAULT_ACCOUNT_ITEM + "," + UPDATED_ACCOUNT_ITEM);

        // Get all the itemList where accountItem equals to UPDATED_ACCOUNT_ITEM
        defaultItemShouldNotBeFound("accountItem.in=" + UPDATED_ACCOUNT_ITEM);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountItemIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountItem is not null
        defaultItemShouldBeFound("accountItem.specified=true");

        // Get all the itemList where accountItem is null
        defaultItemShouldNotBeFound("accountItem.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByAccountItemContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountItem contains DEFAULT_ACCOUNT_ITEM
        defaultItemShouldBeFound("accountItem.contains=" + DEFAULT_ACCOUNT_ITEM);

        // Get all the itemList where accountItem contains UPDATED_ACCOUNT_ITEM
        defaultItemShouldNotBeFound("accountItem.contains=" + UPDATED_ACCOUNT_ITEM);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountItemNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountItem does not contain DEFAULT_ACCOUNT_ITEM
        defaultItemShouldNotBeFound("accountItem.doesNotContain=" + DEFAULT_ACCOUNT_ITEM);

        // Get all the itemList where accountItem does not contain UPDATED_ACCOUNT_ITEM
        defaultItemShouldBeFound("accountItem.doesNotContain=" + UPDATED_ACCOUNT_ITEM);
    }


    @Test
    @Transactional
    public void getAllItemsByIsAllowModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where isAllowModified equals to DEFAULT_IS_ALLOW_MODIFIED
        defaultItemShouldBeFound("isAllowModified.equals=" + DEFAULT_IS_ALLOW_MODIFIED);

        // Get all the itemList where isAllowModified equals to UPDATED_IS_ALLOW_MODIFIED
        defaultItemShouldNotBeFound("isAllowModified.equals=" + UPDATED_IS_ALLOW_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllItemsByIsAllowModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where isAllowModified not equals to DEFAULT_IS_ALLOW_MODIFIED
        defaultItemShouldNotBeFound("isAllowModified.notEquals=" + DEFAULT_IS_ALLOW_MODIFIED);

        // Get all the itemList where isAllowModified not equals to UPDATED_IS_ALLOW_MODIFIED
        defaultItemShouldBeFound("isAllowModified.notEquals=" + UPDATED_IS_ALLOW_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllItemsByIsAllowModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where isAllowModified in DEFAULT_IS_ALLOW_MODIFIED or UPDATED_IS_ALLOW_MODIFIED
        defaultItemShouldBeFound("isAllowModified.in=" + DEFAULT_IS_ALLOW_MODIFIED + "," + UPDATED_IS_ALLOW_MODIFIED);

        // Get all the itemList where isAllowModified equals to UPDATED_IS_ALLOW_MODIFIED
        defaultItemShouldNotBeFound("isAllowModified.in=" + UPDATED_IS_ALLOW_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllItemsByIsAllowModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where isAllowModified is not null
        defaultItemShouldBeFound("isAllowModified.specified=true");

        // Get all the itemList where isAllowModified is null
        defaultItemShouldNotBeFound("isAllowModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByAccountCostIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountCost equals to DEFAULT_ACCOUNT_COST
        defaultItemShouldBeFound("accountCost.equals=" + DEFAULT_ACCOUNT_COST);

        // Get all the itemList where accountCost equals to UPDATED_ACCOUNT_COST
        defaultItemShouldNotBeFound("accountCost.equals=" + UPDATED_ACCOUNT_COST);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountCostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountCost not equals to DEFAULT_ACCOUNT_COST
        defaultItemShouldNotBeFound("accountCost.notEquals=" + DEFAULT_ACCOUNT_COST);

        // Get all the itemList where accountCost not equals to UPDATED_ACCOUNT_COST
        defaultItemShouldBeFound("accountCost.notEquals=" + UPDATED_ACCOUNT_COST);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountCostIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountCost in DEFAULT_ACCOUNT_COST or UPDATED_ACCOUNT_COST
        defaultItemShouldBeFound("accountCost.in=" + DEFAULT_ACCOUNT_COST + "," + UPDATED_ACCOUNT_COST);

        // Get all the itemList where accountCost equals to UPDATED_ACCOUNT_COST
        defaultItemShouldNotBeFound("accountCost.in=" + UPDATED_ACCOUNT_COST);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountCost is not null
        defaultItemShouldBeFound("accountCost.specified=true");

        // Get all the itemList where accountCost is null
        defaultItemShouldNotBeFound("accountCost.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByAccountCostContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountCost contains DEFAULT_ACCOUNT_COST
        defaultItemShouldBeFound("accountCost.contains=" + DEFAULT_ACCOUNT_COST);

        // Get all the itemList where accountCost contains UPDATED_ACCOUNT_COST
        defaultItemShouldNotBeFound("accountCost.contains=" + UPDATED_ACCOUNT_COST);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountCostNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountCost does not contain DEFAULT_ACCOUNT_COST
        defaultItemShouldNotBeFound("accountCost.doesNotContain=" + DEFAULT_ACCOUNT_COST);

        // Get all the itemList where accountCost does not contain UPDATED_ACCOUNT_COST
        defaultItemShouldBeFound("accountCost.doesNotContain=" + UPDATED_ACCOUNT_COST);
    }


    @Test
    @Transactional
    public void getAllItemsByAccountRevenueIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountRevenue equals to DEFAULT_ACCOUNT_REVENUE
        defaultItemShouldBeFound("accountRevenue.equals=" + DEFAULT_ACCOUNT_REVENUE);

        // Get all the itemList where accountRevenue equals to UPDATED_ACCOUNT_REVENUE
        defaultItemShouldNotBeFound("accountRevenue.equals=" + UPDATED_ACCOUNT_REVENUE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountRevenueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountRevenue not equals to DEFAULT_ACCOUNT_REVENUE
        defaultItemShouldNotBeFound("accountRevenue.notEquals=" + DEFAULT_ACCOUNT_REVENUE);

        // Get all the itemList where accountRevenue not equals to UPDATED_ACCOUNT_REVENUE
        defaultItemShouldBeFound("accountRevenue.notEquals=" + UPDATED_ACCOUNT_REVENUE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountRevenueIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountRevenue in DEFAULT_ACCOUNT_REVENUE or UPDATED_ACCOUNT_REVENUE
        defaultItemShouldBeFound("accountRevenue.in=" + DEFAULT_ACCOUNT_REVENUE + "," + UPDATED_ACCOUNT_REVENUE);

        // Get all the itemList where accountRevenue equals to UPDATED_ACCOUNT_REVENUE
        defaultItemShouldNotBeFound("accountRevenue.in=" + UPDATED_ACCOUNT_REVENUE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountRevenueIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountRevenue is not null
        defaultItemShouldBeFound("accountRevenue.specified=true");

        // Get all the itemList where accountRevenue is null
        defaultItemShouldNotBeFound("accountRevenue.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByAccountRevenueContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountRevenue contains DEFAULT_ACCOUNT_REVENUE
        defaultItemShouldBeFound("accountRevenue.contains=" + DEFAULT_ACCOUNT_REVENUE);

        // Get all the itemList where accountRevenue contains UPDATED_ACCOUNT_REVENUE
        defaultItemShouldNotBeFound("accountRevenue.contains=" + UPDATED_ACCOUNT_REVENUE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountRevenueNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountRevenue does not contain DEFAULT_ACCOUNT_REVENUE
        defaultItemShouldNotBeFound("accountRevenue.doesNotContain=" + DEFAULT_ACCOUNT_REVENUE);

        // Get all the itemList where accountRevenue does not contain UPDATED_ACCOUNT_REVENUE
        defaultItemShouldBeFound("accountRevenue.doesNotContain=" + UPDATED_ACCOUNT_REVENUE);
    }


    @Test
    @Transactional
    public void getAllItemsByAccountInternalRevenueIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountInternalRevenue equals to DEFAULT_ACCOUNT_INTERNAL_REVENUE
        defaultItemShouldBeFound("accountInternalRevenue.equals=" + DEFAULT_ACCOUNT_INTERNAL_REVENUE);

        // Get all the itemList where accountInternalRevenue equals to UPDATED_ACCOUNT_INTERNAL_REVENUE
        defaultItemShouldNotBeFound("accountInternalRevenue.equals=" + UPDATED_ACCOUNT_INTERNAL_REVENUE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountInternalRevenueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountInternalRevenue not equals to DEFAULT_ACCOUNT_INTERNAL_REVENUE
        defaultItemShouldNotBeFound("accountInternalRevenue.notEquals=" + DEFAULT_ACCOUNT_INTERNAL_REVENUE);

        // Get all the itemList where accountInternalRevenue not equals to UPDATED_ACCOUNT_INTERNAL_REVENUE
        defaultItemShouldBeFound("accountInternalRevenue.notEquals=" + UPDATED_ACCOUNT_INTERNAL_REVENUE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountInternalRevenueIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountInternalRevenue in DEFAULT_ACCOUNT_INTERNAL_REVENUE or UPDATED_ACCOUNT_INTERNAL_REVENUE
        defaultItemShouldBeFound("accountInternalRevenue.in=" + DEFAULT_ACCOUNT_INTERNAL_REVENUE + "," + UPDATED_ACCOUNT_INTERNAL_REVENUE);

        // Get all the itemList where accountInternalRevenue equals to UPDATED_ACCOUNT_INTERNAL_REVENUE
        defaultItemShouldNotBeFound("accountInternalRevenue.in=" + UPDATED_ACCOUNT_INTERNAL_REVENUE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountInternalRevenueIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountInternalRevenue is not null
        defaultItemShouldBeFound("accountInternalRevenue.specified=true");

        // Get all the itemList where accountInternalRevenue is null
        defaultItemShouldNotBeFound("accountInternalRevenue.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByAccountInternalRevenueContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountInternalRevenue contains DEFAULT_ACCOUNT_INTERNAL_REVENUE
        defaultItemShouldBeFound("accountInternalRevenue.contains=" + DEFAULT_ACCOUNT_INTERNAL_REVENUE);

        // Get all the itemList where accountInternalRevenue contains UPDATED_ACCOUNT_INTERNAL_REVENUE
        defaultItemShouldNotBeFound("accountInternalRevenue.contains=" + UPDATED_ACCOUNT_INTERNAL_REVENUE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountInternalRevenueNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountInternalRevenue does not contain DEFAULT_ACCOUNT_INTERNAL_REVENUE
        defaultItemShouldNotBeFound("accountInternalRevenue.doesNotContain=" + DEFAULT_ACCOUNT_INTERNAL_REVENUE);

        // Get all the itemList where accountInternalRevenue does not contain UPDATED_ACCOUNT_INTERNAL_REVENUE
        defaultItemShouldBeFound("accountInternalRevenue.doesNotContain=" + UPDATED_ACCOUNT_INTERNAL_REVENUE);
    }


    @Test
    @Transactional
    public void getAllItemsByAccountSaleReturnIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountSaleReturn equals to DEFAULT_ACCOUNT_SALE_RETURN
        defaultItemShouldBeFound("accountSaleReturn.equals=" + DEFAULT_ACCOUNT_SALE_RETURN);

        // Get all the itemList where accountSaleReturn equals to UPDATED_ACCOUNT_SALE_RETURN
        defaultItemShouldNotBeFound("accountSaleReturn.equals=" + UPDATED_ACCOUNT_SALE_RETURN);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountSaleReturnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountSaleReturn not equals to DEFAULT_ACCOUNT_SALE_RETURN
        defaultItemShouldNotBeFound("accountSaleReturn.notEquals=" + DEFAULT_ACCOUNT_SALE_RETURN);

        // Get all the itemList where accountSaleReturn not equals to UPDATED_ACCOUNT_SALE_RETURN
        defaultItemShouldBeFound("accountSaleReturn.notEquals=" + UPDATED_ACCOUNT_SALE_RETURN);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountSaleReturnIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountSaleReturn in DEFAULT_ACCOUNT_SALE_RETURN or UPDATED_ACCOUNT_SALE_RETURN
        defaultItemShouldBeFound("accountSaleReturn.in=" + DEFAULT_ACCOUNT_SALE_RETURN + "," + UPDATED_ACCOUNT_SALE_RETURN);

        // Get all the itemList where accountSaleReturn equals to UPDATED_ACCOUNT_SALE_RETURN
        defaultItemShouldNotBeFound("accountSaleReturn.in=" + UPDATED_ACCOUNT_SALE_RETURN);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountSaleReturnIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountSaleReturn is not null
        defaultItemShouldBeFound("accountSaleReturn.specified=true");

        // Get all the itemList where accountSaleReturn is null
        defaultItemShouldNotBeFound("accountSaleReturn.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByAccountSaleReturnContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountSaleReturn contains DEFAULT_ACCOUNT_SALE_RETURN
        defaultItemShouldBeFound("accountSaleReturn.contains=" + DEFAULT_ACCOUNT_SALE_RETURN);

        // Get all the itemList where accountSaleReturn contains UPDATED_ACCOUNT_SALE_RETURN
        defaultItemShouldNotBeFound("accountSaleReturn.contains=" + UPDATED_ACCOUNT_SALE_RETURN);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountSaleReturnNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountSaleReturn does not contain DEFAULT_ACCOUNT_SALE_RETURN
        defaultItemShouldNotBeFound("accountSaleReturn.doesNotContain=" + DEFAULT_ACCOUNT_SALE_RETURN);

        // Get all the itemList where accountSaleReturn does not contain UPDATED_ACCOUNT_SALE_RETURN
        defaultItemShouldBeFound("accountSaleReturn.doesNotContain=" + UPDATED_ACCOUNT_SALE_RETURN);
    }


    @Test
    @Transactional
    public void getAllItemsByAccountSalePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountSalePrice equals to DEFAULT_ACCOUNT_SALE_PRICE
        defaultItemShouldBeFound("accountSalePrice.equals=" + DEFAULT_ACCOUNT_SALE_PRICE);

        // Get all the itemList where accountSalePrice equals to UPDATED_ACCOUNT_SALE_PRICE
        defaultItemShouldNotBeFound("accountSalePrice.equals=" + UPDATED_ACCOUNT_SALE_PRICE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountSalePriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountSalePrice not equals to DEFAULT_ACCOUNT_SALE_PRICE
        defaultItemShouldNotBeFound("accountSalePrice.notEquals=" + DEFAULT_ACCOUNT_SALE_PRICE);

        // Get all the itemList where accountSalePrice not equals to UPDATED_ACCOUNT_SALE_PRICE
        defaultItemShouldBeFound("accountSalePrice.notEquals=" + UPDATED_ACCOUNT_SALE_PRICE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountSalePriceIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountSalePrice in DEFAULT_ACCOUNT_SALE_PRICE or UPDATED_ACCOUNT_SALE_PRICE
        defaultItemShouldBeFound("accountSalePrice.in=" + DEFAULT_ACCOUNT_SALE_PRICE + "," + UPDATED_ACCOUNT_SALE_PRICE);

        // Get all the itemList where accountSalePrice equals to UPDATED_ACCOUNT_SALE_PRICE
        defaultItemShouldNotBeFound("accountSalePrice.in=" + UPDATED_ACCOUNT_SALE_PRICE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountSalePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountSalePrice is not null
        defaultItemShouldBeFound("accountSalePrice.specified=true");

        // Get all the itemList where accountSalePrice is null
        defaultItemShouldNotBeFound("accountSalePrice.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByAccountSalePriceContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountSalePrice contains DEFAULT_ACCOUNT_SALE_PRICE
        defaultItemShouldBeFound("accountSalePrice.contains=" + DEFAULT_ACCOUNT_SALE_PRICE);

        // Get all the itemList where accountSalePrice contains UPDATED_ACCOUNT_SALE_PRICE
        defaultItemShouldNotBeFound("accountSalePrice.contains=" + UPDATED_ACCOUNT_SALE_PRICE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountSalePriceNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountSalePrice does not contain DEFAULT_ACCOUNT_SALE_PRICE
        defaultItemShouldNotBeFound("accountSalePrice.doesNotContain=" + DEFAULT_ACCOUNT_SALE_PRICE);

        // Get all the itemList where accountSalePrice does not contain UPDATED_ACCOUNT_SALE_PRICE
        defaultItemShouldBeFound("accountSalePrice.doesNotContain=" + UPDATED_ACCOUNT_SALE_PRICE);
    }


    @Test
    @Transactional
    public void getAllItemsByAccountAgencyIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountAgency equals to DEFAULT_ACCOUNT_AGENCY
        defaultItemShouldBeFound("accountAgency.equals=" + DEFAULT_ACCOUNT_AGENCY);

        // Get all the itemList where accountAgency equals to UPDATED_ACCOUNT_AGENCY
        defaultItemShouldNotBeFound("accountAgency.equals=" + UPDATED_ACCOUNT_AGENCY);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountAgencyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountAgency not equals to DEFAULT_ACCOUNT_AGENCY
        defaultItemShouldNotBeFound("accountAgency.notEquals=" + DEFAULT_ACCOUNT_AGENCY);

        // Get all the itemList where accountAgency not equals to UPDATED_ACCOUNT_AGENCY
        defaultItemShouldBeFound("accountAgency.notEquals=" + UPDATED_ACCOUNT_AGENCY);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountAgencyIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountAgency in DEFAULT_ACCOUNT_AGENCY or UPDATED_ACCOUNT_AGENCY
        defaultItemShouldBeFound("accountAgency.in=" + DEFAULT_ACCOUNT_AGENCY + "," + UPDATED_ACCOUNT_AGENCY);

        // Get all the itemList where accountAgency equals to UPDATED_ACCOUNT_AGENCY
        defaultItemShouldNotBeFound("accountAgency.in=" + UPDATED_ACCOUNT_AGENCY);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountAgencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountAgency is not null
        defaultItemShouldBeFound("accountAgency.specified=true");

        // Get all the itemList where accountAgency is null
        defaultItemShouldNotBeFound("accountAgency.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByAccountAgencyContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountAgency contains DEFAULT_ACCOUNT_AGENCY
        defaultItemShouldBeFound("accountAgency.contains=" + DEFAULT_ACCOUNT_AGENCY);

        // Get all the itemList where accountAgency contains UPDATED_ACCOUNT_AGENCY
        defaultItemShouldNotBeFound("accountAgency.contains=" + UPDATED_ACCOUNT_AGENCY);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountAgencyNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountAgency does not contain DEFAULT_ACCOUNT_AGENCY
        defaultItemShouldNotBeFound("accountAgency.doesNotContain=" + DEFAULT_ACCOUNT_AGENCY);

        // Get all the itemList where accountAgency does not contain UPDATED_ACCOUNT_AGENCY
        defaultItemShouldBeFound("accountAgency.doesNotContain=" + UPDATED_ACCOUNT_AGENCY);
    }


    @Test
    @Transactional
    public void getAllItemsByAccountRawProductIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountRawProduct equals to DEFAULT_ACCOUNT_RAW_PRODUCT
        defaultItemShouldBeFound("accountRawProduct.equals=" + DEFAULT_ACCOUNT_RAW_PRODUCT);

        // Get all the itemList where accountRawProduct equals to UPDATED_ACCOUNT_RAW_PRODUCT
        defaultItemShouldNotBeFound("accountRawProduct.equals=" + UPDATED_ACCOUNT_RAW_PRODUCT);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountRawProductIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountRawProduct not equals to DEFAULT_ACCOUNT_RAW_PRODUCT
        defaultItemShouldNotBeFound("accountRawProduct.notEquals=" + DEFAULT_ACCOUNT_RAW_PRODUCT);

        // Get all the itemList where accountRawProduct not equals to UPDATED_ACCOUNT_RAW_PRODUCT
        defaultItemShouldBeFound("accountRawProduct.notEquals=" + UPDATED_ACCOUNT_RAW_PRODUCT);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountRawProductIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountRawProduct in DEFAULT_ACCOUNT_RAW_PRODUCT or UPDATED_ACCOUNT_RAW_PRODUCT
        defaultItemShouldBeFound("accountRawProduct.in=" + DEFAULT_ACCOUNT_RAW_PRODUCT + "," + UPDATED_ACCOUNT_RAW_PRODUCT);

        // Get all the itemList where accountRawProduct equals to UPDATED_ACCOUNT_RAW_PRODUCT
        defaultItemShouldNotBeFound("accountRawProduct.in=" + UPDATED_ACCOUNT_RAW_PRODUCT);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountRawProductIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountRawProduct is not null
        defaultItemShouldBeFound("accountRawProduct.specified=true");

        // Get all the itemList where accountRawProduct is null
        defaultItemShouldNotBeFound("accountRawProduct.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByAccountRawProductContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountRawProduct contains DEFAULT_ACCOUNT_RAW_PRODUCT
        defaultItemShouldBeFound("accountRawProduct.contains=" + DEFAULT_ACCOUNT_RAW_PRODUCT);

        // Get all the itemList where accountRawProduct contains UPDATED_ACCOUNT_RAW_PRODUCT
        defaultItemShouldNotBeFound("accountRawProduct.contains=" + UPDATED_ACCOUNT_RAW_PRODUCT);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountRawProductNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountRawProduct does not contain DEFAULT_ACCOUNT_RAW_PRODUCT
        defaultItemShouldNotBeFound("accountRawProduct.doesNotContain=" + DEFAULT_ACCOUNT_RAW_PRODUCT);

        // Get all the itemList where accountRawProduct does not contain UPDATED_ACCOUNT_RAW_PRODUCT
        defaultItemShouldBeFound("accountRawProduct.doesNotContain=" + UPDATED_ACCOUNT_RAW_PRODUCT);
    }


    @Test
    @Transactional
    public void getAllItemsByAccountCostDifferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountCostDifference equals to DEFAULT_ACCOUNT_COST_DIFFERENCE
        defaultItemShouldBeFound("accountCostDifference.equals=" + DEFAULT_ACCOUNT_COST_DIFFERENCE);

        // Get all the itemList where accountCostDifference equals to UPDATED_ACCOUNT_COST_DIFFERENCE
        defaultItemShouldNotBeFound("accountCostDifference.equals=" + UPDATED_ACCOUNT_COST_DIFFERENCE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountCostDifferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountCostDifference not equals to DEFAULT_ACCOUNT_COST_DIFFERENCE
        defaultItemShouldNotBeFound("accountCostDifference.notEquals=" + DEFAULT_ACCOUNT_COST_DIFFERENCE);

        // Get all the itemList where accountCostDifference not equals to UPDATED_ACCOUNT_COST_DIFFERENCE
        defaultItemShouldBeFound("accountCostDifference.notEquals=" + UPDATED_ACCOUNT_COST_DIFFERENCE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountCostDifferenceIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountCostDifference in DEFAULT_ACCOUNT_COST_DIFFERENCE or UPDATED_ACCOUNT_COST_DIFFERENCE
        defaultItemShouldBeFound("accountCostDifference.in=" + DEFAULT_ACCOUNT_COST_DIFFERENCE + "," + UPDATED_ACCOUNT_COST_DIFFERENCE);

        // Get all the itemList where accountCostDifference equals to UPDATED_ACCOUNT_COST_DIFFERENCE
        defaultItemShouldNotBeFound("accountCostDifference.in=" + UPDATED_ACCOUNT_COST_DIFFERENCE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountCostDifferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountCostDifference is not null
        defaultItemShouldBeFound("accountCostDifference.specified=true");

        // Get all the itemList where accountCostDifference is null
        defaultItemShouldNotBeFound("accountCostDifference.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByAccountCostDifferenceContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountCostDifference contains DEFAULT_ACCOUNT_COST_DIFFERENCE
        defaultItemShouldBeFound("accountCostDifference.contains=" + DEFAULT_ACCOUNT_COST_DIFFERENCE);

        // Get all the itemList where accountCostDifference contains UPDATED_ACCOUNT_COST_DIFFERENCE
        defaultItemShouldNotBeFound("accountCostDifference.contains=" + UPDATED_ACCOUNT_COST_DIFFERENCE);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountCostDifferenceNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountCostDifference does not contain DEFAULT_ACCOUNT_COST_DIFFERENCE
        defaultItemShouldNotBeFound("accountCostDifference.doesNotContain=" + DEFAULT_ACCOUNT_COST_DIFFERENCE);

        // Get all the itemList where accountCostDifference does not contain UPDATED_ACCOUNT_COST_DIFFERENCE
        defaultItemShouldBeFound("accountCostDifference.doesNotContain=" + UPDATED_ACCOUNT_COST_DIFFERENCE);
    }


    @Test
    @Transactional
    public void getAllItemsByAccountDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountDiscount equals to DEFAULT_ACCOUNT_DISCOUNT
        defaultItemShouldBeFound("accountDiscount.equals=" + DEFAULT_ACCOUNT_DISCOUNT);

        // Get all the itemList where accountDiscount equals to UPDATED_ACCOUNT_DISCOUNT
        defaultItemShouldNotBeFound("accountDiscount.equals=" + UPDATED_ACCOUNT_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountDiscount not equals to DEFAULT_ACCOUNT_DISCOUNT
        defaultItemShouldNotBeFound("accountDiscount.notEquals=" + DEFAULT_ACCOUNT_DISCOUNT);

        // Get all the itemList where accountDiscount not equals to UPDATED_ACCOUNT_DISCOUNT
        defaultItemShouldBeFound("accountDiscount.notEquals=" + UPDATED_ACCOUNT_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountDiscount in DEFAULT_ACCOUNT_DISCOUNT or UPDATED_ACCOUNT_DISCOUNT
        defaultItemShouldBeFound("accountDiscount.in=" + DEFAULT_ACCOUNT_DISCOUNT + "," + UPDATED_ACCOUNT_DISCOUNT);

        // Get all the itemList where accountDiscount equals to UPDATED_ACCOUNT_DISCOUNT
        defaultItemShouldNotBeFound("accountDiscount.in=" + UPDATED_ACCOUNT_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountDiscount is not null
        defaultItemShouldBeFound("accountDiscount.specified=true");

        // Get all the itemList where accountDiscount is null
        defaultItemShouldNotBeFound("accountDiscount.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByAccountDiscountContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountDiscount contains DEFAULT_ACCOUNT_DISCOUNT
        defaultItemShouldBeFound("accountDiscount.contains=" + DEFAULT_ACCOUNT_DISCOUNT);

        // Get all the itemList where accountDiscount contains UPDATED_ACCOUNT_DISCOUNT
        defaultItemShouldNotBeFound("accountDiscount.contains=" + UPDATED_ACCOUNT_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllItemsByAccountDiscountNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where accountDiscount does not contain DEFAULT_ACCOUNT_DISCOUNT
        defaultItemShouldNotBeFound("accountDiscount.doesNotContain=" + DEFAULT_ACCOUNT_DISCOUNT);

        // Get all the itemList where accountDiscount does not contain UPDATED_ACCOUNT_DISCOUNT
        defaultItemShouldBeFound("accountDiscount.doesNotContain=" + UPDATED_ACCOUNT_DISCOUNT);
    }


    @Test
    @Transactional
    public void getAllItemsBySaleDescIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where saleDesc equals to DEFAULT_SALE_DESC
        defaultItemShouldBeFound("saleDesc.equals=" + DEFAULT_SALE_DESC);

        // Get all the itemList where saleDesc equals to UPDATED_SALE_DESC
        defaultItemShouldNotBeFound("saleDesc.equals=" + UPDATED_SALE_DESC);
    }

    @Test
    @Transactional
    public void getAllItemsBySaleDescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where saleDesc not equals to DEFAULT_SALE_DESC
        defaultItemShouldNotBeFound("saleDesc.notEquals=" + DEFAULT_SALE_DESC);

        // Get all the itemList where saleDesc not equals to UPDATED_SALE_DESC
        defaultItemShouldBeFound("saleDesc.notEquals=" + UPDATED_SALE_DESC);
    }

    @Test
    @Transactional
    public void getAllItemsBySaleDescIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where saleDesc in DEFAULT_SALE_DESC or UPDATED_SALE_DESC
        defaultItemShouldBeFound("saleDesc.in=" + DEFAULT_SALE_DESC + "," + UPDATED_SALE_DESC);

        // Get all the itemList where saleDesc equals to UPDATED_SALE_DESC
        defaultItemShouldNotBeFound("saleDesc.in=" + UPDATED_SALE_DESC);
    }

    @Test
    @Transactional
    public void getAllItemsBySaleDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where saleDesc is not null
        defaultItemShouldBeFound("saleDesc.specified=true");

        // Get all the itemList where saleDesc is null
        defaultItemShouldNotBeFound("saleDesc.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsBySaleDescContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where saleDesc contains DEFAULT_SALE_DESC
        defaultItemShouldBeFound("saleDesc.contains=" + DEFAULT_SALE_DESC);

        // Get all the itemList where saleDesc contains UPDATED_SALE_DESC
        defaultItemShouldNotBeFound("saleDesc.contains=" + UPDATED_SALE_DESC);
    }

    @Test
    @Transactional
    public void getAllItemsBySaleDescNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where saleDesc does not contain DEFAULT_SALE_DESC
        defaultItemShouldNotBeFound("saleDesc.doesNotContain=" + DEFAULT_SALE_DESC);

        // Get all the itemList where saleDesc does not contain UPDATED_SALE_DESC
        defaultItemShouldBeFound("saleDesc.doesNotContain=" + UPDATED_SALE_DESC);
    }


    @Test
    @Transactional
    public void getAllItemsByPurchaseDescIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where purchaseDesc equals to DEFAULT_PURCHASE_DESC
        defaultItemShouldBeFound("purchaseDesc.equals=" + DEFAULT_PURCHASE_DESC);

        // Get all the itemList where purchaseDesc equals to UPDATED_PURCHASE_DESC
        defaultItemShouldNotBeFound("purchaseDesc.equals=" + UPDATED_PURCHASE_DESC);
    }

    @Test
    @Transactional
    public void getAllItemsByPurchaseDescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where purchaseDesc not equals to DEFAULT_PURCHASE_DESC
        defaultItemShouldNotBeFound("purchaseDesc.notEquals=" + DEFAULT_PURCHASE_DESC);

        // Get all the itemList where purchaseDesc not equals to UPDATED_PURCHASE_DESC
        defaultItemShouldBeFound("purchaseDesc.notEquals=" + UPDATED_PURCHASE_DESC);
    }

    @Test
    @Transactional
    public void getAllItemsByPurchaseDescIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where purchaseDesc in DEFAULT_PURCHASE_DESC or UPDATED_PURCHASE_DESC
        defaultItemShouldBeFound("purchaseDesc.in=" + DEFAULT_PURCHASE_DESC + "," + UPDATED_PURCHASE_DESC);

        // Get all the itemList where purchaseDesc equals to UPDATED_PURCHASE_DESC
        defaultItemShouldNotBeFound("purchaseDesc.in=" + UPDATED_PURCHASE_DESC);
    }

    @Test
    @Transactional
    public void getAllItemsByPurchaseDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where purchaseDesc is not null
        defaultItemShouldBeFound("purchaseDesc.specified=true");

        // Get all the itemList where purchaseDesc is null
        defaultItemShouldNotBeFound("purchaseDesc.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByPurchaseDescContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where purchaseDesc contains DEFAULT_PURCHASE_DESC
        defaultItemShouldBeFound("purchaseDesc.contains=" + DEFAULT_PURCHASE_DESC);

        // Get all the itemList where purchaseDesc contains UPDATED_PURCHASE_DESC
        defaultItemShouldNotBeFound("purchaseDesc.contains=" + UPDATED_PURCHASE_DESC);
    }

    @Test
    @Transactional
    public void getAllItemsByPurchaseDescNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where purchaseDesc does not contain DEFAULT_PURCHASE_DESC
        defaultItemShouldNotBeFound("purchaseDesc.doesNotContain=" + DEFAULT_PURCHASE_DESC);

        // Get all the itemList where purchaseDesc does not contain UPDATED_PURCHASE_DESC
        defaultItemShouldBeFound("purchaseDesc.doesNotContain=" + UPDATED_PURCHASE_DESC);
    }


    @Test
    @Transactional
    public void getAllItemsByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where weight equals to DEFAULT_WEIGHT
        defaultItemShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the itemList where weight equals to UPDATED_WEIGHT
        defaultItemShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where weight not equals to DEFAULT_WEIGHT
        defaultItemShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the itemList where weight not equals to UPDATED_WEIGHT
        defaultItemShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultItemShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the itemList where weight equals to UPDATED_WEIGHT
        defaultItemShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where weight is not null
        defaultItemShouldBeFound("weight.specified=true");

        // Get all the itemList where weight is null
        defaultItemShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultItemShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the itemList where weight is greater than or equal to UPDATED_WEIGHT
        defaultItemShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where weight is less than or equal to DEFAULT_WEIGHT
        defaultItemShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the itemList where weight is less than or equal to SMALLER_WEIGHT
        defaultItemShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where weight is less than DEFAULT_WEIGHT
        defaultItemShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the itemList where weight is less than UPDATED_WEIGHT
        defaultItemShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where weight is greater than DEFAULT_WEIGHT
        defaultItemShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the itemList where weight is greater than SMALLER_WEIGHT
        defaultItemShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllItemsByLenghtIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where lenght equals to DEFAULT_LENGHT
        defaultItemShouldBeFound("lenght.equals=" + DEFAULT_LENGHT);

        // Get all the itemList where lenght equals to UPDATED_LENGHT
        defaultItemShouldNotBeFound("lenght.equals=" + UPDATED_LENGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByLenghtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where lenght not equals to DEFAULT_LENGHT
        defaultItemShouldNotBeFound("lenght.notEquals=" + DEFAULT_LENGHT);

        // Get all the itemList where lenght not equals to UPDATED_LENGHT
        defaultItemShouldBeFound("lenght.notEquals=" + UPDATED_LENGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByLenghtIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where lenght in DEFAULT_LENGHT or UPDATED_LENGHT
        defaultItemShouldBeFound("lenght.in=" + DEFAULT_LENGHT + "," + UPDATED_LENGHT);

        // Get all the itemList where lenght equals to UPDATED_LENGHT
        defaultItemShouldNotBeFound("lenght.in=" + UPDATED_LENGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByLenghtIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where lenght is not null
        defaultItemShouldBeFound("lenght.specified=true");

        // Get all the itemList where lenght is null
        defaultItemShouldNotBeFound("lenght.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByLenghtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where lenght is greater than or equal to DEFAULT_LENGHT
        defaultItemShouldBeFound("lenght.greaterThanOrEqual=" + DEFAULT_LENGHT);

        // Get all the itemList where lenght is greater than or equal to UPDATED_LENGHT
        defaultItemShouldNotBeFound("lenght.greaterThanOrEqual=" + UPDATED_LENGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByLenghtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where lenght is less than or equal to DEFAULT_LENGHT
        defaultItemShouldBeFound("lenght.lessThanOrEqual=" + DEFAULT_LENGHT);

        // Get all the itemList where lenght is less than or equal to SMALLER_LENGHT
        defaultItemShouldNotBeFound("lenght.lessThanOrEqual=" + SMALLER_LENGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByLenghtIsLessThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where lenght is less than DEFAULT_LENGHT
        defaultItemShouldNotBeFound("lenght.lessThan=" + DEFAULT_LENGHT);

        // Get all the itemList where lenght is less than UPDATED_LENGHT
        defaultItemShouldBeFound("lenght.lessThan=" + UPDATED_LENGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByLenghtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where lenght is greater than DEFAULT_LENGHT
        defaultItemShouldNotBeFound("lenght.greaterThan=" + DEFAULT_LENGHT);

        // Get all the itemList where lenght is greater than SMALLER_LENGHT
        defaultItemShouldBeFound("lenght.greaterThan=" + SMALLER_LENGHT);
    }


    @Test
    @Transactional
    public void getAllItemsByWideIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where wide equals to DEFAULT_WIDE
        defaultItemShouldBeFound("wide.equals=" + DEFAULT_WIDE);

        // Get all the itemList where wide equals to UPDATED_WIDE
        defaultItemShouldNotBeFound("wide.equals=" + UPDATED_WIDE);
    }

    @Test
    @Transactional
    public void getAllItemsByWideIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where wide not equals to DEFAULT_WIDE
        defaultItemShouldNotBeFound("wide.notEquals=" + DEFAULT_WIDE);

        // Get all the itemList where wide not equals to UPDATED_WIDE
        defaultItemShouldBeFound("wide.notEquals=" + UPDATED_WIDE);
    }

    @Test
    @Transactional
    public void getAllItemsByWideIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where wide in DEFAULT_WIDE or UPDATED_WIDE
        defaultItemShouldBeFound("wide.in=" + DEFAULT_WIDE + "," + UPDATED_WIDE);

        // Get all the itemList where wide equals to UPDATED_WIDE
        defaultItemShouldNotBeFound("wide.in=" + UPDATED_WIDE);
    }

    @Test
    @Transactional
    public void getAllItemsByWideIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where wide is not null
        defaultItemShouldBeFound("wide.specified=true");

        // Get all the itemList where wide is null
        defaultItemShouldNotBeFound("wide.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByWideIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where wide is greater than or equal to DEFAULT_WIDE
        defaultItemShouldBeFound("wide.greaterThanOrEqual=" + DEFAULT_WIDE);

        // Get all the itemList where wide is greater than or equal to UPDATED_WIDE
        defaultItemShouldNotBeFound("wide.greaterThanOrEqual=" + UPDATED_WIDE);
    }

    @Test
    @Transactional
    public void getAllItemsByWideIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where wide is less than or equal to DEFAULT_WIDE
        defaultItemShouldBeFound("wide.lessThanOrEqual=" + DEFAULT_WIDE);

        // Get all the itemList where wide is less than or equal to SMALLER_WIDE
        defaultItemShouldNotBeFound("wide.lessThanOrEqual=" + SMALLER_WIDE);
    }

    @Test
    @Transactional
    public void getAllItemsByWideIsLessThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where wide is less than DEFAULT_WIDE
        defaultItemShouldNotBeFound("wide.lessThan=" + DEFAULT_WIDE);

        // Get all the itemList where wide is less than UPDATED_WIDE
        defaultItemShouldBeFound("wide.lessThan=" + UPDATED_WIDE);
    }

    @Test
    @Transactional
    public void getAllItemsByWideIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where wide is greater than DEFAULT_WIDE
        defaultItemShouldNotBeFound("wide.greaterThan=" + DEFAULT_WIDE);

        // Get all the itemList where wide is greater than SMALLER_WIDE
        defaultItemShouldBeFound("wide.greaterThan=" + SMALLER_WIDE);
    }


    @Test
    @Transactional
    public void getAllItemsByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where height equals to DEFAULT_HEIGHT
        defaultItemShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the itemList where height equals to UPDATED_HEIGHT
        defaultItemShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where height not equals to DEFAULT_HEIGHT
        defaultItemShouldNotBeFound("height.notEquals=" + DEFAULT_HEIGHT);

        // Get all the itemList where height not equals to UPDATED_HEIGHT
        defaultItemShouldBeFound("height.notEquals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultItemShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the itemList where height equals to UPDATED_HEIGHT
        defaultItemShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where height is not null
        defaultItemShouldBeFound("height.specified=true");

        // Get all the itemList where height is null
        defaultItemShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where height is greater than or equal to DEFAULT_HEIGHT
        defaultItemShouldBeFound("height.greaterThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the itemList where height is greater than or equal to UPDATED_HEIGHT
        defaultItemShouldNotBeFound("height.greaterThanOrEqual=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where height is less than or equal to DEFAULT_HEIGHT
        defaultItemShouldBeFound("height.lessThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the itemList where height is less than or equal to SMALLER_HEIGHT
        defaultItemShouldNotBeFound("height.lessThanOrEqual=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where height is less than DEFAULT_HEIGHT
        defaultItemShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the itemList where height is less than UPDATED_HEIGHT
        defaultItemShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllItemsByHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where height is greater than DEFAULT_HEIGHT
        defaultItemShouldNotBeFound("height.greaterThan=" + DEFAULT_HEIGHT);

        // Get all the itemList where height is greater than SMALLER_HEIGHT
        defaultItemShouldBeFound("height.greaterThan=" + SMALLER_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllItemsByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where color equals to DEFAULT_COLOR
        defaultItemShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the itemList where color equals to UPDATED_COLOR
        defaultItemShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllItemsByColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where color not equals to DEFAULT_COLOR
        defaultItemShouldNotBeFound("color.notEquals=" + DEFAULT_COLOR);

        // Get all the itemList where color not equals to UPDATED_COLOR
        defaultItemShouldBeFound("color.notEquals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllItemsByColorIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultItemShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the itemList where color equals to UPDATED_COLOR
        defaultItemShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllItemsByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where color is not null
        defaultItemShouldBeFound("color.specified=true");

        // Get all the itemList where color is null
        defaultItemShouldNotBeFound("color.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsByColorContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where color contains DEFAULT_COLOR
        defaultItemShouldBeFound("color.contains=" + DEFAULT_COLOR);

        // Get all the itemList where color contains UPDATED_COLOR
        defaultItemShouldNotBeFound("color.contains=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllItemsByColorNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where color does not contain DEFAULT_COLOR
        defaultItemShouldNotBeFound("color.doesNotContain=" + DEFAULT_COLOR);

        // Get all the itemList where color does not contain UPDATED_COLOR
        defaultItemShouldBeFound("color.doesNotContain=" + UPDATED_COLOR);
    }


    @Test
    @Transactional
    public void getAllItemsBySpecificationIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where specification equals to DEFAULT_SPECIFICATION
        defaultItemShouldBeFound("specification.equals=" + DEFAULT_SPECIFICATION);

        // Get all the itemList where specification equals to UPDATED_SPECIFICATION
        defaultItemShouldNotBeFound("specification.equals=" + UPDATED_SPECIFICATION);
    }

    @Test
    @Transactional
    public void getAllItemsBySpecificationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where specification not equals to DEFAULT_SPECIFICATION
        defaultItemShouldNotBeFound("specification.notEquals=" + DEFAULT_SPECIFICATION);

        // Get all the itemList where specification not equals to UPDATED_SPECIFICATION
        defaultItemShouldBeFound("specification.notEquals=" + UPDATED_SPECIFICATION);
    }

    @Test
    @Transactional
    public void getAllItemsBySpecificationIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where specification in DEFAULT_SPECIFICATION or UPDATED_SPECIFICATION
        defaultItemShouldBeFound("specification.in=" + DEFAULT_SPECIFICATION + "," + UPDATED_SPECIFICATION);

        // Get all the itemList where specification equals to UPDATED_SPECIFICATION
        defaultItemShouldNotBeFound("specification.in=" + UPDATED_SPECIFICATION);
    }

    @Test
    @Transactional
    public void getAllItemsBySpecificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where specification is not null
        defaultItemShouldBeFound("specification.specified=true");

        // Get all the itemList where specification is null
        defaultItemShouldNotBeFound("specification.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemsBySpecificationContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where specification contains DEFAULT_SPECIFICATION
        defaultItemShouldBeFound("specification.contains=" + DEFAULT_SPECIFICATION);

        // Get all the itemList where specification contains UPDATED_SPECIFICATION
        defaultItemShouldNotBeFound("specification.contains=" + UPDATED_SPECIFICATION);
    }

    @Test
    @Transactional
    public void getAllItemsBySpecificationNotContainsSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where specification does not contain DEFAULT_SPECIFICATION
        defaultItemShouldNotBeFound("specification.doesNotContain=" + DEFAULT_SPECIFICATION);

        // Get all the itemList where specification does not contain UPDATED_SPECIFICATION
        defaultItemShouldBeFound("specification.doesNotContain=" + UPDATED_SPECIFICATION);
    }


    @Test
    @Transactional
    public void getAllItemsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where isActive equals to DEFAULT_IS_ACTIVE
        defaultItemShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the itemList where isActive equals to UPDATED_IS_ACTIVE
        defaultItemShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllItemsByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultItemShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the itemList where isActive not equals to UPDATED_IS_ACTIVE
        defaultItemShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllItemsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultItemShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the itemList where isActive equals to UPDATED_IS_ACTIVE
        defaultItemShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllItemsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where isActive is not null
        defaultItemShouldBeFound("isActive.specified=true");

        // Get all the itemList where isActive is null
        defaultItemShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByTimeCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where timeCreated equals to DEFAULT_TIME_CREATED
        defaultItemShouldBeFound("timeCreated.equals=" + DEFAULT_TIME_CREATED);

        // Get all the itemList where timeCreated equals to UPDATED_TIME_CREATED
        defaultItemShouldNotBeFound("timeCreated.equals=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllItemsByTimeCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where timeCreated not equals to DEFAULT_TIME_CREATED
        defaultItemShouldNotBeFound("timeCreated.notEquals=" + DEFAULT_TIME_CREATED);

        // Get all the itemList where timeCreated not equals to UPDATED_TIME_CREATED
        defaultItemShouldBeFound("timeCreated.notEquals=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllItemsByTimeCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where timeCreated in DEFAULT_TIME_CREATED or UPDATED_TIME_CREATED
        defaultItemShouldBeFound("timeCreated.in=" + DEFAULT_TIME_CREATED + "," + UPDATED_TIME_CREATED);

        // Get all the itemList where timeCreated equals to UPDATED_TIME_CREATED
        defaultItemShouldNotBeFound("timeCreated.in=" + UPDATED_TIME_CREATED);
    }

    @Test
    @Transactional
    public void getAllItemsByTimeCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where timeCreated is not null
        defaultItemShouldBeFound("timeCreated.specified=true");

        // Get all the itemList where timeCreated is null
        defaultItemShouldNotBeFound("timeCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByTimeModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where timeModified equals to DEFAULT_TIME_MODIFIED
        defaultItemShouldBeFound("timeModified.equals=" + DEFAULT_TIME_MODIFIED);

        // Get all the itemList where timeModified equals to UPDATED_TIME_MODIFIED
        defaultItemShouldNotBeFound("timeModified.equals=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllItemsByTimeModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where timeModified not equals to DEFAULT_TIME_MODIFIED
        defaultItemShouldNotBeFound("timeModified.notEquals=" + DEFAULT_TIME_MODIFIED);

        // Get all the itemList where timeModified not equals to UPDATED_TIME_MODIFIED
        defaultItemShouldBeFound("timeModified.notEquals=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllItemsByTimeModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where timeModified in DEFAULT_TIME_MODIFIED or UPDATED_TIME_MODIFIED
        defaultItemShouldBeFound("timeModified.in=" + DEFAULT_TIME_MODIFIED + "," + UPDATED_TIME_MODIFIED);

        // Get all the itemList where timeModified equals to UPDATED_TIME_MODIFIED
        defaultItemShouldNotBeFound("timeModified.in=" + UPDATED_TIME_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllItemsByTimeModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where timeModified is not null
        defaultItemShouldBeFound("timeModified.specified=true");

        // Get all the itemList where timeModified is null
        defaultItemShouldNotBeFound("timeModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdCreated equals to DEFAULT_USER_ID_CREATED
        defaultItemShouldBeFound("userIdCreated.equals=" + DEFAULT_USER_ID_CREATED);

        // Get all the itemList where userIdCreated equals to UPDATED_USER_ID_CREATED
        defaultItemShouldNotBeFound("userIdCreated.equals=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdCreated not equals to DEFAULT_USER_ID_CREATED
        defaultItemShouldNotBeFound("userIdCreated.notEquals=" + DEFAULT_USER_ID_CREATED);

        // Get all the itemList where userIdCreated not equals to UPDATED_USER_ID_CREATED
        defaultItemShouldBeFound("userIdCreated.notEquals=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdCreated in DEFAULT_USER_ID_CREATED or UPDATED_USER_ID_CREATED
        defaultItemShouldBeFound("userIdCreated.in=" + DEFAULT_USER_ID_CREATED + "," + UPDATED_USER_ID_CREATED);

        // Get all the itemList where userIdCreated equals to UPDATED_USER_ID_CREATED
        defaultItemShouldNotBeFound("userIdCreated.in=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdCreated is not null
        defaultItemShouldBeFound("userIdCreated.specified=true");

        // Get all the itemList where userIdCreated is null
        defaultItemShouldNotBeFound("userIdCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdCreatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdCreated is greater than or equal to DEFAULT_USER_ID_CREATED
        defaultItemShouldBeFound("userIdCreated.greaterThanOrEqual=" + DEFAULT_USER_ID_CREATED);

        // Get all the itemList where userIdCreated is greater than or equal to UPDATED_USER_ID_CREATED
        defaultItemShouldNotBeFound("userIdCreated.greaterThanOrEqual=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdCreatedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdCreated is less than or equal to DEFAULT_USER_ID_CREATED
        defaultItemShouldBeFound("userIdCreated.lessThanOrEqual=" + DEFAULT_USER_ID_CREATED);

        // Get all the itemList where userIdCreated is less than or equal to SMALLER_USER_ID_CREATED
        defaultItemShouldNotBeFound("userIdCreated.lessThanOrEqual=" + SMALLER_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdCreatedIsLessThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdCreated is less than DEFAULT_USER_ID_CREATED
        defaultItemShouldNotBeFound("userIdCreated.lessThan=" + DEFAULT_USER_ID_CREATED);

        // Get all the itemList where userIdCreated is less than UPDATED_USER_ID_CREATED
        defaultItemShouldBeFound("userIdCreated.lessThan=" + UPDATED_USER_ID_CREATED);
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdCreatedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdCreated is greater than DEFAULT_USER_ID_CREATED
        defaultItemShouldNotBeFound("userIdCreated.greaterThan=" + DEFAULT_USER_ID_CREATED);

        // Get all the itemList where userIdCreated is greater than SMALLER_USER_ID_CREATED
        defaultItemShouldBeFound("userIdCreated.greaterThan=" + SMALLER_USER_ID_CREATED);
    }


    @Test
    @Transactional
    public void getAllItemsByUserIdModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdModified equals to DEFAULT_USER_ID_MODIFIED
        defaultItemShouldBeFound("userIdModified.equals=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the itemList where userIdModified equals to UPDATED_USER_ID_MODIFIED
        defaultItemShouldNotBeFound("userIdModified.equals=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdModified not equals to DEFAULT_USER_ID_MODIFIED
        defaultItemShouldNotBeFound("userIdModified.notEquals=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the itemList where userIdModified not equals to UPDATED_USER_ID_MODIFIED
        defaultItemShouldBeFound("userIdModified.notEquals=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdModified in DEFAULT_USER_ID_MODIFIED or UPDATED_USER_ID_MODIFIED
        defaultItemShouldBeFound("userIdModified.in=" + DEFAULT_USER_ID_MODIFIED + "," + UPDATED_USER_ID_MODIFIED);

        // Get all the itemList where userIdModified equals to UPDATED_USER_ID_MODIFIED
        defaultItemShouldNotBeFound("userIdModified.in=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdModified is not null
        defaultItemShouldBeFound("userIdModified.specified=true");

        // Get all the itemList where userIdModified is null
        defaultItemShouldNotBeFound("userIdModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdModifiedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdModified is greater than or equal to DEFAULT_USER_ID_MODIFIED
        defaultItemShouldBeFound("userIdModified.greaterThanOrEqual=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the itemList where userIdModified is greater than or equal to UPDATED_USER_ID_MODIFIED
        defaultItemShouldNotBeFound("userIdModified.greaterThanOrEqual=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdModifiedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdModified is less than or equal to DEFAULT_USER_ID_MODIFIED
        defaultItemShouldBeFound("userIdModified.lessThanOrEqual=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the itemList where userIdModified is less than or equal to SMALLER_USER_ID_MODIFIED
        defaultItemShouldNotBeFound("userIdModified.lessThanOrEqual=" + SMALLER_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdModifiedIsLessThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdModified is less than DEFAULT_USER_ID_MODIFIED
        defaultItemShouldNotBeFound("userIdModified.lessThan=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the itemList where userIdModified is less than UPDATED_USER_ID_MODIFIED
        defaultItemShouldBeFound("userIdModified.lessThan=" + UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllItemsByUserIdModifiedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where userIdModified is greater than DEFAULT_USER_ID_MODIFIED
        defaultItemShouldNotBeFound("userIdModified.greaterThan=" + DEFAULT_USER_ID_MODIFIED);

        // Get all the itemList where userIdModified is greater than SMALLER_USER_ID_MODIFIED
        defaultItemShouldBeFound("userIdModified.greaterThan=" + SMALLER_USER_ID_MODIFIED);
    }


    @Test
    @Transactional
    public void getAllItemsByInvoiceLineIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);
        InvoiceLine invoiceLine = InvoiceLineResourceIT.createEntity(em);
        em.persist(invoiceLine);
        em.flush();
        item.addInvoiceLine(invoiceLine);
        itemRepository.saveAndFlush(item);
        Long invoiceLineId = invoiceLine.getId();

        // Get all the itemList where invoiceLine equals to invoiceLineId
        defaultItemShouldBeFound("invoiceLineId.equals=" + invoiceLineId);

        // Get all the itemList where invoiceLine equals to invoiceLineId + 1
        defaultItemShouldNotBeFound("invoiceLineId.equals=" + (invoiceLineId + 1));
    }


    @Test
    @Transactional
    public void getAllItemsByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);
        Unit unit = UnitResourceIT.createEntity(em);
        em.persist(unit);
        em.flush();
        item.setUnit(unit);
        itemRepository.saveAndFlush(item);
        Long unitId = unit.getId();

        // Get all the itemList where unit equals to unitId
        defaultItemShouldBeFound("unitId.equals=" + unitId);

        // Get all the itemList where unit equals to unitId + 1
        defaultItemShouldNotBeFound("unitId.equals=" + (unitId + 1));
    }


    @Test
    @Transactional
    public void getAllItemsByItemGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);
        ItemGroup itemGroup = ItemGroupResourceIT.createEntity(em);
        em.persist(itemGroup);
        em.flush();
        item.setItemGroup(itemGroup);
        itemRepository.saveAndFlush(item);
        Long itemGroupId = itemGroup.getId();

        // Get all the itemList where itemGroup equals to itemGroupId
        defaultItemShouldBeFound("itemGroupId.equals=" + itemGroupId);

        // Get all the itemList where itemGroup equals to itemGroupId + 1
        defaultItemShouldNotBeFound("itemGroupId.equals=" + (itemGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllItemsByStoreIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);
        Store store = StoreResourceIT.createEntity(em);
        em.persist(store);
        em.flush();
        item.setStore(store);
        itemRepository.saveAndFlush(item);
        Long storeId = store.getId();

        // Get all the itemList where store equals to storeId
        defaultItemShouldBeFound("storeId.equals=" + storeId);

        // Get all the itemList where store equals to storeId + 1
        defaultItemShouldNotBeFound("storeId.equals=" + (storeId + 1));
    }


    @Test
    @Transactional
    public void getAllItemsByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);
        Company company = CompanyResourceIT.createEntity(em);
        em.persist(company);
        em.flush();
        item.setCompany(company);
        itemRepository.saveAndFlush(item);
        Long companyId = company.getId();

        // Get all the itemList where company equals to companyId
        defaultItemShouldBeFound("companyId.equals=" + companyId);

        // Get all the itemList where company equals to companyId + 1
        defaultItemShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItemShouldBeFound(String filter) throws Exception {
        restItemMockMvc.perform(get("/api/items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(item.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].skuNum").value(hasItem(DEFAULT_SKU_NUM.doubleValue())))
            .andExpect(jsonPath("$.[*].vatTax").value(hasItem(DEFAULT_VAT_TAX.toString())))
            .andExpect(jsonPath("$.[*].importTax").value(hasItem(DEFAULT_IMPORT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].exportTax").value(hasItem(DEFAULT_EXPORT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].inventoryPriceMethod").value(hasItem(DEFAULT_INVENTORY_PRICE_METHOD.toString())))
            .andExpect(jsonPath("$.[*].accountItem").value(hasItem(DEFAULT_ACCOUNT_ITEM)))
            .andExpect(jsonPath("$.[*].isAllowModified").value(hasItem(DEFAULT_IS_ALLOW_MODIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].accountCost").value(hasItem(DEFAULT_ACCOUNT_COST)))
            .andExpect(jsonPath("$.[*].accountRevenue").value(hasItem(DEFAULT_ACCOUNT_REVENUE)))
            .andExpect(jsonPath("$.[*].accountInternalRevenue").value(hasItem(DEFAULT_ACCOUNT_INTERNAL_REVENUE)))
            .andExpect(jsonPath("$.[*].accountSaleReturn").value(hasItem(DEFAULT_ACCOUNT_SALE_RETURN)))
            .andExpect(jsonPath("$.[*].accountSalePrice").value(hasItem(DEFAULT_ACCOUNT_SALE_PRICE)))
            .andExpect(jsonPath("$.[*].accountAgency").value(hasItem(DEFAULT_ACCOUNT_AGENCY)))
            .andExpect(jsonPath("$.[*].accountRawProduct").value(hasItem(DEFAULT_ACCOUNT_RAW_PRODUCT)))
            .andExpect(jsonPath("$.[*].accountCostDifference").value(hasItem(DEFAULT_ACCOUNT_COST_DIFFERENCE)))
            .andExpect(jsonPath("$.[*].accountDiscount").value(hasItem(DEFAULT_ACCOUNT_DISCOUNT)))
            .andExpect(jsonPath("$.[*].saleDesc").value(hasItem(DEFAULT_SALE_DESC)))
            .andExpect(jsonPath("$.[*].purchaseDesc").value(hasItem(DEFAULT_PURCHASE_DESC)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].lenght").value(hasItem(DEFAULT_LENGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].wide").value(hasItem(DEFAULT_WIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].specification").value(hasItem(DEFAULT_SPECIFICATION)))
            .andExpect(jsonPath("$.[*].itemImageContentType").value(hasItem(DEFAULT_ITEM_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].itemImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_ITEM_IMAGE))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].timeCreated").value(hasItem(DEFAULT_TIME_CREATED.toString())))
            .andExpect(jsonPath("$.[*].timeModified").value(hasItem(DEFAULT_TIME_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].userIdCreated").value(hasItem(DEFAULT_USER_ID_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userIdModified").value(hasItem(DEFAULT_USER_ID_MODIFIED.intValue())));

        // Check, that the count call also returns 1
        restItemMockMvc.perform(get("/api/items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItemShouldNotBeFound(String filter) throws Exception {
        restItemMockMvc.perform(get("/api/items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemMockMvc.perform(get("/api/items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItem() throws Exception {
        // Get the item
        restItemMockMvc.perform(get("/api/items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItem() throws Exception {
        // Initialize the database
        itemService.save(item);

        int databaseSizeBeforeUpdate = itemRepository.findAll().size();

        // Update the item
        Item updatedItem = itemRepository.findById(item.getId()).get();
        // Disconnect from session so that the updates on updatedItem are not directly saved in db
        em.detach(updatedItem);
        updatedItem
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .skuNum(UPDATED_SKU_NUM)
            .vatTax(UPDATED_VAT_TAX)
            .importTax(UPDATED_IMPORT_TAX)
            .exportTax(UPDATED_EXPORT_TAX)
            .inventoryPriceMethod(UPDATED_INVENTORY_PRICE_METHOD)
            .accountItem(UPDATED_ACCOUNT_ITEM)
            .isAllowModified(UPDATED_IS_ALLOW_MODIFIED)
            .accountCost(UPDATED_ACCOUNT_COST)
            .accountRevenue(UPDATED_ACCOUNT_REVENUE)
            .accountInternalRevenue(UPDATED_ACCOUNT_INTERNAL_REVENUE)
            .accountSaleReturn(UPDATED_ACCOUNT_SALE_RETURN)
            .accountSalePrice(UPDATED_ACCOUNT_SALE_PRICE)
            .accountAgency(UPDATED_ACCOUNT_AGENCY)
            .accountRawProduct(UPDATED_ACCOUNT_RAW_PRODUCT)
            .accountCostDifference(UPDATED_ACCOUNT_COST_DIFFERENCE)
            .accountDiscount(UPDATED_ACCOUNT_DISCOUNT)
            .saleDesc(UPDATED_SALE_DESC)
            .purchaseDesc(UPDATED_PURCHASE_DESC)
            .weight(UPDATED_WEIGHT)
            .lenght(UPDATED_LENGHT)
            .wide(UPDATED_WIDE)
            .height(UPDATED_HEIGHT)
            .color(UPDATED_COLOR)
            .specification(UPDATED_SPECIFICATION)
            .itemImage(UPDATED_ITEM_IMAGE)
            .itemImageContentType(UPDATED_ITEM_IMAGE_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .timeCreated(UPDATED_TIME_CREATED)
            .timeModified(UPDATED_TIME_MODIFIED)
            .userIdCreated(UPDATED_USER_ID_CREATED)
            .userIdModified(UPDATED_USER_ID_MODIFIED);

        restItemMockMvc.perform(put("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItem)))
            .andExpect(status().isOk());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
        Item testItem = itemList.get(itemList.size() - 1);
        assertThat(testItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testItem.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testItem.getSkuNum()).isEqualTo(UPDATED_SKU_NUM);
        assertThat(testItem.getVatTax()).isEqualTo(UPDATED_VAT_TAX);
        assertThat(testItem.getImportTax()).isEqualTo(UPDATED_IMPORT_TAX);
        assertThat(testItem.getExportTax()).isEqualTo(UPDATED_EXPORT_TAX);
        assertThat(testItem.getInventoryPriceMethod()).isEqualTo(UPDATED_INVENTORY_PRICE_METHOD);
        assertThat(testItem.getAccountItem()).isEqualTo(UPDATED_ACCOUNT_ITEM);
        assertThat(testItem.isIsAllowModified()).isEqualTo(UPDATED_IS_ALLOW_MODIFIED);
        assertThat(testItem.getAccountCost()).isEqualTo(UPDATED_ACCOUNT_COST);
        assertThat(testItem.getAccountRevenue()).isEqualTo(UPDATED_ACCOUNT_REVENUE);
        assertThat(testItem.getAccountInternalRevenue()).isEqualTo(UPDATED_ACCOUNT_INTERNAL_REVENUE);
        assertThat(testItem.getAccountSaleReturn()).isEqualTo(UPDATED_ACCOUNT_SALE_RETURN);
        assertThat(testItem.getAccountSalePrice()).isEqualTo(UPDATED_ACCOUNT_SALE_PRICE);
        assertThat(testItem.getAccountAgency()).isEqualTo(UPDATED_ACCOUNT_AGENCY);
        assertThat(testItem.getAccountRawProduct()).isEqualTo(UPDATED_ACCOUNT_RAW_PRODUCT);
        assertThat(testItem.getAccountCostDifference()).isEqualTo(UPDATED_ACCOUNT_COST_DIFFERENCE);
        assertThat(testItem.getAccountDiscount()).isEqualTo(UPDATED_ACCOUNT_DISCOUNT);
        assertThat(testItem.getSaleDesc()).isEqualTo(UPDATED_SALE_DESC);
        assertThat(testItem.getPurchaseDesc()).isEqualTo(UPDATED_PURCHASE_DESC);
        assertThat(testItem.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testItem.getLenght()).isEqualTo(UPDATED_LENGHT);
        assertThat(testItem.getWide()).isEqualTo(UPDATED_WIDE);
        assertThat(testItem.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testItem.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testItem.getSpecification()).isEqualTo(UPDATED_SPECIFICATION);
        assertThat(testItem.getItemImage()).isEqualTo(UPDATED_ITEM_IMAGE);
        assertThat(testItem.getItemImageContentType()).isEqualTo(UPDATED_ITEM_IMAGE_CONTENT_TYPE);
        assertThat(testItem.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testItem.getTimeCreated()).isEqualTo(UPDATED_TIME_CREATED);
        assertThat(testItem.getTimeModified()).isEqualTo(UPDATED_TIME_MODIFIED);
        assertThat(testItem.getUserIdCreated()).isEqualTo(UPDATED_USER_ID_CREATED);
        assertThat(testItem.getUserIdModified()).isEqualTo(UPDATED_USER_ID_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingItem() throws Exception {
        int databaseSizeBeforeUpdate = itemRepository.findAll().size();

        // Create the Item

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemMockMvc.perform(put("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isBadRequest());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItem() throws Exception {
        // Initialize the database
        itemService.save(item);

        int databaseSizeBeforeDelete = itemRepository.findAll().size();

        // Delete the item
        restItemMockMvc.perform(delete("/api/items/{id}", item.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
