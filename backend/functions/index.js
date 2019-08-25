const functions = require('firebase-functions');
const CustomerService = require('./services/customer.service');
const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();

app.use(cors());
app.use(bodyParser.json());

app.get('/', (req, res) => {
    // eslint-disable-next-line promise/always-return
    new CustomerService().getCustomer().then(data=>{
        res.send(data);
    }).catch(err=>{
        res.status(400).send(err);
    })
});
app.post('/', (req, res) => {
    console.log(req.body);
    res.send(new CustomerService().addCustomer(req.body));
});
app.post('/update', (req, res) => {
    console.log(req.body);
    res.send(new CustomerService().updateCustomer(req.body));
});

exports.smartfolio = functions.https.onRequest(app);