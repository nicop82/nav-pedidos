export class PedidoDTO {
  constructor(
    public id: string,
    public nombre: string,
    public monto: string,
    public descuento : string) { }
}