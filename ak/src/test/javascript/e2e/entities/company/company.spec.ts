import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CompanyComponentsPage, CompanyDeleteDialog, CompanyUpdatePage } from './company.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Company e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyComponentsPage: CompanyComponentsPage;
  let companyUpdatePage: CompanyUpdatePage;
  let companyDeleteDialog: CompanyDeleteDialog;
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

  it('should load Companies', async () => {
    await navBarPage.goToEntity('company');
    companyComponentsPage = new CompanyComponentsPage();
    await browser.wait(ec.visibilityOf(companyComponentsPage.title), 5000);
    expect(await companyComponentsPage.getTitle()).to.eq('akApp.company.home.title');
  });

  it('should load create Company page', async () => {
    await companyComponentsPage.clickOnCreateButton();
    companyUpdatePage = new CompanyUpdatePage();
    expect(await companyUpdatePage.getPageTitle()).to.eq('akApp.company.home.createOrEditLabel');
    await companyUpdatePage.cancel();
  });

  it('should create and save Companies', async () => {
    const nbButtonsBeforeCreate = await companyComponentsPage.countDeleteButtons();

    await companyComponentsPage.clickOnCreateButton();
    await promise.all([
      companyUpdatePage.setNameInput('name'),
      companyUpdatePage.setAddressInput('address'),
      companyUpdatePage.setLogoInput(absolutePath),
      companyUpdatePage.setEmailInput(
        'pi{zie$+iIZwH,CPtq&#39;z-`|}0qbJ@Sei_Lm0[&amp;3mec-zI?}ooD\fze`-&gt;k{gyjkJ|AQ9G+eR.?d/0&gt;aM`o0&lt;7MT+.&lt;rw0:3X}M{~vH{SV*e&lt;TQ|5W&amp;.`Aj^/=rQu&#34;zU5g&#39;h.OU?&amp;3}AE%U/MBU4Bh1{'
      ),
      companyUpdatePage.setStartDateInput('2000-12-31'),
      companyUpdatePage.setNumOfUsersInput('5'),
      companyUpdatePage.setTypeInput('type'),
      companyUpdatePage.setTimeCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      companyUpdatePage.setTimeModifiedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      companyUpdatePage.setUserIdInput('5'),
      companyUpdatePage.industrySelectLastOption(),
      companyUpdatePage.provinceSelectLastOption()
    ]);
    expect(await companyUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await companyUpdatePage.getAddressInput()).to.eq('address', 'Expected Address value to be equals to address');
    expect(await companyUpdatePage.getLogoInput()).to.endsWith(fileNameToUpload, 'Expected Logo value to be end with ' + fileNameToUpload);
    expect(await companyUpdatePage.getEmailInput()).to.eq(
      'pi{zie$+iIZwH,CPtq&#39;z-`|}0qbJ@Sei_Lm0[&amp;3mec-zI?}ooD\fze`-&gt;k{gyjkJ|AQ9G+eR.?d/0&gt;aM`o0&lt;7MT+.&lt;rw0:3X}M{~vH{SV*e&lt;TQ|5W&amp;.`Aj^/=rQu&#34;zU5g&#39;h.OU?&amp;3}AE%U/MBU4Bh1{',
      'Expected Email value to be equals to pi{zie$+iIZwH,CPtq&#39;z-`|}0qbJ@Sei_Lm0[&amp;3mec-zI?}ooD\fze`-&gt;k{gyjkJ|AQ9G+eR.?d/0&gt;aM`o0&lt;7MT+.&lt;rw0:3X}M{~vH{SV*e&lt;TQ|5W&amp;.`Aj^/=rQu&#34;zU5g&#39;h.OU?&amp;3}AE%U/MBU4Bh1{'
    );
    expect(await companyUpdatePage.getStartDateInput()).to.eq('2000-12-31', 'Expected startDate value to be equals to 2000-12-31');
    expect(await companyUpdatePage.getNumOfUsersInput()).to.eq('5', 'Expected numOfUsers value to be equals to 5');
    expect(await companyUpdatePage.getTypeInput()).to.eq('type', 'Expected Type value to be equals to type');
    const selectedIsActive = companyUpdatePage.getIsActiveInput();
    if (await selectedIsActive.isSelected()) {
      await companyUpdatePage.getIsActiveInput().click();
      expect(await companyUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive not to be selected').to.be.false;
    } else {
      await companyUpdatePage.getIsActiveInput().click();
      expect(await companyUpdatePage.getIsActiveInput().isSelected(), 'Expected isActive to be selected').to.be.true;
    }
    expect(await companyUpdatePage.getTimeCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected timeCreated value to be equals to 2000-12-31'
    );
    expect(await companyUpdatePage.getTimeModifiedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected timeModified value to be equals to 2000-12-31'
    );
    expect(await companyUpdatePage.getUserIdInput()).to.eq('5', 'Expected userId value to be equals to 5');
    await companyUpdatePage.save();
    expect(await companyUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await companyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Company', async () => {
    const nbButtonsBeforeDelete = await companyComponentsPage.countDeleteButtons();
    await companyComponentsPage.clickOnLastDeleteButton();

    companyDeleteDialog = new CompanyDeleteDialog();
    expect(await companyDeleteDialog.getDialogTitle()).to.eq('akApp.company.delete.question');
    await companyDeleteDialog.clickOnConfirmButton();

    expect(await companyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
