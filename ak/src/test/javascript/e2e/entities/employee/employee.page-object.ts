import { element, by, ElementFinder } from 'protractor';

export class EmployeeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('ak-employee div table .btn-danger'));
  title = element.all(by.css('ak-employee div h2#page-heading span')).first();

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

export class EmployeeUpdatePage {
  pageTitle = element(by.id('ak-employee-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  companyIdInput = element(by.id('field_companyId'));
  codeInput = element(by.id('field_code'));
  fullNameInput = element(by.id('field_fullName'));
  sexInput = element(by.id('field_sex'));
  birthdayInput = element(by.id('field_birthday'));
  identityCardInput = element(by.id('field_identityCard'));
  identityDateInput = element(by.id('field_identityDate'));
  identityIssueInput = element(by.id('field_identityIssue'));
  positionInput = element(by.id('field_position'));
  taxCodeInput = element(by.id('field_taxCode'));
  salaryInput = element(by.id('field_salary'));
  salaryRateInput = element(by.id('field_salaryRate'));
  salarySecurityInput = element(by.id('field_salarySecurity'));
  numOfDependsInput = element(by.id('field_numOfDepends'));
  phoneInput = element(by.id('field_phone'));
  mobileInput = element(by.id('field_mobile'));
  emailInput = element(by.id('field_email'));
  bankAccountInput = element(by.id('field_bankAccount'));
  bankNameInput = element(by.id('field_bankName'));
  nodesInput = element(by.id('field_nodes'));
  isActiveInput = element(by.id('field_isActive'));
  timeCreatedInput = element(by.id('field_timeCreated'));
  timeModifiedInput = element(by.id('field_timeModified'));
  userIdCreatedInput = element(by.id('field_userIdCreated'));
  userIdModifiedInput = element(by.id('field_userIdModified'));
  departmentSelect = element(by.id('field_department'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCompanyIdInput(companyId) {
    await this.companyIdInput.sendKeys(companyId);
  }

  async getCompanyIdInput() {
    return await this.companyIdInput.getAttribute('value');
  }

  async setCodeInput(code) {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput() {
    return await this.codeInput.getAttribute('value');
  }

  async setFullNameInput(fullName) {
    await this.fullNameInput.sendKeys(fullName);
  }

  async getFullNameInput() {
    return await this.fullNameInput.getAttribute('value');
  }

  async setSexInput(sex) {
    await this.sexInput.sendKeys(sex);
  }

  async getSexInput() {
    return await this.sexInput.getAttribute('value');
  }

  async setBirthdayInput(birthday) {
    await this.birthdayInput.sendKeys(birthday);
  }

  async getBirthdayInput() {
    return await this.birthdayInput.getAttribute('value');
  }

  async setIdentityCardInput(identityCard) {
    await this.identityCardInput.sendKeys(identityCard);
  }

  async getIdentityCardInput() {
    return await this.identityCardInput.getAttribute('value');
  }

  async setIdentityDateInput(identityDate) {
    await this.identityDateInput.sendKeys(identityDate);
  }

  async getIdentityDateInput() {
    return await this.identityDateInput.getAttribute('value');
  }

  async setIdentityIssueInput(identityIssue) {
    await this.identityIssueInput.sendKeys(identityIssue);
  }

  async getIdentityIssueInput() {
    return await this.identityIssueInput.getAttribute('value');
  }

  async setPositionInput(position) {
    await this.positionInput.sendKeys(position);
  }

  async getPositionInput() {
    return await this.positionInput.getAttribute('value');
  }

  async setTaxCodeInput(taxCode) {
    await this.taxCodeInput.sendKeys(taxCode);
  }

  async getTaxCodeInput() {
    return await this.taxCodeInput.getAttribute('value');
  }

  async setSalaryInput(salary) {
    await this.salaryInput.sendKeys(salary);
  }

  async getSalaryInput() {
    return await this.salaryInput.getAttribute('value');
  }

  async setSalaryRateInput(salaryRate) {
    await this.salaryRateInput.sendKeys(salaryRate);
  }

  async getSalaryRateInput() {
    return await this.salaryRateInput.getAttribute('value');
  }

  async setSalarySecurityInput(salarySecurity) {
    await this.salarySecurityInput.sendKeys(salarySecurity);
  }

  async getSalarySecurityInput() {
    return await this.salarySecurityInput.getAttribute('value');
  }

  async setNumOfDependsInput(numOfDepends) {
    await this.numOfDependsInput.sendKeys(numOfDepends);
  }

  async getNumOfDependsInput() {
    return await this.numOfDependsInput.getAttribute('value');
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

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return await this.emailInput.getAttribute('value');
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

  async setNodesInput(nodes) {
    await this.nodesInput.sendKeys(nodes);
  }

  async getNodesInput() {
    return await this.nodesInput.getAttribute('value');
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

  async departmentSelectLastOption() {
    await this.departmentSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async departmentSelectOption(option) {
    await this.departmentSelect.sendKeys(option);
  }

  getDepartmentSelect(): ElementFinder {
    return this.departmentSelect;
  }

  async getDepartmentSelectedOption() {
    return await this.departmentSelect.element(by.css('option:checked')).getText();
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

export class EmployeeDeleteDialog {
  private dialogTitle = element(by.id('ak-delete-employee-heading'));
  private confirmButton = element(by.id('ak-confirm-delete-employee'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
