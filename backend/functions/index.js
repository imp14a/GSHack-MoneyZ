const functions = require('firebase-functions');
const CustomerService = require('./services/customer.service');

 exports.getCustomer = functions.https.onRequest((request, response) => {
    response.send(new CustomerService().getCustomer());
});



//https://www.bancoazteca.com.mx/content/dam/azteca/producto/tarjeta/escuela-de-clientes/img_banner.png