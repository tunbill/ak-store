import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ItemComponentsPage, ItemDeleteDialog, ItemUpdatePage } from './item.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Item e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let itemComponentsPage: ItemComponentsPage;
  let itemUpdatePage: ItemUpdatePage;
  let itemDeleteDialog: ItemDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Items', async () => {
    await navBarPage.goToEntity('item');
    itemComponentsPage = new ItemComponentsPage();
    await browser.wait(ec.visibilityOf(itemComponentsPage.title), 5000);
    expect(await itemComponentsPage.getTitle()).to.eq('akApp.item.home.title');
  });

  it('should load create Item page', async () => {
    await itemComponentsPage.clickOnCreateButton();
    itemUpdatePage = new ItemUpdatePage();
    expect(await itemUpdatePage.getPageTitle()).to.eq('akApp.item.home.createOrEditLabel');
    await itemUpdatePage.cancel();
  });

  it('should create and save Items', async () => {
    const nbButtonsBeforeCreate = await itemComponentsPage.countDeleteButtons();

    await itemComponentsPage.clickOnCreateButton();
    await promise.all([
      itemUpdatePage.setCodeInput('code'),
      itemUpdatePage.setNameInput('name'),
      itemUpdatePage.setDescriptionInput('description'),
      itemUpdatePage.typeSelectLastOption(),
      itemUpdatePage.setSkuNumInput('5'),
      itemUpdatePage.vatTaxSelectLastOption(),
      itemUpdatePage.setImportTaxInput('5'),
      itemUpdatePage.setExportTaxInput('5'),
      itemUpdatePage.inventoryPriceMethodSelectLastOption(),
      itemUpdatePage.setAccountItemInput('accountItem'),
      itemUpdatePage.setAccountCostInput('accountCost'),
      itemUpdatePage.setAccountRevenueInput('accountRevenue'),
      itemUpdatePage.setAccountInternalRevenueInput('accountInternalRevenue'),
      itemUpdatePage.setAccountSaleReturnInput('accountSaleReturn'),
      itemUpdatePage.setAccountSalePriceInput('accountSalePrice'),
      itemUpdatePage.setAccountAgencyInput('accountAgency'),
      itemUpdatePage.setAccountRawProductInput('accountRawProduct'),
      itemUpdatePage.setAccountCostDifferenceInput('accountCostDifference'),
      itemUpdatePage.setAccountDiscountInput('accountDiscount'),
      itemUpdatePage.setSaleDescInput('saleDesc'),
      itemUpdatePage.setPurchaseDescInput('purchaseDesc'),
      itemUpdatePage.setWeightInput('5'),
      itemUpdatePage.setLenghtInput('5'),
      itemUpdatePage.setWideInput('5'),
      itemUpdatePage.setHeightInput('5'),
      itemUpdatePage.setColorInput('color'),
      itemUpdatePage.setSpecificationInput('specification'),
      itemUpdatePage.setItemImageInput(absolutePath),
      itemUpdatePage.setTimeCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      itemUpdatePage.setTimeModifiedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      itemUpdatePage.setUserIdCreatedInput('5'),
      itemUpdatePage.setUserIdModifiedInput('5'),
      itemUpdatePage.unitSelectLastOption(),
      itemUpdatePage.itemGroupSelectLastOption(),
      itemUpdatePage.storeSelectLastOption(),
      itemUpdatePage.companySelectLastOption()
    ]);
    expect(await itemUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await itemUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await itemUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await itemUpdatePage.getSkuNumInput()).to.eq('5', 'Expected skuNum value to be equals to 5');
    expect(await itemUpdatePage.getImportTaxInput()).to.eq('5', 'Expected importTax value to be equals to 5');
    expect(await itemUpdatePage.getExportTaxInput()).to.eq('5', 'Expected exportTax value to be equals to 5');
    expect(await itemUpdatePage.getAccountItemInput()).to.eq('accountItem', 'Expected AccountItem value to be equals to accountItem');
    const selectedIsAllowModified = itemUpdatePage.getIsAllowModifiedInput();
    if (await selectedIsAllowModified.isSelected()) {
      await itemUpdatePage.getIsAllowModifiedInput().click();
      expect(await itemUpdatePage.getIsAllowModifiedInput().isSelected(), 'Expected isAllowModified not to be selected').to.be.false;
    } else {
      await itemUpdatePage.getIsAllowModifiedInput().click();
      expect(await itemUpdatePage.getIsAllowModifiedInput().isSelected(), 'Expected isAllowModified to be selected').to.be.true;
    }
    expect(await itemUpdatePage.getAccountCostInput()).to.eq('accountCost', 'Expected AccountCost value to be equals to accountCost');
    expect(await itemUpdatePage.getAccountRevenueInput()).to.eq(
      'accountRevenue',
      'Expected AccountRevenue value to be equals to accountRevenue'
    );
    expect(await itemUpdatePage.getAccountInternalRevenueInput()).to.eq(
      'accountInternalRevenue',
      'Expected AccountInternalRevenue value to be equals to accountInternalRevenue'
    );
    expect(await itemUpdatePage.getAccountSaleReturnInput()).to.eq(
      'accountSaleReturn',
      'Expected AccountSaleReturn value to be equals to accountSaleReturn'
    );
    expect(await itemUpdatePage.getAccountSalePriceInput()).to.eq(
      'accountSalePrice',
      'Expected AccountSalePrice value to be equals to accountSalePrice'
    );
    expect(await itemUpdatePage.getAccountAgencyInput()).to.eq(
      'accountAgency',
      'Expected AccountAgency value to be equals to accountAgency'
    );
    expect(await itemUpdatePage.getAccountRawProductInput()).to.eq(
      'accountRawProduct',
      'Expected AccountRawProduct value to be equals to accountRawProduct'
    );
    expect(await itemUpdatePage.getAccountCostDifferenceInput()).to.eq(
      'accountCostDifference',
      'Expected AccountCostDifference value to be equals to accountCostDifference'
    );
    expect(await itemUpdatePage.getAccountDiscountInput()).to.eq(
      'accountDiscount',
      'Expected AccountDiscount value to be equals to accountDiscount'
    );
    expect(await itemUpdatePage.getSaleDescInput()).to.eq('saleDesc', 'Expected SaleDesc value to be equals to saleDesc');
    expect(await itemUpdatePage.getPurchaseDescInput()).to.eq('purchaseDesc', 'Expected PurchaseDesc value to be equals to purchaseDesc');
    expect(await itemUpdatePage.getWeightInput()).to.eq('5', 'Expected weight value to be equals to 5');
    expect(await itemUpdatePage.getLenghtInput()).to.eq('5', 'Expected lenght value to be equals to 5');
    expect(await itemUpdatePage.getWideInput()).to.eq('5', 'Expected wide value to be equals to 5');
    expect(await itemUpdatePage.getHeightInput()).to.eq('5', 'Expected height value to be equals to 5');
    expect(await itemUpdatePage.getColorInput()).to.eq('color', 'Expected Color value to be equals to color');
    expect(await itemUpdatePage.getSpecificationInput()).to.eq(
      'specification',
      'Expected Specification value to be equals to specification'
    );
    expect(await itemUpdatePage.getItemImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected ItemImage value to be end with ' + fileNameToUpload
    );
    const selectedIsActive = itemUpdatePage.getIsActiveInput();
    if (await selectedIsActive.isSelected()) {
      await itemUpdatePage.getIsActiveInput().click();
      expect(await itemUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive not to be selected').to.be.false;
    } else {
      await itemUpdatePage.getIsActiveInput().click();
      expect(await itemUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive to be selected').to.be.true;
    }
    expect(await itemUpdatePage.getTimeCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected timeCreated value to be equals to 2000-12-31'
    );
    expect(await itemUpdatePage.getTimeModifiedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected timeModified value to be equals to 2000-12-31'
    );
    expect(await itemUpdatePage.getUserIdCreatedInput()).to.eq('5', 'Expected userIdCreated value to be equals to 5');
    expect(await itemUpdatePage.getUserIdModifiedInput()).to.eq('5', 'Expected userIdModified value to be equals to 5');
    await itemUpdatePage.save();
    expect(await itemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await itemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Item', async () => {
    const nbButtonsBeforeDelete = await itemComponentsPage.countDeleteButtons();
    await itemComponentsPage.clickOnLastDeleteButton();

    itemDeleteDialog = new ItemDeleteDialog();
    expect(await itemDeleteDialog.getDialogTitle()).to.eq('akApp.item.delete.question');
    await itemDeleteDialog.clickOnConfirmButton();

    expect(await itemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
