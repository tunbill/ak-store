import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmployeeComponentsPage, EmployeeDeleteDialog, EmployeeUpdatePage } from './employee.page-object';

const expect = chai.expect;

describe('Employee e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeComponentsPage: EmployeeComponentsPage;
  let employeeUpdatePage: EmployeeUpdatePage;
  let employeeDeleteDialog: EmployeeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Employees', async () => {
    await navBarPage.goToEntity('employee');
    employeeComponentsPage = new EmployeeComponentsPage();
    await browser.wait(ec.visibilityOf(employeeComponentsPage.title), 5000);
    expect(await employeeComponentsPage.getTitle()).to.eq('akApp.employee.home.title');
  });

  it('should load create Employee page', async () => {
    await employeeComponentsPage.clickOnCreateButton();
    employeeUpdatePage = new EmployeeUpdatePage();
    expect(await employeeUpdatePage.getPageTitle()).to.eq('akApp.employee.home.createOrEditLabel');
    await employeeUpdatePage.cancel();
  });

  it('should create and save Employees', async () => {
    const nbButtonsBeforeCreate = await employeeComponentsPage.countDeleteButtons();

    await employeeComponentsPage.clickOnCreateButton();
    await promise.all([
      employeeUpdatePage.setCompanyIdInput('5'),
      employeeUpdatePage.setCodeInput('code'),
      employeeUpdatePage.setFullNameInput('fullName'),
      employeeUpdatePage.setSexInput('5'),
      employeeUpdatePage.setBirthdayInput('2000-12-31'),
      employeeUpdatePage.setIdentityCardInput('identityCard'),
      employeeUpdatePage.setIdentityDateInput('2000-12-31'),
      employeeUpdatePage.setIdentityIssueInput('identityIssue'),
      employeeUpdatePage.setPositionInput('position'),
      employeeUpdatePage.setTaxCodeInput('taxCode'),
      employeeUpdatePage.setSalaryInput('5'),
      employeeUpdatePage.setSalaryRateInput('5'),
      employeeUpdatePage.setSalarySecurityInput('5'),
      employeeUpdatePage.setNumOfDependsInput('5'),
      employeeUpdatePage.setPhoneInput('phone'),
      employeeUpdatePage.setMobileInput('mobile'),
      employeeUpdatePage.setEmailInput(
        'Xosajs*yIX~y}ujD!;6N|A,bW~ep*$2+UoK&amp;!&gt;Dz{#5DxX&amp;2A@UC[eA0Z50FZ&#34;J]&lt;D++Ygtfa.|L*M{}DvX`ic.wgZQx?m#&gt;&#34;8zc{ndjEZd|E^|NJWE&#39;+hxF(d.S;AEp&amp;o$5/&#39;&#39;eNNWysCYMkLDHK&gt;y-|G3B+i`-BU7{9Cz&#39;-g&lt;I2YD*LA*h$v&#39;m&lt;'
      ),
      employeeUpdatePage.setBankAccountInput('bankAccount'),
      employeeUpdatePage.setBankNameInput('bankName'),
      employeeUpdatePage.setNodesInput('nodes'),
      employeeUpdatePage.setTimeCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      employeeUpdatePage.setTimeModifiedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      employeeUpdatePage.setUserIdCreatedInput('5'),
      employeeUpdatePage.setUserIdModifiedInput('5'),
      employeeUpdatePage.departmentSelectLastOption()
    ]);
    expect(await employeeUpdatePage.getCompanyIdInput()).to.eq('5', 'Expected companyId value to be equals to 5');
    expect(await employeeUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await employeeUpdatePage.getFullNameInput()).to.eq('fullName', 'Expected FullName value to be equals to fullName');
    expect(await employeeUpdatePage.getSexInput()).to.eq('5', 'Expected sex value to be equals to 5');
    expect(await employeeUpdatePage.getBirthdayInput()).to.eq('2000-12-31', 'Expected birthday value to be equals to 2000-12-31');
    expect(await employeeUpdatePage.getIdentityCardInput()).to.eq(
      'identityCard',
      'Expected IdentityCard value to be equals to identityCard'
    );
    expect(await employeeUpdatePage.getIdentityDateInput()).to.eq('2000-12-31', 'Expected identityDate value to be equals to 2000-12-31');
    expect(await employeeUpdatePage.getIdentityIssueInput()).to.eq(
      'identityIssue',
      'Expected IdentityIssue value to be equals to identityIssue'
    );
    expect(await employeeUpdatePage.getPositionInput()).to.eq('position', 'Expected Position value to be equals to position');
    expect(await employeeUpdatePage.getTaxCodeInput()).to.eq('taxCode', 'Expected TaxCode value to be equals to taxCode');
    expect(await employeeUpdatePage.getSalaryInput()).to.eq('5', 'Expected salary value to be equals to 5');
    expect(await employeeUpdatePage.getSalaryRateInput()).to.eq('5', 'Expected salaryRate value to be equals to 5');
    expect(await employeeUpdatePage.getSalarySecurityInput()).to.eq('5', 'Expected salarySecurity value to be equals to 5');
    expect(await employeeUpdatePage.getNumOfDependsInput()).to.eq('5', 'Expected numOfDepends value to be equals to 5');
    expect(await employeeUpdatePage.getPhoneInput()).to.eq('phone', 'Expected Phone value to be equals to phone');
    expect(await employeeUpdatePage.getMobileInput()).to.eq('mobile', 'Expected Mobile value to be equals to mobile');
    expect(await employeeUpdatePage.getEmailInput()).to.eq(
      'Xosajs*yIX~y}ujD!;6N|A,bW~ep*$2+UoK&amp;!&gt;Dz{#5DxX&amp;2A@UC[eA0Z50FZ&#34;J]&lt;D++Ygtfa.|L*M{}DvX`ic.wgZQx?m#&gt;&#34;8zc{ndjEZd|E^|NJWE&#39;+hxF(d.S;AEp&amp;o$5/&#39;&#39;eNNWysCYMkLDHK&gt;y-|G3B+i`-BU7{9Cz&#39;-g&lt;I2YD*LA*h$v&#39;m&lt;',
      'Expected Email value to be equals to Xosajs*yIX~y}ujD!;6N|A,bW~ep*$2+UoK&amp;!&gt;Dz{#5DxX&amp;2A@UC[eA0Z50FZ&#34;J]&lt;D++Ygtfa.|L*M{}DvX`ic.wgZQx?m#&gt;&#34;8zc{ndjEZd|E^|NJWE&#39;+hxF(d.S;AEp&amp;o$5/&#39;&#39;eNNWysCYMkLDHK&gt;y-|G3B+i`-BU7{9Cz&#39;-g&lt;I2YD*LA*h$v&#39;m&lt;'
    );
    expect(await employeeUpdatePage.getBankAccountInput()).to.eq('bankAccount', 'Expected BankAccount value to be equals to bankAccount');
    expect(await employeeUpdatePage.getBankNameInput()).to.eq('bankName', 'Expected BankName value to be equals to bankName');
    expect(await employeeUpdatePage.getNodesInput()).to.eq('nodes', 'Expected Nodes value to be equals to nodes');
    const selectedIsActive = employeeUpdatePage.getIsActiveInput();
    if (await selectedIsActive.isSelected()) {
      await employeeUpdatePage.getIsActiveInput().click();
      expect(await employeeUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive not to be selected').to.be.false;
    } else {
      await employeeUpdatePage.getIsActiveInput().click();
      expect(await employeeUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive to be selected').to.be.true;
    }
    expect(await employeeUpdatePage.getTimeCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected timeCreated value to be equals to 2000-12-31'
    );
    expect(await employeeUpdatePage.getTimeModifiedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected timeModified value to be equals to 2000-12-31'
    );
    expect(await employeeUpdatePage.getUserIdCreatedInput()).to.eq('5', 'Expected userIdCreated value to be equals to 5');
    expect(await employeeUpdatePage.getUserIdModifiedInput()).to.eq('5', 'Expected userIdModified value to be equals to 5');
    await employeeUpdatePage.save();
    expect(await employeeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Employee', async () => {
    const nbButtonsBeforeDelete = await employeeComponentsPage.countDeleteButtons();
    await employeeComponentsPage.clickOnLastDeleteButton();

    employeeDeleteDialog = new EmployeeDeleteDialog();
    expect(await employeeDeleteDialog.getDialogTitle()).to.eq('akApp.employee.delete.question');
    await employeeDeleteDialog.clickOnConfirmButton();

    expect(await employeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
