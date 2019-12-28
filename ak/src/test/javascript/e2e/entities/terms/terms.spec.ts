import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TermsComponentsPage, TermsDeleteDialog, TermsUpdatePage } from './terms.page-object';

const expect = chai.expect;

describe('Terms e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let termsComponentsPage: TermsComponentsPage;
  let termsUpdatePage: TermsUpdatePage;
  let termsDeleteDialog: TermsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Terms', async () => {
    await navBarPage.goToEntity('terms');
    termsComponentsPage = new TermsComponentsPage();
    await browser.wait(ec.visibilityOf(termsComponentsPage.title), 5000);
    expect(await termsComponentsPage.getTitle()).to.eq('akApp.terms.home.title');
  });

  it('should load create Terms page', async () => {
    await termsComponentsPage.clickOnCreateButton();
    termsUpdatePage = new TermsUpdatePage();
    expect(await termsUpdatePage.getPageTitle()).to.eq('akApp.terms.home.createOrEditLabel');
    await termsUpdatePage.cancel();
  });

  it('should create and save Terms', async () => {
    const nbButtonsBeforeCreate = await termsComponentsPage.countDeleteButtons();

    await termsComponentsPage.clickOnCreateButton();
    await promise.all([
      termsUpdatePage.setCodeInput('code'),
      termsUpdatePage.setNameInput('name'),
      termsUpdatePage.setDayOfMonthDueInput('5'),
      termsUpdatePage.setDueNextMonthDaysInput('5'),
      termsUpdatePage.setDiscountDayOfMonthInput('5'),
      termsUpdatePage.setDiscountPctInput('5')
    ]);
    expect(await termsUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await termsUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await termsUpdatePage.getDayOfMonthDueInput()).to.eq('5', 'Expected dayOfMonthDue value to be equals to 5');
    expect(await termsUpdatePage.getDueNextMonthDaysInput()).to.eq('5', 'Expected dueNextMonthDays value to be equals to 5');
    expect(await termsUpdatePage.getDiscountDayOfMonthInput()).to.eq('5', 'Expected discountDayOfMonth value to be equals to 5');
    expect(await termsUpdatePage.getDiscountPctInput()).to.eq('5', 'Expected discountPct value to be equals to 5');
    await termsUpdatePage.save();
    expect(await termsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await termsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Terms', async () => {
    const nbButtonsBeforeDelete = await termsComponentsPage.countDeleteButtons();
    await termsComponentsPage.clickOnLastDeleteButton();

    termsDeleteDialog = new TermsDeleteDialog();
    expect(await termsDeleteDialog.getDialogTitle()).to.eq('akApp.terms.delete.question');
    await termsDeleteDialog.clickOnConfirmButton();

    expect(await termsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
