const mongoose = require('mongoose');

var Reclamation = mongoose.model('Reclamation', {
	aEmail : {type : String},
	commentaires:{ type: String },

			anomalie :{ type: String },
			positionLatitude : { type: String },
			positionLongitude : { type: String },
			imageUrl:{ type :String},
			date : { type: String },
			etat : { type: String },
		   
});

module.exports = { Reclamation };