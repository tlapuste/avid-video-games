import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import BlogPostComponentsPage, { BlogPostDeleteDialog } from './blog-post.page-object';
import BlogPostUpdatePage from './blog-post-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible
} from '../../util/utils';

const expect = chai.expect;

describe('BlogPost e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let blogPostComponentsPage: BlogPostComponentsPage;
  let blogPostUpdatePage: BlogPostUpdatePage;
  let blogPostDeleteDialog: BlogPostDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load BlogPosts', async () => {
    await navBarPage.getEntityPage('blog-post');
    blogPostComponentsPage = new BlogPostComponentsPage();
    expect(await blogPostComponentsPage.title.getText()).to.match(/Blog Posts/);

    expect(await blogPostComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([blogPostComponentsPage.noRecords, blogPostComponentsPage.table]);

    beforeRecordsCount = (await isVisible(blogPostComponentsPage.noRecords)) ? 0 : await getRecordsCount(blogPostComponentsPage.table);
  });

  it('should load create BlogPost page', async () => {
    await blogPostComponentsPage.createButton.click();
    blogPostUpdatePage = new BlogPostUpdatePage();
    expect(await blogPostUpdatePage.getPageTitle().getText()).to.match(/Create or edit a BlogPost/);
    await blogPostUpdatePage.cancel();
  });

  it('should create and save BlogPosts', async () => {
    await blogPostComponentsPage.createButton.click();
    await blogPostUpdatePage.setTitleInput('title');
    expect(await blogPostUpdatePage.getTitleInput()).to.match(/title/);
    await blogPostUpdatePage.setBodyInput('body');
    expect(await blogPostUpdatePage.getBodyInput()).to.match(/body/);
    await waitUntilDisplayed(blogPostUpdatePage.saveButton);
    await blogPostUpdatePage.save();
    await waitUntilHidden(blogPostUpdatePage.saveButton);
    expect(await isVisible(blogPostUpdatePage.saveButton)).to.be.false;

    expect(await blogPostComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(blogPostComponentsPage.table);

    await waitUntilCount(blogPostComponentsPage.records, beforeRecordsCount + 1);
    expect(await blogPostComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last BlogPost', async () => {
    const deleteButton = blogPostComponentsPage.getDeleteButton(blogPostComponentsPage.records.last());
    await click(deleteButton);

    blogPostDeleteDialog = new BlogPostDeleteDialog();
    await waitUntilDisplayed(blogPostDeleteDialog.deleteModal);
    expect(await blogPostDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/avidApp.blogPost.delete.question/);
    await blogPostDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(blogPostDeleteDialog.deleteModal);

    expect(await isVisible(blogPostDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([blogPostComponentsPage.noRecords, blogPostComponentsPage.table]);

    const afterCount = (await isVisible(blogPostComponentsPage.noRecords)) ? 0 : await getRecordsCount(blogPostComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
