import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CustomerTypeComponentsPage, CustomerTypeDeleteDialog, CustomerTypeUpdatePage } from './customer-type.page-object';

const expect = chai.expect;

describe('CustomerType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let customerTypeComponentsPage: CustomerTypeComponentsPage;
  let customerTypeUpdatePage: CustomerTypeUpdatePage;
  let customerTypeDeleteDialog: CustomerTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CustomerTypes', async () => {
    await navBarPage.goToEntity('customer-type');
    customerTypeComponentsPage = new CustomerTypeComponentsPage();
    await browser.wait(ec.visibilityOf(customerTypeComponentsPage.title), 5000);
    expect(await customerTypeComponentsPage.getTitle()).to.eq('akApp.customerType.home.title');
  });

  it('should load create CustomerType page', async () => {
    await customerTypeComponentsPage.clickOnCreateButton();
    customerTypeUpdatePage = new CustomerTypeUpdatePage();
    expect(await customerTypeUpdatePage.getPageTitle()).to.eq('akApp.customerType.home.createOrEditLabel');
    await customerTypeUpdatePage.cancel();
  });

  it('should create and save CustomerTypes', async () => {
    const nbButtonsBeforeCreate = await customerTypeComponentsPage.countDeleteButtons();

    await customerTypeComponentsPage.clickOnCreateButton();
    await promise.all([
      customerTypeUpdatePage.setCompanyIdInput('5'),
      customerTypeUpdatePage.setNameInput('name'),
      customerTypeUpdatePage.setDescriptionInput('description')
    ]);
    expect(await customerTypeUpdatePage.getCompanyIdInput()).to.eq('5', 'Expected companyId value to be equals to 5');
    expect(await customerTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await customerTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    const selectedIsActive = customerTypeUpdatePage.getIsActiveInput();
    if (await selectedIsActive.isSelected()) {
      await customerTypeUpdatePage.getIsActiveInput().click();
      expect(await customerTypeUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive not to be selected').to.be.false;
    } else {
      await customerTypeUpdatePage.getIsActiveInput().click();
      expect(await customerTypeUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive to be selected').to.be.true;
    }
    await customerTypeUpdatePage.save();
    expect(await customerTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await customerTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last CustomerType', async () => {
    const nbButtonsBeforeDelete = await customerTypeComponentsPage.countDeleteButtons();
    await customerTypeComponentsPage.clickOnLastDeleteButton();

    customerTypeDeleteDialog = new CustomerTypeDeleteDialog();
    expect(await customerTypeDeleteDialog.getDialogTitle()).to.eq('akApp.customerType.delete.question');
    await customerTypeDeleteDialog.clickOnConfirmButton();

    expect(await customerTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
