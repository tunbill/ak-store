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
        'o`I2T2w&lt;Ne,N;Nt8ZC4p&amp;3C)g`Nr}Zk4&amp;&gt;P&#34;LsS~ds9Uo+~^8r_y,=]B2zxLDi@Ml*.kc/M~!a&gt;*p6+FMTAqDmhHkN#*Cs&lt;*_uQ$ZqsU&lt;pY5J&lt;MBx=fU&gt;aHjAU`NG3!vWz3Bm?2:A;.m=D`Xs&#39;jbzkX9k/bt7iVOf~k)euR+pWT}{z1QFbmS`Y$+]KE!dAy&#34;ar2&#39;FZ%M;v{g:!;rpB8b!\tV7;:nd^t&#34;&gt;pJjEVd'
      ),
      employeeUpdatePage.setBankAccountInput('bankAccount'),
      employeeUpdatePage.setBankNameInput('bankName'),
      employeeUpdatePage.setNodesInput('nodes'),
      employeeUpdatePage.setTimeCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      employeeUpdatePage.setTimeModifiedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      employeeUpdatePage.setUserIdCreatedInput('5'),
      employeeUpdatePage.setUserIdModifiedInput('5'),
      employeeUpdatePage.departmentSelectLastOption(),
      employeeUpdatePage.companySelectLastOption()
    ]);
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
      'o`I2T2w&lt;Ne,N;Nt8ZC4p&amp;3C)g`Nr}Zk4&amp;&gt;P&#34;LsS~ds9Uo+~^8r_y,=]B2zxLDi@Ml*.kc/M~!a&gt;*p6+FMTAqDmhHkN#*Cs&lt;*_uQ$ZqsU&lt;pY5J&lt;MBx=fU&gt;aHjAU`NG3!vWz3Bm?2:A;.m=D`Xs&#39;jbzkX9k/bt7iVOf~k)euR+pWT}{z1QFbmS`Y$+]KE!dAy&#34;ar2&#39;FZ%M;v{g:!;rpB8b!\tV7;:nd^t&#34;&gt;pJjEVd',
      'Expected Email value to be equals to o`I2T2w&lt;Ne,N;Nt8ZC4p&amp;3C)g`Nr}Zk4&amp;&gt;P&#34;LsS~ds9Uo+~^8r_y,=]B2zxLDi@Ml*.kc/M~!a&gt;*p6+FMTAqDmhHkN#*Cs&lt;*_uQ$ZqsU&lt;pY5J&lt;MBx=fU&gt;aHjAU`NG3!vWz3Bm?2:A;.m=D`Xs&#39;jbzkX9k/bt7iVOf~k)euR+pWT}{z1QFbmS`Y$+]KE!dAy&#34;ar2&#39;FZ%M;v{g:!;rpB8b!\tV7;:nd^t&#34;&gt;pJjEVd'
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
