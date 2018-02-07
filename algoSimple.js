var _ = require('lodash')

function algoSimple(maxX, maxY, turn, maxPayload, productWeights, delivers, wareHouses, drones) {
    console.log(wareHouses);
    console.log("TO");
    console.log(delivers);
    console.log('WITH')
    console.log(drones)
console.log('------------------------------')
	var end = false;
	_.forOwn(delivers, function(deliver, coordinates) {
		console.log(deliver)
	});
    
}


module.exports.resolve = algoSimple;
