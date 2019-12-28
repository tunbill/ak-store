import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InvoiceComponentsPage, InvoiceDeleteDialog, InvoiceUpdatePage } from './invoice.page-object';

const expect = chai.expect;

describe('Invoice e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let invoiceComponentsPage: InvoiceComponentsPage;
  let invoiceUpdatePage: InvoiceUpdatePage;
  let invoiceDeleteDialog: InvoiceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Invoices', async () => {
    await navBarPage.goToEntity('invoice');
    invoiceComponentsPage = new InvoiceComponentsPage();
    await browser.wait(ec.visibilityOf(invoiceComponentsPage.title), 5000);
    expect(await invoiceComponentsPage.getTitle()).to.eq('akApp.invoice.home.title');
  });

  it('should load create Invoice page', async () => {
    await invoiceComponentsPage.clickOnCreateButton();
    invoiceUpdatePage = new InvoiceUpdatePage();
    expect(await invoiceUpdatePage.getPageTitle()).to.eq('akApp.invoice.home.createOrEditLabel');
    await invoiceUpdatePage.cancel();
  });

  it('should create and save Invoices', async () => {
    const nbButtonsBeforeCreate = await invoiceComponentsPage.countDeleteButtons();

    await invoiceComponentsPage.clickOnCreateButton();
    await promise.all([
      invoiceUpdatePage.setInvoiceNoInput('invoiceNo'),
      invoiceUpdatePage.setInvoiceDateInput('2000-12-31'),
      invoiceUpdatePage.setDueDateInput('2000-12-31'),
      invoiceUpdatePage.setBillingAddressInput('billingAddress'),
      invoiceUpdatePage.setAccountNumberInput('accountNumber'),
      invoiceUpdatePage.setPoNumberInput('poNumber'),
      invoiceUpdatePage.setNotesInput('notes'),
      invoiceUpdatePage.setProductTotalInput('5'),
      invoiceUpdatePage.setVatTotalInput('5'),
      invoiceUpdatePage.setDiscountTotalInput('5'),
      invoiceUpdatePage.setTotalInput('5'),
      invoiceUpdatePage.statusSelectLastOption(),
      invoiceUpdatePage.setTimeCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      invoiceUpdatePage.setTimeModifiedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      invoiceUpdatePage.setUserIdCreatedInput('5'),
      invoiceUpdatePage.setUserIdModifiedInput('5'),
      invoiceUpdatePage.customerSelectLastOption(),
      invoiceUpdatePage.termsSelectLastOption(),
      invoiceUpdatePage.employeeSelectLastOption(),
      invoiceUpdatePage.companySelectLastOption()
    ]);
    expect(await invoiceUpdatePage.getInvoiceNoInput()).to.eq('invoiceNo', 'Expected InvoiceNo value to be equals to invoiceNo');
    expect(await invoiceUpdatePage.getInvoiceDateInput()).to.eq('2000-12-31', 'Expected invoiceDate value to be equals to 2000-12-31');
    expect(await invoiceUpdatePage.getDueDateInput()).to.eq('2000-12-31', 'Expected dueDate value to be equals to 2000-12-31');
    expect(await invoiceUpdatePage.getBillingAddressInput()).to.eq(
      'billingAddress',
      'Expected BillingAddress value to be equals to billingAddress'
    );
    expect(await invoiceUpdatePage.getAccountNumberInput()).to.eq(
      'accountNumber',
      'Expected AccountNumber value to be equals to accountNumber'
    );
    expect(await invoiceUpdatePage.getPoNumberInput()).to.eq('poNumber', 'Expected PoNumber value to be equals to poNumber');
    expect(await invoiceUpdatePage.getNotesInput()).to.eq('notes', 'Expected Notes value to be equals to notes');
    expect(await invoiceUpdatePage.getProductTotalInput()).to.eq('5', 'Expected productTotal value to be equals to 5');
    expect(await invoiceUpdatePage.getVatTotalInput()).to.eq('5', 'Expected vatTotal value to be equals to 5');
    expect(await invoiceUpdatePage.getDiscountTotalInput()).to.eq('5', 'Expected discountTotal value to be equals to 5');
    expect(await invoiceUpdatePage.getTotalInput()).to.eq('5', 'Expected total value to be equals to 5');
    expect(await invoiceUpdatePage.getTimeCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected timeCreated value to be equals to 2000-12-31'
    );
    expect(await invoiceUpdatePage.getTimeModifiedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected timeModified value to be equals to 2000-12-31'
    );
    expect(await invoiceUpdatePage.getUserIdCreatedInput()).to.eq('5', 'Expected userIdCreated value to be equals to 5');
    expect(await invoiceUpdatePage.getUserIdModifiedInput()).to.eq('5', 'Expected userIdModified value to be equals to 5');
    await invoiceUpdatePage.save();
    expect(await invoiceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await invoiceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Invoice', async () => {
    const nbButtonsBeforeDelete = await invoiceComponentsPage.countDeleteButtons();
    await invoiceComponentsPage.clickOnLastDeleteButton();

    invoiceDeleteDialog = new InvoiceDeleteDialog();
    expect(await invoiceDeleteDialog.getDialogTitle()).to.eq('akApp.invoice.delete.question');
    await invoiceDeleteDialog.clickOnConfirmButton();

    expect(await invoiceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
