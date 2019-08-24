const CustomerBuilder= require('../model/customer.builder');

class CustomerService{


    getCustomer(){
        return new CustomerBuilder().build();
    }

    getProducts(){
        
    }

}
module.exports=CustomerService;