import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InvoiceLineComponentsPage, InvoiceLineDeleteDialog, InvoiceLineUpdatePage } from './invoice-line.page-object';

const expect = chai.expect;

describe('InvoiceLine e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let invoiceLineComponentsPage: InvoiceLineComponentsPage;
  let invoiceLineUpdatePage: InvoiceLineUpdatePage;
  let invoiceLineDeleteDialog: InvoiceLineDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load InvoiceLines', async () => {
    await navBarPage.goToEntity('invoice-line');
    invoiceLineComponentsPage = new InvoiceLineComponentsPage();
    await browser.wait(ec.visibilityOf(invoiceLineComponentsPage.title), 5000);
    expect(await invoiceLineComponentsPage.getTitle()).to.eq('akApp.invoiceLine.home.title');
  });

  it('should load create InvoiceLine page', async () => {
    await invoiceLineComponentsPage.clickOnCreateButton();
    invoiceLineUpdatePage = new InvoiceLineUpdatePage();
    expect(await invoiceLineUpdatePage.getPageTitle()).to.eq('akApp.invoiceLine.home.createOrEditLabel');
    await invoiceLineUpdatePage.cancel();
  });

  it('should create and save InvoiceLines', async () => {
    const nbButtonsBeforeCreate = await invoiceLineComponentsPage.countDeleteButtons();

    await invoiceLineComponentsPage.clickOnCreateButton();
    await promise.all([
      invoiceLineUpdatePage.setDisplayOrderInput('5'),
      invoiceLineUpdatePage.setItemNameInput('itemName'),
      invoiceLineUpdatePage.setUnitNameInput('unitName'),
      invoiceLineUpdatePage.setQuantityInput('5'),
      invoiceLineUpdatePage.setRateInput('5'),
      invoiceLineUpdatePage.setAmountInput('5'),
      invoiceLineUpdatePage.setDiscountPctInput('5'),
      invoiceLineUpdatePage.setAccountNumberInput('accountNumber'),
      invoiceLineUpdatePage.statusSelectLastOption(),
      invoiceLineUpdatePage.invoiceSelectLastOption(),
      invoiceLineUpdatePage.itemSelectLastOption(),
      invoiceLineUpdatePage.companySelectLastOption()
    ]);
    expect(await invoiceLineUpdatePage.getDisplayOrderInput()).to.eq('5', 'Expected displayOrder value to be equals to 5');
    expect(await invoiceLineUpdatePage.getItemNameInput()).to.eq('itemName', 'Expected ItemName value to be equals to itemName');
    expect(await invoiceLineUpdatePage.getUnitNameInput()).to.eq('unitName', 'Expected UnitName value to be equals to unitName');
    expect(await invoiceLineUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await invoiceLineUpdatePage.getRateInput()).to.eq('5', 'Expected rate value to be equals to 5');
    expect(await invoiceLineUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
    expect(await invoiceLineUpdatePage.getDiscountPctInput()).to.eq('5', 'Expected discountPct value to be equals to 5');
    expect(await invoiceLineUpdatePage.getAccountNumberInput()).to.eq(
      'accountNumber',
      'Expected AccountNumber value to be equals to accountNumber'
    );
    await invoiceLineUpdatePage.save();
    expect(await invoiceLineUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await invoiceLineComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last InvoiceLine', async () => {
    const nbButtonsBeforeDelete = await invoiceLineComponentsPage.countDeleteButtons();
    await invoiceLineComponentsPage.clickOnLastDeleteButton();

    invoiceLineDeleteDialog = new InvoiceLineDeleteDialog();
    expect(await invoiceLineDeleteDialog.getDialogTitle()).to.eq('akApp.invoiceLine.delete.question');
    await invoiceLineDeleteDialog.clickOnConfirmButton();

    expect(await invoiceLineComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
