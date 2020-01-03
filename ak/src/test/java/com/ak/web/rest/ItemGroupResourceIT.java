package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.ItemGroup;
import com.ak.repository.ItemGroupRepository;
import com.ak.service.ItemGroupService;
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
 * Integration tests for the {@link ItemGroupResource} REST controller.
 */
@SpringBootTest(classes = AkApp.class)
public class ItemGroupResourceIT {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private ItemGroupRepository itemGroupRepository;

    @Autowired
    private ItemGroupService itemGroupService;

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

    private MockMvc restItemGroupMockMvc;

    private ItemGroup itemGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemGroupResource itemGroupResource = new ItemGroupResource(itemGroupService);
        this.restItemGroupMockMvc = MockMvcBuilders.standaloneSetup(itemGroupResource)
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
    public static ItemGroup createEntity(EntityManager em) {
        ItemGroup itemGroup = new ItemGroup()
            .companyId(DEFAULT_COMPANY_ID)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return itemGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemGroup createUpdatedEntity(EntityManager em) {
        ItemGroup itemGroup = new ItemGroup()
            .companyId(UPDATED_COMPANY_ID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return itemGroup;
    }

    @BeforeEach
    public void initTest() {
        itemGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemGroup() throws Exception {
        int databaseSizeBeforeCreate = itemGroupRepository.findAll().size();

        // Create the ItemGroup
        restItemGroupMockMvc.perform(post("/api/item-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemGroup)))
            .andExpect(status().isCreated());

        // Validate the ItemGroup in the database
        List<ItemGroup> itemGroupList = itemGroupRepository.findAll();
        assertThat(itemGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ItemGroup testItemGroup = itemGroupList.get(itemGroupList.size() - 1);
        assertThat(testItemGroup.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testItemGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testItemGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItemGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testItemGroup.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createItemGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemGroupRepository.findAll().size();

        // Create the ItemGroup with an existing ID
        itemGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemGroupMockMvc.perform(post("/api/item-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemGroup)))
            .andExpect(status().isBadRequest());

        // Validate the ItemGroup in the database
        List<ItemGroup> itemGroupList = itemGroupRepository.findAll();
        assertThat(itemGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemGroupRepository.findAll().size();
        // set the field null
        itemGroup.setName(null);

        // Create the ItemGroup, which fails.

        restItemGroupMockMvc.perform(post("/api/item-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemGroup)))
            .andExpect(status().isBadRequest());

        List<ItemGroup> itemGroupList = itemGroupRepository.findAll();
        assertThat(itemGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemGroups() throws Exception {
        // Initialize the database
        itemGroupRepository.saveAndFlush(itemGroup);

        // Get all the itemGroupList
        restItemGroupMockMvc.perform(get("/api/item-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getItemGroup() throws Exception {
        // Initialize the database
        itemGroupRepository.saveAndFlush(itemGroup);

        // Get the itemGroup
        restItemGroupMockMvc.perform(get("/api/item-groups/{id}", itemGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemGroup.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemGroup() throws Exception {
        // Get the itemGroup
        restItemGroupMockMvc.perform(get("/api/item-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemGroup() throws Exception {
        // Initialize the database
        itemGroupService.save(itemGroup);

        int databaseSizeBeforeUpdate = itemGroupRepository.findAll().size();

        // Update the itemGroup
        ItemGroup updatedItemGroup = itemGroupRepository.findById(itemGroup.getId()).get();
        // Disconnect from session so that the updates on updatedItemGroup are not directly saved in db
        em.detach(updatedItemGroup);
        updatedItemGroup
            .companyId(UPDATED_COMPANY_ID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);

        restItemGroupMockMvc.perform(put("/api/item-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemGroup)))
            .andExpect(status().isOk());

        // Validate the ItemGroup in the database
        List<ItemGroup> itemGroupList = itemGroupRepository.findAll();
        assertThat(itemGroupList).hasSize(databaseSizeBeforeUpdate);
        ItemGroup testItemGroup = itemGroupList.get(itemGroupList.size() - 1);
        assertThat(testItemGroup.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testItemGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testItemGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItemGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testItemGroup.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingItemGroup() throws Exception {
        int databaseSizeBeforeUpdate = itemGroupRepository.findAll().size();

        // Create the ItemGroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemGroupMockMvc.perform(put("/api/item-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemGroup)))
            .andExpect(status().isBadRequest());

        // Validate the ItemGroup in the database
        List<ItemGroup> itemGroupList = itemGroupRepository.findAll();
        assertThat(itemGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemGroup() throws Exception {
        // Initialize the database
        itemGroupService.save(itemGroup);

        int databaseSizeBeforeDelete = itemGroupRepository.findAll().size();

        // Delete the itemGroup
        restItemGroupMockMvc.perform(delete("/api/item-groups/{id}", itemGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemGroup> itemGroupList = itemGroupRepository.findAll();
        assertThat(itemGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
