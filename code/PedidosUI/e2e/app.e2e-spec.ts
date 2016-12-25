import { HomePage } from './app.po';
import { browser, element, by } from 'protractor';

describe('pedidos-ui App', function() {
  let page: HomePage;

  beforeEach(() => {
    page = new HomePage();
  });

  it('deberia aparecer el texto Monto', () => {
    page.navigateTo();
    expect(page.getCrearPedidoButton().isDisplayed()).toBe(true);
  });
});
