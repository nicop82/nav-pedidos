export class Pedido {
  constructor(
    public id: number,
    public nombre: string,
    public monto: number,
    public descuento : number) { }
}