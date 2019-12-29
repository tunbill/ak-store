import { element, by, ElementFinder } from 'protractor';

export class JobsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('ak-jobs div table .btn-danger'));
  title = element.all(by.css('ak-jobs div h2#page-heading span')).first();

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

export class JobsUpdatePage {
  pageTitle = element(by.id('ak-jobs-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  companyIdInput = element(by.id('field_companyId'));
  codeInput = element(by.id('field_code'));
  nameInput = element(by.id('field_name'));
  statusInput = element(by.id('field_status'));
  startDateInput = element(by.id('field_startDate'));
  endDateInput = element(by.id('field_endDate'));
  estimateInput = element(by.id('field_estimate'));
  investorInput = element(by.id('field_investor'));
  addressInput = element(by.id('field_address'));
  notesInput = element(by.id('field_notes'));
  jobTypeSelect = element(by.id('field_jobType'));

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

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setStatusInput(status) {
    await this.statusInput.sendKeys(status);
  }

  async getStatusInput() {
    return await this.statusInput.getAttribute('value');
  }

  async setStartDateInput(startDate) {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput() {
    return await this.startDateInput.getAttribute('value');
  }

  async setEndDateInput(endDate) {
    await this.endDateInput.sendKeys(endDate);
  }

  async getEndDateInput() {
    return await this.endDateInput.getAttribute('value');
  }

  async setEstimateInput(estimate) {
    await this.estimateInput.sendKeys(estimate);
  }

  async getEstimateInput() {
    return await this.estimateInput.getAttribute('value');
  }

  async setInvestorInput(investor) {
    await this.investorInput.sendKeys(investor);
  }

  async getInvestorInput() {
    return await this.investorInput.getAttribute('value');
  }

  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return await this.addressInput.getAttribute('value');
  }

  async setNotesInput(notes) {
    await this.notesInput.sendKeys(notes);
  }

  async getNotesInput() {
    return await this.notesInput.getAttribute('value');
  }

  async jobTypeSelectLastOption() {
    await this.jobTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async jobTypeSelectOption(option) {
    await this.jobTypeSelect.sendKeys(option);
  }

  getJobTypeSelect(): ElementFinder {
    return this.jobTypeSelect;
  }

  async getJobTypeSelectedOption() {
    return await this.jobTypeSelect.element(by.css('option:checked')).getText();
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

export class JobsDeleteDialog {
  private dialogTitle = element(by.id('ak-delete-jobs-heading'));
  private confirmButton = element(by.id('ak-confirm-delete-jobs'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
