 const express = require('express');
var router = express.Router();
var ObjectId = require('mongoose').Types.ObjectId;

var { Reclamation } = require('../models/reclamation');


router.get('/', (req, res) => {
    Reclamation.find((err, docs) => {
        if (!err) { res.send(docs); }
        else { console.log('Error in Retriving Reclamation :' + JSON.stringify(err, undefined, 2)); }
    });
});

router.get('/:id', (req, res) => {
    if (!ObjectId.isValid(req.params.id))
        return res.status(400).send(`No record with given id : ${req.params.id}`);

  Reclamation.findById(req.params.id, (err, doc) => {
        if (!err) { res.send(doc); }
        else { console.log('Error in Retriving Reclamation :' + JSON.stringify(err, undefined, 2)); }
    });
});

router.post('/', (req, res) => {
    var reclamation = new Reclamation({
        mEmail :req.body.mEmail,
        commentaires : req.body.commentaires,
	anomalie : req.body.anomalie,
    positionLatitude: req.body.positionLatitude,
    positionLongitude: req.body.positionLongitude,
    date :req.body.imageUrl,
	 date :req.body.date,
     etat :req.body.etat,
    
    });
    reclamation.save((err, doc) => {
        if (!err) { res.send(doc); }
        else { console.log('Error in Reclamation Save :' + JSON.stringify(err, undefined, 2)); }
    });
});

router.put('/:id', (req, res) => {

    console.log("PUT Reclamation");
   
    var reclamation = {
        id : req.body._id,
        mEmail :req.body.mEmail,
       description: req.body.description,
       anomalie: req.body.anomalie,
        positionLatitude: req.body.positionLatitude,
        positionLongitude: req.body.positionLongitude,
        date: req.body.imageUrl,
       date: req.body.date,
       etat : req.body.etat,
     
    };

    console.log("reclamation:"+JSON.stringify(reclamation));
    Reclamation.findByIdAndUpdate(reclamation.id, { $set: reclamation }, { new: true }, (err, doc) => {
        if (!err) { res.send(doc); }
        else { console.log('Error in reclamation Update :' + JSON.stringify(err, undefined, 2)); }
    });
});

router.delete('/:id', (req, res) => {
    if (!ObjectId.isValid(req.params.id))
        return res.status(400).send(`No record with given id : ${req.params.id}`);

   Reclamation.findByIdAndRemove(req.params.id, (err, doc) => {
        if (!err) { res.send(doc); }
        else { console.log('Error in Reclamation Delete :' + JSON.stringify(err, undefined, 2)); }
    });
});

module.exports = router;