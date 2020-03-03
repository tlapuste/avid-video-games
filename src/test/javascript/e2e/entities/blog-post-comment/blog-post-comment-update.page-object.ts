import { element, by, ElementFinder } from 'protractor';

export default class BlogPostCommentUpdatePage {
  pageTitle: ElementFinder = element(by.id('avidApp.blogPostComment.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  bodyInput: ElementFinder = element(by.css('input#blog-post-comment-body'));
  commenterSelect: ElementFinder = element(by.css('select#blog-post-comment-commenter'));
  commentSelect: ElementFinder = element(by.css('select#blog-post-comment-comment'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setBodyInput(body) {
    await this.bodyInput.sendKeys(body);
  }

  async getBodyInput() {
    return this.bodyInput.getAttribute('value');
  }

  async commenterSelectLastOption() {
    await this.commenterSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async commenterSelectOption(option) {
    await this.commenterSelect.sendKeys(option);
  }

  getCommenterSelect() {
    return this.commenterSelect;
  }

  async getCommenterSelectedOption() {
    return this.commenterSelect.element(by.css('option:checked')).getText();
  }

  async commentSelectLastOption() {
    await this.commentSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async commentSelectOption(option) {
    await this.commentSelect.sendKeys(option);
  }

  getCommentSelect() {
    return this.commentSelect;
  }

  async getCommentSelectedOption() {
    return this.commentSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }
}
