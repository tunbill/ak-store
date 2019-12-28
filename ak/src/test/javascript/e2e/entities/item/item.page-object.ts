import { element, by, ElementFinder } from 'protractor';

export class ItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('ak-item div table .btn-danger'));
  title = element.all(by.css('ak-item div h2#page-heading span')).first();

  async clickOnCreateButton() {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton() {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ItemUpdatePage {
  pageTitle = element(by.id('ak-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codeInput = element(by.id('field_code'));
  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  typeSelect = element(by.id('field_type'));
  skuNumInput = element(by.id('field_skuNum'));
  vatTaxSelect = element(by.id('field_vatTax'));
  importTaxInput = element(by.id('field_importTax'));
  exportTaxInput = element(by.id('field_exportTax'));
  inventoryPriceMethodSelect = element(by.id('field_inventoryPriceMethod'));
  accountItemInput = element(by.id('field_accountItem'));
  isAllowModifiedInput = element(by.id('field_isAllowModified'));
  accountCostInput = element(by.id('field_accountCost'));
  accountRevenueInput = element(by.id('field_accountRevenue'));
  accountInternalRevenueInput = element(by.id('field_accountInternalRevenue'));
  accountSaleReturnInput = element(by.id('field_accountSaleReturn'));
  accountSalePriceInput = element(by.id('field_accountSalePrice'));
  accountAgencyInput = element(by.id('field_accountAgency'));
  accountRawProductInput = element(by.id('field_accountRawProduct'));
  accountCostDifferenceInput = element(by.id('field_accountCostDifference'));
  accountDiscountInput = element(by.id('field_accountDiscount'));
  saleDescInput = element(by.id('field_saleDesc'));
  purchaseDescInput = element(by.id('field_purchaseDesc'));
  weightInput = element(by.id('field_weight'));
  lenghtInput = element(by.id('field_lenght'));
  wideInput = element(by.id('field_wide'));
  heightInput = element(by.id('field_height'));
  colorInput = element(by.id('field_color'));
  specificationInput = element(by.id('field_specification'));
  itemImageInput = element(by.id('file_itemImage'));
  isActiveInput = element(by.id('field_isActive'));
  timeCreatedInput = element(by.id('field_timeCreated'));
  timeModifiedInput = element(by.id('field_timeModified'));
  userIdCreatedInput = element(by.id('field_userIdCreated'));
  userIdModifiedInput = element(by.id('field_userIdModified'));
  unitSelect = element(by.id('field_unit'));
  itemGroupSelect = element(by.id('field_itemGroup'));
  storeSelect = element(by.id('field_store'));
  companySelect = element(by.id('field_company'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodeInput(code) {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput() {
    return await this.codeInput.getAttribute('value');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setTypeSelect(type) {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect() {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption() {
    await this.typeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setSkuNumInput(skuNum) {
    await this.skuNumInput.sendKeys(skuNum);
  }

  async getSkuNumInput() {
    return await this.skuNumInput.getAttribute('value');
  }

  async setVatTaxSelect(vatTax) {
    await this.vatTaxSelect.sendKeys(vatTax);
  }

  async getVatTaxSelect() {
    return await this.vatTaxSelect.element(by.css('option:checked')).getText();
  }

  async vatTaxSelectLastOption() {
    await this.vatTaxSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setImportTaxInput(importTax) {
    await this.importTaxInput.sendKeys(importTax);
  }

  async getImportTaxInput() {
    return await this.importTaxInput.getAttribute('value');
  }

  async setExportTaxInput(exportTax) {
    await this.exportTaxInput.sendKeys(exportTax);
  }

  async getExportTaxInput() {
    return await this.exportTaxInput.getAttribute('value');
  }

  async setInventoryPriceMethodSelect(inventoryPriceMethod) {
    await this.inventoryPriceMethodSelect.sendKeys(inventoryPriceMethod);
  }

  async getInventoryPriceMethodSelect() {
    return await this.inventoryPriceMethodSelect.element(by.css('option:checked')).getText();
  }

  async inventoryPriceMethodSelectLastOption() {
    await this.inventoryPriceMethodSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setAccountItemInput(accountItem) {
    await this.accountItemInput.sendKeys(accountItem);
  }

  async getAccountItemInput() {
    return await this.accountItemInput.getAttribute('value');
  }

  getIsAllowModifiedInput() {
    return this.isAllowModifiedInput;
  }
  async setAccountCostInput(accountCost) {
    await this.accountCostInput.sendKeys(accountCost);
  }

  async getAccountCostInput() {
    return await this.accountCostInput.getAttribute('value');
  }

  async setAccountRevenueInput(accountRevenue) {
    await this.accountRevenueInput.sendKeys(accountRevenue);
  }

  async getAccountRevenueInput() {
    return await this.accountRevenueInput.getAttribute('value');
  }

  async setAccountInternalRevenueInput(accountInternalRevenue) {
    await this.accountInternalRevenueInput.sendKeys(accountInternalRevenue);
  }

  async getAccountInternalRevenueInput() {
    return await this.accountInternalRevenueInput.getAttribute('value');
  }

  async setAccountSaleReturnInput(accountSaleReturn) {
    await this.accountSaleReturnInput.sendKeys(accountSaleReturn);
  }

  async getAccountSaleReturnInput() {
    return await this.accountSaleReturnInput.getAttribute('value');
  }

  async setAccountSalePriceInput(accountSalePrice) {
    await this.accountSalePriceInput.sendKeys(accountSalePrice);
  }

  async getAccountSalePriceInput() {
    return await this.accountSalePriceInput.getAttribute('value');
  }

  async setAccountAgencyInput(accountAgency) {
    await this.accountAgencyInput.sendKeys(accountAgency);
  }

  async getAccountAgencyInput() {
    return await this.accountAgencyInput.getAttribute('value');
  }

  async setAccountRawProductInput(accountRawProduct) {
    await this.accountRawProductInput.sendKeys(accountRawProduct);
  }

  async getAccountRawProductInput() {
    return await this.accountRawProductInput.getAttribute('value');
  }

  async setAccountCostDifferenceInput(accountCostDifference) {
    await this.accountCostDifferenceInput.sendKeys(accountCostDifference);
  }

  async getAccountCostDifferenceInput() {
    return await this.accountCostDifferenceInput.getAttribute('value');
  }

  async setAccountDiscountInput(accountDiscount) {
    await this.accountDiscountInput.sendKeys(accountDiscount);
  }

  async getAccountDiscountInput() {
    return await this.accountDiscountInput.getAttribute('value');
  }

  async setSaleDescInput(saleDesc) {
    await this.saleDescInput.sendKeys(saleDesc);
  }

  async getSaleDescInput() {
    return await this.saleDescInput.getAttribute('value');
  }

  async setPurchaseDescInput(purchaseDesc) {
    await this.purchaseDescInput.sendKeys(purchaseDesc);
  }

  async getPurchaseDescInput() {
    return await this.purchaseDescInput.getAttribute('value');
  }

  async setWeightInput(weight) {
    await this.weightInput.sendKeys(weight);
  }

  async getWeightInput() {
    return await this.weightInput.getAttribute('value');
  }

  async setLenghtInput(lenght) {
    await this.lenghtInput.sendKeys(lenght);
  }

  async getLenghtInput() {
    return await this.lenghtInput.getAttribute('value');
  }

  async setWideInput(wide) {
    await this.wideInput.sendKeys(wide);
  }

  async getWideInput() {
    return await this.wideInput.getAttribute('value');
  }

  async setHeightInput(height) {
    await this.heightInput.sendKeys(height);
  }

  async getHeightInput() {
    return await this.heightInput.getAttribute('value');
  }

  async setColorInput(color) {
    await this.colorInput.sendKeys(color);
  }

  async getColorInput() {
    return await this.colorInput.getAttribute('value');
  }

  async setSpecificationInput(specification) {
    await this.specificationInput.sendKeys(specification);
  }

  async getSpecificationInput() {
    return await this.specificationInput.getAttribute('value');
  }

  async setItemImageInput(itemImage) {
    await this.itemImageInput.sendKeys(itemImage);
  }

  async getItemImageInput() {
    return await this.itemImageInput.getAttribute('value');
  }

  getIsActiveInput() {
    return this.isActiveInput;
  }
  async setTimeCreatedInput(timeCreated) {
    await this.timeCreatedInput.sendKeys(timeCreated);
  }

  async getTimeCreatedInput() {
    return await this.timeCreatedInput.getAttribute('value');
  }

  async setTimeModifiedInput(timeModified) {
    await this.timeModifiedInput.sendKeys(timeModified);
  }

  async getTimeModifiedInput() {
    return await this.timeModifiedInput.getAttribute('value');
  }

  async setUserIdCreatedInput(userIdCreated) {
    await this.userIdCreatedInput.sendKeys(userIdCreated);
  }

  async getUserIdCreatedInput() {
    return await this.userIdCreatedInput.getAttribute('value');
  }

  async setUserIdModifiedInput(userIdModified) {
    await this.userIdModifiedInput.sendKeys(userIdModified);
  }

  async getUserIdModifiedInput() {
    return await this.userIdModifiedInput.getAttribute('value');
  }

  async unitSelectLastOption() {
    await this.unitSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async unitSelectOption(option) {
    await this.unitSelect.sendKeys(option);
  }

  getUnitSelect(): ElementFinder {
    return this.unitSelect;
  }

  async getUnitSelectedOption() {
    return await this.unitSelect.element(by.css('option:checked')).getText();
  }

  async itemGroupSelectLastOption() {
    await this.itemGroupSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async itemGroupSelectOption(option) {
    await this.itemGroupSelect.sendKeys(option);
  }

  getItemGroupSelect(): ElementFinder {
    return this.itemGroupSelect;
  }

  async getItemGroupSelectedOption() {
    return await this.itemGroupSelect.element(by.css('option:checked')).getText();
  }

  async storeSelectLastOption() {
    await this.storeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async storeSelectOption(option) {
    await this.storeSelect.sendKeys(option);
  }

  getStoreSelect(): ElementFinder {
    return this.storeSelect;
  }

  async getStoreSelectedOption() {
    return await this.storeSelect.element(by.css('option:checked')).getText();
  }

  async companySelectLastOption() {
    await this.companySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async companySelectOption(option) {
    await this.companySelect.sendKeys(option);
  }

  getCompanySelect(): ElementFinder {
    return this.companySelect;
  }

  async getCompanySelectedOption() {
    return await this.companySelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ItemDeleteDialog {
  private dialogTitle = element(by.id('ak-delete-item-heading'));
  private confirmButton = element(by.id('ak-confirm-delete-item'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
