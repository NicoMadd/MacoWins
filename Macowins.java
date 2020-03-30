import java.util.List;
import java.util.stream.Collector;


class Macowins{

    int stock;
    List<Venta> ventas;

    Macowins(int unStock){
        this.stock  = unStock;
        this.ventas = new LinkedList<>();
    }

    private double gananciaDeVentas(List<Venta> unasVentas){
        return ventas.stream().map( venta -> venta.ganacia() ).collect(Collector.toList()).sum();
    }

    public double gananciaTotal(){
        return gananciaDeVentas(ventas);
    }

    public double gananciaDelDia(Date unDia){
        return gananciaDeVentas(filtrarVentasPorFecha(unDia));
    }

    private boolean hayStock(int unaCantidad){
        return stock>=unaCantidad;
    }

    public boolean validarVenta(Venta venta){
        return hayStock(venta.cantidadDePrendas());
    }

    public realizarVenta(Venta venta){
        stock -= venta.cantidadDePrendas();
        ventas.add(venta);
    }

    private List<Ventas> filtrarVentasPorFecha(Date unDia){
        return ventas.stream().filter( venta -> venta.fecha == unDia).collect(Collector.toList());
    }


class Venta{
    List<Prenda> prendas;
    int cuotas;
    TipoDePago tipoDePago;
    Date fecha;
    
    
    Venta(TipoDePago unTipoDePago,int cuotas){
        this.tipoDePago = unTipoDePago;
        this.cuotas = cuotas;
        this.fecha = new LocalDate();
        this.prendas = new List();
    }

    public int cantidadDePrendas(){
        return ventas.size();    }

    private int getCuotas(){
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


    public double ganacia(){
        return this.valorTotalPrendas() + tipoDePago.recargo(this);
    }
    private double valorTotalDePrendas(){
        return prendas.stream().map(prenda -> prenda.precio()).collect(Collector.toList()).sum();
    }

class Prenda{
    public static int prendaId=0;
    TipoDePrenda tipoDePrenda;
    Estado estado;
    Prenda(){
        this.id=Prenda.prendaId++;
    }

    public precio(){
        return estado.modificacion(tipoDePrenda.precio());
    }
}

class TipoDePrenda{
    public static int tipoPrendaId=0;
    int id;
    double precio;
    TipoDePrenda(double precio){
        this.id=TipoDePrenda.prendaId++;
        this.precio=precio;
    }
    
    public double precio(){
        return precio;
    }

    public void cambiarPrecio(double unPrecio){
        precio=unPrecio;
    }
}

interface Estado{
    modificacion(double unPrecio);
}

class Nueva extends Estado{
    public modificacion(double unPrecio){
        return unPrecio;
    }
}

class Promocion extends Estado{
    double valor;
    
    public modificacion(double unPrecio){
        return unPrecio-valor;
    }
}      

class Liquidacion extends Estado{
    public modificacion(double unPrecio){
        return unPrecio*0.5;
    }
}

interface TipoDePago{
    recargo(Venta venta);
}

class Tarjeta extends TipoDePago{
    double coeficiente;
    public double recargo(Venta venta){
        return venta.cuotas() * coeficiente + 0.01 * venta.valorTotalDePrendas();
    }
}

class Efectivo extends TipoDePago{
    public double recargo(Venta venta){
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
///Los pagos en Efectivo se anotan con cuota=1.

///Descarte la codificacion de todos los getters y setters que no se hacen uso.

///En la clase Macowins declare un metodo de hayStock que retorna siempre un true.
 Se da por entender que si hay stock, se valida la venta. Lo deje permanentemente 
 true ya que queria incluir esa consideracion al sistema y como no se tienen en cuenta dentro del contexto
de la situacion asumi stock infinito.

///Considere Macowins como una clase, de esta forma se podria asumir que cada clase Macowins es un
local de Macowins. Capaz, de momento no seria necesario pero a futuro probablemente seria conveniente
si se implenta el sistema a una escala mayor.




*/