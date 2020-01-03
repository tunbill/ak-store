import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CustomerComponentsPage, CustomerDeleteDialog, CustomerUpdatePage } from './customer.page-object';

const expect = chai.expect;

describe('Customer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let customerComponentsPage: CustomerComponentsPage;
  let customerUpdatePage: CustomerUpdatePage;
  let customerDeleteDialog: CustomerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Customers', async () => {
    await navBarPage.goToEntity('customer');
    customerComponentsPage = new CustomerComponentsPage();
    await browser.wait(ec.visibilityOf(customerComponentsPage.title), 5000);
    expect(await customerComponentsPage.getTitle()).to.eq('akApp.customer.home.title');
  });

  it('should load create Customer page', async () => {
    await customerComponentsPage.clickOnCreateButton();
    customerUpdatePage = new CustomerUpdatePage();
    expect(await customerUpdatePage.getPageTitle()).to.eq('akApp.customer.home.createOrEditLabel');
    await customerUpdatePage.cancel();
  });

  it('should create and save Customers', async () => {
    const nbButtonsBeforeCreate = await customerComponentsPage.countDeleteButtons();

    await customerComponentsPage.clickOnCreateButton();
    await promise.all([
      customerUpdatePage.setCompanyIdInput('5'),
      customerUpdatePage.setVendorIdInput('5'),
      customerUpdatePage.setCodeInput('code'),
      customerUpdatePage.setCompanyNameInput('companyName'),
      customerUpdatePage.setAddressInput('address'),
      customerUpdatePage.setPhoneInput('phone'),
      customerUpdatePage.setMobileInput('mobile'),
      customerUpdatePage.setFaxInput('fax'),
      customerUpdatePage.setEmailInput(
        '8/&gt;OY2)[$*E#.4&lt;.41XxiG}:q(qhDELi1?g8@L7qg?Se[(!N&gt;Q/}q&amp;j..22PEuBTL98yQWFo?f;8Xre}JVvjJjv$q&gt;3;Vug&#34;4,9gaF#NKdctQ*4xh*c_z]X'
      ),
      customerUpdatePage.setWebsiteInput('website'),
      customerUpdatePage.setTaxCodeInput('taxCode'),
      customerUpdatePage.setAccountNumberInput('accountNumber'),
      customerUpdatePage.setBankAccountInput('bankAccount'),
      customerUpdatePage.setBankNameInput('bankName'),
      customerUpdatePage.setBalanceInput('5'),
      customerUpdatePage.setTotalBalanceInput('5'),
      customerUpdatePage.setOpenBalanceInput('5'),
      customerUpdatePage.setOpenBalanceDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      customerUpdatePage.setCreditLimitInput('5'),
      customerUpdatePage.setNotesInput('notes'),
      customerUpdatePage.setContactNameInput('contactName'),
      customerUpdatePage.setContactMobileInput('contactMobile'),
      customerUpdatePage.setContactEmailInput(
        'NKY&amp;TnQ&lt;{=[E2Q*lj#!lOsm&amp;J_Uq;]S&#34;Dy8k,[%~a3P&#34;8Aj:D5.::6:x@jSc&gt;&#34;EU1.I}Fu187{(IacXQ9K?W+t,l&#34;w-b`(US7y^%~HLH0E&#39;o)8sa*#&amp;H|DOQw&#39;TN#puOx2Ylq=,8I&amp;)'
      ),
      customerUpdatePage.setTimeCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      customerUpdatePage.setTimeModifiedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      customerUpdatePage.setUserIdCreatedInput('5'),
      customerUpdatePage.setUserIdModifiedInput('5'),
      customerUpdatePage.customerTypeSelectLastOption(),
      customerUpdatePage.termsSelectLastOption()
    ]);
    expect(await customerUpdatePage.getCompanyIdInput()).to.eq('5', 'Expected companyId value to be equals to 5');
    const selectedIsVendor = customerUpdatePage.getIsVendorInput();
    if (await selectedIsVendor.isSelected()) {
      await customerUpdatePage.getIsVendorInput().click();
      expect(await customerUpdatePage.getIsVendorInput().isSelected(), 'Expected isVendor not to be selected').to.be.false;
    } else {
      await customerUpdatePage.getIsVendorInput().click();
      expect(await customerUpdatePage.getIsVendorInput().isSelected(), 'Expected isVendor to be selected').to.be.true;
    }
    expect(await customerUpdatePage.getVendorIdInput()).to.eq('5', 'Expected vendorId value to be equals to 5');
    expect(await customerUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await customerUpdatePage.getCompanyNameInput()).to.eq('companyName', 'Expected CompanyName value to be equals to companyName');
    expect(await customerUpdatePage.getAddressInput()).to.eq('address', 'Expected Address value to be equals to address');
    expect(await customerUpdatePage.getPhoneInput()).to.eq('phone', 'Expected Phone value to be equals to phone');
    expect(await customerUpdatePage.getMobileInput()).to.eq('mobile', 'Expected Mobile value to be equals to mobile');
    expect(await customerUpdatePage.getFaxInput()).to.eq('fax', 'Expected Fax value to be equals to fax');
    expect(await customerUpdatePage.getEmailInput()).to.eq(
      '8/&gt;OY2)[$*E#.4&lt;.41XxiG}:q(qhDELi1?g8@L7qg?Se[(!N&gt;Q/}q&amp;j..22PEuBTL98yQWFo?f;8Xre}JVvjJjv$q&gt;3;Vug&#34;4,9gaF#NKdctQ*4xh*c_z]X',
      'Expected Email value to be equals to 8/&gt;OY2)[$*E#.4&lt;.41XxiG}:q(qhDELi1?g8@L7qg?Se[(!N&gt;Q/}q&amp;j..22PEuBTL98yQWFo?f;8Xre}JVvjJjv$q&gt;3;Vug&#34;4,9gaF#NKdctQ*4xh*c_z]X'
    );
    expect(await customerUpdatePage.getWebsiteInput()).to.eq('website', 'Expected Website value to be equals to website');
    expect(await customerUpdatePage.getTaxCodeInput()).to.eq('taxCode', 'Expected TaxCode value to be equals to taxCode');
    expect(await customerUpdatePage.getAccountNumberInput()).to.eq(
      'accountNumber',
      'Expected AccountNumber value to be equals to accountNumber'
    );
    expect(await customerUpdatePage.getBankAccountInput()).to.eq('bankAccount', 'Expected BankAccount value to be equals to bankAccount');
    expect(await customerUpdatePage.getBankNameInput()).to.eq('bankName', 'Expected BankName value to be equals to bankName');
    expect(await customerUpdatePage.getBalanceInput()).to.eq('5', 'Expected balance value to be equals to 5');
    expect(await customerUpdatePage.getTotalBalanceInput()).to.eq('5', 'Expected totalBalance value to be equals to 5');
    expect(await customerUpdatePage.getOpenBalanceInput()).to.eq('5', 'Expected openBalance value to be equals to 5');
    expect(await customerUpdatePage.getOpenBalanceDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected openBalanceDate value to be equals to 2000-12-31'
    );
    expect(await customerUpdatePage.getCreditLimitInput()).to.eq('5', 'Expected creditLimit value to be equals to 5');
    expect(await customerUpdatePage.getNotesInput()).to.eq('notes', 'Expected Notes value to be equals to notes');
    expect(await customerUpdatePage.getContactNameInput()).to.eq('contactName', 'Expected ContactName value to be equals to contactName');
    expect(await customerUpdatePage.getContactMobileInput()).to.eq(
      'contactMobile',
      'Expected ContactMobile value to be equals to contactMobile'
    );
    expect(await customerUpdatePage.getContactEmailInput()).to.eq(
      'NKY&amp;TnQ&lt;{=[E2Q*lj#!lOsm&amp;J_Uq;]S&#34;Dy8k,[%~a3P&#34;8Aj:D5.::6:x@jSc&gt;&#34;EU1.I}Fu187{(IacXQ9K?W+t,l&#34;w-b`(US7y^%~HLH0E&#39;o)8sa*#&amp;H|DOQw&#39;TN#puOx2Ylq=,8I&amp;)',
      'Expected ContactEmail value to be equals to NKY&amp;TnQ&lt;{=[E2Q*lj#!lOsm&amp;J_Uq;]S&#34;Dy8k,[%~a3P&#34;8Aj:D5.::6:x@jSc&gt;&#34;EU1.I}Fu187{(IacXQ9K?W+t,l&#34;w-b`(US7y^%~HLH0E&#39;o)8sa*#&amp;H|DOQw&#39;TN#puOx2Ylq=,8I&amp;)'
    );
    const selectedIsActive = customerUpdatePage.getIsActiveInput();
    if (await selectedIsActive.isSelected()) {
      await customerUpdatePage.getIsActiveInput().click();
      expect(await customerUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive not to be selected').to.be.false;
    } else {
      await customerUpdatePage.getIsActiveInput().click();
      expect(await customerUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive to be selected').to.be.true;
    }
    expect(await customerUpdatePage.getTimeCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected timeCreated value to be equals to 2000-12-31'
    );
    expect(await customerUpdatePage.getTimeModifiedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected timeModified value to be equals to 2000-12-31'
    );
    expect(await customerUpdatePage.getUserIdCreatedInput()).to.eq('5', 'Expected userIdCreated value to be equals to 5');
    expect(await customerUpdatePage.getUserIdModifiedInput()).to.eq('5', 'Expected userIdModified value to be equals to 5');
    await customerUpdatePage.save();
    expect(await customerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await customerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Customer', async () => {
    const nbButtonsBeforeDelete = await customerComponentsPage.countDeleteButtons();
    await customerComponentsPage.clickOnLastDeleteButton();

    customerDeleteDialog = new CustomerDeleteDialog();
    expect(await customerDeleteDialog.getDialogTitle()).to.eq('akApp.customer.delete.question');
    await customerDeleteDialog.clickOnConfirmButton();

    expect(await customerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
