import { element, by, ElementFinder } from 'protractor';

export class CompanyComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('ak-company div table .btn-danger'));
  title = element.all(by.css('ak-company div h2#page-heading span')).first();

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

export class CompanyUpdatePage {
  pageTitle = element(by.id('ak-company-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  addressInput = element(by.id('field_address'));
  logoInput = element(by.id('file_logo'));
  emailInput = element(by.id('field_email'));
  startDateInput = element(by.id('field_startDate'));
  numOfUsersInput = element(by.id('field_numOfUsers'));
  typeInput = element(by.id('field_type'));
  isActiveInput = element(by.id('field_isActive'));
  timeCreatedInput = element(by.id('field_timeCreated'));
  timeModifiedInput = element(by.id('field_timeModified'));
  userIdInput = element(by.id('field_userId'));
  industrySelect = element(by.id('field_industry'));
  provinceSelect = element(by.id('field_province'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return await this.addressInput.getAttribute('value');
  }

  async setLogoInput(logo) {
    await this.logoInput.sendKeys(logo);
  }

  async getLogoInput() {
    return await this.logoInput.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return await this.emailInput.getAttribute('value');
  }

  async setStartDateInput(startDate) {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput() {
    return await this.startDateInput.getAttribute('value');
  }

  async setNumOfUsersInput(numOfUsers) {
    await this.numOfUsersInput.sendKeys(numOfUsers);
  }

  async getNumOfUsersInput() {
    return await this.numOfUsersInput.getAttribute('value');
  }

  async setTypeInput(type) {
    await this.typeInput.sendKeys(type);
  }

  async getTypeInput() {
    return await this.typeInput.getAttribute('value');
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

  async setUserIdInput(userId) {
    await this.userIdInput.sendKeys(userId);
  }

  async getUserIdInput() {
    return await this.userIdInput.getAttribute('value');
  }

  async industrySelectLastOption() {
    await this.industrySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async industrySelectOption(option) {
    await this.industrySelect.sendKeys(option);
  }

  getIndustrySelect(): ElementFinder {
    return this.industrySelect;
  }

  async getIndustrySelectedOption() {
    return await this.industrySelect.element(by.css('option:checked')).getText();
  }

  async provinceSelectLastOption() {
    await this.provinceSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async provinceSelectOption(option) {
    await this.provinceSelect.sendKeys(option);
  }

  getProvinceSelect(): ElementFinder {
    return this.provinceSelect;
  }

  async getProvinceSelectedOption() {
    return await this.provinceSelect.element(by.css('option:checked')).getText();
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

export class CompanyDeleteDialog {
  private dialogTitle = element(by.id('ak-delete-company-heading'));
  private confirmButton = element(by.id('ak-confirm-delete-company'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
