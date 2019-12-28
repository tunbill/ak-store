import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IndustryComponentsPage, IndustryDeleteDialog, IndustryUpdatePage } from './industry.page-object';

const expect = chai.expect;

describe('Industry e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let industryComponentsPage: IndustryComponentsPage;
  let industryUpdatePage: IndustryUpdatePage;
  let industryDeleteDialog: IndustryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Industries', async () => {
    await navBarPage.goToEntity('industry');
    industryComponentsPage = new IndustryComponentsPage();
    await browser.wait(ec.visibilityOf(industryComponentsPage.title), 5000);
    expect(await industryComponentsPage.getTitle()).to.eq('akApp.industry.home.title');
  });

  it('should load create Industry page', async () => {
    await industryComponentsPage.clickOnCreateButton();
    industryUpdatePage = new IndustryUpdatePage();
    expect(await industryUpdatePage.getPageTitle()).to.eq('akApp.industry.home.createOrEditLabel');
    await industryUpdatePage.cancel();
  });

  it('should create and save Industries', async () => {
    const nbButtonsBeforeCreate = await industryComponentsPage.countDeleteButtons();

    await industryComponentsPage.clickOnCreateButton();
    await promise.all([industryUpdatePage.setNameInput('name')]);
    expect(await industryUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    await industryUpdatePage.save();
    expect(await industryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await industryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Industry', async () => {
    const nbButtonsBeforeDelete = await industryComponentsPage.countDeleteButtons();
    await industryComponentsPage.clickOnLastDeleteButton();

    industryDeleteDialog = new IndustryDeleteDialog();
    expect(await industryDeleteDialog.getDialogTitle()).to.eq('akApp.industry.delete.question');
    await industryDeleteDialog.clickOnConfirmButton();

    expect(await industryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
