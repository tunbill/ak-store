import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StoreComponentsPage, StoreDeleteDialog, StoreUpdatePage } from './store.page-object';

const expect = chai.expect;

describe('Store e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let storeComponentsPage: StoreComponentsPage;
  let storeUpdatePage: StoreUpdatePage;
  let storeDeleteDialog: StoreDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Stores', async () => {
    await navBarPage.goToEntity('store');
    storeComponentsPage = new StoreComponentsPage();
    await browser.wait(ec.visibilityOf(storeComponentsPage.title), 5000);
    expect(await storeComponentsPage.getTitle()).to.eq('akApp.store.home.title');
  });

  it('should load create Store page', async () => {
    await storeComponentsPage.clickOnCreateButton();
    storeUpdatePage = new StoreUpdatePage();
    expect(await storeUpdatePage.getPageTitle()).to.eq('akApp.store.home.createOrEditLabel');
    await storeUpdatePage.cancel();
  });

  it('should create and save Stores', async () => {
    const nbButtonsBeforeCreate = await storeComponentsPage.countDeleteButtons();

    await storeComponentsPage.clickOnCreateButton();
    await promise.all([
      storeUpdatePage.setCodeInput('code'),
      storeUpdatePage.setNameInput('name'),
      storeUpdatePage.setAddressInput('address'),
      storeUpdatePage.companySelectLastOption()
    ]);
    expect(await storeUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await storeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await storeUpdatePage.getAddressInput()).to.eq('address', 'Expected Address value to be equals to address');
    await storeUpdatePage.save();
    expect(await storeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await storeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Store', async () => {
    const nbButtonsBeforeDelete = await storeComponentsPage.countDeleteButtons();
    await storeComponentsPage.clickOnLastDeleteButton();

    storeDeleteDialog = new StoreDeleteDialog();
    expect(await storeDeleteDialog.getDialogTitle()).to.eq('akApp.store.delete.question');
    await storeDeleteDialog.clickOnConfirmButton();

    expect(await storeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
