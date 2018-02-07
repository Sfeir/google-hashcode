var load = function( droneId, warehouseId, productType, quantity) {
    return [droneId, 'L', warehouseId, productType, quantity].join(" ");
}

var unload = function(droneId, warehouseId, productType, quantity) {
    return [droneId, 'U', warehouseId, productType, quantity].join(" ");
}

var deliver = function(droneId, orderId, productType, quantity) {
    return [droneId, 'D', orderId, productType, quantity].join(" ");
}

var wait = function(droneId, waitingTurns) {
    return [droneId, 'W', waitingTurns].join(" ");
}

module.exports.load = load;
module.exports.deliver = deliver;
module.exports.unload = unload;
module.exports.wait=wait;
