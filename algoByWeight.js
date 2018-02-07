var commands = require('./commands');
var _ = require('lodash');

function algoSimple(maxX, maxY, maxPayload, productWeights, delivers, wareHouses, drones) {
    var result=[]
    for(deliverId in delivers){
        coord = delivers[deliverId].coords;
        totalWeight = 0;
        for(colisId in delivers[deliverId].products){
            var nbColis = delivers[deliverId].products[colisId];
            totalWeight+=nbColis*productWeights[colisId];
        }
        if(totalWeight<maxPayload){
            var droneId=nextAvailableDrone(drones);
            stop:for(colisId in delivers[deliverId].products){
                var nbcolis = delivers[deliverId].products[colisId];
                if(nbcolis==0)continue;
                for(wareId in wareHouses){
                    var nbAvailables =wareHouses[wareId].products[colisId];
                    if(nbAvailables>=nbcolis){
                        result.push(droneId+" L "+wareId+" "+colisId+" "+nbcolis);
                        console.log("drone "+droneId+" load in ware "+wareId+": "+nbcolis+" of type "+colisId);
                        wareHouses[wareId].products[colisId]-=nbcolis;
                        drones[droneId][1][colisId]+=nbcolis;
                        delivers[deliverId].products[colisId]-=nbcolis;
                        continue stop;
                    }
                }
            }
            for(colisId in delivers[deliverId].products){
                var nbcolis = delivers[deliverId].products[colisId];
                if(nbcolis==0)continue;
                for(wareId in wareHouses){
                    var nbAvailables =wareHouses[wareId].products[colisId];
                    if(nbAvailables>=0){
                        result.push(droneId+" L "+wareId+" "+colisId+" "+nbcolis);
                        console.log("drone "+droneId+" load in ware "+wareId+": "+nbAvailables+" of type "+colisId);
                        wareHouses[wareId].products[colisId]-=nbAvailables;
                        drones[droneId][1][colisId]+=nbAvailables;
                        delivers[deliverId].products[colisId]-=nbAvailables;
                    }
                }
            }
            for(product in drones[droneId][1]){
                if(drones[droneId][1][product]==0)continue;
                result.push(droneId+" D "+deliverId+" "+product+" "+drones[droneId][1][product]);
                console.log("drone "+droneId+" deliver to"+deliverId+": "+drones[droneId][1][product]+" of type "+product);
                drones[droneId][1][product]-=drones[droneId][1][product];
            }
        }else{
            var localWeight = 0;
            loop:while(localWeight<=maxPayload){
                var droneId=nextAvailableDrone(drones);
                for(colisId in delivers[deliverId].products){
                    if(localWeight>0 && localWeight+productWeights[colisId]>maxPayload){
                        for(product in drones[droneId][1]){
                            if(drones[droneId][1][product]==0)continue;
                            result.push(droneId+" D "+deliverId+" "+product+" "+drones[droneId][1][product]);
                            console.log("drone "+droneId+" deliver to"+deliverId+": "+drones[droneId][1][product]+" of type "+product);
                            drones[droneId][1][product]-=drones[droneId][1][product];
                        }
                        break loop;
                    }
                    var nbcolis = delivers[deliverId].products[colisId];
                    if(nbcolis==0)continue;
                    for(wareId in wareHouses){
                        var nbAvailables =wareHouses[wareId].products[colisId];
                        if(nbAvailables>=0){
                            result.push(droneId+" L "+wareId+" "+colisId+" "+1);
                            console.log("drone "+droneId+" load in ware "+wareId+": "+1+" of type "+colisId);
                            wareHouses[wareId].products[colisId]-=1;
                            drones[droneId][1][colisId]+=1;
                            delivers[deliverId].products[colisId]-=1;
                            break;
                        }
                    }
                    localWeight+=productWeights[colisId];
                }
                if(localWeight>0)
                for(product in drones[droneId][1]){
                    if(drones[droneId][1][product]==0)continue;
                    result.push(droneId+" D "+deliverId+" "+product+" "+drones[droneId][1][product]);
                    console.log("drone "+droneId+" deliver to"+deliverId+": "+drones[droneId][1][product]+" of type "+product);
                    drones[droneId][1][product]-=drones[droneId][1][product];
                }
            }
        }

    }

    return result;
}

function nextAvailableDrone(drones){
    return current++%drones.length;
}

var current=0;

module.exports.resolve = algoSimple;
