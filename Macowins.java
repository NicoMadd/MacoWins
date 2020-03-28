import java.util.List;


class Macowins{

    int stock;
    List<Venta> ventas;

    Macowins(int unStock){
        this.stock  = unStock;
        this.ventas = new LinkedList<>();
    }

    private float gananciaDeVentas(List<Venta> unasVentas){
        return ventas.map( venta -> venta.ganacia() ).sum();
    }

    public float gananciaTotal(){
        return gananciaDeVentas(ventas);
    }

    public float gananciaDeDia(Date unDia){
        return gananciaDeVentas(filtrarVentasPorFecha(unDia));
    }

    private boolean hayStock(){
        return true;
    }

    public boolean validarVenta(Venta venta){
        if(hayStock()){
        return venta.cantidadDePrendas() >= stock;
        }
    }

    public realizarVenta(Venta venta){
        stock -= venta.cantidadDePrendas();
        ventas.add(venta);
    }

    private List<Ventas> filtrarVentasPorFecha(Date unDia){
        return ventas.filter( venta -> venta.fecha == unDia);
    }


class Venta{
    int cuotas;
    TipoDePago tipoDePago;
    Date fecha;
    List<Prenda> prendas;
    
    Venta(TipoDePago unTipoDePago,int cuotas){
        this.tipoDePago = unTipoDePago;
        this.cuotas = cuotas;
        this.fecha = new LocalDate();
        this.prendas = new List();
    }

    private int cuotas(){
        return cuotas;
    }

    public void agregarPrenda(Prenda prenda){
        prendas.add(prenda);
    }
    
    public void realizarVenta(){
        if(Macowins.validarVenta(this)){
            Macowins.realizarVenta(this);
        }
    }

    public Date getFecha(){
        return fecha;
    }


    public float ganacia(){
        return this.valorTotalPrendas() + tipoDePago.recargo(this);
    }
    private float valorTotalDePrendas(){
        return prendas.map(prenda -> prenda.precio()).sum();
    }

class Prenda{
    TipoDePrenda tipoDePrenda;
    Estado estado;

    public precio(){
        return estado.modificacion(tipoDePrenda.precio());
    }
}

class TipoDePrenda{
    public static int prendaId;
    TipoDePrenda(float precio){
        this.id=TipoDePrenda.prendaId;
        TipoDePrenda.prendaId++;
        this.precio=precio;
    }
    float precio;
    public float precio(){
        return precio;
    }

    public void cambiarPrecio(float unPrecio){
        precio=unPrecio;
    }
}


class Nueva{
    public modificacion(float unPrecio){
        return unPrecio;
    }
}

class Promocion{
    float valor;
    
    public modificacion(float unPrecio){
        return unPrecio-valor;
    }
}      

class Liquidacion{
    public modificacion(float unPrecio){
        return unPrecio*0.5;
    }
}


class Tarjeta{
    float coeficiente;
    public float recargo(Venta venta){
        return venta.cuotas() * coeficiente + 0.01 * venta.valorTotalDePrendas();
    }
}

class Efectivo{
    public float recargo(Venta venta){
        return 0;
    }
}

/*
Requeremientos:

REQUERIMIENTOS FUNCIONALES: 
1-Saber precio,estado y tipo de una prenda
2-Conocer la ganancia diaria de las prendas vendidas (Pregunta al usuario, el recargo por cuotas adicional se incorpora dentro del precio neto?
O es un beneficio para el banco por brindar el servicio?
En ese caso desea tener un registro del precio bruto(sin este adicional) y uno neto?
O desea tener el precio neto final y almacenar el procentaje de ganancia del banco?)
REQUERIMIENTOS NO FUNCIONALES: No encontra ninguno



Aclaraciones:
/// Los pagos en Efectivo se anotan con cuota=1.
/// Descarte la codificacion de todos los getters y setters que no se hacen uso.


*/