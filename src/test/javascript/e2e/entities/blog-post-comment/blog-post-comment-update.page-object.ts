import { element, by, ElementFinder } from 'protractor';

export default class BlogPostCommentUpdatePage {
  pageTitle: ElementFinder = element(by.id('avidApp.blogPostComment.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  bodyInput: ElementFinder = element(by.css('input#blog-post-comment-body'));
  commenterSelect: ElementFinder = element(by.css('select#blog-post-comment-commenter'));
  postSelect: ElementFinder = element(by.css('select#blog-post-comment-post'));

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

  async postSelectLastOption() {
    await this.postSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async postSelectOption(option) {
    await this.postSelect.sendKeys(option);
  }

  getPostSelect() {
    return this.postSelect;
  }

  async getPostSelectedOption() {
    return this.postSelect.element(by.css('option:checked')).getText();
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
