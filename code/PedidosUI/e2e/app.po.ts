import { browser, element, by } from 'protractor';

export class HomePage {
  navigateTo() {
    return browser.get('/home');
  }

  getParagraphText() {
    return element(by.css('app-root h1')).getText();
  }

  getCrearPedidoButton(){
    return element(by.css(".btn-primary"));
  }
}
