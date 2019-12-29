import { element, by, ElementFinder } from 'protractor';

export class CustomerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('ak-customer div table .btn-danger'));
  title = element.all(by.css('ak-customer div h2#page-heading span')).first();

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

export class CustomerUpdatePage {
  pageTitle = element(by.id('ak-customer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  companyIdInput = element(by.id('field_companyId'));
  isVendorInput = element(by.id('field_isVendor'));
  vendorIdInput = element(by.id('field_vendorId'));
  codeInput = element(by.id('field_code'));
  companyNameInput = element(by.id('field_companyName'));
  addressInput = element(by.id('field_address'));
  phoneInput = element(by.id('field_phone'));
  mobileInput = element(by.id('field_mobile'));
  faxInput = element(by.id('field_fax'));
  emailInput = element(by.id('field_email'));
  taxCodeInput = element(by.id('field_taxCode'));
  accountNumberInput = element(by.id('field_accountNumber'));
  bankAccountInput = element(by.id('field_bankAccount'));
  bankNameInput = element(by.id('field_bankName'));
  balanceInput = element(by.id('field_balance'));
  totalBalanceInput = element(by.id('field_totalBalance'));
  openBalanceInput = element(by.id('field_openBalance'));
  openBalanceDateInput = element(by.id('field_openBalanceDate'));
  creditLimitInput = element(by.id('field_creditLimit'));
  notesInput = element(by.id('field_notes'));
  contactNameInput = element(by.id('field_contactName'));
  isActiveInput = element(by.id('field_isActive'));
  timeCreatedInput = element(by.id('field_timeCreated'));
  timeModifiedInput = element(by.id('field_timeModified'));
  userIdCreatedInput = element(by.id('field_userIdCreated'));
  userIdModifiedInput = element(by.id('field_userIdModified'));
  customerTypeSelect = element(by.id('field_customerType'));
  termsSelect = element(by.id('field_terms'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCompanyIdInput(companyId) {
    await this.companyIdInput.sendKeys(companyId);
  }

  async getCompanyIdInput() {
    return await this.companyIdInput.getAttribute('value');
  }

  getIsVendorInput() {
    return this.isVendorInput;
  }
  async setVendorIdInput(vendorId) {
    await this.vendorIdInput.sendKeys(vendorId);
  }

  async getVendorIdInput() {
    return await this.vendorIdInput.getAttribute('value');
  }

  async setCodeInput(code) {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput() {
    return await this.codeInput.getAttribute('value');
  }

  async setCompanyNameInput(companyName) {
    await this.companyNameInput.sendKeys(companyName);
  }

  async getCompanyNameInput() {
    return await this.companyNameInput.getAttribute('value');
  }

  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return await this.addressInput.getAttribute('value');
  }

  async setPhoneInput(phone) {
    await this.phoneInput.sendKeys(phone);
  }

  async getPhoneInput() {
    return await this.phoneInput.getAttribute('value');
  }

  async setMobileInput(mobile) {
    await this.mobileInput.sendKeys(mobile);
  }

  async getMobileInput() {
    return await this.mobileInput.getAttribute('value');
  }

  async setFaxInput(fax) {
    await this.faxInput.sendKeys(fax);
  }

  async getFaxInput() {
    return await this.faxInput.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return await this.emailInput.getAttribute('value');
  }

  async setTaxCodeInput(taxCode) {
    await this.taxCodeInput.sendKeys(taxCode);
  }

  async getTaxCodeInput() {
    return await this.taxCodeInput.getAttribute('value');
  }

  async setAccountNumberInput(accountNumber) {
    await this.accountNumberInput.sendKeys(accountNumber);
  }

  async getAccountNumberInput() {
    return await this.accountNumberInput.getAttribute('value');
  }

  async setBankAccountInput(bankAccount) {
    await this.bankAccountInput.sendKeys(bankAccount);
  }

  async getBankAccountInput() {
    return await this.bankAccountInput.getAttribute('value');
  }

  async setBankNameInput(bankName) {
    await this.bankNameInput.sendKeys(bankName);
  }

  async getBankNameInput() {
    return await this.bankNameInput.getAttribute('value');
  }

  async setBalanceInput(balance) {
    await this.balanceInput.sendKeys(balance);
  }

  async getBalanceInput() {
    return await this.balanceInput.getAttribute('value');
  }

  async setTotalBalanceInput(totalBalance) {
    await this.totalBalanceInput.sendKeys(totalBalance);
  }

  async getTotalBalanceInput() {
    return await this.totalBalanceInput.getAttribute('value');
  }

  async setOpenBalanceInput(openBalance) {
    await this.openBalanceInput.sendKeys(openBalance);
  }

  async getOpenBalanceInput() {
    return await this.openBalanceInput.getAttribute('value');
  }

  async setOpenBalanceDateInput(openBalanceDate) {
    await this.openBalanceDateInput.sendKeys(openBalanceDate);
  }

  async getOpenBalanceDateInput() {
    return await this.openBalanceDateInput.getAttribute('value');
  }

  async setCreditLimitInput(creditLimit) {
    await this.creditLimitInput.sendKeys(creditLimit);
  }

  async getCreditLimitInput() {
    return await this.creditLimitInput.getAttribute('value');
  }

  async setNotesInput(notes) {
    await this.notesInput.sendKeys(notes);
  }

  async getNotesInput() {
    return await this.notesInput.getAttribute('value');
  }

  async setContactNameInput(contactName) {
    await this.contactNameInput.sendKeys(contactName);
  }

  async getContactNameInput() {
    return await this.contactNameInput.getAttribute('value');
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

  async customerTypeSelectLastOption() {
    await this.customerTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async customerTypeSelectOption(option) {
    await this.customerTypeSelect.sendKeys(option);
  }

  getCustomerTypeSelect(): ElementFinder {
    return this.customerTypeSelect;
  }

  async getCustomerTypeSelectedOption() {
    return await this.customerTypeSelect.element(by.css('option:checked')).getText();
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

export class CustomerDeleteDialog {
  private dialogTitle = element(by.id('ak-delete-customer-heading'));
  private confirmButton = element(by.id('ak-confirm-delete-customer'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
