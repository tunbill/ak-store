import { element, by, ElementFinder } from 'protractor';

export class InvoiceLineComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('ak-invoice-line div table .btn-danger'));
  title = element.all(by.css('ak-invoice-line div h2#page-heading span')).first();

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

export class InvoiceLineUpdatePage {
  pageTitle = element(by.id('ak-invoice-line-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  companyIdInput = element(by.id('field_companyId'));
  displayOrderInput = element(by.id('field_displayOrder'));
  itemNameInput = element(by.id('field_itemName'));
  unitNameInput = element(by.id('field_unitName'));
  quantityInput = element(by.id('field_quantity'));
  rateInput = element(by.id('field_rate'));
  amountInput = element(by.id('field_amount'));
  discountPctInput = element(by.id('field_discountPct'));
  accountNumberInput = element(by.id('field_accountNumber'));
  statusSelect = element(by.id('field_status'));
  invoiceSelect = element(by.id('field_invoice'));
  itemSelect = element(by.id('field_item'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCompanyIdInput(companyId) {
    await this.companyIdInput.sendKeys(companyId);
  }

  async getCompanyIdInput() {
    return await this.companyIdInput.getAttribute('value');
  }

  async setDisplayOrderInput(displayOrder) {
    await this.displayOrderInput.sendKeys(displayOrder);
  }

  async getDisplayOrderInput() {
    return await this.displayOrderInput.getAttribute('value');
  }

  async setItemNameInput(itemName) {
    await this.itemNameInput.sendKeys(itemName);
  }

  async getItemNameInput() {
    return await this.itemNameInput.getAttribute('value');
  }

  async setUnitNameInput(unitName) {
    await this.unitNameInput.sendKeys(unitName);
  }

  async getUnitNameInput() {
    return await this.unitNameInput.getAttribute('value');
  }

  async setQuantityInput(quantity) {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput() {
    return await this.quantityInput.getAttribute('value');
  }

  async setRateInput(rate) {
    await this.rateInput.sendKeys(rate);
  }

  async getRateInput() {
    return await this.rateInput.getAttribute('value');
  }

  async setAmountInput(amount) {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput() {
    return await this.amountInput.getAttribute('value');
  }

  async setDiscountPctInput(discountPct) {
    await this.discountPctInput.sendKeys(discountPct);
  }

  async getDiscountPctInput() {
    return await this.discountPctInput.getAttribute('value');
  }

  async setAccountNumberInput(accountNumber) {
    await this.accountNumberInput.sendKeys(accountNumber);
  }

  async getAccountNumberInput() {
    return await this.accountNumberInput.getAttribute('value');
  }

  async setStatusSelect(status) {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect() {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption() {
    await this.statusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async invoiceSelectLastOption() {
    await this.invoiceSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async invoiceSelectOption(option) {
    await this.invoiceSelect.sendKeys(option);
  }

  getInvoiceSelect(): ElementFinder {
    return this.invoiceSelect;
  }

  async getInvoiceSelectedOption() {
    return await this.invoiceSelect.element(by.css('option:checked')).getText();
  }

  async itemSelectLastOption() {
    await this.itemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async itemSelectOption(option) {
    await this.itemSelect.sendKeys(option);
  }

  getItemSelect(): ElementFinder {
    return this.itemSelect;
  }

  async getItemSelectedOption() {
    return await this.itemSelect.element(by.css('option:checked')).getText();
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

export class InvoiceLineDeleteDialog {
  private dialogTitle = element(by.id('ak-delete-invoiceLine-heading'));
  private confirmButton = element(by.id('ak-confirm-delete-invoiceLine'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
