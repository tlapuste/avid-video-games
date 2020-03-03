import { element, by, ElementFinder } from 'protractor';

export default class BlogPostUpdatePage {
  pageTitle: ElementFinder = element(by.id('avidApp.blogPost.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  titleInput: ElementFinder = element(by.css('input#blog-post-title'));
  bodyInput: ElementFinder = element(by.css('input#blog-post-body'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTitleInput(title) {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput() {
    return this.titleInput.getAttribute('value');
  }

  async setBodyInput(body) {
    await this.bodyInput.sendKeys(body);
  }

  async getBodyInput() {
    return this.bodyInput.getAttribute('value');
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
