import { element, by, ElementFinder } from 'protractor';

export class CustomerTypeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('ak-customer-type div table .btn-danger'));
  title = element.all(by.css('ak-customer-type div h2#page-heading span')).first();

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

export class CustomerTypeUpdatePage {
  pageTitle = element(by.id('ak-customer-type-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  companyIdInput = element(by.id('field_companyId'));
  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  isActiveInput = element(by.id('field_isActive'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCompanyIdInput(companyId) {
    await this.companyIdInput.sendKeys(companyId);
  }

  async getCompanyIdInput() {
    return await this.companyIdInput.getAttribute('value');
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

  getIsActiveInput() {
    return this.isActiveInput;
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

export class CustomerTypeDeleteDialog {
  private dialogTitle = element(by.id('ak-delete-customerType-heading'));
  private confirmButton = element(by.id('ak-confirm-delete-customerType'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
