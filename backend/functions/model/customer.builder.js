const Credit=require('./credit.model'); 
const CustomerProfile=require('./customerProfile.model');
const CustomerInfo=require('./custonerInfo.model');
const Customer=require('./customer.model');

class CustomerBuilder{

    //TODO logic to return severasl types of data
    build(){
        const cs=[
            new Credit("Card",
            "https://www.bancoazteca.com.mx/content/dam/azteca/producto/tarjeta/escuela-de-clientes/img_banner.png"
            ,"Tarjeta de Credito Clasica","XXXX XXXX XXXX 4313",5000,10000),
            new Credit("Card","https://www.bancoazteca.com.mx/content/dam/azteca/producto/tarjeta/tdc-oro-garantizada/1901/tarjeta-de-credito-oro-garantizada-banner.png",
            "Tarjeta de Credito Oro","XXXX XXXX XXXX 4312",6000,10000),
            new Credit("Personal",
            "https://www.bancoazteca.com.mx/content/dam/azteca/sitio/2019/productos/credito/tarjetas_de_producto/imagen-tarjeta-de-producto-credito-nomina-azteca.jpg"
            ,"Credito de Nómina","XXXX4312",6000,120000),
        ]
        const cp = new CustomerProfile("1001001010",150000);
        const ci = new CustomerInfo("Agustin","Ramirez","Sr",{
            street:"Priv Heliotrospos",
            number:"202",
            city:"Toluca",
            state:"México",
            country:"México"
        });
        return new Customer(ci,cp,cs);
    }
}

module.exports=CustomerBuilder;