var current = "busy_day"

var utils = require('./utilitaires');

var algo = require('./algoByWeight');

var writeToFile = require('./outputs');

var file = "./input/"+current+".in";
var output = "./output/"+current+".out";

var fs = require('fs');

var map = [];
var maxX=0;
var maxY=0;
var drones = [];
var turn = 0;
var maxPayload = 0;
var productWeights = [];
var wareHouses = [];
var delivers = [];

fs.readFile(file, 'utf8', function (err, data) {
	var lines = data.split("\n");
	var i = 0;
	var initData = lines[i++].split(" ");
	maxX = parseInt(initData[0]);
	maxY = parseInt(initData[1]);
	var nbDrones = parseInt(initData[2]);
    turn = parseInt(initData[3]);
	maxPayload = parseInt(initData[4]);
	i++; //osef
    productWeights = lines[i++].split(" ").map(function(param){return parseInt(param)});
    var nbWare = lines[i++];
    var firstWWare = "";
    for(var j=0; j<nbWare; j++){
        var coords = lines[i++];
        if(firstWWare==""){
            firstWWare=coords;
        }
        var products = lines[i++].split(" ").map(function(param){return parseInt(param)});
        wareHouses[j]={};
        wareHouses[j].coords=coords;
        wareHouses[j].products=products;
    }
    var nbOrders = lines[i++];
    for(var k=0; k<nbOrders; k++){
        var coords = lines[i++];
        lines[i++]; //osef
        var products = lines[i++].split(" ").map(function(param){return parseInt(param)});
        var productMap = [];
        for(var m=0;m<productWeights.length;m++){
            productMap[m]=0;
        }
        for(product in products){
            productMap[products[product]]++;
        }
        delivers[k]={};
        delivers[k].coords=coords;
        delivers[k].products=productMap;
    }
    for(var l=0;l<nbDrones;l++){
        drones[l]=[firstWWare,[]];
        for(var m=0;m<productWeights.length;m++){
            drones[l][1][m]=0;
        }
    }
    var commands = algo.resolve(maxX, maxY, maxPayload, productWeights, delivers, wareHouses, drones);

    writeToFile.writeCommands(commands,output);

});

fs.writeFile(output, 'data to write', function(err) {
    if(err) {
        return console.log(err);
    }
});
