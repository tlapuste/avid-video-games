import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import BlogPostCommentComponentsPage, { BlogPostCommentDeleteDialog } from './blog-post-comment.page-object';
import BlogPostCommentUpdatePage from './blog-post-comment-update.page-object';
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

describe('BlogPostComment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let blogPostCommentComponentsPage: BlogPostCommentComponentsPage;
  let blogPostCommentUpdatePage: BlogPostCommentUpdatePage;
  let blogPostCommentDeleteDialog: BlogPostCommentDeleteDialog;
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

  it('should load BlogPostComments', async () => {
    await navBarPage.getEntityPage('blog-post-comment');
    blogPostCommentComponentsPage = new BlogPostCommentComponentsPage();
    expect(await blogPostCommentComponentsPage.title.getText()).to.match(/Blog Post Comments/);

    expect(await blogPostCommentComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([blogPostCommentComponentsPage.noRecords, blogPostCommentComponentsPage.table]);

    beforeRecordsCount = (await isVisible(blogPostCommentComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(blogPostCommentComponentsPage.table);
  });

  it('should load create BlogPostComment page', async () => {
    await blogPostCommentComponentsPage.createButton.click();
    blogPostCommentUpdatePage = new BlogPostCommentUpdatePage();
    expect(await blogPostCommentUpdatePage.getPageTitle().getText()).to.match(/Create or edit a BlogPostComment/);
    await blogPostCommentUpdatePage.cancel();
  });

  it('should create and save BlogPostComments', async () => {
    await blogPostCommentComponentsPage.createButton.click();
    await blogPostCommentUpdatePage.setBodyInput('body');
    expect(await blogPostCommentUpdatePage.getBodyInput()).to.match(/body/);
    await blogPostCommentUpdatePage.commenterSelectLastOption();
    await blogPostCommentUpdatePage.commentSelectLastOption();
    await waitUntilDisplayed(blogPostCommentUpdatePage.saveButton);
    await blogPostCommentUpdatePage.save();
    await waitUntilHidden(blogPostCommentUpdatePage.saveButton);
    expect(await isVisible(blogPostCommentUpdatePage.saveButton)).to.be.false;

    expect(await blogPostCommentComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(blogPostCommentComponentsPage.table);

    await waitUntilCount(blogPostCommentComponentsPage.records, beforeRecordsCount + 1);
    expect(await blogPostCommentComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last BlogPostComment', async () => {
    const deleteButton = blogPostCommentComponentsPage.getDeleteButton(blogPostCommentComponentsPage.records.last());
    await click(deleteButton);

    blogPostCommentDeleteDialog = new BlogPostCommentDeleteDialog();
    await waitUntilDisplayed(blogPostCommentDeleteDialog.deleteModal);
    expect(await blogPostCommentDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/avidApp.blogPostComment.delete.question/);
    await blogPostCommentDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(blogPostCommentDeleteDialog.deleteModal);

    expect(await isVisible(blogPostCommentDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([blogPostCommentComponentsPage.noRecords, blogPostCommentComponentsPage.table]);

    const afterCount = (await isVisible(blogPostCommentComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(blogPostCommentComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
