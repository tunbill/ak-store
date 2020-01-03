package com.ak.web.rest;

import com.ak.domain.ItemGroup;
import com.ak.domain.User;
import com.ak.security.SecurityUtils;
import com.ak.service.ItemGroupService;
import com.ak.service.UserService;
import com.ak.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ak.domain.ItemGroup}.
 */
@RestController
@RequestMapping("/api")
public class ItemGroupResource {

    private final Logger log = LoggerFactory.getLogger(ItemGroupResource.class);

    private static final String ENTITY_NAME = "itemGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemGroupService itemGroupService;
    
    private final UserService userService;

    public ItemGroupResource(ItemGroupService itemGroupService, UserService userService) {
        this.itemGroupService = itemGroupService;
        this.userService = userService;
    }

    /**
     * {@code POST  /item-groups} : Create a new itemGroup.
     *
     * @param itemGroup the itemGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemGroup, or with status {@code 400 (Bad Request)} if the itemGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-groups")
    public ResponseEntity<ItemGroup> createItemGroup(@Valid @RequestBody ItemGroup itemGroup) throws URISyntaxException {
        log.debug("REST request to save ItemGroup : {}", itemGroup);
        if (itemGroup.getId() != null) {
            throw new BadRequestAlertException("A new itemGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<String>  login = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(login.get());
        itemGroup.setCompanyId(user.get().getCompanyId());
        ItemGroup result = itemGroupService.save(itemGroup);
        return ResponseEntity.created(new URI("/api/item-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-groups} : Updates an existing itemGroup.
     *
     * @param itemGroup the itemGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemGroup,
     * or with status {@code 400 (Bad Request)} if the itemGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-groups")
    public ResponseEntity<ItemGroup> updateItemGroup(@Valid @RequestBody ItemGroup itemGroup) throws URISyntaxException {
        log.debug("REST request to update ItemGroup : {}", itemGroup);
        if (itemGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<String>  login = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(login.get());
        itemGroup.setCompanyId(user.get().getCompanyId());
        ItemGroup result = itemGroupService.save(itemGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-groups} : get all the itemGroups.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemGroups in body.
     */
    @GetMapping("/item-groups")
    public ResponseEntity<List<ItemGroup>> getAllItemGroups(Pageable pageable) {
        log.debug("REST request to get a page of ItemGroups");
        Optional<String>  login = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(login.get());
        Page<ItemGroup> page = itemGroupService.findByCompanyId(user.get().getCompanyId(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-groups/:id} : get the "id" itemGroup.
     *
     * @param id the id of the itemGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-groups/{id}")
    public ResponseEntity<ItemGroup> getItemGroup(@PathVariable Long id) {
        log.debug("REST request to get ItemGroup : {}", id);
        Optional<ItemGroup> itemGroup = itemGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemGroup);
    }

    /**
     * {@code DELETE  /item-groups/:id} : delete the "id" itemGroup.
     *
     * @param id the id of the itemGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-groups/{id}")
    public ResponseEntity<Void> deleteItemGroup(@PathVariable Long id) {
        log.debug("REST request to delete ItemGroup : {}", id);
        itemGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
