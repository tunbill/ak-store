import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DepartmentComponentsPage, DepartmentDeleteDialog, DepartmentUpdatePage } from './department.page-object';

const expect = chai.expect;

describe('Department e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let departmentComponentsPage: DepartmentComponentsPage;
  let departmentUpdatePage: DepartmentUpdatePage;
  let departmentDeleteDialog: DepartmentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Departments', async () => {
    await navBarPage.goToEntity('department');
    departmentComponentsPage = new DepartmentComponentsPage();
    await browser.wait(ec.visibilityOf(departmentComponentsPage.title), 5000);
    expect(await departmentComponentsPage.getTitle()).to.eq('akApp.department.home.title');
  });

  it('should load create Department page', async () => {
    await departmentComponentsPage.clickOnCreateButton();
    departmentUpdatePage = new DepartmentUpdatePage();
    expect(await departmentUpdatePage.getPageTitle()).to.eq('akApp.department.home.createOrEditLabel');
    await departmentUpdatePage.cancel();
  });

  it('should create and save Departments', async () => {
    const nbButtonsBeforeCreate = await departmentComponentsPage.countDeleteButtons();

    await departmentComponentsPage.clickOnCreateButton();
    await promise.all([
      departmentUpdatePage.setCompanyIdInput('5'),
      departmentUpdatePage.setCodeInput('code'),
      departmentUpdatePage.setNameInput('name'),
      departmentUpdatePage.setDescriptionInput('description')
    ]);
    expect(await departmentUpdatePage.getCompanyIdInput()).to.eq('5', 'Expected companyId value to be equals to 5');
    expect(await departmentUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await departmentUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await departmentUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    const selectedIsActive = departmentUpdatePage.getIsActiveInput();
    if (await selectedIsActive.isSelected()) {
      await departmentUpdatePage.getIsActiveInput().click();
      expect(await departmentUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive not to be selected').to.be.false;
    } else {
      await departmentUpdatePage.getIsActiveInput().click();
      expect(await departmentUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive to be selected').to.be.true;
    }
    await departmentUpdatePage.save();
    expect(await departmentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await departmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Department', async () => {
    const nbButtonsBeforeDelete = await departmentComponentsPage.countDeleteButtons();
    await departmentComponentsPage.clickOnLastDeleteButton();

    departmentDeleteDialog = new DepartmentDeleteDialog();
    expect(await departmentDeleteDialog.getDialogTitle()).to.eq('akApp.department.delete.question');
    await departmentDeleteDialog.clickOnConfirmButton();

    expect(await departmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
