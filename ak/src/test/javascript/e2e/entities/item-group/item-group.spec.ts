import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ItemGroupComponentsPage, ItemGroupDeleteDialog, ItemGroupUpdatePage } from './item-group.page-object';

const expect = chai.expect;

describe('ItemGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let itemGroupComponentsPage: ItemGroupComponentsPage;
  let itemGroupUpdatePage: ItemGroupUpdatePage;
  let itemGroupDeleteDialog: ItemGroupDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ItemGroups', async () => {
    await navBarPage.goToEntity('item-group');
    itemGroupComponentsPage = new ItemGroupComponentsPage();
    await browser.wait(ec.visibilityOf(itemGroupComponentsPage.title), 5000);
    expect(await itemGroupComponentsPage.getTitle()).to.eq('akApp.itemGroup.home.title');
  });

  it('should load create ItemGroup page', async () => {
    await itemGroupComponentsPage.clickOnCreateButton();
    itemGroupUpdatePage = new ItemGroupUpdatePage();
    expect(await itemGroupUpdatePage.getPageTitle()).to.eq('akApp.itemGroup.home.createOrEditLabel');
    await itemGroupUpdatePage.cancel();
  });

  it('should create and save ItemGroups', async () => {
    const nbButtonsBeforeCreate = await itemGroupComponentsPage.countDeleteButtons();

    await itemGroupComponentsPage.clickOnCreateButton();
    await promise.all([
      itemGroupUpdatePage.setCompanyIdInput('5'),
      itemGroupUpdatePage.setCodeInput('code'),
      itemGroupUpdatePage.setNameInput('name'),
      itemGroupUpdatePage.setDescriptionInput('description')
    ]);
    expect(await itemGroupUpdatePage.getCompanyIdInput()).to.eq('5', 'Expected companyId value to be equals to 5');
    expect(await itemGroupUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await itemGroupUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await itemGroupUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    const selectedIsActive = itemGroupUpdatePage.getIsActiveInput();
    if (await selectedIsActive.isSelected()) {
      await itemGroupUpdatePage.getIsActiveInput().click();
      expect(await itemGroupUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive not to be selected').to.be.false;
    } else {
      await itemGroupUpdatePage.getIsActiveInput().click();
      expect(await itemGroupUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive to be selected').to.be.true;
    }
    await itemGroupUpdatePage.save();
    expect(await itemGroupUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await itemGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ItemGroup', async () => {
    const nbButtonsBeforeDelete = await itemGroupComponentsPage.countDeleteButtons();
    await itemGroupComponentsPage.clickOnLastDeleteButton();

    itemGroupDeleteDialog = new ItemGroupDeleteDialog();
    expect(await itemGroupDeleteDialog.getDialogTitle()).to.eq('akApp.itemGroup.delete.question');
    await itemGroupDeleteDialog.clickOnConfirmButton();

    expect(await itemGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
