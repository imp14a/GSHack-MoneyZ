
class Credit
{
    constructor(type,image,name,identifier,balance,top,dueDate,minimumPayment
        ){
        this.type= type;
        this.image= image;
        this.name= name;
        this.identifier= identifier;
        this.balance= balance;
        this.top= top;
        this.dueDate= dueDate;
        this.minimumPayment= minimumPayment;
    }

}

module.exports = Credit;