import { element, by, ElementFinder } from 'protractor';

export class TermsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('ak-terms div table .btn-danger'));
  title = element.all(by.css('ak-terms div h2#page-heading span')).first();

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

export class TermsUpdatePage {
  pageTitle = element(by.id('ak-terms-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codeInput = element(by.id('field_code'));
  nameInput = element(by.id('field_name'));
  dayOfMonthDueInput = element(by.id('field_dayOfMonthDue'));
  dueNextMonthDaysInput = element(by.id('field_dueNextMonthDays'));
  discountDayOfMonthInput = element(by.id('field_discountDayOfMonth'));
  discountPctInput = element(by.id('field_discountPct'));

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

  async setDayOfMonthDueInput(dayOfMonthDue) {
    await this.dayOfMonthDueInput.sendKeys(dayOfMonthDue);
  }

  async getDayOfMonthDueInput() {
    return await this.dayOfMonthDueInput.getAttribute('value');
  }

  async setDueNextMonthDaysInput(dueNextMonthDays) {
    await this.dueNextMonthDaysInput.sendKeys(dueNextMonthDays);
  }

  async getDueNextMonthDaysInput() {
    return await this.dueNextMonthDaysInput.getAttribute('value');
  }

  async setDiscountDayOfMonthInput(discountDayOfMonth) {
    await this.discountDayOfMonthInput.sendKeys(discountDayOfMonth);
  }

  async getDiscountDayOfMonthInput() {
    return await this.discountDayOfMonthInput.getAttribute('value');
  }

  async setDiscountPctInput(discountPct) {
    await this.discountPctInput.sendKeys(discountPct);
  }

  async getDiscountPctInput() {
    return await this.discountPctInput.getAttribute('value');
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

export class TermsDeleteDialog {
  private dialogTitle = element(by.id('ak-delete-terms-heading'));
  private confirmButton = element(by.id('ak-confirm-delete-terms'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
