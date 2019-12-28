import { element, by, ElementFinder } from 'protractor';

export class ItemGroupComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('ak-item-group div table .btn-danger'));
  title = element.all(by.css('ak-item-group div h2#page-heading span')).first();

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

export class ItemGroupUpdatePage {
  pageTitle = element(by.id('ak-item-group-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codeInput = element(by.id('field_code'));
  nameInput = element(by.id('field_name'));
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

export class ItemGroupDeleteDialog {
  private dialogTitle = element(by.id('ak-delete-itemGroup-heading'));
  private confirmButton = element(by.id('ak-confirm-delete-itemGroup'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
