package com.ak.web.rest;

import com.ak.AkApp;
import com.ak.domain.Unit;
import com.ak.repository.UnitRepository;
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
 * Integration tests for the {@link UnitResource} REST controller.
 */
@SpringBootTest(classes = AkApp.class)
public class UnitResourceIT {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private UnitRepository unitRepository;

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

    private MockMvc restUnitMockMvc;

    private Unit unit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnitResource unitResource = new UnitResource(unitRepository);
        this.restUnitMockMvc = MockMvcBuilders.standaloneSetup(unitResource)
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
    public static Unit createEntity(EntityManager em) {
        Unit unit = new Unit()
            .companyId(DEFAULT_COMPANY_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return unit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unit createUpdatedEntity(EntityManager em) {
        Unit unit = new Unit()
            .companyId(UPDATED_COMPANY_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return unit;
    }

    @BeforeEach
    public void initTest() {
        unit = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnit() throws Exception {
        int databaseSizeBeforeCreate = unitRepository.findAll().size();

        // Create the Unit
        restUnitMockMvc.perform(post("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isCreated());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate + 1);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUnit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUnit.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unitRepository.findAll().size();

        // Create the Unit with an existing ID
        unit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitMockMvc.perform(post("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setName(null);

        // Create the Unit, which fails.

        restUnitMockMvc.perform(post("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnits() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList
        restUnitMockMvc.perform(get("/api/units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unit.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get the unit
        restUnitMockMvc.perform(get("/api/units/{id}", unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unit.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUnit() throws Exception {
        // Get the unit
        restUnitMockMvc.perform(get("/api/units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Update the unit
        Unit updatedUnit = unitRepository.findById(unit.getId()).get();
        // Disconnect from session so that the updates on updatedUnit are not directly saved in db
        em.detach(updatedUnit);
        updatedUnit
            .companyId(UPDATED_COMPANY_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);

        restUnitMockMvc.perform(put("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnit)))
            .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUnit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUnit.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Create the Unit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitMockMvc.perform(put("/api/units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        int databaseSizeBeforeDelete = unitRepository.findAll().size();

        // Delete the unit
        restUnitMockMvc.perform(delete("/api/units/{id}", unit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
