const CustomerBuilder= require('../model/customer.builder');
var crypto = require('crypto');
var md5sum = crypto.createHash('md5');
// Import Admin SDK
var admin = require("firebase-admin");
admin.initializeApp({
    apiKey: "AIzaSyBlXgDGLP6EDDPz9HOuTIJfv47IRCFG8vk",
    authDomain: "moneyzhackgs.firebaseapp.com",
    databaseURL: "https://moneyzhackgs.firebaseio.com",
    projectId: "moneyzhackgs",
    storageBucket: "moneyzhackgs.appspot.com",
    messagingSenderId: "3179122161",
    appId: "1:3179122161:web:7d554bb54f0c4256"
  });

// Get a database reference to our blog
var db = admin.database();

class CustomerService{

    constructor(){
        this.customers=[
            "9929552033",
            "2518311936",
            "1265934011",
            "4081457102",
            "5452733570",
            /*"1088781372",
            "9617015739",
            "7846544552",
            "8933783681"*/
        ]
    }

    getCustomer(){
        
        return new Promise((res,rej)=>{
            const i=Math.floor(Math.random() * 5);
            console.log(i+":"+this.customers[i]);
            let ref=db.ref(this.customers[i]);
            console.log(ref.toJSON());
            ref.on("value", snapshot=> {
                res(snapshot.val());
          }, errorObject=>{
                rej(errorObject);
          });
        });
        
    }

    updateCustomer(data){
        console.log(data);
        return db.ref(data.info.id).update(data);
    }

    addCustomer(data){
        var ref = db.ref(data.info.id);
        return ref.set(data);
    }

}

// eslint-disable-next-line no-extend-native
String.prototype.hashCode = function() {
    var hash = 0, i, chr;
    if (this.length === 0) return hash;
    for (i = 0; i < this.length; i++) {
      chr   = this.charCodeAt(i);
      hash  = ((hash << 5) - hash) + chr;
      hash |= 0; // Convert to 32bit integer
    }
    return hash;
  };
module.exports=CustomerService;