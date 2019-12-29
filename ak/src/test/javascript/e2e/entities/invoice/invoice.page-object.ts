import { element, by, ElementFinder } from 'protractor';

export class InvoiceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('ak-invoice div table .btn-danger'));
  title = element.all(by.css('ak-invoice div h2#page-heading span')).first();

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

export class InvoiceUpdatePage {
  pageTitle = element(by.id('ak-invoice-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  companyIdInput = element(by.id('field_companyId'));
  invoiceNoInput = element(by.id('field_invoiceNo'));
  invoiceDateInput = element(by.id('field_invoiceDate'));
  dueDateInput = element(by.id('field_dueDate'));
  billingAddressInput = element(by.id('field_billingAddress'));
  accountNumberInput = element(by.id('field_accountNumber'));
  poNumberInput = element(by.id('field_poNumber'));
  notesInput = element(by.id('field_notes'));
  productTotalInput = element(by.id('field_productTotal'));
  vatTotalInput = element(by.id('field_vatTotal'));
  discountTotalInput = element(by.id('field_discountTotal'));
  totalInput = element(by.id('field_total'));
  statusSelect = element(by.id('field_status'));
  timeCreatedInput = element(by.id('field_timeCreated'));
  timeModifiedInput = element(by.id('field_timeModified'));
  userIdCreatedInput = element(by.id('field_userIdCreated'));
  userIdModifiedInput = element(by.id('field_userIdModified'));
  customerSelect = element(by.id('field_customer'));
  termsSelect = element(by.id('field_terms'));
  employeeSelect = element(by.id('field_employee'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCompanyIdInput(companyId) {
    await this.companyIdInput.sendKeys(companyId);
  }

  async getCompanyIdInput() {
    return await this.companyIdInput.getAttribute('value');
  }

  async setInvoiceNoInput(invoiceNo) {
    await this.invoiceNoInput.sendKeys(invoiceNo);
  }

  async getInvoiceNoInput() {
    return await this.invoiceNoInput.getAttribute('value');
  }

  async setInvoiceDateInput(invoiceDate) {
    await this.invoiceDateInput.sendKeys(invoiceDate);
  }

  async getInvoiceDateInput() {
    return await this.invoiceDateInput.getAttribute('value');
  }

  async setDueDateInput(dueDate) {
    await this.dueDateInput.sendKeys(dueDate);
  }

  async getDueDateInput() {
    return await this.dueDateInput.getAttribute('value');
  }

  async setBillingAddressInput(billingAddress) {
    await this.billingAddressInput.sendKeys(billingAddress);
  }

  async getBillingAddressInput() {
    return await this.billingAddressInput.getAttribute('value');
  }

  async setAccountNumberInput(accountNumber) {
    await this.accountNumberInput.sendKeys(accountNumber);
  }

  async getAccountNumberInput() {
    return await this.accountNumberInput.getAttribute('value');
  }

  async setPoNumberInput(poNumber) {
    await this.poNumberInput.sendKeys(poNumber);
  }

  async getPoNumberInput() {
    return await this.poNumberInput.getAttribute('value');
  }

  async setNotesInput(notes) {
    await this.notesInput.sendKeys(notes);
  }

  async getNotesInput() {
    return await this.notesInput.getAttribute('value');
  }

  async setProductTotalInput(productTotal) {
    await this.productTotalInput.sendKeys(productTotal);
  }

  async getProductTotalInput() {
    return await this.productTotalInput.getAttribute('value');
  }

  async setVatTotalInput(vatTotal) {
    await this.vatTotalInput.sendKeys(vatTotal);
  }

  async getVatTotalInput() {
    return await this.vatTotalInput.getAttribute('value');
  }

  async setDiscountTotalInput(discountTotal) {
    await this.discountTotalInput.sendKeys(discountTotal);
  }

  async getDiscountTotalInput() {
    return await this.discountTotalInput.getAttribute('value');
  }

  async setTotalInput(total) {
    await this.totalInput.sendKeys(total);
  }

  async getTotalInput() {
    return await this.totalInput.getAttribute('value');
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

  async customerSelectLastOption() {
    await this.customerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async customerSelectOption(option) {
    await this.customerSelect.sendKeys(option);
  }

  getCustomerSelect(): ElementFinder {
    return this.customerSelect;
  }

  async getCustomerSelectedOption() {
    return await this.customerSelect.element(by.css('option:checked')).getText();
  }

  async termsSelectLastOption() {
    await this.termsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async termsSelectOption(option) {
    await this.termsSelect.sendKeys(option);
  }

  getTermsSelect(): ElementFinder {
    return this.termsSelect;
  }

  async getTermsSelectedOption() {
    return await this.termsSelect.element(by.css('option:checked')).getText();
  }

  async employeeSelectLastOption() {
    await this.employeeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async employeeSelectOption(option) {
    await this.employeeSelect.sendKeys(option);
  }

  getEmployeeSelect(): ElementFinder {
    return this.employeeSelect;
  }

  async getEmployeeSelectedOption() {
    return await this.employeeSelect.element(by.css('option:checked')).getText();
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

export class InvoiceDeleteDialog {
  private dialogTitle = element(by.id('ak-delete-invoice-heading'));
  private confirmButton = element(by.id('ak-confirm-delete-invoice'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
