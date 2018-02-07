module.exports = {
	distance: function(coordinate1, coordinate2) {
		var point1 = {
			x: coordinate1.split(' ')[0],
			y: coordinate1.split(' ')[1]
		},
		point2 = {
			x: coordinate2.split(' ')[0],
			y: coordinate2.split(' ')[1]
		};
		return Math.sqrt(
			Math.pow(point1.x - point2.x, 2)
			+ Math.pow(point1.y - point2.y, 2)
		);
	}
};


